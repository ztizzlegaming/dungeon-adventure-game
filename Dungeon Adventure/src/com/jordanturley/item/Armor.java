package com.jordanturley.item;

import javax.swing.ImageIcon;

/**
 * The <code>Armor</code> class extends from <code>Item</code> and stores the damage reduction percentage
 * for a piece of armor.
 * 
 * @author Jordan Turley
 */
public class Armor extends Item {
	private int damageReduction;
	
	public Armor(String name, int weight, boolean canDrop, ImageIcon image, int damageReduction) {
		super(name, weight, canDrop, image);
		this.damageReduction = damageReduction;
	}
	
	public int getDamageReduction() {
		return damageReduction;
	}
	
	@Override
	public String toString() {
		return name + " - weighs " + weight + " - damage reduction: " + damageReduction + "%";
	}
}
