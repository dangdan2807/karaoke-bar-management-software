package UI;

import javax.swing.*;
import javax.swing.border.*;

import DAO.NhanVienDAO;
import UI.PanelCustom.customUI;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.*;

public class fPageNavigation extends JFrame implements ActionListener, MouseListener {

    private JButton btnLogOut, btnQLBanHang, btnQLHeThong, btnQLThongTinCN;
    private NhanVien staff = null;
    private String STAFF = "Nhân viên", MANAGER = "Quản lý";
    private ImageIcon profileIcon = new ImageIcon(
            new ImageIcon("img/profile_512.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon sellIcon = new ImageIcon(
            new ImageIcon("img/money_512.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
    private ImageIcon managerIcon = new ImageIcon(
            new ImageIcon("img/manager_512.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));

    public fPageNavigation(NhanVien staffLogin) {
        setTitle("Điều hướng quản lý");
        setSize(800, 375);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        staff = staffLogin;
        createFormManage(staff.getChucVu());
        setCloseAction(this);
    }

    public void createFormManage(String type) {
        JPanel pnMain = new JPanel();
        pnMain.setBackground(Color.WHITE);
        getContentPane().add(pnMain, BorderLayout.CENTER);
        pnMain.setLayout(null);

        JPanel pnTitle = new JPanel();
        pnTitle.setBounds(0, 0, 789, 32);
        pnTitle.setBackground(Color.decode("#d0e1fd"));
        pnMain.add(pnTitle);
        pnTitle.setLayout(null);

        JLabel lbTitle = new JLabel("CHÀO MỪNG BẠN ĐẾN VỚI PHẦN MỀM QUẢN LÝ QUÁN KARAOKE");
        lbTitle.setBounds(34, 5, 527, 22);
        lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lbTitle.setForeground(Color.decode("#1a66e3"));
        pnTitle.add(lbTitle);

        JPanel pnBottom = new JPanel();
        pnBottom.setBounds(0, 304, 789, 32);
        pnBottom.setBackground(Color.WHITE);
        pnBottom.setPreferredSize(new Dimension(280, 40));
        pnMain.add(pnBottom);
        pnBottom.setLayout(null);

        btnLogOut = new JButton("Đăng xuất");
        btnLogOut.setBounds(684, 0, 93, 26);
        customUI.getInstance().setCustomBtn(btnLogOut);
        pnBottom.add(btnLogOut);

        btnQLBanHang = new JButton("Quản lý đặt phòng");
        btnQLBanHang.setBounds(261, 44, 265, 248);
        pnMain.add(btnQLBanHang);
        btnQLBanHang.setFont(new Font("Dialog", Font.BOLD, 20));
        btnQLBanHang.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnQLBanHang.setPreferredSize(new Dimension(180, 150));
        btnQLBanHang.setIcon(sellIcon);
        btnQLBanHang.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnQLBanHang.setHorizontalTextPosition(SwingConstants.CENTER);
        customUI.getInstance().setCustomBtn(btnQLBanHang);

        btnQLHeThong = new JButton("Quản Trị");
        btnQLHeThong.setBounds(10, 44, 239, 248);
        pnMain.add(btnQLHeThong);
        btnQLHeThong.setFont(new Font("Dialog", Font.BOLD, 20));
        btnQLHeThong.setPreferredSize(new Dimension(180, 150));
        btnQLHeThong.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnQLHeThong.setIcon(managerIcon);
        btnQLHeThong.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnQLHeThong.setHorizontalTextPosition(SwingConstants.CENTER);
        customUI.getInstance().setCustomBtn(btnQLHeThong);

        btnQLThongTinCN = new JButton("<html></br><p style='text-align: center;'>Thông Tin Cá Nhân</p></html>");
        btnQLThongTinCN.setBounds(538, 44, 239, 248);
        pnMain.add(btnQLThongTinCN);
        btnQLThongTinCN.setPreferredSize(new Dimension(180, 150));
        btnQLThongTinCN.setFont(new Font("Dialog", Font.BOLD, 20));
        btnQLThongTinCN.setBorder(new LineBorder(Color.decode("#3EA1EC"), 2));
        btnQLThongTinCN.setIcon(profileIcon);
        btnQLThongTinCN.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnQLThongTinCN.setHorizontalTextPosition(SwingConstants.CENTER);
        customUI.getInstance().setCustomBtn(btnQLThongTinCN);

        btnQLHeThong.addActionListener(this);
        btnQLHeThong.addMouseListener(this);
        btnQLBanHang.addActionListener(this);
        btnQLBanHang.addMouseListener(this);
        btnQLThongTinCN.addActionListener(this);
        btnQLThongTinCN.addMouseListener(this);

        btnLogOut.addActionListener(this);

        btnLogOut.addMouseListener(this);
        checkAccount(type);
    }

    public static void main(String[] args) {
        NhanVien account = NhanVienDAO.getInstance().getNhanVienByTenDangNhap("phamdangdan");
        new fPageNavigation(account).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLogOut)) {
            fLogin f = new fLogin();
            this.setVisible(false);
            f.setVisible(true);
        } else if (o.equals(btnQLBanHang)) {
            fManagerSale f = new fManagerSale(staff);
            this.setVisible(false);
            f.setVisible(true);
        } else if (o.equals(btnQLHeThong)) {
            fAdmin f = new fAdmin(staff);
            this.setVisible(false);
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
            customUI.getInstance().setCustomBtnHover(btnLogOut);
        } else if (o.equals(btnQLBanHang)) {
            customUI.getInstance().setCustomBtnHover(btnQLBanHang);
        } else if (o.equals(btnQLHeThong)) {
            if (staff.getChucVu().equalsIgnoreCase(MANAGER)) {
                customUI.getInstance().setCustomBtnHover(btnQLHeThong);
            }
        } else if (o.equals(btnQLThongTinCN)) {
            customUI.getInstance().setCustomBtnHover(btnQLThongTinCN);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLogOut)) {
            customUI.getInstance().setCustomBtn(btnLogOut);
        } else if (o.equals(btnQLBanHang)) {
            customUI.getInstance().setCustomBtn(btnQLBanHang);
        } else if (o.equals(btnQLHeThong)) {
            if (staff.getChucVu().equalsIgnoreCase(MANAGER)) {
                customUI.getInstance().setCustomBtn(btnQLHeThong);
            }
        } else if (o.equals(btnQLThongTinCN)) {
            customUI.getInstance().setCustomBtn(btnQLThongTinCN);
        }
    }

    // mô tả: Bắt sự kiện khi click btn close(x), sẽ show 1 form xác nhận đăng xuất
    // hay thoát chương trình
    public void setCloseAction(JFrame jframe) {
        jframe.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                fLogin f = new fLogin();
                jframe.setVisible(false);
                f.setVisible(true);
            }
        });
    }

    private void checkAccount(String type) {
        if (type.equalsIgnoreCase(STAFF)) {
            btnQLHeThong.setEnabled(false);
        }
    }
}
