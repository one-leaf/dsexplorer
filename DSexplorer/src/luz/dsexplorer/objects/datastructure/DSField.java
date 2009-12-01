package luz.dsexplorer.objects.datastructure;

public class DSField {
	private DSType type;
	private String name;
	private Datastructure datastructure;
	private long byteCount;

	/* only for XMLEncoder */
	public DSField(){
		
	}
	
	public DSField(DSType type, String name, Datastructure datastructure){
		this(type, name, type.getByteCount(), datastructure);
	}
	
	public DSField(DSType type, String name, long byteCount, Datastructure datastructure){
		this.type=type;
		this.name=name;
		this.datastructure=datastructure;
		this.byteCount=byteCount;
	}

	public Datastructure getDatastructure(){
		return datastructure;
	}
	
	public DSType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public long getByteCount() {
		return byteCount;
	}
	
	public long getOffset(){
		return datastructure.getOffset(this);
	}
	

	public void setType(DSType type) {
		this.type = type;
		this.byteCount=type.getByteCount();
		if (datastructure!=null)
			datastructure.refresh(this);
	}
	
	public void setName(String name) {
		this.name = name;
		if (datastructure!=null)
			datastructure.refresh(this);
	}



	
	public void setByteCount(long byteCount){
		if (type==null || !type.isFixedSize()){
			this.byteCount=byteCount;
		}else{
			this.byteCount=type.getByteCount();
		}
		if (datastructure!=null)
			datastructure.refresh(this);
	}
	
	

	
}
