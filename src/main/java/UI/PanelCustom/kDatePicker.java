// Author : Pham Dang Dan - KayJuno
// Date created   : April 23, 2021
// Last update date: Oct 7, 2021

package UI.PanelCustom;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.sql.Date;

/**
 * <code>kDatePicker</code> dùng để tạo 1 <code>JPanel</code> có thể dùng để
 * chọn ngày
 */
public class kDatePicker extends JPanel implements ActionListener, MouseListener {
    private JTextField txt;
    private JButton btn;
    private int widthDefault = 150;
    private int heightDefault = 20;
    private DialogDatePicker f = new DialogDatePicker();
    private String pathImg = "src/main/java/img/";
    private ImageIcon calenderIcon = new ImageIcon(pathImg + "calender_16.png");
    private Color backgroundColor = Color.decode("#f9f9f9");

    /**
     * Constructor mặc định không tham số
     */
    public kDatePicker() {
        setLayout(null);
        // setSize(200, 200);
        // setResizable(false);
        // setLocationRelativeTo(null);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        createGUI();
    }

    /**
     * Constructor với 2 tham số
     * 
     * @param width  <code>int</code>: chiều dài được hiển thị
     * @param height <code>int</code>: chiều cao được hiển thị
     */
    public kDatePicker(int width, int height) {
        setLayout(null);
        setBounds(0, 0, width, height);
        widthDefault = width;
        heightDefault = height;
        createGUI();
    }

    /**
     * Khởi tạo giao diện
     */
    private void createGUI() {
        txt = new JTextField();
        txt.setBounds(0, 0, widthDefault - 30, heightDefault);
        txt.setEditable(false);
        // txt.setBorder(border);
        txt.setText(DialogDatePicker.getToDay());
        txt.setBackground(backgroundColor);

        btn = new JButton(calenderIcon);
        btn.setBounds(widthDefault - 30, 0, 30, heightDefault);

        this.add(txt);
        this.add(btn);
        btn.addActionListener(this);
        txt.addMouseListener(this);
    }

    public static void main(String[] args) {
        new kDatePicker().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btn)) {
            f.setModal(true);
            f.setVisible(true);
            String date = f.getValueString();
            if (!(date.equals(""))) {
                txt.setText(date);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(txt)) {
            f.setModal(true);
            f.setVisible(true);
            String date = f.getValueString();
            if (!(date.equals(""))) {
                txt.setText(date);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setOpaqueCustom(Boolean opaque) {
        this.setOpaque(opaque);
    };

    /**
     * Thay đổi màu chữ
     * 
     * @param color <code>Color</code>: màu cần thay đổi
     */
    public void setForegroundCustom(Color color) {
        txt.setForeground(color);
    };

    /**
     * Thay đổi màu nền
     * 
     * @param color <code>Color</code>: màu cần thay đổi
     */
    public void setBackgroundColor(Color color) {
        txt.setBackground(color);
    }

    /**
     * Thay đổi viền
     * 
     * @param border <code>Border</code>: border cần thay đổi
     */
    public void setBorderCustom(Border border) {
        txt.setBorder(border);
    }

    /**
     * Lấy TextField của DatePicker
     * 
     * @return <code>JTextField</code> Trả về TextField của DatePicker
     */
    public JTextField getTextFieldCustom() {
        return txt;
    }

    /**
     * Thay đổi font chữ
     * 
     * @param font <code>Font</code> font cần thay đổi
     */
    public void setFontCustom(Font font) {
        txt.setFont(font);
    }

    /**
     * Trả về ngày được hiển thị dạng chuỗi
     * 
     * @return <code>String</code>: ngày được hiển thị
     */
    public String getValue() {
        return txt.getText();
    }

    /**
     * Trả về ngày được hiển thị dạng <code>java.sql.Date</code>
     * 
     * @return <code>java.sql.Date</code>: trả bề ngày được hiển thị
     */
    public Date getValueSqlDate() {
        String strDate = txt.getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }

    /**
     * Cập nhật giá trị về ngày hiện tại
     */
    public void setValueToDay() {
        txt.setText(DialogDatePicker.getToDay());
    }

    /**
     * Lấy ngày hiện tại
     * 
     * @return <code>java.sql.Date</code>: ngày hiện tại
     * @throws ParseException
     */
    public Date getValueToDay() {
        String strDate = DialogDatePicker.getToDay();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }

    /**
     * Cập nhật giá trị với giá trị đầu vào là một <code>java.sql.Date</code>
     * 
     * @param date <code>java.sql.Date</code>: ngày cần cập nhật
     */
    public void setValue(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        txt.setText(sdf.format(date));
    }

    /**
     * Cập nhật giá trị với đầu vào là 1 chuỗi (<code>String</code>)
     */
    public void setValue(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txt.setText(sdf.format(date));
    }

    /**
     * Lấy ra giá trị ngày dưới dạng <code>java.sql.Date</code>
     * 
     * @return <code>java.sql.Date</code> ngày được trả về
     */
    public Date getFullDate() {
        String strDate = txt.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }

    /**
     * lấy ra ngày được hiển thị
     * 
     * @return <code>int</code>: ngày được trả về
     */
    public int getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    /**
     * lấy ra tháng được hiển thị
     * 
     * @return <code>int</code>: tháng được trả về
     */
    public int getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    /**
     * lấy ra năm được hiển thị
     * 
     * @return <code>int</code>: năm được trả về
     */
    public int getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }
}
