package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Phong {
	private String maPhong;
	private int tinhTrangP;
	private String viTri;

	private LoaiPhong loaiPhong;

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

	/**
	 * Tạo 1 {@code Phong} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet} kết quả truy vấn
	 * @throws SQLException {@code SQLException}: lỗi truy vấn
	 */
	public Phong(ResultSet rs) throws SQLException {
		this(rs.getString("maPhong"), rs.getInt("tinhTrangP"), rs.getString("viTri"), new LoaiPhong(rs));
	}

	@Override
	public String toString() {
		return "Phong [loaiPhong=" + loaiPhong + ", maPhong=" + maPhong + ", tinhTrangP=" + tinhTrangP + ", viTri="
				+ viTri + "]";
	}

}