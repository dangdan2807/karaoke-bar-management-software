package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import DAO.ConvertTime;
import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import Event_Handlers.InputEventHandler;
import UI.fDieuHuong;
import UI.fQuanTri;
import entity.KhachHang;
import entity.NhanVien;

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
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch;
	private kDatePicker dpBirthDay;
	private JComboBox<String> cboSearch, cboSearchGender;
	private JRadioButton radMale, radFemale;

	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon addIcon = CustomUI.ADD_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon updateIcon = CustomUI.UPDATE_ICON;

	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;

	public PnKhachHang(NhanVien staff) {
		this.staffLogin = staff;
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
		pnlInfo.setBounds(0, 60, 1238, 140);
		pnlMain.add(pnlInfo);

		dpBirthDay = new kDatePicker(250, 20);
		dpBirthDay.setBackgroundColor(new Color(255, 255, 255, 50));
		dpBirthDay.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dpBirthDay.setForegroundCustom(Color.white);
		dpBirthDay.setFontCustom(new Font("Dialog", Font.PLAIN, 14));
		dpBirthDay.setOpaqueCustom(false);
		pnlInfo.add(dpBirthDay);
		dpBirthDay.setBounds(965, 45, 250, 20);

		txtCMND = new JTextField();
		txtCMND.setBounds(965, 15, 250, 20);
		txtCMND.setToolTipText("Nhập CMND gồm có 9 số hoặc CCCD gồm có 12 số");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		pnlInfo.add(txtCMND);

		lblCMND = new JLabel("CMND/CCCD:");
		CustomUI.getInstance().setCustomLabel(lblCMND);
		lblCMND.setBounds(845, 15, 105, 20);
		pnlInfo.add(lblCMND);

		lblBirthDay = new JLabel("Ngày sinh:");
		CustomUI.getInstance().setCustomLabel(lblBirthDay);
		lblBirthDay.setBounds(845, 45, 105, 20);
		pnlInfo.add(lblBirthDay);

		lblGender = new JLabel("Giới tính:");
		CustomUI.getInstance().setCustomLabel(lblGender);
		lblGender.setBounds(435, 15, 105, 20);
		pnlInfo.add(lblGender);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(555, 45, 250, 20);
		txtPhoneNumber.setToolTipText("Nhập số điện thoại của bạn gồm 10 số và bắt đầu bằng 03, 05, 07, 08, 09");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		pnlInfo.add(txtPhoneNumber);

		radMale = new JRadioButton("Nam");
		CustomUI.getInstance().setCustomRadioButton(radMale);
		radMale.setBounds(551, 15, 115, 20);
		radMale.setSelected(true);
		pnlInfo.add(radMale);

		radFemale = new JRadioButton("Nữ");
		CustomUI.getInstance().setCustomRadioButton(radFemale);
		radFemale.setBounds(671, 15, 115, 20);
		pnlInfo.add(radFemale);

		ButtonGroup groupGender = new ButtonGroup();
		groupGender.add(radMale);
		groupGender.add(radFemale);

		lblPhone = new JLabel("Số điện thoại:");
		CustomUI.getInstance().setCustomLabel(lblPhone);
		lblPhone.setBounds(435, 45, 115, 16);
		pnlInfo.add(lblPhone);

		txtCustomerName = new JTextField();
		txtCustomerName.setBounds(145, 45, 250, 20);
		txtCustomerName.setToolTipText("Nhập tên của khách hàng, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCustomerName);
		pnlInfo.add(txtCustomerName);

		txtCustomerID = new JTextField();
		txtCustomerID.setBounds(145, 15, 250, 20);
		txtCustomerID.setToolTipText("Mã khách hàng");
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerID);
		pnlInfo.add(txtCustomerID);

		lblCustomerID = new JLabel("Mã khách hàng: ");
		CustomUI.getInstance().setCustomLabel(lblCustomerID);
		lblCustomerID.setBounds(20, 15, 120, 20);
		pnlInfo.add(lblCustomerID);

		lblCustomerName = new JLabel("Tên khách hàng:");
		CustomUI.getInstance().setCustomLabel(lblCustomerName);
		lblCustomerName.setBounds(20, 45, 120, 20);
		pnlInfo.add(lblCustomerName);

		btnAdd = new MyButton(100, 35, "Thêm", gra, addIcon.getImage(), 39, 19, 18, 6);
		btnAdd.setToolTipText("Thêm khách hàng mới sau khi đã điền đủ thông tin");
		btnAdd.setBounds(20, 93, 100, 35);
		pnlInfo.add(btnAdd);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, updateIcon.getImage(), 43, 19, 22, 6);
		btnUpdate.setToolTipText("Sửa thông tin khách hàng");
		btnUpdate.setBounds(150, 93, 100, 35);
		btnUpdate.setEnabledCustom(false);
		pnlInfo.add(btnUpdate);

		btnRefresh = new MyButton(100, 35, "Làm mới", gra, refreshIcon.getImage(), 27, 19, 6, 5);
		btnRefresh.setToolTipText("Làm mới form");
		btnRefresh.setBounds(1118, 93, 100, 35);
		pnlInfo.add(btnRefresh);

		JPanel pnlSearch = new JPanel();
		pnlSearch.setBounds(286, 83, 822, 53);
		pnlInfo.add(pnlSearch);
		pnlSearch.setOpaque(false);
		pnlSearch.setLayout(null);
		pnlInfo.add(pnlSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(30, 18, 100, 20);
		pnlSearch.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên khách hàng");
		cboSearch.addItem("Số điện thoại");
		cboSearch.addItem("Giới tính");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(140, 18, 160, 20);
		pnlSearch.add(cboSearch);

		btnSearch = new MyButton(100, 35, "Tìm kiếm", gra, searchIcon.getImage(), 26, 19, 5, 5);
		btnSearch.setToolTipText("Tìm kiếm thông tin khách hàng theo từ khóa");
		btnSearch.setBounds(702, 10, 100, 35);
		pnlSearch.add(btnSearch);

		JLabel lblKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lblKeyWord);
		lblKeyWord.setBounds(364, 18, 76, 20);
		pnlSearch.add(lblKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setBounds(440, 18, 200, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		txtKeyWord.setEditable(false);
		pnlSearch.add(txtKeyWord);

		cboSearchGender = new JComboBox<String>();
		cboSearchGender.addItem("Nam");
		cboSearchGender.addItem("Nữ");
		CustomUI.getInstance().setCustomComboBox(cboSearchGender);
		txtBFieldSearchGender = CustomUI.getInstance().setCustomCBoxField(cboSearchGender);
		cboSearchGender.setBounds(440, 18, 200, 20);
		cboSearchGender.setVisible(false);
		pnlSearch.add(cboSearchGender);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		pnlTable.setBounds(8, 201, 1240, 384);
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
		JScrollPane scrTable = CustomUI.getInstance().setCustomScrollPane(tblTableCustomer);
		scrTable.setBounds(10, 10, 1220, 350);

		pnlTable.add(scrTable);

		pnlMain.add(pnlTable);
		allLoaded();

		btnSearch.addActionListener(this);
		btnAdd.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnUpdate.addActionListener(this);

		btnSearch.addMouseListener(this);
		tblTableCustomer.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		txtBFieldSearchGender.addMouseListener(this);

		txtCMND.addFocusListener(this);
		txtCustomerName.addFocusListener(this);
		txtKeyWord.addFocusListener(this);
		txtPhoneNumber.addFocusListener(this);
		txtBFieldSearch.addFocusListener(this);
		txtBFieldSearchGender.addFocusListener(this);
		
		txtCMND.addKeyListener(this);
		txtKeyWord.addKeyListener(this);
		txtPhoneNumber.addKeyListener(this);

		cboSearch.addItemListener(this);
		cboSearchGender.addItemListener(this);

	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
			new fQuanTri(staff).setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			fDieuHuong winNavigation = new fDieuHuong(staffLogin);
			winNavigation.setVisible(true);
			this.setVisible(false);
		} else if (o.equals(btnAdd)) {

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

		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
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
			txtCustomerID.setText(tblTableCustomer.getValueAt(selectedRow, 1).toString().trim());
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
		loadCustomerList(KhachHangDAO.getInstance().getCustomerList());
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
		// phoneNumberStr = phoneNumberStr.substring(0, 4) + "-" +
		// phoneNumberStr.substring(4, 7) + "-"
		// + phoneNumberStr.substring(7, 10);
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
		if (searchTypeName.equalsIgnoreCase("Tất cả")) {
			customerList = KhachHangDAO.getInstance().getCustomerList();
		} else if (searchTypeName.equalsIgnoreCase("Tên khách hàng")) {
			keyword = txtKeyWord.getText().trim();
			customerList = KhachHangDAO.getInstance().getCustomerListByName(keyword);
		} else if (searchTypeName.equalsIgnoreCase("Số điện thoại")) {
			keyword = txtKeyWord.getText().trim().replace("-", "");
			if (keyword.matches("^[\\d]{0,10}$")) {
				customerList = KhachHangDAO.getInstance().getCustomerListByPhoneNumber(keyword);
			} else {
				String message = "Sổ điện phải phải là số, không được quá 10 số";
				showMessage(txtKeyWord, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			}
		} else if (searchTypeName.equalsIgnoreCase("Giới tính")) {
			String genderStr = cboSearchGender.getSelectedItem().toString();
			boolean gender = genderStr.equalsIgnoreCase("Nữ") ? true : false;
			customerList = KhachHangDAO.getInstance().getCustomerListByGender(gender);
		}
		loadCustomerList(customerList);
	}

	/**
	 * Hiển thị danh sách khách hàng
	 * 
	 * @param customerList {@code ArrayList<KhachHang>}: danh sách khách hàng
	 */
	private void loadCustomerList(ArrayList<KhachHang> customerList) {
		modelTable.getDataVector().removeAllElements();
		modelTable.fireTableDataChanged();
		int i = 1;
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
		columnModel.getColumn(1).setPreferredWidth(130);
		columnModel.getColumn(2).setPreferredWidth(250);
		columnModel.getColumn(3).setPreferredWidth(200);
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
	public JButton getBtnBack() {
		return btnBack;
	}
}
