package com.chickenachiever.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.chickenachiever.map.TileMap;

public class Launcher extends MapElement {

	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 1 };
	private static final int IDLE = 0;

	public Launcher(TileMap tm) {
		super(tm);
	}

	public void update(Player p) {
		if (intersects(p)){
			p.fallSpeed = -0.4;
		}
		else 
			p.fallSpeed = 0.15;
		
	}

	public void draw(Graphics2D g2d) {
	}

}
