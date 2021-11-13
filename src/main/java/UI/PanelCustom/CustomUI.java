package UI.PanelCustom;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.border.*;

public class CustomUI {
	private static CustomUI instance = new CustomUI();
	public static Border BORDER_BOTTOM_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#FCA120"));
	public static Border BORDER_BOTTOM_UN_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0,
			new Color(255, 161, 32, 100));
	public static Border BORDER_BOTTOM_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);
	public static Border BORDER_TITLE_TABLE = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.decode("#9B17EB"), 2), "", TitledBorder.LEADING, TitledBorder.TOP,
			new Font("Dialog", Font.BOLD, 14), Color.white);

	private static String pathImg = "src/main/resources/images/";
	public static final ImageIcon ADD_ICON = new ImageIcon(pathImg + "blueAdd_16.png");
	public static final ImageIcon TRASH_ICON = new ImageIcon(pathImg + "trash_16.png");
	public static final ImageIcon REFRESH_ICON = new ImageIcon(pathImg + "refresh_16.png");
	public static final ImageIcon ANALYTICS_ICON = new ImageIcon(pathImg + "analytics_16.png");
	public static final ImageIcon BACK_ICON = new ImageIcon(pathImg + "back_16.png");
	public static final ImageIcon SEARCH_ICON = new ImageIcon(pathImg + "search_16.png");
	public static final ImageIcon LOGOUT_ICON = new ImageIcon(pathImg + "logout_16.png");
	public static final ImageIcon LOGIN_ICON = new ImageIcon(pathImg + "login_16.png");
	public static final ImageIcon UPDATE_ICON = new ImageIcon(pathImg + "update_16.png");
	public static final ImageIcon TRANSFER_ICON = new ImageIcon(pathImg + "transfer_16.png");
	public static final ImageIcon PAYMENT_ICON = new ImageIcon(pathImg + "payment_16.png");
	public static final ImageIcon ERROR_ICON = new ImageIcon(pathImg + "cancel_16.png");
	public static final ImageIcon USER_ICON = new ImageIcon(pathImg + "user_16.png");
	public static final ImageIcon USER_ICON_512 = new ImageIcon(pathImg + "user_512.png");
	public static final ImageIcon MAN_ICON = new ImageIcon(pathImg + "man_512.png");
	public static final ImageIcon WOMAN_ICON = new ImageIcon(pathImg + "woman_512.png");
	public static final ImageIcon BACKGROUND = new ImageIcon(pathImg + "bgBlue.jpg");
	public static final ImageIcon BACKGROUND_LOGIN = new ImageIcon(pathImg + "anhChen_400.png");
	public static final ImageIcon ROOM_ICON = new ImageIcon(pathImg + "micro_32.png");
	public static final ImageIcon PROFILE_ICON = new ImageIcon(pathImg + "profile_512.png");
	public static final ImageIcon SELL_ICON = new ImageIcon(pathImg + "money_512.png");
	public static final ImageIcon MANAGER_ICON = new ImageIcon(pathImg + "administration_512.png");
	public static final ImageIcon UPDATE_PROFILE_ICON = new ImageIcon(pathImg + "update_profile_16.png");
	public static final ImageIcon RENT_ROOM_ICON = new ImageIcon(pathImg + "rent_room_16.png");
	public static final ImageIcon PDF_ICON = new ImageIcon(pathImg + "pdf_16.png");
	public static final ImageIcon EXCEL_ICON = new ImageIcon(pathImg + "excel_16.png");
	public static final ImageIcon LOGO_ICON = new ImageIcon(pathImg + "Logo.png");
	public static final ImageIcon NEXTRIGHT_ICON = new ImageIcon(pathImg + "2.png");
	public static final ImageIcon DOUBLENEXTRIGHT_ICON = new ImageIcon(pathImg + "1.png");
	public static final ImageIcon NEXTLEFT_ICON = new ImageIcon(pathImg + "4.png");
	public static final ImageIcon DOUBLENEXTLEFT_ICON = new ImageIcon(pathImg + "3.png");

	/**
	 * singleton {@code CustomUI}
	 * 
	 * @return {@code CustomUI}:
	 */
	public static CustomUI getInstance() {
		if (instance == null)
			instance = new CustomUI();
		return instance;
	}

	/**
	 * tùy chỉnh nhanh button
	 * 
	 * @param btn {@code JButton}: button cần tùy chỉnh
	 */
	public void setCustomBtn(JButton btn) {
		btn.setBackground(Color.decode("#d0e1fd"));
		btn.setForeground(Color.decode("#1a66e3"));
		btn.setBorder(new LineBorder(Color.BLUE, 1));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * tùy chỉnh nhanh button khi có sự kiện được đưa chuột lên button (không bao
	 * gồm sự kiện)
	 * 
	 * @param btn {@code JButton}: button cần tùy chỉnh
	 */
	public void setCustomBtnHover(JButton btn) {
		if (btn.isEnabled()) {
			btn.setBackground(Color.decode("#73cdf5"));
			btn.setForeground(Color.WHITE);
			btn.setBorder(new LineBorder(Color.decode("#FCA120"), 1));
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	 * tùy chỉnh nhanh label
	 * 
	 * @param lbl {@code JLabel}: label cần tùy chỉnh
	 */
	public void setCustomLabel(JLabel lbl) {
		lbl.setFont(new Font("Dialog", Font.PLAIN, 14));
		lbl.setForeground(Color.WHITE);
	}

	/**
	 * tùy chỉnh nhanh label class HoaDon Bill
	 * 
	 * @param lbl {@code JLabel}: label cần tùy chỉnh
	 */
	public void setCustomLabelBill(JLabel lbl) {
		lbl.setBackground(Color.WHITE);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("Dialog", Font.PLAIN, 16));
	}

	/**
	 * tùy chỉnh nhanh TextField class HoaDon Bill
	 * 
	 * @param lbl {@code JLabel}: label cần tùy chỉnh
	 */
	public void setCustomTextFieldBill(JTextField txt) {
		txt.setEditable(false);
		txt.setForeground(Color.WHITE);
		txt.setBorder(new EmptyBorder(0, 0, 0, 0));
		txt.setOpaque(false);
		txt.setFont(new Font("Dialog", Font.PLAIN, 14));
	}

	/**
	 * tùy chỉnh nhanh text filed khi có sự kiện focus (không bao gồm sự kiện)
	 * 
	 * @param txt {@code JTextField}: text filed cần tùy chỉnh
	 */
	public void setCustomTextFieldFocus(JTextField txt) {
		if (txt.isEditable()) {
			txt.setBorder(BORDER_BOTTOM_FOCUS);
			txt.setBackground(new Color(246, 210, 255, 50));
			txt.setCaretColor(Color.WHITE);
			txt.setFont(new Font("Dialog", Font.PLAIN, 14));
			txt.setForeground(Color.WHITE);
		}
	}

	/**
	 * tùy chỉnh nhanh text filed khi có sự kiện unFocus (không bao gồm sự kiện)
	 * 
	 * @param txt {@code JTextField}: text filed cần tùy chỉnh
	 */
	public void setCustomTextFieldUnFocus(JTextField txt) {
		if (txt.isEditable()) {
			txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
			txt.setBackground(new Color(246, 210, 255, 50));
			txt.setCaretColor(Color.WHITE);
			txt.setFont(new Font("Dialog", Font.PLAIN, 14));
			txt.setForeground(Color.WHITE);
		} else {
			txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
		}
	}

	/**
	 * tùy chỉnh nhanh text filed không bị vô hiệu hóa (không bao gồm sự kiện)
	 * 
	 * @param txt {@code JTextField}: text filed cần tùy chỉnh
	 */
	public void setCustomTextFieldOn(JTextField txt) {
		txt.setEditable(true);
		txt.setFont(new Font("Dialog", Font.PLAIN, 14));
		txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
		txt.setBackground(new Color(246, 210, 255, 40));
		txt.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		txt.setCaretColor(Color.WHITE);
		txt.setForeground(Color.WHITE);
	}

	/**
	 * tùy chỉnh nhanh text filed bị vô hiệu hóa (không bao gồm sự kiện)
	 * 
	 * @param txt {@code JTextField}: text filed cần tùy chỉnh
	 */
	public void setCustomTextFieldOff(JTextField txt) {
		txt.setEditable(false);
		txt.setFont(new Font("Dialog", Font.PLAIN, 14));
		txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
		txt.setBackground(new Color(246, 210, 255, 150));
		txt.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		txt.setCaretColor(Color.WHITE);
		txt.setForeground(Color.WHITE);
	}

	/**
	 * Tùy chỉnh nhanh radio button
	 * 
	 * @param rad {@code JRadioButton}: radio button cần tùy chỉnh
	 */
	public void setCustomRadioButton(JRadioButton rad) {
		rad.setForeground(Color.WHITE);
		rad.setFocusable(false);
		rad.setOpaque(false);
		rad.setBackground(Color.WHITE);
		rad.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * tùy chỉnh nhanh comboBox
	 * 
	 * @param cbo {@code JComboBox}: comboBox cần tùy chỉnh
	 */
	public void setCustomComboBox(JComboBox<?> cbo) {
		cbo.setBorder(BORDER_BOTTOM_UN_FOCUS);
		cbo.setOpaque(false);
		cbo.setEditable(true);
		cbo.setUI(new BasicComboBoxUI());
		cbo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cbo.setFont(new Font("Dialog", Font.PLAIN, 14));
	}

	/**
	 * tùy chỉnh nhanh text filed khi dùng kết hợp với comboBox
	 * 
	 * @param cbo {@code JComboBox}: comboBox cần tùy chỉnh
	 * @return {@code JTextField}: text filed đi kèm
	 */
	public JTextField setCustomCBoxField(JComboBox<?> cbo) {
		JTextField boxField = (JTextField) cbo.getEditor().getEditorComponent();
		boxField.setFont(new Font("Dialog", Font.PLAIN, 14));
		boxField.setBackground(new Color(246, 210, 255, 50));
		boxField.setForeground(Color.WHITE);
		boxField.setBorder(BORDER_BOTTOM_UN_FOCUS);
		boxField.setEditable(false);
		boxField.setBorder(BorderFactory.createEmptyBorder());
		boxField.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return boxField;
	}

	/**
	 * tùy chỉnh nhanh Spinner
	 * 
	 * @param spn {@code JSpinner}: Spinner cần tùy chỉnh
	 */
	public void setCustomSpinner(JSpinner spn) {
		spn.setFont(new Font("Dialog", Font.PLAIN, 14));
		spn.setBackground(Color.WHITE);
		spn.setBorder(BORDER_BOTTOM_UN_FOCUS);
		spn.setOpaque(false);
		spn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		((JSpinner.DefaultEditor) spn.getEditor()).getTextField().setCaretColor(Color.WHITE);
		((JSpinner.DefaultEditor) spn.getEditor()).getTextField().setForeground(Color.WHITE);
		((JSpinner.DefaultEditor) spn.getEditor()).getTextField().setBackground(new Color(246, 210, 255, 50));
		((JSpinner.DefaultEditor) spn.getEditor()).getTextField().setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * tùy chỉnh nhanh table
	 * 
	 * @param btn {@code JTable}: table cần tùy chỉnh
	 */
	public void setCustomTable(JTable tbl) {
		tbl.setFont(new Font("Dialog", Font.PLAIN, 14));
		tbl.setBackground(new Color(255, 255, 255, 0));
		tbl.setForeground(new Color(255, 255, 255));
		tbl.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
		tbl.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tbl.getTableHeader().setBackground(new Color(255, 255, 255));
	}

	/**
	 * tùy chỉnh nhanh {@code JScrollPane}
	 * 
	 * @param tbl {@code JTable}: table nhận JScrollPane tùy chỉnh
	 */
	public JScrollPane setCustomScrollPane(JTable tbl) {
		JScrollPane src = new JScrollPane(tbl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		src.setOpaque(false);
		src.getViewport().setOpaque(false);
		src.getViewport().setBackground(Color.WHITE);
		return src;
	}

	/**
	 * tùy chỉnh nhanh {@code kDatePicker}
	 * 
	 * @param dp: {@code kDatePicker} cần tùy chỉnh
	 */
	public void setCustomKDatePicker(kDatePicker dp) {
		dp.setBackgroundColor(new Color(255, 255, 255, 50));
		dp.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dp.setForegroundCustom(Color.white);
		dp.setOpaqueCustom(false);
		dp.setFontCustom(new Font("Dialog", Font.PLAIN, 14));
	}
}
