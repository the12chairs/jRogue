package ai;

import java.util.List;
import java.util.ArrayList;

import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

import properties.Race;
import tools.DungeonGenerator;
import lifeforms.Hero;
import lifeforms.Mob;
import lifeforms.AbstractCreature.Profession;
import lowlevel.Dungeon;
import lowlevel.Tile;

public class AStar {
	private Tile start;
	private Tile finish;
	private List<Tile> path;
	private List<Tile> open;
	private List<Tile> closed;
	private long g; // Стоимость перемещения
	private long h; // Расстояние от точки до конечной
	private long f; // g+h
	
	public AStar(Tile start, Tile finish){
		open = new ArrayList<Tile>();
		closed = new ArrayList<Tile>();
		this.start = start;
		this.finish = finish;
		// Манхэттеновское расстояние
	}
	
	
	
	public long manhattan(Tile s, Tile f){
		return (10 * (Math.abs(s.getX() - f.getX()) + Math.abs(s.getY() - f.getY())));
	}
	

	
	
	public void nein(Dungeon d, Tile center) {
		for(int i = (int)center.getY()-1; i <= center.getY()+1; i++){
			if(i < 0 || i > d.getHeight()) continue;
			for(int j = (int)center.getX()-1; j<= center.getX()+1; j++){
				if(j < 0 || j > d.getWidth()) continue;
				Tile t = null;
				/*if(((i > 0) && (j > 0)) || i < d.getHeight() && j < d.getWidth()){
					
				}
				else {
					continue;
				}
				*/
				t = d.getTile(j,  i);
				if( t != null){
					//System.out.println((center.getX()+1)+":"+(center.getY()+1));
					if((t.getX() != center.getX()) || (t.getY() != center.getY())){
						open.add(t);
					}
				}
				//System.out.println(t.getX()+":"+t.getY());
			}
		}
	}
	
	
	
	public void search(Dungeon d) {
		
		boolean found = false;
		Tile actual =  null;
		

		
		actual = start;
		open.add(actual);
		closed.add(actual);
		while(!found){
			nein(d, actual);
			//closed.add(actual);
			//open.remove(actual);
			int g = 0;
			long min = 1000;
			//System.out.println(manhattan(start, finish));
			Tile mint = null;
			for(Tile t : open){
				//System.out.println(t.getX()+":"+t.getY());
				if(t.getPassable()  && !closed.contains(t)){
					// Диагональ
					
					g = 10;
					//Диагонали
					if((t.getX() > actual.getX()) && (t.getY() > actual.getY())){
						g = 14;
					}
					if(t.getX() < actual.getX() && (t.getY() < actual.getY())){
						g = 14;
					}
					if((t.getX() > actual.getX()) && (t.getY() < actual.getY())) {
						g = 14;

					}
					if((t.getX() < actual.getX()) && (t.getY() > actual.getY())){
						g = 14;
					}
					//System.out.println(t.getX()+":"+t.getY()+ "to "+finish.getX()+":"+finish.getY());
					h = manhattan(t, finish);
					f = g * h;
					//System.out.println("h = " + f + " "+g+" = "+ " "+ actual.getX()+"-"+t.getX()+":"+actual.getY()+ "-"+t.getY());
					if(f < min) {
						min = f;
						mint = t;
					}
					
				}
			}
			
			actual = mint;
			System.out.println("Loop");
			//System.out.println(actual.getX() + ":" + actual.getY());
			open.remove(actual);
			closed.add(actual);
			if(open.contains(finish)){
				found = true;
				break;
			}
			if(actual == finish){
				found = true;
				break;
			}
		}
		
		
		for(Tile t : closed){
			System.out.println(t.getX()+":"+t.getY());
		}
		
	}
	
	
	
	public static void main(String args[]){
ScriptingContainer ruby = new ScriptingContainer(LocalVariableBehavior.PERSISTENT);
		
		ruby.runScriptlet(PathType.ABSOLUTE, "./scripts/races.rb");
		Race test_race = (Race) ruby.get("race");
		Race gobo = (Race) ruby.get("gobo");
		//Dice 
		//Race dwarf = new Race("Dwarf", 5, 0, -3, -1, -1, 4);
		Hero you = new Hero("Urist", "./modules/TestModule/heros/hero.png", 3, 3, test_race, 4, Profession.WARRIOR);
		Mob enemy = new Mob("Urist", "./modules/TestModule/heros/hero.png", 5, 5, gobo, 4, true);
		
		ruby.runScriptlet(PathType.ABSOLUTE, "./scripts/basic_forest.rb");
		DungeonGenerator generator = new DungeonGenerator(30, 31, 5, 5);
		//Dungeon d = generator.generateDungeon();//new Dungeon("./modules/TestModule/locations/texture.json");
		Dungeon d = (Dungeon) ruby.get("forest");
		AStar star = new AStar(d.getTile(2, 2), d.getTile(7, 7));
		star.search(d);
	}
	
	
}
