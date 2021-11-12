package UI.PanelCustom;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.border.*;
import javax.swing.table.*;

import DAO.*;
import entity.*;

public class DialogHoaDon extends JFrame {
	private JTextField txtBillId, txtStaffName, txtCustomerName, txtRoomId, txtRoomTypeName, txtRoomPrice, txtStartTime,
			txtEndTime, txtUsedTime, txtTotalPriceService, txtTotalPriceRoom, txtVAT, txtTotalPriceBill;
	private JTable tblTableBillInfo;
	private DefaultTableModel modelTableBillInfo;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	private MyButton btnAbate, btnBack, btnInvoiced;

	private String formatTime = "HH:mm:ss dd/MM/yyyy";
	private DecimalFormat df = new DecimalFormat("#,###.##");
	private HoaDon bill = new HoaDon();

	public DialogHoaDon(String billId) {
		this.bill = HoaDonDAO.getInstance().getBillByBillId(billId);
		Phong room = PhongDAO.getInstance().getRoomByBillId(billId);
		bill.setPhong(room);
		NhanVien staff = NhanVienDAO.getInstance().getStaffByBillId(billId);
		bill.setNhanVien(staff);
		KhachHang customer = KhachHangDAO.getInstance().getCustomerByBillId(billId);
		bill.setKhachHang(customer);
		ArrayList<CTDichVu> billInfoList = CTDichVuDAO.getInstance().getServiceDetailListByBillId(billId);
		bill.setDsCTDichVu(billInfoList);

		setSize(800, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		this.setLayout(null);
		setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		this.setBackground(new Color(255, 255, 255, 0));

		JPanel pnMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(60, 179, 113));
				g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
				setFont(new Font("Dialog", Font.BOLD, 16));
			}
		};
		pnMain.setLayout(null);
		pnMain.setOpaque(false);
		pnMain.setBounds(0, 0, 800, 720);
		this.add(pnMain);

		JLabel lblKaraokeName = new JLabel(
				"<HTML><h1 style=\"margin: 10px 0px -20px 0px;\">KARAOKE DASH </h1> <hr> <p> <i> 12 Nguyễn Văn Bảo, Phường 4, Gò Vấp, Thành phố Hồ Chí Minh </i> </p></HTML>") {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				Image bgMain = CustomUI.LOGO_ICON.getImage();
				g2.drawImage(bgMain, 0, 0, 100, 100, null);
			}
		};
		lblKaraokeName.setVerticalAlignment(SwingConstants.TOP);
		lblKaraokeName.setForeground(Color.WHITE);
		lblKaraokeName.setBorder(BorderFactory.createEmptyBorder(0, 100, 5, 5));
		lblKaraokeName.setHorizontalAlignment(SwingConstants.CENTER);
		lblKaraokeName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblKaraokeName.setBounds(225, 0, 350, 100);
		pnMain.add(lblKaraokeName);

		JLabel lblBillName = new JLabel("HÓA ĐƠN TÍNH TIỀN");
		lblBillName.setForeground(Color.WHITE);
		lblBillName.setFont(new Font("Dialog", Font.BOLD, 24));
		lblBillName.setHorizontalAlignment(SwingConstants.CENTER);
		lblBillName.setBounds(225, 120, 350, 40);
		pnMain.add(lblBillName);

		JPanel pnInfoService = new JPanel();
		pnInfoService.setLayout(null);
		pnInfoService.setBounds(10, 325, 780, 235);
		pnInfoService.setOpaque(false);
		Border a = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.white), "Chi tiết dịch vụ",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 16), Color.WHITE);
		pnInfoService.setBorder(a);

		pnMain.add(pnInfoService);

		JPanel pnTable = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.decode("#ffffff"));
				g2.drawRect(0, 0, 759, getHeight() - 1);

				g2.drawLine(0, 24, 759, 24);
				setFont(new Font("Dialog", Font.BOLD, 16));

			}
		};
		pnTable.setLayout(null);
		pnTable.setOpaque(false);
		pnTable.setBounds(10, 25, 760, 200);
		pnInfoService.add(pnTable);

		String[] colsBillInfo = { "STT", "Tên dịch vụ ", "SL", "Đơn giá", "Thành tiền" };
		modelTableBillInfo = new DefaultTableModel(colsBillInfo, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		tblTableBillInfo = new JTable(modelTableBillInfo) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				setFont(new Font("Dialog", Font.BOLD, 16));
			}
		};
		CustomUI.getInstance().setCustomTable(tblTableBillInfo);
		tblTableBillInfo.setRowHeight(25);
		tblTableBillInfo.setFont(new Font("Dialog", Font.PLAIN, 16));
		tblTableBillInfo.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 16));
		tblTableBillInfo.getTableHeader().setBackground(new Color(255, 255, 255, 0));
		tblTableBillInfo.setOpaque(false);
		tblTableBillInfo.setShowGrid(false);
		JScrollPane scrTableBillInfo = CustomUI.getInstance().setCustomScrollPane(tblTableBillInfo);
		scrTableBillInfo.setBounds(1, 1, 777, 199);
		pnTable.add(scrTableBillInfo);

		JLabel lblTotalPriceService = new JLabel("Tổng tiền dịch vụ:");
		CustomUI.getInstance().setCustomLabelBill(lblTotalPriceService);
		lblTotalPriceService.setBounds(40, 560, 140, 25);
		pnMain.add(lblTotalPriceService);

		txtTotalPriceService = new JTextField("");
		CustomUI.getInstance().setCustomTextFieldBill(txtTotalPriceService);
		txtTotalPriceService.setBounds(605, 560, 200, 25);
		pnMain.add(txtTotalPriceService);

		JLabel lblTotalPriceRoom = new JLabel("Tổng tiền giờ:");
		CustomUI.getInstance().setCustomLabelBill(lblTotalPriceRoom);
		lblTotalPriceRoom.setBounds(40, 585, 140, 25);
		pnMain.add(lblTotalPriceRoom);

		txtTotalPriceRoom = new JTextField("");
		CustomUI.getInstance().setCustomTextFieldBill(txtTotalPriceRoom);
		txtTotalPriceRoom.setBounds(605, 585, 200, 25);
		pnMain.add(txtTotalPriceRoom);

		JLabel lblVAT = new JLabel("VAT(10%):");
		CustomUI.getInstance().setCustomLabelBill(lblVAT);
		lblVAT.setBounds(40, 610, 140, 25);
		pnMain.add(lblVAT);

		txtVAT = new JTextField("");
		CustomUI.getInstance().setCustomTextFieldBill(txtVAT);
		txtVAT.setBounds(605, 610, 200, 25);
		pnMain.add(txtVAT);

		JLabel lblTotalPriceBill = new JLabel("Tổng cộng:");
		lblTotalPriceBill.setForeground(Color.WHITE);
		lblTotalPriceBill.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTotalPriceBill.setBounds(40, 635, 140, 25);
		pnMain.add(lblTotalPriceBill);

		txtTotalPriceBill = new JTextField("");
		CustomUI.getInstance().setCustomTextFieldBill(txtTotalPriceBill);
		txtTotalPriceBill.setFont(new Font("Dialog", Font.BOLD, 18));
		txtTotalPriceBill.setColumns(10);
		txtTotalPriceBill.setHorizontalAlignment(SwingConstants.LEFT);
		txtTotalPriceBill.setBounds(605, 635, 200, 25);
		pnMain.add(txtTotalPriceBill);

		btnBack = new MyButton(130, 35, "Quay lại", gra, null, 32, 19);
		((MyButton) btnBack).setFontCustom(new Font("Dialog", Font.BOLD, 15));
		btnBack.setBounds(40, 670, 130, 35);
		pnMain.add(btnBack);

		btnAbate = new MyButton(130, 35, "Thanh toán", gra, null, 23, 19);
		((MyButton) btnAbate).setFontCustom(new Font("Dialog", Font.BOLD, 15));
		btnAbate.setBounds(240, 670, 130, 35);
		pnMain.add(btnAbate);

		btnInvoiced = new MyButton(130, 35, "Xuất PDF", gra, null, 27, 19);
		((MyButton) btnInvoiced).setFontCustom(new Font("Dialog", Font.BOLD, 15));
		btnInvoiced.setBounds(435, 670, 130, 35);
		pnMain.add(btnInvoiced);

		btnInvoiced = new MyButton(130, 35, "Xuất excel", gra, null, 27, 19);
		((MyButton) btnInvoiced).setFontCustom(new Font("Dialog", Font.BOLD, 15));
		btnInvoiced.setBounds(625, 670, 130, 35);
		pnMain.add(btnInvoiced);

		JLabel txtPhoneNumber = new JLabel("0303.030.303");
		txtPhoneNumber.setBackground(Color.WHITE);
		txtPhoneNumber.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtPhoneNumber.setForeground(Color.WHITE);
		txtPhoneNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPhoneNumber.setBounds(225, 100, 350, 20);
		pnMain.add(txtPhoneNumber);

		JPanel panel = new JPanel();
		Border b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.white), "Thông tin hóa đơn",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 16), Color.WHITE);
		panel.setBounds(10, 150, 780, 170);
		panel.setBorder(b);
		panel.setOpaque(false);
		pnMain.add(panel);
		panel.setLayout(null);

		JLabel lblBillId = new JLabel("Mã hóa đơn:");
		lblBillId.setBounds(25, 23, 140, 25);
		panel.add(lblBillId);
		CustomUI.getInstance().setCustomLabelBill(lblBillId);

		JLabel lblStaffName = new JLabel("Thu ngân:");
		lblStaffName.setBounds(25, 48, 140, 25);
		panel.add(lblStaffName);
		CustomUI.getInstance().setCustomLabelBill(lblStaffName);

		JLabel lblCustomerName = new JLabel("Tên khách hàng:");
		lblCustomerName.setBounds(25, 73, 140, 25);
		panel.add(lblCustomerName);
		CustomUI.getInstance().setCustomLabelBill(lblCustomerName);

		JLabel lblRoomId = new JLabel("Số phòng:");
		lblRoomId.setBounds(25, 98, 140, 25);
		panel.add(lblRoomId);
		CustomUI.getInstance().setCustomLabelBill(lblRoomId);

		JLabel lblRoomTypeName = new JLabel("Loại phòng:");
		lblRoomTypeName.setBounds(25, 123, 140, 25);
		panel.add(lblRoomTypeName);
		CustomUI.getInstance().setCustomLabelBill(lblRoomTypeName);

		JLabel lblRoomPrice = new JLabel("Giá phòng:");
		lblRoomPrice.setBounds(440, 23, 140, 25);
		panel.add(lblRoomPrice);
		CustomUI.getInstance().setCustomLabelBill(lblRoomPrice);

		JLabel lblStartTime = new JLabel("Giờ bắt đầu:");
		lblStartTime.setBounds(440, 48, 140, 25);
		panel.add(lblStartTime);
		CustomUI.getInstance().setCustomLabelBill(lblStartTime);

		JLabel lblEndTime = new JLabel("Giờ kết thúc:");
		lblEndTime.setBounds(440, 73, 140, 25);
		panel.add(lblEndTime);
		CustomUI.getInstance().setCustomLabelBill(lblEndTime);

		JLabel lblUsedTime = new JLabel("Thời gian sử dụng:");
		lblUsedTime.setBounds(440, 98, 140, 25);
		panel.add(lblUsedTime);
		CustomUI.getInstance().setCustomLabelBill(lblUsedTime);

		txtBillId = new JTextField("");
		txtBillId.setBounds(160, 23, 220, 25);
		CustomUI.getInstance().setCustomTextFieldBill(txtBillId);
		panel.add(txtBillId);

		txtStaffName = new JTextField("");
		txtStaffName.setBounds(160, 48, 220, 25);
		panel.add(txtStaffName);
		CustomUI.getInstance().setCustomTextFieldBill(txtStaffName);

		txtCustomerName = new JTextField("");
		txtCustomerName.setBounds(160, 73, 220, 25);
		panel.add(txtCustomerName);
		CustomUI.getInstance().setCustomTextFieldBill(txtCustomerName);

		txtRoomId = new JTextField("");
		txtRoomId.setBounds(160, 98, 220, 25);
		panel.add(txtRoomId);
		CustomUI.getInstance().setCustomTextFieldBill(txtRoomId);

		txtRoomTypeName = new JTextField("");
		txtRoomTypeName.setBounds(160, 123, 220, 25);
		panel.add(txtRoomTypeName);
		CustomUI.getInstance().setCustomTextFieldBill(txtRoomTypeName);

		txtRoomPrice = new JTextField("");
		txtRoomPrice.setBounds(588, 23, 150, 25);
		panel.add(txtRoomPrice);
		CustomUI.getInstance().setCustomTextFieldBill(txtRoomPrice);

		txtStartTime = new JTextField("");
		txtStartTime.setBounds(588, 48, 150, 25);
		panel.add(txtStartTime);
		CustomUI.getInstance().setCustomTextFieldBill(txtStartTime);

		txtEndTime = new JTextField("");
		txtEndTime.setBounds(588, 73, 150, 25);
		panel.add(txtEndTime);
		CustomUI.getInstance().setCustomTextFieldBill(txtEndTime);

		txtUsedTime = new JTextField("");
		txtUsedTime.setBounds(588, 98, 150, 25);
		panel.add(txtUsedTime);
		CustomUI.getInstance().setCustomTextFieldBill(txtUsedTime);

		allLoaded();
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			String billId = "HD2021100100001";
			new DialogHoaDon(billId).setVisible(true);
		});
	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	public void allLoaded() {
		reSizeColumnTableBillInfo();
		showBillInfo();
		showTotalBill();
	}

	/**
	 * Chuyển số giờ thuê thành chuỗi dạng {@code x giờ y phút}
	 * 
	 * @param hours {@code double}: số giờ thuê
	 * @return {@code String}: chuỗi dạng {@code x giờ y phút}
	 */
	private String convertRentalTime(double hours) {
		int minutes = (int) (hours % 1 * 60);
		int hoursInt = (int) hours;
		String minutesStr = minutes < 10 ? "0" + minutes : minutes + "";
		String hoursStr = hoursInt < 10 ? "0" + hoursInt : hoursInt + "";
		String time = String.format("%s giờ %s phút", hoursStr, minutesStr);
		return time;
	}

	/**
	 * Hiển thị thông tin hóa đơn
	 */
	private void showBillInfo() {
		LoaiPhong roomType = bill.getPhong().getLoaiPhong();
		txtBillId.setText(bill.getMaHoaDon());
		txtStaffName.setText(bill.getNhanVien().getHoTen());
		txtCustomerName.setText(bill.getKhachHang().getHoTen());
		txtRoomId.setText(bill.getPhong().getMaPhong());
		txtRoomTypeName.setText(roomType.getTenLP());
		txtRoomPrice.setText(df.format(roomType.getGiaTien()) + " đ/giờ");
		Timestamp startTime = bill.getNgayGioDat();
		String startTimeStr = ConvertTime.getInstance().convertTimeToString(startTime, formatTime);
		txtStartTime.setText(startTimeStr);
		Timestamp endTime = bill.getNgayGioTra();
		String endTimeStr = ConvertTime.getInstance().convertTimeToString(endTime, formatTime);
		txtEndTime.setText(endTimeStr);
		String usedTime = convertRentalTime(bill.tinhGioThue());
		txtUsedTime.setText(usedTime);
	}

	/**
	 * Hiển thị chi tiết tổng tiền của hóa đơn
	 */
	private void showTotalBill() {
		txtTotalPriceService.setText("1");
		txtTotalPriceRoom.setText("1");
		txtVAT.setText("1");
		txtTotalPriceBill.setText("1");
	}

	/**
	 * Thay đổi kích thước cột của table
	 */
	private void reSizeColumnTableBillInfo() {
		tblTableBillInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModelTableBillInfo = tblTableBillInfo.getColumnModel();
		columnModelTableBillInfo.getColumn(0).setPreferredWidth(59);
		columnModelTableBillInfo.getColumn(1).setPreferredWidth(300);
		columnModelTableBillInfo.getColumn(2).setPreferredWidth(70);
		columnModelTableBillInfo.getColumn(3).setPreferredWidth(150);
		columnModelTableBillInfo.getColumn(4).setPreferredWidth(180);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		columnModelTableBillInfo.getColumn(0).setCellRenderer(centerRenderer);
		columnModelTableBillInfo.getColumn(2).setCellRenderer(centerRenderer);
		columnModelTableBillInfo.getColumn(3).setCellRenderer(rightRenderer);
		columnModelTableBillInfo.getColumn(4).setCellRenderer(rightRenderer);
	}
}
