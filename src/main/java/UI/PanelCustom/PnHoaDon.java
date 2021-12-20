package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import DAO.*;
import Event_Handlers.ConvertTime;
import Event_Handlers.ExportBill;
import Event_Handlers.InputEventHandler;
import UI.fDieuHuong;
import entity.*;

/**
 * Giao diện quản lý hóa đơn của phần mềm
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 01/11/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm phân trang cho phần mềm
 */
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
	private MyButton btnSearch, btnExportBill, btnBack;
	private JLabel lblToDate, lblSearch, lblFromDate;
	private kDatePicker dpToDate, dpFromDate;
	private JComboBox<String> cboSearch, cboSearchType;
	private JTextField txtBFieldSearch, txtKeyWord, txtBFieldSearchType;
	private MyButton btnNextToRight, btnNextToLast, btnNextToLeft, btnNextToFirst;
	private PnTextFiledPaging txtPaging;

	private ImageIcon bg = new ImageIcon(new ImageIcon(PnHoaDon.class.getResource(
			CustomUI.BACKGROUND)).getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon nextIconRight = new ImageIcon(new ImageIcon(PnHoaDon.class.getResource(
			CustomUI.NEXT_RIGHT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextRightIcon = new ImageIcon(new ImageIcon(PnHoaDon.class.getResource(
			CustomUI.DOUBLE_NEXT_RIGHT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon nextLeftIcon = new ImageIcon(new ImageIcon(PnHoaDon.class.getResource(
			CustomUI.NEXT_LEFT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextLeftIcon = new ImageIcon(new ImageIcon(PnHoaDon.class.getResource(
			CustomUI.DOUBLE_NEXT_LEFT_ICON)).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon backIcon = new ImageIcon(PnHoaDon.class.getResource(CustomUI.BACK_ICON));
	private ImageIcon billIcon = new ImageIcon(PnHoaDon.class.getResource(CustomUI.BILL_ICON));
	private ImageIcon searchIcon = new ImageIcon(PnDichVu.class.getResource(CustomUI.SEARCH_ICON));

	private NhanVien staffLogin = null;
	private DecimalFormat df = new DecimalFormat("#,###.##");
	private int lineNumberDisplayed = 6;
	private HoaDonDAO billDAO = HoaDonDAO.getInstance();
	private NhanVienDAO staffDAO = NhanVienDAO.getInstance();
	private KhachHangDAO customerDAO = KhachHangDAO.getInstance();
	private PhongDAO roomDAO = PhongDAO.getInstance();
	private CTDichVuDAO serviceDetailDAO = CTDichVuDAO.getInstance();

	/**
	 * Khởi tạo giao diện quản lý hóa đơn
	 * 
	 * @param staff {@code NhanVien} nhân viên đăng nhập
	 */
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
		pnlInfo.setBounds(10, 41, 1238, 100);
		pnlMain.add(pnlInfo);

		dpToDate = new kDatePicker(250, 20);
		dpToDate.setBackgroundColor(new Color(255, 255, 255, 50));
		dpToDate.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dpToDate.setTextColor(Color.white);
		dpToDate.setTextFont(new Font("Dialog", Font.PLAIN, 14));
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
		dpFromDate.setTextColor(Color.white);
		dpFromDate.setTextFont(new Font("Dialog", Font.PLAIN, 14));
		dpFromDate.setOpaqueCustom(false);
		dpFromDate.setBounds(292, 26, 250, 20);
		dpFromDate.setToolTipTextCustom("Ngày bắt đầu thống kê");
		pnlInfo.add(dpFromDate);

		lblFromDate = new JLabel("Từ ngày:");
		CustomUI.getInstance().setCustomLabel(lblFromDate);
		lblFromDate.setBounds(187, 26, 105, 20);
		pnlInfo.add(lblFromDate);

		btnExportBill = new MyButton(150, 35, "Xuất hóa đơn", gra, billIcon.getImage(), 40, 19, 6, 5);
		btnExportBill.setToolTipText("Xuất hóa đơn");
		btnExportBill.setBounds(1065, 60, 150, 35);
		pnlInfo.add(btnExportBill);

		btnSearch = new MyButton(150, 35, "Tìm kiếm", gra, searchIcon.getImage(), 50, 19, 5, 5);
		btnSearch.setBounds(1065, 20, 150, 35);
		btnSearch.setToolTipText("Tìm kiếm thông tin nhân viên theo từ khóa");
		pnlInfo.add(btnSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(187, 60, 105, 20);
		pnlInfo.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên khách hàng");
		cboSearch.addItem("SĐT khách hàng");
		cboSearch.addItem("Tên nhân viên");
		cboSearch.addItem("Mã hóa đơn");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(290, 60, 250, 20);
		pnlInfo.add(cboSearch);

		JLabel lpKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lpKeyWord);
		lpKeyWord.setBounds(642, 60, 105, 20);
		pnlInfo.add(lpKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setBounds(747, 60, 250, 20);
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
		pnlTable.setBounds(8, 130, 1240, 440);
		pnlTable.setOpaque(false);

		JPanel pnlTableBill = new JPanel(null);
		pnlTableBill.setLayout(null);
		CustomUI.getInstance().setBorderTitlePanelTable(pnlTableBill, "Danh sách hóa đơn");
		pnlTableBill.setOpaque(false);
		pnlTableBill.setBounds(10, 0, 1220, 182);
		pnlTable.add(pnlTableBill);

		String[] colsTableBill = { "STT", "Mã hóa đơn", "Ngày đặt ", "Ngày trả", "Mã phòng", "Tên khách hàng",
				"Tên Nhân viên", "Tổng tiền" };
		modelTableBill = new DefaultTableModel(colsTableBill, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		tblTableBill = new JTable(modelTableBill);
		tblTableBill.setLocation(620, 0);
		tblTableBill.setRowHeight(21);
		CustomUI.getInstance().setCustomTable(tblTableBill);
		JScrollPane scrTableBill = CustomUI.getInstance().setCustomScrollPaneNotScroll(tblTableBill);
		scrTableBill.setBounds(10, 20, 1200, 152);
		pnlTableBill.add(scrTableBill);

		btnNextToRight = new MyButton(70, 35, "", gra, nextIconRight.getImage(), 0, 0, 14, -8);
		btnNextToRight.setBounds(690, 185, 70, 35);
		pnlTable.add(btnNextToRight);

		btnNextToLast = new MyButton(70, 35, "", gra, doubleNextRightIcon.getImage(), 0, 0, 14, -8);
		btnNextToLast.setBounds(770, 185, 70, 35);
		pnlTable.add(btnNextToLast);

		btnNextToLeft = new MyButton(70, 35, "", gra, nextLeftIcon.getImage(), 0, 0, 14, -8);
		btnNextToLeft.setBounds(510, 185, 70, 35);
		pnlTable.add(btnNextToLeft);

		btnNextToFirst = new MyButton(70, 35, "", gra, doubleNextLeftIcon.getImage(), 0, 0, 14, -8);
		btnNextToFirst.setBounds(430, 185, 70, 35);
		pnlTable.add(btnNextToFirst);

		txtPaging = new PnTextFiledPaging(90, 35);
		txtPaging.setBounds(590, 185, 91, 36);
		txtPaging.setTextColor(Color.WHITE);
		pnlTable.add(txtPaging);

		JPanel pnlTableBillInfo = new JPanel(null);
		pnlTableBillInfo.setLayout(null);
		CustomUI.getInstance().setBorderTitlePanelTable(pnlTableBillInfo, "Chi tiết hóa đơn");
		pnlTableBillInfo.setOpaque(false);
		pnlTableBillInfo.setBounds(10, 220, 1220, 223);
		pnlTable.add(pnlTableBillInfo);

		String[] colsBillInfo = { "STT", "Tên dịch vụ ", "Số lượng đặt", "Giá tiền", "Thành tiền" };
		modelTableBillInfo = new DefaultTableModel(colsBillInfo, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		tblTableBillInfo = new JTable(modelTableBillInfo);
		tblTableBillInfo.setLocation(620, 0);
		tblTableBillInfo.setRowHeight(21);
		CustomUI.getInstance().setCustomTable(tblTableBillInfo);
		JScrollPane scrTableBillInfo = CustomUI.getInstance().setCustomScrollPane(tblTableBillInfo);
		scrTableBillInfo.setBounds(10, 20, 1200, 193);
		pnlTableBillInfo.add(scrTableBillInfo);
		pnlMain.add(pnlTable);

		btnExportBill.addActionListener(this);
		btnSearch.addActionListener(this);
		btnNextToLast.addActionListener(this);
		btnNextToLeft.addActionListener(this);
		btnNextToRight.addActionListener(this);
		btnNextToFirst.addActionListener(this);

		txtBFieldSearch.addMouseListener(this);
		txtBFieldSearchType.addMouseListener(this);
		tblTableBill.addMouseListener(this);

		txtKeyWord.addFocusListener(this);
		txtKeyWord.addKeyListener(this);

		cboSearch.addItemListener(this);
		allLoaded();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			fDieuHuong winNavigation = new fDieuHuong(staffLogin);
			this.setVisible(false);
			winNavigation.setVisible(true);
		} else if (o.equals(btnExportBill)) {
			int selectedRow = tblTableBill.getSelectedRow();
			if (selectedRow != -1) {
				String billId = tblTableBill.getValueAt(selectedRow, 1).toString().trim();
				HoaDon bill = billDAO.getBillByBillId(billId);
				if (bill != null) {
					String message = "Bạn có muốn xuất hóa đơn dạng nào ?";
					String title = "Xác nhận đăng xuất tài khoản";
					Object[] options = { "PDF", "Excel", "Hủy" };
					int selected = JOptionPane.showOptionDialog(this, message, title, JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
					if (selected != 2) {
						NhanVien staff = staffDAO.getStaffByBillId(billId);
						KhachHang customer = customerDAO.getCustomerByBillId(billId);
						Phong room = roomDAO.getRoomByBillId(billId);
						ArrayList<CTDichVu> serviceDetailList = new ArrayList<>();
						serviceDetailList = serviceDetailDAO.getServiceDetailListByBillId(billId);
						if (staff == null)
							staff = new NhanVien();
						if (room == null)
							room = new Phong();
						if (customer == null)
							customer = new KhachHang();
						bill.setPhong(room);
						bill.setNhanVien(staff);
						bill.setKhachHang(customer);
						bill.setDsCTDichVu(serviceDetailList);
						String pathExportBill = CustomUI.PATH_EXPORT_BILL;
						boolean isSuccess = false;
						if (selected == 1)
							isSuccess = ExportBill.getInstance().exportBillToExcel(bill, pathExportBill);
						else if (selected == 0)
							isSuccess = ExportBill.getInstance().exportBillToPdf(bill, pathExportBill);
						if (isSuccess)
							JOptionPane.showMessageDialog(this, "Xuất hóa đơn thành công");
						else
							JOptionPane.showMessageDialog(this, "Xuất hóa đơn thất bại");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn để xuất", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (o.equals(btnSearch)) {
			searchEventUsingBtnSearch();
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
			ArrayList<CTDichVu> serviceDetailList = serviceDetailDAO.getServiceDetailListByBillId(billId);
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

	}

	@Override
	public void keyReleased(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		InputEventHandler handler = new InputEventHandler();
		String searchTypeName = cboSearch.getSelectedItem().toString();
		if (o.equals(txtKeyWord)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchEventUsingBtnSearch();
			}
			if (searchTypeName.equalsIgnoreCase("SĐT khách hàng")) {
				handler.enterOnlyNumbersAndLimitInput(key, txtKeyWord, 10);
			} else if (searchTypeName.equalsIgnoreCase("Mã hóa đơn")) {
				handler.characterInputLimit(key, txtKeyWord, 15);
			} else {
				handler.characterInputLimit(key, txtKeyWord, 100);
			}
		}
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
		Date endDate = dpToDate.getValueSqlDate();
		String staffId = staffLogin.getMaNhanVien();
		txtPaging.setCurrentPage(1);
		int totalLine = 1;
		ArrayList<HoaDon> billList = new ArrayList<>();
		billList = billDAO.getBillListByDateAndPageNumber(startDate, endDate, staffId, 1,
				lineNumberDisplayed);
		totalLine = billDAO.getTotalLineOfBillList(startDate, endDate, staffId);
		txtPaging.setTotalPage(getLastPage(totalLine));
		loadBillList(billList);
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
		KhachHang customer = customerDAO.getCustomerByBillId(billId);
		NhanVien staff = staffDAO.getStaffByBillId(billId);
		Phong room = roomDAO.getRoomByBillId(billId);
		List<CTDichVu> serviceOrders = new ArrayList<>();
		serviceOrders = serviceDetailDAO.getServiceDetailListByBillId(bill.getMaHoaDon());
		if (staff == null)
			staff = new NhanVien();
		if (room == null)
			room = new Phong();
		if (customer == null)
			customer = new KhachHang();

		bill.setDsCTDichVu(serviceOrders);

		modelTableBill.addRow(new Object[] { sttStr, addSpaceToString(String.valueOf(billId)),
				addSpaceToString(startTimeStr), addSpaceToString(endTimeStr), addSpaceToString(room.getMaPhong()),
				addSpaceToString(customer.getHoTen()), addSpaceToString(staff.getHoTen()),
				addSpaceToString(df.format(bill.getTongTienHD())) });
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
	 * tìm kiếm loại phòng dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		Date fromDate = dpFromDate.getValueSqlDate();
		Date toDate = dpToDate.getValueSqlDate();
		ArrayList<HoaDon> billList = new ArrayList<>();
		String keyword = txtKeyWord.getText().trim();
		String staffId = staffLogin.getMaNhanVien();
		int currentPage = txtPaging.getCurrentPage();
		int totalLine = 1;
		if (validData()) {
			if (searchTypeName.equalsIgnoreCase("Tất cả")) {
				totalLine = billDAO.getTotalLineOfBillList(fromDate, toDate, staffId);
				billList = billDAO.getBillListByDateAndPageNumber(fromDate, toDate, staffId, currentPage,
						lineNumberDisplayed);
			} else if (searchTypeName.equalsIgnoreCase("SĐT khách hàng")) {
				totalLine = billDAO.getTotalLineOfBillListByDateAndCustomerPhoneNumber(keyword, fromDate, toDate,
						keyword);
				billList = billDAO.getBillListByDateAndCustomerPhoneNumberAndPageNumber(keyword, fromDate, toDate,
						staffId, currentPage, lineNumberDisplayed);
			} else if (searchTypeName.equalsIgnoreCase("Tên khách hàng")) {
				totalLine = billDAO.getTotalLineOfBillListByDateAndCustomerName(keyword, fromDate, toDate, staffId);
				billList = billDAO.getBillListByDateAndCustomerNameAndPageNumber(keyword, fromDate, toDate, staffId,
						currentPage, lineNumberDisplayed);
			} else if (searchTypeName.equalsIgnoreCase("Tên nhân viên")) {
				totalLine = billDAO.getTotalLineOfBillListByDateAndStaffName(keyword, fromDate, toDate, staffId);
				billList = billDAO.getBillListByDateAndStaffNameAndPageNumber(keyword, fromDate, toDate, staffId,
						currentPage, lineNumberDisplayed);
			} else if (searchTypeName.equalsIgnoreCase("Mã hóa đơn")) {
				totalLine = billDAO.getTotalLineOfBillListByDateAndBillId(keyword, fromDate, toDate, staffId);
				billList = billDAO.getBillListByDateAndBillIdAndPageNumber(keyword, fromDate, toDate, staffId,
						currentPage,
						lineNumberDisplayed);
			}
		}
		int lastPage = getLastPage(totalLine);
		txtPaging.setTotalPage(lastPage);
		loadBillList(billList);
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
	public int getLastPage(int totalLine) {
		int lastPage = totalLine / lineNumberDisplayed;
		if (totalLine % lineNumberDisplayed != 0) {
			lastPage++;
		}
		return lastPage;
	}
}
