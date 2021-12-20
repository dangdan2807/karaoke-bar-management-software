package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import DAO.*;
import Event_Handlers.*;
import UI.*;
import entity.*;

/**
 * Giao diện quản lý khách hàng của phần mềm
 * <p>
 * Người tham gia thiết kế: Huỳnh Tuấn Anh, Võ Minh Hiếu
 * <p>
 * Ngày tạo: 07/10/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm nút chọn khách hàng khi khách hàng thuê phòng
 */
public class PnKhachHang extends JPanel
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8761327929735598729L;
	private JTable tblTableCustomer;
	private DefaultTableModel modelTable;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	int index = 1;
	private JTextField txtCMND, txtPhoneNumber, txtCustomerName, txtCustomerID, txtBFieldSearch;
	private JTextField txtKeyWord, txtBFieldSearchGender;
	private JLabel lblCMND, lblBirthDay, lblGender, lblPhone, lblCustomerID, lblCustomerName, lblSearch;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch, btnNextToRight, btnNextToLast, btnNextToLeft;
	private MyButton btnNextToFirst, btnChooseCustomer;
	private kDatePicker dpBirthDay;
	private JComboBox<String> cboSearch, cboSearchGender;
	private JRadioButton radMale, radFemale;
	private PnTextFiledPaging txtPaging;

	private ImageIcon addIcon = new ImageIcon(PnKhachHang.class.getResource(CustomUI.ADD_ICON));
	private ImageIcon refreshIcon = new ImageIcon(PnKhachHang.class.getResource(CustomUI.REFRESH_ICON));
	private ImageIcon searchIcon = new ImageIcon(PnKhachHang.class.getResource(CustomUI.SEARCH_ICON));
	private ImageIcon checkIcon = new ImageIcon(PnKhachHang.class.getResource(CustomUI.CHECK_ICON));
	private ImageIcon backIcon = new ImageIcon(PnKhachHang.class.getResource(CustomUI.BACK_ICON));
	private ImageIcon updateIcon = new ImageIcon(PnKhachHang.class.getResource(CustomUI.UPDATE_ICON));
	private ImageIcon bg = new ImageIcon(new ImageIcon(PnKhachHang.class.getResource(
			CustomUI.BACKGROUND)).getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon nextIconRight = new ImageIcon(new ImageIcon(PnKhachHang.class.getResource(
			CustomUI.NEXT_RIGHT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextRightIcon = new ImageIcon(new ImageIcon(PnKhachHang.class.getResource(
			CustomUI.DOUBLE_NEXT_RIGHT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon nextLeftIcon = new ImageIcon(new ImageIcon(PnKhachHang.class.getResource(
			CustomUI.NEXT_LEFT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextLeftIcon = new ImageIcon(new ImageIcon(PnKhachHang.class.getResource(
			CustomUI.DOUBLE_NEXT_LEFT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));

	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;
	private int lineNumberDisplayed = 10;
	private int isChooseCustomer = 0;
	private String selectedCustomerId = "";
	private KhachHangDAO customerDAO = KhachHangDAO.getInstance();

	/**
	 * Khởi tạo giao diện quản lý khách hàng
	 * 
	 * @param staff            {@code NhanVien}: nhân viên đăng nhập
	 * @param isChooseCustomer Hiển thị nút chọn khách hàng hay không
	 *                         <ul>
	 *                         <li>Nếu hiển thị thì nhập {@code 1}</li>
	 *                         <li>Nếu ẩn thì nhập {@code 0}</li>
	 *                         </ul>
	 */
	public PnKhachHang(NhanVien staff, int isChooseCustomer) {
		this.staffLogin = staff;
		this.isChooseCustomer = isChooseCustomer;
		setSize(1270, 630);
		this.setLayout(null);
		// this.setResizable(false);
		// this.setLocationRelativeTo(null);
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnlMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(bg.getImage(), 0, 0, null);
				setFont(new Font("Dialog", Font.BOLD, 24));
				g2.setColor(Color.decode("#9B17EB"));
				g2.drawRoundRect(10, 50, 1238, 530, 20, 20);
				g2.drawRoundRect(9, 49, 1240, 530, 20, 20);
			}
		};
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 1270, 630);
		this.add(pnlMain);

		JPanel pnlTitle = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255, 255, 255));
			}
		};

		pnlTitle.setOpaque(false);
		pnlTitle.setBounds(0, 0, 1270, 50);
		pnlTitle.setLayout(null);

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 30, 19, 9, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlTitle.add(btnBack);
		pnlMain.add(pnlTitle);

		JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lblTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(10, 70, 1238, 180);
		pnlMain.add(pnlInfo);

		dpBirthDay = new kDatePicker(250, 20);
		CustomUI.getInstance().setCustomKDatePicker(dpBirthDay);
		dpBirthDay.setTextFont(new Font("Dialog", Font.PLAIN, 14));
		dpBirthDay.setBounds(165, 85, 250, 20);
		pnlInfo.add(dpBirthDay);

		txtCMND = new JTextField();
		txtCMND.setBounds(165, 50, 250, 20);
		txtCMND.setToolTipText("Nhập CMND gồm có 9 số hoặc CCCD gồm có 12 số");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		pnlInfo.add(txtCMND);

		lblCMND = new JLabel("CMND/CCCD:");
		CustomUI.getInstance().setCustomLabel(lblCMND);
		lblCMND.setBounds(40, 50, 105, 20);
		pnlInfo.add(lblCMND);

		lblBirthDay = new JLabel("Ngày sinh:");
		CustomUI.getInstance().setCustomLabel(lblBirthDay);
		lblBirthDay.setBounds(40, 85, 105, 20);
		pnlInfo.add(lblBirthDay);

		lblGender = new JLabel("Giới tính:");
		CustomUI.getInstance().setCustomLabel(lblGender);
		lblGender.setBounds(539, 85, 105, 20);
		pnlInfo.add(lblGender);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(659, 50, 250, 20);
		txtPhoneNumber.setToolTipText("Nhập số điện thoại của bạn gồm 10 số và bắt đầu bằng 03, 05, 07, 08, 09");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		pnlInfo.add(txtPhoneNumber);

		radMale = new JRadioButton("Nam");
		CustomUI.getInstance().setCustomRadioButton(radMale);
		radMale.setBounds(655, 85, 115, 20);
		radMale.setSelected(true);
		pnlInfo.add(radMale);

		radFemale = new JRadioButton("Nữ");
		CustomUI.getInstance().setCustomRadioButton(radFemale);
		radFemale.setBounds(775, 85, 115, 20);
		pnlInfo.add(radFemale);

		ButtonGroup groupGender = new ButtonGroup();
		groupGender.add(radMale);
		groupGender.add(radFemale);

		lblPhone = new JLabel("Số điện thoại:");
		CustomUI.getInstance().setCustomLabel(lblPhone);
		lblPhone.setBounds(539, 54, 115, 16);
		pnlInfo.add(lblPhone);

		txtCustomerName = new JTextField();
		txtCustomerName.setBounds(659, 15, 250, 20);
		txtCustomerName.setToolTipText("Nhập tên của khách hàng, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCustomerName);
		pnlInfo.add(txtCustomerName);

		txtCustomerID = new JTextField();
		txtCustomerID.setBounds(165, 15, 250, 20);
		txtCustomerID.setToolTipText("Mã khách hàng");
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerID);
		pnlInfo.add(txtCustomerID);

		lblCustomerID = new JLabel("Mã khách hàng: ");
		CustomUI.getInstance().setCustomLabel(lblCustomerID);
		lblCustomerID.setBounds(40, 15, 120, 20);
		pnlInfo.add(lblCustomerID);

		lblCustomerName = new JLabel("Tên khách hàng:");
		CustomUI.getInstance().setCustomLabel(lblCustomerName);
		lblCustomerName.setBounds(539, 15, 120, 20);
		pnlInfo.add(lblCustomerName);

		btnAdd = new MyButton(130, 35, "Thêm", gra, addIcon.getImage(), 50, 19, 10, 6);
		btnAdd.setToolTipText("Thêm khách hàng mới sau khi đã điền đủ thông tin");
		btnAdd.setBounds(1030, 10, 130, 35);
		pnlInfo.add(btnAdd);

		btnUpdate = new MyButton(130, 35, "Sửa", gra, updateIcon.getImage(), 55, 19, 10, 6);
		btnUpdate.setToolTipText("Sửa thông tin khách hàng");
		btnUpdate.setBounds(1030, 50, 130, 35);
		btnUpdate.setEnabledCustom(false);
		pnlInfo.add(btnUpdate);

		btnRefresh = new MyButton(130, 35, "Làm mới", gra, refreshIcon.getImage(), 40, 19, 10, 5);
		btnRefresh.setToolTipText("Làm mới form");
		btnRefresh.setBounds(1030, 90, 130, 35);
		pnlInfo.add(btnRefresh);

		JPanel pnlSearch = new JPanel();
		pnlSearch.setBounds(130, 135, 1100, 40);
		pnlInfo.add(pnlSearch);
		pnlSearch.setOpaque(false);
		pnlSearch.setLayout(null);
		pnlInfo.add(pnlSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(25, 11, 100, 20);
		pnlSearch.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên khách hàng");
		cboSearch.addItem("Số điện thoại");
		cboSearch.addItem("Giới tính");
		cboSearch.addItem("Mã khách hàng");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(91, 11, 200, 20);
		pnlSearch.add(cboSearch);

		btnSearch = new MyButton(130, 35, "Tìm kiếm", gra, searchIcon.getImage(), 40, 19, 10, 5);
		btnSearch.setToolTipText("Tìm kiếm thông tin khách hàng theo từ khóa");
		btnSearch.setBounds(715, 3, 130, 35);
		pnlSearch.add(btnSearch);

		btnChooseCustomer = new MyButton(130, 35, "Chọn KH", gra, checkIcon.getImage(), 40, 19, 10, 5);
		btnChooseCustomer.setToolTipText("Chọn khách hàng theo từ khóa");
		btnChooseCustomer.setBounds(900, 3, 130, 35);
		if (this.isChooseCustomer == 1) {
			btnChooseCustomer.setVisible(true);
		} else {
			btnChooseCustomer.setVisible(false);
		}
		pnlSearch.add(btnChooseCustomer);

		JLabel lblKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lblKeyWord);
		lblKeyWord.setBounds(374, 11, 76, 20);
		pnlSearch.add(lblKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setBounds(448, 11, 200, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		txtKeyWord.setEditable(false);
		pnlSearch.add(txtKeyWord);

		cboSearchGender = new JComboBox<String>();
		cboSearchGender.addItem("Nam");
		cboSearchGender.addItem("Nữ");
		CustomUI.getInstance().setCustomComboBox(cboSearchGender);
		txtBFieldSearchGender = CustomUI.getInstance().setCustomCBoxField(cboSearchGender);
		cboSearchGender.setBounds(448, 11, 200, 20);
		cboSearchGender.setVisible(false);
		pnlSearch.add(cboSearchGender);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		CustomUI.getInstance().setBorderTitlePanelTable(pnlTable, "Danh sách khách hàng");
		pnlTable.setBounds(18, 270, 1220, 265);
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã khách hàng", "Tên khách hàng ", "CMND/CCCD", "Số điện thoại", "Ngày sinh",
				"Giới tính" };

		modelTable = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		tblTableCustomer = new JTable(modelTable);
		CustomUI.getInstance().setCustomTable(tblTableCustomer);
		tblTableCustomer.setRowHeight(21);
		JScrollPane scrTable = CustomUI.getInstance().setCustomScrollPaneNotScroll(tblTableCustomer);
		scrTable.setBounds(10, 20, 1200, 235);
		pnlTable.add(scrTable);
		pnlMain.add(pnlTable);

		btnNextToRight = new MyButton(70, 35, "", gra, nextIconRight.getImage(), 0, 0, 14, -8);
		btnNextToRight.setBounds(690, 540, 70, 35);
		pnlMain.add(btnNextToRight);

		btnNextToLast = new MyButton(70, 35, "", gra, doubleNextRightIcon.getImage(), 0, 0, 14, -8);
		btnNextToLast.setBounds(770, 540, 70, 35);
		pnlMain.add(btnNextToLast);

		btnNextToLeft = new MyButton(70, 35, "", gra, nextLeftIcon.getImage(), 0, 0, 14, -8);
		btnNextToLeft.setBounds(510, 540, 70, 35);
		pnlMain.add(btnNextToLeft);

		btnNextToFirst = new MyButton(70, 35, "", gra, doubleNextLeftIcon.getImage(), 0, 0, 14, -8);
		btnNextToFirst.setBounds(430, 540, 70, 35);
		pnlMain.add(btnNextToFirst);

		txtPaging = new PnTextFiledPaging(90, 35);
		txtPaging.setBounds(590, 540, 91, 36);
		txtPaging.setTextColor(Color.WHITE);
		pnlMain.add(txtPaging);

		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnSearch.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnNextToLast.addActionListener(this);
		btnNextToLeft.addActionListener(this);
		btnNextToRight.addActionListener(this);
		btnNextToFirst.addActionListener(this);

		btnSearch.addMouseListener(this);
		tblTableCustomer.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		txtBFieldSearchGender.addMouseListener(this);

		txtCMND.addFocusListener(this);
		txtKeyWord.addFocusListener(this);
		txtPhoneNumber.addFocusListener(this);
		txtBFieldSearch.addFocusListener(this);
		txtCustomerName.addFocusListener(this);
		txtBFieldSearchGender.addFocusListener(this);

		txtCMND.addKeyListener(this);
		txtKeyWord.addKeyListener(this);
		txtPhoneNumber.addKeyListener(this);
		txtCustomerName.addKeyListener(this);
		txtPaging.getTextFieldPaging().addKeyListener(this);

		cboSearch.addItemListener(this);
		cboSearchGender.addItemListener(this);

		allLoaded();
	}

	// public static void main(String[] args) throws
	// java.lang.reflect.InvocationTargetException, InterruptedException {
	// SwingUtilities.invokeLater(() -> {
	// NhanVien staff = new NhanVien("NV00000001");
	// PnKhachHang login = new PnKhachHang(staff, 0);
	// login.setVisible(true);
	// });
	// }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			fDieuHuong winNavigation = new fDieuHuong(staffLogin);
			winNavigation.setVisible(true);
			this.setVisible(false);
		} else if (o.equals(btnAdd)) {
			addNewCustomer();
		} else if (o.equals(btnRefresh)) {
			txtCustomerID.setText("");
			txtCustomerName.setText("");
			txtCMND.setText("");
			txtPhoneNumber.setText("");
			dpBirthDay.setValueToDay();
			radMale.setSelected(true);
			btnUpdate.setEnabledCustom(false);
			btnAdd.setEnabledCustom(true);
			removeSelectionInterval();
		} else if (o.equals(btnSearch)) {
			searchEventUsingBtnSearch();
		} else if (o.equals(btnUpdate)) {
			updateStaffInfo();
		} else if (o.equals(btnNextToLeft)) {
			txtPaging.subtractOne();
			searchEventUsingBtnSearch();
		} else if (o.equals(btnNextToRight)) {
			txtPaging.plusOne();
			searchEventUsingBtnSearch();
		} else if (o.equals(btnNextToFirst)) {
			txtPaging.toTheFirstPage();
			searchEventUsingBtnSearch();
		} else if (o.equals(btnNextToLast)) {
			txtPaging.toTheLastPage();
			searchEventUsingBtnSearch();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
			txtPaging.toTheFirstPage();
			String searchTypeName = cboSearch.getSelectedItem().toString();
			if (searchTypeName.equals("Giới tính")) {
				txtKeyWord.setVisible(false);
				CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
				cboSearchGender.setVisible(true);
			} else {
				txtKeyWord.setVisible(true);
				txtKeyWord.setText("");
				cboSearchGender.setVisible(false);
				if (searchTypeName.equalsIgnoreCase("Tất cả")) {
					CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
				} else {
					CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
				}
			}
			removeSelectionInterval();
			searchEventUsingBtnSearch();
		} else if (o.equals(cboSearchGender)) {
			removeSelectionInterval();
			searchEventUsingBtnSearch();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		} else if (o.equals(txtBFieldSearchGender)) {
			cboSearchGender.showPopup();
		} else if (o.equals(tblTableCustomer)) {
			int selectedRow = tblTableCustomer.getSelectedRow();
			String customerId = tblTableCustomer.getValueAt(selectedRow, 1).toString().trim();
			txtCustomerID.setText(customerId);
			txtCustomerName.setText(tblTableCustomer.getValueAt(selectedRow, 2).toString().trim());
			txtCMND.setText(tblTableCustomer.getValueAt(selectedRow, 3).toString().trim());
			txtPhoneNumber.setText(tblTableCustomer.getValueAt(selectedRow, 4).toString().trim());
			String birthDayStr = tblTableCustomer.getValueAt(selectedRow, 5).toString().trim();
			dpBirthDay.setValue(birthDayStr);
			String genderStr = tblTableCustomer.getValueAt(selectedRow, 6).toString().trim();
			if (genderStr.equalsIgnoreCase("Nam")) {
				radMale.setSelected(true);
			} else {
				radFemale.setSelected(true);
			}
			btnAdd.setEnabledCustom(false);
			btnUpdate.setEnabledCustom(true);
			selectedCustomerId = customerId;
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
		if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldSearchGender)) {
			cboSearchGender.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldSearchGender)) {
			cboSearchGender.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		if (o.equals(txtPaging.getTextFieldPaging())) {
			if (key == KeyEvent.VK_ENTER) {
				searchEventUsingBtnSearch();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		InputEventHandler handler = new InputEventHandler();
		if (o.equals(txtKeyWord)) {
			String searchTypeName = cboSearch.getSelectedItem().toString().trim();
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchEventUsingBtnSearch();
			}
			if (searchTypeName.equalsIgnoreCase("Số điện thoại")) {
				handler.enterOnlyNumbersAndLimitInput(key, txtKeyWord, 10);
			} else {
				handler.characterInputLimit(key, txtKeyWord, 100);
			}
		} else if (o.equals(txtPhoneNumber)) {
			handler.enterOnlyNumbersAndLimitInput(key, txtPhoneNumber, 10);
		} else if (o.equals(txtCMND)) {
			handler.enterOnlyNumbersAndLimitInput(key, txtCMND, 12);
		} else if (o.equals(txtCustomerName)) {
			handler.characterInputLimit(key, txtCustomerName, 100);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtCMND)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtCMND);
		} else if (o.equals(txtCustomerName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtCustomerName);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		} else if (o.equals(txtPhoneNumber)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtPhoneNumber);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtCMND)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		} else if (o.equals(txtCustomerName)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtCustomerName);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		} else if (o.equals(txtPhoneNumber)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		}
	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	private void allLoaded() {
		reSizeColumnTable();
		txtPaging.setCurrentPage(1);
		ArrayList<KhachHang> customerList = new ArrayList<>();
		int totalLine = customerDAO.getTotalLineOfCustomerList();
		txtPaging.setTotalPage(getLastPage(totalLine));
		customerList = customerDAO.getCustomerListAndPageNumber(1, lineNumberDisplayed);
		loadCustomerList(customerList, 1);
	}

	/**
	 * Kiểm tra thông tin trong form
	 * 
	 * @return {@code boolean}: kết quả trả về của quá trình kiểm tra thông tin
	 *         <ul>
	 *         <li>Nếu hợp lệ thì trả về {@code true}</li>
	 *         <li>Nếu không hợp lệ thì trả về {@code false}</li>
	 *         </ul>
	 */
	private boolean validData() {
		boolean valid = ValidationData.getInstance().ValidName(this, txtCustomerName, "Tên khách hàng", 100, 0);
		if (!valid) {
			return valid;
		}

		valid = ValidationData.getInstance().ValidCmnd(this, txtCMND);
		if (!valid) {
			return valid;
		}

		valid = ValidationData.getInstance().ValidPhoneNumber(this, txtPhoneNumber);
		if (!valid) {
			return valid;
		}

		valid = ValidationData.getInstance().ValidBirthDay(this, dpBirthDay, "khách hàng", 16);
		if (!valid) {
			return valid;
		}
		return true;
	}

	/**
	 * Tự động tạo mã khách hàng mới tăng theo thứ tự tăng dần
	 * 
	 * @return {@code String}: mã khách hàng mới
	 */
	private String createNewStaffID() {
		String lastCustomerId = "";
		lastCustomerId = customerDAO.getLastCustomerId();
		String idStr = "KH";
		int oldNumberCustomerID = 0;
		if (lastCustomerId.equalsIgnoreCase("") == false && lastCustomerId != null) {
			oldNumberCustomerID = Integer.parseInt(lastCustomerId.replace(idStr, ""));
		}

		int newCustomerID = ++oldNumberCustomerID;
		String newCustomerIdStr = idStr + newCustomerID;
		boolean flag = true;
		while (flag) {
			newCustomerIdStr = newCustomerIdStr.replace(idStr, idStr + "0");
			if (newCustomerIdStr.length() > 9) {
				flag = false;
			}
		}
		return newCustomerIdStr;
	}

	/**
	 * chuyển đổi thông tin trong form thành đối tượng {@code KhachHang}
	 * 
	 * @return {@code KhachHang}: khách hàng được chuyển đổi thông tin từ form
	 */
	private KhachHang getCustomerDataInForm() {
		String customerId = txtCustomerID.getText().trim();
		String customerName = txtCustomerName.getText().trim();
		String phoneNumber = txtPhoneNumber.getText().trim();
		String cmnd = txtCMND.getText().trim();
		Date birthDay = dpBirthDay.getValueSqlDate();
		boolean gender = radMale.isSelected() ? false : true;
		if (customerId.equals("") || customerId.length() <= 0)
			customerId = createNewStaffID();
		return new KhachHang(customerId, cmnd, customerName, phoneNumber, birthDay, gender);
	}

	/**
	 * Thêm một khách hàng mới
	 */
	private void addNewCustomer() {
		String message = "";
		if (validData()) {
			KhachHang customer = getCustomerDataInForm();
			Boolean result = customerDAO.insertCustomer(customer);
			String name = "khách hàng";
			if (result) {
				txtPaging.toTheLastPage();
				searchEventUsingBtnSearch();
				message = "Thêm " + name + " mới thành công";
				txtCustomerID.setText(customer.getMaKH());
				// int stt = tblTableCustomer.getRowCount();
				// addRow(stt, customer);
				// int i = 1 + (txtPaging.getTotalPage() - 1) * lineNumberDisplayed;
				// int lastIndex = i + tblTableCustomer.getRowCount();
				// tblTableCustomer.getSelectionModel().setSelectionInterval(lastIndex,
				// lastIndex);
				// tblTableCustomer.scrollRectToVisible(tblTableCustomer.getCellRect(lastIndex,
				// lastIndex, true));
				btnAdd.setEnabledCustom(false);
				btnUpdate.setEnabledCustom(true);
			} else {
				message = "Thêm " + name + " thất bại";
			}
			JOptionPane.showMessageDialog(this, message);
		}
	}

	/**
	 * cập nhật thông tin của khách hàng
	 */
	private void updateStaffInfo() {
		if (validData()) {
			KhachHang customer = getCustomerDataInForm();
			KhachHang oldCustomer = customerDAO.getCustomerById(customer.getMaKH());
			if (oldCustomer == null)
				oldCustomer = new KhachHang();
			String staffName = oldCustomer.getHoTen();
			String message = "";
			int selectedRow = tblTableCustomer.getSelectedRow();
			String name = "khách hàng";
			if (selectedRow == -1) {
				message = "Hãy chọn " + name + " mà bạn cần cập nhật thông tin";
				JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				message = "Xác nhận cập nhật thông tin " + name + " " + staffName;
				int choose = JOptionPane.showConfirmDialog(this, message, "Xác nhận cập nhật thông tin " + name + "",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (choose == JOptionPane.OK_OPTION) {
					Boolean result = customerDAO.updateCustomerInfo(customer);
					if (result) {
						message = "Cập nhật thông tin " + name + " thành công";
						updateRow(selectedRow, customer);
						btnAdd.setEnabledCustom(false);
						btnUpdate.setEnabledCustom(true);
						tblTableCustomer.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
						tblTableCustomer
								.scrollRectToVisible(tblTableCustomer.getCellRect(selectedRow, selectedRow, true));
					} else {
						message = "Cập nhật thông tin " + name + " thất bại";
					}
					JOptionPane.showMessageDialog(this, message);
				}
			}
		}
	}

	/**
	 * Cập nhật thông tin một dòng khi biết dòng mà thông tin khách hàng
	 * 
	 * @param selectedRow {@code int}: dòng được chọn
	 * @param staff       {@code KhachHang}: khách hàng cần cập nhật
	 */
	private void updateRow(int selectedRow, KhachHang staff) {
		boolean gender = staff.getGioiTinh();
		String genderStr = gender ? "Nữ" : "Nam";
		String format = "dd-MM-yyyy";
		String birthDayStr = ConvertTime.getInstance().convertTimeToString(staff.getNgaySinh(), format);
		String phoneNumberStr = staff.getSoDienThoai();
		modelTable.setValueAt(addSpaceToString(staff.getHoTen()), selectedRow, 2);
		modelTable.setValueAt(addSpaceToString(staff.getCmnd()), selectedRow, 3);
		modelTable.setValueAt(addSpaceToString(phoneNumberStr), selectedRow, 4);
		modelTable.setValueAt(addSpaceToString(birthDayStr), selectedRow, 5);
		modelTable.setValueAt(addSpaceToString(genderStr), selectedRow, 6);
		modelTable.fireTableDataChanged();
	}

	/**
	 * Hiển thị popup thông báo của 1 JTextField
	 * 
	 * @param txt     {@code JTextField}: được trỏ đến khi cần thông báo
	 * @param type    {@code int}: mã dạng thông báo (Nếu 1. là lỗi)
	 * @param message {@code String}: Tin nhắn được hiển thị
	 * @param title   {@code String}: Tiêu đề thông báo
	 * @param option  {@code int}: loại thông báo (icon)
	 */
	private void showMessage(JTextField txt, int type, String message, String title, int option) {
		if (type == 1) {
			txt.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
		}
		JOptionPane.showMessageDialog(this, message, title, option);
		txt.requestFocus();
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
	 * Thêm dòng vào danh sách khách hàng đang hiển thị
	 * 
	 * @param stt      {@code int}: số thứ tự
	 * @param customer {@code KhachHang}: khách hàng cần được thêm
	 */
	private void addRow(int stt, KhachHang customer) {
		String sttStr = df.format(stt);
		String format = "dd-MM-yyyy";
		String birthDayStr = ConvertTime.getInstance().convertTimeToString(customer.getNgaySinh(), format);
		String genderStr = customer.getGioiTinh() ? "Nữ" : "Nam";
		String phoneNumberStr = customer.getSoDienThoai();
		modelTable.addRow(new Object[] { sttStr, addSpaceToString(customer.getMaKH()),
				addSpaceToString(customer.getHoTen()), addSpaceToString(customer.getCmnd()),
				addSpaceToString(phoneNumberStr), addSpaceToString(birthDayStr), addSpaceToString(genderStr) });
		modelTable.fireTableDataChanged();
	}

	/**
	 * tìm kiếm khách hàng dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		ArrayList<KhachHang> customerList = null;
		String keyword = "";
		int currentPage = txtPaging.getCurrentPage();
		int totalLine = 1;
		switch (searchTypeName) {
			case "Tất cả":
				totalLine = customerDAO.getTotalLineOfCustomerList();
				customerList = customerDAO.getCustomerListAndPageNumber(currentPage, lineNumberDisplayed);
				break;
			case "Tên khách hàng":
				keyword = txtKeyWord.getText().trim();
				totalLine = customerDAO.getTotalLineOfCustomerListByName(keyword);
				customerList = customerDAO.getCustomerListByNameAndPageNumber(keyword, currentPage,
						lineNumberDisplayed);
				break;
			case "Số điện thoại":
				keyword = txtKeyWord.getText().trim().replace("-", "");
				if (keyword.matches("^[\\d]{0,10}$")) {
					totalLine = customerDAO.getTotalLineOfCustomerListByPhoneNumber(keyword);
					customerList = customerDAO.getCustomerListByPhoneNumberAndPageNumber(keyword, currentPage,
							lineNumberDisplayed);
				} else {
					String message = "Sổ điện phải phải là số, không được quá 10 số";
					showMessage(txtKeyWord, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "Giới tính":
				String genderStr = cboSearchGender.getSelectedItem().toString().trim();
				boolean gender = genderStr.equalsIgnoreCase("Nữ") ? true : false;
				totalLine = customerDAO.getTotalLineOfCustomerListByGender(gender);
				customerList = customerDAO.getCustomerListByGenderAndPageNumber(gender, currentPage,
						lineNumberDisplayed);
				break;
			case "Mã khách hàng":
				keyword = txtKeyWord.getText().trim();
				totalLine = customerDAO.getTotalLineOfCustomerListById(keyword);
				customerList = customerDAO.getCustomerListByIdAndPageNumber(keyword, currentPage,
						lineNumberDisplayed);
				break;
		}
		int lastPage = getLastPage(totalLine);
		txtPaging.setTotalPage(lastPage);
		loadCustomerList(customerList, currentPage);
	}

	/**
	 * Hiển thị danh sách khách hàng
	 * 
	 * @param customerList {@code ArrayList<KhachHang>}: danh sách khách hàng
	 */
	private void loadCustomerList(ArrayList<KhachHang> customerList, int currentPage) {
		modelTable.getDataVector().removeAllElements();
		modelTable.fireTableDataChanged();
		int i = 1 + (currentPage - 1) * lineNumberDisplayed;
		for (KhachHang item : customerList) {
			addRow(i++, item);
		}
	}

	/**
	 * Thay đổi kích thước cột
	 */
	private void reSizeColumnTable() {
		tblTableCustomer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = tblTableCustomer.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(70);
		columnModel.getColumn(1).setPreferredWidth(140);
		columnModel.getColumn(2).setPreferredWidth(250);
		columnModel.getColumn(3).setPreferredWidth(190);
		columnModel.getColumn(4).setPreferredWidth(200);
		columnModel.getColumn(5).setPreferredWidth(200);
		columnModel.getColumn(6).setPreferredWidth(152);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		columnModel.getColumn(0).setCellRenderer(centerRenderer);
		columnModel.getColumn(3).setCellRenderer(rightRenderer);
		columnModel.getColumn(4).setCellRenderer(rightRenderer);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tblTableCustomer.getSelectedRow();
		tblTableCustomer.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}

	/**
	 * Lấy nút quay lại
	 */
	public MyButton getBtnBack() {
		return btnBack;
	}

	/**
	 * tính số trang của bảng dựa trên tổng số khách hàng tìm được
	 * 
	 * @param totalLine {@code int} tổng số khách hàng tìm được
	 * @return {@code int} số trang
	 */
	private int getLastPage(int totalLine) {
		int lastPage = totalLine / lineNumberDisplayed;
		if (totalLine % lineNumberDisplayed != 0) {
			lastPage++;
		}
		return lastPage;
	}

	/**
	 * lấy mã Id khách hàng được chọn
	 * 
	 * @return {@code String} mã Id khách hàng
	 */
	public String getSelectedCustomerId() {
		return selectedCustomerId;
	}

	/**
	 * Lấy nút chọn khách hàng
	 */
	public MyButton getBtnChooseCustomer() {
		if (isChooseCustomer == 1) {
			return btnChooseCustomer;
		} else {
			return null;
		}
	}
}
