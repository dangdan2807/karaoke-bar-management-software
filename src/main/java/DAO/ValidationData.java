package DAO;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import UI.PanelCustom.CustomUI;
import UI.PanelCustom.kDatePicker;

public class ValidationData {
    private static ValidationData instance = new ValidationData();
    private static DecimalFormat df = new DecimalFormat("#,###.##");

    public static ValidationData getInstance() {
        if (instance == null)
            instance = new ValidationData();
        return instance;
    }

    /**
     * Hiển thị popup thông báo của 1 {@code JTextField}
     * 
     * @param jFrame  {@code JFrame} hiển thị popup thông báo
     * @param txt     {@code JTextField} được trỏ đến khi cần thông báo
     * @param type    {@code int} mã dạng thông báo (Nếu 1. là lỗi)
     * @param message {@code String} Tin nhắn được hiển thị
     * @param title   {@code String} Tiêu đề thông báo
     * @param option  {@code int} loại thông báo (icon)
     */
    private void showMessage(JFrame jFrame, JTextField txt, int type, String message, String title, int option) {
        if (type == 1) {
            txt.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
        }
        JOptionPane.showMessageDialog(jFrame, message, title, option);
        txt.requestFocus();
    }

    /**
     * Hiển thị popup thông báo của 1 {@code JTextField}
     * 
     * @param jDialog {@code JDialog} JDialog hiển thị popup thông báo
     * @param txt     {@code JTextField} được trỏ đến khi cần thông báo
     * @param type    {@code int} mã dạng thông báo (Nếu 1. là lỗi)
     * @param message {@code String} Tin nhắn được hiển thị
     * @param title   {@code String} Tiêu đề thông báo
     * @param option  {@code int} loại thông báo (icon)
     */
    private void showMessage(JDialog jDialog, JTextField txt, int type, String message, String title, int option) {
        if (type == 1) {
            txt.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
        }
        JOptionPane.showMessageDialog(jDialog, message, title, option);
        txt.requestFocus();
    }

    /**
     * Hiển thị popup thông báo của 1 {@code JTextField}
     * 
     * @param jPanel  {@code JPanel} hiển thị popup thông báo
     * @param txt     {@code JTextField} được trỏ đến khi cần thông báo
     * @param type    {@code int} mã dạng thông báo (Nếu 1. là lỗi)
     * @param message {@code String} Tin nhắn được hiển thị
     * @param title   {@code String} Tiêu đề thông báo
     * @param option  {@code int} loại thông báo (icon)
     */
    private void showMessage(JPanel jPanel, JTextField txt, int type, String message, String title, int option) {
        if (type == 1) {
            txt.setBorder(CustomUI.BORDER_BOTTOM_ERROR);
        }
        JOptionPane.showMessageDialog(jPanel, message, title, option);
        txt.requestFocus();
    }

    /**
     * Hiển thị popup thông báo
     * 
     * @param jFrame  {@code JFrame} hiển thị popup thông báo
     * @param message {@code String} Tin nhắn được hiển thị
     * @param title   {@code String} Tiêu đề thông báo
     * @param option  {@code int} loại thông báo (icon)
     */
    private void showMessage(JFrame jFrame, String message, String title, int option) {
        JOptionPane.showMessageDialog(jFrame, message, title, option);
    }

    /**
     * Hiển thị popup thông báo
     * 
     * @param jPanel  {@code JPanel} hiển thị popup thông báo
     * @param message {@code String} Tin nhắn được hiển thị
     * @param title   {@code String} Tiêu đề thông báo
     * @param option  {@code int} loại thông báo (icon)
     */
    private void showMessage(JPanel jPanel, String message, String title, int option) {
        JOptionPane.showMessageDialog(jPanel, message, title, option);
    }

