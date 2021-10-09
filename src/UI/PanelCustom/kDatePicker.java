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

public class kDatePicker extends JPanel implements ActionListener, MouseListener {
    private JTextField txt;
    private JButton btn;
    private int widthDefault = 150;
    private int heightDefault = 20;
    DialogDatePicker f = new DialogDatePicker();
    ImageIcon calenderIcon = new ImageIcon("img/calender_16.png");
    private Color backgroundColor = Color.decode("#f9f9f9");

    public kDatePicker() {
        setLayout(null);
        // setSize(200, 200);
        // setResizable(false);
        // setLocationRelativeTo(null);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        createGUI();
    }

    public kDatePicker(int width, int height) {
        setLayout(null);
        setBounds(0, 0, width, height);
        widthDefault = width;
        heightDefault = height;
        createGUI();
    }

    private void createGUI() {
        txt = new JTextField();
        txt.setBounds(0, 0, widthDefault - 30, heightDefault);
        txt.setEditable(false);
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

    public void setBackgroundColor(Color color) {
        txt.setBackground(color);
    }

    public void setBorderCustom(Border border) {
        txt.setBorder(border);
    }

    public void setFontCustom(Font font) {
        txt.setFont(font);
    }

    /**
     * lấy giá trị từ được hiển thị
     * 
     * @return
     */
    public String getValue() {
        return txt.getText();
    }

    /**
     * set giá trị về ngày hiện tại
     */
    public void setValueToDay() {
        txt.setText(DialogDatePicker.getToDay());
    }

    /**
     * set giá trị về ngày hiện tại
     * 
     * @return java.sql.Date ngày hôm nay
     * @throws ParseException
     */
    public Date getValueToDay() throws ParseException {
        String strDate = DialogDatePicker.getToDay();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(strDate);
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }

    /**
     * Cập nhật giá trị với giá trị đầu vào là một java.sql.Date
     * @param date java.sql.Date
     */
    public void setValue(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        txt.setText(sdf.format(date));
    }

    /**
     * Cập nhật giá trị với input String
     */
    public void setValue(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(strDate);
        txt.setText(sdf.format(date));
    }

    /**
     * Lấy ra giá trị ngày dưới dạng java.sql.Date
     */
    public Date getFullDate() throws ParseException {
        String strDate = txt.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(strDate);
        Date sqlDate = new Date(date.getTime());
        return sqlDate;
    }

    /**
     * lấy ra ngày được hiển thị
     *  @return Int 
     */
    public int getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    /**
     * lấy ra tháng được hiển thị
     * @return int
     */
    public int getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }

    /**
     * lấy ra năm được hiển thị
     * @return int
     */
    public int getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String date = sdf.format(txt.getText());
        return Integer.parseInt(date);
    }
}
