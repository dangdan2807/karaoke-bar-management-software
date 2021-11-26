package DAO;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import DAO.Impl.*;
import entity.*;

public class test {
    public static void main(String[] args) {
        ArrayList<LoaiDichVu> list = new ArrayList<>();
        // LoaiDichVu rs = null;
        // int rs = 0;
        // String rs = "";
        boolean rs = false;
        int check = 1;
        int currentPage = 1;
        int lineNumberDisplayed = 10;
        String data = "LDV012";
        boolean gender = true;
        try {
            if (check == 0) {
                list = LoaiDichVuDAOImpl.getInstance().getServiceTypeListByNameAndPageNumber(data ,currentPage, lineNumberDisplayed);
                if (list.size() > 0) {
                    for (LoaiDichVu i : list) {
                        System.out.println(i.toString());
                    }
                } else {
                    System.out.println(0);
                }
            } else {
                LoaiDichVu serviceType = new LoaiDichVu("LDV112", "Món bò 1");
                rs = LoaiDichVuDAOImpl.getInstance().updateInfoServiceType(serviceType);
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
