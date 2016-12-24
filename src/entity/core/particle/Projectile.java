package entity.core.particle;

import java.awt.Color;
import java.awt.image.BufferedImage;

import entity.core.MapObject;
import entity.core.NPC;
//import entity.core.Particle;
//import entity.core.ParticleEmitter;
import game_state.core.PlayState;
import tile_map.TileMap;

public abstract class Projectile extends MapObject{
	
	protected boolean isHit;
	protected boolean remove;
	protected BufferedImage[] moveSprites;
	protected BufferedImage[] hitSprites;
//	protected Particle particle;
	protected int damage;
	

	public Projectile(TileMap tm, boolean isGoingRight, PlayState state) {
		super(tm, state);
		
		this.isFacingRight = isGoingRight;
		
	}
	
	public void setHit(){
		if(this.isHit)
			return;
		this.isHit = true;

//		ParticleEmitter.getInstance().emit(new SpriteParticle(this, this.hitSprites, 70)); //if you want to show the sprites
		ParticleEmitter.getInstance().emit(ParticleEmitter.getInstance().generateRawParticles(this, Color.red, 50, 5, 50, false)); //if you want raw (only for testing)
		this.dx = 0;
	}
	
	public boolean shouldRemove(){
		return remove;
	}
	
	public void checkCollision() {
		for (NPC e : this.state.getEnemies()) {
			System.out.println("check fire ball");
			if(this.intersects(e)){
				System.out.println("get Hit enemy");
				e.hit(this.damage, 400);
				this.setHit();
				
				break;
			}
		}
	}
	

}
