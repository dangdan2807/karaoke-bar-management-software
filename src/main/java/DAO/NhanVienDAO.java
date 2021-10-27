package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.NhanVien;

public class NhanVienDAO {
    private static NhanVienDAO instance = new NhanVienDAO();

    public static NhanVienDAO getInstance() {
        if (instance == null)
            instance = new NhanVienDAO();
        return instance;
    }

    public ArrayList<NhanVien> getStaffList() {
        String query = "{CALL USP_getStaffList}";
        Object[] parameter = new Object[] {};
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<NhanVien> staffList = new ArrayList<NhanVien>();
        try {
            while (rs.next()) {
                staffList.add(new NhanVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public NhanVien getStaffByUsername(String username) {
        String query = "{CALL USP_getNhanVienByTenDangNhap( ? )}";
        Object[] parameter = new Object[] { username };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        NhanVien staff = null;
        try {
            if (rs.next()) {
                staff = new NhanVien(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public NhanVien getStaffByStaffID(String maNhanVien) {
        String query = "{CALL USP_getStaffByStaffID( ? )}";
        Object[] parameter = new Object[] { maNhanVien };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        NhanVien staff = null;
        try {
            if (rs.next()) {
                staff = new NhanVien(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public boolean updateTTNhanVien(NhanVien nhanVien) {
        String query = "{CALL UPS_updateAccount ( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] {};
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    public String getLastStaffID() {
        String query = "{CALL USP_getLastStaffID}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String staffID = obj != null ? obj.toString() : "";
        return staffID;
    }

    public boolean insertStaff(NhanVien staff) {
        String query = "{CALL USP_insertStaff( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { staff.getMaNhanVien(), staff.getCmnd(), staff.getHoTen(),
                staff.getNgaySinh(), staff.getSoDienThoai(), staff.getChucVu(), staff.getMucLuong(),
                staff.getTrangThaiNV(), staff.getGioiTinh(), staff.getTaiKhoan().getTenDangNhap() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    public Boolean updateInfoStaff(NhanVien staff) {
        String query = "{CALL USP_updateInfoStaff( ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { staff.getMaNhanVien(), staff.getCmnd(), staff.getHoTen(),
                staff.getNgaySinh(), staff.getSoDienThoai(), staff.getChucVu(), staff.getMucLuong(),
                staff.getTrangThaiNV(), staff.getGioiTinh() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    public ArrayList<NhanVien> getStaffListByPosition(String position) {
        String query = "{CALL USP_getStaffListByPosition( ? )}";
        Object[] parameter = new Object[] { position };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<NhanVien> staffList = new ArrayList<NhanVien>();
        try {
            while (rs.next()) {
                staffList.add(new NhanVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public ArrayList<NhanVien> getStaffListByStaffName(String staffName) {
        String query = "{CALL USP_getStaffListByStaffName( ? )}";
        Object[] parameter = new Object[] { staffName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<NhanVien> staffList = new ArrayList<NhanVien>();
        try {
            while (rs.next()) {
                staffList.add(new NhanVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public ArrayList<NhanVien> getStaffListByPhoneNumber(String phoneNumber) {
        String query = "{CALL USP_getStaffListByPhoneNumber( ? )}";
        Object[] parameter = new Object[] { phoneNumber };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<NhanVien> staffList = new ArrayList<NhanVien>();
        try {
            while (rs.next()) {
                staffList.add(new NhanVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public String getStaffNameById(String staffId) {
        String query = "{CALL USP_getStaffNameById( ? )}";
        Object[] parameter = new Object[] { staffId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String staffName = obj != null ? obj.toString() : "";
        return staffName;
    }
}
