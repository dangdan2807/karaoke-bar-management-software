package DAO;

import java.sql.*;
import java.util.*;

import entity.Phong;

public class PhongDAO {
    private static PhongDAO instance = new PhongDAO();
    public static int ROOM_WIDTH = 90;
    public static int ROOM_HEIGHT = 90;
    public static int CON_TRONG = 0;
    public static int PAID = 1;
    // public static int DAT_TRUOC = 2;

    public static PhongDAO getInstance() {
        if (instance == null)
            instance = new PhongDAO();
        return instance;
    }

    public ArrayList<Phong> getDSPhong() {
        String query = "{CALL USP_getDSPhong}";
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
        String query = "{CALL USP_getPhongByMaPhong( ? )}";
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
        String query = "{CALL USP_getDSPhongByTenLoaiPhong( ? )}";
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

    public ArrayList<Phong> getDSPhongTrong() {
        String query = "{CALL USP_getDSPhongTrong}";
        Object[] parameter = new Object[] {};
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

    public ArrayList<Phong> getDSPhongTrongTheoLoaiPhong(String loaiPhong) {
        String query = "{CALL USP_getDSPhongTrongTheoLoaiPhong( ? )}";
        Object[] parameter = new Object[] { loaiPhong };
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

    public boolean capNhatTinhTrangPhong(String maPhong, int tinhTrang) {
        String query = "{CALL USP_updateTinhTrangPhong( ? , ? )}";
        Object[] parameter = new Object[] { tinhTrang, maPhong };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }

    public void chuyenPhong(String maPhongCu, String maPhongMoi) {
        String query = "{CALL USP_chuyenPhong( ? , ? )}";
        Object[] parameter = new Object[] { maPhongCu, maPhongMoi };
        DataProvider.getInstance().ExecuteNonQuery(query, parameter);
    }
}
