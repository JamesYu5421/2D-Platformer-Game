package Equips;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Equip;
import TileMap.TileMap;

public class foxMask extends Equip{
	
	private BufferedImage[] sprites;
	
	public foxMask(TileMap tm){
		super(tm);
		
		itemNum = 1;
		moveSpeed = 0;
		maxSpeed = 0;
		fallSpeed = 3;
		maxFallSpeed = 3;
		
		width = 70;
		height = 70;
		cwidth = 50;
		cheight = 50;
		
		
		//load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Equips/Untitled.gif"));
			sprites = new BufferedImage[2];
			for(int i= 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(700);
		facingRight = true;
	}
	private void getNextPosition() {
		//movement
		if(falling) {
			dy+=fallSpeed;
		}
	}
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//update animation
		animation.update();
	}
	public void draw(Graphics2D g) {
		//if(notOnScreen()) return;
		setMapPosition();
		super.draw(g);
	}

	

}
