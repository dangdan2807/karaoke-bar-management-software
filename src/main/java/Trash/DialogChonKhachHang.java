package Trash;

import javax.swing.*;

import DAO.ConvertTime;
import DAO.KhachHangDAO;
import UI.PanelCustom.CustomUI;
import entity.KhachHang;

import java.awt.Color;
import java.awt.event.*;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.border.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class DialogChonKhachHang extends JDialog
        implements ActionListener, MouseListener, FocusListener, KeyListener, ItemListener {
    private JButton[] btnCustomerList;
    private int pnShowTableWidth = 310;
    private int heightTable = 300;
    private int viTri = -1;

    private JTextField txtCustomerId, txtCustomerName, txtCMND, txtPhoneNumber, txtBirthDay, txtGender;
    private JTextField txtSearch;
    private JComboBox<String> cboSearch;
    private JButton btnSearch, btnChooseCustomer;

    private ImageIcon searchIcon = new ImageIcon(
            CustomUI.SEARCH_ICON.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
    private ImageIcon manIcon = new ImageIcon(
            CustomUI.MAN_ICON.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
    private ImageIcon womanIcon = new ImageIcon(
            CustomUI.WOMAN_ICON.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));

    private JPanel pnlShowKH;

    private KhachHang SelectedCustomer = null;

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
        JPanel pnlMain = new JPanel();
        pnlMain.setBounds(0, 0, 800, 400);
        pnlMain.setBackground(Color.WHITE);
        this.add(pnlMain);
        pnlMain.setLayout(null);

        JPanel pnlCustomerList = new JPanel();
        pnlCustomerList.setBorder(
                new TitledBorder(null, "Danh sách khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlCustomerList.setBackground(Color.WHITE);
        pnlCustomerList.setBounds(0, 0, 500, 350);
        pnlMain.add(pnlCustomerList);
        pnlCustomerList.setLayout(new BorderLayout(0, 0));

        pnlShowKH = new JPanel();
        pnlShowKH.setBackground(Color.WHITE);
        FlowLayout flShowTable = new FlowLayout(FlowLayout.LEFT);
        flShowTable.setHgap(6);
        flShowTable.setVgap(6);
        pnlShowKH.setLayout(flShowTable);
        pnlShowKH.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));

        JScrollPane scrShowKH = new JScrollPane(pnlShowKH, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrShowKH.setBackground(Color.WHITE);
        scrShowKH.getVerticalScrollBar().setUnitIncrement(10);
        pnlCustomerList.add(scrShowKH, BorderLayout.CENTER);

        JPanel pnlCustomerInfo = new JPanel();
        pnlCustomerInfo.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "Thông tin khách hàng ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        pnlCustomerInfo.setBackground(Color.WHITE);
        pnlCustomerInfo.setBounds(510, 119, 274, 197);
        pnlMain.add(pnlCustomerInfo);
        pnlCustomerInfo.setLayout(null);

        JLabel lblCustomerId = new JLabel("Mã KH: ");
        lblCustomerId.setBounds(10, 24, 83, 14);
        pnlCustomerInfo.add(lblCustomerId);

        txtCustomerId = new JTextField();
        txtCustomerId.setEditable(false);
        txtCustomerId.setBounds(97, 21, 167, 20);
        txtCustomerId.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtCustomerId.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnlCustomerInfo.add(txtCustomerId);
        txtCustomerId.setColumns(10);

        JLabel lblCustomerName = new JLabel("Tên KH: ");
        lblCustomerName.setBounds(10, 52, 83, 14);
        pnlCustomerInfo.add(lblCustomerName);

        txtCustomerName = new JTextField();
        txtCustomerName.setEditable(false);
        txtCustomerName.setColumns(10);
        txtCustomerName.setBounds(97, 49, 167, 20);
        txtCustomerName.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtCustomerName.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnlCustomerInfo.add(txtCustomerName);

        JLabel lblCMND = new JLabel("CMNN/CCCD: ");
        lblCMND.setBounds(10, 80, 83, 14);
        pnlCustomerInfo.add(lblCMND);

        txtCMND = new JTextField();
        txtCMND.setEditable(false);
        txtCMND.setColumns(10);
        txtCMND.setBounds(97, 77, 167, 20);
        txtCMND.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtCMND.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnlCustomerInfo.add(txtCMND);

        JLabel lblPhoneNumber = new JLabel("SDT: ");
        lblPhoneNumber.setBounds(10, 108, 83, 14);
        pnlCustomerInfo.add(lblPhoneNumber);

        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setEditable(false);
        txtPhoneNumber.setColumns(10);
        txtPhoneNumber.setBounds(97, 105, 167, 20);
        txtPhoneNumber.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtPhoneNumber.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnlCustomerInfo.add(txtPhoneNumber);

        JLabel lblBirthDay = new JLabel("Ngày sinh: ");
        lblBirthDay.setBounds(10, 136, 83, 14);
        pnlCustomerInfo.add(lblBirthDay);

        txtBirthDay = new JTextField();
        txtBirthDay.setEditable(false);
        txtBirthDay.setColumns(10);
        txtBirthDay.setBounds(97, 133, 167, 20);
        txtBirthDay.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtBirthDay.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnlCustomerInfo.add(txtBirthDay);

        JLabel lblGender = new JLabel("Giới tính: ");
        lblGender.setBounds(10, 164, 83, 14);
        pnlCustomerInfo.add(lblGender);

        txtGender = new JTextField();
        txtGender.setEditable(false);
        txtGender.setColumns(10);
        txtGender.setBounds(97, 161, 167, 20);
        txtGender.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtGender.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        pnlCustomerInfo.add(txtGender);

        JPanel pnlSearch = new JPanel();
        pnlSearch.setBorder(new TitledBorder(null, "Tìm thông tin khách hàng ", TitledBorder.LEADING, TitledBorder.TOP,
                null, null));
        pnlSearch.setBackground(Color.WHITE);
        pnlSearch.setBounds(510, 0, 274, 115);
        pnlMain.add(pnlSearch);
        pnlSearch.setLayout(null);

        cboSearch = new JComboBox<String>();
        cboSearch.setUI(new BasicComboBoxUI());
        cboSearch.setBounds(10, 21, 254, 22);
        cboSearch.addItem("Tất cả");
        cboSearch.addItem("Tìm theo tên Khách hàng");
        cboSearch.addItem("Tìm theo mã khách hàng");
        cboSearch.addItem("Tìm theo số điện thoại");
        cboSearch.setFont(new Font("Dialog", Font.BOLD, 12));
        cboSearch.setBackground(Color.WHITE);
        cboSearch.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#1a66e3")));
        pnlSearch.add(cboSearch);

        JLabel lblSearch = new JLabel("Từ khóa: ");
        lblSearch.setBounds(10, 57, 77, 14);
        pnlSearch.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setColumns(10);
        txtSearch.setBounds(97, 54, 167, 20);
        txtSearch.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
        txtSearch.setEditable(false);
        pnlSearch.add(txtSearch);

        btnSearch = new JButton("Tìm khách hàng", searchIcon);
        btnSearch.setBounds(10, 82, 254, 23);
        pnlSearch.add(btnSearch);
        CustomUI.getInstance().setCustomBtn(btnSearch);

        btnChooseCustomer = new JButton("Chọn khách hàng");
        btnChooseCustomer.setBounds(510, 327, 264, 23);
        pnlMain.add(btnChooseCustomer);
        CustomUI.getInstance().setCustomBtn(btnChooseCustomer);

        btnChooseCustomer.addActionListener(this);
        btnSearch.addActionListener(this);

        btnChooseCustomer.addMouseListener(this);
        btnSearch.addMouseListener(this);

        txtSearch.addFocusListener(this);

        txtSearch.addKeyListener(this);

        cboSearch.addItemListener(this);

        LoadCustomerList(KhachHangDAO.getInstance().getCustomerListUnBooked());
    }

    public static void main(String[] args) {
        new DialogChonKhachHang().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnChooseCustomer)) {
            String customerId = txtCustomerId.getText();
            if (customerId.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn một khách hàng", "Thông báo", JOptionPane.OK_OPTION);
            } else {
                SelectedCustomer = KhachHangDAO.getInstance().getCustomerById(customerId);
                this.dispose();
            }
        } else if (o.equals(btnSearch)) {
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
        if (o.equals(txtSearch)) {
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
        if (o.equals(txtSearch)) {
            CustomUI.getInstance().setCustomTextFieldFocus(txtSearch);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object o = e.getSource();
        if (o.equals(txtSearch)) {
            txtSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
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
        if (o.equals(btnChooseCustomer)) {
            CustomUI.getInstance().setCustomBtnHover(btnChooseCustomer);
        } else if (o.equals(btnSearch)) {
            CustomUI.getInstance().setCustomBtnHover(btnSearch);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(btnChooseCustomer)) {
            CustomUI.getInstance().setCustomBtn(btnChooseCustomer);
        } else if (o.equals(btnSearch)) {
            CustomUI.getInstance().setCustomBtn(btnSearch);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o.equals(cboSearch)) {
            if (cboSearch.getSelectedIndex() == 0) {
                txtSearch.setEditable(false);
            } else {
                txtSearch.setEditable(true);
            }
        }
    }

    /**
     * Hiển thị thông tin khách hàng lên form thông tin khách hàng
     * 
     * @param khachHang {@code KhachHang}: khách hàng cần hiển thị
     */
    private void loadDataLenForm(KhachHang khachHang) {
        txtCustomerId.setText(khachHang.getMaKH());
        txtCustomerName.setText(khachHang.getHoTen());
        txtCMND.setText(khachHang.getCmnd());
        txtPhoneNumber.setText(khachHang.getSoDienThoai());
        String ngaySinhStr = ConvertTime.getInstance().convertTimeToString(khachHang.getNgaySinh(), "dd-MM-yyyy");
        txtBirthDay.setText(ngaySinhStr);
        boolean gender = khachHang.getGioiTinh();
        txtGender.setText("Nữ");
        if (!gender) {
            txtGender.setText("Nam");
        }
    }

    /**
     * Hiển thị thông tin khách hàng hàng
     * 
     * @param customerId {@code String}: mã của khách hàng cần hiển thị
     */
    private void loadBtnKH(String customerId) {
        KhachHang customer = KhachHangDAO.getInstance().getCustomerById(customerId);
        String tenBtn = "<html>" + "<p style='text-align: left; width:116px'>Mã KH: " + customer.getMaKH() + " </p>"
                + "<p style='text-align: left; width:116px'>Tên: " + customer.getHoTen() + " </p>"
                + "<p style='text-align: left; width:116px'>CMND: " + customer.getCmnd() + "</p>"
                + "<p style='text-align: left; width:116px'>SDT: " + customer.getSoDienThoai() + "</p></html>";
        int index = 0;
        for (int i = 0; i < btnCustomerList.length; i++) {
            if (btnCustomerList[i].getText().contains(tenBtn))
                index = i;
            else if (btnCustomerList[i].getText().equals("")) {
                index = i;
                break;
            }
        }
        boolean gender = customer.getGioiTinh();
        if (gender) {
            btnCustomerList[index].setIcon(womanIcon);
        } else {
            btnCustomerList[index].setIcon(manIcon);
        }
        btnCustomerList[index].setText(tenBtn);
        btnCustomerList[index].setForeground(Color.WHITE);
        btnCustomerList[index].setFont(new Font("Dialog", Font.BOLD, 11));
        btnCustomerList[index].setBackground(Color.decode("#3c8dbc"));
        btnCustomerList[index].setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCustomerList[index].setHorizontalTextPosition(SwingConstants.RIGHT);
        btnCustomerList[index].setPreferredSize(new Dimension(KhachHangDAO.TABLE_WIDTH, KhachHangDAO.TABLE_HEIGHT));
        pnlShowKH.revalidate();
        pnlShowKH.repaint();
    }

    /**
     * Hiển thị danh sách khách hàng
     * 
     * @param customerList {@code ArrayList<KhachHang>} : danh sách khách hàng cần
     *                    hiển thị
     */
    private void LoadCustomerList(ArrayList<KhachHang> customerList) {
        heightTable = KhachHangDAO.TABLE_HEIGHT;
        pnlShowKH.removeAll();
        pnlShowKH.revalidate();
        pnlShowKH.repaint();
        Border lineRed = new LineBorder(Color.RED, 2);
        Border lineGray = new LineBorder(Color.GRAY, 1);
        int sizeCustomerList = customerList.size();
        btnCustomerList = new JButton[sizeCustomerList];
        for (int i = 0; i < sizeCustomerList; i++) {
            final int selection = i;
            KhachHang customer = customerList.get(i);
            String customerId = customer.getMaKH();
            btnCustomerList[selection] = new JButton("");
            loadBtnKH(customerId);
            btnCustomerList[selection].setBorder(lineGray);
            if ((i + 1) % 2 == 0) {
                heightTable += KhachHangDAO.TABLE_HEIGHT;
                pnlShowKH.setPreferredSize(new Dimension(pnShowTableWidth, heightTable));
            }
            btnCustomerList[selection].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (viTri != -1) {
                        btnCustomerList[viTri].setBorder(lineGray);
                    }
                    viTri = selection;
                    btnCustomerList[selection].setBorder(lineRed);
                    KhachHang khActiveE = KhachHangDAO.getInstance().getCustomerById(customerId);
                    loadDataLenForm(khActiveE);
                }
            });
            // sự kiện hover chuột
            btnCustomerList[selection].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    btnCustomerList[selection].setBackground(Color.decode("#605ca8"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btnCustomerList[selection].setBackground(Color.decode("#3c8dbc"));
                }
            });
            pnlShowKH.add(btnCustomerList[selection]);
        }
    }

    /**
     * Tìm kiếm thông tin của khách hàng
     */
    private void timKiemKH() {
        int searchType = cboSearch.getSelectedIndex();
        ArrayList<KhachHang> customerList = new ArrayList<KhachHang>();
        String keyword = txtSearch.getText();
        if (searchType != 0 && keyword.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Bạn phải nhập từ khoá cần tìm kiếm");
        } else {
            switch (searchType) {
            case 0:
                txtSearch.setText("");
                customerList = KhachHangDAO.getInstance().getCustomerList();
                break;
            case 1:
                customerList = KhachHangDAO.getInstance().getCustomerListByName(keyword);
                break;
            case 2:
                customerList = KhachHangDAO.getInstance().getCustomerListById(keyword);
                break;
            case 3:
                customerList = KhachHangDAO.getInstance().getCustomerListByPhoneNumber(keyword);
                break;
            default:
                break;
            }
            LoadCustomerList(customerList);
        }
    }

    /**
     * Lấy thông tin khách hàng được chọn
     * 
     * @return {@code KhachHang}: khách hàng được chọn
     */
    public KhachHang getSelectedCustomer() {
        return SelectedCustomer;
    }
}
