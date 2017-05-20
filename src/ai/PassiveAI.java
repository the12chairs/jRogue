package ai;

import lifeforms.AbstractCreature;
import lowlevel.Dungeon;
import lowlevel.Tile;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * AI class for passive creatures, wich wouldn't attack first
 * TODO: rework it
 */
public class PassiveAI implements AI{

	private Random rnd;
	private Dungeon viewed;
	private int maxX;
	private int maxY;

	private Tile oldDest;

	ArrayList<Tile> path;

	public PassiveAI(){
		rnd = new Random();
		path = new ArrayList<Tile>();
		oldDest = null;
	}

	public void setVisible(Dungeon d){
		viewed = d;
	}

	@Override
	public void setDivide(int x, int y){
		maxX = x;
		maxY = y;
	}
	
	@Override
	public void lurk(AbstractCreature c) {
		// TODO Auto-generated method stub
		switch(rnd.nextInt(4)+1){
			
		case 1:
			if(c.getX()+1 < maxX){
				if(viewed.getTile(c.getX()+1, c.getY()).getPassable())
					c.move(1, 0);
			}
			break;
		case 2:
			if( c.getX()-1 > 0){
				if(viewed.getTile(c.getX()-1, c.getY()).getPassable())
					c.move(-1, 0);
			}
			break;
		case 3:
			if(c.getY()+1 < maxY){
				if(viewed.getTile(c.getX(), c.getY()+1).getPassable())
					c.move(0, 1);
			}
			break;
		case 4:
			if(c.getY()-1 > 0){
				if(viewed.getTile(c.getX(), c.getY()-1).getPassable())
				c.move(0,  -1);
			}
			break;
		}
	}

	public Tile nextPath(AbstractCreature c){
        return path.get(0);
	}
	
	@Override
	public void attack(AbstractCreature c, AbstractCreature victum) {
		// TODO Auto-generated method stub
		Tile start = viewed.getTile(c.getX(), c.getY());
		Tile finish = viewed.getTile(victum.getX(), victum.getY());

		path = AStar.search(viewed, start, finish);

		// If a victum is near you - hit it
		if(victum.getX() == nextPath(c).getX() && victum.getY() == nextPath(c).getY()){
			c.hit(victum);
		}
		// If not - try to stay near
		else{
			c.setX(nextPath(c).getX());
			c.setY(nextPath(c).getY());
		}
	}
	
	@Override
	public void march(AbstractCreature c, Tile t) {
		if(t != null) {
			if (t.getPassable()) {
				Tile start = viewed.getTile(c.getX(), c.getY());
				path = AStar.search(viewed, start, t);
				c.setX(nextPath(c).getX());
				c.setY(nextPath(c).getY());
			}
		}
	}

	public ArrayList<Tile> getPath()
	{
		return path;
	}
}
