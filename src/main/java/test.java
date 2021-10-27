
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
     * @return <code>String</code>: mã dịch vụ mới
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

    public static void main(String[] args) {
        // testTime();
        // System.out.println(createNewServiceID());
        // ExportBill.getInstance().exportBillToExcel(1, "D:/hd.xlsx");
        // ExportBill.getInstance().exportBillToPdf(1, "D:/hd.pdf");
        // BigDecimal a = new BigDecimal("0.00");
        // BigDecimal b = new BigDecimal("1.00");
        // System.out.println(a.add(b));
        // System.out.println(a.multiply(b));
        LoaiPhong roomType = new LoaiPhong("LP004", "1", 1, 2300.5);
        Boolean result = LoaiPhongDAO.getInstance().updateInfoRoomType(roomType);
        System.out.println(result);
    }   
}
