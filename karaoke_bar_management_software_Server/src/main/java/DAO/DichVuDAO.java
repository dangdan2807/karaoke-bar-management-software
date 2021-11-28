package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.DichVu;

/**
 * Lớp interface cho lớp {@code DichVuDAOImpl}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Đỗ Thị Tường Vi
 * <p>
 * Ngày tạo: 11/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public interface DichVuDAO extends Remote {
    /**
     * Lấy danh sách tất cả dịch vụ
     * 
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<DichVu> getServiceList() throws RemoteException;

    /**
     * Lấy danh sách dịch vụ theo trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<DichVu> getServiceListAndPageNumber(int currentPage, int lineNumberDisplayed)
            throws RemoteException;

    /**
     * Lấy số lượng dịch vụ
     * 
     * @return {@code int}: số lượng dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfServiceList() throws RemoteException;

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<DichVu> getServiceListByServiceTypeName(String serviceTypeName) throws RemoteException;

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName     {@code String}: tên loại dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<DichVu> getServiceListByServiceTypeNameAndPageNumber(String serviceTypeName, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code int}: số lượng dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfServiceListByServiceTypeName(String serviceTypeName) throws RemoteException;

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ
     * 
     * @param serviceName {@code String}: tên dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<DichVu> getServiceListByName(String serviceName) throws RemoteException;

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ và số trang
     * 
     * @param serviceName         {@code String}: tên dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<DichVu> getServiceListByNameAndPageNumber(String serviceName, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng dịch vụ theo tên dịch vụ
     * 
     * @param serviceName {@code String}: tên dịch vụ
     * @return {@code int}: số lượng dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfServiceListByName(String serviceName) throws RemoteException;

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ và tên loại dịch vụ
     * 
     * @param serviceName     {@code String}: tên dịch vụ
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<DichVu> getServiceListByNameAndServiceTypeName(String serviceName, String serviceTypeName)
            throws RemoteException;

    /**
     * Lấy mã dịch vụ được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public String getLastServiceID() throws RemoteException;

    /**
     * Thêm dịch vụ mới
     * 
     * @param service {@code DichVu}: dịch vụ cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public boolean insertService(DichVu service) throws RemoteException;

    /**
     * Lấy tên dịch vụ theo mã dịch vụ
     * 
     * @param serviceId {@code String}: mã dịch vụ
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public String getServiceNameById(String serviceId) throws RemoteException;

    /**
     * Cập nhật thông tin dịch vụ
     * 
     * @param service {@code DichVu}: dịch vụ cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean updateInfoService(DichVu service) throws RemoteException;
}
