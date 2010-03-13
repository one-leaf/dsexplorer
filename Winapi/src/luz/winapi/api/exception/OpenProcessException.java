package luz.winapi.api.exception;

public class OpenProcessException extends Exception {
	private static final long serialVersionUID = 458021893060793152L;
	
	public OpenProcessException(String msg){
		super(msg);
	}
	
	public OpenProcessException(Exception e){
		super(e);
	}
	
}
