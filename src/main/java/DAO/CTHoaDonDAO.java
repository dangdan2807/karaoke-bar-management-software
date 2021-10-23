package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.CTHoaDon;

public class CTHoaDonDAO {
    private static CTHoaDonDAO instance = new CTHoaDonDAO();

    public static CTHoaDonDAO getInstance() {
        if (instance == null)
            instance = new CTHoaDonDAO();
        return instance;
    }

    /**
     * Lấy danh sách chi tiết dịch vụ theo mã phòng
     * 
     * @param maPhong mã phòng
     * @return ArrayList<CTDichVu> danh sách chi tiết dịch vụ
     */
    public ArrayList<CTHoaDon> getCTHoaDonListByMaPhong(String maPhong) {
        ArrayList<CTHoaDon> dataList = new ArrayList<CTHoaDon>();
        String query = "{CALL USP_getCTHoaDonListByMaPhong ( ? )}";
        Object[] parameter = new Object[] { maPhong };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new CTHoaDon(rs, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra 1 chi tiết dịch vụ dựa trên mã hóa đơn và mã dịch vụ
     * 
     * @param maHD mã hóa đơn
     * @param maDV mã dịch vụ
     * @return CTDichVu
     */
    public CTHoaDon getCTHoaDonByMaHDvaMaDV(int maHD, String maDV) {
        CTHoaDon data = null;
        String query = "{CALL USP_getCTHoaDonByMaHDvaMaDV ( ? , ? )}";
        Object[] parameter = new Object[] { maHD, maDV };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next()) {
                data = new CTHoaDon(rs, 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Thêm một chi tiết dịch vụ
     * 
     * @param ctDichVu   Chi tiết dịch vụ
     * @param soLuongDat số lượng đặt
     * @param maHoaDon   mã hóa đơn
     * @return
     */
    public boolean themCTHoaDon(CTHoaDon ctDichVu, int soLuongDat, int maHoaDon) {
        String query = "{CALL USP_themCTHoaDon ( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { ctDichVu.getDichVu().getMaDichVu(), maHoaDon, soLuongDat,
                ctDichVu.getDichVu().getGiaBan() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }
}
