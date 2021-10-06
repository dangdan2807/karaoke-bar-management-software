package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Phong {
	private String maPhong;
	private int tinhTrangP;
	private String viTri;
	public LoaiPhong loaiPhong;

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public int getTinhTrangP() {
		return tinhTrangP;
	}

	public void setTinhTrangP(int tinhTrangP) {
		this.tinhTrangP = tinhTrangP;
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

	public Phong(String maPhong, int tinhTrangP, String viTri, LoaiPhong loaiPhong) {
		this.maPhong = maPhong;
		this.tinhTrangP = tinhTrangP;
		this.viTri = viTri;
		this.loaiPhong = loaiPhong;
	}

	public Phong(String maPhong) {
		this.maPhong = maPhong;
	}

	public Phong() {
	}

	public Phong(ResultSet rs) throws SQLException {
		this(rs.getString("maPhong"), rs.getInt("tinhTrangP"), rs.getString("viTri"), new LoaiPhong(rs));
	}

	public Phong(ResultSet rs, int type) throws SQLException {
		this(rs.getString("maPhong"), rs.getInt("tinhTrangP"), rs.getString("viTri"), new LoaiPhong(rs.getString("maLP")));
	}

	@Override
	public String toString() {
		return "Phong [loaiPhong=" + loaiPhong + ", maPhong=" + maPhong + ", tinhTrangP=" + tinhTrangP + ", viTri="
				+ viTri + "]";
	}

}