package entity.core;

import java.awt.Graphics2D;

import game_state.core.PlayState;
import tile_map.TileMap;

public abstract class NPC extends Unit{
	
	protected int damage;

	//constructor
	public NPC(TileMap tm, int maxHealth, int maxEnergy, int damage, PlayState state) {
		super(tm, maxHealth, maxEnergy, state);
		this.damage = damage;
	}

	//methods
	public int getDamage() {
		return damage;
	}
	
	public abstract void setNPCPattern();
	
	@Override
	public void update() {
		
		super.update();
		this.setNPCPattern();
		this.animation.update();
		
	}
	
	@Override
	public void draw(Graphics2D g){
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTime) / 1000000;
			if(elapsed/100 % 2 == 0){
				return;
			}
		}
		super.draw(g);
	}
	
	

}
