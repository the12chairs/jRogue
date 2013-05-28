package tools;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lowlevel.Tile;
//import lowlevel.ActiveTile;
import lowlevel.Dungeon;


public class DungeonGenerator {
	Dungeon dungeon; // Что мы будем генерить
	
	
	private int height;
	private int width;

	private int maxRooms;
	
	private int minDivide;
	
	
	private List<Room> rooms;
	
	public static final Tile wall = new Tile("Wall", "./res/tiles/wall.png", false, false);
	public static final Tile floor = new Tile("Floor", "./res/tiles/floor.png", false, true);
	
	
	
	// Комнаты для удобства представления
	class Room {
		
		int hlx;
		int hly;
		int brx;
		int bry;

		
		int square;
		
		public Room(int hlx, int hly, int brx, int bry){
			this.hlx = hlx;
			this.hly = hly;
			this.brx = brx;
			this.bry = bry;
			
			square = (brx - hlx) * (bry - hly);
			
		}
		
	}
	
	
	public DungeonGenerator(int height, int width, int rooms, int minDivide){
		
		System.out.println("Initialization dungeon generator...");		
		this.height = height;
		this.width = width;
		this.maxRooms = rooms;
		this.minDivide = minDivide;
		
		this.rooms = new ArrayList<Room>();
		System.out.println("Done!");
		}
	
	
	public int setRoomPoint(int min, int max){
		Random rnd = new Random();
		int point = rnd.nextInt(max);
		while((point < min) && (point > max)){
			point = rnd.nextInt(max);
		}
		
		return point;
		
	}
	
	
	public Room generateRoom(int hlx, int hly, int brx, int bry){

		return new Room(hlx, hly, brx, bry);
	}
	
	

	
	public int horizontalDivide(){
		Random rnd = new Random();
		int d = 0;
		while(d < minDivide){
			d = rnd.nextInt(height - minDivide);
		}
		return d;
	}
	
	public int verticalDivide(){
		Random rnd = new Random();
		int d = 0;
		while(d < minDivide){
			d = rnd.nextInt(width - minDivide);
		}
		return d;
	}
	
	
	public void makeRoom(Room r){
		for(int i = r.hlx; i <= r.brx; i++){
			for(int j = r.hly; j <= r.bry; j++){
				System.out.println(i + " " + j);
				dungeon.removeTile(i, j);
				dungeon.addTile(new Tile(floor, i, j));
			}
		}
	}
	
	
	
	public Dungeon generateDungeon(){
	
		System.out.println("Generate dungeon withs params:");
		
		Dungeon dungeon = new Dungeon(height, width);
		
		
		Random rnd = new Random();
		
		for(int i = 0; i <= height; i++){
			for(int j = 0; j <= width; j++){
	
				dungeon.addTile(new Tile(wall, j, i));

			}
		}

		int prewH = 1;//horizontalDivide();
		int prewW = 1;//verticalDivide();
		
		int x1;
		int y1;
		
		int x2;
		int y2;
		
		
		//for(int i = 1; i < maxRooms; i++){
		

		
		int wh = horizontalDivide();
		int ww = verticalDivide();
		
		//makeRoom(new Room(prewH, wh, prewW, ww));
		
			
			
		// Верхняя левая
		
		
		for(int j = prewW ; j < ww - 1; j++){
			for(int k = prewH; k < wh - 1; k++){
				dungeon.removeTile(j, k);
				dungeon.addTile(new Tile(floor, j, k));
			}
			
		}
		
		x1 = setRoomPoint(prewW, ww);//rnd.nextInt(ww);
		y1 = setRoomPoint(prewH, wh);

		x2 = rnd.nextInt(width);
		y2 = rnd.nextInt(wh);
		
		System.out.println(x1+":"+y1);
		
		
		//dungeon.removeTile(x1, y1);
		//dungeon.removeTile(x2, y2);
		// Верхняя правая
		int coridor1 = rnd.nextInt(wh);
		int coridor2 = rnd.nextInt(ww);
		for(int j = ww ; j < width; j++){
			for(int k = prewH; k < wh; k++){
				dungeon.removeTile(j, k);
				dungeon.addTile(new Tile(floor, j, k));
			}
			if(j == coridor2){

			}
		}
		
		
		// Нижняя левая
		
		for(int j = prewW; j < ww - 1; j++){
			for(int k = wh; k < height - 1; k++){
				dungeon.removeTile(j, k);
				dungeon.addTile(new Tile(floor, j, k));
			}
		}
		
		
		// Нижняя правая
		for(int j = ww + 1; j < width; j++){
			for(int k = wh + 1; k < height; k++){
				dungeon.removeTile(j, k);
				dungeon.addTile(new Tile(floor, j, k));
			}
		}
					
	
		
		
		// Коридоры
		
		
	
		//while (wh)
		//wh = horizontalDivide();
		//ww = verticalDivide();
		//Random rnd = new Random();
		
		//}
		
		

		
		//Room r = new Room(r);
		
		
		/*for(int i = 0; i <= height; i++){
			dungeon.removeTile(i, prewH);
			dungeon.addTile(new Tile(floor, i, prewH));
		}
		
		for(int i = 0; i <= width; i++){
			dungeon.removeTile(prewW, i);
			dungeon.addTile(new Tile(floor, prewW, i));
		}*/
		
		
		return dungeon;
		
	}
	
	
	
	public static void main(String args[]){
		DungeonGenerator g = new DungeonGenerator(20, 30, 2, 5);
		g.generateDungeon();
	}
	
}
