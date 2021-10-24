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

    public int getSLDVuConByTenDichVu(String tenDichVu) {
        String query = "{CALL USP_getSLDVuConByTenDichVu( ? )}";
        Object[] parameter = new Object[] { tenDichVu };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result;
    }

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

    public String getLastServiceID() {
        String query = "{CALL USP_getLastServiceID}";
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        String staffID = obj != null ? obj.toString() : "";
        return staffID;
    }

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

    public boolean insertService(DichVu service) {
        String query = "{CALL USP_insertService( ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { service.getMaDichVu(), service.getTenDichVu(), service.getGiaBan(),
                service.getSoLuongTon(), service.getLoaiDV().getMaLDV() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    public String getServiceNameById(String serviceId) {
        String query = "{CALL  USP_getServiceNameById( ? )}";
        Object[] parameter = new Object[] { serviceId };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String serviceName = obj != null ? obj.toString() : "";
        return serviceName;
    }

    public Boolean updateInfoService(DichVu service) {
        String query = "{CALL USP_updateInfoService( ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { service.getMaDichVu(), service.getTenDichVu(), service.getGiaBan(),
                service.getSoLuongTon(), service.getLoaiDV().getMaLDV() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}
