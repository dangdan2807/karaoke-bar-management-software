package Event_Handlers;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import UI.fDieuHuong;
/**
 * {@code CheckPassword} là 1 <code>Thread</code> dùng để kiểm tra mật khẩu khi
 * đăng nhập có phải và mặc định hay không và sẽ xuất thông báo yêu cầu đổi mật
 * khẩu mới
 */
public class CheckPassword extends Thread {
    private String password;
    private fDieuHuong frame;
    private JButton btn1, btn2;

    /**
     * Constructor {@code CheckPassword}
     * <p>
     * Khi mật khẩu được truyền vào giống mật khẩu mặc định sẽ xuất hiện thông báo
     * yêu cầu đổi mật khẩu và sẽ vô hiệu xóa 2 nút quản trị và quá lý đặt phòng
     * </p>
     * 
     * @param password {@code String}: mật khẩu cần kiểm tra
     * @param btn1     {@code JButton}: nhận sự kiện
     * @param btn2     {@code JButton}: nhận sự kiện
     * @param frame    {@code fDieuHuong}: nhận sự kiện
     */
    public CheckPassword(String password, JButton btn1, JButton btn2, fDieuHuong frame) {
        this.password = password;
        this.btn1 = btn1;
        this.btn2 = btn2;
        this.frame = frame;
    }

    @Override
    public void run() {
        if (password.equalsIgnoreCase("123456")) {
            JOptionPane.showMessageDialog(frame,
                    "<html><p>Bạn đang dùng mật khẩu mặc đinh nên sẽ bị khóa các tính năng khác.<p> Hãy vào phần thông tin cá nhân để đổi mật khẩu và tiếp tục sử dụng hệ thống</html> ",
                    "Cảnh báo", JOptionPane.OK_OPTION);
            btn1.setEnabled(false);
            btn2.setEnabled(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
