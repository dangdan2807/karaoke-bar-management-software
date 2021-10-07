package UI;

import javax.swing.*;

import DAO.NhanVienDAO;
import UI.PanelCustom.customUI;
import UI.PanelCustom.kDatePicker;
import entity.NhanVien;
import entity.TaiKhoan;

import java.awt.Color;
// import javax.swing.event.*;
// import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;

public class fThongTinCaNhan extends JFrame implements ActionListener, KeyListener, MouseListener {
    private JLabel lbTenDangNhap, lbHoTen, lbMatKhau, lbMatKhauMoi, lbNLMatKhauMoi, lbGioiTinh;
    private JLabel lbMucLuong;
    private JTextField txtTenDangNhap, txtHoTen, txtMatKhau, txtMatKhauMoi, txtNLMatKhauMoi;
    private JTextField txtCMND, txtSDT, txtChucVu, txtMucLuong;
    private JButton btnUpdate, btnClose;
    private JComboBox<String> cboGioiTinh;
    private kDatePicker dpNgaySinh;

    private int withPn = 400, heightPn = 315;
    private NhanVien nhanVienLogin = null;
    private fQuanLyDatPhong fManagerSale = null;

    public fThongTinCaNhan(NhanVien nhanVien) {
        setTitle("Thông tin tài khoản");
        setSize(824, 352);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.nhanVienLogin = nhanVien;
        createFormAccountProfile();
    }

    public void createFormAccountProfile() {
        JPanel pnMain = new JPanel();
        pnMain.setBounds(0, 0, withPn, heightPn);
        pnMain.setLayout(null);
        pnMain.setBackground(Color.WHITE);

        btnUpdate = new JButton("Cập nhật");
        customUI.getInstance().setCustomBtn(btnUpdate);
        btnClose = new JButton("Thoát");
        customUI.getInstance().setCustomBtn(btnClose);
        pnMain.add(btnUpdate);
        pnMain.add(btnClose);

        btnUpdate.setBounds(539, 275, 120, 25);
        btnClose.setBounds(670, 275, 120, 25);

        getContentPane().add(pnMain);

        JPanel pnTTCN = new JPanel();
        pnTTCN.setBorder(
                new TitledBorder(null, "Thông tin cá nhân ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTTCN.setBackground(Color.WHITE);
        pnTTCN.setBounds(10, 0, 390, 271);
        pnMain.add(pnTTCN);
        pnTTCN.setLayout(null);
        lbHoTen = new JLabel("Tên nhân viên: ");
        lbHoTen.setBounds(10, 16, 120, 25);
        pnTTCN.add(lbHoTen);
        txtHoTen = new JTextField();
        txtHoTen.setBounds(130, 16, 250, 25);
        pnTTCN.add(txtHoTen);

        JLabel lbCMND = new JLabel("CMND/CCCD: ");
        lbCMND.setBounds(10, 52, 120, 25);
        pnTTCN.add(lbCMND);

        txtCMND = new JTextField();
        txtCMND.setBounds(130, 52, 250, 25);
        pnTTCN.add(txtCMND);

        dpNgaySinh = new kDatePicker(250, 25);
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

        txtChucVu = new JTextField();
        txtChucVu.setBounds(130, 160, 250, 25);
        pnTTCN.add(txtChucVu);
        txtChucVu.setEditable(false);

        JLabel lbChucVu = new JLabel("Chức vụ: ");
        lbChucVu.setBounds(10, 160, 120, 25);
        pnTTCN.add(lbChucVu);

        lbGioiTinh = new JLabel("Giới tính: ");
        lbGioiTinh.setBounds(10, 196, 120, 25);
        pnTTCN.add(lbGioiTinh);

        cboGioiTinh = new JComboBox<String>();
        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");
        cboGioiTinh.setBounds(130, 196, 250, 25);
        pnTTCN.add(cboGioiTinh);

        txtMucLuong = new JTextField();
        txtMucLuong.setBounds(130, 232, 250, 25);
        pnTTCN.add(txtMucLuong);
        txtMucLuong.setEditable(false);

        lbMucLuong = new JLabel("Mức Lương: ");
        lbMucLuong.setBounds(10, 232, 120, 25);
        pnTTCN.add(lbMucLuong);

        JPanel pnTTTK = new JPanel();
        pnTTTK.setLayout(null);
        pnTTTK.setBorder(
                new TitledBorder(null, "Thông tin tài khoản ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnTTTK.setBackground(Color.WHITE);
        pnTTTK.setBounds(410, 0, 390, 271);
        pnMain.add(pnTTTK);

        lbTenDangNhap = new JLabel("Tên đăng nhập: ");
        lbTenDangNhap.setBounds(10, 16, 120, 25);
        pnTTTK.add(lbTenDangNhap);

        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setBounds(130, 16, 250, 25);
        pnTTTK.add(txtTenDangNhap);
        txtTenDangNhap.setEditable(false);
        txtTenDangNhap.setBackground(Color.decode("#f9f9f9"));
        lbMatKhau = new JLabel("Mật khẩu: ");
        lbMatKhau.setBounds(10, 52, 120, 25);
        pnTTTK.add(lbMatKhau);
        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(130, 52, 250, 25);
        pnTTTK.add(txtMatKhau);
        txtMatKhauMoi = new JPasswordField();
        txtMatKhauMoi.setBounds(130, 88, 250, 25);
        pnTTTK.add(txtMatKhauMoi);
        lbMatKhauMoi = new JLabel("Mật khẩu mới: ");
        lbMatKhauMoi.setBounds(10, 88, 120, 25);
        pnTTTK.add(lbMatKhauMoi);
        lbNLMatKhauMoi = new JLabel("Nhập lại: ");
        lbNLMatKhauMoi.setBounds(10, 124, 120, 25);
        pnTTTK.add(lbNLMatKhauMoi);
        txtNLMatKhauMoi = new JPasswordField();
        txtNLMatKhauMoi.setBounds(130, 124, 250, 25);
        pnTTTK.add(txtNLMatKhauMoi);
        txtNLMatKhauMoi.addKeyListener(this);
        txtMatKhauMoi.addKeyListener(this);
        txtMatKhau.addKeyListener(this);

        txtHoTen.addKeyListener(this);
        changeAccount(nhanVienLogin);

        btnUpdate.addActionListener(this);
        btnClose.addActionListener(this);

        btnUpdate.addMouseListener(this);
        btnClose.addMouseListener(this);
    }

    public static void main(String[] args) {
        new fThongTinCaNhan(NhanVienDAO.getInstance().getNhanVienByTenDangNhap("phamdangdan")).setVisible(true);
        ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnUpdate)) {
            updateAccount();
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
            customUI.getInstance().setCustomBtnHover(btnUpdate);
        } else if (o.equals(btnClose)) {
            customUI.getInstance().setCustomBtnHover(btnClose);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(btnUpdate)) {
            customUI.getInstance().setCustomBtn(btnUpdate);
        } else if (o.equals(btnClose)) {
            customUI.getInstance().setCustomBtn(btnClose);
        }
    }

    private void changeAccount(NhanVien nhanVien) {
        TaiKhoan taiKhoan = nhanVien.getTaiKhoan();
        txtTenDangNhap.setText(taiKhoan.getTenDangNhap());
    }

    private void updateAccount() {
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