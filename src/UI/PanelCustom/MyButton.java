package UI.PanelCustom;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MyButton extends JButton {
	private String Label;
	private Color colorShadow, colorFont;
	private Image image;
	private int xLabel, yLabel, xImage, yImage;
	private boolean over = false;
	private Graphics2D g2;
	private int x, y, w, h;
	private GradientPaint gra;

	private Font font = new Font("Dialog", Font.BOLD, 14);

	private Color colorFontHover = new Color(252, 40, 205);
	private Color colorFontExit = Color.decode("#673ab7");
	private Color colorShadowPress = new Color(0, 0, 0, 0);
	private Color colorShadowExit = new Color(215, 254, 251, 100);

	public MyButton() {
	}

	public MyButton(int w, int h, String label, GradientPaint gra, Image image, int xLabel, int yLabel) {
		super();
		x = 0;
		y = 0;

		this.w = w;
		this.h = h;
		this.Label = label;
		this.gra = gra;
		this.image = image;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.xImage = xLabel - 25;
		this.yImage = yLabel - 13;
		colorFont = colorFontExit;

		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		colorShadow = colorShadowExit;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				colorFont = colorFontHover;
				over = true;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				colorFont = colorFontExit;
				over = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				x = 2;
				y = 3;
				MyButton.this.xLabel = xLabel + 2;
				MyButton.this.yLabel = yLabel + 3;
				MyButton.this.xImage += 2;
				MyButton.this.yImage += +3;
				colorShadow = colorShadowPress;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (over) {
					x = 0;
					y = 0;
					MyButton.this.xLabel = xLabel - 2;
					MyButton.this.yLabel = yLabel - 3;
					MyButton.this.xImage -= 2;
					MyButton.this.yImage -= 3;
					colorShadow = colorShadowExit;
				} else {
					x = 0;
					y = 0;
					MyButton.this.xLabel = xLabel - 2;
					MyButton.this.yLabel = yLabel - 3;
					MyButton.this.xImage -= 2;
					MyButton.this.yImage -= 3;
					colorShadow = colorShadowExit;
				}

			}
		});
	}

	public MyButton(int w, int h, String Label, GradientPaint gra, Image image, int xLabel, int yLabel, int xImage,
			int yImage) {
		super();
		x = 0;
		y = 0;

		this.w = w;
		this.h = h;
		this.Label = Label;
		this.gra = gra;
		this.image = image;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.xImage = xImage;
		this.yImage = yImage;
		colorFont = colorFontExit;
		colorShadow = colorShadowExit;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// MyButton.this.gra = new GradientPaint(0, 0, Color.decode("#fffc00"),
				// getWidth(),0 ,Color.decode("#a1ffce"));
				colorFont = colorFontHover;
				over = true;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				colorFont = colorFontExit;
				over = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				x = 2;
				y = 3;
				MyButton.this.xLabel = xLabel + 2;
				MyButton.this.yLabel = yLabel + 3;
				MyButton.this.xImage = xImage + 2;
				MyButton.this.yImage = yImage + 3;
				colorShadow = colorShadowPress;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (over) {
					x = 0;
					y = 0;
					MyButton.this.xLabel = xLabel;
					MyButton.this.yLabel = yLabel;
					MyButton.this.xImage = xImage;
					MyButton.this.yImage = yImage;
					colorShadow = colorShadowExit;
				} else {
					x = 0;
					y = 0;
					MyButton.this.xLabel = xLabel;
					MyButton.this.yLabel = yLabel;
					MyButton.this.xImage = xImage;
					MyButton.this.yImage = yImage - 3;
					colorShadow = colorShadowExit;
				}

			}
		});
	}

	public MyButton(int w, int h, String Label, GradientPaint gra, Image image, int xLabel, int yLabel, int xImage,
			int yImage, Color colorShadowDefault, Color colorFontDefault) {
		super();
		x = 0;
		y = 0;

		this.w = w;
		this.h = h;
		this.Label = Label;
		this.gra = gra;
		this.image = image;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.xImage = xImage;
		this.yImage = yImage;
		this.colorFont = colorFontDefault;
		this.colorShadow = colorShadowDefault;
		this.colorFontExit = colorFontDefault;
		this.colorShadowExit = colorShadowDefault;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				colorFont = colorFontHover;
				over = true;

			}

			@Override
			public void mouseExited(MouseEvent e) {
				colorFont = colorFontExit;
				over = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				x = 2;
				y = 3;
				MyButton.this.xLabel = xLabel + 2;
				MyButton.this.yLabel = yLabel + 3;
				MyButton.this.xImage = xImage + 2;
				MyButton.this.yImage = yImage + 3;
				colorShadow = colorShadowPress;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (over) {
					x = 0;
					y = 0;
					MyButton.this.xLabel = xLabel;
					MyButton.this.yLabel = yLabel;
					MyButton.this.xImage = xImage;
					MyButton.this.yImage = yImage;
					colorShadow = colorShadowExit;
				} else {
					x = 0;
					y = 0;
					MyButton.this.xLabel = xLabel;
					MyButton.this.yLabel = yLabel;
					MyButton.this.xImage = xImage;
					MyButton.this.yImage = yImage - 3;
					colorShadow = colorShadowExit;
				}

			}
		});
	}

	public void setColorShadowPress(Color color) {
		this.colorShadowPress = color;
	}

	public void setColorShadowExit(Color color) {
		this.colorShadowExit = color;
	}

	public void setColorHover(Color color) {
		this.colorFontHover = color;
	}

	public void setColorExit(Color color) {
		this.colorFontExit = color;
	}

	public void setFontCustom(Font font) {
		this.font = font;
	}

	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(colorShadow);
		g2.fillRoundRect(x + 1, y + 2, w - 2, h - 3, 30, 30);
		g2.setPaint(gra);
		g2.fillRoundRect(x, y, w - 3, h - 6, 30, 30);
		g2.drawImage(image, xImage, yImage, null);
		setFont(font);
		g2.setColor(colorFont);
		g2.drawString(Label, xLabel, yLabel);
		super.paintComponent(g);
	}
}
