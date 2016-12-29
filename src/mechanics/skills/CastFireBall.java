package mechanics.skills;

import entity.core.complex.ComplexUnit;
import entity.core.element.ProjectileEmitter;
import entity.player.FireBall;
import mechanics.skills.core.Skill;

public class CastFireBall extends Skill{

	public CastFireBall() {
		super(200, 5, 0, 0);

		
	}

	@Override
	public void use(ComplexUnit u) {
		if(u.getEnergy() >= this.getCost()){
			u.setEnergy(u.getEnergy() - this.getCost());
			FireBall fb = new FireBall(u.getTileMap(), u.isFacingRight(), u.getState());
			fb.setPosition(u.getX(), u.getY());
			ProjectileEmitter.getInstance().emit(fb);
		}
	}

}
