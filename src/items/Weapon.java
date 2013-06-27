package items;

import lowlevel.AbstractThing;
import properties.Stat;

public class Weapon extends AbstractThing{

	
	public enum Type {
		ONE_HAND_SWORD,
		TWO_HAND_SWORD,
		MACE,
		STAFF,
		
		
	}
	
	
	private Type type;
	private Stat damage;
	private String symType;
	
	
	public Weapon(String name, String face, Type type, String symType, Stat damage, int heavy, int cost, long x, long y){
		super(name, face, heavy, cost, x, y);
		this.type = type;
		this.symType = symType;
		this.damage = damage;

	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
	}

}
