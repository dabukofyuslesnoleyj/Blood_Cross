package mechanics.skills;

import java.awt.Color;

import entity.core.complex.ComplexUnit;
import entity.core.element.ParticleEmitter;
import mechanics.skills.core.Skill;

public class Dash extends Skill{

	public Dash() {
		super(200, 0, 0, 500);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void use(ComplexUnit u) {
		if(u.getEnergy() < this.getCost())
			return;
		u.setEnergy(u.getEnergy() - this.getCost());

		if(u.isFacingRight()) {
			u.setDx(u.getDx() + 12); //will depend on player's armor? onti onti na nagiging dark souls lmao // d-d-di ah!
		}
		else {
			u.setDx(u.getDx() - 12);
		}

		ParticleEmitter.getInstance().emit(ParticleEmitter.getInstance().generateRawParticles(u, new Color[] {Color.WHITE, Color.gray}, 5, 5, 50, 1, false, 0, u.getHeight()/3));

	}

}
