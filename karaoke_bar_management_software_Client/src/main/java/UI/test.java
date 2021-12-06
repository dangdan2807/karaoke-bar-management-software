package UI;

import java.util.Calendar;
import java.util.*;

public class test {
    public static void main(String[] args) {
        // Calendar cal = Calendar.getInstance();
        // System.out.println(cal.getTime());
        // cal.add(Calendar.WEEK_OF_MONTH, 1);
        // System.out.println(cal.getTime());
        // cal.add(Calendar.WEEK_OF_MONTH, -2);
        // System.out.println(cal.getTime());
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(100, "A");
        map.put(101, "B");
        map.put(102, "C");
        map.put(101, "C");
        // show map
        Set<Integer> set = map.keySet();
        for (Integer key : set) {
            System.out.println(key + " " + map.get(key));
        }
    }
}
