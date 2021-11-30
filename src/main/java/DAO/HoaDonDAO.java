package DAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

import entity.HoaDon;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code HoaDon}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 09/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class HoaDonDAO {
    private static HoaDonDAO instance = new HoaDonDAO();
    public static int UNPAID = 0;
    public static int PAID = 1;

    public static HoaDonDAO getInstance() {
        if (instance == null)
            instance = new HoaDonDAO();
        return instance;
    }

    /**
     * Lấy hóa đơn chưa tính tiền dựa trên mã phòng
     * 
     * @param roomId {@code String}: mã phòng
     * @return {@code HoaDon}: hóa đơn
     *         <ul>
     *         <li>Nếu tìm thấy hóa đơn thì trả về {@code HoaDon}</li>
     *         <li>Nếu không tìm thấy hóa đơn thì trả về {@code null}</li>
     *         </ul>
     */
    public HoaDon getUncheckBillByRoomId(String roomId) {
        String query = "{CALL USP_getUncheckBillByRoomId( ? )}";
        Object[] parameter = new Object[] { roomId };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        HoaDon data = null;
        try {
            if (rs.next()) {
                data = new HoaDon(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Lấy hóa đơn dựa trên mã hóa đơn
     * 
     * @param billId {@code int}: mã hóa đơn
     * @return {@code HoaDon}: hóa đơn
     *         <ul>
     *         <li>Nếu tìm thấy hóa đơn thì trả về {@code HoaDon}</li>
     *         <li>Nếu không tìm thấy hóa đơn thì trả về {@code null}</li>
     *         </ul>
     */
    public HoaDon getBillByBillId(String billId) {
        String query = "{CALL USP_getBillByBillId( ? )}";
        Object[] parameter = new Object[] { billId };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        HoaDon data = null;
        try {
            if (rs.next()) {
                data = new HoaDon(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (data != null) {
            return data;
        }
        return data;
    }

    /**
     * Lấy mã hóa đơn mới nhất
     * 
     * @return {@code int}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã hóa đơn}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code -1}</li>
     *         </ul>
     */
    public String getLastBillId() {
        String query = "{CALL USP_getLastBillId}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String billID = obj != null ? billID = obj.toString() : null;
        return billID;
    }

    /**
     * Thêm hóa đơn mới
     * 
     * @param bill {@code HoaDon}: hóa đơn cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean insertBill(HoaDon bill) {
        String query = "{CALL USP_insertBill( ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { bill.getMaHoaDon(), bill.getNgayGioDat(),
                bill.getNhanVien().getMaNhanVien(), bill.getKhachHang().getMaKH(), bill.getPhong().getMaPhong() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }

    /**
     * Thanh toán hóa đơn
     * 
     * @param billId     {@code String}: mã hóa đơn
     * @param orderDate  {@code Timestamp}: ngày giờ thanh toán
     * @param totalPrice {@code Double}: tổng tiền thanh toán
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean payment(String billId, Timestamp orderDate) {
        String query = "{CALL USP_payment( ? , ? )}";
        Object[] parameter = new Object[] { billId, orderDate };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }

    /**
     * Lấy tổng tiền của hóa đơn
     * 
     * @param billId {@code int}: mã hóa đơn
     * @return {@code Double}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tổng tiền}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code -1}</li>
     *         </ul>
     */
    public Double getTotalPriceBill(String billId) {
        String query = "{CALL USP_getTotalPriceBill( ? )}";
        Object[] parameter = new Object[] { billId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        BigDecimal totalPrice = obj != null ? totalPrice = (BigDecimal) obj : new BigDecimal(-1);
        return totalPrice.doubleValue();
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn
     * 
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndPageNumber(Date fromDate, Date toDate, String staffId, int currentPage,
            int lineNumberDisplayed) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndPageNumber( ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoản ngày được chọn và số trang
     * 
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code int}: tổng số hóa đơn
     */
    public int getTotalLineOfBillList(Date fromDate, Date toDate, String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDate( ? , ? , ? )}";
        Object[] parameter = new Object[] { fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và số điện thoại của khách
     * hàng
     * 
     * @param phoneNumber         {@code String}: Số điện thoại
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndCustomerPhoneNumberAndPageNumber(String phoneNumber, Date fromDate,
            Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndCustomerPhoneNumberAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { phoneNumber, fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, số điện thoại khách hàng và số trang
     * 
     * @param phoneNumber {@code String}: Số điện thoại
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    public int getTotalLineOfBillListByDateAndCustomerPhoneNumber(String phoneNumber, Date fromDate, Date toDate,
            String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndCustomerPhoneNumber( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { phoneNumber, fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của khách hàng
     * 
     * @param customerName        {@code String}: Tên khách hàng
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndCustomerNameAndPageNumber(String customerName, Date fromDate,
            Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndCustomerNameAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { customerName, fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, số điện thoại khách hàng
     * 
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    public int getTotalLineOfBillListByDateAndCustomerName(String customerName, Date fromDate, Date toDate,
            String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndCustomerName( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { customerName, fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của nhân viên tạo hóa
     * đơn
     * 
     * @param staffName {@code String}: Tên nhân viên
     * @param fromDate  {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate    {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId   {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndStaffNameAndPageNumber(String staffName, Date fromDate, Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndStaffNameAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { staffName, fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, tên nhân viên
     * 
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    public int getTotalLineOfBillListByDateAndStaffName(String staffName, Date fromDate, Date toDate,
            String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndStaffName( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { staffName, fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và mã hóa đơn đơn
     * 
     * @param billId   {@code String}: Mã hóa đơn
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndBillIdAndPageNumber(String billId, Date fromDate, Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndBillIdAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { billId, fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, mã hóa đơn
     * 
     * @param billId   {@code String}: Mã hóa đơn
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    public int getTotalLineOfBillListByDateAndBillId(String BillId, Date fromDate, Date toDate,
            String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndBillId( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { BillId, fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? (int) obj : 0;
        return result;
    }
}
