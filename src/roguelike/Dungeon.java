package roguelike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Dungeon {

	private Tile dungeon[][];
	private int numRooms;
	private LinkedList<Room> rooms;
	private int height;
	private int width;
	
	
	private static final int maxRoomSquare = 20;
	
	public void setDungeon(Tile dungeon[][]) {
		this.dungeon = dungeon;
	}
	
	
	public Dungeon(int x, int y){
		this.rooms = new LinkedList<Room>();
		this.height = x;
		this.width = y;
		// Рисуем коробку x/y, окруженную стеной
		this.dungeon = new Tile[x][y];

		for(int i = 0; i < x; i++){

			for(int j = 0; j < y; j++){
				dungeon[i][j] = new Tile("Пол", '.', true, true, i, j);
				if(i == 0) {
					dungeon[0][j] = new Tile("Стена", '#', true, false, i, j);
				}
				if(i == x - 1){
					dungeon[x-1][j] = new Tile("Стена", '#', true, false, i, j);;
				}
				if(j == 0){
					dungeon[i][0] = new Tile("Стена", '#', true, false, i, j);;
				}
				if(j == y - 1){
					dungeon[i][y-1] = new Tile("Стена", '#', true, false, i, j);;
				}
			}
		}
	}

	
	public Dungeon(String filePath){
		// Прочтем карту из json файла
		// Ох и медленно эта хрень работать будет ><
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
		ArrayList<Tile> tileList = new ArrayList<Tile>();
		for(int i = 0; i < tiles.size(); i++){ // Каждый тайл из файла засунем в список тайлов
			
			// Создадим на основе полученных значений тайлы, и запихнем и в список тайлов
			JSONObject parsableTile = (JSONObject) tiles.get(i);
			
			String title = (String) parsableTile.get("title");
			char tile = parsableTile.get("tile").toString().charAt(0);
			boolean passable = (boolean) parsableTile.get("passable");
			boolean visible = true; // По умолчанию видно тайл
			
			Tile fromFile = new Tile(title, tile, passable, visible, 0, 0);
			tileList.add(fromFile);
			
		}
		
		String stringMap  = (String) jsonObj.get("map");
		//System.out.print(stringMap);
		
		// Посчитаем высоту и ширину карты
		int height = 0;
		
		while(stringMap.charAt(height) != '\n'){
			height++;
		}
		
		int width = stringMap.length() / height;
		
		//System.out.println(width);
		int k = 0;
		char symbol = '1';
		for(int j = 0; j < width; j++){
			for(int i = 0; i < height; i++){
				//char symbol = '1';
				if(stringMap.charAt(k) == '\n'){
					System.out.print('1');//
				}
				else{
					System.out.print(stringMap.charAt(k));
				}
				
				k++;
			}
		}
		
	}
	
	
	public void generateDungeon(int x, int y, int rooms){
		this.height = x;
		this.width = y;
		this.numRooms = rooms;
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				this.dungeon[i][j] = new Tile("Стена", '#', true, false, i, j);;
			}
		}
		

		for(int i = 0; i < rooms; i++){
			Room r = generateRoom(this.height - 10);
			while(!isGoodRoom(r)){
				r = generateRoom(this.height - 10);	
			}
			this.rooms.add(r);
			this.setRoom(r);
		}
		
	}
	
	
	public boolean isGoodRoom(Room room){
		if(room.calculateSquare() > maxRoomSquare && room.edges[1].getX() >= width
				&& room.edges[1].getY() >= height){
			return false;
		}
		return true;
	}
	
	
	public Room generateRoom(int seed){
		// Зафигачим координаты
		Random rnd = new Random();
		
		// Вроде правильно
		
		int hlx = rnd.nextInt(seed - 10);

		int hly = rnd.nextInt(seed - 10);
		
		int hrx = hlx;
		int hry = hly + rnd.nextInt(this.width - 2*hly);
		
		int lrx = hrx + rnd.nextInt(this.width - 2*hrx);
		int lry = hry;
		
		int llx = lrx;
		int lly = hly;
		
		Tile hl = new Tile("Пол", '.', true, true, hlx, hly);
		Tile hr = new Tile("Пол", '.', true, true, hrx, hry);
		Tile lr = new Tile("Пол", '.', true, true, lrx, lry);
		Tile ll = new Tile("Пол", '.', true, true, llx, lly);
		
		return new Room(hl, hr, lr, ll);
	}
	
	public void setRoom(Room room){
		for(int i = room.edges[0].getX(); i < room.edges[2].getX(); i++){
			for(int j = room.edges[0].getY(); j < room.edges[2].getY(); j++){
				this.dungeon[i][j] = new Tile("Пол", '.', true, true, i, j);
			}
		}
		
	}
	
	public static void main(String[] args) {
		//int y = 100;
		//int x = 50;
		//int rooms = 2;

		Dungeon d = new Dungeon("./modules/TestModule/locations/test.json");
		
		/*
		d.generateDungeon(x, y, rooms);
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				//System.out.print("<"+d.dungeon[i][j].getX()+":"+d.dungeon[i][j].getY()+">");
				System.out.print(d.dungeon[i][j].getTile());
			}
			System.out.println();
			
		}
		for(int k = 0; k < d.getNumRooms(); k++){
			System.out.println();
			for(int i = 0; i < 4; i++){
				System.out.println(d.rooms.get(k).edges[i].getX() + ":" + d.rooms.getFirst().edges[i].getY());
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
		
		public int calculateSquare(){
			return ((edges[2].getX() - edges[0].getX()) * (edges[2].getY() - edges[0].getY())); 
		}
		
		
		public int getSquare() {
			return square;
		}
		
		public void setSquare(int square) {
			this.square = square;
		}
		
	}

}

