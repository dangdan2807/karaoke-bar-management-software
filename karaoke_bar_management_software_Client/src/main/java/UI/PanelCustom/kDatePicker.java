// Author : Pham Dang Dan - KayJuno
// Date created   : April 23, 2021
// Last update date: Nov 1, 2021

package UI.PanelCustom;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Calendar;
import java.sql.Date;

/**
 * {@code kDatePicker} dùng để tạo 1 {@code JPanel} có thể dùng để chọn ngày
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 23/04/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm giải thích lớp javadoc
 */
public class kDatePicker extends JPanel implements ActionListener, MouseListener {
	private JTextField txt;
    private JButton btn;
    private int widthDefault = 150;
    private int heightDefault = 20;
    private DialogDatePicker f = new DialogDatePicker();
    private String pathImg = "src/main/resources/images/";
    private ImageIcon calenderIcon = new ImageIcon(pathImg + "calender_16.png");
    private Color backgroundColor = Color.decode("#f9f9f9");
    private Border borderBottomFocus = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#FCA120"));
    private Border borderBottomUnFocus = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 161, 32, 100));

    /**
     * Constructor với 2 tham số
     * 
     * @param width  {@code int}: chiều dài được hiển thị
     * @param height {@code int}: chiều cao được hiển thị
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
        txt.setText(DialogDatePicker.getToDay());
        txt.setBackground(backgroundColor);
        txt.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn = new JButton(calenderIcon);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBounds(widthDefault - 30, 0, 30, heightDefault);

        this.add(txt);
        this.add(btn);
        btn.addActionListener(this);
        txt.addMouseListener(this);
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
        Object o = e.getSource();
        if (o.equals(txt)) {
            txt.setBorder(borderBottomFocus);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(txt)) {
            txt.setBorder(borderBottomUnFocus);
        }
    }

    /**
     * Làm mờ date picker
     * 
     * @param opaque {@code boolean}:
     *               <ul>
     *               <li>{@code true} (giá trị mặc đinh) không làm mờ</li>
     *               <li>{@code false} làm mờ</li>
     *               </ul>
     */
    public void setOpaqueCustom(Boolean opaque) {
        this.setOpaque(opaque);
    };

    /**
     * Thêm tooltip vào khi hover chuột lên date Date Picker
     * 
     * @param text {@code String}: nội dung cần hiển thị
     */
    public void setToolTipTextCustom(String text) {
        txt.setToolTipText(text);
    }

    /**
     * Thay đổi màu chữ
     * 
     * @param color {@code Color}: màu cần thay đổi
     */
    public void setTextColor(Color color) {
        txt.setForeground(color);
    };

    /**
     * Thay đổi màu nền
     * 
     * @param color {@code Color}: màu cần thay đổi
     */
    public void setBackgroundColor(Color color) {
        txt.setBackground(color);
    }

    /**
     * Thay đổi viền
     * 
     * @param border {@code Border}: border cần thay đổi
     */
    public void setBorderCustom(Border border) {
        txt.setBorder(border);
    }

    /**
     * Lấy {@code JTextField} của DatePicker
     * 
     * @return {@code JTextField}: Trả về {@code JTextField} của DatePicker
     */
    public JTextField getTextFieldCustom() {
        return txt;
    }

    /**
     * Thay đổi font chữ
     * 
     * @param font {@code Font}: font cần thay đổi
     */
    public void setTextFont(Font font) {
        txt.setFont(font);
    }

    /**
     * Trả về ngày được hiển thị dạng chuỗi
     * 
     * @return {@code String}: ngày được hiển thị
     */
    public String getValueString() {
        return txt.getText();
    }

    /**
     * Trả về ngày được hiển thị dạng {@code java.sql.Date}
     * 
     * @return {@code java.sql.Date}: trả bề ngày được hiển thị
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
     * Trả về ngày được hiển thị dạng {@code java.sql.Date}
     * 
     * @return {@code java.sql.Date}: trả bề ngày được hiển thị
     */
    public java.util.Date getValueUtilDate() {
        String strDate = txt.getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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
     * @return {@code java.sql.Date}: ngày hiện tại
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
     * Lấy ra ngày kế tiếp của ngày được chọn
     * 
     * @return {@code java.sql.Date}: ngày tiếp theo
     */
    public Date getNextDay() {
        String strDate = txt.getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        Calendar cal = Calendar.getInstance();
        try {
            date = sdf.parse(strDate);
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            date = cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }

    /**
     * Lấy ra ngày kề trước của ngày được chọn
     * 
     * @return {@code java.sql.Date}: ngày kề trước
     */
    public Date getYesterday() {
        String strDate = txt.getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        Calendar cal = Calendar.getInstance();
        try {
            date = sdf.parse(strDate);
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            date = cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }

    /**
     * Cập nhật giá trị với giá trị đầu vào là một {@code java.sql.Date}
     * 
     * @param date {@code java.sql.Date}: ngày cần cập nhật
     */
    public void setValue(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = sdf.format(date);
        txt.setText(dateStr);
    }

    /**
     * Cập nhật giá trị với đầu vào là 1 chuỗi ({@code String})
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
     * lấy ra ngày được hiển thị
     * 
     * @return {@code int}: ngày được trả về
     */
    public int getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    /**
     * lấy ra tháng được hiển thị
     * 
     * @return {@code int}: tháng được trả về
     */
    public int getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    /**
     * lấy ra năm được hiển thị
     * 
     * @return {@code int}: năm được trả về
     */
    public int getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }
}
