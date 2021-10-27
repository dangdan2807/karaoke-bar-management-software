package UI;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

import DAO.NhanVienDAO;
import UI.PanelCustom.PnDichVu;
import UI.PanelCustom.PnLoaiDichVu;
import UI.PanelCustom.PnNhanVien;
import entity.NhanVien;

public class fQuanTri extends JFrame implements ActionListener, ChangeListener {
    private static fQuanTri instance;

    public static fQuanTri getInstance(NhanVien staffLogin) {
        if (instance == null)
            instance = new fQuanTri(staffLogin);
        return instance;
    }

    private JTabbedPane tpTabMain;
    private NhanVien staffLogin = null;

    /**
     * Constructor form quản trị
     * @param staff <code>NhanVien</code> : nhân viên truy cập
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
        tpTabMain = new JTabbedPane();
        PnNhanVien pnNhanVien = new PnNhanVien(staffLogin);
        PnDichVu pnDichVu = new PnDichVu(staffLogin);
        PnLoaiDichVu pnLoaiDichVu = new PnLoaiDichVu(staffLogin);

        tpTabMain.addTab("Nhân viên", null, pnNhanVien, "Quản lý Nhân viên");
        tpTabMain.addTab("Khách hàng", null, null, "Quản lý Khách hàng");
        tpTabMain.addTab("Loại phòng", null, null, "Quản lý loại phòng");
        tpTabMain.addTab("Phòng", null, null, "Quản lý Phòng");
        tpTabMain.addTab("Loại dịch vụ", null, pnLoaiDichVu, "Quản lý loại dịch vụ");
        tpTabMain.addTab("Dịch vụ", null, pnDichVu, "Quản lý dịch vụ");
        tpTabMain.addTab("Hóa đơn", null, null, "Quản lý Hóa đơn");
        this.add(tpTabMain);

        tpTabMain.addChangeListener(this);
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
     * @param jframe sẽ nhận sự kiện
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
