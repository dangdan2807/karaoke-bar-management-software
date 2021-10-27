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

    private static String pathImg = "src/main/java/img/";
    public static ImageIcon ADD_ICON = new ImageIcon(pathImg + "blueAdd_16.png");
    public static ImageIcon TRASH_ICON = new ImageIcon(pathImg + "trash_16.png");
    public static ImageIcon REFRESH_ICON = new ImageIcon(pathImg + "refresh_16.png");
    public static ImageIcon ANALYTICS_ICON = new ImageIcon(pathImg + "analytics_16.png");
    public static ImageIcon BACK_ICON = new ImageIcon(pathImg + "back_16.png");
    public static ImageIcon SEARCH_ICON = new ImageIcon(pathImg + "search_16.png");
    public static ImageIcon LOGOUT_ICON = new ImageIcon(pathImg + "logout_16.png");
    public static ImageIcon LOGIN_ICON = new ImageIcon(pathImg + "login_16.png");
    public static ImageIcon UPDATE_ICON = new ImageIcon(pathImg + "update_16.png");
    public static ImageIcon TRANSFER_ICON = new ImageIcon(pathImg + "transfer_16.png");
    public static ImageIcon PAYMENT_ICON = new ImageIcon(pathImg + "payment_16.png");
    public static ImageIcon ERROR_ICON = new ImageIcon(pathImg + "cancel_16.png");
    public static ImageIcon USER_ICON = new ImageIcon(pathImg + "user_16.png");
    public static ImageIcon USER_ICON_512 = new ImageIcon(pathImg + "user_512.png");
    public static ImageIcon MAN_ICON = new ImageIcon(pathImg + "man_512.png");
    public static ImageIcon WOMAN_ICON = new ImageIcon(pathImg + "woman_512.png");
    public static ImageIcon BACKGROUND = new ImageIcon(pathImg + "bgBlue.jpg");
    public static ImageIcon BACKGROUND_LOGIN = new ImageIcon(pathImg + "anhChen_400.png");
    public static ImageIcon ROOM_ICON = new ImageIcon(pathImg + "micro_32.png");
    public static ImageIcon PROFILE_ICON = new ImageIcon(pathImg + "profile_512.png");
    public static ImageIcon SELL_ICON = new ImageIcon(pathImg + "money_512.png");
    public static ImageIcon MANAGER_ICON = new ImageIcon(pathImg + "administration_512.png");

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
        txt.setCaretColor(Color.WHITE);
    }

    public void setCustomTextField(JTextField txt) {
        txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
        txt.setBackground(new Color(246, 210, 255, 50));
        txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txt.setCaretColor(Color.WHITE);
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
            txt.setCaretColor(Color.WHITE);
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
            txt.setCaretColor(Color.WHITE);
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
        txt.setCaretColor(Color.WHITE);
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
        txt.setCaretColor(Color.WHITE);
    }

    /**
     * tùy chỉnh nhanh comboBox
     * 
     * @param cbo <code>JComboBox</code>: comboBox cần tùy chỉnh
     */
    public void setCustomComboBox(JComboBox<?> cbo) {
        cbo.setUI(new BasicComboBoxUI());
        cbo.setFont(new Font("Dialog", Font.BOLD, 14));
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
        boxField.setFont(new Font("Dialog", Font.PLAIN, 14));
        boxField.setBackground(new Color(246, 210, 255, 50));
        boxField.setForeground(Color.WHITE);
        boxField.setBorder(BORDER_BOTTOM_UN_FOCUS);
        boxField.setEditable(false);
        boxField.setBorder(BorderFactory.createEmptyBorder());
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
		spin.setOpaque(false);
        ((JSpinner.DefaultEditor) spin.getEditor()).getTextField().setCaretColor(Color.WHITE);
        ((JSpinner.DefaultEditor) spin.getEditor()).getTextField().setForeground(Color.WHITE);
		((JSpinner.DefaultEditor) spin.getEditor()).getTextField().setBackground(new Color(246, 210, 255, 50));
    }
}
