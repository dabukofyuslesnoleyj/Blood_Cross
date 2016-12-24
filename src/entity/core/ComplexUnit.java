package entity.core;

import java.util.ArrayList;
import java.util.HashMap;

import mechanics.items.Bag;
import mechanics.items.Item;
import mechanics.items.ItemSet;
import mechanics.skills.core.Skill;
import mechanics.skills.core.SkillSet;
import tile_map.TileMap;

public abstract class ComplexUnit extends Unit{

	//stat constraints
	public static final double MAX_STAT = 99;
	public static final double MIN_STAT= 1;
	
	//unit stats
	private double endurance;
	private double strength;
	private double dexterity;
	private double intelligence;
	private double vitality;
	private double agility;

	private int maxStamina;
	private int stamina;
	
	private double attackSpeed;
	
	private double magicArmor;
	private double physicalArmor;
	private double magicDamage;
	private double physicalDamage;
	
	//skill map
	private HashMap<String, Skill> skillMap;
	
	//item bag
	private Bag bag;
	
	//constructor
	public ComplexUnit(TileMap tm, double endurance, double strength, 
			double dexterity, double intelligence, double vitality, double agility) {
		super(tm, 0, 0);
		this.init(endurance, strength, dexterity, intelligence, vitality, agility);
	}
	
	//methods
	public void init(double endurance, double strength, 
			double dexterity, double intelligence, double vitality, double agility){
		
		setEndurance(endurance);
		setStrength(strength);
		setDexterity(dexterity);
		setIntelligence(intelligence);
		setVitality(vitality);
		setAgility(agility);
		skillMap = new HashMap<String, Skill>();
		bag = new Bag();
		
	}
	
	//item methods
	public void addItem(String itemName, int amount){
		bag.addItem(itemName, amount);
	}
	
	public void removeItem(String itemName, int amount){
		for(int i = 0; i < amount; i++)
			bag.removeItem(itemName);
	}
	
	public Item takeItem(String itemName){
		return bag.removeItem(itemName);
	}
	
	//skill methods
	public void addSkillFromSet(String skillName){
		if(SkillSet.callInstance().isInSet(skillName)){
			Skill s = SkillSet.callInstance().getSkill(skillName);
			this.skillMap.put(skillName, s);
		}
	}
	
	public boolean isInSkills(String skillName){
		if(skillMap.keySet().contains(skillName))
			return true;
		System.out.println("NO SKILL WITH NAME "+skillName);
		return false;
	}
	
	public void castSkill(String skillName, ComplexUnit u){
		if(isInSkills(skillName))
			skillMap.get(skillName).cast(u);
	}
	
	public void removeSkill(String skillName){
		if(isInSkills(skillName))
			skillMap.remove(skillName);
	}
	
	private double checkStatConstraint(double stat){
		if(stat > ComplexUnit.MAX_STAT)
			stat = ComplexUnit.MAX_STAT;
		else if(stat < ComplexUnit.MIN_STAT)
			stat = ComplexUnit.MIN_STAT;
		this.setPhysicalArmor();
		this.setPhysicalDamage();
		this.setMagicArmor();
		this.setMagicDamage();
		
		return stat;
	}
	

	//stat methods
	public double getEndurance() {
		return endurance;
	}

	public void setEndurance(double endurance) {
		this.endurance = this.checkStatConstraint(endurance);
		this.setPhysicalArmor();
		this.setMagicArmor();
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = this.checkStatConstraint(strength);
	}

	public double getDexterity() {
		return dexterity;
	}

	public void setDexterity(double dexterity) {
		this.dexterity = this.checkStatConstraint(dexterity);
	}

	public double getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(double intelligence) {
		this.intelligence = this.checkStatConstraint(intelligence);
	}

	public double getVitality() {
		return vitality;
	}

	public void setVitality(double vitality) {
		this.vitality = this.checkStatConstraint(vitality);
	}

	public double getAgility() {
		return agility;
	}

	public void setAgility(double agility) {
		this.agility = this.checkStatConstraint(agility);
	}
	

	public void setPhysicalDamage(){
		double strBonus = (strength*4);
		double dexBonus = (dexterity*1.5);
		this.physicalDamage = strBonus+dexBonus;
	}
	
	public void setPhysicalArmor(){
		this.physicalArmor = (endurance+vitality*0.5+strength*0.5);
	}
	
	public void setMagicDamage(){
		this.magicDamage = (intelligence*6);
	}
	
	public void setMagicArmor(){
		this.magicArmor = (endurance*0.5+intelligence*1.5);
	}

	public void setMaxStamina() {
		this.maxStamina = (int)(endurance*7);
	}
	
	public void setMaxEnergy(){
		this.maxEnergy = (int)(intelligence*5);
	}
	
	public void setMaxHealth(){
		this.maxHealth = (int)(vitality*6);
	}
	
	public void setMoveSpeed(){
		this.maxSpeed = (agility*0.3);
	}
	
	public void setAttackSpeed(){
		this.attackSpeed = (agility*0.1 + dexterity*0.1);
	}

	public int getMaxStamina() {
		return maxStamina;
	}
	
	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public double getPhysicalArmor() {
		return physicalArmor;
	}

	public double getPhysicalDamage() {
		return physicalDamage;
	}

	public double getAttackSpeed() {
		return attackSpeed;
	}

	public double getMagicArmor() {
		return magicArmor;
	}

	public double getMagicDamage() {
		return magicDamage;
	}
	
}
