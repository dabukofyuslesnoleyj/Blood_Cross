package mechanics.items;

import java.util.HashMap;


public final class ItemSet {

	private HashMap<String, Item> itemMap;
	private static final ItemSet INSTANCE = new ItemSet();	
	
	private ItemSet(){
		itemMap = new HashMap<String, Item>();
	}
	
	public static ItemSet callInstance(){
		return INSTANCE;
	}
	
	public void addItem(String itemName, Item Item){
		itemMap.put(itemName, Item);
	}
	
	public boolean isInSet(String itemName){
		if(itemMap.keySet().contains(itemName))
			return true;
		System.out.println("NO Item IN SET WITH NAME "+itemName);
		return false;
	}
	
	public Item getAnItem(String itemName){
		if(isInSet(itemName))
			return itemMap.get(itemName).cloneItem();
		return null;
	}
	
}
