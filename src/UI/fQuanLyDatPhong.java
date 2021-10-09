package UI;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

import DAO.*;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.DialogChonKhachHang;
import entity.*;

public class fQuanLyDatPhong extends JFrame implements ActionListener, MouseListener, ItemListener, ChangeListener {
	private JButton[] btnDSPhong;
	private int pnShowTableWidth = 310;
	private int heightTable = 140;

	private JPanel pnMain, pnShowRoom;
	private DefaultTableModel modelTableBill, modelTableDichVu;
	private JTable tableBill, tableDichVu;
	private JLabel lbTenNV;
	private JButton btnChuyenPhong, btnLamMoiPhong, btnBack, btnTimDichVu, btnThanhToan, btnThem;
	private JButton btnXoa, btnThueNgay, btnLamMoiDV, btnDatTruoc, btnHuyDatPhong, btnChonKH;
	private JTextField txtMaHoaDon, txtMaPhong, txtTotalPrice, txtTenDichVu, txtViTriP, txtTenLP;
	private JTextField txtSLCon, txtGiaBan, txtTGBatDau, txtGTKetThuc, txtTenKH;
	private JComboBox<String> cboLoaiPhong, cboMaPhong;
	private JSpinner spinSLDat;
	private JMenuBar menuBar;

	private ImageIcon transferIcon = new ImageIcon("img/transfer_16.png");
	private ImageIcon refreshIcon = new ImageIcon("img/refresh_16.png");
	private ImageIcon paymentIcon = new ImageIcon("img/payment_16.png");
	private ImageIcon searchIcon = new ImageIcon("img/search_16.png");
	private ImageIcon phongIcon = new ImageIcon("img/micro_32.png");
	private ImageIcon addIcon = new ImageIcon("img/blueAdd_16.png");
	private ImageIcon trashIcon = new ImageIcon("img/trash_16.png");
	private ImageIcon backIcon = new ImageIcon("img/back_16.png");

	private int viTri = -1;
	private NhanVien staffLogin = null;
	private KhachHang khachHangDcChon = null;

