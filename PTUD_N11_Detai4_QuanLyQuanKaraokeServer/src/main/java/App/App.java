package App;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import DAO.*;
import DAO.Impl.*;

public class App {
	public static void main(String[] args) {
		// Util.HibernateUtil.getInstance().getEntityManager();
		SecurityManager securityManager = System.getSecurityManager();
		if (securityManager == null) {
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
		}

		try {
			int port = 1099;
			LocateRegistry.createRegistry(port);
			TaiKhoanDAO accountDAO = new TaiKhoanDAOImpl();
			NhanVienDAO staffDAO = new NhanVienDAOImpl();
			KhachHangDAO customerDAO = new KhachHangDAOImpl();
			LoaiDichVuDAO serviceTypeDAO = new LoaiDichVuDAOImpl();
			DichVuDAO serviceDAO = new DichVuDAOImpl();
			LoaiPhongDAO roomTypeDAO = new LoaiPhongDAOImpl();
			PhongDAO roomDAO = new PhongDAOImpl();
			HoaDonDAO billDAO = new HoaDonDAOImpl();
			CTDichVuDAO serviceDetailDAO = new CTDichVuDAOImpl();

			Naming.bind("rmi://localhost:" + port + "/accountDAO", accountDAO);
			Naming.bind("rmi://localhost:" + port + "/staffDAO", staffDAO);
			Naming.bind("rmi://localhost:" + port + "/customerDAO", customerDAO);
			Naming.bind("rmi://localhost:" + port + "/serviceTypeDAO", serviceTypeDAO);
			Naming.bind("rmi://localhost:" + port + "/serviceDAO", serviceDAO);
			Naming.bind("rmi://localhost:" + port + "/roomTypeDAO", roomTypeDAO);
			Naming.bind("rmi://localhost:" + port + "/roomDAO", roomDAO);
			Naming.bind("rmi://localhost:" + port + "/billDAO", billDAO);
			Naming.bind("rmi://localhost:" + port + "/serviceDetailDAO", serviceDetailDAO);
			System.out.println("Server Running ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
