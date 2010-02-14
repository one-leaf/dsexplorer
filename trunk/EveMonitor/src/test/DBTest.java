package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import luz.eveMonitor.entities.inv.InvMarketGroup;
import luz.eveMonitor.entities.map.MapRegion;
import luz.eveMonitor.entities.map.MapSolarSystem;
import luz.eveMonitor.entities.sta.StaStation;

public class DBTest {

	public static void main(String[] args) throws Exception {
		
		EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("eveDB");
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
