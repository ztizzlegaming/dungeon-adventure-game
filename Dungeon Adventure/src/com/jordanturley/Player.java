package com.jordanturley;

import java.util.ArrayList;
import java.util.List;

import com.jordanturley.item.Item;

public class Player {
	private int x;
	private int y;
	
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
		
		this.mazeWidth = mazeWidth;
		this.mazeHeight = mazeHeight;
		
		inventory = new ArrayList<Item>();
		this.maxInventorySize = maxInventorySize;
	}
}
