package main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.enemies.Slugger;
import sprite.Spritesheet;

public class Driver {

	public static void main(String[] args) {
		new Game("Game Tutorial", new GamePanel(1366,768)).startGame(false);
	}
	
}
