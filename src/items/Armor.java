package items;

import lowlevel.AbstractThing;
import properties.Material;
public class Armor extends AbstractThing{
	
	
	public static enum Type {
		HEAD,
		BODY,
		ARMS,
		LEGS,
		FOOTS
	}

	public static enum ArmorType {
		ROBE,
		LIGHT,
		HEAVY
	}
	
	private Type type;

	private ArmorType armorType;

	public Type getType(){
		return type;
	}
	
	public void setType(Type type){
		this.type = type;
	}

	public Armor(String name, String face, int heavy, int cost, long x, long y, Type type, ArmorType armorType, Material material){
		super(name, face, heavy, cost, x, y, AbstractThing.MainType.ARMOR, material);
		this.type = type;
		this.armorType = armorType;
	}

	public ArmorType getArmorType()
	{
		return armorType;
	}

	public void setArmorType(ArmorType armorType)
	{
		this.armorType = armorType;
	}
}
