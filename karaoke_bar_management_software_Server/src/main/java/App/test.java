package App;

import java.rmi.*;
import java.sql.*;
import java.util.*;

import DAO.*;
import entity.*;

public class test {

    private static String createNewStaffID() {
        String newStaffIdStr = "";
        try {
            NhanVienDAO staffDAO = (NhanVienDAO) Naming.lookup("rmi://localhost:1099/staffDAO");
            String lastStaffId = staffDAO.getLastStaffID().trim();
            String idStr = "NV";
            int oldNumberStaffID = 0;
            System.out.println(0 + " " + lastStaffId);
            if (!lastStaffId.equals("") && !lastStaffId.isEmpty() && lastStaffId != null) {
                System.out.println(1 + " " + lastStaffId);
                oldNumberStaffID = Integer.parseInt(lastStaffId.replace(idStr, ""));
            }

            System.out.println(2 + " " + lastStaffId);
            int newStaffID = ++oldNumberStaffID;
            newStaffIdStr = idStr + newStaffID;
            boolean flag = true;
            while (flag) {
                newStaffIdStr = newStaffIdStr.replace(idStr, idStr + "0");
                if (newStaffIdStr.length() > 9) {
                    flag = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newStaffIdStr;
    }

    public static void main(String[] args) {
        try {
            HoaDonDAO billDAO = (HoaDonDAO) Naming.lookup("rmi://localhost:1099/billDAO");

            HoaDon bill = billDAO.getBillByBillId("HD2021100100001");
            NhanVienDAO staffDAO = (NhanVienDAO) Naming.lookup("rmi://localhost:1099/staffDAO");
			KhachHangDAO customerDAO = (KhachHangDAO) Naming.lookup("rmi://localhost:1099/customerDAO");
			NhanVien staff = staffDAO.getStaffByBillId(bill.getMaHoaDon());
			bill.setNhanVien(staff);
			KhachHang customer = customerDAO.getCustomerByBillId(bill.getMaHoaDon());
            System.out.println(staff);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
