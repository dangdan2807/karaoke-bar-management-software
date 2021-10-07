package UI;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

import DAO.NhanVienDAO;
import UI.PanelCustom.*;
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

    private pnRevenue pRevenue;
    private pnProduct pProduct;
    private pnCategory pCategory;
    private pnTable pTable;
    private pnKhachHang pAccount;

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
        pRevenue = new pnRevenue();
        pProduct = new pnProduct(staffLogin);
        pCategory = new pnCategory(staffLogin);
        pTable = new pnTable(staffLogin);
        pAccount = new pnKhachHang(staffLogin);
        tpTabMain.addTab("Doanh thu", null, pRevenue, "Quản lý doanh thu");
        tpTabMain.addTab("Sản phẩm", null, pProduct, "Quản lý sản phẩm");
        tpTabMain.addTab("Loại sản phẩm", null, pCategory, "Quản lý loại sản phẩm");
        tpTabMain.addTab("Bàn", null, pTable, "Quản lý bàn");
        tpTabMain.addTab("Tài Khoản", userIcon, pAccount, "Quản lý tài khoản");
        this.add(tpTabMain);

        tpTabMain.addChangeListener(this);
        pRevenue.getBtnLogOut().addActionListener(this);
        pRevenue.getBtnBack().addActionListener(this);
        pProduct.getBtnLogOut().addActionListener(this);
        pProduct.getBtnBack().addActionListener(this);
        pCategory.getBtnLogOut().addActionListener(this);
        pCategory.getBtnBack().addActionListener(this);
        pTable.getBtnLogOut().addActionListener(this);
        pTable.getBtnBack().addActionListener(this);
        pAccount.getBtnLogOut().addActionListener(this);
        pAccount.getBtnBack().addActionListener(this);
    }

    public static void main(String[] args) {
        NhanVien staffLogin = NhanVienDAO.getInstance().getNhanVienByTenDangNhap("phamdangdan");
        new fQuanTri(staffLogin).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(pRevenue.getBtnLogOut()) || o.equals(pProduct.getBtnLogOut()) || o.equals(pAccount.getBtnLogOut())
                || o.equals(pTable.getBtnLogOut()) || o.equals(pAccount.getBtnLogOut())) {
            EventLogOut();
        } else if (o.equals(pRevenue.getBtnBack()) || o.equals(pProduct.getBtnBack())
                || o.equals(pCategory.getBtnBack()) || o.equals(pTable.getBtnBack())
                || o.equals(pAccount.getBtnBack())) {
            EventExit();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object o = e.getSource();
        if (o.equals(tpTabMain)) {
            pProduct.allLoaded();
        }
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
