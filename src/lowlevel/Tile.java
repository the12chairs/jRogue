package lowlevel;

import primitives.GraphObject;



public class Tile extends GraphObject {
	
	protected String name;
	//protected boolean visible;
	protected boolean passable;
	protected String texturePath;
	protected boolean visited;

	public static void main(String[] args) {
		//Tile t = new Tile("Стена", '#', true, false, 1, 1);
		//System.out.println(t.getTile());
	}
	
	
	public Tile() {
		
	}
	
	public Tile(Tile t, int x, int y){
		super.face = t.getFace();
		name = t.getName();
		visible = t.getVisible();
		passable = t.getPassable();
		this.x = x;
		this.y = y;
		
	}
	
	
	public Tile(String name, String face, boolean visible, boolean passable, long x, long y){
		super(face, x, y);
		this.name = name;
		//this.face = face;
		this.visible = visible;
		this.passable = passable;
		this.x = x;
		this.y = y;
		visited = false;
	}
	
	
	
	// Конструктор прототипа.
	public Tile(String name, String face, boolean visible, boolean passable){
		super(face);
		this.name = name;
		this.face = face;
		this.visible = visible;
		this.passable = passable;
		visited = false;
	}
	

	
	public boolean getVisited(){
		return visited;
	}
	

	void setName(String name){
		this.name = name;
	}
	
	
	public void setPassable(boolean passable){
		this.passable = passable;
	}
	
	public String getName(){
		return this.name;
	}
	
	
	public boolean getPassable(){
		return this.passable;
	}
	
	public void setTexturePath(String texturePath){
		this.texturePath = texturePath;
	}
	
	public String getTexturePath(){
		return texturePath;
	}
}
