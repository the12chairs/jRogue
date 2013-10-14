package ai;

import lifeforms.AbstractCreature;
import lowlevel.Dungeon;

public interface AI {
	public void lurk(AbstractCreature c); // Что делать просто так
	public void attack(AbstractCreature c, AbstractCreature victim); // При атаке
	public void setVisible(Dungeon d); // Видимый мобу участок карты
	public void setDivide(int x, int y); // Границы карты
}
