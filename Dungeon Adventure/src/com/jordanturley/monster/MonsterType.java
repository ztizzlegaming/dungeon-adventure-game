package com.jordanturley.monster;

import javax.swing.ImageIcon;

/**
 * The <code>MonsterType</code> class stores all the data for a type of monster. When the types of monsters that
 * can be in the game are read in from the text file, a MonsterType object is created for each monster. This class
 * stores a monster's name, possible health range, possible damage range, and the random chance for it to appear
 * in a room, if a random monster should be in that room.
 * 
 * @author Jordan Turley
 */
public class MonsterType {
	private String name;
	
	private int lowerHealth;
	private int higherHealth;
	
	private int xpToGive;
	
	private int lowerDamage;
	private int higherDamage;
	
	private int randomChance;
	
	private ImageIcon[] images;
	
	/**
	 * Creates a new MonsterType object
	 * @param name The name of this monster (kobold, ogre...)
	 * @param lowerHealth The lower health that this monster can be (inclusive)
	 * @param higherHealth The higher health that this monster can be (inclusive)
	 * @param lowerDamage The lower damage that this monster can do (inclusive)
	 * @param higherDamage The higher damage that this monster can do (inclusive)
	 * @param randomChance The random chance for this monster to appear in a room, if a random
	 * monster should be in the room (int from 1-100)
	 */
	public MonsterType(String name, int lowerHealth, int higherHealth, int xpToGive, int lowerDamage, int higherDamage, int randomChance, ImageIcon[] images) {
		this.name = name;
		this.lowerHealth = lowerHealth;
		this.higherHealth = higherHealth;
		this.xpToGive = xpToGive;
		this.lowerDamage = lowerDamage;
		this.higherDamage = higherDamage;
		this.randomChance = randomChance;
		this.images = images;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLowerHealth() {
		return lowerHealth;
	}
	
	public int getHigherHealth() {
		return higherHealth;
	}
	
	public int getXpToGive() {
		return xpToGive;
	}
	
	public int getLowerDamage() {
		return lowerDamage;
	}
	
	public int getHigherDamage() {
		return higherDamage;
	}
	
	public int getRandomChance() {
		return randomChance;
	}
	
	public ImageIcon[] getImages() {
		return images;
	}
}
