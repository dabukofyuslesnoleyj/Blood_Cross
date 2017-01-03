package entity.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.core.Animation;
import entity.core.NPC;
import entity.core.complex.ComplexNPC;
import entity.core.complex.loot.Loot;
import entity.player.Player;
import entity.player.complex.ComplexPlayer;
import game_state.core.PlayState;
import sprite.Spritesheet;
import tile_map.TileMap;

public class Slugger extends ComplexNPC{

	private BufferedImage[] sprites;
	private BufferedImage[] deathSgprites;
	private ComplexPlayer player;
	
	public Slugger(TileMap tm, ComplexPlayer player, PlayState state) {
		super(tm,0,0,0,0,0, 2, 0, 1, state);
		
		this.player = player;
		
		this.movementSpeed = 0.3;
		this.maxSpeed = 0.6;
		this.fallSpeed = 0.2;
		this.maxFallSpeed = 10.0;
		
		this.jumpStart = 5.6;
		
		this.width = 181;
		this.height = 305;
		this.cwidth = 150;
		this.cheight = 300;
		
		//load sprites
		try {
			
			BufferedImage spriteSheet = ImageIO.read(this.getClass().getResourceAsStream("/Sprites/Enemies/spriteTest.png"));
//			
//			spriteSheet.getSubimage(0, arg1, arg2, arg3)
//			
			Spritesheet sh = new Spritesheet(spriteSheet);
			sh.setThresholds(181, 305);
			sh.parse();
			this.sprites = sh.getFrames(0,3,6,1,4,7,2,5,8);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.animation = new Animation();
		this.animation.setFrames(sprites);
		this.animation.setDelay(100);
		
		this.left = true;
		this.isFacingRight = this.right = false;
//		dx = -1;
	}
	
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
	}

	@Override
	public void NPC_Pattern() {
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
				if(this.player.getCurrCol() >= this.currCol - 20 && this.player.getCurrCol() < this.currCol && right){
					dx = 0;
					right = false;
					left = true;
				}
				else if(this.player.getCurrCol() <= this.currCol + 20  && this.player.getCurrCol() > this.currCol && left){
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

	@Override
	public double getLoot() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLoot(Loot loot) {
		// TODO Auto-generated method stub
		
	}

}
