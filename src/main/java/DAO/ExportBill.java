package DAO;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import entity.*;

/**
 * Sử dụng xuất hóa đơn dạng pdf và excel
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 27/10/2021
 * <p>
 * Lần cập nhật cuối: 07/11/2021
 * <p>
 * Nội dung cập nhật: sửa lỗi không xuất file excel
 */
public class ExportBill {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row = null;
    private Cell cell = null;
    private int rowHeight = 300;
    private IndexedColors excelWhiteColor = IndexedColors.WHITE;
    private IndexedColors excelBlackColor = IndexedColors.BLACK;
    private HorizontalAlignment excelAlignCenter = HorizontalAlignment.CENTER;
    private HorizontalAlignment excelAlignRight = HorizontalAlignment.RIGHT;
    private HorizontalAlignment excelAlignLeft = HorizontalAlignment.LEFT;
    private BorderStyle borderStyleThin = BorderStyle.THIN;
    private BorderStyle borderStyleThick = BorderStyle.THICK;

    private String fontPath = "src/main/resources/fonts/";
    private String pdfFontLight = fontPath + "Roboto-300.ttf";
    private String pdfFontLightItalic = fontPath + "Roboto-300_Italic.ttf";
    private String pdfFontMedium = fontPath + "Roboto-500.ttf";
    private BaseFont baseFontMedium;
    private BaseFont baseFontLight;
    private BaseFont baseFontLightItalic;
    private int pdfAlignLeft = Element.ALIGN_LEFT;
    private int pdfAlignCenter = Element.ALIGN_CENTER;
    private int pdfAlignRight = Element.ALIGN_RIGHT;

    private DecimalFormat df = new DecimalFormat("#,###.##");
    private String formatTime = "HH:mm:ss dd/MM/yyyy";
    private String karaokeName = "KARAOKE DASH";
    private String address = "12 Nguyễn Văn Bảo, Phường 4, Gò Vấp, Thành phố Hồ Chí Minh";
    private String phoneNumber = "0303.030.303";

    private static ExportBill instance = new ExportBill();

    public static ExportBill getInstance() {
        if (instance == null)
            instance = new ExportBill();
        return instance;
    }

    /**
     * Chuyển số giờ thuê thành chuỗi dạng {@code x giờ y phút}
     * 
     * @param hours {@code double}: số giờ thuê
     * @return {@code String}: chuỗi dạng {@code x giờ y phút}
     */
    private String convertRentalTime(double hours) {
        int minutes = (int) (hours % 1 * 60);
        int hoursInt = (int) hours;
        String minutesStr = minutes < 10 ? "0" + minutes : minutes + "";
        String hoursStr = hoursInt < 10 ? "0" + hoursInt : hoursInt + "";
        String time = String.format("%s giờ %s phút", hoursStr, minutesStr);
        return time;
    }

    /**
     * Tạo 1 ô mới tại vị trí chỉ định trong file excel
     * 
     * @param text      {@code String}: nội dung của ô
     * @param cellIndex {@code int}: vị trí của ô
     * @param style     {@code XSSFCellStyle}: style của ô
     */
    private void createCell(String text, int cellIndex, XSSFCellStyle style) {
        cell = row.createCell(cellIndex, CellType.STRING);
        cell.setCellValue(text);
        cell.setCellStyle(style);
    }

    /**
     * Tạo 1 ô mới tại vị trí chỉ định trong file excel
     * 
     * @param text      {@code String}: nội dung của ô
     * @param cellIndex {@code int}: vị trí của ô
     * @param style     {@code XSSFCellStyle}: style của ô
     * @param cellType  {@code XSSFCellType}: kiểu dữ liệu của ô
     */
    private void createCell(String text, int cellIndex, XSSFCellStyle style, CellType cellType) {
        cell = row.createCell(cellIndex, cellType);
        cell.setCellValue(text);
        cell.setCellStyle(style);
    }

    /**
     * Tạo 1 ô mới tại vị trí chỉ định trong file excel
     * 
     * @param text      {@code Double}: nội dung của ô
     * @param cellIndex {@code int}: vị trí của ô
     * @param style     {@code XSSFCellStyle}: style của ô
     * @param cellType  {@code XSSFCellType}: kiểu dữ liệu của ô
     */
    private void createCell(Double text, int cellIndex, XSSFCellStyle style, CellType cellType) {
        cell = row.createCell(cellIndex, cellType);
        cell.setCellValue(text);
        cell.setCellStyle(style);
    }

    /**
     * Tạo 1 ô mới tại vị trí chỉ định trong file excel
     * 
     * @param text      {@code Double}: nội dung của ô
     * @param cellIndex {@code int}: vị trí của ô
     * @param fontSize  {@code int}: font size của ô
     * @param bold      {@code boolean}: in đậm chữ
     *                  <ul>
     *                  <li>{@code true}: in đậm</li>
     *                  <li>{@code false}: không in đậm</li>
     *                  </ul>
     * @param italic    {@code boolean}: in nghiêng chữ
     *                  <ul>
     *                  <li>{@code true}: in nghiêng</li>
     *                  <li>{@code false}: không in nghiêng</li>
     *                  </ul>
     * @param wrapText  {@code boolean}: tự động xuống dòng bên trong ô
     *                  <ul>
     *                  <li>{@code true}: xuống dòng</li>
     *                  <li>{@code false}: không xuống dòng</li>
     *                  </ul>
     * @param align     {@code HorizontalAlignment}: căn lề ô
     */
    private void createCell(String text, int cellIndex, int fontSize, boolean bold, boolean italic, boolean wrapText,
            HorizontalAlignment align) {
        XSSFFont font = workbook.createFont();
        font.setBold(bold);
        font.setItalic(italic);
        font.setFontHeightInPoints((short) fontSize);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(wrapText);
        style.setAlignment(align);
        style.setBorderBottom(borderStyleThin);
        style.setBottomBorderColor(excelWhiteColor.getIndex());
        style.setBorderTop(borderStyleThin);
        style.setTopBorderColor(excelWhiteColor.getIndex());
        style.setBorderLeft(borderStyleThin);
        style.setLeftBorderColor(excelWhiteColor.getIndex());
        style.setBorderRight(borderStyleThin);
        style.setRightBorderColor(excelWhiteColor.getIndex());

        cell = row.createCell(cellIndex, CellType.STRING);
        cell.setCellValue(text);
        cell.setCellStyle(style);
    }

