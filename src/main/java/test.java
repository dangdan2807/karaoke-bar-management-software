
import java.util.*;
import java.util.concurrent.TimeUnit;

import DAO.NhanVienDAO;
import entity.NhanVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {
    public static void testTime() throws ParseException {
        String start = "2021/10/01 15:30:00";
        String end = "2021/10/01 18:10:00";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date1 = format.parse(start);

        Date date2 = format.parse(end);

        long difference = date2.getTime() - date1.getTime();
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);

        if (minutes < 0)
            minutes += 1440;
        System.out.println(minutes);
        System.out.println(minutes * 1.0 / 60);
    }

    public static void main(String[] args) {
        String lastStaffId = "NV1000001999";
		String genderStr = String.valueOf(1);
		String positionStr = String.valueOf(0);
		String idStr = "NV" + genderStr + positionStr;
		String skipStr = lastStaffId.substring(0, 4);
		int oldNumberStaffID = 0;
		if (lastStaffId.equalsIgnoreCase("") == false || lastStaffId != null) {
			oldNumberStaffID = Integer.parseInt(lastStaffId.replace(skipStr, ""));
		}
		int newStaffID = ++oldNumberStaffID;
		String newStaffIdStr = idStr + newStaffID;
		boolean flag = true;
        while (flag) {
            newStaffIdStr = newStaffIdStr.replace(idStr, idStr + "0");
            if (newStaffIdStr.length() >= 12) {
                flag = false;
            }
        }
        System.out.println(newStaffIdStr);
    }
}
