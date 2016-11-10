package com.jordanturley.room;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * The <code>RoomFactory</code> class is used to create new <code>Room</code> objects. To use,
 * you call the getRoom method, passing it the strings read in from the maze file for the
 * monster, items, and directions you can go.
 * <br><br>
 * The RoomFactory makes use of the Flyweight pattern for the images for the rooms.
 * 
 * @author Jordan Turley
 */
public class RoomFactory {
	private static Map<String, ImageIcon> IMAGES = new HashMap<String, ImageIcon>();
	public static Room getRoom(String monsterStr, String itemsStr, String directionsStr) {
		Room room = new Room(monsterStr, itemsStr, directionsStr);
		
		int roomColor = (int) (Math.random() * 4) + 1;
		roomColor = 1; //TODO Crop the rest of the pictures for the other colors
		String imageBase = "images" + File.separator + "rooms" + File.separator + "texture" + roomColor + "_";
		
		ImageIcon[][] allImages = new ImageIcon[4][];
		ImageIcon[] northImages = new ImageIcon[3];
		
		if (room.hasDirection('w')) {
			northImages[Room.LEFT_IMAGE_IDX] = getImageFromMap(imageBase + "left_open.png");
		} else {
			northImages[Room.LEFT_IMAGE_IDX] = getImageFromMap(imageBase + "left.png");
		}
		
		if (room.hasDirection('n')) {
			northImages[Room.CENTER_IMAGE_IDX] = getImageFromMap(imageBase + "center_open.png");
		} else {
			northImages[Room.CENTER_IMAGE_IDX] = getImageFromMap(imageBase + "center.png");
		}
		
		if (room.hasDirection('e')) {
			northImages[Room.RIGHT_IMAGE_IDX] = getImageFromMap(imageBase + "right_open.png");
		} else {
			northImages[Room.RIGHT_IMAGE_IDX] = getImageFromMap(imageBase + "right.png");
		}
		
		allImages[Room.NORTH_IMAGES_IDX] = northImages;
		
		
		ImageIcon[] southImages = new ImageIcon[3];
		if (room.hasDirection('e')) {
			southImages[Room.LEFT_IMAGE_IDX] = getImageFromMap(imageBase + "left_open.png");
		} else {
			southImages[Room.LEFT_IMAGE_IDX] = getImageFromMap(imageBase + "left.png");
		}
		
		if (room.hasDirection('s')) {
			southImages[Room.CENTER_IMAGE_IDX] = getImageFromMap(imageBase + "center_open.png");
		} else {
			southImages[Room.CENTER_IMAGE_IDX] = getImageFromMap(imageBase + "center.png");
		}
		
		if (room.hasDirection('w')) {
			southImages[Room.RIGHT_IMAGE_IDX] = getImageFromMap(imageBase + "right_open.png");
		} else {
			southImages[Room.RIGHT_IMAGE_IDX] = getImageFromMap(imageBase + "right.png");
		}
		
		
		allImages[Room.SOUTH_IMAGES_IDX] = southImages;
		
		ImageIcon[] eastImages = new ImageIcon[3];
		
		if (room.hasDirection('n')) {
			eastImages[Room.LEFT_IMAGE_IDX] = getImageFromMap(imageBase + "left_open.png");
		} else {
			eastImages[Room.LEFT_IMAGE_IDX] = getImageFromMap(imageBase + "left.png");
		}
		
		if (room.hasDirection('e')) {
			eastImages[Room.CENTER_IMAGE_IDX] = getImageFromMap(imageBase + "center_open.png");
		} else {
			eastImages[Room.CENTER_IMAGE_IDX] = getImageFromMap(imageBase + "center.png");
		}
		
		if (room.hasDirection('s')) {
			eastImages[Room.RIGHT_IMAGE_IDX] = getImageFromMap(imageBase + "right_open.png");
		} else {
			eastImages[Room.RIGHT_IMAGE_IDX] = getImageFromMap(imageBase + "right.png");
		}
		
		allImages[Room.EAST_IMAGES_IDX] = eastImages;
		
		ImageIcon[] westImages = new ImageIcon[3];
		
		if (room.hasDirection('s')) {
			westImages[Room.LEFT_IMAGE_IDX] = getImageFromMap(imageBase + "left_open.png");
		} else {
			westImages[Room.LEFT_IMAGE_IDX] = getImageFromMap(imageBase + "left.png");
		}
		
		if (room.hasDirection('w')) {
			westImages[Room.CENTER_IMAGE_IDX] = getImageFromMap(imageBase + "center_open.png");
		} else {
			westImages[Room.CENTER_IMAGE_IDX] = getImageFromMap(imageBase + "center.png");
		}
		
		if (room.hasDirection('n')) {
			westImages[Room.RIGHT_IMAGE_IDX] = getImageFromMap(imageBase + "right_open.png");
		} else {
			westImages[Room.RIGHT_IMAGE_IDX] = getImageFromMap(imageBase + "right.png");
		}
		
		allImages[Room.WEST_IMAGES_IDX] = westImages;
		
		room.setImages(allImages);
		
		return room;
	}
	
	private static ImageIcon getImageFromMap(String imageName) {
		ImageIcon image = null;
		if (IMAGES.containsKey(imageName)) {
			image = IMAGES.get(imageName);
		} else {
			image = new ImageIcon(imageName);
			IMAGES.put(imageName, image);
		}
		return image;
	}
}
