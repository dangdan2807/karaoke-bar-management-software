package UI.PanelCustom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MyTextField extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstText;
	private String lastText;

	public MyTextField(String text) {
		this.lastText = text;
		setFont(new Font("Dialog", Font.BOLD, 14));
		setHorizontalAlignment(SwingConstants.RIGHT);
		setForeground(Color.white);
		setBackground(new Color(255, 255, 255, 100));
		initBorder();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		paintIcon(g);
		// if (!isFocusOwner()) {
		// g.setColor(new Color(255, 255, 255,150));
		// g.fillRect(0, getHeight()-2, getWidth() - 1, 2);
		// } else {
		// g.setColor(new Color(255, 255, 255,255));
		// g.fillRect(0, getHeight()-2, getWidth() - 1, 2);
		// }
	}

	private void paintIcon(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// g2.setFont(new Font("Dialog", Font.BOLD, 14));
		g2.drawString("/ " + lastText, getWidth() - 35, getHeight() / 2 + 6);
	}

	private void initBorder() {
		int left = 5;
		int right = 40;
		// 5 is default
		setBorder(javax.swing.BorderFactory.createEmptyBorder(0, left, 0, right));
	}

	public String getFirstText() {
		return firstText;
	}

	public void setFirstText(String firstText) {
		this.firstText = firstText;
	}

	public String getLastText() {
		return lastText;
	}

	public void setLastText(String lastText) {
		this.lastText = lastText;
	}

}