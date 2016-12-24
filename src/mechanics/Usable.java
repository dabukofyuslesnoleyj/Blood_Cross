package mechanics;

import entity.core.complex.ComplexUnit;

public interface Usable {
	
	public void use(ComplexUnit u);
	public int getCost();
	public int getEffectValue();
	public void setCost(int cost);
	public void setEffectValue(int effectValue);

}
