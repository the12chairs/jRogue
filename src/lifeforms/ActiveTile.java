package lifeforms;

import lowlevel.Tile;

public class ActiveTile extends Tile {
	
	// Двери, рычаги, etc...
	
	protected char offTile;
	protected char onTile;
	protected boolean isActive;
	protected boolean offPassable;
	protected boolean onPassable;
	
	public ActiveTile(String name, char offTile, char onTile, boolean offPassable, boolean onPassable, int x, int y){
		this.name = name;
		this.offTile = offTile;
		this.onTile = onTile;
		this.offPassable = offPassable;
		this.onPassable = onPassable;
		this.x = x;
		this.y = y;
		this.isActive = false;
		this.tile = offTile; // Выводить мы будем тайл, для единообразия
		this.passable = offPassable;
	}
	
	
	public void activate(){
		this.isActive = true;
		this.tile = this.onTile;
		this.passable = this.onPassable;
	}
	
	public char getOffTile() {
		return offTile;
	}
	
	public void setOffTile(char offTile) {
		this.offTile = offTile;
	}
	
	public char getOnTile() {
		return onTile;
	}
	
	public void setOnTile(char onTile) {
		this.onTile = onTile;
	}
	
}
