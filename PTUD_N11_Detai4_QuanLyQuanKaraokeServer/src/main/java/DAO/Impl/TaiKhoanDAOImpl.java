package DAO.Impl;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.*;

import DAO.TaiKhoanDAO;
import Util.HibernateUtil;
import entity.TaiKhoan;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code TaiKhoan}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 02/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public class TaiKhoanDAOImpl extends UnicastRemoteObject implements TaiKhoanDAO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public TaiKhoanDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * kiểm tra thông tin đăng nhập đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @param password {@code String}: mật khẩu
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException bắt lỗi remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean login(String username, String password) throws RemoteException {
        int count = 0;
        String queryStr = "{CALL USP_Login(?, ?)}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            ArrayList<TaiKhoan> accountList = (ArrayList<TaiKhoan>) em.createNativeQuery(queryStr, TaiKhoan.class)
                    .setParameter(1, username)
                    .setParameter(2, password).getResultList();
            tr.commit();
            if (accountList.size() > 0) {
                count = 1;
            } else {
                count = 0;
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * Lấy tài khoản dựa trên tên đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @return {@code TaiKhoan}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code TaiKhoan}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException bắt lỗi remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public TaiKhoan getAccountByUsername(String username) throws RemoteException {
        String query = "{CALL USP_getAccountByUsername( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        TaiKhoan account = null;
        try {
            tr.begin();
            ArrayList<TaiKhoan> accountList = (ArrayList<TaiKhoan>) em.createNativeQuery(query, TaiKhoan.class)
                    .setParameter(1, username).getResultList();
            tr.commit();
            if(accountList.size() > 0) {
                account = accountList.get(0);
            } else {
                account = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
}
