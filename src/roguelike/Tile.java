package roguelike;

public class Tile {
	
	private String name;
	private char tile;
	private boolean visible;
	private boolean passable;
	private int x;
	private int y;
	
	public static void main(String[] args) {
		Tile t = new Tile("Стена", '#', true, false, 1, 1);
		System.out.println(t.getTile());
	}
	
	public Tile(){
		
	}
	
	public Tile(String name, char tile, boolean visible, boolean passable, int x, int y){
		
		this.name = name;
		this.tile = tile;
		this.visible = visible;
		this.passable = passable;
		this.x = x;
		this.y = y;
	}
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setTile(char tile){
		this.tile = tile;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public void setPassable(boolean passable){
		this.passable = passable;
	}
	
	public String getName(){
		return this.name;
	}
	
	public char getTile(){
		return this.tile;
	}
	
	public boolean getVisible(){
		return this.visible;
	}
	
	public boolean getPassable(){
		return this.passable;
	}

	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}

}
