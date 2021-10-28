package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.KhachHang;

public class KhachHangDAO {
    private static KhachHangDAO instance = new KhachHangDAO();
    public static int TABLE_WIDTH = 225;
    public static int TABLE_HEIGHT = 80;

    public static KhachHangDAO getInstance() {
        if (instance == null)
            instance = new KhachHangDAO();
        return instance;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng
     * 
     * @return {@code ArrayList<KhachHang>} : danh sách khách hàng
     */
    public ArrayList<KhachHang> getDSKhachHang() {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "SELECT * FROM dbo.KhachHang";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng có mã khách hàng phù hợp điều kiện
     * 
     * @param maKH {@code String}: mã khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getDSKhachHangByMaKH(String maKH) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getDSKhachHangByMaKH( ? )}";
        Object[] parameter = new Object[] { maKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng có tên khách hàng phù hợp điều kiện
     * 
     * @param tenKH {@code String}: tên khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getDSKhachHangByTenKH(String tenKH) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getDSKhachHangByTenKH( ? )}";
        Object[] parameter = new Object[] { tenKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng có số điện thoại của khách hàng phù hợp
     * điều kiện
     * 
     * @param sdtKH {@code String}: số điện thoại của khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getDSKhachHangBySDT(String sdtKH) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getDSKhachHangBySDT( ? )}";
        Object[] parameter = new Object[] { sdtKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra danh sách tất cả khách hàng chưa đặt phòng
     * 
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng chưa đặt phòng
     */
    public ArrayList<KhachHang> getDSKhachHangChuaDatPhong() {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getDSKhachHangChuaDatPhong()}";
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, null);
        try {
            while (rs.next()) {
                dataList.add(new KhachHang(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy thông tin khách hàng theo mã khách hàng
     * 
     * @param maKH {@code String}: mã khách hàng
     * @return {@code KhachHang}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code KhachHang}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public KhachHang getKhachHangByMaKH(String maKH) {
        KhachHang data = null;
        String query = "{CALL USP_getCustomerById( ? )}";
        Object[] parameter = new Object[] { maKH };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next()) {
                data = new KhachHang(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Thêm khách hàng mới
     * 
     * @param khachHang {@code KhachHang}: khách hàng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean themKhachHang(KhachHang khachHang) {
        String query = "INSERT INTO dbo.KhachHang (maKH, cmnd, hoTen, gioiTinh, soDienThoai, ngaySinh) "
                + "VALUES ( ? , ? , ? , ? , ? , ? )";
        Object[] parameter = new Object[] { khachHang.getMaKH(), khachHang.getCmnd(), khachHang.getHoTen(),
                khachHang.getGioiTinh(), khachHang.getSoDienThoai(), khachHang.getNgaySinh() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }

    /**
     * Lấy thông tin khách hàng được thêm mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã khách hàng}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     */
    public String getMaKHCuoiCung() {
        String query = "SELECT TOP 1 * FROM dbo.KhachHang ORDER BY maKH DESC";
        Object[] parameter = new Object[] {};
        Object obj = DataProvider.getInstance().ExecuteScalar(query, parameter);
        String result = obj != null ? result = obj.toString() : "";
        return result;
    }

    /**
     * Cập nhật thông tin khách hàng
     * 
     * @param khachHang {@code khachHang}: khách hàng cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu cập nhật thành công thì trả về {@code true}</li>
     *         <li>Nếu cập nhật thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean capNhatTTKhachHang(KhachHang khachHang) {
        String query = "Update dbo.KhachHang set cmnd = ? , hoTen = ? , gioiTinh = ? , soDienThoai = ? , "
                + "ngaySinh = ? , Where maKH = ?";
        Object[] parameter = new Object[] { khachHang.getCmnd(), khachHang.getHoTen(), khachHang.getGioiTinh(),
                khachHang.getSoDienThoai(), khachHang.getNgaySinh(), khachHang.getMaKH() };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}
