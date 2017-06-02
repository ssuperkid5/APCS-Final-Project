package com.chickenachiever.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.chickenachiever.map.TileMap;
import com.chickenachiever.map.TileMapRevamp;

public class Corpse extends PlayerRevamp {

	private PlayerRevamp player;
	private long spawnTime;

	public Corpse(TileMapRevamp map, PlayerRevamp player) {
		super(map, player.getx(), player.gety());
		this.player = player;
		setPosition(player.getx(), player.gety());
		dx = player.dx;
		dy = player.dy;
		facingRight = player.facingRight;
		jumping = player.jumping;
		falling = player.falling;
		
		spawnTime = System.nanoTime()/1000000;
		
	}

	public void update() {
		super.update();
	}

	public void draw(Graphics2D graph) {
		//if (((System.nanoTime()*1000000) - spawnTime) < 10000) {
			super.draw(graph);
		//}
	}
	
	public int timeSinceSpawned(){
		return (int)((System.nanoTime()/1000000) - spawnTime);
	}
	
	protected void loadSprites(){
		try {
			BufferedImage loadSprites = ImageIO.read(getClass().getResourceAsStream("/Elements/Chickenv4.gif"));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < 4; i++) {
				
				BufferedImage[] images = new BufferedImage[numFrames[i]];
				for (int j = 0; j < images.length; j++) {
					images[j] = loadSprites.getSubimage(j * width, i * height, width, height);
				}
				sprites.add(images);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
