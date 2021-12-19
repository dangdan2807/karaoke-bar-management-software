package UI.PanelCustom;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.border.*;

/**
 * Lớp này dùng để thiết lập các thuộc tính cho các thành phần trong giao diện
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh, Võ Minh Hiếu
 * <p>
 * Ngày tạo: 01/10/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm giải thích lớp javadoc
 */
public class CustomUI {
	private static CustomUI instance = new CustomUI();
	public static Border BORDER_BOTTOM_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#FCA120"));
	public static Border BORDER_BOTTOM_UN_FOCUS = BorderFactory.createMatteBorder(0, 0, 2, 0,
			new Color(255, 161, 32, 100));
	public static Border BORDER_BOTTOM_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);

	private static String pathImg = "/resources/images/";
	// private static String pathfont = "/resources/fonts/";
	public static String PATH_EXPORT_BILL = System.getProperty("user.dir") + "/src/main/resources/bill/";
	public static final String ADD_ICON = pathImg + "blueAdd_16.png";
	public static final String CHECK_ICON = pathImg + "check.png";
	public static final String TRASH_ICON = pathImg + "trash_16.png";
	public static final String REFRESH_ICON = pathImg + "refresh_16.png";
	public static final String BACK_ICON = pathImg + "back_16.png";
	public static final String SEARCH_ICON = pathImg + "search_16.png";
	public static final String LOGOUT_ICON = pathImg + "logout_16.png";
	public static final String LOGIN_ICON = pathImg + "login_16.png";
	public static final String UPDATE_ICON = pathImg + "update_16.png";
	public static final String TRANSFER_ICON = pathImg + "transfer_16.png";
	public static final String PAYMENT_ICON = pathImg + "payment_16.png";
	public static final String USER_ICON = pathImg + "user_16.png";
	public static final String MAN_ICON = pathImg + "man_512.png";
	public static final String WOMAN_ICON = pathImg + "woman_512.png";
	public static final String BACKGROUND = pathImg + "bgBlue.jpg";
	public static final String BACKGROUND_LOGIN = pathImg + "anhChen_400.png";
	public static final String ROOM_ICON = pathImg + "micro_32.png";
	public static final String PROFILE_ICON = pathImg + "profile_512.png";
	public static final String SELL_ICON = pathImg + "money_512.png";
	public static final String MANAGER_ICON = pathImg + "administration_512.png";
	public static final String UPDATE_PROFILE_ICON = pathImg + "update_profile_16.png";
	public static final String RENT_ROOM_ICON = pathImg + "rent_room_16.png";
	public static final String PDF_ICON = pathImg + "pdf_16.png";
	public static final String EXCEL_ICON = pathImg + "excel_16.png";
	public static final String BILL_ICON = pathImg + "bill_16.png";
	public static final String LOGO_ICON = pathImg + "logo.png";
	public static final String LOGO_APP = pathImg + "login_logo_512.png";
	public static final String NEXT_RIGHT_ICON = pathImg + "nextRight.png";
	public static final String DOUBLE_NEXT_RIGHT_ICON = pathImg + "doubleNextRight.png";
	public static final String NEXT_LEFT_ICON = pathImg + "nextLeft.png";
	public static final String DOUBLE_NEXT_LEFT_ICON = pathImg + "doubleNextLeft.png";
	public static final String DOLLAR_ICON = pathImg + "dollar.png";

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
		lbl.setFont(fontNormal);
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
		txt.setFont(fontNormal);
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
			txt.setFont(fontNormal);
			txt.setForeground(Color.WHITE);
			txt.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		}
	}

	/**
	 * tùy chỉnh nhanh text filed khi có sự kiện unFocus (không bao gồm sự kiện)
	 * 
	 * @param txt {@code JTextField}: text filed cần tùy chỉnh
	 */
	public void setCustomTextFieldUnFocus(JTextField txt) {
		if (txt.isEditable()) {
			txt.setBackground(new Color(246, 210, 255, 50));
			txt.setCaretColor(Color.WHITE);
			txt.setFont(fontNormal);
			txt.setForeground(Color.WHITE);
		}
		txt.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		txt.setBorder(BORDER_BOTTOM_UN_FOCUS);
	}

	/**
	 * tùy chỉnh nhanh text filed không bị vô hiệu hóa (không bao gồm sự kiện)
	 * 
	 * @param txt {@code JTextField}: text filed cần tùy chỉnh
	 */
	public void setCustomTextFieldOn(JTextField txt) {
		txt.setEditable(true);
		txt.setFont(fontNormal);
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
		txt.setFont(fontNormal);
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
		cbo.setFont(fontNormal);
	}

	/**
	 * tùy chỉnh nhanh text filed khi dùng kết hợp với comboBox
	 * 
	 * @param cbo {@code JComboBox}: comboBox cần tùy chỉnh
	 * @return {@code JTextField}: text filed đi kèm
	 */
	public JTextField setCustomCBoxField(JComboBox<?> cbo) {
		JTextField boxField = (JTextField) cbo.getEditor().getEditorComponent();
		boxField.setFont(fontNormal);
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
		spn.setFont(fontNormal);
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
		tbl.setFont(fontNormal);
		tbl.setBackground(new Color(255, 255, 255, 0));
		tbl.setForeground(new Color(255, 255, 255));
		tbl.getTableHeader().setFont(fontBold);
		tbl.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tbl.getTableHeader().setBackground(new Color(255, 255, 255));
	}

	/**
	 * Tạo tên đề cho panel
	 * 
	 * @param pnl   {@code JPanel}: panel cần tạo tiêu đề
	 * @param title {@code String}: nội dung tiêu đề
	 */
	public void setBorderTitlePanelTable(JPanel pnl, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#9B17EB"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.white);
		pnl.setBorder(border);
	}

	/**
	 * tùy chỉnh nhanh {@code JScrollPane} không hỗ trợ cuộn
	 * 
	 * @param tbl {@code JTable}: table nhận JScrollPane tùy chỉnh
	 */
	public JScrollPane setCustomScrollPaneNotScroll(JTable tbl) {
		JScrollPane src = new JScrollPane(tbl, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		src.setOpaque(false);
		src.getViewport().setOpaque(false);
		src.getViewport().setBackground(Color.WHITE);
		return src;
	}

	/**
	 * tùy chỉnh nhanh {@code JScrollPane} có hỗ trợ cuộn
	 * 
	 * @param tbl {@code JTable}: table nhận JScrollPane tùy chỉnh
	 */
	public JScrollPane setCustomScrollPane(JTable tbl) {
		JScrollPane src = new JScrollPane(tbl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		dp.setTextColor(Color.white);
		dp.setOpaqueCustom(false);
		dp.setTextFont(fontNormal);
	}
}
