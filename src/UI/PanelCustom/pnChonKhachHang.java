package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

import entity.KhachHang;

public class pnChonKhachHang extends JFrame implements ActionListener, MouseListener, FocusListener, KeyListener {

	private JTextField txtMaKH, txtTenKH, txtCMND, txtSDT, txtNgaySinh, txtGioiTinh, txtTimKiem;
	private JComboBox<String> cboTimKiem;
	private JButton btnTimKiem, btnChonKH;

	private ImageIcon searchIcon = new ImageIcon(
			CustomUI.getInstance().SEARCH_ICON.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));

	public pnChonKhachHang() {
		setTitle("Chọn khách hàng");
		setSize(800, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		createUI();
	}

	public void createUI() {
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 800, 400);
		pnMain.setBackground(Color.WHITE);
		getContentPane().add(pnMain);
		pnMain.setLayout(null);

		JPanel pnDSKhachHang = new JPanel();
		pnDSKhachHang.setBorder(
				new TitledBorder(null, "Danh sách khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDSKhachHang.setBackground(Color.WHITE);
		pnDSKhachHang.setBounds(0, 0, 500, 350);
		pnMain.add(pnDSKhachHang);
		pnDSKhachHang.setLayout(null);

		JPanel pnTTKhachHang = new JPanel();
		pnTTKhachHang.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thông tin khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnTTKhachHang.setBackground(Color.WHITE);
		pnTTKhachHang.setBounds(510, 119, 274, 197);
		pnMain.add(pnTTKhachHang);
		pnTTKhachHang.setLayout(null);

		JLabel lbMaKH = new JLabel("Mã KH: ");
		lbMaKH.setBounds(10, 24, 83, 14);
		pnTTKhachHang.add(lbMaKH);

		txtMaKH = new JTextField();
		txtMaKH.setEditable(false);
		txtMaKH.setBounds(97, 21, 167, 20);
		txtMaKH.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtMaKH.setBorder(CustomUI.getInstance().BORDER_BOTTOM_UN_FOCUS);
		pnTTKhachHang.add(txtMaKH);
		txtMaKH.setColumns(10);

		JLabel lbTenKH = new JLabel("Tên KH: ");
		lbTenKH.setBounds(10, 52, 83, 14);
		pnTTKhachHang.add(lbTenKH);

		txtTenKH = new JTextField();
		txtTenKH.setEditable(false);
		txtTenKH.setColumns(10);
		txtTenKH.setBounds(97, 49, 167, 20);
		txtTenKH.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtTenKH.setBorder(CustomUI.getInstance().BORDER_BOTTOM_UN_FOCUS);
		pnTTKhachHang.add(txtTenKH);

		JLabel lbCMND = new JLabel("CMNN/CCCD: ");
		lbCMND.setBounds(10, 80, 83, 14);
		pnTTKhachHang.add(lbCMND);

		txtCMND = new JTextField();
		txtCMND.setEditable(false);
		txtCMND.setColumns(10);
		txtCMND.setBounds(97, 77, 167, 20);
		txtCMND.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtCMND.setBorder(CustomUI.getInstance().BORDER_BOTTOM_UN_FOCUS);
		pnTTKhachHang.add(txtCMND);

		JLabel lbSDT = new JLabel("SDT: ");
		lbSDT.setBounds(10, 108, 83, 14);
		pnTTKhachHang.add(lbSDT);

		txtSDT = new JTextField();
		txtSDT.setEditable(false);
		txtSDT.setColumns(10);
		txtSDT.setBounds(97, 105, 167, 20);
		txtSDT.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtSDT.setBorder(CustomUI.getInstance().BORDER_BOTTOM_UN_FOCUS);
		pnTTKhachHang.add(txtSDT);

		JLabel lbNgaySinh = new JLabel("Ngày sinh: ");
		lbNgaySinh.setBounds(10, 136, 83, 14);
		pnTTKhachHang.add(lbNgaySinh);

		txtNgaySinh = new JTextField();
		txtNgaySinh.setEditable(false);
		txtNgaySinh.setColumns(10);
		txtNgaySinh.setBounds(97, 133, 167, 20);
		txtNgaySinh.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtNgaySinh.setBorder(CustomUI.getInstance().BORDER_BOTTOM_UN_FOCUS);
		pnTTKhachHang.add(txtNgaySinh);

		JLabel lbGioiTinh = new JLabel("Giới tính: ");
		lbGioiTinh.setBounds(10, 164, 83, 14);
		pnTTKhachHang.add(lbGioiTinh);

		txtGioiTinh = new JTextField();
		txtGioiTinh.setEditable(false);
		txtGioiTinh.setColumns(10);
		txtGioiTinh.setBounds(97, 161, 167, 20);
		txtGioiTinh.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtGioiTinh.setBorder(CustomUI.getInstance().BORDER_BOTTOM_UN_FOCUS);
		pnTTKhachHang.add(txtGioiTinh);

		JPanel pnTimKiem = new JPanel();
		pnTimKiem.setBorder(new TitledBorder(null, "Tìm thông tin khách hàng ", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		pnTimKiem.setBackground(Color.WHITE);
		pnTimKiem.setBounds(510, 0, 274, 115);
		pnMain.add(pnTimKiem);
		pnTimKiem.setLayout(null);

		cboTimKiem = new JComboBox<String>();
		cboTimKiem.setUI(new BasicComboBoxUI());
		cboTimKiem.setBounds(10, 21, 254, 22);
		cboTimKiem.addItem("Tìm theo tên Khách hàng");
		cboTimKiem.addItem("Tìm theo số điện thoại");
		cboTimKiem.addItem("Tìm theo mã khách hàng");
		cboTimKiem.setFont(new Font("Dialog", Font.BOLD, 12));
		cboTimKiem.setBackground(Color.WHITE);
		cboTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#1a66e3")));
		pnTimKiem.add(cboTimKiem);
		
		JLabel lbTimKiem = new JLabel("Từ khóa: ");
		lbTimKiem.setBounds(10, 57, 77, 14);
		pnTimKiem.add(lbTimKiem);

		txtTimKiem = new JTextField();
		txtTimKiem.setColumns(10);
		txtTimKiem.setBounds(86, 54, 178, 20);
		txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtTimKiem.setBorder(CustomUI.getInstance().BORDER_BOTTOM_UN_FOCUS);
		pnTimKiem.add(txtTimKiem);

		btnTimKiem = new JButton("Tìm khách hàng", searchIcon);
		btnTimKiem.setBounds(10, 82, 254, 23);
		pnTimKiem.add(btnTimKiem);
		CustomUI.getInstance().setCustomBtn(btnTimKiem);

		btnChonKH = new JButton("Chọn khách hàng");
		btnChonKH.setBounds(510, 327, 264, 23);
		pnMain.add(btnChonKH);
		CustomUI.getInstance().setCustomBtn(btnChonKH);

		txtTimKiem.addFocusListener(this);

		btnChonKH.addMouseListener(this);
		btnTimKiem.addMouseListener(this);

		btnChonKH.addActionListener(this);
		btnTimKiem.addActionListener(this);
	}

	public static void main(String[] args) {
		new pnChonKhachHang().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		// bắt sự kiện nhấn phím enter tự nhấn btnLogin
		if (o.equals(txtTimKiem)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnTimKiem.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtTimKiem)) {
			CustomUI.getInstance().setCustomTextFieldFocus(txtTimKiem);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtTimKiem)) {
			txtTimKiem.setBorder(CustomUI.getInstance().BORDER_BOTTOM_UN_FOCUS);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
		if (o.equals(btnChonKH)) {
			CustomUI.getInstance().setCustomBtnHover(btnChonKH);
		} else if (o.equals(btnTimKiem)) {
			CustomUI.getInstance().setCustomBtnHover(btnTimKiem);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(btnChonKH)) {
			CustomUI.getInstance().setCustomBtn(btnChonKH);
		} else if (o.equals(btnTimKiem)) {
			CustomUI.getInstance().setCustomBtn(btnTimKiem);
		}
	}

	private void loadDataLenForm(KhachHang khachHang) {
		txtMaKH.setText(khachHang.getMaKH());
		txtTenKH.setText(khachHang.getHoTen());
		txtCMND.setText(khachHang.getCmnd());
		txtSDT.setText(khachHang.getSoDienThoai());
		String ngaySinhStr = CustomUI.getInstance().convertSqlDateToUtilDateFormatString(khachHang.getNgaySinh(),
				"dd-MM-yyyy");
		txtNgaySinh.setText(ngaySinhStr);
		boolean gioiTinh = khachHang.getGioiTinh();
		txtGioiTinh.setText("Nam");
		if (gioiTinh == false) {
			txtGioiTinh.setText("Nữ");
		}
	}
}
