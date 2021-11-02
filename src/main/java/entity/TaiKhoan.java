package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoan {
	private String tenDangNhap;
	private String matKhau;
	private Boolean tinhTrangTK;

	/**
	 * Lấy tên đăng nhập
	 * 
	 * @return {@code String} tên đăng nhập
	 */
	public String getTenDangNhap() {
		return tenDangNhap;
	}

	/**
	 * cập nhật tên đăng nhập
	 * 
	 * @param tenDangNhap {@code String} tên đăng nhập
	 */
	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	/**
	 * Lấy mật khẩu
	 * 
	 * @return {@code String} mật khẩu
	 */
	public String getMatKhau() {
		return matKhau;
	}

	/**
	 * Cập nhật mật khẩu
	 * 
	 * @param matKhau {@code String} mật khẩu
	 */
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	/**
	 * Lấy tình trạng tài khoản
	 * 
	 * @return {@code Boolean} tình trạng tài khoản
	 *         <ul>
	 *         <li>Có thể sử dụng thì trả về {@code true}</li>
	 *         <li>Đã bị vô hiệu hóa thì trả về {@code false}</li>
	 *         </ul>
	 */
	public Boolean getTinhTrangTK() {
		return tinhTrangTK;
	}

	/**
	 * Cập nhật tình trạng tài khoản
	 * 
	 * @param tinhTrangTK {@code Boolean} tình trạng tài khoản
	 */
	public void setTinhTrangTK(Boolean tinhTrangTK) {
		this.tinhTrangTK = tinhTrangTK;
	}

	/**
	 * Tạo 1 {@code TaiKhoan} với các tham số sau:
	 * 
	 * @param tenDangNhap {@code String} tên đăng nhập
	 * @param matKhau     {@code String} mật khẩu
	 * @param tinhTrangTK {@code Boolean} tình trạng tài khoản
	 */
	public TaiKhoan(String tenDangNhap, String matKhau, Boolean tinhTrangTK) {
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.tinhTrangTK = tinhTrangTK;
	}

	/**
	 * Tạo 1 {@code TaiKhoan} với các tham số sau:
	 * 
	 * @param tenDangNhap {@code String} tên đăng nhập
	 */
	public TaiKhoan(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	/**
	 * Tạo 1 {@code TaiKhoan} không tham số
	 */
	public TaiKhoan() {
	}

	/**
	 * Tạo 1 {@code TaiKhoan} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet} kết quả truy vấn
	 * @return {@code TaiKhoan} tài khoản
	 * @throws SQLException
	 */
	public TaiKhoan(ResultSet rs) throws SQLException {
		this(rs.getString("tenDangNhap"), rs.getString("matKhau"), rs.getBoolean("tinhTrangTK"));
	}

	@Override
	public String toString() {
		return "TaiKhoan [matKhau=" + matKhau + ", tenDangNhap=" + tenDangNhap + ", tinhTrangTK=" + tinhTrangTK + "]";
	}

}