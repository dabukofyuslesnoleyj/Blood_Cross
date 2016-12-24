package sprite;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage spriteSheet;
	private ArrayList<BufferedImage> frameList;
	private int xThreshold;
	private int yThreshold;
	private int rows;
	private int cols;
	
	public Spritesheet(String url){
		
		try {
			spriteSheet = ImageIO.read(getClass().getResourceAsStream(url));			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Spritesheet(BufferedImage spriteSheet){
		try {
			
			this.spriteSheet = spriteSheet;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setThresholds(int xThreshold, int yThreshold){
		this.xThreshold = xThreshold;
		this.yThreshold = yThreshold;
	}
	
	public void parse(){
		
		this.frameList = new ArrayList<BufferedImage>();
		
		try {
			
			this.rows = this.spriteSheet.getHeight() / this.yThreshold;
			this.cols = this.spriteSheet.getWidth() / this.xThreshold;
			
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < cols; j++){
					BufferedImage frame = spriteSheet.getSubimage(j*xThreshold, 
							i*yThreshold, xThreshold, yThreshold);
					if(isValid(frame)){
						this.frameList.add(frame); //adds if the frame is not blank
					}
					else
						break;	//stops parsing the next images in the row
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] getFrames(int... args){
		
		//giving no input will return the entire sheet
		if(args.length == 0){
			BufferedImage[] bi = new BufferedImage[frameList.size()];
			for (int i = 0; i < frameList.size(); i++) {
				bi[i] = frameList.get(i);
			}
			
			return bi;
		}
		
		BufferedImage[] bi = new BufferedImage[args.length];
		
		for(int i = 0; i < args.length; i++){
			for(int j = 0; j < frameList.size(); j++){
				if(args[i] == j)
					bi[i] = frameList.get(j);
			}
		}
		
		return bi;
		
	}
	
	/**
	 * checks if the image is fully transparent or not.
	 * @param img - image to be checked
	 * @return returns true if the image is valid (i.e. not fully transparent), false if invalid (i.e. fully transparent or blank)
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static boolean isValid(BufferedImage img) throws IOException, InterruptedException {
	    //img = img.getScaledInstance(100, -1, Image.SCALE_FAST); for faster processing (will compress image so it might be inaccurate especially with low-dimension images)
	    int w = img.getWidth(null);
	    int h = img.getHeight(null);
	    boolean isValid = false;
	    for (int i = 0 ; i < h; i++) {
	    	for(int j = 0 ; j < w; j++){
	    		Color c = new Color(img.getRGB(i, j), true);
		        if (c.getAlpha() != 0) {
		            isValid = true;
		            break;
		        }
	    	}
	    }
	    return isValid;
	}
}
