package Trash;

import javax.swing.*;
import javax.swing.border.*;

import DAO.NhanVienDAO;
import Event_Handlers.CheckPassword;
import UI.fDangNhap;
import UI.fQuanTri;
import UI.fThongTinCaNhan;
import UI.PanelCustom.CustomUI;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.*;

public class fDieuHuong extends JFrame implements ActionListener, MouseListener {

    protected JButton btnLogOut, btnBookingManagement, btnSystemManagement, btnInfoManagement;
    private NhanVien staff = null;
    private String STAFF = "Nhân viên", MANAGER = "Quản lý";
    private ImageIcon profileIcon = new ImageIcon(
            CustomUI.PROFILE_ICON.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon sellIcon = new ImageIcon(
            CustomUI.SELL_ICON.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon managerIcon = new ImageIcon(
            CustomUI.MANAGER_ICON.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon logoutIcon = CustomUI.LOGOUT_ICON;

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
        JPanel pnlMain = new JPanel();
        pnlMain.setBackground(Color.WHITE);
        getContentPane().add(pnlMain, BorderLayout.CENTER);
        pnlMain.setLayout(null);

        JPanel pnlTitle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gra = new GradientPaint(179, 0, Color.decode("#900a9c"), 180, getHeight(),
                        Color.decode("#00cccb"));
                g2.setPaint(gra);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        pnlTitle.setBounds(0, 0, 771, 32);
        pnlMain.add(pnlTitle);
        pnlTitle.setLayout(null);

        JLabel lblTitle = new JLabel("CHÀO MỪNG BẠN ĐẾN VỚI PHẦN MỀM QUẢN LÝ QUÁN KARAOKE");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(12, 5, 713, 22);
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblTitle.setForeground(Color.WHITE);
        pnlTitle.add(lblTitle);

        JPanel pnlBottom = new JPanel();
        pnlBottom.setBounds(0, 296, 771, 32);
        pnlBottom.setBackground(Color.WHITE);
        pnlBottom.setPreferredSize(new Dimension(280, 40));
        pnlMain.add(pnlBottom);
        pnlBottom.setLayout(null);

        btnLogOut = new JButton("Đăng xuất", logoutIcon);
        btnLogOut.setBounds(645, 0, 108, 26);
        CustomUI.getInstance().setCustomBtn(btnLogOut);
        pnlBottom.add(btnLogOut);

        btnBookingManagement = new JButton("Quản lý đặt phòng");
        btnBookingManagement.setBounds(261, 44, 240, 240);
        pnlMain.add(btnBookingManagement);
        btnBookingManagement.setFont(new Font("Dialog", Font.BOLD, 20));
        btnBookingManagement.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnBookingManagement.setPreferredSize(new Dimension(180, 150));
        btnBookingManagement.setIcon(sellIcon);
        btnBookingManagement.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBookingManagement.setHorizontalTextPosition(SwingConstants.CENTER);
        CustomUI.getInstance().setCustomBtn(btnBookingManagement);

        btnSystemManagement = new JButton("Quản Trị");
        btnSystemManagement.setBounds(10, 44, 240, 240);
        pnlMain.add(btnSystemManagement);
        btnSystemManagement.setFont(new Font("Dialog", Font.BOLD, 20));
        btnSystemManagement.setPreferredSize(new Dimension(180, 150));
        btnSystemManagement.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnSystemManagement.setIcon(managerIcon);
        btnSystemManagement.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSystemManagement.setHorizontalTextPosition(SwingConstants.CENTER);
        CustomUI.getInstance().setCustomBtn(btnSystemManagement);

        btnInfoManagement = new JButton("Thông Tin Cá Nhân");
        btnInfoManagement.setBounds(513, 44, 240, 240);
        pnlMain.add(btnInfoManagement);
        btnInfoManagement.setPreferredSize(new Dimension(180, 150));
        btnInfoManagement.setFont(new Font("Dialog", Font.BOLD, 20));
        btnInfoManagement.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnInfoManagement.setIcon(profileIcon);
        btnInfoManagement.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnInfoManagement.setHorizontalTextPosition(SwingConstants.CENTER);
        CustomUI.getInstance().setCustomBtn(btnInfoManagement);

        btnSystemManagement.addActionListener(this);
        btnSystemManagement.addMouseListener(this);
        btnBookingManagement.addActionListener(this);
        btnBookingManagement.addMouseListener(this);
        btnInfoManagement.addActionListener(this);
        btnInfoManagement.addMouseListener(this);

        btnLogOut.addActionListener(this);

        btnLogOut.addMouseListener(this);
        checkPermission(type);
        CheckPassword t = new CheckPassword(staff.getTaiKhoan().getMatKhau(), btnBookingManagement, btnSystemManagement, this);
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
        if (o.equals(btnLogOut)) {
            CustomUI.getInstance().setCustomBtnHover(btnLogOut);
        } else if (o.equals(btnBookingManagement)) {
            CustomUI.getInstance().setCustomBtnHover(btnBookingManagement);
        } else if (o.equals(btnSystemManagement)) {
            if (staff.getChucVu().equalsIgnoreCase(MANAGER)) {
                CustomUI.getInstance().setCustomBtnHover(btnSystemManagement);
            }
        } else if (o.equals(btnInfoManagement)) {
            CustomUI.getInstance().setCustomBtnHover(btnInfoManagement);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLogOut)) {
            CustomUI.getInstance().setCustomBtn(btnLogOut);
        } else if (o.equals(btnBookingManagement)) {
            CustomUI.getInstance().setCustomBtn(btnBookingManagement);
        } else if (o.equals(btnSystemManagement)) {
            if (staff.getChucVu().equalsIgnoreCase(MANAGER)) {
                CustomUI.getInstance().setCustomBtn(btnSystemManagement);
            }
        } else if (o.equals(btnInfoManagement)) {
            CustomUI.getInstance().setCustomBtn(btnInfoManagement);
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
        } else {
            btnSystemManagement.setEnabled(true);
        }
    }
}
