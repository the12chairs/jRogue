package ai;

import lifeforms.AbstractCreature;
import lowlevel.Dungeon;
import lowlevel.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * AI which will try to attack you anycase
 */
public class AgressiveAI implements AI{

	@Override
	public void lurk(AbstractCreature c) {

	}

	@Override
	public void attack(AbstractCreature c, AbstractCreature victim) {
		// TODO Auto-generated method stub
	}

	@Override
	public void march(AbstractCreature c, Tile t){
		
	}
	@Override
	public void setVisible(Dungeon d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDivide(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Tile> getPath() {
		// TODO Auto-generated method stub
		return null;
	}
}
