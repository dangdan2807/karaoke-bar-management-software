package entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Lớp hóa đơn
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm javadoc
 */
public class HoaDon {
	private String maHoaDon;
	private Timestamp ngayGioDat;
	private Timestamp ngayGioTra;
	private int tinhTrangHD;

	private Double giaPhong;
	private Double tongTienHD;
	private NhanVien nhanVien;
	private Phong phong;
	private KhachHang khachHang;

	private List<CTDichVu> dsCTDichVu;

	/**
	 * Lấy giá của phòng đang cho thuê
	 */
	public Double getGiaPhong() {
		return giaPhong;
	}

	/**
	 * Thiết lập giá của phòng đang cho thuê
	 * 
	 * @param giaPhong {@code Double}: giá của phòng đang cho thuê
	 */
	public void setGiaPhong(Double giaPhong) {
		this.giaPhong = giaPhong;
	}

	/**
	 * Lấy mã hóa đơn
	 * 
	 * @return {@code String}: mã hóa đơn
	 */
	public String getMaHoaDon() {
		return maHoaDon;
	}

	/**
	 * Cập nhật mã hóa đơn
	 * 
	 * @param maHoaDon {@code String}: mã hóa đơn
	 */
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	/**
	 * Lấy ngày giờ đặt
	 * 
	 * @return {@code Timestamp}: ngày giờ đặt
	 */
	public Timestamp getNgayGioDat() {
		return ngayGioDat;
	}

	/**
	 * Cập nhật ngày giờ đặt
	 * 
	 * @param ngayGioDat {@code Timestamp}: ngày giờ đặt
	 */
	public void setNgayGioDat(Timestamp ngayGioDat) {
		this.ngayGioDat = ngayGioDat;
	}

	/**
	 * Lấy ngày giờ trả
	 * 
	 * @return {@code Timestamp}: ngày giờ trả
	 */
	public Timestamp getNgayGioTra() {
		return ngayGioTra;
	}

	/**
	 * Cập nhật ngày giờ trả
	 * 
	 * @param ngayGioTra {@code Timestamp}: ngày giờ trả
	 */
	public void setNgayGioTra(Timestamp ngayGioTra) {
		this.ngayGioTra = ngayGioTra;
		this.tongTienHD = ((tinhTongTienDichVu() + tinhTienPhong()) * 1.1);
	}

	/**
	 * Lấy tình trạng hóa đơn
	 * 
	 * @return {@code int}: tình trạng hóa đơn
	 *         <ul>
	 *         <li>Nếu chưa thanh toán thì trả về {@code 0}</li>
	 *         <li>Nếu đã thanh toán thì trả về {@code 1}</li>
	 *         </ul>
	 */
	public int getTinhTrangHD() {
		return tinhTrangHD;
	}

	/**
	 * Cập nhật tình trạng hóa đơn
	 * 
	 * @param tinhTrangHD {@code int}: tình trạng hóa đơn
	 *                    <ul>
	 *                    <li>Nếu chưa thanh toán thì truyền vào {@code 0}</li>
	 *                    <li>Nếu đã thanh toán thì truyền vào {@code 1}</li>
	 *                    </ul>
	 */
	public void setTinhTrangHD(int tinhTrangHD) {
		this.tinhTrangHD = tinhTrangHD;
	}

	/**
	 * Lấy nhân viên tạo hóa đơn
	 * 
	 * @return {@code NhanVien}: nhân viên tạo hóa đơn
	 */
	public NhanVien getNhanVien() {
		return nhanVien;
	}

	/**
	 * Cập nhật nhân viên tạo hóa đơn
	 * 
	 * @param nhanVien {@code NhanVien}: nhân viên tạo hóa đơn
	 */
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	/**
	 * Lấy khách hàng đặt phòng
	 * 
	 * @return {@code KhachHang}: khách hàng đặt phòng
	 */
	public KhachHang getKhachHang() {
		return khachHang;
	}

	/**
	 * Cập nhật khách hàng đặt phòng
	 * 
	 * @param khachHang {@code KhachHang}: khách hàng đặt phòng
	 */
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	/**
	 * Lấy phòng đã đặt
	 * 
	 * @return {@code Phong}: phòng đã đặt
	 */
	public Phong getPhong() {
		return phong;
	}

	/**
	 * Cập nhật phòng đã đặt
	 * 
	 * @param phong {@code Phong}: phòng đã đặt
	 */
	public void setPhong(Phong phong) {
		this.phong = phong;
	}

	/**
	 * Lấy danh sách chi tiết dịch vụ
	 * 
	 * @return {@code List<CTDichVu>}: danh sách chi tiết dịch vụ
	 */
	public List<CTDichVu> getDsCTDichVu() {
		return dsCTDichVu;
	}

