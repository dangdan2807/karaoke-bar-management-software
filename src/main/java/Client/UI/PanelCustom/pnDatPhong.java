package Client.UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import Client.Event_Handlers.InputEventHandler;
import Client.UI.fQuanLyDatPhong;
import DAO.*;
import entity.*;

/**
 * Giao diện quản lý đặt phòng
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm phân trang cho phần mềm
 */
public class pnDatPhong extends JPanel
		implements ActionListener, MouseListener, ItemListener, ChangeListener, FocusListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7935621774047432226L;
	private JButton[] btnRoomList;
	private int pnShowTableWidth = 310;
	private int heightTable = 140;

	private JPanel pnlMain, pnlShowRoom;
	private DefaultTableModel modelTableBill, modelTableService;
	private JTable tblTableBill, tblTableService;
	private JLabel lblStaffName;
	private JButton btnSwitchRoom, btnRefreshRoom, btnBack, btnSearchService, btnPayment, btnOrderServices;
	private JButton btnCannelServices, btnRentRoom, btnRefreshService, btnChooseCustomer;
	private JTextField txtBillID, txtRoomID, txtTotalPriceBill, txtServiceName, txtRoomLocation;
	private JTextField txtQuantityService, txtServicePrice, txtStartTime, txtEndTime, txtCustomerName;
	private JTextField txtTotalPriceService, txtBFieldRoomID, txtBFieldRoomType, txtBFieldServiceType;
	private JTextField txtRoomTypeName, txtOrderQuantity;
	private JComboBox<String> cboRoomType, cboRoomID, cboServiceType;
	private JCheckBox chkSearchService;
	private JSpinner spnOrderQuantity;
	private SpinnerNumberModel spnModel;

	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1280, 650, Image.SCALE_SMOOTH));
	private ImageIcon transferIcon = CustomUI.TRANSFER_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon paymentIcon = CustomUI.PAYMENT_ICON;
	private ImageIcon rentIcon = CustomUI.RENT_ROOM_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon trashIcon = CustomUI.TRASH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon roomIcon = CustomUI.ROOM_ICON;
	private ImageIcon userIcon = CustomUI.USER_ICON;
	private ImageIcon addIcon = CustomUI.ADD_ICON;

	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private int location = -1;
	private NhanVien staffLogin = null;
	private int selectedServiceIndex = -1;
	private int selectedServiceOrderIndex = -1;
	private KhachHang selectedCustomer = null;
	private String formatTime = "dd-MM-yyyy HH:mm:ss";
	private DecimalFormat df = new DecimalFormat("#,###.##");
	private ArrayList<DichVu> serviceList = new ArrayList<DichVu>();
	private ArrayList<DichVu> serviceOrderList = new ArrayList<DichVu>();
	private boolean isDoubleClick = false;

	/**
	 * Hàm khởi tạo form
	 * 
	 * @param staff {@code NhanVien}: nhân viên đăng nhập
	 */
	public pnDatPhong(NhanVien staff) {
		this.staffLogin = staff;
		setSize(1270, 690);
		this.setLayout(null);

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
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 1270, 630);
		pnlMain.setBackground(Color.WHITE);
		this.add(pnlMain);

		JPanel pnlTitle = new JPanel();
		pnlTitle.setBounds(0, 0, 1280, 39);
		pnlTitle.setLayout(null);
		pnlTitle.setOpaque(false);
		pnlMain.add(pnlTitle);
		pnlTitle.setBackground(Color.decode("#d0e1fd"));

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 33, 19, 12, 5);
		btnBack.setBounds(1150, 5, 100, 35);
		((MyButton) btnBack).setFontCustom(new Font("Dialog", Font.BOLD, 14));
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
		lblStaffName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStaffName.setForeground(Color.WHITE);
		lblStaffName.setFont(new Font("Dialog", Font.BOLD, 24));
		lblStaffName.setBounds(0, 4, 318, 34);
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

		btnSwitchRoom = new MyButton(105, 30, "Đổi phòng", new Dimension(68, 16), transferIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnSwitchRoom).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		btnSwitchRoom.setBounds(210, 0, 105, 30);
		((MyButton) btnSwitchRoom).setEnabledCustom(false);
		pnlRoomControl.add(btnSwitchRoom);

		btnRefreshRoom = new MyButton(105, 30, "Làm mới", new Dimension(60, 16), refreshIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnRefreshRoom).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		btnRefreshRoom.setBounds(210, 33, 105, 30);
		pnlRoomControl.add(btnRefreshRoom);

		cboRoomID = new JComboBox<String>();
		cboRoomID.setBounds(89, 0, 118, 27);
		CustomUI.getInstance().setCustomComboBox(cboRoomID);
		txtBFieldRoomID = CustomUI.getInstance().setCustomCBoxField(cboRoomID);
		pnlRoomControl.add(cboRoomID);

		JLabel lblRoomTypeRoomCtrl = new JLabel("Loại phòng: ");
		CustomUI.getInstance().setCustomLabel(lblRoomTypeRoomCtrl);
		lblRoomTypeRoomCtrl.setBounds(10, 38, 83, 16);
		pnlRoomControl.add(lblRoomTypeRoomCtrl);

		cboRoomType = new JComboBox<String>();
		cboRoomType.setBounds(89, 33, 118, 27);
		CustomUI.getInstance().setCustomComboBox(cboRoomType);
		txtBFieldRoomType = CustomUI.getInstance().setCustomCBoxField(cboRoomType);
		pnlRoomControl.add(cboRoomType);

		JLabel lblRoom = new JLabel("Phòng: ");
		CustomUI.getInstance().setCustomLabel(lblRoom);
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

		String[] colsTitleService = { "STT", "Tên dịch vụ", "SL", "Đơn giá", "Thành tiền" };
		modelTableBill = new DefaultTableModel(colsTitleService, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		pnlOrderList.setLayout(new BorderLayout(0, 0));
		tblTableBill = new JTable(modelTableBill);
		CustomUI.getInstance().setCustomTable(tblTableBill);

		JScrollPane scrTableBill = CustomUI.getInstance().setCustomScrollPane(tblTableBill);
		pnlOrderList.add(scrTableBill, BorderLayout.CENTER);
		pnlBill.setLayout(new BorderLayout(0, 0));

		pnlBill.add(pnlBiffInfo, BorderLayout.NORTH);

		JLabel lblBillID = new JLabel("Mã hóa đơn: ");
		CustomUI.getInstance().setCustomLabel(lblBillID);
		lblBillID.setBounds(12, 20, 85, 20);
		pnlBiffInfo.add(lblBillID);

		txtBillID = new JTextField();
		txtBillID.setForeground(Color.WHITE);
		txtBillID.setBounds(100, 20, 142, 20);
		txtBillID.setColumns(10);
		CustomUI.getInstance().setCustomTextFieldOff(txtBillID);
		pnlBiffInfo.add(txtBillID);

		JLabel lblRoomID = new JLabel("Mã phòng:");
		CustomUI.getInstance().setCustomLabel(lblRoomID);
		lblRoomID.setBounds(248, 20, 82, 20);
		pnlBiffInfo.add(lblRoomID);

		txtRoomID = new JTextField();
		txtRoomID.setBounds(332, 21, 145, 20);
		txtRoomID.setColumns(10);
		txtRoomID.setText("");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomID);
		pnlBiffInfo.add(txtRoomID);

		JLabel lblStartTime = new JLabel("Giờ vào:");
		CustomUI.getInstance().setCustomLabel(lblStartTime);
		lblStartTime.setBounds(12, 51, 85, 16);
		pnlBiffInfo.add(lblStartTime);

		JLabel lblEndTime = new JLabel("Giờ ra:");
		CustomUI.getInstance().setCustomLabel(lblEndTime);
		lblEndTime.setBounds(12, 78, 85, 16);
		pnlBiffInfo.add(lblEndTime);

		txtStartTime = new JTextField("");
		txtStartTime.setBounds(100, 49, 142, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtStartTime);
		pnlBiffInfo.add(txtStartTime);

		btnRentRoom = new MyButton(120, 35, "Thuê ngay", new Dimension(70, 20), rentIcon.getImage(),
				new Dimension(16, 19), gra);
		((MyButton) btnRentRoom).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		((MyButton) btnRentRoom).setEnabledCustom(false);
		pnlBiffInfo.add(btnRentRoom);
		btnRentRoom.setBounds(27, 150, 120, 35);

		btnPayment = new MyButton(120, 35, "Thanh toán", new Dimension(75, 20), paymentIcon.getImage(),
				new Dimension(16, 19), gra);
		((MyButton) btnPayment).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		pnlBiffInfo.add(btnPayment);
		btnPayment.setBounds(177, 150, 120, 35);

		btnChooseCustomer = new MyButton(135, 35, "Chọn K.Hàng", new Dimension(87, 20), userIcon.getImage(),
				new Dimension(16, 19), gra);
		((MyButton) btnChooseCustomer).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		((MyButton) btnChooseCustomer).setEnabledCustom(false);
		pnlBiffInfo.add(btnChooseCustomer);
		btnChooseCustomer.setBounds(327, 150, 135, 35);

		JLabel lblTotalPriceBill = new JLabel("Tổng tiền: ");
		CustomUI.getInstance().setCustomLabel(lblTotalPriceBill);
		lblTotalPriceBill.setBounds(12, 105, 85, 20);
		pnlBiffInfo.add(lblTotalPriceBill);

		txtTotalPriceBill = new JTextField();
		txtTotalPriceBill.setForeground(Color.WHITE);
		txtTotalPriceBill.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalPriceBill.setText("0.0");
		txtTotalPriceBill.setBounds(100, 105, 142, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtTotalPriceBill);
		txtTotalPriceBill.setColumns(10);
		pnlBiffInfo.add(txtTotalPriceBill);

		// JLabel lblVND = new JLabel("(VND)");
		// CustomUI.getInstance().setCustomLabel(lblVND);
		// lblVND.setBounds(248, 105, 43, 20);
		// pnlBiffInfo.add(lblVND);

		txtEndTime = new JTextField("");
		txtEndTime.setForeground(Color.WHITE);
		txtEndTime.setBounds(100, 76, 142, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtEndTime);
		pnlBiffInfo.add(txtEndTime);

		JLabel lblRoomLocation = new JLabel("Vị Trí:");
		CustomUI.getInstance().setCustomLabel(lblRoomLocation);
		lblRoomLocation.setBounds(248, 49, 82, 20);
		pnlBiffInfo.add(lblRoomLocation);

		txtRoomLocation = new JTextField();
		txtRoomLocation.setColumns(10);
		txtRoomLocation.setBounds(332, 50, 145, 20);
		txtRoomLocation.setText("");
		;
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomLocation);
		pnlBiffInfo.add(txtRoomLocation);

		JLabel lblRoomTypeBillInfo = new JLabel("Loại phòng:");
		CustomUI.getInstance().setCustomLabel(lblRoomTypeBillInfo);
		lblRoomTypeBillInfo.setBounds(248, 76, 82, 20);
		pnlBiffInfo.add(lblRoomTypeBillInfo);

		txtRoomTypeName = new JTextField();
		txtRoomTypeName.setColumns(10);
		txtRoomTypeName.setBounds(332, 77, 145, 20);
		txtRoomTypeName.setText("");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomTypeName);
		pnlBiffInfo.add(txtRoomTypeName);

		JLabel lblCustomerName = new JLabel("Tên KH:");
		CustomUI.getInstance().setCustomLabel(lblCustomerName);
		lblCustomerName.setBounds(248, 105, 85, 20);
		pnlBiffInfo.add(lblCustomerName);

		txtCustomerName = new JTextField();
		txtCustomerName.setColumns(10);
		txtCustomerName.setBounds(332, 105, 145, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerName);
		pnlBiffInfo.add(txtCustomerName);

		pnlBill.add(pnlOrderList);
		pnlMain.add(pnlBill);
		this.add(pnlMain);

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
		CustomUI.getInstance().setCustomLabel(lblServiceName);
		lblServiceName.setBounds(12, 12, 90, 16);
		pnlControlService.add(lblServiceName);

		txtServiceName = new JTextField();
		txtServiceName.setBounds(110, 10, 170, 20);
		txtServiceName.setColumns(10);
		txtServiceName.setText("");
		CustomUI.getInstance().setCustomTextFieldOn(txtServiceName);
		pnlControlService.add(txtServiceName);

		btnSearchService = new MyButton(90, 30, "Tìm", new Dimension(25, 16), searchIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnSearchService).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		btnSearchService.setBounds(310, 6, 90, 30);
		pnlControlService.add(btnSearchService);

		JLabel lblOrderQuantity = new JLabel("SL đặt: ");
		CustomUI.getInstance().setCustomLabel(lblOrderQuantity);
		lblOrderQuantity.setBounds(12, 42, 90, 16);
		pnlControlService.add(lblOrderQuantity);

		spnModel = new SpinnerNumberModel(1, 1, 100, 1);
		spnOrderQuantity = new JSpinner(spnModel);
		spnOrderQuantity.setBounds(110, 39, 170, 20);
		spnOrderQuantity.setEnabled(false);
		CustomUI.getInstance().setCustomSpinner(spnOrderQuantity);

		txtOrderQuantity = ((JSpinner.DefaultEditor) spnOrderQuantity.getEditor()).getTextField();
		CustomUI.getInstance().setCustomTextFieldOff(txtOrderQuantity);
		txtOrderQuantity.setEditable(true);
		pnlControlService.add(spnOrderQuantity);

		btnOrderServices = new MyButton(90, 30, "Thêm", new Dimension(33, 16), addIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnOrderServices).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		btnOrderServices.setBounds(310, 42, 90, 30);
		((MyButton) btnOrderServices).setEnabledCustom(false);
		pnlControlService.add(btnOrderServices);

		btnCannelServices = new MyButton(90, 30, "Hủy", new Dimension(25, 16), trashIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnCannelServices).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		((MyButton) btnCannelServices).setEnabledCustom(false);
		btnCannelServices.setBounds(310, 78, 90, 30);
		pnlControlService.add(btnCannelServices);

		JLabel lblQuantityService = new JLabel("SL còn: ");
		CustomUI.getInstance().setCustomLabel(lblQuantityService);
		lblQuantityService.setBounds(12, 69, 90, 16);
		pnlControlService.add(lblQuantityService);

		JLabel lblServicePrice = new JLabel("Giá bán: ");
		CustomUI.getInstance().setCustomLabel(lblServicePrice);
		lblServicePrice.setBounds(12, 98, 90, 16);
		pnlControlService.add(lblServicePrice);

		txtQuantityService = new JTextField();
		txtQuantityService.setColumns(10);
		txtQuantityService.setBounds(110, 67, 170, 20);
		txtQuantityService.setText("");
		txtQuantityService.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtQuantityService);
		pnlControlService.add(txtQuantityService);

		txtServicePrice = new JTextField();
		txtServicePrice.setColumns(10);
		txtServicePrice.setBounds(110, 96, 170, 20);
		txtServicePrice.setText("");
		txtServicePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtServicePrice);
		pnlControlService.add(txtServicePrice);

		btnRefreshService = new MyButton(90, 30, "Làm mới", new Dimension(50, 17), refreshIcon.getImage(),
				new Dimension(14, 18), gra);
		((MyButton) btnRefreshService).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		btnRefreshService.setBounds(310, 114, 90, 30);
		pnlControlService.add(btnRefreshService);

		JLabel lblTotalPriceService = new JLabel("Tổng tiền: ");
		CustomUI.getInstance().setCustomLabel(lblTotalPriceService);
		lblTotalPriceService.setBounds(12, 127, 90, 16);
		pnlControlService.add(lblTotalPriceService);

		txtTotalPriceService = new JTextField();
		txtTotalPriceService.setText("0.0");
		txtTotalPriceService.setColumns(10);
		txtTotalPriceService.setBounds(110, 125, 170, 20);
		txtTotalPriceService.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomUI.getInstance().setCustomTextFieldOff(txtTotalPriceService);
		pnlControlService.add(txtTotalPriceService);

		JLabel lblServiceType = new JLabel("Loại dịch vụ: ");
		CustomUI.getInstance().setCustomLabel(lblTotalPriceService);
		lblServiceType.setForeground(Color.WHITE);
		lblServiceType.setFont(new Font("Dialog", Font.PLAIN, 14));
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
		chkSearchService.setVisible(false);
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
		tblTableService.setRowHeight(21);
		CustomUI.getInstance().setCustomTable(tblTableService);

		JScrollPane scrProductList = CustomUI.getInstance().setCustomScrollPane(tblTableService);
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

		tblTableService.addMouseListener(this);
		tblTableBill.addMouseListener(this);
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
		txtOrderQuantity.addKeyListener(this);

		showStaffName(staffLogin);
		LoadRoomList(PhongDAO.getInstance().getRoomList());
		reSizeColumnTableBillInfo();
		loadCboRoomType();
		loadCboRoom("Tất cả");
		loadDSServiceType();
		String serviceName = cboServiceType.getSelectedItem().toString();
		serviceList = DichVuDAO.getInstance().getServiceListByServiceTypeName(serviceName);
		loadServiceList(serviceList);
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
		if (o.equals(btnRentRoom)) {
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
					String startTimeStr = ConvertTime.getInstance().convertTimeToString(startTime, formatTime);
					txtStartTime.setText(startTimeStr);
					((MyButton) btnChooseCustomer).setEnabledCustom(false);
					((MyButton) btnRentRoom).setEnabledCustom(false);
					((MyButton) btnPayment).setEnabledCustom(true);
				} else {
					JOptionPane.showMessageDialog(this, "Cho thuê phòng thất bại");
				}
			}
		} else if (o.equals(btnOrderServices)) {
			orderService();
		} else if (o.equals(btnCannelServices)) {
			cancelService();
		} else if (o.equals(btnSearchService) || o.equals(btnRefreshService)) {
			searchService(0);
			if (o.equals(btnRefreshService))
				searchService(1);
		} else if (o.equals(btnPayment)) {
			String billID = txtBillID.getText().trim();
			HoaDon bill = HoaDonDAO.getInstance().getBillByBillId(billID);
			if (bill != null) {
				Phong room = PhongDAO.getInstance().getRoomByBillId(billID);
				bill.setPhong(room);
				String billId = txtBillID.getText().trim();
				ArrayList<CTDichVu> billInfoList = CTDichVuDAO.getInstance().getServiceDetailListByBillId(billId);
				bill.setDsCTDichVu(billInfoList);
				long millis = System.currentTimeMillis();
				Timestamp endTime = new Timestamp(millis);
				bill.setNgayGioTra(endTime);
				Double totalPriceBill = bill.tinhTongTienHoaDon();

				DialogHoaDon winPayment = new DialogHoaDon(bill);
				winPayment.setModal(true);
				winPayment.setVisible(true);
				Boolean isPaid = winPayment.getPaid();
				if (isPaid) {
					LoadRoomList(PhongDAO.getInstance().getRoomList());
					String endTimeStr = ConvertTime.getInstance().convertTimeToString(endTime, formatTime);
					String startTimeStr = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(),
							formatTime);
					txtEndTime.setText(endTimeStr);
					txtTotalPriceBill.setText(df.format(totalPriceBill));
					txtBillID.setText(String.valueOf(bill.getMaHoaDon()));
					String customerName = KhachHangDAO.getInstance().getCustomerByBillId(bill.getMaHoaDon()).getHoTen();
					txtCustomerName.setText(customerName);
					txtStartTime.setText(startTimeStr);
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
					String billId = txtBillID.getText().trim();
					PhongDAO.getInstance().switchRoom(billId, RoomIdOld, roomIdNew);
					LoadRoomList(PhongDAO.getInstance().getRoomList());
					String roomTypeName = txtRoomTypeName.getText();
					loadCboRoom(roomTypeName);
					refreshBillForm();
				}
			}
		} else if (o.equals(btnChooseCustomer)) {
			String roomId = txtRoomID.getText().trim();
			if (!roomId.equalsIgnoreCase("") || roomId.length() > 0) {
				DialogChonKhachHang chooseCustomer = new DialogChonKhachHang();
				chooseCustomer.setModal(true);
				chooseCustomer.setVisible(true);
				selectedCustomer = chooseCustomer.getSelectedCustomer();
				if (selectedCustomer != null) {
					txtCustomerName.setText(selectedCustomer.getHoTen());
					((MyButton) btnRentRoom).setEnabledCustom(true);
				}
			} else {
				String message = "Bạn cần chọn phòng trước";
				JOptionPane.showConfirmDialog(this, message, "Thông báo chọn phòng", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(tblTableService)) {
			int selectedRow = tblTableService.getSelectedRow();
			String serviceName = modelTableService.getValueAt(selectedRow, 1).toString().trim();
			DichVu service = serviceList.get(selectedRow);
			selectedServiceIndex = selectedRow;
			int quantity = service.getSoLuongTon();
			txtServiceName.setText(serviceName);
			txtQuantityService.setText(df.format(quantity));
			txtServicePrice.setText(df.format(service.getGiaBan()));
			txtTotalPriceService.setText(df.format(service.getGiaBan()));
			spnOrderQuantity.setEnabled(true);
			spnOrderQuantity.setValue((int) 1);
			((MyButton) btnCannelServices).setEnabledCustom(true);
			((MyButton) btnOrderServices).setEnabledCustom(true);
			if (e.getClickCount() == 2) {
				isDoubleClick = true;
				orderService();
			}
		} else if (o.equals(tblTableBill)) {
			spnOrderQuantity.setEnabled(true);
			int selectedRow = tblTableBill.getSelectedRow();
			String serviceName = modelTableBill.getValueAt(selectedRow, 1).toString().trim();
			txtServiceName.setText(serviceName);
			selectedServiceOrderIndex = selectedRow;
			String price = modelTableBill.getValueAt(selectedRow, 3).toString().trim();
			txtServicePrice.setText(price);
			int quantityService = serviceOrderList.get(selectedRow).getSoLuongTon();
			txtQuantityService.setText(String.valueOf(quantityService));
			spnOrderQuantity.setValue(1);
			((MyButton) btnCannelServices).setEnabledCustom(true);
			((MyButton) btnOrderServices).setEnabledCustom(true);
			if (e.getClickCount() == 2) {
				cancelService();
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
			String roomTypeName = cboRoomType.getSelectedItem().toString();
			loadRoomListByRoomTypeName(roomTypeName);
		} else if (o.equals(cboServiceType)) {
			String serviceTypeName = cboServiceType.getSelectedItem().toString();
			serviceList = DichVuDAO.getInstance().getServiceListByServiceTypeName(serviceTypeName);
			loadServiceList(serviceList);
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

	}

	@Override
	public void keyReleased(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		InputEventHandler handler = new InputEventHandler();
		if (o.equals(txtServiceName)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchService(0);
			}
			handler.characterInputLimit(key, txtServiceName, 100);
		} else if (o.equals(txtOrderQuantity)) {
			handler.enterOnlyNumbersAndLimitInput(key, txtOrderQuantity, 5);
		}
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
			((MyButton) btnRentRoom).setEnabledCustom(false);
			((MyButton) btnPayment).setEnabledCustom(false);
			((MyButton) btnChooseCustomer).setEnabledCustom(false);
			((MyButton) btnSwitchRoom).setEnabledCustom(false);
			break;
		default:
			btnRoomList[index].setBackground(Color.decode("#3c8dbc"));
			((MyButton) btnRentRoom).setEnabledCustom(false);
			((MyButton) btnPayment).setEnabledCustom(true);
			((MyButton) btnChooseCustomer).setEnabledCustom(false);
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
			String roomName = txtRoomID.getText();
			if (roomID.equalsIgnoreCase(roomName)) {
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
						String format = formatTime;
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
						((MyButton) btnRentRoom).setEnabledCustom(false);
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
		serviceOrderList.clear();
		for (CTDichVu item : dataList) {
			DichVu service = item.getDichVu();
			serviceOrderList.add(service);
			if (selectedServiceOrderIndex <= -1) {
				if (selectedServiceOrderIndex == i) {
					tblTableBill.getSelectionModel().addSelectionInterval(i - 1, i - 1);
				}
			}
			Double totalPriceService = item.tinhTienDichVu();
			totalPrice += totalPriceService;
			String stt = df.format(i++);
			String totalPriceStr = df.format(totalPriceService);
			String priceStr = df.format(item.getDonGia());
			String quantityStr = df.format(item.getSoLuongDat());
			modelTableBill.addRow(new Object[] { stt, addSpaceToString(service.getTenDichVu()),
					addSpaceToString(quantityStr), addSpaceToString(priceStr), addSpaceToString(totalPriceStr) });
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
		String serviceTypeName = cboServiceType.getSelectedItem().toString().trim();
		if (serviceName.equalsIgnoreCase("")) {
			serviceList = DichVuDAO.getInstance().getServiceListByServiceTypeName(serviceTypeName);
		} else {
			if (isRefresh == 1) {
				serviceList = DichVuDAO.getInstance().getServiceListByServiceTypeName(serviceTypeName);
			} else
				serviceList = DichVuDAO.getInstance().getServiceListByNameAndServiceTypeName(serviceName,
						serviceTypeName);
		}
		loadServiceList(serviceList);
	}

	/**
	 * Thay đổi kích thước các cột trong bảng chi tiết dịch vụ
	 */
	private void reSizeColumnTableBillInfo() {
		TableColumnModel tcm = tblTableBill.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(15);
		tcm.getColumn(1).setPreferredWidth(150);
		tcm.getColumn(2).setPreferredWidth(25);
		tcm.getColumn(3).setPreferredWidth(50);
		tcm.getColumn(4).setPreferredWidth(70);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tcm.getColumn(0).setCellRenderer(centerRenderer);
		tcm.getColumn(2).setCellRenderer(rightRenderer);
		tcm.getColumn(3).setCellRenderer(rightRenderer);
		tcm.getColumn(4).setCellRenderer(rightRenderer);
	}

	/**
	 * Thay đổi kích thước các cột trong bảng dịch vụ
	 */
	private void reSizeColumnTableService() {
		TableColumnModel tcm = tblTableService.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(15);
		tcm.getColumn(1).setPreferredWidth(210);
		tcm.getColumn(2).setPreferredWidth(40);
		tcm.getColumn(3).setPreferredWidth(40);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tcm.getColumn(0).setCellRenderer(centerRenderer);
		tcm.getColumn(2).setCellRenderer(rightRenderer);
		tcm.getColumn(3).setCellRenderer(rightRenderer);
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
	 * Thêm hoặc cập nhật dịch vụ
	 */
	private void orderService() {
		if (txtBillID.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Phòng này chưa được cho thuê");
		} else if (txtRoomID.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
		} else if (txtServiceName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Bạn cần phải chọn sản phẩm");
		} else {
			int orderQuantity = (int) spnOrderQuantity.getValue();
			if (isDoubleClick) {
				orderQuantity = 1;
				isDoubleClick = false;
			}
			String quantityInStockStr = txtQuantityService.getText().replace(",", "").trim();
			int quantityInStock = Integer.parseInt(quantityInStockStr);
			String message = "";

			// số lượng đặt lớn hơn số lượng trong kho
			if (orderQuantity > quantityInStock) {
				orderQuantity = quantityInStock;
				message = "Số lượng dịch vụ đặt lớn hơn số lượng hiện có";
				JOptionPane.showMessageDialog(this, message, "Cảnh bảo", JOptionPane.ERROR_MESSAGE);

				// Số lượng trong kho bé hơn không
			} else if (quantityInStock <= 0) {
				message = "Dịch vụ đã hết";
				JOptionPane.showMessageDialog(this, message, "Cảnh bảo", JOptionPane.ERROR_MESSAGE);

				// Số lượng đặt bé hơn không
			} else if (orderQuantity <= 0) {
				message = "Số lượng dịch vụ đặt phải lớn hơn 0";
				JOptionPane.showMessageDialog(this, message, "Cảnh bảo", JOptionPane.ERROR_MESSAGE);

				// trường hợp khác
			} else {
				String typeMessage = "Thêm";
				DichVu service = new DichVu();
				if (selectedServiceIndex != -1) {
					service = serviceList.get(selectedServiceIndex);
				} else if (selectedServiceOrderIndex != -1) {
					service = serviceOrderList.get(selectedServiceOrderIndex);
				}

				String roomID = txtRoomID.getText().trim();
				String billID = txtBillID.getText().trim();
				HoaDon bill = HoaDonDAO.getInstance().getBillByBillId(billID);
				boolean result = false;
				message = typeMessage + " dịch vụ thất bại";
				boolean isUpdate = false;
				if (!billID.equalsIgnoreCase("")) {
					CTDichVu serviceInfo = CTDichVuDAO.getInstance().getServiceDetailByBillIdAndServiceId(billID,
							service.getMaDichVu());
					// nếu ctDichVu không tồn tại thì thêm mới
					// nếu ctDichVu đã tồn tại thì cập nhật

					// Thêm mới
					int newOrderQuantity = 0;
					if (serviceInfo == null) {
						isUpdate = false;
						double servicePrice = service.getGiaBan();
						serviceInfo = new CTDichVu(orderQuantity, servicePrice, service);
						result = CTDichVuDAO.getInstance().insertServiceDetail(serviceInfo, orderQuantity,
								bill.getMaHoaDon());
						// cập nhật

					} else {
						int newQuantity = quantityInStock - orderQuantity;
						newOrderQuantity = serviceInfo.getSoLuongDat() + orderQuantity;
						if (serviceInfo.getSoLuongDat() > 0 && newQuantity >= 0) {
							isUpdate = true;
							serviceInfo.setSoLuongDat(newOrderQuantity);
							result = CTDichVuDAO.getInstance().insertServiceDetail(serviceInfo, orderQuantity,
									bill.getMaHoaDon());
						} else {
							message = "Số lượng đặt phải nhỏ hơn số lượng hiện có. Vui lòng nhập lại";
						}
					}
					// kiểm tra kết quả thêm, cập nhật
					if (result) {
						if (isUpdate) {
							int lastIndex = serviceOrderList.size() - 1;
							for (int i = 0; i < lastIndex; i++) {
								DichVu serviceOrder = serviceOrderList.get(i);
								if (service.getMaDichVu().equals(serviceOrder.getMaDichVu())) {
									serviceOrder.setSoLuongTon(newOrderQuantity);
									break;
								}
							}
						} else {
							serviceOrderList.add(service);
						}
						int newQuantity = quantityInStock - orderQuantity;
						showBillInfo(roomID);
						searchService(1);
						spnOrderQuantity.setValue(1);
						txtQuantityService.setText(String.valueOf(newQuantity));
						txtBillID.setText(String.valueOf(billID));
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

	/**
	 * Hủy hoặc cập nhật dịch vụ
	 */
	private void cancelService() {
		if (txtBillID.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Phòng này chưa được cho thuê");
		} else if (txtRoomID.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Bạn cần phải chọn phòng trước");
		} else if (txtServiceName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Bạn cần phải chọn dịch vụ");
		} else {
			int cancelQuantity = (int) spnOrderQuantity.getValue();
			String orderQuantityStr = tblTableBill.getValueAt(selectedServiceOrderIndex, 2).toString().replace(",", "")
					.trim();
			int orderQuantity = Integer.parseInt(orderQuantityStr);
			String message = "";
			if (cancelQuantity <= 0) {
				message = "Số lượng dịch vụ cần hủy phải lớn hơn 0";
				JOptionPane.showMessageDialog(this, message, "Cảnh bảo", JOptionPane.ERROR_MESSAGE);
			} else if (cancelQuantity > orderQuantity) {
				message = "Số lượng dịch vụ cần hủy phải nhỏ hơn hoặc bằng số lượng đã đặt";
				JOptionPane.showMessageDialog(this, message, "Cảnh bảo", JOptionPane.ERROR_MESSAGE);
			} else {
				DichVu service = serviceOrderList.get(selectedServiceOrderIndex);
				String roomID = txtRoomID.getText().trim();
				String billID = txtBillID.getText().trim();
				HoaDon bill = HoaDonDAO.getInstance().getBillByBillId(billID);
				boolean result = false;
				if (!billID.equalsIgnoreCase("")) {
					CTDichVu serviceDetail = CTDichVuDAO.getInstance().getServiceDetailByBillIdAndServiceId(billID,
							service.getMaDichVu());
					// nếu ctDichVu đã tồn tại thì cập nhật
					// nếu ctDichVu không tồn tại thì thông báo lỗi
					int newOrderQuantity = 0;
					int quantityInStock = 0;
					if (serviceDetail != null) {
						newOrderQuantity = serviceDetail.getSoLuongDat() - cancelQuantity;
						serviceDetail.setSoLuongDat(newOrderQuantity);
						quantityInStock = service.getSoLuongTon();
						service.setSoLuongTon(quantityInStock + cancelQuantity);
						result = CTDichVuDAO.getInstance().insertServiceDetail(serviceDetail, (-1) * cancelQuantity,
								bill.getMaHoaDon());
					} else {
						message = "Dịch vụ này chưa được đặt";
					}
					// kiểm tra kết quả thêm, cập nhật
					if (result) {
						if (newOrderQuantity <= 0) {
							serviceOrderList.remove(service);
						} else {
							quantityInStock = quantityInStock + cancelQuantity;
							serviceOrderList.get(selectedServiceOrderIndex).setSoLuongTon(quantityInStock);
						}
						showBillInfo(roomID);
						searchService(1);
						spnOrderQuantity.setValue(1);
						txtQuantityService.setText(String.valueOf(service.getSoLuongTon()));
						txtBillID.setText(String.valueOf(billID));
					} else {
						JOptionPane.showMessageDialog(this, message);
					}
				} else {
					message = "Hóa đơn không tồn tại, vui lòng thử lại";
					JOptionPane.showMessageDialog(this, message);
				}
			}
		}
	}

	/**
	 * Lấy nút quay lại
	 */
	public JButton getBtnBack() {
		return btnBack;
	}
}