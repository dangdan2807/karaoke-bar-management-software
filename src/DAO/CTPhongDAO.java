package DAO;

import entity.CTPhong;

public class CTPhongDAO {
    private static CTPhongDAO instance = new CTPhongDAO();

    public static CTPhongDAO getInstance() {
        if (instance == null)
            instance = new CTPhongDAO();
        return instance;
    }

    public boolean themCTPhongDAO(CTPhong ctPhong) {
        String query = "INSERT INTO dbo.CTPhong (ngayGioNhan, ngayGioTra, maPhong) VALUES ( ? , ? , ? , ? )";
        Object[] parameter = new Object[] { ctPhong.getNgayGioNhan(), ctPhong.getNgayGioTra(),
                ctPhong.getPhong().getMaPhong() };
        int result = DataProvider.getInstance().ExecuteNonQuery(query, parameter);
        return result > 0;
    }
}
