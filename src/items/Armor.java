package items;

import lowlevel.AbstractThing;

public class Armor extends AbstractThing{
	
	
	public static enum Type {
		HEAD,
		BODY,
		ARMS,
		LEGS,
		FOOTS
	}
	
	private Type type;
	
	public Type getType(){
		return type;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	public Armor(String name, String face, int heavy, int cost, long x, long y, Type type){
		super(name, face, heavy, cost, x, y, AbstractThing.MainType.ARMOR);
		this.type = type;
	}

}
