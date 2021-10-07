package DAO;

import java.sql.*;
import entity.KhachHang;

public class KhachHangDAO {
    private static KhachHangDAO instance = new KhachHangDAO();

    public static KhachHangDAO getInstance() {
        if (instance == null)
            instance = new KhachHangDAO();
        return instance;
    }

    /**
     * chưa xong
     * @param maKH
     * @return
     */
    public KhachHang getDSKhachHangByMaKH(String maKH) {
        KhachHang data = null;
        String query = "SELECT * FROM dbo.KhachHang kh WHERE kh.maKH == ?";
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
