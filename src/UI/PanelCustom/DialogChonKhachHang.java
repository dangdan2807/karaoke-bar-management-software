package UI.PanelCustom;

import javax.swing.*;

import DAO.KhachHangDAO;
import entity.KhachHang;

import java.awt.Color;
import java.awt.event.*;

public class DialogChonKhachHang extends JFrame implements ActionListener {
    private JTabbedPane tpTabMain;
    private PnChonKhachHang pnChonHK;

    private KhachHang khachHang = null;

    public DialogChonKhachHang() {
        setTitle("Chọn khách hàng");
        setSize(810, 420);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createUI();
    }

    public void createUI() {
        tpTabMain = new JTabbedPane();
        tpTabMain.setBackground(Color.WHITE);
        pnChonHK = new PnChonKhachHang();

        tpTabMain.addTab("Chọn khách hàng", null, pnChonHK, "Chọn khách hàng");
        tpTabMain.addTab("Quản lý khách hàng", null, null, "Quản lý khách hàng");
        this.add(tpTabMain);

        pnChonHK.getBtnChonKH().addActionListener(this);
    }

    public static void main(String[] args) {
        new DialogChonKhachHang().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(pnChonHK.getBtnChonKH())) {
            String maKH = pnChonHK.getMaKHDaChon();
            System.out.println(maKH);
            // khachHang = KhachHangDAO.getInstance().getKhachHangByMaKH(maKH);
            // System.out.println(khachHang);
            // this.dispose();
        }
    }

    public KhachHang getKHDuocChon() {
        return khachHang;
    }
}
