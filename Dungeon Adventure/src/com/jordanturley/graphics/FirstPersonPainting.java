package com.jordanturley.graphics;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.jordanturley.room.Room;

public class FirstPersonPainting extends JComponent {
	private static final long serialVersionUID = 1L;

	private Room curRoom;
	private int dir;
	
	public FirstPersonPainting() {
		super();
	}
	
	public void setCurRoom(Room room) {
		curRoom = room;
	}
	
	public void setDirectionLooking(int dir) {
		this.dir = dir;
	}
	
	@Override
	public void paint(Graphics g) {
		int imgWidth = getWidth() / 3;
		
		ImageIcon[] images = curRoom.getImagesForDirection(dir);
		ImageIcon leftImage = images[Room.LEFT_IMAGE_IDX];
		ImageIcon centerImage = images[Room.CENTER_IMAGE_IDX];
		ImageIcon rightImage = images[Room.RIGHT_IMAGE_IDX];
		
		g.drawImage(leftImage.getImage(), 0, 0, imgWidth, getHeight(), null);
		g.drawImage(centerImage.getImage(), imgWidth, 0, imgWidth, getHeight(), null);
		g.drawImage(rightImage.getImage(), imgWidth * 2, 0, imgWidth, getHeight(), null);
	}
}
