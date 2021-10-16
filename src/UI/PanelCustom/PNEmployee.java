package UI.PanelCustom;

import javax.swing.*;

import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

public class PNEmployee extends JFrame implements ActionListener, MouseListener, ItemListener, KeyListener {
	private JTable table;
	private DefaultTableModel modelTable;
	private JButton btnSearch;

	private JTextField txtCMND, txtSDT, txtMucLuong, txtTenNV, txtMaNhanVien, boxField, boxField1;
	private JTextField txtKey, boxField2, boxFieldChucVu;
	private JComboBox<String> cboGioiTinh, cboSelect, cboSelect1, cboChucVu;
	private JLabel lbCMND, lbNgaySinh, lbGender, lbChucVu, lbMucLuong, lbSDT, lbMaNhanVien, lbTenNV;
	private JLabel lbTrangThai, lpSelectSreach;
	private MyButton btnAdd, btnUpdate, btnRefresh, btnBack;
	private JRadioButton radConLam, radDaNghi;
	private kDatePicker dpNgaySinh;

	private ImageIcon bg = new ImageIcon(
			new ImageIcon("img/bgBlue.jpg").getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon addIcon = new ImageIcon("img/blueAdd_16.png");
	private ImageIcon refreshIcon = new ImageIcon("img/refresh_16.png");
	private ImageIcon searchIcon = new ImageIcon("img/search_16.png");
	private ImageIcon backIcon = new ImageIcon("img/back_16.png");
	private ImageIcon updateIcon = new ImageIcon("img/update_16.png");
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	int index = 1;

	public PNEmployee() {
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
				setFont(new Font("Tahoma", Font.BOLD, 24));
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
				setFont(new Font("Tahoma", Font.BOLD, 24));
				g2.drawString("QUẢN LÝ NHÂN VIÊN", 500, 33);
			}
		};

		pnTitle.setBounds(0, 0, 1270, 50);
		pnTitle.setOpaque(false);
		pnTitle.setLayout(null);
		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 33, 19, 12, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		pnTitle.add(btnBack);
		pnMain.add(pnTitle);

		JPanel pnInfo = new JPanel();
		pnInfo.setLayout(null);
		pnInfo.setOpaque(false);
		pnInfo.setBounds(10, 50, 1238, 173);
		pnMain.add(pnInfo);

