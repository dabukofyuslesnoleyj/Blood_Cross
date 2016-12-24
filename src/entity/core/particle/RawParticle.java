package entity.core.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import entity.core.MapObject;

public class RawParticle extends Particle{
	
	private Color color;
	private int life;
	private int size;

	public RawParticle(MapObject obj, Color color, int life, int size, boolean damaging) {
		super(obj);
		
		int directionx = this.generateDirection();
		int directiony = this.generateDirection();

		this.dx = Math.random()*directionx;
		this.dy = Math.random()*directiony;
		this.cheight = size;
		this.cwidth = size;

		this.color = color;
		this.life = life;
		this.size = size;
		this.damaging = damaging;
	}
	
	private int generateDirection() {
		Random rand = new Random();
		int direction = 2;
		if(rand.nextInt(2) == 0)
			direction *= -1;
		return direction;
	}
	
	public void update() {
		this.setPosition(x+=dx, y+=dy);
		if(this.damaging)
			this.checkCollision();
	    life--;
	    if(life <= 0)
	    	this.remove = true;
	}
	
	public void draw(Graphics2D g) {
		this.setMapPosition();
		g.setColor(color);
		g.fillRect((int)(x+xmap-size/2),(int)(y+ymap-size/2), size, size);
	}

}
