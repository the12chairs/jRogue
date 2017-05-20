package lowlevel;

import primitives.GraphObject;
import properties.Material;

// Class for items implementation
public abstract class AbstractThing extends GraphObject {

	public static enum MainType {
		WEAPON,
		ARMOR,
		THING,
		POTION,
		SCROLL,
		PORTAL
	}

	private MainType mType;

	protected String name;
	protected int heavy;
	protected int cost;
	protected Material madeOf;
	protected int bonus;
	protected boolean allowed; // Can it be taken?
	protected boolean equipped;
	protected boolean equippable; // Can it be equipped?

	// If we know item positon
	public AbstractThing(String name, String face, int heavy, int cost, long x, long y, MainType mType, Material material) {
		super(face, x, y);
		this.name = name;
		this.heavy = heavy;
		this.cost = cost;
		this.mType = mType;
		allowed = true;
		equipped = false;
		madeOf = material;
	}
	// If we don't know item positon (generated into creature's pocket as example)
	//TODO: possibly don't need this method
	public AbstractThing(String name, String face, int heavy, int cost, MainType mType, Material material) {
		super(face);
		this.name = name;
		this.heavy = heavy;
		this.cost = cost;
		this.mType = mType;
		allowed = true;
		equipped = false;
		madeOf = material;
	}

	public AbstractThing() {
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllow(boolean a) {
		allowed = a;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getBonus() {
		return bonus;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getCost() {
		return this.cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setHeavy(int heavy) {
		this.heavy = heavy;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public int getHeavy() {
		return heavy;
	}

	public boolean getVisible() {
		return visible;
	}

	public MainType getMType() {
		return mType;
	}

	public boolean isEquipped() {
		return equipped;
	}

	public void equip() {
		equipped = true;
	}

	public void unequip() {
		equipped = false;
	}

	public boolean getEquippable() {
		return equippable;
	}

	public void setEquippable(boolean equippable) {
		this.equippable = equippable;
	}

	public void setMType(MainType mType) {
		this.mType = mType;
	}

	public Material getMaterial() {
		return madeOf;
	}

	public void setMAterial(Material material)
	{
		madeOf = material;
	}
}
