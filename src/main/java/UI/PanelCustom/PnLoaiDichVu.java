package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.*;

import DAO.LoaiDichVuDAO;
import DAO.NhanVienDAO;
import DAO.ValidationData;
import UI.fDieuHuong;
import entity.LoaiDichVu;
import entity.NhanVien;

public class PnLoaiDichVu extends JFrame
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon addIcon = CustomUI.ADD_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon updateIcon = CustomUI.UPDATE_ICON;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private JTable tableServiceType;
	private DefaultTableModel modelTable;
	private MyButton btnSearch, btnAdd, btnUpdate, btnRefresh, btnBack;
	private JComboBox<String> cboSearch, cboSearchType;
	private JLabel lpSearch;
	private JTextField txtBFieldSearch, txtKeyWord, txtBFieldSearchType, txtServiceTypeID;
	private JTextField txtServiceTypeName;
	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;

	public PnLoaiDichVu(NhanVien staff) {
		this.staffLogin = staff;
		setSize(1270, 630);
		this.setLayout(null);
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
		add(pnMain);

		JPanel pnTitle = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255, 255, 255));
				setFont(new Font("Dialog", Font.BOLD, 20));
				g2.drawString("QUẢN LÝ LOẠI DỊCH VỤ", 500, 33);
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

		btnAdd = new MyButton(100, 35, "Thêm", gra, addIcon.getImage(), 39, 19);
		btnAdd.setToolTipText("Thêm loại dịch vụ mới sau khi đã điền đủ thông tin");
		btnAdd.setBounds(20, 93, 100, 35);
		pnInfo.add(btnAdd);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, updateIcon.getImage(), 43, 19);
		btnUpdate.setToolTipText("Sửa thông tin loại dịch vụ");
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

		lpSearch = new JLabel("Tìm kiếm theo:");
		lpSearch.setForeground(Color.WHITE);
		lpSearch.setFont(new Font("Dialog", Font.BOLD, 13));
		lpSearch.setBounds(30, 18, 100, 20);
		pnSearch.add(lpSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên loại dịch vụ");
		cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboSearch.setOpaque(false);
		cboSearch.setEditable(true);
		cboSearch.setUI(new BasicComboBoxUI());
		txtBFieldSearch = (JTextField) cboSearch.getEditor().getEditorComponent();
		txtBFieldSearch.setBorder(BorderFactory.createEmptyBorder());
		txtBFieldSearch.setBackground(new Color(246, 210, 255, 50));
		txtBFieldSearch.setForeground(Color.WHITE);
		txtBFieldSearch.setEditable(false);
		txtBFieldSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
		cboSearch.setBounds(140, 18, 160, 20);
		cboSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

		pnSearch.add(cboSearch);

		btnSearch = new MyButton(100, 35, "Tìm kiếm", gra, searchIcon.getImage(), 26, 19);
		btnSearch.setToolTipText("Tìm kiếm thông tin loại dịch vụ theo từ khóa");
		btnSearch.setBounds(702, 10, 100, 35);
		pnSearch.add(btnSearch);

		JLabel lpKeyWord = new JLabel("Từ khóa:");
		lpKeyWord.setForeground(Color.WHITE);
		lpKeyWord.setFont(new Font("Dialog", Font.BOLD, 13));
		lpKeyWord.setBounds(364, 18, 76, 20);
		pnSearch.add(lpKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setForeground(Color.WHITE);
		txtKeyWord.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtKeyWord.setBounds(440, 18, 200, 20);
		txtKeyWord.setToolTipText("Nhập từ khóa cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		pnSearch.add(txtKeyWord);

		// sử dụng trong tương lai
		cboSearchType = new JComboBox<String>();
		cboSearchType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboSearchType.setOpaque(false);
		cboSearchType.setEditable(true);
		cboSearchType.setUI(new BasicComboBoxUI());
		txtBFieldSearchType = CustomUI.getInstance().setCustomCBoxField(cboSearchType);
		cboSearchType.setBounds(440, 18, 200, 20);
		cboSearchType.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnSearch.add(cboSearchType);

		JLabel lbServiceTypeID = new JLabel("Mã loại dịch vụ:");
		lbServiceTypeID.setForeground(Color.WHITE);
		lbServiceTypeID.setFont(new Font("Dialog", Font.BOLD, 13));
		lbServiceTypeID.setBounds(133, 30, 120, 20);
		pnInfo.add(lbServiceTypeID);

		txtServiceTypeID = new JTextField();
		txtServiceTypeID.setEditable(false);
		txtServiceTypeID.setForeground(Color.WHITE);
		txtServiceTypeID.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtServiceTypeID.setBounds(258, 30, 250, 20);
		txtServiceTypeID.setToolTipText("Mã loại dịch vụ");
		CustomUI.getInstance().setCustomTextFieldOff(txtServiceTypeID);
		txtServiceTypeID.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnInfo.add(txtServiceTypeID);

		JLabel lbServiceTypeName = new JLabel("Tên loại dịch vụ:");
		lbServiceTypeName.setForeground(Color.WHITE);
		lbServiceTypeName.setFont(new Font("Dialog", Font.BOLD, 13));
		lbServiceTypeName.setBounds(746, 30, 120, 20);
		pnInfo.add(lbServiceTypeName);

		txtServiceTypeName = new JTextField();
		txtServiceTypeName.setForeground(Color.WHITE);
		txtServiceTypeName.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtServiceTypeName.setBounds(875, 30, 250, 20);
		txtServiceTypeName.setToolTipText("Tên loại dịch vụ, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceTypeName);
		pnInfo.add(txtServiceTypeName);
		cboSearchType.setVisible(false);
		btnSearch.addActionListener(this);
		btnSearch.addMouseListener(this);
		cboSearch.addActionListener(this);

		JPanel pnTable = new JPanel();
		pnTable.setBackground(Color.WHITE);
		pnTable.setLayout(null);
		pnTable.setBounds(8, 201, 1240, 384);
		pnTable.setOpaque(false);
		String[] cols = { "STT", "Mã loại dịch vụ", "Tên loại dịch vụ " };
		modelTable = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		tableServiceType = new JTable(modelTable);
		tableServiceType.setBackground(new Color(255, 255, 255, 0));
		tableServiceType.setForeground(new Color(255, 255, 255));
		tableServiceType.setRowHeight(21);
		tableServiceType.setFont(new Font("Dialog", Font.PLAIN, 13));
		tableServiceType.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
		tableServiceType.getTableHeader().setForeground(Color.decode("#9B17EB"));
		JScrollPane scpTable = new JScrollPane(tableServiceType, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpTable.getViewport().setBackground(Color.WHITE);
		scpTable.setBounds(10, 10, 1220, 350);
		scpTable.setOpaque(false);
		scpTable.getViewport().setOpaque(false);

		pnTable.add(scpTable);
		pnMain.add(pnTable);

		tableServiceType.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);

		txtKeyWord.addFocusListener(this);
		txtServiceTypeName.addFocusListener(this);

		txtKeyWord.addKeyListener(this);

		cboSearch.addItemListener(this);

		btnAdd.addActionListener(this);
		btnBack.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnSearch.addActionListener(this);
		btnUpdate.addActionListener(this);

		allLoaded();
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
			new PnLoaiDichVu(staff).setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object.equals(btnBack)) {
			fDieuHuong f = new fDieuHuong(staffLogin);
			this.setVisible(false);
			f.setVisible(true);
		} else if (object.equals(btnAdd)) {
			if (validData()) {
				String message = "";
				if (validData()) {
					LoaiDichVu serviceType = getServiceTypeDataInForm();
					Boolean result = LoaiDichVuDAO.getInstance().insertService(serviceType);
					if (result) {
						message = "Thêm loại dịch vụ mới thành công";
						txtServiceTypeID.setText(serviceType.getMaLDV());
						int stt = tableServiceType.getRowCount();
						addRow(stt, serviceType);
						int lastIndex = tableServiceType.getRowCount() - 1;
						tableServiceType.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
						tableServiceType.scrollRectToVisible(tableServiceType.getCellRect(lastIndex, lastIndex, true));
						JOptionPane.showMessageDialog(this, message);
						btnAdd.setEnabledCustom(false);
						btnUpdate.setEnabledCustom(true);
					} else {
						message = "Thêm loại dịch vụ thất bại";
						JOptionPane.showMessageDialog(this, message);
					}
				}
			}
		} else if (object.equals(btnRefresh)) {
			txtServiceTypeID.setText("");
			txtServiceTypeName.setText("");
			btnUpdate.setEnabledCustom(false);
			btnAdd.setEnabledCustom(true);
			removeSelectionInterval();
		} else if (object.equals(btnSearch)) {
			searchEventUsingBtnSearch();
		} else if (object.equals(btnUpdate)) {
			if (validData()) {
				LoaiDichVu serviceType = getServiceTypeDataInForm();
				LoaiDichVu serviceTypeOld = LoaiDichVuDAO.getInstance().getServiceTypeById(serviceType.getMaLDV());
				String message = "";
				String name = "loại dịch vụ";
				int selectedRow = tableServiceType.getSelectedRow();
				if (selectedRow == -1) {
					message = "Hãy chọn " + name + " mà bạn cần cập nhật";
					JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					message = "Xác nhận cập nhật thông tin " + name + ": " + serviceTypeOld.getTenLDV();
					int choose = JOptionPane.showConfirmDialog(this, message, "Xác nhận cập nhật thông tin " + name,
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (choose == JOptionPane.OK_OPTION) {
						Boolean result = LoaiDichVuDAO.getInstance().updateInfoServiceType(serviceType);
						if (result) {
							message = "Cập nhật thông tin " + name + " thành công";
							updateRow(selectedRow, serviceType);
							btnAdd.setEnabledCustom(false);
							btnUpdate.setEnabledCustom(true);
							tableServiceType.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tableServiceType
									.scrollRectToVisible(tableServiceType.getCellRect(selectedRow, selectedRow, true));
						} else {
							message = "Cập nhật thông tin " + name + " thất bại";
						}
						JOptionPane.showMessageDialog(this, message);
					}
				}
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
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
		} else if (o.equals(tableServiceType)) {
			int selectedRow = tableServiceType.getSelectedRow();
			txtServiceTypeID.setText(tableServiceType.getValueAt(selectedRow, 1).toString().trim());
			txtServiceTypeName.setText(tableServiceType.getValueAt(selectedRow, 2).toString().trim());
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
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyWord)) {
			txtKeyWord.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtServiceTypeName)) {
			txtServiceTypeName.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyWord)) {
			txtKeyWord.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtServiceTypeName)) {
			txtServiceTypeName.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	public void allLoaded() {
		reSizeColumnTable();
		loadServiceTypeList(LoaiDichVuDAO.getInstance().getServiceTypeList());
	}

	/**
	 * Tự động tạo mã loại dịch vụ mới tăng theo thứ tự tăng dần
	 * 
	 * @return <code>String</code>: mã dịch vụ mới
	 */
	private String createNewServiceTypeID() {
		String lastStrId = LoaiDichVuDAO.getInstance().getLastServiceTypeID();
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
	 * chuyển đổi thông tin trong form thành đối tượng DichVu
	 * 
	 * @return <code>LoaiDichVu</code>: dịch vụ
	 */
	private LoaiDichVu getServiceTypeDataInForm() {
		String serviceTypeID = txtServiceTypeID.getText().trim();
		String serviceTypeName = txtServiceTypeName.getText().trim();
		LoaiDichVu serviceType = new LoaiDichVu(serviceTypeID);
		if (!serviceTypeID.equals("")) {
			serviceType = LoaiDichVuDAO.getInstance().getServiceTypeById(serviceTypeID);
			if (serviceType == null) {
				serviceType = new LoaiDichVu(serviceTypeID, serviceTypeName);
			}
		} else {
			serviceTypeID = createNewServiceTypeID();
			serviceType.setMaLDV(serviceTypeID);
			serviceType.setTenLDV(serviceTypeName);
		}
		return serviceType;
	}

	/**
	 * Kiểm tra thông tin trong form
	 * 
	 * @return <code>boolean</code>: true nếu hợp lê, false nếu không hợp lệ
	 */
	private boolean validData() {
		boolean valid = ValidationData.getInstance().ValidName(this, txtServiceTypeName, "Tên loại dịch vụ", 100, 0);
		return valid;
	}

	/**
	 * Thêm khoảng trắng vào trước và sao chuỗi được truyền vào
	 * 
	 * @param str <code>String</code>: chuỗi cần xử lý
	 * @return <code>String</code>: chuỗi đã xử lý
	 */
	private String addSpaceToString(String str) {
		return " " + str + " ";
	}

	/**
	 * Thêm dòng vào danh sách loại dịch vụ đang hiển thị
	 * 
	 * @param stt         số thứ tự
	 * @param serviceType <code>LoaiDichVu</code>: loại dịch vụ cần được thêm
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
	 * @param selectedRow <code>Int</code>: dòng được chọn
	 * @param serviceType <code>LoaiDichVu</code: loại dịch vụ cần cập nhật
	 */
	private void updateRow(int selectedRow, LoaiDichVu serviceType) {
		String serviceTypeName = serviceType.getTenLDV();
		modelTable.setValueAt(addSpaceToString(serviceTypeName), selectedRow, 2);
		modelTable.fireTableDataChanged();
	}

	/**
	 * Hiển thị danh sách dịch vụ
	 * 
	 * @param serviceTypeList <code>ArrayList LoaiDichVu </code>: danh sách loại
	 *                        dịch vụ
	 */
	private void loadServiceTypeList(ArrayList<LoaiDichVu> serviceTypeList) {
		modelTable.getDataVector().removeAllElements();
		modelTable.fireTableDataChanged();
		int i = 1;
		for (LoaiDichVu item : serviceTypeList) {
			addRow(i++, item);
		}
	}

	/**
	 * Thay đổi kích thước cột
	 */
	private void reSizeColumnTable() {

		tableServiceType.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableServiceType.getColumnModel().getColumn(1).setPreferredWidth(130);
		tableServiceType.getColumnModel().getColumn(2).setPreferredWidth(250);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tableServiceType.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	}

	/**
	 * tìm kiếm loại dịch vụ dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		ArrayList<LoaiDichVu> serviceTypeList = null;
		String keyword = txtKeyWord.getText().trim();
		serviceTypeList = LoaiDichVuDAO.getInstance().getServiceTypeListByName(keyword);
		loadServiceTypeList(serviceTypeList);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tableServiceType.getSelectedRow();
		tableServiceType.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}
}
