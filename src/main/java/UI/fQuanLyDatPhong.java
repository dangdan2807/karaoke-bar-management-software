package UI;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

import DAO.*;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.DialogChonKhachHang;
import entity.*;

public class fQuanLyDatPhong extends JFrame
		implements ActionListener, MouseListener, ItemListener, ChangeListener, FocusListener, KeyListener {
	private JButton[] btnRoomList;
	private int pnShowTableWidth = 310;
	private int heightTable = 140;

	private JPanel pnMain, pnShowRoom;
	private DefaultTableModel modelTableBill, modelTableService;
	private JTable tableBill, tableService;
	private JLabel lbEmpName;
	private JButton btnSwitchRoom, btnRefreshRoom, btnBack, btnSearchService, btnPayment, btnOrderServices;
	private JButton btnCannelServices, btnRentRoom, btnRefreshService, btnChooseCustomer;
	private JTextField txtBillID, txtRoomID, txtTotalPriceBill, txtServiceName, txtRoomLocation, txtRoomTypeName;
	private JTextField txtQuantityService, txtServicePrice, txtStartTime, txtEndTime, txtCustomerName;
	private JTextField txtTotalPriceService, boxFieldRoomID, boxFieldRoomType, boxFieldServiceType;
	private JComboBox<String> cboRoomType, cboRoomID, cboServiceType;
	private JCheckBox chkSearchService;
	private JSpinner spinOrderQuantity;
	private JMenuBar menuBar;

	private ImageIcon transferIcon = CustomUI.TRANSFER_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon paymentIcon = CustomUI.PAYMENT_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon roomIcon = CustomUI.ROOM_ICON;
	private ImageIcon addIcon = CustomUI.ADD_ICON;
	private ImageIcon trashIcon = CustomUI.TRASH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;

	private int location = -1;
	private NhanVien staffLogin = null;
	private KhachHang selectedCustomer = null;

	/**
	 * Constructor form quản lý đặt phòng
	 * 
	 * @param staff <code>NhanVien</code>: nhân viên truy cập
	 */
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

	/**
	 * Khởi tạo menu bar
	 */
	public void createMenuBar() {
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu menuTK = new JMenu("Quản lý thông tin khách hàng");

		menuBar.add(menuTK);
	}

	/**
	 * Khởi tạo giao diện quản lý đặt phòng
	 */
	public void createFromQLKS() {
		pnMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				setFont(new Font("Dialog", Font.PLAIN, 12));
			}
		};
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

		JPanel pnEmpInfo = new JPanel();
		pnEmpInfo.setBackground(Color.WHITE);
		pnEmpInfo.setBorder(new TitledBorder(null, "Nhân Viên: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnEmpInfo.setBounds(0, 39, 330, 65);
		pnMain.add(pnEmpInfo);
		pnEmpInfo.setLayout(new BorderLayout(0, 0));

		JPanel pnEmpControl = new JPanel();
		pnEmpControl.setLayout(null);
		pnEmpControl.setPreferredSize(new Dimension(330, 70));
		pnEmpControl.setBackground(Color.WHITE);
		pnEmpInfo.add(pnEmpControl);

		lbEmpName = new JLabel("Tên nhân viên");
		lbEmpName.setBounds(12, 4, 191, 21);
		pnEmpControl.add(lbEmpName);
		lbEmpName.setFont(new Font("Dialog", Font.BOLD, 18));

		btnBack = new JButton("Quay lại", backIcon);
		btnBack.setBounds(209, 4, 96, 26);
		pnEmpControl.add(btnBack);
		CustomUI.getInstance().setCustomBtn(btnBack);

		JPanel pnRoomList = new JPanel();
		pnRoomList.setBackground(Color.WHITE);
		pnRoomList.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnRoomList.setBounds(0, 105, 330, 534);
		pnMain.add(pnRoomList);
		pnRoomList.setLayout(new BorderLayout(0, 0));

		JPanel pnRoomControl = new JPanel();
		pnRoomControl.setBackground(Color.WHITE);
		pnRoomList.add(pnRoomControl, BorderLayout.NORTH);
		pnRoomControl.setLayout(null);
		pnRoomControl.setPreferredSize(new Dimension(330, 70));

		btnSwitchRoom = new JButton("Đổi phòng", transferIcon);
		btnSwitchRoom.setBounds(210, 0, 96, 27);
		btnSwitchRoom.setEnabled(false);
		CustomUI.getInstance().setCustomBtn(btnSwitchRoom);
		pnRoomControl.add(btnSwitchRoom);

		btnRefreshRoom = new JButton("Làm mới", refreshIcon);
		btnRefreshRoom.setBounds(210, 33, 96, 27);
		CustomUI.getInstance().setCustomBtn(btnRefreshRoom);
		pnRoomControl.add(btnRefreshRoom);

		cboRoomID = new JComboBox<String>();
		cboRoomID.setBounds(89, 0, 118, 27);
		cboRoomID.setEditable(true);
		boxFieldRoomID = CustomUI.getInstance().setCustomCBoxField(cboRoomID);
		boxFieldRoomID.setBounds(89, 0, 118, 27);
		pnRoomControl.add(cboRoomID);

		JLabel lbRoomTypeRoomCtrl = new JLabel("Loại phòng: ");
		lbRoomTypeRoomCtrl.setBounds(10, 38, 83, 16);
		pnRoomControl.add(lbRoomTypeRoomCtrl);

		cboRoomType = new JComboBox<String>();
		cboRoomType.setBounds(89, 33, 118, 27);
		cboRoomType.setEditable(true);
		boxFieldRoomType = CustomUI.getInstance().setCustomCBoxField(cboRoomType);
		boxFieldRoomType.setBounds(89, 33, 118, 27);
		pnRoomControl.add(cboRoomType);

		JLabel lbRoom = new JLabel("Phòng: ");
		lbRoom.setBounds(10, 5, 83, 16);
		pnRoomControl.add(lbRoom);

		pnShowRoom = new JPanel();
		pnShowRoom.setBackground(Color.WHITE);
		FlowLayout flShowRoom = new FlowLayout(FlowLayout.LEFT);
		flShowRoom.setHgap(6);
		flShowRoom.setVgap(6);
		pnShowRoom.setLayout(flShowRoom);
		pnShowRoom.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));

		JScrollPane scpShowRoom = new JScrollPane(pnShowRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnRoomList.add(scpShowRoom, BorderLayout.CENTER);
		scpShowRoom.setBorder(new TitledBorder(null, "Danh sách phòng"));
		scpShowRoom.setBackground(Color.WHITE);
		scpShowRoom.getVerticalScrollBar().setUnitIncrement(10);

		JPanel pnBill = new JPanel();
		pnBill.setBackground(Color.WHITE);
		pnBill.setBounds(330, 40, 488, 597);

		JPanel pnBiffInfo = new JPanel();
		pnBiffInfo.setBackground(Color.WHITE);
		pnBiffInfo.setBorder(
				new TitledBorder(null, "Thông tin hóa đơn", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBiffInfo.setLayout(null);
		pnBiffInfo.setPreferredSize(new Dimension(488, 210));

		JPanel pnOrderList = new JPanel();
		pnOrderList.setBackground(Color.WHITE);
		pnOrderList.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Danh sách dịch vụ đã đặt", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		String[] colsTitleService = { "STT", "Tên sản phẩm", "Đơn giá", "Số Lượng", "Thành tiền" };
		modelTableBill = new DefaultTableModel(colsTitleService, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		pnOrderList.setLayout(new BorderLayout(0, 0));
		tableBill = new JTable(modelTableBill);
		tableBill.setBackground(Color.WHITE);

		JScrollPane scpTableBill = new JScrollPane(tableBill, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scpTableBill.setBackground(Color.WHITE);
		scpTableBill.getViewport().setBackground(Color.WHITE);
		pnOrderList.add(scpTableBill, BorderLayout.CENTER);
		pnBill.setLayout(new BorderLayout(0, 0));

		pnBill.add(pnBiffInfo, BorderLayout.NORTH);

		JLabel lbBillID = new JLabel("Mã hóa đơn: ");
		lbBillID.setBounds(12, 20, 85, 20);
		pnBiffInfo.add(lbBillID);

		txtBillID = new JTextField();
		txtBillID.setBounds(85, 20, 157, 20);
		txtBillID.setColumns(10);
		txtBillID.setText("");
		txtBillID.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtBillID);
		pnBiffInfo.add(txtBillID);

		JLabel lbRoomID = new JLabel("Mã phòng:");
		lbRoomID.setBounds(248, 20, 82, 20);
		pnBiffInfo.add(lbRoomID);

		txtRoomID = new JTextField();
		txtRoomID.setBounds(328, 21, 150, 20);
		txtRoomID.setColumns(10);
		txtRoomID.setText("");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomID);
		pnBiffInfo.add(txtRoomID);

		JLabel lbStartTime = new JLabel("Giờ vào:");
		lbStartTime.setBounds(12, 51, 85, 16);
		pnBiffInfo.add(lbStartTime);

		JLabel lbEndTime = new JLabel("Giờ ra:");
		lbEndTime.setBounds(12, 78, 85, 16);
		pnBiffInfo.add(lbEndTime);

		txtStartTime = new JTextField("");
		txtStartTime.setBounds(85, 49, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtStartTime);
		pnBiffInfo.add(txtStartTime);

		btnPayment = new JButton("Thanh toán", paymentIcon);
		btnPayment.setBounds(170, 172, 150, 26);
		CustomUI.getInstance().setCustomBtn(btnPayment);
		pnBiffInfo.add(btnPayment);

		btnRentRoom = new JButton("Thuê ngay", null);
		btnRentRoom.setBounds(12, 172, 150, 26);
		CustomUI.getInstance().setCustomBtn(btnRentRoom);
		pnBiffInfo.add(btnRentRoom);

		JLabel lbTotalPriceBill = new JLabel("Tổng tiền: ");
		lbTotalPriceBill.setBounds(12, 105, 85, 20);
		pnBiffInfo.add(lbTotalPriceBill);

		txtTotalPriceBill = new JTextField();
		txtTotalPriceBill.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalPriceBill.setText("0.0");
		txtTotalPriceBill.setBounds(85, 105, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtTotalPriceBill);
		txtTotalPriceBill.setColumns(10);
		pnBiffInfo.add(txtTotalPriceBill);

		JLabel lbVND = new JLabel("(VND)");
		lbVND.setBounds(248, 105, 43, 20);
		pnBiffInfo.add(lbVND);

		txtEndTime = new JTextField("");
		txtEndTime.setBounds(85, 76, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtEndTime);
		pnBiffInfo.add(txtEndTime);

		JLabel lbRoomLocation = new JLabel("Vị Trí Phòng:");
		lbRoomLocation.setBounds(248, 49, 82, 20);
		pnBiffInfo.add(lbRoomLocation);

		txtRoomLocation = new JTextField();
		txtRoomLocation.setColumns(10);
		txtRoomLocation.setBounds(328, 50, 150, 20);
		txtRoomLocation.setText("");
		;
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomLocation);
		pnBiffInfo.add(txtRoomLocation);

		JLabel lbRoomTypeBillInfo = new JLabel("Loại phòng:");
		lbRoomTypeBillInfo.setBounds(248, 76, 82, 20);
		pnBiffInfo.add(lbRoomTypeBillInfo);

		txtRoomTypeName = new JTextField();
		txtRoomTypeName.setColumns(10);
		txtRoomTypeName.setBounds(328, 77, 150, 20);
		txtRoomTypeName.setText("");
		;
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomTypeName);
		pnBiffInfo.add(txtRoomTypeName);

		JLabel lbCustomerName = new JLabel("Tên KH:");
		lbCustomerName.setBounds(12, 136, 85, 20);
		pnBiffInfo.add(lbCustomerName);

		btnChooseCustomer = new JButton("Chọn KH", null);
		btnChooseCustomer.setBounds(328, 172, 150, 26);
		CustomUI.getInstance().setCustomBtn(btnChooseCustomer);
		pnBiffInfo.add(btnChooseCustomer);

		txtCustomerName = new JTextField();
		txtCustomerName.setColumns(10);
		txtCustomerName.setBounds(85, 136, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerName);
		pnBiffInfo.add(txtCustomerName);

		pnBill.add(pnOrderList);
		pnMain.add(pnBill);
		getContentPane().add(pnMain);

		JPanel pnService = new JPanel();
		pnService.setBackground(Color.WHITE);
		pnService.setBorder(
				new TitledBorder(null, "Thông tin dịch vụ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnService.setBounds(819, 40, 445, 597);
		pnMain.add(pnService);
		pnService.setLayout(new BorderLayout(0, 0));

		JPanel pnControlService = new JPanel();
		pnControlService.setPreferredSize(new Dimension(445, 190));
		pnControlService.setBackground(Color.WHITE);
		pnService.add(pnControlService, BorderLayout.NORTH);
		pnControlService.setLayout(null);

		JLabel lbServiceName = new JLabel("Tên dịch vụ:");
		lbServiceName.setBounds(12, 12, 90, 16);
		pnControlService.add(lbServiceName);

		txtServiceName = new JTextField();
		txtServiceName.setBounds(110, 10, 170, 20);
		txtServiceName.setColumns(10);
		txtServiceName.setText("");
		CustomUI.getInstance().setCustomTextFieldOn(txtServiceName);
		pnControlService.add(txtServiceName);

		btnSearchService = new JButton("Tìm", searchIcon);
		btnSearchService.setBounds(292, 7, 131, 26);
		CustomUI.getInstance().setCustomBtn(btnSearchService);
		pnControlService.add(btnSearchService);

		JLabel lbOrderQuantity = new JLabel("Số lượng đặt: ");
		lbOrderQuantity.setBounds(12, 42, 90, 16);
		pnControlService.add(lbOrderQuantity);

		spinOrderQuantity = new JSpinner();
		spinOrderQuantity.setModel(new SpinnerNumberModel(1, 1, null, 1));
		spinOrderQuantity.setBounds(110, 39, 170, 20);
		spinOrderQuantity.setEnabled(false);
		CustomUI.getInstance().setCustomSpinner(spinOrderQuantity);
		pnControlService.add(spinOrderQuantity);

		btnOrderServices = new JButton("Thêm", addIcon);
		btnOrderServices.setBounds(292, 40, 131, 26);
		CustomUI.getInstance().setCustomBtn(btnOrderServices);
		pnControlService.add(btnOrderServices);

		btnCannelServices = new JButton("Hủy", trashIcon);
		btnCannelServices.setBounds(292, 76, 131, 26);
		CustomUI.getInstance().setCustomBtn(btnCannelServices);
		pnControlService.add(btnCannelServices);

		JLabel lbQuantityService = new JLabel("Số lượng còn: ");
		lbQuantityService.setBounds(12, 69, 90, 16);
		pnControlService.add(lbQuantityService);

		JLabel lbGiaDV = new JLabel("Giá bán: ");
		lbGiaDV.setBounds(12, 98, 90, 16);
		pnControlService.add(lbGiaDV);

		txtQuantityService = new JTextField();
		txtQuantityService.setColumns(10);
		txtQuantityService.setBounds(110, 67, 170, 20);
		txtQuantityService.setText("");
		;
		txtQuantityService.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtQuantityService);
		pnControlService.add(txtQuantityService);

		txtServicePrice = new JTextField();
		txtServicePrice.setColumns(10);
		txtServicePrice.setBounds(110, 96, 170, 20);
		txtServicePrice.setText("");
		;
		txtServicePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtServicePrice);
		pnControlService.add(txtServicePrice);

		btnRefreshService = new JButton("Làm mới", refreshIcon);
		btnRefreshService.setBounds(292, 113, 131, 26);
		CustomUI.getInstance().setCustomBtn(btnRefreshService);
		pnControlService.add(btnRefreshService);

		JLabel lbTotalPriceService = new JLabel("Tổng tiền: ");
		lbTotalPriceService.setBounds(12, 127, 90, 16);
		pnControlService.add(lbTotalPriceService);

		txtTotalPriceService = new JTextField();
		txtTotalPriceService.setText("0.0");
		txtTotalPriceService.setColumns(10);
		txtTotalPriceService.setBounds(110, 125, 170, 20);
		txtTotalPriceService.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtTotalPriceService);
		pnControlService.add(txtTotalPriceService);

		JLabel lbServiceType = new JLabel("Loại dịch vụ: ");
		lbServiceType.setBounds(12, 156, 90, 16);
		pnControlService.add(lbServiceType);

		cboServiceType = new JComboBox<String>();
		cboServiceType.setBounds(110, 154, 170, 20);
		cboServiceType.setEditable(true);
		boxFieldServiceType = CustomUI.getInstance().setCustomCBoxField(cboServiceType);
		boxFieldServiceType.setBounds(110, 154, 170, 20);
		pnControlService.add(cboServiceType);

		chkSearchService = new JCheckBox("<html>Tìm theo tên và loại</html>");
		chkSearchService.setBackground(Color.WHITE);
		chkSearchService.setBounds(289, 154, 144, 20);
		chkSearchService.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnControlService.add(chkSearchService);

		JPanel pnServiceList = new JPanel();
		pnServiceList.setBackground(Color.WHITE);
		pnServiceList.setBorder(
				new TitledBorder(null, "Danh sách dịch vụ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnService.add(pnServiceList, BorderLayout.CENTER);

		String[] colsProduct = { "STT", "Tên sản phẩm", "SL còn", "Đơn giá" };
		modelTableService = new DefaultTableModel(colsProduct, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		pnServiceList.setLayout(new BorderLayout(0, 0));
		tableService = new JTable(modelTableService);

		JScrollPane scpProductList = new JScrollPane(tableService, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scpProductList.getViewport().setBackground(Color.WHITE);
		pnServiceList.add(scpProductList);

		btnSwitchRoom.addActionListener(this);
		btnRefreshService.addActionListener(this);
		btnSearchService.addActionListener(this);
		btnPayment.addActionListener(this);
		btnOrderServices.addActionListener(this);
		btnCannelServices.addActionListener(this);
		btnRentRoom.addActionListener(this);
		btnRefreshRoom.addActionListener(this);
		btnChooseCustomer.addActionListener(this);
		btnBack.addActionListener(this);

		btnSwitchRoom.addMouseListener(this);
		btnRefreshService.addMouseListener(this);
		btnSearchService.addMouseListener(this);
		btnPayment.addMouseListener(this);
		btnOrderServices.addMouseListener(this);
		btnCannelServices.addMouseListener(this);
		btnRentRoom.addMouseListener(this);
		btnRefreshRoom.addMouseListener(this);
		btnChooseCustomer.addMouseListener(this);
		tableService.addMouseListener(this);
		tableBill.addMouseListener(this);
		btnBack.addMouseListener(this);
		boxFieldRoomID.addMouseListener(this);
		boxFieldRoomType.addMouseListener(this);
		boxFieldServiceType.addMouseListener(this);

		txtServiceName.addFocusListener(this);
		spinOrderQuantity.addFocusListener(this);

		spinOrderQuantity.addChangeListener(this);

		cboRoomType.addItemListener(this);
		cboServiceType.addItemListener(this);
		chkSearchService.addItemListener(this);

		txtServiceName.addKeyListener(this);

		showStaffName(staffLogin);
		LoadRoomList(PhongDAO.getInstance().getDSPhong());
		reSizeColumnTableBillInfo();
		loadRoomTypeList();
		loadCboRoom("Tất cả");
		loadDSServiceType();
		loadServiceList(DichVuDAO.getInstance().getServiceList());
		reSizeColumnTableService();
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
			new fQuanLyDatPhong(staff).setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			fDieuHuong f = new fDieuHuong(staffLogin);
			this.setVisible(false);
			f.setVisible(true);
		} else if (o.equals(btnRentRoom)) {
			if (txtRoomID.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
			} else if (txtCustomerName.getText().trim().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn khách hàng thuê trước");
			} else {
				long millis = System.currentTimeMillis();
				Timestamp startTime = new Timestamp(millis);
				String roomID = txtRoomID.getText().trim();
				Phong room = PhongDAO.getInstance().getPhongByMaPhong(roomID);
				HoaDon bill = new HoaDon(startTime, HoaDonDAO.UNPAID, staffLogin, selectedCustomer, room);
				boolean resultBill = HoaDonDAO.getInstance().themHoaDon(bill);
				if (resultBill) {
					JOptionPane.showMessageDialog(this, "Cho thuê phòng thành công");
					int billID = HoaDonDAO.getInstance().getMaHDCuoiCung();
					PhongDAO.getInstance().capNhatTinhTrangPhong(roomID, PhongDAO.PAID);
					txtBillID.setText(String.valueOf(billID));
					btnPayment.setEnabled(true);
					LoadRoomList(PhongDAO.getInstance().getDSPhong());
					String format = "dd-MM-yyyy HH:mm:ss";
					String startTimeStr = ConvertTime.getInstance().convertSqlTimestampToUtilDateFormatString(startTime,
							format);
					txtStartTime.setText(startTimeStr);
					btnChooseCustomer.setEnabled(false);
					btnRentRoom.setEnabled(false);
					btnPayment.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
				}
			}
		} else if (o.equals(btnOrderServices) || o.equals(btnCannelServices)) {
			if (txtBillID.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Phòng này chưa được cho thuê");
			} else if (txtRoomID.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
			} else if (txtServiceName.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn sản phẩm");
			} else {
				int selectRow = tableService.getSelectedRow();
				int orderQuantity = (int) spinOrderQuantity.getValue();
				if (o.equals(btnCannelServices))
					orderQuantity *= -1;
				int quantity = Integer.parseInt(txtQuantityService.getText());
				if (orderQuantity > quantity) {
					orderQuantity = quantity;
					JOptionPane.showMessageDialog(this, "Số lượng dịch vụ đặt lớn hơn số lượng hiện có", "Cảnh bảo",
							JOptionPane.ERROR_MESSAGE);
				} else if (quantity <= 0) {
					JOptionPane.showMessageDialog(this, "Dịch vụ đã hết", "Cảnh bảo", JOptionPane.ERROR_MESSAGE);
				} else if (orderQuantity <= 0) {
					JOptionPane.showMessageDialog(this, "Số lượng dịch vụ đặt phải lớn hơn 0", "Cảnh bảo",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String serviceName = txtServiceName.getText().trim();
					DichVu service = DichVuDAO.getInstance().getDichVuByTenDichVu(serviceName);
					String roomID = txtRoomID.getText();
					int BillID = Integer.parseInt(txtBillID.getText());
					HoaDon bill = HoaDonDAO.getInstance().getHoaDonByMaHD(BillID);
					boolean result = false;
					String message = "Thêm dịch vụ thất bại";
					if (BillID != -1) {
						CTHoaDon serviceInfo = CTHoaDonDAO.getInstance().getCTHoaDonByMaHDvaMaDV(BillID,
								service.getMaDichVu());
						// nếu ctDichVu đã tồn tại thì cập nhật
						// nếu ctDichVu không tồn tại thì thêm mới
						if (serviceInfo != null) {
							int newQuantity = quantity - orderQuantity;
							if (serviceInfo.getSoLuongDat() > 0 && newQuantity > 0) {
								int newOrderQuantity = serviceInfo.getSoLuongDat() + orderQuantity;
								serviceInfo.setSoLuongDat(newOrderQuantity);
								result = CTHoaDonDAO.getInstance().themCTHoaDon(serviceInfo, orderQuantity,
										bill.getMaHoaDon());
							} else {
								message = "Dịch vụ chưa được thêm nên không thể hủy";
							}
						} else {
							serviceInfo = new CTHoaDon(orderQuantity, service);
							result = CTHoaDonDAO.getInstance().themCTHoaDon(serviceInfo, orderQuantity,
									bill.getMaHoaDon());
						}
						// kiểm tra kết quả thêm, cập nhật
						if (result) {
							int newQuantity = quantity - orderQuantity;
							if (newQuantity > 0) {
								showBillInfo(roomID);
								modelTableService.setValueAt(String.valueOf(newQuantity), selectRow, 2);
								spinOrderQuantity.setValue(1);
								txtQuantityService.setText(String.valueOf(Math.abs(newQuantity)));
								txtBillID.setText(String.valueOf(BillID));
							} else {
								message = "Dịch vụ chưa được thêm nên không thể hủy";
							}
						} else {
							JOptionPane.showMessageDialog(this, message);
						}
					} else {
						JOptionPane.showMessageDialog(this, "Hóa đơn không tồn tại");
					}
				}
			}
		} else if (o.equals(btnSearchService)) {
			searchService();
		} else if (o.equals(btnRefreshService)) {
			String serviceTypeName = cboServiceType.getSelectedItem().toString();
			String serviceName = txtServiceName.getText().trim();
			ArrayList<DichVu> serviceList = null;
			if (serviceName.equalsIgnoreCase("")) {
				if (serviceTypeName.equalsIgnoreCase("tất cả")) {
					serviceList = DichVuDAO.getInstance().getServiceList();
				} else {
					serviceList = DichVuDAO.getInstance().getDSDichVuByTenLoaiDV(serviceTypeName);
				}
			} else {
				if (chkSearchService.isSelected() && !serviceTypeName.equalsIgnoreCase("tất cả")) {
					serviceList = DichVuDAO.getInstance().getDSDichVuByTenDVvaTenLoaiDV(serviceName, serviceTypeName);
				} else {
					serviceList = DichVuDAO.getInstance().getDSDichVuByTenDichVu(serviceName);
				}
			}
			loadServiceList(serviceList);
		} else if (o.equals(btnPayment)) {
			String roomID = txtRoomID.getText().trim();
			HoaDon bill = HoaDonDAO.getInstance().getUncheckHoaDonByMaPhong(roomID);
			String totalPriceStr = txtTotalPriceBill.getText().trim();
			double totalPrice = Double.parseDouble(totalPriceStr.replace(",", ""));
			long millis = System.currentTimeMillis();
			Timestamp endTime = new Timestamp(millis);
			if (bill != null) {
				String message = String.format(
						"Bạn có chắc chắn thanh toán hóa đơn cho %s \nSố tiền khách hàng cần phải trả là: %s VND",
						roomID, totalPriceStr);
				int select = JOptionPane.showConfirmDialog(this, message, "Xác nhận thanh toán",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (select == JOptionPane.OK_OPTION) {
					bill.setNgayGioTra(endTime);
					totalPrice += bill.tinhTienPhong();
					HoaDonDAO.getInstance().thanhToan(bill.getMaHoaDon(), bill.getNgayGioTra(), totalPrice);
					showBillInfo(roomID);
					loadPhong(roomID);
					String endTimeStr = ConvertTime.getInstance().convertSqlTimestampToUtilDateFormatString(endTime,
							"dd-MM-yyyy HH:mm:ss");
					txtEndTime.setText(endTimeStr);
					DecimalFormat df = new DecimalFormat("#,###.##");
					txtTotalPriceBill.setText(df.format(totalPrice));
				}
			}
		} else if (o.equals(btnRefreshRoom)) {
			String roomTypeName = cboRoomType.getSelectedItem().toString();
			loadRoomListByRoomTypeName(roomTypeName);
		} else if (o.equals(btnSwitchRoom)) {
			String RoomIdOld = txtRoomID.getText();
			String roomIdNew = cboRoomID.getSelectedItem().toString().trim();
			String message = String.format("Bạn có chắc chắn muốn chuyển từ phòng %s qua phòng %s", RoomIdOld,
					roomIdNew);
			int select = JOptionPane.showConfirmDialog(this, message, "Xác nhận chuyển phòng",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (select == JOptionPane.OK_OPTION) {
				String billID = txtBillID.getText();
				if (billID.equals("")) {
					JOptionPane.showConfirmDialog(this, message, "Phòng này chưa được cho thuê nên không thể chuyển",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				} else {
					PhongDAO.getInstance().chuyenPhong(RoomIdOld, roomIdNew);
					loadPhong(RoomIdOld);
					loadPhong(roomIdNew);
					showBillInfo(RoomIdOld);
					showBillInfo(roomIdNew);
					String roomTypeName = txtRoomTypeName.getText();
					loadCboRoom(roomTypeName);
					refreshBillForm();
					btnSwitchRoom.setEnabled(false);
				}
			}
		} else if (o.equals(btnChooseCustomer)) {
			DialogChonKhachHang chonKH = new DialogChonKhachHang();
			chonKH.setModal(true);
			chonKH.setVisible(true);
			selectedCustomer = chonKH.getKHDuocChon();
			if (selectedCustomer != null)
				txtCustomerName.setText(selectedCustomer.getHoTen());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		DecimalFormat df = new DecimalFormat("#,###.##");
		if (o.equals(tableService)) {
			spinOrderQuantity.setEnabled(true);
			int row = tableService.getSelectedRow();
			String serviceName = modelTableService.getValueAt(row, 1).toString();
			DichVu service = DichVuDAO.getInstance().getDichVuByTenDichVu(serviceName);
			txtServiceName.setText(serviceName);
			txtQuantityService.setText(df.format(service.getSoLuongTon()));
			txtServicePrice.setText(df.format(service.getGiaBan()));
			txtTotalPriceService.setText(df.format(service.getGiaBan()));
			spinOrderQuantity.setValue(1);
		} else if (o.equals(tableBill)) {
			spinOrderQuantity.setEnabled(true);
			int row = tableBill.getSelectedRow();
			String serviceName = modelTableBill.getValueAt(row, 1).toString();
			txtServiceName.setText(serviceName);
			int orderQuantity = Integer.parseInt(modelTableBill.getValueAt(row, 3).toString());
			String price = modelTableBill.getValueAt(row, 2).toString();
			txtServicePrice.setText(price);
			int quantityService = DichVuDAO.getInstance().getSLDVuConByTenDichVu(serviceName);
			txtQuantityService.setText(String.valueOf(quantityService));
			spinOrderQuantity.setValue(Math.abs(orderQuantity));
		} else if (o.equals(boxFieldRoomID)) {
			cboRoomID.showPopup();
		} else if (o.equals(boxFieldRoomType)) {
			cboRoomType.showPopup();
		} else if (o.equals(boxFieldServiceType)) {
			cboServiceType.showPopup();
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
		if (o.equals(btnSwitchRoom)) {
			CustomUI.getInstance().setCustomBtnHover(btnSwitchRoom);
		} else if (o.equals(btnRefreshRoom)) {
			CustomUI.getInstance().setCustomBtnHover(btnRefreshRoom);
		} else if (o.equals(btnSearchService)) {
			CustomUI.getInstance().setCustomBtnHover(btnSearchService);
		} else if (o.equals(btnPayment)) {
			CustomUI.getInstance().setCustomBtnHover(btnPayment);
		} else if (o.equals(btnOrderServices)) {
			CustomUI.getInstance().setCustomBtnHover(btnOrderServices);
		} else if (o.equals(btnCannelServices)) {
			CustomUI.getInstance().setCustomBtnHover(btnCannelServices);
		} else if (o.equals(btnRefreshService)) {
			CustomUI.getInstance().setCustomBtnHover(btnRefreshService);
		} else if (o.equals(btnRentRoom)) {
			CustomUI.getInstance().setCustomBtnHover(btnRentRoom);
		} else if (o.equals(btnChooseCustomer)) {
			CustomUI.getInstance().setCustomBtnHover(btnChooseCustomer);
		} else if (o.equals(btnBack)) {
			CustomUI.getInstance().setCustomBtnHover(btnBack);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(btnSwitchRoom)) {
			CustomUI.getInstance().setCustomBtn(btnSwitchRoom);
		} else if (o.equals(btnRefreshRoom)) {
			CustomUI.getInstance().setCustomBtn(btnRefreshRoom);
		} else if (o.equals(btnSearchService)) {
			CustomUI.getInstance().setCustomBtn(btnSearchService);
		} else if (o.equals(btnPayment)) {
			CustomUI.getInstance().setCustomBtn(btnPayment);
		} else if (o.equals(btnOrderServices)) {
			CustomUI.getInstance().setCustomBtn(btnOrderServices);
		} else if (o.equals(btnCannelServices)) {
			CustomUI.getInstance().setCustomBtn(btnCannelServices);
		} else if (o.equals(btnRefreshService)) {
			CustomUI.getInstance().setCustomBtn(btnRefreshService);
		} else if (o.equals(btnRentRoom)) {
			CustomUI.getInstance().setCustomBtn(btnRentRoom);
		} else if (o.equals(btnChooseCustomer)) {
			CustomUI.getInstance().setCustomBtn(btnChooseCustomer);
		} else if (o.equals(btnBack)) {
			CustomUI.getInstance().setCustomBtn(btnBack);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboRoomType)) {
			String tenLoaiPhong = cboRoomType.getSelectedItem().toString();
			loadRoomListByRoomTypeName(tenLoaiPhong);
		} else if (o.equals(cboServiceType)) {
			String tenLoaiDV = cboServiceType.getSelectedItem().toString();
			if (tenLoaiDV.equalsIgnoreCase("tất cả")) {
				loadServiceList(DichVuDAO.getInstance().getServiceList());
			} else {
				loadServiceList(DichVuDAO.getInstance().getDSDichVuByTenLoaiDV(tenLoaiDV));
			}
		} else if (o.equals(chkSearchService)) {
			if (chkSearchService.isSelected()) {
				cboServiceType.removeItemListener(this);
			} else {
				cboServiceType.addItemListener(this);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object o = e.getSource();
		if (o.equals(spinOrderQuantity)) {
			int soLuongDat = (int) spinOrderQuantity.getValue();
			double giaTien = Double.parseDouble(txtServicePrice.getText().replace(",", ""));
			double tongTien = giaTien * soLuongDat;
			DecimalFormat df = new DecimalFormat("#,###.##");
			txtTotalPriceService.setText(df.format(tongTien));
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtServiceName);
		} else if (o.equals(spinOrderQuantity)) {
			spinOrderQuantity.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			txtServiceName.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(spinOrderQuantity)) {
			spinOrderQuantity.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchService();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * Bắt sự kiện khi click btn close(x), sẽ show 1 form xác nhận đăng xuất hay
	 * thoát chương trình
	 * 
	 * @param jframe sẽ nhận sự kiện
	 */
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

	/**
	 * chuyển đổi tình trạng phòng từ số sang chuỗi
	 * 
	 * @param type tình trạng phòng
	 * @return <code>String</code>: tính trạng dạng chuỗi 0 là phòng trống và 1 là
	 *         đã cho thuê
	 */
	private String convertRoomStatus(int type) {
		// 1 là đã cho thuê | 0 là là còn trống
		String statusStr = "Trống";
		if (type == 1)
			statusStr = "Đã cho thuê";
		return statusStr;
	}

	/**
	 * Hiển thị thông tin của 1 phòng
	 * 
	 * @param roomId <code>String</code>: mã phòng cần hiển thị
	 */
	private void loadPhong(String roomId) {
		Phong room = PhongDAO.getInstance().getPhongByMaPhong(roomId);
		String roomStatus = convertRoomStatus(room.getTinhTrangP());
		String btnName = "<html><p style='text-align: center;'> " + roomId
				+ " </p></br><p style='text-align: center;'> " + roomStatus + " </p></html>";
		int index = 0;
		for (int i = 0; i < btnRoomList.length; i++) {
			if (btnRoomList[i].getText().contains(roomId))
				index = i;
			else if (btnRoomList[i].getText().equals("")) {
				index = i;
				break;
			}
		}
		btnRoomList[index].setText(btnName);
		btnRoomList[index].setForeground(Color.WHITE);
		btnRoomList[index].setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRoomList[index].setHorizontalTextPosition(SwingConstants.CENTER);
		btnRoomList[index].setPreferredSize(new Dimension(PhongDAO.ROOM_WIDTH, PhongDAO.ROOM_HEIGHT));
		btnRoomList[index].setIcon(roomIcon);
		btnRoomList[index].setCursor(new Cursor(Cursor.HAND_CURSOR));
		switch (roomStatus) {
		case "Trống":
			btnRoomList[index].setBackground(Color.decode("#00a65a"));
			btnPayment.setEnabled(false);
			break;
		default:
			btnRoomList[index].setBackground(Color.decode("#3c8dbc"));
			break;
		}
		pnShowRoom.revalidate();
		pnShowRoom.repaint();
	}

	/**
	 * Hiển thị danh sách phòng được truyền vào
	 * 
	 * @param dsPhong <code>ArrayList<Phong></code>: danh sách phòng cần hiển thị
	 */
	private void LoadRoomList(ArrayList<Phong> dsPhong) {
		heightTable = 70;
		pnShowRoom.removeAll();
		pnShowRoom.revalidate();
		pnShowRoom.repaint();
		Border lineRed = new LineBorder(Color.RED, 2);
		Border lineGray = new LineBorder(Color.GRAY, 1);
		int sizeDSPhong = dsPhong.size();
		btnRoomList = new JButton[sizeDSPhong];

		for (int i = 0; i < sizeDSPhong; i++) {
			final int selection = i;
			String roomID = dsPhong.get(i).getMaPhong();
			btnRoomList[selection] = new JButton("");
			loadPhong(roomID);
			btnRoomList[selection].setBorder(lineGray);
			if ((i + 1) % 3 == 0) {
				heightTable += PhongDAO.ROOM_HEIGHT;
				pnShowRoom.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));
			}
			String roomID2 = txtRoomID.getText();
			if (roomID.equalsIgnoreCase(roomID2)) {
				location = i;
			}
			btnRoomList[selection].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (location != -1) {
						btnRoomList[location].setBorder(lineGray);
					}
					String roomTypeName = LoaiPhongDAO.getInstance().getTenLPbyMaPhong(roomID);
					if (roomTypeName == null) {
						roomTypeName = "Tất cả";
					}
					loadCboRoom(roomTypeName);
					location = selection;
					btnRoomList[selection].setBorder(lineRed);
					showBillInfo(roomID);
					txtRoomID.setText(roomID);
					HoaDon bill = HoaDonDAO.getInstance().getUncheckHoaDonByMaPhong(roomID);
					if (bill.getMaHoaDon() != -1) {
						KhachHang customer = KhachHangDAO.getInstance()
								.getKhachHangByMaKH(bill.getKhachHang().getMaKH());
						txtCustomerName.setText(customer.getHoTen());
						txtBillID.setText(String.valueOf(bill.getMaHoaDon()));
						String format = "dd-MM-yyyy HH:mm:ss";
						Timestamp startTime = bill.getNgayGioDat();
						Timestamp endTime = bill.getNgayGioTra();
						String startTimeStr = "";
						String endTimeStr = "";
						if (startTime != null) {
							startTimeStr = ConvertTime.getInstance()
									.convertSqlTimestampToUtilDateFormatString(startTime, format);
						}
						if (endTime != null) {
							endTimeStr = ConvertTime.getInstance().convertSqlTimestampToUtilDateFormatString(endTime,
									format);
						}
						txtStartTime.setText(startTimeStr);
						txtEndTime.setText(endTimeStr);
					} else {
						txtBillID.setText("");
						txtCustomerName.setText("");
						txtStartTime.setText("");
						txtEndTime.setText("");
						txtTotalPriceBill.setText("0.0");
					}
					spinOrderQuantity.setValue(1);
					Phong roomActiveE = PhongDAO.getInstance().getPhongByMaPhong(roomID);
					txtRoomLocation.setText(roomActiveE.getViTri());
					txtRoomTypeName.setText(roomActiveE.getLoaiPhong().getTenLP());
					String status = convertRoomStatus(roomActiveE.getTinhTrangP());
					switch (status) {
					case "Trống":
						btnRentRoom.setEnabled(true);
						btnPayment.setEnabled(false);
						btnChooseCustomer.setEnabled(true);
						btnSwitchRoom.setEnabled(false);
						break;
					default:
						btnRentRoom.setEnabled(false);
						btnPayment.setEnabled(true);
						btnChooseCustomer.setEnabled(false);
						btnSwitchRoom.setEnabled(true);
						break;
					}
				}
			});
			// sự kiện hover chuột
			btnRoomList[selection].addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {
					btnRoomList[selection].setBackground(Color.decode("#bbdefc"));
					btnRoomList[selection].setForeground(Color.decode("#1a66e3"));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					Phong roomActiveE = PhongDAO.getInstance().getPhongByMaPhong(roomID);
					String status = convertRoomStatus(roomActiveE.getTinhTrangP());
					switch (status) {
					case "Trống":
						btnRoomList[selection].setBackground(Color.decode("#00a65a"));
						break;
					default:
						btnRoomList[selection].setBackground(Color.decode("#3c8dbc"));
						break;
					}
					btnRoomList[selection].setForeground(Color.WHITE);
				}
			});
			pnShowRoom.add(btnRoomList[selection]);
		}
		if (location != -1 && location < btnRoomList.length)
			btnRoomList[location].setBorder(lineRed);
	}

	/**
	 * Hiển thị danh sách phòng khi biết loại phòng trên comboBox Phòng
	 * 
	 * @param roomTypeName <code>String</code>: loại phòng
	 */
	private void loadCboRoom(String roomTypeName) {
		ArrayList<Phong> roomList = new ArrayList<Phong>();
		if (roomTypeName.equalsIgnoreCase("Tất cả")) {
			roomList = PhongDAO.getInstance().getDSPhongTrong();
		} else {
			roomList = PhongDAO.getInstance().getDSPhongTrongTheoLoaiPhong(roomTypeName);
		}
		cboRoomID.removeAllItems();
		for (Phong phong : roomList) {
			cboRoomID.addItem(phong.getMaPhong());
		}
	}

	/**
	 * Hiển thị danh sách CTHoaDon khi biết mã phòng
	 * 
	 * @param roomId <code>String</code>: mã phòng
	 */
	private void showBillInfo(String roomId) {
		ArrayList<CTHoaDon> dataList = CTHoaDonDAO.getInstance().getCTHoaDonListByMaPhong(roomId);
		DecimalFormat df = new DecimalFormat("#,###.##");
		int i = 1;
		modelTableBill.getDataVector().removeAllElements();
		modelTableBill.fireTableDataChanged();
		double totalPrice = 0;
		for (CTHoaDon item : dataList) {
			totalPrice += item.getTienDichVu();
			String stt = df.format(i++);
			String totalPriceStr = df.format(item.getTienDichVu());
			String priceStr = df.format(item.getDichVu().getGiaBan());
			String countStr = df.format(item.getSoLuongDat());
			modelTableBill
					.addRow(new Object[] { stt, item.getDichVu().getTenDichVu(), priceStr, countStr, totalPriceStr });
		}
		txtTotalPriceBill.setText(df.format(totalPrice));
	}

	/**
	 * Hiển thị danh sách loại phòng lên comboBox loại phòng
	 */
	private void loadRoomTypeList() {
		ArrayList<LoaiPhong> dataList = LoaiPhongDAO.getInstance().getDSLoaiPhong();
		cboRoomType.addItem("Tất cả");
		for (LoaiPhong item : dataList) {
			cboRoomType.addItem(item.getTenLP());
		}
	}

	/**
	 * Hiển thị danh sách phòng dựa trên tên loại phòng
	 * 
	 * @param roomTypeName <code>String</code>: tên lại phòng
	 */
	private void loadRoomListByRoomTypeName(String roomTypeName) {
		ArrayList<Phong> dataList = null;
		if (roomTypeName.equalsIgnoreCase("Tất cả"))
			dataList = PhongDAO.getInstance().getDSPhong();
		else {
			location = -1;
			dataList = PhongDAO.getInstance().getDSPhongByTenLoaiPhong(roomTypeName);
		}
		LoadRoomList(dataList);
	}

	/**
	 * Hiển thị danh sách dịch vụ
	 * 
	 * @param dataList <code>ArrayList<DichVu></code>: danh sách dịch vụ
	 */
	private void loadServiceList(ArrayList<DichVu> dataList) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		int i = 1;
		modelTableService.getDataVector().removeAllElements();
		modelTableService.fireTableDataChanged();
		for (DichVu item : dataList) {
			String stt = df.format(i++);
			String priceStr = df.format(item.getGiaBan());
			String quantityStr = df.format(item.getSoLuongTon());
			modelTableService.addRow(new Object[] { stt, item.getTenDichVu(), quantityStr, priceStr });
		}
	}

	/**
	 * Hiển thị danh sách loại dịch vụ
	 */
	private void loadDSServiceType() {
		ArrayList<LoaiDichVu> serviceTypeList = LoaiDichVuDAO.getInstance().getServiceTypeList();
		cboServiceType.addItem("Tất cả");
		for (LoaiDichVu item : serviceTypeList) {
			cboServiceType.addItem(item.getTenLDV());
		}
	}

	/**
	 * Hiển thị tên nhân viên đang sử dụng phần mềm
	 * 
	 * @param staff <code>NhanVien</code>: nhân viên được hiển thị tên
	 */
	private void showStaffName(NhanVien staff) {
		lbEmpName.setText(staff.getHoTen());
	}

	/**
	 * Xóa trắng thông tin trên panel Hóa đơn
	 */
	private void refreshBillForm() {
		txtBillID.setText("");
		txtStartTime.setText("");
		txtEndTime.setText("");
		txtTotalPriceBill.setText("0.0");
		txtCustomerName.setText("");
		modelTableBill.getDataVector().removeAllElements();
	}

	/**
	 * Tìm kiếm dịch vụ
	 */
	private void searchService() {
		String serviceName = txtServiceName.getText().trim();
		ArrayList<DichVu> serviceList = null;
		String serviceTypeName = cboServiceType.getSelectedItem().toString();
		if (chkSearchService.isSelected() && !serviceTypeName.equalsIgnoreCase("tất cả")) {
			serviceList = DichVuDAO.getInstance().getDSDichVuByTenDVvaTenLoaiDV(serviceName, serviceTypeName);
		} else {
			serviceList = DichVuDAO.getInstance().getDSDichVuByTenDichVu(serviceName);
		}
		loadServiceList(serviceList);
	}

	/**
	 * Thay đổi kích thước các cột trong bảng chi tiết hóa đơn
	 */
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
		tableBill.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tableBill.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
	}

	/**
	 * Thay đổi kích thước các cột trong bảng dịch vụ
	 */
	private void reSizeColumnTableService() {
		tableService.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableService.getColumnModel().getColumn(1).setPreferredWidth(210);
		tableService.getColumnModel().getColumn(2).setPreferredWidth(40);
		tableService.getColumnModel().getColumn(3).setPreferredWidth(40);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableService.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tableService.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tableService.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	}
}