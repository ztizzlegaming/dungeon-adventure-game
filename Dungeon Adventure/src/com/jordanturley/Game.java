package com.jordanturley;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jordanturley.trigger.Trigger;
import com.jordanturley.trigger.TriggerFactory;

public class Game {
	private Room[][] rooms;
	
	private List<Trigger> triggers;
	
	private Player player;
	
	private boolean running;
	
	public Game(String filename) throws IOException {
		triggers = new ArrayList<Trigger>();
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String monstersNum = reader.readLine();
		String[] monstersNumParts = monstersNum.split(":");
		int numMonsters = Integer.parseInt(monstersNumParts[1]);
		for (int i1 = 0; i1 < numMonsters; i1++) {
			String monsterStr = reader.readLine();
			String[] monsterParts = monsterStr.split(":");
			
			String monsterName = monsterParts[0];
			
			String healthStr = monsterParts[1];
			String[] healthStrParts = healthStr.split("-");
			int bottomHealth = Integer.parseInt(healthStrParts[0]);
			int topHealth = Integer.parseInt(healthStrParts[1]);
			
			String damageStr = monsterParts[2];
			String[] damageStrParts = damageStr.split("-");
			int bottomDamage = Integer.parseInt(damageStrParts[0]);
			int topDamage = Integer.parseInt(damageStrParts[1]);
			
			int randomPercentage = Integer.parseInt(monsterParts[3]);
			
			System.out.println(monsterName); //Just to show it's being read in
			
			//pass these to MonsterFactory to get a Monster object
		}
		
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
			
			System.out.println(itemName); //just to show it's being read in
			
			//pass these to ItemFactory to get an item
		}
		
		
		String heightWidth = reader.readLine();
		String[] heightWidthParts = heightWidth.split(",");
		
		int height = Integer.parseInt(heightWidthParts[0]);
		int width = Integer.parseInt(heightWidthParts[1]);
		rooms = new Room[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				String desc = reader.readLine();
				String items = reader.readLine();
				String directions = reader.readLine();
				
				Room room = new Room(desc, items, directions);
				rooms[y][x] = room;				
			}
		}
		
		String startLocXYStr = reader.readLine();
		String[] startLocXY = startLocXYStr.split(",");
		int startX = Integer.parseInt(startLocXY[1]);
		int startY = Integer.parseInt(startLocXY[0]);
		
		String maxInventorySizeStr = reader.readLine();
		int maxInventorySize = Integer.parseInt(maxInventorySizeStr);
		
		player = new Player(startX, startY, width, height, maxInventorySize);
		
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
		
		reader.close();
		
		running = false;
	}
	
	public static void main(String[] args) {
		try {
			Game game = new Game("maze.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
