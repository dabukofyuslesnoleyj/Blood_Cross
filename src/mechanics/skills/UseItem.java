package mechanics.skills;

import entity.core.complex.ComplexUnit;
import mechanics.skills.core.Skill;
import mechanics.skills.core.SkillSet;

public class UseItem extends Skill{

	private String itemName;
	
	public UseItem(String itemName) {
		super( 0, 0, 500, 0);
		this.itemName = itemName;		
	}

	@Override
	public void use(ComplexUnit u) {
		u.takeItem(itemName).use(u);
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
	
}
