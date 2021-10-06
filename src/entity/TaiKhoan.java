package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoan {
	private String tenDangNhap;
	private String matKhau;
	private Boolean tinhTrangTK;

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public Boolean getTinhTrangTK() {
		return tinhTrangTK;
	}

	public void setTinhTrangTK(Boolean tinhTrangTK) {
		this.tinhTrangTK = tinhTrangTK;
	}

	public TaiKhoan(String tenDangNhap, String matKhau, Boolean tinhTrangTK) {
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.tinhTrangTK = tinhTrangTK;
	}

	public TaiKhoan(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public TaiKhoan() {
	}

	public TaiKhoan(ResultSet rs) throws SQLException {
		this(rs.getString("tenDangNhap"), rs.getString("matKhau"), rs.getBoolean("tinhTrangTK"));
	}

	@Override
	public String toString() {
		return "TaiKhoan [matKhau=" + matKhau + ", tenDangNhap=" + tenDangNhap + ", tinhTrangTK=" + tinhTrangTK + "]";
	}

}