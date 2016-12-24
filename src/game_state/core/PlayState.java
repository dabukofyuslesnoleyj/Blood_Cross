package game_state.core;

import java.util.ArrayList;

import entity.HUD;
import entity.core.NPC;
import entity.player.Player;

public abstract class PlayState extends GameState{
	
	protected Player player;
	protected ArrayList<NPC> enemies;
	protected HUD hud;
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public ArrayList<NPC> getEnemies() {
		return enemies;
	}
	public void setEnemies(ArrayList<NPC> enemies) {
		this.enemies = enemies;
	}
	public HUD getHud() {
		return hud;
	}
	public void setHud(HUD hud) {
		this.hud = hud;
	}
	
	

}
