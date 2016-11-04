package com.jordanturley.graphics;

import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class MoveButtonsPainting extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private static final String BASE_DIR = "images" + File.separator + "movement" + File.separator;
	private static final ImageIcon UP = new ImageIcon(BASE_DIR + "up.png");
	private static final ImageIcon DOWN = new ImageIcon(BASE_DIR + "down.png");
	private static final ImageIcon LEFT = new ImageIcon(BASE_DIR + "left.png");
	private static final ImageIcon RIGHT = new ImageIcon(BASE_DIR + "right.png");
	private static final ImageIcon TURN_LEFT = new ImageIcon(BASE_DIR + "turn_left.png");
	private static final ImageIcon TURN_RIGHT = new ImageIcon(BASE_DIR + "turn_right.png");

	public MoveButtonsPainting() {
		super();
	}
	
	@Override
	public void paint(Graphics g) {
		int imageWidth = getWidth() / 3;
		int imageHeight = getHeight() / 3;
		g.drawImage(TURN_LEFT.getImage(), 0, 0, imageWidth, imageHeight, null);
		g.drawImage(UP.getImage(), imageWidth, 0, imageWidth, imageHeight, null);
		g.drawImage(TURN_RIGHT.getImage(), imageWidth * 2, 0, imageWidth, imageHeight, null);
		g.drawImage(LEFT.getImage(), 0, imageHeight, imageWidth, imageHeight, null);
		g.drawImage(RIGHT.getImage(), imageWidth * 2, imageHeight, imageWidth, imageHeight, null);
		g.drawImage(DOWN.getImage(), imageWidth, imageHeight * 2, imageWidth, imageHeight, null);
		
		
	}
}
