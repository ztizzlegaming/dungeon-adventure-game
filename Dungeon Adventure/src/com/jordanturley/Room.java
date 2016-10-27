package com.jordanturley;

import java.util.ArrayList;
import java.util.List;

import com.jordanturley.item.Item;
import com.jordanturley.item.ItemFactory;
import com.jordanturley.monster.Monster;
import com.jordanturley.monster.MonsterFactory;

public class Room {
	private Monster monster;
	private List<Item> items;
	private List<Character> directions;
	
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
}
