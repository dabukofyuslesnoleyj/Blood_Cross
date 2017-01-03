package entity.core;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import game_state.core.PlayState;
import main.GamePanel;
import tile_map.Tile;
import tile_map.TileMap;

public abstract class MapObject {
	
	protected PlayState state;

	//tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//sprite dimesions
	protected int width;
	protected int height;
	
	//collision box
	protected int cwidth;
	protected int cheight;
	
	//collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	
	//animation
	protected Animation animation;
	protected int currAction;
	protected int prevAction;
	protected boolean isFacingRight;
	
	//movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	//movement attributes
	protected double movementSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	//constructor
	public MapObject(TileMap tm, PlayState state){
		this.tileMap = tm;
		this.state = state;
		this.tileSize = tm.getTileSize();
		this.animation = new Animation();
	}
	
	//methods
	public boolean intersects (MapObject o){
		Rectangle me = this.getRectangle();
		Rectangle otherObject = o.getRectangle();
		
		return me.intersects(otherObject);
	}
	
	public Rectangle getRectangle(){
		return new Rectangle((int)x - cwidth, (int)y - cheight, cwidth, cheight);
	}
	
	//new collision methods
	public void checkTileMapCollision(){
		this.currCol = (int)x / this.tileSize;
		this.currRow = (int)y / this.tileSize;
		
		this.xdest = this.x + this.dx;
		this.ydest = this.y + this.dy;
		
		this.xtemp = this.x;
		this.ytemp = this.y;
		
		double[] destColBox = getColBox(this.xdest, this.ydest);
		
		
		int leftTile = (int)Math.floor(destColBox[2] / tileSize);
		int rightTile = (int)Math.ceil((destColBox[3] / tileSize)) - 1;
		int topTile = (int)Math.floor(destColBox[0] / tileSize);
		int bottomTile = (int)Math.floor((destColBox[1] / tileSize));
		
		/*
		System.out.println("left:"+leftTile);
        System.out.println("right:"+rightTile);
        System.out.println("top:"+topTile);
        System.out.println("btm:"+bottomTile);
        System.out.println("dy:"+this.dy);
        System.out.println("dx:"+this.dx);
        System.out.println("ytemp:"+this.ytemp);
        System.out.println("xtemp:"+this.xtemp);
        System.out.println("yBtm:"+(bottomTile*tileSize));
        System.out.println("bottom:"+((y + cheight / 2 - 1)));
        System.out.println("bottomcolbox:"+destColBox[1]);
        System.out.println("left:"+((x - cwidth / 2)));
        System.out.println("leftcolbox:"+destColBox[2]);
        System.out.println("------------");
        */

        	if(this.dx!=0){
			//colBox = getColBox(this.xdest, this.y);
			if(this.dx < 0){
				if(checkXCollision(leftTile, topTile, bottomTile)){
					this.dx = 0;
					
					
					int cWidthIsOdd = 0;
					if(this.cwidth%2==1)
						cWidthIsOdd = 1;
					this.xtemp = ((leftTile * tileSize) + tileSize) + (this.cwidth/2) + cWidthIsOdd;
					/*
					System.out.println("new xtemp:"+this.xtemp);
					System.out.println("XCOL");
					*/
					
				}
				else
					this.xtemp += this.dx;
			}
			else if(this.dx > 0){
				if(checkXCollision(rightTile, topTile, bottomTile)){
					this.dx = 0;
					
					int cWidthIsOdd = 0;
					if(this.cwidth%2==1)
						cWidthIsOdd = 1;
					this.xtemp = ((rightTile * tileSize)) - (this.cwidth/2) + cWidthIsOdd;
					/*
					System.out.println("new xtemp:"+this.xtemp);
					System.out.println("XCOL");
					*/
				}
				else
					this.xtemp += this.dx;
			}
		}
		
		destColBox = getColBox(this.xtemp, this.ydest);
		
		
		leftTile = (int)Math.floor(destColBox[2] / tileSize);
		rightTile = (int)Math.ceil((destColBox[3] / tileSize)) - 1;
		topTile = (int)Math.floor(destColBox[0] / tileSize);
		bottomTile = (int)Math.floor((destColBox[1] / tileSize));
		
		if(this.dy!=0){
			//colBox = getColBox(this.x, this.ydest);
			if(this.dy < 0){
				if(checkYCollision(topTile, leftTile, rightTile))
					this.dy = 0;
				else
					this.ytemp += this.dy;
			}
			else if(this.dy > 0){
				if(checkYCollision(bottomTile, leftTile, rightTile)){
					this.dy = 0;
					this.falling = false;
					
					int cHeightIsOdd = 1;
					if(this.cheight%2==1)
						cHeightIsOdd = 0;
					this.ytemp = ((bottomTile * tileSize) - (this.cheight/2)) + cHeightIsOdd;
					/*
					System.out.println("new ytemp:"+this.ytemp);
					System.out.println("collision");
					*/
				}				
				else
					this.ytemp += this.dy;
			}
		}
		
       		if(!falling){
			if(!checkYCollision(bottomTile, leftTile, rightTile)){
				this.falling = true;
			}
		}
		
	}
	
