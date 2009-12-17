package luz.dsexplorer.winapi.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import luz.dsexplorer.exceptions.NoProcessException;
import luz.dsexplorer.objects.datastructure.DSField;
import luz.dsexplorer.objects.datastructure.DSList;
import luz.dsexplorer.objects.datastructure.Datastructure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import org.simpleframework.xml.strategy.Strategy;

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


@Root(name="Results")
public class ResultListImpl implements MutableTreeNode, ResultList{
	private static final long serialVersionUID = 2132455546694390451L;
	private static final Log log = LogFactory.getLog(ResultListImpl.class);
	@ElementList(inline=true)
	private List<Result> results = new LinkedList<Result>();
	private Process process;
	
	public ResultListImpl(){}	
	
	public ResultListImpl(Process process){
		this.process=process;
	}

	public void ReadProcessMemory(Pointer pointer, Pointer outputBuffer, int nSize, IntByReference outNumberOfBytesRead) throws NoProcessException, Exception{
		if (process!=null)
			process.ReadProcessMemory(pointer, outputBuffer, nSize, outNumberOfBytesRead);
		else{
			throw new NoProcessException();
		}
	}	
	
	public void setProcess(Process process) {
		this.process=process;
	}
	
	public Process getProcess() {
		return this.process;
	}
	
	
	public void removeAllChildren() {
		results.clear();		
	}
	
	
	public static ResultListImpl openFromFile(File file) throws Exception{
		log.info("open "+file.getAbsolutePath());
		
		Strategy strategy = new CycleStrategy("id", "ref");
		Serializer serializer = new Persister(strategy);
		ResultListImpl list = serializer.read(ResultListImpl.class, file);
		
		
		//Repair links
		Result r;
		Datastructure ds;
		List<Datastructure> dsset=new LinkedList<Datastructure>();
		for (int i = 0; i < list.getChildCount(); i++){
			r=(Result)list.getChildAt(i);
			r.setResultList(list);	//link list with result
			ds=r.getDatastructure();
			if (ds!=null){
				ds.addListDataListener(r);	//link result with ds
				
				if(!dsset.contains(ds)){	//avoid duplicate links	
					dsset.add(ds);			
					for (DSField f : ds.getFields()) {
						f.addListener(ds);	//link ds with field				
					}	
				}
			}
		}

		return list;
	}
	
	
	public void saveToFile(File file) throws FileNotFoundException{
		log.info("save "+file.getAbsolutePath());
		try {
			Strategy strategy = new CycleStrategy("id", "ref");
			Serializer serializer = new Persister(strategy);
			serializer.write(this, file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public DSList getDatastructures(){
		DSList list=new DSList();
		Datastructure ds;
		Result r;
		for (int i = 0; i < this.getChildCount(); i++) {
			r=(Result)this.getChildAt(i);
			ds=r.getDatastructure();
			if (ds!=null)
				list.addElement(ds);
		}
		
		return list;		
	}
	

	
	
	


	
	//MutableTreeNode//////////////////////////////////////
	
	@Override
	public int getChildCount() {
		return results.size();
	}
	
	@Override
	public boolean isLeaf() {
		return false;
	}	
	
	
	@Override
	public void add(MutableTreeNode newChild) {
		Result r = (Result)newChild;
		r.setResultList(this);	//link the result to this object
		results.add(r);
	}
		
	@Override
	public void insert(MutableTreeNode child, int index) {
		Result r = (Result)child;
		r.setResultList(this);	//link the result to this object
		results.add(index, r);
		
	}

	@Override
	public void remove(int index) {
		results.remove(index);
		
	}

	@Override
	public void remove(MutableTreeNode node) {
		results.remove(node);
		
	}

	@Override
	public Enumeration<Result> children() {
		Enumeration<Result> e = new Enumeration<Result>() {
			Iterator<Result> i = results.iterator();
			
			@Override
			public boolean hasMoreElements() {
				return i.hasNext();
			}

			@Override
			public Result nextElement() {
				return (Result)i.next();
			}
		};
		return e;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return results.get(childIndex);
	}

	@Override
	public int getIndex(TreeNode node) {
		return results.indexOf(node);
	}
	
	@Override
	public void removeFromParent() {
		//Has no parent		
	}

	@Override
	public void setParent(MutableTreeNode newParent) {
		//Has no parent		
	}

	@Override
	public void setUserObject(Object object) {
		// Has no object		
	}

	@Override
	public TreeNode getParent() {
		// Has no parent
		return null;
	}


	//Object//////////////////////////////////////
	
	
	@Override
	public String toString() {
		if (process==null)
			return "<no process>";
		return process.getSzExeFile();
	}



}
