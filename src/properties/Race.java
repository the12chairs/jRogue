package properties;

public class Race {

	private String name;
	// Расовые бонусы к статам
	private int strBonus;
	private int dexBonus;
	private int intelBonus;
	private int hpBonus;
	private int wisBonus;
	private int charBonus;
	
	
	//TODO Не железный бонус, а описание бросков кубиков
	
	public Race(String name, int str, int dex, int intel, int wis, int cha, int hp){
		this.name = name;
		this.strBonus = str;
		this.dexBonus = dex;
		this.intelBonus = intel;
		this.hpBonus = hp;
		this.setCharBonus(cha);
		this.setWisBonus(wis);
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

	public int getWisBonus() {
		return wisBonus;
	}

	public void setWisBonus(int wisBonus) {
		this.wisBonus = wisBonus;
	}

	public int getCharBonus() {
		return charBonus;
	}

	public void setCharBonus(int charBonus) {
		this.charBonus = charBonus;
	}
}
