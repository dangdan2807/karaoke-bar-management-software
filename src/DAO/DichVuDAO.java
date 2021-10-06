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

    public ArrayList<DichVu> getDSDichVu() {
        ArrayList<DichVu> dataList = new ArrayList<DichVu>();
        String query = "SELECT * FROM dbo.DichVu";
        Object[] parameter = new Object[] {};
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
        String query = "SELECT * FROM dbo.DichVu dv WHERE dv.tenDichVu = ?";
        Object[] parameter = new Object[] { tenDichVu };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            rs.next();
            data = new DichVu(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
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

}
