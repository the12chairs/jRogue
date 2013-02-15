package roguelike;

public interface ThingController {
	void setName(String name);
	String getName();
	void setFace(char face);
	char getFace();
	void setPos(int x, int y);
	void setHeavy(int heavy);
	void setVisible(boolean visible);
	int getY();
	int getX();
	int getHeavy();
	boolean getVisible();
	void setCost(int cost);
	int getCost();
	
}
