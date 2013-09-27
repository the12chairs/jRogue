package primitives;

import items.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lowlevel.AbstractThing;

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
				//System.out.println(entry.getValue().getName());
			}
		}

		return weapons;
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
	
	
	// Удаление по ключу
	public void dropItem(Integer key){

		inventory.remove(key);
		itemNumber--;
		
		
		
		// Костыльное царство // Зато работает
		
		// Сохраняем оставшиеся предметы в инвентаре
		List<AbstractThing> buffer = new ArrayList<AbstractThing>();
		for(Entry<Integer, AbstractThing> t: inventory.entrySet()){
			buffer.add(t.getValue());
		}	
		
		// Зачищаем инвентарь
		inventory.clear();
		
		int i = 0;
		// Запихиваем предметы из буфера обратно в инвентарь
		for(AbstractThing t : buffer){
			inventory.put(i, t);
			i++;
		}
		
	}
	
	// Удаление по значению
	public void dropItem(AbstractThing item){
		
		
		for(Entry<Integer, AbstractThing> t: inventory.entrySet()){
		
			if(t == item){
				inventory.remove(t.getKey());
				itemNumber--;
			}
		}
		
		// Сохраняем оставшиеся предметы в инвентаре
		List<AbstractThing> buffer = new ArrayList<AbstractThing>();
		for(Entry<Integer, AbstractThing> t: inventory.entrySet()){
			buffer.add(t.getValue());
		}	
		
		// Зачищаем инвентарь
		inventory.clear();
		
		int i = 0;
		// Запихиваем предметы из буфера обратно в инвентарь
		for(AbstractThing t : buffer){
			inventory.put(i, t);
			i++;
		}
			
	}


}
