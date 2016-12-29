package entity.player;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.core.Animation;
import entity.core.particle.ParticleEmitter;
import entity.core.particle.Projectile;
import game_state.core.PlayState;
import tile_map.TileMap;

public class FireBall extends Projectile{

	//constructor
	public FireBall(TileMap tm, boolean isGoingRight, PlayState state) {
		super(tm, isGoingRight, state);
		
		this.movementSpeed = 4;
		
		if(isGoingRight)
			this.dx = movementSpeed;
		else
			this.dx = -movementSpeed;
		
		this.width = 30;
		this.height = 30;
		this.cwidth = 14;
		this.cheight = 14;
		
		//load sprites
		try {
			
			BufferedImage spriteSheet = ImageIO.read(this.getClass().getResourceAsStream("/Sprites/Player/fireball.gif"));
			
			this.moveSprites = new BufferedImage[4];
			this.hitSprites = new BufferedImage[3];
			for(int i = 0; i < moveSprites.length; i++){
				this.moveSprites[i] = spriteSheet.getSubimage(i*width, 0, width, height);
			}
			
			for(int i = 0; i < hitSprites.length; i++){
				this.hitSprites[i] = spriteSheet.getSubimage(i*width, height, width, height);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//animation stuff
		this.animation = new Animation();
		this.animation.setFrames(moveSprites);
		this.animation.setDelay(70);
		
		//fireball effects
		this.damage = 5;
	}
	
	//methods
	public void update(){
		super.update();
		this.checkCollision();
		
		if(dx == 0 && !this.isHit) {
			this.setHit();
		}		

		ParticleEmitter.getInstance().emit(ParticleEmitter.getInstance().generateRawParticles(this, new Color(255,140,0), 8, 5, 20, 1.5, false));
		this.animation.update();
		if(isHit && this.animation.hasPlayedOnce()){
			this.remove = true;
		}
	}
	
	
	

}