	private double[] getColBox(double x, double y){
		double[] colBox = new double[4];
		colBox[0] = y - this.cheight/2;	//top bound        
		colBox[1] = y + this.cheight/2 - 1;	//bottom bound 
		colBox[2] = x - this.cwidth/2;	//left bound       
		colBox[3] = x + this.cwidth/2 - 1;	//right bound  
		return colBox;
	}
	
	private boolean checkYCollision(int yTile, int leftTile, int rightTile){
		boolean hasCollision = false;
		
		for(int x = leftTile; x <= rightTile ; x++){
			if(this.tileMap.getType(yTile, x) == Tile.BLOCKED){
				hasCollision = true;
				break;
			}
		}
		
		return hasCollision;
	}
	
	private boolean checkXCollision(int xTile, int topTile, int bottomTile){
		boolean hasCollision = false;
		
		for(int y = topTile; y < bottomTile ; y++){
			if(this.tileMap.getType(y, xTile) == Tile.BLOCKED){
				hasCollision = true;
				break;
			}
		}
		
		return hasCollision;
	}
	
	public int getCurrRow() {
		return currRow;
	}

	public int getCurrCol() {
		return currCol;
	}
	
	public int getCheight() {
		return cheight;
	}

	public void setCheight(int cheight) {
		this.cheight = cheight;
	}

	public int getX() {
		return (int)x;
	}

	public int getY() {
		return (int)y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCwidth() {
		return cwidth;
	}
	
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition(){
		this.xmap = this.tileMap.getX();
		this.ymap = this.tileMap.getY();
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	public boolean notOnScreen(){
		return this.x + this.xmap + this.width < 0 ||
				this.x + this.xmap - this.width > GamePanel.WIDTH ||
				this.y + this.ymap + this.height < 0 ||
				this.y + this.ymap - this.height > GamePanel.HEIGHT;
				
	}
	
	public void update(){
		this.checkTileMapCollision();
		this.setPosition(xtemp, ytemp);
	}
	
	public void draw(Graphics2D g){
		this.setMapPosition();
		
		if(this.isFacingRight){
			g.drawImage(this.animation.getImage(),(int)(x+xmap-width/2),(int)(y+ymap-height/2),null);
		}
		else{
			g.drawImage(this.animation.getImage(),(int)(x+xmap-width/2+width),(int)(y+ymap-height/2),-width,height,null);
		}
	}

	public PlayState getState() {
		return state;
	}

	public void setState(PlayState state) {
		this.state = state;
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public boolean isFacingRight() {
		return isFacingRight;
	}

	public void setFacingRight(boolean isFacingRight) {
		this.isFacingRight = isFacingRight;
	}
	
	public double getStopSpeed() {
		return stopSpeed;
	}

	public void setStopSpeed(double stopSpeed) {
		this.stopSpeed = stopSpeed;
	}
	
	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	public boolean isWalking() {
		return this.left || this.right;
	}
	
}
