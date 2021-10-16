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

    public ArrayList<CTDichVu> getDSachCTDichVuByMaPhong(String maPhong) {
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

    public CTDichVu getCTDichVuByMaHDvaMaDV(int maHD, String maDV) {
        CTDichVu data = null;
        String query = "{CALL USP_getCTDichVuByMaHDvaMaDV ( ? , ? )}";
        Object[] parameter = new Object[] { maHD, maDV };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                data = new CTDichVu(rs, 1);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public boolean themCTDichVu(CTDichVu ctDichVu, int soLuongDatMoi, int maHoaDon) {
        String query = "{CALL USP_themCTDichVu ( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { ctDichVu.getDichVu().getMaDichVu(), maHoaDon, soLuongDatMoi,
                ctDichVu.getDichVu().getGiaBan() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }
}
