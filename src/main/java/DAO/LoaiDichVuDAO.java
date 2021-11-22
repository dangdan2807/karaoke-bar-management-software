package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.LoaiDichVu;

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
        ArrayList<LoaiDichVu> dataList = new ArrayList<LoaiDichVu>();
        String query = "{CALL USP_getServiceTypeList()}";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new LoaiDichVu(rs));
            }
        } catch (SQLException e) {
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
        ArrayList<LoaiDichVu> dataList = new ArrayList<LoaiDichVu>();
        String query = "{CALL USP_getServiceTypeListAndPageNumber( ? , ? )}";
        Object[] parameter = new Object[] { currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new LoaiDichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại dịch vụ
     * 
     * @return {@code int}: số lượng loại phòng
     */
    public int getTotalLineOfServiceTypeList() {
        String query = "{CALL USP_getTotalLineOfServiceTypeList()}";
        Object[] parameter = new Object[] {};
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
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
        ArrayList<LoaiDichVu> dataList = new ArrayList<LoaiDichVu>();
        String query = "{CALL USP_getServiceTypeListByNameAndPageNumber( ? , ? , ? )}";
        Object[] parameter = new Object[] { serviceTypeName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new LoaiDichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng loại dịch vụ theo tên
     * 
     * @param serviceTypeName {@code String}: từ khóa trong tên loại dịch vụ
     * @return {@code int}: số lượng loại phòng
     */
    public int getTotalLineOfServiceTypeListByName(String serviceTypeName) {
        String query = "{CALL USP_getTotalLineOfServiceTypeListByName( ? )}";
        Object[] parameter = new Object[] { serviceTypeName };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
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
        LoaiDichVu data = null;
        String query = "{CALL USP_getServiceTypeByName( ? )}";
        Object[] parameter = new Object[] { serviceTypeName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next())
                data = new LoaiDichVu(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
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
        String query = "{CALL USP_getLastServiceTypeID}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String staffID = obj != null ? obj.toString() : "";
        return staffID;
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
        LoaiDichVu data = null;
        String query = "{CALL USP_getServiceTypeById( ? )}";
        Object[] parameter = new Object[] { serviceTypeID };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next())
                data = new LoaiDichVu(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
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
        Object[] parameter = new Object[] { serviceType.getMaLDV(), serviceType.getTenLDV() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
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
        Object[] parameter = new Object[] { serviceType.getMaLDV(), serviceType.getTenLDV() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}
