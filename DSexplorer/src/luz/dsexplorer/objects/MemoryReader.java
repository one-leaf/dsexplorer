package luz.dsexplorer.objects;

import luz.dsexplorer.winapi.interfaces.Kernel32.MEMORY_BASIC_INFORMATION;
import luz.dsexplorer.winapi.tools.Kernel32Tools;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

public abstract class MemoryReader {
	private Process process;


	public MemoryReader(Process process){
		this.process=process;
	}
    
	public void read(long from, long to) throws Exception{
		int bufferSize=512*1024;
		int readSize;
		long regionEnd;
		MEMORY_BASIC_INFORMATION info;
		Memory outputBuffer = new Memory(bufferSize);
		
		for (long regionBegin = from; regionBegin < to; ) {
			info=process.VirtualQueryEx(Pointer.createConstant(regionBegin));
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
					
					System.out.println("Read:\t"+Long.toHexString(regionPart)+" - "+Long.toHexString(regionPart+readSize)+"\t"+Integer.toHexString(info.Type));
					try{
						process.ReadProcessMemory(Pointer.createConstant(regionPart), outputBuffer, readSize, null);
						mem(outputBuffer, regionPart, readSize);
					}catch(Exception e){
						System.out.println(e.getMessage()+"\t"+Long.toHexString(regionPart)+"\t"+Integer.toHexString(info.Type));
					}
				}
			}
			regionBegin+=info.RegionSize;
		}
	}
	
	
	public abstract void mem(Memory outputBuffer, long address, long size);


}
