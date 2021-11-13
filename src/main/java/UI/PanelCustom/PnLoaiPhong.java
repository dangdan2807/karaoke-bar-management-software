package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import DAO.LoaiPhongDAO;
import DAO.NhanVienDAO;
import DAO.ValidationData;
import UI.fDieuHuong;
import UI.fQuanTri;
import entity.LoaiPhong;
import entity.NhanVien;

public class PnLoaiPhong extends JPanel
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8227831825791723961L;
	private JTable tblTableTypeRoom;
	private DefaultTableModel modelTableTypeRoom;
	private JTextField txtBFieldSearch, txtKeyWord, txtRoomTypeId, txtRoomTypeName;
	private JLabel lblCapacity, lblSearch;
	private MyButton btnSearch, btnAdd, btnUpdate, btnRefresh, btnBack;
	private JComboBox<String> cboSearch;
	private JSpinner spnCapacity, spnPrice, spnSearchPrice;

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

	public PnLoaiPhong(NhanVien staff) {
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

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 30, 19);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlTitle.add(btnBack);
		pnlMain.add(pnlTitle);

		JLabel lblTitle = new JLabel("QUẢN LÝ LOẠI PHÒNG");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lblTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(0, 60, 1238, 184);
		pnlMain.add(pnlInfo);

		spnCapacity = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		CustomUI.getInstance().setCustomSpinner(spnCapacity);
		spnCapacity.setBounds(225, 50, 180, 20);
		pnlInfo.add(spnCapacity);

		lblCapacity = new JLabel("Sức chứa:");
		CustomUI.getInstance().setCustomLabel(lblCapacity);
		lblCapacity.setBounds(100, 50, 90, 20);
		pnlInfo.add(lblCapacity);

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
		pnlSearch.setBounds(152, 131, 871, 53);
		pnlInfo.add(pnlSearch);
		pnlSearch.setOpaque(false);
		pnlSearch.setLayout(null);
		pnlInfo.add(pnlSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(30, 15, 66, 20);
		pnlSearch.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên loại phòng");
		cboSearch.addItem("Giá cho thuê");
		cboSearch.setToolTipText("Loại tìm kiếm");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(95, 15, 200, 20);

		pnlSearch.add(cboSearch);

		btnSearch = new MyButton(130, 35, "Tìm kiếm", gra, searchIcon.getImage(), 40, 19, 10, 5);
		btnSearch.setToolTipText("Tìm kiếm thông tin loại phòng theo từ khóa");
		btnSearch.setBounds(728, 8, 130, 35);
		pnlSearch.add(btnSearch);

		JLabel lblKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lblKeyWord);
		lblKeyWord.setBounds(387, 15, 76, 20);
		pnlSearch.add(lblKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setText("");
		txtKeyWord.setBounds(470, 15, 200, 20);
		txtKeyWord.setToolTipText("Nhập từ khóa cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		pnlSearch.add(txtKeyWord);

		spnSearchPrice = new JSpinner(new SpinnerNumberModel(0f, 0f, Double.MAX_VALUE, 1000f));
		CustomUI.getInstance().setCustomSpinner(spnSearchPrice);
		spnSearchPrice.setBounds(470, 16, 200, 20);
		spnSearchPrice.setToolTipText("Nhập giá phòng cần tìm kiếm");
		spnSearchPrice.setVisible(false);
		pnlSearch.add(spnSearchPrice);

		JLabel lblPrice = new JLabel("Giá tiền:");
		CustomUI.getInstance().setCustomLabel(lblPrice);
		lblPrice.setBounds(594, 50, 90, 20);
		pnlInfo.add(lblPrice);

		spnPrice = new JSpinner(new SpinnerNumberModel(1000f, 0f, Double.MAX_VALUE, 1000f));
		CustomUI.getInstance().setCustomSpinner(spnPrice);
		spnPrice.setBounds(713, 50, 180, 20);
		pnlInfo.add(spnPrice);

		JLabel lblRoomTypeID = new JLabel("Mã loại phòng:");
		CustomUI.getInstance().setCustomLabel(lblRoomTypeID);
		lblRoomTypeID.setBounds(100, 20, 120, 20);
		pnlInfo.add(lblRoomTypeID);

		txtRoomTypeId = new JTextField();
		txtRoomTypeId.setText("");
		txtRoomTypeId.setBounds(225, 20, 180, 20);
		txtRoomTypeId.setToolTipText("Mã loại phòng");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomTypeId);
		pnlInfo.add(txtRoomTypeId);

		JLabel lblRoomTypeName = new JLabel("Tên loại phòng:");
		CustomUI.getInstance().setCustomLabel(lblRoomTypeName);
		lblRoomTypeName.setBounds(594, 20, 120, 20);
		pnlInfo.add(lblRoomTypeName);

		txtRoomTypeName = new JTextField();
		txtRoomTypeName.setText("");
		txtRoomTypeName.setBounds(713, 20, 180, 20);
		txtRoomTypeName.setToolTipText("Tên loại phòng");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtRoomTypeName);
		pnlInfo.add(txtRoomTypeName);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		pnlTable.setBounds(0, 242, 1240, 336);
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã loại phòng", "Tên loại phòng ", "Sức chứa", "Giá tiền" };
		modelTableTypeRoom = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		tblTableTypeRoom = new JTable(modelTableTypeRoom);
		tblTableTypeRoom.setBackground(new Color(255, 255, 255, 0));
		tblTableTypeRoom.setForeground(new Color(255, 255, 255));
		tblTableTypeRoom.setRowHeight(21);
		tblTableTypeRoom.setFont(new Font("Dialog", Font.PLAIN, 14));
		tblTableTypeRoom.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
		tblTableTypeRoom.getTableHeader().setForeground(Color.decode("#9B17EB"));
		JScrollPane scrTable = new JScrollPane(tblTableTypeRoom, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrTable.getViewport().setBackground(Color.WHITE);
		scrTable.setBounds(10, 10, 1220, 316);
		scrTable.setOpaque(false);
		scrTable.getViewport().setOpaque(false);

		pnlTable.add(scrTable);
		pnlMain.add(pnlTable);

		btnSearch.addActionListener(this);
		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnRefresh.addActionListener(this);

		tblTableTypeRoom.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		cboSearch.addMouseListener(this);
		txtRoomTypeName.addMouseListener(this);
		txtKeyWord.addMouseListener(this);

		txtRoomTypeName.addFocusListener(this);
		((JSpinner.DefaultEditor) spnCapacity.getEditor()).getTextField().addFocusListener(this);
		((JSpinner.DefaultEditor) spnPrice.getEditor()).getTextField().addFocusListener(this);
		txtKeyWord.addFocusListener(this);

		txtKeyWord.addKeyListener(this);
		((JSpinner.DefaultEditor) spnSearchPrice.getEditor()).getTextField().addKeyListener(this);

		cboSearch.addItemListener(this);

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
		if (o.equals(btnAdd)) {
			String message = "";
			if (validData()) {
				LoaiPhong roomType = getRoomTypeDataInForm();
				Boolean insertResult = LoaiPhongDAO.getInstance().insertRoomType(roomType);
				String name = "loại phòng";
				if (insertResult) {
					message = "Thêm " + name + " mới thành công";
					txtRoomTypeId.setText(roomType.getMaLP());
					int stt = tblTableTypeRoom.getRowCount();
					addRow(stt, roomType);
					int lastIndex = tblTableTypeRoom.getRowCount() - 1;
					tblTableTypeRoom.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
					tblTableTypeRoom.scrollRectToVisible(tblTableTypeRoom.getCellRect(lastIndex, lastIndex, true));
					btnAdd.setEnabledCustom(false);
					btnUpdate.setEnabledCustom(true);
				} else {
					message = "Thêm " + name + " thất bại";
				}
				JOptionPane.showMessageDialog(this, message);
			}
		} else if (o.equals(btnRefresh)) {
			spnPrice.setValue((double) 1000l);
			spnCapacity.setValue((int) 1);
			txtRoomTypeId.setText("");
			txtRoomTypeName.setText("");
			btnUpdate.setEnabledCustom(false);
			btnAdd.setEnabledCustom(true);
			removeSelectionInterval();
		} else if (o.equals(btnBack)) {
			fDieuHuong winNavigation = new fDieuHuong(staffLogin);
			this.setVisible(false);
			winNavigation.setVisible(true);
		} else if (o.equals(btnUpdate)) {
			if (validData()) {
				LoaiPhong newRoomType = getRoomTypeDataInForm();
				LoaiPhong oldRoomType = LoaiPhongDAO.getInstance().getRoomTypeById(newRoomType.getMaLP());
				String message = "";
				int selectedRow = tblTableTypeRoom.getSelectedRow();
				String name = "loại phòng";
				if (selectedRow == -1) {
					message = "Hãy chọn " + name + " mà bạn cần cập nhật thông tin";
					JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					message = "Xác nhận cập nhật thông tin " + name + " " + oldRoomType.getTenLP();
					int confirmUpdate = JOptionPane.showConfirmDialog(this, message,
							"Xác nhận cập nhật thông tin " + name + "", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (confirmUpdate == JOptionPane.OK_OPTION) {
						Boolean updateResult = LoaiPhongDAO.getInstance().updateInfoRoomType(newRoomType);
						if (updateResult) {
							message = "Cập nhật thông tin " + name + " thành công";
							updateRow(selectedRow, newRoomType);
							btnAdd.setEnabledCustom(false);
							btnUpdate.setEnabledCustom(true);
							tblTableTypeRoom.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tblTableTypeRoom
									.scrollRectToVisible(tblTableTypeRoom.getCellRect(selectedRow, selectedRow, true));
						} else {
							message = "Cập nhật thông tin " + name + " thất bại";
						}
						JOptionPane.showMessageDialog(this, message);
					}
				}
			}
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
				txtKeyWord.setEditable(false);
				CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
				searchEventUsingBtnSearch();
			} else {
				if (searchTypeName.equalsIgnoreCase("Giá cho thuê")) {
					txtKeyWord.setVisible(false);
					spnSearchPrice.setVisible(true);
				} else {
					txtKeyWord.setVisible(true);
					spnSearchPrice.setVisible(false);
				}
				txtKeyWord.setEditable(true);
				CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
			}
			removeSelectionInterval();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		} else if (o.equals(tblTableTypeRoom)) {
			int selectedRow = tblTableTypeRoom.getSelectedRow();
			txtRoomTypeId.setText(tblTableTypeRoom.getValueAt(selectedRow, 1).toString().trim());
			txtRoomTypeName.setText(tblTableTypeRoom.getValueAt(selectedRow, 2).toString().trim());

			String capacityStr = tblTableTypeRoom.getValueAt(selectedRow, 3).toString().trim().replace(",", "");
			int capacity = 1;
			if (!capacityStr.equals("")) {
				capacity = Integer.parseInt(capacityStr);
			}
			spnCapacity.setValue((int) capacity);

			String priceStr = tblTableTypeRoom.getValueAt(selectedRow, 4).toString().trim().replace(",", "");
			Double price = 0.0;
			if (!priceStr.equals("")) {
				price = Double.parseDouble(priceStr);
			}
			spnPrice.setValue((Double) price);
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
		int key = e.getKeyCode();
		if (o.equals(((JSpinner.DefaultEditor) spnSearchPrice.getEditor()).getTextField()) || o.equals(txtKeyWord)) {
			if (key == KeyEvent.VK_ENTER)
				searchEventUsingBtnSearch();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(((JSpinner.DefaultEditor) spnCapacity.getEditor()).getTextField())) {
			spnCapacity.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(((JSpinner.DefaultEditor) spnPrice.getEditor()).getTextField())) {
			spnPrice.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtRoomTypeName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtRoomTypeName);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(((JSpinner.DefaultEditor) spnCapacity.getEditor()).getTextField())) {
			spnCapacity.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(((JSpinner.DefaultEditor) spnPrice.getEditor()).getTextField())) {
			spnPrice.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		} else if (o.equals(txtRoomTypeName)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtRoomTypeName);
		}
	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	public void allLoaded() {
		reSizeColumnTable();
		loadRoomTypeList(LoaiPhongDAO.getInstance().getRoomTypeList());
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
		boolean valid = ValidationData.getInstance().ValidName(this, txtRoomTypeName, "Tên loại phòng", 100, 0);
		return valid;
	}

	/**
	 * Tự động tạo mã loại dịch vụ mới tăng theo thứ tự tăng dần
	 * 
	 * @return {@code String}: mã dịch vụ mới
	 */
	private String createNewServiceTypeID() {
		String lastStrId = LoaiPhongDAO.getInstance().getLastRoomTypeId();
		String idStr = "LP";
		int oldNumberId = 0;
		if (lastStrId.equalsIgnoreCase("") == false || lastStrId != null) {
			oldNumberId = Integer.parseInt(lastStrId.replace(idStr, ""));
		}

		int newNumberId = ++oldNumberId;
		String newIdStr = idStr + newNumberId;
		boolean flag = true;
		while (flag) {
			newIdStr = newIdStr.replace(idStr, idStr + "0");
			if (newIdStr.length() > 4) {
				flag = false;
			}
		}
		return newIdStr;
	}

	/**
	 * chuyển đổi thông tin trong form thành đối tượng LoaiPhong
	 * 
	 * @return {@code LoaiPhong}: loại phòng được chuyển đổi thông tin từ form
	 */
	private LoaiPhong getRoomTypeDataInForm() {
		String roomTypeId = txtRoomTypeId.getText().trim();
		String roomTypeName = txtRoomTypeName.getText().trim();
		int capacity = Integer.parseInt(spnCapacity.getValue().toString());
		Double price = Double.parseDouble(spnPrice.getValue().toString());
		if (roomTypeId.equals("") || roomTypeId.length() <= 0) {
			roomTypeId = createNewServiceTypeID();
		}
		return new LoaiPhong(roomTypeId, roomTypeName, capacity, price);
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
	 * Thêm dòng vào danh sách loại phòng đang hiển thị
	 * 
	 * @param stt      {@code int}: số thứ tự
	 * @param roomType {@code LoaiPhong}: loại phòng cần được thêm
	 */
	private void addRow(int stt, LoaiPhong roomType) {
		String sttStr = df.format(stt);
		modelTableTypeRoom.addRow(new Object[] { sttStr, addSpaceToString(roomType.getMaLP()),
				addSpaceToString(roomType.getTenLP()), addSpaceToString(df.format(roomType.getSucChua())),
				addSpaceToString(df.format(roomType.getGiaTien())) });
		modelTableTypeRoom.fireTableDataChanged();
	}

	/**
	 * Cập nhật thông tin một dòng khi biết dòng mà thông tin loại phòng
	 * 
	 * @param selectedRow {@code int}: dòng được chọn
	 * @param roomType    {@code LoaiPhong}: loại phòng cần cập nhật
	 */
	private void updateRow(int selectedRow, LoaiPhong roomType) {
		modelTableTypeRoom.setValueAt(addSpaceToString(roomType.getTenLP()), selectedRow, 2);
		modelTableTypeRoom.setValueAt(addSpaceToString(df.format(roomType.getSucChua())), selectedRow, 3);
		Double price = roomType.getGiaTien();
		modelTableTypeRoom.setValueAt(addSpaceToString(df.format(price)), selectedRow, 4);
		modelTableTypeRoom.fireTableDataChanged();
	}

	/**
	 * Hiển thị danh sách loại phòng
	 * 
	 * @param roomTypeList {@code ArrayList<DichVu>}: danh sách loại phòng
	 */
	private void loadRoomTypeList(ArrayList<LoaiPhong> roomTypeList) {
		modelTableTypeRoom.getDataVector().removeAllElements();
		modelTableTypeRoom.fireTableDataChanged();
		int i = 1;
		for (LoaiPhong item : roomTypeList) {
			addRow(i++, item);
		}
	}

	/**
	 * Thay đổi kích thước cột
	 */
	private void reSizeColumnTable() {
		TableColumnModel columnModel = tblTableTypeRoom.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(70);
		columnModel.getColumn(1).setPreferredWidth(130);
		columnModel.getColumn(2).setPreferredWidth(250);
		columnModel.getColumn(3).setPreferredWidth(200);
		columnModel.getColumn(4).setPreferredWidth(200);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		columnModel.getColumn(0).setCellRenderer(centerRenderer);
		columnModel.getColumn(3).setCellRenderer(rightRenderer);
		columnModel.getColumn(4).setCellRenderer(rightRenderer);
	}

	/**
	 * tìm kiếm loại phòng dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		ArrayList<LoaiPhong> roomTypeList = null;
		String keyword = "";
		if (searchTypeName.equalsIgnoreCase("Tất cả")) {
			roomTypeList = LoaiPhongDAO.getInstance().getRoomTypeList();
		} else if (searchTypeName.equalsIgnoreCase("Tên loại phòng")) {
			keyword = txtKeyWord.getText().trim();
			roomTypeList = LoaiPhongDAO.getInstance().getRoomTypeListByName(keyword);
		} else if (searchTypeName.equalsIgnoreCase("Giá cho thuê")) {
			String priceStr = spnSearchPrice.getValue().toString().replaceAll("\\.[0]+$", "");
			roomTypeList = LoaiPhongDAO.getInstance().getRoomTypeListByPrice(priceStr);
		}
		loadRoomTypeList(roomTypeList);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tblTableTypeRoom.getSelectedRow();
		tblTableTypeRoom.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}

	/**
	 * Lấy nút quay lại
	 */
	public JButton getBtnBack() {
		return btnBack;
	}
}
