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
        String query = "{CALL getNhanVienByTenDangNhap( ? )}";
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
        String query = "{CALL getNhanVienByMaNV( ? )}";
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

    public boolean updateTTNhanVien(NhanVien nhanVien) {
        String query = "{CALL UPS_updateAccount ( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] {  };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }

}
