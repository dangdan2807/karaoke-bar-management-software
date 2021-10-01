package entity;

public class LoaiPhong {
    private String maLP, tenLP;
    private int sucChua;
    private Double giaPhong;

    public Double getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(Double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public String getMaLP() {
        return maLP;
    }

    public void setMaLP(String maLP) {
        this.maLP = maLP;
    }

    public String getTenLP() {
        return tenLP;
    }

    public void setTenLP(String tenLP) {
        this.tenLP = tenLP;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public LoaiPhong(String maLP, String tenLP, int sucChua, Double giaPhong) {
        this.maLP = maLP;
        this.tenLP = tenLP;
        this.sucChua = sucChua;
        this.giaPhong = giaPhong;
    }

    @Override
    public String toString() {
        return "LoaiPhong [giaPhong=" + giaPhong + ", maLP=" + maLP + ", sucChua=" + sucChua + ", tenLP=" + tenLP + "]";
    }

}
