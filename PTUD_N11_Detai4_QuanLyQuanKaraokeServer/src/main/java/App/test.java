package App;

import java.rmi.RemoteException;

import DAO.DichVuDAO;
import DAO.Impl.DichVuDAOImpl;
import entity.DichVu;
import entity.LoaiDichVu;

public class test {
    public static void main(String[] args) {
        // LoaiDichVu loaiDichVu = new LoaiDichVu("LDV001", "Thạch Dừa");
        // DichVu service = new DichVu("DV0115", "1", 1000.0, 1, loaiDichVu);
        // boolean result = false;
        // try {
        //     DichVuDAO dv = new DichVuDAOImpl();
        //     result = dv.insertService(service);
        // } catch (RemoteException e) {
        //     e.printStackTrace();
        // }
        // System.out.println(result);
        String phoneNumber = "0345678912";
        phoneNumber = "+84" + phoneNumber.substring(1);
        System.out.println(phoneNumber);
    }
}
