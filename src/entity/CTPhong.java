package entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class CTPhong {
	private int maCTPhong;
	private Date ngayGioNhan;
	private Date ngayGioTra;
	private Double tienPhong;
	public Phong phong;

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

	public Double getTienPhong() {
		return tienPhong;
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
		this.tienPhong = 0.0;
	}

	public CTPhong(int maCTPhong) {
		this.maCTPhong = maCTPhong;
	}

	public CTPhong() {
	}

	public CTPhong(ResultSet rs) throws SQLException {
		this(rs.getInt("maCTPhong"), rs.getDate("ngayGioNhan"), rs.getDate("ngayGioTra"), new Phong(rs));
	}

	@Override
	public String toString() {
		return "CTPhong [maCTPhong=" + maCTPhong + ", ngayGioNhan=" + ngayGioNhan + ", ngayGioTra=" + ngayGioTra
				+ ", phong=" + phong + ", tienPhong=" + tienPhong + "]";
	}

	public Double tinhGioThue() {
		long difference = ngayGioTra.getTime() - ngayGioNhan.getTime();
		int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
		return minutes * 1.0 / 60;
	}

	public Double tinhTienPhong() {
		Double soGio = tinhGioThue();
		tienPhong = soGio * phong.getLoaiPhong().getGiaTien();
		return tienPhong;
	}
}