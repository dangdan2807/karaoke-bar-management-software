package entity;

import java.sql.Date;

public class HoaDon {
	private int maHoaDon;
	private Date ngayGioDat;
	private int tinhTrang;
	private Double tongTien;
	public NhanVien nhanVien;
	public KhachHang khachHang;
	public CTPhong ctPhong;

	public int getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(int maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public Date getNgayGioDat() {
		return ngayGioDat;
	}

	public void setNgayGioDat(Date ngayGioDat) {
		this.ngayGioDat = ngayGioDat;
	}

	public int getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(int tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public Double getTongTien() {
		return tongTien;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public CTPhong getCtPhong() {
		return ctPhong;
	}

	public void setCtPhong(CTPhong ctPhong) {
		this.ctPhong = ctPhong;
	}

	public HoaDon(int maHoaDon, Date ngayGioDat, int tinhTrang, NhanVien nhanVien, KhachHang khachHang,
			CTPhong ctPhong) {
		this.maHoaDon = maHoaDon;
		this.ngayGioDat = ngayGioDat;
		this.tinhTrang = tinhTrang;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.ctPhong = ctPhong;
		this.tongTien = 0.0;
	}

	public HoaDon(int maHoaDon) {
		this.maHoaDon = maHoaDon;
		this.tongTien = 0.0;
	}

	public HoaDon() {
		this.tongTien = 0.0;
	}

	@Override
	public String toString() {
		return "HoaDon [ctPhong=" + ctPhong + ", khachHang=" + khachHang + ", maHoaDon="
				+ maHoaDon + ", ngayGioDat=" + ngayGioDat + ", nhanVien=" + nhanVien + ", tinhTrang=" + tinhTrang
				+ ", tongTien=" + tongTien + "]";
	}

	public Double tinhTienHoaDon() {
		Double tongTienDV = 0.0;
		// for (CTDichVu item : ctDichVu) {
		// 	tongTienDV += item.getTienDichVu();
		// }
		tongTien = tongTienDV + ctPhong.getTienPhong();
		return tongTien;
	}
}