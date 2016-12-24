package tile_map;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	private int type;
	
	//tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	//Constructor
	public Tile(BufferedImage image, int type) {
		super();
		this.image = image;
		this.type = type;
	}
	
	//Getters
	public BufferedImage getImage() {
		return this.image;
	}

	public int getType() {
		return this.type;
	}
	
}
