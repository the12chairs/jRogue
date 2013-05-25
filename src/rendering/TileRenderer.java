package rendering;

import java.io.IOException;

import lifeforms.Hero;
import lifeforms.AbstractCreature.Profession;
import lowlevel.Dungeon;
import lowlevel.KeyboardControl;


import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import primitives.GraphObject;
import properties.Race;
import tools.DungeonGenerator;

public class TileRenderer extends Thread {

	
	
	long lastFrame;
	long lastFPS;
	int fps;
	
	private static final int HEIGHT = 800;
	private static final int WIDTH = 600;
	private static final int TILE_SIZE = 16;

	
	public KeyboardControl controller = new KeyboardControl();
	
	
	private Dungeon cDungeon;
	
	
	public TileRenderer(Dungeon cDungeon){
		this.cDungeon = cDungeon;
		//initGL(HEIGHT,WIDTH);
	}
	
	
	
	
	public KeyboardControl getController(){
		return controller;
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
	
	// Рисуем тайл
	public void renderDungeon(){

		GraphObject tmp = cDungeon.dungeon().get(0);
		loadTexture(tmp.getFace()).bind();

		for(GraphObject tile : cDungeon.dungeon()){
			if(tile.getVisible() == true){
				renderTile(tile);
				//if(tmp.getFace() == tile.getFace)
				if(!tmp.getFace().equals(tile.getFace())){
					//System.out.println(tile.getFace());
					tmp = tile;
					loadTexture(tmp.getFace()).bind();

				}
			}
			//updateFPS();
		}
		
		
		
		//System.out.println(cDungeon.getCreatures().size());

		
		for(GraphObject creature : cDungeon.getCreatures()){
			loadTexture(creature.getFace()).bind();
			renderTile(creature);
		}


		//System.out.println("Загрузок текстур: " + loads + ", против " + oldLoads + " загрузок ранее.");
	}
	

	
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
	
	public void logic(){
		controller.commandAction();
	}
	
	public void render(){
		initGL(HEIGHT, WIDTH);

		while (true) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			//--------------			
			renderDungeon();
			logic();
			//--------------
			Display.update();
			Display.sync(100);

		if (Display.isCloseRequested()) {
			Display.destroy();
			System.exit(0);
			}
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
	}
	
	
	
	public static void main(String args[]){
		
		
		Race dwarf = new Race("Дварф", 5, 0, -3, 4);
		Hero you = new Hero("Макс", "./modules/TestModule/heros/hero.png", 1, 1, 5, 5, 5, 5, dwarf, Profession.WARRIOR);
		
		
		
		DungeonGenerator generator = new DungeonGenerator(30, 30, 5, 5);
		Dungeon d = generator.generateDungeon();//new Dungeon("./modules/TestModule/locations/texture.json");
		
		
		System.out.println(d.getHeight());
		//System.out.println(you.getFace());

		d.addHero(you);
				
		
		
		TileRenderer r = new TileRenderer(d);
	
		r.controller.controlCreature(you);
		r.controller.setDungeon(d);
		
		r.render();
		
		
		//Thread keyboard = new Thread(r.getController());
		//Thread renderer = new Thread(r);
		
		//r.getController().controlCreature(you);
		
		//renderer.start();
		//keyboard.start();
		

	}

	public Dungeon getcDungeon() {
		return cDungeon;
	}

	public void setcDungeon(Dungeon cDungeon) {
		this.cDungeon = cDungeon;
	}


}
