package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.MyButton;
import entity.NhanVien;

public class fDangNhap extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener {
	private JTextField txtUsername, txtPassword;
	private JButton btnLogin;
	private ImageIcon logoIcon = new ImageIcon(
			CustomUI.USER_ICON_512.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
	private ImageIcon anhChen = new ImageIcon(
			CustomUI.BACKGROUND_LOGIN.getImage().getScaledInstance(700, 300, Image.SCALE_SMOOTH));
	private ImageIcon loginIcon = new ImageIcon(
			CustomUI.LOGIN_ICON.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#c22ed0"), 255, 0, Color.decode("#5ffae0"));
	TaiKhoanDAO taiKhoanDAO = TaiKhoanDAO.getInstance();

	/**
	 * Constructor form đăng nhập
	 */
	public fDangNhap() {
		setTitle("Đăng nhập");
		setSize(460, 650);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		createFormLogin();
	}

	/**
	 * Khởi tạo giao diện đăng nhập
	 */
	public void createFormLogin() {
		JPanel pnMain = new JPanel();
		pnMain.setBackground(Color.decode("#ffffff"));

		JLabel lbUsername, lbPassword;
		pnMain.setLayout(null);

		getContentPane().add(pnMain);

		JPanel pnMau = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				GradientPaint gra = new GradientPaint(90, 0, Color.decode("#900a9c"), 180, getHeight(),
						Color.decode("#00cccb"));
				g2.setPaint(gra);
				g2.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		pnMau.setBounds(0, 0, 455, 323);
		pnMain.add(pnMau);
		pnMau.setLayout(null);
		JLabel lbAnhChen = new JLabel(anhChen);
		lbAnhChen.setBounds(-18, 143, 473, 281);
		pnMau.add(lbAnhChen);

		JLabel lbLogo = new JLabel(logoIcon);
		lbLogo.setBounds(0, 57, 455, 82);
		pnMau.add(lbLogo);
		lbLogo.setText("");
		lbLogo.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lbWelcome = new JLabel("Đăng nhập");
		lbWelcome.setForeground(Color.WHITE);
		lbWelcome.setFont(new Font("Dialog", Font.BOLD, 28));
		lbWelcome.setBounds(0, 138, 455, 35);
		pnMau.add(lbWelcome);
		lbWelcome.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel pnLogin = new JPanel();
		pnLogin.setBackground(Color.WHITE);

		pnLogin.setLayout(null);
		pnLogin.setBounds(0, 325, 455, 273);
		pnMain.add(pnLogin);
		lbUsername = new JLabel("Tên đăng nhập: ");
		lbUsername.setBounds(83, 12, 285, 25);
		pnLogin.add(lbUsername);
		lbUsername.setFont(new Font("Dialog", Font.BOLD, 14));
		lbUsername.setForeground(Color.decode("#1a66e3"));

		txtUsername = new JTextField();
		txtUsername.setBounds(83, 49, 285, 25);
		pnLogin.add(txtUsername);
		txtUsername.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtUsername.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

		lbPassword = new JLabel("Mật khẩu: ");
		lbPassword.setBounds(83, 86, 285, 25);
		pnLogin.add(lbPassword);
		lbPassword.setFont(new Font("Dialog", Font.BOLD, 14));
		lbPassword.setForeground(Color.decode("#1a66e3"));

		txtPassword = new JPasswordField();
		txtPassword.setBounds(83, 123, 285, 25);
		pnLogin.add(txtPassword);
		txtPassword.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

		Color colorShadowDefault = Color.decode("#d2eef5");
		Color colorFontDefault = Color.WHITE;
		btnLogin = new MyButton(285, 40, "Đăng nhập", gra, loginIcon.getImage(), 120, 22, 96, 7, colorShadowDefault,
				colorFontDefault);
		((MyButton) btnLogin).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		((MyButton) btnLogin).setColorHover(Color.WHITE);
		// ((MyButton) btnLogin).setColorExit(Color.WHITE);
		btnLogin.setBounds(83, 177, 285, 40);
		pnLogin.add(btnLogin);

		btnLogin.addActionListener(this);

		btnLogin.addMouseListener(this);
		txtPassword.addKeyListener(this);
		txtPassword.addFocusListener(this);
		txtUsername.addKeyListener(this);

		txtUsername.addFocusListener(this);
	}

	public static void main(String[] args) {
		new fDangNhap().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnLogin)) {
			String username = txtUsername.getText().trim();
			String password = txtPassword.getText().trim();
			if (validData()) {
				if (login(username, password)) {
					NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername(username);
					if (staff.getTaiKhoan().getTinhTrangTK() == true) {
						fDieuHuong f = new fDieuHuong(staff);
						this.setVisible(false);
						f.setVisible(true);
					} else {
						showMessage("Tài khoản của bạn đã bị chủ quán vô hiện hóa");
					}
				} else {
					showMessage("Sai tài khoản hoặc mật khẩu");
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		// bắt sự kiện nhấn phím enter tự nhấn btnLogin
		if (o.equals(txtUsername) || o.equals(txtPassword)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnLogin.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtUsername)) {
			txtUsername.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtPassword)) {
			txtPassword.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtUsername)) {
			txtUsername.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtPassword)) {
			txtPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
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
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Hiển thị popup thông báo
	 * 
	 * @param message <code>String</code>: nội dung thông báo
	 */
	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.OK_OPTION);
	}

	/**
	 * Kiểm tra thông tin trước khi đăng nhập
	 * 
	 * @return <code>boolean</code>: true nếu hợp lê, false nếu không hợp lệ
	 */
	private boolean validData() {
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();
		if (!(username.length() >= 6 && username.matches("^[a-zA-Z0-9_@#]{6,}$"))) {
			if (username.length() < 6)
				showMessage("Tên đăng nhập phải tối thiểu 6 ký tự");
			else
				showMessage("Tên đăng nhập chỉ có thể chứa các kỳ tự, số, @, #, _");
			txtUsername.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
			return false;
		}
		if (!(password.length() >= 6 && password.matches("^[a-zA-Z0-9_@#]{6,}$"))) {
			if (password.length() < 6)
				showMessage("Mật khẩu phải tối thiểu 6 ký tự");
			else
				showMessage("Mật khẩu chỉ có thể chứa các kỳ tự, số, @, #, _");
			txtPassword.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
			return false;
		}
		return true;
	}

	/**
	 * Đăng nhập tài khoản
	 * 
	 * @param username <code>String</code>: tên đăng nhập
	 * @param password <code>String</code>: mật khẩu
	 * @return <code>boolean</code>: true nếu hợp lê, false nếu không hợp lệ
	 */
	private boolean login(String username, String password) {
		boolean result = TaiKhoanDAO.getInstance().dangNhap(username, password);
		return result;
	}
}
