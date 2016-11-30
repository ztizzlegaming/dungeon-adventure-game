package com.jordanturley.item;

import javax.swing.ImageIcon;

/**
 * The <code>HealthItem</code> class extends from <code>Item</code> and also stores the amount of health
 * to restore to the player when used
 * 
 * @author Jordan Turley
 */
public class HealthItem extends Item {
	private int healthRestore;
	
	public HealthItem(String name, int weight, boolean canDrop, ImageIcon image, int healthRestore) {
		super(name, weight, canDrop, image);

		this.healthRestore = healthRestore;
	}
	
	public int getHealthRestore() {
		return healthRestore;
	}
	
	@Override
	public String toString() {
		return name + " - restores: " + healthRestore;
	}
}
