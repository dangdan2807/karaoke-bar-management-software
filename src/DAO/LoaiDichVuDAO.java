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
    public ArrayList<LoaiDichVu> getDSLoaiDV() {
        ArrayList<LoaiDichVu> dataList = new ArrayList<LoaiDichVu>();
        String query = "SELECT * FROM dbo.LoaiDichVu";
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
}
