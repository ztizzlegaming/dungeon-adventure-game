package com.jordanturley.room;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.jordanturley.game.Player;
import com.jordanturley.item.Item;
import com.jordanturley.item.ItemFactory;
import com.jordanturley.monster.Monster;
import com.jordanturley.monster.MonsterFactory;

/**
 * 
 * 
 * @author Jordan Turley
 */
public class Room {
	public static final int LEFT_IMAGE_IDX = 0;
	public static final int CENTER_IMAGE_IDX = 1;
	public static final int RIGHT_IMAGE_IDX = 2;
	
	public static final int NORTH_IMAGES_IDX = 0;
	public static final int SOUTH_IMAGES_IDX = 1;
	public static final int EAST_IMAGES_IDX = 2;
	public static final int WEST_IMAGES_IDX = 3;
	
	private Monster monster;
	private List<Item> items;
	private List<Character> directions;
	
	private ImageIcon[] northImages;
	private ImageIcon[] eastImages;
	private ImageIcon[] southImages;
	private ImageIcon[] westImages;
	
	public Room(String monsterStr, String itemsStr, String directionsStr) {
		String[] monsterParts = monsterStr.split(":");
		String monsterName = monsterParts[1];
		if (monsterName.equals("none")) {
			monster = null;
		} else {
			monster = MonsterFactory.getMonster(monsterName);
		}
		
		items = new ArrayList<Item>();
		if (!itemsStr.equals("Items:none")) {
			String[] itemsTemp = itemsStr.split(":");
			String[] itemsArr = itemsTemp[1].split(",");
			for (String itemName : itemsArr) {
				Item item = ItemFactory.getItem(itemName);
				items.add(item);
			}
		}
		
		directions = new ArrayList<Character>();
		String[] directionsArr = directionsStr.split(",");
		for (String directionStr : directionsArr) {
			char direction = Character.toLowerCase(directionStr.charAt(0));
			directions.add(direction);
		}
	}
	
	public void setImages(ImageIcon[][] allImages) {
		northImages = allImages[Room.NORTH_IMAGES_IDX];
		southImages = allImages[Room.SOUTH_IMAGES_IDX];
		eastImages = allImages[Room.EAST_IMAGES_IDX];
		westImages = allImages[Room.WEST_IMAGES_IDX];
	}
	
	public ImageIcon[] getImagesForDirection(int dir) {
		ImageIcon[] arr;
		if (dir == Player.DIR_NORTH) {
			arr = northImages;
		} else if (dir == Player.DIR_EAST) {
			arr = eastImages;
		} else if (dir == Player.DIR_SOUTH) {
			arr = southImages;
		} else {
			arr = westImages;
		}
		return arr;
	}
	
	public List<Character> getDirections() {
		return directions;
	}
	
	public boolean hasDirection(char dir) {
		return directions.contains(dir);
	}
	
	public int getNumItems() {
		return items.size();
	}
}
