package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import DAO.*;
import Event_Handlers.InputEventHandler;
import UI.*;
import entity.*;

public class PnLoaiDichVu extends JPanel
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7128176687439599864L;
	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon addIcon = CustomUI.ADD_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon updateIcon = CustomUI.UPDATE_ICON;
	private ImageIcon nextIconRight = new ImageIcon(
			CustomUI.NEXT_RIGHT_ICON.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextRightIcon = new ImageIcon(
			CustomUI.DOUBLE_NEXT_RIGHT_ICON.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon nextLeftIcon = new ImageIcon(
			CustomUI.NEXT_LEFT_ICON.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextLeftIcon = new ImageIcon(
			CustomUI.DOUBLE_NEXT_LEFT_ICON.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private JTable tblTableServiceType;
	private DefaultTableModel modelTable;
	private MyButton btnSearch, btnAdd, btnUpdate, btnRefresh, btnBack, btnNextToRight, btnNextToLast;
	private MyButton btnNextToLeft, btnNextToFirst;
	private JComboBox<String> cboSearch, cboSearchType;
	private JLabel lblSearch;
	private JTextField txtBFieldSearch, txtKeyWord, txtBFieldSearchType, txtServiceTypeID;
	private JTextField txtServiceTypeName;
	private PnTextFiledPaging txtPaging;

	private DecimalFormat df = new DecimalFormat("#,###.##");
	private int lineNumberDisplayed = 10;
	private NhanVien staffLogin = null;
	private LoaiDichVuDAO serviceTypeDAO = LoaiDichVuDAO.getInstance();

	public PnLoaiDichVu(NhanVien staff) {
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
		add(pnlMain);

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

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 30, 19, 9, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlTitle.add(btnBack);
		pnlMain.add(pnlTitle);

		JLabel lblTitle = new JLabel("QUẢN LÝ LOẠI DỊCH VỤ");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lblTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(10, 60, 1238, 165);
		pnlMain.add(pnlInfo);

		btnAdd = new MyButton(130, 35, "Thêm", gra, addIcon.getImage(), 50, 19, 10, 6);
		btnAdd.setToolTipText("Thêm loại dịch vụ mới sau khi đã điền đủ thông tin");
		btnAdd.setBounds(1023, 10, 130, 35);
		pnlInfo.add(btnAdd);

		btnUpdate = new MyButton(130, 35, "Sửa", gra, updateIcon.getImage(), 55, 19, 10, 6);
		btnUpdate.setToolTipText("Sửa thông tin loại dịch vụ");
		btnUpdate.setBounds(1023, 50, 130, 35);
		btnUpdate.setEnabledCustom(false);
		pnlInfo.add(btnUpdate);

		btnRefresh = new MyButton(130, 35, "Làm mới", gra, refreshIcon.getImage(), 40, 19, 10, 5);
		btnRefresh.setToolTipText("Làm mới form");
		btnRefresh.setBounds(1023, 90, 130, 35);
		pnlInfo.add(btnRefresh);

		JPanel pnlSearch = new JPanel();
		pnlSearch.setBounds(188, 124, 832, 41);
		pnlInfo.add(pnlSearch);
		pnlSearch.setOpaque(false);
		pnlSearch.setLayout(null);
		pnlInfo.add(pnlSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(21, 11, 70, 20);
		pnlSearch.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên loại dịch vụ");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(88, 11, 200, 20);

		pnlSearch.add(cboSearch);

		btnSearch = new MyButton(130, 35, "Tìm kiếm", gra, searchIcon.getImage(), 40, 19, 10, 5);
		btnSearch.setToolTipText("Tìm kiếm thông tin loại dịch vụ theo từ khóa");
		btnSearch.setBounds(693, 3, 130, 35);
		pnlSearch.add(btnSearch);

		JLabel lblKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lblKeyWord);
		lblKeyWord.setBounds(355, 11, 76, 20);
		pnlSearch.add(lblKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setForeground(Color.WHITE);
		txtKeyWord.setBounds(431, 11, 200, 20);
		txtKeyWord.setToolTipText("Nhập từ khóa cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		pnlSearch.add(txtKeyWord);

		cboSearchType = new JComboBox<String>();
		CustomUI.getInstance().setCustomComboBox(cboSearchType);
		txtBFieldSearchType = CustomUI.getInstance().setCustomCBoxField(cboSearchType);
		cboSearchType.setBounds(431, 11, 200, 20);
		cboSearchType.setVisible(false);
		pnlSearch.add(cboSearchType);

		JLabel lblServiceTypeID = new JLabel("Mã loại dịch vụ:");
		CustomUI.getInstance().setCustomLabel(lblServiceTypeID);
		lblServiceTypeID.setBounds(60, 50, 120, 20);
		pnlInfo.add(lblServiceTypeID);

		txtServiceTypeID = new JTextField();
		txtServiceTypeID.setBounds(185, 50, 250, 20);
		txtServiceTypeID.setToolTipText("Mã loại dịch vụ");
		CustomUI.getInstance().setCustomTextFieldOff(txtServiceTypeID);
		pnlInfo.add(txtServiceTypeID);

		JLabel lblServiceTypeName = new JLabel("Tên loại dịch vụ:");
		CustomUI.getInstance().setCustomLabel(lblServiceTypeName);
		lblServiceTypeName.setBounds(540, 50, 120, 20);
		pnlInfo.add(lblServiceTypeName);

		txtServiceTypeName = new JTextField();
		txtServiceTypeName.setBounds(669, 50, 250, 20);
		txtServiceTypeName.setToolTipText("Tên loại dịch vụ, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceTypeName);
		pnlInfo.add(txtServiceTypeName);
		cboSearchType.setVisible(false);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		CustomUI.getInstance().setBorderTitlePanelTable(pnlTable, "Danh sách loại dịch vụ");
		pnlTable.setBounds(18, 270, 1220, 265);
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã loại dịch vụ", "Tên loại dịch vụ " };
		modelTable = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		tblTableServiceType = new JTable(modelTable);
		CustomUI.getInstance().setCustomTable(tblTableServiceType);
		tblTableServiceType.setRowHeight(21);
		JScrollPane scrTable = CustomUI.getInstance().setCustomScrollPane(tblTableServiceType);
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
		txtPaging.setBounds(590, 540, 90, 35);
		txtPaging.setTextColor(Color.WHITE);
		pnlMain.add(txtPaging);

		btnSearch.addMouseListener(this);
		tblTableServiceType.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);

		txtKeyWord.addFocusListener(this);
		txtServiceTypeName.addFocusListener(this);

		txtKeyWord.addKeyListener(this);
		txtServiceTypeName.addKeyListener(this);

		cboSearch.addItemListener(this);

		cboSearch.addActionListener(this);
		btnAdd.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnSearch.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnNextToLast.addActionListener(this);
		btnNextToLeft.addActionListener(this);
		btnNextToRight.addActionListener(this);
		btnNextToFirst.addActionListener(this);

		allLoaded();
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
			this.setVisible(false);
			winNavigation.setVisible(true);
		} else if (o.equals(btnAdd)) {
			if (validData()) {
				String message = "";
				if (validData()) {
					LoaiDichVu serviceType = getServiceTypeDataInForm();
					Boolean result = serviceTypeDAO.insertService(serviceType);
					String name = "loại dịch vụ";
					if (result) {
						message = "Thêm " + name + " mới thành công";
						txtServiceTypeID.setText(serviceType.getMaLDV());
						int stt = tblTableServiceType.getRowCount();
						addRow(stt, serviceType);
						int lastIndex = tblTableServiceType.getRowCount() - 1;
						tblTableServiceType.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
						tblTableServiceType
								.scrollRectToVisible(tblTableServiceType.getCellRect(lastIndex, lastIndex, true));
						JOptionPane.showMessageDialog(this, message);
						btnAdd.setEnabledCustom(false);
						btnUpdate.setEnabledCustom(true);
					} else {
						message = "Thêm " + name + " mới thất bại";
						JOptionPane.showMessageDialog(this, message);
					}
				}
			}
		} else if (o.equals(btnRefresh)) {
			txtServiceTypeID.setText("");
			txtServiceTypeName.setText("");
			btnUpdate.setEnabledCustom(false);
			btnAdd.setEnabledCustom(true);
			removeSelectionInterval();
		} else if (o.equals(btnSearch)) {
			searchEventUsingBtnSearch();
		} else if (o.equals(btnUpdate)) {
			if (validData()) {
				LoaiDichVu serviceType = getServiceTypeDataInForm();
				LoaiDichVu serviceTypeOld = serviceTypeDAO.getServiceTypeById(serviceType.getMaLDV());
				String message = "";
				String name = "loại dịch vụ";
				int selectedRow = tblTableServiceType.getSelectedRow();
				if (selectedRow == -1) {
					message = "Hãy chọn " + name + " mà bạn cần cập nhật";
					JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					message = "Xác nhận cập nhật thông tin " + name + ": " + serviceTypeOld.getTenLDV();
					int choose = JOptionPane.showConfirmDialog(this, message, "Xác nhận cập nhật thông tin " + name,
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (choose == JOptionPane.OK_OPTION) {
						Boolean result = serviceTypeDAO.updateInfoServiceType(serviceType);
						if (result) {
							message = "Cập nhật thông tin " + name + " thành công";
							updateRow(selectedRow, serviceType);
							btnAdd.setEnabledCustom(false);
							btnUpdate.setEnabledCustom(true);
							tblTableServiceType.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tblTableServiceType.scrollRectToVisible(
									tblTableServiceType.getCellRect(selectedRow, selectedRow, true));
						} else {
							message = "Cập nhật thông tin " + name + " thất bại";
						}
						JOptionPane.showMessageDialog(this, message);
					}
				}
			}
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
			String searchType = cboSearch.getSelectedItem().toString();
			txtKeyWord.setText("");
			if (searchType.equalsIgnoreCase("Tất cả")) {
				CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
			} else if (searchType.equalsIgnoreCase("Tên loại dịch vụ")) {
				CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
			}
			removeSelectionInterval();
			searchEventUsingBtnSearch();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		} else if (o.equals(txtBFieldSearchType)) {
			cboSearchType.showPopup();
		} else if (o.equals(tblTableServiceType)) {
			int selectedRow = tblTableServiceType.getSelectedRow();
			txtServiceTypeID.setText(tblTableServiceType.getValueAt(selectedRow, 1).toString().trim());
			txtServiceTypeName.setText(tblTableServiceType.getValueAt(selectedRow, 2).toString().trim());
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
		Object key = e.getKeyCode();
		if (o.equals(txtKeyWord)) {
			if (key.equals(KeyEvent.VK_ENTER))
				searchEventUsingBtnSearch();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		InputEventHandler handler = new InputEventHandler();
		if (o.equals(txtKeyWord)) {
			handler.characterInputLimit(key, txtKeyWord, 100);
		} else if (o.equals(txtServiceTypeName)) {
			handler.characterInputLimit(key, txtServiceTypeName, 100);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		} else if (o.equals(txtServiceTypeName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtServiceTypeName);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		} else if (o.equals(txtServiceTypeName)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceTypeName);
		}
	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	public void allLoaded() {
		reSizeColumnTable();
		int totalLine = serviceTypeDAO.getTotalLineOfServiceTypeList();
		txtPaging.setCurrentPage(1);
		txtPaging.setTotalPage(getLastPage(totalLine));
		ArrayList<LoaiDichVu> serviceTypeList = serviceTypeDAO.getServiceTypeListAndPageNumber(1, lineNumberDisplayed);
		loadServiceTypeList(serviceTypeList, 1);
	}

	/**
	 * Tự động tạo mã loại dịch vụ mới tăng theo thứ tự tăng dần
	 * 
	 * @return {@code String}: mã dịch vụ mới
	 */
	private String createNewServiceTypeID() {
		String lastStrId = serviceTypeDAO.getLastServiceTypeID();
		String idStr = "LDV";
		int oldNumberId = 0;
		if (lastStrId.equalsIgnoreCase("") == false || lastStrId != null) {
			oldNumberId = Integer.parseInt(lastStrId.replace(idStr, ""));
		}

		int newNumberId = ++oldNumberId;
		String newIdStr = idStr + newNumberId;
		boolean flag = true;
		while (flag) {
			newIdStr = newIdStr.replace(idStr, idStr + "0");
			if (newIdStr.length() > 5) {
				flag = false;
			}
		}
		return newIdStr;
	}

	/**
	 * chuyển đổi thông tin trong form thành đối tượng {@code LoaiDichVu}
	 * 
	 * @return {@code LoaiDichVu}: dịch vụ được chuyển đổi thông tin từ form
	 */
	private LoaiDichVu getServiceTypeDataInForm() {
		String serviceTypeID = txtServiceTypeID.getText().trim();
		String serviceTypeName = txtServiceTypeName.getText().trim();
		if (serviceTypeID.equals("") || serviceTypeID.length() <= 0) {
			serviceTypeID = createNewServiceTypeID();
		}
		return new LoaiDichVu(serviceTypeID, serviceTypeName);
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
		boolean valid = ValidationData.getInstance().ValidName(this, txtServiceTypeName, "Tên loại dịch vụ", 100, 0);
		return valid;
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
	 * Thêm dòng vào danh sách loại dịch vụ đang hiển thị
	 * 
	 * @param stt         {@code int}: số thứ tự
	 * @param serviceType {@code LoaiDichVu}: loại dịch vụ cần được thêm
	 */
	private void addRow(int stt, LoaiDichVu serviceType) {
		String sttStr = df.format(stt);
		modelTable.addRow(new Object[] { sttStr, addSpaceToString(serviceType.getMaLDV()),
				addSpaceToString(serviceType.getTenLDV()) });
		modelTable.fireTableDataChanged();
	}

	/**
	 * Cập nhật thông tin một dòng khi biết dòng mà thông tin loại dịch vụ
	 * 
	 * @param selectedRow {@code int}: dòng được chọn
	 * @param serviceType {@code LoaiDichVu}: loại dịch vụ cần cập nhật
	 */
	private void updateRow(int selectedRow, LoaiDichVu serviceType) {
		String serviceTypeName = serviceType.getTenLDV();
		modelTable.setValueAt(addSpaceToString(serviceTypeName), selectedRow, 2);
		modelTable.fireTableDataChanged();
	}

	/**
	 * Hiển thị danh sách loại dịch vụ
	 * 
	 * @param serviceTypeList {@code ArrayList <LoaiDichVu>}: danh sách loại dịch vụ
	 * @param currentPage  {@code int}: số của trang hiện tại
	 */
	private void loadServiceTypeList(ArrayList<LoaiDichVu> serviceTypeList, int currentPage) {
		modelTable.getDataVector().removeAllElements();
		modelTable.fireTableDataChanged();
		int i = 1 + (currentPage - 1) * lineNumberDisplayed;
		for (LoaiDichVu item : serviceTypeList) {
			addRow(i++, item);
		}
	}

	/**
	 * Thay đổi kích thước cột
	 */
	private void reSizeColumnTable() {
		TableColumnModel columnModel = tblTableServiceType.getColumnModel();

		columnModel.getColumn(0).setPreferredWidth(70);
		columnModel.getColumn(1).setPreferredWidth(130);
		columnModel.getColumn(2).setPreferredWidth(250);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		columnModel.getColumn(0).setCellRenderer(centerRenderer);
	}

	/**
	 * tìm kiếm loại dịch vụ dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		ArrayList<LoaiDichVu> serviceTypeList = null;
		String keyword = txtKeyWord.getText().trim();
		int currentPage = txtPaging.getCurrentPage();
		int totalLine = serviceTypeDAO.getTotalLineOfServiceTypeListByName(keyword);

		serviceTypeList = serviceTypeDAO.getServiceTypeListByNameAndPageNumber(keyword, currentPage, lineNumberDisplayed);
		int lastPage = getLastPage(totalLine);
		txtPaging.setTotalPage(lastPage);
		loadServiceTypeList(serviceTypeList, currentPage);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tblTableServiceType.getSelectedRow();
		tblTableServiceType.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}

	/**
	 * Lấy nút quay lại
	 */
	public JButton getBtnBack() {
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
