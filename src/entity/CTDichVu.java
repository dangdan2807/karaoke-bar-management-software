package entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CTDichVu {
	private int soLuongDat;
	private Double tienDichVu;

	private DichVu dichVu;

	public int getSoLuongDat() {
		return soLuongDat;
	}

	public void setSoLuongDat(int soLuongDat) {
		this.soLuongDat = soLuongDat;
	}

	public Double getTienDichVu() {
		return tienDichVu;
	}

	public DichVu getDichVu() {
		return dichVu;
	}

	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}

	public CTDichVu(int soLuongDat, DichVu dichVu) {
		this.soLuongDat = soLuongDat;
		this.dichVu = dichVu;
		this.tienDichVu = tinhTienDichVu();
	}

	public CTDichVu() {
	}

	public CTDichVu(ResultSet rs) throws SQLException {
		this(rs.getInt("soLuongDat"), new DichVu(rs));
	}

	public CTDichVu(ResultSet rs, int type) throws SQLException {
		this(rs.getInt("soLuongDat"), new DichVu(rs, type));
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dichVu == null) ? 0 : dichVu.hashCode());
		// result = prime * result + ((hoaDon == null) ? 0 : hoaDon.hashCode());
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
		CTDichVu other = (CTDichVu) obj;
		if (dichVu == null) {
			if (other.dichVu != null)
				return false;
		} else if (!dichVu.equals(other.dichVu))
			return false;
		// if (hoaDon == null) {
		// 	if (other.hoaDon != null)
		// 		return false;
		// } else if (!hoaDon.equals(other.hoaDon))
		// 	return false;
		return true;
	}

	@Override
	public String toString() {
		return "CTDichVu [dichVu=" + dichVu + ", soLuongDat=" + soLuongDat + "]";
	}

	public Double tinhTienDichVu() {
		return soLuongDat * dichVu.getGiaBan();
	}
}