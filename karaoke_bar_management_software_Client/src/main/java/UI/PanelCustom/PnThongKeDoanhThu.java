package UI.PanelCustom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class PnThongKeDoanhThu extends JFrame {
	private ImageIcon bg = new ImageIcon(
			CustomUI.BACKGROUND.getImage().getScaledInstance(1270, 630, Image.SCALE_SMOOTH));
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	private ImageIcon searchIcon = CustomUI.SEARCH_ICON;
	private ImageIcon dolaIcon = new ImageIcon(
			CustomUI.DOLLA_ICON.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	private MyButton btnBack;
	private kDatePicker dpToDate;
	private JLabel lblToDate;
	private kDatePicker dpFromDate;
	private JLabel lblFromDate;
	private DefaultTableModel modelTableBillInfo;
	private JTable tblTableBillInfo;
	private JLabel lblSearch;
	private JComboBox<String> cboSearch;
	private JTextField txtBFieldSearch;
	private MyButton btnSearch;
	private JTextField txtTotalBill;
	private JTextField txtTurnOver;

	public PnThongKeDoanhThu() {
		getContentPane().setLayout(null);
		setSize(1270, 630);
		//
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pnlMain = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				Image bgMain = bg.getImage();
				g2.drawImage(bgMain, 0, 0, null);
				setFont(new Font("Dialog", Font.BOLD, 24));
				g2.setColor(Color.decode("#9B17EB"));
				g2.drawRoundRect(10, 50, 1235, 530, 20, 20);
				g2.drawRoundRect(9, 49, 1235, 530, 20, 20);
			}
		};
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 1270, 630);
		getContentPane().add(pnlMain);

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
		CustomUI.getInstance().setCustomComboBox(cboSearch);
		cboSearch.setToolTipText("Chọn loại thông tin cần lọc");
		txtBFieldSearch = CustomUI.getInstance().setCustomCBoxField(cboSearch);
		cboSearch.setBounds(860, 25, 150, 20);
		pnlInfo.add(cboSearch);
		
		btnSearch = new MyButton(150, 35, "Tìm kiếm", gra, searchIcon.getImage(), 50, 19, 10, 5);
		btnSearch.setBounds(1060, 18, 150, 35);
		btnSearch.setToolTipText("Tìm kiếm thông tin nhân viên theo yêu cần đã chọn");
		pnlInfo.add(btnSearch);
		
		JPanel pnlChart = new JPanel();
		pnlChart.setLayout(null);
		pnlChart.setOpaque(false);
		pnlChart.setBounds(10, 101, 1238, 470);
		pnlMain.add(pnlChart);
		
		
		JLabel totalBill_Icon = new JLabel(dolaIcon);
		totalBill_Icon.setBounds(50,10,50,50);
		pnlChart.add(totalBill_Icon);
		
		JLabel totalBill = new JLabel("Tổng số hóa đơn");
		CustomUI.getInstance().setCustomLabel(totalBill);
		totalBill.setBounds(110,10,150,25);
		pnlChart.add(totalBill);
		
		txtTotalBill = new JTextField("49");
		CustomUI.getInstance().setCustomTextFieldBill(txtTotalBill);
		txtTotalBill.setBounds(110,35,150,25);
		pnlChart.add(txtTotalBill);
		
		
		JLabel turnOver_Icon = new JLabel(dolaIcon);
		turnOver_Icon.setBounds(300,10,50,50);
		pnlChart.add(turnOver_Icon);
		
		JLabel turnOver = new JLabel("Doanh thu");
		CustomUI.getInstance().setCustomLabel(turnOver);
		turnOver.setBounds(360,10,150,25);
		pnlChart.add(turnOver);
		
		txtTurnOver = new JTextField("1.122,000,000VNĐ");
		CustomUI.getInstance().setCustomTextFieldBill(txtTurnOver);
		txtTurnOver.setBounds(360,35,150,25);
		pnlChart.add(txtTurnOver);
		
		
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= 7; i++) {
            Random ran = new Random();
            Double total = ran.nextDouble() * Math.pow(10, 8);
            dataset.addValue(total, "VND", String.valueOf(i));
        }

        JFreeChart chart = ChartFactory.createBarChart("BIỂU ĐỒ DOANH THU", "Ngày", "VND", dataset,
                PlotOrientation.VERTICAL, false, false, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(50,70,1138,400);
        pnlChart.add(chartPanel);
		
		
	}

	public static void main(String[] args) {
		new PnThongKeDoanhThu().setVisible(true);
	}
}
