package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DBTest {

	public static void main(String[] args) throws Exception {
		EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("eveMon");
		EntityManager em = emf.createEntityManager();
	
	}



}
