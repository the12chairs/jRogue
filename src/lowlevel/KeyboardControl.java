package lowlevel;


import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import items.Armor;
import items.Weapon;

import org.lwjgl.input.Keyboard;

import properties.Weaponable;
import lifeforms.AbstractCreature;
import lifeforms.Hero;
import lifeforms.Mob;
import rendering.TileRenderer;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;


public class KeyboardControl extends Thread{
	
	private long turns;
	private long oldTurns;
	private AbstractCreature controlled; // Кем управляем
	//private Dungeon dung; // Мля, вторая копия этого ублюдка
	
	

	private IFovAlgorithm a = new ShadowCasting();

	
	public KeyboardControl(){
		Keyboard.enableRepeatEvents(true);
		turns = oldTurns = 0;
		System.out.println("Initializing keyboard controller...");

		controlled = null;
	}
	
	
	public KeyboardControl(AbstractCreature object){
		Keyboard.enableRepeatEvents(true);
		turns = oldTurns = 0;
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
		if(t != null){
			return t.getPassable();
		}
		else{
			return false;
		}
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

		int height = TileRenderer.HEIGHT;
		int width = TileRenderer.WIDTH;

		long controlledX = controlled.getX();
		long controlledY = controlled.getY();

		if(TileRenderer.gameState == TileRenderer.State.INVENTORY){
			TileRenderer.camera.warp(height/2, width/2); // TODO: косяк, возвращать камеру не выходит
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
		
		if(TileRenderer.gameState == TileRenderer.State.DEATH){
			deathAction();
		}
	}

	public synchronized void deathAction(){
		
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

				turns++;
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
					Armor a = (Armor)TileRenderer.check.getThing();
					System.out.println(a.getType());
					if(a.getType() == Armor.Type.HEAD){
						if(controlled.head() == a){
							controlled.unwearArmor(Armor.Type.HEAD);
							System.out.println("Head armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Head armor wearing!");
						}
					} else
					if(a.getType() == Armor.Type.BODY){
						if(controlled.body() == a){
							controlled.unwearArmor(Armor.Type.BODY);
							System.out.println("Body armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Body armor wearing!");
						}
					} else
					if(a.getType() == Armor.Type.FOOTS){
						if(controlled.foots() == a){
							controlled.unwearArmor(Armor.Type.FOOTS);
							System.out.println("Foots armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Foots armor wearing!");
						}
					} else
					if(a.getType() == Armor.Type.ARMS){
						if(controlled.arms() == a){
							controlled.unwearArmor(Armor.Type.ARMS);
							System.out.println("Arms armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Arms armor wearing!");
						}
					} else
					if(a.getType() == Armor.Type.LEGS){
						if(controlled.legs() == a){
							controlled.unwearArmor(Armor.Type.LEGS);
							System.out.println("Legs armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Legs armor wearing!");
						}
					} 
					else {
						System.out.println("Error while armor wearing");
					}
				default:
					break;
				
				}
				
			}
			
			// Дроп
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				turns++;
				drop(controlled, TileRenderer.check.getPos());

			}


			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				TileRenderer.gameState = TileRenderer.State.DUNGEON;
			}
		
		}
	}

	
	public void drop(AbstractCreature who, int what){
		AbstractThing dropped;
		if(who.inventory().allInvenory().get(TileRenderer.check.getPos()) != null){
			dropped = who.inventory().allInvenory().get(what);
			// Попытка выбросить экипированное
			if(who.getHands() == dropped){
				who.unuseWeapon();
			}
			if(who.head() == dropped){
				who.unwearArmor(Armor.Type.HEAD);
			}
			if(who.body() == dropped){
				who.unwearArmor(Armor.Type.BODY);
			}
			if(who.arms() == dropped){
				who.unwearArmor(Armor.Type.ARMS);
			}
			if(who.legs() == dropped){
				who.unwearArmor(Armor.Type.LEGS);
			}
			if(who.foots() == dropped){
				who.unwearArmor(Armor.Type.FOOTS);
			}
			who.dropItem(what);
			TileRenderer.getDungeon().addThing(dropped, who.getX(), who.getY());
		
		}
	}

	// При смерти существа
	
	public void death(AbstractCreature who) {

		// Сохраняем оставшиеся предметы в инвентаре
		List<Integer> buffer = new ArrayList<>();
		for (Entry<Integer, AbstractThing> t : who.inventory().allInvenory().entrySet()) {
			buffer.add(t.getKey());
		}

		// Зачищаем инвентарь
		for (Integer i : buffer) {
			drop(who, i);
		}

	}

	public synchronized void commandAction(){

		// Старушка умерла
		if(!controlled.isAlive()){
			TileRenderer.gameState = TileRenderer.State.DEATH;
		}
		
		long x = controlled.getX();
		long y = controlled.getY();

		//Keyboard.
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
					controlledMove(0, -1);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
					controlledMove(0, 1);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
					controlledMove(-1, 0);
				}
	
				if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					controlledMove(1, 0);
				}
			}
			
			// Взять предмет
			if(Keyboard.isKeyDown(Keyboard.KEY_COMMA)){
				turns++;
				AbstractThing getted = null;
				for(AbstractThing t : TileRenderer.getDungeon().getThings(x, y)){
					if(t.getVisible() && t.isAllowed()){
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
				if(port != null){
					TileRenderer.getDungeon().removeHero();
					port.next().addHero((Hero) controlled);
					TileRenderer.setDungeon(port.next());
					port.next().getHero().setVisible(true);
			
				}
			}
			surroundTurn();
			recreateVisible();
		}
	}
	
	public void controlledMove(int dx, int dy) {
		turns++;
		Tile t = TileRenderer.getDungeon().getTile(controlled.getX()+dx, controlled.getY()+dy);
		Mob c = (Mob)TileRenderer.getDungeon().getCreature(controlled.getX()+dx, controlled.getY()+dy);
		if(c != null && c.isAlive()){
			controlled.hit(c);

		} 
		else {
			if(isPassable(t) && t != null){
				controlled.move(dx, dy);
				TileRenderer.camera.move(-dy, -dx);
			}
		}

		// лечение тут
		int hilKoef = 5;
		if(turns%hilKoef == 0 && controlled.hp().getCurrent() < controlled.hp().getFull()){
			controlled.hp().setCurrent(controlled.hp().getCurrent() + 1);
		}
	}
	
	// Ход всех существ карты
	public void surroundTurn(){
		if(turns>oldTurns){
			List<AbstractCreature> creatures = TileRenderer.getDungeon().getCreatures();
			for(AbstractCreature c : creatures){
				if(c != controlled && c.isAlive()){
					c.getAi().setVisible(TileRenderer.getDungeon());
					c.getAi().setDivide((int)TileRenderer.getDungeon().getWidth(), (int)TileRenderer.getDungeon().getHeight());
					c.lurk();
				}
				if(!c.isAlive()){
					death(c);
				}
				oldTurns = turns;
			}
		}
	}
}
