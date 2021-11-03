package UI;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import DAO.NhanVienDAO;
import UI.PanelCustom.*;
import entity.NhanVien;

public class fQuanTri extends JFrame implements ActionListener, ChangeListener {
    private static fQuanTri instance;
    private JButton btnBackStaff, btnBackCustomer, btnBackRoomType, btnBackRoom, btnBackServiceType;
    private JButton btnBackService, btnBackBill;

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

        PnNhanVien pnlStaff = new PnNhanVien(staffLogin);
        PnKhachHang pnlCustomer = new PnKhachHang(staffLogin);
        PnLoaiPhong pnlRoomType = new PnLoaiPhong(staffLogin);
        PnPhong pnlRoom = new PnPhong(staffLogin);
        PnLoaiDichVu pnlServiceType = new PnLoaiDichVu(staffLogin);
        PnDichVu pnlService = new PnDichVu(staffLogin);
        PnHoaDon pnlBill = new PnHoaDon(staffLogin);

        tabMain.addTab("Nhân viên", null, pnlStaff, "Quản lý Nhân viên");
        tabMain.addTab("Khách hàng", null, pnlCustomer, "Quản lý Khách hàng");
        tabMain.addTab("Loại phòng", null, pnlRoomType, "Quản lý loại phòng");
        tabMain.addTab("Phòng", null, pnlRoom, "Quản lý Phòng");
        tabMain.addTab("Loại dịch vụ", null, pnlServiceType, "Quản lý loại dịch vụ");
        tabMain.addTab("Dịch vụ", null, pnlService, "Quản lý dịch vụ");
        tabMain.addTab("Hóa đơn", null, pnlBill, "Quản lý Hóa đơn");
        this.add(tabMain);

        btnBackStaff = pnlStaff.getBtnBack();
        btnBackCustomer = pnlCustomer.getBtnBack();
        btnBackRoomType = pnlRoomType.getBtnBack();
        btnBackRoom = pnlRoom.getBtnBack();
        btnBackServiceType = pnlServiceType.getBtnBack();
        btnBackService = pnlService.getBtnBack();
        btnBackBill = pnlBill.getBtnBack();

        tabMain.addChangeListener(this);
        btnBackStaff.addActionListener(this);
        btnBackCustomer.addActionListener(this);
        btnBackRoomType.addActionListener(this);
        btnBackRoom.addActionListener(this);
        btnBackServiceType.addActionListener(this);
        btnBackService.addActionListener(this);
        btnBackBill.addActionListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
            new fDieuHuong(staff).setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnBackStaff) || o.equals(btnBackCustomer) || o.equals(btnBackRoomType) || o.equals(btnBackRoom)
                || o.equals(btnBackServiceType) || o.equals(btnBackService) || o.equals(btnBackBill)) {
            EventBackTofDieuHuong();
        }
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

    /**
     * Đóng form hiện tại và mở form điều hướng
     */
    public void EventBackTofDieuHuong() {
        fDieuHuong f = new fDieuHuong(staffLogin);
        setVisible(false);
        f.setVisible(true);
    }
}
