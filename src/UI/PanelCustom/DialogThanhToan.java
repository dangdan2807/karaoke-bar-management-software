package UI.PanelCustom;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DialogThanhToan extends JFrame {

	public DialogThanhToan() {
		setTitle("Thanh Toán Hóa Đơn");
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        createUI();
	}
	
	private void createUI() {
		JPanel pnMain = new JPanel();
		getContentPane().add(pnMain);
		pnMain.setLayout(null);
	}
	
	public static void main(String[] args) {
		new DialogThanhToan().setVisible(true);
	}
}
