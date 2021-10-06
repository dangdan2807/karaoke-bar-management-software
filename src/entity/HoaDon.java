package entity;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HoaDon {
	private int maHoaDon;
	private Timestamp ngayGioDat;
	private int tinhTrangHD;
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

	public Timestamp getNgayGioDat() {
		return ngayGioDat;
	}

	public void setNgayGioDat(Timestamp ngayGioDat) {
		this.ngayGioDat = ngayGioDat;
	}

	public int getTinhTrangHD() {
		return tinhTrangHD;
	}

	public void setTinhTrangHD(int tinhTrangHD) {
		this.tinhTrangHD = tinhTrangHD;
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

	public HoaDon(int maHoaDon, Timestamp ngayGioDat, int tinhTrangHD, NhanVien nhanVien, KhachHang khachHang,
			CTPhong ctPhong) {
		this.maHoaDon = maHoaDon;
		this.ngayGioDat = ngayGioDat;
		this.tinhTrangHD = tinhTrangHD;
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

	public HoaDon(ResultSet rs) throws SQLException {
		this(rs.getInt("maHoaDon"), rs.getTimestamp("ngayGioDatHD"), rs.getInt("tinhTrangHD"), new NhanVien(rs),
				new KhachHang(rs), new CTPhong(rs));
	}

	public HoaDon(ResultSet rs, int type) throws SQLException {
		this(rs.getInt("maHoaDon"), rs.getTimestamp("ngayGioDatHD"), rs.getInt("tinhTrangHD"),
				new NhanVien(rs.getString("maNhanVien")), new KhachHang(rs.getString("maKH")), new CTPhong(rs, type));
	}

	@Override
	public String toString() {
		return "HoaDon [ctPhong=" + ctPhong + ", khachHang=" + khachHang + ", maHoaDon=" + maHoaDon + ", ngayGioDat="
				+ ngayGioDat + ", nhanVien=" + nhanVien + ", tinhTrangHD=" + tinhTrangHD + ", tongTien=" + tongTien
				+ "]";
	}

	public Double tinhTienHoaDon() {
		Double tongTienDV = 0.0;
		// for (CTDichVu item : ctDichVu) {
		// tongTienDV += item.getTienDichVu();
		// }
		tongTien = tongTienDV + ctPhong.getTienPhong();
		return tongTien;
	}
}