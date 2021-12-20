package DAO;

import java.sql.*;
import java.util.ArrayList;

import DAO.CTDichVuDAO;
import entity.CTDichVu;
import entity.DichVu;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code CTDichVu}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 13/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class CTDichVuDAO {
    private static CTDichVuDAO instance = new CTDichVuDAO();

    public static CTDichVuDAO getInstance() {
        if (instance == null)
            instance = new CTDichVuDAO();
        return instance;
    }

    /**
     * Lấy danh sách chi tiết dịch vụ theo mã phòng
     * 
     * @param roomId {@code String}: mã phòng cần tìm
     * @return {@code ArrayList<CTDichVu>}: danh sách chi tiết dịch vụ
     */
    public ArrayList<CTDichVu> getServiceDetailListByRoomId(String roomId) {
        ArrayList<CTDichVu> dataList = new ArrayList<CTDichVu>();
        String query = "{CALL USP_getServiceDetailListByRoomId( ? )}";
        Object[] params = { roomId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new CTDichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách chi tiết dịch vụ theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn cần tìm
     * @return {@code ArrayList<CTDichVu>}: danh sách chi tiết dịch vụ
     */
    public ArrayList<CTDichVu> getServiceDetailListByBillId(String billId) {
        ArrayList<CTDichVu> dataList = new ArrayList<CTDichVu>();
        String query = "{CALL USP_getServiceDetailListByBillId( ? )}";
        Object[] params = { billId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                dataList.add(new CTDichVu(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy ra 1 chi tiết dịch vụ dựa trên mã hóa đơn và mã dịch vụ
     * 
     * @param billId    {@code String}: mã hóa đơn cần tìm
     * @param serviceId {@code String}: mã dịch vụ cần tìm
     * @return {@code CTHoaDon}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code CTHoaDon}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     */
    public CTDichVu getServiceDetailByBillIdAndServiceId(String billId, String serviceId) {
        CTDichVu result = null;
        String query = "{CALL USP_getServiceDetailByBillIdAndServiceId( ? , ? )}";
        Object[] params = { billId, serviceId };
        ResultSet rs = DataProvider.getInstance().executeQuery(query, params);
        try {
            while (rs.next()) {
                result = new CTDichVu(rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thêm một chi tiết dịch vụ
     * 
     * @param serviceDetail {@code CTDichVu}: chi tiết dịch vụ cần thêm
     * @param quantity      {@code int}: số lượng đặt
     * @param billId        {@code String}: mã hóa đơn
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean updateServiceDetail(CTDichVu serviceDetail, int quantity, String billId) {
        String query = "{CALL USP_updateServiceDetail( ? , ? , ? , ? )}";
        DichVu service = serviceDetail.getDichVu();
        Object[] params = { service.getMaDichVu(), billId, quantity, serviceDetail.getDonGia() };
        Object obj = DataProvider.getInstance().executeNonQuery(query, params);
        int result = obj == null ? 0 : (int) obj;
        return result > 0;
    }
}
