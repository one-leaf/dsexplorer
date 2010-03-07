package luz.eveMonitor.datastructure.other;

import java.util.HashSet;
import java.util.Iterator;

import luz.eveMonitor.entities.eveMon.Order;

public class OrderSet implements Iterable<Order> {

	private HashSet<Order> orders=new HashSet<Order>();
	
	
	public void add(Order newOrder) {
		orders.remove(newOrder);
		orders.add(newOrder);
	}


	@Override
	public Iterator<Order> iterator() {
		return orders.iterator();
	}
	
	public int size(){
		return orders.size();
	}


}
