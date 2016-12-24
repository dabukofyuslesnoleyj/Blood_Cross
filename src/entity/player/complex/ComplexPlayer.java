package entity.player.complex;

import entity.core.complex.ComplexUnit;
import entity.core.complex.loot.Looter;
import game_state.core.PlayState;
import tile_map.TileMap;

public abstract class ComplexPlayer extends ComplexUnit implements CanLevelUp, Looter{
	
	private int level;
	private double experience;
	private double threshold;
	
	public ComplexPlayer(TileMap tm, double endurance, double strength,
			double dexterity, double intelligence, double vitality,
			double agility, PlayState state) {
		super(tm, endurance, strength, dexterity, intelligence, vitality, agility, state);
	}

	public void update(){
		super.update();
		
		//player stuff updates
		
		this.updateExperience();
		
		this.animation.update();
	}
	
	@Override
	public void updateExperience() {
		
	}

	@Override
	public void increaseExperience(double addedExperience) {
		this.experience+= addedExperience;
		
	}

	@Override
	public void decreaseExperience(double removedExperience) {
		this.increaseExperience(-removedExperience);
		
	}

	@Override
	public void levelUp() {
		this.level++;
		//update stats?
		
	}

	@Override
	public void levelDown() {
		this.level--;
		//update stats?
		
	}

	@Override
	public double getExperience() {
		// TODO Auto-generated method stub
		return experience;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return level;
	}

	@Override
	public void setExpThresholdPerLevel(double threshold) {
		this.threshold = threshold;
	}
	
	

}
