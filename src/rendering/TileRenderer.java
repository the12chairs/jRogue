package rendering;

import items.Weapon;
import items.Weapon.Type;

import java.awt.Font;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lifeforms.AbstractCreature.Profession;
import lifeforms.Hero;
import lowlevel.AbstractThing;
import lowlevel.Dungeon;
import lowlevel.KeyboardControl;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import dnd.Dice;
import primitives.GraphObject;
import properties.Race;
import properties.Stat;
import tools.DungeonGenerator;

public class TileRenderer extends Thread {

	

	
	
	// TODO: Сделать обработку этих состояний
	public enum State { MAIN_MENU, DUNGEON, INVENTORY,
						DROP_ITEM, WEAR_ARMOR, TAKE_WEAPON,
						MAGICK, ALCHEMY, RUNES, CRAFT };
	
	private static final int HEIGHT = 800;
	private static final int WIDTH = 600;
	private static final int TILE_SIZE = 16;
	
	private long lastFrame;
	private long lastFPS;
	private int fps;
	
	
	private TrueTypeFont headFont;
	private TrueTypeFont bodyFont;
	
	public static State gameState;
	
	
	private Dungeon cDungeon;
	
	
	
	
	private Checkbox check;
	
	
	
	
	public TileRenderer(Dungeon cDungeon){
		this.cDungeon = cDungeon;
		gameState = State.DUNGEON;
		check = new Checkbox(">");
	}
	
	
	public void renderInventory(){
		// Отрисовка инвентаря
		
		
		headFont.drawString(WIDTH / 2 + 40, 20, "Inventory");
		
		int w = 50;
		
		
		for (Entry<Integer, AbstractThing> entry : cDungeon.getHero().inventory().allInvenory().entrySet()) {
			int h = 20;
			bodyFont.drawString(h, w, entry.getKey().toString() + " " + entry.getValue().getName(), Color.white);
			w += 10;

		}

	}

	
	
	
	public Checkbox getCheckbox(){
		return check;
	}
	
	public void renderDrop(){
		// Отрисовка меню выбрасывания предмета
			
		headFont.drawString(WIDTH / 2 + 40, 20, "Drop item");
		
		int w = 50;
		
		
		for (Entry<Integer, AbstractThing> entry : cDungeon.getHero().inventory().allInvenory().entrySet()) {
			int h = 20;
			bodyFont.drawString(h, w, entry.getKey().toString() + " " + entry.getValue().getName(), Color.white);
			w += 10;
		}

	}
	
	
	
	public void renderWeapon(){
		
		headFont.drawString(WIDTH / 2 + 40, 20, "Weapon");
		
		int w = 50;
		
		
		for (Entry<Integer, Weapon> entry : cDungeon.getHero().inventory().getAllWeapon().entrySet()) {
			int h = 20;
			//System.out.println(entry.getValue().getDamage().getDice());
			bodyFont.drawString(h, w, entry.getKey().toString() + " " + entry.getValue().getName() + 
					" +" + entry.getValue().getBonus() + " " + entry.getValue().getDamage().getPair(), Color.white);
			w += 10;
			
			if(entry.getValue() != null){
				check.mark(entry.getValue());
			}
			

		}
		//Map<Integer, Weapon> e = cDungeon.getHero().inventory().getAllWeapon().entrySet();
		check.renderFirst();
		//System.out.println(check.getThing().getName());
	}
	
	
	public void renderMainMenu(){
		// Заглушка
	}
	
	
	// Грузим текстурку
	
