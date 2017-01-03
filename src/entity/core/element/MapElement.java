package entity.core.element;

import entity.core.MapObject;
import entity.core.NPC;
import entity.core.complex.ComplexNPC;
import game_state.core.PlayState;
import tile_map.TileMap;

public abstract class MapElement extends MapObject{
	
	protected boolean remove;
	protected boolean damaging;
	
	public MapElement(TileMap tm, PlayState state) {
		super(tm, state);
		// TODO Auto-generated constructor stub
	}
	
	public boolean shouldRemove() {
		return this.remove;
	}
	
	public void checkCollision() {
		for (ComplexNPC e : this.state.getEnemies()) {
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
