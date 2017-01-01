package entity.core.particle;

import entity.core.element.Emitter;

public class ProjectileEmitter extends Emitter{
	
	private static ProjectileEmitter pe = new ProjectileEmitter();
	
	private ProjectileEmitter() {
		super();
	}
	
	public static ProjectileEmitter getInstance() {
		if(pe == null)
			pe = new ProjectileEmitter();
		return pe;
	}

}
