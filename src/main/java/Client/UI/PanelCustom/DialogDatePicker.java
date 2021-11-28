package Client.UI.PanelCustom;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Calendar;
import java.sql.*;

/**
 * popup dialog chọn ngày tháng năm
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 23/04/2021
 * <p>
 * Lần cập nhật cuối: 20/11/2021
 * <p>
 * Nội dung cập nhật: thêm giải thích lớp javadoc
 */
public class DialogDatePicker extends JDialog implements ActionListener, ChangeListener {
    /**
     * 
     */
    private static final long serialVersionUID = 6679826367296394635L;
    private int width = 450, heightPn = 220, widthPn = width - 20;
    private JButton[] button = new JButton[49];
    private String day = "";
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private JButton btnPre, btnNext, btnCancel, btnSubmit;
    private SpinnerNumberModel spinYearModel;
    private JSpinner spnYear;
    private int check = 0, viTri = -1;
    private JLabel lblMonth, lblYear, lblThu, lblNgayThang, lblToDay;
    private String blueColor = "#3f51b5";
    private String whiteColor = "#fafafa";

    /**
     * Constructor mặc định không tham số
     */
    public DialogDatePicker() {
        setTitle("Chọn ngày");
        setSize(447, 250);
        setResizable(false);
        setLocationRelativeTo(null);

        createFormDatePicker();
    }

