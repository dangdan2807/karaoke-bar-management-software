package entity;

import java.sql.Date;

public class CTDichVu {
	private int soLuongDat;
	private Date ngayGioDat;
	private Double tienDichVu;

	private HoaDon hoaDon;
	private DichVu dichVu;

	public int getSoLuongDat() {
		return soLuongDat;
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

	public CTDichVu(int soLuongDat, Date ngayGioDat, HoaDon hoaDon, DichVu dichVu) {
		this.soLuongDat = soLuongDat;
		this.ngayGioDat = ngayGioDat;
		this.hoaDon = hoaDon;
		this.dichVu = dichVu;
		this.tienDichVu = tinhTienDichVu();
	}

	public CTDichVu(HoaDon hoaDon, DichVu dichVu) {
		this.hoaDon = hoaDon;
		this.dichVu = dichVu;
	}

	public CTDichVu() {
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