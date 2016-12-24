package mechanics.skills.core;

import entity.core.complex.ComplexUnit;
import mechanics.Usable;

public abstract class Skill implements Usable{

	private int cost;
	private int effectValue;
	private long castTime;
	private long cooldown;
	private long lastCast;
	private long elapsed;
	
	protected ComplexUnit caster;
	
	public Skill(ComplexUnit caster, int cost, int effectValue, long castTime, long cooldown){
		this.caster = caster;
		this.cost = cost;
		this.effectValue = effectValue;
		this.castTime = castTime;
		this.cooldown = cooldown;
		this.lastCast = 0;
	}
	
	public void cast(ComplexUnit u){
		this.elapsed = System.nanoTime()/1000000;
		if(lastCast == 0 || elapsed == lastCast+cooldown){
			lastCast = elapsed;
			
			this.use(u);
		}
		else{
			//skill is in cooldown
		}
	}
	
	public int getEffectValue() {
		return effectValue;
	}

	public void setEffectValue(int effectValue) {
		this.effectValue = effectValue;
	}

	public int getCost(){
		return cost;
	}
	
	public void setCost(int cost){
		this.cost = cost;
	}
	
	public long getCastTime(){
		return castTime;
	}
	
}
