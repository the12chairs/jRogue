package properties;

import dnd.Dice;

// Races and they stats
public class Race {

	private String name;
	// Race stat bonuses
	private Dice strBonus;
	private Dice dexBonus;
	private Dice intelBonus;
	private Dice wisBonus;
	private Dice charBonus;
	private Dice stamBonus;

	public Race(String name, Dice str, Dice dex, Dice intel, Dice wis, Dice cha, Dice stam){
		this.name = name;
		this.strBonus = str;
		this.dexBonus = dex;
		this.intelBonus = intel;
		this.stamBonus = stam;
		this.setCharBonus(cha);
		this.setWisBonus(wis);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dice getStrBonus() {
		return strBonus;
	}

	public void setStrBonus(Dice strBonus) {
		this.strBonus = strBonus;
	}

	public Dice getDexBonus() {
		return dexBonus;
	}

	public void setDexBonus(Dice dexBonus) {
		this.dexBonus = dexBonus;
	}

	public Dice getIntelBonus() {
		return intelBonus;
	}

	public void setIntelBonus(Dice intelBonus) {
		this.intelBonus = intelBonus;
	}

	public Dice getStamBonus() {
		return stamBonus;
	}

	public void setStamBonus(Dice stamBonus) {
		this.stamBonus = stamBonus;
	}

	public Dice getWisBonus() {
		return wisBonus;
	}

	public void setWisBonus(Dice wisBonus) {
		this.wisBonus = wisBonus;
	}

	public Dice getCharBonus() {
		return charBonus;
	}

	public void setCharBonus(Dice charBonus) {
		this.charBonus = charBonus;
	}
}
