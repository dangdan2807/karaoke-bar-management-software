package entity;

import java.sql.Date;

public class NhanVien {
    private String maNhanVien, cmnd, hoTen, soDienThoai, chucVu;
    private Date ngaySinh;
    private Double mucLuong;
    private boolean gioiTinh;

    private TaiKhoan taiKhoan;

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Double getMucLuong() {
        return mucLuong;
    }

    public void setMucLuong(Double mucLuong) {
        this.mucLuong = mucLuong;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public NhanVien(String maNhanVien, String cmnd, String hoTen, String soDienThoai, String chucVu, Date ngaySinh,
            Double mucLuong, boolean gioiTinh, TaiKhoan taiKhoan) {
        this.maNhanVien = maNhanVien;
        this.cmnd = cmnd;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.chucVu = chucVu;
        this.ngaySinh = ngaySinh;
        this.mucLuong = mucLuong;
        this.gioiTinh = gioiTinh;
        this.taiKhoan = taiKhoan;
    }

    @Override
    public String toString() {
        return "NhanVien [chucVu=" + chucVu + ", cmnd=" + cmnd + ", gioiTinh=" + gioiTinh + ", hoTen=" + hoTen
                + ", maNhanVien=" + maNhanVien + ", mucLuong=" + mucLuong + ", ngaySinh=" + ngaySinh + ", soDienThoai="
                + soDienThoai + ", taiKhoan=" + taiKhoan + "]";
    }

}
