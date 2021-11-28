package DAO.Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import DAO.PhongDAO;
import Server.Util.HibernateUtil;
import entity.Phong;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code Phong}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 10/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public class PhongDAOImpl extends UnicastRemoteObject implements PhongDAO {
    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public PhongDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * Lấy danh sách tất cả phòng
     * 
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Phong> getRoomList() throws RemoteException {
        String query = "{CALL USP_getRoomList()}";
        ArrayList<Phong> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<Phong>) em.createNativeQuery(query, Phong.class)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy phòng dựa trên mã phòng
     * 
     * @param roomId {@code String}: mã phòng
     * @return {@code Phong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code Phong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public Phong getRoomByRoomId(String roomId) throws RemoteException {
        String query = "{CALL USP_getRoomByRoomId( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        Phong result = null;
        try {
            tr.begin();
            result = (Phong) em.createNativeQuery(query, Phong.class)
                    .setParameter(1, roomId).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách phòng dựa trên tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Phong> getRoomListByRoomTypeName(String roomTypeName) throws RemoteException {
        String query = "{CALL USP_getRoomListByRoomTypeName( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        ArrayList<Phong> dataList = new ArrayList<>();
        try {
            tr.begin();
            dataList = (ArrayList<Phong>) em.createNativeQuery(query, Phong.class)
                    .setParameter(1, roomTypeName).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách phòng dựa trên tên loại phòng và số của trang
     * 
     * @param roomTypeName        {@code String}: tên loại phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Phong> getRoomListByRoomTypeNameAndPageNumber(String roomTypeName, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getRoomListByRoomTypeNameAndPageNumber( ? , ? , ? )}";
        ArrayList<Phong> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<Phong>) em.createNativeQuery(query, Phong.class)
                    .setParameter(1, roomTypeName)
                    .setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng phòng theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code int}: số lượng phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfRoomListByRoomTypeName(String roomTypeName) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfRoomListByRoomTypeName( ? )}";
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
     * Lấy danh sách phòng trống
     * 
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Phong> getListAvailableRoom() throws RemoteException {
        String query = "{CALL USP_getListAvailableRoom()}";
        ArrayList<Phong> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<Phong>) em.createNativeQuery(query, Phong.class)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách phòng trống theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Phong> getListAvailableRoomByRoomTypeName(String roomTypeName) throws RemoteException {
        String query = "{CALL USP_getListAvailableRoomByRoomTypeName( ? )}";
        ArrayList<Phong> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<Phong>) em.createNativeQuery(query, Phong.class)
                    .setParameter(1, roomTypeName).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * cập nhật trạng thái phòng
     * 
     * @param roomId {@code String}: mã phòng
     * @param status {@code int}: thông tin trạng thái phòng
     *               <ul>
     *               <li>Nếu phòng còn trống thì truyền vào {@code 0}</li>
     *               <li>Nếu phòng đã cho thuê thì truyền vào {@code 1}</li>
     *               </ul>
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public boolean updateRoomStatus(String roomId, int status) throws RemoteException {
        String query = "{CALL USP_updateRoomStatus( ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, status)
                    .setParameter(2, roomId)
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Chuyển phòng đang cho thuê
     * 
     * @param roomId {@code String}: mã hóa đơn cần chuyển phòng
     * @param oldRoomId {@code String}: mã phòng cũ
     * @param newRoomId {@code String}: mã phòng mới
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public boolean switchRoom(String billId, String oldRoomId, String newRoomId) throws RemoteException {
        String query = "{CALL USP_switchRoom( ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, billId)
                    .setParameter(2, oldRoomId)
                    .setParameter(3, newRoomId)
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Lấy danh sách phòng theo vị trí
     * 
     * @param location            {@code String}: vị trí của phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Phong> getRoomListByLocationAndPageNumber(String location, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getRoomListByLocationAndPageNumber( ? , ? , ? )}";
        ArrayList<Phong> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<Phong>) em.createNativeQuery(query, Phong.class)
                    .setParameter(1, location)
                    .setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng phòng theo trạng thái
     * 
     * @param location {@code String}: ví trí phòng
     * @return {@code int}: số lượng phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfRoomListByLocation(String location) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfRoomListByLocation( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, location).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách phòng theo trạng thái của phòng
     * 
     * @param roomStatus          {@code int}: tính trạng phòng
     *                            <ul>
     *                            <li>Nếu phòng còn trống thì truyền vào
     *                            {@code 0}</li>
     *                            <li>Nếu phòng đang sử dụng thì truyền vào
     *                            {@code 1}</li>
     *                            </ul>
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Phong> getRoomListByStatusAndPageNumber(int roomStatus, int currentPage, int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getRoomListByStatusAndPageNumber( ? , ? , ? )}";
        ArrayList<Phong> dataList = new ArrayList<>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<Phong>) em.createNativeQuery(query, Phong.class)
                    .setParameter(1, roomStatus)
                    .setParameter(2, currentPage)
                    .setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng phòng theo trạng thái
     * 
     * @param roomStatus {@code int}: tính trạng phòng
     *                   <ul>
     *                   <li>Nếu phòng còn trống thì truyền vào {@code 0}</li>
     *                   <li>Nếu phòng đang sử dụng thì truyền vào {@code 1}</li>
     *                   </ul>
     * @return {@code int}: số lượng phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfRoomListByStatus(int roomStatus) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfRoomListByStatus( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, roomStatus).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Cập nhật thông tin phòng
     * 
     * @param room {@code Phong}: thông tin phòng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean updateInfoRoom(Phong room) throws RemoteException {
        String query = "{CALL USP_updateInfoRoom( ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, room.getMaPhong())
                    .setParameter(2, room.getTinhTrangP())
                    .setParameter(3, room.getViTri())
                    .setParameter(4, room.getLoaiPhong().getMaLP())
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Thêm một phòng mới vào cơ sở dữ liệu
     * 
     * @param room {@code Phong}: phòng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean insertRoom(Phong room) throws RemoteException {
        String query = "{CALL USP_insertRoom( ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, room.getMaPhong())
                    .setParameter(2, room.getTinhTrangP())
                    .setParameter(3, room.getViTri())
                    .setParameter(4, room.getLoaiPhong().getMaLP())
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Lấy mã phòng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public String getLastRoomID() throws RemoteException {
        String query = "{CALL USP_getLastRoomId()}";
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
     * Lấy phòng dựa trên mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn
     * @return {@code Phong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code Phong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Phong getRoomByBillId(String billId) throws RemoteException {
        String query = "{CALL USP_getRoomByBillId( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        Phong result = null;
        try {
            tr.begin();
            result = (Phong) em.createNativeQuery(query, Phong.class)
                    .setParameter(1, billId).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }
}
