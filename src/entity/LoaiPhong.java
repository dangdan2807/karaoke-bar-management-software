package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoaiPhong {
	private String maLP;
	private String tenLP;
	private int sucChua;
	private Double giaTien;

	public String getMaLP() {
		return maLP;
	}

	public void setMaLP(String maLP) {
		this.maLP = maLP;
	}

	public String getTenLP() {
		return tenLP;
	}

	public void setTenLP(String tenLP) {
		this.tenLP = tenLP;
	}

	public int getSucChua() {
		return sucChua;
	}

	public void setSucChua(int sucChua) {
		this.sucChua = sucChua;
	}

	public Double getGiaTien() {
		return giaTien;
	}

	public void setGiaTien(Double giaTien) {
		this.giaTien = giaTien;
	}

	public LoaiPhong(String maLP, String tenLP, int sucChua, Double giaTien) {
		this.maLP = maLP;
		this.tenLP = tenLP;
		this.sucChua = sucChua;
		this.giaTien = giaTien;
	}

	public LoaiPhong(String maLP) {
		this.maLP = maLP;
	}

	public LoaiPhong() {
	}

	public LoaiPhong(ResultSet rs) throws SQLException {
		this(rs.getString("maLP"), rs.getString("tenLP"), rs.getInt("sucChua"), rs.getDouble("giaTien"));
	}

	@Override
	public String toString() {
		return "LoaiPhong [giaTien=" + giaTien + ", maLP=" + maLP + ", sucChua=" + sucChua + ", tenLP=" + tenLP + "]";
	}

}