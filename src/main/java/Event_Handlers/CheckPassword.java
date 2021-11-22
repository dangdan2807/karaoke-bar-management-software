package Event_Handlers;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import UI.fDieuHuong;

/**
 * {@code CheckPassword} là 1 {@code Thread} dùng để kiểm tra mật khẩu khi đăng
 * nhập có phải và mặc định hay không và sẽ xuất thông báo yêu cầu đổi mật khẩu
 * mới
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 05/10/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm giải thích lớp javadoc
 */
public class CheckPassword extends Thread {
    private String password;
    private fDieuHuong frame;
    private JButton btnFirst, btnSecond;

    /**
     * Constructor {@code CheckPassword}
     * <p>
     * Khi mật khẩu được truyền vào giống mật khẩu mặc định sẽ xuất hiện thông báo
     * yêu cầu đổi mật khẩu và sẽ vô hiệu xóa 2 nút quản trị và quá lý đặt phòng
     * </p>
     * 
     * @param password  {@code String}: mật khẩu cần kiểm tra
     * @param btnFirst  {@code JButton}: nhận sự kiện
     * @param btnSecond {@code JButton}: nhận sự kiện
     * @param frame     {@code fDieuHuong}: nhận sự kiện
     */
    public CheckPassword(String password, JButton btnFirst, JButton btnSecond, fDieuHuong frame) {
        this.password = password;
        this.btnFirst = btnFirst;
        this.btnSecond = btnSecond;
        this.frame = frame;
    }

    @Override
    public void run() {
        if (password.equalsIgnoreCase("123456")) {
            JOptionPane.showMessageDialog(frame,
                    "<html><p>Bạn đang dùng mật khẩu mặc đinh nên sẽ bị khóa các tính năng khác.<p> Hãy vào phần thông tin cá nhân để đổi mật khẩu và tiếp tục sử dụng hệ thống</html> ",
                    "Cảnh báo", JOptionPane.OK_OPTION);
            btnFirst.setEnabled(false);
            btnSecond.setEnabled(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
