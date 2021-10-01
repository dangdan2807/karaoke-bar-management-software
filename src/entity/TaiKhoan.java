package entity;

public class TaiKhoan {
    private String taiKhoan, matKhau;
    private boolean tinhTrang;

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public TaiKhoan(String taiKhoan, String matKhau, boolean tinhTrang) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.tinhTrang = tinhTrang;
    }

    public TaiKhoan(String taiKhoan, String matKhau) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "TaiKhoan [matKhau=" + matKhau + ", taiKhoan=" + taiKhoan + ", tinhTrang=" + tinhTrang + "]";
    }

}
