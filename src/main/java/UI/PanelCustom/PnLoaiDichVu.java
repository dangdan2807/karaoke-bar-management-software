package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.*;

public class PnLoaiDichVu extends JFrame implements ActionListener, MouseListener, ItemListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel modelTable;
	private JButton btnSearch;
	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon addIcon = CustomUI.ADD_ICON;
	private ImageIcon refreshIcon = CustomUI.REFRESH_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon updateIcon = CustomUI.UPDATE_ICON;
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	int index = 1;
	private MyButton btnAdd;
	private MyButton btnUpdate;
	private MyButton btnRefresh;
	private MyButton btnBack;
	private JTextField boxField;
	private JComboBox<String> cboSearch;
	private JTextField boxField1;
	private JLabel lpSearch;
	// private JTextField textField;
	private JTextField txtKeyWord;
	private JComboBox<String> cboSelect1;
	private JTextField boxField2;
	private JTextField txtServiceTypeID;
	private JTextField txtServiceTypeName;

	public PnLoaiDichVu() {
		setSize(1270, 630);
		getContentPane().setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnMain = new JPanel() {
			/**
			 * 
			 */
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
		btnAdd.setToolTipText("Thêm nhân viên mới sau khi đã điền đủ thông tin");
		btnAdd.setBounds(20, 93, 100, 35);
		// btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 16));
		pnInfo.add(btnAdd);

		btnUpdate = new MyButton(100, 35, "Sửa", gra, updateIcon.getImage(), 43, 19);
		btnUpdate.setToolTipText("Sửa thông tin nhân viên");
		btnUpdate.setBounds(150, 93, 100, 35);
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
		boxField1 = (JTextField) cboSearch.getEditor().getEditorComponent();
		boxField1.setBorder(BorderFactory.createEmptyBorder());
		boxField1.setBackground(new Color(246, 210, 255, 50));
		boxField1.setForeground(Color.WHITE);
		boxField1.setEditable(false);
		boxField1.setFont(new Font("Dialog", Font.PLAIN, 12));
		cboSearch.setBounds(140, 18, 160, 20);
		cboSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
		txtKeyWord.setForeground(Color.WHITE);
		txtKeyWord.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtKeyWord.setBounds(440, 18, 200, 20);
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtKeyWord);
		pnSearch.add(txtKeyWord);

		cboSelect1 = new JComboBox<String>();
		cboSelect1.addItem("Nam");
		cboSelect1.addItem("Nữ");
		cboSelect1.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		cboSelect1.setOpaque(false);
		cboSelect1.setEditable(true);
		cboSelect1.setUI(new BasicComboBoxUI());
		boxField2 = (JTextField) cboSelect1.getEditor().getEditorComponent();
		boxField2.setFont(new Font("Dialog", Font.PLAIN, 12));
		boxField2.setBorder(BorderFactory.createEmptyBorder());
		boxField2.setBackground(new Color(246, 210, 255, 50));
		boxField2.setForeground(Color.WHITE);
		boxField2.setEditable(false);
		cboSelect1.setBounds(440, 18, 200, 20);
		cboSelect1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnSearch.add(cboSelect1);

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
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtServiceTypeName);
		pnInfo.add(txtServiceTypeName);
		cboSelect1.setVisible(false);
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
		table = new JTable(modelTable);
		table.setBackground(new Color(255, 255, 255, 0));
		table.setForeground(new Color(255, 255, 255));
		table.setRowHeight(21);
		table.setFont(new Font("Dialog", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 13));
		table.getTableHeader().setForeground(Color.decode("#9B17EB"));
		JScrollPane scpTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scpTable.getViewport().setBackground(Color.WHITE);
		scpTable.setBounds(10, 10, 1220, 350);
		scpTable.setOpaque(false);
		scpTable.getViewport().setOpaque(false);

		pnTable.add(scpTable);

		pnMain.add(pnTable);
		allLoaded();
		table.addMouseListener(this);
		boxField1.addMouseListener(this);
		boxField2.addMouseListener(this);

	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			new PnLoaiDichVu().setVisible(true);
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(boxField1)) {
			cboSearch.showPopup();
		}
		if (o.equals(boxField2)) {
			cboSelect1.showPopup();
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
		// Object o = e.getSource();
		// Object key = e.getKeyCode();
		// if (o.equals(txtSreachID)) {
		// if (key.equals(KeyEvent.VK_ENTER))
		// btnSearch.doClick();
		// }
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void allLoaded() {
		reSizeColumnTable();
	}

	private void reSizeColumnTable() {

		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object object = e.getSource();
		if (object.equals(cboSearch)) {
			// if (cboSelect.getSelectedIndex() == 1) {
			// txtKey.setBounds(0, 0, 0, 0);
			// }
		}
	}
}
