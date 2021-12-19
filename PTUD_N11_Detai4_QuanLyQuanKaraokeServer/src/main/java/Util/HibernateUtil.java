package Util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class HibernateUtil {
	private static HibernateUtil instance;

	private EntityManager entityManager;

	private HibernateUtil() {
		entityManager = Persistence.createEntityManagerFactory("PTUD_N11_Detai4_QuanLyQuanKaraokeServer").createEntityManager();
	}

	public synchronized static HibernateUtil getInstance() {
		if (instance == null)
			instance = new HibernateUtil();
		return instance;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
}
