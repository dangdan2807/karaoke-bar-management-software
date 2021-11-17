package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import DAO.*;
import Event_Handlers.InputEventHandler;
import UI.fDieuHuong;
import UI.fQuanTri;
import entity.*;

public class PnHoaDon extends JPanel
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7158582925692848950L;

	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	private JTable tblTableBill, tblTableBillInfo;
	private DefaultTableModel modelTableBill, modelTableBillInfo;
	private MyButton btnSearch, btnRefresh, btnBack;
	private JLabel lblToDate, lblSearch, lblFromDate;
	private kDatePicker dpToDate, dpFromDate;
	private JComboBox<String> cboSearch, cboSearchType;
	private JTextField txtBFieldSearch, txtKeyWord, txtBFieldSearchType;

	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon backIcon = CustomUI.BACK_ICON;

	private NhanVien staffLogin = null;
	private DecimalFormat df = new DecimalFormat("#,###.##");

	public PnHoaDon(NhanVien staff) {
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

		pnlTitle.setBounds(0, 0, 1270, 50);
		pnlTitle.setOpaque(false);
		pnlTitle.setLayout(null);

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 30, 19, 9, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlTitle.add(btnBack);
		pnlMain.add(pnlTitle);

		JLabel lblTitle = new JLabel("QUẢN LÝ HÓA ĐƠN");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lblTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(10, 41, 1238, 110);
		pnlMain.add(pnlInfo);

		dpToDate = new kDatePicker(250, 20);
		dpToDate.setBackgroundColor(new Color(255, 255, 255, 50));
		dpToDate.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dpToDate.setForegroundCustom(Color.white);
		dpToDate.setFontCustom(new Font("Dialog", Font.PLAIN, 14));
		dpToDate.setOpaqueCustom(false);
		dpToDate.setToolTipTextCustom("Ngày kết thúc thống kê");
		dpToDate.setBounds(747, 26, 250, 20);
		pnlInfo.add(dpToDate);

		lblToDate = new JLabel("Đến ngày:");
		CustomUI.getInstance().setCustomLabel(lblToDate);
		lblToDate.setBounds(642, 26, 105, 20);
		pnlInfo.add(lblToDate);

		dpFromDate = new kDatePicker(250, 20);
		dpFromDate.setBackgroundColor(new Color(255, 255, 255, 50));
		dpFromDate.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dpFromDate.setForegroundCustom(Color.white);
		dpFromDate.setFontCustom(new Font("Dialog", Font.PLAIN, 14));
		dpFromDate.setOpaqueCustom(false);
		dpFromDate.setBounds(292, 26, 250, 20);
		dpFromDate.setToolTipTextCustom("Ngày bắt đầu thống kê");
		pnlInfo.add(dpFromDate);

		lblFromDate = new JLabel("Từ ngày:");
		CustomUI.getInstance().setCustomLabel(lblFromDate);
		lblFromDate.setBounds(187, 26, 105, 20);
		pnlInfo.add(lblFromDate);

		btnRefresh = new MyButton(100, 35, "Làm mới", gra, CustomUI.REFRESH_ICON.getImage(), 27, 19, 6, 5);
		btnRefresh.setToolTipText("Làm mới form");
		btnRefresh.setBounds(1116, 70, 100, 35);
		pnlInfo.add(btnRefresh);

		btnSearch = new MyButton(100, 35, "Tìm kiếm", gra, CustomUI.SEARCH_ICON.getImage(), 26, 19, 5, 5);
		btnSearch.setBounds(1116, 20, 100, 35);
		btnSearch.setToolTipText("Tìm kiếm thông tin nhân viên theo từ khóa");
		pnlInfo.add(btnSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(187, 70, 105, 20);
		pnlInfo.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên khách hàng");
		cboSearch.addItem("SĐT khách hàng");
		cboSearch.addItem("Tên nhân viên");
		cboSearch.addItem("Mã hóa đơn");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(290, 70, 250, 20);
		pnlInfo.add(cboSearch);

		JLabel lpKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lpKeyWord);
		lpKeyWord.setBounds(642, 70, 105, 20);
		pnlInfo.add(lpKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setBounds(747, 70, 250, 20);
		txtKeyWord.setForeground(Color.WHITE);
		txtKeyWord.setFont(new Font("Dialog", Font.PLAIN, 14));
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		pnlInfo.add(txtKeyWord);

		cboSearchType = new JComboBox<String>();
		cboSearchType.setBounds(747, 70, 250, 20);
		CustomUI.getInstance().setCustomComboBox(cboSearchType);
		txtBFieldSearchType = CustomUI.getInstance().setCustomCBoxField(cboSearchType);
		cboSearchType.setVisible(false);
		pnlInfo.add(cboSearchType);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		pnlTable.setBounds(8, 151, 1240, 434);
		pnlTable.setOpaque(false);

		String[] colsTableBill = { "STT", "Mã hóa đơn", "Ngày đặt ", "Ngày trả", "Mã phòng", "Tên khách hàng",
				"Tên Nhân viên", "Tổng tiền" };
		modelTableBill = new DefaultTableModel(colsTableBill, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		tblTableBill = new JTable(modelTableBill);
		tblTableBill.setRowHeight(21);
		CustomUI.getInstance().setCustomTable(tblTableBill);
		JScrollPane scrTableBill = CustomUI.getInstance().setCustomScrollPane(tblTableBill);
		scrTableBill.setBounds(10, 35, 1220, 182);
		pnlTable.add(scrTableBill);

		String[] colsBillInfo = { "STT", "Tên dịch vụ ", "Số lượng đặt", "Giá tiền", "Thành tiền" };
		modelTableBillInfo = new DefaultTableModel(colsBillInfo, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		tblTableBillInfo = new JTable(modelTableBillInfo);
		tblTableBillInfo.setRowHeight(21);
		CustomUI.getInstance().setCustomTable(tblTableBillInfo);
		JScrollPane scrTableBillInfo = CustomUI.getInstance().setCustomScrollPane(tblTableBillInfo);
		scrTableBillInfo.setBounds(10, 252, 1220, 175);
		pnlTable.add(scrTableBillInfo);
		pnlMain.add(pnlTable);

		btnRefresh.addActionListener(this);
		btnSearch.addActionListener(this);

		txtBFieldSearch.addMouseListener(this);
		txtBFieldSearchType.addMouseListener(this);
		tblTableBill.addMouseListener(this);

		txtKeyWord.addFocusListener(this);
		txtKeyWord.addKeyListener(this);

		cboSearch.addItemListener(this);
		allLoaded();
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("huynhtuananh");
			new fQuanTri(staff).setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			fDieuHuong winNavigation = new fDieuHuong(staffLogin);
			this.setVisible(false);
			winNavigation.setVisible(true);
		} else if (o.equals(btnRefresh)) {
			txtKeyWord.setText("");
			dpFromDate.setValueToDay();
			dpToDate.setValueToDay();
			cboSearch.setSelectedIndex(0);
			removeSelectionInterval();
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
			if (searchTypeName.equalsIgnoreCase("Tất cả")) {
				CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
			} else {
				CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		} else if (o.equals(txtBFieldSearchType)) {
			cboSearchType.showPopup();
		} else if (o.equals(tblTableBill)) {
			int rowSelected = tblTableBill.getSelectedRow();
			String billId = tblTableBill.getValueAt(rowSelected, 1).toString().trim();
			ArrayList<CTDichVu> serviceDetailList = CTDichVuDAO.getInstance().getServiceDetailListByBillId(billId);
			loadServiceDetailList(serviceDetailList);
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
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
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
		String searchTypeName = cboSearch.getSelectedItem().toString();
		if (o.equals(txtKeyWord)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchEventUsingBtnSearch();
			}
			if (searchTypeName.equalsIgnoreCase("SĐT khách hàng")) {
				handler.enterOnlyNumbers(key, txtKeyWord, 10);
			} else {
				handler.characterInputLimit(key, txtKeyWord, 100);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyWord)) {
			String searchTypeName = cboSearch.getSelectedItem().toString();
			int length = txtKeyWord.getText().trim().length();
			if (searchTypeName.equalsIgnoreCase("SĐT khách hàng") && length == 10) {
				txtKeyWord.setEditable(true);
			} else if (!searchTypeName.equalsIgnoreCase("Tất cả") && length == 100) {
				txtKeyWord.setEditable(true);
			}
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		}
	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	public void allLoaded() {
		reSizeColumnTableBill();
		reSizeColumnTableBillInfo();
		Date startDate = dpFromDate.getValueSqlDate();
		Date endDate = dpToDate.getNextDay();
		String staffId = staffLogin.getMaNhanVien();
		loadBillList(HoaDonDAO.getInstance().getBillListByDate(startDate, endDate, staffId));
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
		Date toDay = dpToDate.getValueToDay();
		Date fromDate = dpFromDate.getValueSqlDate();
		Date toDate = dpToDate.getValueSqlDate();
		String message = "";
		boolean flag = true;
		if (fromDate.after(toDay)) {
			message = "Ngày bắt đầu thống kê phải nhỏ hơn hoặc bằng ngày hiện tại";
			flag = false;
		} else if (fromDate.after(toDate)) {
			message = "Ngày bắt đầu thống kê phải nhỏ hơn hoặc bằng ngày kết thúc thống kê";
			flag = false;
		} else if (toDate.before(fromDate)) {
			message = "Ngày kết thúc thống kê phải lớn hơn hoặc bằng ngày bắt đầu thống kê";
			flag = false;
		}
		if (!flag) {
			JOptionPane.showMessageDialog(null, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
		}
		return flag;
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
	 * Thêm dòng vào danh sách hóa đơn đang hiển thị
	 * 
	 * @param stt  {@code int}: số thứ tự
	 * @param bill {@code HoaDon}: hóa đơn cần được thêm
	 */
	private void addRowTableBill(int stt, HoaDon bill) {
		String billId = bill.getMaHoaDon();
		String sttStr = df.format(stt);
		String format = "dd-MM-yyyy HH:mm:ss";
		String startTimeStr = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(), format);
		String endTimeStr = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioTra(), format);

		KhachHang customer = KhachHangDAO.getInstance().getCustomerByBillId(billId);
		Double totalPrice = HoaDonDAO.getInstance().getTotalPriceBill(billId);
		NhanVien staff = NhanVienDAO.getInstance().getStaffByBillId(billId);
		Phong room = PhongDAO.getInstance().getRoomByBillId(billId);

		modelTableBill.addRow(new Object[] { sttStr, addSpaceToString(String.valueOf(billId)),
				addSpaceToString(startTimeStr), addSpaceToString(endTimeStr), addSpaceToString(room.getMaPhong()),
				addSpaceToString(customer.getHoTen()), addSpaceToString(staff.getHoTen()),
				addSpaceToString(df.format(totalPrice)) });
		modelTableBill.fireTableDataChanged();
	}

	/**
	 * Thêm dòng vào danh sách chi tiết dịch vụ đang hiển thị
	 * 
	 * @param stt           {@code int}: số thứ tự
	 * @param serviceDetail {@code HoaDon}: chi tiết dịch vụ cần được thêm
	 */
	private void addRowTableBillInfo(int stt, CTDichVu serviceDetail) {
		DichVu service = serviceDetail.getDichVu();
		String sttStr = df.format(stt);
		String quantityStr = df.format(serviceDetail.getSoLuongDat());
		String priceStr = df.format(serviceDetail.getDonGia());
		String totalPrice = df.format(serviceDetail.tinhTienDichVu());
		modelTableBillInfo.addRow(new Object[] { sttStr, addSpaceToString(service.getTenDichVu()),
				addSpaceToString(quantityStr), addSpaceToString(priceStr), addSpaceToString(totalPrice) });
		modelTableBillInfo.fireTableDataChanged();
	}

	/**
	 * Hiển thị danh sách hóa đơn
	 * 
	 * @param billList {@code ArrayList <HoaDon>}: danh sách hóa đơn
	 */
	private void loadBillList(ArrayList<HoaDon> billList) {
		modelTableBill.getDataVector().removeAllElements();
		modelTableBill.fireTableDataChanged();
		modelTableBillInfo.getDataVector().removeAllElements();
		modelTableBillInfo.fireTableDataChanged();
		int i = 1;
		for (HoaDon item : billList) {
			addRowTableBill(i++, item);
		}
	}

	/**
	 * Hiển thị chi tiết dịch vụ
	 * 
	 * @param serviceDetailList {@code ArrayList <CTDichVu>}: danh sách chi tiết
	 *                          dịch vụ
	 */
	private void loadServiceDetailList(ArrayList<CTDichVu> serviceDetailList) {
		modelTableBillInfo.getDataVector().removeAllElements();
		modelTableBillInfo.fireTableDataChanged();
		int i = 1;
		for (CTDichVu item : serviceDetailList) {
			addRowTableBillInfo(i++, item);
		}
	}

	/**
	 * Thay đổi kích thước cột danh sách hóa đơn
	 */
	private void reSizeColumnTableBill() {
		tblTableBill.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModelTableBill = tblTableBill.getColumnModel();
		columnModelTableBill.getColumn(0).setPreferredWidth(50);
		columnModelTableBill.getColumn(1).setPreferredWidth(140);
		columnModelTableBill.getColumn(2).setPreferredWidth(160);
		columnModelTableBill.getColumn(3).setPreferredWidth(160);
		columnModelTableBill.getColumn(4).setPreferredWidth(100);
		columnModelTableBill.getColumn(5).setPreferredWidth(210);
		columnModelTableBill.getColumn(6).setPreferredWidth(210);
		columnModelTableBill.getColumn(7).setPreferredWidth(170);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		columnModelTableBill.getColumn(0).setCellRenderer(centerRenderer);
		columnModelTableBill.getColumn(7).setCellRenderer(rightRenderer);
	}

	/**
	 * Thay đổi kích thước cột danh sách chi tiết hóa đơn
	 */
	private void reSizeColumnTableBillInfo() {
		tblTableBillInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModelTableBillInfo = tblTableBillInfo.getColumnModel();
		columnModelTableBillInfo.getColumn(0).setPreferredWidth(70);
		columnModelTableBillInfo.getColumn(1).setPreferredWidth(470);
		columnModelTableBillInfo.getColumn(2).setPreferredWidth(220);
		columnModelTableBillInfo.getColumn(3).setPreferredWidth(220);
		columnModelTableBillInfo.getColumn(4).setPreferredWidth(220);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		columnModelTableBillInfo.getColumn(0).setCellRenderer(centerRenderer);
		columnModelTableBillInfo.getColumn(2).setCellRenderer(rightRenderer);
		columnModelTableBillInfo.getColumn(3).setCellRenderer(rightRenderer);
		columnModelTableBillInfo.getColumn(4).setCellRenderer(rightRenderer);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tblTableBill.getSelectedRow();
		tblTableBill.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
		selectedRow = tblTableBillInfo.getSelectedRow();
		tblTableBillInfo.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}

	/**
	 * tìm kiếm loại phòng dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		Date fromDate = dpFromDate.getValueSqlDate();
		Date toDate = dpToDate.getNextDay();
		ArrayList<HoaDon> billList = new ArrayList<>();
		String keyword = txtKeyWord.getText().trim();
		String staffId = staffLogin.getMaNhanVien();
		if (validData()) {
			if (searchTypeName.equalsIgnoreCase("Tất cả")) {
				billList = HoaDonDAO.getInstance().getBillListByDate(fromDate, toDate, staffId);
			} else if (searchTypeName.equalsIgnoreCase("SĐT khách hàng")) {
				billList = HoaDonDAO.getInstance().getBillListByDateAndCustomerPhoneNumber(keyword, fromDate, toDate, staffId);
			} else if (searchTypeName.equalsIgnoreCase("Tên khách hàng")) {
				billList = HoaDonDAO.getInstance().getBillListByDateAndCustomerName(keyword, fromDate, toDate, staffId);
			} else if (searchTypeName.equalsIgnoreCase("Tên nhân viên")) {
				billList = HoaDonDAO.getInstance().getBillListByDateAndStaffName(keyword, fromDate, toDate, staffId);
			} else if (searchTypeName.equalsIgnoreCase("Mã hóa đơn")) {
				billList = HoaDonDAO.getInstance().getBillListByDateAndBillId(keyword, fromDate, toDate, staffId);
			}
			loadBillList(billList);
		}
	}

	/**
	 * Lấy nút quay lại
	 */
	public JButton getBtnBack() {
		return btnBack;
	}
}
