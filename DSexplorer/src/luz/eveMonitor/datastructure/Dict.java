package luz.eveMonitor.datastructure;

import java.util.Iterator;

import luz.dsexplorer.winapi.api.Process;

public class Dict extends PyObject {

	public Dict(int address, Process process) {
		super(address, process, 400);
	}

	
	public int		getMa_Fill    (){return super.getInt   (24);}
	public int		getMa_Used    (){return super.getInt   (28);}
	public int		getMa_Mask    (){return super.getInt   (32);}
	public int		getMa_Table   (){return super.getInt   (36);}
	public int		getMa_Lokup   (){return super.getInt   (40);}
	public PyDictEntry	getDictEntry(int i){
		int offset=(9*4)+((4*3)*i);
		return new PyDictEntry(super.getInt(offset+0), super.getInt(offset+4), super.getInt(offset+8), process);
	}
	
	public Iterator<PyDictEntry> getDictEntries(final int userLimit){
		return new Iterator<PyDictEntry>() {
			int index=0;
			int limit=Math.min(userLimit, getMa_Used());

			@Override
			public boolean hasNext() {
				return index<limit;
			}

			@Override
			public PyDictEntry next() {
				return getDictEntry(index++);
			}

			@Override
			public void remove() {}
		};
	}
	
	@Override
	public Dict getNext(){
		return new Dict(getNextPtr(), process);
	}
	@Override
	public Dict getPrev(){
		return new Dict(getPrevPtr(), process);
	}	

}