	/**
	 * Cập nhật danh sách chi tiết dịch vụ
	 * 
	 * @param ctDSDichVu {@code List<CTDichVu>}: danh sách chi tiết dịch vụ
	 */
	public void setDsCTDichVu(List<CTDichVu> ctDSDichVu) {
		this.dsCTDichVu = ctDSDichVu;
		this.tongTienHD = ((tinhTongTienDichVu() + tinhTienPhong()) * 1.1);
	}

	/**
	 * Tạo 1 {@code HoaDon} với các tham số sau:
	 * 
	 * @param maHoaDon    {@code String}: mã hóa đơn
	 * @param ngayGioDat  {@code Timestamp}: ngày giờ đặt
	 * @param ngayGioTra  {@code Timestamp}: ngày giờ trả
	 * @param tinhTrangHD {@code int}: tình trạng hóa đơn
	 *                    <ul>
	 *                    <li>Nếu chưa thanh toán thì truyền vào {@code 0}</li>
	 *                    <li>Nếu đã thanh toán thì truyền vào {@code 1}</li>
	 *                    </ul>
	 * @param giaPhong    {@code Double}: giá phòng
	 * @param nhanVien    {@code NhanVien}: nhân viên tạo hóa đơn
	 * @param khachHang   {@code KhachHang}: khách hàng đặt phòng
	 * @param phong       {@code Phong}: phòng được chọn
	 */
	public HoaDon(String maHoaDon, Timestamp ngayGioDat, Timestamp ngayGioTra, int tinhTrangHD, double giaPhong,
			NhanVien nhanVien,
			KhachHang khachHang, Phong phong) {
		this.maHoaDon = maHoaDon;
		this.ngayGioDat = ngayGioDat;
		this.ngayGioTra = ngayGioTra;
		this.tinhTrangHD = tinhTrangHD;
		this.giaPhong = giaPhong;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.phong = phong;

		this.dsCTDichVu = new ArrayList<CTDichVu>();
		this.tongTienHD = ((tinhTongTienDichVu() + tinhTienPhong()) * 1.1);
	}

	/**
	 * Tạo 1 {@code HoaDon} với các tham số sau:
	 * 
	 * @param maHoaDon    {@code String}: mã hóa đơn
	 * @param ngayGioDat  {@code Timestamp}: ngày giờ đặt
	 * @param tinhTrangHD {@code int}: tình trạng hóa đơn
	 *                    <ul>
	 *                    <li>Nếu chưa thanh toán thì truyền vào {@code 0}</li>
	 *                    <li>Nếu đã thanh toán thì truyền vào {@code 1}</li>
	 *                    </ul>
	 * @param giaPhong    {@code Double}: giá phòng
	 * @param nhanVien    {@code NhanVien}: nhân viên tạo hóa đơn
	 * @param khachHang   {@code KhachHang}: khách hàng đặt phòng
	 * @param phong       {@code Phong}: phòng được chọn
	 */
	public HoaDon(String maHoaDon, Timestamp ngayGioDat, int tinhTrangHD, double giaPhong, NhanVien nhanVien,
			KhachHang khachHang,
			Phong phong) {
		this.maHoaDon = maHoaDon;
		this.ngayGioDat = ngayGioDat;
		this.tinhTrangHD = tinhTrangHD;
		this.giaPhong = giaPhong;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.phong = phong;

		this.dsCTDichVu = new ArrayList<CTDichVu>();
		this.tongTienHD = 0.0;
	}

	/**
	 * Tạo 1 {@code HoaDon} mới (tính trạng chưa thanh toán) với các tham số sau:
	 * 
	 * @param maHoaDon   {@code String}: mã hóa đơn
	 * @param ngayGioDat {@code Timestamp}: ngày giờ đặt
	 * @param giaPhong   {@code Double}: giá phòng
	 * @param nhanVien   {@code NhanVien}: nhân viên tạo hóa đơn
	 * @param khachHang  {@code KhachHang}: khách hàng đặt phòng
	 * @param phong      {@code Phong}: phòng được chọn
	 */
	public HoaDon(String maHoaDon, Timestamp ngayGioDat, double giaPhong, NhanVien nhanVien, KhachHang khachHang,
			Phong phong) {
		this.maHoaDon = maHoaDon;
		this.ngayGioDat = ngayGioDat;
		this.tinhTrangHD = 0;
		this.giaPhong = giaPhong;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.phong = phong;

		this.dsCTDichVu = new ArrayList<CTDichVu>();
		this.tongTienHD = 0.0;
	}

