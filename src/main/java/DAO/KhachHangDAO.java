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
     * Lấy ra danh sách tất cả khách hàng có mã khách hàng phù hợp điều kiện
     * 
     * @param maKH mã khách hàng
     * @return ArrayList<KhachHang> danh sách khách hàng
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
     * Lấy ra danh sách tất cả khách hàng có tên khách hàng phù hợp điều kiện
     * 
     * @param tenKH tên khách hàng
     * @return ArrayList<KhachHang> danh sách khách hàng
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
     * Lấy ra danh sách tất cả khách hàng có số điện thoại của khách hàng phù hợp
     * điều kiện
     * 
     * @param sdtKH số điện thoại của khách hàng
     * @return ArrayList<KhachHang> danh sách khách hàng
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

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng
     * 
     * @return ArrayList<KhachHang> danh sách khách hàng chưa đặt phòng
     */
    public ArrayList<KhachHang> getDSKhachHangChuaDatPhong() {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getDSKhachHangChuaDatPhong()}";
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

    public KhachHang getKhachHangByMaKH(String maKH) {
        KhachHang data = null;
        String query = "SELECT * FROM dbo.KhachHang kh WHERE kh.maKH = ?";
        Object[] parameter = new Object[] { maKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next()) {
                data = new KhachHang(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public boolean themKhachHang(KhachHang khachHang) {
        String query = "INSERT INTO dbo.KhachHang (maKH, cmnd, hoTen, gioiTinh, soDienThoai, ngaySinh) "
                + "VALUES ( ? , ? , ? , ? , ? , ? )";
        Object[] parameter = new Object[] { khachHang.getMaKH(), khachHang.getCmnd(), khachHang.getHoTen(),
                khachHang.getGioiTinh(), khachHang.getSoDienThoai(), khachHang.getNgaySinh() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    public String getMaKHCuoiCung() {
        String query = "SELECT TOP 1 * FROM dbo.KhachHang ORDER BY maKH DESC";
        Object[] parameter = new Object[] {};
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String result = obj != null ? result = obj.toString() : "";
        return result;
    }

    public boolean capNhatTTKhachHang(KhachHang khachHang) {
        String query = "Update dbo.KhachHang set cmnd = ? , hoTen = ? , gioiTinh = ? , soDienThoai = ? , "
                + "ngaySinh = ? , Where maKH = ?";
        Object[] parameter = new Object[] { khachHang.getCmnd(), khachHang.getHoTen(), khachHang.getGioiTinh(),
                khachHang.getSoDienThoai(), khachHang.getNgaySinh(), khachHang.getMaKH() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}
