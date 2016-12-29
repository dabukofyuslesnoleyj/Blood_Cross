package entity.player.complex;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.core.complex.ComplexUnit;
import entity.core.complex.loot.Looter;
import game_state.core.PlayState;
import mechanics.skills.UseItem;
import mechanics.skills.core.Skill;
import tile_map.TileMap;

public abstract class ComplexPlayer extends ComplexUnit implements CanLevelUp, Looter{
	
	private int level;
	private double experience;
	private double threshold;
	
	protected static final int IDLE = 0;
	protected static final int WALKING = 1;
	protected static final int JUMPING = 2;
	protected static final int FALLING = 3;
	
	public ComplexPlayer(TileMap tm, double endurance, double strength,
			double dexterity, double intelligence, double vitality,
			double agility, int maxHealth, int maxEnergy, PlayState state) {
		super(tm, endurance, strength, dexterity, intelligence, vitality, agility, maxHealth, maxEnergy, state);
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
	
	public void keyPressedAction(int k) {
		if(k == KeyEvent.VK_LEFT){
			this.setLeft(true);
		}
		if(k == KeyEvent.VK_RIGHT){
			this.setRight(true);
		}
		if(k == KeyEvent.VK_UP){
			this.setUp(true);
		}
		if(k == KeyEvent.VK_DOWN){
			this.setDown(true);
		}
	}
	
	public void keyReleasedAction(int k) {
		if(k == KeyEvent.VK_LEFT){
			this.setLeft(false);
		}
		if(k == KeyEvent.VK_RIGHT){
			this.setRight(false);
		}
		if(k == KeyEvent.VK_UP){
			this.setUp(false);
		}
		if(k == KeyEvent.VK_DOWN){
			this.setDown(false);
		}
		
	}

}