    /**
     * Hiển thị popup thông báo
     * 
     * @param jDialog {@code JPanel} hiển thị popup thông báo
     * @param message {@code String} Tin nhắn được hiển thị
     * @param title   {@code String} Tiêu đề thông báo
     * @param option  {@code int} loại thông báo (icon)
     */
    private void showMessage(JDialog jDialog, String message, String title, int option) {
        JOptionPane.showMessageDialog(jDialog, message, title, option);
    }

    /**
     * Xác thực tên có độ dài từ <code>minLength</code> đến <code>maxLength</code>
     * ký tự
     * 
     * @param jFrame    {@code JFrame}: JFrame nhận popup thông báo
     * @param txt       {@code JTextField}: text field nhận thông báo
     * @param name      {@code String}: tên cần hiển thị trong thông báo ví dụ:
     *                  <ul>
     *                  <li>"tên nhân viên" + thông báo</li>
     *                  <li>"tên khách hàng" + thông báo</li>
     *                  <li>"tên dịch vụ" + thông báo</li>
     *                  </ul>
     * @param maxLength {@code int}: độ dài tối đa của tên
     * @param minLength {@code int}: độ dài tối thiểu của tên
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidName(JFrame jFrame, JTextField txt, String name, int maxLength, int minLength) {
        String staffName = txt.getText().trim();
        String message = "";
        boolean result = true;
        if (staffName.length() > maxLength || staffName.length() <= minLength || staffName.equals("")) {
            if (staffName.length() > maxLength)
                message = name + " phải bé hơn " + maxLength + " ký tự";
            else
                message = name + " không được rỗng";
            showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            result = false;
        }
        return result;
    }

    /**
     * Xác thực tên có độ dài từ <code>minLength</code> đến <code>maxLength</code>
     * ký tự
     * 
     * @param jDialog   {@code JDialog} JDialog nhận popup thông báo
     * @param txt       {@code JTextField}: text field nhận thông báo
     * @param name      {@code String} tên cần hiển thị trong thông báo ví dụ
     *                  <ul>
     *                  <li>"tên nhân viên" + thông báo</li>
     *                  <li>"tên khách hàng" + thông báo</li>
     *                  <li>"tên dịch vụ" + thông báo</li>
     *                  </ul>
     * @param maxLength {@code int} độ dài tối đa của tên
     * @param minLength {@code int} độ dài tối thiểu của tên
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidName(JDialog jDialog, JTextField txt, String name, int maxLength, int minLength) {
        String data = txt.getText().trim();
        String message = "";
        boolean result = true;
        if (data.length() > maxLength || data.length() <= minLength || data.equals("")) {
            if (data.length() > maxLength)
                message = name + " phải bé hơn " + maxLength + " ký tự";
            else
                message = name + " không được rỗng";
            showMessage(jDialog, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            result = false;
        }
        return result;
    }

    /**
     * Xác thực tên có độ dài từ <code>minLength</code> đến <code>maxLength</code>
     * ký tự
     * 
     * @param jFrame    {@code JFrame} JFrame nhận popup thông báo
     * @param txt       {@code JTextField}: text field nhận thông báo
     * @param name      {@code String} tên cần hiển thị trong thông báo ví dụ
     *                  <ul>
     *                  <li>"tên nhân viên" + thông báo</li>
     *                  <li>"tên khách hàng" + thông báo</li>
     *                  <li>"tên dịch vụ" + thông báo</li>
     *                  </ul>
     * @param maxLength {@code int} độ dài tối đa của tên
     * @param minLength {@code int} độ dài tối thiểu của tên
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidName(JPanel jPanel, JTextField txt, String name, int maxLength, int minLength) {
        String data = txt.getText().trim();
        String message = "";
        boolean result = true;
        if (data.length() > maxLength || data.length() <= minLength || data.equals("")) {
            if (data.length() > maxLength)
                message = name + " phải bé hơn " + maxLength + " ký tự";
            else
                message = name + " không được rỗng";
            showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            result = false;
        }
        return result;
    }

    /**
     * 
     * @param jFrame       {@code JFrame} JFrame nhận popup thông báo
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
    public boolean ValidNumber(JFrame jFrame, JTextField txt, String name, int less, int greater, int defaultValue) {
        String message = "";
        String salaryStr = txt.getText().trim().replace(",", "");
        if (salaryStr.length() > 0) {
            try {
                int num = Integer.parseInt(salaryStr);
                if (num < less) {
                    message = name + " phải lớn hơn hoặc bằng " + less;
                    showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                } else if (num > greater) {
                    message = name + " phải nhỏ hơn hoặc bằng " + greater;
                    showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                }
            } catch (Exception e) {
                message = name + " phải là một số";
                showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                txt.setText(df.format(defaultValue));
                return false;
            }
        } else {
            message = name + " không được rỗng";
            showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            txt.setText(df.format(defaultValue));
            return false;
        }
        return true;
    }

    /**
     * 
     * @param jPanel       {@code JPanel} JPanel nhận popup thông báo
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
    public boolean ValidNumber(JPanel jPanel, JTextField txt, String name, int less, int greater, int defaultValue) {
        String message = "";
        String salaryStr = txt.getText().trim().replace(",", "");
        if (salaryStr.length() > 0) {
            try {
                int num = Integer.parseInt(salaryStr);
                if (num < less) {
                    message = name + " phải lớn hơn hoặc bằng " + less;
                    showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                } else if (num > greater) {
                    message = name + " phải nhỏ hơn hoặc bằng " + greater;
                    showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                }
            } catch (Exception e) {
                message = name + " phải là một số";
                showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                txt.setText(df.format(defaultValue));
                return false;
            }
        } else {
            message = name + " không được rỗng";
            showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            txt.setText(df.format(defaultValue));
            return false;
        }
        return true;
    }

    /**
     * 
     * @param jFrame       {@code JFrame} JFrame nhận popup thông báo
     * @param txt          {@code JTextField}: text field nhận thông báo
     * @param name         {@code String} tên cần hiển thị trong thông báo ví dụ
     * @param less         {@code double}: giá trị giới hạn dưới
     * @param greater      {@code double}: giá trị giới hạn trên
     * @param defaultValue {@code double}: giá trị giới hạn mặc định khi nhập sai
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidNumber(JFrame jFrame, JTextField txt, String name, double less, double greater,
            double defaultValue) {
        String message = "";
        String salaryStr = txt.getText().trim().replace(",", "");
        if (salaryStr.length() > 0) {
            try {
                double num = Double.parseDouble(salaryStr);
                if (num < less) {
                    message = name + " phải lớn hơn hoặc bằng " + less;
                    showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                } else if (num > greater) {
                    message = name + " phải nhỏ hơn hoặc bằng " + greater;
                    showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                }
            } catch (Exception e) {
                message = name + " phải là một số";
                showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                txt.setText(df.format(defaultValue));
                return false;
            }
        } else {
            message = name + " không được rỗng";
            showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            txt.setText(df.format(defaultValue));
            return false;
        }
        return true;
    }

    /**
     * 
     * @param jPanel       {@code JPanel} JPanel nhận popup thông báo
     * @param txt          {@code JTextField}: text field nhận thông báo
     * @param name         {@code String} tên cần hiển thị trong thông báo ví dụ
     * @param less         {@code double}: giá trị giới hạn dưới
     * @param greater      {@code double}: giá trị giới hạn trên
     * @param defaultValue {@code double}: giá trị giới hạn mặc định khi nhập sai
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidNumber(JPanel jPanel, JTextField txt, String name, double less, double greater,
            double defaultValue) {
        String message = "";
        String salaryStr = txt.getText().trim().replace(",", "");
        if (salaryStr.length() > 0) {
            try {
                double num = Double.parseDouble(salaryStr);
                if (num < less) {
                    message = name + " phải lớn hơn hoặc bằng " + less;
                    showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                } else if (num > greater) {
                    message = name + " phải nhỏ hơn hoặc bằng " + greater;
                    showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                    txt.setText(df.format(defaultValue));
                    return false;
                }
            } catch (Exception e) {
                message = name + " phải là một số";
                showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                txt.setText(df.format(defaultValue));
                return false;
            }
        } else {
            message = name + " không được rỗng";
            showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            txt.setText(df.format(defaultValue));
            return false;
        }
        return true;
    }

    /**
     * Xác thực số điện thoại với độ dài là 10 số và bắt đầu bằng 03, 05, 07, 08, 09
     * 
     * @param jFrame {@code JFrame} JFrame nhận popup thông báo
     * @param txt    {@code JTextField}: text field nhận thông báo
     * @return {@code boolean} {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidPhoneNumber(JFrame jFrame, JTextField txt) {
        String message = "";
        String phoneNumber = txt.getText().trim();
        if (!((phoneNumber.length() > 0 || phoneNumber.length() < 10) && phoneNumber.matches("^0[35789][\\d]{8}$"))) {
            message = "số điện thoại phải là 10 số và bắt đầu bằng 03, 05, 07, 08, 09";
            showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Xác thực số điện thoại với độ dài là 10 số và bắt đầu bằng 03, 05, 07, 08, 09
     * 
     * @param jDialog {@code JDialog} JDialog nhận popup thông báo
     * @param txt     {@code JTextField}: text field nhận thông báo
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidPhoneNumber(JDialog jDialog, JTextField txt) {
        String message = "";
        String phoneNumber = txt.getText().trim();
        if (!((phoneNumber.length() > 0 || phoneNumber.length() < 10) && phoneNumber.matches("^0[35789][\\d]{8}$"))) {
            message = "số điện thoại phải là 10 số và bắt đầu bằng 03, 05, 07, 08, 09";
            showMessage(jDialog, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Xác thực số điện thoại với độ dài là 10 số và bắt đầu bằng 03, 05, 07, 08, 09
     * 
     * @param jPanel {@code JPanel} JPanel nhận popup thông báo
     * @param txt    {@code JTextField}: text field nhận thông báo
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidPhoneNumber(JPanel jPanel, JTextField txt) {
        String message = "";
        String phoneNumber = txt.getText().trim();
        if (!((phoneNumber.length() > 0 || phoneNumber.length() < 10) && phoneNumber.matches("^0[35789][\\d]{8}$"))) {
            message = "số điện thoại phải là 10 số và bắt đầu bằng 03, 05, 07, 08, 09";
            showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Xác thực CMND với độ dài là 9 số hoặc đối với CCCD với độ dài 12 số
     * 
     * @param jFrame {@code JFrame} JFrame nhận popup thông báo
     * @param txt    {@code JTextField}: text field nhận thông báo
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidCmnd(JFrame jFrame, JTextField txt) {
        String message = "";
        String cmnd = txt.getText().trim();
        if (!cmnd.matches("^[\\d]{9}$|^[\\d]{12}$")) {
            message = "CMND phải là số và gồm 9 số hoặc nếu là CCCD phải là số và gồm 12 số";
            showMessage(jFrame, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Xác thực CMND với độ dài là 9 số hoặc đối với CCCD với độ dài 12 số
     * 
     * @param jPanel {@code JPanel} JPanel nhận popup thông báo
     * @param txt    {@code JTextField}: text field nhận thông báo
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidCmnd(JPanel jPanel, JTextField txt) {
        String message = "";
        String cmnd = txt.getText().trim();
        if (!cmnd.matches("^[\\d]{9}$|^[\\d]{12}$")) {
            message = "CMND phải là số và gồm 9 số hoặc nếu là CCCD phải là số và gồm 12 số";
            showMessage(jPanel, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Xác thực CMND với độ dài là 9 số hoặc đối với CCCD với độ dài 12 số
     * 
     * @param jDialog {@code JDialog} JDialog nhận popup thông báo
     * @param txt     {@code JTextField}: text field nhận thông báo
     * @return {@code boolean}: kết quả trả về xác thực
     *         <ul>
     *         <li>Nếu đúng thì trả về {@code true}</li>
     *         <li>Nếu sai thì trả về {@code false}</li>
     *         </ul>
     */
    public boolean ValidCmnd(JDialog jDialog, JTextField txt) {
        String message = "";
        String cmnd = txt.getText().trim();
        if (!cmnd.matches("^[\\d]{9}$|^[\\d]{12}$")) {
            message = "CMND phải là số và gồm 9 số hoặc nếu là CCCD phải là số và gồm 12 số";
            showMessage(jDialog, txt, 1, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Xác thực ngày sinh phải trước ngày hiện tại và tuổi phải lớn hơn tuổi tối
     * thiểu ({@code ageLimit})
     * 
     * @param jFrame     {@code JFrame} JFrame nhận popup thông báo
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
    public boolean ValidBirthDay(JFrame jFrame, kDatePicker datePicker, String name, int ageLimit) {
        String message = "";
        Date birthDay = datePicker.getValueSqlDate();
        Date today = datePicker.getValueToDay();
        long difference = today.getTime() - birthDay.getTime();
        int currentAge = ((int) TimeUnit.MILLISECONDS.toDays(difference)) / 365;
        if (birthDay.after(today)) {
            message = "Ngày sinh phải trước ngày hiện tại";
            if (ageLimit != -1)
                message += " và " + name + " phải đủ " + ageLimit + " tuổi";
            showMessage(jFrame, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ageLimit != -1) {
            if (currentAge < ageLimit) {
                message = name + " phải đủ " + ageLimit + " tuổi";
                showMessage(jFrame, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Xác thực ngày sinh phải trước ngày hiện tại và tuổi phải lớn hơn tuổi tối
     * thiểu ({@code ageLimit})
     * 
     * @param jPanel     {@code JPanel} JPanel nhận popup thông báo
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
    public boolean ValidBirthDay(JPanel jPanel, kDatePicker datePicker, String name, int ageLimit) {
        String message = "";
        Date birthDay = datePicker.getValueSqlDate();
        Date today = datePicker.getValueToDay();
        long difference = today.getTime() - birthDay.getTime();
        int currentAge = ((int) TimeUnit.MILLISECONDS.toDays(difference)) / 365;
        if (birthDay.after(today)) {
            message = "Ngày sinh phải trước ngày hiện tại";
            if (ageLimit != -1)
                message += " và " + name + " phải đủ " + ageLimit + " tuổi";
            showMessage(jPanel, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ageLimit != -1) {
            if (currentAge < ageLimit) {
                message = name + " phải đủ " + ageLimit + " tuổi";
                showMessage(jPanel, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Xác thực ngày sinh phải trước ngày hiện tại và tuổi phải lớn hơn tuổi tối
     * thiểu ({@code ageLimit})
     * 
     * @param jDialog    {@code JDialog} JDialog nhận popup thông báo
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
    public boolean ValidBirthDay(JDialog jDialog, kDatePicker datePicker, String name, int ageLimit) {
        String message = "";
        Date birthDay = datePicker.getValueSqlDate();
        Date today = datePicker.getValueToDay();
        long difference = today.getTime() - birthDay.getTime();
        int currentAge = ((int) TimeUnit.MILLISECONDS.toDays(difference)) / 365;
        if (birthDay.after(today)) {
            message = "Ngày sinh phải trước ngày hiện tại";
            if (ageLimit > 0)
                message += " và " + name + " phải đủ " + ageLimit + " tuổi";
            showMessage(jDialog, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ageLimit > 0) {
            if (currentAge < ageLimit) {
                message = name + " phải đủ " + ageLimit + " tuổi";
                showMessage(jDialog, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
