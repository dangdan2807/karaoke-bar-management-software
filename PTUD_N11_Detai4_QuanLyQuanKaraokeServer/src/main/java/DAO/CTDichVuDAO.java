package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.CTDichVu;

/**
 * Lớp interface cho lớp {@code CTDichVuDAOImpl}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 13/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public interface CTDichVuDAO extends Remote {
    /**
     * Lấy danh sách chi tiết dịch vụ theo mã phòng
     * 
     * @param roomId {@code String}: mã phòng cần tìm
     * @return {@code ArrayList<CTDichVu>}: danh sách chi tiết dịch vụ
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<CTDichVu> getServiceDetailListByRoomId(String roomId) throws RemoteException;

    /**
     * Lấy danh sách chi tiết dịch vụ theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn cần tìm
     * @return {@code ArrayList<CTDichVu>}: danh sách chi tiết dịch vụ
     * @throws RemoteException Bắt lỗi Remote
     */
    public ArrayList<CTDichVu> getServiceDetailListByBillId(String billId) throws RemoteException;

    /**
     * Lấy ra 1 chi tiết dịch vụ dựa trên mã hóa đơn và mã dịch vụ
     * 
     * @param billId {@code String}: mã hóa đơn cần tìm
     * @param serviceId {@code String}: mã dịch vụ cần tìm
     * @return {@code CTHoaDon}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code CTHoaDon}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    public CTDichVu getServiceDetailByBillIdAndServiceId(String billId, String serviceId) throws RemoteException;

    /**
     * Thêm một chi tiết dịch vụ
     * 
     * @param serviceDetail   {@code CTDichVu}: chi tiết dịch vụ cần thêm
     * @param quantity {@code int}: số lượng đặt
     * @param billId   {@code String}: mã hóa đơn
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi Remote
     */
    public boolean updateServiceDetail(CTDichVu serviceDetail, int quantity, String billId) throws RemoteException;
}
