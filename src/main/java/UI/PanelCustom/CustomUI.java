package UI.PanelCustom;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

import javax.swing.border.*;

public class CustomUI {
    private static CustomUI instance = new CustomUI();
    public static Border BORDER_BOTTOM_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#FCA120"));
    public static Border BORDER_BOTTOM_UN_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0,
            new Color(255, 161, 32, 100));
    public static Border BORDER_BOTTOM_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

    public static ImageIcon ADD_ICON = new ImageIcon("src/main/java/img/blueAdd_16.png");
    public static ImageIcon TRASH_ICON = new ImageIcon("src/main/java/img/trash_16.png");
    public static ImageIcon REFRESH_ICON = new ImageIcon("src/main/java/img/refresh_16.png");
    public static ImageIcon ANALYTICS_ICON = new ImageIcon("src/main/java/img/analytics_16.png");
    public static ImageIcon BACK_ICON = new ImageIcon("src/main/java/img/back_16.png");
    public static ImageIcon SEARCH_ICON = new ImageIcon("src/main/java/img/search_16.png");
    public static ImageIcon LOGOUT_ICON = new ImageIcon("src/main/java/img/logout_16.png");
    public static ImageIcon UPDATE_ICON = new ImageIcon("src/main/java/img/update_16.png");
    public static ImageIcon TRANSFER_ICON = new ImageIcon("src/main/java/img/transfer_16.png");
    public static ImageIcon PAYMENT_ICON = new ImageIcon("src/main/java/img/payment_16.png");
    public static ImageIcon ERROR_ICON = new ImageIcon("src/main/java/img/cancel_16.png");
    public static ImageIcon USER_ICON = new ImageIcon("src/main/java/img/user_16.png");
    public static ImageIcon MAN_ICON = new ImageIcon("src/main/java/img/man_512.png");
    public static ImageIcon WOMAN_ICON = new ImageIcon("src/main/java/img/woman_512.png");
    public static ImageIcon BACKGROUND = new ImageIcon("src/main/java/img/bgBlue.jpg");

    /**
     * singleton <code>CustomUI</code>
     * 
     * @return <code>CustomUI</code>:
     */
    public static CustomUI getInstance() {
        if (instance == null)
            instance = new CustomUI();
        return instance;
    }

    /**
     * tùy chỉnh nhanh button
     * 
     * @param btn <code>JButton</code>: button cần tùy chỉnh
     */
    public void setCustomBtn(JButton btn) {
        btn.setBackground(Color.decode("#d0e1fd"));
        btn.setForeground(Color.decode("#1a66e3"));
        btn.setBorder(new LineBorder(Color.BLUE, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * tùy chỉnh nhanh button khi có sự kiện được đưa chuột lên button (không bao
     * gồm sự kiện)
     * 
     * @param btn <code>JButton</code>: button cần tùy chỉnh
     */
    public void setCustomBtnHover(JButton btn) {
        if (btn.isEnabled()) {
            btn.setBackground(Color.decode("#73cdf5"));
            btn.setForeground(Color.WHITE);
            btn.setBorder(new LineBorder(Color.decode("#FCA120"), 1));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    /**
     * tùy chỉnh nhanh label
     * 
     * @param lb <code>JLabel</code>: label cần tùy chỉnh
     */
    public void setCustomLbTitle(JLabel lb) {
        lb.setFont(new Font("Dialog", Font.BOLD, 24));
        lb.setForeground(Color.decode("#1a66e3"));
    }

    public void setCustomTxt(JTextField txt) {
        txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
        txt.setBackground(new Color(246, 210, 255, 50));
        txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setCustomTextField(JTextField txt) {
        txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
        txt.setBackground(new Color(246, 210, 255, 50));
        txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * tùy chỉnh nhanh text filed khi có sự kiện focus (không bao gồm sự kiện)
     * 
     * @param txt <code>JTextField</code>: text filed cần tùy chỉnh
     */
    public void setCustomTextFieldFocus(JTextField txt) {
        if (txt.isEditable()) {
            txt.setBorder(BORDER_BOTTOM_FOCUS);
            txt.setBackground(new Color(246, 210, 255, 50));
        }
    }

    /**
     * tùy chỉnh nhanh text filed khi có sự kiện unFocus (không bao gồm sự kiện)
     * 
     * @param txt <code>JTextField</code>: text filed cần tùy chỉnh
     */
    public void setCustomTextFieldUnFocus(JTextField txt) {
        if (txt.isEditable()) {
            txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
            txt.setBackground(new Color(246, 210, 255, 50));
        }
    }

    /**
     * tùy chỉnh nhanh text filed không bị vô hiệu hóa (không bao gồm sự kiện)
     * 
     * @param txt <code>JTextField</code>: text filed cần tùy chỉnh
     */
    public void setCustomTextFieldOn(JTextField txt) {
        txt.setEditable(true);
        txt.setFont(new Font("Dialog", Font.PLAIN, 14));
        txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
        txt.setBackground(new Color(246, 210, 255, 40));
        txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * tùy chỉnh nhanh text filed bị vô hiệu hóa (không bao gồm sự kiện)
     * 
     * @param txt <code>JTextField</code>: text filed cần tùy chỉnh
     */
    public void setCustomTextFieldOff(JTextField txt) {
        txt.setEditable(false);
        txt.setFont(new Font("Dialog", Font.PLAIN, 14));
        txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
        txt.setBackground(new Color(246, 210, 255, 150));
        txt.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * tùy chỉnh nhanh comboBox
     * 
     * @param cbo <code>JComboBox</code>: comboBox cần tùy chỉnh
     */
    public void setCustomComboBox(JComboBox<?> cbo) {
        cbo.setUI(new BasicComboBoxUI());
        cbo.setFont(new Font("Dialog", Font.BOLD, 12));
        cbo.setBackground(Color.WHITE);
        cbo.setBorder(BORDER_BOTTOM_UN_FOCUS);
        cbo.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * tùy chỉnh nhanh text filed khi dùng kết hợp với comboBox
     * 
     * @param cbo <code>JComboBox</code>: comboBox cần tùy chỉnh
     * @return cbo <code>JTextField</code>: text filed đi kèm
     */
    public JTextField setCustomCBoxField(JComboBox<?> cbo) {
        JTextField boxField = (JTextField) cbo.getEditor().getEditorComponent();
        boxField.setBackground(Color.WHITE);
        boxField.setBorder(BORDER_BOTTOM_UN_FOCUS);
        boxField.setEditable(false);
        boxField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boxField;
    }

    /**
     * tùy chỉnh nhanh Spinner
     * 
     * @param spin <code>JSpinner</code>: Spinner cần tùy chỉnh
     */
    public void setCustomSpinner(JSpinner spin) {
        spin.setFont(new Font("Dialog", Font.PLAIN, 14));
        spin.setBackground(Color.WHITE);
        spin.setBorder(BORDER_BOTTOM_UN_FOCUS);
        spin.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
