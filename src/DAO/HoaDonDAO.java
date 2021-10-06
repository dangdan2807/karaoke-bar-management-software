package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.HoaDon;

public class HoaDonDAO {
    private static HoaDonDAO instance = new HoaDonDAO();

    public static HoaDonDAO getInstance() {
        if (instance == null)
            instance = new HoaDonDAO();
        return instance;
    }

    public HoaDon getUncheckHoaDonByMaPhong(String maPhong) {
        String query = "{CALL USP_getUncheckHoaDonByMaPhong ( ? )}";
        Object[] parameter = new Object[] { maPhong };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        try {
            while (rs.next()) {
                dataList.add(new HoaDon(rs, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dataList.size() > 0) {
            HoaDon hoaDon = dataList.get(0);
            return hoaDon;
        }
        return null;
    }

    public boolean themHoaDon(HoaDon hoaDon) {
        String query = "INSERT INTO dbo.Account (username , password , displayName , type) VALUES ( ? , ? , ? , ? )";
        // Object[] parameter = new Object[] { account.getUsername(), account.getPassword(), account.getDisplayName(),
        //         account.getType() };
        // int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        // return result > 0;
        return true;
    }
}
