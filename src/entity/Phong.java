package entity;

public class Phong {
    private String maPhong;
    private int tinhTrang;
    private String viTri;
    private LoaiPhong loaiPhong;

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public Phong(String maPhong, int tinhTrang, String viTri, LoaiPhong loaiPhong) {
        this.maPhong = maPhong;
        this.tinhTrang = tinhTrang;
        this.viTri = viTri;
        this.loaiPhong = loaiPhong;
    }

    @Override
    public String toString() {
        return "Phong [loaiPhong=" + loaiPhong + ", maPhong=" + maPhong + ", tinhTrang=" + tinhTrang + ", viTri="
                + viTri + "]";
    }
}
