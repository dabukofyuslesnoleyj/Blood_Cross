package game_state.core;

import java.util.ArrayList;

import entity.HUD;
import entity.core.NPC;
import entity.core.complex.ComplexNPC;
import entity.player.Player;
import entity.player.complex.ComplexPlayer;

public abstract class PlayState extends GameState{
	
	protected ArrayList<ComplexPlayer> players;
	protected ArrayList<ComplexNPC> enemies;
	protected HUD hud;
	public ArrayList<ComplexPlayer> getPlayer() {
		return players;
	}
	public void setPlayer(ArrayList<ComplexPlayer> players) {
		this.players = players;
	}
	public ArrayList<ComplexNPC> getEnemies() {
		return enemies;
	}
	public void setEnemies(ArrayList<ComplexNPC> enemies) {
		this.enemies = enemies;
	}
	public HUD getHud() {
		return hud;
	}
	public void setHud(HUD hud) {
		this.hud = hud;
	}
	
	

}
