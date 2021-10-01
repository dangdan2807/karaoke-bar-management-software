package entity;

import java.sql.Date;

public class HoaDon {
    private int maHoaDon;
    private Date ngayGioDat;
    private Date ngayGiaTra;
    private int tinhTrang;

    private CTDichVu ctDichVu;
    private CTPhong ctPhong;
    private NhanVien nhanVien;
    private KhachHang khachHang;

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

    public Date getNgayGiaTra() {
        return ngayGiaTra;
    }

    public void setNgayGiaTra(Date ngayGiaTra) {
        this.ngayGiaTra = ngayGiaTra;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public CTDichVu getCtDichVu() {
        return ctDichVu;
    }

    public void setCtDichVu(CTDichVu ctDichVu) {
        this.ctDichVu = ctDichVu;
    }

    public CTPhong getCtPhong() {
        return ctPhong;
    }

    public void setCtPhong(CTPhong ctPhong) {
        this.ctPhong = ctPhong;
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

    public Double tinhTienHoaDon() {
        Double tongTien = ctDichVu.tinhTienDichVu() + ctPhong.tinhTienPhong();
        return tongTien;
    }

    public HoaDon(int maHoaDon, Date ngayGioDat, Date ngayGiaTra, int tinhTrang, CTDichVu ctDichVu, CTPhong ctPhong,
            NhanVien nhanVien, KhachHang khachHang) {
        this.maHoaDon = maHoaDon;
        this.ngayGioDat = ngayGioDat;
        this.ngayGiaTra = ngayGiaTra;
        this.tinhTrang = tinhTrang;
        this.ctDichVu = ctDichVu;
        this.ctPhong = ctPhong;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
    }

    @Override
    public String toString() {
        return "HoaDon [ctDichVu=" + ctDichVu + ", ctPhong=" + ctPhong + ", khachHang=" + khachHang + ", maHoaDon="
                + maHoaDon + ", ngayGiaTra=" + ngayGiaTra + ", ngayGioDat=" + ngayGioDat + ", nhanVien=" + nhanVien
                + ", tinhTrang=" + tinhTrang + "]";
    }
}
