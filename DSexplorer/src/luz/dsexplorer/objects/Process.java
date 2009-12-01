package luz.dsexplorer.objects;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import luz.dsexplorer.objects.datastructure.DSType;
import luz.dsexplorer.objects.listener.MemoryListener;
import luz.dsexplorer.winapi.interfaces.Kernel32.LPPROCESSENTRY32;
import luz.dsexplorer.winapi.interfaces.Kernel32.MEMORY_BASIC_INFORMATION;
import luz.dsexplorer.winapi.interfaces.Ntdll.PEB;
import luz.dsexplorer.winapi.interfaces.Ntdll.PROCESS_BASIC_INFORMATION;
import luz.dsexplorer.winapi.tools.Kernel32Tools;
import luz.dsexplorer.winapi.tools.NtdllTools;
import luz.dsexplorer.winapi.tools.PsapiTools;
import luz.dsexplorer.winapi.tools.Shell32Tools;
import luz.dsexplorer.winapi.tools.User32Tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


public class Process {
	private static final Log log = LogFactory.getLog(Process.class);
	
	private final int pid;
	private final String szExeFile;
	private final int cntThreads;
	private final int th32ParentProcessID;
	private final int pcPriClassBase;
	
	private MemoryListener listener;
	private MemoryListener Byte1listener;
	private MemoryListener Byte2listener;
	private MemoryListener Byte4listener;
	private MemoryListener Byte8listener;
	private MemoryListener Floatlistener;
	private MemoryListener Doublelistener;
	
	
	private Kernel32Tools k32   = Kernel32Tools.getInstance();
	private PsapiTools    psapi = PsapiTools   .getInstance();
	private Shell32Tools  s32   = Shell32Tools .getInstance();
	private NtdllTools    nt    = NtdllTools   .getInstance();
	private User32Tools   u32   = User32Tools  .getInstance();
	private List<Pointer> hWnds = new LinkedList<Pointer>();
	
