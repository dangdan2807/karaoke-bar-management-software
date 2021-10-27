package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CTHoaDon {
	private int soLuongDat;

	private DichVu dichVu;

	public int getSoLuongDat() {
		return soLuongDat;
	}

	public void setSoLuongDat(int soLuongDat) {
		this.soLuongDat = soLuongDat;
	}

	public DichVu getDichVu() {
		return dichVu;
	}

	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}

	public CTHoaDon(int soLuongDat, DichVu dichVu) {
		this.soLuongDat = soLuongDat;
		this.dichVu = dichVu;
	}

	public CTHoaDon() {
	}

	public CTHoaDon(ResultSet rs) throws SQLException {
		this(rs.getInt("soLuongDat"), new DichVu(rs));
	}

	public CTHoaDon(ResultSet rs, int type) throws SQLException {
		this(rs.getInt("soLuongDat"), new DichVu(rs, type));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dichVu == null) ? 0 : dichVu.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CTHoaDon other = (CTHoaDon) obj;
		if (dichVu == null) {
			if (other.dichVu != null)
				return false;
		} else if (!dichVu.equals(other.dichVu))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CTDichVu [dichVu=" + dichVu + ", soLuongDat=" + soLuongDat + "]";
	}

	public Double tinhTienDichVu() {
		// BigDecimal soLuongDatBig = new BigDecimal(String.valueOf(soLuongDat));
		return dichVu.getGiaBan() * soLuongDat;
	}
}