package entity;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class CTPhong {
    private int maCTPhong;
    private Date ngayGioNhan;
    private Date ngayGioTra;
    private Phong phong;

    public int getMaCTPhong() {
        return maCTPhong;
    }

    public void setMaCTPhong(int maCTPhong) {
        this.maCTPhong = maCTPhong;
    }

    public Date getNgayGioNhan() {
        return ngayGioNhan;
    }

    public void setNgayGioNhan(Date ngayGioNhan) {
        this.ngayGioNhan = ngayGioNhan;
    }

    public Date getNgayGioTra() {
        return ngayGioTra;
    }

    public void setNgayGioTra(Date ngayGioTra) {
        this.ngayGioTra = ngayGioTra;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public CTPhong(int maCTPhong, Date ngayGioNhan, Date ngayGioTra, Phong phong) {
        this.maCTPhong = maCTPhong;
        this.ngayGioNhan = ngayGioNhan;
        this.ngayGioTra = ngayGioTra;
        this.phong = phong;
    }

    public CTPhong(int maCTPhong, Phong phong) {
        this.maCTPhong = maCTPhong;
        this.ngayGioNhan = null;
        this.ngayGioTra = null;
        this.phong = phong;
    }

    public Double tinhGioThue() {
        long difference = ngayGioTra.getTime() - ngayGioNhan.getTime();
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
        return minutes * 1.0 / 60;
    }

    public Double tinhTienPhong() {
        Double soGio = tinhGioThue();
        Double tienPhong = soGio * phong.getLoaiPhong().getGiaPhong();
        return tienPhong;
    }

    @Override
    public String toString() {
        return "CTPhong [maCTPhong=" + maCTPhong + ", ngayGioNhan=" + ngayGioNhan + ", ngayGioTra=" + ngayGioTra
                + ", phong=" + phong + "]";
    }
}
