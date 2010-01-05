package luz.dsexplorer.winapi.api;

import java.io.File;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import luz.dsexplorer.datastructures.DSType;
import luz.dsexplorer.datastructures.Datastructure;
import luz.dsexplorer.winapi.constants.GAFlags;
import luz.dsexplorer.winapi.constants.ProcessInformationClass;
import luz.dsexplorer.winapi.jna.Kernel32.LPPROCESSENTRY32;
import luz.dsexplorer.winapi.jna.Kernel32.MEMORY_BASIC_INFORMATION;
import luz.dsexplorer.winapi.jna.Ntdll.PEB;
import luz.dsexplorer.winapi.jna.Ntdll.PROCESS_BASIC_INFORMATION;
import luz.dsexplorer.winapi.tools.Kernel32Tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


public class ProcessImpl implements Process {
	private static final Log log = LogFactory.getLog(ProcessImpl.class);
	private WinAPI winAPI;

	private List<Pointer> hWnds = new LinkedList<Pointer>();
	private final int pid;
	private final String szExeFile;
	private final int cntThreads;
	private final int th32ParentProcessID;
	private final int pcPriClassBase;
	
	private MemoryListener listener;
	private MemoryListener Byte1Listener;
	private MemoryListener Byte2Listener;
	private MemoryListener Byte4Listener;
	private MemoryListener Byte8Listener;
	private MemoryListener FloatListener;
	private MemoryListener DoubleListener;
	private MemoryListener ByteArrayListener;
	private MemoryListener AsciiListener;
	private MemoryListener UnicodeListener;
	

	
	public ProcessImpl(LPPROCESSENTRY32 pe32, WinAPI winapi) {
		this.winAPI=winapi;
		this.pid=pe32.th32ProcessID;
		this.szExeFile=pe32.getSzExeFile();
		this.cntThreads=pe32.cntThreads;
		this.pcPriClassBase=pe32.pcPriClassBase.intValue();
		this.th32ParentProcessID=pe32.th32ParentProcessID;
		
		Byte1Listener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				byte current;
				byte target=Byte.parseByte(getValue());
				for (long pos = 0; pos < size; pos=pos+1) {
					current=outputBuffer.getByte(pos);
					if (current==target){
						add(new Result(getResultList(), getType(), address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		Byte2Listener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Short current;
				Short target=Short.parseShort(getValue());
				for (long pos = 0; pos < size-1; pos=pos+1) {
					current=outputBuffer.getShort(pos);
					if (current.equals(target)){
						add(new Result(getResultList(), getType(), address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		Byte4Listener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Integer current;
				Integer target=Integer.parseInt(getValue());
				for (long pos = 0; pos < size-3; pos=pos+1) {
					current=outputBuffer.getInt(pos);
					if (current.equals(target)){
						add(new Result(getResultList(), getType(), address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		Byte8Listener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Long current;
				Long target=Long.parseLong(getValue());
				for (long pos = 0; pos < size-7; pos=pos+1) {
					current=outputBuffer.getLong(pos);
					if (current.equals(target)){
						add(new Result(getResultList(), getType(), address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		FloatListener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Float current;
				Float target=Float.parseFloat(getValue());
				for (long pos = 0; pos < size-3; pos=pos+1) {
					current=outputBuffer.getFloat(pos);
					if (Math.round(current)==Math.round(target)){
						add(new Result(getResultList(), getType(), address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		DoubleListener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				Double current;
				Double target=Double.parseDouble(getValue());
				for (long pos = 0; pos < size-7; pos=pos+1) {
					current=outputBuffer.getDouble(pos);
					if (Math.round(current)==Math.round(target)){
						add(new Result(getResultList(), getType(), address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		ByteArrayListener=new MemoryListener() {
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				byte[] current;
				String value=getValue().trim();
				byte[] target = new byte[value.length()/2];
				for (int i = 0; i < target.length; i++)
				   target[i] = (byte) Integer.parseInt(value.substring(2*i, 2*i+2), 16);
				int targetSize=target.length;
				boolean equal;
				for (long pos = 0; pos < size-targetSize; pos=pos+1) {
					current = outputBuffer.getByteArray(pos, targetSize);
					equal=true;
					for (int i = 0; i < target.length; i++)
						if (current[i]!=target[i]) equal=false;		
					if (equal){
						Datastructure ds = getType();
						ds.setByteCount(targetSize);
						add(new Result(getResultList(), ds, address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		AsciiListener=new MemoryListener() {
			Charset ascii=Charset.forName("US-ASCII");
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				String current;
				String target=getValue();
				int targetSize=target.length();
				for (long pos = 0; pos < size-targetSize; pos=pos+1) {
					current = new String(outputBuffer.getByteArray(pos, targetSize), ascii);
					if (current.equalsIgnoreCase(target)){
						Datastructure ds = getType();
						ds.setByteCount(targetSize);
						add(new Result(getResultList(), ds, address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};
		
		UnicodeListener=new MemoryListener() {
			Charset utf16=Charset.forName("UTF-16");
			@Override
			public void mem(Memory outputBuffer, long address, long size) {
				String current;
				String target=getValue();
				int targetSize=target.length()*2;	//Assume utf16 = 2 bytes
				for (long pos = 0; pos < size-targetSize; pos=pos+1) {
					current = new String(outputBuffer.getByteArray(pos, targetSize), utf16);
					if (current.equalsIgnoreCase(target)){
						Datastructure ds = getType();
						ds.setByteCount(targetSize);
						add(new Result(getResultList(), ds, address+pos, current));
						log.debug("Found:\t"+Long.toHexString(address+pos));
					}
				}
			}
		};

	}

	private Pointer handleCache =null;
	public Pointer getHandle() throws Exception{
		if (handleCache!=null)
			return handleCache;
		handleCache = winAPI.OpenProcess(Kernel32Tools.PROCESS_ALL_ACCESS, false, this.pid);
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
			return winAPI.GetProcessImageFileNameA(getHandle());
		} catch (Exception e) {
			return "";
		}
	}

	public String getModuleFileNameExA(){
		 try {
			return winAPI.GetModuleFileNameExA(getHandle(), null);
		} catch (Exception e) {
			return "";
		}
	}

	public List<Module> getModules(){
		//TODO add modules cache?
		try {
			List<Pointer> pointers = winAPI.EnumProcessModules(getHandle());
			List<Module> modules = new LinkedList<Module>();
			for (Pointer hModule : pointers) 
				modules.add(new Module(getHandle(), hModule, winAPI));
			return modules;
		} catch (Exception e) {
			return null;
		}
	}

	private Module moduleCache=null;
	public Module getModule(){
		if (moduleCache!=null)
			return moduleCache;	

		List<Module> modules = getModules();
		if (modules!=null && modules.size()>0)
			moduleCache=modules.get(0);

		return moduleCache;
	}
	
	@Override
	public String getStatic(Long address) {
		if (address==null)
			return null;
		List<Module> modules = getModules();
		int begin, end;
		for (Module module : modules) {
			begin = module.getLpBaseOfDll();
			end= begin+module.getSizeOfImage();
			//log.trace("module "+begin+" "+end+" "+module.getFileName());
			if (begin<=address && address<=end){
				File f = new File(module.getFileName());
				return  f.getName()+ "+" +String.format("%08X", address-begin);
			}
		}		
		return null;
	}
	
	public int getBase() {
		Module module = getModule();
		if (module!=null)
			return module.getLpBaseOfDll();
		else
			return -1;
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
			return winAPI.GetProcessMemoryInfo(getHandle()).WorkingSetSize;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private ImageIcon iconCache=null;
	public ImageIcon getIcon(){
		if (iconCache!=null)
			return iconCache;
		
		Pointer hIcon = null;
		
        hIcon=winAPI.ExtractSmallIcon(this.getModuleFileNameExA(), 1);        
        if (hIcon==null){
        	hIcon=winAPI.ExtractSmallIcon(this.getSzExeFile(), 1);
        }
        
        if (hIcon==null){      	
        	if(hWnds.size()>0){
        		hIcon = winAPI.getHIcon(winAPI.GetAncestor(hWnds.get(0), GAFlags.GA_ROOTOWNER));
        	}
//        	for (Pointer hWnd : hWnds) {
//        		hIcon = u32.getHIcon(hWnd);
//        		if (hIcon!=null)
//        			break;
//			}
        }
        
        if (hIcon!=null)
        	iconCache=new ImageIcon(winAPI.getIcon(hIcon));
        else
        	iconCache=new ImageIcon();
        return iconCache;        
	}
	
	private PROCESS_BASIC_INFORMATION infoCache=null;
	public PROCESS_BASIC_INFORMATION getPROCESS_BASIC_INFORMATION(){
		if (infoCache!=null)
			return infoCache;
		try {
			infoCache = winAPI.NtQueryInformationProcess(getHandle(), ProcessInformationClass.ProcessBasicInformation);
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
		return winAPI.VirtualQueryEx(getHandle(), lpAddress);
	}
	
	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws Exception{
		winAPI.ReadProcessMemory(getHandle(), pointer, outputBuffer, nSize, outNumberOfBytesRead);
	}
	
	private void search(long from, long to) throws Exception{
		int partSize=512*1024;
		int bufferSize=partSize+listener.getOverlapping();
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
				log.trace("Region:\t"+Long.toHexString(regionBegin)+" - "+Long.toHexString(regionBegin+info.RegionSize));
				
				for (long regionPart = regionBegin; regionPart < regionEnd; regionPart=regionPart+partSize) {
					if ((regionPart+bufferSize)<regionEnd)
						readSize=bufferSize;
					else
						readSize=(int)(regionEnd-regionPart);
					
					log.trace("Read:\t\t"+Long.toHexString(regionPart)+" - "+Long.toHexString(regionPart+readSize)+"\t"+Integer.toHexString(info.Type));
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
		final ResultList results = new ResultListImpl(this);
		long timer=System.currentTimeMillis();
		if (value==null || value.trim().equals(""))
			return results;		

		switch (type){
			case Byte1:		listener=Byte1Listener; 	break;	
			case Byte2:		listener=Byte2Listener;		break;	
			case Byte4:		listener=Byte4Listener; 	break;	
			case Byte8:		listener=Byte8Listener; 	break;
			case Float:		listener=FloatListener;		break;	
			case Double:	listener=DoubleListener;	break;
			case ByteArray:	listener=ByteArrayListener;	break;	
			case Ascii:		listener=AsciiListener;		break;	
			case Unicode:	listener=UnicodeListener;	break;	
		}
		listener.init(results, value, type);
		search(from, to);
		
		
		log.debug("timer "+(System.currentTimeMillis()-timer));
		return results;
	}



	
	//TODO stop search function
	//TODO search function progess

	
}
