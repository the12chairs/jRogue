package ai;

import lifeforms.AbstractCreature;
import lowlevel.Dungeon;
import lowlevel.Tile;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class PassiveAI implements AI{

	private Random rnd;
	private Dungeon viewed;
	private int maxX;
	private int maxY;
	ArrayList<Tile> path; // Путь к жертве
	
	public PassiveAI(){
		rnd = new Random();
		path = new ArrayList<Tile>();
		//history = new ArrayList<Tile>();
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
			if(viewed.getTile(c.getX()+1, c.getY()).getPassable() == true && c.getX()+1 < maxX){
				c.move(1, 0);
			}
			break;
		case 2:
			if(viewed.getTile(c.getX()-1, c.getY()).getPassable() == true && c.getX()-1 > 0){
				c.move(-1, 0);
			}
			break;
		case 3:
			if(viewed.getTile(c.getX(), c.getY()+1).getPassable() == true && c.getY()+1 < maxY){
				c.move(0, 1);
			}
			break;
		case 4:
			if(viewed.getTile(c.getX(), c.getY()-1).getPassable() == true && c.getY()-1 > 0){
				c.move(0,  -1);
			}
			break;
		}
	}

	
	
	public Tile nextPath(AbstractCreature c){
		ArrayList<Tile> history = new ArrayList<Tile>();
		Tile next = path.get(0);
		if(next.getX() == c.getX() && next.getY() == c.getY()){
			next = path.get(1);
		}

		return next;
	}
	
	@Override
	public void attack(AbstractCreature c, AbstractCreature victum) {
		// TODO Auto-generated method stub
		Tile start = viewed.getTile(c.getX(), c.getY());
		Tile finish = viewed.getTile(victum.getX(), victum.getY());
		//System.out.println(path.size());
		path = AStar.search(viewed, start, finish);

		for(Tile t : path){
			System.out.println(t.getX()+":"+t.getY());
		}
		System.out.println("------------");
		// Догнал - бьем
		if(victum.getX() == nextPath(c).getX()  && victum.getY() == nextPath(c).getY()){
			c.hit(victum);
		}
		// Иначе догоняем
		else{
			c.setX(nextPath(c).getX());
			c.setY(nextPath(c).getY());
	
		}
		path.clear();
	}

}
