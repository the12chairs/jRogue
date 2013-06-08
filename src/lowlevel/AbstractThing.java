package lowlevel;

import primitives.GraphObject;


public abstract class AbstractThing extends GraphObject{
	
	protected String name;
	protected int heavy;
	//protected boolean visible;
	protected int cost;
	
	
	
	
	public AbstractThing(String name, String face, int heavy, int cost, long x, long y){
		super(face, x, y);
		this.name = name;
		this.heavy = heavy;
		this.cost = cost;
	}


	public void setName(String name){
		this.name = name;
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
}
