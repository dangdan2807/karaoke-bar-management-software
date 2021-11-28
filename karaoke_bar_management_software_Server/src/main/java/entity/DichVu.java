package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Lớp dịch vụ
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
public class DichVu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1920818359055571615L;

	@Id
	@Column(columnDefinition = "VARCHAR(6)")
	private String maDichVu;

	@Column(columnDefinition = "NVARCHAR(100) default N''", nullable = false)
	private String tenDichVu;

	@Column(columnDefinition = "MONEY default 0 CHECK(giaBan >= 0)", nullable = false)
	private Double giaBan;

	@Column(columnDefinition = "INT default 0 CHECK(soLuongTon >= 0)", nullable = false)
	private int soLuongTon;

	@ManyToOne
	@JoinColumn(name = "maLDV", nullable = false)
	private LoaiDichVu loaiDV;

	@OneToMany(mappedBy = "dichVu")
	private List<CTDichVu> dsCTDichVu;

	/**
	 * Lấy loại dịch vụ của dịch vụ
	 * 
	 * @return {@code LoaiDichVu}: loại dịch vụ của dịch vụ
	 */
	public LoaiDichVu getLoaiDV() {
		return loaiDV;
	}

	/**
	 * Cập nhật loại dịch vụ của dịch vụ
	 * 
	 * @param loaiDV {@code LoaiDichVu}: loại dịch vụ của dịch vụ
	 */
	public void setLoaiDV(LoaiDichVu loaiDV) {
		this.loaiDV = loaiDV;
	}

	/**
	 * Lấy mã dịch vụ
	 * 
	 * @return {@code String}: mã dịch vụ
	 */
	public String getMaDichVu() {
		return maDichVu;
	}

	/**
	 * Cập nhật mã dịch vụ
	 * 
	 * @param maDichVu {@code String}: mã dịch vụ
	 */
	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	/**
	 * Lấy tên dịch vụ
	 * 
	 * @return {@code String}: tên dịch vụ
	 */
	public String getTenDichVu() {
		return tenDichVu;
	}

	/**
	 * Cập nhật tên dịch vụ
	 * 
	 * @param tenDichVu {@code String}: tên dịch vụ
	 */
	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	/**
	 * Lấy giá bán dịch vụ
	 * 
	 * @return {@code Double}: giá bán dịch vụ
	 */
	public Double getGiaBan() {
		return giaBan;
	}

	/**
	 * Cập nhật giá bán của dịch vụ
	 * 
	 * @param giaBan {@code Double}: giá bán của dịch vụ
	 */
	public void setGiaBan(Double giaBan) {
		this.giaBan = giaBan;
	}

	/**
	 * Lấy số lượng tồn của dịch vụ
	 * 
	 * @return {@code int}: số lượng tồn của dịch vụ
	 */
	public int getSoLuongTon() {
		return soLuongTon;
	}

	/**
	 * Cập nhật số lượng tồn của dịch vụ
	 * 
	 * @param soLuongTon {@code int}: số lượng tồn của dịch vụ
	 */
	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	/**
	 * Tạo 1 {@code DichVu} với các tham số sau:
	 * 
	 * @param maDichVu   {@code String}: mã dịch vụ
	 * @param tenDichVu  {@code String}: tên dịch vụ
	 * @param giaBan     {@code Double}: giá bán dịch vụ
	 * @param soLuongTon {@code int}: số lượng tồn dịch vụ
	 * @param loaiDV     {@code LoaiDichVu}: loại dịch vụ của dịch vụ
	 */
	public DichVu(String maDichVu, String tenDichVu, Double giaBan, int soLuongTon, LoaiDichVu loaiDV) {
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.giaBan = giaBan;
		this.soLuongTon = soLuongTon;
		this.loaiDV = loaiDV;
	}

	/**
	 * Tạo 1 {@code DichVu} với các tham số sau:
	 * 
	 * @param maDichVu {@code String}: mã dịch vụ
	 */
	public DichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	/**
	 * Tạo 1 {@code DichVu} không tham số
	 */
	public DichVu() {
	}

	@Override
	public String toString() {
		return "DichVu [giaBan=" + giaBan + ", loaiDV=" + loaiDV + ", maDichVu=" + maDichVu + ", soLuongTon="
				+ soLuongTon + ", tenDichVu=" + tenDichVu + "]";
	}

}