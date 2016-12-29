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
	protected boolean topLeft_isBlocked;
	protected boolean topRight_isBlocked;
	protected boolean bottomLeft_isBlocked;
	protected boolean bottomRight_isBlocked;
	
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
	
	public void calculateCorners(double x, double y){
		int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;

        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
                topLeft_isBlocked = topRight_isBlocked = bottomLeft_isBlocked = bottomRight_isBlocked = false;
                return;
        }
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        topLeft_isBlocked = tl == Tile.BLOCKED;
        topRight_isBlocked = tr == Tile.BLOCKED;
        bottomLeft_isBlocked = bl == Tile.BLOCKED;
        bottomRight_isBlocked = br == Tile.BLOCKED;
	}
	
	public void checkTileMapCollision(){
		
		this.currCol = (int)x / this.tileSize;
		this.currRow = (int)y / this.tileSize;
		
		this.xdest = this.x + this.dx;
		this.ydest = this.y + this.dy;
		
		this.xtemp = this.x;
		this.ytemp = this.y;
		
		
		this.calculateCorners(this.x, this.ydest);
		if(dy < 0){
			if(topLeft_isBlocked || topRight_isBlocked){
				this.dy = 0;
				this.ytemp = this.currRow * this.tileSize + this.cheight / 2;
			}
			else
				this.ytemp += this.dy;
		}
		if(dy > 0){
			if(bottomLeft_isBlocked || bottomRight_isBlocked){
				this.dy = 0;
				this.falling = false;
//no need for this//	this.ytemp = (this.currRow+1) * this.tileSize - this.cheight / 2;
			}
			else
				this.ytemp += this.dy;
		}
		
		this.calculateCorners(xdest, y);
		if(dx < 0){
			if(topLeft_isBlocked || bottomLeft_isBlocked){
				this.dx = 0;
				this.xtemp = this.currCol * this.tileSize + this.cwidth / 2;
			}
			else
				this.xtemp += this.dx;
		}
		if(dx > 0){
			if(topRight_isBlocked || bottomRight_isBlocked){
				this.dx = 0;
				this.xtemp = (this.currCol+1) * this.tileSize - this.cwidth / 2;
			}
			else
				this.xtemp += this.dx;
		}
		
		if(!falling){
			this.calculateCorners(x, ydest+1);
			if(!bottomLeft_isBlocked && !bottomRight_isBlocked)
				this.falling = true;
		}
		
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
	
}