    /**
     * Tạo 1 dòng và ô tại vị trí chỉ định trong file excel
     * 
     * @param textCell  {@code String}: nội dung của ô
     * @param rowIndex  {@code int}: vị trí của dòng
     * @param cellIndex {@code int}: vị trí của ô
     * @param rowHeight {@code int}: chiều cao của dòng
     * @param fontSize  {@code int}: font size của ô
     * @param bold      {@code boolean}: in đậm chữ
     *                  <ul>
     *                  <li>{@code true}: in đậm</li>
     *                  <li>{@code false}: không in đậm</li>
     *                  </ul>
     * @param italic    {@code boolean}: in nghiêng chữ
     *                  <ul>
     *                  <li>{@code true}: in nghiêng</li>
     *                  <li>{@code false}: không in nghiêng</li>
     *                  </ul>
     * @param wrapText  {@code boolean}: tự động xuống dòng bên trong ô
     *                  <ul>
     *                  <li>{@code true}: xuống dòng</li>
     *                  <li>{@code false}: không xuống dòng</li>
     *                  </ul>
     * @param merge     {@code CellRangeAddress}: hợp nhất dải ô
     * @param align     {@code HorizontalAlignment}: căn lề ô
     */
    private void createRow(String textCell, int rowIndex, int cellIndex, int rowHeight, int fontSize, boolean bold,
            boolean italic, boolean wrapText, CellRangeAddress merge, HorizontalAlignment align) {
        XSSFFont font = workbook.createFont();
        font.setBold(bold);
        font.setItalic(italic);
        font.setFontHeightInPoints((short) fontSize);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(wrapText);
        style.setAlignment(align);
        style.setBorderBottom(borderStyleThin);
        style.setBottomBorderColor(excelWhiteColor.getIndex());
        style.setBorderTop(borderStyleThin);
        style.setTopBorderColor(excelWhiteColor.getIndex());
        style.setBorderLeft(borderStyleThin);
        style.setLeftBorderColor(excelWhiteColor.getIndex());
        style.setBorderRight(borderStyleThin);
        style.setRightBorderColor(excelWhiteColor.getIndex());

        row = sheet.createRow((short) rowIndex);
        row.setHeight((short) rowHeight);
        if (merge != null)
            sheet.addMergedRegion(merge);
        createCell(textCell, cellIndex, style, CellType.STRING);
    }

    /**
     * Tạo viền cho ô được chọn
     * 
     * @param top         {@code int}:
     *                    <ul>
     *                    <li>{@code 1}: tạo viền trên</li>
     *                    <li>{@code 0}: không tạo viền trên</li>
     *                    </ul>
     * @param bottom      {@code int}:
     *                    <ul>
     *                    <li>{@code 1}: tạo viền dưới</li>
     *                    <li>{@code 0}: không tạo viền dưới</li>
     *                    </ul>
     * @param left        {@code int}:
     *                    <ul>
     *                    <li>{@code 1}: tạo viền trái</li>
     *                    <li>{@code 0}: không tạo viền trái</li>
     *                    </ul>
     * @param right       {@code int}:
     *                    <ul>
     *                    <li>{@code 1}: tạo viền phải</li>
     *                    <li>{@code 0}: không tạo viền phải</li>
     *                    </ul>
     * @param borderStyle {@code BorderStyle}: kiểu viền muốn tạo
     * @return {@code XSSFCellStyle}: style viền được cấu hình dựa trên các tham sô
     *         truyền vào
     */
    private XSSFCellStyle createBordersExcel(int top, int bottom, int left, int right, BorderStyle borderStyle) {
        XSSFCellStyle style = workbook.createCellStyle();
        if (top == 1) {
            style.setBorderTop(borderStyle);
            style.setTopBorderColor(excelWhiteColor.getIndex());
        }
        if (bottom == 1) {
            style.setBorderBottom(borderStyle);
            style.setBottomBorderColor(excelWhiteColor.getIndex());
        }
        if (left == 1) {
            style.setBorderLeft(borderStyle);
            style.setLeftBorderColor(excelWhiteColor.getIndex());
        }
        if (right == 1) {
            style.setBorderRight(borderStyle);
            style.setRightBorderColor(excelWhiteColor.getIndex());
        }
        return style;
    }

