package mechanics.skills.core;

public class SkillMap {
	
	private Skill skill;
	private boolean using;
	
	public SkillMap(Skill skill, boolean using) {
		this.skill = skill;
		this.using = using;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public boolean isUsing() {
		return using;
	}

	public void setUsing(boolean using) {
		this.using = using;
	}

}
