package primitives;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
	
	
	
	public void dropItem(Integer key){

		inventory.remove(key);
		itemNumber--;
	}
	
	
	// Более не работает, выкидываем элементы по ключу
	/*
	public void dropItem(AbstractThing item){
		for(AbstractThing i : this.inventory){
			if(i == item){
				inventory.remove(i); // ???
				itemNumber--;
			}
		}
	}
	*/
	public static void main(String[] args) {
		

	}

}
