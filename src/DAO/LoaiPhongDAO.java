package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.LoaiPhong;

public class LoaiPhongDAO {
    private static LoaiPhongDAO instance = new LoaiPhongDAO();

    public static LoaiPhongDAO getInstance() {
        if (instance == null)
            instance = new LoaiPhongDAO();
        return instance;
    }

    public ArrayList<LoaiPhong> getListLoaiPhong() {
        ArrayList<LoaiPhong> dataList = new ArrayList<LoaiPhong>();
        String query = "SELECT * FROM dbo.LoaiPhong";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new LoaiPhong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
