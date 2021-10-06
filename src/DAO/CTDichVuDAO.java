package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.CTDichVu;

public class CTDichVuDAO {
    private static CTDichVuDAO instance = new CTDichVuDAO();

    public static CTDichVuDAO getInstance() {
        if (instance == null)
            instance = new CTDichVuDAO();
        return instance;
    }

    public ArrayList<CTDichVu> getCTDichVuListByMaPhong(String maPhong) {
        ArrayList<CTDichVu> dataList = new ArrayList<CTDichVu>();
        String query = "{CALL USP_getCTDichVuListByMaPhong ( ? )}";
        Object[] parameter = new Object[] { maPhong };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new CTDichVu(rs, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
