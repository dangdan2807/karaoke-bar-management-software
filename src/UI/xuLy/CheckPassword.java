package UI.xuLy;

import javax.swing.JButton;
// import javax.swing.JFrame;
import javax.swing.JOptionPane;

import UI.fDieuHuong;

public class CheckPassword extends Thread {
    private String password;
    private fDieuHuong frame;
    private JButton btn1, btn2;

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
