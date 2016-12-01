package com.jordanturley.item;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * The <code>ItemFactory</code> class is used to get new Item objects. First, call createItemType, passing it
 * all the information for a specific item, from the text file when user's define what items are in the game.
 * Then, call getItem, passing the name of the item, to get a new item object.
 * 
 * This class uses the Flyweight pattern
 * 
 * @author Jordan Turley
 */
public class ItemFactory {
	private static final Map<String, ImageIcon> IMAGES = new HashMap<String, ImageIcon>();
	
	private static final Map<String, Item> ITEMS = new HashMap<String, Item>();
	
	/**
	 * Creates a new type of item. For any item, this must be called for that item before getItem is called for it.
	 * @param name The name of the item
	 * @param type The type of item (Weapon, Armor, or Health)
	 * @param effectValue The value for the item (For a weapon, the range of damage. For armor, the damage reduction.
	 * For a health item, the amount of health that is restored)
	 * @param weightStr The weight of the item
	 */
	public static void createItemType(String name, String type, String effectValue, String weightStr) {
		//Get the image for the item
		ImageIcon image = null;
		if (!IMAGES.containsKey(name)) {
			String imagePath = "/images/items/" + name + ".png";
			image = new ImageIcon(ItemFactory.class.getResource(imagePath));
			IMAGES.put(name, image);
		} else {
			image = IMAGES.get(name);
		}
		
		//Parse weight into int
		int weight = Integer.parseInt(weightStr);
		
		//Create Item object, depending on what type of item it is
		Item item = null;
		
		switch (type) {
		case "armor":
			int damageReduction = Integer.parseInt(effectValue);
			item = new Armor(name, weight, true, image, damageReduction);
			break;
		case "weapon":
			String[] damageParts = effectValue.split("-");
			int minDamage = Integer.parseInt(damageParts[0]);
			int maxDamage = Integer.parseInt(damageParts[1]);
			item = new Weapon(name, weight, true, image, minDamage, maxDamage);
			break;
		case "health":
			int healthRestore = Integer.parseInt(effectValue);
			item = new HealthItem(name, weight, true, image, healthRestore);
			break;
		default:
			throw new IllegalArgumentException("The item type was not recognized.");
		}
		
		ITEMS.put(name, item);
	}
	
	/**
	 * Generates a new Item object
	 * @param itemName The name of the item
	 * @return The new Item object
	 */
	public static Item getItem(String itemName) {
		Item item = ITEMS.get(itemName);
		if (item == null) {
			//This will happen if the user doesn't declare an item in the first part of the text file
			throw new IllegalStateException("The item (" + itemName + ") you tried to get was not found. Did you forget to declare it?");
		}
		return item;
	}
}
