package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.LoaiPhong;

public class LoaiPhongDAO {
    private static LoaiPhongDAO instance = new LoaiPhongDAO();

    public static LoaiPhongDAO getInstance() {
        if (instance == null)
            instance = new LoaiPhongDAO();
        return instance;
    }

    /**
     * Lấy danh sách tất cả loại phòng
     * 
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public ArrayList<LoaiPhong> getRoomTypeList() {
        ArrayList<LoaiPhong> dataList = new ArrayList<LoaiPhong>();
        String query = "{CALL USP_getRoomTypeList}";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new LoaiPhong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
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
     */
    public String getRoomTypeNameById(String roomId) {
        String query = "{CALL USP_getRoomTypeNameById( ? )}";
        Object[] parameter = new Object[] { roomId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String roomTypeName = obj != null ? roomTypeName = obj.toString() : "";
        return roomTypeName;
    }

    /**
     * Lấy danh sách loại phòng có tên phù hợp với từ khóa
     * 
     * @param roomTypeName {@code String}: từ khóa trong tên loại phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng phù hợp điều kiện
     */
    public ArrayList<LoaiPhong> getRoomTypeListByName(String roomTypeName) {
        String query = "{CALL USP_getRoomTypeListByName( ? )}";
        Object[] parameter = new Object[] { roomTypeName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<LoaiPhong> staffList = new ArrayList<LoaiPhong>();
        try {
            while (rs.next()) {
                staffList.add(new LoaiPhong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    /**
     * Lấy mã loại phòng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã loại phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getLastRoomTypeId() {
        String query = "{CALL USP_getLastRoomTypeId}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String roomTypeId = obj != null ? obj.toString() : "";
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
     */
    public LoaiPhong getRoomTypeById(String roomTypeId) {
        LoaiPhong data = null;
        String query = "{CALL USP_getRoomTypeById( ? )}";
        Object[] parameter = new Object[] { roomTypeId };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next())
                data = new LoaiPhong(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
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
     */
    public Boolean insertRoomType(LoaiPhong roomType) {
        String query = "{CALL USP_insertRoomType( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { roomType.getMaLP(), roomType.getTenLP(), roomType.getSucChua(),
                roomType.getGiaTien() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
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
     */
    public Boolean updateInfoRoomType(LoaiPhong roomType) {
        String query = "{CALL USP_updateInfoRoomType( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { roomType.getMaLP(), roomType.getTenLP(), roomType.getSucChua(),
                roomType.getGiaTien() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách loại phòng có giá phòng phù hợp với giá được nhập
     * 
     * @param price {@code String}: Giá phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng phù hợp điều kiện
     */
    public ArrayList<LoaiPhong> getRoomTypeListByPrice(String price) {
        String query = "{CALL USP_getRoomTypeListByPrice( ? )}";
        Object[] parameter = new Object[] { price };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<LoaiPhong> staffList = new ArrayList<LoaiPhong>();
        try {
            while (rs.next()) {
                staffList.add(new LoaiPhong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
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
     */
    public LoaiPhong getRoomTypeByName(String roomTypeName) {
        LoaiPhong data = null;
        String query = "{CALL USP_getRoomTypeByName( ? )}";
        Object[] parameter = new Object[] { roomTypeName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next())
                data = new LoaiPhong(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
