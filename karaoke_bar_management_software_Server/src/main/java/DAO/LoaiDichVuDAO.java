package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.LoaiDichVu;

/**
 * Lớp interface cho lớp {@code LoaiDichVuDAOImpl}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Đỗ Thị Tường Vi
 * <p>
 * Ngày tạo: 07/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public interface LoaiDichVuDAO extends Remote {
    /**
     * Lấy danh sách thông tin tất cả loại dịch vụ
     * 
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<LoaiDichVu> getServiceTypeList() throws RemoteException;

    /**
     * Lấy danh sách loại dịch vụ theo số trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<LoaiDichVu> getServiceTypeListAndPageNumber(int currentPage, int lineNumberDisplayed)
            throws RemoteException;

    /**
     * Lấy số lượng loại dịch vụ
     * 
     * @return {@code int}: số lượng loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfServiceTypeList() throws RemoteException;

    /**
     * Lấy danh sách loại dịch vụ theo tên và số trang
     * 
     * @param serviceTypeName     {@code String}: từ khóa trong tên loại dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<LoaiDichVu> getServiceTypeListByNameAndPageNumber(String serviceTypeName, int currentPage,
            int lineNumberDisplayed) throws RemoteException;
    
    /**
     * Lấy số lượng loại dịch vụ theo tên
     * 
     * @param serviceTypeName {@code String}: từ khóa trong tên loại dịch vụ
     * @return {@code int}: số lượng loại dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfServiceTypeListByName(String serviceTypeName) throws RemoteException;

    /**
     * Lấy thông tin loại dịch vụ dựa theo tên
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code LoaiDichVu}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiDichVu}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public LoaiDichVu getServiceTypeByName(String serviceTypeName) throws RemoteException;

    /**
     * Lấy mã loại dịch vụ được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã loại dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public String getLastServiceTypeID() throws RemoteException;

    /**
     * Lấy thông tin loại dịch vụ dựa theo tên
     * 
     * @param serviceTypeID {@code String}: mã loại dịch vụ
     * @return {@code LoaiDichVu}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiDichVu}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public LoaiDichVu getServiceTypeById(String serviceTypeID) throws RemoteException;

    /**
     * Thêm một loại dịch vụ mới vào cơ sở dữ liệu
     * 
     * @param serviceType {@code LoaiDichVu}: loại dịch vụ cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean insertService(LoaiDichVu serviceType) throws RemoteException;

    /**
     * Cập nhật thông tin loại dịch vụ vào cơ sở dữ liệu
     * 
     * @param serviceType {@code LoaiDichVu}: loại dịch vụ cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean updateInfoServiceType(LoaiDichVu serviceType) throws RemoteException;
}
