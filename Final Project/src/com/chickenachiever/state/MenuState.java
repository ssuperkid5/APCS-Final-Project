package com.chickenachiever.state;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.chickenachiever.main.GamePanel;
import com.chickenachiever.map.Background;
import com.chickenachiever.map.Button;

public class MenuState extends GameState {
    private Background bg;
    private int currentChoice = 0;
    private ImageIcon i = new ImageIcon(getClass().getResource("/Backgrounds/Menu.png"));
    private ArrayList<Button> buttons;

    public MenuState(GameStateManager gsm) {
	this.gsm = gsm;
	try {
	    this.bg = new Background(this.i, 1.0D);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	buttons = new ArrayList<Button>(); 
	buttons.add(new Button(GamePanel.WIDTH / 2 - 110, 500, 300, 200, "Play", new Font("Helvetica", Font.PLAIN, 60)));
	buttons.add(new Button(GamePanel.WIDTH / 2 - 110, 310, 220, 60, "Quit"));
	buttons.add(new Button(GamePanel.WIDTH / 2 - 110, 400, 220, 60, "Credits"));

	buttons.get(1).addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});
    }

    public void init() {
    }

    public void update() {
    }

    public void draw(Graphics2D g) {
	bg.draw(g);
	for (Button b: buttons)
	    b.draw(g);
    }

    public void mouseClicked(MouseEvent e) {
	for (Button b: buttons)
	    b.mouseClicked(e);
    }

    public void mouseDragged(MouseEvent e) {
	for (Button b: buttons)
	    b.mouseDragged(e);
    }

    public void mouseMoved(MouseEvent e) {
	for (Button b: buttons)
	    b.mouseMoved(e);
    }

    public void mouseReleased(MouseEvent e) {
	for (Button b: buttons)
	    b.mouseReleased(e);
    }
    
    public void keyReleased(int k) {
    }

    public void keyPressed(int k) {
    }
}