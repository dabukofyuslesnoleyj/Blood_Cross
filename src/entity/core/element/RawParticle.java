package entity.core.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import entity.core.MapObject;
import entity.core.particle.Particle;

public class RawParticle extends Particle{
	
	private Color color;
	private double life;
	private double size;

	public RawParticle(MapObject obj, Color color, double life, double size, double dispersionRate, boolean damaging) {
		super(obj);
		
		double directionx = this.generateDirection(dispersionRate);
		double directiony = this.generateDirection(dispersionRate);

		this.dx = Math.random()*directionx;
		this.dy = Math.random()*directiony;
		this.height = (int) size;
		this.width = (int) size;
		this.cheight = (int) size;
		this.cwidth = (int) size;

		this.color = color;
		this.life = life;
		this.size = size;
		this.damaging = damaging;
		
		
	}
	
	private double generateDirection(double dispersionRate) {
		Random rand = new Random();
		double direction = dispersionRate;
		if(rand.nextInt(2) == 0)
			direction *= -1;
		return direction;
	}
	
	public void update() {
		super.update();

		if(this.damaging)
			this.checkCollision();
	    life--;

	    if(life <= 0 || this.notOnScreen())
	    	this.remove = true;
	}
	
	public void draw(Graphics2D g) {
		this.setMapPosition();
		g.setColor(color);
//		g.fillRect((int)(x+xmap-size/2),(int)(y+ymap-size/2), size, size);
		g.fillOval((int)(x+xmap-size/2),(int)(y+ymap-size/2), (int)size, (int)size);
	}
	
}
