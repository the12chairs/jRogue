package properties;

/**
 * For all stats that can be temporary changed
 */
public class Stat {

	private long cValue; // Current value
	private long fValue; // Full value
	
	public Stat(){
		this.cValue = 0;
		this.fValue = 0;
	}

	public Stat(long both) {
		this.cValue = this.fValue = both;
	}
	
	public Stat (long current, long full){
		this.cValue = current;
		this.fValue = full;
	}
	
	public long getCurrent(){
		return cValue;
	}
	
	public long getFull(){
		return fValue;
	}
	
	public void setCurrent(long value){
		this.cValue = value;
	}
	
	public void setFull(long value){
		this.fValue = value;
	}
	
	public String getPair(){
		return cValue + "/" + fValue;
	}
}
