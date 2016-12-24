package mechanics.items;

import mechanics.Usable;

public abstract class Item implements Usable{

	private int cost;
	private int effectValue;
	
	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return cost;
	}

	@Override
	public int getEffectValue() {
		// TODO Auto-generated method stub
		return effectValue;
	}

	@Override
	public void setCost(int cost) {
		this.cost = cost;
		
	}

	@Override
	public void setEffectValue(int effectValue) {
		this.effectValue = effectValue;
		
	}
	
	public Item cloneItem(){
		try {
			return (Item)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
