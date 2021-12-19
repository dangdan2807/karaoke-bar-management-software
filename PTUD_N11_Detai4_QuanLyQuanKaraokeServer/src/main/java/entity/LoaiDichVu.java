package entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Lớp loại dịch vụ
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
public class LoaiDichVu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2194057142602248712L;
	@Id
	@Column(columnDefinition = "VARCHAR(6)")
	private String maLDV;
	@Column(columnDefinition = "NVARCHAR(100) default N''", nullable = false)
	private String tenLDV;

	@OneToMany(mappedBy = "loaiDV", fetch = FetchType.LAZY)
	private Set<DichVu> dichVu;
	
	/**
	 * Lấy mã loại dịch vụ
	 * 
	 * @return {@code LoaiDichVu}: loại dịch vụ được tìm thấy
	 */
	public String getMaLDV() {
		return maLDV;
	}

	/**
	 * Cập nhật mã loại dịch vụ
	 * 
	 * @param maLDV mã loại dịch vụ
	 */
	public void setMaLDV(String maLDV) {
		this.maLDV = maLDV;
	}

	/**
	 * Lấy tên loại dịch vụ
	 * 
	 * @return {@code LoaiDichVu}: loại dịch vụ được tìm thấy
	 */
	public String getTenLDV() {
		return tenLDV;
	}

	/**
	 * Cập nhật tên loại dịch vụ
	 * 
	 * @param tenLDV tên loại dịch vụ
	 */
	public void setTenLDV(String tenLDV) {
		this.tenLDV = tenLDV;
	}

	/**
	 * Tạo 1 {@code LoaiDichVu} với các tham số sau:
	 * 
	 * @param maLDV  {@code String} : mã loại dịch vụ
	 * @param tenLDV {@code String} : tên loại dịch vụ
	 */
	public LoaiDichVu(String maLDV, String tenLDV) {
		this.maLDV = maLDV;
		this.tenLDV = tenLDV;
	}

	/**
	 * Tạo 1 {@code LoaiDichVu} với các tham số sau:
	 * 
	 * @param maLDV {@code String} : mã loại dịch vụ
	 */
	public LoaiDichVu(String maLDV) {
		this.maLDV = maLDV;
	}

	/**
	 * Tạo 1 {@code LoaiDichVu} không tham số
	 */
	public LoaiDichVu() {
	}

	@Override
	public String toString() {
		return "LoaiDichVu [maLDV=" + maLDV + ", tenLDV=" + tenLDV + "]";
	}
}