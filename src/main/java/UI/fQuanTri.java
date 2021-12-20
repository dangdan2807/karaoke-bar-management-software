package UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

import UI.PanelCustom.*;
import entity.NhanVien;

/**
 * Khung của giao diện quản trị hệ thống
 * <p>
 * Người tham gia thiết kế: Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 10/10/2021
 * <p>
 * Lần cập nhật cuối: 14/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class fQuanTri extends JFrame implements ActionListener, ChangeListener {
    /**
     * 
     */
    private static final long serialVersionUID = 2840284348303179625L;
    private static fQuanTri instance;
    private JButton btnBackStaff, btnBackCustomer, btnBackRoomType, btnBackRoom, btnBackServiceType;
    private JButton btnBackService, btnBackBill, btnBackStatistical;

    public static fQuanTri getInstance(NhanVien staffLogin) {
        if (instance == null)
            instance = new fQuanTri(staffLogin);
        return instance;
    }

    private ImageIcon logoApp = new ImageIcon(fQuanTri.class.getResource(CustomUI.LOGO_APP));
    private JTabbedPane tabMain;
    private NhanVien staffLogin = null;
    private PnNhanVien pnlStaff;
    private PnKhachHang pnlCustomer;
    private PnLoaiPhong pnlRoomType;
    private PnPhong pnlRoom;
    private PnLoaiDichVu pnlServiceType;
    private PnDichVu pnlService;
    private PnHoaDon pnlBill;
    private PnThongKeDoanhThu pnlStatistical;

    /**
     * Khởi tạo giao diện form quản trị
     * 
     * @param staff {@code NhanVien}: nhân viên truy cập
     */
    public fQuanTri(NhanVien staff) {
        setTitle("Quản Lý Hệ Thống");
        setSize(1280, 665);
        setResizable(false);
        setIconImage(logoApp.getImage());
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

        pnlStaff = new PnNhanVien(staffLogin);
        pnlCustomer = new PnKhachHang(staffLogin, 0);
        pnlRoomType = new PnLoaiPhong(staffLogin);
        pnlRoom = new PnPhong(staffLogin);
        pnlServiceType = new PnLoaiDichVu(staffLogin);
        pnlService = new PnDichVu(staffLogin);
        pnlBill = new PnHoaDon(staffLogin);
        pnlStatistical = new PnThongKeDoanhThu(staffLogin);

        tabMain.addTab("Nhân viên", null, pnlStaff, "Quản lý Nhân viên");
        tabMain.addTab("Khách hàng", null, null, "Quản lý Khách hàng");
        tabMain.addTab("Loại phòng", null, null, "Quản lý loại phòng");
        tabMain.addTab("Phòng", null, null, "Quản lý Phòng");
        tabMain.addTab("Loại dịch vụ", null, null, "Quản lý loại dịch vụ");
        tabMain.addTab("Dịch vụ", null, null, "Quản lý dịch vụ");
        tabMain.addTab("Hóa đơn", null, null, "Quản lý hóa đơn");
        tabMain.addTab("Thống kê doanh thu", null, null, "Thống kê doanh thu");
        this.add(tabMain);

        btnBackStaff = pnlStaff.getBtnBack();
        btnBackCustomer = pnlCustomer.getBtnBack();
        btnBackRoomType = pnlRoomType.getBtnBack();
        btnBackRoom = pnlRoom.getBtnBack();
        btnBackServiceType = pnlServiceType.getBtnBack();
        btnBackService = pnlService.getBtnBack();
        btnBackBill = pnlBill.getBtnBack();
        btnBackStatistical = pnlStatistical.getBtnBack();

        btnBackStaff.addActionListener(this);
        btnBackCustomer.addActionListener(this);
        btnBackRoomType.addActionListener(this);
        btnBackRoom.addActionListener(this);
        btnBackServiceType.addActionListener(this);
        btnBackService.addActionListener(this);
        btnBackBill.addActionListener(this);
        btnBackStatistical.addActionListener(this);

        tabMain.addChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnBackStaff) || o.equals(btnBackCustomer) || o.equals(btnBackRoomType) || o.equals(btnBackRoom)
                || o.equals(btnBackServiceType) || o.equals(btnBackService) || o.equals(btnBackBill)
                || o.equals(btnBackStatistical)) {
            EventBackTofDieuHuong();
        }
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

    @Override
    public void stateChanged(ChangeEvent e) {
        int tabSelectedIndex = tabMain.getSelectedIndex();
        switch (tabSelectedIndex) {
            case 0:
                pnlStaff = new PnNhanVien(staffLogin);
                tabMain.setComponentAt(0, pnlStaff);
                btnBackStaff = pnlStaff.getBtnBack();
                btnBackStaff.addActionListener(this);
                break;
            case 1:
                pnlCustomer = new PnKhachHang(staffLogin, 1);
                tabMain.setComponentAt(1, pnlCustomer);
                btnBackCustomer = pnlCustomer.getBtnBack();
                btnBackCustomer.addActionListener(this);
                break;
            case 2:
                pnlRoomType = new PnLoaiPhong(staffLogin);
                tabMain.setComponentAt(2, pnlRoomType);
                btnBackRoomType = pnlRoomType.getBtnBack();
                btnBackRoomType.addActionListener(this);
                break;
            case 3:
                pnlRoom = new PnPhong(staffLogin);
                tabMain.setComponentAt(3, pnlRoom);
                btnBackRoom = pnlRoom.getBtnBack();
                btnBackRoom.addActionListener(this);
                break;
            case 4:
                pnlServiceType = new PnLoaiDichVu(staffLogin);
                tabMain.setComponentAt(4, pnlServiceType);
                btnBackServiceType = pnlServiceType.getBtnBack();
                btnBackServiceType.addActionListener(this);
                break;
            case 5:
                pnlService = new PnDichVu(staffLogin);
                tabMain.setComponentAt(5, pnlService);
                btnBackService = pnlService.getBtnBack();
                btnBackService.addActionListener(this);
                break;
            case 6:
                pnlBill = new PnHoaDon(staffLogin);
                tabMain.setComponentAt(6, pnlBill);
                btnBackBill = pnlBill.getBtnBack();
                btnBackBill.addActionListener(this);
                break;
            case 7:
                pnlStatistical = new PnThongKeDoanhThu(staffLogin);
                tabMain.setComponentAt(7, pnlStatistical);
                btnBackStatistical = pnlStatistical.getBtnBack();
                btnBackStatistical.addActionListener(this);
                break;
        }
    }
}