package lowlevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import items.Armor;
import items.Weapon;
import org.lwjgl.input.Keyboard;
import lifeforms.AbstractCreature;
import lifeforms.Hero;
import lifeforms.Mob;
import rendering.TileRenderer;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;

// Class for set keyboard controls.
// TODO: remove logic from here
public class KeyboardControl extends Thread {
	
	private long turns;
	private long oldTurns;
	private AbstractCreature controlled; // Кем управляем

	private IFovAlgorithm a = new ShadowCasting();

	public KeyboardControl() {

		Keyboard.enableRepeatEvents(true);
		turns = oldTurns = 0;
		System.out.println("Initializing keyboard controller...");

		controlled = null;
	}

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

	// main method. Control the game in depends on render.STATE
	
	public synchronized void stateManager(){

		int height = TileRenderer.HEIGHT;
		int width = TileRenderer.WIDTH;

		if(TileRenderer.gameState == TileRenderer.State.INVENTORY){
			inventoryAction();
		}
		if(TileRenderer.gameState == TileRenderer.State.DUNGEON){
			commandAction();
		}

		if(TileRenderer.gameState == TileRenderer.State.DEATH){
			deathAction();
		}
	}

	public synchronized void deathAction(){
		
	}

	public synchronized void inventoryAction(){
		TileRenderer.check.mark(controlled.inventory().findByKey(TileRenderer.check.getPos()));

		while(Keyboard.next()){
		
			// Move a caret
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				if(TileRenderer.check.getPos() < controlled.inventory().allInvenory().size()-1){
					TileRenderer.check.next();
					TileRenderer.check.mark(controlled.inventory().allInvenory().get(TileRenderer.check.getPos()));
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				if(TileRenderer.check.biggerNull()){
					TileRenderer.check.prev();
					TileRenderer.check.mark(controlled.inventory().allInvenory().get(TileRenderer.check.getPos()));
				}
			}

			// Equip
			if(Keyboard.isKeyDown(Keyboard.KEY_E)){
				turns++;
				switch(TileRenderer.check.getThing().getMType()){
				
				case WEAPON:
					if(TileRenderer.check.getThing() == controlled.getRightHand()){
						controlled.unuseRightWeapon(controlled.getRightHand());
					}
					else{
						controlled.useRightWeapon((Weapon) TileRenderer.check.getThing());
					}
					break;
				
				case ARMOR:
					Armor a = (Armor)TileRenderer.check.getThing();
					System.out.println(a.getType());
					if(a.getType() == Armor.Type.HEAD){
						if(controlled.head() == a){
							controlled.unwearArmor(a);
							System.out.println("Head armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Head armor wearing!");
						}
					} else
					if(a.getType() == Armor.Type.BODY){
						if(controlled.body() == a){
							controlled.unwearArmor(a);
							System.out.println("Body armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Body armor wearing!");
						}
					} else
					if(a.getType() == Armor.Type.FOOTS){
						if(controlled.foots() == a){
							controlled.unwearArmor(a);
							System.out.println("Foots armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Foots armor wearing!");
						}
					} else
					if(a.getType() == Armor.Type.ARMS){
						if(controlled.arms() == a){
							controlled.unwearArmor(a);
							System.out.println("Arms armor unwearing!");
						}
						else{
							controlled.wearArmor(a);
							System.out.println("Arms armor wearing!");
						}
					} else
					if(a.getType() == Armor.Type.LEGS){
						if(controlled.legs() == a){
							controlled.unwearArmor(a);
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
			// Drop item
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
			// Try to drop an equipped item
			if(who.getRightHand() == dropped){
				who.unuseRightWeapon((Weapon) dropped);
			}
			if(who.head() == dropped){
				who.unwearArmor((Armor)dropped);
			}
			if(who.body() == dropped){
				who.unwearArmor((Armor)dropped);
			}
			if(who.arms() == dropped){
				who.unwearArmor((Armor)dropped);
			}
			if(who.legs() == dropped){
				who.unwearArmor((Armor)dropped);
			}
			if(who.foots() == dropped){
				who.unwearArmor((Armor)dropped);
			}
			who.dropItem(what);
			TileRenderer.getDungeon().addThing(dropped, who.getX(), who.getY());
		}
	}

	// On death. TODO: remove as a logic method
	public void death(AbstractCreature who) {
		// Save loot into list
		List<Integer> buffer = new ArrayList<>();
		for (Entry<Integer, AbstractThing> t : who.inventory().allInvenory().entrySet()) {
			buffer.add(t.getKey());
		}
		// Drop all from inventory
		for (Integer i : buffer) {
			drop(who, i);
		}
	}

	public synchronized void commandAction(){

		// If you are dead
		if(!controlled.isAlive()){
			TileRenderer.gameState = TileRenderer.State.DEATH;
		}
		
		long x = controlled.getX();
		long y = controlled.getY();

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
			
			// Take item
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
			
			// Render inventory
			if(Keyboard.isKeyDown(Keyboard.KEY_I)) {
				TileRenderer.gameState = TileRenderer.State.INVENTORY;
			}

			// Move next dungeon
			if(Keyboard.isKeyDown(Keyboard.KEY_G)) {
				Portal port = TileRenderer.getDungeon().getPortal(x, y);
				if(port != null){
					TileRenderer.getDungeon().removeHero();
					port.next().addHero((Hero) controlled);
					TileRenderer.setDungeon(port.next());
					// Set start coords as portal coords wich was used for change dungeon
					port.next().getHero().setX(port.getStartX());
					port.next().getHero().setY(port.getStartY());
					// Visible hero
					port.next().getHero().setVisible(true);
				}
			}
			surroundTurn();
			recreateVisible();
		}
	}

	// Move controlled creature (your hero, pet, any creature what can be controlled at this moment)
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

		// Healing. TODO: remove it
		int hilKoef = 5;
		if(turns%hilKoef == 0 && controlled.hp().getCurrent() < controlled.hp().getFull()){
			controlled.hp().setCurrent(controlled.hp().getCurrent() + 1);
		}
	}

	// All map creatures do something
	// TODO: move all activity into AI and call AI methods
	public void surroundTurn(){
		if(turns>oldTurns){
			List<AbstractCreature> deadCreatures = new ArrayList<>();
			List<AbstractCreature> creatures = TileRenderer.getDungeon().getCreatures();
			for(AbstractCreature c : creatures){
				if(c != controlled && c.isAlive()){
					c.getAi().setVisible(TileRenderer.getDungeon());
					c.getAi().setDivide((int) TileRenderer.getDungeon().getWidth(), (int) TileRenderer.getDungeon().getHeight());
					c.attack(this.controlled); // Attack hero
				}
				if(!c.isAlive()){
					death(c);
					deadCreatures.add(c);
				}
				oldTurns = turns;
			}
			creatures.removeAll(deadCreatures);
		}
	}
}
