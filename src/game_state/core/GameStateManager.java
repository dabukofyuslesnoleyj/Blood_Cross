package game_state.core;

import game_state.Level1State;
import game_state.MenuState;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

	private ArrayList<GameState> gameStates;
	
	private int currentState;
	
	public static final int MENU_STATE = 0;
	public static final int LVL_1_STATE = 1;
	
	//Consastructor
	public GameStateManager(){
		
		this.gameStates = new ArrayList<GameState>();
		
		currentState = MENU_STATE;
		this.gameStates.add(new MenuState(this));
		this.gameStates.add(new Level1State(this));
		
	}
	
	//Methods
	public void setCurrentState(int currentState){
		
		if(currentState > -1 && currentState < this.gameStates.size()){
			this.currentState  = currentState;
			this.gameStates.get(this.currentState).init();
		}
	}
	
	public void update(){
		this.gameStates.get(this.currentState).update();
	}

	public void draw(Graphics2D g){
		this.gameStates.get(this.currentState).draw(g);
	}
	
	public void keyPressed(int k){
		this.gameStates.get(this.currentState).keyPressed(k);
	}
	
	public void keyReleased(int k){
		this.gameStates.get(this.currentState).keyReleased(k);
	}
	
}







