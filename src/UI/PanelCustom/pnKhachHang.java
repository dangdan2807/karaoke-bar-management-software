package UI.PanelCustom;

import javax.swing.*;
import javax.swing.table.*;

import DAO.*;
import entity.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.border.*;

public class pnKhachHang extends JFrame
		implements interfaceBtn, ActionListener, MouseListener, KeyListener, ItemListener {
	private JTable table;
	private DefaultTableModel modelTable;
	private JTextField txtMaKH, txtTenKH, txtKeyWord, txtCmnd, txtSdt;
	private JButton btnThem, btnSua, btnLamMoi, btnLogOut, btnBack, btnTim, btnViewAll;
	private ImageIcon addIcon = new ImageIcon("img/blueAdd_16.png");
	private ImageIcon refreshIcon = new ImageIcon("img/refresh_16.png");
	private ImageIcon searchIcon = new ImageIcon("img/search_16.png");
	private ImageIcon logOutIcon = new ImageIcon("img/logout_16.png");
	private ImageIcon backIcon = new ImageIcon("img/back_16.png");
	private ImageIcon updateIcon = new ImageIcon("img/update_16.png");
	private JComboBox<String> cboSearch;
	JRadioButton radNam, radNu;
	kDatePicker dpNgaySinh;
	int index = 1;
	private NhanVien nhanVienLogin = null;

	public pnKhachHang(NhanVien nhanVien) {
		setSize(1270, 630);
		getContentPane().setLayout(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.nhanVienLogin = nhanVien;
		JPanel pnTop = new JPanel();
		pnTop.setBackground(Color.WHITE);
		pnTop.setPreferredSize(new Dimension(10, 200));
		pnTop.setLayout(null);
		getContentPane().add(pnTop, BorderLayout.NORTH);

		JPanel pnTitle = new JPanel();
		pnTitle.setBounds(0, 0, 1270, 40);
		pnTitle.setBackground(Color.decode("#d0e1fd"));
		pnTop.add(pnTitle);

		JLabel lbTitle = new JLabel("Quán Lý Khách Hàng");
		customUI.getInstance().setCustomLbTitle(lbTitle);
		pnTitle.add(lbTitle);

		JPanel pnInfo = new JPanel();
		pnInfo.setBorder(
				new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Thông tin khách hàng",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		pnInfo.setBackground(Color.WHITE);
		pnInfo.setLayout(null);
		pnInfo.setBounds(0, 41, 1270, 120);
		pnTop.add(pnInfo);

		JLabel lbMaKH = new JLabel("Mã KH:");
		lbMaKH.setBounds(10, 22, 70, 14);
		lbMaKH.setBackground(Color.decode("#f9f9f9"));
		pnInfo.add(lbMaKH);

		txtMaKH = new JTextField();
		txtMaKH.setEditable(false);
		// txtUsername.setEditable(false);
		txtMaKH.setBounds(85, 19, 150, 20);
		pnInfo.add(txtMaKH);
		txtMaKH.setColumns(10);

		JLabel lbTenKH = new JLabel("Tên KH: ");
		lbTenKH.setBounds(10, 48, 80, 16);
		pnInfo.add(lbTenKH);

		txtTenKH = new JTextField();
		txtTenKH.setBounds(85, 46, 150, 20);
		pnInfo.add(txtTenKH);
		txtTenKH.setColumns(10);

		btnThem = new JButton("Thêm", addIcon);
		btnThem.setBounds(10, 77, 101, 26);
		customUI.getInstance().setCustomBtn(btnThem);
		pnInfo.add(btnThem);

		btnSua = new JButton("Sửa", updateIcon);
		btnSua.setBounds(123, 78, 101, 26);
		customUI.getInstance().setCustomBtn(btnSua);
		pnInfo.add(btnSua);

		btnLamMoi = new JButton("Làm mới", refreshIcon);
		btnLamMoi.setBounds(236, 78, 110, 26);
		customUI.getInstance().setCustomBtn(btnLamMoi);
		pnInfo.add(btnLamMoi);

		btnLogOut = new JButton("Đăng xuất", logOutIcon);
		btnLogOut.setBounds(471, 78, 120, 26);
		customUI.getInstance().setCustomBtn(btnLogOut);
		pnInfo.add(btnLogOut);

		btnBack = new JButton("Quay lại", backIcon);
		btnBack.setBounds(358, 79, 101, 26);
		customUI.getInstance().setCustomBtn(btnBack);
		pnInfo.add(btnBack);

		JLabel lbCmnd = new JLabel("CMND/CCCD:");
		lbCmnd.setBackground(new Color(249, 249, 249));
		lbCmnd.setBounds(253, 21, 93, 14);
		pnInfo.add(lbCmnd);

		JLabel lbSdt = new JLabel("Số điện thoại: ");
		lbSdt.setBackground(new Color(249, 249, 249));
		lbSdt.setBounds(253, 48, 93, 14);
		pnInfo.add(lbSdt);

		txtCmnd = new JTextField();
		txtCmnd.setColumns(10);
		txtCmnd.setBounds(358, 19, 150, 20);
		pnInfo.add(txtCmnd);

		txtSdt = new JTextField();
		txtSdt.setColumns(10);
		txtSdt.setBounds(358, 46, 150, 20);
		pnInfo.add(txtSdt);

		JLabel lbNgaySinh = new JLabel("Ngày sinh:");
		lbNgaySinh.setBackground(new Color(249, 249, 249));
		lbNgaySinh.setBounds(526, 21, 80, 14);
		pnInfo.add(lbNgaySinh);

		JLabel lbGioiTinh = new JLabel("Giới tính: ");
		lbGioiTinh.setBackground(new Color(249, 249, 249));
		lbGioiTinh.setBounds(526, 48, 93, 14);
		pnInfo.add(lbGioiTinh);

		radNam = new JRadioButton("Nam");
		radNam.setBounds(601, 44, 70, 24);
		radNam.setBackground(Color.WHITE);
		radNam.setSelected(true);
		pnInfo.add(radNam);

		radNu = new JRadioButton("Nữ");
		radNu.setBounds(675, 44, 70, 24);
		radNu.setBackground(Color.WHITE);
		pnInfo.add(radNu);

		ButtonGroup grRadStatus = new ButtonGroup();
		grRadStatus.add(radNam);
		grRadStatus.add(radNu);

		kDatePicker dpNgaySinh = new kDatePicker();
		dpNgaySinh.setBounds(601, 16, 150, 20);
		pnInfo.add(dpNgaySinh);

		JPanel pnSearch = new JPanel();
		pnSearch.setBackground(Color.WHITE);
		pnSearch.setBounds(0, 161, 1270, 39);
		pnSearch.setLayout(null);
		pnTop.add(pnSearch);

		JLabel lbSearch = new JLabel("Trạng thái: ");
		lbSearch.setBounds(12, 12, 100, 16);
		pnSearch.add(lbSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.setBounds(81, 10, 170, 20);
		cboSearch.addItem("Tất cả");
		cboSearch.addItem("Tìm theo mã KH");
		cboSearch.addItem("Tìm theo tên KH");
		pnSearch.add(cboSearch);

		JLabel lbKeyWord = new JLabel("Từ khóa: ");
		lbKeyWord.setBounds(269, 12, 70, 16);
		pnSearch.add(lbKeyWord);

		txtKeyWord = new JTextField();
		txtKeyWord.setBounds(336, 10, 170, 20);
		pnSearch.add(txtKeyWord);
		txtKeyWord.setColumns(10);

		btnTim = new JButton("Tìm kiếm", searchIcon);
		btnTim.setBounds(518, 7, 120, 26);
		customUI.getInstance().setCustomBtn(btnTim);
		pnSearch.add(btnTim);

		btnViewAll = new JButton("Xem tất cả", null);
		btnViewAll.setBounds(650, 7, 120, 26);
		customUI.getInstance().setCustomBtn(btnViewAll);
		pnSearch.add(btnViewAll);

		String[] cols = { "STT", "Mã khách hàng", "Tên khách hàng", "CMND/CCCD", "Ngày sinh", "Giới tính",
				"Số điện thoại" };
		modelTable = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		table = new JTable(modelTable);

		JScrollPane scpTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpTable.getViewport().setBackground(Color.WHITE);

		JPanel pnTable = new JPanel();
		pnTable.setBackground(Color.WHITE);
		pnTable.setLayout(new BorderLayout(0, 0));
		pnTable.add(scpTable, BorderLayout.CENTER);
		pnTable.setBounds(10, 25, 1250, 600);

		getContentPane().add(pnTable, BorderLayout.CENTER);
		reSizeColumnTable();
		loadAccountList();

		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTim.addActionListener(this);
		btnViewAll.addActionListener(this);

		btnThem.addMouseListener(this);
		btnSua.addMouseListener(this);
		btnLamMoi.addMouseListener(this);
		btnTim.addMouseListener(this);
		btnViewAll.addMouseListener(this);
		btnBack.addMouseListener(this);
		btnLogOut.addMouseListener(this);

		table.addMouseListener(this);

		txtKeyWord.addKeyListener(this);

		cboSearch.addItemListener(this);
	}

	public static void main(String[] args) {
		NhanVien staff = NhanVienDAO.getInstance().getNhanVienByTenDangNhap("phamdangdan");
		new pnKhachHang(staff).setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (validData()) {
				String maKH = taoMaKHTuDong();
				KhachHang khachHangData = getDataInFrom();
				khachHangData.setMaKH(maKH);
				boolean result = KhachHangDAO.getInstance().insertKhachHang(khachHangData);
				DecimalFormat df = new DecimalFormat("#,###.##");
				DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				if (result == true) {
					String stt = df.format(index++);
					String type = "Nam";
					if (khachHangData.getGioiTinh() == false)
						type = "Nữ";
					modelTable.addRow(new Object[] { stt, khachHangData.getMaKH(), khachHangData.getHoTen(),
							khachHangData.getCmnd(), sdf.format(khachHangData.getNgaySinh()), type,
							khachHangData.getSoDienThoai() });
					modelTable.fireTableDataChanged();
					// di chuyển và bô đen dòng vừa thêm vào
					int lastIndex = table.getRowCount() - 1;
					table.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
					table.scrollRectToVisible(table.getCellRect(lastIndex, lastIndex, true));
					JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thành công.");
				} else {
					JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thất bại");
				}
			}
		} else if (o.equals(btnSua)) {
			if (authentication()) {
				if (validData()) {
					int row = table.getSelectedRow();
					if (row != -1) {
						KhachHang khachHangData = getDataInFrom();
						boolean result = KhachHangDAO.getInstance().updateKhachHang(khachHangData);
						if (result == true) {
							String type = "Nam";
							if (khachHangData.getGioiTinh() == false)
								type = "Nữ";
							modelTable.setValueAt(khachHangData.getHoTen(), row, 2);
							modelTable.setValueAt(khachHangData.getCmnd(), row, 3);
							DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							modelTable.setValueAt(sdf.format(khachHangData.getNgaySinh()), row, 4);
							modelTable.setValueAt(type, row, 5);
							modelTable.setValueAt(khachHangData.getSoDienThoai(), row, 6);
							JOptionPane.showMessageDialog(this, "cập nhật thông tin khách hàng thành công");
						} else {
							JOptionPane.showMessageDialog(this, "cập nhật thông tin khách hàng thất bại");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Chọn 1 khách hàng cần cập nhật");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Mật khẩu không chính xác");
			}
		} else if (o.equals(btnLamMoi)) {
			refreshInput();
		} else if (o.equals(btnTim)) {
			String username = txtKeyWord.getText().trim();
			String typeStr = cboSearch.getSelectedItem().toString();
			int type = 0;
			if (typeStr.equals("Tất cả"))
				type = -1;
			else if (typeStr.equals("Quản lý"))
				type = 1;

			if (username.equals("")) {
				if (type == -1) {
					loadAccountList();
				} else {
					searchAccountListByType(type);
				}
			} else {
				if (type == -1)
					searchAccountListByUsername(username);
				else
					searchAccountListByUsernameAndType(username, type);
			}
		} else if (o.equals(btnViewAll)) {
			loadAccountList();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
			String typeStr = String.valueOf(cboSearch.getSelectedItem());
			if (typeStr.equals("Tất cả")) {
				loadAccountList();
			} else {
				if (typeStr.equals("Nhân viên"))
					searchAccountListByType(0);
				else
					searchAccountListByType(1);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(table)) {
			int row = table.getSelectedRow();
			txtMaKH.setText(modelTable.getValueAt(row, 1).toString());
			txtTenKH.setText(modelTable.getValueAt(row, 2).toString());
			String type = String.valueOf(modelTable.getValueAt(row, 3));
			if (type.equals("Nam"))
				radNam.setSelected(true);
			else
				radNu.setSelected(true);
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
		if (o.equals(btnThem)) {
			customUI.getInstance().setCustomBtnHover(btnThem);
		} else if (o.equals(btnSua)) {
			customUI.getInstance().setCustomBtnHover(btnSua);
		} else if (o.equals(btnLamMoi)) {
			customUI.getInstance().setCustomBtnHover(btnLamMoi);
		} else if (o.equals(btnBack)) {
			customUI.getInstance().setCustomBtnHover(btnBack);
		} else if (o.equals(btnLogOut)) {
			customUI.getInstance().setCustomBtnHover(btnLogOut);
		} else if (o.equals(btnTim)) {
			customUI.getInstance().setCustomBtnHover(btnTim);
		} else if (o.equals(btnViewAll)) {
			customUI.getInstance().setCustomBtnHover(btnViewAll);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			customUI.getInstance().setCustomBtn(btnThem);
		} else if (o.equals(btnSua)) {
			customUI.getInstance().setCustomBtn(btnSua);
		} else if (o.equals(btnLamMoi)) {
			customUI.getInstance().setCustomBtn(btnLamMoi);
		} else if (o.equals(btnBack)) {
			customUI.getInstance().setCustomBtn(btnBack);
		} else if (o.equals(btnLogOut)) {
			customUI.getInstance().setCustomBtn(btnLogOut);
		} else if (o.equals(btnTim)) {
			customUI.getInstance().setCustomBtn(btnTim);
		} else if (o.equals(btnViewAll)) {
			customUI.getInstance().setCustomBtn(btnViewAll);
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
				btnTim.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public JButton getBtnLogOut() {
		return btnLogOut;
	}

	@Override
	public JButton getBtnBack() {
		return btnBack;
	}

	private String taoMaKHTuDong() {
		String maKHCuoi = KhachHangDAO.getInstance().getMaKHCuoiCung();
		int idKHCu = Integer.parseInt(maKHCuoi.replace("KH", ""));
		int idKHMoi = idKHCu + 1;
		int lenIdCu = String.valueOf(idKHCu).length();
		int lenIdMoi = String.valueOf(idKHMoi).length();
		String maKHMoi = "";
		if (lenIdCu < lenIdMoi) {
			maKHMoi = maKHCuoi.replace("0" + String.valueOf(idKHCu), String.valueOf(idKHMoi));
		} else
			maKHMoi = maKHCuoi.replace(String.valueOf(idKHCu), String.valueOf(idKHMoi));
		return maKHMoi;
	}

	private boolean authentication() {
		String password = "";
		JPasswordField passwordField = new JPasswordField();
		Object[] obj = { "Vui lòng nhập mật khẩu để xác thực danh tính:\n\n", passwordField };
		Object stringArray[] = { "OK", "Cancel" };
		int select = JOptionPane.showOptionDialog(null, obj, "Xác thực", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, stringArray, obj);

		boolean result = false;
		if (select == JOptionPane.YES_OPTION) {
			password = new String(passwordField.getPassword());
			System.out.println(password);
			result = TaiKhoanDAO.getInstance().login(nhanVienLogin.getTaiKhoan().getTenDangNhap(), password);
		}
		return result;
	}

	private boolean validData() {
		String username = txtMaKH.getText().trim();
		if (!(username.length() > 0 && username.matches("^[a-zA-Z0-9_@#]{5,}$"))) {
			if (username.length() < 0)
				JOptionPane.showMessageDialog(this, "Tài khoản không được để trống");
			else
				JOptionPane.showMessageDialog(this,
						"Tài khoản phải có tối thiểu 5 ký tự và chỉ được chứa chữ, số, @, _, #");
			return false;
		}

		String displayName = txtTenKH.getText().trim();
		if (!(displayName.length() > 0)) {
			JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống");
			return false;
		}

		return true;
	}

	private void refreshInput() {
		txtMaKH.setText("");
		txtTenKH.setText("");
		txtSdt.setText("");
		txtCmnd.setText("");
		dpNgaySinh.setValueToDay();
		radNam.setSelected(true);
	}

	// mỏ txtUsername
	private KhachHang getDataInFrom() {
		String maKH = txtMaKH.getText().trim();
		String tenKH = txtMaKH.getText().trim();
		String cmnd = txtCmnd.getText().trim();
		String sdt = txtSdt.getText().trim();
		Date ngaySinh = null;
		try {
			ngaySinh = dpNgaySinh.getFullDate();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int gioiTinh = 0;
		if (radNam.isSelected())
			gioiTinh = 1;
		return (new KhachHang(maKH, cmnd, tenKH, sdt, ngaySinh, gioiTinh > 0));
	}

	private void loadAccountList() {
		// ArrayList<Account> dataList = AccountDAO.getInstance().getListAccount();
		// loadDataIntoTable(dataList);
	}

	private void searchAccountListByUsername(String username) {
		// ArrayList<Account> dataList =
		// AccountDAO.getInstance().searchAccountListByUsername(username);
		// loadDataIntoTable(dataList);
	}

	private void searchAccountListByUsernameAndType(String username, int type) {
		// ArrayList<Account> dataList =
		// AccountDAO.getInstance().searchAccountListByUsernameAndType(username, type);
		// loadDataIntoTable(dataList);
	}

	private void searchAccountListByType(int type) {
		// ArrayList<Account> dataList =
		// AccountDAO.getInstance().searchAccountListByType(type);
		// loadDataIntoTable(dataList);
	}

	private void loadDataIntoTable(ArrayList<KhachHang> dataList) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		modelTable.getDataVector().removeAllElements();
		modelTable.fireTableDataChanged();
		index = 1;
		for (KhachHang item : dataList) {
			// String stt = df.format(index++);
			// String type = "Nhân viên";
			// if (item.getType() == 1)
			// type = "Quản lý";
			// modelTable.addRow(new Object[] { stt, item.getUsername(),
			// item.getDisplayName(), type });
		}
	}

	private void reSizeColumnTable() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(140);
		table.getColumnModel().getColumn(5).setPreferredWidth(140);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
	}
}
