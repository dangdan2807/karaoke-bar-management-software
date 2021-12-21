package UI.PanelCustom;

import javax.swing.*;
import javax.swing.table.*;

import DAO.*;
import Event_Handlers.*;
import UI.*;
import entity.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Giao diện quản lý nhân viên của phần mềm
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 04/10/2021
 * <p>
 * Lần cập nhật cuối: 18/12/2021
 * <p>
 * Nội dung cập nhật: thay đổi đường dẫn file hình ảnh, 
 * <p>
 */
public class PnNhanVien extends JPanel
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {

	private static final long serialVersionUID = 5815784686959486363L;
	private JTable tblTableStaff;
	private DefaultTableModel modelTableStaff;
	private JTextField txtCMND, txtPhoneNumber, txtStaffName, txtStaffID, txtBFieldSearch, txtKeyWord;
	private JTextField txtBFieldSearchPosition, txtBFieldPosition, txtUsername;
	private JComboBox<String> cboSearch, cboSearchType, cboPosition;
	private JLabel lblCMND, lblBirthDay, lblGender, lblPosition, lblSalary, lblPhoneNumber, lbStaffID;
	private JLabel lblStaffName, lblStatus, lblSearch;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch, btnResetPassword, btnNextToRight;
	private MyButton btnNextToLast, btnNextToLeft, btnNextToFirst;
	private JRadioButton radWorking, radRetired, radMale, radFemale;
	private kDatePicker dpBirthDay;
	private JSpinner spnSalary;
	private PnTextFiledPaging txtPaging;

	private ImageIcon addIcon = new ImageIcon(PnNhanVien.class.getResource(CustomUI.ADD_ICON));
	private ImageIcon refreshIcon = new ImageIcon(PnNhanVien.class.getResource(CustomUI.REFRESH_ICON));
	private ImageIcon searchIcon = new ImageIcon(PnNhanVien.class.getResource(CustomUI.SEARCH_ICON));
	private ImageIcon backIcon = new ImageIcon(PnNhanVien.class.getResource(CustomUI.BACK_ICON));
	private ImageIcon updateIcon = new ImageIcon(PnNhanVien.class.getResource(CustomUI.UPDATE_ICON));
	private ImageIcon bg = new ImageIcon(new ImageIcon(PnNhanVien.class.getResource(
			CustomUI.BACKGROUND)).getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon nextIconRight = new ImageIcon(new ImageIcon(PnNhanVien.class.getResource(
			CustomUI.NEXT_RIGHT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextRightIcon = new ImageIcon(new ImageIcon(PnNhanVien.class.getResource(
			CustomUI.DOUBLE_NEXT_RIGHT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon nextLeftIcon = new ImageIcon(new ImageIcon(PnNhanVien.class.getResource(
			CustomUI.NEXT_LEFT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextLeftIcon = new ImageIcon(new ImageIcon(PnNhanVien.class.getResource(
			CustomUI.DOUBLE_NEXT_LEFT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;
	private boolean isResetPassword = false;
	private boolean isInsertStaff = false;
	private int lineNumberDisplayed = 10;
	private NhanVienDAO staffDAO = NhanVienDAO.getInstance();
	private TaiKhoanDAO accountDAO = TaiKhoanDAO.getInstance();

	/**
	 * Khởi tạo giao diện quản lý nhân viên
	 * 
	 * @param staff {@code NhanVien}: nhân viên truy cập
	 */
	public PnNhanVien(NhanVien staff) {
		this.staffLogin = staff;
		setLayout(null);
		setSize(1270, 630);
		setLayout(new BorderLayout(0, 0));
		// this.setResizable(false);
		// this.setLocationRelativeTo(null);
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnlMain = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				Image bgMain = bg.getImage();
				g2.drawImage(bgMain, 0, 0, null);
				setFont(new Font("Dialog", Font.BOLD, 24));
				g2.setColor(Color.decode("#9B17EB"));
				g2.drawRoundRect(10, 50, 1235, 530, 20, 20);
				g2.drawRoundRect(9, 49, 1235, 530, 20, 20);
			}
		};
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 1270, 630);
		this.add(pnlMain);

		JPanel pnlTitle = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255, 255, 255));
			}
		};

		pnlTitle.setBounds(0, 0, 1270, 50);
		pnlTitle.setOpaque(false);
		pnlTitle.setLayout(null);

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 33, 19, 12, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlTitle.add(btnBack);
		pnlMain.add(pnlTitle);

		JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lblTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(10, 50, 1238, 210);
		pnlMain.add(pnlInfo);

		dpBirthDay = new kDatePicker(250, 20);
		CustomUI.getInstance().setCustomKDatePicker(dpBirthDay);
		dpBirthDay.setToolTipTextCustom("Ngày sinh của nhân viên");
		pnlInfo.add(dpBirthDay);
		dpBirthDay.setBounds(669, 76, 250, 20);

		txtCMND = new JTextField();
		txtCMND.setBounds(165, 51, 250, 20);
		txtCMND.setToolTipText("Nhập CMND gồm có 9 số hoặc CCCD gồm có 12 số");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		pnlInfo.add(txtCMND);

		lblCMND = new JLabel("CMND/CCCD:");
		CustomUI.getInstance().setCustomLabel(lblCMND);
		lblCMND.setBounds(40, 51, 105, 20);
		pnlInfo.add(lblCMND);

		lblBirthDay = new JLabel("Ngày sinh:");
		CustomUI.getInstance().setCustomLabel(lblBirthDay);
		lblBirthDay.setBounds(550, 76, 105, 20);
		pnlInfo.add(lblBirthDay);

		lblGender = new JLabel("Giới tính:");
		CustomUI.getInstance().setCustomLabel(lblGender);
		lblGender.setBounds(550, 127, 105, 20);
		pnlInfo.add(lblGender);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(165, 76, 250, 20);
		txtPhoneNumber.setToolTipText("Nhập số điện thoại của nhân viên gồm 10 số và bắt đầu bằng 03, 05, 07, 08, 09");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		pnlInfo.add(txtPhoneNumber);

		spnSalary = new JSpinner(new SpinnerNumberModel(0f, 0f, Double.MAX_VALUE, 1000f));
		CustomUI.getInstance().setCustomSpinner(spnSalary);
		spnSalary.setBounds(165, 102, 250, 20);
		spnSalary.setToolTipText("Nhập mức lương của nhân viên phải lớn hơn hoặc bằng 0");
		pnlInfo.add(spnSalary);

		radMale = new JRadioButton("Nam");
		CustomUI.getInstance().setCustomRadioButton(radMale);
		radMale.setBounds(669, 127, 115, 20);
		radMale.setSelected(true);
		pnlInfo.add(radMale);

		radFemale = new JRadioButton("Nữ");
		CustomUI.getInstance().setCustomRadioButton(radFemale);
		radFemale.setBounds(787, 127, 115, 20);
		pnlInfo.add(radFemale);

		ButtonGroup groupGender = new ButtonGroup();
		groupGender.add(radMale);
		groupGender.add(radFemale);

		lblPosition = new JLabel("Chức vụ:");
		CustomUI.getInstance().setCustomLabel(lblPosition);
		lblPosition.setBounds(550, 51, 115, 20);
		pnlInfo.add(lblPosition);

		lblSalary = new JLabel("Mức lương:");
		CustomUI.getInstance().setCustomLabel(lblSalary);
		lblSalary.setBounds(40, 102, 115, 20);
		pnlInfo.add(lblSalary);

		lblPhoneNumber = new JLabel("Số điện thoại:");
		CustomUI.getInstance().setCustomLabel(lblPhoneNumber);
		lblPhoneNumber.setBounds(40, 76, 115, 20);
		pnlInfo.add(lblPhoneNumber);

		txtStaffName = new JTextField();
		txtStaffName.setBounds(669, 25, 250, 20);
		txtStaffName.setToolTipText("Nhập tên của nhân viên, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtStaffName);
		pnlInfo.add(txtStaffName);

		txtStaffID = new JTextField();
		txtStaffID.setBounds(165, 25, 250, 20);
		txtStaffID.setToolTipText("Mã nhân viên");
		CustomUI.getInstance().setCustomTextFieldOff(txtStaffID);
		pnlInfo.add(txtStaffID);

		lbStaffID = new JLabel("Mã nhân viên: ");
		CustomUI.getInstance().setCustomLabel(lbStaffID);
		lbStaffID.setBounds(40, 26, 120, 20);
		pnlInfo.add(lbStaffID);

		lblStaffName = new JLabel("Tên nhân viên:");
		CustomUI.getInstance().setCustomLabel(lblStaffName);
		lblStaffName.setBounds(550, 25, 120, 20);
		pnlInfo.add(lblStaffName);

		lblStatus = new JLabel("Trạng thái:");
		CustomUI.getInstance().setCustomLabel(lblStatus);
		lblStatus.setBounds(40, 127, 120, 20);
		pnlInfo.add(lblStatus);

		radWorking = new JRadioButton("Đang làm ");
		CustomUI.getInstance().setCustomRadioButton(radWorking);
		radWorking.setSelected(true);
		radWorking.setBounds(165, 127, 115, 21);
		pnlInfo.add(radWorking);

		radRetired = new JRadioButton("Đã nghỉ");
		CustomUI.getInstance().setCustomRadioButton(radRetired);
		radRetired.setBounds(282, 127, 115, 21);
		pnlInfo.add(radRetired);

		ButtonGroup groupStatus = new ButtonGroup();
		groupStatus.add(radWorking);
		groupStatus.add(radRetired);

		cboPosition = new JComboBox<String>();
		cboPosition.addItem("Nhân viên");
		cboPosition.addItem("Chủ quán");
		CustomUI.getInstance().setCustomComboBox(cboPosition);
		cboPosition.setToolTipText("Chọn chức vụ của nhân viên");
		txtBFieldPosition = CustomUI.getInstance().setCustomCBoxField(cboPosition);
		cboPosition.setEnabled(false);
		cboPosition.setBounds(669, 51, 250, 20);
		pnlInfo.add(cboPosition);

		JPanel pnlSearch = new JPanel();
		pnlSearch.setBounds(40, 173, 1160, 35);
		pnlSearch.setOpaque(false);
		pnlSearch.setLayout(null);
		pnlInfo.add(pnlSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(0, 5, 100, 20);
		pnlSearch.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Trạng thái làm việc");
		cboSearch.addItem("Tên nhân viên");
		cboSearch.addItem("Số điện thoại");
		cboSearch.addItem("Chức vụ");
		cboSearch.addItem("Mã nhân viên");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		cboSearch.setToolTipText("Chọn loại thông tin cần lọc");
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(125, 6, 250, 20);
		pnlSearch.add(cboSearch);

		btnSearch = new MyButton(150, 35, "Tìm kiếm", gra, searchIcon.getImage(), 50, 19, 10, 5);
		btnSearch.setBounds(996, 2, 150, 35);
		btnSearch.setToolTipText("Tìm kiếm thông tin nhân viên theo yêu cần đã chọn");
		pnlSearch.add(btnSearch);

		JLabel lblKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lblKeyWord);
		lblKeyWord.setBounds(510, 5, 73, 20);
		pnlSearch.add(lblKeyWord);

		txtKeyWord = new JTextField();
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		txtKeyWord.setToolTipText("Nhập từ khóa cần tìm kiếm");
		txtKeyWord.setBounds(629, 6, 250, 20);
		txtKeyWord.setVisible(false);
		pnlSearch.add(txtKeyWord);

		cboSearchType = new JComboBox<String>();
		cboSearchType.addItem("Đang làm");
		cboSearchType.addItem("Đã nghỉ");
		CustomUI.getInstance().setCustomComboBox(cboSearchType);
		txtBFieldSearchPosition = CustomUI.getInstance().setCustomCBoxField(cboSearchType);
		cboSearchType.setToolTipText("Chọn chức vụ muốn lọc");
		cboSearchType.setBounds(629, 6, 250, 20);
		pnlSearch.add(cboSearchType);

		JLabel lbUsername = new JLabel("Tên đăng nhập:");
		CustomUI.getInstance().setCustomLabel(lbUsername);
		lbUsername.setBounds(550, 102, 115, 20);
		pnlInfo.add(lbUsername);

		txtUsername = new JTextField();
		txtUsername.setBounds(669, 102, 250, 20);
		txtUsername.setToolTipText("Tên đăng nhập phải có từ 6 đến 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtUsername);
		pnlInfo.add(txtUsername);

		btnAdd = new MyButton(150, 35, "Thêm", gra, addIcon.getImage(), 55, 19, 10, 6);
		btnAdd.setBounds(1036, 17, 150, 35);
		pnlInfo.add(btnAdd);
		btnAdd.setToolTipText("Thêm nhân viên mới từ thông tin đã nhập trên form");

		btnUpdate = new MyButton(150, 35, "Sửa", gra, updateIcon.getImage(), 63, 19, 10, 6);
		btnUpdate.setBounds(1036, 54, 150, 35);
		pnlInfo.add(btnUpdate);
		btnUpdate.setEnabledCustom(false);
		btnUpdate.setToolTipText("Cập nhật thông tin nhân viên");

		btnRefresh = new MyButton(150, 35, "Làm mới", gra, refreshIcon.getImage(), 50, 19, 10, 5);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRefresh.setBounds(1036, 91, 150, 35);
		btnRefresh.setBackground(Color.CYAN);
		btnRefresh.setToolTipText("Xóa rỗng form thông tin");
		pnlInfo.add(btnRefresh);

		btnResetPassword = new MyButton(150, 35, "Đặt lại mật khẩu", gra, updateIcon.getImage(), 31, 19, 10, 5);
		btnResetPassword.setBounds(1036, 128, 150, 35);
		btnResetPassword.setBackground(Color.CYAN);
		btnResetPassword.setToolTipText("Làm mới lại mật khẩu nhân viên");
		pnlInfo.add(btnResetPassword);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		CustomUI.getInstance().setBorderTitlePanelTable(pnlTable, "Danh sách nhân viên");
		pnlTable.setOpaque(false);
		pnlTable.setBounds(18, 260, 1220, 270);
		String[] cols = { "STT", "Mã nhân viên", "Tên nhân viên", "CMND/CCCD", "Chức vụ", "SDT", "Ngày sinh",
				"Mức lương", "Giới tính", "Trạng thái", "Tài khoản" };
		modelTableStaff = new DefaultTableModel(cols, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		tblTableStaff = new JTable(modelTableStaff);
		CustomUI.getInstance().setCustomTable(tblTableStaff);
		tblTableStaff.setRowHeight(21);
		JScrollPane scrTable = CustomUI.getInstance().setCustomScrollPaneNotScroll(tblTableStaff);
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
		btnSearch.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnNextToLast.addActionListener(this);
		btnNextToLeft.addActionListener(this);
		btnNextToRight.addActionListener(this);
		btnNextToFirst.addActionListener(this);
		btnResetPassword.addActionListener(this);

		txtCMND.addMouseListener(this);
		cboSearch.addMouseListener(this);
		spnSalary.addMouseListener(this);
		cboPosition.addMouseListener(this);
		txtStaffName.addMouseListener(this);
		cboSearchType.addMouseListener(this);
		tblTableStaff.addMouseListener(this);
		txtPhoneNumber.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		txtBFieldPosition.addMouseListener(this);
		txtBFieldSearchPosition.addMouseListener(this);

		txtCMND.addFocusListener(this);
		cboSearch.addFocusListener(this);
		dpBirthDay.addFocusListener(this);
		txtKeyWord.addFocusListener(this);
		cboPosition.addFocusListener(this);
		txtUsername.addFocusListener(this);
		txtStaffName.addFocusListener(this);
		cboSearchType.addFocusListener(this);
		txtPhoneNumber.addFocusListener(this);
		((JSpinner.DefaultEditor) spnSalary.getEditor()).getTextField().addFocusListener(this);

		cboSearch.addItemListener(this);
		cboSearchType.addItemListener(this);

		btnAdd.addKeyListener(this);
		btnBack.addKeyListener(this);
		txtCMND.addKeyListener(this);
		btnUpdate.addKeyListener(this);
		btnSearch.addKeyListener(this);
		btnRefresh.addKeyListener(this);
		txtKeyWord.addKeyListener(this);
		txtUsername.addKeyListener(this);
		txtStaffName.addKeyListener(this);
		txtPhoneNumber.addKeyListener(this);
		txtPaging.getTextFieldPaging().addKeyListener(this);

		allLoaded();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			refreshForm();
		} else if (o.equals(btnAdd)) {
			isInsertStaff = true;
			addNewStaff();
		} else if (o.equals(btnBack)) {
			backTofDieuHuong();
		} else if (o.equals(btnUpdate)) {
			updateStaffInfo();
		} else if (o.equals(btnSearch)) {
			searchEventUsingBtnSearch();
		} else if (o.equals(btnResetPassword)) {
			isResetPassword = true;
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
			txtPaging.setCurrentPage(1);
			String searchTypeName = cboSearch.getSelectedItem().toString();
			txtKeyWord.setText("");
			if (searchTypeName.equalsIgnoreCase("Chức vụ") || searchTypeName.equalsIgnoreCase("Trạng thái làm việc")) {
				cboSearchType.setVisible(true);
				txtKeyWord.setVisible(false);
				cboSearchType.removeAllItems();
				if (searchTypeName.equalsIgnoreCase("Chức vụ")) {
					cboSearchType.addItem("Nhân viên");
					cboSearchType.addItem("Chủ quán");
				} else if (searchTypeName.equalsIgnoreCase("Trạng thái làm việc")) {
					cboSearchType.addItem("Đang làm");
					cboSearchType.addItem("Đã nghỉ");
				}
			} else {
				txtKeyWord.setEditable(true);
				CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
				cboSearchType.setVisible(false);
				txtKeyWord.setVisible(true);
			}
		} else if (o.equals(cboSearchType)) {
			removeSelectionInterval();
			searchEventUsingBtnSearch();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		} else if (o.equals(txtBFieldSearchPosition)) {
			cboSearchType.showPopup();
		} else if (o.equals(txtBFieldPosition)) {
			if (cboPosition.isEnabled()) {
				cboPosition.showPopup();
			}
		} else if (o.equals(tblTableStaff)) {
			int selectedRow = tblTableStaff.getSelectedRow();
			txtStaffID.setText(tblTableStaff.getValueAt(selectedRow, 1).toString().trim());
			txtStaffName.setText(tblTableStaff.getValueAt(selectedRow, 2).toString().trim());
			txtCMND.setText(tblTableStaff.getValueAt(selectedRow, 3).toString().trim());
			String positionStr = tblTableStaff.getValueAt(selectedRow, 4).toString().trim();
			cboPosition.setSelectedIndex(0);
			if (positionStr.equalsIgnoreCase("Chủ quán")) {
				cboPosition.setSelectedIndex(1);
			}
			txtPhoneNumber.setText(tblTableStaff.getValueAt(selectedRow, 5).toString().trim());
			dpBirthDay.setValue(tblTableStaff.getValueAt(selectedRow, 6).toString().trim());
			String salaryStr = tblTableStaff.getValueAt(selectedRow, 7).toString().trim().replace(",", "");
			spnSalary.setValue(Double.parseDouble(salaryStr));
			String genderStr = tblTableStaff.getValueAt(selectedRow, 8).toString().trim();
			radMale.setSelected(true);
			if (genderStr.equalsIgnoreCase("Nữ")) {
				radFemale.setSelected(true);
			}
			String statusStr = tblTableStaff.getValueAt(selectedRow, 9).toString().trim();
			radWorking.setSelected(true);
			if (statusStr.equalsIgnoreCase("Đã nghỉ")) {
				radRetired.setSelected(true);
			}
			txtUsername.setText(tblTableStaff.getValueAt(selectedRow, 10).toString().trim());
			CustomUI.getInstance().setCustomTextFieldOff(txtUsername);
			txtUsername.setEditable(false);
			btnAdd.setEnabledCustom(false);
			btnUpdate.setEnabledCustom(true);
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
		} else if (o.equals(txtBFieldSearchPosition)) {
			cboSearchType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldPosition)) {
			if (cboPosition.isEnabled()) {
				cboPosition.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldSearchPosition)) {
			cboSearchType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldPosition)) {
			cboPosition.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
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
			} else if (searchTypeName.equalsIgnoreCase("Tên nhân viên") ||
					searchTypeName.equalsIgnoreCase("Mã nhân viên")) {
				handler.characterInputLimit(key, txtKeyWord, 100);
			}
		} else if (o.equals(txtPhoneNumber)) {
			handler.enterOnlyNumbersAndLimitInput(key, txtPhoneNumber, 10);
		} else if (o.equals(txtCMND)) {
			handler.enterOnlyNumbersAndLimitInput(key, txtCMND, 12);
		} else if (o.equals(txtStaffName)) {
			handler.characterInputLimit(key, txtStaffName, 100);
		} else if (o.equals(txtUsername)) {
			handler.characterInputLimit(key, txtUsername, 100);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtStaffName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtStaffName);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		} else if (o.equals(txtPhoneNumber)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtPhoneNumber);
		} else if (o.equals(txtCMND)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtCMND);
		} else if (o.equals(txtUsername)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtUsername);
		} else if (o.equals(((JSpinner.DefaultEditor) spnSalary.getEditor()).getTextField())) {
			spnSalary.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtStaffName)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtStaffName);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		} else if (o.equals(txtPhoneNumber)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		} else if (o.equals(txtCMND)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		} else if (o.equals(txtUsername)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtUsername);
		} else if (o.equals(((JSpinner.DefaultEditor) spnSalary.getEditor()).getTextField())) {
			spnSalary.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	private void allLoaded() {
		reSizeColumnTable();
		String workingStatus = cboSearchType.getSelectedItem().toString().trim();
		int totalLine = 1;
		ArrayList<NhanVien> staffList = new ArrayList<>();
		txtPaging.setCurrentPage(1);
		totalLine = staffDAO.getTotalLineOfStaffListByWorkingStatus(workingStatus);
		txtPaging.setTotalPage(getLastPage(totalLine));
		staffList = staffDAO.getStaffListByWorkingStatusAndPageNumber(workingStatus, 1,
				lineNumberDisplayed);
		loadStaffList(staffList, 1);
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
	 * Kiểm tra thông tin trong form
	 * 
	 * @return {@code boolean}: kết quả trả về của quá trình kiểm tra thông tin
	 *         <ul>
	 *         <li>Nếu hợp lệ thì trả về {@code true}</li>
	 *         <li>Nếu không hợp lệ thì trả về {@code false}</li>
	 *         </ul>
	 */
	private boolean validData() {
		String message = "";
		String staffID = txtStaffID.getText().trim();

		boolean valid = ValidationData.getInstance().ValidName(this, txtStaffName, "Tên nhân viên", 100, 0);
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

		valid = ValidationData.getInstance().ValidBirthDay(this, dpBirthDay, "nhân viên", 16);
		if (!valid) {
			return valid;
		}

		if (staffID.equalsIgnoreCase("")) {
			String username = txtUsername.getText().trim();
			if ((username.equalsIgnoreCase("") || username.length() <= 0 || username.length() >= 100
					|| username.length() < 6) && !username.matches("^[a-zA-Z@!#]{6,100}$")) {
				if (username.equalsIgnoreCase("") || username.length() < 0) {
					message = "tài khoản không được rỗng";
				} else if (username.length() > 100) {
					message = "tài khoản không được quá 100 ký tự, số và @, !, #";
				} else if (username.length() < 6)
					message = "tài khoản tối thiểu có 6 ký tự, số và @, !, #";
				else if (!username.matches("^[a-zA-Z@!#$_]{6,100}$"))
					message = "tài khoản chỉ gồm 6-100 ký tự, số và @, !, #";
				showMessage(txtUsername, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			TaiKhoan account = accountDAO.getAccountByUsername(username);
			if (account != null) {
				message = "Tài khoản đã tồn tại, vui lòng nhập tài khoản khác";
				showMessage(txtUsername, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * Tự động tạo mã nhân viên mới tăng theo thứ tự tăng dần
	 * 
	 * @return {@code String}: mã nhân viên mới
	 */
	private String createNewStaffID() {
		String newStaffIdStr = "";
		String lastStaffId = staffDAO.getLastStaffID();
		String idStr = "NV";
		int oldNumberStaffID = 0;
		if (!lastStaffId.equals("") && !lastStaffId.isEmpty() && lastStaffId != null) {
			oldNumberStaffID = Integer.parseInt(lastStaffId.replace(idStr, ""));
		}

		int newStaffID = ++oldNumberStaffID;
		newStaffIdStr = idStr + newStaffID;
		boolean flag = true;
		while (flag) {
			newStaffIdStr = newStaffIdStr.replace(idStr, idStr + "0");
			if (newStaffIdStr.length() > 9) {
				flag = false;
			}
		}
		return newStaffIdStr;
	}

	/**
	 * chuyển đổi thông tin trong form thành đối tượng {@code NhanVien}
	 * 
	 * @return {@code NhanVien}: nhân viên được chuyển đổi thông tin từ form
	 */
	private NhanVien getStaffDataInForm() {
		String staffID = txtStaffID.getText().trim();
		String staffName = txtStaffName.getText().trim();
		String status = radRetired.isSelected() ? "Đã nghỉ" : "Đang làm";
		boolean accountStatus = radRetired.isSelected() ? false : true;
		String position = cboPosition.getSelectedItem().toString().trim();
		Double salary = Double.parseDouble(spnSalary.getValue().toString());
		String phoneNumber = txtPhoneNumber.getText().trim();
		String cmnd = txtCMND.getText().trim();
		Date birthDay = dpBirthDay.getValueSqlDate();
		boolean gender = radMale.isSelected() ? false : true;
		String username = txtUsername.getText().trim();
		TaiKhoan account = new TaiKhoan(username);
		account.setTinhTrangTK(accountStatus);
		if (isResetPassword == true || isInsertStaff == true) {
			account.setMatKhau("123456");
		} else {
			NhanVien staff = staffDAO.getStaffByUsername(username);
			if (staff == null)
				staff = new NhanVien();
			String password = staff.getTaiKhoan().getMatKhau();
			account.setMatKhau(password);
		}
		if (staffID.equals("") || staffID.length() <= 0)
			staffID = createNewStaffID();
		return new NhanVien(staffID, cmnd, staffName, birthDay, phoneNumber, position, salary, gender, status, account);
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
	 * Thêm dòng vào danh sách nhân viên đang hiển thị
	 * 
	 * @param stt   {@code int}: số thứ tự
	 * @param staff {@code NhanVien}: nhân viên cần được thêm
	 */
	private void addRow(int stt, NhanVien staff) {
		String sttStr = df.format(stt);
		String salaryStr = df.format(staff.getMucLuong());
		boolean gender = staff.getGioiTinh();
		String genderStr = gender ? "Nữ" : "Nam";
		String format = "dd-MM-yyyy";
		String birthDayStr = ConvertTime.getInstance().convertTimeToString(staff.getNgaySinh(), format);
		String phoneNumberStr = staff.getSoDienThoai();
		modelTableStaff.addRow(new Object[] { sttStr, addSpaceToString(staff.getMaNhanVien()),
				addSpaceToString(staff.getHoTen()), addSpaceToString(staff.getCmnd()),
				addSpaceToString(staff.getChucVu()), addSpaceToString(phoneNumberStr), addSpaceToString(birthDayStr),
				addSpaceToString(salaryStr), addSpaceToString(genderStr), addSpaceToString(staff.getTrangThaiNV()),
				addSpaceToString(staff.getTaiKhoan().getTenDangNhap()) });
		modelTableStaff.fireTableDataChanged();
	}

	/**
	 * Cập nhật thông tin một dòng khi biết dòng mà thông tin nhân viên
	 * 
	 * @param selectedRow {@code int}: dòng được chọn
	 * @param staff       {@code NhanVien}: nhân viên cần cập nhật
	 */
	private void updateRow(int selectedRow, NhanVien staff) {
		String SalaryStr = df.format(staff.getMucLuong());
		boolean gender = staff.getGioiTinh();
		String genderStr = gender ? "Nữ" : "Nam";
		String format = "dd-MM-yyyy";
		String birthDayStr = ConvertTime.getInstance().convertTimeToString(staff.getNgaySinh(), format);
		String phoneNumberStr = staff.getSoDienThoai();
		modelTableStaff.setValueAt(addSpaceToString(staff.getHoTen()), selectedRow, 2);
		modelTableStaff.setValueAt(addSpaceToString(staff.getCmnd()), selectedRow, 3);
		modelTableStaff.setValueAt(addSpaceToString(staff.getChucVu()), selectedRow, 4);
		modelTableStaff.setValueAt(addSpaceToString(phoneNumberStr), selectedRow, 5);
		modelTableStaff.setValueAt(addSpaceToString(birthDayStr), selectedRow, 6);
		modelTableStaff.setValueAt(addSpaceToString(SalaryStr), selectedRow, 7);
		modelTableStaff.setValueAt(addSpaceToString(genderStr), selectedRow, 8);
		modelTableStaff.setValueAt(addSpaceToString(staff.getTrangThaiNV()), selectedRow, 9);
		modelTableStaff.fireTableDataChanged();
	}

	/**
	 * Hiển thị danh sách nhân viên
	 * 
	 * @param staffList   {@code ArrayList <NhanVien>}: danh sách nhân viên
	 * @param currentPage {@code int}: số của trang hiện tại
	 */
	private void loadStaffList(ArrayList<NhanVien> staffList, int currentPage) {
		modelTableStaff.getDataVector().removeAllElements();
		modelTableStaff.fireTableDataChanged();
		int i = 1 + (currentPage - 1) * lineNumberDisplayed;
		for (NhanVien item : staffList) {
			addRow(i++, item);
		}
	}

	/**
	 * Thay đổi kích thước cột
	 */
	private void reSizeColumnTable() {
		TableColumnModel columnModel = tblTableStaff.getColumnModel();
		tblTableStaff.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(1).setPreferredWidth(100);
		columnModel.getColumn(2).setPreferredWidth(200);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(5).setPreferredWidth(100);
		columnModel.getColumn(6).setPreferredWidth(100);
		columnModel.getColumn(7).setPreferredWidth(100);
		columnModel.getColumn(8).setPreferredWidth(80);
		columnModel.getColumn(9).setPreferredWidth(130);
		columnModel.getColumn(10).setPreferredWidth(150);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		columnModel.getColumn(0).setCellRenderer(centerRenderer);
		columnModel.getColumn(3).setCellRenderer(rightRenderer);
		columnModel.getColumn(5).setCellRenderer(rightRenderer);
		columnModel.getColumn(6).setCellRenderer(rightRenderer);
		columnModel.getColumn(7).setCellRenderer(rightRenderer);

	}

	/**
	 * tìm kiếm nhân viên dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		ArrayList<NhanVien> staffList = new ArrayList<>();
		String keyword = "";
		int currentPage = txtPaging.getCurrentPage();
		int totalLine = 1;
		switch (searchTypeName) {
			case "Trạng thái làm việc":
				keyword = "Đang làm";
				if (cboSearchType.getSelectedItem() != null) {
					keyword = cboSearchType.getSelectedItem().toString().trim();
				}
				totalLine = staffDAO.getTotalLineOfStaffListByWorkingStatus(keyword);
				staffList = staffDAO.getStaffListByWorkingStatusAndPageNumber(keyword, currentPage,
						lineNumberDisplayed);
				break;
			case "Tên nhân viên":
				keyword = txtKeyWord.getText().trim();
				totalLine = staffDAO.getTotalLineByStaffName(keyword);
				staffList = staffDAO.getStaffListByStaffNameAndPageNumber(keyword, currentPage, lineNumberDisplayed);
				break;
			case "Số điện thoại":
				keyword = txtKeyWord.getText().trim();
				if (keyword.matches("^[\\d]{0,10}$")) {
					totalLine = staffDAO.getTotalLineByPhoneNumber(keyword);
					staffList = staffDAO.getStaffListByPhoneNumberAndPageNumber(keyword, currentPage,
							lineNumberDisplayed);
				} else {
					String message = "Sổ điện phải phải là số, không được quá 10 số";
					showMessage(txtKeyWord, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "Chức vụ":
				keyword = "Nhân viên";
				if (cboSearchType.getSelectedItem() != null) {
					keyword = cboSearchType.getSelectedItem().toString().trim();
				}
				totalLine = staffDAO.getTotalLineByPosition(keyword);
				staffList = staffDAO.getStaffListByPositionAndPageNumber(keyword, currentPage, lineNumberDisplayed);
				break;
			case "Mã nhân viên":
				keyword = txtKeyWord.getText().trim();
				totalLine = staffDAO.getTotalLineByStaffId(keyword);
				staffList = staffDAO.getStaffListByStaffIdAndPageNumber(keyword, currentPage, lineNumberDisplayed);
				break;
		}
		int lastPage = getLastPage(totalLine);
		txtPaging.setTotalPage(lastPage);
		loadStaffList(staffList, currentPage);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tblTableStaff.getSelectedRow();
		tblTableStaff.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}

	/**
	 * Làm mới form thông tin
	 */
	private void refreshForm() {
		txtStaffID.setText("");
		txtStaffName.setText("");
		txtCMND.setText("");
		txtPhoneNumber.setText("");
		spnSalary.setValue((double) 0);
		radMale.setSelected(true);
		cboPosition.setSelectedIndex(0);
		dpBirthDay.setValueToDay();
		radWorking.setSelected(true);
		txtUsername.setText("");
		txtUsername.setEditable(true);
		CustomUI.getInstance().setCustomTextFieldOn(txtUsername);
		btnUpdate.setEnabledCustom(false);
		btnAdd.setEnabledCustom(true);
		removeSelectionInterval();
	}

	/**
	 * Thêm một nhân viên mới
	 */
	private void addNewStaff() {
		String message = "";
		if (validData()) {
			NhanVien staff = getStaffDataInForm();
			if (staff.getChucVu().equalsIgnoreCase("Nhân viên")) {
				Boolean result = staffDAO.insertStaff(staff);
				String name = "nhân viên";
				if (result) {
					txtPaging.toTheLastPage();
					searchEventUsingBtnSearch();
					message = "Thêm " + name + " mới thành công";
					txtStaffID.setText(staff.getMaNhanVien());
					// int stt = tblTableStaff.getRowCount() + 1;
					// addRow(stt, staff);
					// int lastIndex = tblTableStaff.getRowCount() - 1;
					// tblTableStaff.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
					// tblTableStaff.scrollRectToVisible(tblTableStaff.getCellRect(lastIndex,
					// lastIndex, true));
					CustomUI.getInstance().setCustomTextFieldOff(txtUsername);
					btnAdd.setEnabledCustom(false);
					btnUpdate.setEnabledCustom(true);
					isInsertStaff = false;
				} else {
					message = "Thêm " + name + " thất bại";
				}
			} else {
				message = "Chỉ có duy nhất 1 chủ quán";
			}
			JOptionPane.showMessageDialog(this, message);
		}
	}

	/**
	 * cập nhật thông tin của nhân viên
	 */
	private void updateStaffInfo() {
		if (validData()) {
			NhanVien staff = getStaffDataInForm();
			String message = "";
			boolean isManager = staffLogin.getTaiKhoan().getTenDangNhap()
					.equalsIgnoreCase(staff.getTaiKhoan().getTenDangNhap());
			boolean isUpdateStatus = isManager && staff.getTrangThaiNV().equalsIgnoreCase("Đã nghỉ");
			boolean isUpdateManager = isManager && staff.getChucVu().equalsIgnoreCase("Nhân viên");
			if (!isUpdateStatus && !isUpdateManager) {
				String staffName = staffDAO.getStaffNameById(staff.getMaNhanVien());
				int selectedRow = tblTableStaff.getSelectedRow();
				String name = "nhân viên";
				if (selectedRow == -1) {
					message = "Hãy chọn " + name + " mà bạn cần cập nhật thông tin";
					JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					message = "Xác nhận cập nhật thông tin " + name + " " + staffName;
					int choose = JOptionPane.showConfirmDialog(this, message,
							"Xác nhận cập nhật thông tin " + name + "", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (choose == JOptionPane.OK_OPTION) {
						Boolean result = false;
						if (isResetPassword || staff.getTaiKhoan().getTinhTrangTK() == false) {
							result = staffDAO.updateInfoStaffAndAccount(staff);
						} else {
							result = staffDAO.updateInfoStaff(staff);
						}
						if (result) {
							message = "Cập nhật thông tin " + name + " thành công";
							updateRow(selectedRow, staff);
							CustomUI.getInstance().setCustomTextFieldOff(txtUsername);
							btnAdd.setEnabledCustom(false);
							btnUpdate.setEnabledCustom(true);
							tblTableStaff.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tblTableStaff
									.scrollRectToVisible(tblTableStaff.getCellRect(selectedRow, selectedRow, true));
							isResetPassword = false;
						} else {
							message = "Cập nhật thông tin " + name + " thất bại";
						}
						JOptionPane.showMessageDialog(this, message);
					}
				}
			} else {
				if (isUpdateStatus)
					message = "Bạn không thể vô hiệu hóa tài khoản của chính bạn";
				else if (isUpdateManager)
					message = "Bạn không thể thay đổi chức vụ của chính bạn";
				JOptionPane.showMessageDialog(this, message);
			}
		}
	}

	/**
	 * tính số trang của bảng dựa trên tổng số nhân viên tìm được
	 * 
	 * @param totalLine {@code int} tổng số nhân viên tìm được
	 * @return {@code int} số trang
	 */
	public int getLastPage(int totalLine) {
		int lastPage = totalLine / lineNumberDisplayed;
		if (totalLine % lineNumberDisplayed != 0) {
			lastPage++;
		}
		return lastPage;
	}

	/**
	 * quay trở lại form điều hướng
	 */
	private void backTofDieuHuong() {
		fDieuHuong winNavigation = new fDieuHuong(staffLogin);
		this.setVisible(false);
		winNavigation.setVisible(true);
	}

	/**
	 * Lấy nút quay lại
	 */
	public JButton getBtnBack() {
		return btnBack;
	}
}