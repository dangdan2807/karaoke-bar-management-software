package connectDB;

import java.sql.*;

/**
 * Lớp hỗ trợ kết nối database
 * 
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 04/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: Thêm mô tả cho lớp và hàm (java doc)
 */
public class ConnectDB {
    public static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    /**
     * Sử dụng kiến trúc singleton để tạo ra đối tượng {@code ConnectDB} duy nhất
     * 
     * @return {@code ConnectDB}: đối tượng ConnectDB
     */
    public static ConnectDB getInstance() {
        if (instance == null)
            instance = new ConnectDB();
        return instance;
    }

    /**
     * Kết nối database
     * 
     * @throws SQLException
     */
    public void connect() throws SQLException {
        String severName = "localhost";
        String databaseName = "QuanLyQuanKaraoke";
        String username = "sa";
        String password = "123456";
        String url = "jdbc:sqlserver://" + severName + ":1433;databaseName=" + databaseName;
        con = DriverManager.getConnection(url, username, password);
    }

    /**
     * Ngắt kết nối database
     */
    public void disconnect() {
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    /**
     * Lấy ra Connection đang kết nối đến database
     * 
     * @return Connection
     */
    public static Connection getConnection() {
        return con;
    }
}