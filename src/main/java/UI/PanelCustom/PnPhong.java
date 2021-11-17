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
import UI.fDieuHuong;
import UI.fQuanTri;
import entity.*;

public class PnPhong extends JPanel implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8410052847174456849L;
	private JTable tblTableRoom;
	private DefaultTableModel modelTableRoom;
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

	private JTextField txtLocation, txtBFieldSearch, txtKeyWord, txtBFieldSearchType, txtRoomID;
	private JTextField txtBFieldRoomStatus, txtBFieldRoomType;
	private JLabel lblLocation, lblSearch;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch,btnNextRight,
	btnDoubleNextRight, btnNextLeft, btnDoubleNextLeft;
	private JComboBox<String> cboSearch, cboSearchType, cboRoomType, cboRoomStatus;

	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;
	private MyTextField txtIndex;

	public PnPhong(NhanVien staff) {
		this.staffLogin = staff;
		setSize(1270, 630);
		this.setLayout(null);
		// this.setResizable(false);
		// this.setLocationRelativeTo(null);
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnlMain = new JPanel() {
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

		JLabel lblTitle = new JLabel("QUẢN LÝ PHÒNG");
		CustomUI.getInstance().setCustomLabel(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lblTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(0, 60, 1238, 188);
		pnlMain.add(pnlInfo);

		txtLocation = new JTextField();
		txtLocation.setBounds(206, 65, 180, 20);
		txtLocation.setToolTipText("Nhập vị trí của phòng");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtLocation);
		pnlInfo.add(txtLocation);

		lblLocation = new JLabel("Vị trí:");
		CustomUI.getInstance().setCustomLabel(lblLocation);
		lblLocation.setBounds(116, 65, 70, 20);
		pnlInfo.add(lblLocation);

		btnAdd = new MyButton(130, 35, "Thêm", gra, addIcon.getImage(), 50, 19, 10, 6);
		btnAdd.setToolTipText("Thêm loại dịch vụ mới sau khi đã điền đủ thông tin");
		btnAdd.setBounds(1005, 10, 130, 35);
		pnlInfo.add(btnAdd);

		btnUpdate = new MyButton(130, 35, "Sửa", gra, updateIcon.getImage(), 55, 19, 10, 6);
		btnUpdate.setToolTipText("Sửa thông tin loại dịch vụ");
		btnUpdate.setBounds(1005, 50, 130, 35);
		btnUpdate.setEnabledCustom(false);
		pnlInfo.add(btnUpdate);

		btnRefresh = new MyButton(130, 35, "Làm mới", gra, refreshIcon.getImage(), 40, 19, 10, 5);
		btnRefresh.setToolTipText("Làm mới form");
		btnRefresh.setBounds(1005, 90, 130, 35);
		pnlInfo.add(btnRefresh);

		JPanel pnlSearch = new JPanel();
		pnlSearch.setBounds(186, 135, 822, 53);
		pnlSearch.setOpaque(false);
		pnlSearch.setLayout(null);
		pnlInfo.add(pnlSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(30, 18, 76, 20);
		pnlSearch.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tình trạng phòng");
		cboSearch.addItem("Loại phòng");
		cboSearch.addItem("Vị trí");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		cboSearch.setToolTipText("Loại tìm kiếm");
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(105, 18, 200, 20);

		pnlSearch.add(cboSearch);

		btnSearch = new MyButton(100, 35, "Tìm kiếm", gra, searchIcon.getImage(), 26, 19);
		btnSearch.setToolTipText("Tìm kiếm thông tin nhân viên theo từ khóa");
		btnSearch.setBounds(702, 10, 100, 35);
		pnlSearch.add(btnSearch);

		JLabel lblKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lblKeyWord);
		lblKeyWord.setBounds(364, 18, 76, 20);
		pnlSearch.add(lblKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setToolTipText("Nhập từ khoá cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		txtKeyWord.setBounds(440, 18, 200, 20);
		txtKeyWord.setVisible(false);
		pnlSearch.add(txtKeyWord);

		cboSearchType = new JComboBox<String>();
		cboSearchType.setToolTipText("Loại phòng cần tìm");
		cboSearchType.addItem("Phòng trống");
		cboSearchType.addItem("Phòng đang sử dụng");
		CustomUI.getInstance().setCustomComboBox(cboSearchType);
		txtBFieldSearchType = CustomUI.getInstance().setCustomCBoxField(cboSearchType);
		cboSearchType.setBounds(440, 18, 200, 20);
		cboSearchType.setVisible(true);
		pnlSearch.add(cboSearchType);

		JLabel lblRoomType = new JLabel("Loại phòng:");
		CustomUI.getInstance().setCustomLabel(lblRoomType);
		lblRoomType.setBounds(562, 65, 90, 20);
		pnlInfo.add(lblRoomType);

		JLabel lblRoomID = new JLabel("Mã phòng:");
		CustomUI.getInstance().setCustomLabel(lblRoomID);
		lblRoomID.setBounds(116, 25, 90, 20);
		pnlInfo.add(lblRoomID);

		txtRoomID = new JTextField();
		txtRoomID.setBounds(206, 25, 180, 20);
		txtRoomID.setToolTipText("Mã phòng");
		CustomUI.getInstance().setCustomTextFieldOff(txtRoomID);
		pnlInfo.add(txtRoomID);

		JLabel lblStatusRoom = new JLabel("Tình trạng:");
		CustomUI.getInstance().setCustomLabel(lblStatusRoom);
		lblStatusRoom.setBounds(562, 27, 90, 20);
		pnlInfo.add(lblStatusRoom);

		cboRoomStatus = new JComboBox<String>();
		cboRoomStatus.addItem("Phòng trống");
		cboRoomStatus.addItem("Phòng đang sử dụng");
		cboRoomStatus.setToolTipText("Tình trạng phòng");
		CustomUI.getInstance().setCustomComboBox(cboRoomStatus);
		txtBFieldRoomStatus = CustomUI.getInstance().setCustomCBoxField(cboRoomStatus);
		cboRoomStatus.setBounds(657, 27, 180, 20);
		pnlInfo.add(cboRoomStatus);

		cboRoomType = new JComboBox<String>();
		cboRoomType.setToolTipText("Chọn loại phòng");
		CustomUI.getInstance().setCustomComboBox(cboRoomType);
		txtBFieldRoomType = CustomUI.getInstance().setCustomCBoxField(cboRoomType);
		cboRoomType.setBounds(657, 65, 180, 20);
		pnlInfo.add(cboRoomType);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		CustomUI.getInstance().setBorderTitlePanelTable(pnlTable,"Danh sách phòng");
		pnlTable.setBounds(18, 270, 1220, 260);
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã phòng", "Tình trạng ", "Vị trí", "Loại phòng" };
		modelTableRoom = new DefaultTableModel(cols, 0);
		tblTableRoom = new JTable(modelTableRoom) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		tblTableRoom.setBackground(new Color(255, 255, 255, 0));
		tblTableRoom.setForeground(new Color(255, 255, 255));
		tblTableRoom.setRowHeight(21);
		tblTableRoom.setFont(new Font("Dialog", Font.PLAIN, 14));
		tblTableRoom.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
		tblTableRoom.getTableHeader().setForeground(Color.decode("#9B17EB"));
		JScrollPane scrTable = new JScrollPane(tblTableRoom, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrTable.getViewport().setBackground(Color.WHITE);
		scrTable.setBounds(10, 20, 1200, 230);
		scrTable.setOpaque(false);
		scrTable.getViewport().setOpaque(false);

		pnlTable.add(scrTable);
		pnlMain.add(pnlTable);
		
		
		btnNextRight = new MyButton(70, 35, "", gra, nextIconRight.getImage(), 0, 0, 14, -8);
		btnNextRight.setBounds(680, 540, 70, 35);
		pnlMain.add(btnNextRight);

		btnDoubleNextRight = new MyButton(70, 35, "", gra, doubleNextRightIcon.getImage(), 0, 0, 14, -8);
		btnDoubleNextRight.setBounds(760, 540, 70, 35);
		pnlMain.add(btnDoubleNextRight);

		btnNextLeft = new MyButton(70, 35, "", gra, nextLeftIcon.getImage(), 0, 0, 14, -8);
		btnNextLeft.setBounds(520, 540, 70, 35);
		pnlMain.add(btnNextLeft);

		btnDoubleNextLeft = new MyButton(70, 35, "", gra, doubleNextLeftIcon.getImage(), 0, 0, 14, -8);
		btnDoubleNextLeft.setBounds(440, 540, 70, 35);
		pnlMain.add(btnDoubleNextLeft);

		txtIndex = new MyTextField("12");
		txtIndex.setBounds(600, 540, 70, 35);
		pnlMain.add(txtIndex);

		btnAdd.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnSearch.addActionListener(this);
		btnUpdate.addActionListener(this);

		tblTableRoom.addMouseListener(this);
		txtLocation.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		txtBFieldSearchType.addMouseListener(this);
		txtBFieldRoomType.addMouseListener(this);
		txtBFieldRoomStatus.addMouseListener(this);

		txtKeyWord.addFocusListener(this);
		txtLocation.addFocusListener(this);
		
		txtKeyWord.addKeyListener(this);
		txtLocation.addKeyListener(this);
		
		cboSearch.addItemListener(this);
		cboSearchType.addItemListener(this);

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
			fDieuHuong winNavigation = new fDieuHuong(staffLogin);
			this.setVisible(false);
			winNavigation.setVisible(true);
		} else if (o.equals(btnAdd)) {
			String message = "";
			if (validData()) {
				Phong room = getRoomDataInForm();
				Boolean insertResult = PhongDAO.getInstance().insertRoom(room);
				String name = "phòng";
				if (insertResult) {
					message = "Thêm " + name + " mới thành công";
					txtRoomID.setText(room.getMaPhong());
					int stt = tblTableRoom.getRowCount();
					addRow(stt, room);
					int lastIndex = tblTableRoom.getRowCount() - 1;
					tblTableRoom.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
					tblTableRoom.scrollRectToVisible(tblTableRoom.getCellRect(lastIndex, lastIndex, true));
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
				int selectedRow = tblTableRoom.getSelectedRow();
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
							tblTableRoom.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tblTableRoom.scrollRectToVisible(tblTableRoom.getCellRect(selectedRow, selectedRow, true));
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
		} else if (o.equals(tblTableRoom)) {
			int selectedRow = tblTableRoom.getSelectedRow();
			txtRoomID.setText(tblTableRoom.getValueAt(selectedRow, 1).toString().trim());
			String roomStatusStr = tblTableRoom.getValueAt(selectedRow, 2).toString().trim();
			if (roomStatusStr.equalsIgnoreCase("Trống"))
				cboRoomStatus.setSelectedIndex(0);
			else
				cboRoomStatus.setSelectedIndex(1);
			txtLocation.setText(tblTableRoom.getValueAt(selectedRow, 3).toString().trim());
			String roomTypeStr = tblTableRoom.getValueAt(selectedRow, 4).toString().trim();
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
		} else if (o.equals(txtLocation)) {
			handler.characterInputLimit(key, txtLocation, 100);
		}
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
		loadRoomTypeList();
		String roomStatusStr = cboRoomStatus.getSelectedItem().toString().trim();
		int roomStatus = roomStatusStr.equalsIgnoreCase("Phòng Trống") ? 0 : 1;
		loadRoomList(PhongDAO.getInstance().getRoomListByStatus(roomStatus));
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
		TableColumnModel columnModel = tblTableRoom.getColumnModel();
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
	}

	/**
	 * tìm kiếm phòng dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		ArrayList<Phong> roomList = null;
		Object keyword = "";
		switch (searchTypeName) {
		case "Tình trạng phòng":
			keyword = cboSearchType.getSelectedItem();
			int roomStatus = 0;
			if (keyword != null) {
				roomStatus = keyword.toString().equalsIgnoreCase("Phòng trống") ? 0 : 1;
			}
			roomList = PhongDAO.getInstance().getRoomListByStatus(roomStatus);
			break;
		case "Loại phòng":
			keyword = cboSearchType.getSelectedItem();
			if (keyword == null) {
				keyword = cboRoomStatus.getItemAt(0).toString().trim();
			}
			roomList = PhongDAO.getInstance().getRoomListByRoomTypeName(keyword.toString());
			break;
		case "Vị trí":
			keyword = txtKeyWord.getText().trim();
			roomList = PhongDAO.getInstance().getRoomListByLocation(keyword.toString());
			break;
		}
		loadRoomList(roomList);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tblTableRoom.getSelectedRow();
		tblTableRoom.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}

	/**
	 * Lấy nút quay lại
	 */
	public JButton getBtnBack() {
		return btnBack;
	}
}
