package com.chickenachiever.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.chickenachiever.achievements.Achieve;
import com.chickenachiever.achievements.Property;
import com.chickenachiever.main.GamePanel;
import com.chickenachiever.map.TileMap;
import com.chickenachiever.model.Block;
import com.chickenachiever.model.Corpse;
import com.chickenachiever.model.DSpike;
import com.chickenachiever.model.Launcher;
import com.chickenachiever.model.MapElement;
import com.chickenachiever.model.Player;
import com.chickenachiever.model.USpike;

public class LevelState extends GameState {

	private TileMap tileMap;

	private Player player;
	private ArrayList<Corpse> corpses;
	private Achieve achieve;
	private ArrayList<MapElement> objectList;
	
	private int blocksTouched;
	private int spikesTouched;
	private int launchersTouched;
	
	private final int totalBlocks = 90;
	private final int totalSpikes = 12;
	private final int totalLaunchers = 15;
	private int totalAchievements = 11;
	
	private int deathCount;
	private long lifeStart;
	private long gameStart;
	private long currentTime;
	private int clicks = 0;

	public LevelState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public void init() {

		deathCount = 0;
		lifeStart = System.currentTimeMillis()/1000;
		gameStart = System.currentTimeMillis()/1000;
		blocksTouched = 0;
		spikesTouched = 0;
		launchersTouched = 0;
		
		tileMap = new TileMap(32);
		try {
			tileMap.loadTiles("/Maps/Level 1.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		player = new Player(tileMap, 450, 230);
		corpses = new ArrayList<Corpse>();
		achieve = new Achieve();
		
		objectList = tileMap.getElements();
		int count = 0;
		while ((objectList != null) && (count < objectList.size())) {
			MapElement e = objectList.get(count);
			e.setPlayer(player);
			//System.out.println(count);
			count++;
		}
		/* template for touching x blocks
		 * 	
		 * 
		 * createProperty("xblocks", 0, achieve.ACTIVE_IF_GREATER, x - 1);
		 * setValue(blocksTouched); Property[] xblocks =
		 * {myProperties.get("xblocks")}; achieve.createAchievement("xblocks",
		 * xblocks);
		 */
		
		achieve.createProperty("fiveBlocks", 0, achieve.ACTIVE_IF_GREATER, 4);
		Property[] fiveBlocks = {achieve.getProperties().get("fiveBlocks")}; 
		achieve.createAchievement("Gettin' Touchy",fiveBlocks);
		 

		achieve.createProperty("allBlocks", 0, achieve.ACTIVE_IF_GREATER, totalBlocks-1);
		Property[] allblocks = { achieve.getProperties().get("allBlocks") };
		achieve.createAchievement("Painter", allblocks);

		
		/* template for touching x amount of spikes
		 * 
		 * createProperty("xspikes", 0, achieve.ACTIVE_IF_GREATER, x - 1);
		 * Property[] xspikes = {achieve.getProperties().get("xspikes")};
		 * achieve.createAchievement("xspikes", xspikes);
		 */
		
		  
		 achieve.createProperty("fiveSpikes", 0, achieve.ACTIVE_IF_GREATER, 4);
		 Property[] fiveSpikes = {achieve.getProperties().get("fiveSpikes")};
		 achieve.createAchievement("These Blasted Spikes", fiveSpikes);
		 

		achieve.createProperty("allSpikes", 0, achieve.ACTIVE_IF_GREATER, totalSpikes-1);
		Property[] allspikes = { achieve.getProperties().get("allSpikes") };
		achieve.createAchievement("Minced Chicken", allspikes);

		/*
		 * template for launcher achievements
		 * 
		 * achieve.createProperty("xlaunchers", 0, achieve.ACTIVE_IF_GREATER, x- 1); 
		 * Property[] xlaunchers = {achieve.getProperties().get("xlaunchers")};
		 * achieve.createAchievement("xlaunchers", xlaunchers);
		 */
		
		 achieve.createProperty("fiveLaunchers", 0, achieve.ACTIVE_IF_GREATER, 4); 
		 Property[] fiveLaunchers = {achieve.getProperties().get("fiveLaunchers")};
		 achieve.createAchievement("WHEEE!", fiveLaunchers); 
		 

		achieve.createProperty("allLaunchers", 0, achieve.ACTIVE_IF_EQUAL, totalLaunchers);
		Property[] allLaunchers = { achieve.getProperties().get("allLaunchers") };
		achieve.createAchievement("Chickens can Fly", allLaunchers);

	
		//template for dying x times
		 achieve.createProperty("dieOnce", 0, achieve.ACTIVE_IF_GREATER, 0);
		 Property[] dieOneTime = {achieve.getProperties().get("dieOnce")};
		 achieve.createAchievement("First Blood", dieOneTime);
		 
		 achieve.createProperty("dieTenTimes", 0, achieve.ACTIVE_IF_GREATER, 9);
		 Property[] dieTenTimes = {achieve.getProperties().get("dieTenTimes")};
		 achieve.createAchievement("Used to It", dieTenTimes);
		 
		// all achievements unlocked
		achieve.createProperty("allUnlocked", 0, achieve.ACTIVE_IF_EQUAL, totalAchievements);
		Property[] allAchievements = {achieve.getProperties().get("allUnlocked")};
		achieve.createAchievement("Caught 'em All", allAchievements);
		
		/* template for timeAlive
		 * 
		 * achieve.createProperty("spent xtime Alive", 0, achieve.ACTIVE_IF_GREATER, xtime);
		Property[] timeAlive = {achieve.getProperties().get("spent xtime Alive")};
		achieve.createAchievement("spent xtime Alive", timeAlive); */

		achieve.createProperty("10sec Alive", 0, achieve.ACTIVE_IF_GREATER, 9);
		Property[] tenSecondsAlive = {achieve.getProperties().get("10sec Alive")};
		achieve.createAchievement("Stayin' Alive", tenSecondsAlive);
		
		achieve.createProperty("60sec Alive", 0, achieve.ACTIVE_IF_GREATER, 59);
		Property[] sixtySecondsAlive = {achieve.getProperties().get("60sec Alive")};
		achieve.createAchievement("Old Mother Hen", sixtySecondsAlive);
		
		
		
		achieve.createProperty("Played for 1min", 0, achieve.ACTIVE_IF_GREATER, 59);
		Property[] oneMin = {achieve.getProperties().get("Played for 1min")};
		achieve.createAchievement("Forgot to Close Game", oneMin);
		lifeStart = System.currentTimeMillis()/1000;
		
		//click achievement
		achieve.createProperty("clicked", 0, achieve.ACTIVE_IF_EQUAL, 2);
		Property[] oneClick = {achieve.getProperties().get("clicked")};
		achieve.createAchievement("Chickens eat Mice", oneClick);
	}

	@Override
	public void update() {
		
		// TODO Auto-generated method stub
		objectList = tileMap.getElements();
		//System.out.println("objectlist size" + objectList.size());
		
		currentTime = System.currentTimeMillis()/1000;
		achieve.setPropValue("10sec Alive", (int)(currentTime - lifeStart));
		achieve.setPropValue("Played for 1min", (int) (currentTime - gameStart));
		achieve.setPropValue("60sec Alive", (int)(currentTime - lifeStart)); 
		
		int count = 0;
		while ((objectList != null) && (count < objectList.size())) {
			MapElement e = objectList.get(count);
			e.update();
			//System.out.println(count);
			if(e instanceof Block && e.isTouched() && !e.checked())
			{
				blocksTouched++;
				e.nowChecked();
				achieve.setPropValue("fiveBlocks", blocksTouched);
				achieve.setPropValue("allBlocks", blocksTouched);
			}
			
			else if((e instanceof DSpike || e instanceof USpike) && e.isTouched() && !e.checked())
			{
				spikesTouched++;
				e.nowChecked();
				achieve.setPropValue("fiveSpikes", spikesTouched);
				achieve.setPropValue("allSpikes", spikesTouched);
			}
			
			else if((e instanceof Launcher) && e.isTouched() && !e.checked())
			{
				launchersTouched++;
				e.nowChecked();
				achieve.setPropValue("fiveLaunchers", launchersTouched);
				achieve.setPropValue("allLaunchers", launchersTouched);
			}
			count++;
		}
		//System.out.println("Count:" + count);
		if(player.isAlive()){
			player.update();
		}else if(!player.isAlive()){
			deathCount++;
			lifeStart = System.currentTimeMillis()/1000;
			corpses.add(player.spawnCorpse());
			player.respawn();
		}
		/*achieve.setPropValue("testproperty", 1);
		ArrayList<Achievement> achieved = achieve.checkAchievements();
		for (int i = 0; i < achieved.size(); i++){
			System.out.println(achieved.get(i).getName());
		}*/
		achieve.setPropValue("dieOnce", deathCount);
		achieve.setPropValue("dieTenTimes", deathCount);
		achieve.setPropValue("allUnlocked", achieve.checkAchievements().size());
		
		
		for (int i = 0; i < corpses.size(); i++) {
			corpses.get(i).update();
			if (corpses.size() > 0) {
				if (corpses.get(i).timeSinceSpawned() > 5000) {
					corpses.remove(i);
					i--;
				}
			}
		}
	}

	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, GamePanel.WIDTH * GamePanel.SCALE, GamePanel.HEIGHT * GamePanel.SCALE);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.PWIDTH * GamePanel.SCALE, GamePanel.PHEIGHT * GamePanel.SCALE);

		tileMap.draw(g);
		player.draw(g);
		for (int i = 0; i < corpses.size(); i++) {
			corpses.get(i).draw(g);
			if (corpses.size() > 0) {
				if (corpses.get(i).timeSinceSpawned() > 5000) {
					corpses.remove(i);
					i--;
				}
			}

		}	
		achieve.draw(g);
	}

	public void keyPressed(int k) {
		// TODO Auto-generated method stub

		if (k == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		if (k == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		if (k == KeyEvent.VK_UP) {
			player.setJumping(true);
		}
		if (k == KeyEvent.VK_UP) {
			player.setJumping(true);
		}
		if (k == KeyEvent.VK_1) {
			player.setJumping(true);
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if (k == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if (k == KeyEvent.VK_UP) {
			player.setJumping(false);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicks++;
		if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2 ){
			achieve.setPropValue("clicked", clicks);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
