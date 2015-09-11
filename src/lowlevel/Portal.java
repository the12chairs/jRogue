package lowlevel;


// Портал - активная зона, через которую можно перемещаться между тайлами
public class Portal extends AbstractThing {

	
	private Dungeon prev;
	private Dungeon next;

	// Координаты, куда переносится прошедший
	private int startX;
	private int startY;

	// Портал: куда, откуда, и расположение 
	public Portal(Dungeon first, Dungeon last, long x, long y)
	{
		
		this.x = x;
		this.y = y;
		prev = first;
		next = last;
		allowed = false;
		startX = (int)x;
		startY = (int)y;
	}

	// Портал: куда, откуда, расположение и координаты выхода
	public Portal(Dungeon first, Dungeon last, long x, long y, int startX, int startY)
	{
		this.x = x;
		this.y = y;
		prev = first;
		next = last;
		allowed = false;
		this.startX = startX;
		this.startY = startY;
	}
	
	public Dungeon prev()
	{
		return prev;
	}
	
	public Dungeon next()
	{
		return next;
	}

	public void setStartX(int startX)
	{
		this.startX = startX;
	}

	public int getStartX()
	{
		return startX;
	}

	public void setStartY(int startY)
	{
		this.startY = startY;
	}

	public int getStartY()
	{
		return startY;
	}
}
