package entity.core.complex.loot;

import mechanics.Usable;

public interface Loot {

	public void setValue(double value);
	
	public double getValue();
	
	public Usable[] getUsable();
	
	public void setUsable(Usable[] u);
}
