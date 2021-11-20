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
     * Lấy ra danh sách khách hàng theo số trang
     * 
     * @param currentPage {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>} : danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListAndPageNumber(int currentPage, int lineNumberDisplayed) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListAndPageNumber( ? , ? )}";
        Object[] parameter = new Object[] { currentPage, lineNumberDisplayed };
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
     * Lấy số lượng nhân viên
     * 
     * @return {@code int}: số lượng nhân viên
     */
    public int getTotalLineOfCustomerList() {
        String query = "{CALL USP_getTotalLineOfCustomerList()}";
        Object[] parameter = new Object[] {};
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách tất cả khách hàng dựa theo tên khách hàng và số trang được chỉ
     * định
     * 
     * @param customerName {@code String}: tên khách hàng
     * @param currentPage  {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByNameAndPageNumber(String customerName, int currentPage, int lineNumberDisplayed) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByNameAndPageNumber( ? , ? , ? )}";
        Object[] parameter = new Object[] { customerName, currentPage, lineNumberDisplayed };
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
     * Lấy số lượng khách hàng dựa theo tên khách hàng
     * 
     * @param customerName {@code String}: tên khách hàng
     * @return {@code int}: số lượng nhân viên
     */
    public int getTotalLineOfCustomerListByName(String customerName) {
        String query = "{CALL USP_getTotalLineOfCustomerListByName( ? )}";
        Object[] parameter = new Object[] { customerName };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách tất cả khách hàng dựa theo số điện thoại và số trang được chỉ
     * định
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @param currentPage {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByPhoneNumberAndPageNumber(String phoneNumber, int currentPage, int lineNumberDisplayed) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByPhoneNumberAndPageNumber( ? , ? , ? )}";
        Object[] parameter = new Object[] { phoneNumber, currentPage, lineNumberDisplayed };
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
     * Lấy số lượng khách hàng dựa theo số điện thoại
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @return {@code int}: số lượng nhân viên
     */
    public int getTotalLineOfCustomerListByPhoneNumber(String phoneNumber) {
        String query = "{CALL USP_getTotalLineOfCustomerListByPhoneNumber( ? )}";
        Object[] parameter = new Object[] { phoneNumber };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng
     * 
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     */
    public ArrayList<KhachHang> getCustomerListUnBooked() {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBooked()}";
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
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo tên
     * 
     * @param customerName {@code String}: tên khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     */
    public ArrayList<KhachHang> getCustomerListUnBookedByName(String customerName) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBookedByName( ? )}";
        Object[] parameter = new Object[] { customerName };
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
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo CMND/CCCD
     * 
     * @param cmnd {@code String}: CMND/CCCD của khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     */
    public ArrayList<KhachHang> getCustomerListUnBookedByCMND(String cmnd) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBookedByCMND( ? )}";
        Object[] parameter = new Object[] { cmnd };
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
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo CMND/CCCD
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     */
    public ArrayList<KhachHang> getCustomerListUnBookedByPhoneNumber(String phoneNumber) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBookedByPhoneNumber( ? )}";
        Object[] parameter = new Object[] { phoneNumber };
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
     * Lấy thông tin khách hàng theo mã khách hàng
     * 
     * @param customerId {@code String}: mã khách hàng
     * @return {@code KhachHang}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code KhachHang}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public KhachHang getCustomerById(String customerId) {
        KhachHang data = null;
        String query = "{CALL USP_getCustomerById( ? )}";
        Object[] parameter = new Object[] { customerId };
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

    /**
     * Thêm khách hàng mới
     * 
     * @param customer {@code KhachHang}: khách hàng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean insertCustomer(KhachHang customer) {
        String query = "{CALL USP_insertCustomer( ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { customer.getMaKH(), customer.getCmnd(), customer.getHoTen(),
                customer.getGioiTinh(), customer.getSoDienThoai(), customer.getNgaySinh() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy thông tin khách hàng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã khách hàng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getLastCustomerId() {
        String query = "{CALL USP_getLastCustomerId()}";
        Object[] parameter = new Object[] {};
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String result = obj != null ? result = obj.toString() : "";
        return result;
    }

    /**
     * Cập nhật thông tin khách hàng
     * 
     * @param khachHang {@code khachHang}: khách hàng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu cập nhật thành công thì trả về {@code true}</li>
     *         <li>Nếu cập nhật thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean updateCustomerInfo(KhachHang khachHang) {
        String query = "{CALL USP_updateCustomerInfo( ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { khachHang.getMaKH(), khachHang.getCmnd(), khachHang.getHoTen(),
                khachHang.getGioiTinh(), khachHang.getSoDienThoai(), khachHang.getNgaySinh(), };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách khách hàng theo giới tính
     * 
     * @param gender      {@code boolean}: giới tính khách hàng
     *                    <ul>
     *                    <li>{@code true} thì là Nữ</li>
     *                    <li>{@code false} thì là Nam</li>
     *                    </ul>
     * @param currentPage {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByGenderAndPageNumber(boolean gender, int currentPage, int lineNumberDisplayed) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByGenderAndPageNumber( ? , ? , ? )}";
        Object[] parameter = new Object[] { gender, currentPage, lineNumberDisplayed };
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
     * Lấy số lượng khách hàng dựa theo giới tính
     * 
     * @param gender {@code boolean}: giới tính khách hàng
     *               <ul>
     *               <li>{@code true} thì là Nữ</li>
     *               <li>{@code false} thì là Nam</li>
     *               </ul>
     * @return {@code int}: số lượng nhân viên
     */
    public int getTotalLineOfCustomerListByGender(boolean gender) {
        String query = "{CALL USP_getTotalLineOfCustomerListByGender( ? )}";
        Object[] parameter = new Object[] { gender };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy thông tin khách hàng theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn
     * @return {@code KhachHang}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code KhachHang}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public KhachHang getCustomerByBillId(String billId) {
        KhachHang data = null;
        String query = "{CALL USP_getCustomerByBillId( ? )}";
        Object[] parameter = new Object[] { billId };
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
}
