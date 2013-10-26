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

	private static ArrayList<Tile> open = new ArrayList<Tile>();
	private static ArrayList<Tile> closed  = new ArrayList<Tile>();
	private static long g; // Стоимость перемещения
	private static long h; // Расстояние от точки до конечной
	private static long f; // g+h

	
	private static long manhattan(Tile s, Tile f){
		return (Math.abs(s.getX() - f.getX()) + Math.abs(s.getY() - f.getY()));
	}
	

	
	
	private static void nein(Dungeon d, Tile center) {
		for(int i = (int)center.getY()-1; i <= center.getY()+1; i++){
			//if(i < 0 || i > d.getHeight()) continue;
			for(int j = (int)center.getX()-1; j<= center.getX()+1; j++){
				//if(j < 0 || j > d.getWidth()) continue;
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
	
	
	
	public static ArrayList<Tile> search(Dungeon d, Tile start, Tile finish) {
		
		boolean found = false;
		Tile actual =  null;
		open.clear();
		closed.clear();
		
		actual = start;
		open.add(actual);
		closed.add(actual);
		closed.remove(actual);
		while(!found){
			nein(d, actual);
			//closed.add(actual);
			//open.remove(actual);
			long min = 1000;
			//System.out.println(manhattan(start, finish));
			Tile mint = null;
			for(Tile t : open){
				//System.out.println(t.getX()+":"+t.getY());
				if(!closed.contains(t) && t.getPassable()){
					// Диагональ
					
					g = 10;
					//Диагонали
					if((t.getX() > actual.getX()) && (t.getY() > actual.getY())){
						g = 14;
					} else
					if(t.getX() < actual.getX() && (t.getY() < actual.getY())){
						g = 14;
					} else
					if((t.getX() > actual.getX()) && (t.getY() < actual.getY())) {
						g = 14;

					} else
					if((t.getX() < actual.getX()) && (t.getY() > actual.getY())){
						g = 14;
					}
					
					if(!t.getPassable()){
						g = 100000;
					}
					//System.out.println(t.getX()+":"+t.getY()+ "to "+finish.getX()+":"+finish.getY());
					h = manhattan(t, finish);
					f = g * h;
					//System.out.println("h = " + f + " "+g+" = "+ " "+ actual.getX()+"-"+t.getX()+":"+actual.getY()+ "-"+t.getY());
					
				}
				if(f < min) {
					min = f;
					mint = t;
				}
			}
			
			actual = mint;
			//System.out.println("Loop");
			//System.out.println(actual.getX() + ":" + actual.getY());
			if(actual != null) {
				open.remove(actual);
			
				closed.add(actual);
			}
			/*
			if(open.contains(finish)){
				found = true;
				break;
			}
			*/
			if(actual == finish){
				found = true;
				break;
			}
		}
		
		return closed;
		/*
		for(Tile t : closed){
			System.out.println(t.getX()+":"+t.getY());
		}
		*/
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
		//AStar star = new AStar();
		AStar.search(d, d.getTile(8, 8), d.getTile(3, 4));
	}
	
	
}
