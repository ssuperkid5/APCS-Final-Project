package com.chickenachiever.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.util.ArrayList;

public class Button {

    private State currentState = State.RELEASED;
    private Rectangle box;
    private ArrayList<ActionListener> acLs;
    private String text = "";
    private Color released;
    private Color hover;
    private Color pressed;
    private Font font = new Font("Calisto MT", Font.PLAIN, 50);

    public Button(int x, int y, int width, int height, String text) {
	box = new Rectangle(x, y, width, height);
	acLs = new ArrayList<ActionListener>();
	released = new Color(0, 0, 255);
	hover = new Color(0, 0, 154);
	pressed = new Color(0, 0, 103);
	this.text = text;
    }

    public Button(int x, int y, int width, int height, String text, Font font) {
	box = new Rectangle(x, y, width, height);
	acLs = new ArrayList<ActionListener>();
	released = new Color(0, 0, 255);
	hover = new Color(0, 0, 154);
	pressed = new Color(0, 0, 103);
	this.text = text;
	this.font = font;
    }

    public Button(int x, int y, int width, int height, String text, Font font, int r, int g, int b) {
	box = new Rectangle(x, y, width, height);
	acLs = new ArrayList<ActionListener>();
	released = new Color(r, g, b);
	hover = new Color(r - 101, g - 101, b - 101);
	pressed = new Color(r - 151, g - 151, b - 151);
	this.text = text;
	this.font = font;
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
	if (currentState == State.RELEASED) {
	    g.setColor(released);
	    g.fill(box);
	} else if (currentState == State.HOVER) {
	    g.setColor(hover);
	    g.fill(box);
	} else {
	    g.setColor(pressed);
	    g.fill(box);
	}
	g.setColor(new Color(0, 255, 0));
	g.setFont(font);
	g.drawString(text, box.x + box.width / 2 - (int) (g.getFontMetrics().getStringBounds(text, g).getWidth() / 2), box.y + box.height / 2 + (int) (new TextLayout(text, font, g.getFontRenderContext()).getBounds().getHeight() / 3));
    }

    public void draw(Graphics2D g, Color color) {
	if (currentState == State.RELEASED) {
	    g.setColor(released);
	    g.fill(box);
	} else if (currentState == State.HOVER) {
	    g.setColor(hover);
	    g.fill(box);
	} else {
	    g.setColor(pressed);
	    g.fill(box);
	}
	g.setColor(color);
	g.setFont(font);
	g.drawString(text, box.x + box.width / 2 - (int) (g.getFontMetrics().getStringBounds(text, g).getWidth() / 2), box.y + box.height / 2 + (int) (new TextLayout(text, font, g.getFontRenderContext()).getBounds().getHeight() / 3));
    }

    public void addActionListener(ActionListener listener) {
	acLs.add(listener);
    }

    private enum State {
	RELEASED, HOVER, PRESSED
    }

    public void mouseClicked(MouseEvent e) {
	if (box.contains(e.getPoint()))
	    currentState = State.PRESSED;
    }

    public void mouseDragged(MouseEvent e) {
	if (box.contains(e.getPoint()))
	    currentState = State.PRESSED;
	else
	    currentState = State.RELEASED;
    }

    public boolean mouseMoved(MouseEvent e) {
	if (box.contains(e.getPoint())) {
	    currentState = State.HOVER;
	    return true;
	} else
	    currentState = State.RELEASED;
	return false;
    }

    public void mouseReleased(MouseEvent e) {
	if (box.contains(e.getPoint())) {
	    for (ActionListener al : acLs)
		al.actionPerformed(null);
	}
	currentState = State.RELEASED;
    }

    public int getX() {
	return box.x;
    }

    public int getY() {
	return box.y;
    }

    public int getWidth() {
	return box.width;
    }

    public int getHeight() {
	return box.height;
    }

    public void setText(String text) {
	this.text = text;
    }
}
