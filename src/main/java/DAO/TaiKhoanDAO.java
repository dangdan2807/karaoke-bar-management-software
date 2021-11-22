package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.TaiKhoan;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code TaiKhoan}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 02/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public class TaiKhoanDAO {
    private static TaiKhoanDAO instance = new TaiKhoanDAO();

    public static TaiKhoanDAO getInstance() {
        if (instance == null)
            instance = new TaiKhoanDAO();
        return instance;
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
}
