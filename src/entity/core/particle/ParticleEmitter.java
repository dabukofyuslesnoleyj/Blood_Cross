package entity.core.particle;

import java.awt.Graphics2D;
import java.util.ArrayList;

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
			activeParticles.get(i).draw(g);
		}
	}
	
	public int activeParticleCount() {
		return activeParticles.size();
	}
	
//	public ArrayList<Particle> generateParticles(int amount) {
//		ArrayList<Particle> particles = new ArrayList<Particle>();
//		
//		for(int i = 0; i < amount; i++) {
//			particles.add(new Particle(tm, state));
//		}
//		
//		return particles;
//	}

}
