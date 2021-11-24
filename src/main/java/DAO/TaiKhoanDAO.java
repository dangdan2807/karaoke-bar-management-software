package DAO;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entity.TaiKhoan;

public interface TaiKhoanDAO extends Remote {
    /**
     * kiểm tra thông tin đăng nhập đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @param password {@code String}: mật khẩu
     * @return {@code boolean}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu thành công thì trả về {@code true}</li>
     *         <li>Nếu thất bại thì trả về {@code false}</li>
     *         </ul>
     * @throws RemoteException bắt lỗi remote
     */
    public boolean login(String username, String password) throws RemoteException;

    /**
     * Lấy tài khoản dựa trên tên đăng nhập
     * 
     * @param username {@code String}: tên đăng nhập
     * @return {@code TaiKhoan}: kết quả trả về của câu truy vấn
     *         <ul>
     *         <li>Nếu tìm thấy thì trả về {@code TaiKhoan}</li>
     *         <li>Nếu không tìm thấy thì trả về {@code null}</li>
     *         </ul>
     * @throws RemoteException bắt lỗi remote
     */
    public TaiKhoan getAccountByUsername(String username) throws RemoteException;

}
