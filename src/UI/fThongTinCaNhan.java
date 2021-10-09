package UI;

import javax.swing.*;

import DAO.NhanVienDAO;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.kDatePicker;
import entity.NhanVien;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class fThongTinCaNhan extends JDialog
        implements ActionListener, KeyListener, MouseListener, FocusListener, ItemListener {
    private JLabel lbTenDangNhap, lbHoTen, lbMatKhau, lbMatKhauMoi, lbNLMatKhauMoi, lbGioiTinh;
    private JLabel lbMucLuong;
    private JTextField txtTenDangNhap, txtHoTen, txtMatKhau, txtMatKhauMoi, txtNLMatKhauMoi;
    private JTextField txtCMND, txtSDT, txtChucVu, txtMucLuong;
    private JButton btnUpdate, btnClose;
    private JComboBox<String> cboGioiTinh;
    private kDatePicker dpNgaySinh;

    private int withPn = 400, heightPn = 315;
    private NhanVien nhanVienLogin = null;
    private JCheckBox chkDoiMatKhau;

    public fThongTinCaNhan(NhanVien nhanVien) {
        setTitle("Quản Lý Thông Tin Tài Khoản");
        setSize(824, 348);
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

        btnUpdate.setBounds(539, 275, 120, 25);
        btnClose.setBounds(670, 275, 120, 25);

        getContentPane().add(pnMain);

        JPanel pnTTCN = new JPanel();
        pnTTCN.setBorder(
                new TitledBorder(null, "Thông tin cá nhân ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTTCN.setBackground(Color.WHITE);
        pnTTCN.setBounds(10, 0, 390, 269);
        pnMain.add(pnTTCN);
        pnTTCN.setLayout(null);

        lbHoTen = new JLabel("Tên nhân viên: ");
        lbHoTen.setBounds(10, 16, 120, 25);
        pnTTCN.add(lbHoTen);

        txtHoTen = new JTextField();
        txtHoTen.setBounds(130, 16, 250, 25);
        pnTTCN.add(txtHoTen);
        txtHoTen.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtHoTen.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        JLabel lbCMND = new JLabel("CMND/CCCD: ");
        lbCMND.setBounds(10, 52, 120, 25);
        pnTTCN.add(lbCMND);

        txtCMND = new JTextField();
        txtCMND.setBounds(130, 52, 250, 25);
        pnTTCN.add(txtCMND);
        txtCMND.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtCMND.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        dpNgaySinh = new kDatePicker(250, 25);
        dpNgaySinh.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        dpNgaySinh.setBackgroundColor(Color.WHITE);
        dpNgaySinh.setFontCustom(new Font("Dialog", Font.PLAIN, 14));
        dpNgaySinh.setBounds(130, 88, 250, 25);
        pnTTCN.add(dpNgaySinh);

        JLabel lbNgaySinh = new JLabel("Ngày sinh: ");
        lbNgaySinh.setBounds(10, 88, 120, 25);
        pnTTCN.add(lbNgaySinh);

        JLabel lbSDT = new JLabel("Số điện thoại: ");
        lbSDT.setBounds(10, 124, 120, 25);
        pnTTCN.add(lbSDT);

        txtSDT = new JTextField();
        txtSDT.setBounds(130, 124, 250, 25);
        pnTTCN.add(txtSDT);
        txtSDT.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtSDT.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        txtChucVu = new JTextField();
        txtChucVu.setBounds(130, 160, 250, 25);
        pnTTCN.add(txtChucVu);
        txtChucVu.setEditable(false);
        txtChucVu.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtChucVu.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        JLabel lbChucVu = new JLabel("Chức vụ: ");
        lbChucVu.setBounds(10, 160, 120, 25);
        pnTTCN.add(lbChucVu);

        lbGioiTinh = new JLabel("Giới tính: ");
        lbGioiTinh.setBounds(10, 196, 120, 25);
        pnTTCN.add(lbGioiTinh);

        cboGioiTinh = new JComboBox<String>();
        cboGioiTinh.setUI(new BasicComboBoxUI());
        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");
        cboGioiTinh.setBounds(130, 196, 250, 25);
        pnTTCN.add(cboGioiTinh);
        cboGioiTinh.setFont(new Font("Dialog", Font.PLAIN, 14));
        cboGioiTinh.setBackground(Color.WHITE);
        cboGioiTinh.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#1a66e3")));

        txtMucLuong = new JTextField();
        txtMucLuong.setBounds(130, 232, 250, 25);
        pnTTCN.add(txtMucLuong);
        txtMucLuong.setEditable(false);
        txtMucLuong.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtMucLuong.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        lbMucLuong = new JLabel("Mức Lương: ");
        lbMucLuong.setBounds(10, 232, 120, 25);
        pnTTCN.add(lbMucLuong);

        JPanel pnTTTaiKhoan = new JPanel();
        pnTTTaiKhoan.setLayout(null);
        pnTTTaiKhoan.setBorder(
                new TitledBorder(null, "Thông tin tài khoản ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTTTaiKhoan.setBackground(Color.WHITE);
        pnTTTaiKhoan.setBounds(410, 0, 390, 90);
        pnMain.add(pnTTTaiKhoan);

        lbTenDangNhap = new JLabel("Tên đăng nhập: ");
        lbTenDangNhap.setBounds(10, 16, 120, 25);
        pnTTTaiKhoan.add(lbTenDangNhap);
        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setBounds(130, 16, 250, 25);

        txtTenDangNhap.setEditable(false);
        txtTenDangNhap.setBackground(Color.decode("#f9f9f9"));
        pnTTTaiKhoan.add(txtTenDangNhap);
        txtTenDangNhap.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtTenDangNhap.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        lbMatKhau = new JLabel("Mật khẩu: ");
        lbMatKhau.setBounds(10, 52, 120, 25);
        pnTTTaiKhoan.add(lbMatKhau);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(130, 52, 250, 25);
        pnTTTaiKhoan.add(txtMatKhau);
        txtMatKhau.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtMatKhau.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);

        JPanel pnTTMatKhau = new JPanel();
        pnTTMatKhau.setLayout(null);
        pnTTMatKhau
                .setBorder(new TitledBorder(null, "Đổi mật khẩu ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTTMatKhau.setBackground(Color.WHITE);
        pnTTMatKhau.setBounds(410, 100, 390, 169);
        pnMain.add(pnTTMatKhau);

        lbMatKhauMoi = new JLabel("Mật khẩu mới: ");
        lbMatKhauMoi.setBounds(10, 46, 120, 25);
        pnTTMatKhau.add(lbMatKhauMoi);

        txtMatKhauMoi = new JPasswordField();
        txtMatKhauMoi.setBounds(130, 46, 250, 25);
        txtMatKhauMoi.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtMatKhauMoi.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        txtMatKhauMoi.setEditable(false);
        pnTTMatKhau.add(txtMatKhauMoi);

        chkDoiMatKhau = new JCheckBox("Đổi mật khẩu");
        chkDoiMatKhau.setBounds(10, 16, 370, 23);
        chkDoiMatKhau.setFont(new Font("Dialog", Font.BOLD, 12));
        chkDoiMatKhau.setBackground(Color.WHITE);
        pnTTMatKhau.add(chkDoiMatKhau);

        lbNLMatKhauMoi = new JLabel("Nhập lại: ");
        lbNLMatKhauMoi.setBounds(10, 82, 120, 25);
        pnTTMatKhau.add(lbNLMatKhauMoi);

        txtNLMatKhauMoi = new JPasswordField();
        txtNLMatKhauMoi.setBounds(130, 82, 250, 25);
        txtNLMatKhauMoi.setEditable(false);
        txtNLMatKhauMoi.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtNLMatKhauMoi.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnTTMatKhau.add(txtNLMatKhauMoi);

        txtHoTen.addKeyListener(this);
        txtMatKhau.addKeyListener(this);
        txtMatKhauMoi.addKeyListener(this);
        txtNLMatKhauMoi.addKeyListener(this);
        
        btnUpdate.addActionListener(this);
        btnClose.addActionListener(this);

        btnUpdate.addMouseListener(this);
        btnClose.addMouseListener(this);

        txtHoTen.addFocusListener(this);
        txtMatKhau.addFocusListener(this);
        txtCMND.addFocusListener(this);
        txtSDT.addFocusListener(this);
        txtMatKhauMoi.addFocusListener(this);
        txtNLMatKhauMoi.addFocusListener(this);

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
        if (o.equals(txtMatKhau) || o.equals(txtHoTen) || o.equals(txtMatKhauMoi) || o.equals(txtNLMatKhauMoi)) {
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
        if (o.equals(txtHoTen)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtHoTen);
        } else if (o.equals(txtCMND)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtCMND);
        } else if (o.equals(txtSDT)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtSDT);
        } else if (o.equals(txtMatKhau)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtMatKhau);
        } else if (o.equals(txtMatKhauMoi)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtMatKhauMoi);
        } else if (o.equals(txtNLMatKhauMoi)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtNLMatKhauMoi);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object o = e.getSource();
        if (o.equals(txtHoTen)) {
            txtHoTen.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtCMND)) {
            txtCMND.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtSDT)) {
            txtSDT.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtMatKhau)) {
            txtMatKhau.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtMatKhauMoi)) {
            txtMatKhauMoi.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        } else if (o.equals(txtNLMatKhauMoi)) {
            txtNLMatKhauMoi.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(chkDoiMatKhau)) {
            txtMatKhauMoi.setEditable(chkDoiMatKhau.isSelected());
            txtNLMatKhauMoi.setEditable(chkDoiMatKhau.isSelected());
        }
    }

    private void changeAccount(NhanVien nhanVien) {
        this.nhanVienLogin = nhanVien;
        txtHoTen.setText(nhanVien.getHoTen());
        txtCMND.setText(nhanVien.getCmnd());
        dpNgaySinh.setValue(nhanVien.getNgaySinh());
        txtSDT.setText(nhanVien.getSoDienThoai());
        txtChucVu.setText(nhanVien.getChucVu());
        boolean gioiTinh = nhanVien.getGioiTinh();
        cboGioiTinh.setSelectedIndex(0);
        if (gioiTinh == false) {
            cboGioiTinh.setSelectedIndex(1);
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        txtMucLuong.setText(df.format(nhanVien.getMucLuong()));
        txtTenDangNhap.setText(nhanVien.getTaiKhoan().getTenDangNhap());
        txtMatKhau.setText("");
        txtMatKhauMoi.setText("");
        txtNLMatKhauMoi.setText("");
    }

    private void capNhatThongTin() {
        String username = txtTenDangNhap.getText().trim();
        String displayName = txtHoTen.getText().trim();
        String password = txtMatKhau.getText().trim();
        String reNewPassWord = txtNLMatKhauMoi.getText().trim();
        String newPassword = txtMatKhauMoi.getText().trim();
        if (!(newPassword.equals(reNewPassWord))) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp", "Thông báo", JOptionPane.ERROR_MESSAGE);
        } else {
            // if (NhanVienDAO.getInstance().updateAccountInfo(username, displayName,
            // password, newPassword)) {
            // JOptionPane.showMessageDialog(this, "Cập nhật thành công", "Thông báo",
            // JOptionPane.INFORMATION_MESSAGE);
            // fManagerSale.setEmpName(displayName);
            // } else if (password.equals("")) {
            // JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu để cập nhật",
            // "Thông báo",
            // JOptionPane.ERROR_MESSAGE);
            // } else {
            // JOptionPane.showMessageDialog(this, "Mật khẩu sai !!!", "Thông báo",
            // JOptionPane.ERROR_MESSAGE);
            // }
        }
    }

}