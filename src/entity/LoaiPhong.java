package entity;

public class LoaiPhong {
	private String maLP;
	private String tennLP;
	private int sucChua;
	private Double giaTien;

	public String getMaLP() {
		return maLP;
	}

	public void setMaLP(String maLP) {
		this.maLP = maLP;
	}

	public String getTennLP() {
		return tennLP;
	}

	public void setTennLP(String tennLP) {
		this.tennLP = tennLP;
	}

	public int getSucChua() {
		return sucChua;
	}

	public void setSucChua(int sucChua) {
		this.sucChua = sucChua;
	}

	public Double getGiaTien() {
		return giaTien;
	}

	public void setGiaTien(Double giaTien) {
		this.giaTien = giaTien;
	}

	public LoaiPhong(String maLP, String tennLP, int sucChua, Double giaTien) {
		this.maLP = maLP;
		this.tennLP = tennLP;
		this.sucChua = sucChua;
		this.giaTien = giaTien;
	}

	public LoaiPhong(String maLP) {
		this.maLP = maLP;
	}

	public LoaiPhong() {
	}

	@Override
	public String toString() {
		return "LoaiPhong [giaTien=" + giaTien + ", maLP=" + maLP + ", sucChua=" + sucChua + ", tennLP=" + tennLP + "]";
	}

}