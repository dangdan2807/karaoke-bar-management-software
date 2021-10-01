package entity;

import java.sql.Date;
import java.util.List;

public class CTDichVu {
    private int maCTDichVu;
    private int soLuongDat;
    private Date ngayGioDat;
    private List<DichVu> dichVu;

    public int getMaCTDichVu() {
        return maCTDichVu;
    }

    public void setMaCTDichVu(int maCTDichVu) {
        this.maCTDichVu = maCTDichVu;
    }

    public List<DichVu> getDichVu() {
        return dichVu;
    }

    public void setDichVu(List<DichVu> dichVu) {
        this.dichVu = dichVu;
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

    public CTDichVu(int maCTDichVu, int soLuongDat, Date ngayGioDat, List<DichVu> dichVu) {
        this.maCTDichVu = maCTDichVu;
        this.soLuongDat = soLuongDat;
        this.ngayGioDat = ngayGioDat;
        this.dichVu = dichVu;
    }

    public Double tinhTienDichVu() {
        Double tongTien = 0.0;
        for (DichVu item : dichVu) {
            tongTien += item.getGiaBan();
        }
        return tongTien;
    }

    @Override
    public String toString() {
        return "CTDichVu [dichVu=" + dichVu + ", maCTDichVu=" + maCTDichVu + ", ngayGioDat=" + ngayGioDat
                + ", soLuongDat=" + soLuongDat + "]";
    }

}
