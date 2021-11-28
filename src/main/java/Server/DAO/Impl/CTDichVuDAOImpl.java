package DAO.Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import DAO.CTDichVuDAO;
import Server.Util.HibernateUtil;
import entity.CTDichVu;
import entity.DichVu;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code CTDichVu}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 13/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class CTDichVuDAOImpl extends UnicastRemoteObject implements CTDichVuDAO {
    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public CTDichVuDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * Lấy danh sách chi tiết dịch vụ theo mã phòng
     * 
     * @param roomId {@code String}: mã phòng cần tìm
     * @return {@code ArrayList<CTDichVu>}: danh sách chi tiết dịch vụ
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<CTDichVu> getServiceDetailListByRoomId(String roomId) throws RemoteException {
        ArrayList<CTDichVu> dataList = new ArrayList<CTDichVu>();
        String query = "{CALL USP_getServiceDetailListByRoomId( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<CTDichVu>) em.createNativeQuery(query, CTDichVu.class).setParameter(1, roomId)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách chi tiết dịch vụ theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn cần tìm
     * @return {@code ArrayList<CTDichVu>}: danh sách chi tiết dịch vụ
     * @throws RemoteException Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<CTDichVu> getServiceDetailListByBillId(String billId) throws RemoteException {
        ArrayList<CTDichVu> dataList = new ArrayList<CTDichVu>();
        String query = "{CALL USP_getServiceDetailListByBillId( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<CTDichVu>) em.createNativeQuery(query, CTDichVu.class).setParameter(1, billId)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra 1 chi tiết dịch vụ dựa trên mã hóa đơn và mã dịch vụ
     * 
     * @param billId {@code String}: mã hóa đơn cần tìm
     * @param serviceId {@code String}: mã dịch vụ cần tìm
     * @return {@code CTHoaDon}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code CTHoaDon}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    @Override
    public CTDichVu getServiceDetailByBillIdAndServiceId(String billId, String serviceId) throws RemoteException {
        CTDichVu result = null;
        String query = "{CALL USP_getServiceDetailByBillIdAndServiceId( ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            result = (CTDichVu) em.createNativeQuery(query, CTDichVu.class)
                    .setParameter(1, billId)
                    .setParameter(2, serviceId)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thêm một chi tiết dịch vụ
     * 
     * @param serviceDetail   {@code CTDichVu}: chi tiết dịch vụ cần thêm
     * @param quantity {@code int}: số lượng đặt
     * @param billId   {@code String}: mã hóa đơn
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    @Override
    public boolean insertServiceDetail(CTDichVu serviceDetail, int quantity, String billId) throws RemoteException {
        String query = "{CALL USP_insertServiceDetail( ? , ? , ? , ? )}";
        DichVu service = serviceDetail.getDichVu();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, service.getMaDichVu())
                    .setParameter(2, billId)
                    .setParameter(3, quantity)
                    .setParameter(4, serviceDetail.getDonGia())
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }
}
