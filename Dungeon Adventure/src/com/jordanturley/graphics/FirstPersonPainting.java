package com.jordanturley.graphics;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.jordanturley.item.Item;
import com.jordanturley.room.Room;

/**
 * The <code>FirstPersonPainting</code> class extends from JComponent and overrides the paint method, to
 * paint the first person view of the player: the walls, items, and monster in the current room.
 * 
 * @author Jordan Turley
 */
public class FirstPersonPainting extends JComponent {
	private static final long serialVersionUID = 1L;
	
	public static final int ITEM_IMAGE_BOTTOM_MARGIN = 20;

	private int dir;
	private Room curRoom;
	
	public FirstPersonPainting() {
		super();
	}
	
	public void setDirectionLooking(int dir) {
		this.dir = dir;
	}
	
	public void setCurRoom(Room room) {
		curRoom = room;
	}
	
	@Override
	public void paint(Graphics g) {
		int roomImgWidth = getWidth() / 3;
		int itemImgSize = roomImgWidth / 3;
		
		//Get the left, center, and right images for the current room for the direction the player is facing
		ImageIcon[] images = curRoom.getImagesForDirection(dir);
		ImageIcon leftImage = images[Room.LEFT_IMAGE_IDX];
		ImageIcon centerImage = images[Room.CENTER_IMAGE_IDX];
		ImageIcon rightImage = images[Room.RIGHT_IMAGE_IDX];
		
		//Draw the images
		g.drawImage(leftImage.getImage(), 0, 0, roomImgWidth, getHeight(), null);
		g.drawImage(centerImage.getImage(), roomImgWidth, 0, roomImgWidth, getHeight(), null);
		g.drawImage(rightImage.getImage(), roomImgWidth * 2, 0, roomImgWidth, getHeight(), null);
		
		//Go through and draw each of the items in the room
		List<Item> items = curRoom.getItems();
		for (int i1 = 0; i1 < items.size(); i1++) {
			Item item = items.get(i1);
			ImageIcon itemImg = item.getImage();
			
			int startX = itemImgSize * i1 + roomImgWidth;
			int startY = getHeight() - itemImgSize - ITEM_IMAGE_BOTTOM_MARGIN;
			
			g.drawImage(itemImg.getImage(), startX, startY, itemImgSize, itemImgSize, null);
		}
		
		//TODO Draw the monster in the room, if there is one
	}
}
