package DAO;

import java.sql.*;
import java.util.ArrayList;
import DAO.LoaiDichVuDAO;
import entity.LoaiDichVu;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code LoaiDichVu}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Võ Minh Hiếu
 * <p>
 * Ngày tạo: 07/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class LoaiDichVuDAO {
    private static LoaiDichVuDAO instance = new LoaiDichVuDAO();

    public static LoaiDichVuDAO getInstance() {
        if (instance == null)
            instance = new LoaiDichVuDAO();
        return instance;
    }

    /**
     * Lấy danh sách thông tin tất cả loại dịch vụ
     * 
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     */
    public ArrayList<LoaiDichVu> getServiceTypeList() {
        String query = "{CALL USP_getServiceTypeList()}";
        ResultSet rs = DataProvider.getInstance().executeQuery(query, null);
        ArrayList<LoaiDichVu> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new LoaiDichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách loại dịch vụ theo số trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     */
    public ArrayList<LoaiDichVu> getServiceTypeListAndPageNumber(int currentPage, int lineNumberDisplayed) {
        String query = "{CALL USP_getServiceTypeListAndPageNumber( ? , ? )}";
        Object[] params = { currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<LoaiDichVu> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new LoaiDichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại dịch vụ
     * 
     * @return {@code int}: số lượng loại dịch vụ
     */
    public int getTotalLineOfServiceTypeList() {
        String query = "{CALL USP_getTotalLineOfServiceTypeList()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách loại dịch vụ theo tên và số trang
     * 
     * @param serviceTypeName     {@code String}: từ khóa trong tên loại dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     */
    public ArrayList<LoaiDichVu> getServiceTypeListByNameAndPageNumber(String serviceTypeName, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getServiceTypeListByNameAndPageNumber( ? , ? , ? )}";
        ArrayList<LoaiDichVu> dataList = new ArrayList<>();
        Object[] params = { serviceTypeName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new LoaiDichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại dịch vụ theo tên
     * 
     * @param serviceTypeName {@code String}: từ khóa trong tên loại dịch vụ
     * @return {@code int}: số lượng loại dịch vụ
     */
    public int getTotalLineOfServiceTypeListByName(String serviceTypeName) {
        String query = "{CALL USP_getTotalLineOfServiceTypeListByName( ? )}";
        Object[] params = { serviceTypeName };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy thông tin loại dịch vụ dựa theo tên
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code LoaiDichVu}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiDichVu}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public LoaiDichVu getServiceTypeByName(String serviceTypeName) {
        String query = "{CALL USP_getServiceTypeByName( ? )}";
        Object[] params = { serviceTypeName };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        LoaiDichVu result = null;
        try {
            while (rs.next()) {
                result = new LoaiDichVu(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy mã loại dịch vụ được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã loại dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getLastServiceTypeID() {
        String query = "{CALL USP_getLastServiceTypeID()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        String result = obj != null ? obj.toString() : "";
        return result;
    }

    /**
     * Lấy thông tin loại dịch vụ dựa theo tên
     * 
     * @param serviceTypeID {@code String}: mã loại dịch vụ
     * @return {@code LoaiDichVu}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code LoaiDichVu}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public LoaiDichVu getServiceTypeById(String serviceTypeID) {
        String query = "{CALL USP_getServiceTypeById( ? )}";
        Object[] params = { serviceTypeID };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        LoaiDichVu result = null;
        try {
            while (rs.next()) {
                result = new LoaiDichVu(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thêm một loại dịch vụ mới vào cơ sở dữ liệu
     * 
     * @param serviceType {@code LoaiDichVu}: loại dịch vụ cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean insertService(LoaiDichVu serviceType) {
        String query = "{CALL USP_insertServiceType( ? , ? )}";
        Object[] params = { serviceType.getMaLDV(), serviceType.getTenLDV() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Cập nhật thông tin loại dịch vụ vào cơ sở dữ liệu
     * 
     * @param serviceType {@code LoaiDichVu}: loại dịch vụ cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean updateInfoServiceType(LoaiDichVu serviceType) {
        String query = "{CALL USP_updateInfoServiceType( ? , ? )}";
        Object[] params = { serviceType.getMaLDV(), serviceType.getTenLDV() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy danh sách loại dịch vụ theo mã và số trang
     * 
     * @param serviceTypeId     {@code String}: từ khóa trong mã loại dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<LoaiDichVu>}: danh sách loại dịch vụ
     */
    public ArrayList<LoaiDichVu> getServiceTypeListByIdAndPageNumber(String serviceTypeId, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getServiceTypeListByIdAndPageNumber( ? , ? , ? )}";
        ArrayList<LoaiDichVu> dataList = new ArrayList<>();
        Object[] params = { serviceTypeId, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new LoaiDichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại dịch vụ theo mã
     * 
     * @param serviceTypeId {@code String}: từ khóa trong mã loại dịch vụ
     * @return {@code int}: số lượng loại dịch vụ
     */
    public int getTotalLineOfServiceTypeListById(String serviceTypeId) {
        String query = "{CALL USP_getTotalLineOfServiceTypeListById( ? )}";
        Object[] params = { serviceTypeId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }
}
