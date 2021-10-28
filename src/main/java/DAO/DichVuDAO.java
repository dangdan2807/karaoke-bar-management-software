package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.DichVu;

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
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getServiceList}";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ
     * 
     * @param serviceName {@code String}: tên dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceListByServiceName(String serviceName) {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getServiceListByServiceName( ? )}";
        Object[] parameter = new Object[] { serviceName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     * 
     * @param serviceTypeName {@code String}: loại tên dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getServiceListByServiceTypeName(String serviceTypeName) {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getServiceListByServiceTypeName( ? )}";
        Object[] parameter = new Object[] { serviceTypeName };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy dịch vụ theo tên dịch vụ
     * 
     * @param tenDichVu {@code int}: tên dịch vụ
     * @return {@code DichVu}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code DichVu}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public DichVu getDichVuByTenDichVu(String tenDichVu) {
        DichVu data = null;
        String query = "{CALL USP_getDichVuByTenDichVu( ? )}";
        Object[] parameter = new Object[] { tenDichVu };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next())
                data = new DichVu(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Lấy số lượng tồn của dịch vụ bằng tên dịch vụ
     * 
     * @param tenDichVu {@code String}: tên dịch vụ
     * @return {@code int}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code số lượng tồn của dịch vụ}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code 0} 0</li>
     *         </ul>
     */
    public int getSLDVuConByTenDichVu(String tenDichVu) {
        String query = "{CALL USP_getSLDVuConByTenDichVu( ? )}";
        Object[] parameter = new Object[] { tenDichVu };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ
     * 
     * @param tenDichVu {@code String}: tên dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getDSDichVuByTenDichVu(String tenDichVu) {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getDSDichVuByTenDichVu( ? )}";
        Object[] parameter = new Object[] { tenDichVu };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên loại dịch vụ
     * 
     * @param tenLoaiDV {@code String}: tên loại dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getDSDichVuByTenLoaiDV(String tenLoaiDV) {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getDSDichVuByTenLoaiDV( ? )}";
        Object[] parameter = new Object[] { tenLoaiDV };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách dịch vụ theo tên dịch vụ và tên loại dịch vụ
     * 
     * @param tenDV     {@code String}: tên dịch vụ
     * @param tenLoaiDV {@code String}: tên loại dịch vụ
     * @return {@code ArrayList<DichVu>}: danh sách dịch vụ
     */
    public ArrayList<DichVu> getDSDichVuByTenDVvaTenLoaiDV(String tenDV, String tenLoaiDV) {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getDSDichVuByTenDVvaTenLoaiDV( ? , ? )}";
        Object[] parameter = new Object[] { tenDV, tenLoaiDV };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new DichVu(rs));
            }
        } catch (SQLException e) {
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
        String query = "{CALL USP_getLastServiceID}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String staffID = obj != null ? obj.toString() : "";
        return staffID;
    }

    /**
     * Lấy dịch vụ theo mã dịch vụ
     * 
     * @param serviceID {@code String}: mã dịch vụ
     * @return {@code DichVu}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code DichVu}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public DichVu getServiceById(String serviceID) {
        DichVu data = null;
        String query = "{CALL USP_getServiceById( ? )}";
        Object[] parameter = new Object[] { serviceID };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next())
                data = new DichVu(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
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
        Object[] parameter = new Object[] { service.getMaDichVu(), service.getTenDichVu(), service.getGiaBan(),
                service.getSoLuongTon(), service.getLoaiDV().getMaLDV() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
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
        Object[] parameter = new Object[] { serviceId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String serviceName = obj != null ? obj.toString() : "";
        return serviceName;
    }

    /**
     * Cập nhật thông tin dịch vụ
     * 
     * @param service {@code DichVu}: dịch vụ cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public Boolean updateInfoService(DichVu service) {
        String query = "{CALL USP_updateInfoService( ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { service.getMaDichVu(), service.getTenDichVu(), service.getGiaBan(),
                service.getSoLuongTon(), service.getLoaiDV().getMaLDV() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}
