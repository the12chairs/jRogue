package lowlevel;


// Портал - активная зона, через которую можно перемещаться между тайлами
public class Portal extends AbstractThing {

	
	private Dungeon prev;
	private Dungeon next;
	
	// Портал: куда, откуда, и расположение 
	public Portal(Dungeon first, Dungeon last, long x, long y) {
		
		this.x = x;
		this.y = y;
		prev = first;
		next = last;
		allowed = false;
	}
	
	public Dungeon prev(){
		return prev;
	}
	
	public Dungeon next(){
		return next;
	}

}
