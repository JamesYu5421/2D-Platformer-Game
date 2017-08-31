package Entity;

import TileMap.TileMap;

public class Equip extends MapObject{
	
	protected int itemNum;
	protected boolean pickedUp;

	public Equip(TileMap tm) {
		super(tm);
		width = 70;
		height = 70;
		cwidth = 70;
		cheight = 70;
		
		moveSpeed = 0;
		maxSpeed = 0;
		stopSpeed = 0;
		fallSpeed = 1;
		maxFallSpeed = 3;
		jumpStart = 0;
		stopJumpSpeed = 0;
		
		
	}
	public void pickUp(){
		pickedUp = true;
	}
	public boolean isPickedUp(){
		return pickedUp;
	}
	public void update(){
		// update position
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//update animation
		animation.update();
	}
	public int getItemNum() {
		return itemNum;
	}

}
