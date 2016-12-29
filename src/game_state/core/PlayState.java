package game_state.core;

import java.util.ArrayList;

import entity.HUD;
import entity.core.NPC;
import entity.player.Player;
import entity.player.complex.ComplexPlayer;

public abstract class PlayState extends GameState{
	
	protected ArrayList<ComplexPlayer> players;
	protected ArrayList<NPC> enemies;
	protected HUD hud;
	public ArrayList<ComplexPlayer> getPlayer() {
		return players;
	}
	public void setPlayer(ArrayList<ComplexPlayer> players) {
		this.players = players;
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
