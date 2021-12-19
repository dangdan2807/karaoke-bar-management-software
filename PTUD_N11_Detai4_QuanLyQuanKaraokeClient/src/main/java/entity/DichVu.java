package entity;

import java.sql.*;

/**
 * Lớp dịch vụ
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
public class DichVu {
	private String maDichVu;
	private String tenDichVu;
	private Double giaBan;
	private int soLuongTon;
	
	private LoaiDichVu loaiDV;

	/**
	 * Lấy loại dịch vụ của dịch vụ
	 * 
	 * @return {@code LoaiDichVu}: loại dịch vụ của dịch vụ
	 */
	public LoaiDichVu getLoaiDV() {
		return loaiDV;
	}

	/**
	 * Cập nhật loại dịch vụ của dịch vụ
	 * 
	 * @param loaiDV {@code LoaiDichVu}: loại dịch vụ của dịch vụ
	 */
	public void setLoaiDV(LoaiDichVu loaiDV) {
		this.loaiDV = loaiDV;
	}

	/**
	 * Lấy mã dịch vụ
	 * 
	 * @return {@code String}: mã dịch vụ
	 */
	public String getMaDichVu() {
		return maDichVu;
	}

	/**
	 * Cập nhật mã dịch vụ
	 * 
	 * @param maDichVu {@code String}: mã dịch vụ
	 */
	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	/**
	 * Lấy tên dịch vụ
	 * 
	 * @return {@code String}: tên dịch vụ
	 */
	public String getTenDichVu() {
		return tenDichVu;
	}

	/**
	 * Cập nhật tên dịch vụ
	 * 
	 * @param tenDichVu {@code String}: tên dịch vụ
	 */
	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	/**
	 * Lấy giá bán dịch vụ
	 * 
	 * @return {@code Double}: giá bán dịch vụ
	 */
	public Double getGiaBan() {
		return giaBan;
	}

	/**
	 * Cập nhật giá bán của dịch vụ
	 * 
	 * @param giaBan {@code Double}: giá bán của dịch vụ
	 */
	public void setGiaBan(Double giaBan) {
		this.giaBan = giaBan;
	}

	/**
	 * Lấy số lượng tồn của dịch vụ
	 * 
	 * @return {@code int}: số lượng tồn của dịch vụ
	 */
	public int getSoLuongTon() {
		return soLuongTon;
	}

	/**
	 * Cập nhật số lượng tồn của dịch vụ
	 * 
	 * @param soLuongTon {@code int}: số lượng tồn của dịch vụ
	 */
	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	/**
	 * Tạo 1 {@code DichVu} với các tham số sau:
	 * 
	 * @param maDichVu   {@code String}: mã dịch vụ
	 * @param tenDichVu  {@code String}: tên dịch vụ
	 * @param giaBan     {@code Double}: giá bán dịch vụ
	 * @param soLuongTon {@code int}: số lượng tồn dịch vụ
	 * @param loaiDV     {@code LoaiDichVu}: loại dịch vụ của dịch vụ
	 */
	public DichVu(String maDichVu, String tenDichVu, Double giaBan, int soLuongTon, LoaiDichVu loaiDV) {
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.giaBan = giaBan;
		this.soLuongTon = soLuongTon;
		this.loaiDV = loaiDV;
	}

	/**
	 * Tạo 1 {@code DichVu} với các tham số sau:
	 * 
	 * @param maDichVu {@code String}: mã dịch vụ
	 */
	public DichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	/**
	 * Tạo 1 {@code DichVu} không tham số
	 */
	public DichVu() {
	}

	/**
	 * Tạo 1 {@code DichVu} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet}: kết quả truy vấn
	 * @throws SQLException {@code SQLException}: lỗi truy vấn
	 */
	public DichVu(ResultSet rs) throws SQLException {
		this(rs.getString("maDichVu"), rs.getString("tenDichVu"), rs.getDouble("giaBan"), rs.getInt("soLuongTon"),
				new LoaiDichVu(rs));
	}

	@Override
	public String toString() {
		return "DichVu [giaBan=" + giaBan + ", loaiDV=" + loaiDV + ", maDichVu=" + maDichVu + ", soLuongTon="
				+ soLuongTon + ", tenDichVu=" + tenDichVu + "]";
	}

}