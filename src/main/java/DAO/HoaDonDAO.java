package DAO;

import java.sql.*;

import entity.HoaDon;

public class HoaDonDAO {
    private static HoaDonDAO instance = new HoaDonDAO();
    public static int UNPAID = 0;
    public static int DA_THANH_TOAN = 1;

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
     *         <li>Nếu không tìm thấy hóa đơn thì trả về {@code HoaDon có mã hóa
     *         đơn = -1}</li>
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
        return data != null ? data : new HoaDon(-1);
    }

    /**
     * Lấy hóa đơn dựa trên mã hóa đơn
     * 
     * @param billId {@code int}: mã hóa đơn
     * @return {@code HoaDon}: hóa đơn
     *         <ul>
     *         <li>Nếu tìm thấy hóa đơn thì trả về {@code HoaDon}</li>
     *         <li>Nếu không tìm thấy hóa đơn thì trả về {@code HoaDon có mã hóa
     *         đơn = -1}</li>
     *         </ul>
     */
    public HoaDon getBillByBillId(int billId) {
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
        return new HoaDon(-1);
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
    public int getLastBillId() {
        String query = "{CALL USP_getLastBillId}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        int billID = obj != null ? billID = (int) obj : -1;
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
        String query = "{CALL USP_insertBill( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { bill.getNgayGioDat(), bill.getNhanVien().getMaNhanVien(),
                bill.getKhachHang().getMaKH(), bill.getPhong().getMaPhong() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }

    /**
     * Thanh toán hóa đơn
     * 
     * @param billId   {@code int}: mã hóa đơn
     * @param orderDate {@code Timestamp}: ngày giờ thanh toán
     * @param totalPrice   {@code int}: tổng tiền thanh toán
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean payment(int billId, Timestamp orderDate, Double totalPrice) {
        String query = "{CALL USP_payment( ? , ? , ? )}";
        Object[] param = new Object[] { billId, orderDate, totalPrice };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, param);
        return result > 0;
    }
}
