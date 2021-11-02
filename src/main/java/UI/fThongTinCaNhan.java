package UI;

import javax.swing.*;

import DAO.*;
import Event_Handlers.InputEventHandler;
import UI.PanelCustom.*;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DecimalFormat;

import javax.swing.border.TitledBorder;

public class fThongTinCaNhan extends JDialog
        implements ActionListener, KeyListener, MouseListener, FocusListener, ItemListener {
    private JLabel lblUsername, lblFullName, lblPassword, lblNewPassword, lblReNewPassword, lblGender;
    private JLabel lblSalary;
    private JTextField txtUsername, txtFullName, txtPassword, txtNewPassword, txtReNewPassword;
    private JTextField txtCMND, txtPhoneNumber, txtPosition, txtSalary, txtEmpID;
    private MyButton btnUpdate, btnBack;
    private kDatePicker dpBirthday;
    private JCheckBox chkChangePassword;
    private JRadioButton radMale, radFemale;

    private ImageIcon background = new ImageIcon(
            CustomUI.BACKGROUND.getImage().getScaledInstance(965, 512, Image.SCALE_SMOOTH));
    private int withPn = 965, heightPn = 500;
    private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
            Color.decode("#FAFFD1"));

    private DecimalFormat df = new DecimalFormat("#,###.##");
    private NhanVien staffLogin = null;

    /**
     * Constructor form thông tin cá nhân
     * 
     * @param staff {@code NhanVien}: nhân viên truy cập
     */
    public fThongTinCaNhan(NhanVien staff) {
        setTitle("Quản Lý Thông Tin Tài Khoản");
        setSize(965, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.staffLogin = staff;
        createFormAccountProfile();
    }

    /**
     * Khởi tạo giao diện
     */
    public void createFormAccountProfile() {
        JPanel pnlMain = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Image bgMain = background.getImage();
                g2.drawImage(bgMain, 0, 0, null);
                g2.setColor(new Color(255, 255, 255));
                setFont(new Font("Dialog", Font.BOLD, 24));
                g2.drawString("QUẢN LÝ THÔNG TIN CÁ NHÂN", 280, 33);
            }
        };
        pnlMain.setBounds(0, 0, withPn, heightPn);
        pnlMain.setLayout(null);
        getContentPane().add(pnlMain);

        btnBack = new MyButton(100, 35, "Quay lại", gra, CustomUI.BACK_ICON.getImage(), 30, 19, 9, 5);
        btnBack.setBounds(840, 10, 100, 35);
        btnBack.setToolTipText("Quay lại giao diện điều hướng");
        pnlMain.add(btnBack);

        JPanel pnlPersonalInfo = new JPanel();
        pnlPersonalInfo.setBorder(new TitledBorder(null, "Thông tin cá nhân ", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Dialog", Font.BOLD, 15), Color.white));
        pnlPersonalInfo.setBackground(new Color(196, 196, 196, 26));
        pnlPersonalInfo.setBounds(470, 50, 460, 352);
        pnlMain.add(pnlPersonalInfo);
        pnlPersonalInfo.setLayout(null);

        JLabel lblMaNV = new JLabel("Mã nhân viên: ");
        lblMaNV.setForeground(Color.WHITE);
        lblMaNV.setFont(new Font("Dialog", Font.BOLD, 14));
        lblMaNV.setBounds(40, 30, 120, 25);
        pnlPersonalInfo.add(lblMaNV);

        txtEmpID = new JTextField();
        CustomUI.getInstance().setCustomTextFieldOff(txtEmpID);
        txtEmpID.setBounds(160, 30, 250, 25);
        pnlPersonalInfo.add(txtEmpID);

        lblFullName = new JLabel("Tên nhân viên: ");
        lblFullName.setForeground(Color.WHITE);
        lblFullName.setFont(new Font("Dialog", Font.BOLD, 14));
        lblFullName.setBounds(40, 65, 120, 25);
        pnlPersonalInfo.add(lblFullName);

        txtFullName = new JTextField();
        txtFullName.setBounds(160, 65, 250, 25);
        CustomUI.getInstance().setCustomTextFieldUnFocus(txtFullName);
        pnlPersonalInfo.add(txtFullName);

        JLabel lblCMND = new JLabel("CMND/CCCD: ");
        lblCMND.setForeground(Color.WHITE);
        lblCMND.setFont(new Font("Dialog", Font.BOLD, 14));
        lblCMND.setBounds(40, 100, 120, 25);
        pnlPersonalInfo.add(lblCMND);

        txtCMND = new JTextField();
        txtCMND.setBounds(160, 100, 250, 25);
        txtCMND.setToolTipText("Nhập CMND gồm có 9 số hoặc CCCD gồm có 12 số");
        CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
        pnlPersonalInfo.add(txtCMND);

        dpBirthday = new kDatePicker(250, 25);
        dpBirthday.setBackgroundColor(new Color(255, 255, 255, 50));
        dpBirthday.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        dpBirthday.setForegroundCustom(Color.white);
        dpBirthday.setOpaqueCustom(false);
        dpBirthday.setFontCustom(new Font("Dialog", Font.PLAIN, 14));
        dpBirthday.setBounds(160, 135, 250, 25);
        pnlPersonalInfo.add(dpBirthday);

        JLabel lblBirthDay = new JLabel("Ngày sinh: ");
        lblBirthDay.setForeground(Color.WHITE);
        lblBirthDay.setFont(new Font("Dialog", Font.BOLD, 14));
        lblBirthDay.setBounds(40, 135, 120, 25);
        pnlPersonalInfo.add(lblBirthDay);

        JLabel lblPhoneNumber = new JLabel("Số điện thoại: ");
        lblPhoneNumber.setForeground(Color.WHITE);
        lblPhoneNumber.setFont(new Font("Dialog", Font.BOLD, 14));
        lblPhoneNumber.setBounds(40, 170, 120, 25);
        pnlPersonalInfo.add(lblPhoneNumber);

        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setBounds(160, 170, 250, 25);
        txtPhoneNumber.setToolTipText("Nhập số điện thoại của bạn gồm 10 số và bắt đầu bằng 03, 05, 07, 08, 09");
        CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
        pnlPersonalInfo.add(txtPhoneNumber);

        txtPosition = new JTextField();
        txtPosition.setForeground(Color.WHITE);
        txtPosition.setBounds(160, 205, 250, 25);
        pnlPersonalInfo.add(txtPosition);
        txtPosition.setEditable(false);
        txtPosition.setFont(new Font("Dialog", Font.PLAIN, 14));
        CustomUI.getInstance().setCustomTextFieldOff(txtPosition);

        JLabel lblPosition = new JLabel("Chức vụ: ");
        lblPosition.setForeground(Color.WHITE);
        lblPosition.setFont(new Font("Dialog", Font.BOLD, 14));
        lblPosition.setBounds(40, 205, 115, 25);
        pnlPersonalInfo.add(lblPosition);

        lblGender = new JLabel("Giới tính: ");
        lblGender.setForeground(Color.WHITE);
        lblGender.setFont(new Font("Dialog", Font.BOLD, 14));
        lblGender.setBounds(40, 240, 115, 25);
        pnlPersonalInfo.add(lblGender);

        radMale = new JRadioButton("Nam");
        CustomUI.getInstance().setCustomRadioButton(radMale);
        radMale.setBounds(155, 240, 100, 25);
        radMale.setSelected(true);
        pnlPersonalInfo.add(radMale);

        radFemale = new JRadioButton("Nữ");
        CustomUI.getInstance().setCustomRadioButton(radFemale);
        radFemale.setBounds(275, 240, 100, 25);
        pnlPersonalInfo.add(radFemale);

        ButtonGroup groupGender = new ButtonGroup();
        groupGender.add(radMale);
        groupGender.add(radFemale);

        txtSalary = new JTextField();
        txtSalary.setBounds(160, 275, 250, 25);
        txtSalary.setHorizontalAlignment(SwingConstants.RIGHT);
        CustomUI.getInstance().setCustomTextFieldOff(txtSalary);
        pnlPersonalInfo.add(txtSalary);

        lblSalary = new JLabel("Mức Lương: ");
        lblSalary.setForeground(Color.WHITE);
        lblSalary.setFont(new Font("Dialog", Font.BOLD, 14));
        lblSalary.setBounds(40, 275, 120, 25);
        pnlPersonalInfo.add(lblSalary);

        JPanel pnlAccountInfo = new JPanel();
        pnlAccountInfo.setLayout(null);
        pnlAccountInfo.setBorder(new TitledBorder(null, "Thông tin tài khoản ", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Dialog", Font.BOLD, 15), Color.white));
        pnlAccountInfo.setBackground(new Color(196, 196, 196, 26));
        pnlAccountInfo.setBounds(10, 50, 440, 145);
        pnlMain.add(pnlAccountInfo);

        lblUsername = new JLabel("Tên đăng nhập: ");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Dialog", Font.BOLD, 14));
        lblUsername.setBounds(32, 35, 120, 25);
        pnlAccountInfo.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(152, 35, 250, 25);
        txtUsername.setToolTipText("Tên đăng nhập của bạn");
        CustomUI.getInstance().setCustomTextFieldOff(txtUsername);
        pnlAccountInfo.add(txtUsername);

        lblPassword = new JLabel("Mật khẩu: ");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Dialog", Font.BOLD, 14));
        lblPassword.setBounds(32, 80, 120, 25);
        pnlAccountInfo.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(152, 80, 250, 25);
        CustomUI.getInstance().setCustomTextFieldUnFocus(txtPassword);
        pnlAccountInfo.add(txtPassword);

        JPanel pnlPasswordInfo = new JPanel();
        pnlPasswordInfo.setLayout(null);
        pnlPasswordInfo.setBorder(new TitledBorder(null, "Đổi mật khẩu ", TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Dialog", Font.BOLD, 15), Color.WHITE));
        pnlPasswordInfo.setBackground(new Color(196, 196, 196, 26));
        pnlPasswordInfo.setBounds(10, 210, 440, 192);
        pnlMain.add(pnlPasswordInfo);

        lblNewPassword = new JLabel("Mật khẩu mới: ");
        lblNewPassword.setForeground(Color.WHITE);
        lblNewPassword.setFont(new Font("Dialog", Font.BOLD, 14));
        lblNewPassword.setBounds(33, 80, 120, 25);
        pnlPasswordInfo.add(lblNewPassword);

        txtNewPassword = new JPasswordField();
        txtNewPassword.setBounds(153, 80, 250, 25);
        CustomUI.getInstance().setCustomTextFieldOff(txtNewPassword);
        pnlPasswordInfo.add(txtNewPassword);

        chkChangePassword = new JCheckBox("Đổi mật khẩu");
        chkChangePassword.setForeground(Color.WHITE);
        chkChangePassword.setBounds(30, 36, 370, 23);
        chkChangePassword.setOpaque(false);
        chkChangePassword.setFont(new Font("Dialog", Font.BOLD, 14));
        chkChangePassword.setBackground(Color.WHITE);
        pnlPasswordInfo.add(chkChangePassword);

        lblReNewPassword = new JLabel("Nhập lại: ");
        lblReNewPassword.setForeground(Color.WHITE);
        lblReNewPassword.setFont(new Font("Dialog", Font.BOLD, 14));
        lblReNewPassword.setBounds(33, 125, 120, 25);
        pnlPasswordInfo.add(lblReNewPassword);

        txtReNewPassword = new JPasswordField();
        txtReNewPassword.setBounds(153, 125, 250, 25);
        CustomUI.getInstance().setCustomTextFieldOff(txtReNewPassword);
        pnlPasswordInfo.add(txtReNewPassword);

        btnUpdate = new MyButton(120, 35, "Đổi thông tin", gra, null, 13, 19);
        btnUpdate.setBounds(790, 415, 120, 35);
        pnlMain.add(btnUpdate);

        btnUpdate.addActionListener(this);
        btnBack.addActionListener(this);

        txtFullName.addKeyListener(this);
        txtPassword.addKeyListener(this);
        txtNewPassword.addKeyListener(this);
        txtReNewPassword.addKeyListener(this);
        txtPhoneNumber.addKeyListener(this);
        txtCMND.addKeyListener(this);

        txtFullName.addFocusListener(this);
        txtPassword.addFocusListener(this);
        txtCMND.addFocusListener(this);
        txtPhoneNumber.addFocusListener(this);
        txtNewPassword.addFocusListener(this);
        txtReNewPassword.addFocusListener(this);

        chkChangePassword.addItemListener(this);

        showInfoStaff(staffLogin);
    }

    public static void main(String[] args) {
        new fThongTinCaNhan(NhanVienDAO.getInstance().getStaffByUsername("phamdangdan")).setVisible(true);
        ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnUpdate)) {
            if (validData()) {
                updateInfo();
                chkChangePassword.setSelected(false);
            }
        } else if (o.equals(btnBack)) {
            this.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Object o = e.getSource();
        int key = e.getKeyCode();
        InputEventHandler handler = new InputEventHandler();
        if (o.equals(txtPassword) || o.equals(txtFullName) || o.equals(txtNewPassword) || o.equals(txtReNewPassword)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (validData())
                    updateInfo();
            }
        } else if (o.equals(txtPhoneNumber)) {
            handler.enterOnlyNumbers(key, txtPhoneNumber, 10);
        } else if (o.equals(txtCMND)) {
            handler.enterOnlyNumbers(key, txtCMND, 12);
        } else if (o.equals(txtFullName)) {
            handler.characterInputLimit(key, txtFullName, 100);
        } else if (o.equals(txtNewPassword)) {
            handler.characterInputLimit(key, txtNewPassword, 100);
        } else if (o.equals(txtReNewPassword)) {
            handler.characterInputLimit(key, txtReNewPassword, 100);
        } else if (o.equals(txtPassword)) {
            handler.characterInputLimit(key, txtPassword, 100);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

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
        if (o.equals(btnUpdate)) {
            CustomUI.getInstance().setCustomBtnHover(btnUpdate);
        } else if (o.equals(btnBack)) {
            CustomUI.getInstance().setCustomBtnHover(btnBack);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(btnUpdate)) {
            CustomUI.getInstance().setCustomBtn(btnUpdate);
        } else if (o.equals(btnBack)) {
            CustomUI.getInstance().setCustomBtn(btnBack);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        Object o = e.getSource();
        if (o.equals(txtFullName)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtFullName);
        } else if (o.equals(txtCMND)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtCMND);
        } else if (o.equals(txtPhoneNumber)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtPhoneNumber);
        } else if (o.equals(txtPassword)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtPassword);
        } else if (o.equals(txtNewPassword)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtNewPassword);
        } else if (o.equals(txtReNewPassword)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtReNewPassword);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object o = e.getSource();
        if (o.equals(txtFullName)) {
            txtFullName.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtCMND)) {
            txtCMND.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtPhoneNumber)) {
            txtPhoneNumber.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtPassword)) {
            txtPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtNewPassword)) {
            txtNewPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtReNewPassword)) {
            txtReNewPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(chkChangePassword)) {
            if (chkChangePassword.isSelected()) {
                CustomUI.getInstance().setCustomTextFieldOn(txtNewPassword);
                CustomUI.getInstance().setCustomTextFieldOn(txtReNewPassword);
            } else {
                CustomUI.getInstance().setCustomTextFieldOff(txtNewPassword);
                CustomUI.getInstance().setCustomTextFieldOff(txtReNewPassword);
            }
        }
    }

    /**
     * Hiển thị thông tin nhân viên được truyền vào lên màn hình
     * 
     * @param staff {@code NhanVien}: Nhân viên cần hiển thị thông tin
     */
    private void showInfoStaff(NhanVien staff) {
        this.staffLogin = staff;
        txtEmpID.setText(staff.getMaNhanVien());
        txtFullName.setText(staff.getHoTen());
        txtCMND.setText(staff.getCmnd());
        dpBirthday.setValue(staff.getNgaySinh());
        txtPhoneNumber.setText(staff.getSoDienThoai());
        txtPosition.setText(staff.getChucVu());
        boolean gender = staff.getGioiTinh();
        radMale.setSelected(true);
        if (gender)
            radFemale.setSelected(true);
        txtSalary.setText(df.format(staff.getMucLuong()));
        txtUsername.setText(staff.getTaiKhoan().getTenDangNhap());
        txtPassword.setText("");
        txtNewPassword.setText("");
        txtReNewPassword.setText("");
    }

    /**
     * Chuyển đổi thông tin trong form thành đối tượng {@code NhanVien}
     * 
     * @return {@code NhanVien}: Nhân viên sau khi đã chuyển đổi
     */
    private NhanVien getDataInForm() {
        String staffId = txtEmpID.getText().trim();
        String staffName = txtFullName.getText().trim();
        String cmnd = txtCMND.getText().trim();
        Date birthday = dpBirthday.getValueSqlDate();
        String phoneNumber = txtPhoneNumber.getText().trim();
        String position = txtPosition.getText().trim();
        boolean gender = false;
        if (radFemale.isSelected())
            gender = true;
        Double salary = Double.parseDouble(txtSalary.getText().trim().replace(",", ""));
        return new NhanVien(staffId, cmnd, staffName, birthday, phoneNumber, position, salary, gender,
                staffLogin.getTrangThaiNV(), staffLogin.getTaiKhoan());
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
        JOptionPane.showMessageDialog(this, message, title, option);
    }

    /**
     * Kiểm tra mât khẩu
     * 
     * @param password {@code String}: mật khẩu
     * @param mess     {@code String}: tên loại mật khẩu (mật khẩu, mật khẩu mới,
     *                 nhập lại mật khẩu, ...)
     */
    private void checkPassword(String password, String mess) {
        String message = mess;
        if (password.length() < 6) {
            message = mess + " phải tối thiểu 6 ký tự";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
        } else {
            message = mess + " chỉ có thể chứa các kỳ tự, số, @, #, _, !, $";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
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
        boolean valid = ValidationData.getInstance().ValidName(this, txtFullName, "Họ và tên", 100, 0);
        if (!valid) {
            return false;
        }

        valid = ValidationData.getInstance().ValidCmnd(this, txtCMND);
        if (!valid) {
            return false;
        }

        valid = ValidationData.getInstance().ValidBirthDay(this, dpBirthday, "", 0);
        if (!valid) {
            return false;
        }

        valid = ValidationData.getInstance().ValidPhoneNumber(this, txtPhoneNumber);
        if (!valid) {
            return false;
        }

        NhanVien newStaffLogin = getDataInForm();
        String password = txtPassword.getText().trim();
        if (password.equalsIgnoreCase("") || password.length() <= 0) {
            message = "Vui lòng nhập mật khẩu để xác nhận bạn là chủ tài khoản";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;

        } else if (password.equals("123456")) {
            message = "Mật khẩu quá đơn giản. Vui lòng đổi mật khẩu khác";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;

        } else if (!(password.length() >= 6 && password.matches("^[a-zA-Z0-9@!#$_]{6,}$"))) {
            checkPassword(password, "Mật khẩu");
            return false;

        } else if (!password.equalsIgnoreCase(newStaffLogin.getTaiKhoan().getMatKhau())) {
            message = "Mật khẩu sai. Vui lòng nhập lại";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (chkChangePassword.isSelected()) {
            String newPassword = txtNewPassword.getText().trim();
            String rePassword = txtReNewPassword.getText().trim();

            if (!(rePassword.equals(newPassword))) {
                message = "Mật khẩu mới không khớp";
                showMessage(txtReNewPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;

            } else if (newPassword.equals("")) {
                message = "Mật khẩu mới không được rỗng";
                showMessage(txtNewPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;

            } else if (rePassword.equals("")) {
                message = "Mật khẩu nhập lại không được rỗng";
                showMessage(txtReNewPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;

            } else if (!(rePassword.length() >= 6 && rePassword.matches("^[a-zA-Z0-9_@#]{6,}$"))) {
                checkPassword(rePassword, "Mật khẩu nhập lại");
                return false;

            } else if (!(newPassword.length() >= 6 && newPassword.matches("^[a-zA-Z0-9_@#]{6,}$"))) {
                checkPassword(newPassword, "Mật khẩu mới");
                return false;

            }
        }
        return true;
    }

    /**
     * Cập nhật thông tin nhân viên
     */
    private void updateInfo() {
        String message = "";
        NhanVien newStaffLogin = getDataInForm();
        String newPassword = txtNewPassword.getText().trim();

        boolean result = false;
        if (chkChangePassword.isSelected()) {
            newStaffLogin.getTaiKhoan().setMatKhau(newPassword);
            result = NhanVienDAO.getInstance().updateInfoStaffAndAccount(newStaffLogin);
        } else {
            result = NhanVienDAO.getInstance().updateInfoStaff(newStaffLogin);
        }
        if (result) {
            message = "Cập nhật thông tin thành công";
            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            showInfoStaff(newStaffLogin);
            staffLogin = newStaffLogin;

        } else {
            message = "Cập nhật thông tin thất bại";
            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}