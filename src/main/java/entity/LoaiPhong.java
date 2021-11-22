package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Lớp loại phòng
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
public class LoaiPhong {
	private String maLP;
	private String tenLP;
	private int sucChua;
	private Double giaTien;

	/**
	 * Lấy mã loại phòng
	 * 
	 * @return {@code String} mã loại phòng
	 */
	public String getMaLP() {
		return maLP;
	}

	/**
	 * Cập nhật mã loại phòng
	 * 
	 * @param maLP {@code String} mã loại phòng
	 */
	public void setMaLP(String maLP) {
		this.maLP = maLP;
	}

	/**
	 * Lấy tên loại phòng
	 * 
	 * @return {@code String} tên loại phòng
	 */
	public String getTenLP() {
		return tenLP;
	}

	/**
	 * Cập nhật tên loại phòng
	 * 
	 * @param tenLP {@code String} tên loại phòng
	 */
	public void setTenLP(String tenLP) {
		this.tenLP = tenLP;
	}

	/**
	 * Lấy sức chứa
	 * 
	 * @return {@code int} sức chứa
	 */
	public int getSucChua() {
		return sucChua;
	}

	/**
	 * Cập nhật sức chứa
	 * 
	 * @param sucChua {@code int} sức chứa
	 */
	public void setSucChua(int sucChua) {
		this.sucChua = sucChua;
	}

	/**
	 * Lấy giá tiền phòng
	 * 
	 * @return {@code Double} giá tiền phòng
	 */
	public Double getGiaTien() {
		return giaTien;
	}

	/**
	 * Cập nhật giá tiền phòng
	 * 
	 * @param giaTien {@code Double} giá tiền phòng
	 */
	public void setGiaTien(Double giaTien) {
		this.giaTien = giaTien;
	}

	/**
	 * Tạo 1 loại phòng từ các tham số sau:
	 * 
	 * @param maLP    {@code String} mã loại phòng
	 * @param tenLP   {@code String} tên loại phòng
	 * @param sucChua {@code int} sức chứa
	 * @param giaTien {@code Double} giá tiền phòng
	 */
	public LoaiPhong(String maLP, String tenLP, int sucChua, Double giaTien) {
		this.maLP = maLP;
		this.tenLP = tenLP;
		this.sucChua = sucChua;
		this.giaTien = giaTien;
	}

	/**
	 * Tạo 1 loại phòng từ các tham số sau:
	 * 
	 * @param maLP {@code String} mã loại phòng
	 */
	public LoaiPhong(String maLP) {
		this.maLP = maLP;
	}

	/**
	 * Tạo 1 loại phòng không tham số
	 */
	public LoaiPhong() {
	}

	/**
	 * Tạo 1 loại {@code Phong} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet}
	 * @throws SQLException
	 */
	public LoaiPhong(ResultSet rs) throws SQLException {
		this(rs.getString("maLP"), rs.getString("tenLP"), rs.getInt("sucChua"), rs.getDouble("giaTien"));
	}

	@Override
	public String toString() {
		return "LoaiPhong [giaTien=" + giaTien + ", maLP=" + maLP + ", sucChua=" + sucChua + ", tenLP=" + tenLP + "]";
	}

}