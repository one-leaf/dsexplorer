package test;

import java.text.SimpleDateFormat;
import java.util.List;

import luz.eveMonitor.datastructure.PyDict;
import luz.eveMonitor.datastructure.DBRow;
import luz.eveMonitor.utils.Reader;

import com.sun.jna.Memory;

public class EveTest2 {
	static Memory buf = new Memory(100);
	static Memory buf2 = new Memory(32);
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Reader r= new Reader();
		r.findProcess();
		r.init();
		
		//showRows(r);
		showDicts(r);
		
		
	
	}
	private static void showDicts(Reader r) throws Exception {
		List<PyDict> dicts = r.findDict();
		StringBuilder sb=new StringBuilder();
		int rows=0;
		for (PyDict dict : dicts) {
			sb.setLength(0);
			sb.append(dict.getMa_Fill()).append('\t');
			sb.append(dict.getMa_Used()).append('\t');
			sb.append(dict.getMa_Mask()).append('\t');
			
			System.out.println(sb.toString());
			rows++;			
		}
		System.out.println("rows "+rows);
	}

	private static void showRows(Reader r) throws Exception {
		List<DBRow> list = r.getRows();
		StringBuilder sb=new StringBuilder();
		int rows=0;
		for (DBRow marketRow : list) {
			sb.setLength(0);
			sb.append(rows)								.append('\t');
			sb.append(String.format("%08X", marketRow.getAddress())).append('\t');//sb.append("price     ");
			sb.append(marketRow.getPrice())				.append('\t');//sb.append("vol rem   ");
			sb.append(marketRow.getVolRem())			.append('\t');//sb.append("issued    ");
			sb.append(sdf.format(marketRow.getIssued())).append('\t');//sb.append("orderID   ");
			sb.append(marketRow.getOrderID())			.append('\t');//sb.append("volEnter  ");
			sb.append(marketRow.getVolEnter())			.append('\t');//sb.append("volMin    ");
			sb.append(marketRow.getVolMin())			.append('\t');//sb.append("stationID ");
			sb.append(marketRow.getStationID())			.append('\t');//sb.append("regionID  ");
			sb.append(marketRow.getRegionID())			.append('\t');//sb.append("systemID  ");
			sb.append(marketRow.getSystemID())			.append('\t');//sb.append("jumps     ");
			sb.append(marketRow.getJumps())				.append('\t');//sb.append("type      ");
			sb.append(marketRow.getType())				.append('\t');//sb.append("range     ");
			sb.append(marketRow.getRange())				.append('\t');//sb.append("duration  ");
			sb.append(marketRow.getDuration())			.append('\t');//sb.append("bid       ");
			sb.append(marketRow.getBid())				.append('\t');
			System.out.println(sb.toString());
			rows++;
		}	
	}

	
}
