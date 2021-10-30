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
    public ArrayList<KhachHang> getCustomerList() {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerList}";
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
     * Lấy danh sách tất cả khách hàng dựa trên mã khách hàng
     * 
     * @param customerId {@code String}: mã khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListById(String customerId) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListById( ? )}";
        Object[] parameter = new Object[] { customerId };
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
     * Lấy danh sách tất cả khách hàng có tên khách hàng phù hợp điều kiện
     * 
     * @param customerName {@code String}: tên khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByName(String customerName) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByName( ? )}";
        Object[] parameter = new Object[] { customerName };
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
     * Lấy danh sách tất cả khách hàng có số điện thoại phù hợp điều kiện
     * 
     * @param phoneNumber {@code String}: số điện thoại của khách hàng
     * @return {@code ArrayList<KhachHang>}: danh sách khách hàng
     */
    public ArrayList<KhachHang> getCustomerListByPhoneNumber(String phoneNumber) {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListByPhoneNumber( ? )}";
        Object[] parameter = new Object[] { phoneNumber };
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
    public ArrayList<KhachHang> getCustomerListUnBooked() {
        ArrayList<KhachHang> dataList = new ArrayList<KhachHang>();
        String query = "{CALL USP_getCustomerListUnBooked()}";
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
     * @param customerId {@code String}: mã khách hàng
     * @return {@code KhachHang}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code KhachHang}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public KhachHang getCustomerById(String customerId) {
        KhachHang data = null;
        String query = "{CALL USP_getCustomerById( ? )}";
        Object[] parameter = new Object[] { customerId };
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
     * @param customer {@code KhachHang}: khách hàng cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean insertCustomer(KhachHang customer) {
        String query = "{CALL USP_insertCustomer( ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { customer.getMaKH(), customer.getCmnd(), customer.getHoTen(),
                customer.getGioiTinh(), customer.getSoDienThoai(), customer.getNgaySinh() };
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
    public String getLastCustomerId() {
        String query = "{CALL USP_getLastCustomerId}";
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
    public boolean updateCustomerInfo(KhachHang khachHang) {
        String query = "{CALL USP_updateCustomerInfo( ? , ? , ? , ? , ? , ? )}";
        Object[] parameter = new Object[] { khachHang.getMaKH(), khachHang.getCmnd(), khachHang.getHoTen(),
                khachHang.getGioiTinh(), khachHang.getSoDienThoai(), khachHang.getNgaySinh(), };
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}
