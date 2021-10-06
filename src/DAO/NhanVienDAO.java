package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.NhanVien;

public class NhanVienDAO {
    private static NhanVienDAO instance = new NhanVienDAO();

    public static NhanVienDAO getInstance() {
        if (instance == null)
            instance = new NhanVienDAO();
        return instance;
    }

    public NhanVien getNhanVienByTenDangNhap(String username) {
        String query = "Select * from dbo.TaiKhoan tk join dbo.NhanVien nv on tk.tenDangNhap = nv.taiKhoan WHERE tk.tenDangNhap = ?";
        Object[] parameter = new Object[] { username };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        NhanVien nhanVien = null;
        try {
            rs.next();
            nhanVien = new NhanVien(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVien;
    }

    public NhanVien getNhanVienByMaNV(String maNhanVien) {
        String query = "Select * from dbo.TaiKhoan tk join dbo.NhanVien nv on tk.tenDangNhap = nv.taiKhoan WHERE nv.maNhanVien = ?";
        Object[] parameter = new Object[] { maNhanVien };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        NhanVien nhanVien = null;
        try {
            rs.next();
            nhanVien = new NhanVien(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVien;
    }
}
