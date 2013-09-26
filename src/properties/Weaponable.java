package properties;

import items.Weapon;

public interface Weaponable {
	public void useWeapon(Weapon w);
	public void unuseWeapon();
	public boolean isWeaponed();
}
