package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DichVu {
	private String maDichVu;
	private String tenDichVu;
	private Double giaBan;
	private int soLuongTon;
	
	private LoaiDichVu loaiDV;

	public LoaiDichVu getLoaiDV() {
		return loaiDV;
	}

	public void setLoaiDV(LoaiDichVu loaiDV) {
		this.loaiDV = loaiDV;
	}

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

	public DichVu(String maDichVu, String tenDichVu, Double giaBan, int soLuongTon, LoaiDichVu loaiDV) {
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.giaBan = giaBan;
		this.soLuongTon = soLuongTon;
		this.loaiDV = loaiDV;
	}

	public DichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public DichVu() {
	}

	public DichVu(ResultSet rs) throws SQLException {
		this(rs.getString("maDichVu"), rs.getString("tenDichVu"), rs.getDouble("giaBan"), rs.getInt("soLuongTon"),
				new LoaiDichVu(rs));
	}

	public DichVu(ResultSet rs, int type) throws SQLException {
		this(rs.getString("maDichVu"), rs.getString("tenDichVu"), rs.getDouble("giaBan"), rs.getInt("soLuongTon"),
				new LoaiDichVu(rs.getString("maLDV")));
	}

	@Override
	public String toString() {
		return "DichVu [giaBan=" + giaBan + ", loaiDV=" + loaiDV + ", maDichVu=" + maDichVu + ", soLuongTon="
				+ soLuongTon + ", tenDichVu=" + tenDichVu + "]";
	}

}