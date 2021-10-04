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
	private String trangThai;
	private TaiKhoan taiKhoan;

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public Double getMucLuong() {
		return mucLuong;
	}

	public void setMucLuong(Double mucLuong) {
		this.mucLuong = mucLuong;
	}

	public Boolean getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(Boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public NhanVien(String maNhanVien, String cmnd, String hoTen, Date ngaySinh, String soDienThoai, String chucVu,
			Double mucLuong, Boolean gioiTinh, String trangThai, TaiKhoan taiKhoan) {
		this.maNhanVien = maNhanVien;
		this.cmnd = cmnd;
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.soDienThoai = soDienThoai;
		this.chucVu = chucVu;
		this.mucLuong = mucLuong;
		this.gioiTinh = gioiTinh;
		this.trangThai = trangThai;
		this.taiKhoan = taiKhoan;
	}

	public NhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public NhanVien() {
	}

	public NhanVien(ResultSet rs) throws SQLException {
		this(rs.getString("maNhanVien"), rs.getString("cmnd"), rs.getString("hoTen"), rs.getDate("ngaySinh"),
				rs.getString("soDienThoai"), rs.getString("chucVu"), rs.getDouble("mucLuong"),
				rs.getBoolean("gioiTinh"), rs.getString("trangThai"), new TaiKhoan(rs));
	}

	@Override
	public String toString() {
		return "NhanVien [chucVu=" + chucVu + ", cmnd=" + cmnd + ", gioiTinh=" + gioiTinh + ", hoTen=" + hoTen
				+ ", maNhanVien=" + maNhanVien + ", mucLuong=" + mucLuong + ", ngaySinh=" + ngaySinh + ", soDienThoai="
				+ soDienThoai + ", trangThai=" + trangThai + ", taiKhoan=" + taiKhoan + "]";
	}

}