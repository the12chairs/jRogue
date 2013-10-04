package lowlevel;


import items.Weapon;

import org.lwjgl.input.Keyboard;

import properties.Weaponable;
import lifeforms.AbstractCreature;
import lifeforms.Hero;
import rendering.TileRenderer;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;


public class KeyboardControl extends Thread{
	
	private AbstractCreature controlled; // Кем управляем
	//private Dungeon dung; // Мля, вторая копия этого ублюдка
	
	

	private IFovAlgorithm a = new ShadowCasting();

	
	public KeyboardControl(){
		System.out.println("Initializing keyboard controller...");

		controlled = null;
	}
	
	
	public KeyboardControl(AbstractCreature object){
		System.out.println("Initializing keyboard controller...");
		controlled = object;
		System.out.println("Done!");
	}
	
	/*public void setDungeon(Dungeon dung){
		this.dung = dung;
	}
	*/
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
		a.visitFieldOfView(TileRenderer.getDungeon(), (int)controlled.getX(), (int)controlled.getY(), controlled.getVisionRadius());
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
	
			TileRenderer.check.mark(controlled.inventory().findByKey(TileRenderer.check.getPos()));
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				TileRenderer.check.next();
			}
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				TileRenderer.check.prev();
			}
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				w.useWeapon(controlled.inventory().getAllWeapon().get(TileRenderer.check.getPos()));
				controlled = (AbstractCreature) w;
			}
			
			
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_0)){
				w.useWeapon(controlled.inventory().getAllWeapon().get(0));
				controlled = (AbstractCreature) w;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_1)){
				w.useWeapon(controlled.inventory().getAllWeapon().get(1));
				controlled = (AbstractCreature) w;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_2)){
				w.useWeapon(controlled.inventory().getAllWeapon().get(2));
				controlled = (AbstractCreature) w;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_3)){
				w.useWeapon(controlled.inventory().getAllWeapon().get(3));
				controlled = (AbstractCreature) w;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				TileRenderer.gameState = TileRenderer.State.DUNGEON;
			}
		}
		
	}
	
	
	public synchronized void dropAction(){
		
		
		long x = controlled.getX();
		long y = controlled.getY();
		//int pos = 0;
		TileRenderer.check.mark(controlled.inventory().findByKey(TileRenderer.check.getPos()));
		
		if(TileRenderer.check.getThing() != null)
			System.out.println(TileRenderer.check.getPos() + ":" + TileRenderer.check.getThing().name);
		else
			System.out.println("Nothing");
		while(Keyboard.next()){
			
			
			
			// По нажатию на вверх/вниз меняем позицию маркера и кладем предмет в контейнер маркера
			

			
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				if(TileRenderer.check.getPos() < controlled.inventory().allInvenory().size()){
					TileRenderer.check.next();
					TileRenderer.check.mark(controlled.inventory().allInvenory().get(TileRenderer.check.getPos()));
					//System.out.println(pos);
				}
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				if(TileRenderer.check.biggerNull()){
					TileRenderer.check.prev();
					TileRenderer.check.mark(controlled.inventory().allInvenory().get(TileRenderer.check.getPos()));
			
				}
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				AbstractThing dropped = null;
				if(controlled.inventory().allInvenory().get(TileRenderer.check.getPos()) != null){
					dropped = controlled.inventory().allInvenory().get(TileRenderer.check.getPos());
					controlled.inventory().dropItem(TileRenderer.check.getPos());
					
					TileRenderer.getDungeon().addThing(dropped, x, y);
				
				}
				
			}

			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				TileRenderer.gameState = TileRenderer.State.DUNGEON;
			}
		}
	}

	public synchronized void inventoryAction(){
				
			
		TileRenderer.check.mark(controlled.inventory().findByKey(TileRenderer.check.getPos()));
		
		long x = controlled.getX();
		long y = controlled.getY();			
		/*	
		if(TileRenderer.check.getThing() != null)
			System.out.println(TileRenderer.check.getPos() + ":" + TileRenderer.check.getThing().name);
		else
			System.out.println("Nothing");
			*/
			
		//System.out.println(controlled.getDamage().getPair());
		
		while(Keyboard.next()){
		
			// Перемещаем ползунок
			
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				if(TileRenderer.check.getPos() < controlled.inventory().allInvenory().size()-1){
					TileRenderer.check.next();
					TileRenderer.check.mark(controlled.inventory().allInvenory().get(TileRenderer.check.getPos()));
					//System.out.println(pos);
				}
			}
				
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				if(TileRenderer.check.biggerNull()){
					TileRenderer.check.prev();
					TileRenderer.check.mark(controlled.inventory().allInvenory().get(TileRenderer.check.getPos()));
				}
			}
		
			
			// Эквип
			if(Keyboard.isKeyDown(Keyboard.KEY_E)){
				
				//if(TileRenderer.check.getThing().getMType() == AbstractThing.MainType.WEAPON)
				
				
				switch(TileRenderer.check.getThing().getMType()){
				
				case WEAPON:
					
					if(TileRenderer.check.getThing() == controlled.getHands()){
						controlled.unuseWeapon();
					}
					else{
						controlled.useWeapon((Weapon)TileRenderer.check.getThing());
					}	
					
					break;
				
				case ARMOR:
					System.out.println("Not ready yet");
					break;
				
				
				default:
					break;
				
				}
				
			}
			
			// Дроп
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				AbstractThing dropped = null;
				if(controlled.inventory().allInvenory().get(TileRenderer.check.getPos()) != null){
					dropped = controlled.inventory().allInvenory().get(TileRenderer.check.getPos());
					//controlled.inventory().dropItem(TileRenderer.check.getPos());
					// Попытка выбросить экипированное
					if(controlled.getHands() == dropped){
						controlled.unuseWeapon();
					}
					controlled.dropItem(TileRenderer.check.getPos());
					TileRenderer.getDungeon().addThing(dropped, x, y);
				
				}
				
			}
			
			
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
				Tile t = TileRenderer.getDungeon().getTile(x, y - 1);
				if(isPassable(t)){
					controlled.move(0, -1);
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				Tile t = TileRenderer.getDungeon().getTile(x, y + 1);
				if(isPassable(t)){
					controlled.move(0, 1);	
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				Tile t = TileRenderer.getDungeon().getTile(x - 1, y);
				if(isPassable(t)){
					controlled.move(-1, 0);
				}

			}
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				Tile t = TileRenderer.getDungeon().getTile(x + 1, y);
				if(isPassable(t)){
					controlled.move(1, 0);
				}
			}
			// Взять предмет
			if(Keyboard.isKeyDown(Keyboard.KEY_COMMA)){
				AbstractThing getted = null;
				for(AbstractThing t : TileRenderer.getDungeon().getThings(x, y)){
					if(t.getVisible() == true && t.isAllowed() == true){
						getted = t;
						TileRenderer.getDungeon().getItems().remove(getted);
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

			
			// Переход на другую карту
			if(Keyboard.isKeyDown(Keyboard.KEY_G)){
				Portal port = TileRenderer.getDungeon().getPortal(x, y);
				System.out.println(port.next());
				TileRenderer.getDungeon().removeHero();
				port.next().addHero((Hero) controlled);
				TileRenderer.setDungeon(port.next());
				port.next().getHero().setVisible(true);
			}
			
			recreateVisible();
		}
	}


}
