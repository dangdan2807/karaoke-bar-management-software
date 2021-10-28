package DAO;

import java.sql.*;
import java.util.*;

import entity.Phong;

public class PhongDAO {
    private static PhongDAO instance = new PhongDAO();
    public static int ROOM_WIDTH = 90;
    public static int ROOM_HEIGHT = 90;
    public static int CON_TRONG = 0;
    public static int PAID = 1;

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
    public ArrayList<Phong> getDSPhong() {
        String query = "{CALL USP_getDSPhong}";
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
     * @param maPhong {@code String}: mã phòng
     * @return {@code Phong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code Phong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public Phong getPhongByMaPhong(String maPhong) {
        String query = "{CALL USP_getPhongByMaPhong( ? )}";
        Object[] parameter = new Object[] { maPhong };
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
     * @param tenLP {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng
     */
    public ArrayList<Phong> getDSPhongByTenLoaiPhong(String tenLP) {
        String query = "{CALL USP_getDSPhongByTenLoaiPhong( ? )}";
        Object[] parameter = new Object[] { tenLP };
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
    public ArrayList<Phong> getDSPhongTrong() {
        String query = "{CALL USP_getDSPhongTrong}";
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
     * @param tenLoaiPhong {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     */
    public ArrayList<Phong> getDSPhongTrongTheoLoaiPhong(String tenLoaiPhong) {
        String query = "{CALL USP_getDSPhongTrongTheoTenLoaiPhong( ? )}";
        Object[] parameter = new Object[] { tenLoaiPhong };
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
     * @param maPhong   {@code String}: mã phòng
     * @param tinhTrang {@code int}: thông tin trạng thái phòng
     *                  <ul>
     *                  <li>Nếu phòng còn trống thì truyền vào {@code 0}</li>
     *                  <li>Nếu phòng đã cho thuê thì truyền vào {@code 1}</li>
     *                  </ul>
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean capNhatTinhTrangPhong(String maPhong, int tinhTrang) {
        String query = "{CALL USP_updateTinhTrangPhong( ? , ? )}";
        Object[] parameter = new Object[] { tinhTrang, maPhong };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Chuyển đổi phòng
     * 
     * @param maPhongCu  {@code String}: mã phòng cũ
     * @param maPhongMoi {@code String}: mã phòng mới
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean chuyenPhong(String maPhongCu, String maPhongMoi) {
        String query = "{CALL USP_chuyenPhong( ? , ? )}";
        Object[] parameter = new Object[] { maPhongCu, maPhongMoi };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}
