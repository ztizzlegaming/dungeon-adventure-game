package com.jordanturley.game;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.jordanturley.graphics.InventoryPainting;
import com.jordanturley.item.Armor;
import com.jordanturley.item.HealthItem;
import com.jordanturley.item.Item;
import com.jordanturley.item.Weapon;

/**
 * The <code>Player</code> class stores everything for the player, like their direction, inventory,
 * (x,y) position in the maze, health, active weapon, and active armor.
 * 
 * @author Jordan Turley
 */
public class Player {
	public static final int DIR_NORTH = 0;
	public static final int DIR_EAST = 1;
	public static final int DIR_SOUTH = 2;
	public static final int DIR_WEST = 3;
	
	/**
	 * The maximum number of items (not depending on weight) the player can hold, based on
	 * how many items can be shown on the screen.
	 */
	public static final int MAX_INVENTORY_ITEMS = InventoryPainting.NUM_ITEMS_WIDTH * InventoryPainting.NUM_ITEMS_HEIGHT;
	
	public static final Weapon FISTS = new Weapon(
			"fists",
			0,
			false,
			new ImageIcon(Player.class.getResource("/images/items/fists.png")),
			2,
			4);
	
	public static final Armor T_SHIRT = new Armor(
			"t shirt",
			0,
			false,
			new ImageIcon(Player.class.getResource("/images/items/t shirt.png")),
			0);
	
	private int x;
	private int y;
	private int dir;
	
	private int health;
	private int maxHealth;
	
	private int xp;
	private int level;
	private int levelUpXp;
	
	private Weapon activeWeapon;
	private Armor activeArmor;
	
	private final int mazeWidth;
	private final int mazeHeight;
	
	private List<Item> inventory;
	private int inventoryWeight;
	private int maxInventoryWeight;
	
	/**
	 * Creates a new Player at (0, 0), with a max inventory size of 10
	 */
	public Player(int mazeWidth, int mazeHeight) {
		this(0, 0, mazeWidth, mazeHeight, 10);
	}
	
