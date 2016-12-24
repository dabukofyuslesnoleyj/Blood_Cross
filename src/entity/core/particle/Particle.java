package entity.core.particle;

import entity.core.MapObject;
import entity.core.NPC;

public abstract class Particle extends MapObject{
	
	protected boolean remove;
	protected boolean damaging;
	
	public Particle(MapObject obj) {
		super(obj.getTileMap(), obj.getState());

		this.x = obj.getX();
		this.y = obj.getY();
		
		this.remove = false;
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public void checkCollision() {
		for (NPC e : this.state.getEnemies()) {
			System.out.println("check fire ball");
			if(this.intersects(e)){
				System.out.println("get Hit enemy");
				e.hit(2, 400);
				this.remove = true;
				
				break;
			}
		}
	}

}
