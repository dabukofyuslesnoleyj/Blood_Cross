package tile_map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileMap {

	//postion
	private double x;
	private double y;
	
	//bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	//map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	//tileset
	private BufferedImage tileSet;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	//drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	//Constructor
	public TileMap(int tileSize){
		this.tileSize = tileSize;
		
		this.numRowsToDraw = GamePanel.HEIGHT / this.tileSize+2;
		this.numColsToDraw = GamePanel.WIDTH / this.tileSize+2;
		
		
	}
	
	//Methods
	public void loadTiles(String s){
		
		try {
			
			tileSet = ImageIO.read(getClass().getResource(s));
			
			numTilesAcross = tileSet.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subImage;
			for (int col = 0; col < numTilesAcross; col++) {
				subImage = tileSet.getSubimage(col*tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subImage, Tile.NORMAL);
			
				subImage = tileSet.getSubimage(col*tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String s){
		
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			
			map = new int[numRows][numCols];
			width = numCols*tileSize;
			height = numRows*tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;	
			
			String delim = "\\s+";
			for(int row = 0; row < numRows; row++){
				String line = br.readLine();
				String[] tokens = line.split(delim);
				for(int col = 0; col < numCols; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void fixBounds(){
		
		if(this.x < this.xmin)
			this.x = xmin;
		if(this.y < this.ymin)
			this.y = ymin;
		if(this.x > this.xmax)
			this.x = xmax;
		if(this.y > this.ymax)
			this.y = ymax;
		
	}
	
	public void draw(Graphics2D g){
		for(int row = rowOffset; row < rowOffset+numRowsToDraw; row++){
			if(row >= numRows || row < 0)
				break;
			for(int col = colOffset; col < colOffset+numColsToDraw; col++){
				
				if(col >= numCols)
					break;
				
				if(map[row][col] == 0)
					continue;
				
				int rc = map[row][col];
				
				int r = rc / this.numTilesAcross;
				int c = rc % this.numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(),(int)x+col*tileSize,(int)y+row*tileSize,null);
			}
		}
	}

	//Getters and Setters
	public int getNumRows(){ 
		return numRows; 
	}
	
	public int getNumCols(){ 
		return numCols; 
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getType(int row, int col){
		int rc = 0;
		try {
			rc = map[row][col];
		}catch(ArrayIndexOutOfBoundsException e) {
			//TODO: change this
		}
		
		int r = rc / this.numTilesAcross;
		int c = rc % this.numTilesAcross;
		
		return tiles[r][c].getType();
	}
	
	public void setTween(double tween){
		this.tween = tween;
	}
	
	public void setPosition(double x, double y){
		
		this.x += (x - this.x) * this.tween;
		this.y += (y - this.y) * this.tween;
		
		this.fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
		
	}
	
}
