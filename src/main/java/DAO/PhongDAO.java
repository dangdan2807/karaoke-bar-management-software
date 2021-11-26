package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.Phong;

/**
 * Lớp interface cho lớp {@code PhongDAOImpl}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 10/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public interface PhongDAO extends Remote {
    public static int ROOM_WIDTH = 90;
    public static int ROOM_HEIGHT = 90;
    public static int EMPTY = 0;
    public static int RENT = 1;

    /**
     * Lấy danh sách tất cả phòng
     * 
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<Phong> getRoomList() throws RemoteException;

    /**
     * Lấy phòng dựa trên mã phòng
     * 
     * @param roomId {@code String}: mã phòng
     * @return {@code Phong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code Phong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Phong getRoomByRoomId(String roomId) throws RemoteException;

    /**
     * Lấy danh sách phòng dựa trên tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<Phong> getRoomListByRoomTypeName(String roomTypeName) throws RemoteException;

    /**
     * Lấy danh sách phòng dựa trên tên loại phòng và số của trang chỉ định
     * 
     * @param roomTypeName        {@code String}: tên loại phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<Phong> getRoomListByRoomTypeNameAndPageNumber(String roomTypeName, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng phòng theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code int}: số lượng phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfRoomListByRoomTypeName(String roomTypeName) throws RemoteException;

    /**
     * Lấy danh sách phòng trống
     * 
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<Phong> getListAvailableRoom() throws RemoteException;

    /**
     * Lấy danh sách phòng trống theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<Phong>}: danh sách phòng trống
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<Phong> getListAvailableRoomByRoomTypeName(String roomTypeName) throws RemoteException;

    /**
     * cập nhật trạng thái phòng
     * 
     * @param roomId {@code String}: mã phòng
     * @param status {@code int}: thông tin trạng thái phòng
     *               <ul>
     *               <li>Nếu phòng còn trống thì truyền vào {@code 0}</li>
     *               <li>Nếu phòng đã cho thuê thì truyền vào {@code 1}</li>
     *               </ul>
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public boolean updateRoomStatus(String roomId, int status) throws RemoteException;

    /**
     * Chuyển phòng đang cho thuê
     * 
     * @param roomId    {@code String}: mã hóa đơn cần chuyển phòng
     * @param oldRoomId {@code String}: mã phòng cũ
     * @param newRoomId {@code String}: mã phòng mới
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public boolean switchRoom(String billId, String oldRoomId, String newRoomId) throws RemoteException;

    /**
     * Lấy danh sách phòng theo vị trí
     * 
     * @param location            {@code String}: vị trí của phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<Phong> getRoomListByLocationAndPageNumber(String location, int currentPage,
            int lineNumberDisplayed) throws RemoteException;

    /**
     * Lấy số lượng phòng theo trạng thái
     * 
     * @param location {@code String}: ví trí phòng
     * @return {@code int}: số lượng loại phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfRoomListByLocation(String location) throws RemoteException;

    /**
     * Lấy danh sách phòng theo trạng thái của phòng
     * 
     * @param roomStatus          {@code int}: tính trạng phòng
     *                            <ul>
     *                            <li>Nếu phòng còn trống thì truyền vào
     *                            {@code 0}</li>
     *                            <li>Nếu phòng đang sử dụng thì truyền vào
     *                            {@code 1}</li>
     *                            </ul>
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<Phong>}: danh sách phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public ArrayList<Phong> getRoomListByStatusAndPageNumber(int roomStatus, int currentPage, int lineNumberDisplayed)
            throws RemoteException;

    /**
     * Lấy số lượng phòng theo trạng thái
     * 
     * @param roomStatus {@code int}: tính trạng phòng
     *                   <ul>
     *                   <li>Nếu phòng còn trống thì truyền vào {@code 0}</li>
     *                   <li>Nếu phòng đang sử dụng thì truyền vào {@code 1}</li>
     *                   </ul>
     * @return {@code int}: số lượng phòng
     * @throws RemoteException - Bắt lỗi Remote
     */
    public int getTotalLineOfRoomListByStatus(int roomStatus) throws RemoteException;

    /**
     * Cập nhật thông tin phòng
     * 
     * @param room {@code Phong}: thông tin phòng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean updateInfoRoom(Phong room) throws RemoteException;

    /**
     * Thêm một phòng mới vào cơ sở dữ liệu
     * 
     * @param room {@code Phong}: phòng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Boolean insertRoom(Phong room) throws RemoteException;

    /**
     * Lấy mã phòng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public String getLastRoomID() throws RemoteException;

    /**
     * Lấy phòng dựa trên mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn
     * @return {@code Phong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code Phong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    public Phong getRoomByBillId(String billId) throws RemoteException;
}
