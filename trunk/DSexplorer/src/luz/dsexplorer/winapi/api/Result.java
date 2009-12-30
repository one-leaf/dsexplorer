package luz.dsexplorer.winapi.api;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.TreeNode;

import luz.dsexplorer.datastructures.Container;
import luz.dsexplorer.datastructures.DSListener;
import luz.dsexplorer.datastructures.Datastructure;
import luz.dsexplorer.exceptions.NoProcessException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

@Root
public class Result implements TreeNode, DSListener, Cloneable {
	private ResultList resultList;	//needs to be reconstructed after loading
	@Attribute
	private Long address;
	@Element
	private Datastructure datastructure;
	private int fieldIndex;	//only for fields. used to calcualte the Offset

	private Object valueCache=null;
	private boolean valueCacheOK=false;
	
	private Long pointerCache=null;
	private boolean pointerCacheOK=false;
	
	protected Result(){}
	
	//used from Process to create results 
	public Result(ResultList resultList, Datastructure datastructure, long address, Object value){
		this.resultList=resultList;
		this.address=address;
		this.valueCache=value;
		this.valueCacheOK=false;
		this.datastructure=datastructure;
		this.datastructure.addListener(this);
	}
		
	//used from Results to create children
	public Result(ResultList resultList, Result parent, Datastructure datastructure, int fieldIndex){
		this.resultList=resultList;
		this.parent=parent;
		this.fieldIndex=fieldIndex;
		this.datastructure=datastructure;
		this.datastructure.addListener(this);
	}

	//Used to create manual Results
	public Result(Datastructure datastructure) {
		this.datastructure=datastructure;
		this.datastructure.addListener(this);		
	}

	public void setDatastructure(Datastructure newDS) {
		Datastructure oldDS=this.datastructure;

		if (isSimpleResult()){	//simple result
			log.debug("change ds");
			
			oldDS.removeListener(this);
			removeAllChilds();
			valueCacheOK=false;
			this.datastructure = newDS;
			newDS.addListener(this);
			if (!newDS.isContainer())
				newDS.setName(oldDS.getName());	//take over old name

			getResultList().reload(this);	//if ds changed->childs may change
		}else{
			log.debug("change field");
			Container dsParent = (Container)oldDS.getContainer();
			dsParent.replaceField(oldDS, newDS, fieldIndex);
		}
	}

	public Datastructure getDatastructure() {
		return datastructure;
	}

	public void setResultList(ResultList resultList) {
		this.resultList = resultList;
	}

	public ResultList getResultList() {
		return resultList;
	}
	
	public void setAddress(Long address) {
		if (isSimpleResult()){		//only for simple result
			this.address = address;
			invalidateParentAndChilds();
		}
	}
	
	public Long getAddress() {
		if (isSimpleResult()){	//simple result
			return address;
		}else{

			Container dsParent=(Container)datastructure.getContainer();
			Long address;
			if (dsParent.isPointer())
				address=((Result)parent).getPointer();	//warning, recursion, down to the root
			else	
				address=((Result)parent).getAddress();	//warning, recursion, down to the root
			if (address!=null){
				address+=dsParent.getOffset(fieldIndex);
			}
			return address;	
		}
	}
	
	public String getAddressString(){
		Long p=getAddress();
		return p==null?null:String.format("%1$08X", p);
	}
	
	
	private Long getPointer(){
		if (datastructure.isContainer()){
			Container c = (Container)datastructure;
			if (c.isPointer()){
				
				if(pointerCacheOK)
					return pointerCache;	
				
				Memory buffer=new Memory(4);
				try {
					log.trace("Pointer: "+getAddressString());
					getResultList().ReadProcessMemory(Pointer.createConstant(getAddress()), buffer, (int)buffer.getSize(), null);
					pointerCache=(long)buffer.getInt(0);
				} catch (NoProcessException e){
					pointerCache=null;
				} catch (Exception e) {
					log.warn(e);
					pointerCache=null;
				}
				pointerCacheOK=true;
				return pointerCache;
			}
		}
		return null;
	}
	
	public String getPointerString() {
		Long p=getPointer();
		return p==null?null:String.format("%1$08X", p);
	}
	
	public Object getValue(){
		if (datastructure.isContainer())
			return null;
		
		if(valueCacheOK)
			return valueCache;
		
		Memory buffer=new Memory(datastructure.getByteCount());
		try {
			Long address=getAddress();
			if (address!=null && address!=0){
				log.trace("Read: "+getAddressString());
				getResultList().ReadProcessMemory(Pointer.createConstant(address), buffer, (int)buffer.getSize(), null);
				valueCache=datastructure.eval(buffer);
				valueCacheOK=true;
			}else{
				valueCache=null;
			}
		} catch (NoProcessException e){
			valueCache=null;
		} catch (Exception e) {
			log.warn("Cannot Read: "+getAddressString());
			valueCache=null;
		}
		return valueCache;
	}
	
	public String getValueString(){
		Object v=getValue();
		return v==null?null:getDatastructure().valueToString(v);
	}
		
	public void delete() {
		Container dsParent=(Container)datastructure.getContainer();
		if (isSimpleResult()){	//simple Result
			getResultList().remove(this);
		}else{
			dsParent.removeField(fieldIndex);
		}
	}
	
