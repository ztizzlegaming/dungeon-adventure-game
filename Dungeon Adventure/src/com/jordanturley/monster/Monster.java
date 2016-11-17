package com.jordanturley.monster;

import javax.swing.ImageIcon;

import com.jordanturley.game.Game;

/**
 * The <code>Monster</code> class stores the information for monsters in the dungeon game, such as name,
 * health, min and max damage, and the images to use for it.
 * 
 * @author Jordan Turley
 */
public class Monster {
	/**
	 * The number of frames (images) for any monster
	 */
	public static final int NUM_ANIMATION_FRAMES = 2;
	
	private String name;
	
	private int health;
	private final int maxHealth;
	private boolean alive;
	
	private int lowerDamage;
	private int higherDamage;
	
	private ImageIcon[] animationFrames;
	private int currentImageIdx;
	
	/**
	 * Creates a new Monster object
	 * @param name The name of the monster
	 * @param health The health of the monster
	 * @param lowerDamage The minimum amount of damage this monster can do
	 * @param higherDamage The maximum amount of damage this monster can do
	 * @param animationFrames The array of images (ImageIcon objects) for this monster
	 */
	public Monster(String name, int health, int lowerDamage, int higherDamage, ImageIcon[] animationFrames) {
		this.name = name;
		
		this.health = health;
		maxHealth = health;
		alive = true;
		
		this.lowerDamage = lowerDamage;
		this.higherDamage = higherDamage;
		
		this.animationFrames = animationFrames;
		currentImageIdx = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void doDamage(int damage) {
		health -= damage;
		if (health <= 0) {
			health = 0;
			alive = false;
		}
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public int getDamage() {
		return Game.RANDOM.nextInt(higherDamage - lowerDamage + 1) + lowerDamage;
	}
	
	public ImageIcon getCurrentImage() {
		return animationFrames[currentImageIdx];
	}
	
	public void nextImage() {
		if (++currentImageIdx == animationFrames.length) {
			currentImageIdx = 0;
		}
	}
}
