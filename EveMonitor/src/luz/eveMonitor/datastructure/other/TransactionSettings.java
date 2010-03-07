package luz.eveMonitor.datastructure.other;

public class TransactionSettings {
	private double maxMoney;
	private double maxVolume;
	private int accounting;
	private Security security;
	private int number;
	
	public TransactionSettings(double maxMoney, double maxVolume, int accounting, Security highsec, int number) {
		this.maxMoney=maxMoney;
		this.maxVolume=maxVolume;
		this.accounting=accounting;
		this.security=highsec;
		this.number=number;
	}
	

	public double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(double maxMoney) {
		this.maxMoney = maxMoney;
	}

	public double getMaxVolume() {
		return maxVolume;
	}

	public void setMaxVolume(double maxVolume) {
		this.maxVolume = maxVolume;
	}

	public int getAccounting() {
		return accounting;
	}

	public void setAccounting(int accounting) {
		this.accounting = accounting;
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}


	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
	
	

}
