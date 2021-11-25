package DAO;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import DAO.Impl.*;
import entity.*;

public class test {
    public static void main(String[] args) {
        ArrayList<LoaiPhong> list = new ArrayList<>();
        LoaiPhong rs = null;
        // int rs = 0;
        // String rs = "";
        // boolean rs = false;
        int currentPage = 1;
        int check = 1;
        int lineNumberDisplayed = 10;
        String data = "8";
        boolean gender = true;
        try {
            if (check == 0) {
                list = LoaiPhongDAOImpl.getInstance().getRoomTypeListByPriceAndPageNumber(data, currentPage,
                        lineNumberDisplayed);
                if (list.size() > 0) {
                    for (LoaiPhong kh : list) {
                        System.out.println(kh.toString());
                    }
                } else {
                    System.out.println(0);
                }
            } else {
                LoaiPhong roomType = new LoaiPhong("LP004", "1005", 15, 8.0);
                rs = LoaiPhongDAOImpl.getInstance().getRoomTypeByName("1005");
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
