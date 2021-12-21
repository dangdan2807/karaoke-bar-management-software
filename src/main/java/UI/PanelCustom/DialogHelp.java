package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.tree.*;

import DAO.NhanVienDAO;
import entity.NhanVien;

public class DialogHelp extends JFrame implements MouseListener {

	private JTree tree;
	private JScrollPane sc;
	private JPanel pnTree;
	private JTextPane txtArea;
	private DefaultMutableTreeNode nodeQLSP, nodeQLHD, nodeQLKH, nodeQLNV, nodeThemNhanVien, nodeCapNhatNhanVien;
	private DefaultMutableTreeNode nodeTimKiemNhanVien, nodeTimKiemHoaDon, nodeThongKeDoanhThu;
	private DefaultMutableTreeNode nodeSuaThongTinCaNhan, nodeDatPhong, nodeDatDichVu, nodeHuyDatDichVu;
	private DefaultMutableTreeNode nodeThanhToan, rootHDSD, nodeXuatHoaDon;
	private final String MANAGER = "Chủ quán";
	private NhanVien staffLogin;
	private String iconPath = "/icon/";
	private String imagesPath = "/images/";

	public DialogHelp(NhanVien staff) {
		this.staffLogin = staff;
		setSize(1500, 800);
		setTitle("Hướng Dẫn sử dụng tài liệu");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		JPanel pnNorth = new JPanel();
		pnNorth.setBackground(Color.decode("#69FFDD"));
		JLabel lblTaiLieu = new JLabel("Hướng Dẫn Sử Dụng Ứng Dụng");
		lblTaiLieu.setFont(new Font("Tahoma", Font.BOLD, 24));
		pnNorth.add(lblTaiLieu);

		rootHDSD = new DefaultMutableTreeNode("Hướng dẫn sử dụng");
		tree = new JTree(rootHDSD);
		tree.setFont(new Font("Dialog", Font.BOLD, 15));
		sc = new JScrollPane(tree);
		sc.setPreferredSize(new Dimension(200, 0));

		pnTree = new JPanel();
		pnTree.setLayout(new BorderLayout());
		// TaoHinh
		ImageIcon leafIcon = new ImageIcon();
		ImageIcon openIcon = ResizeImageIcon(DialogHelp.class.getResource(iconPath + "open-book.png"));
		ImageIcon closedIcon = ResizeImageIcon(DialogHelp.class.getResource(iconPath + "book.png"));

		if (leafIcon != null) {
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setClosedIcon(closedIcon);
			renderer.setOpenIcon(openIcon);
			renderer.setLeafIcon(leafIcon);
			tree.setCellRenderer(renderer);
		}

		nodeThemNhanVien = new DefaultMutableTreeNode("Thêm Nhân Viên");
		nodeCapNhatNhanVien = new DefaultMutableTreeNode("Cập Nhật Nhân Viên");
		nodeTimKiemNhanVien = new DefaultMutableTreeNode("Tìm Kiếm Nhân Viên");
		nodeThongKeDoanhThu = new DefaultMutableTreeNode("Thống Kê Doanh Thu");
		nodeTimKiemHoaDon = new DefaultMutableTreeNode("Tìm Kiếm Hóa Đơn");
		nodeSuaThongTinCaNhan = new DefaultMutableTreeNode("Sửa Thông Tin Cá Nhân");
		nodeDatPhong = new DefaultMutableTreeNode("Quản Lý Đặt Phòng");
		nodeDatDichVu = new DefaultMutableTreeNode("Đặt Dịch Vụ");
		nodeHuyDatDichVu = new DefaultMutableTreeNode("Hủy Dịch Vụ Đã Đặt");
		nodeThanhToan = new DefaultMutableTreeNode("Thanh Toán Hóa Đơn");
		nodeXuatHoaDon = new DefaultMutableTreeNode("Xuất Hóa Đơn");

		if (staffLogin.getChucVu().equalsIgnoreCase(MANAGER)) {
			rootHDSD.add(nodeThemNhanVien);
			rootHDSD.add(nodeCapNhatNhanVien);
			rootHDSD.add(nodeTimKiemNhanVien);
			rootHDSD.add(nodeThongKeDoanhThu);
		}

		rootHDSD.add(nodeTimKiemHoaDon);
		rootHDSD.add(nodeSuaThongTinCaNhan);
		rootHDSD.add(nodeDatPhong);
		rootHDSD.add(nodeDatDichVu);
		rootHDSD.add(nodeHuyDatDichVu);
		rootHDSD.add(nodeThanhToan);
		rootHDSD.add(nodeXuatHoaDon);

		tree.setRowHeight(35);
		tree.expandRow(0);
		pnTree.add(sc, BorderLayout.WEST);

		txtArea = new JTextPane();
		txtArea.setEditable(false);
		JScrollPane sc1 = new JScrollPane(txtArea);
		sc1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				txtArea.select(0, 0);
			}
		});
		pnTree.add(sc1, BorderLayout.CENTER);
		tree.addMouseListener(this);

		getContentPane().add(pnNorth, BorderLayout.NORTH);
		getContentPane().add(pnTree);

	}

	public static void main(String[] args) {
		NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
		new DialogHelp(staff).setVisible(true);
	}

	public ImageIcon ResizeImageIcon(URL url) {
		ImageIcon MyImage = new ImageIcon(url);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DefaultMutableTreeNode nodeSelected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (nodeSelected.equals(nodeTimKiemHoaDon)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Tìm Kiếm Hóa Đơn</h1>" + "<div>"
					+ getPHTML(0)
					+ "<b>Cách 1: </b>Tìm hoá đơn trong 1 khoảng thời gian.</p>" + getPHTML(1)
					+ "<b>Bước 1: </b>Chọn ngày trên lịch.</p>" + getImageHTML("image002.png") + getPHTML(1)
					+ "<b>Bước 2: </b>Chọn nút tìm kiếm, các hoá đơn có trong khoảng thời gian đã chọn sẽ xuất hiện ở bảng dưới.</p>"
					+ getImageHTML("image004.png") + getPHTML(1)
					+ "<b>Bước 3: </b>Chọn hoá đơn cần xem chi tiết, ở bảng dưới cùng sẽ xuất hiện các dịch vụ đã đặt.</p>"
					+ getImageHTML("image006.png")
					+ getPHTML(0) + "<b>Cách 2: </b>Tìm hoá đơn bằng chức năng “Lọc theo” của phần mềm.</p>"
					+ getPHTML(1)
					+ "<b>Bước 1: </b>Click vào nút mũi tên ở phần “Lọc theo” để xuất hiện các tiêu chí tìm kiếm.</p>"
					+ getImageHTML("image008.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Chọn 1 tiêu chí tìm kiếm và nhập thông tin cần tìm vào ô “Từ khoá” kế bên.</p>"
					+ getImageHTML("image010.png")
					+ getPHTML(1)
					+ "<b>Bước 3: </b>Click vào nút tìm kiếm, các hoá đơn phù hợp sẽ xuất hiện ở bảng dưới.</p>"
					+ getImageHTML("image012.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeThongKeDoanhThu)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Thống Kê Doanh Thu</h1>" + "<div>"
					+ getPHTML(1)
					+ "<b>Bước 1: </b>Click vào nút mũi tên ở phần “Lọc theo” để xuất hiện các tiêu chí thống kê doanh thu.</p>"
					+ getImageHTML("image014.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Chọn 1 tiêu chí cần thống kê vào click nút “Thống kê”. Biểu đổ sẽ thống kê doanh thu và cho biết tổng số hoá đơn và tổng doanh thu.</p>"
					+ getImageHTML("image016.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeThemNhanVien)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Thêm Nhân Viên</h1>" + "<div>"
					+ getPHTML(1) + "<b>Bước 1: </b>Trên giao diện “Quản lý hệ thống” chọn tab “Nhân viên”.</p>"
					+ getImageHTML("image018.png")
					+ getPHTML(1) + "<b>Bước 2: </b>Nhập/ chọn các thông tin cần thêm tại các TextFied và Combobox.</p>"
					+ getImageHTML("image020.png")
					+ getPHTML(1) + "<b>Bước 3: </b>Nhấn nút “Thêm” để thêm nhân viên mới.</p>"
					+ getImageHTML("image022.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeCapNhatNhanVien)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Sửa Thông Tin Nhân Viên</h1>"
					+ "<div>"
					+ getPHTML(1) + "<b>Bước 1: </b>Trên giao diện “Quản lý hệ thống” chọn tab “Nhân viên”.</p>"
					+ getImageHTML("image024.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Click chọn nhân viên cần chỉnh sửa trong form “Danh sách nhân viên”.</p>"
					+ getImageHTML("image026.png")
					+ getPHTML(1)
					+ "<b>Bước 3: </b>Nhập/ chọn các thông tin cần cập nhật tại các TextFied và Combobox.</p>"
					+ getImageHTML("image028.png")
					+ getPHTML(1) + "<b>Bước 4: </b>Nhấn nút “Sửa” và xác nhận sửa.</p>"
					+ getImageHTML("image030.png")
					+ getImageHTML("image032.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeTimKiemNhanVien)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Tìm Kiếm Nhân Viên</h1>" + "<div>"
					+ getPHTML(0)
					+ "<b>Cách 1: </b>Tìm kiếm nhân viên theo tên nhân viên.</p>"
					+ getPHTML(1) + "<b>Bước 1: </b>Trên giao diện “Quản lý hệ thống” chọn tab “Nhân viên”.</p>"
					+ getImageHTML("image033.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Trên Combobox “Lọc theo” chọn lọc theo “Tên nhân viên” và nhập tên nhân viên vào field “Từ khóa”.</p>"
					+ getImageHTML("image034.png")
					+ getPHTML(1) + "<b>Bước 3: </b>Nhấn nút “Tìm kiếm”.</p>"
					+ getImageHTML("image036.png")
					+ getPHTML(0)
					+ "<b>Cách 2: </b>Tìm kiếm nhân viên theo chức vụ.</p>"
					+ getPHTML(1) + "<b>Bước 1: </b>Trên giao diện “Quản lý hệ thống” chọn tab “Nhân viên”.</p>"
					+ getImageHTML("image038.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Trên Combobox “Lọc theo” chọn lọc theo “Tên nhân viên” và nhập tên nhân viên vào field “Từ khóa”.</p>"
					+ getImageHTML("image040.png")
					+ getPHTML(1) + "<b>Bước 3: </b>Nhấn nút “Tìm kiếm”.</p>"
					+ getImageHTML("image042.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeSuaThongTinCaNhan)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Sửa Thông Tin Cá Nhân</h1>"
					+ "<div>"
					+ getPHTML(1)
					+ "<b>Bước 1: </b>Click chọn vào nút “THÔNG TIN CÁ NHÂN” tại giao diện điều hướng quản lý.</p>"
					+ getImageHTML("image044.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Lúc này giao diện sẽ chuyển sang giao diện “QUẢN LÝ THÔNG TIN CÁ NHÂN”.</p>"
					+ getImageHTML("image046.png")
					+ getPHTML(1)
					+ "<b>Bước 3: </b>Nhập chính xác mật khẩu và chỉnh sửa lại các thông tin cần thay đổi tại Form “Thông tin cá nhân” sau đó nhấn nút “Đổi thông tin cá nhân”.</p>"
					+ "<p style='font-size:15px;text-align: center;color:red'>( <b>Lưu ý : </b>Hệ thống chỉ cho phép sửa các thông tin phù hợp)</p>"
					+ getImageHTML("image048.png")
					+ getImageHTML("image050.png")
					+ getPHTML(1)
					+ "Nếu muốn đổi mật khẩu thì nhấn vào checkbox “Đổi mật khẩu” và nhập mật khẩu mới vào sau đó nhấn nút “Đổi thông tin cá nhân”.</p>"
					+ getImageHTML("image052.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeDatPhong)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Quản Lý Đặt Phòng</h1>" + "<div>"
					+ getPHTML(1)
					+ "<b>Bước 1: </b>Click chọn phòng cho khách ở danh sách phòng. Thông tin phòng sẽ hiện trên hoá đơn.</p>"
					+ getImageHTML("image053.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Click vào nút Chọn K.Hàng để chọn khách hàng có trong hệ thống hoặc thêm khách hàng mới. Giao diện sẽ chuyển sang Quản lý khách hàng.</p>"
					+ getImageHTML("image055.png")
					+ getPHTML(1)
					+ "<b>Bước 3: </b>Chọn khách hàng ở dưới dánh sách hoặc điền thông tin của khách hàng mới ở các ô nhập liệu và click nút “Thêm”.</p>"
					+ getImageHTML("image057.png")
					+ getPHTML(1)
					+ "<b>Bước 4: </b>Click nút “Chọn KH”. Quay lại giao diện Quản lý đặt phòng Click nút “Thuê ngay” để đặt phòng.</p>"
					+ getImageHTML("image059.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeDatDichVu)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Thêm Dịch Vụ Vào Hóa Đơn</h1>"
					+ "<div>"
					+ getPHTML(1) + "<b>Bước 1: </b>Chọn phòng ở trạng thái “Đang sử dụng” cần thêm dịch vụ.</p>"
					+ getImageHTML("image061.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Chọn dịch vụ ở Danh sách dịch vụ. Có thể tìm kiếm theo “Tên dịch vụ” hoặc lọc theo “Loại dịch vụ”.</p>"
					+ getImageHTML("image063.png")
					+ getPHTML(1) + "<b>Bước 3: </b>Click nút “Thêm” để thêm dịch vụ vào Danh sách dịch vụ đã đặt</p>"
					+ getImageHTML("image065.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeHuyDatDichVu)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Hủy Dịch Vụ Đã Thêm</h1>" + "<div>"
					+ getPHTML(1) + "<b>Bước 1: </b>Chọn dịch vụ cần huỷ, nhập số lượng cần huỷ.</p>"
					+ getImageHTML("image067.png")
					+ getPHTML(1) + "<b>Bước 2: </b>Click nút “Huỷ”</p>"
					+ getImageHTML("image069.png")
					// + getPHTML(1)+"<b>Bước 3: </b>Chọn phòng cần thanh toán. Click nút “Thanh
					// toán”.</p>"
					// + getImageHTML("image065.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeThanhToan)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Thanh Toán Hóa Đơn</h1>" + "<div>"
					+ getPHTML(1) + "<b>Bước 1: </b>Chọn phòng cần thanh toán. Click nút “Thanh toán”.</p>"
					+ getImageHTML("image071.png")
					+ getPHTML(1)
					+ "<b>Bước 2: </b>Giao diện Hoá đơn sẽ hiện lên. Người dùng có thể xem lại Hoá đơn và xác nhận thanh toán cũng như có thể xuất hoá đơn ở dạng excel hoặc pdf nếu cần.</p>"
					+ getImageHTML("image073.png")
					+ getPHTML(1) + "<b>Bước 3: </b>Click nút “Thanh toán” để thanh toán hoá đơn.</p>"
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
		if (nodeSelected.equals(nodeXuatHoaDon)) {
			txtArea.setContentType("text/html");
			txtArea.setEditable(false);
			txtArea.setBackground(Color.decode("#D7FFF6"));
			String html = "<html>" + "<h1 style='text-align:center; font-size:20px '>Xuất Hóa Đơn</h1>" + "<div>"
					+ getPHTML(1)
					+ "Người dùng có thể xuất hoá đơn ở dạng pdf hoặc excel ở giao diện quản lý hoá đơn hoặc ngay tại lúc thanh toán.</p>"
					+ getImageHTML("image074.png")
					+ "<div height='30'></div>"
					+ getImageHTML("image075.png")
					+ "</div>" + "</html>";
			txtArea.setText(html);
		}
	}

	public String getPHTML(int x) {
		if (x == 0)
			return "<p style='text-align:left; font-size:17px ;margin-left: 25px'>";
		if (x == 1)
			return "<p style='text-align:left; font-size:17px ;margin-left: 75px'>";
		if (x == 2)
			return "<p style='text-align:left; font-size:17px ;margin-left: 125px'>";
		if (x == 3)
			return "<p style='text-align:left; font-size:17px ;margin-left: 150px'>";
		return "";
	}

	public String getImageHTML(String fileImage) {
		return "<div style='text-align:center;'>" + "<img width='1000' height='550' style='text-align:center;' src='"
				+ getClass().getResource(imagesPath + fileImage).toString() + "'/>" + "</div>";
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
