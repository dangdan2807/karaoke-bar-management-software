package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KhachHang {
	private String maKH;
	private String cmnd;
	private String hoTen;
	private String soDienThoai;
	private Date ngaySinh;
	private Boolean gioiTinh;

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
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

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public Boolean getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(Boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public KhachHang(String maKH, String cmnd, String hoTen, String soDienThoai, Date ngaySinh, Boolean gioiTinh) {
		this.maKH = maKH;
		this.cmnd = cmnd;
		this.hoTen = hoTen;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
	}

	public KhachHang(String maKH) {
		this.maKH = maKH;
	}

	public KhachHang() {
	}

	public KhachHang(ResultSet rs) throws SQLException {
		this(rs.getString("maKH"), rs.getString("cmnd"), rs.getString("hoTen"), rs.getString("soDienThoai"),
				rs.getDate("ngaySinh"), rs.getBoolean("gioiTinh"));
	}

	@Override
	public String toString() {
		return "KhachHang [cmnd=" + cmnd + ", gioiTinh=" + gioiTinh + ", hoTen=" + hoTen + ", maKH=" + maKH
				+ ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + "]";
	}

}