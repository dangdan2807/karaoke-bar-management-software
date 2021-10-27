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
     * @return <code>ArrayList - LoaiPhong</code>: danh sách loại phòng
     */
    public ArrayList<LoaiPhong> getDSLoaiPhong() {
        ArrayList<LoaiPhong> dataList = new ArrayList<LoaiPhong>();
        String query = "{CALL USP_getDSLoaiPhong}";
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
     * @param maPhong <code>String</code>: mã loại phòng
     * @return <code>String</code>: tên loại phòng
     */
    public String getTenLPbyMaPhong(String maPhong) {
        String query = "{CALL USP_getTenLPbyMaPhong( ? )}";
        Object[] parameter = new Object[] { maPhong };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String roomTypeName = obj != null ? roomTypeName = obj.toString() : "";
        return roomTypeName;
    }

    /**
     * Lấy danh sách loại phòng có tên phù hợp với từ khóa
     * 
     * @param roomTypeName <code>String</code>: từ khóa trong tên loại phòng
     * @return <code>ArrayList - LoaiPhong</code>: danh sách loại phòng phù hợp điều
     *         kiện
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
     * @return <code>String</code>: mã loại phòng mới nhất được thêm vào
     */
    public String getLastRoomTypeId() {
        String query = "{CALL USP_getLastRoomTypeId}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String staffID = obj != null ? obj.toString() : "";
        return staffID;
    }

    /**
     * Lấy thông tin loại phòng dựa theo mã phòng
     * 
     * @param roomTypeId <code>String</code>: mã loại phòng
     * @return <code>LoaiPhong</code>: loại phòng được tìm thấy
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
     * @param roomType <code>LoaiPhong</code>: loại phòng cần thêm
     * @return <code>boolean</code>: <code>true</code> thành công,
     *         <code>false</code> nếu thất bại
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
     * @param roomType <code>LoaiPhong</code>: loại phòng cần cập nhật
     * @return <code>boolean</code>: <code>true</code> thành công,
     *         <code>false</code> nếu thất bại
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
     * Lấy danh sách loại phòng có tên phù hợp với từ khóa
     * 
     * @param roomTypeName <code>String</code>: từ khóa trong tên loại phòng
     * @return <code>ArrayList - LoaiPhong</code>: danh sách loại phòng phù hợp điều
     *         kiện
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
}
