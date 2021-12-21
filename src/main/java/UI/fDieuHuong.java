package UI;

import javax.swing.*;

import Event_Handlers.CheckPassword;
import UI.PanelCustom.CustomUI;
import UI.PanelCustom.MyButton;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.*;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code CTDichVu}
 * <p>
 * Người tham gia thiết kế: Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 13/10/2021
 * <p>
 * Lần cập nhật cuối: 21/12/2021
 * <p>
 * Nội dung cập nhật: đổi tên btn Help thành hỗ trợ
 */
public class fDieuHuong extends JFrame implements ActionListener {

    private static final long serialVersionUID = 8033841194327699528L;
    private JButton btnLogOut, btnBookingManagement, btnSystemManagement, btnInfoManagement, btnHelp;
    private JLabel lblStaffName, lblStaffNameTxt;
    private NhanVien staffLogin = null;

    private final String STAFF = "Nhân viên", MANAGER = "Chủ quán";
    private ImageIcon logoApp = new ImageIcon(fDieuHuong.class.getResource(CustomUI.LOGO_APP));
    private ImageIcon logoutIcon = new ImageIcon(fDieuHuong.class.getResource(CustomUI.LOGOUT_ICON));
    private ImageIcon profileIcon = new ImageIcon(new ImageIcon(fDieuHuong.class.getResource(
            CustomUI.PROFILE_ICON)).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon sellIcon = new ImageIcon(new ImageIcon(fDieuHuong.class.getResource(
            CustomUI.SELL_ICON)).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon managerIcon = new ImageIcon(new ImageIcon(fDieuHuong.class.getResource(
            CustomUI.MANAGER_ICON)).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
            Color.decode("#FAFFD1"));

    /**
     * Khỏi tại giao diện form điều hướng
     * 
     * @param staffLogin {@code NhanVien}: nhân viên truy cập
     */
    public fDieuHuong(NhanVien staff) {
        setTitle("Điều hướng quản lý");
        setSize(776, 370);
        setIconImage(logoApp.getImage());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        staffLogin = staff;
        createFormManage(staffLogin.getChucVu());
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

        btnBookingManagement = new JButton("QUẢN LÝ ĐẶT PHÒNG");
        btnBookingManagement.setIcon(sellIcon);
        customBtnSize(btnBookingManagement, 20, 180, 150);

        btnInfoManagement = new JButton("THÔNG TIN CÁ NHÂN");
        btnInfoManagement.setIcon(profileIcon);
        customBtnSize(btnInfoManagement, 20, 180, 150);

        btnSystemManagement = new JButton("QUẢN TRỊ");
        btnSystemManagement.setIcon(managerIcon);
        customBtnSize(btnSystemManagement, 20, 180, 150);

        if (staffLogin.getChucVu().equals(MANAGER)) {
            btnBookingManagement.setBounds(261, 25, 240, 240);
            btnSystemManagement.setBounds(10, 25, 240, 240);
            btnInfoManagement.setBounds(513, 25, 240, 240);
            btnSystemManagement.setVisible(true);
            btnSystemManagement.setEnabled(true);
        } else {
            btnBookingManagement.setBounds(111, 25, 240, 240);
            btnInfoManagement.setBounds(433, 25, 240, 240);
            btnSystemManagement.setVisible(false);
            btnSystemManagement.setEnabled(false);
        }

        lblStaffName = new JLabel("Nhân viên:");
        lblStaffName.setFont(new Font("Dialog", Font.BOLD, 15));
        lblStaffName.setForeground(Color.WHITE);
        lblStaffName.setBounds(20, 0, 80, 30);
        pnlMain.add(lblStaffName);

        String staffName = staffLogin.getHoTen();
        if (staffName.equals("") || staffName.isEmpty() || staffName == null) {
            staffName = "";
        }

        lblStaffNameTxt = new JLabel(staffName);
        lblStaffNameTxt.setForeground(Color.WHITE);
        lblStaffNameTxt.setFont(new Font("Dialog", Font.BOLD, 15));
        lblStaffNameTxt.setBounds(110, 0, 200, 30);
        pnlMain.add(lblStaffNameTxt);

        btnHelp = new JButton("<html><u>Hỗ trợ?</u></html>");
        customBtnSize(btnHelp, 15, 80, 30);
        btnHelp.setBounds(670, 0, 80, 30);
        pnlMain.add(btnHelp);

        pnlMain.add(btnSystemManagement);
        pnlMain.add(btnBookingManagement);
        pnlMain.add(btnInfoManagement);

        lblStaffName = new JLabel("Nhân viên:");
        lblStaffName.setFont(new Font("Dialog", Font.BOLD, 15));
        lblStaffName.setForeground(Color.WHITE);
        lblStaffName.setBounds(20, 0, 80, 30);
        pnlMain.add(lblStaffName);

        lblStaffNameTxt = new JLabel(staffLogin.getHoTen());
        lblStaffNameTxt.setForeground(Color.WHITE);
        lblStaffNameTxt.setFont(new Font("Dialog", Font.BOLD, 15));
        lblStaffNameTxt.setBounds(110, 0, 200, 30);
        pnlMain.add(lblStaffNameTxt);

        btnLogOut.addActionListener(this);
        btnHelp.addActionListener(this);
        btnInfoManagement.addActionListener(this);
        btnSystemManagement.addActionListener(this);
        btnBookingManagement.addActionListener(this);

        checkPermission(type);
        CheckPassword t = new CheckPassword(staffLogin.getTaiKhoan().getMatKhau(), btnBookingManagement,
                btnSystemManagement, this);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLogOut)) {
            logout(this);
        } else if (o.equals(btnBookingManagement)) {
            fQuanLyDatPhong winBookingManagement = new fQuanLyDatPhong(staffLogin);
            this.setVisible(false);
            winBookingManagement.setVisible(true);
        } else if (o.equals(btnSystemManagement)) {
            fQuanTri winSystemManagement = new fQuanTri(staffLogin);
            this.setVisible(false);
            winSystemManagement.setVisible(true);
        } else if (o.equals(btnInfoManagement)) {
            fThongTinCaNhan winInfoManagement = new fThongTinCaNhan(staffLogin);
            winInfoManagement.setModal(true);
            winInfoManagement.setVisible(true);
            staffLogin = winInfoManagement.getNewStaffInfo();
            if (staffLogin != null) {
                String password = staffLogin.getTaiKhoan().getMatKhau();
                if (!password.equalsIgnoreCase("123456") && password.matches("^[a-zA-Z0-9_@!#]{6,}$")) {
                    btnBookingManagement.setEnabled(true);
                    checkPermission(staffLogin.getChucVu());
                }
            }
        } else if (o.equals(btnHelp)) {
            fHelp winHelp = new fHelp(staffLogin);
            winHelp.setModal(true);
            winHelp.setVisible(true);
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
                logout(jframe);
            }
        });
    }

    /**
     * Xác nhận đăng xuất tài khoản
     * 
     * @param jframe {@code JFrame} frame sẽ nhận sự kiện
     */
    private void logout(JFrame jframe) {
        String title = "Xác nhận đăng xuất tài khoản";
        String message = "Bạn muốn đăng xuất tài khoản này";
        Object[] options = { "Đăng xuất", "Hủy" };
        int selected = JOptionPane.showOptionDialog(jframe, message, title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        if (selected == 0) {
            fDangNhap winLogin = new fDangNhap();
            jframe.setVisible(false);
            winLogin.setVisible(true);
        }
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

    /**
     * Thiết lập các thông số cho các btn theo font size chữ, chiều dài và rộng của
     * nút
     * 
     * @param btn
     */
    private void customBtnSize(JButton btn, int fontSize, int width, int height) {
        btn.setFont(new Font("Dialog", Font.BOLD, fontSize));
        btn.setPreferredSize(new Dimension(width, height));
        customBtn(btn);
    }

    /**
     * Thiết lập các thông số cho các btn
     * 
     * @param btn
     */
    private void customBtn(JButton btn) {
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setForeground(Color.white);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
