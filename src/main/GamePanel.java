package main;

import game_state.core.GameStateManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{


	private static final long serialVersionUID = 1L;
	public static final int SCALE = 1;
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	
	//game thread
	private Thread thread;
	private boolean isRunning;
	private static final int FPS = 60;
	private static final int TARGET_TIME = 1000/FPS;
	private int width;
	private int height;
	
	private BufferedImage image;
	private Graphics2D g;
	
	//Game State Manager
	private GameStateManager gsm;
	
	//constructor
	public GamePanel(int width, int height){
		super();
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width*SCALE, height*SCALE));
		this.setFocusable(true);
		this.requestFocus();
	}
	
	//Methods
	
	private void init(){
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.g = (Graphics2D) this.image.getGraphics();
		this.isRunning = true;
		
		this.gsm = new GameStateManager();
		
	}
	
	public void addNotify(){
		super.addNotify();
		if(this.thread == null){
			this.thread = new Thread(this);
			this.addKeyListener(this);
			this.thread.start();
		}
	}
	
	private void update(){
		this.gsm.update();
	}
	
	private void draw(){
		this.gsm.draw(this.g);
	}
	
	private void drawToScreen(){
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, width*SCALE, height*SCALE, null);
		g.dispose();
	}
	
	//Methods for Runnable
		@Override
		public void run() {
			
			this.init();
			
			long start;
			long elapsed;
			long wait;
			
			//game loop
			while(this.isRunning){
				
				start = System.nanoTime();
				
				this.update();
				this.draw();
				this.drawToScreen();
			
				elapsed = System.nanoTime() - start;
				wait = TARGET_TIME - elapsed / 1000000;
				if(wait < 0)
					wait = 10;
				
				try {
					
					Thread.sleep(wait);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
			
		}
		
	//Methods for KeyListener
	@Override
	public void keyPressed(KeyEvent e) {
		
		this.gsm.keyPressed(e.getKeyCode());
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		
		this.gsm.keyReleased(e.getKeyCode());
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
}











