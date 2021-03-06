package entity.player;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import entity.core.complex.loot.Lootable;
import entity.player.complex.ComplexPlayer;
import game_state.core.PlayState;
import sprite.Spritesheet;
import tile_map.TileMap;

public class Player extends ComplexPlayer{
	
	//skill that player is using
	private boolean firing;
	private boolean scratching;
	private boolean dashing;

	private boolean weaponIsDrawn;
	private boolean drawingWeapon;
	private boolean keepingWeapon;
	
	//gliding
	private boolean gliding;
	
	//animations
	private HashMap<Integer, BufferedImage[]> spriteMap;
	
	//animation actions
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;
	private static final int DRAWING = 7;
	private static final int KEEPING = 8;
	private static final int DASHING = 9;
	
	private static final int SPRITE_WIDTH = 320;
	
	public Player(TileMap tm, int maxHealth, int maxEnergy, PlayState state) {
		super(tm, 7, 10, 10, 10, 10, 10, maxHealth, maxEnergy, state);
		this.width = SPRITE_WIDTH;
		this.height = 320;
		this.cwidth = 60;
		this.cheight = 290;//308; //TODO change to fit better
		
		this.movementSpeed = 4.1;
		this.maxSpeed = 7.6;
		this.stopSpeed = 0.4;
		this.fallSpeed = 0.15;
		this.maxFallSpeed = 10.0;
		this.jumpStart = -4.8;
		this.stopJumpSpeed = 0.3;
		
		this.isFacingRight = true;
		
		this.drawingWeapon = false;
		this.weaponIsDrawn = false;
		this.keepingWeapon = false;
		
		//load sprites
		try {
			
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.png"));
			this.spriteMap = new HashMap<Integer, BufferedImage[]>();
			
			
			Spritesheet sh = new Spritesheet(spriteSheet);
			
			sh.setThresholds(SPRITE_WIDTH, SPRITE_WIDTH);
			sh.parse();
			
			this.spriteMap.put(IDLE, sh.getFrames(0,1,2,3));
			this.spriteMap.put(WALKING, sh.getFrames(4,5,6,7,8,9,10,11));
			this.spriteMap.put(JUMPING, sh.getFrames(12));
			this.spriteMap.put(FALLING, sh.getFrames(13,14));
			this.spriteMap.put(GLIDING, sh.getFrames(15,16,17,18));
			this.spriteMap.put(DRAWING, sh.getFrames(20));
			this.spriteMap.put(FIREBALL, sh.getFrames(21,22,23,24));
			this.spriteMap.put(KEEPING, sh.getFrames(26));
			this.spriteMap.put(SCRATCHING, sh.getFrames(27,28));
			this.spriteMap.put(DASHING, sh.getFrames(15,16,17,18));
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.currAction = IDLE;
		this.animation.setFrames(this.spriteMap.get(IDLE));
		this.animation.setDelay(400);
		
		//add skills
		this.addSkillFromSet("Cast Fireball");
		this.addSkillFromSet("Slash");
		this.addSkillFromSet("Dash");
	}
	
	//methods
	@Override
	protected void getNextPosition(){
		if(this.currAction != DASHING)
			super.getNextPosition();
		else {//implement falling physics while dashing
			dy += fallSpeed;
			if(dy > 0)
				jumping = false;
			if(dy < 0 && !jumping){
				dy += stopJumpSpeed;
			}
			
			if(dy > maxFallSpeed)
				dy = maxFallSpeed;			
		}
		
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
	
	@Override
	public void update(){
		
		super.update();
		
		if(this.currRow > tileMap.getNumRows()){
			this.setPosition(200, 500);
			this.hit(1, 1000);
		}
		
		//check if attacking
		if(currAction == SCRATCHING){
			System.out.println("SLASH");
			if(this.animation.hasPlayedOnce()) {
				this.scratching = false;
			}
		}
		
		if(currAction == DASHING) {
			if(this.animation.hasPlayedOnce()) {
				this.dashing = false;
			}
		}
		
		if(currAction == DRAWING){
			if(this.animation.hasPlayedOnce()){
				this.drawingWeapon = false;
				this.weaponIsDrawn = true;
				this.firing = true;
			}
		}		
//		else if(currAction == FIREBALL){
//			if(this.animation.hasPlayedOnce()){
//				this.firing = false;
//			}
//		}
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
//			if(energy >= fireCost){
//				this.energy -= this.fireCost;
//				FireBall fb = new FireBall(this.tileMap, isFacingRight, this.state);
//				fb.setPosition(x, y);
//				ProjectileEmitter.getInstance().emit(fb);
//			}
			this.castSkill("Cast Fireball", this);
		}


		//set animations
		if(this.scratching){
			if(this.currAction != SCRATCHING){
				this.currAction = SCRATCHING;
				this.animation.setFrames(this.spriteMap.get(SCRATCHING));
				this.animation.setDelay(200);
				this.width = SPRITE_WIDTH;
				this.castSkill("Slash", this);
			}
		}
		else if(this.dashing) {
			if(this.currAction != DASHING){
				//TODO: implement proper animation
				this.currAction = DASHING;
				this.animation.setFrames(this.spriteMap.get(DASHING));
				this.animation.setDelay(200);
				this.width = SPRITE_WIDTH;
				this.castSkill("Dash", this);
				
			}
		}
		else if(this.drawingWeapon && !keepingWeapon){
			if(this.currAction != DRAWING){
				this.currAction = DRAWING;
				//TODO: Implement proper animation
				this.animation.setFrames(this.spriteMap.get(DRAWING));
				this.animation.setDelay(100);
				this.width = SPRITE_WIDTH;
			}
		}
		else if(this.firing && !keepingWeapon){
			if(this.currAction != FIREBALL){
				this.currAction = FIREBALL;
				this.animation.setFrames(this.spriteMap.get(FIREBALL));
				this.animation.setDelay(100);
				this.width = SPRITE_WIDTH;
			}
		}
		else if(this.keepingWeapon && !this.firing){
			if(this.currAction != KEEPING){
				this.currAction = KEEPING;
				//TODO: Implement proper animation
				this.animation.setFrames(this.spriteMap.get(KEEPING));
				this.animation.setDelay(100);
				this.width = SPRITE_WIDTH;
			}
		}
		else if(dy > 0){
			if(this.gliding){
				if(this.currAction != GLIDING){
					this.currAction = GLIDING;
					this.animation.setFrames(this.spriteMap.get(GLIDING));
					this.animation.setDelay(100);
					this.width = SPRITE_WIDTH;
				}
			}
			else if(this.currAction != FALLING){
				this.currAction = FALLING;
				this.animation.setFrames(this.spriteMap.get(FALLING));
				this.animation.setDelay(100);
				this.width = SPRITE_WIDTH;
			}
		}
		else if(dy < 0){
			if(this.currAction != JUMPING){
				this.currAction = JUMPING;
				this.animation.setFrames(this.spriteMap.get(JUMPING));
				this.animation.setDelay(-1);
				this.width = SPRITE_WIDTH;
			}
		}
		else if(this.left || this.right){
			if(this.currAction != WALKING){
				this.currAction = WALKING;
				this.animation.setFrames(this.spriteMap.get(WALKING));
				this.animation.setDelay(80);
				this.width = SPRITE_WIDTH;
			}
		}
		else{
			if(this.currAction != IDLE){
				this.currAction = IDLE;
				this.animation.setFrames(this.spriteMap.get(IDLE));
				this.animation.setDelay(400);
				this.width = SPRITE_WIDTH;
			}
		}
		
		this.animation.update();
		
		//set direction
		if(this.currAction != SCRATCHING && currAction != FIREBALL && this.currAction != DASHING){
			if(right)
				this.isFacingRight = true;
			if(left)
				this.isFacingRight = false;
		}
	}
	
	public void draw(Graphics2D g){
	
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
	
	public void setDashing(boolean dashing) {
		this.dashing = dashing;
	}
	
	public void setKeeping(){
		if(weaponIsDrawn){
			this.keepingWeapon = true;
			this.weaponIsDrawn = false;
			this.firing = false;
		}
	}

	@Override
	public void setLevelLimit(int levelLimit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeLoot(Lootable lootable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressedAction(int k) {
		super.keyPressedAction(k);
		
		if(k == KeyEvent.VK_SPACE){
			if(this.isFalling()){
				this.setGliding(true);
			}
			else
				this.setJumping(true);
		}
		if(k == KeyEvent.VK_Q){
			this.setScratching();
			//will trigger scratch damage if enemy is near enough
		}
		if(k == KeyEvent.VK_E){
			this.setFiring();
		}
	}

	@Override
	public void keyReleasedAction(int k) {
		super.keyReleasedAction(k);
		
		if(k == KeyEvent.VK_SPACE){
			if(this.isFalling()){
				this.setGliding(false);
			}
			else
				this.setJumping(false);
		}
		if(k == KeyEvent.VK_E){
			this.setKeeping();
		}
		if(k == KeyEvent.VK_SHIFT) {
			if(this.isWalking() && !this.jumping && !this.falling)
				this.setDashing(true);
		}
	}

}
