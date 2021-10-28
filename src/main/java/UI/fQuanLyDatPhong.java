package UI;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.*;

import DAO.*;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.DialogChonKhachHang;
import UI.PanelCustom.MyButton;
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
	private JTextField txtTotalPriceService, txtBFieldRoomID, txtBFieldRoomType, txtBFieldServiceType;
	private JComboBox<String> cboRoomType, cboRoomID, cboServiceType;
	private JCheckBox chkSearchService;
	private JSpinner spinOrderQuantity;
	private JMenuBar menuBar;

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
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu menuTK = new JMenu("Quản lý thông tin khách hàng");

		menuBar.add(menuTK);
	}

	/**
	 * Hàm tạo form
	 */
	public void createFrom() {
		pnMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				setFont(new Font("Dialog", Font.PLAIN, 12));
				Image bgMain = bg.getImage();
				g2.drawImage(bgMain, 0, 0, null);
			}
		};
		pnMain.setBackground(Color.WHITE);
		pnMain.setLayout(null);
		JPanel pnTitle = new JPanel();

		pnTitle.setBounds(0, 0, 1264, 39);
		pnTitle.setLayout(null);
		pnTitle.setOpaque(false);
		pnMain.add(pnTitle);
		pnTitle.setBackground(Color.decode("#d0e1fd"));

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 33, 19, 12, 5);
		btnBack.setBounds(1150, 5, 100, 35);
		((MyButton) btnBack).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		pnTitle.add(btnBack);

		JLabel lbTitle = new JLabel("Quản Lý Đặt Phòng");
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lbTitle.setBounds(500, 5, 400, 30);
		pnTitle.add(lbTitle);

		JPanel pnEmpInfo = new JPanel();
		pnEmpInfo.setBackground(Color.WHITE);
		pnEmpInfo.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Nh\u00E2n Vi\u00EAn: ", TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 13),
				Color.WHITE));
		pnEmpInfo.setBounds(0, 39, 330, 65);
		pnEmpInfo.setOpaque(false);
		pnMain.add(pnEmpInfo);
		pnEmpInfo.setLayout(new BorderLayout(0, 0));

		JPanel pnEmpControl = new JPanel();
		pnEmpControl.setLayout(null);
		pnEmpControl.setPreferredSize(new Dimension(330, 70));
		pnEmpControl.setBackground(Color.WHITE);
		pnEmpControl.setOpaque(false);
		pnEmpInfo.add(pnEmpControl);

		lbEmpName = new JLabel("Tên nhân viên");
		lbEmpName.setForeground(Color.WHITE);
		lbEmpName.setBounds(80, 4, 191, 21);
		pnEmpControl.add(lbEmpName);
		lbEmpName.setFont(new Font("Dialog", Font.BOLD, 20));

		JPanel pnRoomList = new JPanel();
		pnRoomList.setBackground(Color.WHITE);
		pnRoomList.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Phòng",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.BOLD, 13), Color.WHITE));
		pnRoomList.setBounds(0, 105, 330, 534);
		pnRoomList.setOpaque(false);
		pnMain.add(pnRoomList);
		pnRoomList.setLayout(new BorderLayout(0, 0));

		JPanel pnRoomControl = new JPanel();
		pnRoomControl.setOpaque(false);
		pnRoomControl.setBackground(Color.WHITE);
		pnRoomList.add(pnRoomControl, BorderLayout.NORTH);
		pnRoomControl.setLayout(null);
		pnRoomControl.setPreferredSize(new Dimension(330, 70));

		btnSwitchRoom = new MyButton(90, 30, "Đổi phòng", new Dimension(60, 17), transferIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnSwitchRoom).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnSwitchRoom.setBounds(220, 0, 90, 30);
		((MyButton) btnSwitchRoom).setEnabledCustom(false);
		pnRoomControl.add(btnSwitchRoom);

		btnRefreshRoom = new MyButton(90, 30, "Làm mới", new Dimension(50, 17), refreshIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnRefreshRoom).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnRefreshRoom.setBounds(220, 33, 90, 30);
		pnRoomControl.add(btnRefreshRoom);

		cboRoomID = new JComboBox<String>();
		cboRoomID.setBounds(89, 0, 118, 27);
		cboRoomID.setEditable(true);
		cboRoomID.setOpaque(false);
		cboRoomID.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboRoomID.setUI(new BasicComboBoxUI());
		txtBFieldRoomID = (JTextField) cboRoomID.getEditor().getEditorComponent();
		txtBFieldRoomID.setBorder(BorderFactory.createEmptyBorder());
		txtBFieldRoomID.setBackground(new Color(246, 210, 255, 50));
		txtBFieldRoomID.setForeground(Color.WHITE);
		txtBFieldRoomID.setEditable(false);
		cboRoomID.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnRoomControl.add(cboRoomID);

		JLabel lbRoomTypeRoomCtrl = new JLabel("Loại phòng: ");
		lbRoomTypeRoomCtrl.setFont(new Font("Dialog", Font.BOLD, 13));
		lbRoomTypeRoomCtrl.setForeground(Color.WHITE);
		lbRoomTypeRoomCtrl.setBounds(10, 38, 83, 16);
		pnRoomControl.add(lbRoomTypeRoomCtrl);

		cboRoomType = new JComboBox<String>();
		cboRoomType.setBounds(89, 33, 118, 27);
		cboRoomType.setEditable(true);
		cboRoomType.setOpaque(false);
		cboRoomType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboRoomType.setUI(new BasicComboBoxUI());
		txtBFieldRoomType = (JTextField) cboRoomType.getEditor().getEditorComponent();
		txtBFieldRoomType.setBorder(BorderFactory.createEmptyBorder());
		txtBFieldRoomType.setBackground(new Color(246, 210, 255, 50));
		txtBFieldRoomType.setForeground(Color.WHITE);
		txtBFieldRoomType.setEditable(false);
		txtBFieldRoomType.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnRoomControl.add(cboRoomType);

		JLabel lbRoom = new JLabel("Phòng: ");
		lbRoom.setForeground(Color.WHITE);
		lbRoom.setFont(new Font("Dialog", Font.BOLD, 13));
		lbRoom.setBounds(10, 5, 83, 16);
		pnRoomControl.add(lbRoom);

		pnShowRoom = new JPanel();
		pnShowRoom.setOpaque(false);
		pnShowRoom.setBackground(Color.WHITE);
		FlowLayout flShowRoom = new FlowLayout(FlowLayout.LEFT);
		flShowRoom.setHgap(6);
		flShowRoom.setVgap(6);
		pnShowRoom.setLayout(flShowRoom);
		pnShowRoom.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));

		JScrollPane scpShowRoom = new JScrollPane(pnShowRoom, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scpShowRoom.setOpaque(false);
		scpShowRoom.getViewport().setOpaque(false);

		JMenu mnNewMenu = new JMenu("New menu");
		pnShowRoom.add(mnNewMenu);
		pnRoomList.add(scpShowRoom, BorderLayout.CENTER);
		scpShowRoom.setBorder(new TitledBorder(null, "Thông tin cá nhân ", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 13), Color.white));
		scpShowRoom.setBackground(Color.WHITE);
		scpShowRoom.getVerticalScrollBar().setUnitIncrement(10);

		JPanel pnBill = new JPanel();
		pnBill.setBackground(Color.WHITE);
		pnBill.setBounds(330, 40, 488, 597);
		pnBill.setOpaque(false);

		JPanel pnBiffInfo = new JPanel();
		pnBiffInfo.setBackground(Color.WHITE);
		pnBiffInfo.setOpaque(false);
		pnBiffInfo.setBorder(new TitledBorder(null, "Thông tin hóa đơn", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 13), Color.white));
		pnBiffInfo.setLayout(null);
		pnBiffInfo.setPreferredSize(new Dimension(488, 210));

		JPanel pnOrderList = new JPanel();
		pnOrderList.setOpaque(false);
		pnOrderList.setBackground(Color.WHITE);
		pnOrderList.setBorder(new TitledBorder(
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
		pnOrderList.setLayout(new BorderLayout(0, 0));
		tableBill = new JTable(modelTableBill);
		tableBill.setBackground(new Color(255, 255, 255, 0));
		tableBill.setForeground(new Color(255, 255, 255));
		tableBill.setRowHeight(21);
		tableBill.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
		tableBill.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tableBill.getTableHeader().setBackground(new Color(255, 255, 255));

		JScrollPane scpTableBill = new JScrollPane(tableBill, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scpTableBill.setBackground(Color.WHITE);
		scpTableBill.setOpaque(false);
		scpTableBill.getViewport().setOpaque(false);
		scpTableBill.getViewport().setBackground(Color.WHITE);
		pnOrderList.add(scpTableBill, BorderLayout.CENTER);
		pnBill.setLayout(new BorderLayout(0, 0));

		pnBill.add(pnBiffInfo, BorderLayout.NORTH);

		JLabel lbBillID = new JLabel("Mã hóa đơn: ");
		lbBillID.setForeground(Color.WHITE);
		lbBillID.setFont(new Font("Dialog", Font.BOLD, 12));
		lbBillID.setBounds(12, 20, 85, 20);
		pnBiffInfo.add(lbBillID);

		txtBillID = new JTextField();
		txtBillID.setForeground(Color.WHITE);
		txtBillID.setBounds(85, 20, 157, 20);
		txtBillID.setColumns(10);
		txtBillID.setText("");
		txtBillID.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtBillID);
		pnBiffInfo.add(txtBillID);

		JLabel lbRoomID = new JLabel("Mã phòng:");
		lbRoomID.setForeground(Color.WHITE);
		lbRoomID.setFont(new Font("Dialog", Font.BOLD, 12));
		lbRoomID.setBounds(248, 20, 82, 20);
		pnBiffInfo.add(lbRoomID);

		txtRoomID = new JTextField();
		txtRoomID.setForeground(Color.WHITE);
		txtRoomID.setBounds(328, 21, 150, 20);
		txtRoomID.setColumns(10);
		txtRoomID.setText("");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomID);
		pnBiffInfo.add(txtRoomID);

		JLabel lbStartTime = new JLabel("Giờ vào:");
		lbStartTime.setForeground(Color.WHITE);
		lbStartTime.setFont(new Font("Dialog", Font.BOLD, 12));
		lbStartTime.setBounds(12, 51, 85, 16);
		pnBiffInfo.add(lbStartTime);

		JLabel lbEndTime = new JLabel("Giờ ra:");
		lbEndTime.setForeground(Color.WHITE);
		lbEndTime.setFont(new Font("Dialog", Font.BOLD, 12));
		lbEndTime.setBounds(12, 78, 85, 16);
		pnBiffInfo.add(lbEndTime);

		txtStartTime = new JTextField("");
		txtStartTime.setForeground(Color.WHITE);
		txtStartTime.setBounds(85, 49, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtStartTime);
		pnBiffInfo.add(txtStartTime);

		btnPayment = new MyButton(100, 35, "Thanh toán", new Dimension(65, 21), paymentIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnPayment).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnPayment.setBounds(190, 165, 100, 35);
		pnBiffInfo.add(btnPayment);

		btnRentRoom = new MyButton(100, 35, "Thuê ngay", new Dimension(67, 21), null, new Dimension(0, 0), gra);
		((MyButton) btnRentRoom).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnRentRoom.setBounds(40, 165, 100, 35);
		pnBiffInfo.add(btnRentRoom);

		JLabel lbTotalPriceBill = new JLabel("Tổng tiền: ");
		lbTotalPriceBill.setForeground(Color.WHITE);
		lbTotalPriceBill.setFont(new Font("Dialog", Font.BOLD, 12));
		lbTotalPriceBill.setBounds(12, 105, 85, 20);
		pnBiffInfo.add(lbTotalPriceBill);

		txtTotalPriceBill = new JTextField();
		txtTotalPriceBill.setForeground(Color.WHITE);
		txtTotalPriceBill.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalPriceBill.setText("0.0");
		txtTotalPriceBill.setBounds(85, 105, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtTotalPriceBill);
		txtTotalPriceBill.setColumns(10);
		pnBiffInfo.add(txtTotalPriceBill);

		JLabel lbVND = new JLabel("(VND)");
		lbVND.setForeground(Color.WHITE);
		lbVND.setFont(new Font("Dialog", Font.BOLD, 11));
		lbVND.setBounds(248, 105, 43, 20);
		pnBiffInfo.add(lbVND);

		txtEndTime = new JTextField("");
		txtEndTime.setForeground(Color.WHITE);
		txtEndTime.setBounds(85, 76, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtEndTime);
		pnBiffInfo.add(txtEndTime);

		JLabel lbRoomLocation = new JLabel("Vị Trí Phòng:");
		lbRoomLocation.setForeground(Color.WHITE);
		lbRoomLocation.setFont(new Font("Dialog", Font.BOLD, 12));
		lbRoomLocation.setBounds(248, 49, 82, 20);
		pnBiffInfo.add(lbRoomLocation);

		txtRoomLocation = new JTextField();
		txtRoomLocation.setForeground(Color.WHITE);
		txtRoomLocation.setColumns(10);
		txtRoomLocation.setBounds(328, 50, 150, 20);
		txtRoomLocation.setText("");
		;
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomLocation);
		pnBiffInfo.add(txtRoomLocation);

		JLabel lbRoomTypeBillInfo = new JLabel("Loại phòng:");
		lbRoomTypeBillInfo.setForeground(Color.WHITE);
		lbRoomTypeBillInfo.setFont(new Font("Dialog", Font.BOLD, 12));
		lbRoomTypeBillInfo.setBounds(248, 76, 82, 20);
		pnBiffInfo.add(lbRoomTypeBillInfo);

		txtRoomTypeName = new JTextField();
		txtRoomTypeName.setForeground(Color.WHITE);
		txtRoomTypeName.setColumns(10);
		txtRoomTypeName.setBounds(328, 77, 150, 20);
		txtRoomTypeName.setText("");
		;
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomTypeName);
		pnBiffInfo.add(txtRoomTypeName);

		JLabel lbCustomerName = new JLabel("Tên KH:");
		lbCustomerName.setForeground(Color.WHITE);
		lbCustomerName.setFont(new Font("Dialog", Font.BOLD, 12));
		lbCustomerName.setBounds(12, 136, 85, 20);
		pnBiffInfo.add(lbCustomerName);

		btnChooseCustomer = new MyButton(105, 35, "Chọn khách hàng", new Dimension(103, 21), null, new Dimension(0, 0),
				gra);
		((MyButton) btnChooseCustomer).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnChooseCustomer.setBounds(340, 165, 105, 35);
		pnBiffInfo.add(btnChooseCustomer);

		txtCustomerName = new JTextField();
		txtCustomerName.setForeground(Color.WHITE);
		txtCustomerName.setColumns(10);
		txtCustomerName.setBounds(85, 136, 157, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerName);
		pnBiffInfo.add(txtCustomerName);

		pnBill.add(pnOrderList);
		pnMain.add(pnBill);
		getContentPane().add(pnMain);

		JPanel pnService = new JPanel();
		pnService.setOpaque(false);
		pnService.setBackground(Color.WHITE);
		pnService.setBorder(new TitledBorder(null, "Thông tin dịch vụ", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 13), Color.white));
		pnService.setBounds(819, 40, 445, 597);
		pnMain.add(pnService);
		pnService.setLayout(new BorderLayout(0, 0));

		JPanel pnControlService = new JPanel();
		pnControlService.setPreferredSize(new Dimension(445, 190));
		pnControlService.setBackground(Color.WHITE);
		pnControlService.setOpaque(false);
		pnService.add(pnControlService, BorderLayout.NORTH);
		pnControlService.setLayout(null);

		JLabel lbServiceName = new JLabel("Tên dịch vụ:");
		lbServiceName.setForeground(Color.WHITE);
		lbServiceName.setFont(new Font("Dialog", Font.BOLD, 12));
		lbServiceName.setBounds(12, 12, 90, 16);
		pnControlService.add(lbServiceName);

		txtServiceName = new JTextField();
		txtServiceName.setForeground(Color.WHITE);
		txtServiceName.setBounds(110, 10, 170, 20);
		txtServiceName.setColumns(10);
		txtServiceName.setText("");
		CustomUI.getInstance().setCustomTextFieldOn(txtServiceName);
		pnControlService.add(txtServiceName);

		btnSearchService = new MyButton(90, 30, "Tìm", new Dimension(25, 16), searchIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnSearchService).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		btnSearchService.setBounds(310, 6, 90, 30);
		pnControlService.add(btnSearchService);

		JLabel lbOrderQuantity = new JLabel("Số lượng đặt: ");
		lbOrderQuantity.setForeground(Color.WHITE);
		lbOrderQuantity.setFont(new Font("Dialog", Font.BOLD, 12));
		lbOrderQuantity.setBounds(12, 42, 90, 16);
		pnControlService.add(lbOrderQuantity);

		spinOrderQuantity = new JSpinner();
		spinOrderQuantity.setModel(new SpinnerNumberModel(1, 1, null, 1));
		spinOrderQuantity.setBounds(110, 39, 170, 20);
		spinOrderQuantity.setEnabled(false);
		CustomUI.getInstance().setCustomSpinner(spinOrderQuantity);
		spinOrderQuantity.setModel(new SpinnerNumberModel(0, 0, null, 1));
		spinOrderQuantity.setOpaque(false);
		CustomUI.getInstance()
				.setCustomTextFieldOff(((JSpinner.DefaultEditor) spinOrderQuantity.getEditor()).getTextField());
		pnControlService.add(spinOrderQuantity);

		btnOrderServices = new MyButton(90, 30, "Thêm", new Dimension(33, 16), addIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnOrderServices).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		btnOrderServices.setBounds(310, 42, 90, 30);
		pnControlService.add(btnOrderServices);

		btnCannelServices = new MyButton(90, 30, "Hủy", new Dimension(25, 16), trashIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnCannelServices).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		btnCannelServices.setBounds(310, 78, 90, 30);
		pnControlService.add(btnCannelServices);

		JLabel lbQuantityService = new JLabel("Số lượng còn: ");
		lbQuantityService.setForeground(Color.WHITE);
		lbQuantityService.setFont(new Font("Dialog", Font.BOLD, 12));
		lbQuantityService.setBounds(12, 69, 90, 16);
		pnControlService.add(lbQuantityService);

		JLabel lbGiaDV = new JLabel("Giá bán: ");
		lbGiaDV.setForeground(Color.WHITE);
		lbGiaDV.setFont(new Font("Dialog", Font.BOLD, 12));
		lbGiaDV.setBounds(12, 98, 90, 16);
		pnControlService.add(lbGiaDV);

		txtQuantityService = new JTextField();
		txtQuantityService.setForeground(Color.WHITE);
		txtQuantityService.setColumns(10);
		txtQuantityService.setBounds(110, 67, 170, 20);
		txtQuantityService.setText("");
		;
		txtQuantityService.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtQuantityService);
		pnControlService.add(txtQuantityService);

		txtServicePrice = new JTextField();
		txtServicePrice.setForeground(Color.WHITE);
		txtServicePrice.setColumns(10);
		txtServicePrice.setBounds(110, 96, 170, 20);
		txtServicePrice.setText("");
		;
		txtServicePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtServicePrice);
		pnControlService.add(txtServicePrice);

		btnRefreshService = new MyButton(90, 30, "Làm mới", new Dimension(50, 17), refreshIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnRefreshService).setFontCustom(new Font("Dialog", Font.BOLD, 12));
		btnRefreshService.setBounds(310, 114, 90, 30);
		pnControlService.add(btnRefreshService);

		JLabel lbTotalPriceService = new JLabel("Tổng tiền: ");
		lbTotalPriceService.setForeground(Color.WHITE);
		lbTotalPriceService.setFont(new Font("Dialog", Font.BOLD, 12));
		lbTotalPriceService.setBounds(12, 127, 90, 16);
		pnControlService.add(lbTotalPriceService);

		txtTotalPriceService = new JTextField();
		txtTotalPriceService.setForeground(Color.WHITE);
		txtTotalPriceService.setText("0.0");
		txtTotalPriceService.setColumns(10);
		txtTotalPriceService.setBounds(110, 125, 170, 20);
		txtTotalPriceService.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtTotalPriceService);
		pnControlService.add(txtTotalPriceService);

		JLabel lbServiceType = new JLabel("Loại dịch vụ: ");
		lbServiceType.setForeground(Color.WHITE);
		lbServiceType.setFont(new Font("Dialog", Font.BOLD, 12));
		lbServiceType.setBounds(12, 156, 90, 16);
		pnControlService.add(lbServiceType);

		cboServiceType = new JComboBox<String>();
		cboServiceType.setBounds(110, 154, 170, 20);
		cboServiceType.setEditable(true);
		cboServiceType.setOpaque(false);
		cboServiceType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboServiceType.setUI(new BasicComboBoxUI());
		txtBFieldServiceType = (JTextField) cboServiceType.getEditor().getEditorComponent();
		txtBFieldServiceType.setBorder(BorderFactory.createEmptyBorder());
		txtBFieldServiceType.setBackground(new Color(246, 210, 255, 50));
		txtBFieldServiceType.setForeground(Color.WHITE);
		txtBFieldServiceType.setEditable(false);
		txtBFieldServiceType.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnControlService.add(cboServiceType);

		chkSearchService = new JCheckBox("<html>Tìm theo tên và loại</html>");
		chkSearchService.setForeground(Color.WHITE);
		chkSearchService.setFont(new Font("Dialog", Font.BOLD, 12));
		chkSearchService.setBackground(Color.WHITE);
		chkSearchService.setBounds(289, 154, 144, 20);
		chkSearchService.setOpaque(false);
		chkSearchService.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnControlService.add(chkSearchService);

		JPanel pnServiceList = new JPanel();
		pnServiceList.setOpaque(false);
		pnServiceList.setBackground(Color.WHITE);
		pnServiceList.setBorder(new TitledBorder(null, "Danh sách dịch vụ", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Dialog", Font.BOLD, 13), Color.white));
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
		tableService.setBackground(new Color(255, 255, 255, 0));
		tableService.setForeground(new Color(255, 255, 255));
		tableService.setRowHeight(21);
		tableService.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
		tableService.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tableService.getTableHeader().setBackground(new Color(255, 255, 255));

		JScrollPane scpProductList = new JScrollPane(tableService, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scpProductList.setOpaque(false);
		scpProductList.getViewport().setOpaque(false);
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
		txtBFieldRoomID.addMouseListener(this);
		txtBFieldRoomType.addMouseListener(this);
		txtBFieldServiceType.addMouseListener(this);
		cboRoomID.addMouseListener(this);
		cboRoomType.addMouseListener(this);
		cboServiceType.addMouseListener(this);

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
					((MyButton) btnPayment).setEnabledCustom(true);
					LoadRoomList(PhongDAO.getInstance().getDSPhong());
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
			if (txtBillID.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Phòng này chưa được cho thuê");
			} else if (txtRoomID.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
			} else if (txtServiceName.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Bạn cần phải chọn sản phẩm");
			} else {
				int selectRow = tableService.getSelectedRow();
				int orderQuantity = (int) spinOrderQuantity.getValue();
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
					if (o.equals(btnCannelServices))
						orderQuantity *= -1;
					String serviceName = txtServiceName.getText().trim();
					DichVu service = DichVuDAO.getInstance().getDichVuByTenDichVu(serviceName);
					String roomID = txtRoomID.getText();
					int BillID = Integer.parseInt(txtBillID.getText());
					HoaDon bill = HoaDonDAO.getInstance().getHoaDonByMaHD(BillID);
					boolean result = false;
					String message = "Thêm dịch vụ thất bại";
					if (BillID != -1) {
						CTDichVu serviceInfo = CTDichVuDAO.getInstance().getCTHoaDonByMaHDvaMaDV(BillID,
								service.getMaDichVu());
						// nếu ctDichVu đã tồn tại thì cập nhật
						// nếu ctDichVu không tồn tại thì thêm mới
						if (serviceInfo != null) {
							int newQuantity = quantity - orderQuantity;
							if (serviceInfo.getSoLuongDat() > 0 && newQuantity > 0) {
								int newOrderQuantity = serviceInfo.getSoLuongDat() + orderQuantity;
								serviceInfo.setSoLuongDat(newOrderQuantity);
								result = CTDichVuDAO.getInstance().themCTHoaDon(serviceInfo, orderQuantity,
										bill.getMaHoaDon());
							} else {
								message = "Dịch vụ chưa được thêm nên không thể hủy";
							}
						} else {
							serviceInfo = new CTDichVu(orderQuantity, service);
							result = CTDichVuDAO.getInstance().themCTHoaDon(serviceInfo, orderQuantity,
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
					boolean rs = HoaDonDAO.getInstance().thanhToan(bill.getMaHoaDon(), bill.getNgayGioTra(),
							totalPriceBill);
					if (rs) {
						LoadRoomList(PhongDAO.getInstance().getDSPhong());
						String endTimeStr = ConvertTime.getInstance().convertTimeToString(endTime,
								"dd-MM-yyyy HH:mm:ss");
						String startTimeStr = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(),
								"dd-MM-yyyy HH:mm:ss");
						txtEndTime.setText(endTimeStr);
						txtTotalPriceBill.setText(df.format(totalPriceBill));
						txtBillID.setText(String.valueOf(bill.getMaHoaDon()));
						txtCustomerName.setText(bill.getPhong().getMaPhong());
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
					PhongDAO.getInstance().chuyenPhong(RoomIdOld, roomIdNew);
					LoadRoomList(PhongDAO.getInstance().getDSPhong());
					String roomTypeName = txtRoomTypeName.getText();
					loadCboRoom(roomTypeName);
					refreshBillForm();
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
			int row = tableService.getSelectedRow();
			String serviceName = modelTableService.getValueAt(row, 1).toString();
			DichVu service = DichVuDAO.getInstance().getDichVuByTenDichVu(serviceName);
			txtServiceName.setText(serviceName);
			txtQuantityService.setText(df.format(service.getSoLuongTon()));
			txtServicePrice.setText(df.format(service.getGiaBan()));
			txtTotalPriceService.setText(df.format(service.getGiaBan()));
			spinOrderQuantity.setEnabled(true);
			spinOrderQuantity = new JSpinner(new SpinnerNumberModel(1, 1, service.getSoLuongTon(), 1));
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
			cboRoomID.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldRoomType)) {
			cboRoomType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldServiceType)) {
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
			String giaTienStr = txtServicePrice.getText().trim().replace(",", "");
			if (!giaTienStr.equals("")) {
				double giaTien = Double.parseDouble(giaTienStr);
				double tongTien = giaTien * soLuongDat;
				DecimalFormat df = new DecimalFormat("#,###.##");
				txtTotalPriceService.setText(df.format(tongTien));
			}
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
			statusStr = "Đã cho thuê";
		return statusStr;
	}

	/**
	 * Hiển thị thông tin của 1 phòng
	 * 
	 * @param roomId {@code String}: mã phòng cần hiển thị
	 */
	private void loadRoom(String maPhong) {
		Phong room = PhongDAO.getInstance().getPhongByMaPhong(maPhong);
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
		pnShowRoom.revalidate();
		pnShowRoom.repaint();
	}

	/**
	 * Hiển thị danh sách phòng được truyền vào
	 * 
	 * @param dsPhong {@code ArrayList<Phong>}: danh sách phòng cần hiển thị
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
			loadRoom(roomID);
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
					spinOrderQuantity.setValue((int) 1);
					Phong roomActiveE = PhongDAO.getInstance().getPhongByMaPhong(roomID);
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
	 * @param roomId {@code String}: mã phòng
	 */
	private void showBillInfo(String maPhong) {
		ArrayList<CTDichVu> dataList = CTDichVuDAO.getInstance().getCTHoaDonListByMaPhong(maPhong);
		DecimalFormat df = new DecimalFormat("#,###.##");
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
			String countStr = df.format(item.getSoLuongDat());
			modelTableBill
					.addRow(new Object[] { stt, item.getDichVu().getTenDichVu(), priceStr, countStr, totalPriceStr });
		}
		txtTotalPriceBill.setText(df.format(totalPrice));
	}

	/**
	 * Hiển thị danh sách loại phòng lên comboBox loại phòng
	 */
	private void loadCboRoomType() {
		ArrayList<LoaiPhong> dataList = LoaiPhongDAO.getInstance().getDSLoaiPhong();
		cboRoomType.addItem("Tất cả");
		for (LoaiPhong item : dataList) {
			cboRoomType.addItem(item.getTenLP());
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
	 * @param dataList {@code ArrayList<DichVu>}: danh sách dịch vụ
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
	 * @param staff {@code NhanVien}: nhân viên được hiển thị tên
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