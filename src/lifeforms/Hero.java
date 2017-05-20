package lifeforms;

import items.Weapon;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import lowlevel.AbstractThing;

import dnd.Dice;


import primitives.Inventory;
import primitives.Quest;
import properties.Race;
import properties.Stat;
import properties.Weaponable;

// Our playable hero.
public class Hero extends AbstractCreature{

	LinkedList<Quest> questJournal; // Quests
	private Stat exp; // experience
	private int level;
	private long statPoints;
	private Weapon hands;

	// Balance. WTF is this?
	private static final int expCoef = 6;
	private static final int statCoef = 10;
	private static final int firstExpCoef = 5;
	private static final int massCoef = 8;

	// TODO: implement Level class with perks, etc
	public boolean isLevelUp(){
		if(this.exp.getCurrent() >= this.exp.getFull()){
			return true;
		}
		else{
			return false;
		}
	}

	public void doLevelUp(){
		while(this.isLevelUp()){
			this.level++;
			this.statPoints += statCoef;
			this.exp.setFull(this.exp.getFull() * expCoef); // Be careful!
		}
	}

	public void takeQuest(Quest quest){
		this.questJournal.push(quest);
	}
	
	public void finishQuest(Quest quest){
		for(Quest q : questJournal){
			if(q == quest){
				q.success();
				this.purse += q.getGold();
				// Gain some exp
				this.exp.setCurrent(this.exp.getCurrent() + q.getExp());
			}
		}
	}

	public void failQuest(Quest quest){
		quest.fail();
	}
	
	public Hero(String name, String face, int x, int y, Race race, int visionRadius, Profession profession){
		this.race = race;
		this.name = name;
		this.face = face;
		this.setPos(x, y);
		this.stamina = new Stat(race.getStamBonus().throwDice());
		this.str = new Stat(race.getStrBonus().throwDice());
		this.dex = new Stat(race.getDexBonus().throwDice());
		this.intel = new Stat(race.getIntelBonus().throwDice());
		this.wisdom = new Stat(race.getWisBonus().throwDice());
		this.charisma = new Stat(race.getCharBonus().throwDice());
		this.profession = profession;
		this.inventory = new Inventory();
		this.mass = new Stat(0, str.getCurrent() * massCoef);
		this.questJournal = new LinkedList<Quest>();
		this.level = 0;
		this.exp = new Stat(0, firstExpCoef);
		this.statPoints = 0;
		this.damage = new Dice(1, 3);
		this.visionRadius = visionRadius;
		this.hp = new Stat(modifSta()+6);
		rightHand = null;
		leftHand = null;
	}
	
    public Stat getExp() {
		return exp;
	}

	public void setExp(Stat exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getStatPoints() {
		return statPoints;
	}

	public void setStatPoints(long statPoints) {
		this.statPoints = statPoints;
	}
}
