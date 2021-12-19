package entity;


import java.io.Serializable;

import javax.persistence.*;

/**
 * Lớp tài khoản nhân viên
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
@Entity
public class TaiKhoan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4669986007368687239L;
	@Id
	@Column(columnDefinition = "VARCHAR(100)", unique = true)
	private String tenDangNhap;

	@Column(columnDefinition = "VARCHAR(100) default '123456'", nullable = false)
	private String matKhau;
	
	@Column(columnDefinition = "BIT default 1", nullable = false)
	private Boolean tinhTrangTK;

	
	@OneToOne
	@PrimaryKeyJoinColumn
	private NhanVien nhanVien;

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

	@Override
	public String toString() {
		return "TaiKhoan [matKhau=" + matKhau + ", tenDangNhap=" + tenDangNhap + ", tinhTrangTK=" + tinhTrangTK + "]";
	}

}