package UI.PanelCustom;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.border.*;

public class CustomUI {
    private static CustomUI instance = new CustomUI();
    public static Border BORDER_BOTTOM_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#1a66e3"));
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

    public void setCustomTextFieldFocus(JTextField txt) {
        if (txt.isEditable()) {
            txt.setBorder(BORDER_BOTTOM_FOCUS);
        }
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
