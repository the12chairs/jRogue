package lifeforms;

import java.util.LinkedList;

import primitives.Inventory;
import primitives.Quest;
import properties.Race;
import properties.Stat;




public class Hero extends AbstractCreature implements CreatureController{

	
	LinkedList<Quest> questJournal; // Активные, выполненные, проваленные. Смотрим по флажку в Quest
	private Stat exp; // Опыт персонажа
	private int level; // Его уровень
	private long statPoints;
	
	// Баланс тут
	private static final int expCoef = 6;
	private static final int statCoef = 10;
	private static final int firstExpCoef = 5;
	private static final int massCoef = 8;
	private static final int damageCoef = 3;
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
	
	public void makeQuest(Quest quest){
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
	
	public Hero(String name, char face, int x, int y, int hp, int str, int dex,
			    int intel, Race race, Profession profession){
		
		this.name = name;
		this.face = face;
		this.setPos(x, y);
		this.hp = new Stat(hp, hp);
		this.str = new Stat(str, str);
		this.dex = new Stat(dex, dex);
		this.intel = new Stat(intel, intel);
		this.race = race;
		this.profession = profession;
		this.inventory = new Inventory();
		this.mass = new Stat(0, str * massCoef);
		this.initRaceBonuses();
		this.questJournal = new LinkedList<Quest>();
		this.level = 0;
		this.exp = new Stat(0, firstExpCoef);
		this.statPoints = 0;
		this.damage = new Stat(str, str + damageCoef);
	}
	

	public static void main(String[] args) {
		// Тесты
		Race dwarf = new Race("Дварф", 5, 0, -3, 4);
		Hero you = new Hero("Макс", '@', 2, 1, 5, 5, 5, 5, dwarf, Profession.WARRIOR);
		System.out.println("Опыт: " + you.exp.getPair());
		you.initRaceBonuses();

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
