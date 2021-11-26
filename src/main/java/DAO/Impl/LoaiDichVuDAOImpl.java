package DAO.Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import DAO.LoaiDichVuDAO;
import Util.HibernateUtil;
import entity.LoaiDichVu;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code LoaiDichVu}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Võ Minh Hiếu
 * <p>
 * Ngày tạo: 07/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class LoaiDichVuDAOImpl extends UnicastRemoteObject implements LoaiDichVuDAO {
    private static LoaiDichVuDAOImpl instance;
    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public LoaiDichVuDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * Sử dụng kiến trúc singleton để tạo ra 1 đối tượng duy nhất
     * 
     * @return {@code LoaiDichVuDAOImpl}
     */
    public static LoaiDichVuDAOImpl getInstance() {
        if (instance == null)
            try {
                instance = new LoaiDichVuDAOImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        return instance;
    }

    /**
     * Lấy danh sách thông tin tất cả loại dịch vụ
     * 
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<LoaiDichVu> getServiceTypeList() throws RemoteException {
        String query = "{CALL USP_getServiceTypeList()}";
        ArrayList<LoaiDichVu> dataList = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<LoaiDichVu>) em.createNativeQuery(query, LoaiDichVu.class).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return dataList;
    }

    /**
     * Lấy danh sách loại dịch vụ theo số trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<LoaiDichVu> getServiceTypeListAndPageNumber(int currentPage, int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getServiceTypeListAndPageNumber( ? , ? )}";
        ArrayList<LoaiDichVu> dataList = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<LoaiDichVu>) em.createNativeQuery(query, LoaiDichVu.class).setParameter(1, currentPage)
                    .setParameter(2, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return dataList;
    }

    /**
     * Lấy số lượng loại dịch vụ
     * 
     * @return {@code int}: số lượng loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfServiceTypeList() throws RemoteException {
        String query = "{CALL USP_getTotalLineOfServiceTypeList()}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy danh sách loại dịch vụ theo tên và số trang
     * 
     * @param serviceTypeName     {@code String}: từ khóa trong tên loại dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<LoaiDichVu> getServiceTypeListByNameAndPageNumber(String serviceTypeName, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getServiceTypeListByNameAndPageNumber( ? , ? , ? )}";
        ArrayList<LoaiDichVu> dataList = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<LoaiDichVu>) em.createNativeQuery(query, LoaiDichVu.class).setParameter(1, serviceTypeName).setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return dataList;
    }

    /**
     * Lấy số lượng loại dịch vụ theo tên
     * 
     * @param serviceTypeName {@code String}: từ khóa trong tên loại dịch vụ
     * @return {@code int}: số lượng loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfServiceTypeListByName(String serviceTypeName) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfServiceTypeListByName( ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, serviceTypeName).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy thông tin loại dịch vụ dựa theo tên
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code LoaiDichVu}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiDichVu}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public LoaiDichVu getServiceTypeByName(String serviceTypeName) throws RemoteException {
        String query = "{CALL USP_getServiceTypeByName( ? )}";
        EntityTransaction tr = em.getTransaction();
        LoaiDichVu result = null;
        try {
            tr.begin();
            result = (LoaiDichVu) em.createNativeQuery(query, LoaiDichVu.class).setParameter(1, serviceTypeName).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy mã loại dịch vụ được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã loại dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public String getLastServiceTypeID() throws RemoteException {
        String query = "{CALL USP_getLastServiceTypeID()}";
        EntityTransaction tr = em.getTransaction();
        String result = "";
        try {
            tr.begin();
            result = (String) em.createNativeQuery(query).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy thông tin loại dịch vụ dựa theo tên
     * 
     * @param serviceTypeID {@code String}: mã loại dịch vụ
     * @return {@code LoaiDichVu}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiDichVu}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public LoaiDichVu getServiceTypeById(String serviceTypeID) throws RemoteException {
        String query = "{CALL USP_getServiceTypeById( ? )}";
        EntityTransaction tr = em.getTransaction();
        LoaiDichVu result = null;
        try {
            tr.begin();
            result = (LoaiDichVu) em.createNativeQuery(query, LoaiDichVu.class).setParameter(1, serviceTypeID).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Thêm một loại dịch vụ mới vào cơ sở dữ liệu
     * 
     * @param serviceType {@code LoaiDichVu}: loại dịch vụ cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public Boolean insertService(LoaiDichVu serviceType) throws RemoteException {
        String query = "{CALL USP_insertServiceType( ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, serviceType.getMaLDV())
                    .setParameter(2, serviceType.getTenLDV())
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result > 0;
    }

    /**
     * Cập nhật thông tin loại dịch vụ vào cơ sở dữ liệu
     * 
     * @param serviceType {@code LoaiDichVu}: loại dịch vụ cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean updateInfoServiceType(LoaiDichVu serviceType) throws RemoteException {
        String query = "{CALL USP_updateInfoServiceType( ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, serviceType.getMaLDV())
                    .setParameter(2, serviceType.getTenLDV())
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result > 0;
    }
}
