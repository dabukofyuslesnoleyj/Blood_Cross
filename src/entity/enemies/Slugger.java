package entity.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.core.Animation;
import entity.core.NPC;
import entity.player.Player;
import game_state.core.PlayState;
import tile_map.TileMap;

public class Slugger extends NPC{

	private BufferedImage[] sprites;
	private BufferedImage[] deathSgprites;
	private Player player;
	
	public Slugger(TileMap tm, Player player, PlayState state) {
		super(tm, 2, 0, 1, state);
		
		this.player = player;
		
		this.movementSpeed = 0.3;
		this.maxSpeed = 0.6;
		this.fallSpeed = 0.2;
		this.maxFallSpeed = 10.0;
		
		this.jumpStart = 5.6;
		
		this.width = 30;
		this.height = 30;
		this.cwidth = 20;
		this.cheight = 20;
		
		//load sprites
		this.sprites = new BufferedImage[3];
		try {
			
			BufferedImage spriteSheet = ImageIO.read(this.getClass().getResourceAsStream("/Sprites/Enemies/slugger.gif"));
			
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spriteSheet.getSubimage(i*width, 0, width, height);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.animation = new Animation();
		this.animation.setFrames(sprites);
		this.animation.setDelay(100);
		
		this.isFacingRight = this.right = true;
	}
	
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
	}

	@Override
	public void setNPCPattern() {
		if(dx == 0){
			if(right){
				right = false;
				left = true;
			}
			else if(left){
				right = true;
				left = false;
			}
		}
		else {
			if(this.player.getCurrRow() >= this.currRow){
				if(this.player.getCurrCol() >= this.currCol - 3 && this.player.getCurrCol() < this.currCol && right){
					dx = 0;
					right = false;
					left = true;
				}
				else if(this.player.getCurrCol() <= this.currCol + 3  && this.player.getCurrCol() > this.currCol && left){
					dx = 0;
					right = true;
					left = false;
				}
			}
			else {
				this.dy += jumpStart;
				if(this.player.getCurrCol() <= this.currCol + 3 ){
					this.jumping = true;
				}
				
			}
			
		}
		this.isFacingRight = right;
	}

}
