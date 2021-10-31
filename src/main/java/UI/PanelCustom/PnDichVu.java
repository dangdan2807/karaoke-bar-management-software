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

public class PnDichVu extends JFrame
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));

	private JTable tableService;
	private DefaultTableModel modelTableService;
	private JTextField txtServiceName, txtServiceID, txtBFieldSerType, txtBFieldSearch;
	private JTextField txtBFieldSearchSerType, txtKeyWord;
	private JLabel lbServiceType, lbServiceName, lbQuantityInStock, lbServiceID, lbPrice, lpSearch;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch;
	private JComboBox<String> cboServiceType, cboSearch, cboSearchServiceType;
	private JSpinner spinQuantity, spinPrice;
	private DecimalFormat df = new DecimalFormat("#,###.##");

	private NhanVien staffLogin = null;

	/**
	 * Constructor mặc định của panel Dịch vụ
	 * 
	 * @param staff {@code NhanVien}: nhân viên truy cập
	 */
	public PnDichVu(NhanVien staff) {
		this.staffLogin = staff;
		setSize(1270, 630);
		setLayout(null);
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
				setFont(new Font("DiaLog", Font.BOLD, 24));
				g2.setColor(Color.decode("#9B17EB"));
				g2.drawRoundRect(10, 50, 1238, 530, 20, 20);
				g2.drawRoundRect(9, 49, 1240, 530, 20, 20);
			}
		};
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 1270, 630);
		add(pnMain);

		JPanel pnTitle = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255, 255, 255));
			}
		};
		pnTitle.setBounds(0, 0, 1270, 50);
		pnTitle.setOpaque(false);
		pnTitle.setLayout(null);

		btnBack = new MyButton(100, 35, "Quay lại", gra, CustomUI.BACK_ICON.getImage(), 30, 19);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnTitle.add(btnBack);
		pnMain.add(pnTitle);

		JLabel lbTitle = new JLabel("QUẢN LÝ DỊCH VỤ");
		lbTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 1250, 45);
		pnTitle.add(lbTitle);

		JPanel pnInfo = new JPanel();
		pnInfo.setLayout(null);
		pnInfo.setOpaque(false);
		pnInfo.setBounds(0, 60, 1238, 140);
		pnMain.add(pnInfo);

		txtServiceName = new JTextField();
		txtServiceName.setForeground(Color.WHITE);
		txtServiceName.setBounds(555, 15, 250, 20);
		txtServiceName.setToolTipText("Nhập tên dịch vụ, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceName);
		pnInfo.add(txtServiceName);

		lbServiceType = new JLabel("Loại dịch vụ:");
		lbServiceType.setForeground(Color.WHITE);
		lbServiceType.setFont(new Font("Dialog", Font.BOLD, 13));
		lbServiceType.setBounds(845, 15, 105, 20);
		pnInfo.add(lbServiceType);

		lbServiceName = new JLabel("Tên dịch vụ:");
		lbServiceName.setForeground(Color.WHITE);
		lbServiceName.setFont(new Font("Dialog", Font.BOLD, 13));
		lbServiceName.setBounds(435, 15, 105, 20);
		pnInfo.add(lbServiceName);

		cboServiceType = new JComboBox<String>();
		CustomUI.getInstance().setCustomComboBox(cboServiceType);
		cboServiceType.setToolTipText("Loại dịch vụ");
		txtBFieldSerType = CustomUI.getInstance().setCustomCBoxField(cboServiceType);
		cboServiceType.setBounds(965, 15, 250, 20);
		pnInfo.add(cboServiceType);

		lbQuantityInStock = new JLabel("Số lượng tồn:");
		lbQuantityInStock.setForeground(Color.WHITE);
		lbQuantityInStock.setFont(new Font("Dialog", Font.BOLD, 13));
		lbQuantityInStock.setBounds(435, 45, 115, 16);
		pnInfo.add(lbQuantityInStock);

		spinPrice = new JSpinner(new SpinnerNumberModel(1000f, 0f, Double.MAX_VALUE, 1000f));
		spinPrice.setBounds(145, 45, 250, 20);
		CustomUI.getInstance().setCustomSpinner(spinPrice);
		spinPrice.setToolTipText("Nhập giá bán của dịch vụ và phải là một số dương lớn hơn 0");
		pnInfo.add(spinPrice);

		txtServiceID = new JTextField();
		txtServiceID.setForeground(Color.WHITE);
		txtServiceID.setBounds(145, 15, 250, 20);
		txtServiceID.setToolTipText("Mã dịch vụ");
		CustomUI.getInstance().setCustomTextFieldOff(txtServiceID);

		pnInfo.add(txtServiceID);

		lbServiceID = new JLabel("Mã dịch vụ: ");
		lbServiceID.setFont(new Font("Dialog", Font.BOLD, 13));
		lbServiceID.setForeground(Color.WHITE);
		lbServiceID.setBackground(new Color(249, 249, 249));
		lbServiceID.setBounds(20, 15, 120, 20);
		pnInfo.add(lbServiceID);

		lbPrice = new JLabel("Giá bán:");
		lbPrice.setForeground(Color.WHITE);
		lbPrice.setFont(new Font("Dialog", Font.BOLD, 13));
		lbPrice.setBounds(20, 45, 120, 20);
		pnInfo.add(lbPrice);

		btnAdd = new MyButton(100, 35, "Thêm", gra, CustomUI.ADD_ICON.getImage(), 39, 19);
		btnAdd.setToolTipText("Thêm dịch vụ mới");
		btnAdd.setBounds(20, 93, 100, 35);
		pnInfo.add(btnAdd);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, CustomUI.UPDATE_ICON.getImage(), 43, 19);
		btnUpdate.setToolTipText("Sửa thông tin nhân viên");
		btnUpdate.setBounds(150, 93, 100, 35);
		btnUpdate.setEnabledCustom(false);
		pnInfo.add(btnUpdate);

		btnRefresh = new MyButton(100, 35, "Làm mới", gra, CustomUI.REFRESH_ICON.getImage(), 27, 19);
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
		cboSearch.addItem("Tên dịch vụ");
		cboSearch.addItem("Tên loại dịch vụ");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		cboSearch.setToolTipText("Loại tìm kiếm");
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(140, 18, 160, 20);
		pnSearch.add(cboSearch);

		btnSearch = new MyButton(100, 35, "Tìm kiếm", gra, CustomUI.SEARCH_ICON.getImage(), 30, 19);
		btnSearch.setToolTipText("Tìm kiếm thông tin dịch vụ theo từ khóa");
		btnSearch.setBounds(702, 10, 100, 35);
		pnSearch.add(btnSearch);

		JLabel lpKeyWord = new JLabel("Từ khóa:");
		lpKeyWord.setForeground(Color.WHITE);
		lpKeyWord.setFont(new Font("Dialog", Font.BOLD, 13));
		lpKeyWord.setBounds(364, 18, 76, 20);
		pnSearch.add(lpKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setForeground(Color.WHITE);
		txtKeyWord.setBounds(440, 18, 200, 20);
		txtKeyWord.setToolTipText("Nhập từ khóa cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		pnSearch.add(txtKeyWord);

		cboSearchServiceType = new JComboBox<String>();
		cboSearchServiceType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		CustomUI.getInstance().setCustomComboBox(cboSearchServiceType);
		cboSearchServiceType.setToolTipText("Loại dịch vụ cần tìm kiếm");
		txtBFieldSearchSerType = CustomUI.getInstance().setCustomCBoxField(cboSearchServiceType);
		cboSearchServiceType.setBounds(440, 18, 200, 20);
		cboSearchServiceType.setVisible(false);
		pnSearch.add(cboSearchServiceType);

		spinQuantity = new JSpinner();
		spinQuantity.setModel(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1));
		CustomUI.getInstance().setCustomSpinner(spinQuantity);
		spinQuantity.setBounds(555, 45, 250, 20);
		pnInfo.add(spinQuantity);

		JPanel pnTable = new JPanel();
		pnTable.setBackground(Color.WHITE);
		pnTable.setLayout(null);
		pnTable.setBounds(8, 201, 1240, 384);
		pnTable.setOpaque(false);
		String[] cols = { "STT", "Mã dịch vụ", "Tên dịch vụ", "Giá bán", "Số lượng", "Loại dịch vụ" };
		modelTableService = new DefaultTableModel(cols, 0);
		tableService = new JTable(modelTableService);
		tableService.setBackground(new Color(255, 255, 255, 0));
		tableService.setForeground(new Color(255, 255, 255));
		tableService.setRowHeight(21);
		tableService.setFont(new Font("Dialog", Font.PLAIN, 14));
		tableService.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
		tableService.getTableHeader().setForeground(Color.decode("#9B17EB"));
		JScrollPane scpTable = new JScrollPane(tableService, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpTable.getViewport().setBackground(Color.WHITE);
		scpTable.setBounds(10, 10, 1220, 350);
		scpTable.setOpaque(false);
		scpTable.getViewport().setOpaque(false);
		pnTable.add(scpTable);

		pnMain.add(pnTable);

		tableService.addMouseListener(this);
		txtBFieldSerType.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		txtBFieldSearchSerType.addMouseListener(this);

		btnAdd.addActionListener(this);
		btnBack.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnSearch.addActionListener(this);

		cboSearch.addItemListener(this);
		cboSearchServiceType.addItemListener(this);

		txtServiceName.addFocusListener(this);
		((JSpinner.DefaultEditor) spinQuantity.getEditor()).getTextField().addFocusListener(this);
		txtKeyWord.addFocusListener(this);
		((JSpinner.DefaultEditor) spinPrice.getEditor()).getTextField().addFocusListener(this);

		txtKeyWord.addKeyListener(this);

		allLoaded();
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
			new PnDichVu(staff).setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnSearch)) {
			searchEventUsingBtnSearch();
		} else if (o.equals(btnAdd)) {
			if (validData()) {
				String message = "";
				if (validData()) {
					DichVu service = getServiceDataInForm();
					Boolean result = DichVuDAO.getInstance().insertService(service);
					if (result) {
						message = "Thêm dịch vụ mới thành công";
						txtServiceID.setText(service.getMaDichVu());
						int stt = tableService.getRowCount();
						addRow(stt, service);
						int lastIndex = tableService.getRowCount() - 1;
						tableService.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
						tableService.scrollRectToVisible(tableService.getCellRect(lastIndex, lastIndex, true));
						JOptionPane.showMessageDialog(this, message);
						btnAdd.setEnabledCustom(false);
						btnUpdate.setEnabledCustom(true);
					} else {
						message = "Thêm dịch vụ thất bại";
						JOptionPane.showMessageDialog(this, message);
					}
				}
			}
		} else if (o.equals(btnBack)) {
			fDieuHuong f = new fDieuHuong(staffLogin);
			this.setVisible(false);
			f.setVisible(true);
		} else if (o.equals(btnRefresh)) {
			btnAdd.setEnabledCustom(true);
			btnUpdate.setEnabledCustom(false);
			txtServiceID.setText("");
			txtServiceName.setText("");
			cboServiceType.setSelectedIndex(0);
			spinPrice.setValue((double) 0);
			spinQuantity.setValue((int) 1);
			removeSelectionInterval();
		} else if (o.equals(btnUpdate)) {
			if (validData()) {
				DichVu service = getServiceDataInForm();
				String serviceName = DichVuDAO.getInstance().getServiceNameById(service.getMaDichVu());
				String message = "";
				int selectedRow = tableService.getSelectedRow();
				String name = "dịch vụ";
				if (selectedRow == -1) {
					message = "Hãy chọn " + name + " mà bạn cần cập nhật thông tin";
					JOptionPane.showConfirmDialog(this, message, "Thông báo", JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					message = "Xác nhận cập nhật thông tin " + name + ": " + serviceName;
					int choose = JOptionPane.showConfirmDialog(this, message,
							"Xác nhận cập nhật thông tin " + name + "", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (choose == JOptionPane.OK_OPTION) {
						Boolean result = DichVuDAO.getInstance().updateInfoService(service);
						if (result) {
							message = "Cập nhật thông tin " + name + " thành công";
							updateRow(selectedRow, service);
							btnAdd.setEnabledCustom(false);
							btnUpdate.setEnabledCustom(true);
							tableService.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tableService.scrollRectToVisible(tableService.getCellRect(selectedRow, selectedRow, true));
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
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSerType)) {
			cboServiceType.showPopup();
		} else if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		} else if (o.equals(txtBFieldSearchSerType)) {
			cboSearchServiceType.showPopup();
		} else if (o.equals(tableService)) {
			int selectedRow = tableService.getSelectedRow();
			txtServiceID.setText(tableService.getValueAt(selectedRow, 1).toString().trim());
			txtServiceName.setText(tableService.getValueAt(selectedRow, 2).toString().trim());
			String priceStr = tableService.getValueAt(selectedRow, 3).toString().trim().replace(",", "");
			spinPrice.setValue(Integer.parseInt(priceStr));
			String quantityStr = tableService.getValueAt(selectedRow, 4).toString().trim().replace(",", "");
			spinQuantity.setValue(Integer.parseInt(quantityStr));
			cboServiceType.setSelectedItem(tableService.getValueAt(selectedRow, 5).toString().trim());
			btnUpdate.setEnabledCustom(true);
			btnAdd.setEnabledCustom(false);
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
		if (o.equals(txtBFieldSerType)) {
			cboServiceType.showPopup();
			cboServiceType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldSearchSerType)) {
			cboSearchServiceType.showPopup();
			cboSearchServiceType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSerType)) {
			cboServiceType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtBFieldSearchSerType)) {
			cboSearchServiceType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
			String searchType = cboSearch.getSelectedItem().toString();
			txtKeyWord.setText("");
			if (searchType.equalsIgnoreCase("Tên loại dịch vụ")) {
				txtKeyWord.setVisible(false);
				cboSearchServiceType.setVisible(true);
			} else {
				txtKeyWord.setVisible(true);
				cboSearchServiceType.setVisible(false);
				if (searchType.equalsIgnoreCase("Tất cả")) {
					CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
				} else if (searchType.equalsIgnoreCase("Tên dịch vụ")) {
					CustomUI.getInstance().setCustomTextFieldOn(txtKeyWord);
				}
			}
			removeSelectionInterval();
			searchEventUsingBtnSearch();
		} else if (o.equals(cboSearchServiceType)) {
			removeSelectionInterval();
			searchEventUsingBtnSearch();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyWord)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchEventUsingBtnSearch();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtServiceName);
		} else if (o.equals(((JSpinner.DefaultEditor) spinPrice.getEditor()).getTextField())) {
			spinPrice.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		} else if (o.equals(((JSpinner.DefaultEditor) spinQuantity.getEditor()).getTextField())) {
			spinQuantity.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceName);
		} else if (o.equals(((JSpinner.DefaultEditor) spinPrice.getEditor()).getTextField())) {
			spinPrice.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		} else if (o.equals(((JSpinner.DefaultEditor) spinQuantity.getEditor()).getTextField())) {
			spinQuantity.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	public void allLoaded() {
		reSizeColumnTable();
		loadServiceTypeList(LoaiDichVuDAO.getInstance().getServiceTypeList());
		loadServiceList(DichVuDAO.getInstance().getServiceList());
	}

	/**
	 * chuyển đổi thông tin trong form thành đối tượng DichVu
	 * 
	 * @return {@code DichVu}: dịch vụ
	 */
	private DichVu getServiceDataInForm() {
		String serviceId = txtServiceID.getText().trim();
		String staffName = txtServiceName.getText().trim();
		String serviceTypeName = cboServiceType.getSelectedItem().toString().trim();
		Double price = Double.parseDouble(spinPrice.getValue().toString());
		int quantity = Integer.parseInt(spinQuantity.getValue().toString());
		if (serviceId.equals("") || serviceId.length() <= 0) {
			serviceId = createNewServiceID();
		}
		LoaiDichVu serviceType = LoaiDichVuDAO.getInstance().getServiceTypeByName(serviceTypeName);
		return new DichVu(serviceId, staffName, price, quantity, serviceType);
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
		boolean valid = ValidationData.getInstance().ValidName(this, txtServiceName, "Tên dịch vụ", 100, 0);
		if (!valid) {
			return valid;
		}
		return true;
	}

	/**
	 * Tự động tạo mã dịch vụ mới tăng theo thứ tự tăng dần
	 * 
	 * @return {@code String}: mã dịch vụ mới
	 */
	private String createNewServiceID() {
		String lastServiceId = DichVuDAO.getInstance().getLastServiceID();
		String idStr = "DV";
		int oldNumberStaffID = 0;
		if (lastServiceId.equalsIgnoreCase("") == false || lastServiceId != null) {
			oldNumberStaffID = Integer.parseInt(lastServiceId.replace(idStr, ""));
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
	 * Thêm khoảng trắng vào trước và sao chuỗi được truyền vào
	 * 
	 * @param str {@code String}: chuỗi cần xử lý
	 * @return {@code String}: chuỗi đã xử lý
	 */
	private String addSpaceToString(String str) {
		return " " + str + " ";
	}

	/**
	 * Thêm dòng vào danh sách dịch vụ đang hiển thị
	 * 
	 * @param stt     {@code int}: số thứ tự
	 * @param service {@code DichVu}: dịch vụ cần được thêm
	 */
	private void addRow(int stt, DichVu service) {
		String sttStr = df.format(stt);
		String priceStr = df.format(service.getGiaBan());
		String quantityStr = df.format(service.getSoLuongTon());
		String serviceTypeName = service.getLoaiDV().getTenLDV();
		modelTableService.addRow(new Object[] { sttStr, addSpaceToString(service.getMaDichVu()),
				addSpaceToString(service.getTenDichVu()), addSpaceToString(priceStr), addSpaceToString(quantityStr),
				addSpaceToString(serviceTypeName) });
		modelTableService.fireTableDataChanged();
	}

	/**
	 * Cập nhật thông tin một dòng khi biết dòng mà thông tin dịch vụ
	 * 
	 * @param selectedRow {@code int}: dòng được chọn
	 * @param service     {@code DichVu}: dịch vụ cần cập nhật
	 */
	private void updateRow(int selectedRow, DichVu service) {
		String priceStr = df.format(service.getGiaBan());
		String quantityStr = df.format(service.getSoLuongTon());
		String serviceTypeName = service.getLoaiDV().getTenLDV();
		modelTableService.setValueAt(addSpaceToString(service.getTenDichVu()), selectedRow, 2);
		modelTableService.setValueAt(addSpaceToString(priceStr), selectedRow, 3);
		modelTableService.setValueAt(addSpaceToString(quantityStr), selectedRow, 4);
		modelTableService.setValueAt(addSpaceToString(serviceTypeName), selectedRow, 5);
		modelTableService.fireTableDataChanged();
	}

	/**
	 * Hiển thị danh sách dịch vụ
	 * 
	 * @param serviceList {@code ArrayList<DichVu>}: danh sách dịch vụ
	 */
	private void loadServiceList(ArrayList<DichVu> serviceList) {
		modelTableService.getDataVector().removeAllElements();
		modelTableService.fireTableDataChanged();
		int i = 1;
		for (DichVu item : serviceList) {
			addRow(i++, item);
		}
	}

	/**
	 * Hiển thị danh sách dịch vụ vào comboBox
	 * 
	 * @param serviceTypeList {@code ArrayList<DichVu>}: danh sách dịch vụ
	 */
	private void loadServiceTypeList(ArrayList<LoaiDichVu> serviceTypeList) {
		cboServiceType.removeAllItems();
		cboServiceType.repaint();
		cboSearchServiceType.removeAllItems();
		cboSearchServiceType.repaint();
		for (LoaiDichVu serviceType : serviceTypeList) {
			cboServiceType.addItem(serviceType.getTenLDV());
			cboSearchServiceType.addItem(serviceType.getTenLDV());
		}
	}

	/**
	 * Thay đổi kích thước cột
	 */
	private void reSizeColumnTable() {
		TableColumnModel columnModel = tableService.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(70);
		columnModel.getColumn(1).setPreferredWidth(130);
		columnModel.getColumn(2).setPreferredWidth(250);
		columnModel.getColumn(3).setPreferredWidth(200);
		columnModel.getColumn(4).setPreferredWidth(200);
		columnModel.getColumn(5).setPreferredWidth(200);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		columnModel.getColumn(0).setCellRenderer(centerRenderer);
		columnModel.getColumn(1).setCellRenderer(centerRenderer);
		columnModel.getColumn(3).setCellRenderer(rightRenderer);
		columnModel.getColumn(4).setCellRenderer(rightRenderer);
	}

	/**
	 * tìm kiếm dịch vụ dựa trên điều kiện khi kích hoạt event trên btnSearch
	 */
	private void searchEventUsingBtnSearch() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		String keyword = "";
		ArrayList<DichVu> serviceList = new ArrayList<DichVu>();
		if (searchTypeName.equalsIgnoreCase("Tất cả")) {
			serviceList = DichVuDAO.getInstance().getServiceList();
		} else if (searchTypeName.equalsIgnoreCase("Tên dịch vụ")) {
			keyword = txtKeyWord.getText().trim();
			serviceList = DichVuDAO.getInstance().getServiceListByName(keyword);
		} else if (searchTypeName.equalsIgnoreCase("Tên loại dịch vụ")) {
			keyword = cboSearchServiceType.getSelectedItem().toString().trim();
			serviceList = DichVuDAO.getInstance().getServiceListByServiceTypeName(keyword);
		}
		loadServiceList(serviceList);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tableService.getSelectedRow();
		tableService.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
	}
}
