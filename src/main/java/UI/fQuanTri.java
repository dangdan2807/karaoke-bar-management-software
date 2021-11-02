package UI;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import DAO.NhanVienDAO;
import UI.PanelCustom.PnDichVu;
import UI.PanelCustom.PnKhachHang;
import UI.PanelCustom.PnLoaiDichVu;
import UI.PanelCustom.PnLoaiPhong;
import UI.PanelCustom.PnNhanVien;
import UI.PanelCustom.PnPhong;
import entity.NhanVien;

public class fQuanTri extends JFrame implements ActionListener, ChangeListener {
    private static fQuanTri instance;

    public static fQuanTri getInstance(NhanVien staffLogin) {
        if (instance == null)
            instance = new fQuanTri(staffLogin);
        return instance;
    }

    private JTabbedPane tabMain;
    private NhanVien staffLogin = null;

    /**
     * Constructor form quản trị
     * 
     * @param staff {@code NhanVien}: nhân viên truy cập
     */
    public fQuanTri(NhanVien staff) {
        setTitle("Quản Lý Hệ Thống");
        setSize(1275, 655);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.staffLogin = staff;
        createTabControl();
        setCloseAction(this);
    }

    /**
     * Khởi tạo giao diện và Tab menu
     */
    public void createTabControl() {
        tabMain = new JTabbedPane();
        tabMain.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        tabMain.setBorder(null);
        tabMain.setFont(new Font("Dialog", Font.PLAIN, 14));

        PnNhanVien pnlNhanVien = new PnNhanVien(staffLogin);
        PnKhachHang pnlKhachHang = new PnKhachHang(staffLogin);
        PnLoaiPhong pnlLoaiPhong = new PnLoaiPhong(staffLogin);
        PnPhong pnlPhong = new PnPhong(staffLogin);
        PnLoaiDichVu pnlLoaiDichVu = new PnLoaiDichVu(staffLogin);
        PnDichVu pnlDichVu = new PnDichVu(staffLogin);

        tabMain.addTab("Nhân viên", null, pnlNhanVien, "Quản lý Nhân viên");
        tabMain.addTab("Khách hàng", null, pnlKhachHang, "Quản lý Khách hàng");
        tabMain.addTab("Loại phòng", null, pnlLoaiPhong, "Quản lý loại phòng");
        tabMain.addTab("Phòng", null, pnlPhong, "Quản lý Phòng");
        tabMain.addTab("Loại dịch vụ", null, pnlLoaiDichVu, "Quản lý loại dịch vụ");
        tabMain.addTab("Dịch vụ", null, pnlDichVu, "Quản lý dịch vụ");
        tabMain.addTab("Hóa đơn", null, null, "Quản lý Hóa đơn");
        this.add(tabMain);

        tabMain.addChangeListener(this);
    }

    public static void main(String[] args) {
        NhanVien staffLogin = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
        new fQuanTri(staffLogin).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
    }

    /**
     * Bắt sự kiện khi click btn close(x), sẽ show 1 form xác nhận đăng xuất hay
     * thoát chương trình
     * 
     * @param jframe {@code JFrame} sẽ nhận sự kiện
     */
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
