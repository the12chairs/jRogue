package lowlevel;

/**
 * Class for tiles with some action/trigger
 * Doors, levers, traps...
 */
public class ActiveTile extends Tile {

	protected String offTile;
	protected String onTile;
	protected boolean isActive;
	protected boolean offPassable;
	protected boolean onPassable;
	
	public ActiveTile(String name, String offTile, String onTile, boolean offPassable, boolean onPassable, int x, int y){
		super();
		this.name = name;
		this.offTile = offTile;
		this.onTile = onTile;
		this.offPassable = offPassable;
		this.onPassable = onPassable;
		this.x = x;
		this.y = y;
		this.isActive = false;
		this.face = offTile; // We need render it as other tiles
		this.passable = offPassable;
	}

	public void activate(){
		this.isActive = true;
		this.face = this.onTile;
		this.passable = this.onPassable;
	}
	
	public String getOffTile() {
		return offTile;
	}
	
	public void setOffTile(String offTile) {
		this.offTile = offTile;
	}
	
	public String getOnTile() {
		return onTile;
	}
	
	public void setOnTile(String onTile) {
		this.onTile = onTile;
	}
}
