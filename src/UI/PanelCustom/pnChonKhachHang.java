package UI.PanelCustom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.NhanVien;

public class pnChonKhachHang extends JFrame
		implements ActionListener, ChangeListener {

	private JTabbedPane tpTabMain;
	
	private NhanVien nhanVienLogin = null;

	public pnChonKhachHang(NhanVien nhanVien) {
		this.nhanVienLogin = nhanVien;
		setTitle("Chọn khách hàng");
		setSize(800, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		createTabControl();
		createUI();
	}
	
	public void createTabControl() {
		tpTabMain = new JTabbedPane();
        this.add(tpTabMain);

        tpTabMain.addChangeListener(this);
	}
	
	public void createUI() {
		JPanel pnMain = new JPanel();
        pnMain.setBounds(0, 0, 800, 400);
        pnMain.setBackground(Color.WHITE);
        pnMain.setLayout(null);
        getContentPane().add(pnMain);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
