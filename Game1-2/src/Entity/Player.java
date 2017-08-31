package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Player extends MapObject{

	private int health;
	private int maxHealth;
	
	private boolean dead;
	private boolean flinching;
	private long flinchTime;
	
	//scratch
	private boolean attacking;
	private int attackDamage;
	private int attackRange;
	
	//pickup up equips
	private boolean pickingUp;
	private boolean pickedUpS;
	private boolean pickedUpW;
	
	//animations
	private ArrayList<BufferedImage[]> Ssprites;
	private final int[] SnumFrames = {
			2,4,1,2,2
	};
	private ArrayList<BufferedImage[]> Wsprites;
	private final int[] WnumFrames = {
			2,4,1,2,2
	};
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		2, 4, 1, 2, 2 //corresponds to the number of frames per action
	};
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int ATTACKING = 4;
	
	private int SmaxSpeed = 8;
	private int SmaxFallSpeed = 4;
	private int SattackDamage = 9;
	private int SattackRange = 140;
	
	private int WmaxSpeed = 8;
	private int WmaxFallSpeed = 2;
	private int WattackDamage = 3;
	
	public Player(TileMap tm) {
		super(tm);
		
		width = 70;
		height = 70;
		cwidth = 60;
		cheight = 60;
		
		moveSpeed = 4;
		maxSpeed = 5;
		stopSpeed = .4;
		fallSpeed = .75;
		maxFallSpeed = 5;
		jumpStart = -14.8;
		stopJumpSpeed = 3;
		
		facingRight = true;
		
		health = maxHealth = 5;
		
		attackDamage = 9;
		attackRange = 105;
		//adding sprites of default
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.gif"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 5; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				if(i != 4){
					for (int j = 0; j < numFrames[i]; j++) {
					
					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					}
				} else {
					for (int j = 0; j < numFrames[i]; j++) {
						
						bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
						}
				}
				sprites.add(bi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//adding sprites of sword
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/Splayersprites.gif"));
			Ssprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 5; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				if(i != 4){
					for (int j = 0; j < numFrames[i]; j++) {
					
					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					}
				} else {
					for (int j = 0; j < numFrames[i]; j++) {
						
						bi[j] = spritesheet.getSubimage(j * width * 3, i * height, width * 3, height);
						}
				}
				Ssprites.add(bi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//adding sprites of wand
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/Wplayersprites.gif"));
			Wsprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 5; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				if(i != 4){
					for (int j = 0; j < numFrames[i]; j++) {
					
					bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
					}
				} else {
					for (int j = 0; j < numFrames[i]; j++) {
						
						bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
						}
				}
				Wsprites.add(bi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(100);
	}
	public void changeSstats() {
		maxSpeed = SmaxSpeed;
		maxFallSpeed = SmaxFallSpeed;
		attackDamage = SattackDamage;
		attackRange = SattackRange;
	}
	public void changeWstats(){
		maxSpeed = WmaxSpeed;
		maxFallSpeed = WmaxFallSpeed;
		attackDamage = WattackDamage;
	}
	public int getHealth() {return health; }
	public int getMaxHealth() { return maxHealth; }
	public void setAttacking () {attacking = true;}
	public void checkPickedUp(ArrayList<Equip> equips) {
		for(int i = 0; i < equips.size(); i++ ) {
			Equip e = equips.get(i);
			if(pickingUp){
				if(e.getx() > x - width/2 && e.getx() < x + width/2 && e.gety() > y-height/2 && e.gety() < y+height /2) {
					e.pickUp();
					if (e.getItemNum() == 1) {
						if (pickedUpW){
							pickedUpW = false;
							pickedUpS = true;
							
						} else {
							pickedUpS = true;
						}
					} else {
						if (pickedUpS){
							pickedUpW = true;
							pickedUpS = false;
						} else {
							pickedUpW = true;
						}
					}
			}
			}
		}
		
	}
	private void getNextPosition() {
		
		//left right movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;

			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		//jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		//falling
		if(falling) {
			dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	public void update() {
		
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition (xtemp, ytemp);
		if(pickedUpS){
			sprites = Ssprites;
			changeSstats();
		} else if(pickedUpW){
			sprites = Wsprites;
			changeWstats();
		}
		if(up) {
			pickingUp = true;
		} else {
			pickingUp = false;
		}
		if(currentAction == ATTACKING) {
			if(animation.hasPlayedOnce()) attacking = false;
		}
		if(attacking) {
			if(pickedUpS) {
				if(currentAction != ATTACKING) {
					currentAction = ATTACKING;
					animation.setFrames(sprites.get(ATTACKING));
					animation.setDelay(150);
					width = 210;
				}
			}
			if(currentAction != ATTACKING) {
				currentAction = ATTACKING;
				animation.setFrames(sprites.get(ATTACKING));
				animation.setDelay(150);
				width = 140;
			}
		}
		else if (dy > 0) {
			if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 70;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 70;
			}
		}
		else if(left||right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width = 70;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 70;
			}
		}
		animation.update();
		if(currentAction != ATTACKING){
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
	public void draw(Graphics2D g){
		setMapPosition();
		
		//draw player
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTime)/ 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}
		
}
