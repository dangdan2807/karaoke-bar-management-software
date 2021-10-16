package UI;

import javax.swing.*;

import DAO.NhanVienDAO;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.kDatePicker;
import entity.NhanVien;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class fThongTinCaNhan extends JDialog
        implements ActionListener, KeyListener, MouseListener, FocusListener, ItemListener {
    private JLabel lbUsername, lbFullName, lbPassword, lbNewPassword, lbReNewPassword, lbGender;
    private JLabel lbSalary;
    private JTextField txtUsername, txtFullName, txtPassword, txtNewPassword, txtReNewPassword;
    private JTextField txtCMND, txtPhoneNumber, txtPosition, txtSalary, txtEmpID;
    private JButton btnUpdate, btnClose;
    private JComboBox<String> cboGender;
    private kDatePicker dpNgaySinh;

    private int withPn = 400, heightPn = 315;
    private NhanVien nhanVienLogin = null;
    private JCheckBox chkDoiMatKhau;

    public fThongTinCaNhan(NhanVien nhanVien) {
        setTitle("Quản Lý Thông Tin Tài Khoản");
        setSize(824, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.nhanVienLogin = nhanVien;
        createFormAccountProfile();
    }

    public void createFormAccountProfile() {
        JPanel pnMain = new JPanel();
        pnMain.setBounds(0, 0, withPn, heightPn);
        pnMain.setLayout(null);
        pnMain.setBackground(Color.WHITE);

        btnUpdate = new JButton("Cập nhật");
        CustomUI.getInstance().setCustomBtn(btnUpdate);
        btnClose = new JButton("Thoát");
        CustomUI.getInstance().setCustomBtn(btnClose);
        pnMain.add(btnUpdate);
        pnMain.add(btnClose);

        btnUpdate.setBounds(540, 281, 120, 25);
        btnClose.setBounds(670, 281, 120, 25);

        getContentPane().add(pnMain);

        JPanel pnPersonalInfo = new JPanel();
        pnPersonalInfo.setBorder(
                new TitledBorder(null, "Thông tin cá nhân ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnPersonalInfo.setBackground(Color.WHITE);
        pnPersonalInfo.setBounds(10, 0, 390, 306);
        pnMain.add(pnPersonalInfo);
        pnPersonalInfo.setLayout(null);

        JLabel lbMaNV = new JLabel("Mã nhân viên: ");
        lbMaNV.setBounds(10, 16, 120, 25);
        pnPersonalInfo.add(lbMaNV);

        txtEmpID = new JTextField();
        txtEmpID.setEditable(false);
        txtEmpID.setText((String) null);
        txtEmpID.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtEmpID.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        txtEmpID.setBounds(130, 16, 250, 25);
        pnPersonalInfo.add(txtEmpID);

        lbFullName = new JLabel("Tên nhân viên: ");
        lbFullName.setBounds(10, 53, 120, 25);
        pnPersonalInfo.add(lbFullName);

        txtFullName = new JTextField();
        txtFullName.setBounds(130, 52, 250, 25);
        pnPersonalInfo.add(txtFullName);
        txtFullName.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtFullName.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        JLabel lbCMND = new JLabel("CMND/CCCD: ");
        lbCMND.setBounds(10, 88, 120, 25);
        pnPersonalInfo.add(lbCMND);

        txtCMND = new JTextField();
        txtCMND.setBounds(130, 88, 250, 25);
        pnPersonalInfo.add(txtCMND);
        txtCMND.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtCMND.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        dpNgaySinh = new kDatePicker(250, 25);
        dpNgaySinh.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        dpNgaySinh.setBackgroundColor(Color.WHITE);
        dpNgaySinh.setFontCustom(new Font("Dialog", Font.PLAIN, 14));
        dpNgaySinh.setBounds(130, 124, 250, 25);
        pnPersonalInfo.add(dpNgaySinh);

        JLabel lbBirthDay = new JLabel("Ngày sinh: ");
        lbBirthDay.setBounds(10, 124, 120, 25);
        pnPersonalInfo.add(lbBirthDay);

        JLabel lbPhoneNumber = new JLabel("Số điện thoại: ");
        lbPhoneNumber.setBounds(10, 160, 120, 25);
        pnPersonalInfo.add(lbPhoneNumber);

        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setBounds(130, 160, 250, 25);
        pnPersonalInfo.add(txtPhoneNumber);
        txtPhoneNumber.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtPhoneNumber.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        txtPosition = new JTextField();
        txtPosition.setBounds(130, 196, 250, 25);
        pnPersonalInfo.add(txtPosition);
        txtPosition.setEditable(false);
        txtPosition.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtPosition.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        JLabel lbPosition = new JLabel("Chức vụ: ");
        lbPosition.setBounds(10, 196, 120, 25);
        pnPersonalInfo.add(lbPosition);

        lbGender = new JLabel("Giới tính: ");
        lbGender.setBounds(10, 232, 120, 25);
        pnPersonalInfo.add(lbGender);

        cboGender = new JComboBox<String>();
        cboGender.setUI(new BasicComboBoxUI());
        cboGender.addItem("Nam");
        cboGender.addItem("Nữ");
        cboGender.setBounds(130, 232, 250, 25);
        pnPersonalInfo.add(cboGender);
        cboGender.setFont(new Font("Dialog", Font.PLAIN, 14));
        cboGender.setBackground(Color.WHITE);
        cboGender.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#1a66e3")));

        txtSalary = new JTextField();
        txtSalary.setBounds(130, 268, 250, 25);
        pnPersonalInfo.add(txtSalary);
        txtSalary.setEditable(false);
        txtSalary.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtSalary.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        lbSalary = new JLabel("Mức Lương: ");
        lbSalary.setBounds(10, 268, 120, 25);
        pnPersonalInfo.add(lbSalary);

        JPanel pnAccountInfo = new JPanel();
        pnAccountInfo.setLayout(null);
        pnAccountInfo.setBorder(
                new TitledBorder(null, "Thông tin tài khoản ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnAccountInfo.setBackground(Color.WHITE);
        pnAccountInfo.setBounds(410, 0, 390, 90);
        pnMain.add(pnAccountInfo);

        lbUsername = new JLabel("Tên đăng nhập: ");
        lbUsername.setBounds(10, 16, 120, 25);
        pnAccountInfo.add(lbUsername);
        txtUsername = new JTextField();
        txtUsername.setBounds(130, 16, 250, 25);

        txtUsername.setEditable(false);
        txtUsername.setBackground(Color.decode("#f9f9f9"));
        pnAccountInfo.add(txtUsername);
        txtUsername.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtUsername.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        lbPassword = new JLabel("Mật khẩu: ");
        lbPassword.setBounds(10, 52, 120, 25);
        pnAccountInfo.add(lbPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(130, 52, 250, 25);
        pnAccountInfo.add(txtPassword);
        txtPassword.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        JPanel pnPasswordInfo = new JPanel();
        pnPasswordInfo.setLayout(null);
        pnPasswordInfo
                .setBorder(new TitledBorder(null, "Đổi mật khẩu ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnPasswordInfo.setBackground(Color.WHITE);
        pnPasswordInfo.setBounds(410, 100, 390, 175);
        pnMain.add(pnPasswordInfo);

        lbNewPassword = new JLabel("Mật khẩu mới: ");
        lbNewPassword.setBounds(10, 46, 120, 25);
        pnPasswordInfo.add(lbNewPassword);

        txtNewPassword = new JPasswordField();
        txtNewPassword.setBounds(130, 46, 250, 25);
        txtNewPassword.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtNewPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        txtNewPassword.setEditable(false);
        pnPasswordInfo.add(txtNewPassword);

        chkDoiMatKhau = new JCheckBox("Đổi mật khẩu");
        chkDoiMatKhau.setBounds(10, 16, 370, 23);
        chkDoiMatKhau.setFont(new Font("Dialog", Font.BOLD, 12));
        chkDoiMatKhau.setBackground(Color.WHITE);
        pnPasswordInfo.add(chkDoiMatKhau);

        lbReNewPassword = new JLabel("Nhập lại: ");
        lbReNewPassword.setBounds(10, 82, 120, 25);
        pnPasswordInfo.add(lbReNewPassword);

        txtReNewPassword = new JPasswordField();
        txtReNewPassword.setBounds(130, 82, 250, 25);
        txtReNewPassword.setEditable(false);
        txtReNewPassword.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtReNewPassword.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnPasswordInfo.add(txtReNewPassword);

        txtFullName.addKeyListener(this);
        txtPassword.addKeyListener(this);
        txtNewPassword.addKeyListener(this);
        txtReNewPassword.addKeyListener(this);

        btnUpdate.addActionListener(this);
        btnClose.addActionListener(this);

        btnUpdate.addMouseListener(this);
        btnClose.addMouseListener(this);

        txtFullName.addFocusListener(this);
        txtPassword.addFocusListener(this);
        txtCMND.addFocusListener(this);
        txtPhoneNumber.addFocusListener(this);
        txtNewPassword.addFocusListener(this);
        txtReNewPassword.addFocusListener(this);

        chkDoiMatKhau.addItemListener(this);

        changeAccount(nhanVienLogin);
    }

    public static void main(String[] args) {
        new fThongTinCaNhan(NhanVienDAO.getInstance().getNhanVienByTenDangNhap("phamdangdan")).setVisible(true);
        ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnUpdate)) {
            if (validData())
                capNhatThongTin();
        } else if (o.equals(btnClose)) {
            this.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Object o = e.getSource();
        // bắt sự kiện nhấn phím enter tự nhấn btnLogin
        if (o.equals(txtPassword) || o.equals(txtFullName) || o.equals(txtNewPassword) || o.equals(txtReNewPassword)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                btnUpdate.doClick();
            }
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
        } else if (o.equals(btnClose)) {
            CustomUI.getInstance().setCustomBtnHover(btnClose);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(btnUpdate)) {
            CustomUI.getInstance().setCustomBtn(btnUpdate);
        } else if (o.equals(btnClose)) {
            CustomUI.getInstance().setCustomBtn(btnClose);
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
        if (o.equals(chkDoiMatKhau)) {
            txtNewPassword.setEditable(chkDoiMatKhau.isSelected());
            txtReNewPassword.setEditable(chkDoiMatKhau.isSelected());
        }
    }

    private void changeAccount(NhanVien nhanVien) {
        this.nhanVienLogin = nhanVien;
        txtEmpID.setText(nhanVien.getMaNhanVien());
        txtFullName.setText(nhanVien.getHoTen());
        txtCMND.setText(nhanVien.getCmnd());
        dpNgaySinh.setValue(nhanVien.getNgaySinh());
        txtPhoneNumber.setText(nhanVien.getSoDienThoai());
        txtPosition.setText(nhanVien.getChucVu());
        boolean gioiTinh = nhanVien.getGioiTinh();
        cboGender.setSelectedIndex(0);
        if (gioiTinh) {
            cboGender.setSelectedIndex(1);
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        txtSalary.setText(df.format(nhanVien.getMucLuong()));
        txtUsername.setText(nhanVien.getTaiKhoan().getTenDangNhap());
        txtPassword.setText("");
        txtNewPassword.setText("");
        txtReNewPassword.setText("");
    }

    private NhanVien getDataInForm() {
        String maNhanVien = txtEmpID.getText().trim();
        String hoTen = txtFullName.getText().trim();
        String cmnd = txtCMND.getText().trim();
        Date ngaySinh = null;
        try {
            ngaySinh = dpNgaySinh.getFullDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String soDienThoai = txtPhoneNumber.getText();
        String chucVu = txtPosition.getText();
        boolean gioiTinh = (boolean) cboGender.getSelectedItem();
        Double mucLuong = Double.parseDouble(txtSalary.getText().trim().replace(",", ""));

        return new NhanVien(maNhanVien, cmnd, hoTen, ngaySinh, soDienThoai, chucVu, mucLuong, gioiTinh,
                nhanVienLogin.getTrangThaiNV(), nhanVienLogin.getTaiKhoan());
    }

    private void showMessage(JTextField txt, int type, String message, String title, int option) {
        if (type == 1) {
            txt.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
        }
        JOptionPane.showMessageDialog(this, message, title, option);
    }

    private void checkPassword(String matKhau, String mes) {
        String message = mes;
        if (matKhau.length() < 6) {
            message = mes + " phải tối thiểu 6 ký tự";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.OK_OPTION);
        } else {
            message = mes + " chỉ có thể chứa các kỳ tự, số, @, #, _";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.OK_OPTION);
        }
    }

    private void showMessage(String message, String title, int option) {
        JOptionPane.showMessageDialog(this, message, title, option);
    }

    private boolean validData() {
        String message = "";
        String hoTen = txtFullName.getText().trim();
        if (hoTen.equalsIgnoreCase("")) {
            message = "Họ tên không được rỗng";
            showMessage(txtFullName, 1, message, "Thông báo", JOptionPane.OK_OPTION);
            return false;
        }

        String cmnd = txtCMND.getText().trim();
        if (!cmnd.matches("^[\\d]{9}$|^[\\d]{12}$")) {
            message = "CMND phải là số và gồm 9 số hoặc nếu là CCCD phải là số và gồm 12 số";
            showMessage(txtCMND, 1, message, "Thông báo", JOptionPane.OK_OPTION);
            return false;
        }

        Date ngaySinh = null;
        try {
            ngaySinh = dpNgaySinh.getFullDate();
            if (ngaySinh.after(dpNgaySinh.getValueToDay())) {
                message = "Ngày sinh phải bé hơn ngày hiện tại";
                showMessage(message, "Thông báo", JOptionPane.OK_OPTION);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String soDienThoai = txtPhoneNumber.getText();
        if (!soDienThoai.matches("^0[35789][\\d]{8}$")) {
            message = "Số điện thoại phải là số và gồm 10 số bắt đầu bằng 03, 05, 07, 08, 09";
            showMessage(txtCMND, 1, message, "Thông báo", JOptionPane.OK_OPTION);
            return false;
        }

        NhanVien nhanVienLoginMoi = getDataInForm();
        String matKhau = txtPassword.getText().trim();
        if (matKhau.equalsIgnoreCase("")) {
            message = "Vui lòng nhập mật khẩu để xác nhận bạn là chủ tài khoản";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;

        } else if (matKhau.equals("123456")) {
            message = "Mật khẩu quá đơn giản. Vui lòng đổi mật khẩu khác";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.OK_OPTION);
            return false;

        } else if (matKhau.length() >= 6 && matKhau.matches("^[a-zA-Z0-9_@#]{6,}$")) {
            checkPassword(matKhau, "Mật khẩu");
            return false;

        } else if (!matKhau.equals(nhanVienLoginMoi.getTaiKhoan().getMatKhau())) {
            message = "Mật khẩu sai. Vui lòng nhập lại";
            showMessage(txtPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (chkDoiMatKhau.isSelected()) {
            String matKhauMoi = txtNewPassword.getText().trim();
            String nlMatKhau = txtReNewPassword.getText().trim();

            if (!(nlMatKhau.equals(matKhauMoi))) {
                message = "Mật khẩu mới không khớp";
                showMessage(txtReNewPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;

            } else if (matKhauMoi.equals("")) {
                message = "Mật khẩu mới không được rỗng";
                showMessage(txtNewPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;

            } else if (nlMatKhau.equals("")) {
                message = "Mật khẩu nhập lại không được rỗng";
                showMessage(txtReNewPassword, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;

            } else if (nlMatKhau.length() >= 6 && nlMatKhau.matches("^[a-zA-Z0-9_@#]{6,}$")) {
                checkPassword(nlMatKhau, "Mật khẩu nhập lại");
                return false;

            } else if (matKhauMoi.length() >= 6 && matKhauMoi.matches("^[a-zA-Z0-9_@#]{6,}$")) {
                checkPassword(matKhauMoi, "Mật khẩu mới");
                return false;

            }
        }
        return true;
    }

    private void capNhatThongTin() {
        String message = "";
        NhanVien nhanVienLoginMoi = getDataInForm();
        String matKhauMoi = txtNewPassword.getText().trim();

        if (chkDoiMatKhau.isSelected()) {
            nhanVienLoginMoi.getTaiKhoan().setMatKhau(matKhauMoi);
        }

        boolean ketQua = NhanVienDAO.getInstance().updateTTNhanVien(nhanVienLoginMoi);
        if (ketQua) {
            message = "Cập nhật thông tin thành công";
            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            changeAccount(nhanVienLoginMoi);
            nhanVienLogin = nhanVienLoginMoi;
        } else {
            message = "Cập nhật thông tin thất bại";
            JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}