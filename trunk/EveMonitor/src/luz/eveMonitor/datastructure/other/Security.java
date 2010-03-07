package luz.eveMonitor.datastructure.other;

public enum Security {
	zerosec(0.0, 0.0),
	lowsec (0.1, 0.4),
	highsec(0.5, 1.0);

	double min;
	double max;
	Security(double min, double max){
		this.min=min;
		this.max=max;
	}
	
	public double getMin(){
		return min;
	}

	public double getMax(){
		return max;
	}
}

