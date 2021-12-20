package DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import DAO.DichVuDAO;
import entity.DichVu;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code DichVu}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Võ Minh Hiếu
 * <p>
 * Ngày tạo: 11/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class DichVuDAO {
    private static DichVuDAO instance = new DichVuDAO();

    public static DichVuDAO getInstance() {
        if (instance == null)
            instance = new DichVuDAO();
        return instance;
    }

    /**
     * Lấy danh sách tất cả dịch vụ
     * 
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceList() {
        String query = "{CALL USP_getServiceList()}";
        ArrayList<DichVu> dataList = new ArrayList<>();
        ResultSet rs = DataProvider.getInstance().executeQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo trang
     * 
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceListAndPageNumber(int currentPage, int lineNumberDisplayed) {
        String query = "{CALL USP_getServiceListAndPageNumber( ? , ? )}";
        ArrayList<DichVu> dataList = new ArrayList<>();
        Object[] params = { currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng dịch vụ
     * 
     * @return {@code int}: số lượng dịch vụ
     */
    public int getTotalLineOfServiceList() {
        String query = "{CALL USP_getTotalLineOfServiceList()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceListByServiceTypeName(String serviceTypeName) {
        String query = "{CALL USP_getServiceListByServiceTypeName( ? )}";
        Object[] params = { serviceTypeName };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<DichVu> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName     {@code String}: tên loại dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceListByServiceTypeNameAndPageNumber(String serviceTypeName, int currentPage,
            int lineNumberDisplayed) {
        String query = "{CALL USP_getServiceListByServiceTypeNameAndPageNumber( ? , ? , ? )}";
        Object[] params = { serviceTypeName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        ArrayList<DichVu> dataList = new ArrayList<>();
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code int}: số lượng dịch vụ
     */
    public int getTotalLineOfServiceListByServiceTypeName(String serviceTypeName) {
        String query = "{CALL USP_getTotalLineOfServiceListByServiceTypeName( ? )}";
        Object[] params = { serviceTypeName };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ
     * 
     * @param serviceName {@code String}: tên dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceListByName(String serviceName) {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        Object[] params = { serviceName };
        String query = "{CALL USP_getServiceListByName( ? )}";
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ và số trang
     * 
     * @param serviceName         {@code String}: tên dịch vụ
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceListByNameAndPageNumber(String serviceName, int currentPage,
            int lineNumberDisplayed) {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getServiceListByNameAndPageNumber( ? , ? , ? )}";
        Object[] params = { serviceName, currentPage, lineNumberDisplayed };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng dịch vụ theo tên dịch vụ
     * 
     * @param serviceName {@code String}: tên dịch vụ
     * @return {@code int}: số lượng dịch vụ
     */
    public int getTotalLineOfServiceListByName(String serviceName) {
        String query = "{CALL USP_getTotalLineOfServiceListByName( ? )}";
        Object[] params = { serviceName };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        int result = obj != null ? (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ và tên loại dịch vụ
     * 
     * @param serviceName     {@code String}: tên dịch vụ
     * @param serviceTypeName {@code String}: tên loại dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceListByNameAndServiceTypeName(String serviceName, String serviceTypeName) {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getServiceListByNameAndServiceTypeName( ? , ? )}";
        Object[] params = { serviceName, serviceTypeName };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy mã dịch vụ được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getLastServiceID() {
        String query = "{CALL USP_getLastServiceId()}";
        Object obj = DataProvider.getInstance().executeScalar(query, null);
        String result = obj != null ? obj.toString() : "";
        return result;
    }

    /**
     * Thêm dịch vụ mới
     * 
     * @param service {@code DichVu}: dịch vụ cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean insertService(DichVu service) {
        String query = "{CALL USP_insertService( ? , ? , ? , ? , ? )}";
        Object[] params = { service.getMaDichVu(), service.getTenDichVu(), service.getGiaBan(),
                service.getSoLuongTon(), service.getLoaiDV().getMaLDV() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy tên dịch vụ theo mã dịch vụ
     * 
     * @param serviceId {@code String}: mã dịch vụ
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getServiceNameById(String serviceId) {
        String query = "{CALL  USP_getServiceNameById( ? )}";
        Object[] params = { serviceId };
        Object obj = DataProvider.getInstance().executeScalar(query, params);
        String result = obj != null ? obj.toString() : "";
        return result;
    }

    /**
     * Cập nhật thông tin dịch vụ
     * 
     * @param service {@code DichVu}: dịch vụ cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean updateInfoService(DichVu service) {
        String query = "{CALL USP_updateInfoService( ? , ? , ? , ? , ? )}";
        Object[] params = { service.getMaDichVu(), service.getTenDichVu(), service.getGiaBan(),
                service.getSoLuongTon(), service.getLoaiDV().getMaLDV() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj != null ? (int) obj : 0;
        return result > 0;
    }
}
