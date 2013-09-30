package items;

import lowlevel.AbstractThing;

public class Armor extends AbstractThing{
	
	public Armor(String name, String face, int heavy, int cost, long x, long y){
		super(name, face, heavy, cost, x, y, AbstractThing.MainType.ARMOR);
	}

}
