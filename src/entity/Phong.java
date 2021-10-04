package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Phong {
	private String maPhong;
	private int tinhTrang;
	private String viTri;
	public LoaiPhong loaiPhong;

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public int getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(int tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public String getViTri() {
		return viTri;
	}

	public void setViTri(String viTri) {
		this.viTri = viTri;
	}

	public LoaiPhong getLoaiPhong() {
		return loaiPhong;
	}

	public void setLoaiPhong(LoaiPhong loaiPhong) {
		this.loaiPhong = loaiPhong;
	}

	public Phong(String maPhong, int tinhTrang, String viTri, LoaiPhong loaiPhong) {
		this.maPhong = maPhong;
		this.tinhTrang = tinhTrang;
		this.viTri = viTri;
		this.loaiPhong = loaiPhong;
	}

	public Phong(String maPhong) {
		this.maPhong = maPhong;
	}

	public Phong() {
	}

	public Phong(ResultSet rs) throws SQLException {
		this(rs.getString("maPhong"), rs.getInt("tinhTrang"), rs.getString("viTri"), new LoaiPhong(rs));
	}

	@Override
	public String toString() {
		return "Phong [loaiPhong=" + loaiPhong + ", maPhong=" + maPhong + ", tinhTrang=" + tinhTrang + ", viTri="
				+ viTri + "]";
	}

}