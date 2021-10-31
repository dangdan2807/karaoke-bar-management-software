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

    private JTabbedPane tpTabMain;
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
        tpTabMain = new JTabbedPane();
        tpTabMain.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        tpTabMain.setBorder(null);
        tpTabMain.setFont(new Font("Dialog", Font.PLAIN, 14));

        PnNhanVien pnNhanVien = new PnNhanVien(staffLogin);
        PnKhachHang pnKhachHang = new PnKhachHang(staffLogin);
        PnLoaiPhong pnLoaiPhong = new PnLoaiPhong(staffLogin);
        PnPhong pnPhong = new PnPhong(staffLogin);
        PnLoaiDichVu pnLoaiDichVu = new PnLoaiDichVu(staffLogin);
        PnDichVu pnDichVu = new PnDichVu(staffLogin);

        tpTabMain.addTab("Nhân viên", null, pnNhanVien, "Quản lý Nhân viên");
        tpTabMain.addTab("Khách hàng", null, pnKhachHang, "Quản lý Khách hàng");
        tpTabMain.addTab("Loại phòng", null, pnLoaiPhong, "Quản lý loại phòng");
        tpTabMain.addTab("Phòng", null, pnPhong, "Quản lý Phòng");
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
