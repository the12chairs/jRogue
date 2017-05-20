package lifeforms;


import java.util.ArrayList;
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

// Abstract creature class. Extend it for every lifeform you need
public abstract class AbstractCreature extends GraphObject{

	public static enum Profession { WARRIOR, ROGUE, MAGE }; // Read from  `profession` directory TODO: remove and create Profession class
	
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
	protected long purse; // Money
	protected Stat mass;
	protected Dice damage;
	protected Inventory inventory;
	protected int visionRadius;
	protected Stat age; //current is current age, full is maximum lifetime
	protected boolean weaponed;
	
	protected Weapon rightHand;
    protected Weapon leftHand; // TODO: it can be a chield, remove and set as AbstractTHing

	protected Armor shield;
	protected Armor head;
	protected Armor body;
	protected Armor arms;
	protected Armor legs;
	protected Armor foots;

	//TODO: jewelery

	protected AI ai;
	
	// Protection stats (resists etc)
	protected Stat headDP;
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

	// TODO: probably don't need this methods
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

	// Creature stats
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

	//TODO: what are thise modificators? Why do we need it?
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

	public void setPos(int x, int y)
	{
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
	
	public Dice calculateDamage()
	{
		return new Dice(1,3);
	}
	

	public String getProfession()
	{
		switch (this.profession) {
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
	
	// Metods for take and drop items, we need it for calc weight
	// TODO: implement overload
	public void takeItem(AbstractThing item)
	{
		this.inventory.pushItem(item);
		this.mass.setCurrent(this.mass.getCurrent() + item.getHeavy());
	}

	// By inventory number (Do we really need this method?)
	public void dropItem(Integer item)
	{
		AbstractThing t = inventory.allInvenory().get(item);
		this.inventory.dropItem(item);
		this.mass.setCurrent(this.mass.getCurrent() - t.getHeavy());
	}

	public void dropItem(AbstractThing item)
	{
		this.inventory.dropItem(item);
		this.mass.setCurrent(this.mass.getCurrent() - item.getHeavy());
	}

	// TODO: handle as exeption
	public void unwearArmor(Armor armor)
	{
		Armor.Type type = armor.getType();

		switch(type){
		case HEAD:
			head = null;
			armor.unequip();
			break;
		case BODY:
			body = null;
			armor.unequip();
			break;
		case ARMS:
			arms = null;
			armor.unequip();
			break;
		case FOOTS:
			foots = null;
			armor.unequip();
			break;
		case LEGS:
			legs = null;
			armor.unequip();
			break;
		default:
			System.out.println("Error while armor unwearing");
		}
	}
	
	//TODO: complete method
	public void wearArmor(Armor armor)
	{
		
		for(Entry<Integer, Armor> p : inventory.getAllArmor().entrySet()){
			// For each armor into inventory, if we take marked
			if(p.getValue() == armor) {

				// if this is head armor
				if (p.getValue().getType() == Armor.Type.HEAD) {
					head = p.getValue();
					armor.equip();
				}
				// for our body
				if (p.getValue().getType() == Armor.Type.BODY) {
					body = p.getValue();
					armor.equip();
				}
				// for arms
				if (p.getValue().getType() == Armor.Type.ARMS) {
					arms = p.getValue();
					armor.equip();
				}
				// legs
				if (p.getValue().getType() == Armor.Type.LEGS) {
					legs = p.getValue();
					armor.equip();
				}
				// and foots
				if (p.getValue().getType() == Armor.Type.FOOTS) {
					foots = p.getValue();
					armor.equip();
				}
			}
		}
	}

    public void setAI(AI ai){
        this.ai = ai;
    }

    public AI getAi(){
        return ai;
    }

	public Weapon getRightHand()
    {
		return rightHand;
	}

    public Weapon getLeftHand()
    {
        return leftHand;
    }

	public void useRightWeapon(Weapon w)
	{
		// Some black magic here, do not modify
		for(Entry<Integer, Weapon> p : inventory.getAllWeapon().entrySet()){
			if(p.getValue() == w) {
				// Switch weapon
				if (rightHand != null) {
					rightHand.unequip();
					rightHand = w;
					this.damage = rightHand.getDamage();
					w.equip();
					weaponed = true;
					System.out.println("Switch weapons");
				} else {
					rightHand = p.getValue();
					damage = rightHand.getDamage();
					w.equip();
					weaponed = true;
					System.out.println("Use weapon");
				}
				break;
			}
		}
	}

	public void unuseRightWeapon(Weapon w) {
		this.damage = new Dice(1, 3);
        rightHand = null;
		weaponed = false;
		w.unequip();
		System.out.println("Unuse weapon");
	}

	//TODO: refactor, left one method, provide left or right hand as a function parameter
    public void useLeftWeapon(Weapon w)
    {
        // Some black magic here, do not modify
        for(Entry<Integer, Weapon> p : inventory.getAllWeapon().entrySet()){
            if(p.getValue() == w){
                // Switch weapon
                if(leftHand != null){
                    leftHand.unequip();
                    leftHand = w;
                    this.damage = leftHand.getDamage();
                    w.equip();
                    weaponed = true;
                    System.out.println("Switch weapons");
                }
                else{
                    leftHand = p.getValue();
                    damage = leftHand.getDamage();
                    w.equip();
                    weaponed = true;
                    System.out.println("Use weapon");
                }
                break;
            }
        }
    }

	//TODO: refactor, left one method, provide left or right hand as a function parameter
    public void unuseLeftWeapon(Weapon w) {
        this.damage = new Dice(1, 3);
        leftHand = null;
        weaponed = false;
        w.unequip();
        System.out.println("Unuse weapon");
    }

	public boolean isWeaponed() {
		return weaponed;
	}
}
