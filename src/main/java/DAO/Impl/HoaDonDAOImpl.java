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
import entity.HoaDon;

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
    private static HoaDonDAOImpl instance;

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
     * Sử dụng kiến trúc singleton để tạo ra 1 đối tượng duy nhất
     * 
     * @return {@code HoaDonDAOImpl}
     */
    public static HoaDonDAOImpl getInstance() {
        if (instance == null)
            try {
                instance = new HoaDonDAOImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        return instance;
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
    @Override
    public HoaDon getUncheckBillByRoomId(String roomId) throws RemoteException {
        String query = "{CALL USP_getUncheckBillByRoomId( ? )}";
        HoaDon result = null;
        EntityTransaction tr = em.getTransaction();
        Object[] list = null;
        try {
            tr.begin();
            list = (Object[]) em.createNativeQuery(query).setParameter(1, roomId).getSingleResult();
            tr.commit();
            String billID = (String) list[0];
            Timestamp startTime = (Timestamp) list[1];
            Timestamp endTime = (Timestamp) list[2];
            int status = (int) list[3];
            result = new HoaDon(billID, startTime, endTime, status);
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
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
    @Override
    public HoaDon getBillByBillId(String billId) throws RemoteException {
        String query = "{CALL USP_getBillByBillId( ? )}";
        HoaDon result = null;
        EntityTransaction tr = em.getTransaction();
        Object[] list = null;
        try {
            tr.begin();
            list = (Object[]) em.createNativeQuery(query).setParameter(1, billId).getSingleResult();
            tr.commit();
            String billID = (String) list[0];
            Timestamp startTime = (Timestamp) list[1];
            Timestamp endTime = (Timestamp) list[2];
            int status = (int) list[3];
            result = new HoaDon(billID, startTime, endTime, status);
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
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
    @Override
    public String getLastBillId() throws RemoteException {
        String query = "{CALL USP_getLastBillId()}";
        EntityTransaction tr = em.getTransaction();
        String result = "";
        try {
            tr.begin();
            result = (String) em.createNativeQuery(query).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
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
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result > 0;
    }

    /**
     * Thanh toán hóa đơn
     * 
     * @param billId     {@code String}: mã hóa đơn
     * @param paymentDate  {@code Timestamp}: ngày giờ thanh toán
     * @param totalPrice {@code Double}: tổng tiền thanh toán
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
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query)
                    .setParameter(1, billId)
                    .setParameter(2, paymentDate)
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
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
    @Override
    public Double getTotalPriceBill(String billId) throws RemoteException {
        String query = "{CALL USP_getTotalPriceBill( ? )}";
        EntityTransaction tr = em.getTransaction();
        Double result = 0.0;
        try {
            tr.begin();
            BigDecimal db = (BigDecimal) em.createNativeQuery(query).setParameter(1, billId).getSingleResult();
            result = db.doubleValue();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy danh sách hóa đơn trong khoảng ngày được chọn và tìm theo mã nhân viên
     * 
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDate(Date fromDate, Date toDate, String staffId) throws RemoteException {
        String query = "{CALL USP_getBillListByDate( ? , ? , ? )}";
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, fromDate)
                    .setParameter(2, toDate)
                    .setParameter(3, staffId).getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                HoaDon bill = new HoaDon(billID, startTime, endTime, status);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và số điện thoại của khách
     * hàng
     * 
     * @param phoneNumber {@code String}: Số điện thoại
     * @param fromDate    {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate      {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId     {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndCustomerPhoneNumber(String phoneNumber, Date fromDate, Date toDate,
            String staffId) throws RemoteException {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndCustomerPhoneNumber( ? , ? , ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, phoneNumber)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId).getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                HoaDon bill = new HoaDon(billID, startTime, endTime, status);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của khách hàng
     * 
     * @param customerName {@code String}: Tên khách hàng
     * @param fromDate     {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate       {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId      {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndCustomerName(String customerName, Date fromDate, Date toDate,
            String staffId) throws RemoteException {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndCustomerName( ? , ? , ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, customerName)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId).getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                HoaDon bill = new HoaDon(billID, startTime, endTime, status);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và tên của nhân viên tạo hóa
     * đơn
     * 
     * @param staffName {@code String}: Tên nhân viên
     * @param fromDate  {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate    {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId   {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndStaffName(String staffName, Date fromDate, Date toDate,
            String staffId) throws RemoteException {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndStaffName( ? , ? , ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, staffName)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId).getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                HoaDon bill = new HoaDon(billID, startTime, endTime, status);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * Lấy danh sách hóa đơn trong khoản ngày được chọn và mã hóa đơn đơn
     * 
     * @param billId   {@code String}: Mã hóa đơn
     * @param fromDate {@code java.sql.Date}: ngày bắt đầu thống kê
     * @param toDate   {@code java.sql.Date}: ngày kết thúc thống kê
     * @param staffId  {@code String}: mã nhân viên
     * @return {@code ArrayList<HoaDon>}: danh sách hóa đơn
     * @throws RemoteException - Bắt lỗi Remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<HoaDon> getBillListByDateAndBillId(String billId, Date fromDate, Date toDate, String staffId) throws RemoteException {
        ArrayList<HoaDon> dataList = new ArrayList<HoaDon>();
        String query = "{CALL USP_getBillListByDateAndBillId( ? , ? , ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        List<Object[]> list = new ArrayList<>();
        try {
            tr.begin();
            list = (List<Object[]>) em.createNativeQuery(query)
                    .setParameter(1, billId)
                    .setParameter(2, fromDate)
                    .setParameter(3, toDate)
                    .setParameter(4, staffId).getResultList();
            tr.commit();
            list.stream().forEach(e -> {
                String billID = (String) e[0];
                Timestamp startTime = (Timestamp) e[1];
                Timestamp endTime = (Timestamp) e[2];
                int status = (int) e[3];
                HoaDon bill = new HoaDon(billID, startTime, endTime, status);
                dataList.add(bill);
            });
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return dataList;
    }
}
