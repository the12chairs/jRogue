package lowlevel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import lifeforms.AbstractCreature;
import lifeforms.Hero;
import lifeforms.AbstractCreature.Profession;

import org.jruby.RubyProcess;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import primitives.Quest;
import properties.Race;
import rlforj.los.ILosBoard;


import tools.FileReader;

/**
 * Class for level maps. It's all a dungeons
 */
public class Dungeon implements ILosBoard{
	
	private LinkedList<Tile> dungeon;
	private LinkedList<AbstractThing> sceneThings;
	private LinkedList<Quest> sceneQuests;
	private LinkedList<AbstractCreature> sceneLife;
	private LinkedList<Portal> scenePortals;

	private int numRooms;
	
	private long height;
	private long width;
	
	public void addPortal(Portal p){
		scenePortals.push(p);
	}
	
	public void addHero(Hero hero){
		sceneLife.addFirst(hero);
	}
	
	public void removeHero() {
		sceneLife.removeFirst();
	}
	public AbstractCreature getHero(){
		return sceneLife.getFirst();
	}
	
	public void addThing(AbstractThing thing){
		sceneThings.push(thing);
	}
	
	public void addThing(AbstractThing thing, long x, long y){
		thing.setX(x);
		thing.setY(y);
		sceneThings.push(thing);
	}

	public void addLife(AbstractCreature life){
		sceneLife.push(life);
	}

    public void removeLife(AbstractCreature life)
    {
        sceneLife.remove(life);
    }
	
	public void addQuest(Quest quest){
		sceneQuests.push(quest);
	}

	public long getHeight(){
		return this.height;
	}

	public long getWidth(){
		return this.width;
	}
	
	public void setDungeon(LinkedList<Tile> dungeon) {
		this.dungeon = dungeon;
	}

	// Return all creatures on a map
	public LinkedList<AbstractCreature> getCreatures(){
		return this.sceneLife;
	}

	// Return all items on a map
	public LinkedList<AbstractThing> getItems(){
		if(this.sceneThings.size() == 0) return null;
		else
			return this.sceneThings;
	}

	public Dungeon(int x, int y){
		this.height = x;
		this.width = y;
		// Рисуем коробку x/y, окруженную стеной
		this.dungeon = new LinkedList<Tile>();
		this.sceneLife = new LinkedList<AbstractCreature>();
		this.sceneQuests = new LinkedList<Quest>();
		this.sceneThings = new LinkedList<AbstractThing>();
		this.scenePortals = new LinkedList<Portal>();
	}

	public LinkedList<Tile> dungeon(){
		return this.dungeon;
	}

	// JSON map format. For pre-generated maps (for scenarios, as example)
	public Dungeon(String filePath){

		this.sceneLife = new LinkedList<AbstractCreature>();
		this.sceneQuests = new LinkedList<Quest>();
		this.sceneThings = new LinkedList<AbstractThing>();
		this.scenePortals = new LinkedList<Portal>();

		// Read map form JSON
		// Can it be optimized?
		dungeon = new LinkedList<Tile>();
		String fileContent = null;
		
		try {
			fileContent = FileReader.readFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Parse dat motafucker
		JSONParser parser = new JSONParser();
		
		Object obj = null;
		try {
			obj = parser.parse(fileContent);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Get main dungeon params from file
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray tiles = new JSONArray();
		this.height = (long) jsonObj.get("height");
		this.width = (long) jsonObj.get("width");
		tiles = (JSONArray) jsonObj.get("tiles");

		// Push all tile walues into list
		for(int i = 0; i < tiles.size(); i++) {
			JSONObject parsableTile = (JSONObject) tiles.get(i);
			
			String title = (String) parsableTile.get("title");
			String tile = parsableTile.get("tile").toString();
			boolean passable = (boolean) parsableTile.get("passable");
			boolean visible = true; // By default tile is visible
			long x = (long) parsableTile.get("x"); 
			long y = (long) parsableTile.get("y");
			dungeon.add(new Tile(title, tile, passable, visible, x, y));
		}
	}

	public int getDungeonSize(){
		return this.dungeon.size();
	}

	public void generateDungeon(int x, int y, int rooms){}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}
	
	// Add full-functional Tile with coords
	public void addTile(Tile t){
		dungeon.push(t);
	}
	
	public void removeTile(int x, int y){
		Tile t = getTile(x, y);
		dungeon.remove(t);
	}

	// Add proto-Tile without coords and set they here
	public void addTile(Tile t, int x, int y){
		t.setX(x);
		t.setY(y);
		dungeon.push(t);
	}

	// Get a creature form tile with coords x,y. return null of there is no creature
	// Important: only first-founded creature!
	public AbstractCreature getCreature(long x, long y){
		AbstractCreature creature = null;
		for(AbstractCreature c : sceneLife){
			if((c.getX() == x) && (c.getY() == y)){
				creature = c;
				break;
			}
		}
		return creature;
	}

	// Get all things for tile with coords x,y. Return empty list if there are no things
	public List<AbstractThing> getThings(long x, long y){
		List<AbstractThing> things = new LinkedList<AbstractThing>();
        for(AbstractThing t : sceneThings){
            if((t.getX() == x) && (t.getY() == y)){
                things.add(t);
            }
        }
		return things;
	}

	// Return all things linked with this dungeon
    public List<AbstractThing> getThings(){
        return sceneThings;
    }

    // Get portal setted on this tile with coords x,y. Null if there is no portal
	// Important: only first-found portal will be returned
	public Portal getPortal(long x, long y){
		Portal portal = null;
		for(Portal p : scenePortals){
			if((p.getX() == x) && (p.getY() == y)){
				portal = p;
				break;
			}
		}
		return portal;
	}
	
	// Get tile by coords
	public Tile getTile(long x, long y){
		Tile t = null;
		if (x > width || y > width){
			return null;
		}
		for(Tile i : dungeon){
			if((i.getX() == x) && (i.getY() == y)){
				t = i;
				break;
			}
		}
		return t;
	}

	// If such coords in dungeon size
	@Override
	public boolean contains(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	// can we stay on this tile with x,y?
	@Override
	public boolean isObstacle(int x, int y) {
		return !getTile(x, y).getPassable();
	}

	// Visit a tile, it means remember it for FOV-alhorytm
	@Override
	public void visit(int x, int y) {
		if(x < width && y < height) {
			getTile(x, y).visit();
			getTile(x, y).setVisible(true);

			for(AbstractThing t : getThings(x, y)){
				if (t == null) continue;
				if(getTile(x,y).isVisited()){
					t.setVisible(true);
				}
				else{
					t.setVisible(false);
				}
			}
			
			// TODO: repair. WAT?
			for(AbstractCreature c : sceneLife){
				if(c == sceneLife.getFirst()) continue;
				if(getTile(c.getX(), c.getY()).isVisited()){
					c.setVisible(true);
				}
				else{
					c.setVisible(false);
				}
			}
		}
	}
}

