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
     * Lấy ra danh sách tất cả loại dịch vụ
     * 
     * @param
     * @return
     */
    public ArrayList<LoaiDichVu> getServiceTypeList() {
        ArrayList<LoaiDichVu> dataList = new ArrayList<LoaiDichVu>();
        String query = "{CALL USP_getLoaiDichVuList()}";
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
}