	public fQuanLyDatPhong(NhanVien staff) {
		setTitle("Phần Mềm Quản Lý Quán Karaoke");
		setSize(1280, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.staffLogin = staff;
		createMenuBar();
		createFromQLKS();
		setCloseAction(this);
	}

	public void createMenuBar() {
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu menuTK = new JMenu("Tài khoản");

		menuBar.add(menuTK);
	}

	public void createFromQLKS() {
		pnMain = new JPanel();
		pnMain.setBackground(Color.WHITE);
		pnMain.setLayout(null);
		JPanel pnTitle = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				GradientPaint gra = new GradientPaint(179, 0, Color.decode("#900a9c"), 180, getHeight(),
						Color.decode("#00cccb"));
				g2.setPaint(gra);
				g2.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		pnTitle.setBounds(0, 0, 1264, 39);
		pnMain.add(pnTitle);
		pnTitle.setBackground(Color.decode("#d0e1fd"));

		JLabel lbTitle = new JLabel("Quản Lý Đặt Phòng");
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		pnTitle.add(lbTitle);

		JPanel pnNameEmp = new JPanel();
		pnNameEmp.setBackground(Color.WHITE);
		pnNameEmp.setBorder(new TitledBorder(null, "Nhân Viên: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnNameEmp.setBounds(0, 39, 330, 65);
		pnMain.add(pnNameEmp);
		pnNameEmp.setLayout(new BorderLayout(0, 0));

		JPanel pnControlPhong_1 = new JPanel();
		pnControlPhong_1.setLayout(null);
		pnControlPhong_1.setPreferredSize(new Dimension(330, 70));
		pnControlPhong_1.setBackground(Color.WHITE);
		pnNameEmp.add(pnControlPhong_1);

		lbTenNV = new JLabel("Tên nhân viên");
		lbTenNV.setBounds(12, 4, 191, 21);
		pnControlPhong_1.add(lbTenNV);
		lbTenNV.setFont(new Font("Dialog", Font.BOLD, 18));

		btnBack = new JButton("Quay lại", backIcon);
		btnBack.setBounds(209, 4, 96, 26);
		pnControlPhong_1.add(btnBack);
		CustomUI.getInstance().setCustomBtn(btnBack);
		btnBack.addActionListener(this);
		btnBack.addMouseListener(this);

		JPanel pnTableList = new JPanel();
		pnTableList.setBackground(Color.WHITE);
		pnTableList.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnTableList.setBounds(0, 105, 330, 542);
		pnMain.add(pnTableList);
		pnTableList.setLayout(new BorderLayout(0, 0));

		JPanel pnControlPhong = new JPanel();
		pnControlPhong.setBackground(Color.WHITE);
		pnTableList.add(pnControlPhong, BorderLayout.NORTH);
		pnControlPhong.setLayout(null);
		pnControlPhong.setPreferredSize(new Dimension(330, 70));

		btnChuyenPhong = new JButton("Đổi phòng", transferIcon);
		btnChuyenPhong.setBounds(210, 0, 96, 27);
		CustomUI.getInstance().setCustomBtn(btnChuyenPhong);
		pnControlPhong.add(btnChuyenPhong);

		btnLamMoiPhong = new JButton("Làm mới", refreshIcon);
		btnLamMoiPhong.setBounds(210, 33, 96, 27);
		CustomUI.getInstance().setCustomBtn(btnLamMoiPhong);
		pnControlPhong.add(btnLamMoiPhong);

		cboMaPhong = new JComboBox<String>();
		cboMaPhong.setBackground(Color.WHITE);
		cboMaPhong.setBounds(89, 0, 118, 27);
		pnControlPhong.add(cboMaPhong);

		JLabel lbLoaiPhong = new JLabel("Loại phòng: ");
		lbLoaiPhong.setBounds(10, 38, 83, 16);
		pnControlPhong.add(lbLoaiPhong);

		cboLoaiPhong = new JComboBox<String>();
		cboLoaiPhong.setBounds(89, 33, 118, 27);
		pnControlPhong.add(cboLoaiPhong);
		cboLoaiPhong.setBackground(Color.WHITE);

		JLabel lbPhong = new JLabel("Phòng: ");
		lbPhong.setBounds(10, 5, 83, 16);
		pnControlPhong.add(lbPhong);

		cboLoaiPhong.addItemListener(this);

		pnShowRoom = new JPanel();
		pnShowRoom.setBackground(Color.WHITE);
		FlowLayout flShowTable = new FlowLayout(FlowLayout.LEFT);
		flShowTable.setHgap(6);
		flShowTable.setVgap(6);
		pnShowRoom.setLayout(flShowTable);
		pnShowRoom.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));

		JScrollPane scpShowTable = new JScrollPane(pnShowRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnTableList.add(scpShowTable, BorderLayout.CENTER);
		scpShowTable.setBorder(new TitledBorder(null, "Danh sách phòng"));
		scpShowTable.setBackground(Color.WHITE);
		scpShowTable.getVerticalScrollBar().setUnitIncrement(10);

		JPanel pnBill = new JPanel();
		pnBill.setBackground(Color.WHITE);
		pnBill.setBounds(330, 40, 488, 597);

		JPanel pnBillInfo = new JPanel();
		pnBillInfo.setBackground(Color.WHITE);
		pnBillInfo.setBorder(
				new TitledBorder(null, "Thông tin hóa đơn", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBillInfo.setLayout(null);
		pnBillInfo.setPreferredSize(new Dimension(488, 210));

		JPanel pnBillList = new JPanel();
		pnBillList.setBackground(Color.WHITE);
		pnBillList.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Danh sách dịch vụ đã đặt", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		String[] colsDV = { "STT", "Tên sản phẩm", "Đơn giá", "Số Lượng", "Thành tiền" };
		modelTableBill = new DefaultTableModel(colsDV, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		pnBillList.setLayout(new BorderLayout(0, 0));
		tableBill = new JTable(modelTableBill);
		tableBill.setBackground(Color.WHITE);

		JScrollPane scpTableBill = new JScrollPane(tableBill, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scpTableBill.setBackground(Color.WHITE);
		scpTableBill.getViewport().setBackground(Color.WHITE);
		pnBillList.add(scpTableBill, BorderLayout.CENTER);
		pnBill.setLayout(new BorderLayout(0, 0));

		pnBill.add(pnBillInfo, BorderLayout.NORTH);

		JLabel lbMaHD = new JLabel("Mã hóa đơn: ");
		lbMaHD.setBounds(12, 20, 85, 20);
		pnBillInfo.add(lbMaHD);

		txtMaHoaDon = new JTextField();
		txtMaHoaDon.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtMaHoaDon.setEditable(false);
		txtMaHoaDon.setBounds(85, 20, 157, 20);
		txtMaHoaDon.setBackground(Color.decode("#f9f9f9"));
		pnBillInfo.add(txtMaHoaDon);
		txtMaHoaDon.setColumns(10);

		JLabel lblMaBan = new JLabel("Mã phòng:");
		lblMaBan.setBounds(248, 20, 82, 20);
		pnBillInfo.add(lblMaBan);

		txtMaPhong = new JTextField();
		txtMaPhong.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtMaPhong.setEditable(false);
		txtMaPhong.setBounds(328, 21, 150, 20);
		txtMaPhong.setBackground(Color.decode("#f9f9f9"));
		pnBillInfo.add(txtMaPhong);
		txtMaPhong.setColumns(10);

		JLabel lbBatDau = new JLabel("Giờ vào:");
		lbBatDau.setBounds(12, 51, 85, 16);
		pnBillInfo.add(lbBatDau);

		JLabel lbKetThuc = new JLabel("Giờ ra:");
		lbKetThuc.setBounds(12, 78, 85, 16);
		pnBillInfo.add(lbKetThuc);

		txtTGBatDau = new JTextField("");
		txtTGBatDau.setEditable(false);
		txtTGBatDau.setBounds(85, 49, 157, 20);
		txtTGBatDau.setBackground(Color.decode("#f9f9f9"));
		pnBillInfo.add(txtTGBatDau);

		btnThanhToan = new JButton("Thanh toán", paymentIcon);
		btnThanhToan.setBounds(368, 172, 110, 26);
		CustomUI.getInstance().setCustomBtn(btnThanhToan);
		pnBillInfo.add(btnThanhToan);

		btnDatTruoc = new JButton("Đặt trước", null);
		btnDatTruoc.setBounds(12, 172, 110, 26);
		CustomUI.getInstance().setCustomBtn(btnDatTruoc);
		pnBillInfo.add(btnDatTruoc);

		btnThueNgay = new JButton("Thuê ngay", null);
		btnThueNgay.setBounds(132, 172, 110, 26);
		CustomUI.getInstance().setCustomBtn(btnThueNgay);
		pnBillInfo.add(btnThueNgay);

		btnHuyDatPhong = new JButton("Hủy đặt", null);
		btnHuyDatPhong.setBounds(248, 172, 110, 26);
		CustomUI.getInstance().setCustomBtn(btnHuyDatPhong);
		pnBillInfo.add(btnHuyDatPhong);

		JLabel lblTongTien = new JLabel("Tổng tiền: ");
		lblTongTien.setBounds(12, 105, 85, 20);
		pnBillInfo.add(lblTongTien);

		txtTotalPrice = new JTextField();
		txtTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalPrice.setText("0.0");
		txtTotalPrice.setEditable(false);
		txtTotalPrice.setBounds(85, 105, 157, 20);
		txtTotalPrice.setBackground(Color.decode("#f9f9f9"));
		pnBillInfo.add(txtTotalPrice);
		txtTotalPrice.setColumns(10);

		JLabel lbVND = new JLabel("(VND)");
		lbVND.setBounds(248, 105, 43, 20);
		pnBillInfo.add(lbVND);

		txtGTKetThuc = new JTextField("");
		txtGTKetThuc.setEditable(false);
		txtGTKetThuc.setBounds(85, 76, 157, 20);
		txtGTKetThuc.setBackground(Color.decode("#f9f9f9"));
		pnBillInfo.add(txtGTKetThuc);

		JLabel lbViTriP = new JLabel("Vị Trí Phòng:");
		lbViTriP.setBounds(248, 49, 82, 20);
		pnBillInfo.add(lbViTriP);

		txtViTriP = new JTextField();
		txtViTriP.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtViTriP.setEditable(false);
		txtViTriP.setColumns(10);
		txtViTriP.setBackground(new Color(249, 249, 249));
		txtViTriP.setBounds(328, 50, 150, 20);
		pnBillInfo.add(txtViTriP);

		JLabel lblLoaiPhong = new JLabel("Loại phòng:");
		lblLoaiPhong.setBounds(248, 76, 82, 20);
		pnBillInfo.add(lblLoaiPhong);

		txtTenLP = new JTextField();
		txtTenLP.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtTenLP.setEditable(false);
		txtTenLP.setColumns(10);
		txtTenLP.setBackground(new Color(249, 249, 249));
		txtTenLP.setBounds(328, 77, 150, 20);
		pnBillInfo.add(txtTenLP);

		JLabel lbKH = new JLabel("Tên KH:");
		lbKH.setBounds(12, 136, 85, 20);
		pnBillInfo.add(lbKH);

		btnChonKH = new JButton("Chọn KH", null);
		btnChonKH.setBounds(248, 133, 230, 26);
		CustomUI.getInstance().setCustomBtn(btnChonKH);
		pnBillInfo.add(btnChonKH);

		txtTenKH = new JTextField();
		txtTenKH.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtTenKH.setEditable(false);
		txtTenKH.setColumns(10);
		txtTenKH.setBackground(new Color(249, 249, 249));
		txtTenKH.setBounds(85, 136, 157, 20);
		pnBillInfo.add(txtTenKH);

		pnBill.add(pnBillList);
		pnMain.add(pnBill);
		getContentPane().add(pnMain);

		JPanel pnProduct = new JPanel();
		pnProduct.setBackground(Color.WHITE);
		pnProduct.setBorder(
				new TitledBorder(null, "Thông tin dịch vụ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnProduct.setBounds(819, 40, 445, 597);
		pnMain.add(pnProduct);
		pnProduct.setLayout(new BorderLayout(0, 0));

		JPanel pnControlProduct = new JPanel();
		pnControlProduct.setPreferredSize(new Dimension(445, 150));
		pnControlProduct.setBackground(Color.WHITE);
		pnProduct.add(pnControlProduct, BorderLayout.NORTH);
		pnControlProduct.setLayout(null);

		JLabel lbTenDV = new JLabel("Tên dịch vụ:");
		lbTenDV.setBounds(12, 12, 90, 16);
		pnControlProduct.add(lbTenDV);

		txtTenDichVu = new JTextField();
		txtTenDichVu.setBounds(110, 10, 170, 20);
		pnControlProduct.add(txtTenDichVu);
		txtTenDichVu.setColumns(10);

		btnTimDichVu = new JButton("Tìm", searchIcon);
		btnTimDichVu.setBounds(292, 7, 131, 26);
		CustomUI.getInstance().setCustomBtn(btnTimDichVu);
		pnControlProduct.add(btnTimDichVu);

		JLabel lbSLDVDat = new JLabel("Số lượng đặt: ");
		lbSLDVDat.setBounds(12, 38, 90, 16);
		pnControlProduct.add(lbSLDVDat);

		spinSLDat = new JSpinner();
		spinSLDat.setModel(new SpinnerNumberModel(1, 1, null, 1));
		spinSLDat.setBounds(110, 35, 170, 20);
		pnControlProduct.add(spinSLDat);

		btnThem = new JButton("Thêm", addIcon);
		btnThem.setBounds(292, 40, 131, 26);
		CustomUI.getInstance().setCustomBtn(btnThem);
		pnControlProduct.add(btnThem);

		btnXoa = new JButton("Hủy", trashIcon);
		btnXoa.setBounds(292, 76, 131, 26);
		CustomUI.getInstance().setCustomBtn(btnXoa);
		pnControlProduct.add(btnXoa);

		JLabel lbSLDVCon = new JLabel("Số lượng còn: ");
		lbSLDVCon.setBounds(12, 65, 90, 16);
		pnControlProduct.add(lbSLDVCon);

		JLabel lbGiaDV = new JLabel("Giá bán: ");
		lbGiaDV.setBounds(12, 92, 90, 16);
		pnControlProduct.add(lbGiaDV);

		txtSLCon = new JTextField();
		txtSLCon.setEditable(false);
		txtSLCon.setColumns(10);
		txtSLCon.setBounds(110, 63, 170, 20);
		pnControlProduct.add(txtSLCon);

		txtGiaBan = new JTextField();
		txtGiaBan.setEditable(false);
		txtGiaBan.setColumns(10);
		txtGiaBan.setBounds(110, 90, 170, 20);
		pnControlProduct.add(txtGiaBan);

		btnLamMoiDV = new JButton("Làm mới", refreshIcon);
		btnLamMoiDV.setBounds(292, 113, 131, 26);
		CustomUI.getInstance().setCustomBtn(btnLamMoiDV);
		pnControlProduct.add(btnLamMoiDV);

		JPanel pnProductList = new JPanel();
		pnProductList.setBackground(Color.WHITE);
		pnProductList.setBorder(
				new TitledBorder(null, "Danh sách dịch vụ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnProduct.add(pnProductList, BorderLayout.CENTER);

		String[] colsProduct = { "STT", "Tên sản phẩm", "SL còn", "Đơn giá" };
		modelTableDichVu = new DefaultTableModel(colsProduct, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		pnProductList.setLayout(new BorderLayout(0, 0));
		tableDichVu = new JTable(modelTableDichVu);

		JScrollPane scpProductList = new JScrollPane(tableDichVu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scpProductList.getViewport().setBackground(Color.WHITE);
		pnProductList.add(scpProductList);

		btnChuyenPhong.addActionListener(this);
		btnLamMoiDV.addActionListener(this);
		btnTimDichVu.addActionListener(this);
		btnThanhToan.addActionListener(this);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnThueNgay.addActionListener(this);
		btnHuyDatPhong.addActionListener(this);
		btnLamMoiPhong.addActionListener(this);
		btnDatTruoc.addActionListener(this);
		btnChonKH.addActionListener(this);

		btnChuyenPhong.addMouseListener(this);
		btnLamMoiDV.addMouseListener(this);
		btnTimDichVu.addMouseListener(this);
		btnThanhToan.addMouseListener(this);
		btnThem.addMouseListener(this);
		btnXoa.addMouseListener(this);
		btnThueNgay.addMouseListener(this);
		btnHuyDatPhong.addMouseListener(this);
		btnLamMoiPhong.addMouseListener(this);
		btnDatTruoc.addMouseListener(this);
		btnChonKH.addMouseListener(this);
		tableDichVu.addMouseListener(this);
		tableBill.addMouseListener(this);

		changeAccount(staffLogin);
		LoadDSPhong(PhongDAO.getInstance().getDSPhong());
		// reSizeColumnTableBillInfo();
		loadCboLoaiPhong();
		loadCboPhong();
		loadDSDichVu(DichVuDAO.getInstance().getDSDichVu());
		reSizeColumnTableDichVu();
	}

	public static void main(String[] args) {
		NhanVien staff = NhanVienDAO.getInstance().getNhanVienByTenDangNhap("phamdangdan");
		new fQuanLyDatPhong(staff).setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			fDieuHuong f = new fDieuHuong(staffLogin);
			this.setVisible(false);
			f.setVisible(true);
		} else if (o.equals(btnThueNgay)) {
			if (txtMaPhong.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
			}
			if (txtTenKH.getText().trim().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn khách hàng thuê trước");
			}
			CTPhong ctPhong = new CTPhong();
			boolean ketQuaCTPhong = CTPhongDAO.getInstance().themCTPhongDAO(ctPhong);
			if (!ketQuaCTPhong) {
				JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
			} else {
				HoaDon hoaDon = new HoaDon();
				boolean ketQuaHD = HoaDonDAO.getInstance().themHoaDon(hoaDon);
			}
		} else if (o.equals(btnThem) || o.equals(btnXoa)) {
			if (txtMaPhong.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
			}
			if (txtTenDichVu.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn sản phẩm khách gọi");
			} else {
				int count = (int) spinSLDat.getValue();
				if (o.equals(btnXoa))
					count *= -1;
				String tenDichVu = txtTenDichVu.getText().trim();
				DichVu dichVu = DichVuDAO.getInstance().getDichVuByTenDichVu(tenDichVu);
				String maDichVu = dichVu.getMaDichVu();
				String maPhong = txtMaPhong.getText().trim();
				Phong phong = PhongDAO.getInstance().getPhongByMaPhong(maPhong);
				HoaDon hoaDon = HoaDonDAO.getInstance().getUncheckHoaDonByMaPhong(maPhong);
				int maHoaDon = hoaDon.getMaHoaDon();
				// create new bill
				// if (maHoaDon == -1) {
				// HoaDonDAO.getInstance().insertBill(maPhong);
				// maHoaDon = HoaDonDAO.getInstance().getMaxIDBill();
				// // create new bill info
				// CTDichVuDAO.getInstance().insertCTDichVuDAO(maHoaDon, maDichVu, count);
				// } else {
				// // create new bill info
				// CTDichVuDAO.getInstance().insertCTDichVuDAO(maHoaDon, maDichVu, count);
				// }
				showBill(maPhong);
				loadPhong(maPhong);
				txtMaHoaDon.setText(String.valueOf(maHoaDon));
				btnThanhToan.setEnabled(true);
			}
		} else if (o.equals(btnTimDichVu)) {
			String tenDichVu = txtTenDichVu.getText().trim();
			if (tenDichVu.equals("")) {
				JOptionPane.showMessageDialog(this, "Tên sản phẩm không được rỗng");
			} else {
				ArrayList<DichVu> dsDichVu = DichVuDAO.getInstance().getDSDichVuByTenDichVu(tenDichVu);
				loadDSDichVu(dsDichVu);
			}
		} else if (o.equals(btnThanhToan)) {
			// String tableName = txtTableName.getText().trim();
			// Table table = TableDAO.getInstance().getTableByTableName(tableName);
			// int tableID = table.getId();
			// int discount = (int) spinDiscount.getValue();
			// int billID = BillDAO.getInstance().getUncheckBillByTableID(tableID);
			// String totalPricePayment = txtPayment.getText().trim();
			// double totalPrice = Double.parseDouble(totalPricePayment.replace(",", ""));
			// if (billID != -1) {
			// String message = String.format(
			// "Bạn có chắc chắn thanh toán hóa đơn cho %s \nSố tiền khách hàng cần phải trả
			// là: %s VND",
			// tableName, totalPricePayment);
			// int select = JOptionPane.showConfirmDialog(this, message, "Xác nhận thanh
			// toán",
			// JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			// if (select == JOptionPane.OK_OPTION) {
			// BillDAO.getInstance().checkOut(billID, discount, totalPrice);
			// showBill(tableID);
			// loadTable(tableID);
			// txtBillID.setText("");
			// spinCount.setValue(0);
			// }
			// }
		} else if (o.equals(btnLamMoiPhong)) {
			String tenLoaiPhong = cboLoaiPhong.getSelectedItem().toString();
			loadDSPhongByTenLoaiPhong(tenLoaiPhong);
		} else if (o.equals(btnLamMoiPhong)) {
			loadDSDichVu(DichVuDAO.getInstance().getDSDichVu());
		} else if (o.equals(btnChuyenPhong)) {
			// String tableName1 = txtTableName.getText().trim();
			// Table table1 = TableDAO.getInstance().getTableByTableName(tableName1);
			// int tableID1 = table1.getId();

			// String tableName2 = cboTableName.getSelectedItem().toString().trim();
			// Table table2 = TableDAO.getInstance().getTableByTableName(tableName2);
			// int tableID2 = table2.getId();

			// String message = String.format("Bạn có chắc chắn muốn chuyển từ bàn %d qua
			// bàn %d", tableID1, tableID2);
			// int select = JOptionPane.showConfirmDialog(this, message, "Xác nhận chuyển
			// bàn",
			// JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			// if (select == JOptionPane.OK_OPTION) {
			// TableDAO.getInstance().switchTable(tableID1, tableID2);
			// loadTable(tableID1);
			// loadTable(tableID2);
			// showBill(tableID1);
			// showBill(tableID2);
			// }
		} else if (o.equals(btnHuyDatPhong)) {
		} else if (o.equals(btnDatTruoc)) {
		} else if (o.equals(btnChonKH)) {
			DialogChonKhachHang chonKH = new DialogChonKhachHang();
			chonKH.setModal(true);
			chonKH.setVisible(true);
			khachHangDcChon = chonKH.getKHDuocChon();
			System.out.println(khachHangDcChon.toString());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		DecimalFormat df = new DecimalFormat("#,###.##");
		if (o.equals(tableDichVu)) {
			int row = tableDichVu.getSelectedRow();
			String tenDichVu = modelTableDichVu.getValueAt(row, 1).toString();
			DichVu dichVu = DichVuDAO.getInstance().getDichVuByTenDichVu(tenDichVu);
			txtTenDichVu.setText(tenDichVu);
			spinSLDat.setValue(1);
			txtSLCon.setText(df.format(dichVu.getSoLuongTon()));
			txtGiaBan.setText(df.format(dichVu.getGiaBan()));
		} else if (o.equals(tableBill)) {
			int row = tableBill.getSelectedRow();
			String productName = modelTableBill.getValueAt(row, 1).toString();
			txtTenDichVu.setText(productName);
			int count = Integer.parseInt(modelTableBill.getValueAt(row, 3).toString());
			String giaTien = modelTableBill.getValueAt(row, 2).toString();
			txtGiaBan.setText(giaTien);
			txtSLCon.setText("");
			spinSLDat.setValue(count);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(btnChuyenPhong)) {
			CustomUI.getInstance().setCustomBtnHover(btnChuyenPhong);
		} else if (o.equals(btnLamMoiPhong)) {
			CustomUI.getInstance().setCustomBtnHover(btnLamMoiPhong);
		} else if (o.equals(btnTimDichVu)) {
			CustomUI.getInstance().setCustomBtnHover(btnTimDichVu);
		} else if (o.equals(btnThanhToan)) {
			CustomUI.getInstance().setCustomBtnHover(btnThanhToan);
		} else if (o.equals(btnThem)) {
			CustomUI.getInstance().setCustomBtnHover(btnThem);
		} else if (o.equals(btnXoa)) {
			CustomUI.getInstance().setCustomBtnHover(btnXoa);
		} else if (o.equals(btnBack)) {
			CustomUI.getInstance().setCustomBtnHover(btnBack);
		} else if (o.equals(btnLamMoiDV)) {
			CustomUI.getInstance().setCustomBtnHover(btnLamMoiDV);
		} else if (o.equals(btnThueNgay)) {
			CustomUI.getInstance().setCustomBtnHover(btnThueNgay);
		} else if (o.equals(btnDatTruoc)) {
			CustomUI.getInstance().setCustomBtnHover(btnDatTruoc);
		} else if (o.equals(btnHuyDatPhong)) {
			CustomUI.getInstance().setCustomBtnHover(btnHuyDatPhong);
		} else if (o.equals(btnChonKH)) {
			CustomUI.getInstance().setCustomBtnHover(btnChonKH);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(btnChuyenPhong)) {
			CustomUI.getInstance().setCustomBtn(btnChuyenPhong);
		} else if (o.equals(btnLamMoiPhong)) {
			CustomUI.getInstance().setCustomBtn(btnLamMoiPhong);
		} else if (o.equals(btnTimDichVu)) {
			CustomUI.getInstance().setCustomBtn(btnTimDichVu);
		} else if (o.equals(btnThanhToan)) {
			CustomUI.getInstance().setCustomBtn(btnThanhToan);
		} else if (o.equals(btnThem)) {
			CustomUI.getInstance().setCustomBtn(btnThem);
		} else if (o.equals(btnXoa)) {
			CustomUI.getInstance().setCustomBtn(btnXoa);
		} else if (o.equals(btnBack)) {
			CustomUI.getInstance().setCustomBtn(btnBack);
		} else if (o.equals(btnLamMoiDV)) {
			CustomUI.getInstance().setCustomBtn(btnLamMoiDV);
		} else if (o.equals(btnThueNgay)) {
			CustomUI.getInstance().setCustomBtn(btnThueNgay);
		} else if (o.equals(btnDatTruoc)) {
			CustomUI.getInstance().setCustomBtn(btnDatTruoc);
		} else if (o.equals(btnHuyDatPhong)) {
			CustomUI.getInstance().setCustomBtn(btnHuyDatPhong);
		} else if (o.equals(btnChonKH)) {
			CustomUI.getInstance().setCustomBtn(btnChonKH);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboLoaiPhong)) {
			String tenLoaiPhong = cboLoaiPhong.getSelectedItem().toString();
			loadDSPhongByTenLoaiPhong(tenLoaiPhong);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// Object o = e.getSource();
		// if (o.equals(spinDiscount)) {
		// int discount = (int) spinDiscount.getValue();
		// double totalPrice = Double.parseDouble(txtTotalPrice.getText().replace(",",
		// ""));
		// double totalPricePayment = totalPrice - (totalPrice / 100) * discount;
		// DecimalFormat df = new DecimalFormat("#,###.##");
		// txtPayment.setText(df.format(totalPricePayment));
		// }
	}

	// mô tả: Bắt sự kiện khi click btn close(x), sẽ show 1 form xác nhận đăng xuất
	// hay thoát chương trình
	public void setCloseAction(JFrame jframe) {
		jframe.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				fDieuHuong f = new fDieuHuong(staffLogin);
				jframe.setVisible(false);
				f.setVisible(true);
			}
		});
	}

	private String getTinhTrangPhong(int tinhTrang) {
		// 0 là đã cho thuê | 1 là là còn trống | 2 là đặt trước
		String status = "Trống";
		if (tinhTrang == 0)
			status = "Đã cho thuê";
		else if (tinhTrang == 2)
			status = "Đặt trước";
		return status;
	}

	private void loadPhong(String maPhong) {
		Phong phong = PhongDAO.getInstance().getPhongByMaPhong(maPhong);
		String status = getTinhTrangPhong(phong.getTinhTrangP());
		String tenPhong = phong.getMaPhong();
		String tenBtn = "<html><p style='text-align: center;'> " + tenPhong
				+ " </p></br><p style='text-align: center;'> " + status + " </p></html>";
		int index = 0;
		for (int i = 0; i < btnDSPhong.length; i++) {
			if (btnDSPhong[i].getText().contains(tenPhong))
				index = i;
			else if (btnDSPhong[i].getText().equals("")) {
				index = i;
				break;
			}
		}
		btnDSPhong[index].setText(tenBtn);
		btnDSPhong[index].setForeground(Color.WHITE);
		btnDSPhong[index].setVerticalTextPosition(SwingConstants.BOTTOM);
		btnDSPhong[index].setHorizontalTextPosition(SwingConstants.CENTER);
		btnDSPhong[index].setPreferredSize(new Dimension(PhongDAO.ROOM_WIDTH, PhongDAO.ROOM_HEIGHT));
		btnDSPhong[index].setIcon(phongIcon);
		switch (status) {
			case "Trống":
				btnDSPhong[index].setBackground(Color.decode("#00a65a"));
				btnThanhToan.setEnabled(false);
				break;
			case "Đặt trước":
				btnDSPhong[index].setBackground(Color.decode("#605ca8"));
				btnThanhToan.setEnabled(false);
				break;
			default:
				btnDSPhong[index].setBackground(Color.decode("#3c8dbc"));
				break;
		}
		pnShowRoom.revalidate();
		pnShowRoom.repaint();
	}

	private void LoadDSPhong(ArrayList<Phong> dsPhong) {
		heightTable = 140;
		pnShowRoom.removeAll();
		pnShowRoom.revalidate();
		pnShowRoom.repaint();
		Border lineBlue = new LineBorder(Color.RED, 2);
		Border lineGray = new LineBorder(Color.GRAY, 1);
		int sizeDSPhong = dsPhong.size();
		btnDSPhong = new JButton[sizeDSPhong];
		for (int i = 0; i < sizeDSPhong; i++) {
			final int selection = i;
			Phong phong = dsPhong.get(i);
			String maPhong = phong.getMaPhong();
			btnDSPhong[selection] = new JButton("");
			loadPhong(maPhong);
			btnDSPhong[selection].setBorder(lineGray);
			if ((i + 1) % 3 == 0) {
				heightTable += PhongDAO.ROOM_HEIGHT;
				pnShowRoom.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));
			}
			btnDSPhong[selection].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (viTri != -1) {
						btnDSPhong[viTri].setBorder(lineGray);
					}
					viTri = selection;
					btnDSPhong[selection].setBorder(lineBlue);
					showBill(maPhong);
					txtMaPhong.setText(phong.getMaPhong());
					HoaDon hoaDon = HoaDonDAO.getInstance().getUncheckHoaDonByMaPhong(maPhong);
					if (hoaDon != null) {
						txtMaHoaDon.setText(String.valueOf(hoaDon.getMaHoaDon()));
						CTPhong ctPhong = hoaDon.getCtPhong();
						String format = "dd-MM-yyyy HH:mm:ss";
						Timestamp ngayGioNhan = ctPhong.getNgayGioNhan();
						Timestamp ngayGioTra = ctPhong.getNgayGioTra();
						String ngayGioNhanStr = CustomUI.getInstance()
								.convertSqlTimestampToUtilDateFormatString(ngayGioNhan, format);
						String ngayGioTraStr = CustomUI.getInstance()
								.convertSqlTimestampToUtilDateFormatString(ngayGioTra, format);
						txtTGBatDau.setText(ngayGioNhanStr);
						txtGTKetThuc.setText(ngayGioTraStr);
					} else
						txtMaHoaDon.setText("");
					spinSLDat.setValue(1);
					Phong phongActiveE = PhongDAO.getInstance().getPhongByMaPhong(maPhong);
					txtViTriP.setText(phongActiveE.getViTri());
					txtTenLP.setText(phongActiveE.getLoaiPhong().getTenLP());
					String status = getTinhTrangPhong(phongActiveE.getTinhTrangP());
					switch (status) {
						case "Trống":
							btnThueNgay.setEnabled(true);
							btnDatTruoc.setEnabled(true);
							btnHuyDatPhong.setEnabled(false);
							btnThanhToan.setEnabled(false);
							btnChonKH.setEnabled(true);
							break;
						case "Đặt trước":
							btnThueNgay.setEnabled(false);
							btnDatTruoc.setEnabled(false);
							btnHuyDatPhong.setEnabled(true);
							btnThanhToan.setEnabled(false);
							btnChonKH.setEnabled(false);
							break;
						default:
							btnThueNgay.setEnabled(false);
							btnDatTruoc.setEnabled(false);
							btnHuyDatPhong.setEnabled(false);
							btnThanhToan.setEnabled(true);
							btnChonKH.setEnabled(false);
							break;
					}
				}
			});
			// sự kiện hover chuột
			btnDSPhong[selection].addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {
					btnDSPhong[selection].setBackground(Color.decode("#bbdefc"));
					btnDSPhong[selection].setForeground(Color.decode("#1a66e3"));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					Phong phongActiveE = PhongDAO.getInstance().getPhongByMaPhong(maPhong);
					String status = getTinhTrangPhong(phongActiveE.getTinhTrangP());
					switch (status) {
						case "Trống":
							btnDSPhong[selection].setBackground(Color.decode("#00a65a"));
							break;
						case "Đặt trước":
							btnDSPhong[selection].setBackground(Color.decode("#605ca8"));
							break;
						default:
							btnDSPhong[selection].setBackground(Color.decode("#3c8dbc"));
							break;
					}
					btnDSPhong[selection].setForeground(Color.WHITE);
				}
			});
			pnShowRoom.add(btnDSPhong[selection]);
		}
	}

	private void loadCboPhong() {
		ArrayList<Phong> dsPhong = PhongDAO.getInstance().getDSPhong();
		for (Phong phong : dsPhong) {
			cboMaPhong.addItem(phong.getMaPhong());
		}
	}

	private void showBill(String maPhong) {
		ArrayList<CTDichVu> dataList = CTDichVuDAO.getInstance().getCTDichVuListByMaPhong(maPhong);
		DecimalFormat df = new DecimalFormat("#,###.##");
		int i = 1;
		modelTableBill.getDataVector().removeAllElements();
		modelTableBill.fireTableDataChanged();
		double totalPrice = 0;
		for (CTDichVu item : dataList) {
			totalPrice += item.getTienDichVu();
			String stt = df.format(i++);
			String totalPriceStr = df.format(item.getTienDichVu());
			String priceStr = df.format(item.getDichVu().getGiaBan());
			String countStr = df.format(item.getSoLuongDat());
			modelTableBill
					.addRow(new Object[] { stt, item.getDichVu().getTenDichVu(), priceStr, countStr, totalPriceStr });
		}
		txtTotalPrice.setText(df.format(totalPrice));
	}

	private void loadCboLoaiPhong() {
		ArrayList<LoaiPhong> dataList = LoaiPhongDAO.getInstance().getDSLoaiPhong();
		cboLoaiPhong.addItem("Tất cả");
		for (LoaiPhong item : dataList) {
			cboLoaiPhong.addItem(item.getTenLP());
		}
	}

	private void loadDSPhongByTenLoaiPhong(String tenLP) {
		ArrayList<Phong> dataList = null;
		if (tenLP.equalsIgnoreCase("Tất cả"))
			dataList = PhongDAO.getInstance().getDSPhong();
		else
			dataList = PhongDAO.getInstance().getDSPhongByTenLoaiPhong(tenLP);
		LoadDSPhong(dataList);
	}

	private void loadDSDichVu(ArrayList<DichVu> dataList) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		int i = 1;
		modelTableDichVu.getDataVector().removeAllElements();
		modelTableDichVu.fireTableDataChanged();
		for (DichVu item : dataList) {
			String stt = df.format(i++);
			String giaStr = df.format(item.getGiaBan());
			String slConStr = df.format(item.getSoLuongTon());
			modelTableDichVu.addRow(new Object[] { stt, item.getTenDichVu(), slConStr, giaStr });
		}
	}

	private void changeAccount(NhanVien staff) {
		lbTenNV.setText(staff.getHoTen());
	}

	private void reSizeColumnTableBillInfo() {
		tableBill.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableBill.getColumnModel().getColumn(1).setPreferredWidth(110);
		tableBill.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableBill.getColumnModel().getColumn(3).setPreferredWidth(45);
		tableBill.getColumnModel().getColumn(4).setPreferredWidth(80);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableBill.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tableBill.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tableBill.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tableBill.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
	}

	private void reSizeColumnTableDichVu() {
		tableDichVu.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableDichVu.getColumnModel().getColumn(1).setPreferredWidth(210);
		tableDichVu.getColumnModel().getColumn(2).setPreferredWidth(40);
		tableDichVu.getColumnModel().getColumn(3).setPreferredWidth(40);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableDichVu.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tableDichVu.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tableDichVu.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	}
}