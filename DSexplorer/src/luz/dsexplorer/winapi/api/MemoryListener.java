package luz.dsexplorer.winapi.api;

import luz.dsexplorer.objects.datastructure.DSType;

import com.sun.jna.Memory;


public abstract class MemoryListener {
	private ResultList results;
	private String value;
	private DSType type;
	private int overlapping;

	public void add(Result result) {
		results.add(result);
	}
	
	public String getValue() {
		return value;
	}
	
	public DSType getType() {
		return type;
	}
	
	/**
	 * To optimize memory reading, the reading algorithm divides large memory areas into
	 * smaller ones. This value forces the reading algorithm to overlap these memory areas by
	 * the given value. This is usefull if the seach pattern is longer than one Byte. In this case, 
	 * the MemoryListener has only to worry about one area at each time. Values at the end of an 
	 * area will repeat at the beginning of the next area. In general this value should be 
	 * 'size of the search pattern -1'.
	 * @param overlapping
	 */
	public int getOverlapping(){
		return overlapping;
	}
		
	public void init(ResultList results, String value, DSType type){
		this.results=results;
		this.value=value;
		this.type=type;
		switch (type){
			case Byte1:		overlapping=0;	break;	
			case Byte2:		overlapping=1;	break;	
			case Byte4:		overlapping=3;	break;	
			case Byte8:		overlapping=7;	break;
			case Float:		overlapping=3;	break;	
			case Double:	overlapping=7;	break;
			case ByteArray:	overlapping=value.length()/2;	break;	
			case Ascii:		overlapping=value.length();		break;	
			case Unicode:	overlapping=value.length();		break;	
		}
	}
	
	public abstract void mem(Memory outputBuffer, long address, long size);

}
