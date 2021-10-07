import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class test1 extends JFrame implements ActionListener {

	public static void main(String[] arr) throws Exception {
		JFrame jf = new JFrame("Chữ chạy");
		JPanel jp = new JPanel();
		jf.setSize(500, 600);
		jp.setSize(500, 600);
		JLabel lb = new JLabel("Chữ Chạy");
		jp.setLayout(null);
		Insets in = jp.getInsets();
		Dimension size = lb.getPreferredSize();
		int x = 0;
		boolean y = false;
		lb.setBounds(jp.getHeight() / 2, x, size.width, size.height);
		jp.add(lb);
		jf.getContentPane().add(jp);
		jf.setVisible(true);
		while (true) {
			if (x == jp.getWidth() - 60 && !y) {
				y = true;
			} else if (x == 0 && y) {
				y = false;
			} else {
				if (y) {
					x--;
				} else {
					x++;
				}
				lb.setBounds(x, jp.getHeight() / 2, size.width, size.height);
				Thread.sleep(10);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}