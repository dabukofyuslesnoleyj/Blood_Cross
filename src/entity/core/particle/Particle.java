package entity.core.particle;

import entity.core.MapObject;
import entity.core.NPC;
import entity.core.element.MapElement;

public abstract class Particle extends MapElement{

	public Particle(MapObject obj) {
		super(obj.getTileMap(), obj.getState());

		this.x = obj.getX();
		this.y = obj.getY();
		this.setMapPosition();
		
		this.remove = false;
	}
}
