package luz.eveMonitor.datastructure.other;

import java.util.HashMap;

import luz.eveMonitor.entities.eveMon.Order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TypeGroupMap extends HashMap<Short, TypeGroup> {
	private static final long serialVersionUID = 6338817211296046005L;
	private static final Log log = LogFactory.getLog(TypeGroupMap.class);
//	OrderSet orders = new OrderSet();
	TransactionSet transs;
	TransactionSettings settings;


	public TypeGroupMap(TransactionSettings settings) {
		this.settings=settings;
		transs = new TransactionSet(settings.getNumber());
	}

	private TypeGroup getTypeGroup(short typeId) {
		TypeGroup tg = this.get(typeId);
		if (tg == null) {
			tg = new TypeGroup();
			this.put(typeId, tg);
		}
		return tg;
	}

	public int getStamp(short typeId) {
		return getTypeGroup(typeId).getStamp();
	}

	public void setStamp(short typeId, int stamp) {
		getTypeGroup(typeId).setStamp(stamp);
	}

	public void addBuy(short typeId, Order order) {
		getTypeGroup(typeId).addBuy(order);
//		orders.add(order);
	}

	public void addSell(short typeId, Order order) {
		getTypeGroup(typeId).addSell(order);
//		orders.add(order);
	}
//
//	public OrderSet getOrders() {
//	return orders;
//}
	
	public synchronized void refresh(TransactionSettings ts) {
		this.settings=ts;
		transs.refresh(ts);		
	}
	
	@Override
	public void clear(){
		super.clear();
		transs.clear();
	}
	
	public void removeTypeGroup(short typeId) {
		log.debug("removing "+typeId);
		this.remove(typeId);
		transs.removeType(typeId);
	}
	
	public void createTrans(short typeId) {
		TypeGroup tp = getTypeGroup(typeId);
		log.trace("create Trans");
		tp.createTrans(settings);
		log.trace("add Trans");
		transs.addAll(tp.getTrans());
		log.trace("add Trans done");
	}

	public TransactionSet getTransactions() {
		return transs;
	}
	
	public TransactionSettings getTransactionSettings() {
		return settings;
	}

	public int getOrderCount() {
		int count=0;
		for (TypeGroup tg : this.values())
			count+=tg.getOrderCount();			
		return count;
	}

	public int getTypeCount() {
		return this.values().size();
	}




}
