package luz.winapi.api.exception;

public class ReadProcessMemoryException extends Exception {
	private static final long serialVersionUID = 458021893060793152L;
	
	public ReadProcessMemoryException(String msg){
		super(msg);
	}
	
	public ReadProcessMemoryException(Exception e){
		super(e);
	}
	
}
