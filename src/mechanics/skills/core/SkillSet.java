package mechanics.skills.core;

import java.util.HashMap;

public final class SkillSet {

	private HashMap<String, Skill> skillMap;
	private static final SkillSet INSTANCE = new SkillSet();	
	
	private SkillSet(){
		skillMap = new HashMap<String, Skill>();
	}
	
	public static SkillSet callInstance(){
		return INSTANCE;
	}
	
	public void addSkill(String skillName, Skill skill){
		skillMap.put(skillName, skill);
	}
	
	public boolean isInSet(String skillName){
		if(skillMap.keySet().contains(skillName))
			return true;
		System.out.println("NO SKILL IN SET WITH NAME "+skillName);
		return false;
	}
	
	public Skill getSkill(String skillName){
		if(isInSet(skillName))
			return skillMap.get(skillName);
		return null;
	}
}
