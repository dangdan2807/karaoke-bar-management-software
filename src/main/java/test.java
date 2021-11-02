
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

    private static String createNewBillId(Timestamp date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateStr = format.format(date);
        String lastBillId = HoaDonDAO.getInstance().getLastBillId();
        System.out.println(lastBillId);
        String newIdStr = "HD" + dateStr;
        int oldNumberBillId = 0;
        // lấy 5 số cuối của hóa đơn
        String lastBillIdStr = lastBillId.substring(0, 10);
        if (lastBillId.equalsIgnoreCase("") == false || lastBillId != null) {
            oldNumberBillId = Integer.parseInt(lastBillId.substring(10));
        }

        // Nếu ngày hóa đơn là ngày hiện tại thì tăng thêm 1
        // Nếu ngày hóa đơn là ngày hôm qua thì bắt đầu từ 1
        if (lastBillIdStr.equalsIgnoreCase(newIdStr)) {
            ++oldNumberBillId;
        } else {
            oldNumberBillId = 1;
        }

        int newStaffID = oldNumberBillId;
        String newStaffIdStr = newIdStr + newStaffID;
        boolean flag = true;
        while (flag) {
            newStaffIdStr = newStaffIdStr.replace(newIdStr, newIdStr + "0");
            if (newStaffIdStr.length() > 14) {
                flag = false;
            }
        }
        return newStaffIdStr;
    }

    public static void main(String[] args) {
        int key = 10;
        switch (key) {
        case 1:
            testTime();
            break;
        case 2:
            System.out.println(createNewServiceID());
            break;
        case 3:
            ExportBill.getInstance().exportBillToExcel("HD2021010100001", "D:/hd.xlsx");
            break;
        case 4:
            // ExportBill.getInstance().exportBillToPdf(1, "D:/hd.xlsx");
            break;
        case 5:
            BigDecimal a = new BigDecimal("0.00");
            BigDecimal b = new BigDecimal("1.00");
            System.out.println(a.add(b));
            System.out.println(a.multiply(b));
            break;
        case 6:
            String data = "8000.0";
            System.out.println(data.replaceAll("\\.[0]+$", ""));
            break;
        case 7:
            JFrame frame = new JFrame("Test");
            JDialog jDialog = new JDialog();
            JPanel jPanel = new JPanel();
            test1(frame);
            test1(jDialog);
            test1(jPanel);
            break;
        case 8:
            JTextField txt = new JTextField();
            JCheckBox chk = new JCheckBox();
            JSpinner spin = new JSpinner();
            JLabel ldl = new JLabel();
            test2(txt);
            test2(chk);
            test2(spin);
            test2(ldl);
            break;
        case 9:
            String phone = "0303030303";
            phone = phone.substring(0, 4) + "-" + phone.substring(4, 7) + "-" + phone.substring(7, 10);
            System.out.println(phone);
            phone = phone.replace("-", "");
            System.out.println(phone);
            break;
        case 10:
            String id = createNewBillId(Timestamp.valueOf("2021-10-02 00:00:00"));
            System.out.println(id);
            break;
        default:
            break;
        }

    }
}