	public Texture loadTexture(String texturePath){
		Texture t = null;
		try {
			t = TextureLoader.getTexture
					("PNG", ResourceLoader.getResourceAsStream(texturePath));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	// Рисуем карту
	public void renderDungeon(){

		GraphObject tmp = cDungeon.dungeon().get(0);
		loadTexture(tmp.getFace()).bind();
		
		for(GraphObject tile : cDungeon.dungeon()){
					
			if(tile.getVisible() == true){
				//Texture t = loadTexture(tile.getFace());
				//t.bind();
				//loadTexture(tile.getFace()).bind();
				renderTile(tile);
			
				//t.release();
			}
			
			
			if(!tmp.getFace().equals(tile.getFace())){
				//System.out.println(tile.getFace());
				tmp = tile;
				loadTexture(tmp.getFace()).bind();

			}
			//updateFPS();
		}
		
		
		
		
		for(AbstractThing item : cDungeon.getItems()){
			if(item.getVisible() == true){
				loadTexture(item.getFace()).bind();
				renderTile(item);
			}
		}
		
		for(GraphObject creature : cDungeon.getCreatures()){
			if(creature.getVisible() == true){
				loadTexture(creature.getFace()).bind();
				renderTile(creature);
			}
		}
		
	}
	

	// Отрисовка отдельного тайла
	public void renderTile(GraphObject tile){
		
		
		//IntBuffer cBuffer = BufferUtils.createIntBuffer(4); // Четыре точки квадратного тайла
		//loadTexture(tile.getFace()).bind();
		
		//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		// Умножим игровую координату на TILE_SIZE, чтобы получить реальную графическую координату
		GL11.glVertex2f(tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(tile.getX() * TILE_SIZE + TILE_SIZE, tile.getY() * TILE_SIZE);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(tile.getX() * TILE_SIZE + TILE_SIZE, tile.getY() * TILE_SIZE + TILE_SIZE);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE + TILE_SIZE);
		GL11.glEnd();
	}

	
	
	
	@Override
	public void run(){
		System.out.println("Rendering thread has started.");
		render();
		Thread.yield();
		
	}
	
	
	
	public long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta(){
		long time = getTime();
		int delta = (int)(time - lastFrame);
		lastFrame = time;
		return delta;
	}
	
	
	public void updateFPS(){
		if(getTime() - lastFPS > 1000){
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	
	 public void renderState(){
		 // Состояния рендерера
		 switch(gameState){
		 case INVENTORY:
			 renderInventory();
			 break;
		 case DROP_ITEM:
			 renderDrop();
			 break;
		 case DUNGEON:
			 renderDungeon();
			 break;
		 case TAKE_WEAPON:
			 renderWeapon();
			 break;
		 default:
			 break;
			
		 }
	 }
	
	
	
	public synchronized void render(){
		initGL(HEIGHT, WIDTH);

		while (true) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			//--------------			
			renderState();
			//--------------
			Display.update();
			Display.sync(100);

		if (Display.isCloseRequested()) {
			Display.destroy();
			System.exit(0);
			}
		Thread.yield();
		}
		
	}

	
	
	private void initGL(int width, int height) {

		try {		
			System.out.println("Creating display...");
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
			Display.setVSyncEnabled(true);
			Keyboard.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
				System.exit(0);

			}
		System.out.println("Done!");
		System.out.println("Initializing OpenGL...");
		GL11.glEnable(GL11.GL_TEXTURE_2D);              
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);         

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0,0,width,height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		System.out.println("Done!");
		
		System.out.println("Fonts initializing.");
		initFonts();
		System.out.println("Done!");
	}
	
	public void initFonts() {
		// load a default java font
		Font awtFont1 = new Font("Times New Roman", Font.BOLD, 24);
		headFont = new TrueTypeFont(awtFont1, false);
		Font awtFont2 = new Font("Times New Roman", Font.PLAIN, 14);
		bodyFont = new TrueTypeFont(awtFont2, false);
		/*	
		// load font from a .ttf file
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("TIMCYR.TTF");
			
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			font2 = new TrueTypeFont(awtFont2, false);
				
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public static void main(String args[]){
		
		
		Race dwarf = new Race("Дварф", 5, 0, -3, 4);
		Hero you = new Hero("Макс", "./modules/TestModule/heros/hero.png", 1, 1, 5, 5, 5, 5, dwarf, 4, Profession.WARRIOR);
		
		
		
		DungeonGenerator generator = new DungeonGenerator(30, 30, 5, 5);
		Dungeon d = generator.generateDungeon();//new Dungeon("./modules/TestModule/locations/texture.json");

		you.setVisible(true);
		
				
		d.addThing(new Weapon("Morgenshtern", "./res/items/star.png", Type.ONE_HAND_SWORD, "Mace", new Dice(1, 6), 100, 10, 4, 4));
		
		d.addThing(new Weapon("Pipka", "./res/items/star.png", Type.ONE_HAND_SWORD, "Mace", new Dice(1, 8), 100, 10, 4, 5));

		TileRenderer r = new TileRenderer(d);
	
		KeyboardControl controller = new KeyboardControl();
		//r.controller.setDungeon(d);
		controller.setDungeon(d);
		d.addHero(you);
		controller.controlCreature(you);

		
		Thread keyboard = new Thread(controller);
		Thread renderer = new Thread(r);
		

		
		renderer.start();
		keyboard.start();


	}

	public Dungeon getcDungeon() {
		return cDungeon;
	}

	public void setcDungeon(Dungeon cDungeon) {
		this.cDungeon = cDungeon;
	}

	
	
	// Контейнер, содержащий выбранный предмет
	public class Checkbox {
		
		private AbstractThing checked; // Предмет
		private int checkedPos; // Его позиция в инвентаре (0 - N)
		
		private String mark;
		
		
		public Checkbox(String mark) {
			this.mark = mark;
		}
		
		
		
		public void renderFirst(){
			bodyFont.drawString(8, 50, mark);
		}
		
		public void render() {
			bodyFont.drawString(8, 5 + checkedPos, mark);
		}
		
		public void prev() {
			checkedPos++;
		}
	
		public void next() {
			checkedPos--;
		}
		
		public void mark(AbstractThing t) {
			checked = t;
		}
		
		public AbstractThing getThing() {
			return checked;
		}
	}
	


}