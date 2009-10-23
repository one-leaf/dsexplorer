package luz.dsexplorer.tools;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import luz.dsexplorer.interfaces.Gdi32;
import luz.dsexplorer.interfaces.User32;
import luz.dsexplorer.interfaces.Gdi32.BITMAPINFO;
import luz.dsexplorer.interfaces.Gdi32.BITMAPINFOHEADER;
import luz.dsexplorer.interfaces.User32.ICONINFO;
import luz.dsexplorer.interfaces.User32.WNDENUMPROC;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class User32Tools {
	private static User32Tools INSTANCE=null;
	private static User32 u32 = User32.INSTANCE;
	private static Kernel32Tools k32 = Kernel32Tools.getInstance();
	private Gdi32 gdi32 = Gdi32.INSTANCE;
	
	////////////////////////////////////////////////////////////////////////
	
	private User32Tools(){}
	
	public static User32Tools getInstance(){
		if (INSTANCE==null)
			INSTANCE=new User32Tools();
		return INSTANCE;
	}
	
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
	
	public Pointer getHwnd(Process process){
		final int pidx=process.getPid();
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
    
    
	public static final int GA_PARENT=1;
	public static final int GA_ROOT=2;
	public static final int GA_ROOTOWNER=3;
    Pointer GetAncestor(Pointer hwnd, int gaFlags){
        return u32.GetAncestor(hwnd, gaFlags);
    }
    
	public void GetWindowThreadProcessId(Pointer hWnd, IntByReference lpdwProcessId){
		u32.GetWindowThreadProcessId(hWnd,lpdwProcessId);
	}
	
	
	public static final int WM_GETICON=0x7f;
	
	public static final int ICON_SMALL=0;		//wParam
	public static final int ICON_BIG=1;		//wParam
    
	public Pointer SendMessageA(Pointer hWnd,int Msg,int wParam,int lParam){
        return u32.SendMessageA(hWnd, Msg, wParam, lParam);
    }
    
	public static final int GCL_HICON=-14;
	public static final int GCL_HICONSM=-34;
	
    public int GetClassLong(Pointer hWnd,int nIndex) throws Exception{
        int ret = u32.GetClassLongA(hWnd, nIndex);
    	if (ret==0){
    		int err=k32.GetLastError();
    		throw new Exception("GetClassLong failed. Error: "+err);
    	}
    	return ret;
    }
    
    public Pointer getHIcon(Pointer hWnd){
    	Pointer iconS = u32.SendMessageA(hWnd, WM_GETICON, ICON_SMALL, 0);
    	if (iconS!=null) return u32.CopyIcon(iconS);
        
        Pointer iconB = u32.SendMessageA(hWnd, WM_GETICON, ICON_BIG, 0);
        if (iconB!=null) return u32.CopyIcon(iconB);

		try {
	    	int hiconSM = GetClassLong(hWnd, GCL_HICONSM);
	    	if (hiconSM!=0) return u32.CopyIcon(Pointer.createConstant(hiconSM));
		} catch (Exception e) {
		}		
		
		try {
	    	int hicon = GetClassLong(hWnd, GCL_HICON);
	    	if (hicon!=0) return u32.CopyIcon(Pointer.createConstant(hicon));
		} catch (Exception e) {
		}
    	
    	return null;
    }  

    
    public static final int DI_MASK = 1;
    public static final int DI_IMAGE = 2;
    public static final int DI_NORMAL = 3;
    public static final int DIB_RGB_COLORS=0;
    public static final int BI_RGB = 0;
    public static final int BI_BITFIELDS=3;

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
    	hdr.biCompression=BI_RGB;
    	
    	Pointer hDC =u32.GetDC(null);
    	ICONINFO piconinfo = new ICONINFO();
    	u32.GetIconInfo(hIcon, piconinfo);
        gdi32.GetDIBits(hDC, piconinfo.hbmColor, 0, height, lpBitsColor, info, DIB_RGB_COLORS);
        gdi32.GetDIBits(hDC, piconinfo.hbmMask , 0, height, lpBitsMask , info, DIB_RGB_COLORS);
        
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
    
    
//  public Pointer getHbitmap(Pointer hIcon){
//	Pointer hBitmap;
//	int width=16, height=16;
//	
////	ICONINFO piconinfo = new ICONINFO();
////	u32.GetIconInfo(hIcon, piconinfo);
////	System.out.println(piconinfo.xHotspot);
////	System.out.println(piconinfo.yHotspot);
////	hBitmap=piconinfo.hbmColor;
//	
//	Pointer hDC = gdi32.CreateCompatibleDC(null);
//	Pointer hbm = gdi32.CreateCompatibleBitmap(hDC, width, height);
//	gdi32.SelectObject( hDC, hbm);
//	u32.DrawIcon(hDC, 0, 0, hIcon);
//	gdi32.DeleteDC(hDC);
//	hBitmap=hbm;
//	
//	return hBitmap;    	
//}
//    
//    public BufferedImage getIcon2(Pointer hIcon) {
//    	BufferedImage image = getIcon2(hIcon, DI_NORMAL, BufferedImage.TYPE_INT_ARGB);
//    	BufferedImage mask  = getIcon2(hIcon, DI_MASK, BufferedImage.TYPE_INT_RGB);
//
//    	int width = image.getWidth();
//    	int height = image.getHeight();
//    	for (int x = 0; x < width; x++) {
//    		for (int y = 0; y < height; y++) {
//    			int masked = mask.getRGB(x, y);
//    			if ((masked & 0x00FFFFFF) == 0) {
//    				int rgb = image.getRGB(x, y);
//    				rgb = rgb | 0xFF000000;
//    				image.setRGB(x, y, rgb);
//    			}
//    		}
//    	}
//    	return image;
//    }    
//    
//    public BufferedImage getIcon2(Pointer hIcon, int diFlags, int imageType) {
//    	int width = 16;
//    	int height = 16;
//    	BufferedImage image = new BufferedImage(width, height, imageType);
//
//    	
//    	Pointer hDC =u32.GetDC(null);
//    	//Pointer hDC = gdi32.CreateCompatibleDC(null);
//    	Pointer hBitmap = gdi32.CreateCompatibleBitmap(hDC, width, height);
//    	Pointer hOldBitmap=gdi32.SelectObject(hDC, hBitmap);
//    	u32.DrawIconEx(hDC, 0, 0, hIcon, width, height, 0, null, diFlags);
//    	gdi32.SelectObject(hDC, hOldBitmap);
//    	
//    	for (int x = 0; x < width; x++) {
//    		for (int y = 0; y < width; y++) {
//	    		int rgb = gdi32.GetPixel(hDC, x, y);
//	    		int b = (rgb>>16) & 0xFF;
//	    		int g = (rgb>> 8) & 0xFF;
//	    		int r = (rgb    ) & 0xFF;	    		
//	    		rgb=(r<<16) | (g<<8) | b;
//	    		image.setRGB(x, y, rgb);
//	    	}
//	    }
//    	System.out.println();
//    	gdi32.DeleteObject(hOldBitmap);
//	    gdi32.DeleteObject(hBitmap);
//	    gdi32.DeleteDC(hDC);
//	    return image;
//    }
    
    
}
