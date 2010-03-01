package luz.eveMonitor.datastructure.other;

public class TransactionSettings {
	private double maxMoney;
	private double maxVolume;
	private int accounting;
	private double security;
	
	public TransactionSettings(double maxMoney, double maxVolume, int accounting, double security) {
		this.maxMoney=maxMoney;
		this.maxVolume=maxVolume;
		this.accounting=accounting;
		this.security=security;
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

	public double getSecurity() {
		return security;
	}

	public void setSecurity(double security) {
		this.security = security;
	}

}
