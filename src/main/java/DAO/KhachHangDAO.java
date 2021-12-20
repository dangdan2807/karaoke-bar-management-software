package DAO;

import java.sql.*;
import java.util.ArrayList;

import DAO.KhachHangDAO;
import entity.KhachHang;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code KhachHang}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Võ Minh Hiếu, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 11/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class KhachHangDAO {
    public static int TABLE_WIDTH = 225;
    public static int TABLE_HEIGHT = 80;
    private static KhachHangDAO instance = new KhachHangDAO();

    public static KhachHangDAO getInstance() {
        if (instance == null)
            instance = new KhachHangDAO();
        return instance;
    }

    /**
     * Lấy ra danh sách khách hàng theo từng trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>} : danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListAndPageNumber(int currentPage, int lineNumberDisplayed) {
        String query = "{CALL USP_getCustomerListAndPageNumber( ? , ? )}";
        ArrayList<KhachHang> dataList = new ArrayList<>();
        Object[] params = new Object[] { currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng khách hàng
     * 
     * @return {@code int}: số lượng khách hàng
     */
    public int getTotalLineOfCustomerList() {
        String query = "{CALL USP_getTotalLineOfCustomerList()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách tất cả khách hàng dựa theo tên khách hàng và số trang được chỉ
     * định
     * 
     * @param customerName        {@code String}: tên khách hàng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByNameAndPageNumber(String customerName, int currentPage,
            int lineNumberDisplayed) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByNameAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { customerName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng khách hàng dựa theo tên khách hàng
     * 
     * @param customerName {@code String}: tên khách hàng
     * @return {@code int}: số lượng khách hàng
     */
    public int getTotalLineOfCustomerListByName(String customerName) {
        String query = "{CALL USP_getTotalLineOfCustomerListByName( ? )}";
        Object[] params = new Object[] { customerName };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách tất cả khách hàng dựa theo số điện thoại và số trang được chỉ
     * định
     * 
     * @param phoneNumber         {@code String}: số điện thoại của khách hàng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByPhoneNumberAndPageNumber(String phoneNumber, int currentPage,
            int lineNumberDisplayed) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByPhoneNumberAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { phoneNumber, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng khách hàng dựa theo số điện thoại
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @return {@code int}: số lượng khách hàng
     */
    public int getTotalLineOfCustomerListByPhoneNumber(String phoneNumber) {
        String query = "{CALL USP_getTotalLineOfCustomerListByPhoneNumber( ? )}";
        Object[] params = new Object[] { phoneNumber };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
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
        ResultSet rs = DataProvider.getInstance().executeQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
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
        Object[] params = new Object[] { customerName };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
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
        Object[] params = new Object[] { cmnd };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
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
        Object[] params = new Object[] { phoneNumber };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
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
        KhachHang result = null;
        String query = "{CALL USP_getCustomerById( ? )}";
        Object[] params = new Object[] { customerId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                result = new KhachHang(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
        Object[] params = new Object[] { customer.getMaKH(), customer.getCmnd(), customer.getHoTen(),
                customer.getGioiTinh(), customer.getSoDienThoai(), customer.getNgaySinh() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
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
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        String result = obj != null ? result = obj.toString() : "";
        return result;
    }

    /**
     * Cập nhật thông tin khách hàng
     * 
     * @param customer {@code khachHang}: khách hàng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu cập nhật thành công thì trả về {@code true}</li>
     *         <li>Nếu cập nhật thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean updateCustomerInfo(KhachHang customer) {
        String query = "{CALL USP_updateCustomerInfo( ? , ? , ? , ? , ? , ? )}";
        Object[] params = new Object[] { customer.getMaKH(), customer.getCmnd(), customer.getHoTen(),
                customer.getGioiTinh(), customer.getSoDienThoai(), customer.getNgaySinh() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách khách hàng theo giới tính
     * 
     * @param gender              {@code boolean}: giới tính khách hàng
     *                            <ul>
     *                            <li>{@code true} thì là Nữ</li>
     *                            <li>{@code false} thì là Nam</li>
     *                            </ul>
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByGenderAndPageNumber(boolean gender, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getCustomerListByGenderAndPageNumber( ? , ? , ? )}";
        ArrayList<KhachHang> dataList = new ArrayList<>();
        Object[] params = new Object[] { gender, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
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
     * @return {@code int}: số lượng khách hàng
     */
    public int getTotalLineOfCustomerListByGender(boolean gender) {
        String query = "{CALL USP_getTotalLineOfCustomerListByGender( ? )}";
        Object[] params = new Object[] { gender };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? result = (int) obj : 0;
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
        KhachHang result = null;
        String query = "{CALL USP_getCustomerByBillId( ? )}";
        Object[] params = new Object[] { billId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                result = new KhachHang(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách tất cả khách hàng dựa theo mã khách hàng và số trang được chỉ
     * định
     * 
     * @param customerId        {@code String}: mã khách hàng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByIdAndPageNumber(String customerId, int currentPage,
            int lineNumberDisplayed) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByIdAndPageNumber( ? , ? , ? )}";
        Object[] params = new Object[] { customerId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng khách hàng dựa theo mã khách hàng
     * 
     * @param customerId {@code String}: mã khách hàng
     * @return {@code int}: số lượng khách hàng
     */
    public int getTotalLineOfCustomerListById(String customerId) {
        String query = "{CALL USP_getTotalLineOfCustomerListById( ? )}";
        Object[] params = new Object[] { customerId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }
}
