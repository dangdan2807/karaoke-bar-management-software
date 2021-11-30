package DAO.Impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import DAO.HoaDonDAO;
import Util.HibernateUtil;
import entity.*;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code HoaDon}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan
 * <p>
 * Ngày tạo: 09/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm mô tả lớp và hàm (java doc)
 */
public class HoaDonDAOImpl extends UnicastRemoteObject implements HoaDonDAO {
    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public HoaDonDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * Lấy hóa đơn chưa tính tiền dựa trên mã phòng
     * 
     * @param roomId {@code String}: mã phòng
     * @return {@code HoaDon}: hóa đơn
     *         <ul>
     *         <li>Nếu tìm thấy hóa đơn thì trả về {@code HoaDon}</li>
     *         <li>Nếu không tìm thấy hóa đơn thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public HoaDon getUncheckBillByRoomId(String roomId) throws RemoteException {
        String query = "{CALL USP_getUncheckBillByRoomId( ? )}";
        HoaDon result = null;
        em.clear();
        EntityTransaction tr = em.getTransaction();
        ArrayList<Object[]> list = new ArrayList<>();
        ;
        try {
            tr.begin();
            list = (ArrayList<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, roomId).getResultList();
            tr.commit();
            if (list.size() > 0) {
                String billID = (String) list.get(0)[0];
                Timestamp startTime = (Timestamp) list.get(0)[1];
                Timestamp endTime = (Timestamp) list.get(0)[2];
                int status = (int) list.get(0)[3];
                NhanVien staff = new NhanVien((String) list.get(0)[4]);
                KhachHang customer = new KhachHang((String) list.get(0)[5]);
                Phong room = new Phong((String) list.get(0)[6]);
                result = new HoaDon(billID, startTime, endTime, status, staff, customer, room);
            } else {
                result = null;
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy hóa đơn dựa trên mã hóa đơn
     * 
     * @param billId {@code int}: mã hóa đơn
     * @return {@code HoaDon}: hóa đơn
     *         <ul>
     *         <li>Nếu tìm thấy hóa đơn thì trả về {@code HoaDon}</li>
     *         <li>Nếu không tìm thấy hóa đơn thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public HoaDon getBillByBillId(String billId) throws RemoteException {
        String query = "{CALL USP_getBillByBillId( ? )}";
        HoaDon result = null;
        em.clear();
        EntityTransaction tr = em.getTransaction();
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (ArrayList<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, billId).getResultList();
            tr.commit();
            if (list.size() > 0) {
                String billID = (String) list.get(0)[0];
                Timestamp startTime = (Timestamp) list.get(0)[1];
                Timestamp endTime = (Timestamp) list.get(0)[2];
                int status = (int) list.get(0)[3];
                NhanVien staff = new NhanVien((String) list.get(0)[4]);
                KhachHang customer = new KhachHang((String) list.get(0)[5]);
                Phong room = new Phong((String) list.get(0)[6]);
                result = new HoaDon(billID, startTime, endTime, status, staff, customer, room);
            } else {
                result = null;
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy mã hóa đơn mới nhất
     * 
     * @return {@code int}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã hóa đơn}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code -1}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getLastBillId() throws RemoteException {
        String query = "{CALL USP_getLastBillId()}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        String result = "";
        try {
            tr.begin();
            ArrayList<String> resultQuery = (ArrayList<String>) em.createNativeQuery(query)
                    .getResultList();
            tr.commit();
            if (resultQuery.size() > 0) {
                result = resultQuery.get(0);
            } else {
                result = "-1";
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Thêm hóa đơn mới
     * 
     * @param bill {@code HoaDon}: hóa đơn cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public boolean insertBill(HoaDon bill) throws RemoteException {
        String query = "{CALL USP_insertBill( ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, bill.getMaHoaDon())
                    .setParameter(2, bill.getNgayGioDat())
                    .setParameter(3, bill.getNhanVien().getMaNhanVien())
                    .setParameter(4, bill.getKhachHang().getMaKH())
                    .setParameter(5, bill.getPhong().getMaPhong())
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Thanh toán hóa đơn
     * 
     * @param billId      {@code String}: mã hóa đơn
     * @param paymentDate {@code Timestamp}: ngày giờ thanh toán
     * @param totalPrice  {@code Double}: tổng tiền thanh toán
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thêm thành công thì trả về {@code true}</li>
     *         <li>Nếu thêm thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public boolean payment(String billId, Timestamp paymentDate) throws RemoteException {
        String query = "{CALL USP_payment( ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, billId)
                    .setParameter(2, paymentDate)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * Lấy tổng tiền của hóa đơn
     * 
     * @param billId {@code int}: mã hóa đơn
     * @return {@code Double}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tổng tiền}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code -1}</li>
     *         </ul>
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public Double getTotalPriceBill(String billId) throws RemoteException {
        String query = "{CALL USP_getTotalPriceBill( ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        Double result = 0.0;
        try {
            tr.begin();
            ArrayList<BigDecimal> db = (ArrayList<BigDecimal>) em.createNativeQuery(query)
                    .setParameter(1, billId).getResultList();
            if(db.size() > 0) {
                result = db.get(0).doubleValue();
            } else {
                result = 0.0;
            }
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoảng ngày được chọn và tìm theo mã nhân viên
     * 
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndPageNumber(Date fromDate, Date toDate, String staffId, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getBillListByDateAndPageNumber( ? , ? , ? , ? , ? )}";
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        em.clear();
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, fromDate)
                    .setParameter(2, toDate)
                    .setParameter(3, staffId)
                    .setParameter(4, currentPage)
                    .setParameter(5, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                NhanVien staff = new NhanVien((String) e[4]);
                KhachHang customer = new KhachHang((String) e[5]);
                Phong room = new Phong((String) e[6]);
                HoaDon bill = new HoaDon(billID, startTime, endTime, status, staff, customer, room);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoản ngày được chọn và số trang
     * 
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code int}: tổng số hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfBillList(Date fromDate, Date toDate, String staffId) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfBillListByDate( ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, fromDate)
                    .setParameter(2, toDate)
                    .setParameter(3, staffId)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và số điện thoại của khách
     * hàng
     * 
     * @param phoneNumber         {@code String}: Số điện thoại
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndCustomerPhoneNumberAndPageNumber(String phoneNumber, Date fromDate,
            Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) throws RemoteException {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndCustomerPhoneNumberAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, phoneNumber)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId)
                    .setParameter(5, currentPage)
                    .setParameter(6, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                NhanVien staff = new NhanVien((String) e[4]);
                KhachHang customer = new KhachHang((String) e[5]);
                Phong room = new Phong((String) e[6]);
                HoaDon bill = new HoaDon(billID, startTime, endTime, status, staff, customer, room);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, số điện thoại khách hàng và
     * số trang
     * 
     * @param phoneNumber {@code String}: Số điện thoại
     * @param fromDate    {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate      {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId     {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     *         * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfBillListByDateAndCustomerPhoneNumber(String phoneNumber, Date fromDate, Date toDate,
            String staffId) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndCustomerPhoneNumber( ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, phoneNumber)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của khách hàng
     * 
     * @param customerName        {@code String}: Tên khách hàng
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndCustomerNameAndPageNumber(String customerName, Date fromDate,
            Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) throws RemoteException {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndCustomerNameAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, customerName)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId)
                    .setParameter(5, currentPage)
                    .setParameter(6, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                NhanVien staff = new NhanVien((String) e[4]);
                KhachHang customer = new KhachHang((String) e[5]);
                Phong room = new Phong((String) e[6]);
                HoaDon bill = new HoaDon(billID, startTime, endTime, status, staff, customer, room);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, số điện thoại khách hàng
     * 
     * @param customerName {@code String}: Tên khách hàng
     * @param fromDate     {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate       {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId      {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @Override
    public int getTotalLineOfBillListByDateAndCustomerName(String customerName, Date fromDate, Date toDate,
            String staffId) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndCustomerName( ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, customerName)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của nhân viên tạo hóa
     * đơn
     * 
     * @param staffName           {@code String}: Tên nhân viên
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndStaffNameAndPageNumber(String staffName, Date fromDate, Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed) throws RemoteException {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndStaffNameAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, staffName)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId)
                    .setParameter(5, currentPage)
                    .setParameter(6, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                NhanVien staff = new NhanVien((String) e[4]);
                KhachHang customer = new KhachHang((String) e[5]);
                Phong room = new Phong((String) e[6]);
                HoaDon bill = new HoaDon(billID, startTime, endTime, status, staff, customer, room);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, tên nhân viên
     * 
     * @param staffName {@code String}: Tên nhân viên
     * @param fromDate  {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate    {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId   {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    @Override
    public int getTotalLineOfBillListByDateAndStaffName(String staffName, Date fromDate, Date toDate,
            String staffId) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndStaffName( ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, staffName)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và mã hóa đơn đơn
     * 
     * @param billId              {@code String}: Mã hóa đơn
     * @param fromDate            {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate              {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId             {@code String}: mã nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndBillIdAndPageNumber(String billId, Date fromDate, Date toDate,
            String staffId, int currentPage, int lineNumberDisplayed)
            throws RemoteException {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndBillIdAndPageNumber( ? , ? , ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, billId)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId)
                    .setParameter(5, currentPage)
                    .setParameter(6, lineNumberDisplayed)
                    .getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                NhanVien staff = new NhanVien((String) e[4]);
                KhachHang customer = new KhachHang((String) e[5]);
                Phong room = new Phong((String) e[6]);
                HoaDon bill = new HoaDon(billID, startTime, endTime, status, staff, customer, room);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy số lượng hóa đơn trong khoảng ngày được chọn, mã hóa đơn
     * 
     * @param billId   {@code String}: Mã hóa đơn
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code int}: số lượng hóa đơn
     */
    @Override
    public int getTotalLineOfBillListByDateAndBillId(String BillId, Date fromDate, Date toDate,
            String staffId) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfBillListByDateAndBillId( ? , ? , ? , ? )}";
        em.clear();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, BillId)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId)
                    .getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return result;
    }
}
