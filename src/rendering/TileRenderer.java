package rendering;

import ai.AgressiveAI;
import ai.PassiveAI;
import items.Armor;
import items.Weapon;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import lifeforms.AbstractCreature;
import lifeforms.AbstractCreature.Profession;
import lifeforms.Hero;
import lifeforms.Mob;
import lowlevel.AbstractThing;
import lowlevel.Dungeon;
import lowlevel.KeyboardControl;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.PathType;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import dnd.Dice;
import org.newdawn.slick.opengl.Texture;
import primitives.GraphObject;
import properties.Race;
import properties.Material;

/**
 * Graphic renderer
 */
public class TileRenderer extends Thread {


	public enum State { MAIN_MENU, DUNGEON, INVENTORY,
						DROP_ITEM, WEAR_ARMOR, TAKE_WEAPON,
						MAGIC, ALCHEMY, RUNES, CRAFT, DEATH }
	
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	public static final int TILE_SIZE = 16;
	
	private long lastFrame;
	private long lastFPS;
	private int fps;

	private TrueTypeFont headFont;
	private TrueTypeFont bodyFont;
	
	public static State gameState;

	private static Dungeon cDungeon;

	public static Camera camera;

	public static Checkbox check;

	public static Map<String, Texture> textures; // Map of loaded textures

	public TileRenderer(Dungeon cDungeon)
	{
		TileRenderer.cDungeon = cDungeon;
		gameState = State.DUNGEON;
		check = new Checkbox(">");
		textures = new HashMap<>();
	}

	public static void storeTexture(String key, Texture value)
	{
		textures.put(key, value);
	}

	public static Texture getTexture(String path)
	{
		return textures.get(path);
	}

