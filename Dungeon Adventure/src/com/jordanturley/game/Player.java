package com.jordanturley.game;

import java.util.ArrayList;
import java.util.List;

import com.jordanturley.item.Item;

public class Player {
	public static final int DIR_NORTH = 0;
	public static final int DIR_EAST = 1;
	public static final int DIR_SOUTH = 2;
	public static final int DIR_WEST = 3;
	
	private int x;
	private int y;
	private int dir;
	
	private final int mazeWidth;
	private final int mazeHeight;
	
	private List<Item> inventory;
	public final int maxInventorySize;
	
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
		
		this.mazeWidth = mazeWidth;
		this.mazeHeight = mazeHeight;
		
		inventory = new ArrayList<Item>();
		this.maxInventorySize = maxInventorySize;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
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
	
	public void turnRight() {
		if (++dir > DIR_WEST) {
			dir = 0;
		}
	}
	
	public void turnLeft() {
		if (--dir < DIR_NORTH) {
			dir = 3;
		}
	}
	public List<Item> getInventory() {
		return inventory;
	}
	
	public int getDirection() {
		return dir;
	}
}
