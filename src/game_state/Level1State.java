package game_state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.HUD;
import entity.core.NPC;
import entity.core.particle.ParticleEmitter;
import entity.core.particle.ProjectileEmitter;
import entity.enemies.Slugger;
import entity.player.Player;
import entity.player.complex.ComplexPlayer;
import game_state.core.GameStateManager;
import game_state.core.PlayState;
import main.GamePanel;
import mechanics.skills.CastFireBall;
import mechanics.skills.Scratch;
import mechanics.skills.core.SkillSet;
import tile_map.Background;
import tile_map.TileMap;

public class Level1State extends PlayState{

	private Background bg;
	
	private TileMap tilemap;
	
	//Constructor
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		
		this.init();
	}
	
	//Implemented methods
	@Override
	public void init() {
		
		this.tilemap = new TileMap(30);
		this.tilemap.loadTiles("/Tilesets/grasstileset.gif");
		this.tilemap.loadMap("/Maps/testmap.map");
		this.tilemap.setPosition(0, 0);
		this.tilemap.setTween(0.05);
		
		SkillSet.callInstance().addSkill("Cast Fireball", new CastFireBall());
		SkillSet.callInstance().addSkill("Scratch", new Scratch());
		
		this.bg = new Background("/Backgrounds/grassbg1.png", -0.1);
		

		this.players = new ArrayList<ComplexPlayer>();
		
		Player p = new Player(tilemap, 5, 2500, this);
		p.setPosition(100, 100);
		this.players.add(p);
		
		this.players.set(0, new Player(tilemap, 5, 2500, this));
		this.players.get(0).setPosition(100, 100);
		
		this.hud = new HUD(players.get(0));
		
		this.enemies = new ArrayList<NPC>();
		
		Slugger s = new Slugger(tilemap, players.get(0), this);
		s.setPosition(100, 100);
		this.enemies.add(s);
		
		
	}

	@Override
	public void update() {
		//update player
		for(ComplexPlayer player: players){
			player.update();
			this.tilemap.setPosition(GamePanel.WIDTH/2-player.getX(), GamePanel.HEIGHT/2-player.getY());
		}	
		
		//update background
//		this.bg.setPosition(this.tilemap.getX(), this.tilemap.getY());
		
		//check if player is attacking enemies
//		this.player.checkAttackEnemies(enemies);
		
		//update all enemies
		for(int i = 0; i < this.enemies.size(); i++){
			NPC e = enemies.get(i);
			e.update();
			if(e.isDead()){
				this.enemies.remove(i);
				i--;
			}
		}
		
		//update particles
		if(ParticleEmitter.getInstance().activeElementCount() > 0) {
			ParticleEmitter.getInstance().update();
		}
		
		//update projectiles
		if(ProjectileEmitter.getInstance().activeElementCount() > 0) {
			ProjectileEmitter.getInstance().update();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		
		//draw background
		this.bg.draw(g);
		
		//draw tile map
		this.tilemap.draw(g);
		
		//draw players
		for (ComplexPlayer p : this.players) {
			p.draw(g);
		}
		
		//draw enemies
		for(NPC e: this.enemies){
			e.draw(g);
		}
		
		//draw HUD
		this.hud.draw(g);
		
		//draw particles
		if(ParticleEmitter.getInstance().activeElementCount() > 0) {
			ParticleEmitter.getInstance().draw(g);
		}
		//draw projectiles
		if(ProjectileEmitter.getInstance().activeElementCount() > 0) {
			ProjectileEmitter.getInstance().draw(g);
		}
	}

	@Override
	public void keyPressed(int k) {
		for (ComplexPlayer p : this.players) {
			p.keyPressedAction(k);
		}
//		if(k == KeyEvent.VK_LEFT){
//			player.setLeft(true);
//		}
//		if(k == KeyEvent.VK_RIGHT){
//			player.setRight(true);
//		}
//		if(k == KeyEvent.VK_UP){
//			player.setUp(true);
//		}
//		if(k == KeyEvent.VK_DOWN){
//			player.setDown(true);
//		}
//		if(k == KeyEvent.VK_SPACE){
//			if(this.player.isFalling()){
//				player.setGliding(true);
//			}
//			else
//				player.setJumping(true);
//		}
//		if(k == KeyEvent.VK_Q){
//			player.setScratching();
//			//will trigger scratch damage if enemy is near enough
//			player.scratch(enemies);
//		}
//		if(k == KeyEvent.VK_E){
//			player.setFiring();
//		}
	}

	@Override
	public void keyReleased(int k) {
		for (ComplexPlayer p : this.players) {
			p.keyReleasedAction(k);
		}
//		if(k == KeyEvent.VK_LEFT){
//			player.setLeft(false);
//		}
//		if(k == KeyEvent.VK_RIGHT){
//			player.setRight(false);
//		}
//		if(k == KeyEvent.VK_UP){
//			player.setUp(false);
//		}
//		if(k == KeyEvent.VK_DOWN){
//			player.setDown(false);
//		}
//		if(k == KeyEvent.VK_SPACE){
//			if(this.player.isFalling()){
//				player.setGliding(false);
//			}
//			else
//				player.setJumping(false);
//		}
//		if(k == KeyEvent.VK_E){
//			player.setKeeping();
//		}
	}

}
