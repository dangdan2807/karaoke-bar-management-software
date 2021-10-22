package entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class HoaDon {
	private int maHoaDon;
	private Timestamp ngayGioDat;
	private Timestamp ngayGioTra;
	private int tinhTrangHD;
	private Double tongTien;

	private NhanVien nhanVien;
	private KhachHang khachHang;
	private Phong phong;

	private List<CTHoaDon> ctDSDichVu;

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

	public Timestamp getNgayGioTra() {
		return ngayGioTra;
	}

	public void setNgayGioTra(Timestamp ngayGioTra) {
		this.ngayGioTra = ngayGioTra;
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

	public Phong getPhong() {
		return phong;
	}

	public void setPhong(Phong phong) {
		this.phong = phong;
	}

	public List<CTHoaDon> getCtDSDichVu() {
		return ctDSDichVu;
	}

	public void setCtDSDichVu(List<CTHoaDon> ctDSDichVu) {
		this.ctDSDichVu = ctDSDichVu;
	}

	public HoaDon(int maHoaDon, Timestamp ngayGioDat, Timestamp ngayGioTra, int tinhTrangHD, NhanVien nhanVien,
			KhachHang khachHang, Phong phong) {
		this.maHoaDon = maHoaDon;
		this.ngayGioDat = ngayGioDat;
		this.ngayGioTra = ngayGioTra;
		this.tinhTrangHD = tinhTrangHD;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.phong = phong;
		this.tongTien = 0.0;

		ctDSDichVu = new ArrayList<CTHoaDon>();

	}

	public HoaDon(Timestamp ngayGioDat, int tinhTrangHD, NhanVien nhanVien, KhachHang khachHang, Phong phong) {
		this.ngayGioDat = ngayGioDat;
		this.tinhTrangHD = tinhTrangHD;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.phong = phong;
		this.tongTien = 0.0;

		ctDSDichVu = new ArrayList<CTHoaDon>();
	}

	public HoaDon(int maHoaDon) {
		this.maHoaDon = maHoaDon;
		this.tongTien = 0.0;

		ctDSDichVu = new ArrayList<CTHoaDon>();
	}

	public HoaDon() {
		this.tongTien = 0.0;

		ctDSDichVu = new ArrayList<CTHoaDon>();
	}

	public HoaDon(ResultSet rs) throws SQLException {
		this(rs.getInt("maHoaDon"), rs.getTimestamp("ngayGioDat"), rs.getTimestamp("ngayGioTra"),
				rs.getInt("tinhTrangHD"), new NhanVien(rs), new KhachHang(rs), new Phong(rs));
	}

	public HoaDon(ResultSet rs, int type) throws SQLException {
		this(rs.getInt("maHoaDon"), rs.getTimestamp("ngayGioDat"), rs.getTimestamp("ngayGioTra"),
				rs.getInt("tinhTrangHD"), new NhanVien(rs.getString("maNhanVien")), new KhachHang(rs.getString("maKH")),
				new Phong(rs, type));
	}

	@Override
	public String toString() {
		return "HoaDon [ngayGioTra=" + ngayGioTra + ", ctDichVu=" + ctDSDichVu + ", khachHang=" + khachHang
				+ ", maHoaDon=" + maHoaDon + ", ngayGioDat=" + ngayGioDat + ", nhanVien=" + nhanVien + ", phong="
				+ phong + ", tinhTrangHD=" + tinhTrangHD + ", tongTien=" + tongTien + "]";
	}

	public Double tinhTienHoaDon() {
		Double tongTienDV = 0.0;
		for (CTHoaDon item : ctDSDichVu) {
			tongTienDV += item.getTienDichVu();
		}
		tongTien = tongTienDV;
		return tongTien;
	}

	public boolean themCTDichVu(DichVu dichVu, int soLuongDat) {
		CTHoaDon ctDV = new CTHoaDon(soLuongDat, dichVu);
		if (ctDSDichVu.contains(ctDV)) {
			return false;
		}
		return true;
	}

	public Double tinhGioThue() {
		int minutes = 0;
		if (ngayGioTra != null && ngayGioDat != null) {
			long difference = ngayGioTra.getTime() - ngayGioDat.getTime();
			minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
		}
		return minutes * 1.0 / 60;
	}

	public Double tinhTienPhong() {
		Double soGio = tinhGioThue();
		return soGio * phong.getLoaiPhong().getGiaTien();
	}
}