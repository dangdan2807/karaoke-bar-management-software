package entity;

import java.sql.*;

/**
 * Lớp loại dịch vụ
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
public class LoaiDichVu {
	private String maLDV;
	private String tenLDV;

	/**
	 * Lấy mã loại dịch vụ
	 * 
	 * @return {@code LoaiDichVu}: loại dịch vụ được tìm thấy
	 */
	public String getMaLDV() {
		return maLDV;
	}

	/**
	 * Cập nhật mã loại dịch vụ
	 * 
	 * @param maLDV mã loại dịch vụ
	 */
	public void setMaLDV(String maLDV) {
		this.maLDV = maLDV;
	}

	/**
	 * Lấy tên loại dịch vụ
	 * 
	 * @return {@code LoaiDichVu}: loại dịch vụ được tìm thấy
	 */
	public String getTenLDV() {
		return tenLDV;
	}

	/**
	 * Cập nhật tên loại dịch vụ
	 * 
	 * @param tenLDV tên loại dịch vụ
	 */
	public void setTenLDV(String tenLDV) {
		this.tenLDV = tenLDV;
	}

	/**
	 * Tạo 1 {@code LoaiDichVu} với các tham số sau:
	 * 
	 * @param maLDV  {@code String} : mã loại dịch vụ
	 * @param tenLDV {@code String} : tên loại dịch vụ
	 */
	public LoaiDichVu(String maLDV, String tenLDV) {
		this.maLDV = maLDV;
		this.tenLDV = tenLDV;
	}

	/**
	 * Tạo 1 {@code LoaiDichVu} với các tham số sau:
	 * 
	 * @param maLDV {@code String} : mã loại dịch vụ
	 */
	public LoaiDichVu(String maLDV) {
		this.maLDV = maLDV;
	}

	/**
	 * Tạo 1 {@code LoaiDichVu} không tham số
	 */
	public LoaiDichVu() {
		this.maLDV = "";
		this.tenLDV = "";
	}

	/**
	 * Tạo 1 {@code LoaiDichVu} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet}: kết quả truy vấn
	 * @return {@code LoaiDichVu}: loại dịch vụ được tìm thấy
	 * @throws SQLException nếu có lỗi trong quá trình lấy dữ liệu
	 */
	public LoaiDichVu(ResultSet rs) throws SQLException {
		this(rs.getString("maLDV"), rs.getString("tenLDV"));
	}

	@Override
	public String toString() {
		return "LoaiDichVu [maLDV=" + maLDV + ", tenLDV=" + tenLDV + "]";
	}
}