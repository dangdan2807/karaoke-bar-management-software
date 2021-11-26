package DAO;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import DAO.Impl.*;
import Util.HibernateUtil;
import entity.*;

public class test {
    public static void main(String[] args) {
        // HibernateUtil.getInstance().getEntityManager();
        ArrayList<HoaDon> list = new ArrayList<HoaDon>();
        HoaDon rs = null;
        // int rs = 0;
        // String rs = "";
        // boolean rs = false;
        // Double rs = 0.0;
        int check = 0;
        int currentPage = 1;
        int lineNumberDisplayed = 10;
        String data = "P0001";
        try {
            HoaDonDAO request = new HoaDonDAOImpl();
            if (check == 0) {
                Date startDate = Date.valueOf("2021-10-01");
                Date endDate = Date.valueOf("2021-12-01");
                list = request.getBillListByDateAndBillId("HD2021112600001" ,startDate, endDate, "NV00000001");
                if (list.size() > 0) {
                    for (HoaDon i : list) {
                        System.out.println(i.toString());
                    }
                } else {
                    System.out.println(0);
                }
            } else {
                NhanVien staff = new NhanVien("NV00000001");
                KhachHang customer = new KhachHang("KH00000001");
                Phong room = new Phong("P0001");
                Timestamp startDate = Timestamp.valueOf("2021-10-01 10:00:00");
                Timestamp endDate = new Timestamp(System.currentTimeMillis());
                HoaDon bill = new HoaDon("HD2021112600001", startDate, 0, staff, customer, room);
                rs = request.getUncheckBillByRoomId("P0001");
                // if (rs) {
                System.out.println(rs);
                // } else {
                // System.out.println("null");
                // }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
