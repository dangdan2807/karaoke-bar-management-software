package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CTDichVu {
	private int maCTDichVu;
	private int soLuongDat;
	private Timestamp ngayGioDat;
	private Double tienDichVu;

	private HoaDon hoaDon;
	private DichVu dichVu;

	public int getSoLuongDat() {
		return soLuongDat;
	}

	public int getMaCTDichVu() {
		return maCTDichVu;
	}

	public void setMaCTDichVu(int maCTDichVu) {
		this.maCTDichVu = maCTDichVu;
	}

	public void setSoLuongDat(int soLuongDat) {
		this.soLuongDat = soLuongDat;
	}

	public Timestamp getNgayGioDat() {
		return ngayGioDat;
	}

	public void setNgayGioDat(Timestamp ngayGioDat) {
		this.ngayGioDat = ngayGioDat;
	}

	public Double getTienDichVu() {
		return tienDichVu;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public DichVu getDichVu() {
		return dichVu;
	}

	public void setDichVu(DichVu dichVu) {
		this.dichVu = dichVu;
	}

	public CTDichVu(int maCTDichVu, int soLuongDat, Timestamp ngayGioDat, HoaDon hoaDon, DichVu dichVu) {
		this.maCTDichVu = maCTDichVu;
		this.soLuongDat = soLuongDat;
		this.ngayGioDat = ngayGioDat;
		this.hoaDon = hoaDon;
		this.dichVu = dichVu;
		this.tienDichVu = tinhTienDichVu();
	}

	public CTDichVu(int maCTDichVu) {
		this.maCTDichVu = maCTDichVu;
	}

	public CTDichVu() {
	}

	public CTDichVu(ResultSet rs) throws SQLException {
		this(rs.getInt("maCTDichVu"), rs.getInt("soLuongDat"), rs.getTimestamp("ngayGioDatCTDV"), new HoaDon(rs), new DichVu(rs));
	}

	public CTDichVu(ResultSet rs, int type) throws SQLException {
		this(rs.getInt("maCTDichVu"), rs.getInt("soLuongDat"), rs.getTimestamp("ngayGioDatCTDV"), new HoaDon(rs, type), new DichVu(rs));
	}

	@Override
	public String toString() {
		return "CTDichVu [dichVu=" + dichVu + ", hoaDon=" + hoaDon + ", ngayGioDat=" + ngayGioDat + ", soLuongDat="
				+ soLuongDat + ", tienDichVu=" + tienDichVu + "]";
	}

	public Double tinhTienDichVu() {
		return soLuongDat * dichVu.getGiaBan();
	}
}