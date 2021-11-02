package Event_Handlers;

import java.awt.event.*;
import javax.swing.JTextField;

import UI.PanelCustom.CustomUI;

public class InputEventHandler {

    public InputEventHandler() {

    }

    /**
     * Giới hạn số lượng ký tự nhập vào và chỉ cho phép nhập số
     * 
     * @param key       {@code int}: mã số của phím được nhấn
     * @param txt       {@code JTextField}: text field nhận sự kiện
     * @param maxLength {@code int}: số lượng ký tự tối đa có thể nhập được
     */
    public void enterOnlyNumbers(int key, JTextField txt, int maxLength) {
        String phoneNumberStr = txt.getText();
        int length = phoneNumberStr.length();
        if (key >= 48 && key <= 57 || key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
            txt.setEditable(true);
            txt.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
            if (length == maxLength && key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_DELETE) {
                txt.setEditable(false);
            }
        } else {
            txt.setEditable(false);
        }
    }

    /**
     * Giới hạn số lượng ký tự nhập vào
     * 
     * @param key       {@code int}: mã số của phím được nhấn
     * @param txt       {@code JTextField}: text field nhận sự kiện
     * @param maxLength {@code int}: số lượng ký tự tối đa có thể nhập được
     */
    public void characterInputLimit(int key, JTextField txt, int maxLength) {
        String phoneNumberStr = txt.getText();
        int length = phoneNumberStr.length();

        if (key != -1 || key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
            txt.setEditable(true);
            txt.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
            if (length >= maxLength && key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_DELETE) {
                txt.setEditable(false);
            }
        } else {
            txt.setEditable(false);
        }
    }
}