	public Process(LPPROCESSENTRY32 pe32) {
		this.pid=pe32.th32ProcessID;
		this.szExeFile=pe32.getSzExeFile();
		this.cntThreads=pe32.cntThreads;
		this.pcPriClassBase=pe32.pcPriClassBase.intValue();
		this.th32ParentProcessID=pe32.th32ParentProcessID;
		
		Byte1listener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				byte current;
				byte target=Byte.parseByte(getValue());
				for (long pos = 0; pos < size; pos=pos+1) {
					current=outputBuffer.getByte(pos);
					if (current==target){
						add(new Result(address+pos, current, getType()));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		Byte2listener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Short current;
				Short target=Short.parseShort(getValue());
				//TODO handle data which overlaps buffer limits
				for (long pos = 0; pos < size-1; pos=pos+1) {
					current=outputBuffer.getShort(pos);
					if (current==target){
						add(new Result(address+pos, current, getType()));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		Byte4listener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Integer current;
				Integer target=Integer.parseInt(getValue());
				//TODO handle data which overlaps buffer limits
				for (long pos = 0; pos < size-3; pos=pos+1) {
					current=outputBuffer.getInt(pos);
					if (current==target){
						add(new Result(address+pos, current, getType()));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		Byte8listener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Long current;
				Long target=Long.parseLong(getValue());
				//TODO handle data which overlaps buffer limits
				for (long pos = 0; pos < size-7; pos=pos+1) {
					current=outputBuffer.getLong(pos);
					if (current==target){
						add(new Result(address+pos, current, getType()));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		Floatlistener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Float current;
				Float target=Float.parseFloat(getValue());
				//TODO handle data which overlaps buffer limits
				for (long pos = 0; pos < size-3; pos=pos+1) {
					current=outputBuffer.getFloat(pos);
					if (Math.round(current)==Math.round(target)){
						add(new Result(address+pos, current, getType()));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		Doublelistener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Double current;
				Double target=Double.parseDouble(getValue());
				//TODO handle data which overlaps buffer limits
				for (long pos = 0; pos < size-7; pos=pos+1) {
					current=outputBuffer.getDouble(pos);
					if (Math.round(current)==Math.round(target)){
						add(new Result(address+pos, current, getType()));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		//TODO ByteArray Search
		//TODO Ascii Search
		//TODO Unicode Search

	}

	private Pointer handleCache =null;
	public Pointer getHandle() throws Exception{
		if (handleCache!=null)
			return handleCache;
		handleCache = k32.OpenProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, this.pid);
		return handleCache;
	}
	
	
	public void clearHwnds() {
		hWnds.clear();
	}
	
	public void addHwnd(Pointer hWnd) {
		hWnds.add(hWnd);		
	}
	
	public List<Pointer> getHwnds(){
		return hWnds;
	}
	
	

	//Getter
	
	public int getPid() {
		return pid;
	}

	public String getSzExeFile() {
		return szExeFile;
	}

	public int getCntThreads() {
		return cntThreads;
	}
	
	public int getTh32ParentProcessID() {
		return th32ParentProcessID;
	}	

	public int getPcPriClassBase() {
		return pcPriClassBase;
	}
	
	public String getProcessImageFileName(){
		 try {
			return psapi.GetProcessImageFileNameA(getHandle());
		} catch (Exception e) {
			return "";
		}
	}

	public String getModuleFileNameExA(){
		 try {
			return psapi.GetModuleFileNameExA(getHandle(), null);
		} catch (Exception e) {
			return "";
		}
	}

	public List<Module> getModules(){
		try {
			return psapi.EnumProcessModules(getHandle());
		} catch (Exception e) {
			return new LinkedList<Module>();
		}
	}

	private Module moduleCache=null;
	public Module getModule(){
		if (moduleCache!=null)
			return moduleCache;	

		List<Module> modules = getModules();
		if (modules.size()>0)
			moduleCache=modules.get(0);

		return moduleCache;
	}
	
	public Pointer getBase() {
		Module module = getModule();
		if (module!=null)
			return module.getLpBaseOfDll();
		else
			return null;
	}
	
	public int getSize() {
		Module module = getModule();
		if (module!=null)
			return module.getSizeOfImage();
		else
			return 0;
	}
	
	public int getMemUsage() {
		try {
			return psapi.GetProcessMemoryInfo(getHandle()).WorkingSetSize;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private ImageIcon iconCache=null;
	public ImageIcon getIcon(){
		if (iconCache!=null)
			return iconCache;
		
		Pointer hIcon = null;
		
        hIcon=s32.ExtractSmallIcon(this.getModuleFileNameExA(), 1);        
        if (hIcon==null){
        	hIcon=s32.ExtractSmallIcon(this.getSzExeFile(), 1);
        }
        
        if (hIcon==null){      	
        	if(hWnds.size()>0){
        		hIcon = u32.getHIcon(u32.GetAncestor(hWnds.get(0), User32Tools.GA_ROOTOWNER));
        	}
//        	for (Pointer hWnd : hWnds) {
//        		hIcon = u32.getHIcon(hWnd);
//        		if (hIcon!=null)
//        			break;
//			}
        }
        
        if (hIcon!=null)
        	iconCache=new ImageIcon(u32.getIcon(hIcon));
        else
        	iconCache=new ImageIcon();
        return iconCache;        
	}
	
	private PROCESS_BASIC_INFORMATION infoCache=null;
	public PROCESS_BASIC_INFORMATION getPROCESS_BASIC_INFORMATION(){
		if (infoCache!=null)
			return infoCache;
		try {
			infoCache = nt.NtQueryInformationProcess(getHandle(), NtdllTools.ProcessBasicInformation);
		} catch (Exception e) {}
		
		return infoCache;
	}
	
	private PEB pebCache=null;
	public PEB getPEB() throws Exception{
		if (pebCache!=null)
			return pebCache;
		pebCache=getPROCESS_BASIC_INFORMATION().getPEB();
		return pebCache;
	}
	
	public MEMORY_BASIC_INFORMATION VirtualQueryEx(Pointer lpAddress) throws Exception{
		return k32.VirtualQueryEx(getHandle(), lpAddress);
	}
	
	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws Exception{
		k32.ReadProcessMemory(getHandle(), pointer, outputBuffer, nSize, outNumberOfBytesRead);
	}
	
	private void search(long from, long to) throws Exception{
		int bufferSize=512*1024;
		int readSize;
		long regionEnd;
		MEMORY_BASIC_INFORMATION info;
		Memory outputBuffer = new Memory(bufferSize);
		long maxRegionSize=0;
		
		for (long regionBegin = from; regionBegin < to; ) {
			info=VirtualQueryEx(Pointer.createConstant(regionBegin));
			maxRegionSize=Math.max(maxRegionSize, info.RegionSize);
			regionEnd=regionBegin+info.RegionSize;
		
			if (info.State==Kernel32Tools.MEM_COMMIT 
				&& (info.Protect&Kernel32Tools.PAGE_NOACCESS    )==0
				&& (info.Protect&Kernel32Tools.PAGE_GUARD       )==0
				&& (info.Protect&Kernel32Tools.PAGE_EXECUTE_READ)==0
				&& (info.Protect&Kernel32Tools.PAGE_READONLY    )==0
			){
				//System.out.println("Region:\t"+Long.toHexString(regionBegin)+" - "+Long.toHexString(regionBegin+regionSize)+"\t"+info.BaseAddress);
				
				for (long regionPart = regionBegin; regionPart < regionEnd; regionPart+=bufferSize) {
					if ((regionPart+bufferSize)<regionEnd)
						readSize=bufferSize;
					else
						readSize=(int)(regionEnd-regionPart);
					
					log.trace("Read:\t"+Long.toHexString(regionPart)+" - "+Long.toHexString(regionPart+readSize)+"\t"+Integer.toHexString(info.Type));
					try{
						ReadProcessMemory(Pointer.createConstant(regionPart), outputBuffer, readSize, null);
						listener.mem(outputBuffer, regionPart, readSize);
					}catch(Exception e){
						log.warn(e.getMessage()+"\t"+Long.toHexString(regionPart)+"\t"+Integer.toHexString(info.Type));
					}
				}
			}
			regionBegin+=info.RegionSize;
		}
		log.debug("maxRegionSize "+(maxRegionSize/1024)+" kB");
	}

	public ResultList search(long from, long to, final String value, final DSType type) throws Exception {
		log.debug("search from "+Long.toHexString(from)+" to "+Long.toHexString(to)+" value "+value+" type "+type);
		final ResultList results = new ResultList(this);
		long timer=System.currentTimeMillis();
		if (value==null || value.trim().equals(""))
			return results;		

		switch (type){
			case Byte1:		listener=Byte1listener;		break;	
			case Byte2:		listener=Byte2listener;		break;	
			case Byte4:		listener=Byte4listener;		break;	
			case Byte8:		listener=Byte8listener;		break;
			case Float:		listener=Floatlistener;		break;	
			case Double:	listener=Doublelistener;	break;	
		}
		listener.init(results, value, type);
		search(from, to);
		
		
		log.debug("timer "+(System.currentTimeMillis()-timer));
		return results;
	}

	
}
