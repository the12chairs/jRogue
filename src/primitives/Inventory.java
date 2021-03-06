package primitives;

import items.Armor;
import items.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lowlevel.AbstractThing;

// Inventory and all it can do
public class Inventory {

	int itemNumber;

	private Map<Integer, AbstractThing> inventory;

	public Inventory(){
		inventory = new HashMap<Integer, AbstractThing>();
		itemNumber = 0;
	}

	public Map<Integer, AbstractThing> allInvenory(){
		return inventory;
	}

	public Map<Integer, Weapon> getAllWeapon(){
		Map<Integer, Weapon> weapons = new HashMap<Integer, Weapon>();
		
		for (Entry<Integer, AbstractThing> entry : inventory.entrySet()){
			if(entry.getValue().getMType() == AbstractThing.MainType.WEAPON){
				weapons.put(entry.getKey(), (Weapon) entry.getValue());
			}
		}
		return weapons;
	}

	public Map<Integer, Armor> getAllArmor(){
		Map<Integer, Armor> armors = new HashMap<Integer, Armor>();
	
		for (Entry<Integer, AbstractThing> entry : inventory.entrySet()){
			if(entry.getValue().getMType() == AbstractThing.MainType.ARMOR){
				armors.put(entry.getKey(), (Armor) entry.getValue());
			}
		}
		return armors;
	}

	public AbstractThing findByName(String name){
		for(AbstractThing i : this.inventory.values()){
			if(i.getName() == name){
				return i;
			}
		}
		return null;
	}
	
	public AbstractThing findByKey(Integer key){
		return inventory.get(key);
	}

	public void pushItem(AbstractThing item){
		
		this.inventory.put(itemNumber,item);
		itemNumber++;
	}
	
	// Remove by key
	public void dropItem(Integer key){

		inventory.remove(key);
		itemNumber--;

		// Black magic

		// Save rest of the objects in inventory
		List<AbstractThing> buffer = new ArrayList<AbstractThing>();
		for(Entry<Integer, AbstractThing> t: inventory.entrySet()){
			buffer.add(t.getValue());
		}

		// Clear inventory
		inventory.clear();
		
		int i = 0;
		// Push saved items form buffer to inventory
		for(AbstractThing t : buffer){
			inventory.put(i, t);
			i++;
		}
		
	}
	
	// Remove by value
	public void dropItem(AbstractThing item){
		
		for(Entry<Integer, AbstractThing> t : inventory.entrySet()){
		
			if(t == item){
				inventory.remove(t.getKey());
				itemNumber--;
				System.out.println("Dropped");
			}
		}
		
		// Save rest of the objects in inventory
		List<AbstractThing> buffer = new ArrayList<AbstractThing>();
		for(Entry<Integer, AbstractThing> t: inventory.entrySet()){
			buffer.add(t.getValue());
		}	
		
		// Clear inventory
		inventory.clear();
		
		int i = 0;
		// Push saved items form buffer to inventory
		for(AbstractThing t : buffer){
			inventory.put(i, t);
			i++;
		}
	}
}
