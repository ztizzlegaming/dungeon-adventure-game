package com.jordanturley.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import com.jordanturley.item.ItemFactory;
import com.jordanturley.monster.MonsterFactory;
import com.jordanturley.room.Room;
import com.jordanturley.room.RoomFactory;

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
	
	private Player player;
	
	/**
	 * Creates a new Game object, reading in the map from the given filename
	 * @param filename The file to read the map from
	 * @throws IOException If the map file cannot be found
	 */
	public Game(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		readInMonsters(reader);
		readInItems(reader);
		readInRooms(reader);
		readInPlayerInfo(reader);
		
		reader.close();
	}
	
	/**
	 * Reads in the monsters and their stats from the maze file
	 * @param reader The reader that has already been initialized with the maze file
	 * @throws IOException If something goes wrong with the reader
	 */
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
			String xpStr = monsterParts[4];
			
			MonsterFactory.createMonsterType(monsterName, healthStr, damageStr, randomPercentageStr, xpStr);
		}
	}
	
	/**
	 * Reads in the items and their stats from the maze file
	 * @param reader The reader that has already been initialized with the maze file
	 * @throws IOException If something goes wrong with the reader
	 */
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
	
	/**
	 * Reads in the rooms (the monster in each room, and the items) from the maze file
	 * @param reader The reader that has already been initialized with the maze file
	 * @throws IOException If something goes wrong with the reader
	 */
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
	
	/**
	 * Reads in the information for the player (their starting position and max carry weight) from the maze file
	 * @param reader The reader that has already been initialized with the maze file
	 * @throws IOException If something goes wrong with the reader
	 */
	private void readInPlayerInfo(BufferedReader reader) throws IOException {
		String startLocXYStr = reader.readLine();
		String[] startLocXY = startLocXYStr.split(",");
		int startX = Integer.parseInt(startLocXY[1]);
		int startY = Integer.parseInt(startLocXY[0]);
		
		String maxInventorySizeStr = reader.readLine();
		int maxInventorySize = Integer.parseInt(maxInventorySizeStr);
		
		player = new Player(startX, startY, width, height, maxInventorySize);
	}
	
	public Room[][] getRooms() {
		return rooms;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Moves the player forward, depending on their direction,
	 * making sure they do not go through a wall
	 */
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
	
	/**
	 * Moves the player backward, depending on their direction,
	 * making sure they do not go through a wall
	 */
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
	
	/**
	 * Moves the player left, depending on their direction,
	 * making sure they do not go through a wall
	 */
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
	
	/**
	 * Moves the player forward, depending on their direction,
	 * making sure they do not go through a wall
	 */
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
	
	/**
	 * Gets the current room, based on the player's current location
	 * @return The current room that the player is in
	 */
	public Room getCurRoom() {
		return rooms[player.getY()][player.getX()];
	}
}
