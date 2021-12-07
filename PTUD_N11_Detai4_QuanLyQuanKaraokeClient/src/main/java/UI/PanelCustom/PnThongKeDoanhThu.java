package UI.PanelCustom;

import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Naming;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import DAO.HoaDonDAO;
import entity.NhanVien;

public class PnThongKeDoanhThu extends JFrame implements ActionListener, MouseListener, ItemListener {
	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon dollarIcon = new ImageIcon(
			CustomUI.DOLLAR_ICON.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));

	private MyButton btnBack, btnSearch;
	private kDatePicker dpToDate, dpFromDate;
	private JLabel lblToDate, lblFromDate, lblSearch;
	private JComboBox<String> cboSearch;
	private JTextField txtBFieldSearch, txtTotalBill, txtTotalPrice;
	private ChartPanel chartPanel;

	private DecimalFormat df = new DecimalFormat("#,###.##");
	private NhanVien staffLogin = null;
	private final String YEAR = "yyyy";
	private final String MONTH = "MM-yyyy";
	private final String DAY = "dd-MM-yyyy";
	private SecurityManager securityManager = System.getSecurityManager();

	public PnThongKeDoanhThu(NhanVien staff) {
		this.staffLogin = staff;
		if (securityManager == null) {
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
		}

		this.setLayout(null);
		setSize(1270, 630);
		this.setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnlMain = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				Image bgMain = bg.getImage();
				g2.drawImage(bgMain, 0, 0, null);
				setFont(new Font("Dialog", Font.BOLD, 24));
				g2.setColor(Color.decode("#9B17EB"));
				g2.drawRoundRect(10, 50, 1235, 520, 20, 20);
				g2.drawRoundRect(9, 49, 1235, 520, 20, 20);
			}
		};
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 1270, 630);
		this.add(pnlMain);

		JPanel pnlTitle = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(255, 255, 255));
			}
		};

		pnlTitle.setBounds(0, 0, 1270, 50);
		pnlTitle.setOpaque(false);
		pnlTitle.setLayout(null);

		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 33, 19, 12, 5);
		btnBack.setBounds(1150, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlTitle.add(btnBack);
		pnlMain.add(pnlTitle);

		JLabel lbTitle = new JLabel("THỐNG KÊ DOANH THU");
		lbTitle.setFont(new Font("Dialog", Font.BOLD, 24));
		lbTitle.setForeground(Color.WHITE);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(0, 0, 1250, 45);
		pnlTitle.add(lbTitle);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(10, 41, 1238, 60);
		pnlMain.add(pnlInfo);

		dpToDate = new kDatePicker(180, 20);
		dpToDate.setBackgroundColor(new Color(255, 255, 255, 50));
		dpToDate.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dpToDate.setTextColor(Color.white);
		dpToDate.setTextFont(new Font("Dialog", Font.PLAIN, 14));
		dpToDate.setOpaqueCustom(false);
		dpToDate.setToolTipTextCustom("Ngày kết thúc thống kê");
		dpToDate.setActive(false);
		dpToDate.setBounds(495, 24, 180, 20);
		pnlInfo.add(dpToDate);

		lblToDate = new JLabel("Đến ngày:");
		CustomUI.getInstance().setCustomLabel(lblToDate);
		lblToDate.setBounds(390, 24, 105, 20);
		pnlInfo.add(lblToDate);

		dpFromDate = new kDatePicker(180, 20);
		dpFromDate.setBackgroundColor(new Color(255, 255, 255, 50));
		dpFromDate.setBorderCustom(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		dpFromDate.setTextColor(Color.white);
		dpFromDate.setTextFont(new Font("Dialog", Font.PLAIN, 14));
		dpFromDate.setOpaqueCustom(false);
		dpFromDate.setBounds(125, 26, 180, 20);
		dpFromDate.setToolTipTextCustom("Ngày bắt đầu thống kê");
		dpFromDate.setActive(false);
		pnlInfo.add(dpFromDate);

		lblFromDate = new JLabel("Từ ngày:");
		CustomUI.getInstance().setCustomLabel(lblFromDate);
		lblFromDate.setBounds(20, 26, 105, 20);
		pnlInfo.add(lblFromDate);

		lblSearch = new JLabel("Lọc theo:");
		CustomUI.getInstance().setCustomLabel(lblSearch);
		lblSearch.setBounds(765, 25, 100, 20);
		pnlInfo.add(lblSearch);

		cboSearch = new JComboBox<String>();
		cboSearch.addItem("7 ngày gần nhất");
		cboSearch.addItem("1 tháng gần nhất");
		cboSearch.addItem("3 tháng gần nhất");
		cboSearch.addItem("6 tháng gần nhất");
		cboSearch.addItem("1 năm gần nhất");
		cboSearch.addItem("Tùy chỉnh");

		CustomUI.getInstance().setCustomComboBox(cboSearch);
		cboSearch.setToolTipText("Chọn loại thông tin cần lọc");
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(860, 25, 150, 20);
		pnlInfo.add(cboSearch);

		btnSearch = new MyButton(150, 35, "Thống kê", gra, searchIcon.getImage(), 50, 19, 10, 5);
		btnSearch.setBounds(1060, 18, 150, 35);
		pnlInfo.add(btnSearch);

		JPanel pnlChart = new JPanel();
		pnlChart.setLayout(null);
		pnlChart.setOpaque(false);
		pnlChart.setBounds(10, 101, 1238, 470);
		pnlMain.add(pnlChart);

		JLabel totalBillIcon = new JLabel(dollarIcon);
		totalBillIcon.setBounds(50, 10, 50, 50);
		pnlChart.add(totalBillIcon);

		JLabel totalBill = new JLabel("Tổng số hóa đơn");
		CustomUI.getInstance().setCustomLabel(totalBill);
		totalBill.setBounds(110, 10, 150, 25);
		pnlChart.add(totalBill);

		txtTotalBill = new JTextField("0");
		CustomUI.getInstance().setCustomTextFieldBill(txtTotalBill);
		txtTotalBill.setBounds(110, 35, 150, 25);
		pnlChart.add(txtTotalBill);

		JLabel turnOver_Icon = new JLabel(dollarIcon);
		turnOver_Icon.setBounds(300, 10, 50, 50);
		pnlChart.add(turnOver_Icon);

		JLabel turnOver = new JLabel("Doanh thu");
		CustomUI.getInstance().setCustomLabel(turnOver);
		turnOver.setBounds(360, 10, 150, 25);
		pnlChart.add(turnOver);

		txtTotalPrice = new JTextField("0 VNĐ");
		CustomUI.getInstance().setCustomTextFieldBill(txtTotalPrice);
		txtTotalPrice.setBounds(360, 35, 150, 25);
		pnlChart.add(txtTotalPrice);

		JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU", "Ngày", "VND", null,
				PlotOrientation.VERTICAL, false, false, false);
		chart.getPlot().setBackgroundPaint(Color.WHITE);

		chartPanel = new ChartPanel(chart);
		chartPanel.setBounds(50, 70, 1138, 390);
		pnlChart.add(chartPanel);

		btnSearch.addActionListener(this);

		txtBFieldSearch.addMouseListener(this);

		cboSearch.addItemListener(this);

		allLoaded();
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			NhanVien staff = new NhanVien("NV00000001");
			new PnThongKeDoanhThu(staff).setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnSearch)) {
			statistical();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();
		if (o.equals(cboSearch)) {
			String search = cboSearch.getSelectedItem().toString();
			switch (search) {
				case "7 ngày gần nhất":
					dpFromDate.setActive(false);
					dpToDate.setActive(false);
					dpFromDate.setDatesFromToday(Calendar.WEEK_OF_MONTH, -1);
					dpToDate.setValueToDay();
					statistical();
					break;
				case "1 tháng gần nhất":
					dpFromDate.setActive(false);
					dpToDate.setActive(false);
					dpFromDate.setDatesFromToday(Calendar.MONTH, -1);
					dpToDate.setValueToDay();
					statistical();
					break;
				case "3 tháng gần nhất":
					dpFromDate.setActive(false);
					dpToDate.setActive(false);
					dpFromDate.setDatesFromToday(Calendar.MONTH, -2);
					dpToDate.setValueToDay();
					statistical();
					break;
				case "6 tháng gần nhất":
					dpFromDate.setActive(false);
					dpToDate.setActive(false);
					dpFromDate.setDatesFromToday(Calendar.MONTH, -5);
					dpToDate.setValueToDay();
					statistical();
					break;
				case "1 năm gần nhất":
					dpFromDate.setActive(false);
					dpToDate.setActive(false);
					dpFromDate.setDatesFromToday(Calendar.MONTH, -11);
					dpToDate.setValueToDay();
					statistical();
					break;
				case "Tùy chỉnh":
					dpFromDate.setActive(true);
					dpToDate.setActive(true);
					break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.showPopup();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_FOCUS);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(txtBFieldSearch)) {
			cboSearch.setBorder(CustomUI.BORDER_BOTTOM_UN_FOCUS);
		}
	}

	/**
	 * Chạy tất cả các hàm khi bắt đầu chạy form
	 */
	private void allLoaded() {
		dpFromDate.setDatesFromToday(Calendar.WEEK_OF_MONTH, -1);
		dpToDate.setValueToDay();
		statistical();
	}

	public void showStatistical(Date fromDate, Date toDate, int dayOfMonth, int dayOfYear, String format,
			ArrayList<Object[]> totalPriceList) {
		chartPanel.removeAll();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(toDate);
		int day2 = calendar.get(Calendar.DAY_OF_MONTH);

		long difference = toDate.getTime() - fromDate.getTime();
		int times = (int) TimeUnit.MILLISECONDS.toDays(difference);

		if (times > dayOfMonth) {
			calendar.setTime(fromDate);
			day1 = calendar.get(Calendar.MONTH) + 1;
			calendar.setTime(toDate);
			day2 = calendar.get(Calendar.MONTH) + 1;
			times = (day2 - day1) < 0 ? 1 : day2 - day1;
		} else if (times > dayOfYear) {
			calendar.setTime(fromDate);
			day1 = calendar.get(Calendar.YEAR);
			calendar.setTime(toDate);
			day2 = calendar.get(Calendar.YEAR);
			times = day2 - day1;
		}

		SimpleDateFormat sdfFullDate = new SimpleDateFormat("dd/MM/yyyy");

		String title = "";
		if (times == 1) {
			title = "NGÀY " + sdfFullDate.format(fromDate);
		} else {
			title = "TỪ NGÀY " + sdfFullDate.format(fromDate) + " ĐẾN NGÀY " + sdfFullDate.format(toDate);
		}

		String timeUnit = "Ngày";
		if (format.equals("mm")) {
			timeUnit = "Tháng";
		} else if (format.equals("yyyy")) {
			timeUnit = "Năm";
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int size = totalPriceList.size();
		Double total = 0.0;

		calendar.setTime(fromDate);
		int oldMonth = calendar.get(Calendar.MONTH) + 1;
		for (int i = 0; i <= times; i++) {
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			Double totalPrice = 0.0;

			if (size > i && size != 0) {
				totalPrice = (Double) totalPriceList.get(i)[1];
				if (totalPrice == null) {
					totalPrice = 0.0;
				}
				total += totalPrice;
			}

			String timeStr = "";
			if (format.equals("mm")) {
				timeStr = month < 10 ? "0" + month : month + "";
				dataset.addValue(totalPrice, "VND", timeStr);
				calendar.add(Calendar.MONTH, 1);
			} else if (format.equals("dd")) {
				timeStr = day < 10 ? "0" + day : day + "";
				if (oldMonth != month)
					timeStr = +month < 10 ? "0" + month : month + "";
				dataset.addValue(totalPrice, "VND", timeStr);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			} else {
				timeStr = year + "";
				dataset.addValue(totalPrice, "VND", timeStr);
				calendar.add(Calendar.YEAR, 1);
			}
		}
		txtTotalPrice.setText(df.format(total));

		JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU " + title, timeUnit, "VND", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		chart.getPlot().setBackgroundPaint(Color.WHITE);
		chartPanel.setChart(chart);
	}

	/**
	 * Thống kê doanh thu
	 */
	private void statistical() {
		Date fromDate = dpFromDate.getValueSqlDate();
		Date toDate = dpToDate.getValueSqlDate();

		int TotalBill = 0;
		try {
			HoaDonDAO billDAO = (HoaDonDAO) Naming.lookup("rmi://localhost:1099/billDAO");
			TotalBill = billDAO.getTotalLineOfBillList(fromDate, toDate,
					staffLogin.getMaNhanVien());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		txtTotalBill.setText(df.format(TotalBill));

		ArrayList<Object[]> totalPriceList = new ArrayList<>();
		long difference = toDate.getTime() - fromDate.getTime();
		int days = (int) TimeUnit.MILLISECONDS.toDays(difference);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);
		int month = calendar.get(Calendar.MONTH) + 1;
		int dayOfMonth = 30;
		int dayOfYear = 365;
		int year = calendar.get(Calendar.YEAR);
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				dayOfMonth = 31;
				break;
			case 2:
				if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
					dayOfMonth = 29;
					dayOfYear = 366;
				} else {
					dayOfMonth = 28;
				}
				break;
		}
		if (days < 0) {
			String message = "Ngày kết thúc thống kê phải lớn hơn hoặc bằng ngày bắt đầu thống kê";
			JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
		} else if (days >= 0 && days <= dayOfMonth) {
			try {
				HoaDonDAO billDAO = (HoaDonDAO) Naming.lookup("rmi://localhost:1099/billDAO");
				totalPriceList = billDAO.getTotalPriceBillListByDate(fromDate, toDate,
						DAY);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "dd", totalPriceList);
		} else if (days > dayOfMonth && days <= dayOfYear) {
			try {
				HoaDonDAO billDAO = (HoaDonDAO) Naming.lookup("rmi://localhost:1099/billDAO");
				totalPriceList = billDAO.getTotalPriceBillListByDate(fromDate, toDate,
						MONTH);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "mm", totalPriceList);
		} else if (days > dayOfYear) {
			try {
				HoaDonDAO billDAO = (HoaDonDAO) Naming.lookup("rmi://localhost:1099/billDAO");
				totalPriceList = billDAO.getTotalPriceBillListByDate(fromDate, toDate,
						YEAR);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			showStatistical(fromDate, toDate, dayOfMonth, dayOfYear, "yyyy", totalPriceList);
		}
	}
}
