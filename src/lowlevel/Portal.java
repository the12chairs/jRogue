package lowlevel;


/**
 * Portal is a mechanic which allows you to move between dungeons. Stay on portal and press [G]o
 */
public class Portal extends AbstractThing {

	private Dungeon prev;
	private Dungeon next;

	// Coordinates of a portal
	private int startX;
	private int startY;

	// start dung, finish dung and coords
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

	// start dung, finish dung, coords and coords for exit
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
