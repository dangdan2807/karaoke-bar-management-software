package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CTDichVu {
	private int soLuongDat;

	private DichVu dichVu;

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
	 * @param dichVu     {@code DichVu}: Dịch vụ đã đặt
	 */
	public CTDichVu(int soLuongDat, DichVu dichVu) {
		this.soLuongDat = soLuongDat;
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
		this(rs.getInt("soLuongDat"), new DichVu(rs));
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
		return "CTDichVu [dichVu=" + dichVu + ", soLuongDat=" + soLuongDat + "]";
	}

	/**
	 * Tính tiền dịch vụ đã đặt
	 * 
	 * @return {@code double}: Tiền dịch vụ đã đặt
	 */
	public Double tinhTienDichVu() {
		return dichVu.getGiaBan() * soLuongDat;
	}
}