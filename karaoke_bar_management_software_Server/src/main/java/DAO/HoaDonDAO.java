package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import entity.HoaDon;

/**
 * Lớp interface cho lớp {@code HoaDonDAOImpl}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 09/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public interface HoaDonDAO extends Remote {
    public static int UNPAID = 0;
    public static int PAID = 1;

    /**
     * Lấy hóa đơn chưa tính tiền dựa trên mã phòng
     * 
     * @param roomId {@code String}: mã phòng
     * @return {@code HoaDon}: hóa đơn
     *         <ul>
     *         <li>Nếu tìm thấy hóa đơn thì trả về {@code HoaDon}</li>
     *         <li>Nếu không tìm thấy hóa đơn thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public HoaDon getUncheckBillByRoomId(String roomId) throws RemoteException;

    /**
     * Lấy hóa đơn dựa trên mã hóa đơn
     * 
     * @param billId {@code int}: mã hóa đơn
     * @return {@code HoaDon}: hóa đơn
     *         <ul>
     *         <li>Nếu tìm thấy hóa đơn thì trả về {@code HoaDon}</li>
     *         <li>Nếu không tìm thấy hóa đơn thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public HoaDon getBillByBillId(String billId) throws RemoteException;

    /**
     * Lấy mã hóa đơn mới nhất
     * 
     * @return {@code int}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã hóa đơn}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code -1}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public String getLastBillId() throws RemoteException;

    /**
     * Thêm hóa đơn mới
     * 
     * @param bill {@code HoaDon}: hóa đơn cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public boolean insertBill(HoaDon bill) throws RemoteException;

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
     * @throws RemoteException - Bắt lỗi Remote
     */
    public boolean payment(String billId, Timestamp orderDate) throws RemoteException;

    /**
     * Lấy tổng tiền của hóa đơn
     * 
     * @param billId {@code int}: mã hóa đơn
     * @return {@code Double}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tổng tiền}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code -1}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Double getTotalPriceBill(String billId) throws RemoteException;

    /**
     * Lấy danh sách hóa đơn trong khoảng ngày được chọn và tìm theo mã nhân viên
     * 
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<HoaDon> getBillListByDate(Date fromDate, Date toDate, String staffId) throws RemoteException;

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và số điện thoại của khách
     * hàng
     * 
     * @param phoneNumber {@code String}: Số điện thoại
     * @param fromDate    {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate      {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId     {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<HoaDon> getBillListByDateAndCustomerPhoneNumber(String phoneNumber, Date fromDate, Date toDate,
            String staffId) throws RemoteException;

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của khách hàng
     * 
     * @param customerName {@code String}: Tên khách hàng
     * @param fromDate     {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate       {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId      {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<HoaDon> getBillListByDateAndCustomerName(String customerName, Date fromDate, Date toDate,
            String staffId) throws RemoteException;

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của nhân viên tạo hóa
     * đơn
     * 
     * @param staffName {@code String}: Tên nhân viên
     * @param fromDate  {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate    {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId   {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<HoaDon> getBillListByDateAndStaffName(String staffName, Date fromDate, Date toDate,
            String staffId) throws RemoteException;

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và mã hóa đơn đơn
     * 
     * @param billId   {@code String}: Mã hóa đơn
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<HoaDon> getBillListByDateAndBillId(String billId, Date fromDate, Date toDate, String staffId)
            throws RemoteException;
}
