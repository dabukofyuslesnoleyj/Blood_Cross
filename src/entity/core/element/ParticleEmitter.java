package entity.core.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.core.MapObject;

public class ParticleEmitter extends Emitter{
	
	private static ParticleEmitter pe = new ParticleEmitter();
	
	private ParticleEmitter() {
		super();
	}
	
	public static ParticleEmitter getInstance() {
		if(pe == null)
			pe = new ParticleEmitter();
		return pe;
	}
	
	public ArrayList<Particle> generateRawParticles(MapObject obj, Color color, double life, double size, int amount, double dispersionRate, boolean damaging, int xOffset, int yOffset) {
		ArrayList<Particle> particles = new ArrayList<Particle>();
		RawParticle rp = null;
		
		for(int i = 0; i < amount; i++) {
			double s = Math.random()*size;
			double l = Math.random()*(120)+life;
			rp = new RawParticle(obj, color, l, s, dispersionRate, damaging);
			rp.setOffsets(xOffset, yOffset);
			particles.add(rp);
		}
		
		return particles;
	}
	
	public ArrayList<Particle> generateRawParticles(MapObject obj, Color[] color, double life, double size, int amount, double dispersionRate, boolean damaging, int xOffset, int yOffset) {
		ArrayList<Particle> particles = new ArrayList<Particle>();
		RawParticle rp = null;
		
		for(int i = 0; i < amount; i++) {
			double s = Math.random()*size;
			double l = Math.random()*(120)+life;
			rp = new RawParticle(obj, color, l, s, dispersionRate, damaging);
			rp.setOffsets(xOffset, yOffset);
			particles.add(rp);
		}
		
		return particles;
	}
}
