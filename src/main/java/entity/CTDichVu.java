package entity;

import java.sql.*;

/**
 * Lớp chi tiết dịch vụ
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
public class CTDichVu {
	private HoaDon hoaDon;
	private DichVu dichVu;

	private int soLuongDat;
	private double donGia;

	/**
	 * Lây đơn giá bán ở thời điểm tạo hóa đơn
	 * 
	 * @return {@code double}: đơn giá bán
	 */
	public double getDonGia() {
		return donGia;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	/**
	 * Cập nhật đơn giá bán lúc tạo hóa đơn
	 * 
	 * @param donGia {@code double}: đơn giá bán
	 */
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	/**
	 * Lấy số lượng dịch vụ đã đặt
	 * 
	 * @return {@code int}: Số lượng dịch vụ đã đặt
	 */
	public int getSoLuongDat() {
		return soLuongDat;
	}

	/**
	 * Thay đổi số lượng dịch vụ đã đặt
	 * 
	 * @param soLuongDat {@code int}: Số lượng dịch vụ đã đặt cần cập nhật
	 */
	public void setSoLuongDat(int soLuongDat) {
		this.soLuongDat = soLuongDat;
	}

	/**
	 * Lấy ra dịch vụ đã đặt
	 * 
	 * @return {@code DichVu}: Dịch vụ đã đặt
	 */
	public DichVu getDichVu() {
		return dichVu;
	}

	/**
	 * Cập nhật dịch vụ đã đặt
	 * 
	 * @param dichVu {@code DichVu}: Dịch vụ đã đặt cần cập nhật
	 */
	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}

	/**
	 * Tạo 1 {@code CTDichVu} với các tham số: số lượng đặt và dịch vụ
	 * 
	 * @param soLuongDat {@code int}: Số lượng đặt
	 * @param donGia     {@code double}: Giá dịch vụ tại thời điểm bán
	 * @param dichVu     {@code DichVu}: Dịch vụ đã đặt
	 */
	public CTDichVu(int soLuongDat, double donGia, DichVu dichVu, HoaDon hoaDon) {
		this.soLuongDat = soLuongDat;
		this.donGia = donGia;
		this.dichVu = dichVu;
	}

	/**
	 * Tạo 1 {@code CTDichVu} với không tham số
	 */
	public CTDichVu() {
	}

	/**
	 * Tạo 1 {@code CTDichVu} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet}: Kết quả truy vấn
	 * @throws SQLException {@code SQLException}: lỗi truy vấn
	 */
	public CTDichVu(ResultSet rs) throws SQLException {
		this(rs.getInt("soLuongDat"), rs.getDouble("donGia"), new DichVu(rs), new HoaDon(rs));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dichVu == null) ? 0 : dichVu.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CTDichVu other = (CTDichVu) obj;
		if (dichVu == null) {
			if (other.dichVu != null)
				return false;
		} else if (!dichVu.equals(other.dichVu))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CTDichVu [dichVu=" + dichVu + ", donGia=" + donGia + ", soLuongDat=" + soLuongDat + "]";
	}

	/**
	 * Tính tiền dịch vụ đã đặt
	 * 
	 * @return {@code double}: Tiền dịch vụ đã đặt
	 */
	public Double tinhTienDichVu() {
		return donGia * soLuongDat;
	}
}