package com.jordanturley.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jordanturley.graphics.Window;
import com.jordanturley.item.ItemFactory;
import com.jordanturley.monster.MonsterFactory;
import com.jordanturley.room.Room;
import com.jordanturley.room.RoomFactory;
import com.jordanturley.trigger.Trigger;
import com.jordanturley.trigger.TriggerFactory;

/**
 * The <code>Game</code> class is used to play the dungeon adventure game.
 * Create a new Game object, passing it the name of the map file, then call play()
 * 
 * @author Jordan Turley
 */
public class Game {
	public static final Random RANDOM = new Random();
	
	private int width;
	private int height;
	
	private Room[][] rooms;
	
	private List<Trigger> triggers;
	
	private Player player;
	
	/**
	 * Creates a new Game object, reading in the map from the given filename
	 * @param filename The file to read the map from
	 * @throws IOException If the map file cannot be found
	 */
	public Game(String filename) throws IOException {
		triggers = new ArrayList<Trigger>();
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		readInMonsters(reader);
		readInItems(reader);
		readInRooms(reader);
		readInPlayerInfo(reader);
		readInTriggers(reader);
		
		reader.close();
	}
	
	private void readInMonsters(BufferedReader reader) throws IOException {
		String monstersNum = reader.readLine();
		String[] monstersNumParts = monstersNum.split(":");
		int numMonsters = Integer.parseInt(monstersNumParts[1]);
		for (int i1 = 0; i1 < numMonsters; i1++) {
			String monsterStr = reader.readLine();
			String[] monsterParts = monsterStr.split(":");
			
			String monsterName = monsterParts[0];
			String healthStr = monsterParts[1];
			String damageStr = monsterParts[2];
			String randomPercentageStr = monsterParts[3];
			
			MonsterFactory.createMonsterType(monsterName, healthStr, damageStr, randomPercentageStr);
		}
	}
	
	private void readInItems(BufferedReader reader) throws IOException {
		String itemsNum = reader.readLine();
		String[] itemsNumParts = itemsNum.split(":");
		int numItems = Integer.parseInt(itemsNumParts[1]);
		for (int i1 = 0; i1 < numItems; i1++) {
			String itemStr = reader.readLine();
			String[] itemParts = itemStr.split(":");
			
			String itemName = itemParts[0];
			String itemType = itemParts[1];
			String itemEffectValue = itemParts[2];
			String itemWeight = itemParts[3];
			
			ItemFactory.createItemType(itemName, itemType, itemEffectValue, itemWeight);
		}
	}
	
	private void readInRooms(BufferedReader reader) throws IOException {
		String heightWidth = reader.readLine();
		String[] heightWidthParts = heightWidth.split(",");
		
		height = Integer.parseInt(heightWidthParts[0]);
		width = Integer.parseInt(heightWidthParts[1]);
		rooms = new Room[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				String monster = reader.readLine();
				String items = reader.readLine();
				String directions = reader.readLine();
				
				Room room = RoomFactory.getRoom(monster, items, directions);
				rooms[y][x] = room;				
			}
		}
	}
	
	private void readInPlayerInfo(BufferedReader reader) throws IOException {
		String startLocXYStr = reader.readLine();
		String[] startLocXY = startLocXYStr.split(",");
		int startX = Integer.parseInt(startLocXY[1]);
		int startY = Integer.parseInt(startLocXY[0]);
		
		String maxInventorySizeStr = reader.readLine();
		int maxInventorySize = Integer.parseInt(maxInventorySizeStr);
		
		player = new Player(startX, startY, width, height, maxInventorySize);
	}
	
	private void readInTriggers(BufferedReader reader) throws IOException {
		String triggerStr = reader.readLine();
		while (triggerStr != null) {
			String activated = reader.readLine();
			String activationMethod = reader.readLine();
			String whatActivates = reader.readLine();
			String effectType = reader.readLine();
			String roomAffected = reader.readLine();
			String textAddToRoom = reader.readLine();
			String itemsToAdd = reader.readLine();
			String itemsToRemove = reader.readLine();
			String triggerText = reader.readLine();
			
			Trigger trigger = TriggerFactory.getTrigger(activated, activationMethod, whatActivates, effectType, roomAffected, textAddToRoom, itemsToAdd, itemsToRemove, triggerText);
			triggers.add(trigger);
			
			triggerStr = reader.readLine();
		}
	}
	
	public Room[][] getRooms() {
		return rooms;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void movePlayerForward() {
		int dir = player.getDirection();
		switch (dir) {
		case Player.DIR_NORTH:
			if (getCurRoom().hasDirection('n')) {
				player.moveForward();
			}
			break;
		case Player.DIR_SOUTH:
			if (getCurRoom().hasDirection('s')) {
				player.moveForward();
			}
			break;
		case Player.DIR_EAST:
			if (getCurRoom().hasDirection('e')) {
				player.moveForward();
			}
			break;
		case Player.DIR_WEST:
			if (getCurRoom().hasDirection('w')) {
				player.moveForward();
			}
			break;
		}
	}
	
	public void movePlayerBack() {
		int dir = player.getDirection();
		switch (dir) {
		case Player.DIR_NORTH:
			if (getCurRoom().hasDirection('s')) {
				player.moveBack();
			}
			break;
		case Player.DIR_SOUTH:
			if (getCurRoom().hasDirection('n')) {
				player.moveBack();
			}
			break;
		case Player.DIR_EAST:
			if (getCurRoom().hasDirection('w')) {
				player.moveBack();
			}
			break;
		case Player.DIR_WEST:
			if (getCurRoom().hasDirection('e')) {
				player.moveBack();
			}
			break;
		}
	}
	
	public void movePlayerLeft() {
		int dir = player.getDirection();
		switch (dir) {
		case Player.DIR_NORTH:
			if (getCurRoom().hasDirection('w')) {
				player.moveLeft();
			}
			break;
		case Player.DIR_SOUTH:
			if (getCurRoom().hasDirection('e')) {
				player.moveLeft();
			}
			break;
		case Player.DIR_EAST:
			if (getCurRoom().hasDirection('n')) {
				player.moveLeft();
			}
			break;
		case Player.DIR_WEST:
			if (getCurRoom().hasDirection('s')) {
				player.moveLeft();
			}
			break;
		}
	}
	
	public void movePlayerRight() {
		int dir = player.getDirection();
		switch (dir) {
		case Player.DIR_NORTH:
			if (getCurRoom().hasDirection('e')) {
				player.moveRight();
			}
			break;
		case Player.DIR_SOUTH:
			if (getCurRoom().hasDirection('w')) {
				player.moveRight();
			}
			break;
		case Player.DIR_EAST:
			if (getCurRoom().hasDirection('s')) {
				player.moveRight();
			}
			break;
		case Player.DIR_WEST:
			if (getCurRoom().hasDirection('n')) {
				player.moveRight();
			}
			break;
		}
	}
	
	public Room getCurRoom() {
		return rooms[player.getY()][player.getX()];
	}
	
	/**
	 * Starts the game
	 */
	public void play() {
		
	}
}
