package Client.UI;

import javax.swing.*;
import javax.swing.event.*;

import Client.UI.PanelCustom.*;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;

import DAO.NhanVienDAO;
import entity.NhanVien;

/**
 * khung giao diện quản lý đặt phòng
 * <p>
 * Người tham gia thiết kế: Võ Minh Hiếu
 * <p>
 * Ngày tạo: 11/10/2021
 * <p>
 * Lần cập nhật cuối: 17/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class fQuanLyDatPhong extends JFrame implements ActionListener, ChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2840284348303179625L;
	private static fQuanLyDatPhong instance;
    private JButton btnBackCustomer, btnBackBill, btnBackRentRoom;

    public static fQuanLyDatPhong getInstance(NhanVien staffLogin) {
        if (instance == null)
            instance = new fQuanLyDatPhong(staffLogin);
        return instance;
    }

    private ImageIcon logoApp = CustomUI.LOGO_APP;
    private JTabbedPane tabMain;
    private NhanVien staffLogin = null;

    /**
     * Constructor form quản trị
     * 
     * @param staff {@code NhanVien}: nhân viên truy cập
     */
    public fQuanLyDatPhong(NhanVien staff) {
        setTitle("Quản Lý Đặt Phòng");
        setSize(1280, 700);
        setIconImage(logoApp.getImage());
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
        tabMain.setFont(new Font("Dialog", Font.PLAIN, 15));

        pnDatPhong pnlRentRoom = new pnDatPhong(staffLogin);
        PnKhachHang pnlCustomer = new PnKhachHang(staffLogin);
        PnHoaDon pnlBill = new PnHoaDon(staffLogin);

        tabMain.addTab("Quản lý đặt phòng", null, pnlRentRoom, "Quản lý Nhân viên");
        tabMain.addTab("Quản lý khách hàng", null, pnlCustomer, "Quản lý Khách hàng");
        tabMain.addTab("Quản lý Hóa đơn", null, pnlBill, "Quản lý Hóa đơn");
        this.add(tabMain);

        btnBackRentRoom = pnlRentRoom.getBtnBack();
        btnBackCustomer = pnlCustomer.getBtnBack();
        btnBackBill = pnlBill.getBtnBack();

        tabMain.addChangeListener(this);
        btnBackRentRoom.addActionListener(this);
        btnBackCustomer.addActionListener(this);
        btnBackBill.addActionListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NhanVien staff = null;
        try {
            NhanVienDAO staffDAO = (NhanVienDAO) Naming.lookup("rmi://localhost:1099/staffDAO");
            staff = staffDAO.getStaffByUsername("phamdangdan");
        } catch (Exception e) {
            e.printStackTrace();
        }
            new fQuanLyDatPhong(staff).setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnBackCustomer) || o.equals(btnBackBill) || o.equals(btnBackRentRoom)) {
            EventBackTofDieuHuong();
        } else if (o.equals(tabMain)) {
            // EventChangeTab();
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
