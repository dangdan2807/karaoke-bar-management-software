package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

import DAO.ConvertTime;
import DAO.KhachHangDAO;
import entity.KhachHang;

public class DialogChonKhachHang extends JDialog
		implements ActionListener, MouseListener, FocusListener, KeyListener, ItemListener {
	private JButton[] btnCustomerList;
	private int pnShowTableWidth = 310;
	private int heightTable = 300;
	private int viTri = -1;

	private JTextField txtCustomerId, txtCustomerName, txtCMND, txtPhoneNumber, txtBirthDay, txtGender;
	private JTextField txtKeyword;
	private JComboBox<String> cboSearch;
	private JButton btnSearch, btnChooseCustomer;

	private ImageIcon background = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(810, 400, Image.SCALE_SMOOTH));
	private ImageIcon searchIcon = new ImageIcon(
			CustomUI.SEARCH_ICON.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
	private ImageIcon manIcon = new ImageIcon(
			CustomUI.MAN_ICON.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	private ImageIcon womanIcon = new ImageIcon(
			CustomUI.WOMAN_ICON.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));

	private JPanel pnlShowCustomer;
	private JTextField txtBFieldSearch;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private KhachHang khachHang = null;

	/**
	 * Constructor mặc định không tham số
	 */
	public DialogChonKhachHang() {
		setTitle("Chọn khách hàng");
		setSize(810, 400);
		setResizable(false);
		setLocationRelativeTo(null);

		createUI();
	}

	/**
	 * Khởi tạo giao diện
	 */
	public void createUI() {
		JPanel pnlMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				setFont(new Font("Dialog", Font.PLAIN, 12));
				Image bgMain = background.getImage();
				g2.drawImage(bgMain, 0, 0, null);
			}
		};
		pnlMain.setBounds(0, 0, 800, 400);
		pnlMain.setBackground(Color.WHITE);
		getContentPane().add(pnlMain);
		pnlMain.setLayout(null);

		JPanel pnlCustomerList = new JPanel();
		pnlCustomerList.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Danh sách khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		pnlCustomerList.setBackground(Color.WHITE);
		pnlCustomerList.setBounds(0, 0, 500, 350);
		pnlCustomerList.setOpaque(false);
		pnlMain.add(pnlCustomerList);
		pnlCustomerList.setLayout(new BorderLayout(0, 0));

		pnlShowCustomer = new JPanel();
		pnlShowCustomer.setBackground(Color.WHITE);
		FlowLayout flShowTable = new FlowLayout(FlowLayout.LEFT);
		flShowTable.setHgap(6);
		flShowTable.setVgap(6);
		pnlShowCustomer.setLayout(flShowTable);
		pnlShowCustomer.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));
		pnlShowCustomer.setOpaque(false);

		JScrollPane scrShowKH = new JScrollPane(pnlShowCustomer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrShowKH.setBackground(Color.WHITE);
		scrShowKH.getVerticalScrollBar().setUnitIncrement(10);
		scrShowKH.setOpaque(false);
		scrShowKH.getViewport().setOpaque(false);
		pnlCustomerList.add(scrShowKH, BorderLayout.CENTER);

		JPanel pnlCustomerInfo = new JPanel();
		pnlCustomerInfo.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thông tin khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		pnlCustomerInfo.setBackground(Color.WHITE);
		pnlCustomerInfo.setBounds(510, 119, 274, 197);
		pnlMain.add(pnlCustomerInfo);
		pnlCustomerInfo.setLayout(null);
		pnlCustomerInfo.setOpaque(false);

		JLabel lblCustomerId = new JLabel("Mã KH: ");
		lblCustomerId.setForeground(Color.WHITE);
		lblCustomerId.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCustomerId.setBounds(10, 24, 83, 14);
		pnlCustomerInfo.add(lblCustomerId);

		txtCustomerId = new JTextField();
		txtCustomerId.setBounds(97, 21, 167, 20);
		txtCustomerId.setColumns(10);
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerId);
		pnlCustomerInfo.add(txtCustomerId);

		JLabel lblCustomerName = new JLabel("Tên KH: ");
		lblCustomerName.setForeground(Color.WHITE);
		lblCustomerName.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCustomerName.setBounds(10, 52, 83, 14);
		pnlCustomerInfo.add(lblCustomerName);

		txtCustomerName = new JTextField();
		txtCustomerName.setBounds(97, 49, 167, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerName);
		pnlCustomerInfo.add(txtCustomerName);

		JLabel lblCMND = new JLabel("CMNN/CCCD: ");
		lblCMND.setForeground(Color.WHITE);
		lblCMND.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCMND.setBounds(10, 80, 83, 14);
		pnlCustomerInfo.add(lblCMND);

		txtCMND = new JTextField();
		txtCMND.setBounds(97, 77, 167, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtCMND);
		pnlCustomerInfo.add(txtCMND);

		JLabel lblPhoneNumber = new JLabel("SDT: ");
		lblPhoneNumber.setForeground(Color.WHITE);
		lblPhoneNumber.setFont(new Font("Dialog", Font.BOLD, 12));
		lblPhoneNumber.setBounds(10, 108, 83, 14);
		pnlCustomerInfo.add(lblPhoneNumber);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(97, 105, 167, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtPhoneNumber);
		pnlCustomerInfo.add(txtPhoneNumber);

		JLabel lblNgaySinh = new JLabel("Ngày sinh: ");
		lblNgaySinh.setForeground(Color.WHITE);
		lblNgaySinh.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNgaySinh.setBounds(10, 136, 83, 14);
		pnlCustomerInfo.add(lblNgaySinh);

		txtBirthDay = new JTextField();
		txtBirthDay.setBounds(97, 133, 167, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtBirthDay);
		pnlCustomerInfo.add(txtBirthDay);

		JLabel lblGender = new JLabel("Giới tính: ");
		lblGender.setForeground(Color.WHITE);
		lblGender.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGender.setBounds(10, 164, 83, 14);
		pnlCustomerInfo.add(lblGender);

		txtGender = new JTextField();
		txtGender.setColumns(10);
		txtGender.setBounds(97, 161, 167, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtGender);
		pnlCustomerInfo.add(txtGender);

		JPanel pnlSearch = new JPanel();
		pnlSearch.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thông tin khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		pnlSearch.setBackground(Color.WHITE);
		pnlSearch.setBounds(510, 0, 274, 116);
		pnlSearch.setOpaque(false);
		pnlMain.add(pnlSearch);
		pnlSearch.setLayout(null);

		JLabel lblSearchType = new JLabel("Lọc theo: ");
		lblSearchType.setForeground(Color.WHITE);
		lblSearchType.setFont(new Font("Dialog", Font.BOLD, 13));
		lblSearchType.setBounds(10, 23, 77, 14);
		pnlSearch.add(lblSearchType);

		cboSearch = new JComboBox<String>();
		cboSearch.setBounds(97, 21, 167, 20);
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tên Khách hàng");
		cboSearch.addItem("Mã khách hàng");
		cboSearch.addItem("Số điện thoại");
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		pnlSearch.add(cboSearch);

		JLabel lblSearch = new JLabel("Từ khóa: ");
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setFont(new Font("Dialog", Font.BOLD, 13));
		lblSearch.setBounds(10, 57, 77, 14);
		pnlSearch.add(lblSearch);

		txtKeyword = new JTextField();
		txtKeyword.setBounds(97, 54, 167, 20);
		CustomUI.getInstance().setCustomTextFieldOff(txtKeyword);
		pnlSearch.add(txtKeyword);

		btnSearch = new MyButton(150, 30, "Tìm khách hàng", new Dimension(100, 17), searchIcon.getImage(),
				new Dimension(16, 18), gra);
		((MyButton) btnSearch).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		btnSearch.setBounds(62, 80, 150, 30);
		pnlSearch.add(btnSearch);

		btnChooseCustomer = new MyButton(150, 30, "Chọn khách hàng", new Dimension(115, 17), null, new Dimension(0, 0),
				gra);
		((MyButton) btnChooseCustomer).setFontCustom(new Font("Dialog", Font.BOLD, 13));
		btnChooseCustomer.setBounds(572, 323, 150, 30);
		pnlMain.add(btnChooseCustomer);

		btnChooseCustomer.addActionListener(this);
		btnSearch.addActionListener(this);

		btnChooseCustomer.addMouseListener(this);
		btnSearch.addMouseListener(this);
		txtBFieldSearch.addMouseListener(this);

		txtKeyword.addFocusListener(this);

		txtKeyword.addKeyListener(this);

		cboSearch.addItemListener(this);

		LoadCustomerList(KhachHangDAO.getInstance().getCustomerListUnBooked());
	}

	public static void main(String[] args) {
		new DialogChonKhachHang().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnSearch)) {
			searchCustomer();
		} else if (o.equals(btnChooseCustomer)) {
			String maKH = txtCustomerId.getText();
			if (maKH.equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(this, "Bạn phải chọn một khách hàng", "Thông báo", JOptionPane.OK_OPTION);
			} else {
				khachHang = KhachHangDAO.getInstance().getCustomerById(maKH);
				this.dispose();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		// bắt sự kiện nhấn phím enter tự nhấn btnLogin
		if (o.equals(txtKeyword)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				searchCustomer();
			}
			String searchTypeName = cboSearch.getSelectedItem().toString();
			if (searchTypeName.equalsIgnoreCase("Số điện thoại")) {
				enterOnlyNumbers(key, txtKeyword, 10);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyword)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtKeyword);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtKeyword)) {
			CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyword);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object object = e.getSource();
		if (object.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
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
		Object object = e.getSource();
		if (object.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
			String searchTypeName = cboSearch.getSelectedItem().toString();
			txtKeyword.setText("");
			if (searchTypeName.equalsIgnoreCase("Tất cả")) {
				txtKeyword.setEditable(false);
				CustomUI.getInstance().setCustomTextFieldOff(txtKeyword);
			} else {
				txtKeyword.setEditable(true);
				CustomUI.getInstance().setCustomTextFieldOn(txtKeyword);
			}
		}
	}

	/**
	 * Hiển thị thông tin khách hàng lên form thông tin khách hàng
	 * 
	 * @param customer {@code KhachHang}: khách hàng cần hiển thị
	 */
	private void loadDataLenForm(KhachHang customer) {
		txtCustomerId.setText(customer.getMaKH());
		txtCustomerName.setText(customer.getHoTen());
		txtCMND.setText(customer.getCmnd());
		txtPhoneNumber.setText(customer.getSoDienThoai());
		String ngaySinhStr = ConvertTime.getInstance().convertTimeToString(customer.getNgaySinh(), "dd-MM-yyyy");
		txtBirthDay.setText(ngaySinhStr);
		boolean gioiTinh = customer.getGioiTinh();
		txtGender.setText("Nữ");
		if (gioiTinh == false) {
			txtGender.setText("Nam");
		}
	}

	/**
	 * Hiển thị thông tin khách hàng hàng
	 * 
	 * @param customerId {@code String}: mã của khách hàng cần hiển thị
	 */
	private void loadBtnCustomer(String customerId) {
		KhachHang khachHang = KhachHangDAO.getInstance().getCustomerById(customerId);
		String tenBtn = "<html>" + "<p style='text-align: left; width:116px'>Mã KH: " + khachHang.getMaKH() + " </p>"
				+ "<p style='text-align: left; width:116px'>Tên: " + khachHang.getHoTen() + " </p>"
				+ "<p style='text-align: left; width:116px'>CMND: " + khachHang.getCmnd() + "</p>"
				+ "<p style='text-align: left; width:116px'>SDT: " + khachHang.getSoDienThoai() + "</p></html>";
		int index = 0;
		for (int i = 0; i < btnCustomerList.length; i++) {
			if (btnCustomerList[i].getText().contains(tenBtn))
				index = i;
			else if (btnCustomerList[i].getText().equals("")) {
				index = i;
				break;
			}
		}
		boolean gioiTinh = khachHang.getGioiTinh();
		if (gioiTinh) {
			btnCustomerList[index].setIcon(womanIcon);
		} else {
			btnCustomerList[index].setIcon(manIcon);
		}
		btnCustomerList[index].setText(tenBtn);
		btnCustomerList[index].setForeground(Color.WHITE);
		btnCustomerList[index].setFont(new Font("Dialog", Font.BOLD, 11));
		btnCustomerList[index].setBackground(Color.decode("#3c8dbc"));
		btnCustomerList[index].setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCustomerList[index].setHorizontalTextPosition(SwingConstants.RIGHT);
		btnCustomerList[index].setPreferredSize(new Dimension(KhachHangDAO.TABLE_WIDTH, KhachHangDAO.TABLE_HEIGHT));
		pnlShowCustomer.revalidate();
		pnlShowCustomer.repaint();
	}

	/**
	 * Hiển thị danh sách khách hàng
	 * 
	 * @param customerList {@code ArrayList<KhachHang>} : danh sách khách hàng cần
	 *                     hiển thị
	 */
	private void LoadCustomerList(ArrayList<KhachHang> customerList) {
		heightTable = KhachHangDAO.TABLE_HEIGHT;
		pnlShowCustomer.removeAll();
		pnlShowCustomer.revalidate();
		pnlShowCustomer.repaint();
		Border lineRed = new LineBorder(Color.RED, 2);
		Border lineGray = new LineBorder(Color.GRAY, 1);
		int sizeDSPhong = customerList.size();
		btnCustomerList = new JButton[sizeDSPhong];
		for (int i = 0; i < sizeDSPhong; i++) {
			final int selection = i;
			KhachHang khachHang = customerList.get(i);
			String maKH = khachHang.getMaKH();
			btnCustomerList[selection] = new JButton("");
			loadBtnCustomer(maKH);
			btnCustomerList[selection].setBorder(lineGray);
			if ((i + 1) % 2 == 0) {
				heightTable += KhachHangDAO.TABLE_HEIGHT;
				pnlShowCustomer.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));
			}
			btnCustomerList[selection].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (viTri != -1) {
						btnCustomerList[viTri].setBorder(lineGray);
					}
					viTri = selection;
					btnCustomerList[selection].setBorder(lineRed);
					KhachHang khActiveE = KhachHangDAO.getInstance().getCustomerById(maKH);
					loadDataLenForm(khActiveE);
				}
			});
			// sự kiện hover chuột
			btnCustomerList[selection].addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {
					btnCustomerList[selection].setBackground(Color.decode("#605ca8"));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					btnCustomerList[selection].setBackground(Color.decode("#3c8dbc"));
				}
			});
			pnlShowCustomer.add(btnCustomerList[selection]);
		}
	}

	/**
	 * Tìm kiếm thông tin của khách hàng
	 */
	private void searchCustomer() {
		String searchTypeName = cboSearch.getSelectedItem().toString().trim();
		ArrayList<KhachHang> dsKhachHang = new ArrayList<KhachHang>();
		String keyword = txtKeyword.getText();
		switch (searchTypeName) {
		case "Tất cả":
			txtKeyword.setText("");
			dsKhachHang = KhachHangDAO.getInstance().getCustomerList();
			break;
		case "Tên Khách hàng":
			dsKhachHang = KhachHangDAO.getInstance().getCustomerListByName(keyword);
			break;
		case "Mã khách hàng":
			dsKhachHang = KhachHangDAO.getInstance().getCustomerListById(keyword);
			break;
		case "Số điện thoại":
			dsKhachHang = KhachHangDAO.getInstance().getCustomerListByPhoneNumber(keyword);
			break;
		default:
			break;
		}
		LoadCustomerList(dsKhachHang);
	}

	/**
	 * Lấy thông tin khách hàng được chọn
	 * 
	 * @return {@code KhachHang}: khách hàng được chọn
	 */
	public KhachHang getSelectedCustomer() {
		return khachHang;
	}

	/**
	 * Giới hạn số lượng ký tự nhập vào và chỉ cho phép nhập số
	 * 
	 * @param key       {@code int}: mã số của phím được nhấn
	 * @param txt       {@code JTextField}: text field nhận sự kiện
	 * @param maxLength {@code int}: số lượng ký tự tối đa có thể nhập được
	 */
	private void enterOnlyNumbers(int key, JTextField txt, int maxLength) {
		String phoneNumberStr = txt.getText();
		int length = phoneNumberStr.length();
		if (key >= 49 && key <= 57 || key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
			txt.setEditable(true);
			if (length == maxLength && key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_DELETE)
				txt.setEditable(false);
		} else {
			txt.setEditable(false);
		}
	}
}
