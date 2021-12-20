package DAO;

import java.sql.*;
import java.util.ArrayList;

import DAO.HoaDonDAO;
import entity.*;

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
        Object[] params = { roomId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        HoaDon result = null;
        try {
            while (rs.next()) {
                result = new HoaDon(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
        Object[] params = { billId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        HoaDon result = null;
        try {
            while (rs.next()) {
                result = new HoaDon(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
        String query = "{CALL USP_getLastBillId()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        String result = obj != null ? obj.toString() : "";
        return result;
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
        String query = "{CALL USP_insertBill( ? , ? , ? , ? , ? , ? )}";
        Object[] params = { bill.getMaHoaDon(), bill.getNgayGioDat(), bill.getNhanVien().getMaNhanVien(),
                bill.getKhachHang().getMaKH(),
                bill.getPhong().getMaPhong(), bill.getGiaPhong() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Thanh toán hóa đơn
     * 
     * @param billId      {@code String}: mã hóa đơn
     * @param totalPrice  {@code Double}: tổng tiền thanh toán
     * @param paymentDate {@code Timestamp}: ngày giờ thanh toán
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean payment(String billId, double getTotalPriceBill, Timestamp paymentDate) {
        String query = "{CALL USP_payment( ? , ? , ? )}";
        Object[] params = { billId, getTotalPriceBill, paymentDate };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
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
        Object[] params = { billId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        Double result = obj != null ? (Double) obj : 0.0;
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoảng ngày được chọn và tìm theo mã nhân viên và
     * phân trang
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
        String query = "{CALL USP_getBillListByDateAndPageNumber( ? , ? , ? , ? , ? )}";
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        Object[] params = { fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (Exception e) {
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
        Object[] params = { fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
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
        Object[] params = { phoneNumber, fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, số điện thoại khách hàng và
     * số trang
     * 
     * @param phoneNumber {@code String}: Số điện thoại
     * @param fromDate    {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate      {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId     {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    public int getTotalLineOfBillListByDateAndCustomerPhoneNumber(String phoneNumber, Date fromDate, Date toDate,
            String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndCustomerPhoneNumber( ? , ? , ? , ? )}";
        Object[] params = { phoneNumber, fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
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
        Object[] params = { customerName, fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, số điện thoại khách hàng
     * 
     * @param customerName {@code String}: Tên khách hàng
     * @param fromDate     {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate       {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId      {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    public int getTotalLineOfBillListByDateAndCustomerName(String customerName, Date fromDate, Date toDate,
            String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndCustomerName( ? , ? , ? , ? )}";
        Object[] params = { customerName, fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của nhân viên tạo hóa
     * đơn
     * 
     * @param staffName           {@code String}: Tên nhân viên
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndStaffNameAndPageNumber(String staffName, Date fromDate, Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndStaffNameAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        Object[] params = { staffName, fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, tên nhân viên
     * 
     * @param staffName {@code String}: Tên nhân viên
     * @param fromDate  {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate    {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId   {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    public int getTotalLineOfBillListByDateAndStaffName(String staffName, Date fromDate, Date toDate,
            String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndStaffName( ? , ? , ? , ? )}";
        Object[] params = { staffName, fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và mã hóa đơn đơn
     * 
     * @param billId              {@code String}: Mã hóa đơn
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndBillIdAndPageNumber(String billId, Date fromDate, Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndBillIdAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        Object[] params = { billId, fromDate, toDate, staffId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs));
            }
        } catch (Exception e) {
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
    public int getTotalLineOfBillListByDateAndBillId(String billId, Date fromDate, Date toDate,
            String staffId) {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndBillId( ? , ? , ? , ? )}";
        Object[] params = { billId, fromDate, toDate, staffId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách Tổng tiền hóa đơn trong khoảng ngày từ {@code fromDate} đến
     * {@code toDate}
     * 
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param format   {@code String}: định dạng của ngày tháng năm
     * @return {@code ArrayList<Double>}: danh sách tổng tiền của các hóa đơn
     */
    public ArrayList<Object[]> getTotalPriceBillListByDate(Date fromDate, Date toDate, String format) {
        String query = "{CALL USP_getTotalPriceBillListByDate( ? , ? , ? )}";
        Object[] params = { fromDate, toDate, format };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<Object[]> result = new ArrayList<>();
        try {
            while (rs.next()) {
                String dateStr = rs.getString("ngayGioDat");
                Double totalPrice = rs.getDouble("totalPrice");
                result.add(new Object[] { dateStr, totalPrice });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
