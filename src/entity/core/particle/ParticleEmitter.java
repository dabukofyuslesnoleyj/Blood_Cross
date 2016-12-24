package entity.core.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.core.MapObject;

public class ParticleEmitter {
	
	private static ParticleEmitter pe = new ParticleEmitter();
	private ArrayList<Particle> activeParticles;
	
	private ParticleEmitter() {
		this.activeParticles = new ArrayList<Particle>();
	}
	
	public static ParticleEmitter getInstance() {
		if(pe == null)
			pe = new ParticleEmitter();
		return pe;
	}
	
	public void emit(Particle particle) {
		this.activeParticles.add(particle);
	}
	
	public void emit(ArrayList<Particle> particle) {
		this.activeParticles.addAll(particle);
	}
	
	public void update() {
		for (int i = 0; i < activeParticleCount(); i++) {
			if(activeParticles.get(i).shouldRemove())
				activeParticles.remove(i);
			else
				activeParticles.get(i).update();
		}
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < activeParticleCount(); i++) {
			if(activeParticles.get(i).shouldRemove())
				activeParticles.remove(i);
			else
				activeParticles.get(i).draw(g);
		}
	}
	
	public int activeParticleCount() {
		return activeParticles.size();
	}
	
	public ArrayList<Particle> generateRawParticles(MapObject obj, Color color, int life, int size, int amount, boolean damaging) {
		ArrayList<Particle> particles = new ArrayList<Particle>();
		
		for(int i = 0; i < amount; i++) {
			int s = (int) (Math.random()*size);
            int l = (int) Math.random()*(120)+life;
			particles.add(new RawParticle(obj, color, l, s, damaging));
		}
		
		return particles;
	}

}
