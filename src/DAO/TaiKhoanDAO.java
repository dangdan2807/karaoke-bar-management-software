package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.TaiKhoan;

public class TaiKhoanDAO {
    private static TaiKhoanDAO instance = new TaiKhoanDAO();

    public static TaiKhoanDAO getInstance() {
        if (instance == null)
            instance = new TaiKhoanDAO();
        return instance;
    }

    public ArrayList<TaiKhoan> getDSTaiKhoan() {
        String query = "SELECT * FROM dbo.TaiKhoan";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        ArrayList<TaiKhoan> dataList = new ArrayList<TaiKhoan>();
        try {
            while (rs.next()) {
                dataList.add(new TaiKhoan(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public boolean dangNhap(String username, String password) {
        int count = 0;
        String query = "{CALL USP_Login ( ?, ? )}";
        Object[] parameter = new Object[] { username, password };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            rs.next();
            count = rs.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    public TaiKhoan getAccountByUsername(String username) {
        String query = "Select * from dbo.TaiKhoan where username = ?";
        Object[] parameter = new Object[] { username };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        TaiKhoan account = null;
        try {
            rs.next();
            account = new TaiKhoan(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
}
