package entity;

import java.sql.Date;

public class HoaDon {
    private int maHoaDon;
    private Date ngayGioDat;
    private Date ngayGiaTra;
    private int tinhTrang;

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

    public HoaDon(int maHoaDon, Date ngayGioDat, Date ngayGiaTra, int tinhTrang, NhanVien nhanVien,
            KhachHang khachHang) {
        this.maHoaDon = maHoaDon;
        this.ngayGioDat = ngayGioDat;
        this.ngayGiaTra = ngayGiaTra;
        this.tinhTrang = tinhTrang;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
    }

    @Override
    public String toString() {
        return "HoaDon [khachHang=" + khachHang + ", maHoaDon=" + maHoaDon + ", ngayGiaTra=" + ngayGiaTra
                + ", ngayGioDat=" + ngayGioDat + ", nhanVien=" + nhanVien + ", tinhTrang=" + tinhTrang + "]";
    }

}
