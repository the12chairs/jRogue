package ai;

import lifeforms.AbstractCreature;
import lowlevel.Dungeon;
import lowlevel.Tile;

import java.util.ArrayList;

/**
 * AI interface
 */
public interface AI {
	public void lurk(AbstractCreature c); // What to do without commands
	public void attack(AbstractCreature c, AbstractCreature victim); // What to do at attack
	public void march(AbstractCreature c, Tile t); // Go to tile
	public void setVisible(Dungeon d); // What a creature see
	public void setDivide(int x, int y); // Map divides
	public ArrayList<Tile> getPath(); // Path in a creature's memory
}