    /**
     * 
     * /** Tạo viền cho ô được chọn
     * 
     * @param top         {@code int}:
     *                    <ul>
     *                    <li>{@code 1}: tạo viền trên</li>
     *                    <li>{@code 0}: không tạo viền trên</li>
     *                    </ul>
     * @param bottom      {@code int}:
     *                    <ul>
     *                    <li>{@code 1}: tạo viền dưới</li>
     *                    <li>{@code 0}: không tạo viền dưới</li>
     *                    </ul>
     * @param left        {@code int}:
     *                    <ul>
     *                    <li>{@code 1}: tạo viền trái</li>
     *                    <li>{@code 0}: không tạo viền trái</li>
     *                    </ul>
     * @param right       {@code int}:
     *                    <ul>
     *                    <li>{@code 1}: tạo viền phải</li>
     *                    <li>{@code 0}: không tạo viền phải</li>
     *                    </ul>
     * @param borderStyle {@code BorderStyle}: kiểu viền muốn tạo
     * @param colorTop    {@code IndexedColors}: màu viền trên
     * @param colorBottom {@code IndexedColors}: màu viền dưới
     * @param colorLeft   {@code IndexedColors}: màu viền trái
     * @param colorRight  {@code IndexedColors}: màu viền phải
     * @return {@code XSSFCellStyle}: style viền được cấu hình dựa trên các tham sô
     *         truyền vào
     */
    private XSSFCellStyle createBordersExcel(int top, int bottom, int left, int right, BorderStyle borderStyle,
            IndexedColors colorTop, IndexedColors colorBottom, IndexedColors colorLeft, IndexedColors colorRight) {
        XSSFCellStyle style = workbook.createCellStyle();
        if (top == 1) {
            style.setBorderTop(borderStyle);
            style.setTopBorderColor(colorTop.getIndex());
        }
        if (bottom == 1) {
            style.setBorderBottom(borderStyle);
            style.setBottomBorderColor(colorBottom.getIndex());
        }
        if (left == 1) {
            style.setBorderLeft(borderStyle);
            style.setLeftBorderColor(colorLeft.getIndex());
        }
        if (right == 1) {
            style.setBorderRight(borderStyle);
            style.setRightBorderColor(colorRight.getIndex());
        }
        return style;
    }

