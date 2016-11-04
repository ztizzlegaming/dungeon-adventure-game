package com.jordanturley.item;

import javax.swing.ImageIcon;

/**
 * The <code>Item</code> abstract class stores the basic information for any Item,
 * like the name, weight, and image.
 * 
 * @author Jordan Turley
 */
public abstract class Item implements Cloneable {
	private String name;
	
	private int weight;
	
	private ImageIcon image;
	
	/**
	 * Creates a new Item object
	 * @param name The name of the item
	 * @param weight The weight of the item
	 * @param image The image of the Item
	 */
	public Item(String name, int weight, ImageIcon image) {
		this.name = name;
		this.weight = weight;
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
