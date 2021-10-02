package entity;

import java.sql.Date;

public class KhachHang {
    private String maKH, cmnd, hoTen, soDienThoai;
    private Date ngaySinh;
    private boolean gioiTinh;

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public KhachHang(String maKH, String cmnd, String hoTen, String soDienThoai, Date ngaySinh, boolean gioiTinh) {
        this.maKH = maKH;
        this.cmnd = cmnd;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return "KhachHang [cmnd=" + cmnd + ", gioiTinh=" + gioiTinh + ", hoTen=" + hoTen + ", maKH=" + maKH
                + ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + "]";
    }

}
