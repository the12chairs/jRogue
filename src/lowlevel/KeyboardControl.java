package lowlevel;


import items.Weapon;

import org.lwjgl.input.Keyboard;

import properties.Weaponable;

import lifeforms.AbstractCreature;

import rendering.TileRenderer;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;


public class KeyboardControl extends Thread{
	
	private AbstractCreature controlled; // Кем управляем
	private Dungeon dung; // Мля, вторая копия этого ублюдка
	

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
			stateManager();
			Thread.yield();
		}
		
	}
	
	
	public void recreateVisible(){
		a.visitFieldOfView(dung, (int)controlled.getX(), (int)controlled.getY(), controlled.getVisionRadius());
	}
	

	
	
	// Главный метод; Вызывается в потоке и вызывает управление в зависимости он состояния рендерера
	
	public synchronized void stateManager(){
	
		if(TileRenderer.gameState == TileRenderer.State.INVENTORY){
			inventoryAction();
		}
		
		if(TileRenderer.gameState == TileRenderer.State.DROP_ITEM){
			dropAction();
		}
		
		if(TileRenderer.gameState == TileRenderer.State.DUNGEON){
			commandAction();
		}
		
		if(TileRenderer.gameState == TileRenderer.State.TAKE_WEAPON){
			takeWeaponAction();
		}
		
	}
	
	
	
	public synchronized void takeWeaponAction(){
		
		Weaponable w = (Weaponable) controlled;
		while(Keyboard.next()){
			
			if(Keyboard.isKeyDown(Keyboard.KEY_0)){
				Weapon wep = controlled.inventory().getAllWeapon().get(0);
				w.useWeapon(wep);
				controlled = (AbstractCreature) w;
			}
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				TileRenderer.gameState = TileRenderer.State.DUNGEON;
			}
		}
		
	}
	
	
	public synchronized void dropAction(){
		
		
		while(Keyboard.next()){
			
			if(Keyboard.isKeyDown(Keyboard.KEY_0)){
				
				AbstractThing dropped = controlled.inventory().allInvenory().get(0);
				controlled.inventory().dropItem(0);
				long x = controlled.getX();
				long y = controlled.getY();
			
				dung.addThing(dropped, x, y);
			}
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				TileRenderer.gameState = TileRenderer.State.DUNGEON;
			}
		}
	}

	public synchronized void inventoryAction(){
				
		while(Keyboard.next()){
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				TileRenderer.gameState = TileRenderer.State.DUNGEON;
			}
		}
	}
	
	
	
	public synchronized void commandAction(){

		
		long x = controlled.getX();
		long y = controlled.getY();
		
		
		while(Keyboard.next()){
			
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				Tile t = dung.getTile(x, y - 1);
				if(isPassable(t)){
					controlled.move(0, -1);
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				Tile t = dung.getTile(x, y + 1);
				if(isPassable(t)){
					controlled.move(0, 1);	
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				Tile t = dung.getTile(x - 1, y);
				if(isPassable(t)){
					controlled.move(-1, 0);
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				Tile t = dung.getTile(x + 1, y);
				if(isPassable(t)){
					controlled.move(1, 0);
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
			
			// Вызов инвентаря
			if(Keyboard.isKeyDown(Keyboard.KEY_I)){
				TileRenderer.gameState = TileRenderer.State.INVENTORY;
			}
			
			// Дроп
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				TileRenderer.gameState = TileRenderer.State.DROP_ITEM;
			}
			
			// Список оружия
			if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				TileRenderer.gameState = TileRenderer.State.TAKE_WEAPON;
			}
			System.out.println(controlled.getDamage().getDice());
			recreateVisible();
		}
	}


}
