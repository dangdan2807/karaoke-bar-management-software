package Event_Handlers;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import UI.PanelCustom.CustomUI;
import UI.PanelCustom.kDatePicker;

/**
 * lớp này dùng để xác thực dữ liệu và thông báo lỗi
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh, Đỗ Thị Tường Vi
 * <p>
 * Ngày tạo: 02/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: Thêm mô tả cho lớp và các hàm
 */
public class ValidationData {
    private static ValidationData instance = new ValidationData();
    private static DecimalFormat df = new DecimalFormat("#,###.##");

    public static ValidationData getInstance() {
        if (instance == null)
            instance = new ValidationData();
        return instance;
    }

    /**
     * Hiển thị popup thông báo của 1 {@code JComponent}
     * 
     * @param component {@code Component} component hiển thị popup thông báo:
     *                  ({@code JPane}, {@code JDialog}, {@code JFrame}, ...)
     * @param txt       {@code JComponent} được trỏ đến khi cần thông báo
     * @param type      {@code int} mã dạng thông báo (Nếu 1. là lỗi)
     * @param message   {@code String} Tin nhắn được hiển thị
     * @param title     {@code String} Tiêu đề thông báo
     * @param option    {@code int} loại thông báo (icon)
     */
    private void showMessage(Component component, JComponent txt, int type, String message, String title, int option) {
        if (type == 1) {
            txt.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
        }
        JOptionPane.showMessageDialog(component, message, title, option);
        txt.requestFocus();
    }

    /**
     * Hiển thị popup thông báo
     * 
     * @param component {@code Component} component hiển thị popup thông báo:
     *                  ({@code JPane}, {@code JDialog}, {@code JFrame}, ...)
     * @param message   {@code String} Tin nhắn được hiển thị
     * @param title     {@code String} Tiêu đề thông báo
     * @param option    {@code int} loại thông báo (icon)
     */
    private void showMessage(Component component, String message, String title, int option) {
        JOptionPane.showMessageDialog(component, message, title, option);
    }

