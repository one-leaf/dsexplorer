package luz.eveMonitor.datastructure.python.exception;

public class PythonObjectException extends Exception {
	private static final long serialVersionUID = 458021893060793152L;
	
	public PythonObjectException(String msg){
		super(msg);
	}
	
	public PythonObjectException(Exception e){
		super(e);
	}
	
}
