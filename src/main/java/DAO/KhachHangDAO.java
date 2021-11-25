package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.KhachHang;

public interface KhachHangDAO extends Remote {
    /**
     * Lấy ra danh sách khách hàng theo từng trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>} : danh sách khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<KhachHang> getCustomerListAndPageNumber(int currentPage, int lineNumberDisplayed)
            throws RemoteException;

    /**
     * Lấy số lượng khách hàng
     * 
     * @return {@code int}: số lượng khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    public int getTotalLineOfCustomerList() throws RemoteException;

    /**
     * Lấy danh sách tất cả khách hàng dựa theo tên khách hàng và số trang được chỉ
     * định
     * 
     * @param customerName        {@code String}: tên khách hàng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<KhachHang> getCustomerListByNameAndPageNumber(String customerName, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng khách hàng dựa theo tên khách hàng
     * 
     * @param customerName {@code String}: tên khách hàng
     * @return {@code int}: số lượng khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    public int getTotalLineOfCustomerListByName(String customerName) throws RemoteException;

    /**
     * Lấy danh sách tất cả khách hàng dựa theo số điện thoại và số trang được chỉ
     * định
     * 
     * @param phoneNumber         {@code String}: số điện thoại của khách hàng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<KhachHang> getCustomerListByPhoneNumberAndPageNumber(String phoneNumber, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng khách hàng dựa theo số điện thoại
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @return {@code int}: số lượng khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    public int getTotalLineOfCustomerListByPhoneNumber(String phoneNumber) throws RemoteException;

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng
     * 
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<KhachHang> getCustomerListUnBooked() throws RemoteException;

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo tên
     * 
     * @param customerName {@code String}: tên khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<KhachHang> getCustomerListUnBookedByName(String customerName) throws RemoteException;

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo CMND/CCCD
     * 
     * @param cmnd {@code String}: CMND/CCCD của khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<KhachHang> getCustomerListUnBookedByCMND(String cmnd) throws RemoteException;

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng theo CMND/CCCD
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<KhachHang> getCustomerListUnBookedByPhoneNumber(String phoneNumber) throws RemoteException;

    /**
     * Lấy thông tin khách hàng theo mã khách hàng
     * 
     * @param customerId {@code String}: mã khách hàng
     * @return {@code KhachHang}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code KhachHang}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    public KhachHang getCustomerById(String customerId) throws RemoteException;

    /**
     * Thêm khách hàng mới
     * 
     * @param customer {@code KhachHang}: khách hàng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    public boolean insertCustomer(KhachHang customer) throws RemoteException;

    /**
     * Lấy thông tin khách hàng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã khách hàng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    public String getLastCustomerId() throws RemoteException;

    /**
     * Cập nhật thông tin khách hàng
     * 
     * @param khachHang {@code khachHang}: khách hàng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu cập nhật thành công thì trả về {@code true}</li>
     *         <li>Nếu cập nhật thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    public boolean updateCustomerInfo(KhachHang khachHang) throws RemoteException;

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
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<KhachHang> getCustomerListByGenderAndPageNumber(boolean gender, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng khách hàng dựa theo giới tính
     * 
     * @param gender {@code boolean}: giới tính khách hàng
     *               <ul>
     *               <li>{@code true} thì là Nữ</li>
     *               <li>{@code false} thì là Nam</li>
     *               </ul>
     * @return {@code int}: số lượng khách hàng
     * @throws RemoteException Bắt lỗi Remote
     */
    public int getTotalLineOfCustomerListByGender(boolean gender) throws RemoteException;

    /**
     * Lấy thông tin khách hàng theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn
     * @return {@code KhachHang}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code KhachHang}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    public KhachHang getCustomerByBillId(String billId) throws RemoteException;
}
