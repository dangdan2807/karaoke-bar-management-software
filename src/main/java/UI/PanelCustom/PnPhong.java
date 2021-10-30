package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import DAO.*;
import UI.fDieuHuong;
import entity.*;

public class PnPhong extends JFrame implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private JTable tableRoom;
	private DefaultTableModel modelTableRoom;
	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon addIcon = CustomUI.ADD_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon updateIcon = CustomUI.UPDATE_ICON;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private JTextField txtLocation, txtBFieldSearch, txtKeyWord, txtBFieldSearchType, txtRoomID;
	private JTextField txtBFieldRoomStatus, txtBFieldRoomType;
	private JLabel lbLocation, lpSearch;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch;
	private JComboBox<String> cboSearch, cboSearchType, cboRoomType, cboRoomStatus;

	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;

	public PnPhong(NhanVien staff) {
		this.staffLogin = staff;
		setSize(1270, 630);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnMain = new JPanel() {
			private static final long serialVersionUID = 1L;

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
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255, 255, 255));
				setFont(new Font("Dialog", Font.BOLD, 20));
				g2.drawString("QUẢN LÝ PHÒNG", 555, 33);
			}
		};

		pnTitle.setBounds(0, 0, 1270, 50);
		pnTitle.setOpaque(false);
		pnTitle.setLayout(null);

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 30, 19, 9, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnTitle.add(btnBack);
		pnMain.add(pnTitle);

		JPanel pnInfo = new JPanel();
		pnInfo.setLayout(null);
		pnInfo.setOpaque(false);
		pnInfo.setBounds(0, 60, 1238, 140);
		pnMain.add(pnInfo);

		txtLocation = new JTextField();
		txtLocation.setBounds(725, 15, 180, 20);
		txtLocation.setToolTipText("Nhập vị trí của phòng");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtLocation);
		pnInfo.add(txtLocation);

		lbLocation = new JLabel("Vị trí:");
		lbLocation.setForeground(Color.WHITE);
		lbLocation.setFont(new Font("Dialog", Font.BOLD, 13));
		lbLocation.setBounds(660, 15, 70, 20);
		pnInfo.add(lbLocation);

		btnAdd = new MyButton(100, 35, "Thêm", gra, addIcon.getImage(), 39, 19);
		btnAdd.setToolTipText("Thêm nhân viên mới sau khi đã điền đủ thông tin");
		btnAdd.setBounds(20, 93, 100, 35);
		pnInfo.add(btnAdd);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, updateIcon.getImage(), 43, 19);
		btnUpdate.setToolTipText("Sửa thông tin nhân viên");
		btnUpdate.setBounds(150, 93, 100, 35);
		btnUpdate.setEnabledCustom(false);
		pnInfo.add(btnUpdate);

		btnRefresh = new MyButton(100, 35, "Làm mới", gra, refreshIcon.getImage(), 27, 19);
		btnRefresh.setToolTipText("Làm mới form");
		btnRefresh.setBounds(1118, 93, 100, 35);
		pnInfo.add(btnRefresh);

		JPanel pnSearch = new JPanel();
		pnSearch.setBounds(286, 83, 822, 53);
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
		cboSearch.addItem("Tình trạng phòng");
		cboSearch.addItem("Loại phòng");
		cboSearch.addItem("Vị trí");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		cboSearch.setToolTipText("Loại tìm kiếm");
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(140, 18, 160, 20);

		pnSearch.add(cboSearch);

		btnSearch = new MyButton(100, 35, "Tìm kiếm", gra, searchIcon.getImage(), 26, 19);
		btnSearch.setToolTipText("Tìm kiếm thông tin nhân viên theo từ khóa");
		btnSearch.setBounds(702, 10, 100, 35);
		pnSearch.add(btnSearch);

		JLabel lpKeyWord = new JLabel("Từ khóa:");
		lpKeyWord.setForeground(Color.WHITE);
		lpKeyWord.setFont(new Font("Dialog", Font.BOLD, 13));
		lpKeyWord.setBounds(364, 18, 76, 20);
		pnSearch.add(lpKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setToolTipText("Nhập từ khoá cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		txtKeyWord.setBounds(440, 18, 200, 20);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		pnSearch.add(txtKeyWord);

		cboSearchType = new JComboBox<String>();
		cboSearchType.setToolTipText("Loại phòng cần tìm");
		CustomUI.getInstance().setCustomComboBox(cboSearchType);
		txtBFieldSearchType = CustomUI.getInstance().setCustomCBoxField(cboSearchType);
		cboSearchType.setBounds(440, 18, 200, 20);
		cboSearchType.setVisible(false);
		pnSearch.add(cboSearchType);

		JLabel lpRoomType = new JLabel("Loại phòng:");
		lpRoomType.setForeground(Color.WHITE);
		lpRoomType.setFont(new Font("Dialog", Font.BOLD, 13));
		lpRoomType.setBounds(955, 15, 90, 20);
		pnInfo.add(lpRoomType);

		JLabel lbRoomID = new JLabel("Mã phòng:");
		lbRoomID.setForeground(Color.WHITE);
		lbRoomID.setFont(new Font("Dialog", Font.BOLD, 13));
		lbRoomID.setBounds(25, 15, 90, 20);
		pnInfo.add(lbRoomID);

		txtRoomID = new JTextField();
		txtRoomID.setBounds(115, 15, 180, 20);
		txtRoomID.setToolTipText("Mã phòng");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomID);
		pnInfo.add(txtRoomID);

		JLabel lbStatusRoom = new JLabel("Tình trạng:");
		lbStatusRoom.setForeground(Color.WHITE);
		lbStatusRoom.setFont(new Font("Dialog", Font.BOLD, 13));
		lbStatusRoom.setBounds(340, 15, 90, 20);
		pnInfo.add(lbStatusRoom);

		cboRoomStatus = new JComboBox<String>();
		cboRoomStatus.addItem("Phòng trống");
		cboRoomStatus.addItem("Phòng đang sử dụng");
		cboRoomStatus.setToolTipText("Tình trạng phòng");
		CustomUI.getInstance().setCustomComboBox(cboRoomStatus);
		txtBFieldRoomStatus = CustomUI.getInstance().setCustomCBoxField(cboRoomStatus);
		cboRoomStatus.setBounds(430, 15, 180, 20);
		pnInfo.add(cboRoomStatus);

		cboRoomType = new JComboBox<String>();
		cboRoomType.setToolTipText("Chọn loại phòng");
		CustomUI.getInstance().setCustomComboBox(cboRoomType);
		txtBFieldRoomType = CustomUI.getInstance().setCustomCBoxField(cboRoomType);
		cboRoomType.setBounds(1050, 15, 180, 20);
		pnInfo.add(cboRoomType);

		JPanel pnTable = new JPanel();
		pnTable.setBackground(Color.WHITE);
		pnTable.setLayout(null);
		pnTable.setBounds(8, 201, 1240, 384);
		pnTable.setOpaque(false);
		String[] cols = { "STT", "Mã phòng", "Tình trạng ", "Vị trí", "Loại phòng" };
		modelTableRoom = new DefaultTableModel(cols, 0);
		tableRoom = new JTable(modelTableRoom) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		tableRoom.setBackground(new Color(255, 255, 255, 0));
		tableRoom.setForeground(new Color(255, 255, 255));
		tableRoom.setRowHeight(21);
		tableRoom.setFont(new Font("Dialog", Font.PLAIN, 14));
		tableRoom.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
		tableRoom.getTableHeader().setForeground(Color.decode("#9B17EB"));
		JScrollPane scpTable = new JScrollPane(tableRoom, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpTable.getViewport().setBackground(Color.WHITE);
		scpTable.setBounds(10, 10, 1220, 350);
		scpTable.setOpaque(false);
		scpTable.getViewport().setOpaque(false);

		pnTable.add(scpTable);
		pnMain.add(pnTable);

		btnAdd.addActionListener(this);
		btnBack.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnSearch.addActionListener(this);
		btnUpdate.addActionListener(this);

		tableRoom.addMouseListener(this);
		txtLocation.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		txtBFieldSearchType.addMouseListener(this);
		txtBFieldRoomType.addMouseListener(this);
		txtBFieldRoomStatus.addMouseListener(this);

		txtKeyWord.addFocusListener(this);
		txtLocation.addFocusListener(this);

		txtKeyWord.addKeyListener(this);

		cboSearch.addItemListener(this);
		cboSearchType.addItemListener(this);

		allLoaded();
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
			new PnPhong(staff).setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnRefresh)) {
			txtRoomID.setText("");
			cboRoomStatus.setSelectedIndex(0);
			txtLocation.setText("");
			cboRoomType.setSelectedIndex(0);
			btnUpdate.setEnabledCustom(false);
			btnAdd.setEnabledCustom(true);
			cboSearch.setSelectedIndex(0);
			removeSelectionInterval();
		} else if (o.equals(btnBack)) {
			fDieuHuong f = new fDieuHuong(staffLogin);
			this.setVisible(false);
			f.setVisible(true);
		} else if (o.equals(btnAdd)) {
			String message = "";
			if (validData()) {
				Phong room = getRoomDataInForm();
				Boolean insertResult = PhongDAO.getInstance().insertRoom(room);
				String name = "phòng";
				if (insertResult) {
					message = "Thêm " + name + " mới thành công";
					txtRoomID.setText(room.getMaPhong());
					int stt = tableRoom.getRowCount();
					addRow(stt, room);
					int lastIndex = tableRoom.getRowCount() - 1;
					tableRoom.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
					tableRoom.scrollRectToVisible(tableRoom.getCellRect(lastIndex, lastIndex, true));
					btnAdd.setEnabledCustom(false);
					btnUpdate.setEnabledCustom(true);
				} else {
					message = "Thêm " + name + " thất bại";
				}
				JOptionPane.showMessageDialog(this, message);
			}
		} else if (o.equals(btnUpdate)) {
			if (validData()) {
				Phong newRoom = getRoomDataInForm();
				Phong oldRoomType = PhongDAO.getInstance().getRoomByRoomId(newRoom.getMaPhong());
				String message = "";
				int selectedRow = tableRoom.getSelectedRow();
				String name = "phòng";
				if (selectedRow == -1) {
					message = "Hãy chọn " + name + " mà bạn cần cập nhật thông tin";
					JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					message = "Xác nhận cập nhật thông tin " + name + " " + oldRoomType.getMaPhong();
					int confirmUpdate = JOptionPane.showConfirmDialog(this, message,
							"Xác nhận cập nhật thông tin " + name + "", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (confirmUpdate == JOptionPane.OK_OPTION) {
						Boolean updateResult = PhongDAO.getInstance().updateInfoRoom(newRoom);
						if (updateResult) {
							message = "Cập nhật thông tin " + name + " thành công";
							updateRow(selectedRow, newRoom);
							btnAdd.setEnabledCustom(false);
							btnUpdate.setEnabledCustom(true);
							tableRoom.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tableRoom.scrollRectToVisible(tableRoom.getCellRect(selectedRow, selectedRow, true));
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
			String searchType = cboSearch.getSelectedItem().toString();
			txtKeyWord.setText("");
			if (searchType.equalsIgnoreCase("Vị trí") || searchType.equalsIgnoreCase("Tất cả")) {
				cboSearchType.setVisible(false);
				txtKeyWord.setVisible(true);
				if (searchType.equalsIgnoreCase("Vị trí"))
					CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
				else
					CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
			} else {
				cboSearchType.setVisible(true);
				txtKeyWord.setVisible(false);
				if (searchType.equalsIgnoreCase("Tình trạng phòng")) {
					cboSearchType.removeAllItems();
					cboSearchType.addItem("Phòng trống");
					cboSearchType.addItem("Phòng đang sử dụng");
				} else if (searchType.equalsIgnoreCase("Loại phòng")) {
					cboSearchType.removeAllItems();
					ArrayList<LoaiPhong> roomTypeList = LoaiPhongDAO.getInstance().getRoomTypeList();
					for (LoaiPhong loaiPhong : roomTypeList) {
						cboSearchType.addItem(loaiPhong.getTenLP());
					}
				}
			}
			removeSelectionInterval();
			searchEventUsingBtnSearch();

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
		} else if (o.equals(txtBFieldSearchType)) {
			cboSearchType.showPopup();
		} else if (o.equals(txtBFieldRoomType)) {
			cboRoomType.showPopup();
		} else if (o.equals(txtBFieldRoomStatus)) {
			cboRoomStatus.showPopup();
		} else if (o.equals(tableRoom)) {
			int selectedRow = tableRoom.getSelectedRow();
			txtRoomID.setText(tableRoom.getValueAt(selectedRow, 1).toString().trim());
			String roomStatusStr = tableRoom.getValueAt(selectedRow, 2).toString().trim();
			if (roomStatusStr.equalsIgnoreCase("Trống"))
				cboRoomStatus.setSelectedIndex(0);
			else
				cboRoomStatus.setSelectedIndex(1);
			txtLocation.setText(tableRoom.getValueAt(selectedRow, 3).toString().trim());
			String roomTypeStr = tableRoom.getValueAt(selectedRow, 4).toString().trim();
			cboRoomType.setSelectedItem(roomTypeStr);
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
		} else if (o.equals(txtBFieldSearchType)) {
			cboSearchType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldRoomType)) {
			cboRoomType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldRoomStatus)) {
			cboRoomStatus.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldSearchType)) {
			cboSearchType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldRoomType)) {
			cboRoomType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldRoomStatus)) {
			cboRoomStatus.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
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
				btnSearch.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtLocation)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtLocation);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtLocation)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtLocation);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		}
	}

	/**
	 * Chạy tất cả các hàm khi bắt đầu chạy form
	 */
	public void allLoaded() {
		reSizeColumnTable();
		loadRoomList(PhongDAO.getInstance().getRoomList());
		loadRoomTypeList();
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
		boolean valid = ValidationData.getInstance().ValidName(this, txtLocation, "vị trí của phòng", 100, -1);
		return valid;
	}

	/**
	 * Tự động tạo mã nhân viên mới tăng theo thứ tự tăng dần
	 * 
	 * @return {@code String}: mã nhân viên mới
	 */
	private String createNewRoomID() {
		String lastStaffId = PhongDAO.getInstance().getLastRoomID();
		String idStr = "P";
		int oldNumberStaffID = 0;
		if (lastStaffId.equalsIgnoreCase("") == false || lastStaffId != null) {
			oldNumberStaffID = Integer.parseInt(lastStaffId.replace(idStr, ""));
		}

		int newStaffID = ++oldNumberStaffID;
		String newStaffIdStr = idStr + newStaffID;
		boolean flag = true;
		while (flag) {
			newStaffIdStr = newStaffIdStr.replace(idStr, idStr + "0");
			if (newStaffIdStr.length() > 4) {
				flag = false;
			}
		}
		return newStaffIdStr;
	}

	/**
	 * chuyển đổi thông tin trong form thành đối tượng {@code Phong}
	 * 
	 * @return {@code Phong}: phòng được chuyển đổi thông tin từ form
	 */
	private Phong getRoomDataInForm() {
		String roomId = txtRoomID.getText().trim();
		String roomStatusStr = cboRoomStatus.getSelectedItem().toString().trim();
		int roomStatus = roomStatusStr.equalsIgnoreCase("Phòng Trống") ? 0 : 1;
		String location = txtLocation.getText().trim();
		String roomTypeName = cboRoomType.getSelectedItem().toString().trim();
		LoaiPhong roomType = LoaiPhongDAO.getInstance().getRoomTypeByName(roomTypeName);
		if (roomId.equals("") || roomId.length() <= 0) {
			roomId = createNewRoomID();
		}
		return new Phong(roomId, roomStatus, location, roomType);
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
	 * Thêm dòng vào danh sách phòng đang hiển thị
	 * 
	 * @param stt  {@code int}: số thứ tự
	 * @param room {@code Phong}: phòng cần hiển thị
	 */
	private void addRow(int stt, Phong room) {
		String sttStr = df.format(stt);
		String roomStatusStr = room.getTinhTrangP() == 1 ? "Đang sử dụng" : "Trống";
		modelTableRoom
				.addRow(new Object[] { sttStr, addSpaceToString(room.getMaPhong()), addSpaceToString(roomStatusStr),
						addSpaceToString(room.getViTri()), addSpaceToString(room.getLoaiPhong().getTenLP()) });
		modelTableRoom.fireTableDataChanged();
	}

	/**
	 * Cập nhật thông tin một dòng khi biết dòng mà thông tin phòng
	 * 
	 * @param selectedRow {@code int}: dòng được chọn
	 * @param room        {@code LoaiPhong}: phòng cần cập nhật
	 */
	private void updateRow(int selectedRow, Phong room) {
		String roomStatusStr = room.getTinhTrangP() == 1 ? "Đang sử dụng" : "Trống";
		modelTableRoom.setValueAt(addSpaceToString(roomStatusStr), selectedRow, 2);
		modelTableRoom.setValueAt(addSpaceToString(room.getViTri()), selectedRow, 3);
		modelTableRoom.setValueAt(addSpaceToString(room.getLoaiPhong().getTenLP()), selectedRow, 4);
		modelTableRoom.fireTableDataChanged();
	}

	/**
	 * Hiển thị danh sách phòng
	 * 
	 * @param roomList {@code ArrayList<Phong>}: danh sách phòng
	 */
	private void loadRoomList(ArrayList<Phong> roomList) {
		modelTableRoom.getDataVector().removeAllElements();
		modelTableRoom.fireTableDataChanged();
		int i = 1;
		for (Phong item : roomList) {
			addRow(i++, item);
		}
	}

	/**
	 * Hiển thị danh sách phòng
	 */
	private void loadRoomTypeList() {
		ArrayList<LoaiPhong> roomTypeList = LoaiPhongDAO.getInstance().getRoomTypeList();
		for (LoaiPhong loaiPhong : roomTypeList) {
			cboRoomType.addItem(loaiPhong.getTenLP());
		}
	}

	/**
	 * Thay đổi kích thước cột
	 */
	private void reSizeColumnTable() {
		tableRoom.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableRoom.getColumnModel().getColumn(1).setPreferredWidth(130);
		tableRoom.getColumnModel().getColumn(2).setPreferredWidth(250);
		tableRoom.getColumnModel().getColumn(3).setPreferredWidth(200);
		tableRoom.getColumnModel().getColumn(4).setPreferredWidth(200);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableRoom.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tableRoom.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tableRoom.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
	}

	/**
	 * tìm kiếm phòng dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		ArrayList<Phong> roomList = null;
		Object keyword = "";
		if (searchTypeName.equalsIgnoreCase("Tất cả")) {
			roomList = PhongDAO.getInstance().getRoomList();

		} else if (searchTypeName.equalsIgnoreCase("Tình trạng phòng")) {
			keyword = cboSearchType.getSelectedItem();
			int roomStatus = 0;
			if (keyword != null) {
				roomStatus = keyword.toString().equalsIgnoreCase("Phòng trống") ? 0 : 1;
			}
			roomList = PhongDAO.getInstance().getRoomListByStatus(roomStatus);

		} else if (searchTypeName.equalsIgnoreCase("Loại phòng")) {
			keyword = cboSearchType.getSelectedItem();
			if (keyword == null) {
				keyword = cboRoomStatus.getItemAt(0).toString().trim();
			}
			roomList = PhongDAO.getInstance().getRoomListByRoomTypeName(keyword.toString());

		} else if (searchTypeName.equalsIgnoreCase("Vị trí")) {
			keyword = txtKeyWord.getText().trim();
			roomList = PhongDAO.getInstance().getRoomListByLocation(keyword.toString());
		}
		loadRoomList(roomList);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tableRoom.getSelectedRow();
		tableRoom.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}
}
