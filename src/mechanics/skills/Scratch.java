package mechanics.skills;

import java.awt.Color;

import entity.core.NPC;
import entity.core.complex.ComplexUnit;
import entity.core.particle.ParticleEmitter;
import mechanics.skills.core.Skill;

public class Scratch extends Skill{
	
	private int range; 
	private int delay;

	public Scratch() {
		super( 0, 8, 0, 0);

		this.range = 40;
	}
	
//	public void scratch(ArrayList<NPC> enemies) {
//	for(NPC e : enemies) {
//		if(e.getY() > y - height/2 && e.getY() < y + height/2)
//			if((e.getX() > x && e.getX() < x + scratchRange && isFacingRight) 
//					|| (e.getX() < x && e.getX() > x - scratchRange &&!isFacingRight)) {
//				e.hit(this.scratchDamage, 400);
//				ParticleEmitter.getInstance().emit(ParticleEmitter.getInstance().generateRawParticles(e, Color.red, 50, 5, 50, 5, false));
//			}
//	}
//}

	@Override
	public void use(ComplexUnit u) {
		System.out.println("SCRACTH");
		for(NPC e : u.getState().getEnemies()) {
			if(e.getY() > u.getY() - u.getHeight()/2 && e.getY() < u.getY() + u.getHeight()/2)
				if((e.getX() > u.getX() && e.getX() < u.getX() + this.range && u.isFacingRight()) 
						|| (e.getX() < u.getX() && e.getX() > u.getX() - this.range &&!u.isFacingRight())) {
					e.hit(this.getEffectValue(), 400);
					ParticleEmitter.getInstance().emit(ParticleEmitter.getInstance().generateRawParticles(e, Color.red, 50, 5, 50, 5, false));
				}
		}
	}

}
