package roguelike;

public interface CreatureController {
	
	void move(int dx, int dy);
	void setPos(int x, int y);
	int getX();
	int getY();
	String getName();
	void setName(String name);
	char getFace();
	void setFace(char face);
	String getProfession();
	void initRaceBonuses();
	void takeItem(AbstractThing item);
	void dropItem(AbstractThing item);
	void setPurse(long gold);
	long getPurse();
	
}
