package entity.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.core.Animation;
import entity.core.NPC;
import entity.player.Player;
import entity.player.complex.ComplexPlayer;
import game_state.core.PlayState;
import sprite.Spritesheet;
import tile_map.TileMap;

public class Slugger extends NPC{

	private BufferedImage[] sprites;
	private BufferedImage[] deathSgprites;
	private ComplexPlayer player;
	
	public Slugger(TileMap tm, ComplexPlayer player, PlayState state) {
		super(tm, 2, 0, 1, state);
		
		this.player = player;
		
		this.movementSpeed = 0.3;
		this.maxSpeed = 0.6;
		this.fallSpeed = 0.2;
		this.maxFallSpeed = 10.0;
		
		this.jumpStart = 5.6;
		
		this.width = 135;
		this.height = 135;
		this.cwidth = 30;
		this.cheight = 120;
		
		//load sprites
		try {
			
			BufferedImage spriteSheet = ImageIO.read(this.getClass().getResourceAsStream("/Sprites/Player/playersprites Alt.gif"));
			
			Spritesheet sh = new Spritesheet(spriteSheet);
			sh.setThresholds(135, 135);
			sh.parse();
			this.sprites = sh.getFrames(4,5,6,7,8,9);
			
			
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
