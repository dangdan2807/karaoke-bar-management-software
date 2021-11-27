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
        ArrayList<CTDichVu> list = new ArrayList<CTDichVu>();
        // CTDichVu rs = null;
        // int rs = 0;
        // String rs = "";
        boolean rs = false;
        // Double rs = 0.0;
        int check = 0;
        int currentPage = 1;
        int lineNumberDisplayed = 10;
        String data = "HD2021100100001";
        try {
            CTDichVuDAO request = new CTDichVuDAOImpl();
            if (check == 1) {
                Date startDate = Date.valueOf("2021-10-01");
                Date endDate = Date.valueOf("2021-12-01");
                list = request.getServiceDetailListByBillId(data);
                if (list.size() > 0) {
                    for (CTDichVu i : list) {
                        System.out.println(i.toString());
                    }
                } else {
                    System.out.println(0);
                }
            } else {
                DichVu service = new DichVu("DV0112", "Bào Tử Hấp Gừng", 120000.0, 85, new LoaiDichVu("LDV013"));
                CTDichVu bill = new CTDichVu(1, 30000, service);
                rs = request.insertServiceDetail(bill, 1, "HD2021100100002");
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
