package luz.dsexplorer.winapi.constants;

public enum ProcessInformationClass{
	ProcessBasicInformation (0),
	ProcessDebugPort        (7),
	ProcessWow64Information (26),
	ProcessImageFileName    (27);
	
	private int value;
	ProcessInformationClass(int value)     { this.value=value; }
	public int getValue() { return value;     }
}