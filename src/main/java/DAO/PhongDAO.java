package DAO;

import java.sql.*;
import java.util.*;

import entity.Phong;

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
        String query = "{CALL USP_getRoomList}";
        ArrayList<Phong> dataList = new ArrayList<Phong>();
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (SQLException e) {
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
        Object[] parameter = new Object[] { roomId };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        Phong phong = null;
        try {
            if (rs.next()) {
                phong = new Phong(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phong;
    }

    /**
     * Lấy danh sách phòng dựa trên tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng
     */
    public ArrayList<Phong> getRoomListByRoomTypeName(String roomTypeName) {
        String query = "{CALL USP_getRoomListByRoomTypeName( ? )}";
        Object[] parameter = new Object[] { roomTypeName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<Phong> dsPhong = new ArrayList<Phong>();
        try {
            while (rs.next()) {
                dsPhong.add(new Phong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
    }

    /**
     * Lấy danh sách phòng trống
     * 
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     */
    public ArrayList<Phong> getListAvailableRoom() {
        String query = "{CALL USP_getListAvailableRoom}";
        Object[] parameter = new Object[] {};
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<Phong> dsPhong = new ArrayList<Phong>();
        try {
            while (rs.next()) {
                dsPhong.add(new Phong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
    }

    /**
     * Lấy danh sách phòng trống theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     */
    public ArrayList<Phong> getListAvailableRoomByRoomTypeName(String roomTypeName) {
        String query = "{CALL USP_getListAvailableRoomByRoomTypeName( ? )}";
        Object[] parameter = new Object[] { roomTypeName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<Phong> dsPhong = new ArrayList<Phong>();
        try {
            while (rs.next()) {
                dsPhong.add(new Phong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
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
        Object[] parameter = new Object[] { status, roomId };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Chuyển đổi phòng
     * 
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
        Object[] parameter = new Object[] { billId, oldRoomId, newRoomId };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách phòng theo vị trí
     * 
     * @param location {@code String}: từ khóa tìm kiếm
     * @return {@code ArrayList<Phong>}: danh sách phòng
     */
    public ArrayList<Phong> getRoomListByLocation(String location) {
        String query = "{CALL USP_getRoomListByLocation( ? )}";
        Object[] parameter = new Object[] { location };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<Phong> dsPhong = new ArrayList<Phong>();
        try {
            while (rs.next()) {
                dsPhong.add(new Phong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
    }

    /**
     * Lấy danh sách phòng theo trạng thái của phòng
     * 
     * @param roomStatus {@code int}: tính trạng phòng
     *                   <ul>
     *                   <li>Nếu phòng còn trống thì truyền vào {@code 0}</li>
     *                   <li>Nếu phòng đang sử dụng thì truyền vào {@code 1}</li>
     *                   </ul>
     * @return {@code ArrayList<Phong>}: danh sách phòng
     */
    public ArrayList<Phong> getRoomListByStatus(int roomStatus) {
        String query = "{CALL USP_getRoomListByStatus( ? )}";
        Object[] parameter = new Object[] { roomStatus };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<Phong> dsPhong = new ArrayList<Phong>();
        try {
            while (rs.next()) {
                dsPhong.add(new Phong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
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
        Object[] parameter = new Object[] { room.getMaPhong(), room.getTinhTrangP(), room.getViTri(),
                room.getLoaiPhong().getMaLP() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
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
        Object[] parameter = new Object[] { room.getMaPhong(), room.getTinhTrangP(), room.getViTri(),
                room.getLoaiPhong().getMaLP() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
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
        String query = "{CALL USP_getLastRoomId}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String roomId = obj != null ? obj.toString() : "";
        return roomId;
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
        Object[] parameter = new Object[] { billId };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        Phong phong = null;
        try {
            if (rs.next()) {
                phong = new Phong(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phong;
    }
}
