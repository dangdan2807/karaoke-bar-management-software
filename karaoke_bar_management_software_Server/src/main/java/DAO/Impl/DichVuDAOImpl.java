package DAO.Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import DAO.DichVuDAO;
import Util.HibernateUtil;
import entity.DichVu;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code DichVu}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Đỗ Thị Tường Vi
 * <p>
 * Ngày tạo: 11/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class DichVuDAOImpl extends UnicastRemoteObject implements DichVuDAO {
    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public DichVuDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * Lấy danh sách tất cả dịch vụ
     * 
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<DichVu> getServiceList() throws RemoteException {
        String query = "{CALL USP_getServiceList()}";
        ArrayList<DichVu> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<DichVu>) em.createNativeQuery(query, DichVu.class).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<DichVu> getServiceListAndPageNumber(int currentPage, int lineNumberDisplayed)
            throws RemoteException {
        String query = "{CALL USP_getServiceListAndPageNumber( ? , ? )}";
        ArrayList<DichVu> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<DichVu>) em.createNativeQuery(query, DichVu.class).setParameter(1, currentPage)
                    .setParameter(2, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng dịch vụ
     * 
     * @return {@code int}: số lượng dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfServiceList() throws RemoteException {
        String query = "{CALL USP_getTotalLineOfServiceList()}";
        em.clear();
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
        return result;
    }

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<DichVu> getServiceListByServiceTypeName(String serviceTypeName) throws RemoteException {
        String query = "{CALL USP_getServiceListByServiceTypeName( ? )}";
        ArrayList<DichVu> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<DichVu>) em.createNativeQuery(query, DichVu.class).setParameter(1, serviceTypeName)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName     {@code String}: tên loại dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<DichVu> getServiceListByServiceTypeNameAndPageNumber(String serviceTypeName, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getServiceListByServiceTypeNameAndPageNumber( ? , ? , ? )}";
        ArrayList<DichVu> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<DichVu>) em.createNativeQuery(query, DichVu.class).setParameter(1, serviceTypeName).setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code int}: số lượng dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfServiceListByServiceTypeName(String serviceTypeName) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfServiceListByServiceTypeName( ? )}";
        em.clear();
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
        return result;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ
     * 
     * @param serviceName {@code String}: tên dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<DichVu> getServiceListByName(String serviceName) throws RemoteException {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getServiceListByName( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<DichVu>) em.createNativeQuery(query, DichVu.class).setParameter(1, serviceName)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ và số trang
     * 
     * @param serviceName         {@code String}: tên dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<DichVu> getServiceListByNameAndPageNumber(String serviceName, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getServiceListByNameAndPageNumber( ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<DichVu>) em.createNativeQuery(query, DichVu.class).setParameter(1, serviceName).setParameter(2, currentPage).setParameter(3, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng dịch vụ theo tên dịch vụ
     * 
     * @param serviceName {@code String}: tên dịch vụ
     * @return {@code int}: số lượng dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfServiceListByName(String serviceName) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfServiceListByName( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, serviceName).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ và tên loại dịch vụ
     * 
     * @param serviceName     {@code String}: tên dịch vụ
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<DichVu> getServiceListByNameAndServiceTypeName(String serviceName, String serviceTypeName) throws RemoteException {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getServiceListByNameAndServiceTypeName( ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<DichVu>) em.createNativeQuery(query, DichVu.class).setParameter(1, serviceName)
                    .setParameter(2, serviceTypeName).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy mã dịch vụ được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public String getLastServiceID() throws RemoteException {
        String query = "{CALL USP_getLastServiceId}";
        em.clear();
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
        return result;
    }

    /**
     * Thêm dịch vụ mới
     * 
     * @param service {@code DichVu}: dịch vụ cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public boolean insertService(DichVu service) throws RemoteException {
        String query = "{CALL USP_insertService( ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, service.getMaDichVu())
                    .setParameter(2, service.getTenDichVu())
                    .setParameter(3, service.getGiaBan())
                    .setParameter(4, service.getSoLuongTon())
                    .setParameter(5, service.getLoaiDV().getMaLDV())
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Lấy tên dịch vụ theo mã dịch vụ
     * 
     * @param serviceId {@code String}: mã dịch vụ
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public String getServiceNameById(String serviceId) throws RemoteException {
        String query = "{CALL  USP_getServiceNameById( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        String result = "";
        try {
            tr.begin();
            result = (String) em.createNativeQuery(query).setParameter(1, serviceId).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cập nhật thông tin dịch vụ
     * 
     * @param service {@code DichVu}: dịch vụ cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public Boolean updateInfoService(DichVu service) throws RemoteException {
        String query = "{CALL USP_updateInfoService( ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, service.getMaDichVu())
                    .setParameter(2, service.getTenDichVu())
                    .setParameter(3, service.getGiaBan())
                    .setParameter(4, service.getSoLuongTon())
                    .setParameter(5, service.getLoaiDV().getMaLDV())
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }
}
