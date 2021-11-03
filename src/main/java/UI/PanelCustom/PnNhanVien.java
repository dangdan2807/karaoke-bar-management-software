package UI.PanelCustom;

import javax.swing.*;
import javax.swing.table.*;

import DAO.ConvertTime;
import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import DAO.ValidationData;
import Event_Handlers.InputEventHandler;
import UI.fDieuHuong;
import entity.NhanVien;
import entity.TaiKhoan;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PnNhanVien extends JPanel
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	private JTable tblTableStaff;
	private DefaultTableModel modelTableStaff;
	private JTextField txtCMND, txtPhoneNumber, txtStaffName, txtStaffID, txtBFieldSearch, txtKeyWord;
	private JTextField txtBFieldSearchPosition, txtBFieldPosition, txtUsername;
	private JComboBox<String> cboSearch, cboSearchPosition, cboPosition;
	private JLabel lblCMND, lblBirthDay, lblGender, lblPosition, lblSalary, lblPhoneNumber, lbStaffID;
	private JLabel lblStaffName, lblStatus, lblSearch;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch;
	private JRadioButton radWorking, radRetired, radMale, radFemale;
	private kDatePicker dpBirthDay;
	private JSpinner spnSalary;

	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon addIcon = CustomUI.ADD_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon updateIcon = CustomUI.UPDATE_ICON;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;

	/**
	 * Constructor form quản lý nhân viên
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
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				Image bgMain = bg.getImage();
				g2.drawImage(bgMain, 0, 0, null);
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

		pnlTitle.setBounds(0, 0, 1270, 50);
		pnlTitle.setOpaque(false);
		pnlTitle.setLayout(null);

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 33, 19, 12, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlTitle.add(btnBack);
		pnlMain.add(pnlTitle);

		JLabel lbTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
		lbTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lbTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(10, 50, 1238, 182);
		pnlMain.add(pnlInfo);

		dpBirthDay = new kDatePicker(250, 20);
		dpBirthDay.setBackgroundColor(new Color(255, 255, 255, 50));
		dpBirthDay.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dpBirthDay.setForegroundCustom(Color.white);
		dpBirthDay.setOpaqueCustom(false);
		dpBirthDay.setToolTipTextCustom("Ngày sinh của nhân viên");
		dpBirthDay.setFontCustom(new Font("Dialog", Font.PLAIN, 14));
		pnlInfo.add(dpBirthDay);
		dpBirthDay.setBounds(965, 54, 250, 20);

		txtCMND = new JTextField();
		txtCMND.setBounds(965, 29, 250, 20);
		txtCMND.setToolTipText("Nhập CMND gồm có 9 số hoặc CCCD gồm có 12 số");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		pnlInfo.add(txtCMND);

		lblCMND = new JLabel("CMND/CCCD:");
		lblCMND.setForeground(Color.WHITE);
		lblCMND.setBounds(845, 29, 105, 20);
		pnlInfo.add(lblCMND);

		lblBirthDay = new JLabel("Ngày sinh:");
		lblBirthDay.setForeground(Color.WHITE);
		lblBirthDay.setBounds(845, 54, 105, 20);
		pnlInfo.add(lblBirthDay);

		lblGender = new JLabel("Giới tính:");
		lblGender.setForeground(Color.WHITE);
		lblGender.setBounds(845, 79, 105, 20);
		pnlInfo.add(lblGender);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(555, 79, 250, 20);
		txtPhoneNumber.setToolTipText("Nhập số điện thoại của nhân viên gồm 10 số và bắt đầu bằng 03, 05, 07, 08, 09");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		pnlInfo.add(txtPhoneNumber);

		spnSalary = new JSpinner(new SpinnerNumberModel(0f, 0f, Double.MAX_VALUE, 1000f));
		CustomUI.getInstance().setCustomSpinner(spnSalary);
		spnSalary.setBounds(554, 54, 250, 20);
		spnSalary.setToolTipText("Nhập mức lương của nhân viên phải lớn hơn hoặc bằng 0");
		pnlInfo.add(spnSalary);

		radMale = new JRadioButton("Nam");
		CustomUI.getInstance().setCustomRadioButton(radMale);
		radMale.setBounds(961, 79, 115, 20);
		radMale.setSelected(true);
		pnlInfo.add(radMale);

		radFemale = new JRadioButton("Nữ");
		CustomUI.getInstance().setCustomRadioButton(radFemale);
		radFemale.setBounds(1082, 79, 115, 20);
		pnlInfo.add(radFemale);

		ButtonGroup groupGender = new ButtonGroup();
		groupGender.add(radMale);
		groupGender.add(radFemale);

		lblPosition = new JLabel("Chức vụ:");
		lblPosition.setForeground(Color.WHITE);
		lblPosition.setBounds(435, 29, 115, 20);
		pnlInfo.add(lblPosition);

		lblSalary = new JLabel("Mức lương:");
		lblSalary.setForeground(Color.WHITE);
		lblSalary.setBounds(435, 54, 115, 16);
		pnlInfo.add(lblSalary);

		lblPhoneNumber = new JLabel("Số điện thoại:");
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setBounds(435, 79, 115, 16);
		pnlInfo.add(lblPhoneNumber);

		txtStaffName = new JTextField();
		txtStaffName.setBounds(145, 54, 250, 20);
		txtStaffName.setToolTipText("Nhập tên của nhân viên, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtStaffName);
		pnlInfo.add(txtStaffName);

		txtStaffID = new JTextField();
		txtStaffID.setBounds(145, 28, 250, 20);
		txtStaffID.setToolTipText("Mã nhân viên");
		CustomUI.getInstance().setCustomTextFieldOff(txtStaffID);
		pnlInfo.add(txtStaffID);

		lbStaffID = new JLabel("Mã nhân viên: ");
		lbStaffID.setForeground(Color.WHITE);
		lbStaffID.setBackground(new Color(249, 249, 249));
		lbStaffID.setBounds(20, 29, 120, 20);
		pnlInfo.add(lbStaffID);

		lblStaffName = new JLabel("Tên nhân viên:");
		lblStaffName.setForeground(Color.WHITE);
		lblStaffName.setBounds(20, 54, 120, 20);
		pnlInfo.add(lblStaffName);

		lblStatus = new JLabel("Trạng thái:");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setBounds(20, 79, 120, 20);
		pnlInfo.add(lblStatus);

		radWorking = new JRadioButton("Đang làm ");
		CustomUI.getInstance().setCustomRadioButton(radWorking);
		radWorking.setSelected(true);
		radWorking.setBounds(142, 78, 115, 21);
		pnlInfo.add(radWorking);

		radRetired = new JRadioButton("Đã nghỉ");
		CustomUI.getInstance().setCustomRadioButton(radRetired);
		radRetired.setBounds(262, 78, 115, 21);
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
		cboPosition.setBounds(555, 28, 250, 20);
		pnlInfo.add(cboPosition);

		JPanel pnlSearch = new JPanel();
		pnlSearch.setBounds(10, 135, 1218, 41);
		pnlInfo.add(pnlSearch);
		pnlSearch.setOpaque(false);
		pnlSearch.setLayout(null);
		pnlInfo.add(pnlSearch);

		lblSearch = new JLabel("Lọc theo:");
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setBounds(285, 10, 100, 20);
		pnlSearch.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên nhân viên");
		cboSearch.addItem("Số điện thoại");
		cboSearch.addItem("Chức vụ");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		cboSearch.setToolTipText("Chọn loại thông tin cần lọc");
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(385, 11, 200, 20);
		pnlSearch.add(cboSearch);

		btnSearch = new MyButton(110, 35, "Tìm kiếm", gra, searchIcon.getImage(), 35, 19, 14, 5);
		btnSearch.setBounds(956, 5, 110, 35);
		btnSearch.setToolTipText("Tìm kiếm thông tin nhân viên theo yêu cần đã chọn");
		pnlSearch.add(btnSearch);

		JLabel lblKeyWord = new JLabel("Từ khóa:");
		lblKeyWord.setForeground(Color.WHITE);
		lblKeyWord.setBounds(641, 10, 73, 20);
		pnlSearch.add(lblKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setBounds(707, 10, 190, 20);
		txtKeyWord.setToolTipText("Nhập từ khóa cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		pnlSearch.add(txtKeyWord);

		cboSearchPosition = new JComboBox<String>();
		cboSearchPosition.addItem("Nhân viên");
		cboSearchPosition.addItem("Chủ quán");
		CustomUI.getInstance().setCustomComboBox(cboSearchPosition);
		txtBFieldSearchPosition = CustomUI.getInstance().setCustomCBoxField(cboSearchPosition);
		cboSearchPosition.setToolTipText("Chọn chức vụ muốn lọc");
		cboSearchPosition.setVisible(false);
		cboSearchPosition.setBounds(707, 11, 190, 20);
		pnlSearch.add(cboSearchPosition);

		btnRefresh = new MyButton(100, 35, "Làm mới", gra, refreshIcon.getImage(), 31, 19, 10, 5);
		btnRefresh.setBounds(1106, 5, 100, 35);
		btnRefresh.setBackground(Color.CYAN);
		btnRefresh.setToolTipText("Xóa rỗng form thông tin");
		pnlSearch.add(btnRefresh);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, updateIcon.getImage(), 45, 19, 24, 6);
		btnUpdate.setBounds(136, 5, 100, 35);
		btnUpdate.setEnabledCustom(false);
		btnUpdate.setToolTipText("Cập nhật thông tin nhân viên");
		pnlSearch.add(btnUpdate);

		btnAdd = new MyButton(100, 35, "Thêm", gra, addIcon.getImage(), 40, 19, 19, 6);
		btnAdd.setBounds(10, 5, 100, 35);
		btnAdd.setToolTipText("Thêm nhân viên mới từ thông tin đã nhập trên form");
		pnlSearch.add(btnAdd);

		JLabel lbUsername = new JLabel("Tài khoản:");
		lbUsername.setForeground(Color.WHITE);
		lbUsername.setBounds(20, 104, 120, 20);
		pnlInfo.add(lbUsername);

		txtUsername = new JTextField();
		txtUsername.setForeground(Color.WHITE);
		txtUsername.setCaretColor(Color.WHITE);
		txtUsername.setBounds(145, 104, 250, 20);
		txtUsername.setToolTipText("Tên đăng nhập phải có từ 6 đến 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtUsername);
		pnlInfo.add(txtUsername);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		pnlTable.setBounds(8, 234, 1240, 351);
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã nhân viên", "Tên nhân viên", "CMND/CCCD", "Chức vụ", "SDT", "Ngày sinh",
				"Mức lương", "Giới tính", "Trạng thái", "Tài khoản" };
		modelTableStaff = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		tblTableStaff = new JTable(modelTableStaff);
		tblTableStaff.setBackground(new Color(255, 255, 255, 0));
		tblTableStaff.setForeground(new Color(255, 255, 255));
		tblTableStaff.setRowHeight(21);
		tblTableStaff.setFont(new Font("Dialog", Font.PLAIN, 14));
		tblTableStaff.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
		tblTableStaff.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tblTableStaff.getTableHeader().setBackground(new Color(255, 255, 255));

		tblTableStaff.getTableHeader().setBackground(new Color(255, 255, 255));
		JScrollPane scrTable = new JScrollPane(tblTableStaff, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrTable.getViewport().setBackground(Color.WHITE);
		scrTable.setBounds(10, 10, 1220, 330);
		scrTable.setOpaque(false);
		scrTable.getViewport().setOpaque(false);

		pnlTable.add(scrTable);
		pnlMain.add(pnlTable);

		btnSearch.addActionListener(this);
		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnRefresh.addActionListener(this);

		tblTableStaff.addMouseListener(this);
		txtStaffName.addMouseListener(this);
		txtCMND.addMouseListener(this);
		txtPhoneNumber.addMouseListener(this);
		spnSalary.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		txtBFieldSearchPosition.addMouseListener(this);
		txtBFieldPosition.addMouseListener(this);
		cboPosition.addMouseListener(this);
		cboSearch.addMouseListener(this);
		cboSearchPosition.addMouseListener(this);

		txtStaffName.addFocusListener(this);
		txtKeyWord.addFocusListener(this);
		txtPhoneNumber.addFocusListener(this);
		txtCMND.addFocusListener(this);
		cboPosition.addFocusListener(this);
		cboSearch.addFocusListener(this);
		cboSearchPosition.addFocusListener(this);
		dpBirthDay.addFocusListener(this);
		txtUsername.addFocusListener(this);
		((JSpinner.DefaultEditor) spnSalary.getEditor()).getTextField().addFocusListener(this);

		cboSearch.addItemListener(this);
		cboSearchPosition.addItemListener(this);

		txtPhoneNumber.addKeyListener(this);
		txtKeyWord.addKeyListener(this);
		txtCMND.addKeyListener(this);
		btnAdd.addKeyListener(this);
		btnBack.addKeyListener(this);
		btnUpdate.addKeyListener(this);
		btnSearch.addKeyListener(this);
		btnRefresh.addKeyListener(this);

		allLoaded();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
			new fDieuHuong(staff).setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			refreshForm();
		} else if (o.equals(btnAdd)) {
			addNewStaff();
		} else if (o.equals(btnBack)) {
			backTofDieuHuong();
		} else if (o.equals(btnUpdate)) {
			updateStaffInfo();
		} else if (o.equals(btnSearch)) {
			searchEventUsingBtnSearch();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
			String searchTypeName = cboSearch.getSelectedItem().toString();
			txtKeyWord.setText("");
			if (searchTypeName.equalsIgnoreCase("Chức vụ")) {
				cboSearchPosition.setVisible(true);
				txtKeyWord.setVisible(false);
			} else {
				if (searchTypeName.equalsIgnoreCase("Tất cả")) {
					txtKeyWord.setEditable(false);
					CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
				} else {
					txtKeyWord.setEditable(true);
					CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
				}
				cboSearchPosition.setVisible(false);
				txtKeyWord.setVisible(true);
			}
			removeSelectionInterval();
			searchEventUsingBtnSearch();
		} else if (o.equals(cboSearchPosition)) {
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
			cboSearchPosition.showPopup();
		} else if (o.equals(txtBFieldPosition)) {
			cboPosition.showPopup();
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
			cboSearch.showPopup();
		} else if (o.equals(txtBFieldSearchPosition)) {
			cboSearchPosition.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
			cboSearchPosition.showPopup();
		} else if (o.equals(txtBFieldPosition)) {
			cboPosition.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
			cboPosition.showPopup();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldSearchPosition)) {
			cboSearchPosition.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
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
		InputEventHandler handler = new InputEventHandler();
		if (o.equals(txtKeyWord)) {
			String searchTypeName = cboSearch.getSelectedItem().toString().trim();
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchEventUsingBtnSearch();
			}
			if (searchTypeName.equalsIgnoreCase("Số điện thoại")) {
				handler.enterOnlyNumbers(key, txtKeyWord, 10);
			}
		} else if (o.equals(txtPhoneNumber)) {
			handler.enterOnlyNumbers(key, txtPhoneNumber, 10);
		} else if (o.equals(txtCMND)) {
			handler.enterOnlyNumbers(key, txtCMND, 12);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

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
		loadStaffList(NhanVienDAO.getInstance().getStaffList());
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
			TaiKhoan account = TaiKhoanDAO.getInstance().getAccountByUsername(username);
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
		String lastStaffId = NhanVienDAO.getInstance().getLastStaffID();
		String idStr = "NV";
		int oldNumberStaffID = 0;
		if (lastStaffId.equalsIgnoreCase("") == false || lastStaffId != null) {
			oldNumberStaffID = Integer.parseInt(lastStaffId.replace(idStr, ""));
		}

		int newStaffID = ++oldNumberStaffID;
		String newStaffIdStr = idStr + newStaffID;
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
		String position = cboPosition.getSelectedItem().toString().trim();
		Double salary = Double.parseDouble(spnSalary.getValue().toString());
		String phoneNumber = txtPhoneNumber.getText().trim();
		String cmnd = txtCMND.getText().trim();
		Date birthDay = dpBirthDay.getValueSqlDate();
		boolean gender = radMale.isSelected() ? false : true;
		String username = txtUsername.getText().trim();
		TaiKhoan account = new TaiKhoan(username);
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
	 * @param staffList {@code ArrayList <NhanVien>}: danh sách nhân viên
	 */
	private void loadStaffList(ArrayList<NhanVien> staffList) {
		modelTableStaff.getDataVector().removeAllElements();
		modelTableStaff.fireTableDataChanged();
		int i = 1;
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
		columnModel.getColumn(3).setPreferredWidth(90);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(5).setPreferredWidth(100);
		columnModel.getColumn(6).setPreferredWidth(100);
		columnModel.getColumn(7).setPreferredWidth(100);
		columnModel.getColumn(8).setPreferredWidth(80);
		columnModel.getColumn(9).setPreferredWidth(130);
		columnModel.getColumn(10).setPreferredWidth(160);

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
		ArrayList<NhanVien> staffList = null;
		String keyword = "";
		if (searchTypeName.equalsIgnoreCase("Tất cả")) {
			staffList = NhanVienDAO.getInstance().getStaffList();
		} else if (searchTypeName.equalsIgnoreCase("Tên nhân viên")) {
			keyword = txtKeyWord.getText().trim();
			staffList = NhanVienDAO.getInstance().getStaffListByStaffName(keyword);
		} else if (searchTypeName.equalsIgnoreCase("Số điện thoại")) {
			keyword = txtKeyWord.getText().trim().replace("-", "");
			if (keyword.matches("^[\\d]{0,10}$")) {
				staffList = NhanVienDAO.getInstance().getStaffListByPhoneNumber(keyword);
			} else {
				String message = "Sổ điện phải phải là số, không được quá 10 số";
				showMessage(txtKeyWord, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			}
		} else if (searchTypeName.equalsIgnoreCase("Chức vụ")) {
			keyword = cboSearchPosition.getSelectedItem().toString().trim();
			staffList = NhanVienDAO.getInstance().getStaffListByPosition(keyword);
		}
		loadStaffList(staffList);
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
			Boolean result = NhanVienDAO.getInstance().insertStaff(staff);
			String name = "nhân viên";
			if (result) {
				message = "Thêm " + name + " mới thành công";
				txtStaffID.setText(staff.getMaNhanVien());
				int stt = tblTableStaff.getRowCount();
				addRow(stt, staff);
				int lastIndex = tblTableStaff.getRowCount() - 1;
				tblTableStaff.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
				tblTableStaff.scrollRectToVisible(tblTableStaff.getCellRect(lastIndex, lastIndex, true));
				txtUsername.setEditable(false);
				btnAdd.setEnabledCustom(false);
				btnUpdate.setEnabledCustom(true);
			} else {
				message = "Thêm " + name + " thất bại";
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
			String staffName = NhanVienDAO.getInstance().getStaffNameById(staff.getMaNhanVien());
			String message = "";
			int selectedRow = tblTableStaff.getSelectedRow();
			String name = "nhân viên";
			if (selectedRow == -1) {
				message = "Hãy chọn " + name + " mà bạn cần cập nhật thông tin";
				JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				message = "Xác nhận cập nhật thông tin " + name + " " + staffName;
				int choose = JOptionPane.showConfirmDialog(this, message, "Xác nhận cập nhật thông tin " + name + "",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (choose == JOptionPane.OK_OPTION) {
					Boolean result = NhanVienDAO.getInstance().updateInfoStaff(staff);
					if (result) {
						message = "Cập nhật thông tin " + name + " thành công";
						updateRow(selectedRow, staff);
						txtUsername.setEditable(false);
						btnAdd.setEnabledCustom(false);
						btnUpdate.setEnabledCustom(true);
						tblTableStaff.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
						tblTableStaff.scrollRectToVisible(tblTableStaff.getCellRect(selectedRow, selectedRow, true));
					} else {
						message = "Cập nhật thông tin " + name + " thất bại";
					}
					JOptionPane.showMessageDialog(this, message);
				}
			}
		}
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