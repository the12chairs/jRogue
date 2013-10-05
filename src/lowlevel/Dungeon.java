package lowlevel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import lifeforms.AbstractCreature;
import lifeforms.Hero;
import lifeforms.AbstractCreature.Profession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import primitives.Quest;
import properties.Race;
import rlforj.los.ILosBoard;


import tools.FileReader;

public class Dungeon implements ILosBoard{
	
	private LinkedList<Tile> dungeon;
	private LinkedList<AbstractThing> sceneThings;
	private LinkedList<Quest> sceneQuests;
	private LinkedList<AbstractCreature> sceneLife;
	private LinkedList<Portal> scenePortals;

	private int numRooms;
	
	//private LinkedList<Room> rooms;
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
	
	
	public void addQuest(Quest quest){
		sceneQuests.push(quest);
	}
	//private static final int maxRoomSquare = 20;
	
	public long getHeight(){
		return this.height;
	}
	public long getWidth(){
		return this.width;
	}
	
	public void setDungeon(LinkedList<Tile> dungeon) {
		this.dungeon = dungeon;
	}
	
	
	public LinkedList<AbstractCreature> getCreatures(){
		return this.sceneLife;
	}
	
	
	public LinkedList<AbstractThing> getItems(){
		return this.sceneThings;
	}
	
	
	public Dungeon(int x, int y){
		//this.rooms = new LinkedList<Room>();
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
	
	public Dungeon(String filePath){
		
		
		
		sceneLife = new LinkedList<AbstractCreature>();
		
		// Прочтем карту из json файла
		// Ох и медленно эта хрень работать будет ><
		dungeon = new LinkedList<Tile>();
		String fileContent = null;
		
		try {
			fileContent = FileReader.readFile(filePath);
			
		} catch (IOException e) { // Хьюстон, у нас проблема
			e.printStackTrace();
		}
		
		// Парсим засранца
		JSONParser parser = new JSONParser();
		
		Object obj = null;
		try {
			obj = parser.parse(fileContent);
		} catch (ParseException e) {
			// Нираспарсилось((((999
			e.printStackTrace();
		}
		// Выдернем из него все значения
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray tiles = new JSONArray();
		this.height = (long) jsonObj.get("height");
		this.width = (long) jsonObj.get("width");
		tiles = (JSONArray) jsonObj.get("tiles");

		for(int i = 0; i < tiles.size(); i++){ // Каждый тайл из файла засунем в список тайлов
			
			// Создадим на основе полученных значений тайлы, и запихнем и в список тайлов
			JSONObject parsableTile = (JSONObject) tiles.get(i);
			
			String title = (String) parsableTile.get("title");
			String tile = parsableTile.get("tile").toString();
			boolean passable = (boolean) parsableTile.get("passable");
			boolean visible = true; // По умолчанию видно тайл
			long x = (long) parsableTile.get("x"); 
			long y = (long) parsableTile.get("y");
			dungeon.add(new Tile(title, tile, passable, visible, x, y));
			

		}

		
	}
	
	
	

	public int getDungeonSize(){
		return this.dungeon.size();
	}
	
	public void generateDungeon(int x, int y, int rooms){

	}
	
	
	public int getNumRooms() {
		return numRooms;
	}


	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}
	
	
	// Добавить полноценный тайл с коодинатами
	public void addTile(Tile t){
		dungeon.push(t);
	}
	
	
	public void removeTile(int x, int y){
		Tile t = getTile(x, y);
		dungeon.remove(t);
	}
	// Добавить прототип без координат
	public void addTile(Tile t, int x, int y){
		t.setX(x);
		t.setY(y);
		dungeon.push(t);
	}
	
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
	
	
	public List<AbstractThing> getThings(long x, long y){
		List<AbstractThing> things = new LinkedList<AbstractThing>();
		for(AbstractThing t : sceneThings){
			if((t.getX() == x) && (t.getY() == y)){
				things.add(t);
			}
		}
		return things;
	}
	
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

	public static void main(String[] args) {
		// Тесты
		/*
		Race dwarf = new Race("Дварф", 5, 0, -3, -1, -1, 4);
		Hero you = new Hero("Макс", "ololo", 2, 1, 5, 5, 5, 5, dwarf, 2, Profession.WARRIOR);
		//System.out.println("Опыт: " + you.exp.getPair());
		you.initRaceBonuses();
		
		System.out.println(you.getFace());
		//Dungeon d = new Dungeon("./modules/TestModule/locations/texture.json");
		LinkedList<AbstractCreature> f = new LinkedList<AbstractCreature>();
		f.push(you);
		//d.addLife(you);
*/
	}

	@Override
	public boolean contains(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
		//return true;
	}

	@Override
	public boolean isObstacle(int x, int y) {
	
		if(getTile(x, y).getPassable() == false){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public void visit(int x, int y) {
		if( x < width && y < height){
			getTile(x, y).setVisible(true);
			for(AbstractThing t : getThings(x, y)){
				t.setVisible(true);
			}
		}
	}


}

