package luz.dsexplorer.objects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import luz.dsexplorer.winapi.tools.Kernel32Tools;
import luz.dsexplorer.winapi.tools.User32Tools;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class ProcessList implements Iterable<Process> {
	private static final long serialVersionUID = 6688283424432327658L;
	private Map<Integer, Process> map = new HashMap<Integer, Process>();
	private List        <Process> list= new LinkedList      <Process>();
	
	private Kernel32Tools k32 = Kernel32Tools.getInstance(); 
	private User32Tools   u32 = User32Tools  .getInstance();
	
	
	public void add(Process p){
		map.put(p.getPid(), p);
		list.add(p);
	}
	
	public Process get(Integer index){
		return list.get(index);
	}
	
	public void refresh(){
		refreshProcesses();
		refreshWindows();
	}
	
	public void refreshProcesses(){
		try {
			ProcessList processes = k32.getProcessList();
			map.clear();
			list.clear();
			for (Process p : processes){ 
				map.put(p.getPid(), p);
				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshWindows(){
		for (Entry<Integer, Process> entry : map.entrySet())
			entry.getValue().clearHwnds();

		List<Pointer> hWnds = u32.EnumWindows();
		IntByReference lpdwProcessId=new IntByReference();
		int pid=0;
		
		for (Pointer hWnd : hWnds) {			
			u32.GetWindowThreadProcessId(hWnd,lpdwProcessId);
			pid=lpdwProcessId.getValue();			
			map.get(pid).addHwnd(hWnd);			
		}		
	}

	@Override
	public Iterator<Process> iterator() {
		return list.iterator();
	}

	public int size() {
		return list.size();
	}

}