    /**
     * Khởi tạo giao diện
     */
    public void createFormDatePicker() {
        JPanel pnlMain = new JPanel();
        pnlMain.setBounds(0, 0, widthPn, heightPn);
        pnlMain.setBackground(Color.decode(whiteColor));
        pnlMain.setLayout(null);

        JPanel pnlShowTime = new JPanel();
        pnlShowTime.setBounds(0, 0, 100, 202);
        pnlShowTime.setBackground(Color.decode(blueColor));
        pnlMain.add(pnlShowTime);
        pnlShowTime.setLayout(null);

        lblYear = new JLabel("year");
        lblYear.setFont(new Font("Dialog", Font.BOLD, 15));
        lblYear.setBounds(12, 12, 107, 16);
        lblYear.setForeground(Color.decode("#aeb5df"));
        pnlShowTime.add(lblYear);

        lblThu = new JLabel("thứ");
        lblThu.setFont(new Font("Dialog", Font.BOLD, 18));
        lblThu.setBounds(12, 40, 107, 25);
        lblThu.setForeground(Color.WHITE);
        pnlShowTime.add(lblThu);

        lblNgayThang = new JLabel("tháng ngày");
        lblNgayThang.setFont(new Font("Dialog", Font.BOLD, 18));
        lblNgayThang.setBounds(12, 68, 107, 25);
        lblNgayThang.setForeground(Color.WHITE);
        pnlShowTime.add(lblNgayThang);

        String[] header = { "S", "M", "T", "W", "T", "F", "S" };
        JPanel pnlDateTable = new JPanel(new GridLayout(7, 7));
        pnlDateTable.setBackground(Color.decode(whiteColor));

        for (int i = 0; i < button.length; i++) {
            final int selection = i;
            button[i] = new JButton();
            button[i].setFocusPainted(false);
            button[i].setBackground(Color.WHITE);
            if (i < 7) {
                button[i].setText(header[i]);
                button[i].setEnabled(false);
                button[i].setForeground(Color.decode(blueColor));
            } else {
                button[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        if (viTri != -1) {
                            button[viTri].setBackground(Color.decode(whiteColor));
                            button[viTri].setForeground(Color.BLACK);
                        }
                        viTri = selection;
                        button[selection].setBackground(Color.decode(blueColor));
                        button[selection].setForeground(Color.decode(whiteColor));
                        int day = Integer.parseInt(button[selection].getText());
                        displayShowDate(day);
                    }
                });
            }
            button[i].setBorder(null);
            button[i].setPreferredSize(new Dimension(20, 20));
            button[i].setBackground(Color.decode(whiteColor));
            pnlDateTable.add(button[i]);
        }

        JPanel pnlBtn = new JPanel();
        pnlBtn.setLayout(null);
        pnlBtn.setBackground(Color.decode(whiteColor));

        btnPre = new JButton("<");
        btnNext = new JButton(">");

        spinYearModel = new SpinnerNumberModel(year, 1900, null, 1);
        spnYear = new JSpinner(spinYearModel);
        spnYear.setFont(new Font("Dialog", Font.BOLD, 13));
        lblMonth = new JLabel("tháng");
        lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
        lblMonth.setFont(new Font("Dialog", Font.BOLD, 14));

        int h = 25;
        btnPre.setBounds(3, 0, 41, h);
        btnPre.setBackground(Color.decode(whiteColor));
        btnPre.setForeground(Color.decode(blueColor));
        btnPre.setBorder(null);
        lblMonth.setBounds(62, 2, 105, 25);
        spnYear.setBounds(165, 2, 70, h);
        btnNext.setBounds(287, 0, 41, h);
        btnNext.setBackground(Color.decode(whiteColor));
        btnNext.setForeground(Color.decode(blueColor));
        btnNext.setBorder(null);

        pnlBtn.setBounds(101, 0, 330, 30);
        pnlDateTable.setBounds(101, 30, 330, 140);

        pnlBtn.add(btnPre);
        pnlBtn.add(lblMonth);
        pnlBtn.add(spnYear);
        pnlBtn.add(btnNext);

        pnlMain.add(pnlBtn);
        pnlMain.add(pnlDateTable);

        JPanel pnlSubmit = new JPanel();
        pnlSubmit.setBackground(Color.decode(whiteColor));
        pnlSubmit.setLayout(null);
        pnlSubmit.setBounds(101, 172, 330, 30);
        pnlMain.add(pnlSubmit);

        btnSubmit = new JButton("OK");
        btnSubmit.setBounds(268, 0, 60, 26);
        btnSubmit.setBackground(Color.decode(whiteColor));
        btnSubmit.setForeground(Color.decode(blueColor));
        btnSubmit.setBorder(null);
        pnlSubmit.add(btnSubmit);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(182, 0, 74, 26);
        btnCancel.setBackground(Color.decode(whiteColor));
        btnCancel.setForeground(Color.decode(blueColor));
        btnCancel.setBorder(null);
        pnlSubmit.add(btnCancel);

        lblToDay = new JLabel("Today: ");
        lblToDay.setBounds(10, 6, 162, 14);
        pnlSubmit.add(lblToDay);

        displayDate();
        showToDay();
        getContentPane().add(pnlMain);

        btnNext.addActionListener(this);
        btnPre.addActionListener(this);
        btnSubmit.addActionListener(this);
        btnCancel.addActionListener(this);
        spnYear.addChangeListener(this);
    }

    public static void main(String[] args) {
        new DialogDatePicker().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnPre)) {
            month--;
            displayDate();
        } else if (o.equals(btnNext)) {
            month++;
            displayDate();
        } else if (o.equals(btnSubmit)) {
            check = 1;
            dispose();
        } else if (o.equals(btnCancel)) {
            check = 0;
            dispose();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object o = e.getSource();
        if (o.equals(spnYear)) {
            displayDate();
        }
    }

    /**
     * thay đổi lịch theo tháng năm
     */
    public void displayDate() {
        for (int i = 7; i < button.length; i++)
            button[i].setText("");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM");
        Calendar cal = Calendar.getInstance();
        int y = (int) spnYear.getValue();
        if (y != year)
            year = y;

        cal.set(year, month, 1);

        lblMonth.setText(sdfMonth.format(cal.getTime()));
        spnYear.setValue(year);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 6 + dayOfWeek; i++) {
            button[i].setEnabled(false);
        }
        for (int i = 6 + dayOfWeek, day = 1; day <= daysInMonth; i++, day++) {
            button[i].setText("" + day);
            button[i].setEnabled(true);
        }
        for (int i = 6 + dayOfWeek + daysInMonth; i < button.length; i++) {
            button[i].setEnabled(false);
        }
    }

    /**
     * Hiển thị ngày hiện tại
     */
    public void showToDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        lblNgayThang.setText(sdf.format(cal.getTime()));
        sdf = new SimpleDateFormat("E");

        lblThu.setText(sdf.format(cal.getTime()) + ",");
        sdf = new SimpleDateFormat("yyyy");

        lblYear.setText(sdf.format(cal.getTime()));
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        lblToDay.setText("Today: " + sdf.format(cal.getTime()));
    }

    /**
     * Hiển thị ngày được chọn
     * 
     * @param day {@code int}: ngày được chọn
     */
    public void displayShowDate(int day) {
        Calendar cal = Calendar.getInstance();
        int y = (int) spnYear.getValue();
        if (y != year)
            year = y;
        cal.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        lblNgayThang.setText(sdf.format(cal.getTime()));

        sdf = new SimpleDateFormat("E");
        lblThu.setText(sdf.format(cal.getTime()) + ",");

        sdf = new SimpleDateFormat("yyyy");
        lblYear.setText(sdf.format(cal.getTime()));
    }

    /**
     * Lấy ngày chọn từ lịch và trả về ngày dạng chuỗi
     * 
     * @return {@code String}: ngày được chọn
     */
    private String getPickedDate() {
        if (day.equals(""))
            return day;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }

    /**
     * Lấy ngày chọn từ lịch và trả về ngày dạng {@code java.sql.Date}
     * 
     * @return {@code java.sql.Date}: ngày được chọn
     */
    public Date getDate() {
        if (day.equals(""))
            day = "0";
        Calendar cal = Calendar.getInstance();
        int date = Integer.parseInt(day);
        cal.set(year, month, date);
        return (Date) cal.getTime();
    }

    /**
     * Lấy ngày hiện tại và trả về ngày dạng chuỗi
     * 
     * @return {@code String}: ngày hiện tại
     */
    public static String getToDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(cal.getTimeInMillis());
    }

    /**
     * Lấy ra ngày đã chọn ở dạng chuỗi
     * 
     * @return {@code String}: ngày được chọn
     */
    public String getValueString() {
        String re = "";
        if (check == 1)
            re = getPickedDate();
        return re;
    }
}
