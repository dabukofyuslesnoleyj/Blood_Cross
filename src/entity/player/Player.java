package entity.player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import entity.core.NPC;
import entity.core.Unit;
import game_state.core.PlayState;
import tile_map.TileMap;

public class Player extends Unit{
	
	//range attack
	private boolean firing;
	private int fireCost;
	private int fireDamage;
	private ArrayList<FireBall> fireballs;
	
	private boolean weaponIsDrawn;
	private boolean drawingWeapon;
	private boolean keepingWeapon;
	
	//melee attack
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
	
	//gliding
	private boolean gliding;
	
	//animations
	private HashMap<Integer, BufferedImage[]> spriteMap;
//	private ArrayList<BufferedImage[]> sprites;
	private final int numFrames[] = {
		4, 6, 1, 2, 2, 3, 2	
	};
	
	//animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;
	private static final int DRAWING = 7;
	private static final int KEEPING = 8;
	
	public Player(TileMap tm, int maxHealth, int maxEnergy, PlayState state) {
		super(tm, maxHealth, maxEnergy, state);
		this.width = 135;
		this.height = 135;
		this.cwidth = 30;
		this.cheight = 120; //TODO change to fit better
		
		this.movementSpeed = 0.6;
		this.maxSpeed = 1.6;
		this.stopSpeed = 0.4;
		this.fallSpeed = 0.15;
		this.maxFallSpeed = 10.0;
		this.jumpStart = -4.8;
		this.stopJumpSpeed = 0.3;
		
		this.isFacingRight = true;
		
		this.fireCost = 200;
		this.fireDamage = 5;
		this.fireballs = new ArrayList<FireBall>();
		
		this.drawingWeapon = false;
		this.weaponIsDrawn = false;
		this.keepingWeapon = false;
		
		this.scratchDamage = 8;
		this.scratchRange = 40;
		
		//load sprites
		try {
			
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites GIF.gif"));
//			this.sprites = new ArrayList<BufferedImage[]>();
			this.spriteMap = new HashMap<Integer, BufferedImage[]>();
			for(int i = 0; i < this.numFrames.length; i++){
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++){
//					if(i < this.numFrames.length-1)
						bi[j] = spriteSheet.getSubimage(j*width, i*height, width, height);
//					else
//						bi[j] = spriteSheet.getSubimage(j*width*2, i*height, width*2, height);
				}
				this.spriteMap.put(i, bi);
//				this.sprites.add(bi);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.currAction = IDLE;
		this.animation.setFrames(this.spriteMap.get(IDLE));
		this.animation.setDelay(400);
	}
	
	//methods
	@Override
	protected void getNextPosition(){
		
		super.getNextPosition();
		
		//cannot move while attacking except on air
		if((this.currAction == SCRATCHING || this.currAction == FIREBALL) && !(jumping || falling)){
			dx = 0;
		}
		//falling
		if(falling){
			if(dy > 0 && gliding){
				dy -= fallSpeed;
				dy += fallSpeed*0.1;
			}
		}
		
	}
	
	//scratch will become generalized melee attack method.
	public void scratch(ArrayList<NPC> enemies) {
		for(NPC e : enemies) {
			if(e.getY() > y - height/2 && e.getY() < y + height/2)
				if((e.getX() > x && e.getX() < x + scratchRange && isFacingRight) 
						|| (e.getX() < x && e.getX() > x - scratchRange &&!isFacingRight))
					e.hit(this.scratchDamage, 400);
		}
	}
	
//	public void checkAttackEnemies(ArrayList<NPC> enemies){
//		
//			for (NPC e : enemies) {
//				
//				for (FireBall f : fireballs) {
//					System.out.println("check fire ball");
//					if(f.intersects(e)){
//						System.out.println("get Hit enemy");
//						e.hit(fireDamage, 400);
//						f.setHit();
//						
//						break;
//					}
//				}
//				
//				if(scratching){
//					if(e.getY() > y - height/2 && e.getY() < y + height/2)
//						if((e.getX() > x && e.getX() < x + scratchRange && isFacingRight) 
//								|| (e.getX() < x && e.getX() > x - scratchRange &&!isFacingRight))
//							e.hit(this.scratchDamage, 400);
//				}
//				
//				
//			}
//	}
	
	@Override
	public void update(){
		
		super.update();
		
		if(this.currRow > tileMap.getNumRows()){
			this.setPosition(100, 100);
			this.hit(1, 1000);
		}
		
		//check if attacking
		if(currAction == SCRATCHING){
			if(this.animation.hasPlayedOnce())
				this.scratching = false;
		}
		
		if(currAction == DRAWING){
			if(this.animation.hasPlayedOnce()){
				this.drawingWeapon = false;
				this.weaponIsDrawn = true;
				this.firing = true;
			}
		}		
		else if(currAction == FIREBALL){
			if(this.animation.hasPlayedOnce()){
				this.firing = false;
			}
		}
		else if(currAction == KEEPING){
			if(this.animation.hasPlayedOnce()){
				this.keepingWeapon = false;
				this.weaponIsDrawn = false;
				this.firing = false;
			}
		}
		
		//fireball
		this.energy++;
		if(this.energy > this.maxEnergy){
			this.energy = this.maxEnergy;
		}
		if(this.firing && currAction != FIREBALL){
			if(energy >= fireCost){
				this.energy -= this.fireCost;
				FireBall fb = new FireBall(this.tileMap, isFacingRight, this.state);
				fb.setPosition(x, y);
				this.fireballs.add(fb);
			}
		}
		for(int i = 0; i < this.fireballs.size(); i++){
			this.fireballs.get(i).update();
			if(this.fireballs.get(i).shouldRemove()){
				this.fireballs.remove(i);
				i--;
			}
		}
		
		//set animations
		if(this.scratching){
			if(this.currAction != SCRATCHING){
				this.currAction = SCRATCHING;
				this.animation.setFrames(this.spriteMap.get(SCRATCHING));
				this.animation.setDelay(200);
				this.width = 135;
			}
		}
		else if(this.drawingWeapon && !keepingWeapon){
			if(this.currAction != DRAWING){
				this.currAction = DRAWING;
				//TODO: Implement proper animation
				this.animation.setFrames(this.spriteMap.get(FIREBALL));
				this.animation.setDelay(100);
				this.width = 135;
			}
		}
		else if(this.firing && !keepingWeapon){
			if(this.currAction != FIREBALL){
				this.currAction = FIREBALL;
				this.animation.setFrames(this.spriteMap.get(FIREBALL));
				this.animation.setDelay(100);
				this.width = 135;
			}
		}
		else if(this.keepingWeapon){
			if(this.currAction != KEEPING){
				this.currAction = KEEPING;
				//TODO: Implement proper animation
				this.animation.setFrames(this.spriteMap.get(GLIDING));
				this.animation.setDelay(100);
				this.width = 135;
			}
		}
		else if(dy > 0){
			if(this.gliding){
				if(this.currAction != GLIDING){
					this.currAction = GLIDING;
					this.animation.setFrames(this.spriteMap.get(GLIDING));
					this.animation.setDelay(100);
					this.width = 135;
				}
			}
			else if(this.currAction != FALLING){
				this.currAction = FALLING;
				this.animation.setFrames(this.spriteMap.get(FALLING));
				this.animation.setDelay(100);
				this.width = 135;
			}
		}
		else if(dy < 0){
			if(this.currAction != JUMPING){
				this.currAction = JUMPING;
				this.animation.setFrames(this.spriteMap.get(JUMPING));
				this.animation.setDelay(-1);
				this.width = 135;
			}
		}
		else if(this.left || this.right){
			if(this.currAction != WALKING){
				this.currAction = WALKING;
				this.animation.setFrames(this.spriteMap.get(WALKING));
				this.animation.setDelay(80);
				this.width = 135;
			}
		}
		else{
			if(this.currAction != IDLE){
				this.currAction = IDLE;
				this.animation.setFrames(this.spriteMap.get(IDLE));
				this.animation.setDelay(400);
				this.width = 135;
			}
		}
		
		this.animation.update();
		
		//set direction
		if(this.currAction != SCRATCHING && currAction != FIREBALL){
			if(right)
				this.isFacingRight = true;
			if(left)
				this.isFacingRight = false;
		}
	}
	
	public void draw(Graphics2D g){
	
		for(FireBall f: this.fireballs){
			f.draw(g);
		}
		
		//draw player
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTime) / 1000000;
			if(elapsed/100 % 2 == 0){
				return;
			}
		}
		super.draw(g);
		
		
	}
	
	public boolean isFalling(){
		return this.falling;
	}
	
	public void setFiring(){
		if(weaponIsDrawn){
			this.firing = true;
		}
		else{
			this.drawingWeapon = true;	
		}
	}
	
	public void setScratching(){
		this.scratching = true;
	}
	
	public void setGliding(boolean gliding){
		this.gliding = gliding;
	}
	
	public void setKeeping(){
		if(weaponIsDrawn){
			this.keepingWeapon = true;
			this.weaponIsDrawn = false;
			this.firing = false;
		}
	}

}
