
import java.sql.*;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

import DAO.DichVuDAO;
import DAO.NhanVienDAO;
import entity.DichVu;
import entity.LoaiDichVu;
import entity.NhanVien;
import entity.TaiKhoan;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {
    public static void testTime() throws ParseException {
        String start = "2021/10/01 15:30:00";
        String end = "2021/10/01 18:10:00";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date1 = format.parse(start);

        java.util.Date date2 = format.parse(end);

        long difference = date2.getTime() - date1.getTime();
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);

        if (minutes < 0)
            minutes += 1440;
        System.out.println(minutes);
        System.out.println(minutes * 1.0 / 60);
    }

    /**
     * Tự động tạo mã dịch vụ mới tăng theo thứ tụ tăng dần
     * 
     * @return <code>String</code>: mã dịch vụ mới
     */
    private static String createNewServiceID() {
        String lastServiceId = DichVuDAO.getInstance().getLastServiceID();
        String idStr = "DV";
        int oldNumberStaffID = 0;
        if (lastServiceId.equalsIgnoreCase("") == false || lastServiceId != null) {
            oldNumberStaffID = Integer.parseInt(lastServiceId.replace(idStr, ""));
        }

        int newStaffID = ++oldNumberStaffID;
        String newStaffIdStr = idStr + newStaffID;
        boolean flag = true;
        while (flag) {
            newStaffIdStr = newStaffIdStr.replace(idStr, idStr + "0");
            if (newStaffIdStr.length() > 4) {
                flag = false;
            }
        }
        return newStaffIdStr;
    }

    public static void main(String[] args) {
        // TaiKhoan account = new TaiKhoan("dan1");
        // Date date = Date.valueOf("2001-01-01");
        // NhanVien staff = new NhanVien("NV00000012", "111111111", "Phạm Đăng Đan", date, "0312345678", "Chủ quán",
        //         6000000.0, false, "Đang làm", account);
        // boolean rs = NhanVienDAO.getInstance().insertStaff(staff);
        // System.out.println(rs);
        String rs = createNewServiceID();
        System.out.println(rs);
    }
}
