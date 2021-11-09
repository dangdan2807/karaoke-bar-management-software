package DAO;

import java.io.*;
import java.util.*;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import entity.*;

public class ExportBill {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row = null;
    private Cell cell = null;
    private int rowHeight = 300;
    private IndexedColors whiteColor = IndexedColors.WHITE;
    private IndexedColors blackColor = IndexedColors.BLACK;
    private HorizontalAlignment alignCenter = HorizontalAlignment.CENTER;
    private HorizontalAlignment alignRight = HorizontalAlignment.RIGHT;
    private HorizontalAlignment alignLeft = HorizontalAlignment.LEFT;
    private BorderStyle borderStyleThin = BorderStyle.THIN;
    private BorderStyle borderStyleThick = BorderStyle.THICK;

    private static ExportBill instance = new ExportBill();

    public static ExportBill getInstance() {
        if (instance == null)
            instance = new ExportBill();
        return instance;
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
        style.setBottomBorderColor(whiteColor.getIndex());
        style.setBorderTop(borderStyleThin);
        style.setTopBorderColor(whiteColor.getIndex());
        style.setBorderLeft(borderStyleThin);
        style.setLeftBorderColor(whiteColor.getIndex());
        style.setBorderRight(borderStyleThin);
        style.setRightBorderColor(whiteColor.getIndex());

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
    private void createRow(String textCell, int rowIndex, int cellIndex, int rowHeight, int fontSize,
            boolean bold, boolean italic, boolean wrapText, CellRangeAddress merge, HorizontalAlignment align) {
        XSSFFont font = workbook.createFont();
        font.setBold(bold);
        font.setItalic(italic);
        font.setFontHeightInPoints((short) fontSize);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(wrapText);
        style.setAlignment(align);
        style.setBorderBottom(borderStyleThin);
        style.setBottomBorderColor(whiteColor.getIndex());
        style.setBorderTop(borderStyleThin);
        style.setTopBorderColor(whiteColor.getIndex());
        style.setBorderLeft(borderStyleThin);
        style.setLeftBorderColor(whiteColor.getIndex());
        style.setBorderRight(borderStyleThin);
        style.setRightBorderColor(whiteColor.getIndex());

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
            style.setTopBorderColor(whiteColor.getIndex());
        }
        if (bottom == 1) {
            style.setBorderBottom(borderStyle);
            style.setBottomBorderColor(whiteColor.getIndex());
        }
        if (left == 1) {
            style.setBorderLeft(borderStyle);
            style.setLeftBorderColor(whiteColor.getIndex());
        }
        if (right == 1) {
            style.setBorderRight(borderStyle);
            style.setRightBorderColor(whiteColor.getIndex());
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
    private void skipRow(int rowIndex, int addCellHeight) {
        int cellIndex = 0;
        createRow("", rowIndex, cellIndex, rowHeight + addCellHeight, 11, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), alignCenter);
        removeBorders(cellIndex);
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
    private int createHeaderExcel(int rowIndex) {
        int fontSize = 11;
        int cellIndex = 0;
        // tên quán
        String karaokeName = "KARAOKE DASH";
        createRow(karaokeName, rowIndex, cellIndex, rowHeight + 300, 18, true, false, false,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), alignCenter);
        removeBorders(cellIndex);
        ++rowIndex; // 1

        // địa chỉ
        String address = "12 Nguyễn Văn Bảo, Phường 4, Gò Vấp \nThành phố Hồ Chí Minh";
        createRow(address, rowIndex, cellIndex, rowHeight + 300, fontSize, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), alignCenter);
        removeBorders(cellIndex);
        ++rowIndex; // 2

        // sdt
        String phoneNumber = "0113114115";
        createRow(phoneNumber, rowIndex, cellIndex, rowHeight, fontSize, false, false, false,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), alignCenter);
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
    private int createBillInfoExcel(HoaDon bill, int rowIndex) {
        int fontSize = 11;
        int cellIndex = 0;
        String formatTime = "hh:mm:ss dd/MM/yyyy";
        XSSFCellStyle cellStyle = createBordersExcel(1, 1, 1, 1, borderStyleThin);
        XSSFCellStyle cellStyleMargin = createBordersExcel(1, 1, 1, 1, borderStyleThin);
        cellStyleMargin.setAlignment(alignLeft);
        cellStyleMargin.setIndention((short) 3);
        XSSFCellStyle cellLastStyle = createBordersExcel(1, 1, 1, 0, borderStyleThin);

        // skip row
        skipRow(rowIndex, -150);
        ++rowIndex; // 4

        // tên quán
        String billName = "HOÁ ĐƠN TÍNH TIỀN";
        createRow(billName, rowIndex, cellIndex, rowHeight + 150, 18, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), alignCenter);
        removeBorders(cellIndex);
        ++rowIndex; // 5

