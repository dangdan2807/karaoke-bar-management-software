package DAO.Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Util.HibernateUtil;
import entity.LoaiPhong;
import DAO.LoaiPhongDAO;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code LoaiPhong}
 * <p>
 * Người tham gia thiết kế: Võ Minh Hiếu
 * <p>
 * Ngày tạo: 03/10/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public class LoaiPhongDAOImpl extends UnicastRemoteObject implements LoaiPhongDAO {
    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public LoaiPhongDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * Lấy danh sách tất cả loại phòng
     * 
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<LoaiPhong> getRoomTypeList() throws RemoteException {
        ArrayList<LoaiPhong> dataList = new ArrayList<LoaiPhong>();
        String query = "{CALL USP_getRoomTypeList()}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<LoaiPhong>) em.createNativeQuery(query, LoaiPhong.class)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách loại phòng theo từng trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<LoaiPhong> getRoomTypeListAndPageNumber(int currentPage, int lineNumberDisplayed)
            throws RemoteException {
        ArrayList<LoaiPhong> dataList = new ArrayList<LoaiPhong>();
        String query = "{CALL USP_getRoomTypeListAndPageNumber( ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<LoaiPhong>) em.createNativeQuery(query, LoaiPhong.class)
                    .setParameter(1, currentPage)
                    .setParameter(2, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại phòng
     * 
     * @return {@code int}: số lượng loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfRoomTypeList() throws RemoteException {
        String query = "{CALL USP_getTotalLineOfRoomTypeList()}";
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
     * Lấy tên loại phòng theo mã loại phòng
     * 
     * @param roomId {@code String}: mã loại phòng
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên loại phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getRoomTypeNameById(String roomId) throws RemoteException {
        String query = "{CALL USP_getRoomTypeNameById( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        String roomTypeName = "";
        try {
            tr.begin();
            ArrayList<String> resultQuery = (ArrayList<String>) em.createNativeQuery(query)
                    .setParameter(1, roomId)
                    .getResultList();
            tr.commit();
            if (resultQuery.size() > 0) {
                roomTypeName = resultQuery.get(0);
            } else {
                roomTypeName = "";
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return roomTypeName;
    }

    /**
     * Lấy danh sách loại phòng có tên và số trang
     * 
     * @param roomTypeName        {@code String}: từ khóa trong tên loại phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<LoaiPhong> getRoomTypeListByNameAndPageNumber(String roomTypeName, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getRoomTypeListByNameAndPageNumber( ? , ? , ? )}";
        ArrayList<LoaiPhong> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<LoaiPhong>) em.createNativeQuery(query, LoaiPhong.class)
                    .setParameter(1, roomTypeName)
                    .setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại phòng theo tên và số trang
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfRoomTypeListByName(String roomTypeName) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfRoomTypeListByName( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, roomTypeName).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy mã loại phòng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã loại phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getLastRoomTypeId() throws RemoteException {
        String query = "{CALL USP_getLastRoomTypeId()}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        String roomTypeId = "";
        try {
            tr.begin();
            ArrayList<String> resultQuery = (ArrayList<String>) em.createNativeQuery(query)
                    .getResultList();
            tr.commit();
            if (resultQuery.size() > 0) {
                roomTypeId = resultQuery.get(0);
            } else {
                roomTypeId = "";
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return roomTypeId;
    }

    /**
     * Lấy thông tin loại phòng dựa theo mã phòng
     * 
     * @param roomTypeId {@code String}: mã loại phòng
     * @return {@code LoaiPhong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiPhong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public LoaiPhong getRoomTypeById(String roomTypeId) throws RemoteException {
        String query = "{CALL USP_getRoomTypeById( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        LoaiPhong result = null;
        try {
            tr.begin();
            ArrayList<LoaiPhong> resultQuery = (ArrayList<LoaiPhong>) em.createNativeQuery(query, LoaiPhong.class)
                    .setParameter(1, roomTypeId)
                    .getResultList();
            tr.commit();
            if (resultQuery.size() > 0) {
                result = resultQuery.get(0);
            } else {
                result = null;
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thêm một loại phòng mới vào cơ sở dữ liệu
     * 
     * @param roomType {@code LoaiPhong}: loại phòng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public Boolean insertRoomType(LoaiPhong roomType) throws RemoteException {
        String query = "{CALL USP_insertRoomType( ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, roomType.getMaLP())
                    .setParameter(2, roomType.getTenLP())
                    .setParameter(3, roomType.getSucChua())
                    .setParameter(4, roomType.getGiaTien())
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Cập nhật thông tin loại phòng
     * 
     * @param roomType {@code LoaiPhong}: loại phòng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public Boolean updateInfoRoomType(LoaiPhong roomType) throws RemoteException {
        String query = "{CALL USP_updateInfoRoomType( ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, roomType.getMaLP())
                    .setParameter(2, roomType.getTenLP())
                    .setParameter(3, roomType.getSucChua())
                    .setParameter(4, roomType.getGiaTien())
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Lấy danh sách loại phòng theo giá phòng và số trang
     * 
     * @param price               {@code String}: Giá phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<LoaiPhong> getRoomTypeListByPriceAndPageNumber(String price, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getRoomTypeListByPriceAndPageNumber( ? , ? , ? )}";
        ArrayList<LoaiPhong> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<LoaiPhong>) em.createNativeQuery(query, LoaiPhong.class)
                    .setParameter(1, price)
                    .setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại phòng theo giá và số trang
     * 
     * @param price {@code String}: Giá phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfRoomTypeListByPrice(String price) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfRoomTypeListByPrice( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, price)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy thông tin loại phòng dựa theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code LoaiPhong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiPhong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public LoaiPhong getRoomTypeByName(String roomTypeName) throws RemoteException {
        String query = "{CALL USP_getRoomTypeByName( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        LoaiPhong result = null;
        try {
            tr.begin();
            ArrayList<LoaiPhong> resultQuery = (ArrayList<LoaiPhong>) em.createNativeQuery(query, LoaiPhong.class)
                    .setParameter(1, roomTypeName)
                    .getResultList();
            tr.commit();
            if (resultQuery.size() > 0) {
                result = resultQuery.get(0);
            } else {
                result = null;
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }
}
