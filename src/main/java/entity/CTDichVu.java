package entity;

import java.io.Serializable;

import javax.persistence.*;

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
@Entity
@IdClass(CTDichVuPK.class)
public class CTDichVu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3647809579675358546L;

	@Id
	@ManyToOne
	@JoinColumn(name = "maDichVu", columnDefinition = "VARCHAR(6)")
	private DichVu dichVu;

	@Id
	@ManyToOne
	@JoinColumn(name = "maHoaDon", columnDefinition = "VARCHAR(15)")
	private HoaDon hoaDon;

	@Column(columnDefinition = "INT DEFAULT 1 CHECK(soLuongDat > 0)", nullable = false)
	private int soLuongDat;

	@Column(columnDefinition = "MONEY DEFAULT 0 CHECK(donGia >= 0)", nullable = false)
	private double donGia;

	/**
	 * Lây đơn giá bán ở thời điểm tạo hóa đơn
	 * 
	 * @return {@code double}: đơn giá bán
	 */
	public double getDonGia() {
		return donGia;
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
	public CTDichVu(int soLuongDat, double donGia, DichVu dichVu) {
		this.soLuongDat = soLuongDat;
		this.donGia = donGia;
		this.dichVu = dichVu;
	}

	/**
	 * Tạo 1 {@code CTDichVu} với không tham số
	 */
	public CTDichVu() {
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
		return donGia * soLuongDat;
	}
}