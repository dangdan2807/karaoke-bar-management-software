package DAO;

import java.sql.*;
import java.util.*;

import entity.Phong;

public class PhongDAO {
    private static PhongDAO instance = new PhongDAO();
    public static int TABLE_WIDTH = 90;
    public static int TABLE_HEIGHT = 90;

    public static PhongDAO getInstance() {
        if (instance == null)
            instance = new PhongDAO();
        return instance;
    }

    public Phong getTableByTableID(String roomID) {
        String query = "select * FROM dbo.Phong p WHERE p.maPhong = ?";
        Object[] parameter = new Object[] { roomID };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        Phong phong = null;
        try {
            while (rs.next()) {
                phong = new Phong(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phong;
    }

    public ArrayList<Phong> getRoomList() {
        String query = "SELECT * FROM dbo.Phong";
        ArrayList<Phong> dataList = new ArrayList<Phong>();
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new Phong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
