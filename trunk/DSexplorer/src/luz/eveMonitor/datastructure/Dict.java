package luz.eveMonitor.datastructure;

import com.sun.jna.Memory;

public class Dict extends Memory {
	private int address;

	public Dict(int addr) {
		super(100);
		this.address=addr;
	}
	public int		getAddress    (){return address;}
	
	public int		getRefCount	  (){return super.getInt   (16);}	
	public int		getTypePtr	  (){return super.getInt   (20);}
	public int		getMa_Fill    (){return super.getInt   (24);}
	public int		getMa_Used    (){return super.getInt   (28);}
	public int		getMa_Mask    (){return super.getInt   (32);}
	public int		getMa_Table   (){return super.getInt   (36);}
	public int		getMa_Lokup   (){return super.getInt   (40);}
	public PyDictEntry	getDictEntry(int i){
		int offset=44+(4*i);
		return new PyDictEntry(super.getInt(offset+0), super.getInt(offset+4), super.getInt(offset+8));
	}

}