
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.awt.*;

import javax.swing.*;

import DAO.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {
    public static void testTime() {
        String start = "2021/10/01 23:30:00";
        String end = "2021/10/01 00:30:05";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
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

        System.out.println();

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

    /**
     * tính số giờ thuê phòng
     * 
     * @return {@code Double}: số giờ thuê phòng
     */
    public static Double tinhGioThue(Timestamp ngayGioDat, Timestamp ngayGioTra) {
        int minutes = 0;
        if (ngayGioTra != null && ngayGioDat != null) {
            long difference = ngayGioTra.getTime() - ngayGioDat.getTime();
            minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
        }

        minutes = (int) minutes / 30;
        return minutes * 1.0 / 2;
    }

    public static void main(String[] args) {
        int key = 100;
        String billId = "HD2021100200002";
        switch (key) {
        case 0:
            System.out.println(billId);
            // final String workingDir = System.getProperty("user.dir") + "/src/main/java/";
            // System.out.println(workingDir + "");
            // URL location =
            // Test.class.getProtectionDomain().getCodeSource().getLocation();
            // System.out.println(location.getFile());
            ArrayList<String> list = new ArrayList<>();
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");

            System.out.println(list.get(0));
            // list.remove(2);
            System.out.println(list.get(2));
            break;
        case 1:
            testTime();
            break;
        case 2:
            System.out.println(createNewServiceID());
            break;
        case 3:
            // ExportBill.getInstance().exportBillToExcel(billId, "D:");
            break;
        case 4:
            // ExportBill.getInstance().exportBillToPdf(billId, "D:/");
            break;
        case 5:
            BigDecimal a = new BigDecimal("1.00");
            BigDecimal b = new BigDecimal("2.00");
            System.out.println("a + b = " + a.add(b));
            System.out.println("a - b = " + a.subtract(b));
            System.out.println("a * b = " + a.multiply(b));
            System.out.println("a / b = " + a.divide(b));
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
        case 11:
            String demoPath1 = "D:/123";
            // D:/123 -> D:/123/
            // D:/123/ -> D:/123/
            // D:/123\\ -> D:/123\

            String demoPath2 = demoPath1;
            // ^.+[^\\\/]$
            if (!demoPath1.matches("^.+[\\\\/]$")) {
                demoPath1 += "/";
            }
            boolean result = false;
            if (demoPath1.equalsIgnoreCase(demoPath2 + "/") && !demoPath1.endsWith("\\/")
                    || demoPath1.equalsIgnoreCase(demoPath2)) {
                result = true;
            }
            System.out.println(demoPath2 + " -> " + demoPath1 + " : " + result);
            break;
        case 12:
            Timestamp ngayGioDat = Timestamp.valueOf("2021-10-02 00:00:00");
            Timestamp ngayGioTra = Timestamp.valueOf("2021-10-03 00:00:00");
            System.out.println(tinhGioThue(ngayGioDat, ngayGioTra));
            break;
        default:
            // String end = "12/12/11 23:22:09";
            // SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            // java.util.Date date = null;
            // try {
            // date = format.parse(end);
            // } catch (ParseException e) {
            // e.printStackTrace();
            // }
            // System.out.println("Time in 24Hours =" + new
            // SimpleDateFormat("HH:mm").format(date));
            int value1 = 0;
            int value2 = 0;
            try {
                String value1Str = "1";
                String value2Str = "93476376487364863864876348";
                value1 = Integer.parseInt(value1Str);
                value2 = Integer.parseInt(value2Str);
            } catch (Exception e) {

            }
            System.out.println(value1);
            System.out.println(value2);
            break;
        }

    }
}
