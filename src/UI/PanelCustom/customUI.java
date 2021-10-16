package UI.PanelCustom;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.plaf.basic.BasicComboBoxUI;

import javax.swing.border.*;

public class CustomUI {
    private static CustomUI instance = new CustomUI();
    public static Border BORDER_BOTTOM_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#FCA120"));
    public static Border BORDER_BOTTOM_UN_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#d0e1fd"));
    public static Border BORDER_BOTTOM_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

    public static ImageIcon ADD_ICON = new ImageIcon("img/blueAdd_16.png");
    public static ImageIcon TRASH_ICON = new ImageIcon("img/trash_16.png");
    public static ImageIcon REFRESH_ICON = new ImageIcon("img/refresh_16.png");
    public static ImageIcon ANALYTICS_ICON = new ImageIcon("img/analytics_16.png");
    public static ImageIcon BACK_ICON = new ImageIcon("img/back_16.png");
    public static ImageIcon SEARCH_ICON = new ImageIcon("img/search_512.png");
    public static ImageIcon LOGOUT_ICON = new ImageIcon("img/logout_16.png");
    public static ImageIcon UPDATE_ICON = new ImageIcon("img/update_16.png");
    public static ImageIcon TRANSFER_ICON = new ImageIcon("img/transfer_16.png");
    public static ImageIcon PAYMENT_ICON = new ImageIcon("img/payment_16.png");
    public static ImageIcon ERROR_ICON = new ImageIcon("img/cancel_16.png");
    public static ImageIcon USER_ICON = new ImageIcon("img/user_16.png");
    public static ImageIcon MAN_ICON = new ImageIcon("img/man_512.png");
    public static ImageIcon WOMAN_ICON = new ImageIcon("img/woman_512.png");

    public static CustomUI getInstance() {
        if (instance == null)
            instance = new CustomUI();
        return instance;
    }

    public void setCustomBtn(JButton btn) {
        btn.setBackground(Color.decode("#d0e1fd"));
        btn.setForeground(Color.decode("#1a66e3"));
        btn.setBorder(new LineBorder(Color.BLUE, 1));
    }

    public void setCustomBtnHover(JButton btn) {
        if (btn.isEnabled()) {
            btn.setBackground(Color.decode("#73cdf5"));
            btn.setForeground(Color.WHITE);
            btn.setBorder(new LineBorder(Color.decode("#FCA120"), 1));
        }
    }

    public void setCustomLbTitle(JLabel lb) {
        lb.setFont(new Font("Dialog", Font.BOLD, 24));
        lb.setForeground(Color.decode("#1a66e3"));
    }

    public void setCustomTxt(JTextField txt) {
        txt.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.decode("#FCA120")));
        txt.setBackground(new Color(246, 210, 255, 50));
    }

    public void setCustomTextField(JTextField txt) {
        txt.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 1, Color.decode("#FCA120")));
        txt.setBackground(new Color(246, 210, 255, 50));
    }

    public void setCustomTextFieldFocus(JTextField txt) {
        if (txt.isEditable()) {
            txt.setBorder(BORDER_BOTTOM_FOCUS);
        }
    }

    public void setCustomTextFieldOn(JTextField txt) {
        txt.setFont(new Font("Dialog", Font.PLAIN, 14));
        txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
    }

    public void setCustomTextFieldOff(JTextField txt) {
        txt.setEditable(false);
        txt.setFont(new Font("Dialog", Font.PLAIN, 14));
        txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
        txt.setBackground(Color.decode("#f9f9f9"));
    }

    public void setCustomComboBox(JComboBox<?> cbo) {
        cbo.setUI(new BasicComboBoxUI());
        cbo.setFont(new Font("Dialog", Font.BOLD, 12));
        cbo.setBackground(Color.WHITE);
        cbo.setBorder(BORDER_BOTTOM_UN_FOCUS);
    }

    public JTextField setCustomCBoxField(JComboBox<?> cbo) {
        JTextField boxField = (JTextField) cbo.getEditor().getEditorComponent();
        boxField.setBackground(Color.WHITE);
        boxField.setBorder(BORDER_BOTTOM_UN_FOCUS);
        boxField.setEditable(false);
        return boxField;
    }

    public void setCustomSpinner(JSpinner spin) {
        spin.setFont(new Font("Dialog", Font.PLAIN, 14));
        spin.setBackground(Color.WHITE);
        spin.setBorder(BORDER_BOTTOM_UN_FOCUS);
    }

    public java.util.Date convertSqlDateToUtilDate(Date date) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        return utilDate;
    }

    public String convertSqlDateToUtilDateFormatString(Date date, String format) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        DateFormat df = new SimpleDateFormat(format);
        return df.format(utilDate);
    }

    public String convertSqlTimestampToUtilDateFormatString(Timestamp date, String format) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        DateFormat df = new SimpleDateFormat(format);
        return df.format(utilDate);
    }
}
