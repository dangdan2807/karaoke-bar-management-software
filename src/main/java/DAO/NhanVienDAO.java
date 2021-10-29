package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.NhanVien;
import entity.TaiKhoan;

public class NhanVienDAO {
    private static NhanVienDAO instance = new NhanVienDAO();

    public static NhanVienDAO getInstance() {
        if (instance == null)
            instance = new NhanVienDAO();
        return instance;
    }

    /**
     * Lấy danh sách tất cả khách hàng
     * 
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
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

    /**
     * Lấy thông tin nhân viên theo tên đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @return {@code NhanVien}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code NhanVien}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public NhanVien getStaffByUsername(String username) {
        String query = "{CALL USP_getStaffByUsername( ? )}";
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

    /**
     * Lấy thông tin nhân viên theo mã nhân viên
     * 
     * @param maNhanVien {@code String}: mã nhân viên
     * @return {@code NhanVien}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code NhanVien}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
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

    /**
     * Lấy mã nhân viên mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã nhân viên mới nhất}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getLastStaffID() {
        String query = "{CALL USP_getLastStaffID}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String staffID = obj != null ? obj.toString() : "";
        return staffID;
    }

    /**
     * Thêm mới nhân viên
     * 
     * @param staff {@code NhanVien}: nhân viên cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean insertStaff(NhanVien staff) {
        String query = "{CALL USP_insertStaff( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { staff.getMaNhanVien(), staff.getCmnd(), staff.getHoTen(),
                staff.getNgaySinh(), staff.getSoDienThoai(), staff.getChucVu(), staff.getMucLuong(),
                staff.getTrangThaiNV(), staff.getGioiTinh(), staff.getTaiKhoan().getTenDangNhap() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Cập nhật thông tin nhân viên
     * 
     * @param staff {@code NhanVien}: nhân viên cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean updateInfoStaff(NhanVien staff) {
        String query = "{CALL USP_updateInfoStaff( ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { staff.getMaNhanVien(), staff.getCmnd(), staff.getHoTen(),
                staff.getNgaySinh(), staff.getSoDienThoai(), staff.getChucVu(), staff.getMucLuong(),
                staff.getTrangThaiNV(), staff.getGioiTinh() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Cập nhật thông tin nhân viên và mật khẩu
     * 
     * @param staff {@code NhanVien}: nhân viên cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean updateInfoStaffAndAccount(NhanVien staff) {
        String query = "{CALL USP_updateInfoStaffAndAccount( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        TaiKhoan taiKhoan = staff.getTaiKhoan();
        Object[] parameter = new Object[] { staff.getMaNhanVien(), staff.getCmnd(), staff.getHoTen(),
                staff.getNgaySinh(), staff.getSoDienThoai(), staff.getChucVu(), staff.getMucLuong(),
                staff.getTrangThaiNV(), staff.getGioiTinh(), taiKhoan.getTenDangNhap(), taiKhoan.getMatKhau() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách nhân viên dự theo chức vụ
     * 
     * @param position {@code String}: chức vụ nhân viên cần tìm
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     */
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

    /**
     * Lấy danh sách nhân viên dự theo tên nhân viên
     * 
     * @param staffName {@code String}: tên nhân viên cần tìm
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     */
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

    /**
     * Lấy danh sách nhân viên dự theo số điện thoại
     * 
     * @param phoneNumber {@code String}: số điện thoại cần tìm
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     */
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

    /**
     * Lấy tên nhân viên dựa trên mã nhân viên
     * 
     * @param staffId {@code String}: mã nhân viên cần tìm
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên nhân viên}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getStaffNameById(String staffId) {
        String query = "{CALL USP_getStaffNameById( ? )}";
        Object[] parameter = new Object[] { staffId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String staffName = obj != null ? obj.toString() : "";
        return staffName;
    }
}
