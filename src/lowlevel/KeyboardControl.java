package lowlevel;


import org.lwjgl.input.Keyboard;

import lifeforms.AbstractCreature;

import rendering.TileRenderer;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;


public class KeyboardControl extends Thread{
	
	private AbstractCreature controlled; // Кем управляем
	private Dungeon dung; // Мля, вторая копия этого ублюдка
	
	/*
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;	
	*/
	
	private IFovAlgorithm a = new ShadowCasting();
	
	private boolean invStatus = false; // открыт/закрыт инвентарь
	
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
		recreateVisible();
	}
	
	
	public boolean isPassable(Tile t){
		return t.getPassable();
	}
	
	@Override
	public void run() {
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Keyboard thread has started.");
		while(true){
			commandAction();
			Thread.yield();
		}
		
	}
	
	
	public void recreateVisible(){
		a.visitFieldOfView(dung, (int)controlled.getX(), (int)controlled.getY(), controlled.getVisionRadius());
	}
	
	
	
	
	public synchronized void dropAction(){
		if(TileRenderer.gameState == TileRenderer.State.DUNGEON){
			commandAction();
		}
		while(Keyboard.next()){
			

			
			if(Keyboard.isKeyDown(Keyboard.KEY_0)){
				
				AbstractThing dropped = controlled.inventory().allInvenory().get(0);
				controlled.inventory().dropItem(0);
				long x = controlled.getX();
				long y = controlled.getY();
				
				dropped.setX(x);
				dropped.setY(y);
				dung.addThing(dropped);
				TileRenderer.gameState = TileRenderer.State.DUNGEON;
			}
			
		}
	}
	
	public synchronized void commandAction(){

		long x = controlled.getX();
		long y = controlled.getY();
		
		//System.out.println(openInv);
		//Tile t = dung.getTile(x, y);
		
		if(TileRenderer.gameState == TileRenderer.State.DROP_ITEM){
			System.out.println("ololo");
			dropAction();
		}
		
		while(Keyboard.next()){
			
			

			
			
			
			int openInv = 0;	
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				Tile t = dung.getTile(x, y - 1);
				//recreateVisible();
				if(isPassable(t)){
					controlled.move(0, -1);
					//up = true;
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				Tile t = dung.getTile(x, y + 1);
				//recreateVisible();
				if(isPassable(t)){
					controlled.move(0, 1);	
					//down = true;
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				Tile t = dung.getTile(x - 1, y);
				//recreateVisible();
				if(isPassable(t)){
					controlled.move(-1, 0);
					//left = true;
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				Tile t = dung.getTile(x + 1, y);
				//recreateVisible();
				if(isPassable(t)){
					controlled.move(1, 0);
					//right = true;
				}
			}
			// Взять предмет
			if(Keyboard.isKeyDown(Keyboard.KEY_COMMA)){
				AbstractThing getted = null;
				for(AbstractThing t : dung.getThings(x, y)){
					if(t.getVisible() == true){
						getted = t;
						dung.getItems().remove(getted);
						break;
					}
				}
				if(getted != null){
					controlled.takeItem(getted);
				}
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_I)){
				// Робит
				if(getInvStatus()) {
					TileRenderer.gameState = TileRenderer.State.DUNGEON;
					setInvStatus(false);
				}
				else {
					TileRenderer.gameState = TileRenderer.State.INVENTORY;
					setInvStatus(true);
				}
			}
			// Дроп
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				// Робит
				if(getInvStatus()) {
					TileRenderer.gameState = TileRenderer.State.DUNGEON;
					setInvStatus(false);
				}
				else {
					TileRenderer.gameState = TileRenderer.State.DROP_ITEM;
					setInvStatus(true);
				}
			}
			recreateVisible();
		}
	}


	public boolean getInvStatus() {
		return invStatus;
	}


	public void setInvStatus(boolean invStatus) {
		this.invStatus = invStatus;
	}
}
