package UI;

import javax.swing.*;
import javax.swing.border.*;

import DAO.CheckPassword;
import DAO.NhanVienDAO;
import UI.PanelCustom.CustomUI;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.*;

public class fDieuHuong extends JFrame implements ActionListener, MouseListener {

    protected JButton btnLogOut, btnQLDatPhong, btnQLHeThong, btnQLThongTinCN;
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
     * @param staff <code>NhanVien</code>: nhân viên truy cập
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
     * @param type <code>String</code>: chức vụ của nhân viên
     */
    public void createFormManage(String type) {
        JPanel pnMain = new JPanel();
        pnMain.setBackground(Color.WHITE);
        getContentPane().add(pnMain, BorderLayout.CENTER);
        pnMain.setLayout(null);

        JPanel pnTitle = new JPanel() {
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
        pnTitle.setBounds(0, 0, 771, 32);
        pnMain.add(pnTitle);
        pnTitle.setLayout(null);

        JLabel lbTitle = new JLabel("CHÀO MỪNG BẠN ĐẾN VỚI PHẦN MỀM QUẢN LÝ QUÁN KARAOKE");
        lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitle.setBounds(12, 5, 713, 22);
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbTitle.setForeground(Color.WHITE);
        pnTitle.add(lbTitle);

        JPanel pnBottom = new JPanel();
        pnBottom.setBounds(0, 296, 771, 32);
        pnBottom.setBackground(Color.WHITE);
        pnBottom.setPreferredSize(new Dimension(280, 40));
        pnMain.add(pnBottom);
        pnBottom.setLayout(null);

        btnLogOut = new JButton("Đăng xuất", logoutIcon);
        btnLogOut.setBounds(645, 0, 108, 26);
        CustomUI.getInstance().setCustomBtn(btnLogOut);
        pnBottom.add(btnLogOut);

        btnQLDatPhong = new JButton("Quản lý đặt phòng");
        btnQLDatPhong.setBounds(261, 44, 240, 240);
        pnMain.add(btnQLDatPhong);
        btnQLDatPhong.setFont(new Font("Dialog", Font.BOLD, 20));
        btnQLDatPhong.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnQLDatPhong.setPreferredSize(new Dimension(180, 150));
        btnQLDatPhong.setIcon(sellIcon);
        btnQLDatPhong.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnQLDatPhong.setHorizontalTextPosition(SwingConstants.CENTER);
        CustomUI.getInstance().setCustomBtn(btnQLDatPhong);

        btnQLHeThong = new JButton("Quản Trị");
        btnQLHeThong.setBounds(10, 44, 240, 240);
        pnMain.add(btnQLHeThong);
        btnQLHeThong.setFont(new Font("Dialog", Font.BOLD, 20));
        btnQLHeThong.setPreferredSize(new Dimension(180, 150));
        btnQLHeThong.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnQLHeThong.setIcon(managerIcon);
        btnQLHeThong.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnQLHeThong.setHorizontalTextPosition(SwingConstants.CENTER);
        CustomUI.getInstance().setCustomBtn(btnQLHeThong);

        btnQLThongTinCN = new JButton("Thông Tin Cá Nhân");
        btnQLThongTinCN.setBounds(513, 44, 240, 240);
        pnMain.add(btnQLThongTinCN);
        btnQLThongTinCN.setPreferredSize(new Dimension(180, 150));
        btnQLThongTinCN.setFont(new Font("Dialog", Font.BOLD, 20));
        btnQLThongTinCN.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnQLThongTinCN.setIcon(profileIcon);
        btnQLThongTinCN.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnQLThongTinCN.setHorizontalTextPosition(SwingConstants.CENTER);
        CustomUI.getInstance().setCustomBtn(btnQLThongTinCN);

        btnQLHeThong.addActionListener(this);
        btnQLHeThong.addMouseListener(this);
        btnQLDatPhong.addActionListener(this);
        btnQLDatPhong.addMouseListener(this);
        btnQLThongTinCN.addActionListener(this);
        btnQLThongTinCN.addMouseListener(this);

        btnLogOut.addActionListener(this);

        btnLogOut.addMouseListener(this);
        checkPermission(type);
        CheckPassword t = new CheckPassword(staff.getTaiKhoan().getMatKhau(), btnQLDatPhong, btnQLHeThong, this);
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
            fDangNhap f = new fDangNhap();
            this.setVisible(false);
            f.setVisible(true);
        } else if (o.equals(btnQLDatPhong)) {
            fQuanLyDatPhong f = new fQuanLyDatPhong(staff);
            this.setVisible(false);
            f.setVisible(true);
        } else if (o.equals(btnQLHeThong)) {
            fQuanTri f = new fQuanTri(staff);
            this.setVisible(false);
            f.setVisible(true);
        } else if (o.equals(btnQLThongTinCN)) {
            fThongTinCaNhan f = new fThongTinCaNhan(staff);
            f.setModal(true);
            f.setVisible(true);
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
        } else if (o.equals(btnQLDatPhong)) {
            CustomUI.getInstance().setCustomBtnHover(btnQLDatPhong);
        } else if (o.equals(btnQLHeThong)) {
            if (staff.getChucVu().equalsIgnoreCase(MANAGER)) {
                CustomUI.getInstance().setCustomBtnHover(btnQLHeThong);
            }
        } else if (o.equals(btnQLThongTinCN)) {
            CustomUI.getInstance().setCustomBtnHover(btnQLThongTinCN);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLogOut)) {
            CustomUI.getInstance().setCustomBtn(btnLogOut);
        } else if (o.equals(btnQLDatPhong)) {
            CustomUI.getInstance().setCustomBtn(btnQLDatPhong);
        } else if (o.equals(btnQLHeThong)) {
            if (staff.getChucVu().equalsIgnoreCase(MANAGER)) {
                CustomUI.getInstance().setCustomBtn(btnQLHeThong);
            }
        } else if (o.equals(btnQLThongTinCN)) {
            CustomUI.getInstance().setCustomBtn(btnQLThongTinCN);
        }
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
                fDangNhap f = new fDangNhap();
                jframe.setVisible(false);
                f.setVisible(true);
            }
        });
    }

    /**
     * Kiểm tra quyền của nhân viên
     * 
     * @param type <code>String</code>:
     */
    private void checkPermission(String type) {
        if (type.equalsIgnoreCase(STAFF)) {
            btnQLHeThong.setEnabled(false);
        } else {
            btnQLHeThong.setEnabled(true);
        }
    }
}
