package lifeforms;

import java.util.LinkedList;

import ai.AI;
import ai.AgressiveAI;
import dnd.Dice;
import lifeforms.AbstractCreature.Profession;
import primitives.Inventory;
import primitives.Quest;
import properties.Race;
import properties.Stat;

// Killable NPC
public class Mob extends AbstractCreature {

	private boolean agressive;

	public Mob(String name, String face, int x, int y, Race race, int visionRadius, boolean agro){
		super();
		this.name = name;
		this.face = face;
		this.setPos(x, y);
		this.stamina = new Stat(race.getStamBonus().throwDice());
		this.str = new Stat(race.getStrBonus().throwDice());
		this.dex = new Stat(race.getDexBonus().throwDice());
		this.intel = new Stat(race.getIntelBonus().throwDice());
		this.wisdom = new Stat(race.getWisBonus().throwDice());
		this.charisma = new Stat(race.getCharBonus().throwDice());
		this.inventory = new Inventory();
		this.mass = new Stat(0, str.getCurrent() * 5);

		this.damage = new Dice(1, 3);
		this.visionRadius = visionRadius;
		this.hp = new Stat(modifSta()+6);
		visible = true;
		agressive = agro;
		ai = new AgressiveAI();
	}

	public boolean isAgressive(){
		return agressive;
	}
}
