package main;

import javax.swing.JFrame;

public class Game {
	
	private String title;
	private GamePanel gp;
	
	public Game(String title, GamePanel gp){
		this.title = title;
		this.gp = gp;
	}
	
	public void startGame(boolean isResizable){
		
		JFrame window = new JFrame(title);
		window.setContentPane(gp);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(isResizable);
		window.pack();
		window.setVisible(true);
		
	}
	
}
