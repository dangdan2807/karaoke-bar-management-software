package UI.PanelCustom;

import javax.swing.*;

import Event_Handlers.InputEventHandler;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * JPanel dùng để thực hiện phân trang
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Võ Minh Hiếu
 * <p>
 * Ngày tạo: 17/11/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: sửa lỗi: bắt sự kiện khi nhập số bị lệch hàng
 * <p>
 */
public class PnTextFiledPaging extends JPanel implements KeyListener {

    private JTextField txtPaging;
    private JLabel lblPaging;
    private JLabel lblSeparates;
    private Font fontTxt = new Font("Dialog", Font.BOLD, 14);
    private Font fontLbl = new Font("Dialog", Font.BOLD, 14);
    private Color backgroundColor = new Color(255, 255, 255, 100);
    private Border border = BorderFactory.createEmptyBorder();

    /**
     * Constructor text field phân trang
     * 
     * @param width  {@code int}: chiều dài được hiển thị
     * @param height {@code int}: chiều cao được hiển thị
     */
    public PnTextFiledPaging(int width, int height) {
        setLayout(null);
        setSize(width, height);
        setBounds(0, 0, width, height);
        // setResizable(false);
        // setLocationRelativeTo(null);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        createGUI(1, 1);
    }

    /**
     * Constructor text field phân trang
     * 
     * @param width      {@code int}: chiều dài được hiển thị
     * @param height     {@code int}: chiều cao được hiển thị
     * @param pageNumber {@code int}: số trang hiện tại
     * @param totalPage  {@code int}: tổng số trang
     */
    public PnTextFiledPaging(int width, int height, int pageNumber, int totalPage) {
        setLayout(null);
        setSize(width, height);
        setBounds(0, 0, width, height);
        // setResizable(false);
        // setLocationRelativeTo(null);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        createGUI(pageNumber, totalPage);
    }

    /**
     * Hàm tạo text field phân trang
     *
     * @param pageNumber {@code int}: số trang hiện tại
     * @param totalPage  {@code int}: tổng số trang
     */
    private void createGUI(int pageNumber, int totalPage) {
        this.setBackground(backgroundColor);
        int widthDefault = this.getWidth();
        int heightDefault = this.getHeight();
        int width = widthDefault / 2;
        txtPaging = new JTextField(String.valueOf(pageNumber));
        txtPaging.setFont(fontTxt);
        txtPaging.setBounds(0, 0, width - 2, heightDefault);
        txtPaging.setBackground(new Color(255, 255, 255, 0));
        txtPaging.setHorizontalAlignment(JTextField.RIGHT);
        txtPaging.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtPaging.setBorder(border);
        this.add(txtPaging);

        lblSeparates = new JLabel("/");
        lblSeparates.setFont(fontLbl);
        lblSeparates.setBounds(width - 1, 0, 4, heightDefault);
        lblSeparates.setBackground(new Color(255, 255, 255, 0));
        this.add(lblSeparates);

        lblPaging = new JLabel(String.valueOf(totalPage));
        lblPaging.setFont(fontLbl);
        lblPaging.setBounds(width + 6, 0, width - 6, heightDefault);
        lblPaging.setBackground(backgroundColor);
        this.add(lblPaging);
        txtPaging.addKeyListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PnTextFiledPaging(100, 21).setVisible(true);
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        Object o = e.getSource();
        int key = e.getKeyCode();
        InputEventHandler handler = new InputEventHandler();
        if (o.equals(txtPaging)) {
            handler.enterOnlyNumbers(key, txtPaging, 20);
        }
    }

    /**
     * Cập nhật font chữ
     * 
     * @param font {@code Font}: font chữ
     */
    public void setFontCustom(Font font) {
        int size = font.getSize();
        int style = font.getStyle();
        this.fontTxt = font.deriveFont(style, size);
        this.fontLbl = font.deriveFont(style, size + 1);
        txtPaging.setFont(fontTxt);
        lblPaging.setFont(fontLbl);
    }

