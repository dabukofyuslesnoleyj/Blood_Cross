package entity.core;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean hasPlayed;
	
	//constructor
	public Animation(){
		this.hasPlayed = false;
	}
	
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		this.currentFrame = 0;
		this.startTime = System.nanoTime();
		this.hasPlayed = false;
	}

	public void setDelay(long delay){
		this.delay = delay;
	}
	
	public void setCurrentFrame(int currentFrame){
		this.currentFrame = currentFrame;
	}
	
	public void update(){
		
		if(this.delay == -1)
			return;
		
		long elapsed = (System.nanoTime() - this.startTime) / 1000000;
		if(elapsed > this.delay){
			this.currentFrame++;
			this.startTime = System.nanoTime();
		}
		if(this.currentFrame == frames.length){
			this.currentFrame = 0;
			this.hasPlayed = true;
		}
		
	}
	
	public int getCurrentFrame(){
		return this.currentFrame;
	}
	
	public BufferedImage getImage(){
		return this.frames[this.currentFrame];
	}
	
	public boolean hasPlayedOnce(){
		return this.hasPlayed;
	}
	
}
