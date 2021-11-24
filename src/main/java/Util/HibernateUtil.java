package Util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class HibernateUtil {
	private static HibernateUtil instance;

	private EntityManager entityManager;

	private HibernateUtil() {
		entityManager = Persistence.createEntityManagerFactory("karaoke-bar-management-software").createEntityManager();
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
