package lowlevel;

public class Tile {
	
	protected String name;
	protected char tile;
	protected boolean visible;
	protected boolean passable;
	protected long x;
	protected long y;
	
	public static void main(String[] args) {
		Tile t = new Tile("Стена", '#', true, false, 1, 1);
		System.out.println(t.getTile());
	}
	
	public Tile(){
		
	}
	
	public Tile(String name, char tile, boolean visible, boolean passable, long x, long y){
		
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

	public long getX() {
		return x;
	}


	public void setX(long x) {
		this.x = x;
	}


	public long getY() {
		return y;
	}


	public void setY(long y) {
		this.y = y;
	}

}
