package properties;

public class Race {

	private String name;
	// Расовые бонусы к статам
	private int strBonus;
	private int dexBonus;
	private int intelBonus;
	private int hpBonus;
	
	
	public Race(String name, int str, int dex, int intel, int hp){
		this.name = name;
		this.strBonus = str;
		this.dexBonus = dex;
		this.intelBonus = intel;
		this.hpBonus = hp;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrBonus() {
		return strBonus;
	}

	public void setStrBonus(int strBonus) {
		this.strBonus = strBonus;
	}

	public int getDexBonus() {
		return dexBonus;
	}

	public void setDexBonus(int dexBonus) {
		this.dexBonus = dexBonus;
	}

	public int getIntelBonus() {
		return intelBonus;
	}

	public void setIntelBonus(int intelBonus) {
		this.intelBonus = intelBonus;
	}

	public int getHpBonus() {
		return hpBonus;
	}

	public void setHpBonus(int hpBonus) {
		this.hpBonus = hpBonus;
	}
}
