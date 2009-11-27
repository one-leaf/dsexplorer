package luz.dsexplorer.objects.datastructure;

public class DSField {
	private DSType type;
	private String name;
	private Datastructure ds;
	private long byteCount;
	
	public DSField(DSType type, String name, Datastructure ds){
		this.type=type;
		this.name=name;
		this.ds=ds;
		this.byteCount=type.getByteCount();
	}

	public Datastructure getDatastructure(){
		return ds;
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
		return ds.getOffset(this);
	}
	

	public void setType(DSType type) {
		this.type = type;
		this.byteCount=type.getByteCount();
		ds.refresh(this);
	}
	
	public void setName(String name) {
		this.name = name;
		ds.refresh(this);
	}



	
	public void setByteCount(long byteCount){
		if (!type.isFixedSize()){
			this.byteCount=byteCount;
		}else{
			this.byteCount=type.getByteCount();
		}
		ds.refresh(this);
	}
	
	

	
}