        // skip row
        skipRow(rowIndex, -150);
        ++rowIndex; // 6

        // mã hóa đơn
        String billLabel = "Mã hóa đơn:";
        createRow(billLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, alignLeft);
        String billId = bill.getMaHoaDon() + "";
        createCell("", 1, fontSize, false, false, true, alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(billId, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
        ++rowIndex; // 7

        // tên nhân viên
        String staffNameLabel = "Thu ngân:";
        createRow(staffNameLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, alignLeft);
        String staffName = bill.getNhanVien().getHoTen();
        createCell("", 1, fontSize, false, false, true, alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(staffName, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
        ++rowIndex; // 8

        // tên khách hàng
        String customerNameLabel = "Tên khách hàng:";
        createRow(customerNameLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, alignLeft);
        String customerName = bill.getKhachHang().getHoTen();
        createCell("", 1, fontSize, false, false, true, alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(customerName, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
        ++rowIndex; // 9

        // tên số phòng
        String roomIdLabel = "Số phòng:";
        createRow(roomIdLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, alignLeft);
        String roomId = bill.getPhong().getMaPhong();
        createCell("", 1, fontSize, false, false, true, alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(roomId, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
        ++rowIndex; // 10

        // tên loại phòng
        String roomTypeNameLabel = "Loại phòng:";
        createRow(roomTypeNameLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, alignLeft);
        String roomTypeName = bill.getPhong().getLoaiPhong().getTenLP();
        createCell("", 1, fontSize, false, false, true, alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(roomTypeName, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
        ++rowIndex; // 11

        // thời gian bắt đầu hát
        String startTimeLabel = "Giờ bắt đầu:";
        createRow(startTimeLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, alignLeft);
        String startTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(), formatTime);
        createCell("", 1, fontSize, false, false, true, alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(startTime, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
        ++rowIndex; // 12

        // thời gian kết thúc hát
        String endTimeLabel = "Giờ kết thúc:";
        createRow(endTimeLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, alignLeft);
        String endTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioTra(), formatTime);
        createCell("", 1, fontSize, false, false, true, alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(endTime, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
        ++rowIndex; // 13

        // thời gian hát
        double hours = bill.tinhGioThue();
        int minutes = (int) (hours % 1 * 60);
        int hoursInt = (int) hours;
        String minutesStr = minutes < 10 ? "0" + minutes : minutes + "";
        String hoursStr = hoursInt < 10 ? "0" + hoursInt : hoursInt + "";
        String time = String.format("%s giờ %s phút", hoursStr, minutesStr);
        String hoursLabel = "Thời gian:";

        createRow(hoursLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null, alignLeft);
        createCell("", 1, fontSize, false, false, true, alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(time, cellIndex + 1, cellStyleMargin);
        createCell("", cellIndex + 2, cellStyle);
        createCell("", cellIndex + 3, cellLastStyle);
        ++rowIndex; // 14

        // skip row
        skipRow(rowIndex, -160);
        ++rowIndex; // 15
        return rowIndex;
    }

    /**
     * Tạo thanh tiêu đề của dịch vụ đã đặt
     * 
     * @param rowIndex {@code int}: vị trí của dòng bắt đầu tạo
     * @return {@code int}: vị trí của dòng cuối cùng + 1
     */
    private int createServiceOrderHeaderExcel(int rowIndex) {
        row = sheet.createRow(rowIndex);

        cell = row.createCell(0, CellType.STRING);
        XSSFCellStyle styleR0 = createBordersExcel(1, 1, 1, 1, borderStyleThick, whiteColor, whiteColor, whiteColor,
                whiteColor);
        styleR0.setAlignment(alignLeft);
        cell.setCellStyle(styleR0);
        cell.setCellValue("Tên dịch vụ");

        XSSFCellStyle styleR1 = createBordersExcel(1, 1, 1, 1, borderStyleThick, whiteColor, whiteColor, whiteColor,
                whiteColor);
        styleR1.setAlignment(alignRight);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellStyle(styleR1);
        cell.setCellValue("SL");

        cell = row.createCell(2, CellType.STRING);
        cell.setCellStyle(styleR1);
        cell.setCellValue("Đơn giá");

        XSSFCellStyle styleR3 = createBordersExcel(1, 1, 1, 0, borderStyleThick, whiteColor, whiteColor, whiteColor,
                whiteColor);
        styleR3.setAlignment(alignRight);
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("T.Tiền");
        cell.setCellStyle(styleR3);
        ++rowIndex;

        row = sheet.createRow(rowIndex);
        XSSFCellStyle styleBorderBottom = createBordersExcel(1, 1, 1, 1, borderStyleThick, whiteColor, blackColor,
                whiteColor, whiteColor);
        row.setHeight((short) 40);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 3));
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(styleBorderBottom);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellStyle(styleBorderBottom);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellStyle(styleBorderBottom);

        XSSFCellStyle styleBottomRight = createBordersExcel(1, 1, 1, 0, borderStyleThick, whiteColor, blackColor,
                whiteColor, whiteColor);
        cell = row.createCell(3, CellType.STRING);
        cell.setCellStyle(styleBottomRight);
        ++rowIndex;
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
                    XSSFCellStyle styleR0 = createBordersExcel(1, 1, 1, 1, borderStyleThick, whiteColor, blackColor,
                            whiteColor, whiteColor);
                    row.setHeight((short) 160);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex + lastIndex, rowIndex + lastIndex, 0, 3));
                    Cell cell = row.createCell(0, CellType.STRING);
                    cell.setCellStyle(styleR0);
                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellStyle(styleR0);
                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellStyle(styleR0);

                    XSSFCellStyle styleR3 = createBordersExcel(1, 1, 1, 0, borderStyleThick, whiteColor, blackColor,
                            whiteColor, whiteColor);
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
                    styleR1.setAlignment(alignRight);
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
            System.out.println("không tìm thấy hóa đơn");
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
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(0, 1, 1, 1, borderStyleThin));

        XSSFCellStyle styleFormatNumber = createBordersExcel(0, 1, 1, 0, borderStyleThin);
        styleFormatNumber.setDataFormat(dataFormat.getFormat("#,###"));
        styleFormatNumber.setAlignment(alignRight);
        createCell(totalPriceService, cellIndex + 2, styleFormatNumber, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
        ++rowIndex;

        // tổng tiền giờ
        String totalPriceRoomLabel = "Tổng tiền giờ:";
        createRow(totalPriceRoomLabel, rowIndex, cellIndex, rowHeight, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, borderStyleThin));

        createCell(totalPriceRoom, cellIndex + 2, styleFormatNumber, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
        ++rowIndex;

        // VAT
        Double vat = (totalPriceRoom + totalPriceService) * 0.1;
        String vatLabel = "VAT(10%):";
        createRow(vatLabel, rowIndex, cellIndex, rowHeight, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, borderStyleThin));

        createCell(vat, cellIndex + 2, styleFormatNumber, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
        ++rowIndex;

        // tổng hoá đơn
        Double totalPrice = bill.tinhTongTienHoaDon();
        String totalPriceBillLabel = "Tổng hoá đơn:";
        createRow(totalPriceBillLabel, rowIndex, cellIndex, rowHeight + 50, 13, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), alignLeft);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, borderStyleThin));

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 13);
        XSSFCellStyle styleBill = createBordersExcel(0, 1, 1, 0, borderStyleThin);
        styleBill.setAlignment(alignRight);
        styleBill.setFont(font);
        styleBill.setDataFormat(dataFormat.getFormat("#,###"));

        createCell(totalPrice, cellIndex + 2, styleBill, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, borderStyleThin));
        ++rowIndex;
        return rowIndex;
    }

    /**
     * 
     * @param rowIndex {@code int}: vị trí của dòng bắt đầu tạo
     * @param room     {@code Phong}: thông tin phòng đã thuê
     * @return {@code int}: vị trí của dòng cuối cùng + 1
     */
    private int showFooterExcel(int rowIndex, Phong room) {
        int cellIndex = 0;
        // skip row
        skipRow(rowIndex, 0);
        ++rowIndex;

        // goodbye
        String message = "Quý khách vui lòng kiểm tra lại hoá đơn trước khi thanh toán. "
                + "\nXin cảm ơn và hẹn gặp lại quý khách";
        createRow(message, rowIndex, cellIndex, rowHeight * 2, 11, false, true, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), alignCenter);
        removeBorders(cellIndex);
        ++rowIndex;
        return rowIndex;
    }

    /**
     * Xuất file excel đã tạo
     * 
     * @param path     {@code String}: đường dẫn đến file excel
     * @param rowIndex {@code int}: vị trí của dòng cuối hóa đơn
     */
    private void writeFileExcel(String path, int rowIndex) {
        int cellIndex = 0;
        try {
            // skip row
            createRow("", rowIndex, cellIndex, rowHeight, 11, true, false, true,
                    new CellRangeAddress(rowIndex, rowIndex, 0, 3), alignCenter);
            XSSFCellStyle styleR = createBordersExcel(1, 0, 1, 1, borderStyleThin, whiteColor,
                    IndexedColors.GREY_25_PERCENT, whiteColor, whiteColor);
            row.setHeight((short) 200);
            Cell cell = row.createCell(0);
            cell.setCellStyle(styleR);
            cell = row.createCell(1);
            cell.setCellStyle(styleR);
            cell = row.createCell(2);
            cell.setCellStyle(styleR);

            XSSFCellStyle styleR3 = createBordersExcel(1, 0, 1, 0, borderStyleThick, whiteColor, blackColor, whiteColor,
                    whiteColor);
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
     * Tạo file excel
     * 
     * @param billId {@code int}: mã hóa đơn
     * @param path   {@code String}: đường dẫn đến file excel
     */
    public void exportBillToExcel(String billId, String path) {
        HoaDon bill = HoaDonDAO.getInstance().getBillByBillId(billId);
        Phong room = PhongDAO.getInstance().getRoomByBillId(billId);
        bill.setPhong(room);
        NhanVien staff = NhanVienDAO.getInstance().getStaffByBillId(billId);
        bill.setNhanVien(staff);
        KhachHang customer = KhachHangDAO.getInstance().getCustomerByBillId(billId);
        bill.setKhachHang(customer);
        workbook = new XSSFWorkbook();

        sheet = workbook.createSheet("Hóa Đơn");
        // sheet.setDisplayGridlines(false);
        sheet.setColumnWidth(0, 6500); // Cột Tên dịch vụ
        sheet.setColumnWidth(1, 2300); // Cột số lượng
        sheet.setColumnWidth(2, 3200); // Cột đơn giá
        sheet.setColumnWidth(3, 3000); // Cột thành tiền
        sheet.setColumnWidth(4, 500);

        int rowIndex = 0;
        rowIndex = createHeaderExcel(rowIndex);
        rowIndex = createBillInfoExcel(bill, rowIndex);
        rowIndex = createServiceOrderHeaderExcel(rowIndex);
        ArrayList<CTDichVu> billInfoList = CTDichVuDAO.getInstance().getServiceDetailListByBillId(billId);
        bill.setDsCTDichVu(billInfoList);
        rowIndex = showServiceOrderExcel(bill, rowIndex);
        rowIndex = showTotalPriceExcel(bill, rowIndex);
        rowIndex = showFooterExcel(rowIndex, bill.getPhong());
        writeFileExcel(path, rowIndex);
    }

    /**
     * Xuất file pdf hóa đơn dựa trong mã hóa đơn và đường dẫn đến nơi xuất file
     * 
     * @param billId {@code String}: mã hóa đơn
     * @param path   {@code String}: đường dẫn đến file pdf
     */
    public void exportBillToPdf(String billId, String path) {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();
            Paragraph paraKaraokeName = new Paragraph("KARAOKE DASH");
            paraKaraokeName.setAlignment(Element.ALIGN_CENTER);
            Font font = new Font();
            font.setSize(100);
            font.setStyle(Font.BOLD);
            paraKaraokeName.setFont(font);

            doc.add(paraKaraokeName);
            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
