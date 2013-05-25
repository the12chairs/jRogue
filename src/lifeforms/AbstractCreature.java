package lifeforms;

import primitives.GraphObject;
import primitives.Inventory;
import properties.Race;
import properties.Stat;
import lowlevel.AbstractThing;



public abstract class AbstractCreature extends GraphObject{

	//static enum Race { HUMAN, ELF, DWARF };
	public static enum Profession { WARRIOR, ROGUE, MAGE }; 
	
	protected String name;
	//protected String face;
	protected Race race;
	protected Profession profession;
	protected Stat hp;
	protected Stat str;
	protected Stat dex;
	protected Stat intel;
	protected long purse;
	protected Stat mass;
	protected Stat damage;
	protected Inventory inventory;
	// Интерфейс
	public void move(int dx, int dy){
		this.x += dx;
		this.y += dy;
	}

	public long getPurse(){
		return this.purse;
	}
	
	public void setPurse(long gold){
		this.purse += gold;
	}
	public void setPos(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	// Выставим расовые бонусы
	public void initRaceBonuses(){
		
		this.dex.setFull(this.dex.getFull() + this.race.getDexBonus());
		this.str.setFull(this.str.getFull() + this.race.getStrBonus());
		this.intel.setFull(this.intel.getFull() + this.race.getIntelBonus());
		this.hp.setFull(this.hp.getFull() + this.race.getHpBonus());
	
		this.dex.setCurrent(this.dex.getCurrent() + this.race.getDexBonus());
		this.str.setCurrent(this.str.getCurrent() + this.race.getStrBonus());
		this.intel.setCurrent(this.intel.getCurrent() + this.race.getIntelBonus());
		this.hp.setCurrent(this.hp.getCurrent() + this.race.getHpBonus());
	}

	public String getProfession(){
		switch (this.profession){
		case WARRIOR:
			return "Воин";
		case ROGUE:
			return "Разбойник";
		case MAGE:
			return "Маг";
		default:
			return "Неизвестная профессия";
		}
	}
	
	// Обертки для взятия/выкидывания предметов, учитывающие изменение переносимого веса
	public void takeItem(AbstractThing item){
		this.inventory.pushItem(item);
		this.mass.setCurrent(this.mass.getCurrent() + item.getHeavy());
	}
	
	public void dropItem(AbstractThing item){
		this.inventory.dropItem(item);
		this.mass.setCurrent(this.mass.getCurrent() - item.getHeavy());
	}
}