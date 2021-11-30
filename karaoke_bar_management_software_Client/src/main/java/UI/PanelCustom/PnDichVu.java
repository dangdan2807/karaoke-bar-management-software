package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import DAO.*;
import Event_Handlers.InputEventHandler;
import Event_Handlers.ValidationData;
import UI.*;
import entity.*;

/**
 * Giao diện quản lý dịch vụ của phần mềm
 * <p>
 * Người tham gia thiết kế: Đỗ Thị Tường Vi
 * <p>
 * Ngày tạo: 26/10/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm phân trang cho phần mềm
 */
public class PnDichVu extends JPanel
		implements ActionListener, MouseListener, ItemListener, KeyListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -356436856151590278L;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon nextIconRight = new ImageIcon(
			CustomUI.NEXT_RIGHT_ICON.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextRightIcon = new ImageIcon(
			CustomUI.DOUBLE_NEXT_RIGHT_ICON.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon nextLeftIcon = new ImageIcon(
			CustomUI.NEXT_LEFT_ICON.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	private ImageIcon doubleNextLeftIcon = new ImageIcon(
			CustomUI.DOUBLE_NEXT_LEFT_ICON.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));

	private JTable tblTableService;
	private DefaultTableModel modelTableService;
	private JTextField txtServiceName, txtServiceID, txtBFieldSerType, txtBFieldSearch;
	private JTextField txtBFieldSearchSerType, txtKeyWord;
	private JLabel lblServiceType, lblServiceName, lblQuantityInStock, lblServiceID, lblPrice, lblSearch;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack, btnSearch, btnNextToRight, btnNextToLast;
	private MyButton btnNextToLeft, btnNextToFirst;
	private JComboBox<String> cboServiceType, cboSearch, cboSearchServiceType;
	private JSpinner spnQuantity, spnPrice;
	private PnTextFiledPaging txtPaging;

	private int lineNumberDisplayed = 10;
	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;
	private SecurityManager securityManager = System.getSecurityManager();

	/**
	 * Constructor mặc định của panel Dịch vụ
	 * 
	 * @param staff {@code NhanVien}: nhân viên truy cập
	 */
	public PnDichVu(NhanVien staff) {
		if (securityManager == null) {
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
		}

		this.staffLogin = staff;
		setSize(1270, 630);
		setLayout(null);
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
				setFont(new Font("DiaLog", Font.BOLD, 24));
				g2.setColor(Color.decode("#9B17EB"));
				g2.drawRoundRect(10, 50, 1238, 530, 20, 20);
				g2.drawRoundRect(9, 49, 1240, 530, 20, 20);
			}
		};
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 1270, 630);
		add(pnlMain);

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

		btnBack = new MyButton(100, 35, "Quay lại", gra, CustomUI.BACK_ICON.getImage(), 30, 19);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlTitle.add(btnBack);
		pnlMain.add(pnlTitle);

		JLabel lblTitle = new JLabel("QUẢN LÝ DỊCH VỤ");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lblTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(0, 60, 1238, 191);
		pnlMain.add(pnlInfo);

		txtServiceName = new JTextField();
		txtServiceName.setBounds(685, 21, 250, 20);
		txtServiceName.setToolTipText("Nhập tên dịch vụ, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceName);
		pnlInfo.add(txtServiceName);

		lblServiceType = new JLabel("Loại dịch vụ:");
		CustomUI.getInstance().setCustomLabel(lblServiceType);
		lblServiceType.setBounds(89, 91, 105, 20);
		pnlInfo.add(lblServiceType);

		lblServiceName = new JLabel("Tên dịch vụ:");
		CustomUI.getInstance().setCustomLabel(lblServiceName);
		lblServiceName.setBounds(565, 21, 105, 20);
		pnlInfo.add(lblServiceName);

		cboServiceType = new JComboBox<String>();
		CustomUI.getInstance().setCustomComboBox(cboServiceType);
		cboServiceType.setToolTipText("Loại dịch vụ");
		txtBFieldSerType = CustomUI.getInstance().setCustomCBoxField(cboServiceType);
		cboServiceType.setBounds(214, 91, 250, 20);
		pnlInfo.add(cboServiceType);

		lblQuantityInStock = new JLabel("Số lượng tồn:");
		CustomUI.getInstance().setCustomLabel(lblQuantityInStock);
		lblQuantityInStock.setBounds(565, 56, 115, 16);
		pnlInfo.add(lblQuantityInStock);

		spnPrice = new JSpinner(new SpinnerNumberModel(1000f, 0f, Double.MAX_VALUE, 1000f));
		spnPrice.setBounds(214, 56, 250, 20);
		CustomUI.getInstance().setCustomSpinner(spnPrice);
		spnPrice.setToolTipText("Nhập giá bán của dịch vụ và phải là một số dương lớn hơn 0");
		pnlInfo.add(spnPrice);

		txtServiceID = new JTextField();
		txtServiceID.setBounds(214, 21, 250, 20);
		txtServiceID.setToolTipText("Mã dịch vụ");
		CustomUI.getInstance().setCustomTextFieldOff(txtServiceID);

		pnlInfo.add(txtServiceID);

		lblServiceID = new JLabel("Mã dịch vụ: ");
		CustomUI.getInstance().setCustomLabel(lblServiceID);
		lblServiceID.setBounds(89, 21, 120, 20);
		pnlInfo.add(lblServiceID);

		lblPrice = new JLabel("Giá bán:");
		CustomUI.getInstance().setCustomLabel(lblPrice);
		lblPrice.setBounds(89, 56, 120, 20);
		pnlInfo.add(lblPrice);

		btnAdd = new MyButton(130, 35, "Thêm", gra, CustomUI.ADD_ICON.getImage(), 50, 19, 10, 6);
		btnAdd.setToolTipText("Thêm loại dịch vụ mới sau khi đã điền đủ thông tin");
		btnAdd.setBounds(1023, 10, 130, 35);
		pnlInfo.add(btnAdd);

		btnUpdate = new MyButton(130, 35, "Sửa", gra, CustomUI.UPDATE_ICON.getImage(), 55, 19, 10, 6);
		btnUpdate.setToolTipText("Sửa thông tin loại dịch vụ");
		btnUpdate.setBounds(1023, 50, 130, 35);
		btnUpdate.setEnabledCustom(false);
		pnlInfo.add(btnUpdate);

		btnRefresh = new MyButton(130, 35, "Làm mới", gra, CustomUI.REFRESH_ICON.getImage(), 40, 19, 10, 5);
		btnRefresh.setToolTipText("Làm mới form");
		btnRefresh.setBounds(1023, 90, 130, 35);
		pnlInfo.add(btnRefresh);

		JPanel pnlSearch = new JPanel();
		pnlSearch.setBounds(190, 138, 851, 53);
		pnlSearch.setOpaque(false);
		pnlSearch.setLayout(null);
		pnlInfo.add(pnlSearch);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(30, 18, 100, 20);
		pnlSearch.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên dịch vụ");
		cboSearch.addItem("Tên loại dịch vụ");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		cboSearch.setToolTipText("Loại tìm kiếm");
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(140, 18, 160, 20);
		pnlSearch.add(cboSearch);

		btnSearch = new MyButton(130, 35, "Tìm kiếm", gra, CustomUI.SEARCH_ICON.getImage(), 40, 19, 10, 5);
		btnSearch.setToolTipText("Tìm kiếm thông tin dịch vụ theo từ khóa");
		btnSearch.setBounds(702, 10, 130, 35);
		pnlSearch.add(btnSearch);

		JLabel lblKeyWord = new JLabel("Từ khóa:");
		CustomUI.getInstance().setCustomLabel(lblKeyWord);
		lblKeyWord.setBounds(364, 18, 76, 20);
		pnlSearch.add(lblKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setForeground(Color.WHITE);
		txtKeyWord.setBounds(440, 18, 200, 20);
		txtKeyWord.setToolTipText("Nhập từ khóa cần tìm kiếm");
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyWord);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		pnlSearch.add(txtKeyWord);

		cboSearchServiceType = new JComboBox<String>();
		cboSearchServiceType.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		CustomUI.getInstance().setCustomComboBox(cboSearchServiceType);
		cboSearchServiceType.setToolTipText("Loại dịch vụ cần tìm kiếm");
		txtBFieldSearchSerType = CustomUI.getInstance().setCustomCBoxField(cboSearchServiceType);
		cboSearchServiceType.setBounds(440, 18, 200, 20);
		cboSearchServiceType.setVisible(false);
		pnlSearch.add(cboSearchServiceType);

		spnQuantity = new JSpinner();
		spnQuantity.setModel(new SpinnerNumberModel(1, 0, Integer.MAX_VALUE, 1));
		CustomUI.getInstance().setCustomSpinner(spnQuantity);
		spnQuantity.setBounds(685, 56, 250, 20);
		pnlInfo.add(spnQuantity);

		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		CustomUI.getInstance().setBorderTitlePanelTable(pnlTable, "Danh sách dịch vụ");
		pnlTable.setBounds(18, 270, 1220, 265);
		pnlTable.setOpaque(false);

		String[] cols = { "STT", "Mã dịch vụ", "Tên dịch vụ", "Giá bán", "Số lượng", "Loại dịch vụ" };
		modelTableService = new DefaultTableModel(cols, 0);
		tblTableService = new JTable(modelTableService);
		CustomUI.getInstance().setCustomTable(tblTableService);
		tblTableService.setRowHeight(21);

		JScrollPane scrTable = CustomUI.getInstance().setCustomScrollPaneNotScroll(tblTableService);
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

		tblTableService.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);
		txtBFieldSerType.addMouseListener(this);
		txtBFieldSearchSerType.addMouseListener(this);

		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnSearch.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnNextToLast.addActionListener(this);
		btnNextToLeft.addActionListener(this);
		btnNextToRight.addActionListener(this);
		btnNextToFirst.addActionListener(this);

		cboSearch.addItemListener(this);
		cboSearchServiceType.addItemListener(this);

		txtKeyWord.addFocusListener(this);
		txtServiceName.addFocusListener(this);
		((JSpinner.DefaultEditor) spnPrice.getEditor()).getTextField().addFocusListener(this);
		((JSpinner.DefaultEditor) spnQuantity.getEditor()).getTextField().addFocusListener(this);

		txtKeyWord.addKeyListener(this);
		txtServiceName.addKeyListener(this);

		allLoaded();
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
					Boolean result = false;
					try {
						DichVuDAO serviceDAO = (DichVuDAO) Naming.lookup("rmi://localhost:1099/serviceDAO");
						result = serviceDAO.insertService(service);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (result) {
						txtPaging.toTheLastPage();
						searchEventUsingBtnSearch();
						message = "Thêm dịch vụ mới thành công";
						txtServiceID.setText(service.getMaDichVu());
						// int stt = tblTableService.getRowCount();
						// addRow(stt, service);
						// int lastIndex = tblTableService.getRowCount() - 1;
						// tblTableService.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
						// tblTableService.scrollRectToVisible(tblTableService.getCellRect(lastIndex, lastIndex, true));
						btnAdd.setEnabledCustom(false);
						btnUpdate.setEnabledCustom(true);
					} else {
						message = "Thêm dịch vụ thất bại";
					}
					JOptionPane.showMessageDialog(this, message);
				}
			}
		} else if (o.equals(btnBack)) {
			fDieuHuong winNavigation = new fDieuHuong(staffLogin);
			this.setVisible(false);
			winNavigation.setVisible(true);
		} else if (o.equals(btnRefresh)) {
			btnAdd.setEnabledCustom(true);
			btnUpdate.setEnabledCustom(false);
			txtServiceID.setText("");
			txtServiceName.setText("");
			cboServiceType.setSelectedIndex(0);
			spnPrice.setValue((double) 0);
			spnQuantity.setValue((int) 1);
			removeSelectionInterval();
		} else if (o.equals(btnUpdate)) {
			if (validData()) {
				DichVu service = getServiceDataInForm();
				String serviceName = "";
				try {
					DichVuDAO serviceDAO = (DichVuDAO) Naming.lookup("rmi://localhost:1099/serviceDAO");
					serviceName = serviceDAO.getServiceNameById(service.getMaDichVu());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				String message = "";
				int selectedRow = tblTableService.getSelectedRow();
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
						Boolean result = false;
						try {
							DichVuDAO serviceDAO = (DichVuDAO) Naming.lookup("rmi://localhost:1099/serviceDAO");
							result = serviceDAO.updateInfoService(service);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (result) {
							message = "Cập nhật thông tin " + name + " thành công";
							updateRow(selectedRow, service);
							btnAdd.setEnabledCustom(false);
							btnUpdate.setEnabledCustom(true);
							tblTableService.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
							tblTableService
									.scrollRectToVisible(tblTableService.getCellRect(selectedRow, selectedRow, true));
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
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSerType)) {
			cboServiceType.showPopup();
		} else if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		} else if (o.equals(txtBFieldSearchSerType)) {
			cboSearchServiceType.showPopup();
		} else if (o.equals(tblTableService)) {
			int selectedRow = tblTableService.getSelectedRow();
			txtServiceID.setText(tblTableService.getValueAt(selectedRow, 1).toString().trim());
			txtServiceName.setText(tblTableService.getValueAt(selectedRow, 2).toString().trim());
			String priceStr = tblTableService.getValueAt(selectedRow, 3).toString().trim().replace(",", "");
			spnPrice.setValue(Integer.parseInt(priceStr));
			String quantityStr = tblTableService.getValueAt(selectedRow, 4).toString().trim().replace(",", "");
			spnQuantity.setValue(Integer.parseInt(quantityStr));
			cboServiceType.setSelectedItem(tblTableService.getValueAt(selectedRow, 5).toString().trim());
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
			cboServiceType.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtBFieldSearchSerType)) {
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
			txtPaging.toTheFirstPage();
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
		Object o = e.getSource();
		int key = e.getKeyCode();
		InputEventHandler handler = new InputEventHandler();
		if (o.equals(txtKeyWord)) {
			handler.characterInputLimit(key, txtKeyWord, 100);
		} else if (o.equals(txtServiceName)) {
			handler.characterInputLimit(key, txtServiceName, 100);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtServiceName);
		} else if (o.equals(((JSpinner.DefaultEditor) spnPrice.getEditor()).getTextField())) {
			spnPrice.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyWord);
		} else if (o.equals(((JSpinner.DefaultEditor) spnQuantity.getEditor()).getTextField())) {
			spnQuantity.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtServiceName)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceName);
		} else if (o.equals(((JSpinner.DefaultEditor) spnPrice.getEditor()).getTextField())) {
			spnPrice.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtKeyWord)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		} else if (o.equals(((JSpinner.DefaultEditor) spnQuantity.getEditor()).getTextField())) {
			spnQuantity.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	/**
	 * chạy tất cả các hàm khi bắt đầu chạy form
	 */
	public void allLoaded() {
		reSizeColumnTable();
		txtPaging.setCurrentPage(1);
		ArrayList<DichVu> serviceList = new ArrayList<>();
		try {
			DichVuDAO serviceDAO = (DichVuDAO) Naming.lookup("rmi://localhost:1099/serviceDAO");
			LoaiDichVuDAO serviceTypeDAO = (LoaiDichVuDAO) Naming.lookup("rmi://localhost:1099/serviceTypeDAO");
			loadServiceTypeList(serviceTypeDAO.getServiceTypeList());
			int totalLine = serviceDAO.getTotalLineOfServiceList();
			txtPaging.setTotalPage(getLastPage(totalLine));
			serviceList = serviceDAO.getServiceListAndPageNumber(1, lineNumberDisplayed);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		loadServiceList(serviceList, 1);
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
		Double price = Double.parseDouble(spnPrice.getValue().toString());
		int quantity = Integer.parseInt(spnQuantity.getValue().toString());
		if (serviceId.equals("") || serviceId.length() <= 0) {
			serviceId = createNewServiceID();
		}
		LoaiDichVu serviceType = null;
		try {
			LoaiDichVuDAO serviceTypeDAO = (LoaiDichVuDAO) Naming.lookup("rmi://localhost:1099/serviceTypeDAO");
			serviceType = serviceTypeDAO.getServiceTypeByName(serviceTypeName);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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
		String lastServiceId = "";
		try {
			DichVuDAO serviceDAO = (DichVuDAO) Naming.lookup("rmi://localhost:1099/serviceDAO");
			lastServiceId = serviceDAO.getLastServiceID();
			lastServiceId = serviceDAO.getLastServiceID();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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
			if (newStaffIdStr.length() > 5) {
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
	 * @param roomList    {@code ArrayList<Phong>}: danh sách phòng
	 * @param serviceList {@code ArrayList<DichVu>}: danh sách dịch vụ
	 */
	private void loadServiceList(ArrayList<DichVu> serviceList, int currentPage) {
		modelTableService.getDataVector().removeAllElements();
		modelTableService.fireTableDataChanged();
		int i = 1 + (currentPage - 1) * lineNumberDisplayed;
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
		TableColumnModel columnModel = tblTableService.getColumnModel();
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
		int currentPage = txtPaging.getCurrentPage();
		int totalLine = 1;
		ArrayList<DichVu> serviceList = new ArrayList<DichVu>();
		try {
			DichVuDAO serviceDAO = (DichVuDAO) Naming.lookup("rmi://localhost:1099/serviceDAO");
			if (searchTypeName.equalsIgnoreCase("Tất cả")) {
				totalLine = serviceDAO.getTotalLineOfServiceList();
				serviceList = serviceDAO.getServiceListAndPageNumber(currentPage, lineNumberDisplayed);
			} else if (searchTypeName.equalsIgnoreCase("Tên dịch vụ")) {
				keyword = txtKeyWord.getText().trim();
				totalLine = serviceDAO.getTotalLineOfServiceListByName(keyword);
				serviceList = serviceDAO.getServiceListByNameAndPageNumber(keyword, currentPage, lineNumberDisplayed);
			} else if (searchTypeName.equalsIgnoreCase("Tên loại dịch vụ")) {
				keyword = cboSearchServiceType.getSelectedItem().toString().trim();
				totalLine = serviceDAO.getTotalLineOfServiceListByServiceTypeName(keyword);
				serviceList = serviceDAO.getServiceListByServiceTypeNameAndPageNumber(keyword, currentPage,
						lineNumberDisplayed);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int lastPage = getLastPage(totalLine);
		txtPaging.setTotalPage(lastPage);
		loadServiceList(serviceList, currentPage);
	}

	/**
	 * Xóa bỏ dòng đang chọn
	 */
	private void removeSelectionInterval() {
		int selectedRow = tblTableService.getSelectedRow();
		tblTableService.getSelectionModel().removeSelectionInterval(selectedRow, selectedRow);
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
