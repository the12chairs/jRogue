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




public class Hero extends AbstractCreature{

	
	LinkedList<Quest> questJournal; // Активные, выполненные, проваленные. Смотрим по флажку в Quest
	private Stat exp; // Опыт персонажа
	private int level; // Его уровень
	private long statPoints;
	private Weapon hands;
	
	
	// Баланс тут
	private static final int expCoef = 6;
	private static final int statCoef = 10;
	private static final int firstExpCoef = 5;
	private static final int massCoef = 8;
	// Пока уровень не является отдельной сущностью. Пусть будет тут
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
			this.exp.setFull(this.exp.getFull() * expCoef); // Будь внимателен!
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
				// Получим экспу
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
		//this.initRaceBonuses();
		this.questJournal = new LinkedList<Quest>();
		this.level = 0;
		this.exp = new Stat(0, firstExpCoef);
		this.statPoints = 0;
		this.damage = new Dice(1, 3);//new Stat(str, str + damageCoef);
		this.visionRadius = visionRadius;
		// HP = stamina / 2
		this.hp = new Stat(modifSta()+6);
	}
	

	public static void main(String[] args) {
		// Тесты
		//Race dwarf = new Race("Дварф", 5, 0, -3, -2, -1,  4);
		//Hero you = new Hero("Макс", "ololo", 2, 1, 5, 5, 5, 5, dwarf, 3, Profession.WARRIOR);
		//System.out.println("Опыт: " + you.exp.getPair());

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
