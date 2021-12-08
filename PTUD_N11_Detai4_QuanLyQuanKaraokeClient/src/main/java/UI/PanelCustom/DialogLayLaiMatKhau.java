package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.util.Random;

import javax.swing.*;

import Event_Handlers.*;
import entity.NhanVien;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.*;
import com.twilio.type.PhoneNumber;

import DAO.TaiKhoanDAO;

/**
 * Giao diện đăng nhập của phần mềm
 * <p>
 * Người tham gia thiết kế: Võ Minh Hiếu
 * <p>
 * Ngày tạo: 04/12/2021
 * <p>
 * Lần cập nhật cuối: 08/12/2021
 * <p>
 */
public class DialogLayLaiMatKhau extends JFrame
		implements ActionListener, KeyListener, FocusListener, MouseListener, ItemListener {
	private ImageIcon logoApp = CustomUI.LOGO_APP;
	private ImageIcon checkIcon = CustomUI.CHECK_ICON;
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private JLabel lblPhoneNumber, lblConfirmationCode, lblNewPassword, lblReEnterPassword;
	private JTextField txtPhoneNumber, txtConfirmationCode;
	private JPasswordField txtNewPassword, txtReEnterPassword;
	private JCheckBox chkShowPassword;
	private MyButton btnBack, btnComplete, btnGetCode, btnConfirmCode;
	private String code = "";

	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private String username = "";
	public static final String ACCOUNT_SID = "ACdb3d3e69764436434bfdc11e5ea3e3ea";
	public static final String AUTH_TOKEN = "e9c53c2a7d0baed29465e15c9f50d0e2";
	public static final String PHONE_NUMBER_SEND_CODE = "";

	/**
	 * Khởi tạo giao diện lấy lại mật khẩu
	 */
	public DialogLayLaiMatKhau(String username) {
		this.username = username;
		setTitle("Quên mật khẩu");
		setSize(460, 600);
		setIconImage(logoApp.getImage());
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnlMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				GradientPaint gra = new GradientPaint(90, 0, Color.decode("#900a9c"), 180, getHeight(),
						Color.decode("#00cccb"));
				g2.setPaint(gra);
				g2.fillRect(0, 0, getWidth(), getHeight());
				setFont(new Font("Dialog", Font.BOLD, 24));
			}
		};
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 460, 600);
		this.add(pnlMain);

		lblPhoneNumber = new JLabel("Số điện thoại:");
		lblPhoneNumber.setBounds(49, 65, 250, 25);
		lblPhoneNumber.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPhoneNumber.setForeground(Color.decode("#fffffff"));
		pnlMain.add(lblPhoneNumber);

		txtPhoneNumber = new JTextField("0389324159");
		txtPhoneNumber.setBounds(49, 90, 240, 30);
		CustomUI.getInstance().setCustomTextFieldOn(txtPhoneNumber);
		pnlMain.add(txtPhoneNumber);

		lblConfirmationCode = new JLabel("Nhập mã xác nhận:");
		lblConfirmationCode.setBounds(49, 155, 250, 25);
		lblConfirmationCode.setFont(new Font("Dialog", Font.BOLD, 14));
		lblConfirmationCode.setForeground(Color.decode("#fffffff"));
		pnlMain.add(lblConfirmationCode);

		txtConfirmationCode = new JTextField("");
		txtConfirmationCode.setBounds(49, 180, 240, 30);
		CustomUI.getInstance().setCustomTextFieldOn(txtConfirmationCode);
		pnlMain.add(txtConfirmationCode);

		lblNewPassword = new JLabel("Nhập mật khẩu mới:");
		lblNewPassword.setBounds(49, 245, 300, 25);
		lblNewPassword.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewPassword.setForeground(Color.decode("#fffffff"));
		pnlMain.add(lblNewPassword);

		txtNewPassword = new JPasswordField("");
		txtNewPassword.setBounds(49, 270, 350, 30);
		CustomUI.getInstance().setCustomTextFieldOn(txtNewPassword);
		pnlMain.add(txtNewPassword);

		lblReEnterPassword = new JLabel("Nhập mật khẩu mới:");
		lblReEnterPassword.setBounds(49, 335, 300, 25);
		lblReEnterPassword.setFont(new Font("Dialog", Font.BOLD, 14));
		lblReEnterPassword.setForeground(Color.decode("#fffffff"));
		pnlMain.add(lblReEnterPassword);

		txtReEnterPassword = new JPasswordField("");
		txtReEnterPassword.setBounds(49, 360, 350, 30);
		CustomUI.getInstance().setCustomTextFieldOn(txtReEnterPassword);
		pnlMain.add(txtReEnterPassword);

		chkShowPassword = new JCheckBox("Hiện mật khẩu");
		chkShowPassword.setBounds(300, 410, 120, 25);
		pnlMain.add(chkShowPassword);
		chkShowPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chkShowPassword.setContentAreaFilled(false);
		chkShowPassword.setFocusPainted(false);
		chkShowPassword.setBorder(null);
		chkShowPassword.setForeground(Color.decode("#ffffff"));
		chkShowPassword.setFont(new Font("Dialog", Font.PLAIN, 15));

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 30, 19);
		btnBack.setBounds(320, 480, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlMain.add(btnBack);

		btnComplete = new MyButton(100, 35, "Xác nhận", gra, checkIcon.getImage(), 25, 19, 5, 5);
		btnComplete.setBounds(180, 480, 100, 35);
		btnComplete.setToolTipText("Kích hoạt lấy lại mật khẩu");
		btnComplete.setEnabledCustom(false);
		pnlMain.add(btnComplete);

		btnGetCode = new MyButton(100, 35, "Lấy mã", gra, null, 25, 19, 5, 5);
		btnGetCode.setBounds(299, 90, 100, 35);
		pnlMain.add(btnGetCode);

		btnConfirmCode = new MyButton(100, 35, "Xác nhận", gra, null, 18, 19, 5, 5);
		btnConfirmCode.setBounds(299, 175, 100, 35);
		btnConfirmCode.setEnabledCustom(false);
		pnlMain.add(btnConfirmCode);

		txtNewPassword.addFocusListener(this);
		txtPhoneNumber.addFocusListener(this);
		txtReEnterPassword.addFocusListener(this);
		txtConfirmationCode.addFocusListener(this);

		txtPhoneNumber.addKeyListener(this);

		btnBack.addActionListener(this);
		btnGetCode.addActionListener(this);
		btnComplete.addActionListener(this);
		btnConfirmCode.addActionListener(this);

		chkShowPassword.addItemListener(this);
	}

	public static void main(String[] args) {
		new DialogLayLaiMatKhau("phamdangdan").setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnBack)) {
			this.dispose();
		} else if (o.equals(btnGetCode)) {
			boolean valid = ValidationData.getInstance().ValidPhoneNumber(this, txtPhoneNumber);
			if (valid) {
				Random rd = new Random();
				code = rd.nextInt(999999) + "";
				String phoneNumber = txtPhoneNumber.getText().toString().trim();
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
				phoneNumber = "+84" + phoneNumber.substring(1);
				Message.creator(new PhoneNumber(phoneNumber), // to
						new PhoneNumber(PHONE_NUMBER_SEND_CODE), // from
						code)
						.create();
				btnConfirmCode.setEnabledCustom(true);
			}
		} else if (o.equals(btnComplete)) {
			String username = txtPhoneNumber.getText().toString().trim();
			if (validData()) {
				boolean result = false;
				username = "";
				String password = txtNewPassword.getText().toString().trim();
				try {
					TaiKhoanDAO accountDAO = (TaiKhoanDAO) Naming.lookup("rmi://localhost:1099/accountDAO");
					result = accountDAO.resetPassword(username, password);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (result) {
					JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (o.equals(btnConfirmCode)) {
			String codeConfirm = txtConfirmationCode.getText().toString().trim();
			if (codeConfirm.equals(code)) {
				btnComplete.setEnabledCustom(true);
			} else {
				String message = "Mã xác nhận không đúng";
				showMessage(txtConfirmationCode, 1, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(chkShowPassword)) {
			if (chkShowPassword.isSelected()) {
				txtNewPassword.setEchoChar((char) 0);
				txtReEnterPassword.setEchoChar((char) 0);
			} else {
				txtNewPassword.setEchoChar('*');
				txtReEnterPassword.setEchoChar('*');
			}
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

	@Override
	public void focusGained(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtConfirmationCode)) {
			txtConfirmationCode.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtNewPassword)) {
			txtNewPassword.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtPhoneNumber)) {
			txtPhoneNumber.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		} else if (o.equals(txtReEnterPassword)) {
			txtReEnterPassword.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		Object o = e.getSource();
		if (o.equals(txtConfirmationCode)) {
			txtConfirmationCode.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtNewPassword)) {
			txtNewPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtPhoneNumber)) {
			txtPhoneNumber.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		} else if (o.equals(txtReEnterPassword)) {
			txtReEnterPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		InputEventHandler handler = new InputEventHandler();
		if (o.equals(txtPhoneNumber)) {
			handler.enterOnlyNumbersAndLimitInput(key, txtPhoneNumber, 10);
		}
	}

	/**
	 * Hiển thị popup thông báo của 1 {@code JTextField}
	 * 
	 * @param txt     {@code JTextField}: được trỏ đến khi cần thông báo
	 * @param type    {@code int}: mã dạng thông báo (Nếu 1. là lỗi)
	 * @param message {@code String}: Tin nhắn được hiển thị
	 * @param title   {@code String}: Tiêu đề thông báo
	 * @param option  {@code int}: loại thông báo (icon)
	 */
	private void showMessage(JTextField txt, int type, String message, String title, int option) {
		if (type == 1) {
			txt.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
		}
		txt.requestFocus();
		JOptionPane.showMessageDialog(this, message, title, option);
	}

	/**
	 * Kiểm tra mât khẩu
	 * 
	 * @param password {@code String}: mật khẩu
	 * @param message  {@code String}: tên loại mật khẩu (mật khẩu, mật khẩu mới,
	 *                 nhập lại mật khẩu, ...)
	 */
	private void checkPassword(String password, String message) {
		String text = message;
		if (password.length() < 6) {
			text = message + " phải tối thiểu 6 ký tự";
			showMessage(txtNewPassword, 1, text, "Thông báo", JOptionPane.ERROR_MESSAGE);
		} else {
			text = message + " chỉ có thể chứa các kỳ tự, số, @, #, !";
			showMessage(txtNewPassword, 1, text, "Thông báo", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Kiểm tra thông tin trong form
	 * 
	 * @return {@code boolean}: kết quả trả về của quá trình kiểm tra thông tin
	 *         trong form
	 *         <ul>
	 *         <li>Nếu hợp lệ thì trả về {@code true}</li>
	 *         <li>Nếu không hợp lệ thì trả về {@code false}</li>
	 *         </ul>
	 */
	private boolean validData() {
		String message = "";
		boolean valid = ValidationData.getInstance().ValidName(this, null, "tên đăng nhập", 100, 0);
		if (!valid) {
			return false;
		}

		String newPassword = txtNewPassword.getText().trim();
		// Trường hợp: mật khẩu rỗng
		if (newPassword.equalsIgnoreCase("") || newPassword.length() <= 0) {
			message = "Mật khẩu không được rỗng";
			showMessage(txtNewPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;

			// Trường hợp: có chiều dài ít hơn 6 ký tự và phù hợp regex:
			// ^[a-zA-Z0-9@!#]{6,}$
		} else if (!(newPassword.length() >= 6 && newPassword.matches("^[a-zA-Z0-9@!#]{6,}$"))) {
			checkPassword(newPassword, "Mật khẩu");
			return false;
		}

		// Nếu đối mật khẩu
		String rePassword = txtReEnterPassword.getText().trim();

		if (!(rePassword.equals(newPassword))) {
			message = "Mật khẩu nhập lại không khớp mật khẩu mới";
			showMessage(txtReEnterPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;

		} else if (newPassword.equals("") || newPassword.length() <= 0) {
			message = "Mật khẩu mới không được rỗng";
			showMessage(txtNewPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;

		} else if (rePassword.equals("") || rePassword.length() <= 0) {
			message = "Mật khẩu nhập lại không được rỗng";
			showMessage(txtReEnterPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
			return false;

		} else if (!(rePassword.length() >= 6 && rePassword.matches("^[a-zA-Z0-9_@!#]{6,}$"))) {
			checkPassword(rePassword, "Mật khẩu nhập lại");
			return false;

		} else if (!(newPassword.length() >= 6 && newPassword.matches("^[a-zA-Z0-9_@!#]{6,}$"))) {
			checkPassword(newPassword, "Mật khẩu mới");
			return false;

		}
		return true;
	}

}
