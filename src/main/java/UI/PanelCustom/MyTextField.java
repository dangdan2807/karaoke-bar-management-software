package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MyTextField extends JTextField implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;

	public MyTextField(String text) {
		this.text = text;
		setFont(new Font("Dialog", Font.BOLD, 14));
		setHorizontalAlignment(SwingConstants.RIGHT);
		setForeground(Color.white);
		setBackground(new Color(255, 255, 255, 100));
		initBorder();
		this.addKeyListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		paintIcon(g);
	}

	private void paintIcon(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// g2.setFont(new Font("Dialog", Font.BOLD, 14));
		g2.drawString("/ " + text, getWidth() - 45, getHeight() / 2 + 6);
	}

	private void initBorder() {
		int left = 5;
		int right = 50;
		// 5 is default
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, left, 0, right));
	}

	public void setTextMyTextField(String t) {
		this.text = t;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// int key = e.getKeyCode();
		// int length = text.length();
		// System.out.println(length);
		// if (key >= 48 && key <= 57 || key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
		// 	this.setEditable(true);
		// 	this.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		// 	if (length == 10 && key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_DELETE) {
		// 		this.setEditable(false);
		// 	}
		// } else {
		// 	this.setEditable(false);
		// }
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}