package DAO;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import DAO.Impl.*;
import entity.*;

public class test {
    public static void main(String[] args) {
        ArrayList<DichVu> list = new ArrayList<>();
        // LoaiDichVu rs = null;
        // int rs = 0;
        // String rs = "";
        boolean rs = false;
        int check = 0;
        int currentPage = 1;
        int lineNumberDisplayed = 10;
        String data = "Lẫu";
        boolean gender = true;
        try {
            DichVuDAO request = new DichVuDAOImpl();
            if (check == 1) {
                list = request.getServiceListByNameAndServiceTypeName(data, "Lẫu");
                if (list.size() > 0) {
                    for (DichVu i : list) {
                        System.out.println(i.toString());
                    }
                } else {
                    System.out.println(0);
                }
            } else {
                LoaiDichVu serviceType = new LoaiDichVu("LDV112", "Món bò 1");
                DichVu service = new DichVu("DV1067", "Lẫu lẫu 1", 800.0, 10, serviceType);
                rs = request.updateInfoService(service);
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
