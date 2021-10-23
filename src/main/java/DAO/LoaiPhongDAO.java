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

    public ArrayList<LoaiPhong> getDSLoaiPhong() {
        ArrayList<LoaiPhong> dataList = new ArrayList<LoaiPhong>();
        String query = "{CALL USP_getDSLoaiPhong}";
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

    public String getTenLPbyMaPhong(String maPhong) {
        String query = "{CALL USP_getTenLPbyMaPhong( ? )}";
        Object[] parameter = new Object[] { maPhong };
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter).toString();
        String roomTypeName = obj != null ? roomTypeName = obj.toString() : "";
        return roomTypeName;
    }
}
