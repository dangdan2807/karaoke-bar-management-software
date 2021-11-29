package App;

import java.math.BigDecimal;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.persistence.*;

import DAO.*;
import Util.HibernateUtil;
import entity.*;

public class test {

    public static void main(String[] args) {
        try {
            // HoaDonDAO billDAO = (HoaDonDAO) Naming.lookup("rmi://localhost:1099/billDAO");

            // HoaDon bill = billDAO.getBillByBillId("HD2021100100001");
            NhanVienDAO staffDAO = (NhanVienDAO) Naming.lookup("rmi://localhost:1099/staffDAO");
			// KhachHangDAO customerDAO = (KhachHangDAO) Naming.lookup("rmi://localhost:1099/customerDAO");
			// NhanVien staff = staffDAO.getStaffByBillId(bill.getMaHoaDon());
			// bill.setNhanVien(staff);
			// KhachHang customer = customerDAO.getCustomerByBillId(bill.getMaHoaDon());
            // System.out.println(customer);
            ArrayList<NhanVien> staffs = staffDAO.getStaffListByWorkingStatusAndPageNumber("Đang làm1", 1, 10);
            for (NhanVien nhanVien : staffs) {
                System.out.println(nhanVien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
