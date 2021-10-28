package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVien {
	private String maNhanVien;
	private String cmnd;
	private String hoTen;
	private Date ngaySinh;
	private String soDienThoai;
	private String chucVu;
	private Double mucLuong;
	private Boolean gioiTinh;
	private String trangThaiNV;

	private TaiKhoan taiKhoan;

	/**
	 * Lấy trạng thái nhân viên
	 * 
	 * @return {@code String} trạng thái nhân viên
	 */
	public String getTrangThaiNV() {
		return trangThaiNV;
	}

	/**
	 * cập nhật trạng thái nhân viên
	 * 
	 * @param trangThaiNV {@code String} trạng thái nhân viên
	 */
	public void setTrangThaiNV(String trangThaiNV) {
		this.trangThaiNV = trangThaiNV;
	}

	/**
	 * Lấy mã nhân viên
	 * 
	 * @return {@code String} mã nhân viên
	 */
	public String getMaNhanVien() {
		return maNhanVien;
	}

	/**
	 * Cập nhật mã nhân viên
	 * 
	 * @param maNhanVien {@code String} mã nhân viên
	 */
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	/**
	 * Lấy chứng minh nhân dân
	 * 
	 * @return {@code String} chứng minh nhân dân
	 */
	public String getCmnd() {
		return cmnd;
	}

	/**
	 * Cập nhật chứng minh nhân dân
	 * 
	 * @param cmnd {@code String} chứng minh nhân dân
	 */
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	/**
	 * Lấy họ tên
	 * 
	 * @return {@code String} họ tên
	 */
	public String getHoTen() {
		return hoTen;
	}

	/**
	 * Cập nhật họ tên
	 * 
	 * @param hoTen {@code String} họ tên
	 */
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	/**
	 * Lấy ngày sinh
	 * 
	 * @return {@code Date} ngày sinh
	 */
	public Date getNgaySinh() {
		return ngaySinh;
	}

	/**
	 * Cập nhật ngày sinh
	 * 
	 * @param ngaySinh {@code Date} ngày sinh
	 */
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	/**
	 * Lấy số điện thoại
	 * 
	 * @return {@code String} số điện thoại
	 */
	public String getSoDienThoai() {
		return soDienThoai;
	}

	/**
	 * Cập nhật số điện thoại
	 * 
	 * @param soDienThoai {@code String} số điện thoại
	 */
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	/**
	 * Lấy chức vụ
	 * 
	 * @return {@code String} chức vụ
	 */
	public String getChucVu() {
		return chucVu;
	}

	/**
	 * Cập nhật chức vụ
	 * 
	 * @param chucVu {@code String} chức vụ
	 */
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	/**
	 * Lấy mức lương
	 * 
	 * @return {@code Double} mức lương
	 */
	public Double getMucLuong() {
		return mucLuong;
	}

	/**
	 * Cập nhật mức lương
	 * 
	 * @param mucLuong {@code Double} mức lương
	 */
	public void setMucLuong(Double mucLuong) {
		this.mucLuong = mucLuong;
	}

	/**
	 * Lấy giới tính
	 * 
	 * @return {@code Boolean} giới tính
	 *         <ul>
	 *         <li>{@code true} thì là nữ</li>
	 *         <li>{@code false} thì là nam</li>
	 *         </ul>
	 */
	public Boolean getGioiTinh() {
		return gioiTinh;
	}

	/**
	 * Cập nhật giới tính
	 * 
	 * @param gioiTinh {@code Boolean} giới tính
	 *                 <ul>
	 *                 <li>{@code true} Nữ</li>
	 *                 <li>{@code false} Nam</li>
	 *                 </ul>
	 */
	public void setGioiTinh(Boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	/**
	 * Lấy tài khoản nhân viên
	 * 
	 * @return {@code String} tài khoản nhân viên
	 */
	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	/**
	 * Cập nhật tài khoản nhân viên
	 * 
	 * @param taiKhoan {@code String} tài khoản nhân viên
	 */
	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	/**
	 * Tạo 1 {@code NhanVien} từ các tham số sau:
	 * 
	 * @param maNhanVien  {@code String} mã nhân viên
	 * @param cmnd        {@code String} chứng minh nhân dân
	 * @param hoTen       {@code String} họ tên
	 * @param ngaySinh    {@code String} ngày sinh
	 * @param soDienThoai {@code String} số điện thoại
	 * @param chucVu      {@code String} chức vụ
	 * @param mucLuong    {@code Double} mức lương
	 * @param gioiTinh    {@code Boolean} giới tính
	 * @param trangThaiNV {@code Boolean} trạng thái nhân viên
	 *                    <ul>
	 *                    <li>{@code true} Nữ</li>
	 *                    <li>{@code false} Nam</li>
	 *                    </ul>
	 * @param taiKhoan    {@code TaiKhoan} tài khoản nhân viên
	 */
	public NhanVien(String maNhanVien, String cmnd, String hoTen, Date ngaySinh, String soDienThoai, String chucVu,
			Double mucLuong, Boolean gioiTinh, String trangThaiNV, TaiKhoan taiKhoan) {
		this.maNhanVien = maNhanVien;
		this.cmnd = cmnd;
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.soDienThoai = soDienThoai;
		this.chucVu = chucVu;
		this.mucLuong = mucLuong;
		this.gioiTinh = gioiTinh;
		this.trangThaiNV = trangThaiNV;
		this.taiKhoan = taiKhoan;
	}

	/**
	 * Tạo 1 {@code NhanVien} từ các tham số sau:
	 * 
	 * @param maNhanVien {@code String} mã nhân viên
	 */
	public NhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	/**
	 * Tạo 1 {@code NhanVien} không tham số
	 */
	public NhanVien() {
	}

	/**
	 * Tạo 1 {@code NhanVien} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet} kết quả truy vấn
	 * @throws SQLException {@code SQLException}: lỗi truy vấn
	 */
	public NhanVien(ResultSet rs) throws SQLException {
		this(rs.getString("maNhanVien"), rs.getString("cmndNV"), rs.getString("hoTenNV"), rs.getDate("ngaySinhNV"),
				rs.getString("sdtNV"), rs.getString("chucVu"), rs.getDouble("mucLuong"), rs.getBoolean("gioiTinhNV"),
				rs.getString("trangThaiNV"), new TaiKhoan(rs));
	}

	@Override
	public String toString() {
		return "NhanVien [chucVu=" + chucVu + ", cmnd=" + cmnd + ", gioiTinh=" + gioiTinh + ", hoTen=" + hoTen
				+ ", maNhanVien=" + maNhanVien + ", mucLuong=" + mucLuong + ", ngaySinh=" + ngaySinh + ", soDienThoai="
				+ soDienThoai + ", trangThaiNV=" + trangThaiNV + ", taiKhoan=" + taiKhoan + "]";
	}

}