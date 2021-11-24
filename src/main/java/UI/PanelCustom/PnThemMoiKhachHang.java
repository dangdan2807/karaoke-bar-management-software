package UI.PanelCustom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PnThemMoiKhachHang extends JFrame{
	private ImageIcon logoApp = CustomUI.LOGO_APP;
	private kDatePicker dpBirthDay;
	private JTextField txtCMND;
	private JLabel lblCMND;
	private JLabel lblBirthDay;
	private JLabel lblGender;
	private JTextField txtPhoneNumber;
	private JRadioButton radMale;
	private JRadioButton radFemale;
	private JLabel lblPhone;
	private JTextField txtCustomerName;
	private JTextField txtCustomerID;
	private JLabel lblCustomerID;
	private JLabel lblCustomerName;
	private MyButton btnAddCustomerNew;
	private ImageIcon addIcon = new ImageIcon(
			CustomUI.ADD_ICON.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
	private ImageIcon backIcon = CustomUI.BACK_ICON;
	
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0,
			Color.decode("#FAFFD1"));
	private MyButton btnBack;
	public PnThemMoiKhachHang() {
		setTitle("Chọn khách hàng");
		setSize(900, 300);
		setIconImage(logoApp.getImage());
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel pnlMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
//				g2.drawImage(bg.getImage(), 0, 0, null);
				setFont(new Font("Dialog", Font.BOLD, 24));
//				g2.setColor(Color.decode("#9B17EB"));
//				g2.drawRoundRect(10, 50, 1238, 530, 20, 20);
//				g2.drawRoundRect(9, 49, 1240, 530, 20, 20);
			}
		};
		pnlMain.setBackground(new Color(31,8,138));
		pnlMain.setLayout(null);
		pnlMain.setBounds(0, 0, 900, 500);
		getContentPane().add(pnlMain);
		
		
		btnBack = new MyButton(100, 35, "Quay lại", gra, backIcon.getImage(), 30, 19, 9, 5);
		btnBack.setBounds(770, 10, 100, 35);
		btnBack.setToolTipText("Quay lại giao diện điều hướng");
		pnlMain.add(btnBack);
		
		
		JPanel pnlInfo = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				setFont(new Font("Dialog", Font.BOLD, 24));
				g2.setColor(Color.decode("#9B17EB"));
				g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
//				g2.drawRoundRect(1, 1, getWidth()-1, getHeight()-1, 20, 20);
			}
		};
		pnlInfo.setLayout(null);
		pnlInfo.setOpaque(false);
		pnlInfo.setBounds(13, 65, 860, 170);
		pnlMain.add(pnlInfo);

		dpBirthDay = new kDatePicker(250, 20);
		CustomUI.getInstance().setCustomKDatePicker(dpBirthDay);
		dpBirthDay.setTextFont(new Font("Dialog", Font.PLAIN, 14));
		dpBirthDay.setBounds(145, 85, 250, 20);
		pnlInfo.add(dpBirthDay);

		txtCMND = new JTextField();
		txtCMND.setBounds(145, 50, 250, 20);
		txtCMND.setToolTipText("Nhập CMND gồm có 9 số hoặc CCCD gồm có 12 số");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCMND);
		pnlInfo.add(txtCMND);

		lblCMND = new JLabel("CMND/CCCD:");
		CustomUI.getInstance().setCustomLabel(lblCMND);
		lblCMND.setBounds(20, 50, 105, 20);
		pnlInfo.add(lblCMND);

		lblBirthDay = new JLabel("Ngày sinh:");
		CustomUI.getInstance().setCustomLabel(lblBirthDay);
		lblBirthDay.setBounds(20, 85, 105, 20);
		pnlInfo.add(lblBirthDay);

		lblGender = new JLabel("Giới tính:");
		CustomUI.getInstance().setCustomLabel(lblGender);
		lblGender.setBounds(469, 85, 105, 20);
		pnlInfo.add(lblGender);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(589, 50, 250, 20);
		txtPhoneNumber.setToolTipText("Nhập số điện thoại của bạn gồm 10 số và bắt đầu bằng 03, 05, 07, 08, 09");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtPhoneNumber);
		pnlInfo.add(txtPhoneNumber);

		radMale = new JRadioButton("Nam");
		CustomUI.getInstance().setCustomRadioButton(radMale);
		radMale.setBounds(589, 85, 115, 20);
		radMale.setSelected(true);
		pnlInfo.add(radMale);

		radFemale = new JRadioButton("Nữ");
		CustomUI.getInstance().setCustomRadioButton(radFemale);
		radFemale.setBounds(724, 85, 115, 20);
		pnlInfo.add(radFemale);

		ButtonGroup groupGender = new ButtonGroup();
		groupGender.add(radMale);
		groupGender.add(radFemale);

		lblPhone = new JLabel("Số điện thoại:");
		CustomUI.getInstance().setCustomLabel(lblPhone);
		lblPhone.setBounds(469, 54, 115, 16);
		pnlInfo.add(lblPhone);

		txtCustomerName = new JTextField();
		txtCustomerName.setBounds(589, 15, 250, 20);
		txtCustomerName.setToolTipText("Nhập tên của khách hàng, không quá 100 ký tự");
		CustomUI.getInstance().setCustomTextFieldUnFocus(txtCustomerName);
		pnlInfo.add(txtCustomerName);

		txtCustomerID = new JTextField();
		txtCustomerID.setBounds(145, 15, 250, 20);
		txtCustomerID.setToolTipText("Mã khách hàng");
		CustomUI.getInstance().setCustomTextFieldOff(txtCustomerID);
		pnlInfo.add(txtCustomerID);

		lblCustomerID = new JLabel("Mã khách hàng: ");
		CustomUI.getInstance().setCustomLabel(lblCustomerID);
		lblCustomerID.setBounds(20, 15, 120, 20);
		pnlInfo.add(lblCustomerID);

		lblCustomerName = new JLabel("Tên khách hàng:");
		CustomUI.getInstance().setCustomLabel(lblCustomerName);
		lblCustomerName.setBounds(469, 15, 120, 20);
		pnlInfo.add(lblCustomerName);
		
		btnAddCustomerNew = new MyButton(115, 35, "Thêm mới", gra, addIcon.getImage(), 30, 19, 9, 5);
		((MyButton) btnAddCustomerNew).setFontCustom(new Font("Dialog", Font.BOLD, 14));
		btnAddCustomerNew.setBounds(380, 125, 115, 34);
		pnlInfo.add(btnAddCustomerNew);
		
		JLabel lblNewLabel = new JLabel("THÊM KHÁCH HÀNG");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(193, 25, 500, 30);
		pnlMain.add(lblNewLabel);
		
		
	}
	public static void main(String[] args) {
		new PnThemMoiKhachHang().setVisible(true); 
	}
}
