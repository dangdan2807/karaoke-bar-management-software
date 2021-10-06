package DAO;

import java.sql.*;
import entity.KhachHang;
import entity.NhanVien;

public class KhachHangDAO {
    private static KhachHangDAO instance = new KhachHangDAO();

    public static KhachHangDAO getInstance() {
        if (instance == null)
            instance = new KhachHangDAO();
        return instance;
    }

    public KhachHang getKhachHangByMaKH(String maKH) {
        KhachHang data = null;
        String query = "SELECT * FROM dbo.KhachHang kh WHERE kh.maKH = ?";
        Object[] parameter = new Object[] { maKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            rs.next();
            data = new KhachHang(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public boolean insertKhachHang(KhachHang khachHangData) {
        return false;
    }
    
    public String getMaKHCuoiCung() {
		String maKH = "";
		
		return maKH;
	}
}
