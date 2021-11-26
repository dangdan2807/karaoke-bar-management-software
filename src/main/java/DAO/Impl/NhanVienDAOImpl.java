package DAO.Impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.*;

import DAO.NhanVienDAO;
import Util.HibernateUtil;
import entity.NhanVien;
import entity.TaiKhoan;

/**
 * Thêm, sửa, đọc dữ liệu từ database cho lớp {@code NhanVien}
 * <p>
 * Người tham gia thiết kế: Phạm Đăng Đan, Huỳnh Tuấn Anh
 * <p>
 * Ngày tạo: 04/10/2021
 * <p>
 * Lần cập nhật cuối: 19/11/2021
 * <p>
 * Nội dung cập nhật: thêm, sửa các hàm hỗ trợ lấy dữ liệu dựa trên phân trang
 */
public class NhanVienDAOImpl extends UnicastRemoteObject implements NhanVienDAO {
    private static NhanVienDAOImpl instance;

    private EntityManager em;

    /**
     * Constructor mặc định không tham số
     * 
     * @throws RemoteException - Bắt lỗi Remote
     */
    public NhanVienDAOImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    /**
     * Sử dụng kiến trúc singleton để tạo ra 1 đối tượng duy nhất
     * 
     * @return {@code NhanVienDAOImpl}
     */
    public static NhanVienDAOImpl getInstance() {
        if (instance == null)
            try {
                instance = new NhanVienDAOImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        return instance;
    }

    /**
     * Lấy danh sách tất cả nhân viên đang làm việc
     * 
     * @param workingStatus       {@code String}: trạng thái làm việc của nhân viên
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<NhanVien> getStaffListByWorkingStatusAndPageNumber(String workingStatus, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getStaffListByWorkingStatusAndPageNumber(?, ?, ?)}";
        ArrayList<NhanVien> dataList = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            dataList = (ArrayList<NhanVien>) em.createNativeQuery(query, NhanVien.class).setParameter(1, workingStatus)
                    .setParameter(2, currentPage).setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return dataList;
    }

    /**
     * Lấy số lượng nhân viên tìm được dựa trên trạng thái nhân viên
     * 
     * @param workingStatus {@code String}: trạng thái làm việc của nhân viên
     * @return {@code int}: số lượng nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public int getTotalLineOfStaffListByWorkingStatus(String workingStatus) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfStaffListByWorkingStatus( ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, workingStatus).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy thông tin nhân viên theo tên đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @return {@code NhanVien}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code NhanVien}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public NhanVien getStaffByUsername(String username) throws RemoteException {
        String query = "{CALL USP_getStaffByUsername( ? )}";
        EntityTransaction tr = em.getTransaction();
        NhanVien staff = null;
        try {
            tr.begin();
            staff = (NhanVien) em.createNativeQuery(query, NhanVien.class).setParameter(1, username).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return staff;
    }

    /**
     * Lấy mã nhân viên mới nhất
     * 
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code mã nhân viên mới nhất}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public String getLastStaffID() throws RemoteException {
        String query = "{CALL USP_getLastStaffID()}";
        EntityTransaction tr = em.getTransaction();
        String staffID = "";
        try {
            tr.begin();
            staffID = (String) em.createNativeQuery(query).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return staffID;
    }

    /**
     * Thêm mới nhân viên
     * 
     * @param staff {@code NhanVien}: nhân viên cần thêm
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public boolean insertStaff(NhanVien staff) throws RemoteException {
        String query = "{CALL USP_insertStaff( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, staff.getMaNhanVien())
                    .setParameter(2, staff.getCmnd())
                    .setParameter(3, staff.getHoTen())
                    .setParameter(4, staff.getNgaySinh())
                    .setParameter(5, staff.getSoDienThoai())
                    .setParameter(6, staff.getChucVu())
                    .setParameter(7, staff.getMucLuong())
                    .setParameter(8, staff.getTrangThaiNV())
                    .setParameter(9, staff.getGioiTinh())
                    .setParameter(10, staff.getTaiKhoan().getTenDangNhap())
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
     * Cập nhật thông tin nhân viên
     * 
     * @param staff {@code NhanVien}: nhân viên cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public Boolean updateInfoStaff(NhanVien staff) throws RemoteException {
        String query = "{CALL USP_updateInfoStaff( ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, staff.getMaNhanVien())
                    .setParameter(2, staff.getCmnd())
                    .setParameter(3, staff.getHoTen())
                    .setParameter(4, staff.getNgaySinh())
                    .setParameter(5, staff.getSoDienThoai())
                    .setParameter(6, staff.getChucVu())
                    .setParameter(7, staff.getMucLuong())
                    .setParameter(8, staff.getTrangThaiNV())
                    .setParameter(9, staff.getGioiTinh())
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
     * Cập nhật thông tin nhân viên và mật khẩu
     * 
     * @param staff {@code NhanVien}: nhân viên cần cập nhật thông tin
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public Boolean updateInfoStaffAndAccount(NhanVien staff) throws RemoteException {
        String query = "{CALL USP_updateInfoStaffAndAccount( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )}";
        TaiKhoan taiKhoan = staff.getTaiKhoan();
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, staff.getMaNhanVien())
                    .setParameter(2, staff.getCmnd())
                    .setParameter(3, staff.getHoTen())
                    .setParameter(4, staff.getNgaySinh())
                    .setParameter(5, staff.getSoDienThoai())
                    .setParameter(6, staff.getChucVu())
                    .setParameter(7, staff.getMucLuong())
                    .setParameter(8, staff.getTrangThaiNV())
                    .setParameter(9, staff.getGioiTinh())
                    .setParameter(10, taiKhoan.getTenDangNhap())
                    .setParameter(11, taiKhoan.getMatKhau())
                    .setParameter(12, taiKhoan.getTinhTrangTK())
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
     * Lấy danh sách nhân viên dựa theo chức vụ
     * 
     * @param position            {@code String}: chức vụ nhân viên cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<NhanVien> getStaffListByPositionAndPageNumber(String position, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getStaffListByPositionAndPageNumber( ? , ? , ? )}";
        EntityTransaction tr = em.getTransaction();
        ArrayList<NhanVien> staffList = new ArrayList<NhanVien>();
        try {
            tr.begin();
            staffList = (ArrayList<NhanVien>) em.createNativeQuery(query, NhanVien.class).setParameter(1, position)
                    .setParameter(2, currentPage).setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return staffList;
    }

    /**
     * Lấy số lượng nhân viên tìm được dựa theo chức vụ
     * 
     * @param position {@code String}: chức vụ nhân viên cần tìm
     * @return {@code int}: số lượng nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public int getTotalLineByPosition(String position) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfStaffListByPosition( ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, position).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy danh sách nhân viên dựa theo tên nhân viên
     * 
     * @param staffName           {@code String}: tên nhân viên cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<NhanVien> getStaffListByStaffNameAndPageNumber(String staffName, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getStaffListByStaffNameAndPageNumber( ? , ? , ? )}";
        ArrayList<NhanVien> staffList = new ArrayList<NhanVien>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            staffList = (ArrayList<NhanVien>) em.createNativeQuery(query, NhanVien.class).setParameter(1, staffName)
                    .setParameter(2, currentPage).setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return staffList;
    }

    /**
     * Lấy số lượng nhân viên tìm được dựa theo tên nhân viên
     * 
     * @param staffName {@code String}: tên nhân viên cần tìm
     * @return {@code int}: số lượng nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public int getTotalLineByStaffName(String staffName) throws RemoteException {
        String query = "{CALL USP_getTotalLineOfStaffListByStaffName( ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, staffName).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy danh sách nhân viên dựa theo số điện thoại
     * 
     * @param phoneNumber         {@code String}: số điện thoại cần tìm
     * @param currentPage         {@code int}: số của trang cần lấy thông tin
     * @param lineNumberDisplayed {@code int}: số dòng được hiển thị trên một trang
     * @return {@code ArrayList<NhanVien>}: danh sách nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<NhanVien> getStaffListByPhoneNumberAndPageNumber(String phoneNumber, int currentPage,
            int lineNumberDisplayed) throws RemoteException {
        String query = "{CALL USP_getStaffListByPhoneNumberAndPageNumber( ? , ? , ? )}";
        ArrayList<NhanVien> staffList = new ArrayList<NhanVien>();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            staffList = (ArrayList<NhanVien>) em.createNativeQuery(query, NhanVien.class).setParameter(1, phoneNumber)
                    .setParameter(2, currentPage).setParameter(3, lineNumberDisplayed).getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return staffList;
    }

    /**
     * Lấy số lượng nhân viên tìm được dựa theo số điện thoại
     * 
     * @param phoneNumber {@code String}: số điện thoại cần tìm
     * @return {@code int}: số lượng nhân viên
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public int getTotalLineByPhoneNumber(String phoneNumber) throws RemoteException {
        String query = "{CALL USP_getTotalLineStaffListByPhoneNumber( ? )}";
        EntityTransaction tr = em.getTransaction();
        int result = 0;
        try {
            tr.begin();
            result = (int) em.createNativeQuery(query).setParameter(1, phoneNumber).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return result;
    }

    /**
     * Lấy tên nhân viên dựa trên mã nhân viên
     * 
     * @param staffId {@code String}: mã nhân viên cần tìm
     * @return {@code String}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code tên nhân viên}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code ""}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public String getStaffNameById(String staffId) throws RemoteException {
        String query = "{CALL USP_getStaffNameById( ? )}";
        EntityTransaction tr = em.getTransaction();
        String staffName = "";
        try {
            tr.begin();
            staffName = (String) em.createNativeQuery(query).setParameter(1, staffId).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return staffName;
    }

    /**
     * Lấy thông tin nhân viên theo mã hóa đơn
     * 
     * @param billId {@code String}: mã hóa đơn
     * @return {@code NhanVien}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code NhanVien}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException Bắt lỗi remote
     */
    @Override
    public NhanVien getStaffByBillId(String billId) throws RemoteException {
        String query = "{CALL USP_getStaffByBillId( ? )}";
        NhanVien staff = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            staff = (NhanVien) em.createNativeQuery(query, NhanVien.class).setParameter(1, billId).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        em.close();
        return staff;
    }
}
