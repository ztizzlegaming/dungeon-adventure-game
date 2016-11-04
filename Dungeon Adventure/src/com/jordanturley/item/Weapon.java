package com.jordanturley.item;

import javax.swing.ImageIcon;

import com.jordanturley.game.Game;

/**
 * The <code>Weapon</code> class extends from <code>Item</code> and also stores the damage for a weapon
 * 
 * @author Jordan Turley
 */
public class Weapon extends Item {
	private int minDamage;
	private int maxDamage;

	public Weapon(String name, int weight, ImageIcon image, int minDamage, int maxDamage) {
		super(name, weight, image);
		
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
	
	/**
	 * @return A random damage between minDamage and maxDamage, inclusively
	 */
	public int getDamage() {
		return Game.RANDOM.nextInt(maxDamage - minDamage + 1) + minDamage;
	}
}
