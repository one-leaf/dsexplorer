package luz.dsexplorer.winapi;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import luz.dsexplorer.winapi.objects.Process;
import luz.dsexplorer.winapi.objects.Result;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * This class groups Result objects which represent memory pointers with their interpretation.
 * This class gives a meaning to these results because it links the Results with a process.
 * That way, the results can receive their value. And it is easy to switch the process for every result,
 * by simply changing the process of this class.
 * Additonally, this class has the feature to load an save Results from files.
 * @author cappella
 *
 */
public class ResultList extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 2132455546694390451L;
	private Process process;
	
	public ResultList(Process process){
		this.process=process;
	}
	
	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws Exception{
		process.ReadProcessMemory(pointer, outputBuffer, nSize, outNumberOfBytesRead);
	}
	
	
	public void setProcess(Process process) {
		this.process=process;
	}
	
	public void saveToFile(File file) throws FileNotFoundException{
		System.out.println("save "+file.getAbsolutePath());
		
		try {
			BeanInfo info = Introspector.getBeanInfo(Result.class);
			PropertyDescriptor[] propertyDescriptors =
                info.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; ++i) {
				PropertyDescriptor pd = propertyDescriptors[i];
				if (pd.getName().equals("resultList") ||
					pd.getName().equals("pointerCache") ||
					pd.getName().equals("pointerCacheOK") ||
					pd.getName().equals("valueCache") ||
					pd.getName().equals("valueCacheOK") ||
					pd.getName().equals("value") ||
					pd.getName().equals("children") ||
					pd.getName().equals("parent") 
				) {
					pd.setValue("transient", Boolean.TRUE);
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}


		
		XMLEncoder e = new XMLEncoder(
			new BufferedOutputStream(
		        new FileOutputStream(file)
		    )
		);

//		e.setPersistenceDelegate(Result.class,
//			new DefaultPersistenceDelegate(
//				new String[]{"address", "type", "name", "isPointer"}
//			) 
//		);
		
//		e.setPersistenceDelegate(Datastructure.class,
//			new DefaultPersistenceDelegate(
//				new String[]{"name"}
//			) 
//		);
		
//		e.setPersistenceDelegate(DSField.class,
//			new DefaultPersistenceDelegate(
//				new String[]{ "type", "name", "byteCount", "datastructure"}
//			) 
//		);
			
		
		Object child;
		for (int i = 0; i < getChildCount(); i++) {
			child=getChildAt(i);
			System.out.println(child.toString());
			e.writeObject(((Result)child).clone());
			
		}
		e.close();
	}
	
	
	public void openFromFile(File file) throws FileNotFoundException{
		System.out.println("open "+file.getAbsolutePath());
		//TODO xml open
	}
	
	
	@Override
	public void add(MutableTreeNode newChild) {
		Result r = (Result)newChild;
		r.setResultList(this);	//link the result to this object
		super.add(newChild);
	}
		
	
	
	
	////////////////////////////////////////
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public int getChildCount() {
		return super.getChildCount();
	}

	
	@Override
	public String toString() {
		if (process==null)
			return null;
		return process.getSzExeFile();
	}




}
