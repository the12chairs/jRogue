package items;

import dnd.Dice;
import lowlevel.AbstractThing;
import properties.Material;

/**
 * Class for weapon implementation
 */
public class Weapon extends AbstractThing{

	public enum Type {
		SWORD,
		MACE,
		STAFF,
		AXE
	}

	private Type type;
	private Dice damage;
	private boolean onehanded;

	public Weapon(String name, String face, Type type, Material material, boolean onehanded,
			Dice damage, int heavy, int cost, long x, long y) {

		super(name, face, heavy, cost, x, y, AbstractThing.MainType.WEAPON, material);
		this.type = type;
		this.damage = damage;
		this.onehanded = onehanded;
	}

	public Weapon(String name, String face, Type type, Material material, boolean onehanded,
				  Dice damage, int heavy, int cost) {

		super(name, face, heavy, cost, AbstractThing.MainType.WEAPON, material);
		this.type = type;
		this.damage = damage;
		this.onehanded = onehanded;
	}

	public Type getType(){
		return this.type;
	}

	public Dice getDamage(){
		return this.damage;
	}
	
	public boolean isOnehanded()
	{
		return onehanded;
	}

	public boolean isTwohanded()
	{
		return !onehanded;
	}
}
