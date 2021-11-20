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

    private JTextField txt;
    private JLabel lbl;
    private Font fontTxt = new Font("Dialog", Font.BOLD, 14);
    private Font fontLbl = new Font("Dialog", Font.BOLD, 14);
    private Color backgroundColor = new Color(255, 255, 255, 100);
    private Border border = BorderFactory.createEmptyBorder();

    private int widthDefault = 100;
    private int heightDefault = 21;

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
        this.widthDefault = width;
        this.heightDefault = height;
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
        this.widthDefault = width;
        this.heightDefault = height;
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
        // int heightDefault = 21;
        int width = widthDefault / 2;
        txt = new JTextField(String.valueOf(pageNumber));
        txt.setFont(fontTxt);
        txt.setBounds(0, 0, width - 2, heightDefault);
        txt.setBackground(new Color(255, 255, 255, 0));
        txt.setHorizontalAlignment(JTextField.RIGHT);
        txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txt.setBorder(border);
        txt.setForeground(Color.WHITE);
        this.add(txt);
        
        JLabel lblSeparates = new JLabel("/");
        lblSeparates.setFont(fontLbl);
        lblSeparates.setBounds(width - 1, 0, 4, heightDefault);
        lblSeparates.setForeground(Color.WHITE);
        lblSeparates.setBackground(new Color(255, 255, 255, 0));
        this.add(lblSeparates);
        
        lbl = new JLabel(String.valueOf(totalPage));
        lbl.setFont(fontLbl);
        lbl.setBounds(width + 6, 0, width - 6, heightDefault);
        lbl.setForeground(Color.WHITE);
        lbl.setBackground(backgroundColor);
        this.add(lbl);
        txt.addKeyListener(this);
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
        if (o.equals(txt)) {
            handler.enterOnlyNumbers(key, txt, 20);
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
        txt.setFont(fontTxt);
        lbl.setFont(fontLbl);
    }

    /**
     * Cập nhật màu chữ
     * 
     * @param color {@code Color}: màu chữ
     */
    public void setForegroundCustom(Color color) {
        txt.setForeground(color);
        lbl.setForeground(color);
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
        txt.setText(String.valueOf(pageNumber));
    }

    /**
     * Cập nhật tổng số trang
     * 
     * @param totalPage {@code int}: tổng số trang
     */
    public void setTotalPage(int totalPage) {
        lbl.setText(String.valueOf(totalPage));
    }

    /**
     * Lấy số trang hiện tại
     * 
     * @return {@code int}: số trang hiện tại
     */
    public int getCurrentPage() {
        String currentPage = txt.getText();
        return Integer.parseInt(currentPage.isEmpty() ? "1" : currentPage);
    }

    /**
     * Lấy tổng số trang
     * 
     * @return {@code int}: tổng số trang
     */
    public int getTotalPage() {
        String totalPage = lbl.getText();
        return Integer.parseInt(totalPage.isEmpty() ? "1" : totalPage);
    }

    /**
     * Tăng trang hiện tại lên 1
     */
    public void plusOne() {
        String currentPageStr = txt.getText();
        int currentPage = Integer.parseInt(currentPageStr.isEmpty() ? "1" : currentPageStr);
        String totalPageStr = lbl.getText();
        int totalPage = Integer.parseInt(totalPageStr.isEmpty() ? "1" : totalPageStr);
        if (currentPage > 0 && currentPage < totalPage) {
            ++currentPage;
            txt.setText(String.valueOf(currentPage));
        }
    }

    /**
     * Giảm trang hiện tại đi 1
     */
    public void subtractOne() {
        String currentPageStr = txt.getText();
        int currentPage = Integer.parseInt(currentPageStr.isEmpty() ? "1" : currentPageStr);
        String totalPageStr = lbl.getText();
        int totalPage = Integer.parseInt(totalPageStr.isEmpty() ? "1" : totalPageStr);
        if (currentPage > 1 && currentPage <= totalPage) {
            --currentPage;
            txt.setText(String.valueOf(currentPage));
        }
    }

    /**
     * Chuyển đến trang cuối cùng
     */
    public void toTheLastPage() {
        String totalPageStr = lbl.getText();
        int totalPage = Integer.parseInt(totalPageStr.isEmpty() ? "1" : totalPageStr);
        txt.setText(String.valueOf(totalPage));
    }

    /**
     * Chuyển đến trang đầu tiên
     */
    public void toTheFirstPage() {
        txt.setText("1");
    }
}
