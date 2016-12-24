package sprite;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage spriteSheet;
	private BufferedImage[][] frames;
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
		try {
			
			this.rows = this.spriteSheet.getHeight() / this.yThreshold;
			this.cols = this.spriteSheet.getWidth() / this.xThreshold;
			
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < cols; j++){
					BufferedImage frame = spriteSheet.getSubimage(j*xThreshold, 
							i*yThreshold, xThreshold, yThreshold);
					if(isValid(frame))
						this.frames[i][j] = frame; //checks if subimage is not fully transparent/blank
					else
						break;	//stops parsing the next images in the row
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] getRow(int row){
		try {
		
			BufferedImage[] bi = new BufferedImage[cols];
			
			for(int i = 0; i < cols; i++){
				bi[i] = frames[row][i];
			}
			
			return bi;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	private BufferedImage[] convert_2Dto1D(){
		
		BufferedImage[] bi = new BufferedImage[this.rows*this.cols];
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				bi[i+j] = frames[i][j];
			}
		}
		
		return bi;
	}
	
	public BufferedImage[] getFrames(int... args){
		
		BufferedImage[] bi = new BufferedImage[args.length];
		BufferedImage[] sheet = convert_2Dto1D();
		for(int i = 0; i < sheet.length; i++) {
			for (int j = 0; j < args.length; j++) {
				if(args[j] == i)
					bi[j] = sheet[i];
			}
		}		
		
		return bi;
		
	}
	
	public BufferedImage getFrame(int frame){
		BufferedImage[] sheet = convert_2Dto1D();
		return sheet[frame];
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
