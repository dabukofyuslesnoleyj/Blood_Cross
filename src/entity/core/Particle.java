package entity.core;

import java.awt.image.BufferedImage;

import game_state.core.PlayState;
import tile_map.TileMap;

public class Particle extends MapObject{
	
	private boolean remove;
	
	//if you just want to generate particles. lighter method
	public Particle(TileMap tm, PlayState state, BufferedImage sprites[], int life, double x, double y) {
		super(tm, state);
		
		this.x = x;
		this.y = y;
		
		this.animation = new Animation();
		this.animation.setFrames(sprites);
		this.animation.setDelay(life);

	}

	//if you want to bind the particles to a map object
	public Particle(MapObject obj, TileMap tm, PlayState state, BufferedImage sprites[], int life) {
		super(tm, state);

		this.x = obj.getX();
		this.y = obj.getY();
		this.width = obj.getWidth();
		this.height = obj.getHeight();
		this.cheight = obj.getCheight();
		this.cwidth = obj.getCwidth();

		this.animation = new Animation();
		this.animation.setFrames(sprites);
		this.animation.setDelay(life);
	}
	
	public void update() {
		super.update();
//		System.out.println("PLAY");
		if(this.animation.hasPlayedOnce()){
			this.remove = true;
		}

		this.animation.update();
	}
	
	public boolean shouldRemove() {
		return remove;
	}

}
