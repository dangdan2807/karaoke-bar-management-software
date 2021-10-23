package DAO;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConvertTime {
    private static ConvertTime instance = new ConvertTime();

    public static ConvertTime getInstance() {
        if (instance == null)
            instance = new ConvertTime();
        return instance;
    }

    /**
     * Chuyển đổi ngày ở kiểu dữ liệu java.sql.Date sang ngày ở kiểu dữ liệu java.util.Date
     * @param date java.sql.Date
     * @return java.util.Date
     */
    public java.util.Date convertSqlDateToUtilDate(Date date) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        return utilDate;
    }

    /**
     * chuyển đổi ngày ở kiểu dữ liệu java.sql.Date sang ngày ở kiểu dạng chuỗi
     * @param date ngày cần chuyển đổi ở kiểu dữ liệu java.sql.Date
     * @param format định dạng cần format Ví dụ: "dd/MM/yyyy", "hh:mm:ss", "dd/MM/yyyy hh:mm:ss", ...
     * @return chuỗi ngày giờ đã định dạng
     */
    public String convertSqlDateToUtilDateFormatString(Date date, String format) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        DateFormat df = new SimpleDateFormat(format);
        return df.format(utilDate);
    }

    /**
     * chuyển đổi ngày ở kiểu dữ liệu java.sql.Timestamp sang ngày ở kiểu dạng chuỗi
     * @param date ngày cần chuyển đổi ở kiểu dữ liệu java.sql.Timestamp
     * @param format định dạng cần format Ví dụ: "dd/MM/yyyy", "hh:mm:ss", "dd/MM/yyyy hh:mm:ss", ...
     * @return chuỗi ngày giờ đã định dạng
     */
    public String convertSqlTimestampToUtilDateFormatString(Timestamp date, String format) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        DateFormat df = new SimpleDateFormat(format);
        return df.format(utilDate);
    }
}
