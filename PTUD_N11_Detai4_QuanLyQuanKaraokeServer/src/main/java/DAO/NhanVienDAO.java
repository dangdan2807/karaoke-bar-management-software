package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.NhanVien;

/**
 * Lớp interface cửa lớp {@code NhanVienDAOImpl}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 04/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public interface NhanVienDAO extends Remote {
    /**
     * Lấy danh sách tất cả nhân viên đang làm việc
     * 
     * @param workingStatus       {@code String}: trạng thái làm việc của nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    public ArrayList<NhanVien> getStaffListByWorkingStatusAndPageNumber(String workingStatus, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng nhân viên tìm được dựa trên trạng thái nhân viên
     * 
     * @param workingStatus {@code String}: trạng thái làm việc của nhân viên
     * @return {@code int}: số lượng nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    public int getTotalLineOfStaffListByWorkingStatus(String workingStatus) throws RemoteException;

    /**
     * Lấy thông tin nhân viên theo tên đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @return {@code NhanVien}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code NhanVien}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    public NhanVien getStaffByUsername(String username) throws RemoteException;

    /**
     * Lấy mã nhân viên mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã nhân viên mới nhất}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    public String getLastStaffID() throws RemoteException;

    /**
     * Thêm mới nhân viên
     * 
     * @param staff {@code NhanVien}: nhân viên cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    public boolean insertStaff(NhanVien staff) throws RemoteException;

    /**
     * Cập nhật thông tin nhân viên
     * 
     * @param staff {@code NhanVien}: nhân viên cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    public Boolean updateInfoStaff(NhanVien staff) throws RemoteException;

    /**
     * Cập nhật thông tin nhân viên và mật khẩu
     * 
     * @param staff {@code NhanVien}: nhân viên cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    public Boolean updateInfoStaffAndAccount(NhanVien staff) throws RemoteException;

    /**
     * Lấy danh sách nhân viên dựa theo chức vụ
     * 
     * @param position            {@code String}: chức vụ nhân viên cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    public ArrayList<NhanVien> getStaffListByPositionAndPageNumber(String position, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng nhân viên tìm được dựa theo chức vụ
     * 
     * @param position {@code String}: chức vụ nhân viên cần tìm
     * @return {@code int}: số lượng nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    public int getTotalLineByPosition(String position) throws RemoteException;

    /**
     * Lấy danh sách nhân viên dựa theo tên nhân viên
     * 
     * @param staffName           {@code String}: tên nhân viên cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    public ArrayList<NhanVien> getStaffListByStaffNameAndPageNumber(String staffName, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng nhân viên tìm được dựa theo tên nhân viên
     * 
     * @param staffName {@code String}: tên nhân viên cần tìm
     * @return {@code int}: số lượng nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    public int getTotalLineByStaffName(String staffName) throws RemoteException;

    /**
     * Lấy danh sách nhân viên dựa theo số điện thoại
     * 
     * @param phoneNumber         {@code String}: số điện thoại cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    public ArrayList<NhanVien> getStaffListByPhoneNumberAndPageNumber(String phoneNumber, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng nhân viên tìm được dựa theo số điện thoại
     * 
     * @param phoneNumber {@code String}: số điện thoại cần tìm
     * @return {@code int}: số lượng nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    public int getTotalLineByPhoneNumber(String phoneNumber) throws RemoteException;

    /**
     * Lấy tên nhân viên dựa trên mã nhân viên
     * 
     * @param staffId {@code String}: mã nhân viên cần tìm
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên nhân viên}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    public String getStaffNameById(String staffId) throws RemoteException;

    /**
     * Lấy thông tin nhân viên theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn
     * @return {@code NhanVien}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code NhanVien}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    public NhanVien getStaffByBillId(String billId) throws RemoteException;

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
     * @throws RemoteException
     */
    public boolean checkPhoneNumberStaff(String username, String phoneNumber) throws RemoteException;
}
