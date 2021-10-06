package DAO;

import java.sql.*;
import java.util.*;

import entity.Phong;

public class PhongDAO {
    private static PhongDAO instance = new PhongDAO();
    public static int ROOM_WIDTH = 90;
    public static int ROOM_HEIGHT = 90;

    public static PhongDAO getInstance() {
        if (instance == null)
            instance = new PhongDAO();
        return instance;
    }

    public ArrayList<Phong> getDSPhong() {
        String query = "SELECT * FROM dbo.Phong p, dbo.LoaiPhong lp where p.maLP = lp.maLP";
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

    public Phong getPhongByMaPhong(String maPhong) {
        String query = "select * FROM dbo.Phong p, dbo.LoaiPhong lp WHERE p.maLP = lp.maLP and p.maPhong = ?";
        Object[] parameter = new Object[] { maPhong };
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

    public ArrayList<Phong> getDSPhongByTenLoaiPhong(String tenLP) {
        String query = "select * FROM dbo.Phong p, dbo.LoaiPhong lp WHERE p.maLP = lp.maLP and lp.tenLP = ?";
        Object[] parameter = new Object[] { tenLP };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<Phong> dsPhong = new ArrayList<Phong>();
        try {
            while (rs.next()) {
                dsPhong.add(new Phong(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
    }
}
