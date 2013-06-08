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
	
	
	
	public Weapon(String name, String face, Type type, Stat damage, int heavy, int cost, long x, long y){
		super(name, face, heavy, cost, x, y);
		this.type = type;
		this.damage = damage;

	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
	}

}
