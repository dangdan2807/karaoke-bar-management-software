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

    public ArrayList<DichVu> getDSachDichVu() {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "{CALL USP_getDSDichVu}";
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

}
