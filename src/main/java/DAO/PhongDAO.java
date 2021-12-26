package DAO;

import java.sql.*;
import java.util.ArrayList;

import DAO.PhongDAO;
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
public class PhongDAO {
    private static PhongDAO instance = new PhongDAO();
    public static int ROOM_WIDTH = 90;
    public static int ROOM_HEIGHT = 90;
    public static int EMPTY = 0;
    public static int RENT = 1;

    public static PhongDAO getInstance() {
        if (instance == null)
            instance = new PhongDAO();
        return instance;
    }

    /**
     * Lấy danh sách tất cả phòng
     * 
     * @return {@code ArrayList<Phong>}: danh sách phòng
     */
    public ArrayList<Phong> getRoomList() {
        String query = "{CALL USP_getRoomList()}";
        ArrayList<Phong> dataList = new ArrayList<>();
        ResultSet rs = DataProvider.getInstance().executeQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (Exception e) {
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
     */
    public Phong getRoomByRoomId(String roomId) {
        String query = "{CALL USP_getRoomByRoomId( ? )}";
        Object[] params = new Object[] { roomId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        Phong result = null;
        try {
            while (rs.next()) {
                result = new Phong(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách phòng dựa trên tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng
     */
    public ArrayList<Phong> getRoomListByRoomTypeName(String roomTypeName) {
        String query = "{CALL USP_getRoomListByRoomTypeName( ? )}";
        Object[] params = new Object[] { roomTypeName };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<Phong> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (Exception e) {
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
     */
    public ArrayList<Phong> getRoomListByRoomTypeNameAndPageNumber(String roomTypeName, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getRoomListByRoomTypeNameAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { roomTypeName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<Phong> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng phòng theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code int}: số lượng phòng
     */
    public int getTotalLineOfRoomListByRoomTypeName(String roomTypeName) {
        String query = "{CALL USP_getTotalLineOfRoomListByRoomTypeName( ? )}";
        Object[] params = new Object[] { roomTypeName };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách phòng trống
     * 
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     */
    public ArrayList<Phong> getListAvailableRoom() {
        String query = "{CALL USP_getListAvailableRoom()}";
        ArrayList<Phong> dataList = new ArrayList<>();
        ResultSet rs = DataProvider.getInstance().executeQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách phòng trống theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     */
    public ArrayList<Phong> getListAvailableRoomByRoomTypeName(String roomTypeName) {
        String query = "{CALL USP_getListAvailableRoomByRoomTypeName( ? )}";
        Object[] params = new Object[] { roomTypeName };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<Phong> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (Exception e) {
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
     */
    public boolean updateRoomStatus(String roomId, int status) {
        String query = "{CALL USP_updateRoomStatus( ? , ? )}";
        Object[] params = new Object[] { status, roomId };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Chuyển phòng đang cho thuê
     * 
     * @param roomId    {@code String}: mã hóa đơn cần chuyển phòng
     * @param oldRoomId {@code String}: mã phòng cũ
     * @param newRoomId {@code String}: mã phòng mới
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean switchRoom(String billId, String oldRoomId, String newRoomId) {
        String query = "{CALL USP_switchRoom( ? , ? , ? )}";
        Object[] params = new Object[] { billId, oldRoomId, newRoomId };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách phòng theo vị trí
     * 
     * @param location            {@code String}: vị trí của phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<Phong>}: danh sách phòng
     */
    public ArrayList<Phong> getRoomListByLocationAndPageNumber(String location, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getRoomListByLocationAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { location, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<Phong> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng phòng theo trạng thái
     * 
     * @param location {@code String}: ví trí phòng
     * @return {@code int}: số lượng phòng
     */
    public int getTotalLineOfRoomListByLocation(String location) {
        String query = "{CALL USP_getTotalLineOfRoomListByLocation( ? )}";
        Object[] params = new Object[] { location };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
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
     */
    public ArrayList<Phong> getRoomListByStatusAndPageNumber(int roomStatus, int currentPage, int lineNumberDisplayed) {
        String query = "{CALL USP_getRoomListByStatusAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { roomStatus, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<Phong> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (Exception e) {
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
     */
    public int getTotalLineOfRoomListByStatus(int roomStatus) {
        String query = "{CALL USP_getTotalLineOfRoomListByStatus( ? )}";
        Object[] params = new Object[] { roomStatus };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
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
     */
    public Boolean updateInfoRoom(Phong room) {
        String query = "{CALL USP_updateInfoRoom( ? , ? , ? , ? )}";
        Object[] params = new Object[] { room.getMaPhong(), room.getTinhTrangP(), room.getViTri(),
                room.getLoaiPhong().getMaLP() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
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
     */
    public Boolean insertRoom(Phong room) {
        String query = "{CALL USP_insertRoom( ? , ? , ? , ? )}";
        Object[] params = new Object[] { room.getMaPhong(), room.getTinhTrangP(), room.getViTri(),
                room.getLoaiPhong().getMaLP() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
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
     */
    public String getLastRoomID() {
        String query = "{CALL USP_getLastRoomId()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        String result = obj != null ? obj.toString() : "";
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
     */
    public Phong getRoomByBillId(String billId) {
        String query = "{CALL USP_getRoomByBillId( ? )}";
        Object[] params = new Object[] { billId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        Phong result = null;
        try {
            while (rs.next()) {
                result = new Phong(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy số lượng phòng theo mã phòng
     * 
     * @param roomID {@code String}: mã phòng
     * @return {@code int}: số lượng phòng
     */
    public int getTotalLineOfRoomListByRoomID(String roomID) {
        String query = "{CALL USP_getTotalLineOfRoomListByRoomID( ? )}";
        Object[] params = new Object[] { roomID };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách phòng theo mã phòng
     * 
     * @param roomId            {@code String}: mã phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<Phong>}: danh sách phòng
     */
    public ArrayList<Phong> getRoomListByRoomIDAndPageNumber(String roomId, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getRoomListByRoomIDAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { roomId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<Phong> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
