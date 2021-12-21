package App;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import UI.fDangNhap;

/**
 * Lớp học này là điểm bắt đầu của chương trình phía client.
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 04/10/2021
 * <p>
 * Lần cập nhật cuối: 18/11/2021
 * <p>
 */
public class App {
	
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			fDangNhap login = new fDangNhap();	
			login.setVisible(true);
		});
	}
}
