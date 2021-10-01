package entity;

import java.sql.Date;

public class NhanVien {
    private String maNhanVien, cmnd, hoTen, soDienThoai;
    private Date ngaySinh;
    private Double mucLuong;
    private TaiKhoan taiKhoan;

    public NhanVien(String maNhanVien, String cmnd, String hoTen, Date ngaySinh, String soDienThoai, Double mucLuong, TaiKhoan taiKhoan) {
        this.maNhanVien = maNhanVien;
        this.cmnd = cmnd;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.mucLuong = mucLuong;
        this.taiKhoan = taiKhoan;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Double getMucLuong() {
        return mucLuong;
    }

    public void setMucLuong(Double mucLuong) {
        this.mucLuong = mucLuong;
    }

    @Override
    public String toString() {
        return "NhanVien [cmnd=" + cmnd + ", hoTen=" + hoTen + ", maNhanVien=" + maNhanVien + ", mucLuong=" + mucLuong
                + ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + ", taiKhoan" + "]";
    }

}
