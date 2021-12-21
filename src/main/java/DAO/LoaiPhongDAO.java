package DAO;

import java.sql.*;
import java.util.ArrayList;
import entity.LoaiPhong;
import DAO.LoaiPhongDAO;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code LoaiPhong}
 * <p>
 * Người tham gia thiết kế: Võ Minh Hiếu
 * <p>
 * Ngày tạo: 03/10/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public class LoaiPhongDAO {
    private static LoaiPhongDAO instance = new LoaiPhongDAO();

    public static LoaiPhongDAO getInstance() {
        if (instance == null)
            instance = new LoaiPhongDAO();
        return instance;
    }

    /**
     * Lấy danh sách tất cả loại phòng
     * 
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public ArrayList<LoaiPhong> getRoomTypeList() {
        String query = "{CALL USP_getRoomTypeList()}";
        ResultSet rs = DataProvider.getInstance().executeQuery(query, null);
        ArrayList<LoaiPhong> dataList = new ArrayList<LoaiPhong>();
        try {
            while (rs.next()) {
                dataList.add(new LoaiPhong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách loại phòng theo từng trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public ArrayList<LoaiPhong> getRoomTypeListAndPageNumber(int currentPage, int lineNumberDisplayed) {
        ArrayList<LoaiPhong> dataList = new ArrayList<LoaiPhong>();
        String query = "{CALL USP_getRoomTypeListAndPageNumber( ? , ? )}";
        Object[] params = new Object[] { currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new LoaiPhong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại phòng
     * 
     * @return {@code int}: số lượng loại phòng
     */
    public int getTotalLineOfRoomTypeList() {
        String query = "{CALL USP_getTotalLineOfRoomTypeList()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy tên loại phòng theo mã loại phòng
     * 
     * @param roomId {@code String}: mã loại phòng
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên loại phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getRoomTypeNameById(String roomId) {
        String query = "{CALL USP_getRoomTypeNameById( ? )}";
        Object[] params = new Object[] { roomId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        String result = obj != null ? obj.toString() : "";
        return result;
    }

    /**
     * Lấy danh sách loại phòng có tên và số trang
     * 
     * @param roomTypeName        {@code String}: từ khóa trong tên loại phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public ArrayList<LoaiPhong> getRoomTypeListByNameAndPageNumber(String roomTypeName, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getRoomTypeListByNameAndPageNumber( ? , ? , ? )}";
        ArrayList<LoaiPhong> dataList = new ArrayList<>();
        Object[] params = new Object[] { roomTypeName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new LoaiPhong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại phòng theo tên và số trang
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public int getTotalLineOfRoomTypeListByName(String roomTypeName) {
        String query = "{CALL USP_getTotalLineOfRoomTypeListByName( ? )}";
        Object[] params = new Object[] { roomTypeName };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy mã loại phòng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã loại phòng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getLastRoomTypeId() {
        String query = "{CALL USP_getLastRoomTypeId()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        String result = obj != null ? obj.toString() : "";
        return result;
    }

    /**
     * Lấy thông tin loại phòng dựa theo mã phòng
     * 
     * @param roomTypeId {@code String}: mã loại phòng
     * @return {@code LoaiPhong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiPhong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public LoaiPhong getRoomTypeById(String roomTypeId) {
        String query = "{CALL USP_getRoomTypeById( ? )}";
        Object[] params = new Object[] { roomTypeId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        LoaiPhong result = null;
        try {
            while (rs.next()) {
                result = new LoaiPhong(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thêm một loại phòng mới vào cơ sở dữ liệu
     * 
     * @param roomType {@code LoaiPhong}: loại phòng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean insertRoomType(LoaiPhong roomType) {
        String query = "{CALL USP_insertRoomType( ? , ? , ? , ? )}";
        Object[] params = new Object[] { roomType.getMaLP(), roomType.getTenLP(),
                roomType.getSucChua(), roomType.getGiaTien() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Cập nhật thông tin loại phòng
     * 
     * @param roomType {@code LoaiPhong}: loại phòng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean updateInfoRoomType(LoaiPhong roomType) {
        String query = "{CALL USP_updateInfoRoomType( ? , ? , ? , ? )}";
        Object[] params = new Object[] { roomType.getMaLP(), roomType.getTenLP(),
                roomType.getSucChua(), roomType.getGiaTien() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách loại phòng theo giá phòng và số trang
     * 
     * @param price               {@code String}: Giá phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public ArrayList<LoaiPhong> getRoomTypeListByPriceAndPageNumber(String price, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getRoomTypeListByPriceAndPageNumber( ? , ? , ? )}";
        ArrayList<LoaiPhong> dataList = new ArrayList<>();
        Object[] params = new Object[] { price, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new LoaiPhong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại phòng theo giá và số trang
     * 
     * @param price {@code String}: Giá phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public int getTotalLineOfRoomTypeListByPrice(String price) {
        String query = "{CALL USP_getTotalLineOfRoomTypeListByPrice( ? )}";
        Object[] params = new Object[] { price };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy thông tin loại phòng dựa theo tên loại phòng
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code LoaiPhong}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiPhong}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public LoaiPhong getRoomTypeByName(String roomTypeName) {
        String query = "{CALL USP_getRoomTypeByName( ? )}";
        Object[] params = new Object[] { roomTypeName };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        LoaiPhong result = null;
        try {
            while (rs.next()) {
                result = new LoaiPhong(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách loại phòng có tên và số trang
     * 
     * @param roomTypeName        {@code String}: từ khóa trong tên loại phòng
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public ArrayList<LoaiPhong> getRoomTypeListByIdAndPageNumber(String roomTypeName, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getRoomTypeListByIdAndPageNumber( ? , ? , ? )}";
        ArrayList<LoaiPhong> dataList = new ArrayList<>();
        Object[] params = new Object[] { roomTypeName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new LoaiPhong(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại phòng theo tên và số trang
     * 
     * @param roomTypeName {@code String}: tên loại phòng
     * @return {@code ArrayList<LoaiPhong>}: danh sách loại phòng
     */
    public int getTotalLineOfRoomTypeListById(String roomTypeName) {
        String query = "{CALL USP_getTotalLineOfRoomTypeListById( ? )}";
        Object[] params = new Object[] { roomTypeName };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }
}
