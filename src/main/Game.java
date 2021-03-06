package main;

import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import mechanics.skills.core.SkillSet;

public class Game {
	
	private String title;
	private GamePanel gp;
	
	public Game(String title, GamePanel gp){
		this.title = title;
		this.gp = gp;
	}
	
	public void startGame(boolean isResizable){
		
		final JFrame fullscreenFrame = new JFrame(title);
 		fullscreenFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 		fullscreenFrame.setUndecorated(true);
 		fullscreenFrame.setResizable(isResizable);
 		fullscreenFrame.validate();
 		fullscreenFrame.setContentPane(gp);
 		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(fullscreenFrame);
		
	}
	
}
