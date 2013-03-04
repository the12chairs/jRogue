package properties;

public class Stat {

	private long cValue; // Текущее значение
	private long fValue; // Полное значение
	
	public Stat(){
		this.cValue = 0;
		this.fValue = 0;
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
		// Для удобства вывода
		return cValue + "/" + fValue;
	}
	
}
