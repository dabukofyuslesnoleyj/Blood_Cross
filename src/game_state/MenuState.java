package game_state;

import game_state.core.GameState;
import game_state.core.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import tile_map.Background;

public class MenuState extends GameState {

	private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {
			"Start",
			"Quit"
	};
	
	private Color titleColor;
	
	private Font titleFont;
	private Font font;
	
	//Constructor
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 28);
			
			font = new Font("Arial", Font.PLAIN, 12);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Methods
	private void select(){
		switch(this.currentChoice){
		case 0: 
			this.gsm.setCurrentState(GameStateManager.LVL_1_STATE);
			break;
		case 1: 
			//help
			break;
		case 2: 
			//exit
			System.exit(0);
			break;
		}
	}
	
	//Inherited Methods
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		this.bg.update();
		
	}

	@Override
	public void draw(Graphics2D g) {
		this.bg.draw(g);
		
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Game Tutorial", 70, 70);
		
		g.setFont(font);
		for (int i = 0; i < options.length; i++) {
			if(i == currentChoice){
				g.setColor(Color.BLACK);
			}
			else{
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 140+i*15);
		}
		
	}

	@Override
	public void keyPressed(int k) {
		switch(k){
		case KeyEvent.VK_SPACE:
			select();
			break;
		case KeyEvent.VK_UP:
			currentChoice--;
			if(currentChoice < 0)
				currentChoice = options.length - 1;
			break;
		case KeyEvent.VK_DOWN:
			currentChoice = (currentChoice + 1) % this.options.length;
			break;
		}	
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
