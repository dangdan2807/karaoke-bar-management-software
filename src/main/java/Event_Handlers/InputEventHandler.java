package Event_Handlers;

import java.awt.event.*;
import javax.swing.JTextField;

/**
 * Lớp này sử dụng để kiểm tra dữ liệu nhập vào của người dùng và giới hạn số
 * lượng nhập vào
 */
public class InputEventHandler {

    /**
     * Constructor mặc định không tham số
     */
    public InputEventHandler() {

    }

    /**
     * Giới hạn số lượng ký tự nhập vào và chỉ cho phép nhập số
     * 
     * @param key       {@code int}: mã số của phím được nhấn
     * @param txt       {@code JTextField}: text field nhận sự kiện
     * @param maxLength {@code int}: số lượng ký tự tối đa có thể nhập được
     */
    public void enterOnlyNumbersAndLimitInput(int key, JTextField txt, int maxLength) {
        String numberStr = txt.getText();
        int length = numberStr.length();
        switch (key) {
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 56:
        case 57:
        case KeyEvent.VK_BACK_SPACE:
        case KeyEvent.VK_DELETE:
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_SHIFT:
        case KeyEvent.VK_ALT:
        case KeyEvent.VK_CAPS_LOCK:
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_CONTROL:
            break;

        default:
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    String str = "";
                    str = numberStr.charAt(i) + "";
                    if (!str.matches("[\\d]")) {
                        numberStr = numberStr.substring(0, i);
                        break;
                    }
                }
                length = numberStr.length();
            }
            txt.setText(numberStr);
            break;
        }
        if (length > maxLength) {
            switch (key) {
            case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                break;

            default:
                int count = length < maxLength ? length : maxLength;
                numberStr = numberStr.substring(0, count);
                for (int i = 0; i < count; i++) {
                    String str = numberStr.charAt(i) + "";
                    if (!str.matches("[\\d]")) {
                        numberStr = numberStr.substring(0, i);
                        break;
                    }
                }
                txt.setText(numberStr);
                break;
            }
        }
    }

    /**
     * Không giới hạn số lượng ký tự nhập vào và chỉ cho phép nhập số
     * 
     * @param key       {@code int}: mã số của phím được nhấn
     * @param txt       {@code JTextField}: text field nhận sự kiện
     * @param maxLength {@code int}: số lượng ký tự tối đa có thể nhập được
     */
    public void enterOnlyNumbersAndUnLimitInput(int key, JTextField txt) {
        String numberStr = txt.getText();
        int length = numberStr.length();
        switch (key) {
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 56:
        case 57:
        case KeyEvent.VK_BACK_SPACE:
        case KeyEvent.VK_DELETE:
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_SHIFT:
        case KeyEvent.VK_ALT:
        case KeyEvent.VK_CAPS_LOCK:
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_CONTROL:
            break;

        default:
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    String str = "";
                    str = numberStr.charAt(i) + "";
                    if (!str.matches("[\\d]")) {
                        numberStr = numberStr.substring(0, i);
                        break;
                    }
                }
                length = numberStr.length();
            }
            txt.setText(numberStr);
            break;
        }
        switch (key) {
        case KeyEvent.VK_BACK_SPACE:
        case KeyEvent.VK_DELETE:
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_RIGHT:

            break;

        default:
            numberStr = numberStr.substring(0, length);
            for (int i = 0; i < length; i++) {
                String str = numberStr.charAt(i) + "";
                if (!str.matches("[\\d]")) {
                    numberStr = numberStr.substring(0, i);
                    break;
                }
            }
            txt.setText(numberStr);
            break;
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
        String str = txt.getText();
        int length = str.length();
        if (length > maxLength && key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_DELETE) {
            int count = length < maxLength ? length : maxLength;
            str = str.substring(0, count);
            txt.setText(str);
        }
    }
}
