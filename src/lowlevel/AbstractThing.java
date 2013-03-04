package lowlevel;


public abstract class AbstractThing implements ThingController {
	
	protected String name;
	protected char face;
	protected int x;
	protected int y;
	protected int heavy;
	protected boolean visible;
	protected int cost;
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setFace(char face){
		this.face = face;
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
	
	public char getFace(){
		return face;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	public int getHeavy(){
		return heavy;
	}
	
	public boolean getVisible(){
		return visible;
	}
}
