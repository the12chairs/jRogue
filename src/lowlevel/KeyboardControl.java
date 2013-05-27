package lowlevel;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import lifeforms.AbstractCreature;
import lifeforms.Hero;

import rlforj.los.IConeFovAlgorithm;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;
import sz.csi.CharKey;



public class KeyboardControl extends Thread{
	
	private AbstractCreature controlled; // Кем управляем
	private Dungeon dung; // Мля, вторая копия этого ублюдка
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;	
	
	
	private IFovAlgorithm a = new ShadowCasting();
	
	public KeyboardControl(){
		System.out.println("Initializing keyboard controller...");

		controlled = null;
	}
	
	
	public KeyboardControl(Dungeon dung, AbstractCreature object){
		System.out.println("Initializing keyboard controller...");
		this.dung = dung;
		controlled = object;
		System.out.println("Done!");
	}
	
	public void setDungeon(Dungeon dung){
		this.dung = dung;
	}
	
	public void controlCreature(AbstractCreature creature){
		controlled = creature;
	}
	
	
	public boolean isPassable(Tile t){
		return t.getPassable();
	}
	
	@Override
	public void run() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		commandAction();
		Thread.yield();
		
	}
	
	
	public void recreateVisible(){
		a.visitFieldOfView(dung, (int)controlled.getX(), (int)controlled.getY(), controlled.getVisionRadius());
	}
	
	public void commandAction(){
		long x = controlled.getX();
		long y = controlled.getY();
		
		
		
		//Tile t = dung.getTile(x, y);
		
		while(Keyboard.next()){
			
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				Tile t = dung.getTile(x, y - 1);
				//recreateVisible();
				if(isPassable(t)){
					controlled.move(0, -1);
					up = true;
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				Tile t = dung.getTile(x, y + 1);
				//recreateVisible();
				if(isPassable(t)){
					controlled.move(0, 1);	
					down = true;
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				Tile t = dung.getTile(x - 1, y);
				//recreateVisible();
				if(isPassable(t)){
					controlled.move(-1, 0);
					left = true;
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				Tile t = dung.getTile(x + 1, y);
				//recreateVisible();
				if(isPassable(t)){
					controlled.move(1, 0);
					right = true;
				}
			}
			recreateVisible();
		}
	}

}
