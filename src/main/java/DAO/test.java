package DAO;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import DAO.Impl.KhachHangDAOImpl;
import entity.KhachHang;

public class test {
    public static void main(String[] args) {
        ArrayList<KhachHang> list = new ArrayList<>();
        KhachHang rs = null;
        // int rs = 0;
        // String rs = "";
        // boolean rs = false;
        int currentPage = 1;
        int lineNumberDisplayed = 10;
        int check = 1;
        String data = "KH00000087";
        boolean gender = true;
        try {
            if (check == 0) {
                list = KhachHangDAOImpl.getInstance().getCustomerListByGenderAndPageNumber(gender, currentPage, lineNumberDisplayed);
                if (list.size() > 0) {
                    for (KhachHang kh : list) {
                        System.out.println(kh.toString());
                    }
                } else {
                    System.out.println(0);
                }
            } else {
                Date ngaySinh = Date.valueOf("1996-01-01");
                KhachHang customer = new KhachHang("KH00001017", "200000087", "Trung Hieu Vo", "0303030303", ngaySinh, false);
                rs = KhachHangDAOImpl.getInstance().getCustomerByBillId("HD2021100100001");
                System.out.println(rs);
                // if (rs) {
                //     System.out.println(rs);
                // } else {
                //     System.out.println("null");
                // }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
