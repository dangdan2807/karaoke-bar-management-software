package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CTDichVu {
	private int maCTDichVu;
	private int soLuongDat;
	private Date ngayGioDat;
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

	public Date getNgayGioDat() {
		return ngayGioDat;
	}

	public void setNgayGioDat(Date ngayGioDat) {
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

	public CTDichVu(int maCTDichVu, int soLuongDat, Date ngayGioDat, HoaDon hoaDon, DichVu dichVu) {
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
		this(rs.getInt("maCTPhong"), rs.getInt("soLuongDat"), rs.getDate("ngayGioDat"), new HoaDon(), new DichVu(rs));
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