    /**
     * Xác thực tên có độ dài từ {@code minLength} đến {@code maxLength} ký tự
     * 
     * @param component   {@code Component} component hiển thị popup thông báo:
     *                    ({@code JPane}, {@code JDialog}, {@code JFrame}, ...)
     * @param txt         {@code JTextField}: text field nhận thông báo
     * @param nameMessage {@code String}: tên cần hiển thị trong thông báo ví dụ:
     *                    <ul>
     *                    <li>"tên nhân viên" + thông báo</li>
     *                    <li>"tên khách hàng" + thông báo</li>
     *                    <li>"tên dịch vụ" + thông báo</li>
     *                    </ul>
     * @param maxLength   {@code int}: độ dài tối đa của tên
     * @param minLength   {@code int}: độ dài tối thiểu của tên
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidName(Component component, JTextField txt, String nameMessage, int maxLength, int minLength) {
        String name = txt.getText().trim();
        String message = "";
        boolean result = true;
        if (name.length() > maxLength || name.length() < minLength || name.equals("")) {
            if (name.length() > maxLength)
                message = nameMessage + " phải bé hơn " + maxLength + " ký tự";
            else if (name.length() <= minLength)
                message = nameMessage + " phải lớn hơn " + minLength + " ký tự";
            else if (name.equals("") || name.length() <= 0)
                message = nameMessage + " không được rỗng";
            showMessage(component, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            result = false;
        }
        return result;
    }

    /**
     * 
     * @param component    {@code Component} component hiển thị popup thông báo:
     *                     ({@code JPane}, {@code JDialog}, {@code JFrame}, ...)
     * @param txt          {@code JTextField}: text field nhận thông báo
     * @param name         {@code String} tên cần hiển thị trong thông báo ví dụ
     * @param less         {@code int}: giá trị giới hạn dưới
     * @param greater      {@code int}: giá trị giới hạn trên
     * @param defaultValue {@code int}: giá trị giới hạn mặc định khi nhập sai
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidNumber(Component component, JTextField txt, String name, int less, int greater,
            int defaultValue) {
        String message = "";
        String salaryStr = txt.getText().trim().replace(",", "");
        if (salaryStr.length() > 0) {
            try {
                int num = Integer.parseInt(salaryStr);
                if (num < less) {
                    message = name + " phải lớn hơn hoặc bằng " + less;
                    showMessage(component, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                } else if (num > greater) {
                    message = name + " phải nhỏ hơn hoặc bằng " + greater;
                    showMessage(component, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                }
            } catch (Exception e) {
                message = name + " phải là một số";
                showMessage(component, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                txt.setText(df.format(defaultValue));
                return false;
            }
        } else {
            message = name + " không được rỗng";
            showMessage(component, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            txt.setText(df.format(defaultValue));
            return false;
        }
        return true;
    }

    /**
     * Xác thực số điện thoại với độ dài là 10 số và bắt đầu bằng 03, 05, 07, 08, 09
     * 
     * @param component {@code Component} component hiển thị popup thông báo:
     *                  ({@code JPane}, {@code JDialog}, {@code JFrame}, ...)
     * @param txt       {@code JTextField}: text field nhận thông báo
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidPhoneNumber(Component component, JTextField txt) {
        String message = "";
        String phoneNumber = txt.getText().trim();
        if (!((phoneNumber.length() > 0 || phoneNumber.length() < 10) && phoneNumber.matches("^0[35789][\\d]{8}$"))) {
            message = "số điện thoại phải là 10 số và bắt đầu bằng 03, 05, 07, 08, 09";
            showMessage(component, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Xác thực CMND với độ dài là 9 số hoặc đối với CCCD với độ dài 12 số
     * 
     * @param component {@code Component} component hiển thị popup thông báo:
     *                  ({@code JPane}, {@code JDialog}, {@code JFrame}, ...)
     * @param txt       {@code JTextField}: text field nhận thông báo
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidCmnd(Component component, JTextField txt) {
        String message = "";
        String cmnd = txt.getText().trim();
        if (!cmnd.matches("^[\\d]{9}$|^[\\d]{12}$")) {
            message = "CMND phải là số và gồm 9 số hoặc nếu là CCCD phải là số và gồm 12 số";
            showMessage(component, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Xác thực ngày sinh phải trước ngày hiện tại và tuổi phải lớn hơn tuổi tối
     * thiểu ({@code ageLimit})
     * 
     * @param component  {@code Component} component hiển thị popup thông báo:
     *                   ({@code JPane}, {@code JDialog}, {@code JFrame}, ...)
     * @param datePicker {@code DatePicker}: DatePicker chọn ngày sinh
     * @param name       {@code String}: tên được hiển thị trên thông báo
     * @param ageLimit   {@code int}: tuổi tối thiểu
     *                   <ul>
     *                   <li>Truyền vào {@code bất kỳ <= 0} nếu bỏ qua</li>
     *                   <li>Truyền vào {@code số bất kỳ} thì áp dụng tuổi đó</li>
     *                   </ul>
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidBirthDay(Component component, kDatePicker datePicker, String name, int ageLimit) {
        String message = "";
        Date birthDay = datePicker.getValueSqlDate();
        Date today = datePicker.getValueToDay();
        long difference = today.getTime() - birthDay.getTime();
        int currentAge = ((int) TimeUnit.MILLISECONDS.toDays(difference)) / 365;
        if (birthDay.after(today)) {
            message = "Ngày sinh phải trước ngày hiện tại";
            if (ageLimit != -1)
                message += " và " + name + " phải đủ " + ageLimit + " tuổi";
            showMessage(component, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ageLimit != -1) {
            if (currentAge < ageLimit) {
                message = name + " phải đủ " + ageLimit + " tuổi";
                showMessage(component, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
