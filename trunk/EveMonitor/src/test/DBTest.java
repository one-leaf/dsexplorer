package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import luz.eveMonitor.entities.eveDB.inv.InvMarketGroup;
import luz.eveMonitor.entities.eveDB.map.MapRegion;
import luz.eveMonitor.entities.eveDB.map.MapSolarSystem;
import luz.eveMonitor.entities.eveDB.sta.StaStation;

public class DBTest {

	public static void main(String[] args) throws Exception {
		EntityManagerFactory emfm = Persistence.createEntityManagerFactory("eveMon");
		EntityManager emm = emfm.createEntityManager();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("eveDB");
		EntityManager em = emf.createEntityManager();
		
		InvMarketGroup a = em.find(InvMarketGroup.class, 5);
		System.out.println(a.getMarketGroupName());
		System.out.println(a.getParentGroupID().getMarketGroupName());
		System.out.println(a.getGraphicID().getDescription());
		System.out.println(a.isHasTypes());

		
		StaStation s = em.find(StaStation.class, 60000004);
		System.out.println(s.getStationName());
		
		
		MapSolarSystem m = em.find(MapSolarSystem.class, 30000001);
		System.out.println(m.getSolarSystemName());
		System.out.println(m.getRegionID().getRegionName());
		System.out.println(m.getConstellationID().getConstellationName());
		System.out.println(m.getConstellationID().getRegionID().getRegionName());
		
		MapRegion mr = em.find(MapRegion.class, 10000001);
		System.out.println(mr.getRegionName());
	
	}



}
