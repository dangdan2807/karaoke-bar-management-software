package entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Lớp phòng cho thuê
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
public class Phong implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1883396537100026926L;

	@Id
	@Column(columnDefinition = "VARCHAR(5)")
	private String maPhong;

	@Column(columnDefinition = "INT default 0", nullable = false)
	private int tinhTrangP;

	@Column(columnDefinition = "NVARCHAR(100) default N''", nullable = false)
	private String viTri;

	@ManyToOne
	@JoinColumn(name = "maLP", nullable = false)
	private LoaiPhong loaiPhong;

	@OneToMany(mappedBy = "phong", fetch = FetchType.LAZY)
	private Set<HoaDon> hoaDon;

	/**
	 * Lấy mã phòng
	 * 
	 * @return {@code String} mã phòng
	 */
	public String getMaPhong() {
		return maPhong;
	}

	/**
	 * cập nhật mã phòng
	 * 
	 * @param maPhong {@code String} mã phòng
	 */
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	/**
	 * Lấy tình trạng phòng
	 * 
	 * @return {@code int} tình trạng phòng
	 */
	public int getTinhTrangP() {
		return tinhTrangP;
	}

	/**
	 * cập nhật tình trạng phòng
	 * 
	 * @param tinhTrangP {@code int} tình trạng phòng
	 */
	public void setTinhTrangP(int tinhTrangP) {
		this.tinhTrangP = tinhTrangP;
	}

	/**
	 * Lấy vị trí phòng
	 * 
	 * @return {@code String} vị trí phòng
	 */
	public String getViTri() {
		return viTri;
	}

	/**
	 * cập nhật vị trí phòng
	 * 
	 * @param viTri {@code String} vị trí phòng
	 */
	public void setViTri(String viTri) {
		this.viTri = viTri;
	}

	/**
	 * Lấy loại phòng
	 * 
	 * @return {@code LoaiPhong} loại phòng
	 */
	public LoaiPhong getLoaiPhong() {
		return loaiPhong;
	}

	/**
	 * cập nhật loại phòng
	 * 
	 * @param loaiPhong {@code LoaiPhong} loại phòng
	 */
	public void setLoaiPhong(LoaiPhong loaiPhong) {
		this.loaiPhong = loaiPhong;
	}

	/**
	 * Tạo 1 {@code Phong} với các tham số sau:
	 * 
	 * @param maPhong    {@code String} mã phòng
	 * @param tinhTrangP {@code int} tình trạng phòng
	 * @param viTri      {@code String} vị trí phòng
	 * @param loaiPhong  {@code LoaiPhong} loại phòng
	 */
	public Phong(String maPhong, int tinhTrangP, String viTri, LoaiPhong loaiPhong) {
		this.maPhong = maPhong;
		this.tinhTrangP = tinhTrangP;
		this.viTri = viTri;
		this.loaiPhong = loaiPhong;
	}

	/**
	 * Tạo 1 {@code Phong} với các tham số sau:
	 * 
	 * @param maPhong {@code String} mã phòng
	 */
	public Phong(String maPhong) {
		this.maPhong = maPhong;
	}

	/**
	 * Tạo 1 {@code Phong} không tham số
	 */
	public Phong() {
	}

	@Override
	public String toString() {
		return "Phong [loaiPhong=" + loaiPhong + ", maPhong=" + maPhong + ", tinhTrangP=" + tinhTrangP + ", viTri="
				+ viTri + "]";
	}

}