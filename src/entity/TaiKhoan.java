package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoan {
	private String tenDangNhap;
	private String matKhau;
	private Boolean tinhTrang;

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

	public Boolean getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(Boolean tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public TaiKhoan(String tenDangNhap, String matKhau, Boolean tinhTrang) {
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.tinhTrang = tinhTrang;
	}

	public TaiKhoan(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public TaiKhoan() {
	}

	public TaiKhoan(ResultSet rs) throws SQLException {
		this(rs.getString("tenDangNhap"), rs.getString("matKhau"), rs.getBoolean("tinhTrang"));
	}

	@Override
	public String toString() {
		return "TaiKhoan [matKhau=" + matKhau + ", tenDangNhap=" + tenDangNhap + ", tinhTrang=" + tinhTrang + "]";
	}

}