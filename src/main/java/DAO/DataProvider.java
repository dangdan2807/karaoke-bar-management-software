package DAO;

import java.sql.*;
import connectDB.ConnectDB;

/**
 * Sử dụng jdbc để thao tác với database dựa trên câu query và mảng object được
 * truyền vào
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 15/06/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: Thêm mô tả cho lớp và các hàm
 */
public class DataProvider {
    private static DataProvider instance = new DataProvider();
    private static ConnectDB db = ConnectDB.getInstance();

    /**
     * Sử dụng kiến trúc singleton để tạo ra đối tượng {@code DataProvider} duy nhất
     * 
     * @return {@code DataProvider}: đối tượng DataProvider
     */
    public static DataProvider getInstance() {
        if (instance == null)
            instance = new DataProvider();
        return instance;
    }

    /**
     * Dùng để truy vấn select
     * 
     * @param query     {@code String}: câu truy vấn
     * @param parameter {@code Object[]}: các giá trị được truyền vào dấu truy vấn
     *                  tại các dấu {@code ?}
     * @return {@code ResultSet} trả về 1 {@code ResultSet} chứa thông tin của câu
     *         truy vấn truyền vào
     */
    public ResultSet ExecuteQuery(String query, Object[] parameter) {
        PreparedStatement stmt = null;
        ResultSet dataList = null;
        Connection con = null;
        try {
            db.connect();
            con = ConnectDB.getConnection();
            stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (parameter != null) {
                String[] listParams = query.split(" ");
                int i = 1;
                for (String item : listParams) {
                    if (item.contains("?")) {
                        stmt.setObject(i, parameter[i - 1]);
                        i++;
                    }
                }
            }
            dataList = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Dùng khi thực hiện các câu truy vấn insert, delete, update hoặc các câu query
     * trả về số lượng dòng thực
     * 
     * @param query     {@code String}: câu truy vấn
     * @param parameter {@code Object[]}: các giá trị được truyền vào dấu truy vấn
     *                  tại các dấu {@code ?}
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>{@code true} truy vấn thành công</li>
     *         <li>{@code false} truy vấn thất bại</li>
     *         </ul>
     */
    public int ExecuteNonQuery(String query, Object[] parameter) {
        int data = 0;
        PreparedStatement stmt = null;
        Connection con = null;
        try {
            db.connect();
            con = ConnectDB.getConnection();
            stmt = con.prepareStatement(query);
            if (parameter != null) {
                String[] listParams = query.split(" ");
                int i = 1;
                for (String item : listParams) {
                    if (item.contains("?")) {
                        stmt.setObject(i, parameter[i - 1]);
                        i++;
                    }
                }
            }
            data = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * Dùng để đếm, ... hoặc lấy giá trị của hàng đầu của cột đầu tiên của kế quả
     * câu truy vấn
     * 
     * @param query     {@code String}: câu truy vấn
     * @param parameter {@code Object[]}: các giá trị được truyền vào dấu truy vấn
     *                  tại các dấu {@code ?}
     * @return {@code Object}: trả về 1 Object chứa giá trị cần lấy ra
     */
    public Object ExecuteScalar(String query, Object[] parameter) {
        Object data = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection con = null;
        try {
            db.connect();
            con = ConnectDB.getConnection();
            stmt = con.prepareStatement(query);
            if (parameter != null) {
                String[] listParams = query.split(" ");
                int i = 1;
                for (String item : listParams) {
                    if (item.contains("?")) {
                        stmt.setObject(i, parameter[i - 1]);
                        i++;
                    }
                }
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                data = rs.getObject(1);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