	public void setParent(TreeNode parent) {
		this.parent=parent;		
	}

	public void invalidateParentAndChilds(){
		valueCacheOK=false;
		pointerCacheOK=false;
		getResultList().nodeChanged(this);
		if (childs!=null)
			for (Result r : childs)
				r.invalidateParentAndChilds();

	}
	
	private void invalidateFollowingSiblings(){
		if (parent instanceof Result){
			List<Result> siblings = ((Result)parent).childs;
			if (siblings!=null)		//should not happen
				for (int i = siblings.indexOf(this)+1; i < siblings.size(); i++)
					siblings.get(i).invalidateParentAndChilds();
		}
	}
	
	private void invalidateFollowingChilds(int index){
		if (childs!=null)
			for (int i = index+1; i < childs.size(); i++)
				childs.get(i).invalidateParentAndChilds();
	}
	
	
	//DSListener///////////////////////////////////////////////
	
	@Override
	public void hasChanged() {
		valueCacheOK=false;
		getResultList().nodeChanged(this);
	}

	@Override
	public void pointerChanged(boolean pointer) {
		invalidateParentAndChilds();
		invalidateFollowingSiblings();	//ByteCount has changed of this field (addresses of the following siblings too)
		getResultList().nodeChanged(this);
		
	}
	
	// triggered on the parent of the added field
	@Override
	public void addedField(Datastructure field, int fieldIndex) {
		if(childs!=null){
			Result r = new Result(resultList, this, field, fieldIndex);
			childs.add(fieldIndex, r);
			for (int i = fieldIndex+1; i < childs.size(); i++)
				childs.get(i).fieldIndex++;
			getResultList().nodeInserted(r);
		}
		invalidateFollowingSiblings();	//ByteCount has changed of this field (addresses of the following childs too)
	}
	
	// triggered on the parent of the removed field
	@Override
	public void removedField(int fieldIndex) {
		if(childs!=null){
			log.debug("field removed");
			Result child=childs.get(fieldIndex);
			if (child.fieldIndex==fieldIndex){
				childs.remove(fieldIndex);
				for (int i = fieldIndex; i < childs.size(); i++)
					childs.get(i).fieldIndex--;
				getResultList().nodeRemoved(child, fieldIndex);
			}
		}			
	}
	
	// triggered on the parent of the removed field
	@Override
	public void replacedField(Datastructure oldField, Datastructure newField, int fieldIndex) {
		if(childs!=null){			
			log.debug("replaced Field");
			Result child=childs.get(fieldIndex);
			if (child.fieldIndex==fieldIndex){
				oldField.removeListener(child);
				child.removeAllChilds();
				child.valueCacheOK=false;
				child.datastructure=newField;
				newField.addListener(child);
				if (!newField.isContainer())
					newField.setName(oldField.getName());	//take over old name
				
				getResultList().reload(child);	//if ds changed->childs may change
			}
		}
		invalidateFollowingChilds(fieldIndex);	//ByteCount has changed of this field (addresses of the following siblings too)
	}


	
	//Utils///////////////////////////////////////////////
	private static final Log log = LogFactory.getLog(Result.class);

	private void defineChildNodes(){
		if (!isLeaf() && childs==null){
			childs=new LinkedList<Result>();
			if (datastructure.isContainer()){
				List<Datastructure> fields = ((Container)datastructure).getFields();
				for (int i = 0; i < fields.size(); i++) {
					Result r = new Result(resultList, this, fields.get(i), i);
					childs.add(r);
				}
			}			
		}
	}
	
	private void removeAllChilds(){
		childs=null;
	}

	
	public boolean isSimpleResult(){
		return parent.equals(resultList);
	}
	
	
	//TreeNode///////////////////////////////////////////////
	private TreeNode parent;	//needs to be reconstructed after loading
	private List<Result> childs;
	
	@Override
	public boolean isLeaf() {
		return !datastructure.isContainer();
	}	
	
	@Override
	public int getChildCount() {
		defineChildNodes();
		return childs.size();
	}
	
	@Override
	public TreeNode getParent() {
		return parent;
	}
	
	@Override
	public boolean getAllowsChildren() {
		return !datastructure.isContainer();
	}

	@Override
	public Enumeration<Result> children() {
		return new Enumeration<Result>() {
			Iterator<Result> iter=childs.iterator();
			public boolean hasMoreElements() { return iter.hasNext(); }
			public Result nextElement() { return iter.next(); }
		};
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		defineChildNodes();
		return childs.get(childIndex);
	}

	@Override
	public int getIndex(TreeNode node) {
		defineChildNodes();
		return childs.indexOf(node);
	}

	
	//Object//////////////////////////////////////
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(getAddressString()).append(' ');
		sb.append('[').append(getDatastructure().getName()).append("] ");
		if(!datastructure.isContainer()){
			sb.append(datastructure.valueToString(getValue()));
		}else{
			if (((Container)datastructure).isPointer())
				sb.append(getPointerString());
		}		
		
		return sb.toString();
	}

	//Clonable//////////////////////////////////////
	
	@Override
	public Result clone() {
		try {
			return (Result)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
