package lifeforms;


import java.util.Map.Entry;

import ai.AI;
import lowlevel.Tile;
import items.Armor;
import items.Weapon;
import dnd.Dice;
import primitives.GraphObject;
import primitives.Inventory;
import properties.Race;
import properties.Stat;
import lowlevel.AbstractThing;



public abstract class AbstractCreature extends GraphObject{

	//static enum Race { HUMAN, ELF, DWARF };
	public static enum Profession { WARRIOR, ROGUE, MAGE }; // Читать из директории profession
	
	protected String name;
	protected Race race;
	protected Profession profession;
	protected Stat hp;
	protected Stat str;
	protected Stat dex;
	protected Stat intel;
	protected Stat wisdom;
	protected Stat charisma;
	protected Stat stamina;
	protected long purse; // Деньги-денежки
	protected Stat mass;
	protected Dice damage;
	protected Inventory inventory;
	protected int visionRadius;
	protected Stat age; // Смари, current значение - текущий возраст, full значение - возраст смерти.
	protected boolean weaponed;
	
	
	protected Weapon hands; // Что в руках

	protected Armor shield;
	protected Armor head;
	protected Armor body;
	protected Armor arms;
	protected Armor legs;
	protected Armor foots;
	
	protected AI ai;
	
	// Статы защиты
	
	protected Stat headDP; // Защита
	protected Stat bodyDP;
	protected Stat legsDP;
	protected Stat armsDP;
	protected Stat footsDP;
	

	
	public void head(Armor head){
		this.head = head;
	}
	
	public Armor head(){
		return head;
	}
	
	public void body(Armor body){
		this.body = body;
	}
	
	public Armor body(){
		return body;
	}
	public void legs(Armor legs){
		this.legs = legs;
	}
	
	public Armor legs(){
		return legs;
	}
	
	public void foots(Armor foots){
		this.foots = foots;
	}
	
	public Armor foots(){
		return foots;
	}
	
	public void arms(Armor arms){
		this.arms = arms;
	}
	
	public Armor arms(){
		return arms;
	}
	public void headDP(Stat dp){
		headDP = dp;
	}
	
	public Stat headDP(){
		return headDP;
	}
	
	public void bodyDP(Stat dp){
		bodyDP = dp;
	}
	
	public Stat bodyDP(){
		return bodyDP;
	}
	public void armsDP(Stat dp){
		armsDP = dp;
	}
	
	public Stat armsDP(){
		return armsDP;
	}
	public void legsDP(Stat dp){
		legsDP = dp;
	}
	
	public Stat legsDP(){
		return legsDP;
	}
	public void footsDP(Stat dp){
		footsDP = dp;
	}
	
	public Stat footsDP(){
		return footsDP;
	}
	
	public void hit(AbstractCreature c){
		c.hp.setCurrent(c.hp.getCurrent() - damage.throwDice());
	}
	
	public void move(int dx, int dy){
		this.x += dx;
		this.y += dy;
	}

	
	public AbstractCreature(){
		headDP = new Stat(4,4);
		bodyDP = new Stat(4,4);
		armsDP = new Stat(4,4);
		legsDP = new Stat(4,4);
		footsDP = new Stat(4,4);
		
	}

	public void lurk(){
		ai.lurk(this);
	}
	
	
	public void attack(AbstractCreature victum){
		ai.attack(this, victum);
	}
	
	public void march(Tile t){
		ai.march(this, t);
	}
	
	public boolean isAlive() {
		if(hp.getCurrent() > 0) return true;
		else return false;
	}
	
	public Stat wis(){
		return wisdom;
	}
	
	
	public Stat cha(){
		return charisma;
	}
	
	public Stat hp(){
		return hp;
	}
	
	public Stat str(){
		return str;
	}
	
	public Stat dex(){
		return dex;
	}
	public Stat intel(){
		return intel;
	}

	
	public Stat stam(){
		return stamina;
	}
	
	public Stat mass(){
		return mass;
	}
	
