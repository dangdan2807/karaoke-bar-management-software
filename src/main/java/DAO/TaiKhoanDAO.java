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

    /**
     * Lấy danh sách tất cả tài khoản
     * 
     * @return {@code ArrayList<TaiKhoan>}: danh sách tài khoản
     */
    public ArrayList<TaiKhoan> getAccountList() {
        String query = "{CALL USP_getAccountList}";
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

    /**
     * đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @param password {@code String}: mật khẩu
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean login(String username, String password) {
        int count = 0;
        String query = "{CALL USP_Login ( ?, ? )}";
        Object[] parameter = new Object[] { username, password };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        try {
            if (rs.next()) {
                count = rs.getRow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * Lấy tài khoản dựa trên tên đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @return {@code TaiKhoan}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code TaiKhoan}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public TaiKhoan getAccountByUsername(String username) {
        String query = "{CALL USP_getAccountByUsername( ? )}";
        Object[] parameter = new Object[] { username };
        ResultSet rs = DataProvider.getInstance().ExecuteQuery(query, parameter);
        TaiKhoan account = null;
        try {
            while (rs.next()) {
                account = new TaiKhoan(rs);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * cập nhật thông tin nhân viên
     * 
     * @param account {@code NhanVien}: nhân viên cần cập nhật
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean updateAccount(TaiKhoan account) {
        String query = "{CALL UPS_updateAccount ( ? , ? , ? , ? )}";
        Object[] parameter = new Object[] {};
        Object obj = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        int result = obj != null ? result = (int) obj : 0;
        return result > 0;
    }
}
