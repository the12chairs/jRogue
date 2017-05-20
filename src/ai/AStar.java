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

/**
 *  A standart AStar pathfinding algorythm implementation, used for AI classes
 */
public class AStar {

	private static ArrayList<Tile> open = new ArrayList<>();
	private static ArrayList<Tile> closed  = new ArrayList<>();
	private static long g; // Veigth
	private static long h; // path form start to finish
	private static long f; // g+h

	private static long manhattan(Tile s, Tile f){
		return (Math.abs(s.getX() - f.getX()) + Math.abs(s.getY() - f.getY()));
	}

    private static long chebishev(Tile s, Tile f){
        return Math.max(Math.abs(s.getX() - f.getX()), Math.abs(s.getY() - f.getY()));
    }

    private static void nein(Dungeon d, Tile center) {
		for(int i = (int)center.getY()-1; i <= center.getY()+1; i++){
			for(int j = (int)center.getX()-1; j<= center.getX()+1; j++){
				Tile t = null;

				t = d.getTile(j,  i);
				if(t != null){
                    if(!t.getPassable()) {
                        continue;
                    }
					if((t.getX() != center.getX()) || (t.getY() != center.getY())) { // Switch to OR
                        open.add(t);
					}
				}
			}
		}
	}

	public static ArrayList<Tile> search(Dungeon d, Tile start, Tile finish) {

        Tile actual =  null;
		open.clear();
		closed.clear();
		
		actual = start;
		open.add(actual);
		closed.add(actual);
		closed.remove(actual);
		while(true){
			nein(d, actual);
			long min = 100000;
			Tile mint = null;

			for(Tile t : open){

				if(!closed.contains(t)){
					// Diagonal
					g = 140;
					//Диагонали
					if((t.getX() > actual.getX()) && (t.getY() > actual.getY())){
						g = 140;
					} else
					if(t.getX() < actual.getX() && (t.getY() < actual.getY())){
						g = 140;
					} else
					if((t.getX() > actual.getX()) && (t.getY() < actual.getY())) {
						g = 140;
					} else
					if((t.getX() < actual.getX()) && (t.getY() > actual.getY())){
						g = 140;
					}

					h = manhattan(t, finish);
					f = g * h;

				}
				if(f < min) {
					min = f;
					mint = t;
				}
			}
			
			actual = mint;
			if(actual != null) {
				open.remove(actual);
			
				closed.add(actual);
			}

			if(actual == finish){
				break;
			}
		}
		return closed;
	}
}
