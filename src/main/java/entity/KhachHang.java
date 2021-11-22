package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Lớp khách hàng
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
public class KhachHang {
	private String maKH;
	private String hoTen;
	private Date ngaySinh;
	private String cmnd;
	private String soDienThoai;
	private Boolean gioiTinh;

	/**
	 * Lấy mã khách hàng đặt phòng
	 * 
	 * @return {@code String} mã khách hàng
	 */
	public String getMaKH() {
		return maKH;
	}

	/**
	 * Cập nhật mã khách hàng đặt phòng
	 * 
	 * @param maKH {@code String} mã khách hàng
	 */
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	/**
	 * Lấy số chứng minh nhân dân
	 * 
	 * @return {@code String} số chứng minh nhân dân
	 */
	public String getCmnd() {
		return cmnd;
	}

	/**
	 * Cập nhật số chứng minh nhân dân
	 * 
	 * @param cmnd {@code String} số chứng minh nhân dân
	 */
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	/**
	 * Lấy họ tên khách hàng
	 * 
	 * @return {@code String} họ tên khách hàng
	 */
	public String getHoTen() {
		return hoTen;
	}

	/**
	 * Cập nhật họ tên khách hàng
	 * 
	 * @param hoTen {@code String} họ tên khách hàng
	 */
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	/**
	 * Lấy số điện thoại khách hàng
	 * 
	 * @return {@code String} số điện thoại khách hàng
	 */
	public String getSoDienThoai() {
		return soDienThoai;
	}

	/**
	 * Cập nhật số điện thoại khách hàng
	 * 
	 * @param soDienThoai {@code String} số điện thoại khách hàng
	 */
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	/**
	 * Lấy ngày sinh khách hàng
	 * 
	 * @return {@code Date} ngày sinh khách hàng
	 */
	public Date getNgaySinh() {
		return ngaySinh;
	}

	/**
	 * Cập nhật ngày sinh khách hàng
	 * 
	 * @param ngaySinh {@code Date} ngày sinh khách hàng
	 */
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	/**
	 * Lấy giới tính khách hàng
	 * 
	 * @return {@code Boolean} giới tính khách hàng
	 *         <ul>
	 *         <li>{@code true} thì là nữ</li>
	 *         <li>{@code false} thì là nam</li>
	 *         </ul>
	 */
	public Boolean getGioiTinh() {
		return gioiTinh;
	}

	/**
	 * Cập nhật giới tính khách hàng
	 * 
	 * @param gioiTinh {@code Boolean} giới tính khách hàng
	 *                 <ul>
	 *                 <li>{@code true} Nữ</li>
	 *                 <li>{@code false} Nam</li>
	 *                 </ul>
	 */
	public void setGioiTinh(Boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	/**
	 * Tạo 1 {@code KhachHang} với các tham số sau:
	 * 
	 * @param maKH        {@code String} mã khách hàng
	 * @param cmnd        {@code String} số chứng minh nhân dân
	 * @param hoTen       {@code String} họ tên khách hàng
	 * @param soDienThoai {@code String} số điện thoại khách hàng
	 * @param ngaySinh    {@code String} ngày sinh khách hàng
	 * @param gioiTinh    {@code Boolean} giới tính khách hàng
	 *                    <ul>
	 *                    <li>{@code true} Nữ</li>
	 *                    <li>{@code false} Nam</li>
	 *                    </ul>
	 */
	public KhachHang(String maKH, String cmnd, String hoTen, String soDienThoai, Date ngaySinh, Boolean gioiTinh) {
		this.maKH = maKH;
		this.cmnd = cmnd;
		this.hoTen = hoTen;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
	}

	/**
	 * Tạo 1 {@code KhachHang} với các tham số sau:
	 * 
	 * @param maKH {@code String} mã khách hàng
	 */
	public KhachHang(String maKH) {
		this.maKH = maKH;
	}

	/**
	 * Tạo 1 {@code KhachHang} không tham số
	 */
	public KhachHang() {
	}

	/**
	 * Tạo 1 {@code KhachHang} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet} kết quả truy vấn
	 * @throws SQLException
	 */
	public KhachHang(ResultSet rs) throws SQLException {
		this(rs.getString("maKH"), rs.getString("cmndKH"), rs.getString("hoTenKH"), rs.getString("sdtKH"),
				rs.getDate("ngaySinhKH"), rs.getBoolean("gioiTinhKH"));
	}

	@Override
	public String toString() {
		return "KhachHang [cmnd=" + cmnd + ", gioiTinh=" + gioiTinh + ", hoTen=" + hoTen + ", maKH=" + maKH
				+ ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + "]";
	}

}