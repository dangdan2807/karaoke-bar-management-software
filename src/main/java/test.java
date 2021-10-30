
import java.math.BigDecimal;
import java.awt.*;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.poi.ss.usermodel.CellStyle;

import DAO.*;
import entity.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {
    public static void testTime() {
        String start = "2021/10/01 15:30:00";
        String end = "2021/10/01 15:30:05";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date1 = null;
        try {
            date1 = format.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.util.Date date2 = null;
        try {
            date2 = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);

        if (minutes < 0)
            minutes += 1440;
        System.out.println(minutes);
        System.out.println(minutes * 1.0 / 60);
    }

    /**
     * Tự động tạo mã dịch vụ mới tăng theo thứ tự tăng dần
     * 
     * @return {@code String}: mã dịch vụ mới
     */
    private static String createNewServiceID() {
        String lastStrId = LoaiDichVuDAO.getInstance().getLastServiceTypeID();
        String idStr = "LDV";
        int oldNumberId = 0;
        if (lastStrId.equalsIgnoreCase("") == false || lastStrId != null) {
            oldNumberId = Integer.parseInt(lastStrId.replace(idStr, ""));
        }

        int newNumberId = ++oldNumberId;
        String newIdStr = idStr + newNumberId;
        boolean flag = true;
        while (flag) {
            newIdStr = newIdStr.replace(idStr, idStr + "0");
            if (newIdStr.length() > 5) {
                flag = false;
            }
        }
        return newIdStr;
    }

    private static void test1(Component c) {
        if (c instanceof JFrame) {
            System.out.println("jFrame");
        } else if (c instanceof JPanel) {
            System.out.println("jPanel");
        } else if (c instanceof JDialog) {
            System.out.println("jDialog");
        } else {
            System.out.println("khong xac dinh");
        }
    }

    private static void test2(JComponent c) {
        if (c instanceof JTextField) {
            System.out.println("jTextField");
        } else if (c instanceof JLabel) {
            System.out.println("jLabel");
        } else if (c instanceof JCheckBox) {
            System.out.println("jCheckBox");
        } else if (c instanceof JSpinner) {
            System.out.println("jSpinner");
        } else {
            System.out.println("khong xac dinh");
        }
    }

    private void showMessage(JFrame jFrame) {

    }

    private void showMessage(JDialog jDialog) {

    }

    private void showMessage(JPanel jPanel) {

    }

    public static void main(String[] args) {
        // testTime();
        // System.out.println(createNewServiceID());
        // ExportBill.getInstance().exportBillToExcel(1, "D:/hd.xlsx");
        // ExportBill.getInstance().exportBillToPdf(1, "D:/hd.pdf");
        // BigDecimal a = new BigDecimal("0.00");
        // BigDecimal b = new BigDecimal("1.00");
        // System.out.println(a.add(b));
        // System.out.println(a.multiply(b));
        // LoaiPhong roomType = new LoaiPhong("LP004", "1", 1, 2300.5);
        // Boolean result = LoaiPhongDAO.getInstance().updateInfoRoomType(roomType);
        // System.out.println(result);
        // String data = "8000.0";
        // System.out.println(data.replaceAll("\\.[0]+$", ""));
        // JFrame frame = new JFrame("Test");
        // JDialog jDialog = new JDialog();
        // JPanel jPanel = new JPanel();

        // test1((Component) frame);
        // test1((Component) jDialog);
        // test1((Component) jPanel);
        JTextField a = new JTextField();
        JCheckBox b = new JCheckBox();
        JSpinner c = new JSpinner();
        JLabel d = new JLabel();
        test2(a);
        test2(b);
        test2(c);
        test2(d);
    }
}
