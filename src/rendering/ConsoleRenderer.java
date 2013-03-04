package rendering;

import lifeforms.AbstractCreature;
import lifeforms.ActiveTile;
import lowlevel.AbstractThing;
import lowlevel.Tile;
import sz.csi.ConsoleSystemInterface;
import sz.csi.wswing.WSwingConsoleInterface;

public class ConsoleRenderer {
	
	ConsoleSystemInterface csi;
	
	public ConsoleRenderer(){
		this.csi = new WSwingConsoleInterface();
		csi.cls();
	}
	
	public void renderTile(Tile t){
		csi.print((int)t.getY(), (int)t.getX(), t.getTile(), ConsoleSystemInterface.CYAN);
	}
	
	public void renderCreature(AbstractCreature c){
		csi.print((int)c.getY(), (int)c.getX(), c.getFace(), ConsoleSystemInterface.CYAN);
	}
	
	public void renderThing(AbstractThing t){
		csi.print((int)t.getY(), (int)t.getX(), t.getFace(), ConsoleSystemInterface.CYAN);
	}
	public static void main(String args[]){
		ActiveTile t = new ActiveTile("JJ",'X', 'O', true, true, 0, 0);
		ConsoleRenderer r = new ConsoleRenderer();
		r.csi.locateCaret((int)t.getX(), (int)t.getY());
		t.activate();
		r.renderTile(t);
		/*Dungeon d = new Dungeon("./modules/TestModule/locations/generated.json");
		//d.oldDungeon("./modules/TestModule/locations/test.old.json");
		ConsoleSystemInterface csi = new WSwingConsoleInterface();
		csi.cls();
		for(Tile t : d.dungeon()){
			csi.print((int)t.getY(), (int)t.getX(), t.getTile(), ConsoleSystemInterface.CYAN);
		}*/
		
	}

}