		dpNgaySinh = new kDatePicker(250, 20);
		dpNgaySinh.setBackgroundColor(new Color(255, 255, 255, 50));
		dpNgaySinh.setBorderCustom(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.decode("#FCA120")));
		dpNgaySinh.setForegroundCustom(Color.white);
		dpNgaySinh.setOpaqueCustom(false);
		pnInfo.add(dpNgaySinh);
		dpNgaySinh.setBounds(965, 54, 250, 20);
		txtCMND = new JTextField();

		txtCMND.setForeground(Color.WHITE);
		txtCMND.setText("312256125");
		txtCMND.setBounds(965, 29, 250, 20);
		CustomUI.getInstance().setCustomTxt(txtCMND);
		pnInfo.add(txtCMND);

		lbCMND = new JLabel("CMND/CCCD:");
		lbCMND.setForeground(Color.WHITE);
		lbCMND.setBounds(845, 29, 105, 20);
		pnInfo.add(lbCMND);

		lbNgaySinh = new JLabel("Ngày sinh:");
		lbNgaySinh.setForeground(Color.WHITE);
		lbNgaySinh.setBounds(845, 54, 105, 20);
		pnInfo.add(lbNgaySinh);

		lbGender = new JLabel("Giới tính:");
		lbGender.setForeground(Color.WHITE);
		lbGender.setBounds(845, 79, 105, 20);
		pnInfo.add(lbGender);

		txtSDT = new JTextField();
		txtSDT.setForeground(Color.WHITE);
		txtSDT.setText("0123456789");
		txtSDT.setBounds(555, 79, 250, 20);
		CustomUI.getInstance().setCustomTxt(txtSDT);
		pnInfo.add(txtSDT);

		txtMucLuong = new JTextField();
		txtMucLuong.setForeground(Color.WHITE);
		txtMucLuong.setText("8,800,000");
		txtMucLuong.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMucLuong.setBounds(555, 54, 250, 20);
		CustomUI.getInstance().setCustomTxt(txtMucLuong);
		pnInfo.add(txtMucLuong);

		cboGioiTinh = new JComboBox<String>();
		cboGioiTinh.addItem("Nam");
		cboGioiTinh.addItem("Nữ");
		cboGioiTinh.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.decode("#FCA120")));
		cboGioiTinh.setOpaque(false);
		cboGioiTinh.setEditable(true);
		cboGioiTinh.setUI(new BasicComboBoxUI());
		boxField = (JTextField) cboGioiTinh.getEditor().getEditorComponent();
		boxField.setBorder(BorderFactory.createEmptyBorder());
		boxField.setBackground(new Color(246, 210, 255, 50));
		boxField.setForeground(Color.WHITE);
		boxField.setEditable(false);
		cboGioiTinh.setBounds(965, 79, 250, 20);
		cboGioiTinh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnInfo.add(cboGioiTinh);

		lbChucVu = new JLabel("Chức vụ:");
		lbChucVu.setForeground(Color.WHITE);
		lbChucVu.setBounds(435, 29, 115, 20);
		pnInfo.add(lbChucVu);

		lbMucLuong = new JLabel("Mức lương:");
		lbMucLuong.setForeground(Color.WHITE);
		lbMucLuong.setBounds(435, 54, 115, 16);
		pnInfo.add(lbMucLuong);

		lbSDT = new JLabel("Số điện thoại:");
		lbSDT.setForeground(Color.WHITE);
		lbSDT.setBounds(435, 79, 115, 16);
		pnInfo.add(lbSDT);

		txtTenNV = new JTextField();
		txtTenNV.setForeground(Color.WHITE);
		txtTenNV.setText("Nguyễn Văn A");
		txtTenNV.setBounds(145, 54, 250, 20);
		CustomUI.getInstance().setCustomTxt(txtTenNV);
		pnInfo.add(txtTenNV);

		txtMaNhanVien = new JTextField();
		txtMaNhanVien.setForeground(Color.WHITE);
		txtMaNhanVien.setText("NV01");
		txtMaNhanVien.setEditable(false);
		txtMaNhanVien.setBounds(145, 28, 250, 20);
		txtMaNhanVien.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CustomUI.getInstance().setCustomTxt(txtMaNhanVien);

		pnInfo.add(txtMaNhanVien);

		lbMaNhanVien = new JLabel("Mã nhân viên: ");
		lbMaNhanVien.setForeground(Color.WHITE);
		lbMaNhanVien.setBackground(new Color(249, 249, 249));
		lbMaNhanVien.setBounds(20, 29, 120, 20);
		pnInfo.add(lbMaNhanVien);

		lbTenNV = new JLabel("Tên nhân viên:");
		lbTenNV.setForeground(Color.WHITE);
		lbTenNV.setBounds(20, 54, 120, 20);
		pnInfo.add(lbTenNV);

		lbTrangThai = new JLabel("Trạng thái:");
		lbTrangThai.setForeground(Color.WHITE);
		lbTrangThai.setBounds(20, 79, 120, 20);
		pnInfo.add(lbTrangThai);

		btnAdd = new MyButton(100, 35, "Thêm", gra, addIcon.getImage(), 40, 19, 19, 6);
		btnAdd.setBounds(20, 120, 100, 35);
		pnInfo.add(btnAdd);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, updateIcon.getImage(), 45, 19, 24, 6);
		btnUpdate.setBounds(150, 120, 100, 35);
		pnInfo.add(btnUpdate);

		btnRefresh = new MyButton(100, 35, "Làm mới", gra, refreshIcon.getImage(), 31, 19, 10, 5);
		btnRefresh.setBounds(1118, 120, 100, 35);
		pnInfo.add(btnRefresh);

		radConLam = new JRadioButton("Còn làm ");
		radConLam.setForeground(Color.WHITE);
		radConLam.setFocusable(false);
		radConLam.setOpaque(false);
		radConLam.setBackground(Color.WHITE);
		radConLam.setBounds(155, 78, 103, 21);
		radConLam.setSelected(true);

		radDaNghi = new JRadioButton("Đã nghỉ");
		radDaNghi.setForeground(Color.WHITE);
		radDaNghi.setFocusable(false);
		radDaNghi.setOpaque(false);
		radDaNghi.setBackground(Color.WHITE);
		radDaNghi.setBounds(276, 78, 103, 21);

		ButtonGroup group = new ButtonGroup();
		group.add(radConLam);
		group.add(radDaNghi);
		pnInfo.add(radConLam);
		pnInfo.add(radDaNghi);

		cboChucVu = new JComboBox<String>();
		cboChucVu.addItem("Nhân viên");
		cboChucVu.addItem("Quản lý");
		cboChucVu.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.decode("#FCA120")));
		cboChucVu.setOpaque(false);
		cboChucVu.setEditable(true);
		cboChucVu.setUI(new BasicComboBoxUI());
		boxFieldChucVu = (JTextField) cboChucVu.getEditor().getEditorComponent();
		boxFieldChucVu.setBorder(BorderFactory.createEmptyBorder());
		boxFieldChucVu.setBackground(new Color(246, 210, 255, 50));
		boxFieldChucVu.setForeground(Color.WHITE);
		boxFieldChucVu.setEditable(false);
		cboChucVu.setBounds(555, 28, 250, 20);
		cboChucVu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnInfo.add(cboChucVu);

		JPanel pnSearch = new JPanel();
		pnSearch.setBounds(286, 110, 822, 53);
		pnInfo.add(pnSearch);
		pnSearch.setOpaque(false);
		pnSearch.setLayout(null);
		pnInfo.add(pnSearch);

		lpSelectSreach = new JLabel("Tìm kiếm theo:");
		lpSelectSreach.setForeground(Color.WHITE);
		lpSelectSreach.setBounds(40, 18, 100, 20);
		pnSearch.add(lpSelectSreach);

		cboSelect = new JComboBox<String>();
		cboSelect.addItem("Tất cả");
		cboSelect.addItem("Tên nhân viên");
		cboSelect.addItem("Số diện thoại");
		cboSelect.addItem("Chức vụ");
		cboSelect.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.decode("#FCA120")));
		cboSelect.setOpaque(false);
		cboSelect.setEditable(true);
		cboSelect.setUI(new BasicComboBoxUI());
		boxField1 = (JTextField) cboSelect.getEditor().getEditorComponent();
		cboSelect.setBounds(140, 18, 160, 20);
		cboSelect.setCursor(new Cursor(Cursor.HAND_CURSOR));

		pnSearch.add(cboSelect);

		btnSearch = new MyButton(100, 35, "Tìm kiếm", gra, searchIcon.getImage(), 31, 19, 10, 5);
		btnSearch.setBounds(702, 10, 100, 35);
		pnSearch.add(btnSearch);

		JLabel lpKey = new JLabel("Từ khóa:");
		lpKey.setForeground(Color.WHITE);
		lpKey.setBounds(364, 18, 100, 20);
		pnSearch.add(lpKey);

		txtKey = new JTextField();
		txtKey.setText("Nguyễn Văn A");
		txtKey.setForeground(Color.WHITE);
		txtKey.setBounds(464, 18, 200, 20);
		CustomUI.getInstance().setCustomTxt(txtKey);
		pnSearch.add(txtKey);

		cboSelect1 = new JComboBox<String>();
		cboSelect1.addItem("Nhân viên");
		cboSelect1.addItem("Quản lý");
		cboSelect1.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.decode("#FCA120")));
		cboSelect1.setOpaque(false);
		cboSelect1.setEditable(true);
		cboSelect1.setUI(new BasicComboBoxUI());
		boxField2 = (JTextField) cboSelect1.getEditor().getEditorComponent();
		boxField2.setBorder(BorderFactory.createEmptyBorder());
		boxField2.setBackground(new Color(246, 210, 255, 50));
		boxField2.setForeground(Color.WHITE);
		boxField2.setEditable(false);
		cboSelect1.setBounds(440, 18, 200, 20);
		cboSelect1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnSearch.add(cboSelect1);
		cboSelect1.setVisible(false);
		btnSearch.addActionListener(this);
		btnSearch.addMouseListener(this);
		cboSelect.addActionListener(this);
		boxField1.setBorder(BorderFactory.createEmptyBorder());
		boxField1.setBackground(new Color(246, 210, 255, 50));
		boxField1.setForeground(Color.WHITE);
		boxField1.setEditable(false);
		boxField2.setBorder(BorderFactory.createEmptyBorder());
		boxField2.setBackground(new Color(246, 210, 255, 50));
		boxField2.setForeground(Color.WHITE);
		boxField2.setEditable(false);

		JPanel pnTable = new JPanel();
		pnTable.setBackground(Color.WHITE);
		pnTable.setLayout(null);
		pnTable.setBounds(8, 223, 1240, 362);
		pnTable.setOpaque(false);
		String[] cols = { "STT", "Mã nhân viên", "Tên nhân viên ", "CMND/CCCD", "Chức vụ", "Số điện thoại", "Ngày sinh",
				"Mức lương", "Giới tính", "Trạng thái" };
		modelTable = new DefaultTableModel(cols, 0);
		table = new JTable(modelTable);
		table.getTableHeader().setBackground(new Color(255, 255, 255));
		JScrollPane scpTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpTable.getViewport().setBackground(Color.WHITE);
		scpTable.setBounds(10, 10, 1220, 335);
		scpTable.setOpaque(false);
		scpTable.getViewport().setOpaque(false);
		for (int i = 0; i < 4; i++) {
			modelTable.addRow(new Object[0]);
			modelTable.setValueAt(i + 1, i, 0);
			modelTable.setValueAt("NV0" + i, i, 1);
			modelTable.setValueAt(" Nguyễn Văn A", i, 2);
			modelTable.setValueAt(" 31247178" + i, i, 3);
			modelTable.setValueAt(" Nhân viên", i, 4);
			modelTable.setValueAt(" 037772346" + i, i, 5);
			modelTable.setValueAt(" 09/12/200" + i, i, 6);
			modelTable.setValueAt(" 200000000", i, 7);
			modelTable.setValueAt(" Nam", i, 8);
			modelTable.setValueAt(" Còn làm", i, 9);
		}

		pnTable.add(scpTable);

		pnMain.add(pnTable);
		allLoaded();
		table.addMouseListener(this);

		txtTenNV.addMouseListener(this);
		txtCMND.addMouseListener(this);
		txtSDT.addMouseListener(this);
		txtMucLuong.addMouseListener(this);
		boxField.addMouseListener(this);
		boxField1.addMouseListener(this);
		boxField2.addMouseListener(this);
		boxFieldChucVu.addMouseListener(this);

	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			new PNEmployee().setVisible(true);
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(boxField)) {
			cboGioiTinh.showPopup();
		}
		if (o.equals(boxField1)) {
			cboSelect.showPopup();
		}
		if (o.equals(boxField2)) {
			cboSelect1.showPopup();
		}
		if (o.equals(boxFieldChucVu)) {
			cboChucVu.showPopup();
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

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void allLoaded() {
		reSizeColumnTable();
	}

	private void reSizeColumnTable() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(190);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(140);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(130);
		table.getColumnModel().getColumn(7).setPreferredWidth(140);
		table.getColumnModel().getColumn(8).setPreferredWidth(100);
		table.getColumnModel().getColumn(9).setPreferredWidth(120);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object.equals(cboSelect)) {
			// if (cboSelect.getSelectedIndex() == 1) {
			// txtKey.setBounds(0, 0, 0, 0);
			// }
		}
	}
}
