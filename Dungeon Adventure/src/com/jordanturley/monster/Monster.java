package com.jordanturley.monster;

import javax.swing.ImageIcon;

public class Monster {
	private int health;
	private int damage;
	
	private ImageIcon[] animationFrames;
	
	private ImageIcon currentImage;
	private int currentImageIdx;
	
	public Monster(int health, int damage, ImageIcon[] animationFrames) {
		this.health = health;
		this.damage = damage;
		this.animationFrames = animationFrames;
		
		currentImage = animationFrames[0];
		currentImageIdx = 0;
	}
}
