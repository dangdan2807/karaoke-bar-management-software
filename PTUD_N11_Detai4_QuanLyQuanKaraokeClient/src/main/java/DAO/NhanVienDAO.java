package DAO;

import java.sql.*;
import java.util.ArrayList;

import DAO.NhanVienDAO;
import entity.NhanVien;
import entity.TaiKhoan;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code NhanVien}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 04/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public class NhanVienDAO {
    private static NhanVienDAO instance = new NhanVienDAO();

    public static NhanVienDAO getInstance() {
        if (instance == null)
            instance = new NhanVienDAO();
        return instance;
    }

    /**
     * Lấy danh sách tất cả nhân viên đang làm việc
     * 
     * @param workingStatus       {@code String}: trạng thái làm việc của nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     */
    public ArrayList<NhanVien> getStaffListByWorkingStatusAndPageNumber(String workingStatus, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getStaffListByWorkingStatusAndPageNumber(?, ?, ?)}";
        Object[] params = new Object[] { workingStatus, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<NhanVien> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new NhanVien(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng nhân viên tìm được dựa trên trạng thái nhân viên
     * 
     * @param workingStatus {@code String}: trạng thái làm việc của nhân viên
     * @return {@code int}: số lượng nhân viên
     */
    public int getTotalLineOfStaffListByWorkingStatus(String workingStatus) {
        String query = "{CALL USP_getTotalLineOfStaffListByWorkingStatus( ? )}";
        Object[] params = new Object[] { workingStatus };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
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
        Object[] params = new Object[] { username };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        NhanVien staff = null;
        try {
            while (rs.next()) {
                staff = new NhanVien(rs);
                break;
            }
        } catch (Exception e) {
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
        String query = "{CALL USP_getLastStaffID()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        String result = obj != null ? obj.toString() : "";
        return result;
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
        Object[] params = new Object[] { staff.getMaNhanVien(), staff.getCmnd(), staff.getHoTen(), staff.getNgaySinh(),
                staff.getSoDienThoai(), staff.getChucVu(), staff.getMucLuong(), staff.getTrangThaiNV(),
                staff.getGioiTinh(),
                staff.getTaiKhoan().getTenDangNhap() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Cập nhật thông tin nhân viên
     * 
     * @param staff {@code NhanVien}: nhân viên cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean updateInfoStaff(NhanVien staff) {
        String query = "{CALL USP_updateInfoStaff( ?, ?, ?, ?, ?, ?, ?, ?, ? )}";
        Object[] params = new Object[] { staff.getMaNhanVien(), staff.getCmnd(), staff.getHoTen(), staff.getNgaySinh(),
                staff.getSoDienThoai(), staff.getChucVu(), staff.getMucLuong(), staff.getTrangThaiNV(),
                staff.getGioiTinh() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Cập nhật thông tin nhân viên và mật khẩu
     * 
     * @param staff {@code NhanVien}: nhân viên cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean updateInfoStaffAndAccount(NhanVien staff) {
        String query = "{CALL USP_updateInfoStaffAndAccount( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        TaiKhoan taiKhoan = staff.getTaiKhoan();
        Object[] params = new Object[] { staff.getMaNhanVien(), staff.getCmnd(), staff.getHoTen(), staff.getNgaySinh(),
                staff.getSoDienThoai(), staff.getChucVu(), staff.getMucLuong(), staff.getTrangThaiNV(),
                staff.getGioiTinh(), taiKhoan.getTenDangNhap(), taiKhoan.getMatKhau(), taiKhoan.getTinhTrangTK() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách nhân viên dựa theo chức vụ
     * 
     * @param position            {@code String}: chức vụ nhân viên cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     */
    public ArrayList<NhanVien> getStaffListByPositionAndPageNumber(String position, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getStaffListByPositionAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { position, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<NhanVien> dataList = new ArrayList<NhanVien>();
        try {
            while (rs.next()) {
                dataList.add(new NhanVien(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng nhân viên tìm được dựa theo chức vụ
     * 
     * @param position {@code String}: chức vụ nhân viên cần tìm
     * @return {@code int}: số lượng nhân viên
     */
    public int getTotalLineByPosition(String position) {
        String query = "{CALL USP_getTotalLineOfStaffListByPosition( ? )}";
        Object[] params = new Object[] { position };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách nhân viên dựa theo tên nhân viên
     * 
     * @param staffName           {@code String}: tên nhân viên cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     */
    public ArrayList<NhanVien> getStaffListByStaffNameAndPageNumber(String staffName, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getStaffListByStaffNameAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { staffName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<NhanVien> dataList = new ArrayList<NhanVien>();
        try {
            while (rs.next()) {
                dataList.add(new NhanVien(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng nhân viên tìm được dựa theo tên nhân viên
     * 
     * @param staffName {@code String}: tên nhân viên cần tìm
     * @return {@code int}: số lượng nhân viên
     */
    public int getTotalLineByStaffName(String staffName) {
        String query = "{CALL USP_getTotalLineOfStaffListByStaffName( ? )}";
        Object[] params = new Object[] { staffName };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách nhân viên dựa theo số điện thoại
     * 
     * @param phoneNumber         {@code String}: số điện thoại cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     */
    public ArrayList<NhanVien> getStaffListByPhoneNumberAndPageNumber(String phoneNumber, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getStaffListByPhoneNumberAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { phoneNumber, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<NhanVien> dataList = new ArrayList<NhanVien>();
        try {
            while (rs.next()) {
                dataList.add(new NhanVien(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng nhân viên tìm được dựa theo số điện thoại
     * 
     * @param phoneNumber {@code String}: số điện thoại cần tìm
     * @return {@code int}: số lượng nhân viên
     */
    public int getTotalLineByPhoneNumber(String phoneNumber) {
        String query = "{CALL USP_getTotalLineStaffListByPhoneNumber( ? )}";
        Object[] params = new Object[] { phoneNumber };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
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
        Object[] params = new Object[] { staffId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        String result = obj != null ? obj.toString() : "";
        return result;
    }

    /**
     * Lấy thông tin nhân viên theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn
     * @return {@code NhanVien}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code NhanVien}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public NhanVien getStaffByBillId(String billId) {
        String query = "{CALL USP_getStaffByBillId( ? )}";
        Object[] params = new Object[] { billId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        NhanVien staff = null;
        try {
            while (rs.next()) {
                staff = new NhanVien(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    /**
     * Kiểm tra số điện thoại có thuộc về tài khoản hay không
     * 
     * @param username    {@code String}: tên đăng nhập
     * @param phoneNumber {@code String}: số điện thoại
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean checkPhoneNumberStaff(String username, String phoneNumber) {
        String query = "{CALL USP_checkPhoneNumberStaff( ? , ? )}";
        Object[] params = new Object[] { username, phoneNumber };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }
}
