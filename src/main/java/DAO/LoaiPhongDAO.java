package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.LoaiPhong;

/**
 * Lớp interface cho lớp {@code LoaiPhongDAO}
 * <p>
 * Người tham gia thiết kế: Võ Minh Hiếu
 * <p>
 * Ngày tạo: 03/10/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public interface LoaiPhongDAO extends Remote {
    /**
     * Lấy danh sách tất cả loại phòng
     * 
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<LoaiPhong> getRoomTypeList() throws RemoteException;

    /**
     * Lấy danh sách loại phòng theo từng trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<LoaiPhong> getRoomTypeListAndPageNumber(int currentPage, int lineNumberDisplayed)
            throws RemoteException;

    /**
     * Lấy số lượng loại phòng
     * 
     * @return {@code int}: số lượng loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfRoomTypeList() throws RemoteException;

    /**
     * Lấy tên loại phòng theo mã loại phòng
     * 
     * @param roomId {@code String}: mã loại phòng
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên loại phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public String getRoomTypeNameById(String roomId) throws RemoteException;

    /**
     * Lấy danh sách loại phòng có tên và số trang
     * 
     * @param roomTypeName        {@code String}: từ khóa trong tên loại phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<LoaiPhong> getRoomTypeListByNameAndPageNumber(String roomTypeName, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng loại phòng theo tên và số trang
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfRoomTypeListByName(String roomTypeName) throws RemoteException;

    /**
     * Lấy mã loại phòng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã loại phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public String getLastRoomTypeId() throws RemoteException;

    /**
     * Lấy thông tin loại phòng dựa theo mã phòng
     * 
     * @param roomTypeId {@code String}: mã loại phòng
     * @return {@code LoaiPhong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiPhong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public LoaiPhong getRoomTypeById(String roomTypeId) throws RemoteException;

    /**
     * Thêm một loại phòng mới vào cơ sở dữ liệu
     * 
     * @param roomType {@code LoaiPhong}: loại phòng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean insertRoomType(LoaiPhong roomType) throws RemoteException;

    /**
     * Cập nhật thông tin loại phòng
     * 
     * @param roomType {@code LoaiPhong}: loại phòng cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean updateInfoRoomType(LoaiPhong roomType) throws RemoteException;

    /**
     * Lấy danh sách loại phòng theo giá phòng và số trang
     * 
     * @param price               {@code String}: Giá phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<LoaiPhong> getRoomTypeListByPriceAndPageNumber(String price, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng loại phòng theo giá và số trang
     * 
     * @param price {@code String}: Giá phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfRoomTypeListByPrice(String price) throws RemoteException;

    /**
     * Lấy thông tin loại phòng dựa theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code LoaiPhong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiPhong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public LoaiPhong getRoomTypeByName(String roomTypeName) throws RemoteException;
}
