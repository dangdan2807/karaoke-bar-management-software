package UI.PanelCustom;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.*;
import javax.swing.table.*;

import entity.HoaDon;

public class DialogHoaDon extends JFrame {
	private JTextField txtBillId, txtStaffName, txtCustomerName, txtRoomId, txtRoomTypeName, txtRoomPrice, txtStartTime, txtEndTime, txtUsedTime, txtTotalPriceService, txtTotalPriceRoom, txtVAT, txtTotalPriceBill;
	private JTable tblTableBillInfo;
	private DefaultTableModel modelTableBillInfo;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	private MyButton btnAbate, btnBack, btnInvoiced;

	private HoaDon bill = new HoaDon();

	public DialogHoaDon() {
		setSize(600, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		this.setLayout(null);

		JPanel pnMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(60, 179, 113));
				g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 0, 0);
				setFont(new Font("Dialog", Font.BOLD, 16));
			}
		};
		pnMain.setLayout(null);
		pnMain.setOpaque(false);
		pnMain.setBounds(0, 0, 600, 800);
		this.add(pnMain);

		JLabel lblKaraokeName = new JLabel(
				"<HTML><h1 style=\"margin: 10px 0px -20px 0px;\">KARAOKE DASH </h1> <hr> <p> <i> 122 Nguyễn Văn Bảo, Phường 4, Gò Vấp, Thành phố Hồ Chí Minh </i> </p></HTML>") {
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
		lblKaraokeName.setBounds(125, 0, 350, 100);
		pnMain.add(lblKaraokeName);

		JLabel lblBillName = new JLabel("HÓA ĐƠN TÍNH TIỀN");
		lblBillName.setForeground(Color.WHITE);
		lblBillName.setFont(new Font("Dialog", Font.BOLD, 24));
		lblBillName.setHorizontalAlignment(SwingConstants.CENTER);
		lblBillName.setBounds(125, 120, 350, 40);
		pnMain.add(lblBillName);

		JPanel pnInfoService = new JPanel();
		pnInfoService.setLayout(null);
		pnInfoService.setBounds(10, 410, 580, 235);
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
				g2.drawRect(0, 0, 558, getHeight() - 1);
				g2.drawLine(0, 24, 558, 24);
				setFont(new Font("Dialog", Font.BOLD, 16));

			}
		};
		pnTable.setLayout(null);
		pnTable.setOpaque(false);
		pnTable.setBounds(10, 25, 560, 200);
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
				// Graphics2D g2 = (Graphics2D) g;
				setFont(new Font("Dialog", Font.BOLD, 16));
			}
		};
		tblTableBillInfo.setRowHeight(25);
		tblTableBillInfo.setFont(new Font("Dialog", Font.PLAIN, 16));
		tblTableBillInfo.setBackground(new Color(255, 255, 255, 0));
		tblTableBillInfo.setOpaque(false);
		tblTableBillInfo.setForeground(new Color(255, 255, 255));
		tblTableBillInfo.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 16));
		tblTableBillInfo.getTableHeader().setForeground(Color.decode("#fffffff"));
		tblTableBillInfo.getTableHeader().setBackground(new Color(255, 255, 255, 0));
		tblTableBillInfo.setShowGrid(false);
		JScrollPane scrTableBillInfo = CustomUI.getInstance().setCustomScrollPane(tblTableBillInfo);
		scrTableBillInfo.setBounds(-1, 0, 578, 200);
		modelTableBillInfo.addRow(new Object[] { "1", "Thạch dừa dâu", "2", "35,000", "70,0000001" });
		modelTableBillInfo.addRow(new Object[] { "2", "Thạch dừa dâu", "2", "35,000", "70,0000001" });
		modelTableBillInfo.addRow(new Object[] { "3", "Thạch dừa dâu", "2", "35,000", "70,0000001" });
		pnTable.add(scrTableBillInfo);

		JLabel lblTotalPriceService = new JLabel("Tổng tiền dịch vụ:");
		CustomUI.getInstance().setCustomLabelBill(lblTotalPriceService);
		lblTotalPriceService.setBounds(30, 645, 140, 25);
		pnMain.add(lblTotalPriceService);

		txtTotalPriceService = new JTextField("185,000");
		CustomUI.getInstance().setCustomTextFieldBill(txtTotalPriceService);
		txtTotalPriceService.setBounds(350, 645, 200, 25);
		pnMain.add(txtTotalPriceService);

		JLabel lblTotalPriceRoom = new JLabel("Tổng tiền giờ:");
		CustomUI.getInstance().setCustomLabelBill(lblTotalPriceRoom);
		lblTotalPriceRoom.setBounds(30, 670, 140, 25);
		pnMain.add(lblTotalPriceRoom);

		txtTotalPriceRoom = new JTextField("160,000");
		CustomUI.getInstance().setCustomTextFieldBill(txtTotalPriceRoom);
		txtTotalPriceRoom.setBounds(350, 670, 200, 25);
		pnMain.add(txtTotalPriceRoom);

		JLabel lblVAT = new JLabel("VAT(10%):");
		CustomUI.getInstance().setCustomLabelBill(lblVAT);
		lblVAT.setBounds(30, 695, 140, 25);
		pnMain.add(lblVAT);

		txtVAT = new JTextField("34,500");
		CustomUI.getInstance().setCustomTextFieldBill(txtVAT);
		txtVAT.setBounds(350, 695, 200, 25);
		pnMain.add(txtVAT);

		JLabel lblTotalPriceBill = new JLabel("Tổng cộng:");
		lblTotalPriceBill.setForeground(Color.WHITE);
		lblTotalPriceBill.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTotalPriceBill.setBounds(30, 720, 140, 25);
		pnMain.add(lblTotalPriceBill);

		txtTotalPriceBill = new JTextField("379,500");
		txtTotalPriceBill.setHorizontalAlignment(SwingConstants.LEFT);
		txtTotalPriceBill.setOpaque(false);
		txtTotalPriceBill.setForeground(Color.WHITE);
		txtTotalPriceBill.setFont(new Font("Dialog", Font.BOLD, 18));
		txtTotalPriceBill.setEditable(false);
		txtTotalPriceBill.setColumns(10);
		txtTotalPriceBill.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtTotalPriceBill.setBounds(350, 720, 200, 25);
		pnMain.add(txtTotalPriceBill);

		btnAbate = new MyButton(130, 35, "Thanh toán", gra, null, 23, 19);
		((MyButton) btnAbate).setFontCustom(new Font("Dialog", Font.BOLD, 15));
		btnAbate.setBounds(234, 755, 130, 35);
		pnMain.add(btnAbate);

		btnBack = new MyButton(130, 35, "Quay lại", gra, null, 32, 19);
		((MyButton) btnBack).setFontCustom(new Font("Dialog", Font.BOLD, 15));
		btnBack.setBounds(52, 755, 130, 35);
		pnMain.add(btnBack);

		btnInvoiced = new MyButton(130, 35, "Xuất excel", gra, null, 27, 19);
		((MyButton) btnInvoiced).setFontCustom(new Font("Dialog", Font.BOLD, 15));
		btnInvoiced.setBounds(416, 755, 130, 35);
		pnMain.add(btnInvoiced);

		JLabel txtPhoneNumber = new JLabel("0113114115");
		txtPhoneNumber.setBackground(Color.WHITE);
		txtPhoneNumber.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtPhoneNumber.setForeground(Color.WHITE);
		txtPhoneNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPhoneNumber.setBounds(125, 100, 350, 20);
		pnMain.add(txtPhoneNumber);

		JPanel panel = new JPanel();
		Border b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.white), "Thông tin hóa đơn",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 16), Color.WHITE);
		panel.setBounds(10, 150, 580, 260);
		panel.setBorder(b);
		panel.setOpaque(false);
		pnMain.add(panel);
		panel.setLayout(null);

		JLabel lblBillId = new JLabel("Mã hóa đơn:");
		lblBillId.setBounds(20, 25, 140, 25);
		panel.add(lblBillId);
		CustomUI.getInstance().setCustomLabelBill(lblBillId);

		JLabel lblStaffName = new JLabel("Thu ngân:");
		lblStaffName.setBounds(20, 50, 140, 25);
		panel.add(lblStaffName);
		CustomUI.getInstance().setCustomLabelBill(lblStaffName);

		JLabel lblCustomerName = new JLabel("Tên khách hàng:");
		lblCustomerName.setBounds(20, 75, 140, 25);
		panel.add(lblCustomerName);
		CustomUI.getInstance().setCustomLabelBill(lblCustomerName);

		JLabel lblRoomId = new JLabel("Số phòng:");
		lblRoomId.setBounds(20, 100, 140, 25);
		panel.add(lblRoomId);
		CustomUI.getInstance().setCustomLabelBill(lblRoomId);

		JLabel lblRoomTypeName = new JLabel("Loại phòng:");
		lblRoomTypeName.setBounds(20, 125, 140, 25);
		panel.add(lblRoomTypeName);
		CustomUI.getInstance().setCustomLabelBill(lblRoomTypeName);

		JLabel lblRoomPrice = new JLabel("Giá phòng:");
		lblRoomPrice.setBounds(20, 150, 140, 25);
		panel.add(lblRoomPrice);
		CustomUI.getInstance().setCustomLabelBill(lblRoomPrice);

		JLabel lblStartTime = new JLabel("Giờ bắt đầu:");
		lblStartTime.setBounds(20, 175, 140, 25);
		panel.add(lblStartTime);
		CustomUI.getInstance().setCustomLabelBill(lblStartTime);

		JLabel lblEndTime = new JLabel("Giờ kết thúc:");
		lblEndTime.setBounds(20, 200, 140, 25);
		panel.add(lblEndTime);
		CustomUI.getInstance().setCustomLabelBill(lblEndTime);

		JLabel lblUsedTime = new JLabel("Thời gian sử dụng:");
		lblUsedTime.setBounds(20, 225, 140, 25);
		panel.add(lblUsedTime);
		CustomUI.getInstance().setCustomLabelBill(lblUsedTime);

		txtBillId = new JTextField("HD2021100100001");
		txtBillId.setBounds(340, 25, 220, 25);
		CustomUI.getInstance().setCustomTextFieldBill(txtBillId);
		panel.add(txtBillId);

		txtStaffName = new JTextField("Võ Minh Hiếu");
		txtStaffName.setBounds(340, 50, 220, 25);
		panel.add(txtStaffName);
		CustomUI.getInstance().setCustomTextFieldBill(txtStaffName);

		txtCustomerName = new JTextField("Nguyễn Huỳnh Văn Thanh");
		txtCustomerName.setBounds(340, 75, 220, 25);
		panel.add(txtCustomerName);
		CustomUI.getInstance().setCustomTextFieldBill(txtCustomerName);

		txtRoomId = new JTextField("P0001");
		txtRoomId.setBounds(340, 100, 220, 25);
		panel.add(txtRoomId);
		CustomUI.getInstance().setCustomTextFieldBill(txtRoomId);

		txtRoomTypeName = new JTextField("Phòng 10 Người");
		txtRoomTypeName.setBounds(340, 125, 220, 25);
		panel.add(txtRoomTypeName);
		CustomUI.getInstance().setCustomTextFieldBill(txtRoomTypeName);

		txtRoomPrice = new JTextField("120,000 đ/giờ");
		txtRoomPrice.setBounds(340, 150, 220, 25);
		panel.add(txtRoomPrice);
		CustomUI.getInstance().setCustomTextFieldBill(txtRoomPrice);

		txtStartTime = new JTextField("10:00:00 01/10/2021");
		txtStartTime.setBounds(340, 175, 220, 25);
		panel.add(txtStartTime);
		CustomUI.getInstance().setCustomTextFieldBill(txtStartTime);

		txtEndTime = new JTextField("12:00:00 01/10/2021");
		txtEndTime.setBounds(340, 200, 220, 25);
		panel.add(txtEndTime);
		CustomUI.getInstance().setCustomTextFieldBill(txtEndTime);

		txtUsedTime = new JTextField("2 giờ 00 phút");
		txtUsedTime.setBounds(340, 225, 220, 25);
		panel.add(txtUsedTime);
		CustomUI.getInstance().setCustomTextFieldBill(txtUsedTime);

		allLoaded();

	}

	public static void main(String[] args) {
		new DialogHoaDon().setVisible(true);
	}

	public void allLoaded() {
		reSizeColumnTableBillInfo();
	}

	private void reSizeColumnTableBillInfo() {
		tblTableBillInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModelTableBillInfo = tblTableBillInfo.getColumnModel();
		columnModelTableBillInfo.getColumn(0).setPreferredWidth(50);
		columnModelTableBillInfo.getColumn(1).setPreferredWidth(200);
		columnModelTableBillInfo.getColumn(2).setPreferredWidth(60);
		columnModelTableBillInfo.getColumn(3).setPreferredWidth(100);
		columnModelTableBillInfo.getColumn(4).setPreferredWidth(150);

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
