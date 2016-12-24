package mechanics.skills;

import entity.core.complex.ComplexUnit;
import mechanics.skills.core.Skill;

public class UseItem extends Skill{

	private String itemName;
	
	public UseItem(ComplexUnit caster, String itemName) {
		super(caster, 0, 0, 500, 0);
		this.itemName = itemName;
	}

	@Override
	public void use(ComplexUnit u) {
		caster.takeItem(itemName).use(u);
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
	
}