	public Stat age(){
		return age;
	}
	
	
	
	public int modifStr(){
		return (int)(str.getCurrent() - 10) / 2;
	}

	public int modifDex(){
		return (int)(dex.getCurrent() - 10) / 2;
	}
	
	public int modifWis(){
		return (int)(wisdom.getCurrent() - 10) / 2;
	}
	
	public int modifCha(){
		return (int)(charisma.getCurrent() - 10) / 2;
	}
	
	public int modifSta(){
		return (int)(stamina.getCurrent() - 10) / 2;
	}

	public int modifAge(){
		return (int)(age.getCurrent() - 10) / 2;
	}

	public int modifHp(){
		return (int)(hp.getCurrent() - 10) / 2;
	}
	
	public Inventory inventory(){
		return inventory;
	}
	
	public void getVisionRadius(int visionRadius){
		this.visionRadius = visionRadius;
	}
	
	public int getVisionRadius(){
		return visionRadius;
	}
	
	public long getGold(){
		return this.purse;
	}
	
	public void addGold(long gold){
		this.purse += gold;
	}
	
	public Race getRace(){
		return race;
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
	
	
	
	public Dice getDamage(){
		return damage;
	}
	
	public Dice calculateDamage(){
		return new Dice(1,3);
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
	
	
	public void dropItem(Integer item){
		AbstractThing t = inventory.allInvenory().get(item);
		this.inventory.dropItem(item);
		this.mass.setCurrent(this.mass.getCurrent() - t.getHeavy());
	}
	
	
	public void dropItem(AbstractThing item){
		//AbstractThing t = inventory.findByKey(key);
		this.inventory.dropItem(item);
		this.mass.setCurrent(this.mass.getCurrent() - item.getHeavy());
	}
	
	
	
	public void unwearArmor(Armor.Type type){
		switch(type){
		case HEAD:
			head = null;
			break;
		case BODY:
			body = null;
			break;
		case ARMS:
			arms = null;
			break;
		case FOOTS:
			foots = null;
			break;
		case LEGS:
			legs = null;
			break;
		default:
			System.out.println("Error while armor unwearing");
		}
		
	}
	
	//TODO: доделать!
	public void wearArmor(Armor armor){
		
		for(Entry<Integer, Armor> p : inventory.getAllArmor().entrySet()){
			// For each armor into inventory, if we take marked
			if(p.getValue() == armor){
				
				// if this head armor 
				if(p.getValue().getType() == Armor.Type.HEAD){
					head = p.getValue();
				}
				// for our body
				if(p.getValue().getType() == Armor.Type.BODY){
					body = p.getValue();
				}
				// for arms
				if(p.getValue().getType() == Armor.Type.ARMS){
					arms = p.getValue();
				}
				// legs
				if(p.getValue().getType() == Armor.Type.LEGS){
					legs = p.getValue();
				}
				// and foots
				if(p.getValue().getType() == Armor.Type.FOOTS){
					foots = p.getValue();
				}	
				
			}
				
		}

	}
	
	public Weapon getHands(){
		return hands;
	}
	
	public void useWeapon(Weapon w) {
		 
		//Чорная магия, не трожь! 
		for(Entry<Integer, Weapon> p : inventory.getAllWeapon().entrySet()){
			if(p.getValue() == w){
				
				// Если руки заняты, меняем оружие
				if(hands != null){
					//System.out.println("Ololo");
					//inventory.pushItem(hands);
					//takeItem(hands);
					hands = w;
					this.damage = w.getDamage();
				}
				else{
					hands = p.getValue();
					damage = hands.getDamage();
					//dropItem(p.getKey());
				}
				break;
			}
				
		}
		
	}
	
	
	public void setAI(AI ai){
		this.ai = ai;
	}
	public AI getAi(){
		return ai;
	}

	public void unuseWeapon() {
		this.damage = new Dice(1, 3);
		hands = null;
	}

	public boolean isWeaponed() {
		return weaponed;
	}

	
	
	
}
