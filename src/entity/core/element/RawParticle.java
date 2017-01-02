package entity.core.element;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.util.ArrayList;
import java.util.Random;

import entity.core.MapObject;

public class RawParticle extends Particle{
	
	//for single colored
	private Color color;
	private double life;
	private double size;
	
	//for multicolored
	private LinearGradientPaint paint;
	private boolean multiColored;

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
		this.multiColored = false;
		
	}
	
	//for multiple colored particles
	public RawParticle(MapObject obj, Color[] colors, double life, double size, double dispersionRate, boolean damaging) {
		super(obj);
		
		double directionx = this.generateDirection(dispersionRate);
		double directiony = this.generateDirection(dispersionRate);

		this.dx = Math.random()*directionx;
		this.dy = Math.random()*directiony;
		this.height = (int) size;
		this.width = (int) size;
		this.cheight = (int) size;
		this.cwidth = (int) size;

		this.paint = new LinearGradientPaint(0, 0, 20, 20, this.getFractions(colors), colors, CycleMethod.REPEAT);
		this.life = life;
		this.size = size;
		this.damaging = damaging;
		
		this.multiColored = true;
		
	}
	
	private float[] getFractions(Color[] colors) {
		float[] fractions = new float[colors.length];
		for(int i = 0; i < fractions.length; i++) {
			fractions[i] = (float) i/fractions.length;
		}
		
		return fractions;
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
		if(this.multiColored) {
			g.setPaint(paint);
		} else
			g.setColor(color);
//		g.fillRect((int)(x+xmap-size/2),(int)(y+ymap-size/2), size, size);
		g.fillOval((int)(x+xmap-size/2),(int)(y+ymap-size/2), (int)size, (int)size);
	}
	
	public void setOffsets(int x, int y) {
		this.x += x;
		this.y += y;
		this.setMapPosition();
	}
	
}
