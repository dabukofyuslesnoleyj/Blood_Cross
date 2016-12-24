package entity.core;

import java.awt.Graphics2D;

import game_state.core.PlayState;
import tile_map.TileMap;

public abstract class Unit extends MapObject{

	//unit stuff
		protected int health;
		protected int maxHealth;
		protected int energy;
		protected int maxEnergy;
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
	
	public int getEnergy() {
		return energy;
	}

	public void setHealth(int health){
		if(health > maxHealth)
			this.health = maxHealth;
		if(health < 0){
			this.health = 0;
			this.isDead = true;
		}
		else
			this.health = health;
	}
	
	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getMaxEnergy() {
		return maxEnergy;
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
