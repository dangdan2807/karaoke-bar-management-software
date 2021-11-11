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
	/**
	 * 
	 */
	private static final long serialVersionUID = 4368075097887104646L;
	private JTextField txtUsername, txtPassword;
	private JButton btnLogin;

	private ImageIcon logoIcon = new ImageIcon(
			CustomUI.USER_ICON_512.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
	private ImageIcon backgroundTop = new ImageIcon(
			CustomUI.BACKGROUND_LOGIN.getImage().getScaledInstance(700, 300, Image.SCALE_SMOOTH));
	private ImageIcon loginIcon = new ImageIcon(
			CustomUI.LOGIN_ICON.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#900a9c"), 250, 0, Color.decode("#00cccb"));
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
		JPanel pnlMain = new JPanel();
		pnlMain.setBackground(Color.decode("#ffffff"));

		JLabel lblUsername, lblPassword;
		pnlMain.setLayout(null);

		getContentPane().add(pnlMain);

		JPanel pnlMau = new JPanel() {
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
		pnlMau.setBounds(0, 0, 455, 323);
		pnlMain.add(pnlMau);
		pnlMau.setLayout(null);

		JLabel lblBackgroundTop = new JLabel(backgroundTop);
		lblBackgroundTop.setBounds(-18, 143, 473, 281);
		pnlMau.add(lblBackgroundTop);

		JLabel lblLogo = new JLabel(logoIcon);
		lblLogo.setBounds(0, 57, 455, 82);
		pnlMau.add(lblLogo);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblWelcome = new JLabel("Đăng nhập");
		lblWelcome.setForeground(Color.WHITE);
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 28));
		lblWelcome.setBounds(0, 138, 455, 35);
		pnlMau.add(lblWelcome);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel pnlLogin = new JPanel();
		pnlLogin.setBackground(Color.WHITE);

		pnlLogin.setLayout(null);
		pnlLogin.setBounds(0, 325, 455, 273);
		pnlMain.add(pnlLogin);

		lblUsername = new JLabel("Tên đăng nhập: ");
		lblUsername.setBounds(83, 12, 285, 25);
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 14));
		lblUsername.setForeground(Color.decode("#1a66e3"));
		pnlLogin.add(lblUsername);

		txtUsername = new JTextField("phamdangdan");
		txtUsername.setBounds(83, 49, 285, 25);
		txtUsername.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtUsername.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		pnlLogin.add(txtUsername);

		lblPassword = new JLabel("Mật khẩu: ");
		lblPassword.setBounds(83, 86, 285, 25);
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPassword.setForeground(Color.decode("#1a66e3"));
		pnlLogin.add(lblPassword);

		txtPassword = new JPasswordField("1234567");
		txtPassword.setBounds(83, 123, 285, 25);
		pnlLogin.add(txtPassword);
		txtPassword.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

		Color colorShadowDefault = Color.decode("#d2eef5");
		Color colorFontDefault = Color.WHITE;
		btnLogin = new MyButton(285, 40, "Đăng nhập", gra, loginIcon.getImage(), 120, 22, 96, 7, colorShadowDefault,
				colorFontDefault);
		((MyButton) btnLogin).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		((MyButton) btnLogin).setColorHover(Color.WHITE);
		btnLogin.setBounds(83, 177, 285, 40);
		pnlLogin.add(btnLogin);

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
			// xác thực dữ liệu trên form
			if (validData()) {
				boolean loginResult = login(username, password);
				// nếu tài khoản, mật khẩu hợp lệ và không bị vô hiệu hóa thì đăng nhập thành
				// công
				if (loginResult) {
					NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername(username);
					// kiểm tra tài khoản có bị vô hiệu hóa hay không
					if (staff.getTaiKhoan().getTinhTrangTK() == true) {
						fDieuHuong winDieuHuong = new fDieuHuong(staff);
						this.setVisible(false);
						winDieuHuong.setVisible(true);

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
	 * @param message {@code String}: nội dung thông báo
	 */
	private void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.OK_OPTION);
	}

	/**
	 * Kiểm tra thông tin trước khi đăng nhập
	 * 
	 * @return {@code boolean}: kết quả trả về của quá trình kiểm tra
	 *         <ul>
	 *         <li>Nếu hợp lệ thì trả về {@code true}</li>
	 *         <li>Nếu không hợp lệ thì trả về {@code false}</li>
	 *         </ul>
	 */
	private boolean validData() {
		String username = txtUsername.getText().trim();
		String password = txtPassword.getText().trim();
		// username tối thiểu 6 ký tự và không được quá 100 ký tự
		// username có thể chứa ký tự, số và @, !, #, _
		if (!(username.length() >= 6 && password.length() <= 100 && username.matches("^[a-zA-Z0-9_@#!]{6,100}$"))) {
			if (username.length() < 6)
				showMessage("Tên đăng nhập phải tối thiểu 6 ký tự");
			else if (username.length() > 100)
				showMessage("Tên đăng nhập không quá 100 ký tự");
			else
				showMessage("Tên đăng nhập chỉ có thể chứa các kỳ tự, số, @, #, !");
			txtUsername.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
			return false;
		}

		// password tối thiểu 6 ký tự và không được quá 100 ký tự
		// password có thể chứa ký tự, số và @, #
		if (!(password.length() >= 6 && password.matches("^[a-zA-Z0-9@#!]{6,100}$"))) {
			if (password.length() < 6)
				showMessage("Mật khẩu phải tối thiểu 6 ký tự");
			else if (password.length() > 100)
				showMessage("Mật khẩu không quá 100 ký tự");
			else
				showMessage("Mật khẩu chỉ có thể chứa các kỳ tự, số, @, #, !");
			txtPassword.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
			return false;
		}
		return true;
	}

	/**
	 * Đăng nhập tài khoản
	 * 
	 * @param username {@code String}: tên đăng nhập
	 * @param password {@code String}: mật khẩu
	 * @return {@code boolean}: kết quả trả về của câu truy vấn
	 *         <ul>
	 *         <li>Nếu thêm thành công thì trả về {@code true}</li>
	 *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
	 *         </ul>
	 */
	private boolean login(String username, String password) {
		boolean result = TaiKhoanDAO.getInstance().login(username, password);
		return result;
	}
}