	public static boolean textureExists(String path)
	{
		if(textures.get(path) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Render inventory
	 */
	public void renderInventory()
	{
		headFont.drawString(WIDTH / 2 + 40, 20, "Inventory");
		
		int w = 50;
		
		Map<Integer, AbstractThing> buffer = new HashMap<Integer,AbstractThing>(cDungeon.getHero().inventory().allInvenory());
		
		for (Entry<Integer, AbstractThing> entry : buffer.entrySet()) {
			int h = 20;

			String actionString = null;
			if(!entry.getValue().isEquipped()) {
				actionString = "[e]quip";
			} else {
				actionString = "un[e]quip";
			}

			bodyFont.drawString(h, w, entry.getKey().toString() + " " + entry.getValue().getName() + " " + actionString + "   |   " + entry.getValue().getMaterial().getName(), Color.white);
			w += 12;

		}
		check.render();
	}

	public Checkbox getCheckbox(){
		return check;
	}

	public void renderMainMenu(){
		// TODO: make it
	}

	public void renderInfo(){
		
		int h = 20;

		int w = 500;

		bodyFont.drawString(h, w, cDungeon.getHero().getName() + ", the " + cDungeon.getHero().getRace().getName());

		w = w + 50;
		
		if(cDungeon.getHero().getRightHand() != null){
			bodyFont.drawString(h, w, "Equipped: " + cDungeon.getHero().getRightHand().getName() + " " +
					cDungeon.getHero().getRightHand().getDamage().getPair() + " +" + cDungeon.getHero().getRightHand().getBonus());
		}
		else{
			bodyFont.drawString(h, w, "Equipped: nothing" + " " + cDungeon.getHero().getDamage().getPair());
		}

		w = w + 35;

		bodyFont.drawString(h, w, "STR: " + cDungeon.getHero().str().getCurrent() + " DEX: " +
				cDungeon.getHero().dex().getCurrent() + " INT: " + cDungeon.getHero().intel().getCurrent() + " STA: "
				+ cDungeon.getHero().stam().getCurrent() + " WIS: " + cDungeon.getHero().wis().getCurrent() + " CHA: "
				+ cDungeon.getHero().cha().getCurrent() + " HP: " + cDungeon.getHero().hp().getPair());
	}

	public void renderDungeon(){

		GraphObject tmp = cDungeon.dungeon().get(0);

		for(GraphObject tile : cDungeon.dungeon()){
			String face = tile.getFace();
			if(tile.getVisible()){
				getTexture(face).bind();
				renderTile(tile);
			}
			if(!tmp.getFace().equals(tile.getFace())){
				tmp = tile;
				getTexture(face).bind();
			}
		}
		if (cDungeon.getItems() != null)
			for(AbstractThing item : cDungeon.getItems()){
				String face = item.getFace();
				if(item.getVisible()){
					getTexture(face).bind();
					renderTile(item);
				}
			}
		for(AbstractCreature creature : cDungeon.getCreatures()){

			// Trace path for debug
			if(creature.getAi() != null) {
				for (GraphObject t : creature.getAi().getPath()) {
					bodyFont.drawString(t.getX() * TILE_SIZE, t.getY() * TILE_SIZE, "*");
				}
			}
			if(creature.getVisible() && creature.isAlive()){
				String face = creature.getFace();
				getTexture(face).bind();
				renderTile(creature);
			}
		}
		renderInfo();
	}

	public static void loadTextures(){
		// TODO: handle situation when no elements
		// Load unic textures into hash
		System.out.println("Loading textures...");
		for(GraphObject tile : cDungeon.dungeon()){
			if(!textureExists(tile.getFace())) {
				textures.put(tile.getFace(), tile.loadTexture());
			}
		}
		if(cDungeon.getItems() != null) {
			for (AbstractThing item : cDungeon.getItems()) {
				if (!textureExists(item.getFace())) {
					textures.put(item.getFace(), item.loadTexture());
				}
			}
		}
		for(GraphObject creature : cDungeon.getCreatures()) {
			if(!textureExists(creature.getFace())) {
				textures.put(creature.getFace(), creature.loadTexture());
			}
		}

		// All items in all inventories
		for(AbstractCreature c : cDungeon.getCreatures()) {
			for(Entry<Integer, AbstractThing> t : c.inventory().allInvenory().entrySet()) {
				if(!textureExists(t.getValue().getFace())) {
					textures.put(t.getValue().getFace(), t.getValue().loadTexture());
				}
			}
		}
		System.out.println("Done");
	}
	
	public void destroyTextures() {
		System.out.println("Starting textures destroying...");
		for(GraphObject tile : cDungeon.dungeon()){
			if(textureExists(tile.getFace())) {
				tile.destroyTexture();
				textures.remove(tile.getFace());
			}
		}
		if (cDungeon.getItems() != null) {
			for (AbstractThing item : cDungeon.getItems()) {
				if (textureExists(item.getFace())) {
					item.destroyTexture();
					textures.remove(item.getFace());
				}
			}
		}
		for(GraphObject creature : cDungeon.getCreatures()) {
			if(textureExists(creature.getFace())) {
				creature.destroyTexture();
				textures.remove(creature.getFace());
			}
		}
		for(AbstractCreature c : cDungeon.getCreatures()) {
			for(Entry<Integer, AbstractThing> t : c.inventory().allInvenory().entrySet()) {
				if(textureExists(t.getValue().getFace())) {
					t.getValue().destroyTexture();
					textures.remove(t.getValue().getFace());
				}
			}
		}
		System.out.println("Done");
	}
	
	// Render a single tile
	public void renderTile(GraphObject tile){
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(tile.getX() * TILE_SIZE + TILE_SIZE, tile.getY() * TILE_SIZE);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(tile.getX() * TILE_SIZE + TILE_SIZE, tile.getY() * TILE_SIZE + TILE_SIZE);
		GL11.glTexCoord2f(0, 1);
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

	public void renderDeath(){
		headFont.drawString(WIDTH / 2, HEIGHT / 2, "You're dead!");
	}
	
	 public void renderState(){
		 // Renderer state
		 switch(gameState){
		 case INVENTORY:
			 renderInventory();
			 break;
		 case DUNGEON:
			 renderDungeon();
			 break;
		 case DEATH:
			 renderDeath();
			 break;
		 default:
			 break;
		 }
	 }

	public synchronized void render() {
		initGL(WIDTH, HEIGHT);
		loadTextures();
		Dungeon prev = cDungeon;
		while (true) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			if(cDungeon != prev){
				destroyTextures();
				prev = cDungeon;
				loadTextures();
			}
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
	}
	
	public static Dungeon getDungeon() {
		return cDungeon;
	}
	
	public static void setDungeon(Dungeon d) {
		cDungeon = d;
		gameState = State.DUNGEON;
	}

	public static void main(String args[]){
		
		ScriptingContainer ruby = new ScriptingContainer(LocalVariableBehavior.PERSISTENT);
		
		ruby.runScriptlet(PathType.ABSOLUTE, "./scripts/races.rb");
		Race test_race = (Race) ruby.get("race");
		Race gobo = (Race) ruby.get("gobo");
		Hero you = new Hero("Maga", "./res/mobs/human.png", 10, 10, test_race, 4, Profession.WARRIOR);
		
		ruby.runScriptlet(PathType.ABSOLUTE, "./scripts/basic_forest.rb");

		Dungeon d = (Dungeon) ruby.get("forest");

		Random rnd = new Random();

		for(int i = 1; i < 2; i++){
			d.addLife(new Mob("Grusk'ar #", "./res/mobs/gobbo.png", rnd.nextInt(10), rnd.nextInt(10), gobo, 4, true));
		}

		for(AbstractCreature c : d.getCreatures()){
			if(c != you)
				c.setAI(new PassiveAI());
		}

		you.setVisible(true);

		d.addThing(new Weapon("Morgenshtern", "./res/items/sword.png", Weapon.Type.MACE, new Material("Iron"), true, new Dice(1, 6), 100, 10), 10, 10);

		TileRenderer r = new TileRenderer(d);
	
		KeyboardControl controller = new KeyboardControl();

		d.addHero(you);
		you.setVisible(true);

		Thread keyboard = new Thread(controller);
		Thread renderer = new Thread(r);
		
		renderer.start();
		keyboard.start();
		// Center on hero
		camera = new Camera((int)you.getY()+6, (int)you.getX()+6);

		controller.controlCreature(you);
	}

	// Container with selected item (in inventory)
	public class Checkbox {
		
		private AbstractThing checked; // Item
		private int checkedPos; // Item position (0 - N)
		private int renderPos; // Renderer coordinates
		private String mark;
		
		public Checkbox(String mark) {
			this.mark = mark;
			checkedPos = renderPos = 0;
		}

		public boolean biggerNull(){
			return (checkedPos > 0);
		}

		public int getPos(){
			return checkedPos;
		}
		
		public void render() {
			bodyFont.drawString(8, 50 + renderPos, mark);
		}
		
		public void prev() {
			checkedPos--;
			renderPos = checkedPos * 12;
		}
	
		public void next() {
			checkedPos++;
			renderPos = checkedPos * 12;
		}
		
		public void mark(AbstractThing t) {
			checked = t;
		}
		
		public AbstractThing getThing() {
			return checked;
		}
	}
}