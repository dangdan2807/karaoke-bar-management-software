package DAO;

import java.sql.*;

import entity.HoaDon;

public class HoaDonDAO {
    private static HoaDonDAO instance = new HoaDonDAO();
    public static int UNPAID = 0;
    public static int DA_THANH_TOAN = 1;

    public static HoaDonDAO getInstance() {
        if (instance == null)
            instance = new HoaDonDAO();
        return instance;
    }

    public HoaDon getUncheckHoaDonByMaPhong(String maPhong) {
        String query = "{CALL USP_getUncheckHoaDonByMaPhong ( ? )}";
        Object[] parameter = new Object[] { maPhong };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        HoaDon data = null;
        try {
            while (rs.next()) {
                data = new HoaDon(rs, 1);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data != null ? data : new HoaDon(-1);
    }

    public HoaDon getHoaDonByMaHD(int maHoaDon) {
        String query = "{CALL USP_getHoaDonByMaHD ( ? )}";
        Object[] parameter = new Object[] { maHoaDon };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        HoaDon data = null;
        try {
            while (rs.next()) {
                data = new HoaDon(rs, 1);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (data != null) {
            return data;
        }
        return new HoaDon(-1);
    }

    public int getMaHDCuoiCung() {
        String query = "{CALL USP_getMaHDCuoiCung}";
        int maHoaDon = (int) DataProvider.getInstance().ExecuteScalar(query, null);
        return maHoaDon;
    }

    public boolean themHoaDon(HoaDon hoaDon) {
        String query = "{CALL USP_themHoaDon ( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { hoaDon.getNgayGioDat(), hoaDon.getNhanVien().getMaNhanVien(),
                hoaDon.getKhachHang().getMaKH(), hoaDon.getPhong().getMaPhong() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }

    public void thanhToan(int maHoaDon, double tongTien) {
        String query = "{CALL USP_thanhToanHD( ? , ? )}";
        Object[] param = new Object[] { tongTien, maHoaDon };
        DataProvider.getInstance().ExecuteNonQuery(query, param);
    }
}
