package items;

import dnd.Dice;
import lowlevel.AbstractThing;

public class Weapon extends AbstractThing{

	
	public enum Type {
		ONE_HAND_SWORD,
		TWO_HAND_SWORD,
		MACE,
		STAFF,
		
		
	}
	
	
	private Type type;
	//private Stat damage;
	private Dice damage;
	private String symType;
	
	
	public Weapon(String name, String face, Type type, String symType,
			Dice damage, int heavy, int cost, long x, long y){
		
		
		super(name, face, heavy, cost, x, y, AbstractThing.MainType.WEAPON);
		this.type = type;
		this.symType = symType;
		this.damage = damage;
		
		
	}
	
	
	public Type getType(){
		return this.type;
	}

	public Dice getDamage(){
		return this.damage;
	}
	
	
	
	public String getSymType(){
		return this.symType;
	}
}
