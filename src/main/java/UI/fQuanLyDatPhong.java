package UI;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import DAO.*;
import Event_Handlers.InputEventHandler;
import UI.PanelCustom.DialogChonKhachHang;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.MyButton;
import entity.*;

public class fQuanLyDatPhong extends JFrame
		implements ActionListener, MouseListener, ItemListener, ChangeListener, FocusListener, KeyListener {
	private JButton[] btnRoomList;
	private int pnShowTableWidth = 310;
	private int heightTable = 140;

	private JPanel pnlMain, pnlShowRoom;
	private DefaultTableModel modelTableBill, modelTableService;
	private JTable tableBill, tblTableService;
	private JLabel lblStaffName;
	private JButton btnSwitchRoom, btnRefreshRoom, btnBack, btnSearchService, btnPayment, btnOrderServices;
	private JButton btnCannelServices, btnRentRoom, btnRefreshService, btnChooseCustomer;
	private JTextField txtBillID, txtRoomID, txtTotalPriceBill, txtServiceName, txtRoomLocation;
	private JTextField txtQuantityService, txtServicePrice, txtStartTime, txtEndTime, txtCustomerName;
	private JTextField txtTotalPriceService, txtBFieldRoomID, txtBFieldRoomType, txtBFieldServiceType;
	private JTextField txtRoomTypeName;
	private JComboBox<String> cboRoomType, cboRoomID, cboServiceType;
	private JCheckBox chkSearchService;
	private JSpinner spnOrderQuantity;
	private JMenuBar mnuMenuBar;

	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon transferIcon = CustomUI.TRANSFER_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon paymentIcon = CustomUI.PAYMENT_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon roomIcon = CustomUI.ROOM_ICON;
	private ImageIcon addIcon = CustomUI.ADD_ICON;
	private ImageIcon trashIcon = CustomUI.TRASH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;

	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private int location = -1;
	private NhanVien staffLogin = null;
	private KhachHang selectedCustomer = null;
	private DecimalFormat df = new DecimalFormat("#,###.##");

	/**
	 * Hàm khởi tạo form
	 * 
	 * @param staff {@code NhanVien}: nhân viên đăng nhập
	 */
	public fQuanLyDatPhong(NhanVien staff) {
		setTitle("Phần Mềm Quản Lý Quán Karaoke");
		setSize(1280, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.staffLogin = staff;
		createMenuBar();
		createFrom();
		setCloseAction(this);
	}

	/**
	 * Hàm tạo menu bar
	 */
	public void createMenuBar() {
		mnuMenuBar = new JMenuBar();
		this.setJMenuBar(mnuMenuBar);

		JMenu menuTK = new JMenu("Quản lý thông tin khách hàng");

		mnuMenuBar.add(menuTK);
	}

	/**
	 * Hàm tạo form
	 */
	public void createFrom() {
		pnlMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				setFont(new Font("Dialog", Font.PLAIN, 12));
				Image bgMain = bg.getImage();
				g2.drawImage(bgMain, 0, 0, null);
			}
		};
		pnlMain.setBackground(Color.WHITE);
		pnlMain.setLayout(null);

		JPanel pnlTitle = new JPanel();
		pnlTitle.setBounds(0, 0, 1264, 39);
		pnlTitle.setLayout(null);
		pnlTitle.setOpaque(false);
		pnlMain.add(pnlTitle);
		pnlTitle.setBackground(Color.decode("#d0e1fd"));

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 33, 19, 12, 5);
		btnBack.setBounds(1150, 5, 100, 35);
		((MyButton) btnBack).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		pnlTitle.add(btnBack);

		JLabel lblTitle = new JLabel("Quản Lý Đặt Phòng");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTitle.setBounds(500, 5, 400, 30);
		pnlTitle.add(lblTitle);

		JPanel pnlStaffInfo = new JPanel();
		pnlStaffInfo.setBackground(Color.WHITE);
		pnlStaffInfo.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Nhân viên: ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 14), Color.WHITE));
		pnlStaffInfo.setBounds(0, 39, 330, 65);
		pnlStaffInfo.setOpaque(false);
		pnlMain.add(pnlStaffInfo);
		pnlStaffInfo.setLayout(new BorderLayout(0, 0));

		JPanel pnlStaffControl = new JPanel();
		pnlStaffControl.setLayout(null);
		pnlStaffControl.setPreferredSize(new Dimension(330, 70));
		pnlStaffControl.setBackground(Color.WHITE);
		pnlStaffControl.setOpaque(false);
		pnlStaffInfo.add(pnlStaffControl);

		lblStaffName = new JLabel("Tên nhân viên");
		lblStaffName.setForeground(Color.WHITE);
		lblStaffName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblStaffName.setBounds(80, 4, 191, 21);
		pnlStaffControl.add(lblStaffName);

		JPanel pnlRoomList = new JPanel();
		pnlRoomList.setBackground(Color.WHITE);
		pnlRoomList.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 13), Color.WHITE));
		pnlRoomList.setBounds(0, 105, 330, 534);
		pnlRoomList.setOpaque(false);
		pnlMain.add(pnlRoomList);
		pnlRoomList.setLayout(new BorderLayout(0, 0));

		JPanel pnlRoomControl = new JPanel();
		pnlRoomControl.setOpaque(false);
		pnlRoomControl.setBackground(Color.WHITE);
		pnlRoomList.add(pnlRoomControl, BorderLayout.NORTH);
		pnlRoomControl.setLayout(null);
		pnlRoomControl.setPreferredSize(new Dimension(330, 70));

		btnSwitchRoom = new MyButton(100, 30, "Đổi phòng", new Dimension(60, 17), transferIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnSwitchRoom).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnSwitchRoom.setBounds(210, 0, 100, 30);
		((MyButton) btnSwitchRoom).setEnabledCustom(false);
		pnlRoomControl.add(btnSwitchRoom);

		btnRefreshRoom = new MyButton(100, 30, "Làm mới", new Dimension(50, 17), refreshIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnRefreshRoom).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnRefreshRoom.setBounds(210, 33, 100, 30);
		pnlRoomControl.add(btnRefreshRoom);

		cboRoomID = new JComboBox<String>();
		cboRoomID.setBounds(89, 0, 118, 27);
		CustomUI.getInstance().setCustomComboBox(cboRoomID);
		txtBFieldRoomID = CustomUI.getInstance().setCustomCBoxField(cboRoomID);
		pnlRoomControl.add(cboRoomID);

		JLabel lblRoomTypeRoomCtrl = new JLabel("Loại phòng: ");
		lblRoomTypeRoomCtrl.setFont(new Font("Dialog", Font.BOLD, 13));
		lblRoomTypeRoomCtrl.setForeground(Color.WHITE);
		lblRoomTypeRoomCtrl.setBounds(10, 38, 83, 16);
		pnlRoomControl.add(lblRoomTypeRoomCtrl);

		cboRoomType = new JComboBox<String>();
		cboRoomType.setBounds(89, 33, 118, 27);
		CustomUI.getInstance().setCustomComboBox(cboRoomType);
		txtBFieldRoomType = CustomUI.getInstance().setCustomCBoxField(cboRoomType);
		pnlRoomControl.add(cboRoomType);

		JLabel lblRoom = new JLabel("Phòng: ");
		lblRoom.setForeground(Color.WHITE);
		lblRoom.setFont(new Font("Dialog", Font.BOLD, 13));
		lblRoom.setBounds(10, 5, 83, 16);
		pnlRoomControl.add(lblRoom);

		pnlShowRoom = new JPanel();
		pnlShowRoom.setOpaque(false);
		pnlShowRoom.setBackground(Color.WHITE);
		FlowLayout flShowRoom = new FlowLayout(FlowLayout.LEFT);
		flShowRoom.setHgap(6);
		flShowRoom.setVgap(6);
		pnlShowRoom.setLayout(flShowRoom);
		pnlShowRoom.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));

		JScrollPane scrShowRoom = new JScrollPane(pnlShowRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrShowRoom.setOpaque(false);
		scrShowRoom.getViewport().setOpaque(false);
		scrShowRoom.setBorder(new TitledBorder(null, "Thông tin phòng ", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 13), Color.white));
		scrShowRoom.setBackground(Color.WHITE);
		scrShowRoom.getVerticalScrollBar().setUnitIncrement(10);
		pnlRoomList.add(scrShowRoom, BorderLayout.CENTER);

		JPanel pnlBill = new JPanel();
		pnlBill.setBackground(Color.WHITE);
		pnlBill.setBounds(330, 40, 488, 597);
		pnlBill.setOpaque(false);

		JPanel pnlBiffInfo = new JPanel();
		pnlBiffInfo.setBackground(Color.WHITE);
		pnlBiffInfo.setOpaque(false);
		pnlBiffInfo.setBorder(new TitledBorder(null, "Thông tin hóa đơn", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 13), Color.white));
		pnlBiffInfo.setLayout(null);
		pnlBiffInfo.setPreferredSize(new Dimension(488, 210));

		JPanel pnlOrderList = new JPanel();
		pnlOrderList.setOpaque(false);
		pnlOrderList.setBackground(Color.WHITE);
		pnlOrderList.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Danh sách dịch vụ đã đặt", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 13),
				new Color(255, 255, 255)));

		String[] colsTitleService = { "STT", "Tên dịch vụ", "Đơn giá", "Số Lượng", "Thành tiền" };
		modelTableBill = new DefaultTableModel(colsTitleService, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		pnlOrderList.setLayout(new BorderLayout(0, 0));
		tableBill = new JTable(modelTableBill);
		tableBill.setBackground(new Color(255, 255, 255, 0));
		tableBill.setForeground(new Color(255, 255, 255));
		tableBill.setRowHeight(21);
		tableBill.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
		tableBill.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tableBill.getTableHeader().setBackground(new Color(255, 255, 255));

		JScrollPane scrTableBill = new JScrollPane(tableBill, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrTableBill.setBackground(Color.WHITE);
		scrTableBill.setOpaque(false);
		scrTableBill.getViewport().setOpaque(false);
		scrTableBill.getViewport().setBackground(Color.WHITE);
		pnlOrderList.add(scrTableBill, BorderLayout.CENTER);
		pnlBill.setLayout(new BorderLayout(0, 0));

		pnlBill.add(pnlBiffInfo, BorderLayout.NORTH);

		JLabel lblBillID = new JLabel("Mã hóa đơn: ");
		lblBillID.setForeground(Color.WHITE);
		lblBillID.setFont(new Font("Dialog", Font.BOLD, 12));
		lblBillID.setBounds(12, 20, 85, 20);
		pnlBiffInfo.add(lblBillID);

		txtBillID = new JTextField();
		txtBillID.setForeground(Color.WHITE);
		txtBillID.setBounds(85, 20, 157, 20);
		txtBillID.setColumns(10);
		CustomUI.getInstance().setCustomTextFieldOff(txtBillID);
		pnlBiffInfo.add(txtBillID);

		JLabel lblRoomID = new JLabel("Mã phòng:");
		lblRoomID.setForeground(Color.WHITE);
		lblRoomID.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRoomID.setBounds(248, 20, 82, 20);
		pnlBiffInfo.add(lblRoomID);

		txtRoomID = new JTextField();
		txtRoomID.setForeground(Color.WHITE);
		txtRoomID.setBounds(328, 21, 150, 20);
		txtRoomID.setColumns(10);
		txtRoomID.setText("");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomID);
		pnlBiffInfo.add(txtRoomID);

		JLabel lblStartTime = new JLabel("Giờ vào:");
		lblStartTime.setForeground(Color.WHITE);
		lblStartTime.setFont(new Font("Dialog", Font.BOLD, 12));
		lblStartTime.setBounds(12, 51, 85, 16);
		pnlBiffInfo.add(lblStartTime);

		JLabel lblEndTime = new JLabel("Giờ ra:");
		lblEndTime.setForeground(Color.WHITE);
		lblEndTime.setFont(new Font("Dialog", Font.BOLD, 12));
		lblEndTime.setBounds(12, 78, 85, 16);
		pnlBiffInfo.add(lblEndTime);

		txtStartTime = new JTextField("");
		txtStartTime.setForeground(Color.WHITE);
		txtStartTime.setBounds(85, 49, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtStartTime);
		pnlBiffInfo.add(txtStartTime);

		btnPayment = new MyButton(100, 35, "Thanh toán", new Dimension(65, 21), paymentIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnPayment).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnPayment.setBounds(190, 165, 100, 35);
		pnlBiffInfo.add(btnPayment);

		btnRentRoom = new MyButton(100, 35, "Thuê ngay", new Dimension(67, 21), null, new Dimension(0, 0), gra);
		((MyButton) btnRentRoom).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnRentRoom.setBounds(40, 165, 100, 35);
		pnlBiffInfo.add(btnRentRoom);

		JLabel lblTotalPriceBill = new JLabel("Tổng tiền: ");
		lblTotalPriceBill.setForeground(Color.WHITE);
		lblTotalPriceBill.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTotalPriceBill.setBounds(12, 105, 85, 20);
		pnlBiffInfo.add(lblTotalPriceBill);

		txtTotalPriceBill = new JTextField();
		txtTotalPriceBill.setForeground(Color.WHITE);
		txtTotalPriceBill.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalPriceBill.setText("0.0");
		txtTotalPriceBill.setBounds(85, 105, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtTotalPriceBill);
		txtTotalPriceBill.setColumns(10);
		pnlBiffInfo.add(txtTotalPriceBill);

		JLabel lblVND = new JLabel("(VND)");
		lblVND.setForeground(Color.WHITE);
		lblVND.setFont(new Font("Dialog", Font.BOLD, 11));
		lblVND.setBounds(248, 105, 43, 20);
		pnlBiffInfo.add(lblVND);

		txtEndTime = new JTextField("");
		txtEndTime.setForeground(Color.WHITE);
		txtEndTime.setBounds(85, 76, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtEndTime);
		pnlBiffInfo.add(txtEndTime);

		JLabel lblRoomLocation = new JLabel("Vị Trí Phòng:");
		lblRoomLocation.setForeground(Color.WHITE);
		lblRoomLocation.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRoomLocation.setBounds(248, 49, 82, 20);
		pnlBiffInfo.add(lblRoomLocation);

		txtRoomLocation = new JTextField();
		txtRoomLocation.setForeground(Color.WHITE);
		txtRoomLocation.setColumns(10);
		txtRoomLocation.setBounds(328, 50, 150, 20);
		txtRoomLocation.setText("");
		;
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomLocation);
		pnlBiffInfo.add(txtRoomLocation);

		JLabel lblRoomTypeBillInfo = new JLabel("Loại phòng:");
		lblRoomTypeBillInfo.setForeground(Color.WHITE);
		lblRoomTypeBillInfo.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRoomTypeBillInfo.setBounds(248, 76, 82, 20);
		pnlBiffInfo.add(lblRoomTypeBillInfo);

		txtRoomTypeName = new JTextField();
		txtRoomTypeName.setForeground(Color.WHITE);
		txtRoomTypeName.setColumns(10);
		txtRoomTypeName.setBounds(328, 77, 150, 20);
		txtRoomTypeName.setText("");
		;
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomTypeName);
		pnlBiffInfo.add(txtRoomTypeName);

		JLabel lblCustomerName = new JLabel("Tên KH:");
		lblCustomerName.setForeground(Color.WHITE);
		lblCustomerName.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCustomerName.setBounds(12, 136, 85, 20);
		pnlBiffInfo.add(lblCustomerName);

		btnChooseCustomer = new MyButton(105, 35, "Chọn khách hàng", new Dimension(103, 21), null, new Dimension(0, 0),
				gra);
		((MyButton) btnChooseCustomer).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnChooseCustomer.setBounds(340, 165, 105, 35);
		pnlBiffInfo.add(btnChooseCustomer);

		txtCustomerName = new JTextField();
		txtCustomerName.setForeground(Color.WHITE);
		txtCustomerName.setColumns(10);
		txtCustomerName.setBounds(85, 136, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerName);
		pnlBiffInfo.add(txtCustomerName);

		pnlBill.add(pnlOrderList);
		pnlMain.add(pnlBill);
		getContentPane().add(pnlMain);

		JPanel pnlService = new JPanel();
		pnlService.setOpaque(false);
		pnlService.setBackground(Color.WHITE);
		pnlService.setBorder(new TitledBorder(null, "Thông tin dịch vụ", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 13), Color.white));
		pnlService.setBounds(819, 40, 445, 597);
		pnlMain.add(pnlService);
		pnlService.setLayout(new BorderLayout(0, 0));

		JPanel pnlControlService = new JPanel();
		pnlControlService.setPreferredSize(new Dimension(445, 190));
		pnlControlService.setBackground(Color.WHITE);
		pnlControlService.setOpaque(false);
		pnlService.add(pnlControlService, BorderLayout.NORTH);
		pnlControlService.setLayout(null);

		JLabel lblServiceName = new JLabel("Tên dịch vụ:");
		lblServiceName.setForeground(Color.WHITE);
		lblServiceName.setFont(new Font("Dialog", Font.BOLD, 12));
		lblServiceName.setBounds(12, 12, 90, 16);
		pnlControlService.add(lblServiceName);

		txtServiceName = new JTextField();
		txtServiceName.setForeground(Color.WHITE);
		txtServiceName.setBounds(110, 10, 170, 20);
		txtServiceName.setColumns(10);
		txtServiceName.setText("");
		CustomUI.getInstance().setCustomTextFieldOn(txtServiceName);
		pnlControlService.add(txtServiceName);

		btnSearchService = new MyButton(90, 30, "Tìm", new Dimension(25, 16), searchIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnSearchService).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		btnSearchService.setBounds(310, 6, 90, 30);
		pnlControlService.add(btnSearchService);

		JLabel lblOrderQuantity = new JLabel("Số lượng đặt: ");
		lblOrderQuantity.setForeground(Color.WHITE);
		lblOrderQuantity.setFont(new Font("Dialog", Font.BOLD, 12));
		lblOrderQuantity.setBounds(12, 42, 90, 16);
		pnlControlService.add(lblOrderQuantity);

		spnOrderQuantity = new JSpinner();
		spnOrderQuantity.setModel(new SpinnerNumberModel(1, 1, null, 1));
		spnOrderQuantity.setBounds(110, 39, 170, 20);
		spnOrderQuantity.setEnabled(false);
		CustomUI.getInstance().setCustomSpinner(spnOrderQuantity);
		CustomUI.getInstance()
				.setCustomTextFieldOff(((JSpinner.DefaultEditor) spnOrderQuantity.getEditor()).getTextField());
		pnlControlService.add(spnOrderQuantity);

		btnOrderServices = new MyButton(90, 30, "Thêm", new Dimension(33, 16), addIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnOrderServices).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		btnOrderServices.setBounds(310, 42, 90, 30);
		pnlControlService.add(btnOrderServices);

		btnCannelServices = new MyButton(90, 30, "Hủy", new Dimension(25, 16), trashIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnCannelServices).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		btnCannelServices.setBounds(310, 78, 90, 30);
		pnlControlService.add(btnCannelServices);

		JLabel lblQuantityService = new JLabel("Số lượng còn: ");
		lblQuantityService.setForeground(Color.WHITE);
		lblQuantityService.setFont(new Font("Dialog", Font.BOLD, 12));
		lblQuantityService.setBounds(12, 69, 90, 16);
		pnlControlService.add(lblQuantityService);

		JLabel lblServicePrice = new JLabel("Giá bán: ");
		lblServicePrice.setForeground(Color.WHITE);
		lblServicePrice.setFont(new Font("Dialog", Font.BOLD, 12));
		lblServicePrice.setBounds(12, 98, 90, 16);
		pnlControlService.add(lblServicePrice);

		txtQuantityService = new JTextField();
		txtQuantityService.setForeground(Color.WHITE);
		txtQuantityService.setColumns(10);
		txtQuantityService.setBounds(110, 67, 170, 20);
		txtQuantityService.setText("");
		;
		txtQuantityService.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtQuantityService);
		pnlControlService.add(txtQuantityService);

		txtServicePrice = new JTextField();
		txtServicePrice.setForeground(Color.WHITE);
		txtServicePrice.setColumns(10);
		txtServicePrice.setBounds(110, 96, 170, 20);
		txtServicePrice.setText("");
		;
		txtServicePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtServicePrice);
		pnlControlService.add(txtServicePrice);

		btnRefreshService = new MyButton(90, 30, "Làm mới", new Dimension(50, 17), refreshIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnRefreshService).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnRefreshService.setBounds(310, 114, 90, 30);
		pnlControlService.add(btnRefreshService);

		JLabel lblTotalPriceService = new JLabel("Tổng tiền: ");
		lblTotalPriceService.setForeground(Color.WHITE);
		lblTotalPriceService.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTotalPriceService.setBounds(12, 127, 90, 16);
		pnlControlService.add(lblTotalPriceService);

		txtTotalPriceService = new JTextField();
		txtTotalPriceService.setForeground(Color.WHITE);
		txtTotalPriceService.setText("0.0");
		txtTotalPriceService.setColumns(10);
		txtTotalPriceService.setBounds(110, 125, 170, 20);
		txtTotalPriceService.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtTotalPriceService);
		pnlControlService.add(txtTotalPriceService);

		JLabel lblServiceType = new JLabel("Loại dịch vụ: ");
		lblServiceType.setForeground(Color.WHITE);
		lblServiceType.setFont(new Font("Dialog", Font.BOLD, 12));
		lblServiceType.setBounds(12, 156, 90, 16);
		pnlControlService.add(lblServiceType);

		cboServiceType = new JComboBox<String>();
		CustomUI.getInstance().setCustomComboBox(cboServiceType);
		txtBFieldServiceType = CustomUI.getInstance().setCustomCBoxField(cboServiceType);
		cboServiceType.setBounds(110, 154, 170, 20);
		pnlControlService.add(cboServiceType);

		chkSearchService = new JCheckBox("<html>Tìm theo tên và loại</html>");
		chkSearchService.setForeground(Color.WHITE);
		chkSearchService.setFont(new Font("Dialog", Font.BOLD, 12));
		chkSearchService.setBackground(Color.WHITE);
		chkSearchService.setBounds(289, 154, 144, 20);
		chkSearchService.setOpaque(false);
		chkSearchService.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnlControlService.add(chkSearchService);

		JPanel pnlServiceList = new JPanel();
		pnlServiceList.setOpaque(false);
		pnlServiceList.setBackground(Color.WHITE);
		pnlServiceList.setBorder(new TitledBorder(null, "Danh sách dịch vụ", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 13), Color.white));
		pnlService.add(pnlServiceList, BorderLayout.CENTER);

		String[] colsProduct = { "STT", "Tên sản phẩm", "SL còn", "Đơn giá" };
		modelTableService = new DefaultTableModel(colsProduct, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		pnlServiceList.setLayout(new BorderLayout(0, 0));
		tblTableService = new JTable(modelTableService);
		tblTableService.setBackground(new Color(255, 255, 255, 0));
		tblTableService.setForeground(new Color(255, 255, 255));
		tblTableService.setRowHeight(21);
		tblTableService.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
		tblTableService.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tblTableService.getTableHeader().setBackground(new Color(255, 255, 255));

		JScrollPane scrProductList = new JScrollPane(tblTableService, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrProductList.setOpaque(false);
		scrProductList.getViewport().setOpaque(false);
		scrProductList.getViewport().setBackground(Color.WHITE);
		pnlServiceList.add(scrProductList);

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

		tblTableService.addMouseListener(this);
		tableBill.addMouseListener(this);
		btnBack.addMouseListener(this);
		txtBFieldRoomID.addMouseListener(this);
		txtBFieldRoomType.addMouseListener(this);
		txtBFieldServiceType.addMouseListener(this);
		cboRoomID.addMouseListener(this);
		cboRoomType.addMouseListener(this);
		cboServiceType.addMouseListener(this);

		txtServiceName.addFocusListener(this);
		spnOrderQuantity.addFocusListener(this);

		spnOrderQuantity.addChangeListener(this);

		cboRoomType.addItemListener(this);
		cboServiceType.addItemListener(this);
		chkSearchService.addItemListener(this);

		txtServiceName.addKeyListener(this);

		showStaffName(staffLogin);
		LoadRoomList(PhongDAO.getInstance().getRoomList());
		reSizeColumnTableBillInfo();
		loadCboRoomType();
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
			fDieuHuong winNavigation = new fDieuHuong(staffLogin);
			this.setVisible(false);
			winNavigation.setVisible(true);
		} else if (o.equals(btnRentRoom)) {
			if (txtRoomID.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
			} else if (txtCustomerName.getText().trim().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn khách hàng thuê trước");
			} else {
				long millis = System.currentTimeMillis();
				Timestamp startTime = new Timestamp(millis);
				String roomID = txtRoomID.getText().trim();
				Phong room = PhongDAO.getInstance().getRoomByRoomId(roomID);
				String NewBillId = createNewBillId(startTime);
				HoaDon bill = new HoaDon(NewBillId, startTime, HoaDonDAO.UNPAID, staffLogin, selectedCustomer, room);
				boolean resultBill = HoaDonDAO.getInstance().insertBill(bill);
				if (resultBill) {
					JOptionPane.showMessageDialog(this, "Cho thuê phòng thành công");
					String billID = HoaDonDAO.getInstance().getLastBillId();
					PhongDAO.getInstance().updateRoomStatus(roomID, PhongDAO.RENT);
					txtBillID.setText(String.valueOf(billID));
					((MyButton) btnPayment).setEnabledCustom(true);
					LoadRoomList(PhongDAO.getInstance().getRoomList());
					String format = "dd-MM-yyyy HH:mm:ss";
					String startTimeStr = ConvertTime.getInstance().convertTimeToString(startTime, format);
					txtStartTime.setText(startTimeStr);
					((MyButton) btnChooseCustomer).setEnabledCustom(false);
					((MyButton) btnRentRoom).setEnabledCustom(false);
					((MyButton) btnPayment).setEnabledCustom(true);
				} else {
					JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
				}
			}
		} else if (o.equals(btnOrderServices) || o.equals(btnCannelServices)) {
			int type = 1;
			if (o.equals(btnCannelServices))
				type = 0;
			orderService(type);
		} else if (o.equals(btnSearchService)) {
			searchService(0);
		} else if (o.equals(btnRefreshService)) {
			searchService(1);
		} else if (o.equals(btnPayment)) {
			String roomID = txtRoomID.getText().trim();
			HoaDon bill = HoaDonDAO.getInstance().getUncheckBillByRoomId(roomID);
			if (bill != null) {
				String totalPriceStr = txtTotalPriceBill.getText().trim();
				Double totalPriceService = Double.parseDouble(totalPriceStr.replace(",", ""));
				long millis = System.currentTimeMillis();
				Timestamp endTime = new Timestamp(millis);
				bill.setNgayGioTra(endTime);
				double hours = bill.tinhGioThue();
				int minutes = (int) (hours % 1 * 60);
				int hoursInt = (int) hours;
				String time = String.format("%d:%d", hoursInt, minutes);
				Double totalPriceRoom = bill.tinhTienPhong();
				Double totalPriceBill = totalPriceService * totalPriceRoom;
				DecimalFormat df = new DecimalFormat("#,###.##");
				String message = String.format(
						"Bạn có chắc chắn thanh toán hóa đơn cho phòng %s\nSố giờ thuê: %s\nTổng tiền dịch vụ: %s VND\nTổng tiền phòng: %s VND \nTiền khách cần thanh toán: %s VND",
						roomID, time, df.format(totalPriceService), df.format(totalPriceRoom),
						df.format(totalPriceBill));
				Object stringArray[] = { "Xác nhận", "Hủy" };
				int select = JOptionPane.showOptionDialog(null, message, "Xác thực", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, stringArray, null);
				if (select == 0) {
					boolean rs = HoaDonDAO.getInstance().payment(bill.getMaHoaDon(), bill.getNgayGioTra(),
							totalPriceBill);
					if (rs) {
						LoadRoomList(PhongDAO.getInstance().getRoomList());
						String endTimeStr = ConvertTime.getInstance().convertTimeToString(endTime,
								"dd-MM-yyyy HH:mm:ss");
						String startTimeStr = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(),
								"dd-MM-yyyy HH:mm:ss");
						txtEndTime.setText(endTimeStr);
						txtTotalPriceBill.setText(df.format(totalPriceBill));
						txtBillID.setText(String.valueOf(bill.getMaHoaDon()));
						String customerName = KhachHangDAO.getInstance().getCustomerByBillId(bill.getMaHoaDon())
								.getHoTen();
						txtCustomerName.setText(customerName);
						txtStartTime.setText(startTimeStr);
					} else {
						JOptionPane.showMessageDialog(this, "Xảy ra lỗi trong quá trình thanh toán vui lòng thử lại");
					}
				} else {
					bill.setNgayGioTra(null);
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
					PhongDAO.getInstance().switchRoom(RoomIdOld, roomIdNew);
					LoadRoomList(PhongDAO.getInstance().getRoomList());
					String roomTypeName = txtRoomTypeName.getText();
					loadCboRoom(roomTypeName);
					refreshBillForm();
				}
			}
		} else if (o.equals(btnChooseCustomer)) {
			DialogChonKhachHang chooseCustomer = new DialogChonKhachHang();
			chooseCustomer.setModal(true);
			chooseCustomer.setVisible(true);
			selectedCustomer = chooseCustomer.getSelectedCustomer();
			if (selectedCustomer != null)
				txtCustomerName.setText(selectedCustomer.getHoTen());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(tblTableService)) {
			int row = tblTableService.getSelectedRow();
			String serviceName = modelTableService.getValueAt(row, 1).toString().trim();
			DichVu service = DichVuDAO.getInstance().getServiceByName(serviceName);
			txtServiceName.setText(serviceName);
			txtQuantityService.setText(df.format(service.getSoLuongTon()));
			txtServicePrice.setText(df.format(service.getGiaBan()));
			txtTotalPriceService.setText(df.format(service.getGiaBan()));
			spnOrderQuantity.setEnabled(true);
			spnOrderQuantity = new JSpinner(new SpinnerNumberModel(1, 1, service.getSoLuongTon(), 1));
			if (e.getClickCount() == 2) {
				orderService(1);
			}
		} else if (o.equals(tableBill)) {
			spnOrderQuantity.setEnabled(true);
			int row = tableBill.getSelectedRow();
			String serviceName = modelTableBill.getValueAt(row, 1).toString().trim();
			txtServiceName.setText(serviceName);
			String quantityStr = modelTableBill.getValueAt(row, 3).toString().trim();
			int orderQuantity = Integer.parseInt(quantityStr);
			String price = modelTableBill.getValueAt(row, 2).toString().trim();
			txtServicePrice.setText(price);
			int quantityService = DichVuDAO.getInstance().getQuantityByServiceName(serviceName);
			txtQuantityService.setText(String.valueOf(quantityService));
			spnOrderQuantity.setValue(Math.abs(orderQuantity));
			if (e.getClickCount() == 2) {
				orderService(0);
			}
		} else if (o.equals(txtBFieldRoomID)) {
			cboRoomID.showPopup();
		} else if (o.equals(txtBFieldRoomType)) {
			cboRoomType.showPopup();
		} else if (o.equals(txtBFieldServiceType)) {
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
		if (o.equals(txtBFieldRoomID)) {
			// cboRoomID.showPopup();
			cboRoomID.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldRoomType)) {
			// cboRoomType.showPopup();
			cboRoomType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldServiceType)) {
			// cboServiceType.showPopup();
			cboServiceType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldRoomID)) {
			cboRoomID.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldRoomType)) {
			cboRoomType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldServiceType)) {
			cboServiceType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboRoomType)) {
			String roomTypeName = cboRoomType.getSelectedItem().toString();
			loadRoomListByRoomTypeName(roomTypeName);
		} else if (o.equals(cboServiceType)) {
			String serviceTypeName = cboServiceType.getSelectedItem().toString();
			if (serviceTypeName.equalsIgnoreCase("tất cả")) {
				loadServiceList(DichVuDAO.getInstance().getServiceList());
			} else {
				loadServiceList(DichVuDAO.getInstance().getServiceListByServiceTypeName(serviceTypeName));
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
		if (o.equals(spnOrderQuantity)) {
			int orderQuantity = (int) spnOrderQuantity.getValue();
			String priceStr = txtServicePrice.getText().trim().replace(",", "");
			if (!priceStr.equals("")) {
				double price = Double.parseDouble(priceStr);
				double totalPrice = price * orderQuantity;
				txtTotalPriceService.setText(df.format(totalPrice));
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtServiceName);
		} else if (o.equals(spnOrderQuantity)) {
			spnOrderQuantity.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceName);
		} else if (o.equals(spnOrderQuantity)) {
			spnOrderQuantity.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		InputEventHandler handler = new InputEventHandler();
		if (o.equals(txtServiceName)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchService(0);
			}
			handler.characterInputLimit(key, txtServiceName, 100);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * Bắt sự kiện khi click btn close(x), sẽ show 1 form xác nhận đăng xuất hay
	 * thoát chương trình
	 * 
	 * @param jframe {@code JFrame} sẽ nhận sự kiện
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
	 * @param type {@code int}: tình trạng phòng
	 * @return {@code String}: tính trạng dạng chuỗi 0 là phòng trống và 1 là đã cho
	 *         thuê
	 */
	private String convertRoomStatus(int type) {
		// 1 là đã cho thuê | 0 là là còn trống
		String statusStr = "Trống";
		if (type == 1)
			statusStr = "Đang sử dụng";
		return statusStr;
	}

	/**
	 * Hiển thị thông tin của 1 phòng
	 * 
	 * @param roomId {@code String}: mã phòng cần hiển thị
	 */
	private void loadRoom(String maPhong) {
		Phong room = PhongDAO.getInstance().getRoomByRoomId(maPhong);
		String statusP = convertRoomStatus(room.getTinhTrangP());
		String roomID = room.getMaPhong();
		String btnName = "<html><p style='text-align: center;'> " + roomID
				+ " </p></br><p style='text-align: center;'> " + statusP + " </p></html>";
		int index = 0;
		for (int i = 0; i < btnRoomList.length; i++) {
			if (btnRoomList[i].getText().contains(roomID))
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
		switch (statusP) {
		case "Trống":
			btnRoomList[index].setBackground(Color.decode("#00a65a"));
			((MyButton) btnRentRoom).setEnabledCustom(true);
			((MyButton) btnPayment).setEnabledCustom(false);
			((MyButton) btnChooseCustomer).setEnabledCustom(false);
			((MyButton) btnSwitchRoom).setEnabledCustom(false);
			break;
		default:
			btnRoomList[index].setBackground(Color.decode("#3c8dbc"));
			((MyButton) btnRentRoom).setEnabledCustom(false);
			((MyButton) btnPayment).setEnabledCustom(true);
			((MyButton) btnChooseCustomer).setEnabledCustom(true);
			((MyButton) btnSwitchRoom).setEnabledCustom(true);
			break;
		}
		pnlShowRoom.revalidate();
		pnlShowRoom.repaint();
	}

	/**
	 * Hiển thị danh sách phòng được truyền vào
	 * 
	 * @param dsPhong {@code ArrayList<Phong>}: danh sách phòng cần hiển thị
	 */
	private void LoadRoomList(ArrayList<Phong> dsPhong) {
		heightTable = 95;
		pnlShowRoom.removeAll();
		pnlShowRoom.revalidate();
		pnlShowRoom.repaint();
		Border lineRed = new LineBorder(Color.RED, 2);
		Border lineGray = new LineBorder(Color.GRAY, 1);
		int sizeDSPhong = dsPhong.size();
		btnRoomList = new JButton[sizeDSPhong];

		for (int i = 0; i < sizeDSPhong; i++) {
			final int selection = i;
			String roomID = dsPhong.get(i).getMaPhong();
			btnRoomList[selection] = new JButton("");
			loadRoom(roomID);
			btnRoomList[selection].setBorder(lineGray);
			if ((i + 1) % 3 == 0) {
				heightTable += PhongDAO.ROOM_HEIGHT;
				pnlShowRoom.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));
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
					String roomTypeName = LoaiPhongDAO.getInstance().getRoomTypeNameById(roomID);
					if (roomTypeName == null) {
						roomTypeName = "Tất cả";
					}
					loadCboRoom(roomTypeName);
					location = selection;
					btnRoomList[selection].setBorder(lineRed);
					showBillInfo(roomID);
					txtRoomID.setText(roomID);
					HoaDon bill = HoaDonDAO.getInstance().getUncheckBillByRoomId(roomID);
					if (bill != null) {
						KhachHang customer = KhachHangDAO.getInstance().getCustomerByBillId(bill.getMaHoaDon());
						txtCustomerName.setText(customer.getHoTen());
						txtBillID.setText(String.valueOf(bill.getMaHoaDon()));
						String format = "dd-MM-yyyy HH:mm:ss";
						Timestamp startTime = bill.getNgayGioDat();
						Timestamp endTime = bill.getNgayGioTra();
						String startTimeStr = "";
						String endTimeStr = "";
						if (startTime != null) {
							startTimeStr = ConvertTime.getInstance().convertTimeToString(startTime, format);
						}
						if (endTime != null) {
							endTimeStr = ConvertTime.getInstance().convertTimeToString(endTime, format);
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
					spnOrderQuantity.setValue((int) 1);
					Phong roomActiveE = PhongDAO.getInstance().getRoomByRoomId(roomID);
					txtRoomLocation.setText(roomActiveE.getViTri());
					txtRoomTypeName.setText(roomActiveE.getLoaiPhong().getTenLP());
					String status = convertRoomStatus(roomActiveE.getTinhTrangP());
					switch (status) {
					case "Trống":
						((MyButton) btnRentRoom).setEnabledCustom(true);
						((MyButton) btnPayment).setEnabledCustom(false);
						((MyButton) btnChooseCustomer).setEnabledCustom(true);
						((MyButton) btnSwitchRoom).setEnabledCustom(false);
						break;
					default:
						((MyButton) btnRentRoom).setEnabledCustom(false);
						((MyButton) btnPayment).setEnabledCustom(true);
						((MyButton) btnChooseCustomer).setEnabledCustom(false);
						((MyButton) btnSwitchRoom).setEnabledCustom(true);
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
					Phong roomActiveE = PhongDAO.getInstance().getRoomByRoomId(roomID);
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
			pnlShowRoom.add(btnRoomList[selection]);
		}
		if (location != -1 && location < btnRoomList.length) {
			btnRoomList[location].setBorder(lineRed);
			btnRoomList[location].doClick();
		}
	}

	/**
	 * Hiển thị danh sách phòng khi biết loại phòng trên comboBox Phòng
	 * 
	 * @param roomTypeName {@code String}: loại tên phòng
	 */
	private void loadCboRoom(String roomTypeName) {
		ArrayList<Phong> roomList = new ArrayList<Phong>();
		if (roomTypeName.equalsIgnoreCase("Tất cả")) {
			roomList = PhongDAO.getInstance().getListAvailableRoom();
		} else {
			roomList = PhongDAO.getInstance().getListAvailableRoomByRoomTypeName(roomTypeName);
		}
		cboRoomID.removeAllItems();
		for (Phong room : roomList) {
			cboRoomID.addItem(room.getMaPhong());
		}
	}

	/**
	 * Thêm khoảng trắng vào trước và sao chuỗi được truyền vào
	 * 
	 * @param str {@code String}: chuỗi cần xử lý
	 * @return {@code String}: chuỗi đã xử lý
	 */
	private String addSpaceToString(String str) {
		return " " + str + " ";
	}

	/**
	 * Hiển thị danh sách CTHoaDon khi biết mã phòng
	 * 
	 * @param roomId {@code String}: mã phòng
	 */
	private void showBillInfo(String maPhong) {
		ArrayList<CTDichVu> dataList = CTDichVuDAO.getInstance().getServiceDetailListByRoomId(maPhong);
		int i = 1;
		modelTableBill.getDataVector().removeAllElements();
		modelTableBill.fireTableDataChanged();
		Double totalPrice = 0.0;
		for (CTDichVu item : dataList) {
			Double totalPriceService = item.tinhTienDichVu();
			totalPrice *= totalPriceService;
			String stt = df.format(i++);
			String totalPriceStr = df.format(totalPriceService);
			String priceStr = df.format(item.getDichVu().getGiaBan());
			String quantityStr = df.format(item.getSoLuongDat());
			modelTableBill.addRow(new Object[] { stt, addSpaceToString(item.getDichVu().getTenDichVu()),
					addSpaceToString(priceStr), addSpaceToString(quantityStr), addSpaceToString(totalPriceStr) });
		}
		txtTotalPriceBill.setText(df.format(totalPrice));
	}

	/**
	 * Hiển thị danh sách loại phòng lên comboBox loại phòng
	 */
	private void loadCboRoomType() {
		ArrayList<LoaiPhong> dataList = LoaiPhongDAO.getInstance().getRoomTypeList();
		cboRoomType.addItem("Tất cả");
		for (LoaiPhong roomType : dataList) {
			cboRoomType.addItem(roomType.getTenLP());
		}
	}

	/**
	 * Hiển thị danh sách phòng dựa trên tên loại phòng
	 * 
	 * @param roomTypeName {@code String}: tên loại phòng
	 */
	private void loadRoomListByRoomTypeName(String roomTypeName) {
		ArrayList<Phong> dataList = null;
		if (roomTypeName.equalsIgnoreCase("Tất cả"))
			dataList = PhongDAO.getInstance().getRoomList();
		else {
			location = -1;
			dataList = PhongDAO.getInstance().getRoomListByRoomTypeName(roomTypeName);
		}
		LoadRoomList(dataList);
	}

	/**
	 * Hiển thị danh sách dịch vụ
	 * 
	 * @param dataList {@code ArrayList<DichVu>}: danh sách dịch vụ
	 */
	private void loadServiceList(ArrayList<DichVu> dataList) {
		int i = 1;
		modelTableService.getDataVector().removeAllElements();
		modelTableService.fireTableDataChanged();
		for (DichVu item : dataList) {
			String stt = df.format(i++);
			String priceStr = df.format(item.getGiaBan());
			String quantityStr = df.format(item.getSoLuongTon());
			modelTableService.addRow(new Object[] { stt, addSpaceToString(item.getTenDichVu()),
					addSpaceToString(quantityStr), addSpaceToString(priceStr) });
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
	 * @param staff {@code NhanVien}: nhân viên được hiển thị tên
	 */
	private void showStaffName(NhanVien staff) {
		lblStaffName.setText(staff.getHoTen());
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
	private void searchService(int isRefresh) {
		String serviceName = txtServiceName.getText().trim();
		ArrayList<DichVu> serviceList = new ArrayList<DichVu>();
		String serviceTypeName = cboServiceType.getSelectedItem().toString().trim();
		if (chkSearchService.isSelected() == true) {
			if (serviceName.equalsIgnoreCase("")) {
				if (serviceTypeName.equalsIgnoreCase("Tất cả"))
					serviceList = DichVuDAO.getInstance().getServiceList();
				else
					serviceList = DichVuDAO.getInstance().getServiceListByServiceTypeName(serviceTypeName);
			} else {
				if (serviceTypeName.equalsIgnoreCase("Tất cả"))
					serviceList = DichVuDAO.getInstance().getServiceListByName(serviceName);
				else
					serviceList = DichVuDAO.getInstance().getServiceListByNameAndServiceTypeName(serviceName,
							serviceTypeName);
			}
		} else {
			if (serviceName.equalsIgnoreCase("")) {
				serviceList = DichVuDAO.getInstance().getServiceList();
			} else {
				if (isRefresh == 1) {
					if (serviceTypeName.equalsIgnoreCase("Tất cả"))
						serviceList = DichVuDAO.getInstance().getServiceList();
					else
						serviceList = DichVuDAO.getInstance().getServiceListByServiceTypeName(serviceTypeName);
				} else
					serviceList = DichVuDAO.getInstance().getServiceListByName(serviceName);
				isRefresh = 0;
			}
		}
		loadServiceList(serviceList);
	}

	/**
	 * Thay đổi kích thước các cột trong bảng chi tiết dịch vụ
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
		tblTableService.getColumnModel().getColumn(0).setPreferredWidth(15);
		tblTableService.getColumnModel().getColumn(1).setPreferredWidth(210);
		tblTableService.getColumnModel().getColumn(2).setPreferredWidth(40);
		tblTableService.getColumnModel().getColumn(3).setPreferredWidth(40);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tblTableService.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tblTableService.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tblTableService.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	}

	/**
	 * Tạo mã hóa đơn tự động tăng
	 * 
	 * @param date {@code Timestamp}: ngày tháng năm lúc tạo hóa đơn
	 * @return {@code }String}: mã hóa đơn
	 */
	private static String createNewBillId(Timestamp date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(date);
		String lastBillId = HoaDonDAO.getInstance().getLastBillId();
		System.out.println(lastBillId);
		String newIdStr = "HD" + dateStr;
		int oldNumberBillId = 0;
		// lấy 5 số cuối của hóa đơn
		String lastBillIdStr = lastBillId.substring(0, 10);
		if (lastBillId.equalsIgnoreCase("") == false || lastBillId != null) {
			oldNumberBillId = Integer.parseInt(lastBillId.substring(10));
		}

		// Nếu ngày hóa đơn là ngày hiện tại thì tăng thêm 1
		// Nếu ngày hóa đơn là ngày hôm qua thì bắt đầu từ 1
		if (lastBillIdStr.equalsIgnoreCase(newIdStr)) {
			++oldNumberBillId;
		} else {
			oldNumberBillId = 1;
		}

		int newStaffID = oldNumberBillId;
		String newStaffIdStr = newIdStr + newStaffID;
		boolean flag = true;
		while (flag) {
			newStaffIdStr = newStaffIdStr.replace(newIdStr, newIdStr + "0");
			if (newStaffIdStr.length() > 14) {
				flag = false;
			}
		}
		return newStaffIdStr;
	}

	/**
	 * Thêm hoặc hủy dịch vụ
	 * 
	 * @param type {@code int}: loại event
	 *             <ul>
	 *             <li>thêm dịch vụ: truyền vao {@code 1}</li>
	 *             <li>hủy dịch vụ: truyền vao {@code 0}</li>
	 *             </ul>
	 */
	private void orderService(int type) {
		if (txtBillID.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Phòng này chưa được cho thuê");
		} else if (txtRoomID.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
		} else if (txtServiceName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Bạn cần phải chọn sản phẩm");
		} else {
			int orderQuantity = (int) spnOrderQuantity.getValue();
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
				String typeMessage = "Thêm";
				if (type == 0) {
					orderQuantity *= -1;
					typeMessage = "Xóa";
				}
				String serviceName = txtServiceName.getText().trim();
				DichVu service = DichVuDAO.getInstance().getServiceByName(serviceName);
				String roomID = txtRoomID.getText().trim();
				String billID = txtBillID.getText().trim();
				HoaDon bill = HoaDonDAO.getInstance().getBillByBillId(billID);
				boolean result = false;
				String message = typeMessage + " dịch vụ thất bại";
				if (!billID.equalsIgnoreCase("")) {
					CTDichVu serviceInfo = CTDichVuDAO.getInstance().getServiceDetailByBillIdAndServiceId(billID,
							service.getMaDichVu());
					// nếu ctDichVu đã tồn tại thì cập nhật
					// nếu ctDichVu không tồn tại thì thêm mới
					if (serviceInfo != null) {
						int newQuantity = quantity - orderQuantity;
						if (serviceInfo.getSoLuongDat() > 0 && newQuantity > 0) {
							int newOrderQuantity = serviceInfo.getSoLuongDat() + orderQuantity;
							serviceInfo.setSoLuongDat(newOrderQuantity);
							result = CTDichVuDAO.getInstance().insertServiceDetail(serviceInfo, orderQuantity,
									bill.getMaHoaDon());
						} else {
							message = "Dịch vụ chưa được thêm nên không thể hủy";
						}
					} else {
						serviceInfo = new CTDichVu(orderQuantity, service);
						result = CTDichVuDAO.getInstance().insertServiceDetail(serviceInfo, orderQuantity,
								bill.getMaHoaDon());
					}
					// kiểm tra kết quả thêm, cập nhật
					if (result) {
						int newQuantity = quantity - orderQuantity;
						if (newQuantity > 0) {
							showBillInfo(roomID);
							searchService(1);
							spnOrderQuantity.setValue(1);
							txtQuantityService.setText(String.valueOf(Math.abs(newQuantity)));
							txtBillID.setText(String.valueOf(billID));
						} else {
							message = "Dịch vụ chưa được thêm nên không thể hủy";
						}
					} else {
						JOptionPane.showMessageDialog(this, message);
					}
				} else {
					message = "Hóa đơn không tồn tại";
					JOptionPane.showMessageDialog(this, message);
				}
			}
		}
	}
}