package UI;

import javax.swing.*;

import DAO.NhanVienDAO;
import Event_Handlers.CheckPassword;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.MyButton;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.*;

public class fDieuHuong extends JFrame implements ActionListener {

    private JButton btnLogOut, btnBookingManagement, btnSystemManagement, btnInfoManagement;
    private NhanVien staff = null;
    private String STAFF = "Nhân viên", MANAGER = "Quản lý";
    private ImageIcon profileIcon = new ImageIcon(
            CustomUI.PROFILE_ICON.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon sellIcon = new ImageIcon(
            CustomUI.SELL_ICON.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon managerIcon = new ImageIcon(
            CustomUI.MANAGER_ICON.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon logoutIcon = CustomUI.LOGOUT_ICON;
    private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
            Color.decode("#FAFFD1"));;

    /**
     * Constructor form điều hướng
     * 
     * @param staff {@code NhanVien}: nhân viên truy cập
     */
    public fDieuHuong(NhanVien nhanVienLogin) {
        setTitle("Điều hướng quản lý");
        setSize(776, 370);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        staff = nhanVienLogin;
        createFormManage(staff.getChucVu());
        setCloseAction(this);
    }

    /**
     * Khởi tạo giao diện
     * 
     * @param type {@code String}: chức vụ của nhân viên
     */
    public void createFormManage(String type) {
        JPanel pnlMain = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gra = new GradientPaint(179, 0, Color.decode("#900a9c"), 180, getHeight(),
                        Color.decode("#00cccb"));
                g2.setPaint(gra);
                g2.fillRect(0, 0, getWidth() - 1, getHeight());
            }
        };
        pnlMain.setBounds(0, 0, 776, 370);
        pnlMain.setBackground(Color.WHITE);
        getContentPane().add(pnlMain);
        pnlMain.setLayout(null);

        JPanel pnlBottom = new JPanel();
        pnlBottom.setBounds(0, 275, 762, 53);
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setPreferredSize(new Dimension(280, 40));
        pnlBottom.setOpaque(false);
        pnlMain.add(pnlBottom);
        pnlBottom.setLayout(null);

        btnLogOut = new MyButton(114, 35, "Đăng xuất", gra, logoutIcon.getImage(), 32, 19);
        ((MyButton) btnLogOut).setFontCustom(new Font("Dialog", Font.BOLD, 14));
        btnLogOut.setBounds(635, 0, 114, 35);
        btnLogOut.setToolTipText("Đăng xuất tài khoản");
        pnlBottom.add(btnLogOut);

        btnBookingManagement = new JButton("Quản lý đặt phòng");
        btnBookingManagement.setBounds(261, 25, 240, 240);
        btnBookingManagement.setIcon(sellIcon);
        customBtn(btnBookingManagement);
        pnlMain.add(btnBookingManagement);

        btnSystemManagement = new JButton("Quản Trị");
        btnSystemManagement.setBounds(10, 25, 240, 240);
        btnSystemManagement.setIcon(managerIcon);
        customBtn(btnSystemManagement);
        pnlMain.add(btnSystemManagement);

        btnInfoManagement = new JButton("Thông Tin Cá Nhân");
        btnInfoManagement.setBounds(513, 25, 240, 240);
        btnInfoManagement.setIcon(profileIcon);
        customBtn(btnInfoManagement);
        pnlMain.add(btnInfoManagement);

        btnBookingManagement.addActionListener(this);
        btnInfoManagement.addActionListener(this);
        btnLogOut.addActionListener(this);
        btnSystemManagement.addActionListener(this);

        checkPermission(type);
        CheckPassword t = new CheckPassword(staff.getTaiKhoan().getMatKhau(), btnBookingManagement, btnSystemManagement,
                this);
        t.start();
    }

    public static void main(String[] args) {
        NhanVien staff = NhanVienDAO.getInstance().getStaffByUsername("phamdangdan");
        new fDieuHuong(staff).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLogOut)) {
            fDangNhap winLogin = new fDangNhap();
            this.setVisible(false);
            winLogin.setVisible(true);
        } else if (o.equals(btnBookingManagement)) {
            fQuanLyDatPhong winBookingManagement = new fQuanLyDatPhong(staff);
            this.setVisible(false);
            winBookingManagement.setVisible(true);
        } else if (o.equals(btnSystemManagement)) {
            fQuanTri winSystemManagement = new fQuanTri(staff);
            this.setVisible(false);
            winSystemManagement.setVisible(true);
        } else if (o.equals(btnInfoManagement)) {
            fThongTinCaNhan winInfoManagement = new fThongTinCaNhan(staff);
            winInfoManagement.setModal(true);
            winInfoManagement.setVisible(true);
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
                fDangNhap f = new fDangNhap();
                jframe.setVisible(false);
                f.setVisible(true);
            }
        });
    }

    /**
     * Kiểm tra quyền của nhân viên
     * 
     * @param type {@code String}: chức vụ của nhân viên
     */
    private void checkPermission(String type) {
        if (type.equalsIgnoreCase(STAFF)) {
            btnSystemManagement.setEnabled(false);
        } else if (type.equalsIgnoreCase(MANAGER)) {
            btnSystemManagement.setEnabled(true);
        }
    }

    private void customBtn(JButton btn) {
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Dialog", Font.BOLD, 20));
        btn.setPreferredSize(new Dimension(180, 150));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
