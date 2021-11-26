package DAO;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import DAO.Impl.*;
import entity.*;

public class test {
    public static void main(String[] args) {
        ArrayList<Phong> list = new ArrayList<>();
        Phong rs = null;
        // int rs = 0;
        // String rs = "";
        // boolean rs = false;
        int check = 1;
        int currentPage = 1;
        int lineNumberDisplayed = 10;
        String data = "HD2021100100001";
        boolean gender = true;
        try {
            if (check == 0) {
                list = PhongDAOImpl.getInstance().getRoomListByStatusAndPageNumber(0, currentPage, lineNumberDisplayed);
                if (list.size() > 0) {
                    for (Phong kh : list) {
                        System.out.println(kh.toString());
                    }
                } else {
                    System.out.println(0);
                }
            } else {
                LoaiPhong roomType = new LoaiPhong("LP001", "5 người", 5, 80000.0);
                Phong room = new Phong("P0101", 0, "Tầng 1", roomType);
                rs = PhongDAOImpl.getInstance().getRoomByBillId(data);
                // rs = PhongDAOImpl.getInstance().updateRoomStatus("P0001", 1);
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
