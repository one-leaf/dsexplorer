package luz.dsexplorer.winapi.tools;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import luz.dsexplorer.winapi.constants.BICompression;
import luz.dsexplorer.winapi.constants.DIBwUsage;
import luz.dsexplorer.winapi.constants.FType;
import luz.dsexplorer.winapi.constants.GAFlags;
import luz.dsexplorer.winapi.constants.GCFlags;
import luz.dsexplorer.winapi.constants.Messages;
import luz.dsexplorer.winapi.jna.Gdi32;
import luz.dsexplorer.winapi.jna.User32;
import luz.dsexplorer.winapi.jna.Gdi32.BITMAPINFO;
import luz.dsexplorer.winapi.jna.Gdi32.BITMAPINFOHEADER;
import luz.dsexplorer.winapi.jna.User32.ICONINFO;
import luz.dsexplorer.winapi.jna.User32.WNDENUMPROC;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class User32Tools {
	private static User32Tools INSTANCE=null;
	private static User32 u32 = User32.INSTANCE;
	private Gdi32 gdi32 = Gdi32.INSTANCE;
	
	private User32Tools(){}
	
	public static User32Tools getInstance(){
		if (INSTANCE==null)
			INSTANCE=new User32Tools();
		return INSTANCE;
	}
	
	////////////////////////////////////////////////////////////////////////
	
	private class Container<A>{
		A object=null;
		void setFirst(A object){
			this.object=object;
		}
		A getFirst(){
			return this.object;
		}		
	}
	
	public List<Pointer> EnumWindows(){
		final List<Pointer> mutex=Collections.synchronizedList(new LinkedList<Pointer>());
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				u32.EnumWindows(new WNDENUMPROC() {  
					public boolean callback(Pointer hWnd, Pointer userData) {  
						mutex.add(hWnd);
						return true;					
					} 
				}, null);				
			}
		}).start();
		
		try {
			synchronized (mutex) {
				mutex.wait(20);	//FIXME Find better method. Dont go below 10!
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		return mutex;
	}
	
	public Pointer getHwnd(final int pidx){
		final Container<Pointer> mutex=new Container<Pointer>();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				u32.EnumWindows(new WNDENUMPROC() {  
					public boolean callback(Pointer hWnd, Pointer userData) {  
						IntByReference lpdwProcessId=new IntByReference();
						u32.GetWindowThreadProcessId(hWnd,lpdwProcessId);
						int pid=lpdwProcessId.getValue();
						if (pid==pidx){
							synchronized (mutex) {
								mutex.setFirst(hWnd);
								mutex.notifyAll();
							}
							return false;
						}
						return true;
					} 
				}, null);				
			}
		}).start();
		
		try {
			synchronized (mutex) {
				mutex.wait(5000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		return mutex.getFirst();
	}
	
    public Pointer GetParent(Pointer hWnd){
        return u32.GetParent(hWnd);
    }
    
    
    public Pointer GetAncestor(Pointer hwnd, GAFlags gaFlags){
        return u32.GetAncestor(hwnd, gaFlags.getValue());
    }
    
	public void GetWindowThreadProcessId(Pointer hWnd, IntByReference lpdwProcessId){
		u32.GetWindowThreadProcessId(hWnd,lpdwProcessId);
	}

		public Pointer SendMessageA(Pointer hWnd,Messages Msg,FType wParam,int lParam){
        return u32.SendMessageA(hWnd, Msg.getValue(), wParam.getValue(), lParam);
    }
	
	public static final int SMTO_NORMAL             = 0x00;	
	public static final int SMTO_BLOCK              = 0x01;
	public static final int SMTO_ABORTIFHUNG        = 0x02;
	public static final int SMTO_NOTIMEOUTIFNOTHUNG = 0x08;
	public static final int SMTO_ERRORONEXIT        = 0x20;
	
	public Pointer SendMessageTimeoutA(Pointer hWnd,Messages Msg,FType wParam,int lParam, int fuFlags, int uTimeout) throws Exception{
		PointerByReference lpdwResult = new PointerByReference();
		int ret = u32.SendMessageTimeoutA(hWnd, Msg.getValue(), wParam.getValue(), lParam, fuFlags, uTimeout, lpdwResult);
    	if (ret==0){
    		int err=Native.getLastError();
    		throw new Exception("GetClassLong failed. Error: "+err);
    	}
    	return lpdwResult.getValue();
	}
    
    public int GetClassLong(Pointer hWnd,GCFlags nIndex) throws Exception{
        int ret = u32.GetClassLongA(hWnd, nIndex.getValue());
    	if (ret==0){
    		int err=Native.getLastError();
    		throw new Exception("GetClassLong failed. Error: "+err);
    	}
    	return ret;
    }
    
    public Pointer getHIcon(Pointer hWnd){
    	try{
        	Pointer icon = SendMessageTimeoutA(hWnd, Messages.WM_GETICON, FType.ICON_SMALL, 0, SMTO_NORMAL, 20);
        if (icon!=null) return u32.CopyIcon(icon);
		} catch (Exception e) {
		}	
    	
        try{
        	Pointer icon = SendMessageTimeoutA(hWnd, Messages.WM_GETICON, FType.ICON_BIG, 0, SMTO_NORMAL, 20);
        if (icon!=null) return u32.CopyIcon(icon);
		} catch (Exception e) {
		}
		
        try{
        	Pointer icon = SendMessageTimeoutA(hWnd, Messages.WM_GETICON, FType.ICON_SMALL2, 0, SMTO_NORMAL, 20);
        if (icon!=null) return u32.CopyIcon(icon);
		} catch (Exception e) {
		}	

		try {
	    	int hiconSM = GetClassLong(hWnd, GCFlags.GCL_HICONSM);
	    	if (hiconSM!=0) return u32.CopyIcon(Pointer.createConstant(hiconSM));
		} catch (Exception e) {
		}		
		
		try {
	    	int hicon = GetClassLong(hWnd, GCFlags.GCL_HICON);
	    	if (hicon!=0) return u32.CopyIcon(Pointer.createConstant(hicon));
		} catch (Exception e) {
		}
    	
    	return null;
    }  

    public BufferedImage getIcon(Pointer hIcon) {
    	int   width =16;
    	int   height=16;
    	short depth =24;
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    	byte[] lpBitsColor = new byte[width*height*depth/8];
    	byte[] lpBitsMask  = new byte[width*height*depth/8];
    	BITMAPINFO info     = new BITMAPINFO();
    	BITMAPINFOHEADER hdr= new BITMAPINFOHEADER();
    	info.bmiHeader   =hdr;
    	hdr.biWidth      =width;
    	hdr.biHeight     =height;
    	hdr.biPlanes     =1;
    	hdr.biBitCount   =depth;
    	hdr.biCompression=BICompression.BI_RGB.getValue();
    	
    	Pointer hDC =u32.GetDC(null);
    	ICONINFO piconinfo = new ICONINFO();
    	u32.GetIconInfo(hIcon, piconinfo);
        gdi32.GetDIBits(hDC, piconinfo.hbmColor, 0, height, lpBitsColor, info, DIBwUsage.DIB_RGB_COLORS.getValue());
        gdi32.GetDIBits(hDC, piconinfo.hbmMask , 0, height, lpBitsMask , info, DIBwUsage.DIB_RGB_COLORS.getValue());
        
        int r, g, b, a, argb;
        int x=0, y=height-1;
        for (int i = 0; i < lpBitsColor.length; i=i+3) {
        	b =      lpBitsColor[i  ] & 0xFF;
        	g =      lpBitsColor[i+1] & 0xFF;
        	r =      lpBitsColor[i+2] & 0xFF;
        	a = 0xFF-lpBitsMask [i  ] & 0xFF;
        	//System.out.println(lpBitsMask[i]+" "+lpBitsMask[i+1]+" "+lpBitsMask[i+2]);
        	argb= (a<<24) | (r<<16) | (g<<8) | b;      	
        	image.setRGB(x, y, argb);
        	x=(x+1)%width;
        	if (x==0) y--;
		}

        u32.ReleaseDC(null, hDC);
	    
	    return image;
    }

    
    
}
