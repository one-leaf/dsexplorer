package test;

import luz.eveMonitor.utils.Reader;

public class EveFindStatic {
	public static void main(String[] args) {
		Reader r = new Reader();
		r.findProcess();
		r.findRootAddr(0x1885DA08L);
	}

}
