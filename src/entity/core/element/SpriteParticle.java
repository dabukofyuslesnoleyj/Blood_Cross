package entity.core.element;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.core.Animation;
import entity.core.MapObject;
import entity.core.particle.Particle;

public class SpriteParticle extends Particle{

	public SpriteParticle(MapObject obj, BufferedImage[] sprites, int life, boolean damaging) {
		super(obj);

		this.width = obj.getWidth();
		this.height = obj.getHeight();
		this.cheight = obj.getCheight();
		this.cwidth = obj.getCwidth();
		this.damaging = damaging;
		

		this.animation = new Animation();
		this.animation.setFrames(sprites);
		this.animation.setDelay(life);
		
	}
	
	public void update() {
		super.update();
		if(this.damaging)
			this.checkCollision();
		this.animation.update();
		if(this.animation.hasPlayedOnce()){
			this.remove = true;
		}
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
