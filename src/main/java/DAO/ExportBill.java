package DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entity.CTDichVu;
import entity.HoaDon;
import entity.Phong;

public class ExportBill {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row = null;
    private Cell cell = null;
    private int rowHeight = 300;
    private static ExportBill instance = new ExportBill();
    private DecimalFormat df = new DecimalFormat("#,###.##");

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
        cell.setCellValue(df.format(text));
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
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());

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
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());

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
            style.setTopBorderColor(IndexedColors.WHITE.getIndex());
        }
        if (bottom == 1) {
            style.setBorderBottom(borderStyle);
            style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        }
        if (left == 1) {
            style.setBorderLeft(borderStyle);
            style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        }
        if (right == 1) {
            style.setBorderRight(borderStyle);
            style.setRightBorderColor(IndexedColors.WHITE.getIndex());
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
     * @param rowIndex {@code int}: vị trí của dòng muốn làm trắng và bỏ qua
     */
    private void skipRow(int rowIndex) {
        int cellIndex = 0;
        createRow("", rowIndex, cellIndex, rowHeight, 11, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.CENTER);
        removeBorders(cellIndex);
    }

    /**
     * làm trắng viền của ô được chọn
     * 
     * @param cellIndex {@code int}: vị trí của ô muốn làm trắng
     */
    private void removeBorders(int cellIndex) {
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 2, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
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
        String karaokeName = "Karaoke DASH";
        createRow(karaokeName, rowIndex, cellIndex, rowHeight + 300, 18, true, false, false,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.CENTER);
        removeBorders(cellIndex);
        ++rowIndex; // 1

        // địa chỉ
        String address = "12 Nguyễn Văn Bảo, Phường 4, Gò Vấp \nThành phố Hồ Chí Minh";
        createRow(address, rowIndex, cellIndex, rowHeight + 300, fontSize, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.CENTER);
        removeBorders(cellIndex);
        ++rowIndex; // 2

        // sdt
        String phoneNumber = "0113114115";
        createRow(phoneNumber, rowIndex, cellIndex, rowHeight, fontSize, false, false, false,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.CENTER);
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

        // skip row
        skipRow(rowIndex);
        ++rowIndex; // 4

        // tên quán
        String billName = "HOÁ ĐƠN TÍNH TIỀN \nPHÒNG " + bill.getPhong().getMaPhong();
        createRow(billName, rowIndex, cellIndex, rowHeight + 600, 18, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.CENTER);
        removeBorders(cellIndex);
        ++rowIndex; // 5

        // skip row
        skipRow(rowIndex);
        ++rowIndex; // 6

        // mã hóa đơn
        String billLabel = "Mã hóa đơn:";
        createRow(billLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null,
                HorizontalAlignment.LEFT);
        String billId = bill.getMaHoaDon() + "";
        createCell("", 1, fontSize, false, false, true, HorizontalAlignment.LEFT);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(billId, cellIndex + 1, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 2, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
        ++rowIndex; // 7

        // tên nhân viên
        String staffNameLabel = "Thu ngân:";
        createRow(staffNameLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null,
                HorizontalAlignment.LEFT);
        String staffName = bill.getNhanVien().getHoTen();
        createCell("", 1, fontSize, false, false, true, HorizontalAlignment.LEFT);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(staffName, cellIndex + 1, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 2, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
        ++rowIndex; // 8

        // thời gian bắt đầu hát
        String startTimeLabel = "Giờ bắt đầu:";
        createRow(startTimeLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null,
                HorizontalAlignment.LEFT);
        String startTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioDat(), formatTime);
        createCell("", 1, fontSize, false, false, true, HorizontalAlignment.LEFT);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(startTime, cellIndex + 1, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 2, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
        ++rowIndex; // 9

        // thời gian kết thúc hát
        String endTimeLabel = "Giờ kết thúc:";
        createRow(endTimeLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null,
                HorizontalAlignment.LEFT);
        String endTime = ConvertTime.getInstance().convertTimeToString(bill.getNgayGioTra(), formatTime);
        createCell("", 1, fontSize, false, false, true, HorizontalAlignment.LEFT);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(endTime, cellIndex + 1, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 2, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
        ++rowIndex; // 10

        // thời gian hát
        double hours = bill.tinhGioThue();
        int minutes = (int) (hours % 1 * 60);
        int hoursInt = (int) hours;
        String minutesStr = minutes < 10 ? "0" + minutes : minutes + "";
        String hoursStr = hoursInt < 10 ? "0" + hoursInt : hoursInt + "";
        String time = String.format("%s giờ %s phút", hoursStr, minutesStr);
        String hoursLabel = "Thời gian:";
        createRow(hoursLabel, rowIndex, cellIndex, rowHeight, fontSize, false, false, true, null,
                HorizontalAlignment.LEFT);
        createCell("", 1, fontSize, false, false, true, HorizontalAlignment.LEFT);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 3));
        createCell(time, cellIndex + 1, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 2, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
        ++rowIndex; // 11

        // skip row
        skipRow(rowIndex);
        ++rowIndex; // 12
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
        XSSFCellStyle styleR0 = createBordersExcel(1, 1, 1, 1, BorderStyle.THICK, IndexedColors.WHITE,
                IndexedColors.BLACK, IndexedColors.WHITE, IndexedColors.WHITE);
        styleR0.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellStyle(styleR0);
        cell.setCellValue("Tên dịch vụ");

        XSSFCellStyle styleR1 = createBordersExcel(1, 1, 1, 1, BorderStyle.THICK, IndexedColors.WHITE,
                IndexedColors.BLACK, IndexedColors.WHITE, IndexedColors.WHITE);
        styleR1.setAlignment(HorizontalAlignment.RIGHT);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellStyle(styleR1);
        cell.setCellValue("SL");

        cell = row.createCell(2, CellType.STRING);
        cell.setCellStyle(styleR1);
        cell.setCellValue("Đơn giá");

        XSSFCellStyle styleR3 = createBordersExcel(1, 1, 1, 0, BorderStyle.THICK, IndexedColors.WHITE,
                IndexedColors.BLACK, IndexedColors.WHITE, IndexedColors.WHITE);
        styleR3.setAlignment(HorizontalAlignment.RIGHT);
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Tổng");
        cell.setCellStyle(styleR3);
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
    private int showServiceOrderExcel(ArrayList<CTDichVu> billInfoList, int rowIndex) {
        DataFormat dataFormat = workbook.createDataFormat();
        if (billInfoList != null) {
            int lastIndex = billInfoList.size();
            for (int i = 0; i <= lastIndex; i++) {
                row = sheet.createRow(rowIndex + i);
                if (i == 0) {
                    row.setHeight((short) 450);
                }
                if (i == lastIndex) {
                    XSSFCellStyle styleR0 = createBordersExcel(1, 1, 1, 1, BorderStyle.THICK, IndexedColors.WHITE,
                            IndexedColors.BLACK, IndexedColors.WHITE, IndexedColors.WHITE);
                    row.setHeight((short) 150);
                    Cell cell = row.createCell(0, CellType.STRING);
                    cell.setCellStyle(styleR0);
                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellStyle(styleR0);
                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellStyle(styleR0);

                    XSSFCellStyle styleR3 = createBordersExcel(1, 1, 1, 0, BorderStyle.THICK, IndexedColors.WHITE,
                            IndexedColors.BLACK, IndexedColors.WHITE, IndexedColors.WHITE);
                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellStyle(styleR3);
                } else {
                    CTDichVu billInfo = billInfoList.get(i);
                    Double price = billInfo.getDichVu().getGiaBan();
                    int quantityOrder = billInfo.getSoLuongDat();
                    Double totalPriceOrder = price * quantityOrder;

                    XSSFCellStyle styleR0 = createBordersExcel(1, 1, 1, 0, BorderStyle.THIN);
                    Cell cell = row.createCell(0, CellType.STRING);
                    cell.setCellStyle(styleR0);
                    cell.setCellValue(billInfo.getDichVu().getTenDichVu());

                    XSSFCellStyle styleR1 = createBordersExcel(1, 1, 1, 0, BorderStyle.THIN);
                    styleR1.setDataFormat(dataFormat.getFormat("#,###"));
                    styleR1.setAlignment(HorizontalAlignment.RIGHT);
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
    private int showTotalPriceExcel(ArrayList<CTDichVu> billInfoList, HoaDon bill, int rowIndex) {
        int cellIndex = 0;
        DataFormat dataFormat = workbook.createDataFormat();
        Double totalPriceService = 0.0;
        for (CTDichVu ctHoaDon : billInfoList) {
            totalPriceService += ctHoaDon.tinhTienDichVu();
        }
        totalPriceService = (double) Math.round(totalPriceService);

        // tổng tiền dịch vụ
        String totalPriceServiceLabel = "Tổng tiền dịch vụ:";
        createRow(totalPriceServiceLabel, rowIndex, cellIndex, rowHeight + 150, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), HorizontalAlignment.LEFT);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(0, 1, 1, 1, BorderStyle.THIN));

        XSSFCellStyle styleService = createBordersExcel(0, 1, 1, 0, BorderStyle.THIN);
        styleService.setDataFormat(dataFormat.getFormat("#,###"));
        styleService.setAlignment(HorizontalAlignment.RIGHT);
        createCell(totalPriceService, cellIndex + 2, styleService, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
        ++rowIndex;

        // tổng tiền giờ
        Double totalPriceRoom = bill.tinhTienPhong();
        String totalPriceRoomLabel = "Tổng tiền giờ:";
        createRow(totalPriceRoomLabel, rowIndex, cellIndex, rowHeight, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), HorizontalAlignment.LEFT);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));

        XSSFCellStyle styleRoom = createBordersExcel(0, 1, 1, 0, BorderStyle.THIN);
        styleRoom.setAlignment(HorizontalAlignment.RIGHT);
        styleRoom.setDataFormat(dataFormat.getFormat("#,###"));
        createCell(totalPriceRoom, cellIndex + 2, styleRoom, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
        ++rowIndex;

        // tổng hoá đơn
        Double totalPrice = totalPriceService + totalPriceRoom;
        String totalPriceBillLabel = "Tổng hoá đơn:";
        createRow(totalPriceBillLabel, rowIndex, cellIndex, rowHeight + 50, 13, true, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 1), HorizontalAlignment.LEFT);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        createCell("", cellIndex + 1, createBordersExcel(1, 1, 1, 1, BorderStyle.THIN));

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 13);
        XSSFCellStyle styleBill = createBordersExcel(0, 1, 1, 0, BorderStyle.THIN);
        styleBill.setAlignment(HorizontalAlignment.RIGHT);
        styleBill.setFont(font);
        styleBill.setDataFormat(dataFormat.getFormat("#,###"));

        createCell(totalPrice, cellIndex + 2, styleBill, CellType.NUMERIC);
        createCell("", cellIndex + 3, createBordersExcel(1, 1, 1, 0, BorderStyle.THIN));
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
        // loại phòng
        String serviceTypeNameLabel = "Loại phòng: " + room.getLoaiPhong().getTenLP();
        createRow(serviceTypeNameLabel, rowIndex, cellIndex, rowHeight + 150, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.LEFT);
        removeBorders(cellIndex);
        ++rowIndex;

        // giá phòng
        DecimalFormat df = new DecimalFormat("#,###");
        String roomPriceLabel = "Giá phòng: " + df.format(room.getLoaiPhong().getGiaTien()) + "đ/giờ";
        createRow(roomPriceLabel, rowIndex, cellIndex, rowHeight, 11, false, false, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.LEFT);
        removeBorders(cellIndex);
        ++rowIndex;

        // skip row
        skipRow(rowIndex);
        ++rowIndex;

        // goodbye
        String message = "Quý khách vui lòng kiểm tra lại hoá đơn trước khi thanh toán. "
                + "\nXin cảm ơn và hẹn gặp lại quý khách";
        createRow(message, rowIndex, cellIndex, rowHeight * 2, 11, false, true, true,
                new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.CENTER);
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
                    new CellRangeAddress(rowIndex, rowIndex, 0, 3), HorizontalAlignment.CENTER);
            XSSFCellStyle styleR = createBordersExcel(1, 0, 1, 1, BorderStyle.THIN, IndexedColors.WHITE,
                    IndexedColors.GREY_25_PERCENT, IndexedColors.WHITE, IndexedColors.WHITE);
            row.setHeight((short) 200);
            Cell cell = row.createCell(0);
            cell.setCellStyle(styleR);
            cell = row.createCell(1);
            cell.setCellStyle(styleR);
            cell = row.createCell(2);
            cell.setCellStyle(styleR);

            XSSFCellStyle styleR3 = createBordersExcel(1, 0, 1, 0, BorderStyle.THICK, IndexedColors.WHITE,
                    IndexedColors.BLACK, IndexedColors.WHITE, IndexedColors.WHITE);
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
        workbook = new XSSFWorkbook();

        sheet = workbook.createSheet("Hóa Đơn");
        // sheet.setDisplayGridlines(false);
        sheet.setColumnWidth(0, 6500);
        sheet.setColumnWidth(1, 2000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 1500);

        int rowIndex = 0;
        rowIndex = createHeaderExcel(rowIndex);
        rowIndex = createBillInfoExcel(bill, rowIndex);
        rowIndex = createServiceOrderHeaderExcel(rowIndex);
        ArrayList<CTDichVu> billInfoList = CTDichVuDAO.getInstance().getServiceDetailListByBillId(billId);
        rowIndex = showServiceOrderExcel(billInfoList, rowIndex);
        rowIndex = showTotalPriceExcel(billInfoList, bill, rowIndex);
        rowIndex = showFooterExcel(rowIndex, bill.getPhong());
        writeFileExcel(path, rowIndex);
    }

    public void exportBillToPdf(int billId, String path) {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            PdfDocument pdf = new PdfDocument();
            doc.open();
            Paragraph paraKaraokeName = new Paragraph("Karaoke DASH");
            paraKaraokeName.setAlignment(Element.ALIGN_CENTER);
            Font font = new Font();
            font.setSize(30);
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
