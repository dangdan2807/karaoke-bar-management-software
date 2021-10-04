package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DichVu {
	private String maDichVu;
	private String tenDichVu;
	private Double giaBan;
	private int soLuongTon;

	public String getMaDichVu() {
		return maDichVu;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public String getTenDichVu() {
		return tenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	public Double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(Double giaBan) {
		this.giaBan = giaBan;
	}

	public int getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	public DichVu(String maDichVu, String tenDichVu, Double giaBan, int soLuongTon) {
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.giaBan = giaBan;
		this.soLuongTon = soLuongTon;
	}

	public DichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public DichVu() {
	}

	public DichVu(ResultSet rs) throws SQLException {
		this(rs.getString("maDichVu"), rs.getString("tenDichVu"), rs.getDouble("giaBan"), rs.getInt("soLuongTon"));
	}

	@Override
	public String toString() {
		return "DichVu [giaBan=" + giaBan + ", maDichVu=" + maDichVu + ", soLuongTon=" + soLuongTon + ", tenDichVu="
				+ tenDichVu + "]";
	}

}