package entity;

import java.sql.Date;

public class CTDichVu {
    private int maCTDichVu;
    private int soLuongDat;
    private Date ngayGioDat;

    private HoaDon hoaDon;
    private DichVu dichVu;

    public int getMaCTDichVu() {
        return maCTDichVu;
    }

    public void setMaCTDichVu(int maCTDichVu) {
        this.maCTDichVu = maCTDichVu;
    }

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

    public CTDichVu(int maCTDichVu, int soLuongDat, Date ngayGioDat, HoaDon hoaDon, DichVu dichVu) {
        this.maCTDichVu = maCTDichVu;
        this.soLuongDat = soLuongDat;
        this.ngayGioDat = ngayGioDat;
        this.hoaDon = hoaDon;
        this.dichVu = dichVu;
    }

    @Override
    public String toString() {
        return "CTDichVu [dichVu=" + dichVu + ", hoaDon=" + hoaDon + ", maCTDichVu=" + maCTDichVu + ", ngayGioDat="
                + ngayGioDat + ", soLuongDat=" + soLuongDat + "]";
    }

}
