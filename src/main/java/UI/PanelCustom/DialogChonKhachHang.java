package UI.PanelCustom;

import javax.swing.*;

import DAO.ConvertTime;
import DAO.KhachHangDAO;
import entity.KhachHang;

import java.awt.Color;
import java.awt.event.*;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.border.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class DialogChonKhachHang extends JDialog
        implements ActionListener, MouseListener, FocusListener, KeyListener, ItemListener {
    private JButton[] btnDSKHang;
    private int pnShowTableWidth = 310;
    private int heightTable = 300;
    private int viTri = -1;

    private JTextField txtMaKH, txtTenKH, txtCMND, txtSDT, txtNgaySinh, txtGioiTinh, txtTimKiem;
    private JComboBox<String> cboTimKiem;
    private JButton btnTimKiem, btnChonKH;

    private ImageIcon searchIcon = new ImageIcon(
            CustomUI.SEARCH_ICON.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
    private ImageIcon manIcon = new ImageIcon(
            CustomUI.MAN_ICON.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
    private ImageIcon womanIcon = new ImageIcon(
            CustomUI.WOMAN_ICON.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));

    private JPanel pnShowKH;

    private KhachHang khachHang = null;

    /**
     * Constructor mặc định không tham số
     */
    public DialogChonKhachHang() {
        setTitle("Chọn khách hàng");
        setSize(810, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Khởi tạo giao diện
     */
    public void createUI() {
        JPanel pnMain = new JPanel();
        pnMain.setBounds(0, 0, 800, 400);
        pnMain.setBackground(Color.WHITE);
        this.add(pnMain);
        pnMain.setLayout(null);

        JPanel pnDSKhachHang = new JPanel();
        pnDSKhachHang.setBorder(
                new TitledBorder(null, "Danh sách khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnDSKhachHang.setBackground(Color.WHITE);
        pnDSKhachHang.setBounds(0, 0, 500, 350);
        pnMain.add(pnDSKhachHang);
        pnDSKhachHang.setLayout(new BorderLayout(0, 0));

        pnShowKH = new JPanel();
        pnShowKH.setBackground(Color.WHITE);
        FlowLayout flShowTable = new FlowLayout(FlowLayout.LEFT);
        flShowTable.setHgap(6);
        flShowTable.setVgap(6);
        pnShowKH.setLayout(flShowTable);
        pnShowKH.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));

        JScrollPane scpShowKH = new JScrollPane(pnShowKH, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scpShowKH.setBackground(Color.WHITE);
        scpShowKH.getVerticalScrollBar().setUnitIncrement(10);
        pnDSKhachHang.add(scpShowKH, BorderLayout.CENTER);

        JPanel pnTTKhachHang = new JPanel();
        pnTTKhachHang.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "Thông tin khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnTTKhachHang.setBackground(Color.WHITE);
        pnTTKhachHang.setBounds(510, 119, 274, 197);
        pnMain.add(pnTTKhachHang);
        pnTTKhachHang.setLayout(null);

        JLabel lbMaKH = new JLabel("Mã KH: ");
        lbMaKH.setBounds(10, 24, 83, 14);
        pnTTKhachHang.add(lbMaKH);

        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        txtMaKH.setBounds(97, 21, 167, 20);
        txtMaKH.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtMaKH.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnTTKhachHang.add(txtMaKH);
        txtMaKH.setColumns(10);

        JLabel lbTenKH = new JLabel("Tên KH: ");
        lbTenKH.setBounds(10, 52, 83, 14);
        pnTTKhachHang.add(lbTenKH);

        txtTenKH = new JTextField();
        txtTenKH.setEditable(false);
        txtTenKH.setColumns(10);
        txtTenKH.setBounds(97, 49, 167, 20);
        txtTenKH.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtTenKH.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnTTKhachHang.add(txtTenKH);

        JLabel lbCMND = new JLabel("CMNN/CCCD: ");
        lbCMND.setBounds(10, 80, 83, 14);
        pnTTKhachHang.add(lbCMND);

        txtCMND = new JTextField();
        txtCMND.setEditable(false);
        txtCMND.setColumns(10);
        txtCMND.setBounds(97, 77, 167, 20);
        txtCMND.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtCMND.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnTTKhachHang.add(txtCMND);

        JLabel lbSDT = new JLabel("SDT: ");
        lbSDT.setBounds(10, 108, 83, 14);
        pnTTKhachHang.add(lbSDT);

        txtSDT = new JTextField();
        txtSDT.setEditable(false);
        txtSDT.setColumns(10);
        txtSDT.setBounds(97, 105, 167, 20);
        txtSDT.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtSDT.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnTTKhachHang.add(txtSDT);

        JLabel lbNgaySinh = new JLabel("Ngày sinh: ");
        lbNgaySinh.setBounds(10, 136, 83, 14);
        pnTTKhachHang.add(lbNgaySinh);

        txtNgaySinh = new JTextField();
        txtNgaySinh.setEditable(false);
        txtNgaySinh.setColumns(10);
        txtNgaySinh.setBounds(97, 133, 167, 20);
        txtNgaySinh.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtNgaySinh.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnTTKhachHang.add(txtNgaySinh);

        JLabel lbGioiTinh = new JLabel("Giới tính: ");
        lbGioiTinh.setBounds(10, 164, 83, 14);
        pnTTKhachHang.add(lbGioiTinh);

        txtGioiTinh = new JTextField();
        txtGioiTinh.setEditable(false);
        txtGioiTinh.setColumns(10);
        txtGioiTinh.setBounds(97, 161, 167, 20);
        txtGioiTinh.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtGioiTinh.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnTTKhachHang.add(txtGioiTinh);

        JPanel pnTimKiem = new JPanel();
        pnTimKiem.setBorder(new TitledBorder(null, "Tìm thông tin khách hàng ", TitledBorder.LEADING, TitledBorder.TOP,
                null, null));
        pnTimKiem.setBackground(Color.WHITE);
        pnTimKiem.setBounds(510, 0, 274, 115);
        pnMain.add(pnTimKiem);
        pnTimKiem.setLayout(null);

        cboTimKiem = new JComboBox<String>();
        cboTimKiem.setUI(new BasicComboBoxUI());
        cboTimKiem.setBounds(10, 21, 254, 22);
        cboTimKiem.addItem("Tất cả");
        cboTimKiem.addItem("Tìm theo tên Khách hàng");
        cboTimKiem.addItem("Tìm theo mã khách hàng");
        cboTimKiem.addItem("Tìm theo số điện thoại");
        cboTimKiem.setFont(new Font("Dialog", Font.BOLD, 12));
        cboTimKiem.setBackground(Color.WHITE);
        cboTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#1a66e3")));
        pnTimKiem.add(cboTimKiem);

        JLabel lbTimKiem = new JLabel("Từ khóa: ");
        lbTimKiem.setBounds(10, 57, 77, 14);
        pnTimKiem.add(lbTimKiem);

        txtTimKiem = new JTextField();
        txtTimKiem.setColumns(10);
        txtTimKiem.setBounds(97, 54, 167, 20);
        txtTimKiem.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtTimKiem.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        txtTimKiem.setEditable(false);
        pnTimKiem.add(txtTimKiem);

        btnTimKiem = new JButton("Tìm khách hàng", searchIcon);
        btnTimKiem.setBounds(10, 82, 254, 23);
        pnTimKiem.add(btnTimKiem);
        CustomUI.getInstance().setCustomBtn(btnTimKiem);

        btnChonKH = new JButton("Chọn khách hàng");
        btnChonKH.setBounds(510, 327, 264, 23);
        pnMain.add(btnChonKH);
        CustomUI.getInstance().setCustomBtn(btnChonKH);

        btnChonKH.addActionListener(this);
        btnTimKiem.addActionListener(this);

        btnChonKH.addMouseListener(this);
        btnTimKiem.addMouseListener(this);

        txtTimKiem.addFocusListener(this);

        txtTimKiem.addKeyListener(this);

        cboTimKiem.addItemListener(this);

        LoadDSPhong(KhachHangDAO.getInstance().getDSKhachHangChuaDatPhong());
    }

    public static void main(String[] args) {
        new DialogChonKhachHang().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnChonKH)) {
            String maKH = txtMaKH.getText();
            if (maKH.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn một khách hàng", "Thông báo", JOptionPane.OK_OPTION);
            } else {
                khachHang = KhachHangDAO.getInstance().getKhachHangByMaKH(maKH);
                this.dispose();
            }
        } else if (o.equals(btnTimKiem)) {
            timKiemKH();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Object o = e.getSource();
        // bắt sự kiện nhấn phím enter tự nhấn btnLogin
        if (o.equals(txtTimKiem)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                timKiemKH();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {
        Object o = e.getSource();
        if (o.equals(txtTimKiem)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtTimKiem);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object o = e.getSource();
        if (o.equals(txtTimKiem)) {
            txtTimKiem.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
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
        if (o.equals(btnChonKH)) {
            CustomUI.getInstance().setCustomBtnHover(btnChonKH);
        } else if (o.equals(btnTimKiem)) {
            CustomUI.getInstance().setCustomBtnHover(btnTimKiem);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(btnChonKH)) {
            CustomUI.getInstance().setCustomBtn(btnChonKH);
        } else if (o.equals(btnTimKiem)) {
            CustomUI.getInstance().setCustomBtn(btnTimKiem);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(cboTimKiem)) {
            if (cboTimKiem.getSelectedIndex() == 0) {
                txtTimKiem.setEditable(false);
            } else {
                txtTimKiem.setEditable(true);
            }
        }
    }

    /**
     * Hiển thị thông tin khách hàng lên form thông tin khách hàng
     * 
     * @param khachHang <code>KhachHang</code>: khách hàng cần hiển thị
     */
    private void loadDataLenForm(KhachHang khachHang) {
        txtMaKH.setText(khachHang.getMaKH());
        txtTenKH.setText(khachHang.getHoTen());
        txtCMND.setText(khachHang.getCmnd());
        txtSDT.setText(khachHang.getSoDienThoai());
        String ngaySinhStr = ConvertTime.getInstance().convertTimeToString(khachHang.getNgaySinh(),
                "dd-MM-yyyy");
        txtNgaySinh.setText(ngaySinhStr);
        boolean gioiTinh = khachHang.getGioiTinh();
        txtGioiTinh.setText("Nữ");
        if (gioiTinh == false) {
            txtGioiTinh.setText("Nam");
        }
    }

    /**
     * Hiển thị thông tin khách hàng hàng
     * 
     * @param maKH mã khách hàng
     */
    private void loadBtnKH(String maKH) {
        KhachHang khachHang = KhachHangDAO.getInstance().getKhachHangByMaKH(maKH);
        String tenBtn = "<html>" + "<p style='text-align: left; width:116px'>Mã KH: " + khachHang.getMaKH() + " </p>"
                + "<p style='text-align: left; width:116px'>Tên: " + khachHang.getHoTen() + " </p>"
                + "<p style='text-align: left; width:116px'>CMND: " + khachHang.getCmnd() + "</p>"
                + "<p style='text-align: left; width:116px'>SDT: " + khachHang.getSoDienThoai() + "</p></html>";
        int index = 0;
        for (int i = 0; i < btnDSKHang.length; i++) {
            if (btnDSKHang[i].getText().contains(tenBtn))
                index = i;
            else if (btnDSKHang[i].getText().equals("")) {
                index = i;
                break;
            }
        }
        boolean gioiTinh = khachHang.getGioiTinh();
        if (gioiTinh) {
            btnDSKHang[index].setIcon(womanIcon);
        } else {
            btnDSKHang[index].setIcon(manIcon);
        }
        btnDSKHang[index].setText(tenBtn);
        btnDSKHang[index].setForeground(Color.WHITE);
        btnDSKHang[index].setFont(new Font("Dialog", Font.BOLD, 11));
        btnDSKHang[index].setBackground(Color.decode("#3c8dbc"));
        btnDSKHang[index].setVerticalTextPosition(SwingConstants.BOTTOM);
        btnDSKHang[index].setHorizontalTextPosition(SwingConstants.RIGHT);
        btnDSKHang[index].setPreferredSize(new Dimension(KhachHangDAO.TABLE_WIDTH, KhachHangDAO.TABLE_HEIGHT));
        pnShowKH.revalidate();
        pnShowKH.repaint();
    }

    /**
     * Hiển thị danh sách khách hàng
     * 
     * @param dsKhachHang <code>ArrayList KhachHang</code>
     */
    private void LoadDSPhong(ArrayList<KhachHang> dsKhachHang) {
        heightTable = KhachHangDAO.TABLE_HEIGHT;
        pnShowKH.removeAll();
        pnShowKH.revalidate();
        pnShowKH.repaint();
        Border lineRed = new LineBorder(Color.RED, 2);
        Border lineGray = new LineBorder(Color.GRAY, 1);
        int sizeDSPhong = dsKhachHang.size();
        btnDSKHang = new JButton[sizeDSPhong];
        for (int i = 0; i < sizeDSPhong; i++) {
            final int selection = i;
            KhachHang khachHang = dsKhachHang.get(i);
            String maKH = khachHang.getMaKH();
            btnDSKHang[selection] = new JButton("");
            loadBtnKH(maKH);
            btnDSKHang[selection].setBorder(lineGray);
            if ((i + 1) % 2 == 0) {
                heightTable += KhachHangDAO.TABLE_HEIGHT;
                pnShowKH.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));
            }
            btnDSKHang[selection].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (viTri != -1) {
                        btnDSKHang[viTri].setBorder(lineGray);
                    }
                    viTri = selection;
                    btnDSKHang[selection].setBorder(lineRed);
                    KhachHang khActiveE = KhachHangDAO.getInstance().getKhachHangByMaKH(maKH);
                    loadDataLenForm(khActiveE);
                }
            });
            // sự kiện hover chuột
            btnDSKHang[selection].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    btnDSKHang[selection].setBackground(Color.decode("#605ca8"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btnDSKHang[selection].setBackground(Color.decode("#3c8dbc"));
                }
            });
            pnShowKH.add(btnDSKHang[selection]);
        }
    }

    /**
     * Lấy giá trị trong text filed mã khách hàng
     * 
     * @return <code>String</code>: mã khách hàng
     */
    public String getValueTxtMaKH() {
        return txtMaKH.getText();
    }

    /**
     * Lấy ra JButton chọn khách hàng
     * 
     * @return <code>JButton</code>: button chọn khách hàng
     */
    public JButton getBtnChonKH() {
        return btnChonKH;
    }

    /**
     * Tìm kiếm thông tin của khách hàng
     */
    private void timKiemKH() {
        int loaiTimKiem = cboTimKiem.getSelectedIndex();
        ArrayList<KhachHang> dsKhachHang = new ArrayList<KhachHang>();
        String tuKhoa = txtTimKiem.getText();
        if (loaiTimKiem != 0 && tuKhoa.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Bạn phải nhập từ khoá cần tìm kiếm");
        } else {
            switch (loaiTimKiem) {
            case 0:
                txtTimKiem.setText("");
                dsKhachHang = KhachHangDAO.getInstance().getDSKhachHang();
                break;
            case 1:
                dsKhachHang = KhachHangDAO.getInstance().getDSKhachHangByTenKH(tuKhoa);
                break;
            case 2:
                dsKhachHang = KhachHangDAO.getInstance().getDSKhachHangByMaKH(tuKhoa);
                break;
            case 3:
                dsKhachHang = KhachHangDAO.getInstance().getDSKhachHangBySDT(tuKhoa);
                break;
            default:
                break;
            }
            LoadDSPhong(dsKhachHang);
        }
    }

    /**
     * Lấy thông tin khách hàng được chọn
     * 
     * @return <code>KhachHang</code>: khách hàng được chọn
     */
    public KhachHang getKHDuocChon() {
        return khachHang;
    }
}
