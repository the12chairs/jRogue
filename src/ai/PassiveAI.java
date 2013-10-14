package ai;

import lifeforms.AbstractCreature;
import lowlevel.Dungeon;

import java.util.Random;
public class PassiveAI implements AI{

	private Random rnd;
	private Dungeon viewed;
	private int maxX;
	private int maxY;
	public PassiveAI(){
		rnd = new Random();
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

	@Override
	public void attack(AbstractCreature c, AbstractCreature victim) {
		// TODO Auto-generated method stub
		
	}

}
