package luz.dsexplorer.objects;

public enum DSType{
	//           fix, 	byteCount
	Byte1		(true,	 1), 
	Byte2		(true,	 2), 
	Byte4		(true,	 4), 
	Byte8		(true,	 8), 
	Float		(true,	 4), 
	Double		(true,	 8), 
	Ascii		(false,	32), 
	Unicode		(false,	32), 
	ByteArray	(false,	32), 
	Custom		(true ,	 4);
	private int byteCount;
	private boolean fixedSize;

	DSType(boolean fixedSize, int size){
		this.fixedSize=fixedSize;
		this.byteCount=size;
	}
	
	public int getByteCount()	       {return byteCount;}
	public boolean isFixedSize() {return fixedSize;}
}