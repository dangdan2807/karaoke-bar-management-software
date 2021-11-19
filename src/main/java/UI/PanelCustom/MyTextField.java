package UI.PanelCustom;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MyTextField extends JTextField {
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

	public void setCurrentPage(int pageNumber) {
		this.setText(String.valueOf(pageNumber));
	}

	public void setNumberOfPages(int numberOfPages) {
		this.text = String.valueOf(numberOfPages);
	}

	public int getCurrentPage() {
		String currentPage = this.getText();
		return Integer.parseInt(currentPage.isEmpty() ? "1" : currentPage);
	}

	public int getNumberOfPages() {
		String numberOfPages = this.text;
		return Integer.parseInt(numberOfPages.isEmpty() ? "1" : numberOfPages);
	}

	public void plusOne() {
		String currentPageStr = this.getText();
		int currentPage = Integer.parseInt(currentPageStr.isEmpty() ? "1" : currentPageStr);
		String numberOfPagesStr = this.text;
		int numberOfPages = Integer.parseInt(numberOfPagesStr.isEmpty() ? "1" : numberOfPagesStr);
		if (currentPage > 0 && currentPage < numberOfPages) {
			++currentPage;
			this.setText(String.valueOf(currentPage));
		}
	}

	public void subtractOne() {
		String currentPageStr = this.getText();
		int currentPage = Integer.parseInt(currentPageStr.isEmpty() ? "1" : currentPageStr);
		String numberOfPagesStr = this.text;
		int numberOfPages = Integer.parseInt(numberOfPagesStr.isEmpty() ? "1" : numberOfPagesStr);
		if (currentPage > 1 && currentPage <= numberOfPages) {
			--currentPage;
			this.setText(String.valueOf(currentPage));
		}
	}

	public void toTheLastPage() {
		String numberOfPagesStr = this.text;
		int numberOfPages = Integer.parseInt(numberOfPagesStr.isEmpty() ? "1" : numberOfPagesStr);
		this.setText(String.valueOf(numberOfPages));
	}

	public void toTheFirstPage() {
		this.setText("1");
	}
}