package luz.dsexplorer.winapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import luz.dsexplorer.winapi.objects.Module;

import com.sun.jna.Pointer;

public class ModuleList implements Iterable<Module> {
	private static final long serialVersionUID = 6688283424432327658L;
	private Pointer hProcess;
	private List <Module> list= new LinkedList <Module>();

	
	public ModuleList(Pointer hProcess) {
		this.hProcess=hProcess;
	}
	
	public void add(Pointer hModule){
		list.add(new Module(hProcess, hModule));
	}	

	
	public Module get(Integer index){
		return list.get(index);
	}


	@Override
	public Iterator<Module> iterator() {
		return list.iterator();
	}

	public int size() {
		return list.size();
	}


}
