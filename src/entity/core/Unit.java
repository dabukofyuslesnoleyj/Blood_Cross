package entity.core;

import java.awt.Graphics2D;

import game_state.core.PlayState;
import tile_map.TileMap;

public abstract class Unit extends MapObject{

	//unit stuff
		protected float health;
		protected float maxHealth;
		protected float energy;
		protected float maxEnergy;
		
		protected float stamina;
		protected float maxStamina;
		
		protected float berserk;
		protected float maxBerserk;
		
		protected float exp;
		protected float maxExp;
		
		protected boolean dead;
		protected boolean flinching;
		protected long flinchTime;
		protected long flinchLength;
		protected boolean isDead;
	
	//constructor	

	public Unit(TileMap tm, int maxHealth, int maxEnergy, PlayState state) {
		super(tm, state);
		this.health = this.maxHealth = maxHealth;
		this.energy = this.maxEnergy = maxEnergy;
		

		this.maxStamina = 100;
		this.stamina = this.maxStamina;
		
		this.maxBerserk = 100;
		this.berserk = 80;
		
		this.maxExp = 100;
		this.exp = 80;
	}
	
	//methods
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
	}
	
	protected void getNextPosition(){		
		//movement
		if(left){
			dx -= movementSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
		}
		else if(right){
			dx += movementSpeed;
			if(dx > maxSpeed){
				dx = maxSpeed;
			}
		}
		else{
			if(dx > 0){
				dx -= stopSpeed;
				if(dx < 0)
					dx = 0;
			}
			else if(dx < 0){
				dx += stopSpeed;
				if(dx > 0)
					dx = 0;
			}
		}
		

		if(jumping && !falling){
			dy = jumpStart;
			this.falling = true;
		}
		
		//falling
		if(falling){
			dy += fallSpeed;
			if(dy > 0)
				jumping = false;
			if(dy < 0 && !jumping){
				dy += stopJumpSpeed;
			}
			
			if(dy > maxFallSpeed)
				dy = maxFallSpeed;			
		
		}
		
	}
	
	@Override
	public void update(){
		//update position
		this.getNextPosition();
		super.update();
		//check flinching
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTime) / 1000000;
			if(elapsed > this.flinchLength){
				this.flinching = false;
			}
		}
	}
	
	public void hit(int damage, long flinchLength){
		if(isDead || flinching)
			return;
		this.setHealth(this.health-damage);
		this.flinching = true;
		this.flinchTime = System.nanoTime();
		this.flinchLength = flinchLength;
	}
	
	public float getEnergy() {
		return energy;
	}

	public void setHealth(float health){
		if(health > maxHealth)
			this.health = maxHealth;
		if(health < 0){
			this.health = 0;
			this.isDead = true;
		}
		else
			this.health = health;
	}
	
	public float getHealth() {
		return health;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public float getMaxEnergy() {
		return maxEnergy;
	}
	
	public float getStamina() {
		return stamina;
	}

	public float getMaxStamina() {
		return maxStamina;
	}

	public float getBerserk() {
		return berserk;
	}

	public float getMaxBerserk() {
		return maxBerserk;
	}

	public float getExp() {
		return exp;
	}

	public float getMaxExp() {
		return maxExp;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isFalling(){
		return falling;
	}
	
}
