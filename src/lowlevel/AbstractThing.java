package lowlevel;

import primitives.GraphObject;


public abstract class AbstractThing extends GraphObject{

	public static enum MainType{
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
	
	protected int bonus;
	protected boolean allowed; // Запрет/разрешение подбирать предмет
	protected boolean equipped;
	protected boolean equippable; // Можно натянуть на себя?

	public AbstractThing(String name, String face, int heavy, int cost, long x, long y, MainType mType){
		super(face, x, y);
		this.name = name;
		this.heavy = heavy;
		this.cost = cost;
		this.mType = mType;
		allowed = true;
		equipped = false;
	}

	public AbstractThing(String name, String face, int heavy, int cost, MainType mType){
		super(face);
		this.name = name;
		this.heavy = heavy;
		this.cost = cost;
		this.mType = mType;
		allowed = true;
		equipped = false;
	}


	public AbstractThing() {
		
	}
	public boolean isAllowed(){
		return allowed;
	}

	public void setAllow(boolean a) {
		allowed = a;
	}
	public void setName(String name){
		this.name = name;
	}

	public void setBonus(int bonus){
		this.bonus = bonus;
	}
	
	public int getBonus(){
		return bonus;
	}

	public void setPos(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getCost(){
		return this.cost;
	}
	
	public void setCost(int cost){
		this.cost = cost;
	}
	
	public void setHeavy(int heavy){
		this.heavy = heavy;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public String getName(){
		return name;
	}

	public int getHeavy(){
		return heavy;
	}
	
	public boolean getVisible(){
		return visible;
	}

	public MainType getMType() {
		return mType;
	}

	public boolean isEquipped()
	{
		return equipped;
	}

	public void equip()
	{
		equipped = true;
	}

	public void unequip()
	{
		equipped = false;
	}

	public boolean getEquippable()
	{
		return equippable;
	}

	public void setEquippable(boolean equippable)
	{
		this.equippable = equippable;
	}

	public void setMType(MainType mType) {
		this.mType = mType;
	}
}
