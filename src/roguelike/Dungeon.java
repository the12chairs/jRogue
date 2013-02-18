package roguelike;

import java.io.IOException;
import java.util.LinkedList;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Dungeon {
	
	private LinkedList<Tile> dungeon;
	private int numRooms;
	//private LinkedList<Room> rooms;
	private long height;
	private long width;
	
	
	private static final int maxRoomSquare = 20;
	
	public long getHeight(){
		return this.height;
	}
	public long getWidth(){
		return this.width;
	}
	
	public void setDungeon(LinkedList<Tile> dungeon) {
		this.dungeon = dungeon;
	}
	
	
	public Dungeon(int x, int y){
		//this.rooms = new LinkedList<Room>();
		this.height = x;
		this.width = y;
		// Рисуем коробку x/y, окруженную стеной
		this.dungeon = new LinkedList<Tile>();

	}

	
	public Dungeon(String filePath){
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
			char tile = parsableTile.get("tile").toString().charAt(0);
			boolean passable = (boolean) parsableTile.get("passable");
			boolean visible = true; // По умолчанию видно тайл
			long x = (long) parsableTile.get("x"); 
			long y = (long) parsableTile.get("y");
			dungeon.add(new Tile(title, tile, passable, visible, x, y));
			
		}

		
	}
	
	
	
	
	public void oldDungeon(String filePath){
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

		tiles = (JSONArray) jsonObj.get("tiles");
		LinkedList<TileProto> tilePrototipes = new LinkedList<TileProto>();
		for(int i = 0; i < tiles.size(); i++){ // Каждый тайл из файла засунем в список тайлов
			
			// Создадим на основе полученных значений тайлы, и запихнем и в список тайлов
			JSONObject parsableTile = (JSONObject) tiles.get(i);
			
			String title = (String) parsableTile.get("title");
			char tile = parsableTile.get("tile").toString().charAt(0);
			boolean passable = (boolean) parsableTile.get("passable");
			TileProto p = new TileProto(title, tile, passable);
			// Добавить прототип в масив прототипов
			tilePrototipes.add(p);
			
			
		}
		
		// Дебаг
		for(TileProto t : tilePrototipes){
			System.out.println(t.getTile());
		}
		
		
		String map = (String) jsonObj.get("map"); // Выдернем карту из файла
		
		// Посчитаем высоту и ширину
		int w = 0;
		while(map.charAt(w) != '\n'){
			w++;
		}
		
		// Занесем в атрибуты класса
		this.width = w - 1;
		this.height = map.length() / w - 1;
		
		// Дебаг
		System.out.println(height + ":" + width);
		int current = 0;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){

				for(TileProto t : tilePrototipes){ // Пробежимся по списку прототипов и найдем
					if(t.getTile() == map.charAt(current)){
						//System.out.println("Нашли "+ t.getTitle());
						Tile finalTile = new Tile(t.getTitle(), t.getTile(), t.getPassable(), true, i, j);
						dungeon.add(finalTile);
					}
				}
				current++;
			}
		}
	
	}
	
	public void generateDungeon(int x, int y, int rooms){

	}
	
	
	public boolean isGoodRoom(Room room){
		if(room.calculateSquare() > maxRoomSquare && room.edges[1].getX() >= width
				&& room.edges[1].getY() >= height){
			return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		//int y = 100;
		//int x = 50;
		//int rooms = 2;

		Dungeon d = new Dungeon("./modules/TestModule/locations/test.json");
		d.oldDungeon("../jRogueLocationConverter/test.old.json");
		int i = 0;
		for(Tile t : d.dungeon){
			System.out.print(t.getY());
			if(i == d.getWidth() - 1){
				System.out.println();
				i = 0;
			}
			i++;
		}

		/*
  		int k = 0;
		for(int i = 0; i < d.getHeight(); i++){
			for(int j = 0; j < d.getWidth(); j++){
				if(d.dungeon.get(k).getX() == i && d.dungeon.get(k).getY() == j){
					System.out.print(( d.dungeon.get(k)).getTile());
				}
				k++;
			}
			System.out.println();

		}
*/
	}
	
	public int getNumRooms() {
		return numRooms;
	}


	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	class Room {
		// Класс содержит вершинные тайлы комнаты
		private Tile edges[] = new Tile[4];
		private int square;
		public Room(Tile highLeft, Tile highRight, Tile lowRight, Tile lowLeft){
			edges[0] = highLeft;
			edges[1] = highRight;
			edges[2] = lowRight;
			edges[3] = lowLeft;
		}
		
		public long calculateSquare(){
			return ((edges[2].getX() - edges[0].getX()) * (edges[2].getY() - edges[0].getY())); 
		}
		
		
		public int getSquare() {
			return square;
		}
		
		public void setSquare(int square) {
			this.square = square;
		}
		
	}
	
	
	// Прототип тайла без координат
	public class TileProto{
		private String title;
		private char tile;
		private boolean passable;
		
		public TileProto(String title, char tile, boolean passable){
			this.title = title;
			this.tile = tile;
			this.passable = passable;
		}

		public char getTile() {
			return tile;
		}

		public String getTitle(){
			return title;
		}
		
		public boolean getPassable(){
			return passable;
		}
	}
	

}

