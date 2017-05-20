package items;

import lowlevel.AbstractThing;
import properties.Material;

/**
 * Class for armor, clothes, etc. NOT FOR JEWELERY!
 */
//TODO: armor does nothing! Implement protection
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

	// For which body part?
	private Type type;

	// Which material?
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
