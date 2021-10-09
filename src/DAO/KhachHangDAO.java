package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.KhachHang;

public class KhachHangDAO {
    private static KhachHangDAO instance = new KhachHangDAO();
    public static int TABLE_WIDTH = 225;
    public static int TABLE_HEIGHT = 80;

    public static KhachHangDAO getInstance() {
        if (instance == null)
            instance = new KhachHangDAO();
        return instance;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng
     * 
     * @param
     * @return
     */
    public ArrayList<KhachHang> getDSKhachHang() {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "SELECT * FROM dbo.KhachHang";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng có mã khách hàng phù hợp
     * 
     * @param maKH
     * @return
     */
    public ArrayList<KhachHang> getDSKhachHangByMaKH(String maKH) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getDSKhachHangByMaKH( ? )}";
        Object[] parameter = new Object[] { maKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * chưa xong
     * 
     * @param tenKH
     * @return
     */
    public ArrayList<KhachHang> getDSKhachHangByTenKH(String tenKH) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getDSKhachHangByTenKH( ? )}";
        Object[] parameter = new Object[] { tenKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * chưa xong
     * 
     * @param sdtKH
     * @return
     */
    public ArrayList<KhachHang> getDSKhachHangBySDT(String sdtKH) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getDSKhachHangBySDT( ? )}";
        Object[] parameter = new Object[] { sdtKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public KhachHang getKhachHangByMaKH(String maKH) {
        KhachHang data = null;
        String query = "SELECT * FROM dbo.KhachHang kh WHERE kh.maKH = ?";
        Object[] parameter = new Object[] { maKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            rs.next();
            data = new KhachHang(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public boolean insertKhachHang(KhachHang khachHang) {
        String query = "INSERT INTO dbo.KhachHang (maKH, cmnd, hoTen, gioiTinh, soDienThoai, ngaySinh) VALUES ( ? , ? , ? , ? , ? , ? )";
        Object[] parameter = new Object[] { khachHang.getMaKH(), khachHang.getCmnd(), khachHang.getHoTen(),
                khachHang.getGioiTinh(), khachHang.getSoDienThoai(), khachHang.getNgaySinh() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }

    public String getMaKHCuoiCung() {
        String query = "SELECT TOP 1 * FROM dbo.KhachHang ORDER BY maKH DESC";
        Object[] parameter = new Object[] {};
        String maKH = (String) DataProvider.getInstance().ExecuteScalar(query, parameter);
        return maKH;
    }

    public boolean updateKhachHang(KhachHang khachHang) {
        String query = "Update dbo.KhachHang set cmnd = ? , hoTen = ? , gioiTinh = ? , soDienThoai = ? , ngaySinh = ? , Where maKH = ?";
        Object[] parameter = new Object[] { khachHang.getCmnd(), khachHang.getHoTen(), khachHang.getGioiTinh(),
                khachHang.getSoDienThoai(), khachHang.getNgaySinh(), khachHang.getMaKH() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }
}
