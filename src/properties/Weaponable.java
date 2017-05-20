package properties;

import items.Weapon;

/**
 * Interface for implement for all inems that we can take in arms
 */
// TODO: do we really need it?
public interface Weaponable {
	public void useWeapon(Weapon w);
	public void unuseWeapon();
	public boolean isWeaponed();
}