	/**
	 * Tạo 1 {@code HoaDon} mới (tính trạng chưa thanh toán) với các tham số sau:
	 * 
	 * @param maHoaDon    {@code String}: mã hóa đơn
	 * @param ngayGioDat  {@code Timestamp}: ngày giờ đặt
	 * @param ngayGioTra  {@code Timestamp}: ngày giờ tính tiền
	 * @param tinhTrangHD {@code int}: tình trạng hóa đơn
	 * @param giaPhong    {@code Double}: giá phòng
	 */
	public HoaDon(String maHoaDon, Timestamp ngayGioDat, Timestamp ngayGioTra, int tinhTrangHD, double giaPhong) {
		this.maHoaDon = maHoaDon;
		this.ngayGioDat = ngayGioDat;
		this.ngayGioTra = ngayGioTra;
		this.tinhTrangHD = tinhTrangHD;
		this.giaPhong = giaPhong;

		this.dsCTDichVu = new ArrayList<CTDichVu>();
		this.tongTienHD = ((tinhTongTienDichVu() + tinhTienPhong()) * 1.1);
	}

	/**
	 * Tạo 1 {@code HoaDon} với các tham số sau:
	 * 
	 * @param maHoaDon {@code String}: mã hóa đơn
	 */
	public HoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;

		dsCTDichVu = new ArrayList<CTDichVu>();
	}

	/**
	 * Tạo 1 {@code HoaDon} không tham số
	 */
	public HoaDon() {
		dsCTDichVu = new ArrayList<CTDichVu>();
	}

	/**
	 * Tạo 1 {@code HoaDon} từ kết quả truy vấn nhận được từ cơ sở dữ liệu
	 * 
	 * @param rs {@code ResultSet}: kết quả truy vấn từ cơ sở dữ liệu
	 * @throws SQLException {@code SQLException}: lỗi truy vấn
	 */
	public HoaDon(ResultSet rs) throws SQLException {
		this(rs.getString("maHoaDon"), rs.getTimestamp("ngayGioDat"), rs.getTimestamp("ngayGioTra"),
				rs.getInt("tinhTrangHD"), rs.getDouble("giaPhong"));
	}

	@Override
	public String toString() {
		return "HoaDon [ngayGioTra=" + ngayGioTra + ", ctDichVu=" + dsCTDichVu + ", khachHang=" + khachHang
				+ ", maHoaDon=" + maHoaDon + ", ngayGioDat=" + ngayGioDat + ", nhanVien=" + nhanVien + ", phong="
				+ phong + ", tinhTrangHD=" + tinhTrangHD + "]";
	}

	public Double tinhTongTienDichVu() {
		Double tongTienDV = 0.0;
		for (CTDichVu item : dsCTDichVu) {
			tongTienDV += item.tinhTienDichVu();
		}
		return tongTienDV;
	}

	/**
	 * Lấy tổng tiền dịch vụ
	 * 
	 * @return {@code double}: tổng tiền dịch vụ
	 */
	public Double getTongTienHD() {
		return tongTienHD;
	}

	/**
	 * Thêm một chi tiết dịch vụ vào hóa đơn
	 * 
	 * @param dichVu     {@code DichVu}: dịch vụ được thêm
	 * @param donGia     {@code double}: Giá bán tại thời điểm tạo hóa đơn
	 * @param soLuongDat {@code int}: số lượng đặt
	 * @return {@code boolean}: kết quả trả về của câu truy vấn
	 *         <ul>
	 *         <li>Nếu thêm thành công thì trả về {@code true}</li>
	 *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
	 *         </ul>
	 */
	public boolean themCTDichVu(DichVu dichVu, int soLuongDat, double donGia) {
		CTDichVu ctDV = new CTDichVu(soLuongDat, donGia, dichVu);
		if (dsCTDichVu.contains(ctDV)) {
			return false;
		}
		this.dsCTDichVu.add(ctDV);
		this.tongTienHD = ((tinhTongTienDichVu() + tinhTienPhong()) * 1.1);
		return true;
	}

	/**
	 * tính số giờ thuê phòng
	 * 
	 * @return {@code Double}: số giờ thuê phòng
	 */
	public Double tinhGioThue() {
		int soPhut = 0;
		if (ngayGioTra != null && ngayGioDat != null) {
			long difference = ngayGioTra.getTime() - ngayGioDat.getTime();
			soPhut = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
		}
		soPhut = (int) soPhut / 15;
		return soPhut * 1.0 / 4;
	}

	/**
	 * Tính tiền thuê phòng
	 * 
	 * @return {@code Double}: tiền phòng đã thuê
	 */
	public Double tinhTienPhong() {
		Double soGio = tinhGioThue();
		if (soGio < 1.0) {
			soGio = 1.0;
		}
		return soGio * giaPhong;
	}
}