    /**
     * Cập nhật màu chữ
     * 
     * @param color {@code Color}: màu chữ
     */
    public void setForegroundCustom(Color color) {
        txtPaging.setForeground(color);
        txtPaging.setCaretColor(color);
        lblPaging.setForeground(color);
        lblSeparates.setForeground(color);
    }

    /**
     * Cập nhật màu nền
     * 
     * @param color {@code Color}: màu nền
     */
    public void setBackgroundCustom(Color color) {
        this.backgroundColor = color;
        this.setBackground(color);
    }

    /**
     * Cập nhật số trang hiện tại
     *
     * @param pageNumber {@code int}: số trang hiện tại
     */
    public void setCurrentPage(int pageNumber) {
        txtPaging.setText(String.valueOf(pageNumber));
    }

    /**
     * Lấy ra text field Paging
     * 
     * @return {@code JTextField}: txtPaging
     */
    public JTextField getTextFieldPaging() {
        return txtPaging;
    }

    /**
     * Cập nhật tổng số trang
     * 
     * @param totalPage {@code int}: tổng số trang
     */
    public void setTotalPage(int totalPage) {
        lblPaging.setText(String.valueOf(totalPage));
    }

    /**
     * Lấy số trang hiện tại
     * 
     * @return {@code int}: số trang hiện tại
     */
    public int getCurrentPage() {
        String currentPageStr = txtPaging.getText();
        String totalPageStr = lblPaging.getText();
        if (currentPageStr.equals("")) {
            currentPageStr = "1";
        } else if (currentPageStr.equals("0")) {
            currentPageStr = "1";
        }
        if(totalPageStr.equals("")){
            totalPageStr = "1";
        } else if (totalPageStr.equals("0")) {
            totalPageStr = "1";
        }
        int currentPage = Integer.parseInt(currentPageStr);
        int totalPage = Integer.parseInt(totalPageStr);
        if(currentPage > totalPage){
            currentPage = totalPage;
        } else if (currentPage < 1) {
            currentPage = 1;
        }
        txtPaging.setText(String.valueOf(currentPage));
        return currentPage;
    }

    /**
     * Lấy tổng số trang
     * 
     * @return {@code int}: tổng số trang
     */
    public int getTotalPage() {
        String totalPage = lblPaging.getText();
        return Integer.parseInt(totalPage.isEmpty() ? "1" : totalPage);
    }

    /**
     * Tăng trang hiện tại lên 1
     */
    public void plusOne() {
        String currentPageStr = txtPaging.getText();
        int currentPage = Integer.parseInt(currentPageStr.isEmpty() ? "1" : currentPageStr);
        String totalPageStr = lblPaging.getText();
        int totalPage = Integer.parseInt(totalPageStr.isEmpty() ? "1" : totalPageStr);
        if (currentPage > 0 && currentPage < totalPage) {
            ++currentPage;
            txtPaging.setText(String.valueOf(currentPage));
        }
    }

    /**
     * Giảm trang hiện tại đi 1
     */
    public void subtractOne() {
        String currentPageStr = txtPaging.getText();
        int currentPage = Integer.parseInt(currentPageStr.isEmpty() ? "1" : currentPageStr);
        String totalPageStr = lblPaging.getText();
        int totalPage = Integer.parseInt(totalPageStr.isEmpty() ? "1" : totalPageStr);
        if (currentPage > 1 && currentPage <= totalPage) {
            --currentPage;
            txtPaging.setText(String.valueOf(currentPage));
        }
    }

    /**
     * Chuyển đến trang cuối cùng
     */
    public void toTheLastPage() {
        String totalPageStr = lblPaging.getText();
        int totalPage = Integer.parseInt(totalPageStr.isEmpty() ? "1" : totalPageStr);
        txtPaging.setText(String.valueOf(totalPage));
    }

    /**
     * Chuyển đến trang đầu tiên
     */
    public void toTheFirstPage() {
        txtPaging.setText("1");
    }
}
