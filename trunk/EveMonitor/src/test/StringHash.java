package test;

import luz.eveMonitor.datastructure.python.PyObjectFactory;

public class StringHash {
	
	public static void main(String[] args) throws Exception {
		String x;

//		x = "GetSystemAsks";				//0x49CB90BC
//		x = "GetOrders";					//0x3CD8D06E
//		x = "GetRegionBest";				//0xADEDC177
//		x = "RefreshAll";					//0xAA83BD40
//		x = "GetBestBid";					//0x5D55C05D
//		x = "DumpQuotes";					//0xE7253A23
//		x = "GetMarketProxy";				//0xB834478A
//		x = "svc.marketQuote";				//0x91CC1F37
//		x = "marketquote";					//0x0957B5AF
//		x = "groupsQuotes";					//0xCD718E8B
//		x = "groupQuotes";					//0x760682FD	
//		x = "watingFor";					//0x1CA92BAC
//		x = "GetCharOrders";				//0xDEB28DAE
//		x = "orderCache";					//0x8FDE2CCC	<--WORKS
		x = "minAutoPilotWarpInDistance";	//0xA9200DB1	<--WORKS (PyInt=15000)
//		x = "currentmoney";					//0x56587EB9	<--WORKS (PyFloat)
		int hash=(int)PyObjectFactory.pyStringHash(x);
		System.out.println(String.format("0x%08X", hash));

		
		
		
	}


}