	/**
	 * Creates a new Player at (x, y) with a given max inventory size
	 * @param x The initial x location of the player
	 * @param y The initial y location of the player
	 * @param maxInventorySize The maximum number of items the player can hold
	 */
	public Player(int x, int y, int mazeWidth, int mazeHeight, int maxInventorySize) {
		this.x = x;
		this.y = y;
		dir = DIR_NORTH;
		health = 100;
		maxHealth = 100;
		
		xp = 0;
		level = 1;
		levelUpXp = 10;
		
		this.mazeWidth = mazeWidth;
		this.mazeHeight = mazeHeight;
		
		inventory = new ArrayList<Item>();
		this.maxInventoryWeight = maxInventorySize;
		
		activeWeapon = FISTS;
		inventory.add(FISTS);
		
		activeArmor = T_SHIRT;
		inventory.add(T_SHIRT);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public List<Item> getInventory() {
		return inventory;
	}
	
	public int getNumItems() {
		return inventory.size();
	}
	
	public int getDirection() {
		return dir;
	}
	
	public Weapon getActiveWeapon() {
		return activeWeapon;
	}
	
	public Armor getActiveArmor() {
		return activeArmor;
	}
	
	public int getInventoryWeight() {
		return inventoryWeight;
	}
	
	public int getMaxInventoryWeight() {
		return maxInventoryWeight;
	}
	
	public int getHealth() {
		return health;
	}
	
	public boolean isAlive() {
		return health > 0;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getXp() {
		return xp;
	}
	
	public int getLevelUpXp() {
		return levelUpXp;
	}
	
	public int getLevel() {
		return level;
	}
	
	
	/**
	 * Moves the player forward, based on the direction they are facing
	 */
	public void moveForward() {
		switch (dir) {
		case DIR_NORTH:
			y--;
			break;
		case DIR_SOUTH:
			y++;
			break;
		case DIR_EAST:
			x++;
			break;
		case DIR_WEST:
			x--;
			break;
		}
		
		checkOutOfBounds();
	}
	
	/**
	 * Moves the player backward, based on the direction they are facing
	 */
	public void moveBack() {
		switch (dir) {
		case DIR_NORTH:
			y++;
			break;
		case DIR_SOUTH:
			y--;
			break;
		case DIR_EAST:
			x--;
			break;
		case DIR_WEST:
			x++;
			break;
		}

		checkOutOfBounds();
	}
	
	/**
	 * Moves the player left, based on the direction they are facing
	 */
	public void moveLeft() {
		switch (dir) {
		case DIR_NORTH:
			x--;
			break;
		case DIR_SOUTH:
			x++;
			break;
		case DIR_EAST:
			y--;
			break;
		case DIR_WEST:
			y++;
			break;
		}

		checkOutOfBounds();
	}
	
	/**
	 * Moves the player right, based on the direction they are facing
	 */
	public void moveRight() {
		switch (dir) {
		case DIR_NORTH:
			x++;
			break;
		case DIR_SOUTH:
			x--;
			break;
		case DIR_EAST:
			y++;
			break;
		case DIR_WEST:
			y--;
			break;
		}

		checkOutOfBounds();
	}
	
	/**
	 * Checks if the player is out of bounds, after they have moved in one direction
	 */
	private void checkOutOfBounds() {
		if (x < 0) {
			x++;
		}
		if (x >= mazeWidth) {
			x--;
		}
		if (y < 0) {
			y++;
		}
		if (y >= mazeHeight) {
			y--;
		}
	}
	
	/**
	 * Rotates the player to the right
	 */
	public void turnRight() {
		if (++dir > DIR_WEST) {
			dir = 0;
		}
	}
	
	/**
	 * Rotates the player to the left
	 */
	public void turnLeft() {
		if (--dir < DIR_NORTH) {
			dir = 3;
		}
	}
	
	/**
	 * Checks if the player can carry an Item, depending on it's weight and the number of items you have
	 * @param item The Item to check
	 * @return True if the player can carry the item
	 */
	public boolean canCarry(Item item) {
		return item.getWeight() + inventoryWeight <= maxInventoryWeight && inventory.size() < MAX_INVENTORY_ITEMS;
	}
	
	/**
	 * Adds an Item to the player's inventory. Assumes canCarry(Item) has already been checked. 
	 * @param item The Item pick up
	 * @throws IllegalStateException if the item cannot be added. If it is too heavy or you have too many items.
	 */
	public void addItem(Item item) throws IllegalStateException {
		if (!canCarry(item)) {
			throw new IllegalStateException("You cannot carry this item! It is too heavy");
		}
		
		inventory.add(item);
		
		inventoryWeight += item.getWeight();
	}
	
	/**
	 * Removes an Item from the player's inventory at the given index.
	 * If the item is their active weapon, then the new active weapon is Fists
	 * If the item is their active armor, then the new active armor is the T Shirt
	 * @param idx
	 */
	public void removeItem(int idx) {
		Item item = inventory.get(idx);
		if (item == activeWeapon) {
			activeWeapon = FISTS;
		} else if (item == activeArmor) {
			activeArmor = T_SHIRT;
		}
		
		inventoryWeight -= item.getWeight();
		
		inventory.remove(idx);
	}
	
	/**
	 * Gets an item at an index in the player's inventory. Assumes the index is valid.
	 * @param idx The index of the Item.
	 * @return The Item at the given index
	 */
	public Item getItem(int idx) {
		return inventory.get(idx);
	}
	
	/**
	 * Uses an Item. If it is a weapon, this is their active weapon. If it is armor, this is their active armor.
	 * If it is a Health item, it is consumed and health is restored.
	 * @param item The Item to use
	 */
	public void useItem(Item item) {
		if (item instanceof Weapon) {
			activeWeapon = (Weapon) item;
		} else if (item instanceof Armor) {
			activeArmor = (Armor) item;
		} else if (item instanceof HealthItem) {
			HealthItem healthItem = (HealthItem) item;
			int healthRestore = healthItem.getHealthRestore();
			health += healthRestore;
			if (health > maxHealth) {
				health = maxHealth;
			}
			inventory.remove(item);
			inventoryWeight -= item.getWeight();
		}
	}
	
	/**
	 * Does damage to and possibly kills the player, from a monster
	 * @param damage The amount of damage to do.
	 */
	public void doDamage(int damage) {
		double damagePercent = (double) (100 - activeArmor.getDamageReduction()) / 100;
		damage *= damagePercent;
		health -= damage;
		if (health <= 0) {
			health = 0;
			//TODO kill the character or something
		}
	}
	
	/**
	 * Adds xp, and checks if user leveled up
	 * @param xpToAdd The amount of xp to add
	 */
	public void addXp(int xpToAdd) {
		xp += xpToAdd;
		
		if (xp >= levelUpXp) {
			levelUp();
		}
	}
	
	/**
	 * Levels up the player, adding 25 to their health and weight they can carry 
	 */
	private void levelUp() {
		xp %= levelUpXp;
		levelUpXp += 5;
		level++;
		
		maxHealth += 25;
		health += 25;
		
		maxInventoryWeight += 25;
	}
}