    /**
     * làm trắng và bỏ qua dòng được chọn
     * 
     * @param rowIndex      {@code int}: vị trí của dòng muốn làm trắng và bỏ qua
     * @param addCellHeight {@code int}: cộng thêm chiều cao cho cột, Nếu bỏ qua thì
     *                      truyền vào {@code 0}
     */
    private void skipRowExcel(int rowIndex, int addCellHeight) {
        int cellIndex = 0;
        createRow("", rowIndex, cellIndex, rowHeight + addCellHeight, 11, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), excelAlignCenter);
        removeBorders(cellIndex);
    }

    /**
     * Tạo nhanh 1 dòng thông tin hóa đơn trong excel
     * 
     * @param rowIndex {@code int}: vị trí của dòng muốn tạo
     * @param label    {@code String}: nội dung của dòng (ví dụ: mã hóa đơn, thu
     *                 ngân, ...)
     * @param value    {@code String}: giá trị của dòng
     * @param fontSize {@code int}: cỡ chữ của dòng
     */
    private void createRowExcelBillInfo(int rowIndex, String label, String value, int fontSize) {
        int cellIndex = 0;
        XSSFCellStyle cellStyle = createBordersExcel(1, 1, 1, 1, borderStyleThin);
        XSSFCellStyle cellStyleMargin = createBordersExcel(1, 1, 1, 1, borderStyleThin);
        cellStyleMargin.setAlignment(excelAlignLeft);
        cellStyleMargin.setIndention((short) 3);
        XSSFCellStyle cellLastStyle = createBordersExcel(1, 1, 1, 0, borderStyleThin);
        createRow(label, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, excelAlignLeft);
        createCell("", 1, fontSize, false, false, true, excelAlignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(value, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
    }

    /**
     * làm trắng viền của ô được chọn
     * 
     * @param cellIndex {@code int}: vị trí của ô muốn làm trắng
     */
    private void removeBorders(int cellIndex) {
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, borderStyleThin));
        createCell("", cellIndex + 2, createBordersExcel(1, 1, 1, 1, borderStyleThin));
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
    }

    /**
     * Tạo phần header cho hóa đơn trên file excel
     * 
     * @param rowIndex {@code int}: vị trí của dòng bắt đầu tạo
     * @return {@code int}: vị trí của dòng cuối cùng + 1
     */
    private int showHeaderExcel(int rowIndex) {
        int fontSize = 11;
        int cellIndex = 0;
        // tên quán
        createRow(karaokeName, rowIndex, cellIndex, rowHeight + 300, 18, true, false, false,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), excelAlignCenter);
        removeBorders(cellIndex);
        ++rowIndex; // 1

        // địa chỉ
        createRow(address, rowIndex, cellIndex, rowHeight + 50, fontSize, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), excelAlignCenter);
        removeBorders(cellIndex);
        ++rowIndex; // 2

        // sdt
        createRow(phoneNumber, rowIndex, cellIndex, rowHeight, fontSize, false, false, false,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), excelAlignCenter);
        removeBorders(cellIndex);
        return ++rowIndex; // 3
    }

    /**
     * Tạo phần thông tin hóa đơn trên file excel
     * 
     * @param bill     {@code HoaDon}: Thông tin hóa đơn cần hiển thị
     * @param rowIndex {@code int}: vị trí của dòng bắt đầu tạo
     * @return {@code int}: vị trí của dòng cuối cùng + 1
     */
    private int showBillInfoExcel(HoaDon bill, int rowIndex) {
        int fontSize = 11;
        int cellIndex = 0;
        // skip row
        skipRowExcel(rowIndex, -150);
        ++rowIndex; // 4

        // tên quán
        String billName = "HOÁ ĐƠN TÍNH TIỀN";
        createRow(billName, rowIndex, cellIndex, rowHeight + 150, 18, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), excelAlignCenter);
        removeBorders(cellIndex);
        ++rowIndex; // 5

        // skip row
        skipRowExcel(rowIndex, -150);
        ++rowIndex; // 6

        // mã hóa đơn
        String billLabel = "Mã hóa đơn:";
        String billId = bill.getMaHoaDon() + "";
        createRowExcelBillInfo(rowIndex, billLabel, billId, fontSize);
        ++rowIndex; // 7

        // tên nhân viên
        String staffNameLabel = "Thu ngân:";
        String staffName = bill.getNhanVien().getHoTen();
        createRowExcelBillInfo(rowIndex, staffNameLabel, staffName, fontSize);
        ++rowIndex; // 8

        // tên khách hàng
        String customerNameLabel = "Tên khách hàng:";
        String customerName = bill.getKhachHang().getHoTen();
        createRowExcelBillInfo(rowIndex, customerNameLabel, customerName, fontSize);
        ++rowIndex; // 9

        // tên số phòng
        String roomIdLabel = "Số phòng:";
        String roomId = bill.getPhong().getMaPhong();
        createRowExcelBillInfo(rowIndex, roomIdLabel, roomId, fontSize);

        ++rowIndex; // 10

        // tên loại phòng
        String roomTypeNameLabel = "Loại phòng:";
        LoaiPhong roomType = bill.getPhong().getLoaiPhong();
        String roomTypeName = roomType.getTenLP();
        createRowExcelBillInfo(rowIndex, roomTypeNameLabel, roomTypeName, fontSize);
        ++rowIndex; // 11

        // Giá phòng
        String roomTypePriceLabel = "Giá phòng:";
        String roomTypePrice = df.format(roomType.getGiaTien()) + "đ/Giờ";
        createRowExcelBillInfo(rowIndex, roomTypePriceLabel, roomTypePrice, fontSize);

        ++rowIndex; // 12

        // thời gian bắt đầu hát
        String startTimeLabel = "Giờ bắt đầu:";
        String startTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(), formatTime);
        createRowExcelBillInfo(rowIndex, startTimeLabel, startTime, fontSize);
        ++rowIndex; // 13

        // thời gian kết thúc hát
        String endTimeLabel = "Giờ kết thúc:";
        String endTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioTra(), formatTime);
        createRowExcelBillInfo(rowIndex, endTimeLabel, endTime, fontSize);
        ++rowIndex; // 14

        // thời gian hát
        String usedTime = convertRentalTime(bill.tinhGioThue());
        String hoursLabel = "Thời gian:";
        createRowExcelBillInfo(rowIndex, hoursLabel, usedTime, fontSize);
        ++rowIndex; // 15

        // skip row
        skipRowExcel(rowIndex, -160);
        ++rowIndex; // 16
        return rowIndex;
    }

    /**
     * Tạo thanh tiêu đề của dịch vụ đã đặt
     * 
     * @param rowIndex {@code int}: vị trí của dòng bắt đầu tạo
     * @return {@code int}: vị trí của dòng cuối cùng + 1
     */
    private int showServiceOrderHeaderExcel(int rowIndex) {
        row = sheet.createRow(rowIndex);

        cell = row.createCell(0, CellType.STRING);
        XSSFCellStyle styleR0 = createBordersExcel(1, 1, 1, 1, borderStyleThick, excelWhiteColor, excelWhiteColor,
                excelWhiteColor, excelWhiteColor);
        styleR0.setAlignment(excelAlignLeft);
        cell.setCellStyle(styleR0);
        cell.setCellValue("Tên");

        XSSFCellStyle styleR1 = createBordersExcel(1, 1, 1, 1, borderStyleThick, excelWhiteColor, excelWhiteColor,
                excelWhiteColor, excelWhiteColor);
        styleR1.setAlignment(excelAlignRight);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellStyle(styleR1);
        cell.setCellValue("SL");

        cell = row.createCell(2, CellType.STRING);
        cell.setCellStyle(styleR1);
        cell.setCellValue("Đơn giá");

        XSSFCellStyle styleR3 = createBordersExcel(1, 1, 1, 0, borderStyleThick, excelWhiteColor, excelWhiteColor,
                excelWhiteColor, excelWhiteColor);
        styleR3.setAlignment(excelAlignRight);
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("T.Tiền");
        cell.setCellStyle(styleR3);
        ++rowIndex; // 17

        row = sheet.createRow(rowIndex);
        XSSFCellStyle styleBorderBottom = createBordersExcel(1, 1, 1, 1, borderStyleThick, excelWhiteColor,
                excelBlackColor, excelWhiteColor, excelWhiteColor);
        row.setHeight((short) 40);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 3));
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(styleBorderBottom);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellStyle(styleBorderBottom);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellStyle(styleBorderBottom);

        XSSFCellStyle styleBottomRight = createBordersExcel(1, 1, 1, 0, borderStyleThick, excelWhiteColor,
                excelBlackColor, excelWhiteColor, excelWhiteColor);
        cell = row.createCell(3, CellType.STRING);
        cell.setCellStyle(styleBottomRight);
        ++rowIndex; // 18
        return rowIndex;
    }

    /**
     * Hiển thị thông tin các dịch vụ đã đặt
     * 
     * @param billInfoList {@code ArrayList<CTDichVu>}: danh sách thông tin các dịch
     *                     vụ đã đặt
     * @param rowIndex     {@code int}: vị trí của dòng bắt đầu tạo
     * @return {@code int}: vị trí của dòng cuối cùng + 1
     */
    private int showServiceOrderExcel(HoaDon bill, int rowIndex) {
        List<CTDichVu> billInfoList = bill.getDsCTDichVu();
        DataFormat dataFormat = workbook.createDataFormat();
        if (billInfoList != null) {
            int lastIndex = billInfoList.size();
            for (int i = 0; i <= lastIndex; i++) {
                row = sheet.createRow(rowIndex + i);
                if (i == 0)
                    row.setHeight((short) 410);
                if (i == lastIndex) {
                    XSSFCellStyle styleR0 = createBordersExcel(1, 1, 1, 1, borderStyleThick, excelWhiteColor,
                            excelBlackColor, excelWhiteColor, excelWhiteColor);
                    row.setHeight((short) 160);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex + lastIndex, rowIndex + lastIndex, 0, 3));
                    Cell cell = row.createCell(0, CellType.STRING);
                    cell.setCellStyle(styleR0);
                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellStyle(styleR0);
                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellStyle(styleR0);

                    XSSFCellStyle styleR3 = createBordersExcel(1, 1, 1, 0, borderStyleThick, excelWhiteColor,
                            excelBlackColor, excelWhiteColor, excelWhiteColor);
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellStyle(styleR3);
                } else {
                    CTDichVu billInfo = billInfoList.get(i);
                    Double price = billInfo.getDichVu().getGiaBan();
                    int quantityOrder = billInfo.getSoLuongDat();
                    Double totalPriceOrder = price * quantityOrder;

                    XSSFCellStyle styleR0 = createBordersExcel(1, 1, 1, 0, borderStyleThin);
                    Cell cell = row.createCell(0, CellType.STRING);
                    cell.setCellStyle(styleR0);
                    cell.setCellValue(billInfo.getDichVu().getTenDichVu());

                    XSSFCellStyle styleR1 = createBordersExcel(1, 1, 1, 0, borderStyleThin);
                    styleR1.setDataFormat(dataFormat.getFormat("#,###"));
                    styleR1.setAlignment(excelAlignRight);
                    cell = row.createCell(1, CellType.NUMERIC);
                    cell.setCellStyle(styleR1);
                    cell.setCellValue(quantityOrder);

                    cell = row.createCell(2, CellType.NUMERIC);
                    cell.setCellStyle(styleR1);
                    cell.setCellValue(price);

                    cell = row.createCell(3, CellType.NUMERIC);
                    cell.setCellStyle(styleR1);
                    cell.setCellValue(totalPriceOrder);
                }
            }
            rowIndex += lastIndex + 1;
        } else {
            // System.out.println("không tìm thấy hóa đơn");
        }
        return rowIndex;
    }

    /**
     * Hiển thị thông tin thanh toán hóa đơn
     * 
     * @param billInfoList {@code ArrayList<CTDichVu>}: danh sách thông tin các dịch
     *                     vụ đã đặt
     * @param bill         {@code HoaDon}: Thông tin hóa đơn
     * @param rowIndex     {@code int}: vị trí của dòng bắt đầu tạo
     * @return {@code int}: vị trí của dòng cuối cùng + 1
     */
    private int showTotalPriceExcel(HoaDon bill, int rowIndex) {
        int cellIndex = 0;
        DataFormat dataFormat = workbook.createDataFormat();
        Double totalPriceService = bill.tinhTongTienDichVu();
        Double totalPriceRoom = bill.tinhTienPhong();

        // tổng tiền dịch vụ
        String totalPriceServiceLabel = "Tổng tiền dịch vụ:";
        createRow(totalPriceServiceLabel, rowIndex, cellIndex, rowHeight + 150, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), excelAlignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(0, 1, 1, 1, borderStyleThin));

        XSSFCellStyle styleFormatNumber = createBordersExcel(0, 1, 1, 0, borderStyleThin);
        styleFormatNumber.setDataFormat(dataFormat.getFormat("#,###"));
        styleFormatNumber.setAlignment(excelAlignRight);
        createCell(totalPriceService, cellIndex + 2, styleFormatNumber, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
        ++rowIndex;

        // tổng tiền giờ
        String totalPriceRoomLabel = "Tổng tiền giờ:";
        createRow(totalPriceRoomLabel, rowIndex, cellIndex, rowHeight, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), excelAlignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, borderStyleThin));

        createCell(totalPriceRoom, cellIndex + 2, styleFormatNumber, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
        ++rowIndex;

        // VAT
        Double vat = (totalPriceRoom + totalPriceService) * 0.1;
        String vatLabel = "VAT(10%):";
        createRow(vatLabel, rowIndex, cellIndex, rowHeight, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), excelAlignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, borderStyleThin));

        createCell(vat, cellIndex + 2, styleFormatNumber, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
        ++rowIndex;

        // tổng hoá đơn
        Double totalPrice = bill.tinhTongTienHoaDon();
        String totalPriceBillLabel = "Tổng cộng:";
        createRow(totalPriceBillLabel, rowIndex, cellIndex, rowHeight + 50, 13, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), excelAlignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, borderStyleThin));

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 13);
        XSSFCellStyle styleBill = createBordersExcel(0, 1, 1, 0, borderStyleThin);
        styleBill.setAlignment(excelAlignRight);
        styleBill.setFont(font);
        styleBill.setDataFormat(dataFormat.getFormat("#,###"));

        createCell(totalPrice, cellIndex + 2, styleBill, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
        ++rowIndex;
        return rowIndex;
    }

    /**
     * Tạo chân trang hóa đơn
     * 
     * @param rowIndex {@code int}: vị trí của dòng bắt đầu tạo
     * @return {@code int}: vị trí của dòng cuối cùng + 1
     */
    private int showFooterExcel(int rowIndex) {
        int cellIndex = 0;
        // skip row
        skipRowExcel(rowIndex, 0);
        ++rowIndex;

        // goodbye
        String message = "Quý khách vui lòng kiểm tra lại hoá đơn trước khi thanh toán. "
                + "\nXin cảm ơn và hẹn gặp lại quý khách";
        createRow(message, rowIndex, cellIndex, rowHeight * 2, 11, false, true, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), excelAlignCenter);
        removeBorders(cellIndex);
        ++rowIndex;
        return rowIndex;
    }

    /**
     * Tạo file excel
     * 
     * @param path     {@code String}: đường dẫn đến thư mục nơi lưu file excel
     * @param rowIndex {@code int}: vị trí của dòng cuối hóa đơn
     */
    private void writeFileExcel(String path, int rowIndex) {
        int cellIndex = 0;
        try {
            // skip row
            createRow("", rowIndex, cellIndex, rowHeight, 11, true, false, true,
                    new CellRangeAddress(rowIndex, rowIndex, 0, 3), excelAlignCenter);
            XSSFCellStyle styleR = createBordersExcel(1, 0, 1, 1, borderStyleThin, excelWhiteColor,
                    IndexedColors.GREY_25_PERCENT, excelWhiteColor, excelWhiteColor);
            row.setHeight((short) 200);
            Cell cell = row.createCell(0);
            cell.setCellStyle(styleR);
            cell = row.createCell(1);
            cell.setCellStyle(styleR);
            cell = row.createCell(2);
            cell.setCellStyle(styleR);

            XSSFCellStyle styleR3 = createBordersExcel(1, 0, 1, 0, borderStyleThick, excelWhiteColor, excelBlackColor,
                    excelWhiteColor, excelWhiteColor);
            cell = row.createCell(3);
            cell.setCellStyle(styleR3);

            FileOutputStream out = new FileOutputStream(new File(path));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xuất file excel đến thư mục đã chỉ định, tên file sẽ là mã hóa đơn
     * 
     * @param billId {@code int}: mã hóa đơn
     * @param path   {@code String}: đường dẫn đến thư mục nơi lưu file excel
     */
    public boolean exportBillToExcel(HoaDon bill, String path) {
        String fileName = bill.getMaHoaDon() + ".xlsx";
        if (!path.matches("^.+[\\\\/]$")) {
            path += "/";
        }
        String filePath = path + fileName;
        workbook = new XSSFWorkbook();

        sheet = workbook.createSheet("Hóa Đơn");
        // sheet.setDisplayGridlines(false);
        sheet.setColumnWidth(0, 6500); // Cột Tên dịch vụ
        sheet.setColumnWidth(1, 2300); // Cột số lượng
        sheet.setColumnWidth(2, 3200); // Cột đơn giá
        sheet.setColumnWidth(3, 3000); // Cột thành tiền
        sheet.setColumnWidth(4, 500);

        int rowIndex = 0;
        rowIndex = showHeaderExcel(rowIndex);
        rowIndex = showBillInfoExcel(bill, rowIndex);
        rowIndex = showServiceOrderHeaderExcel(rowIndex);
        rowIndex = showServiceOrderExcel(bill, rowIndex);
        rowIndex = showTotalPriceExcel(bill, rowIndex);
        rowIndex = showFooterExcel(rowIndex);
        writeFileExcel(filePath, rowIndex);
        return true;
    }

    /**
     * tạo khoản trắng trên file pdf
     * 
     * @return {@code Paragraph}: dòng khoản trắng
     */
    private Paragraph skipRowPdf() {
        Font skipFont = new Font(baseFontLight, 5);
        Chunk chunkSkip = new Chunk(" ", skipFont);
        Paragraph pSkip = new Paragraph(chunkSkip);
        return pSkip;
    }

    /**
     * tạo 1 dòng thông tin hóa đơn
     * 
     * @param label       {@code String}: tên của dòng
     * @param value       {@code String}: giá trị của dòng
     * @param numberOfTab {@code int}: số tab cần thêm
     * @return {@code Paragraph}: dòng thông tin hóa đơn
     */
    private Paragraph createRowBillInfoPdf(String label, String value, int numberOfTab) {
        Font font = new Font(baseFontLight, 15);

        Chunk chunk = new Chunk(label, font);
        Paragraph paragraph = new Paragraph(chunk);
        for (int i = 0; i < numberOfTab; i++) {
            paragraph.add(Chunk.TABBING);
        }
        paragraph.add(value);
        return paragraph;
    }

    /**
     * Tạo 1 ô chứa thông tin dịch vụ đã đặt
     * 
     * @param value             {@code String}: giá trị của dòng
     * @param borderWidthBottom {@code int}: độ rộng của viền dưới
     * @param paddingBottom     {@code int}: khoảng cách dưới so với chữ
     * @param font              {@code Font}: font chữ
     * @param align             {@code int}: căn lề của dòng
     *                          <ul>
     *                          <li>Căn trái thì truyền vào {@code 0} hoặc dùng biến
     *                          {@code pdfAlignLeft}</li>
     *                          <li>Căn giữa thì truyền vào {@code 1} hoặc dùng biến
     *                          {@code pdfAlignCenter}</li>
     *                          <li>Căn phải thì truyền vào {@code 2} hoặc dùng biến
     *                          {@code pdfAlignRight}</li>
     *                          </ul>
     * @return {@code PdfPCell}: ô thông tin dịch vụ đã đặt
     */
    private PdfPCell createCellServiceOrder(String value, int borderWidthBottom, int paddingBottom, Font font,
            int align) {
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(borderWidthBottom);
        cell.setPaddingBottom(paddingBottom);
        cell.setHorizontalAlignment(align);
        return cell;
    }

    /**
     * Tạo phần header cho hóa đơn trên file pdf
     * 
     * @param doc {@code Document}: document xử lý thông tin hóa đơn
     */
    private void showHeaderPdf(Document doc) {
        try {
            // karaoke name
            Font fontKaraokeName = new Font(baseFontMedium, 20);
            Chunk chunkKaraokeName = new Chunk(karaokeName, fontKaraokeName);
            Paragraph pKaraokeName = new Paragraph(chunkKaraokeName);
            pKaraokeName.setAlignment(pdfAlignCenter);
            doc.add(pKaraokeName);

            // address
            Font font = new Font(baseFontLight, 15);
            Chunk chunkAddress = new Chunk(address, font);
            Paragraph pAddress = new Paragraph(chunkAddress);
            pAddress.setAlignment(pdfAlignCenter);
            doc.add(pAddress);

            // phone number
            Chunk chunkPhoneNumber = new Chunk(phoneNumber, font);
            Paragraph pPhoneNumber = new Paragraph(chunkPhoneNumber);
            pPhoneNumber.setAlignment(pdfAlignCenter);
            doc.add(pPhoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tạo phần thông tin hóa đơn trên file pdf
     * 
     * @param doc {@code Document}: document xử lý thông tin hóa đơn
     */
    private void showBillInfoPdf(HoaDon bill, Document doc) {
        try {
            // skip
            Paragraph pSkip = skipRowPdf();
            doc.add(pSkip);

            // bill name
            Font fontBillName = new Font(baseFontMedium, 20);
            Chunk chunkBillName = new Chunk("HÓA ĐƠN TÍNH TIỀN", fontBillName);
            Paragraph pBillName = new Paragraph(chunkBillName);
            pBillName.setAlignment(pdfAlignCenter);
            doc.add(pBillName);

            // skip
            doc.add(pSkip);

            // bill id
            Paragraph pBillId = createRowBillInfoPdf("Mã hóa đơn: ", bill.getMaHoaDon(), 5);
            doc.add(pBillId);

            // staff Name
            NhanVien staff = bill.getNhanVien();
            Paragraph pStaffName = createRowBillInfoPdf("Thu ngân: ", staff.getHoTen(), 6);
            doc.add(pStaffName);

            // customer Name
            KhachHang customer = bill.getKhachHang();
            Paragraph pCustomerName = createRowBillInfoPdf("Tên khách hàng: ", customer.getHoTen(), 4);
            doc.add(pCustomerName);

            // room id
            Paragraph pRoomId = createRowBillInfoPdf("Số phòng: ", bill.getPhong().getMaPhong(), 6);
            doc.add(pRoomId);

            // room type name
            LoaiPhong roomType = bill.getPhong().getLoaiPhong();
            Paragraph pRoomTypeName = createRowBillInfoPdf("Loại phòng: ", roomType.getTenLP(), 5);
            doc.add(pRoomTypeName);

            // room type price
            String roomTypePrice = df.format(roomType.getGiaTien()) + "đ/giờ";
            Paragraph pRoomTypePrice = createRowBillInfoPdf("Giá phòng: ", roomTypePrice, 5);
            doc.add(pRoomTypePrice);

            // start Time
            String startTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(), formatTime);
            Paragraph pStartTime = createRowBillInfoPdf("Giờ bắt đầu: ", startTime, 5);
            doc.add(pStartTime);

            // end Time
            String endTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioTra(), formatTime);
            Paragraph pEndTime = createRowBillInfoPdf("Giờ kết thúc: ", endTime, 5);
            doc.add(pEndTime);

            // used Time
            String usedTime = convertRentalTime(bill.tinhGioThue());
            Paragraph pUsedTime = createRowBillInfoPdf("Thời gian sử dụng: ", usedTime, 4);
            doc.add(pUsedTime);

            // skip
            doc.add(pSkip);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông tin các dịch vụ đã đặt
     * 
     * @param bill {@code HoaDon}: hóa đơn cần hiển thị danh sách dịch vụ
     * @param doc  {@code Document}: document xử lý thông tin hóa đơn
     */
    private void showServiceOrderPdf(List<CTDichVu> serviceOrders, Document doc) {
        int serviceOrdersLastIndex = serviceOrders.size();
        Font fontHeader = new Font(baseFontMedium, 15);
        Font fontContent = new Font(baseFontLight, 15);
        int borderWidthHeader = 2;
        int borderWidthContent = 0;
        int paddingBottom = 8;
        try {
            // skip
            Paragraph pSkip = skipRowPdf();
            doc.add(pSkip);

            // table Service Order
            PdfPTable table = new PdfPTable(4);
            table.setLockedWidth(false);
            float[] columnWidth = { 290f, 60f, 125f, 125f };
            table.setWidthPercentage(columnWidth, PageSize.A4);

            PdfPCell cell1 = createCellServiceOrder("Tên", borderWidthHeader, paddingBottom, fontHeader, pdfAlignLeft);
            table.addCell(cell1);

            PdfPCell cell2 = createCellServiceOrder("SL", borderWidthHeader, paddingBottom, fontHeader, pdfAlignRight);
            table.addCell(cell2);

            PdfPCell cell3 = createCellServiceOrder("Đơn Giá", borderWidthHeader, paddingBottom, fontHeader,
                    pdfAlignRight);
            table.addCell(cell3);

            PdfPCell cell4 = createCellServiceOrder("T.Tiền", borderWidthHeader, paddingBottom, fontHeader,
                    pdfAlignRight);
            table.addCell(cell4);

            for (int i = 0; i < serviceOrdersLastIndex; i++) {
                CTDichVu serviceOrder = serviceOrders.get(i);
                DichVu service = serviceOrder.getDichVu();
                if (i == serviceOrdersLastIndex - 1) {
                    borderWidthContent = 2;
                }
                cell1 = createCellServiceOrder(service.getTenDichVu(), borderWidthContent, paddingBottom, fontContent,
                        pdfAlignLeft);
                table.addCell(cell1);

                String quantityOrder = df.format(serviceOrder.getSoLuongDat());
                cell2 = createCellServiceOrder(quantityOrder, borderWidthContent, paddingBottom, fontContent,
                        pdfAlignRight);
                table.addCell(cell2);

                String price = df.format(serviceOrder.getDonGia());
                cell3 = createCellServiceOrder(price, borderWidthContent, paddingBottom, fontContent, pdfAlignRight);
                table.addCell(cell3);

                String totalPrice = df.format(serviceOrder.tinhTienDichVu());
                cell4 = createCellServiceOrder(totalPrice, borderWidthContent, paddingBottom, fontContent,
                        pdfAlignRight);
                table.addCell(cell4);
            }
            doc.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông tin thanh toán hóa đơn
     * 
     * @param bill {@code HoaDon}: hóa đơn cần hiển thị thông tin thanh toán
     * @param doc  {@code Document}: document xử lý thông tin hóa đơn
     */
    private void showTotalPricePdf(HoaDon bill, Document doc) {
        Font fontContent = new Font(baseFontLight, 15);
        int borderWidthContent = 0;
        int paddingBottom = 6;

        Double totalPriceService = bill.tinhTongTienDichVu();
        Double totalPriceRoom = bill.tinhTienPhong();
        Double vat = (totalPriceRoom + totalPriceService) * 0.1;
        Double totalPriceBill = bill.tinhTongTienHoaDon();
        String labels[] = { "Tổng tiền dịch vụ:", "Tổng tiền phòng:", "VAT(10%):", "Tổng cộng:" };
        String values[] = { df.format(totalPriceService), df.format(totalPriceRoom), df.format(vat),
                df.format(totalPriceBill) };
        try {
            // skip
            Paragraph pSkip = skipRowPdf();
            doc.add(pSkip);

            // bill id
            PdfPTable table = new PdfPTable(2);
            table.setLockedWidth(false);
            float[] columnWidth = { 300f, 300f };
            table.setWidthPercentage(columnWidth, PageSize.A4);

            int lastIndex = labels.length;
            for (int i = 0; i < lastIndex; i++) {
                if (i == lastIndex - 1) {
                    fontContent = new Font(baseFontMedium, 17);
                }
                PdfPCell cell1 = createCellServiceOrder(labels[i], borderWidthContent, paddingBottom, fontContent,
                        pdfAlignLeft);
                table.addCell(cell1);

                PdfPCell cell2 = createCellServiceOrder(values[i], borderWidthContent, paddingBottom, fontContent,
                        pdfAlignRight);
                table.addCell(cell2);
            }
            doc.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tạo chân trang hóa đơn
     * 
     * @param doc {@code Document}: document xử lý chân trang hóa đơn
     */
    private void showFooterPdf(Document doc) {
        Font font = new Font(baseFontLightItalic, 15);
        // skip
        String message = "Quý khách vui lòng kiểm tra lại hoá đơn trước khi thanh toán. "
                + "\nXin cảm ơn và hẹn gặp lại quý khách";
        try {
            Paragraph pSkip = skipRowPdf();
            doc.add(pSkip);

            Chunk chunk = new Chunk(message, font);
            Paragraph pSeeYouAgain = new Paragraph(chunk);
            pSeeYouAgain.setAlignment(pdfAlignCenter);
            doc.add(pSeeYouAgain);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xuất file pdf hóa đơn dựa trong mã hóa đơn và đường dẫn đến nơi xuất file
     * 
     * @param billId {@code String}: mã hóa đơn
     * @param path   {@code String}: đường dẫn đến file pdf
     */
    public boolean exportBillToPdf(HoaDon bill, String path) {
        String fileName = bill.getMaHoaDon() + ".pdf";
        if (!path.matches("^.+[\\\\/]$")) {
            path += "/";
        }

        String filePath = path + fileName;
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            baseFontMedium = BaseFont.createFont(pdfFontMedium, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            baseFontLight = BaseFont.createFont(pdfFontLight, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            baseFontLightItalic = BaseFont.createFont(pdfFontLightItalic, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            showHeaderPdf(document);
            showBillInfoPdf(bill, document);
            showServiceOrderPdf(bill.getDsCTDichVu(), document);
            showTotalPricePdf(bill, document);
            showFooterPdf(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
