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
            if (rs.next()) {
                data = new HoaDon(rs, 1);
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
            if (rs.next()) {
                data = new HoaDon(rs, 1);
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
        Object obj = DataProvider.getInstance().ExecuteScalar(query, null);
        int billID = obj != null ? billID = (int) obj : 0;
        return billID;
    }

    public boolean themHoaDon(HoaDon hoaDon) {
        String query = "{CALL USP_themHoaDon ( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { hoaDon.getNgayGioDat(), hoaDon.getNhanVien().getMaNhanVien(),
                hoaDon.getKhachHang().getMaKH(), hoaDon.getPhong().getMaPhong() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }

    public boolean thanhToan(int maHoaDon, Timestamp ngayGioTra, double tongTien) {
        String query = "{CALL USP_thanhToanHD( ? , ? , ? )}";
        Object[] param = new Object[] { tongTien, ngayGioTra, maHoaDon };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, param);
        return result > 0;
    }
}
