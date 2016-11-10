package com.jordanturley.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.jordanturley.game.Player;

/**
 * The <code>MoveButtonsPainting</code> class extends from JComponent and overrides the paint method, to
 * paint the move buttons, the direction the player is facing, and the health of the player
 * 
 * @author Jordan Turley
 */
public class MoveButtonsPainting extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private static final String BASE_DIR = "images" + File.separator + "movement" + File.separator;
	private static final ImageIcon UP = new ImageIcon(BASE_DIR + "up.png");
	private static final ImageIcon DOWN = new ImageIcon(BASE_DIR + "down.png");
	private static final ImageIcon LEFT = new ImageIcon(BASE_DIR + "left.png");
	private static final ImageIcon RIGHT = new ImageIcon(BASE_DIR + "right.png");
	private static final ImageIcon TURN_LEFT = new ImageIcon(BASE_DIR + "turn_left.png");
	private static final ImageIcon TURN_RIGHT = new ImageIcon(BASE_DIR + "turn_right.png");
	
	private Player player;
	
	public MoveButtonsPainting(Player player) {
		super();
		
		this.player = player;
	}
	
	@Override
	public void paint(Graphics g) {
		//Calculate the width and height of the buttons, based on the width and height of this whole component
		int imageWidth = getWidth() / 3;
		int imageHeight = getHeight() / 4;
		
		//Paint all of the arrow images at the right spots
		g.drawImage(TURN_LEFT.getImage(), 0, 0, imageWidth, imageHeight, null);
		g.drawImage(UP.getImage(), imageWidth, 0, imageWidth, imageHeight, null);
		g.drawImage(TURN_RIGHT.getImage(), imageWidth * 2, 0, imageWidth, imageHeight, null);
		g.drawImage(LEFT.getImage(), 0, imageHeight, imageWidth, imageHeight, null);
		g.drawImage(RIGHT.getImage(), imageWidth * 2, imageHeight, imageWidth, imageHeight, null);
		g.drawImage(DOWN.getImage(), imageWidth, imageHeight * 2, imageWidth, imageHeight, null);
		
		//Draw the player's direction and health below the buttons
		String str = "Dir:";
		int dir = player.getDirection();
		if (dir == Player.DIR_NORTH) {
			str += "N";
		} else if (dir == Player.DIR_EAST) {
			str += "E";
		} else if (dir == Player.DIR_SOUTH) {
			str += "S";
		} else {
			str += "W";
		}
		str += " HP:" + player.getHealth() + "/" + player.getMaxHealth();
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 22));
		g.drawString(str, 0, imageHeight * 3 + imageHeight / 2 + Window.TEXT_OFFSET);
	}
}
