package entity;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.player.Player;

public class HUD {

	private Player player;
	
	private BufferedImage image;
	private Font font;
	
	public HUD(Player player){
		this.player = player;
		
		//load image
		try {
			
			image = ImageIO.read(this.getClass().getResourceAsStream("/HUD/hud.gif"));
			
			this.font = new Font("Arial", Font.PLAIN, 14);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g){
		
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.drawString(player.getHealth()+"/"+player.getMaxHealth(), 30, 25);
		g.drawString(player.getEnergy()/100 +"/"+player.getMaxEnergy()/100, 30, 45);
		
	}
	
}
