package UI.PanelCustom;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class test {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 10);
        Date fromDate = calendar.getTime();
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(2021, 11, 21);
        Date toDate = calendar.getTime();
		int day2 = calendar.get(Calendar.DAY_OF_MONTH);

		long difference = toDate.getTime() - fromDate.getTime();
		int times = (int) TimeUnit.MILLISECONDS.toDays(difference);
        System.out.println(fromDate);
        System.out.println(toDate);
        System.out.println(times);
    }
}
