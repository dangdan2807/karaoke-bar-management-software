package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoaiDichVu {
	private String maLDV;
	private String tenLDV;

	public String getMaLDV() {
		return maLDV;
	}

	public void setMaLDV(String maLDV) {
		this.maLDV = maLDV;
	}

	public String getTenLDV() {
		return tenLDV;
	}

	public void setTenLDV(String tenLDV) {
		this.tenLDV = tenLDV;
	}

	public LoaiDichVu(String maLDV, String tenLDV) {
		this.maLDV = maLDV;
		this.tenLDV = tenLDV;
	}

	public LoaiDichVu(String maLDV) {
		this.maLDV = maLDV;
	}

	public LoaiDichVu() {
	}

	public LoaiDichVu(ResultSet rs) throws SQLException {
		this(rs.getString("maLDV"), rs.getString("tenLDV"));
	}

	@Override
	public String toString() {
		return "LoaiDichVu [maLDV=" + maLDV + ", tenLDV=" + tenLDV + "]";
	}
}