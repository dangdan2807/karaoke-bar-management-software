package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class test {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, 9, 01);
        Date fromDate = new Date(cal.getTimeInMillis());
        cal.set(2021, 11, 01);
        Date toDate = new Date(cal.getTimeInMillis());
        System.out.println(fromDate);
        System.out.println(toDate);
        ArrayList<Object[]> result = HoaDonDAO.getInstance().getTotalPriceBillListByDate(fromDate,
                toDate, "dd-MM-yyyy");
        System.out.println(result.size());
        for (Object[] objects : result) {
            System.out.println(objects[0] + " " + objects[1]);
        }
    }
}
