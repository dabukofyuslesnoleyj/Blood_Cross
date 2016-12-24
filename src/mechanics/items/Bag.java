package mechanics.items;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import entity.core.complex.ComplexUnit;

public class Bag {

	private HashMap<String, Queue<Item>> itemMap;

	public Bag(){
		itemMap = new HashMap<String, Queue<Item>>();
	}
	
	public void addItem(String itemName, int amount){
		if(ItemSet.callInstance().isInSet(itemName)){
			if(!hasItem(itemName))
				itemMap.put(itemName, new LinkedList<Item>());
			for(int i = 0; i < amount; i++){
				Item item = ItemSet.callInstance().getAnItem(itemName);
				itemMap.get(itemName).offer(item);
			}
		}
	}
	
	public void useItem(String itemName, ComplexUnit u){
		Item item = removeItem(itemName);
		if(item != null)
			item.use(u);
	}
	
	public Item removeItem(String itemName){
		if(ItemSet.callInstance().isInSet(itemName)){
			if(hasItem(itemName))
				return itemMap.get(itemName).poll();						
		}
		return null;
	}
	
	public boolean hasItem(String itemName){
		if(itemMap.keySet().contains(itemName)){
			if(!itemMap.get(itemName).isEmpty())
				return true;
		}
		System.out.println("NO Item IN SET WITH NAME "+itemName);
		return false;
	}
}
