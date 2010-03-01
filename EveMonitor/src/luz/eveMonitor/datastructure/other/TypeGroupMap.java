package luz.eveMonitor.datastructure.other;

import java.util.HashMap;

import luz.eveMonitor.entities.eveMon.Order;

public class TypeGroupMap extends HashMap<Integer, TypeGroup> {
	private static final long serialVersionUID = 6338817211296046005L;
//	OrderSet orders = new OrderSet();
	TransactionSet transs = new TransactionSet();
	TransactionSettings settings;


	public TypeGroupMap(TransactionSettings settings) {
		this.settings=settings;
	}

	private TypeGroup getTypeGroup(int typeId) {
		TypeGroup tg = this.get(typeId);
		if (tg == null) {
			tg = new TypeGroup();
			this.put(typeId, tg);
		}
		return tg;
	}

	public int getStamp(int typeId) {
		return getTypeGroup(typeId).getStamp();
	}

	public void setStamp(int typeId, int stamp) {
		getTypeGroup(typeId).setStamp(stamp);
	}

	public void addBuy(int typeId, Order order) {
		getTypeGroup(typeId).addBuy(order);
//		orders.add(order);
	}

	public void addSell(int typeId, Order order) {
		getTypeGroup(typeId).addSell(order);
//		orders.add(order);
	}
//
//	public OrderSet getOrders() {
//	return orders;
//}
	
	
	public void createTrans(int typeId) {
		TypeGroup tp = getTypeGroup(typeId);
		tp.createTrans(settings);
		transs.addAll(tp.getTrans());
	}

	public TransactionSet getTransactions() {
		return transs;
	}
	
	public TransactionSettings getTransactionSettings() {
		return settings;
	}
}
