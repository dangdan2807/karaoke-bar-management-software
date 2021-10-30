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
import entity.LoaiPhong;
import entity.NhanVien;

public class PnLoaiPhong extends JFrame
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	private JTable tableTypeRoom;
	private DefaultTableModel modelTableTypeRoom;
	private JTextField txtBFieldSearch, txtKeyWord, txtRoomTypeId, txtRoomTypeName;
	private JLabel lbCapacity, lpSearch;
	private MyButton btnSearch, btnAdd, btnUpdate, btnRefresh, btnBack;
	private JComboBox<String> cboSearch;
	private JSpinner spinCapacity, spinPrice, spinSearchPrice;

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
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnMain = new JPanel() {
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
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 1270, 630);
		getContentPane().add(pnMain);

		JPanel pnTitle = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255, 255, 255));
				setFont(new Font("Dialog", Font.BOLD, 20));
				g2.drawString("QUẢN LÝ LOẠI PHÒNG", 500, 33);
			}
		};
		pnTitle.setBounds(0, 0, 1270, 50);
		pnTitle.setOpaque(false);
		pnTitle.setLayout(null);
		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 30, 19);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnTitle.add(btnBack);
		pnMain.add(pnTitle);

		JPanel pnInfo = new JPanel();
		pnInfo.setLayout(null);
		pnInfo.setOpaque(false);
		pnInfo.setBounds(0, 60, 1238, 140);
		pnMain.add(pnInfo);

		spinCapacity = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		CustomUI.getInstance().setCustomSpinner(spinCapacity);
		spinCapacity.setBounds(763, 15, 180, 20);
		pnInfo.add(spinCapacity);

		lbCapacity = new JLabel("Sức chứa:");
		lbCapacity.setForeground(Color.WHITE);
		lbCapacity.setFont(new Font("Dialog", Font.BOLD, 13));
		lbCapacity.setBounds(670, 15, 90, 20);
		pnInfo.add(lbCapacity);

		btnAdd = new MyButton(100, 35, "Thêm", gra, addIcon.getImage(), 39, 19);
		btnAdd.setToolTipText("Thêm loại phòng mới");
		btnAdd.setBounds(20, 93, 100, 35);
		pnInfo.add(btnAdd);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, updateIcon.getImage(), 43, 19);
		btnUpdate.setToolTipText("Sửa thông tin loại phòng");
		btnUpdate.setBounds(150, 93, 100, 35);
		btnUpdate.setEnabledCustom(false);
		pnInfo.add(btnUpdate);

		btnRefresh = new MyButton(100, 35, "Làm mới", gra, refreshIcon.getImage(), 27, 19);
		btnRefresh.setToolTipText("Làm mới form");
		btnRefresh.setBounds(1118, 93, 100, 35);
		pnInfo.add(btnRefresh);

		JPanel pnSearch = new JPanel();
		pnSearch.setBounds(286, 83, 822, 53);
		pnInfo.add(pnSearch);
		pnSearch.setOpaque(false);
		pnSearch.setLayout(null);
		pnInfo.add(pnSearch);

		lpSearch = new JLabel("Lọc theo:");
		lpSearch.setForeground(Color.WHITE);
		lpSearch.setFont(new Font("Dialog", Font.BOLD, 13));
		lpSearch.setBounds(30, 18, 100, 20);
		pnSearch.add(lpSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên loại phòng");
		cboSearch.addItem("Giá cho thuê");
		cboSearch.setToolTipText("Loại tìm kiếm");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(140, 18, 160, 20);

		pnSearch.add(cboSearch);

		btnSearch = new MyButton(100, 35, "Tìm kiếm", gra, searchIcon.getImage(), 26, 19);
		btnSearch.setToolTipText("Tìm kiếm thông tin loại phòng theo từ khóa");
		btnSearch.setBounds(702, 10, 100, 35);
		pnSearch.add(btnSearch);

		JLabel lpKeyWord = new JLabel("Từ khóa:");
		lpKeyWord.setForeground(Color.WHITE);
		lpKeyWord.setFont(new Font("Dialog", Font.BOLD, 13));
		lpKeyWord.setBounds(364, 18, 76, 20);
		pnSearch.add(lpKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setText("");
		txtKeyWord.setBounds(440, 18, 200, 20);
		txtKeyWord.setToolTipText("Nhập từ khóa cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		pnSearch.add(txtKeyWord);

		spinSearchPrice = new JSpinner(new SpinnerNumberModel(0f, 0f, Double.MAX_VALUE, 1000f));
		CustomUI.getInstance().setCustomSpinner(spinSearchPrice);
		spinSearchPrice.setBounds(440, 18, 200, 20);
		spinSearchPrice.setToolTipText("Nhập giá phòng cần tìm kiếm");
		spinSearchPrice.setVisible(false);
		pnSearch.add(spinSearchPrice);

		JLabel lpPrice = new JLabel("Giá tiền:");
		lpPrice.setForeground(Color.WHITE);
		lpPrice.setFont(new Font("Dialog", Font.BOLD, 13));
		lpPrice.setBounds(977, 15, 90, 20);
		pnInfo.add(lpPrice);

		spinPrice = new JSpinner(new SpinnerNumberModel(1000f, 0f, Double.MAX_VALUE, 1000f));
		CustomUI.getInstance().setCustomSpinner(spinPrice);
		spinPrice.setBounds(1063, 15, 165, 20);
		pnInfo.add(spinPrice);

		JLabel lbRoomTypeID = new JLabel("Mã loại phòng:");
		lbRoomTypeID.setForeground(Color.WHITE);
		lbRoomTypeID.setFont(new Font("Dialog", Font.BOLD, 13));
		lbRoomTypeID.setBounds(20, 15, 120, 20);
		pnInfo.add(lbRoomTypeID);

		txtRoomTypeId = new JTextField();
		txtRoomTypeId.setText("");
		txtRoomTypeId.setBounds(145, 15, 165, 20);
		txtRoomTypeId.setToolTipText("Mã loại phòng");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomTypeId);
		pnInfo.add(txtRoomTypeId);

		JLabel lbRoomTypeName = new JLabel("Tên loại phòng:");
		lbRoomTypeName.setForeground(Color.WHITE);
		lbRoomTypeName.setFont(new Font("Dialog", Font.BOLD, 13));
		lbRoomTypeName.setBounds(344, 15, 120, 20);
		pnInfo.add(lbRoomTypeName);

		txtRoomTypeName = new JTextField();
		txtRoomTypeName.setText("");
		txtRoomTypeName.setBounds(469, 15, 165, 20);
		txtRoomTypeName.setToolTipText("Tên loại phòng");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtRoomTypeName);
		pnInfo.add(txtRoomTypeName);

		JPanel pnTable = new JPanel();
		pnTable.setBackground(Color.WHITE);
		pnTable.setLayout(null);
		pnTable.setBounds(8, 201, 1240, 384);
		pnTable.setOpaque(false);
		String[] cols = { "STT", "Mã loại phòng", "Tên loại phòng ", "Sức chứa", "Giá tiền" };
		modelTableTypeRoom = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		tableTypeRoom = new JTable(modelTableTypeRoom);
		tableTypeRoom.setBackground(new Color(255, 255, 255, 0));
		tableTypeRoom.setForeground(new Color(255, 255, 255));
		tableTypeRoom.setRowHeight(21);
		tableTypeRoom.setFont(new Font("Dialog", Font.PLAIN, 14));
		tableTypeRoom.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
		tableTypeRoom.getTableHeader().setForeground(Color.decode("#9B17EB"));
		JScrollPane scpTable = new JScrollPane(tableTypeRoom, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpTable.getViewport().setBackground(Color.WHITE);
		scpTable.setBounds(10, 10, 1220, 350);
		scpTable.setOpaque(false);
		scpTable.getViewport().setOpaque(false);

		pnTable.add(scpTable);
		pnMain.add(pnTable);

		btnSearch.addActionListener(this);
		btnBack.addActionListener(this);
		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnRefresh.addActionListener(this);

		tableTypeRoom.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		cboSearch.addMouseListener(this);
		txtRoomTypeName.addMouseListener(this);
		txtKeyWord.addMouseListener(this);

		txtRoomTypeName.addFocusListener(this);
		((JSpinner.DefaultEditor) spinCapacity.getEditor()).getTextField().addFocusListener(this);
		((JSpinner.DefaultEditor) spinPrice.getEditor()).getTextField().addFocusListener(this);
		txtKeyWord.addFocusListener(this);

		txtKeyWord.addKeyListener(this);
		((JSpinner.DefaultEditor) spinSearchPrice.getEditor()).getTextField().addKeyListener(this);

		cboSearch.addItemListener(this);

		allLoaded();
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
			new PnLoaiPhong(staff).setVisible(true);
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
					int stt = tableTypeRoom.getRowCount();
					addRow(stt, roomType);
					int lastIndex = tableTypeRoom.getRowCount() - 1;
					tableTypeRoom.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
					tableTypeRoom.scrollRectToVisible(tableTypeRoom.getCellRect(lastIndex, lastIndex, true));
					btnAdd.setEnabledCustom(false);
					btnUpdate.setEnabledCustom(true);
				} else {
					message = "Thêm " + name + " thất bại";
				}
				JOptionPane.showMessageDialog(this, message);
			}
		} else if (o.equals(btnRefresh)) {
			spinPrice.setValue((double) 1000l);
			spinCapacity.setValue((int) 1);
			txtRoomTypeId.setText("");
			txtRoomTypeName.setText("");
			btnUpdate.setEnabledCustom(false);
			btnAdd.setEnabledCustom(true);
			removeSelectionInterval();
		} else if (o.equals(btnBack)) {
			fDieuHuong f = new fDieuHuong(staffLogin);
			this.setVisible(false);
			f.setVisible(true);
		} else if (o.equals(btnUpdate)) {
			if (validData()) {
				LoaiPhong newRoomType = getRoomTypeDataInForm();
				LoaiPhong oldRoomType = LoaiPhongDAO.getInstance().getRoomTypeById(newRoomType.getMaLP());
				String message = "";
				int selectedRow = tableTypeRoom.getSelectedRow();
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
							tableTypeRoom.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tableTypeRoom
									.scrollRectToVisible(tableTypeRoom.getCellRect(selectedRow, selectedRow, true));
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
					spinSearchPrice.setVisible(true);
				} else {
					txtKeyWord.setVisible(true);
					spinSearchPrice.setVisible(false);
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
		} else if (o.equals(tableTypeRoom)) {
			int selectedRow = tableTypeRoom.getSelectedRow();
			txtRoomTypeId.setText(tableTypeRoom.getValueAt(selectedRow, 1).toString().trim());
			txtRoomTypeName.setText(tableTypeRoom.getValueAt(selectedRow, 2).toString().trim());

			String capacityStr = tableTypeRoom.getValueAt(selectedRow, 3).toString().trim().replace(",", "");
			int capacity = 1;
			if (!capacityStr.equals("")) {
				capacity = Integer.parseInt(capacityStr);
			}
			spinCapacity.setValue((int) capacity);

			String priceStr = tableTypeRoom.getValueAt(selectedRow, 4).toString().trim().replace(",", "");
			Double price = 0.0;
			if (!priceStr.equals("")) {
				price = Double.parseDouble(priceStr);
			}
			spinPrice.setValue((Double) price);
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
		if (o.equals(((JSpinner.DefaultEditor) spinSearchPrice.getEditor()).getTextField()) || o.equals(txtKeyWord)) {
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
		if (o.equals(((JSpinner.DefaultEditor) spinCapacity.getEditor()).getTextField())) {
			spinCapacity.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(((JSpinner.DefaultEditor) spinPrice.getEditor()).getTextField())) {
			spinPrice.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtRoomTypeName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtRoomTypeName);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(((JSpinner.DefaultEditor) spinCapacity.getEditor()).getTextField())) {
			spinCapacity.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(((JSpinner.DefaultEditor) spinPrice.getEditor()).getTextField())) {
			spinPrice.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
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
		int capacity = Integer.parseInt(spinCapacity.getValue().toString());
		Double price = Double.parseDouble(spinPrice.getValue().toString());
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
		tableTypeRoom.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableTypeRoom.getColumnModel().getColumn(1).setPreferredWidth(130);
		tableTypeRoom.getColumnModel().getColumn(2).setPreferredWidth(250);
		tableTypeRoom.getColumnModel().getColumn(3).setPreferredWidth(200);
		tableTypeRoom.getColumnModel().getColumn(4).setPreferredWidth(200);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableTypeRoom.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tableTypeRoom.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tableTypeRoom.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
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
			String priceStr = spinSearchPrice.getValue().toString().replaceAll("\\.[0]+$", "");
			roomTypeList = LoaiPhongDAO.getInstance().getRoomTypeListByPrice(priceStr);
		}
		loadRoomTypeList(roomTypeList);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tableTypeRoom.getSelectedRow();
		tableTypeRoom.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}
}
