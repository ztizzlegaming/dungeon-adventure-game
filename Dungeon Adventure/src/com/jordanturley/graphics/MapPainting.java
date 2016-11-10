package com.jordanturley.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

import com.jordanturley.game.Player;
import com.jordanturley.room.Room;

/**
 * The <code>MapPainting</code> class extends from JComponent and overrides the paint method, to
 * paint the rooms and walls to the map JDialog
 * 
 * @author Jordan Turley
 */
public class MapPainting extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private Room[][] rooms;
	private Player player;

	public MapPainting(Room[][] rooms, Player player) {
		super();
		
		this.rooms = rooms;
		this.player = player;
	}
	
	@Override
	public void paint(Graphics g) {
		int roomWidth = getWidth() / rooms[0].length;
		int roomHeight = getHeight() / rooms.length;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (int y = 0; y < rooms.length; y++) {
			for (int x = 0; x < rooms[0].length; x++) {
				Room room = rooms[y][x];
				List<Character> directions = room.getDirections();
				
				int xPixels = x * roomWidth;
				int yPixels = y * roomHeight;
				
				g.setColor(Color.BLACK);
				if (!directions.contains('n')) {
					g.drawLine(xPixels, yPixels, xPixels + roomWidth, yPixels);
				}
				if (!directions.contains('s')) {
					g.drawLine(xPixels, yPixels + roomHeight, xPixels + roomWidth, yPixels + roomHeight);
				}
				if (!directions.contains('e')) {
					g.drawLine(xPixels + roomWidth, yPixels, xPixels + roomWidth, yPixels + roomHeight);
				}
				if (!directions.contains('w')) {
					g.drawLine(xPixels, yPixels, xPixels, yPixels + roomHeight);
				}
				
				//Draw little square for items here
				int numItems = room.getNumItems();
				if (numItems > 0) {
					if (numItems == 1) {
						g.setColor(Color.BLACK);	
					} else if (numItems == 2) {
						g.setColor(Color.GREEN);
					} else if (numItems >= 3) {
						g.setColor(Color.RED);
					}
					g.fillRect(xPixels + 10, yPixels + roomHeight - 20, 10, 10);
				}
			}
		}
		
		int playerXPixels = player.getX() * roomWidth + roomWidth / 2;
		int playerYPixels = player.getY() * roomHeight + roomHeight / 2;
		
		//Rotate the U whichever direction they are facing
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font(g.getFont().getFontName(), Font.PLAIN, 22);
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(90 * player.getDirection()), 0, 0);
		Font rotatedFont = font.deriveFont(affineTransform);
		g2.setFont(rotatedFont);
		g2.drawString("U",playerXPixels,playerYPixels);
	}
}
