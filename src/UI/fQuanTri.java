package UI;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

import DAO.NhanVienDAO;
import entity.NhanVien;

public class fQuanTri extends JFrame implements ActionListener, ChangeListener {
    private static fQuanTri instance;

    public static fQuanTri getInstance(NhanVien staffLogin) {
        if (instance == null)
            instance = new fQuanTri(staffLogin);
        return instance;
    }

    private JTabbedPane tpTabMain;
    private ImageIcon userIcon = new ImageIcon("img/user_16.png");
    private NhanVien staffLogin = null;

    public fQuanTri(NhanVien staff) {
        setTitle("Quản Lý Hệ Thống");
        setSize(1280, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.staffLogin = staff;
        createTabControl();
        setCloseAction(this);
    }

    public void createTabControl() {
        tpTabMain = new JTabbedPane();
        
        tpTabMain.addTab("Doanh thu", null, null, "Quản lý doanh thu");
        tpTabMain.addTab("Sản phẩm", null, null, "Quản lý sản phẩm");
        tpTabMain.addTab("Loại sản phẩm", null, null, "Quản lý loại sản phẩm");
        tpTabMain.addTab("Bàn", null, null, "Quản lý bàn");
        tpTabMain.addTab("Tài Khoản", userIcon, null, "Quản lý tài khoản");
        this.add(tpTabMain);

        tpTabMain.addChangeListener(this);
    }

    public static void main(String[] args) {
        NhanVien staffLogin = NhanVienDAO.getInstance().getNhanVienByTenDangNhap("phamdangdan");
        new fQuanTri(staffLogin).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void stateChanged(ChangeEvent e) {
    }

    public void EventLogOut() {
        fDangNhap f = new fDangNhap();
        setVisible(false);
        f.setVisible(true);
    }

    public void EventExit() {
        fDieuHuong f = new fDieuHuong(staffLogin);
        setVisible(false);
        f.setVisible(true);
    }

    // mô tả: Bắt sự kiện khi click btn close(x), sẽ show 1 form xác nhận đăng xuất
    // hay thoát chương trình
    public void setCloseAction(JFrame jframe) {
        jframe.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                fDieuHuong f = new fDieuHuong(staffLogin);
                jframe.setVisible(false);
                f.setVisible(true);
            }
        });
    }
}
