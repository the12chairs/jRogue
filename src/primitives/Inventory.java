package primitives;

import java.util.LinkedList;
import java.util.List;

import lowlevel.AbstractThing;

public class Inventory {
	
	int itemNumber;
	
	private List<AbstractThing> inventory;
	
	
	public Inventory(){
		
		inventory = new LinkedList<AbstractThing>();
		itemNumber = 0;
	}
	
	
	
	
	
	public List<AbstractThing> allInvenory(){
		return inventory;
	}
	
	
	public AbstractThing findByName(String name){
		for(AbstractThing i : this.inventory){
			if(i.getName() == name){
				return i;
			}
		}
		return null;
	}
	
	public void pushItem(AbstractThing item){
		this.inventory.add(item);
		itemNumber++;
	}
	
	public void dropItem(AbstractThing item){
		for(AbstractThing i : this.inventory){
			if(i == item){
				inventory.remove(i); // ???
				itemNumber--;
			}
		}
	}
	
	public static void main(String[] args) {
		

	}

}
