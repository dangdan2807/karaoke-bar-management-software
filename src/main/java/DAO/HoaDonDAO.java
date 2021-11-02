package DAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

import entity.HoaDon;

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
        Object[] parameter = new Object[] { bill.getMaHoaDon() , bill.getNgayGioDat(), bill.getNhanVien().getMaNhanVien(),
                bill.getKhachHang().getMaKH(), bill.getPhong().getMaPhong() };
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
    public boolean payment(String billId, Timestamp orderDate, Double totalPrice) {
        String query = "{CALL USP_payment( ? , ? , ? )}";
        Object[] parameter = new Object[] { billId, orderDate, totalPrice };
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
     * @param startDate {@code java.sql.Date}: ngày bắt đầu
     * @param endDate   {@code java.sql.Date}: ngày kết thúc
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDate(Date startDate, Date endDate) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDate( ? , ? )}";
        Object[] parameter = new Object[] { startDate, endDate };
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
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và số điện thoại của khách
     * hàng
     * 
     * @param phoneNumber {@code String}: Số điện thoại
     * @param startDate   {@code java.sql.Date}: ngày bắt đầu
     * @param endDate     {@code java.sql.Date}: ngày kết thúc
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndPhoneNumber(String phoneNumber, Date startDate, Date endDate) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndPhoneNumber( ? , ? , ? )}";
        Object[] parameter = new Object[] { phoneNumber, startDate, endDate };
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
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của khách hàng
     * 
     * @param customerName {@code String}: Tên khách hàng
     * @param startDate    {@code java.sql.Date}: ngày bắt đầu
     * @param endDate      {@code java.sql.Date}: ngày kết thúc
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndCustomerName(String customerName, Date startDate, Date endDate) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndCustomerName( ? , ? , ? )}";
        Object[] parameter = new Object[] { customerName, startDate, endDate };
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
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của nhân viên tạo hóa
     * đơn
     * 
     * @param staffName {@code String}: Tên nhân viên
     * @param startDate    {@code java.sql.Date}: ngày bắt đầu
     * @param endDate      {@code java.sql.Date}: ngày kết thúc
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    public ArrayList<HoaDon> getBillListByDateAndStaffName(String staffName, Date startDate, Date endDate) {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndStaffName( ? , ? , ? )}";
        Object[] parameter = new Object[] { staffName, startDate, endDate };
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
}
