package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.*;
import Equips.foxMask;
import Equips.helmet;
//import Entity.Enemies.*;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level extends GameState{

	private TileMap tileMap;
	private Background bg;
	private Player player;
	private ArrayList<Equip> equips;
	
	public Level(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		tileMap = new TileMap(70); // each tile is 70 by 70
		tileMap.loadTiles("/Tilesets/levelTiles.gif"); //uploads the tile sprites
		tileMap.loadMap("/Maps/level.map");
		tileMap.setPosition(0, 0);
		bg = new Background("/Backgrounds/grassbg2.gif", .1); //The second paramter is move speed and its moving at 10% speed
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		equips = new ArrayList<Equip>();
		foxMask f = new foxMask(tileMap);
		f.setPosition(300, 100);
		equips.add(f);
		helmet h = new helmet(tileMap);
		h.setPosition(600, 100);
		equips.add(h);
		
	}
	
	public void update(){
		//updates player
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT/2- player.gety());
		
		
		//set position of the background of the level
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//checks to see if the player picked up equips
		player.checkPickedUp(equips);
		
		//updates the equips
		for(int i = 0; i < equips.size(); i++) {
			Equip e = equips.get(i);
			e.update();
			if(e.isPickedUp()){
				equips.remove(i);
				i--;
			}
		}
		
	}
	public void draw(Graphics2D g) {
		//draw background
		bg.draw(g);
		
		//draw tiles
		tileMap.draw(g);
		
		//draw player
		player.draw(g);
		
		//draw equips
		for(int i = 0; i < equips.size(); i++) {
			equips.get(i).draw(g);
		}
	}
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_LEFT){
			player.setLeft(true);
		}
		if(k == KeyEvent.VK_RIGHT){
			player.setRight(true);
		}
		if(k == KeyEvent.VK_Z){
			player.setUp(true);
		}
		if(k == KeyEvent.VK_DOWN){
			player.setDown(true);
		}
		if(k == KeyEvent.VK_SPACE) {
			player.setJumping(true);
		}
		if(k == KeyEvent.VK_SHIFT) {
			player.setAttacking();
		}
	}
	public void keyReleased(int k){
		if (k == KeyEvent.VK_LEFT){
			player.setLeft(false);
			
		}
		if(k == KeyEvent.VK_RIGHT){
			player.setRight(false);
		}
		if(k == KeyEvent.VK_Z){
			player.setUp(false);
		}
		if(k == KeyEvent.VK_DOWN){
			player.setDown(false);
		}
		if(k == KeyEvent.VK_SPACE) {
			player.setJumping(false);
		}
	}
}