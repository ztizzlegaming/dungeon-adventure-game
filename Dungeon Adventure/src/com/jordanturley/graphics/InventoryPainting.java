package com.jordanturley.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.jordanturley.game.Player;
import com.jordanturley.item.Item;

/**
 * The <code>InventoryPainting</code> class extends from JComponent and overrides the paint method, to
 * paint the items in the player's inventory, their active weapon and armor, and health. 
 * 
 * @author Jordan Turley
 */
public class InventoryPainting extends JComponent {
	private static final long serialVersionUID = 1L;
	
	public static final int NUM_ITEMS_WIDTH = 5;
	public static final int NUM_ITEMS_HEIGHT = 3;
	
	private Player player;

	public InventoryPainting(Player player) {
		super();
		
		this.player = player;
	}
	
	@Override
	public void paint(Graphics g) {
		//Calculate the size of the image when it is shown
		int itemSize = getWidth() / NUM_ITEMS_WIDTH;
		
		//Draw the vertical and horizontal lines for their inventory
		for (int y = 1; y < NUM_ITEMS_HEIGHT + 1; y++) {
			g.drawLine(0, y * itemSize, getWidth(), y * itemSize);
		}
		for (int x = 0; x < NUM_ITEMS_WIDTH; x++) {
			g.drawLine(x * itemSize, 0, x * itemSize, NUM_ITEMS_HEIGHT * itemSize);
		}
		
		//Go through the player's items and paint them
		List<Item> inventory = player.getInventory();
		for (int i1 = 0; i1 < inventory.size(); i1++) {
			Item item = inventory.get(i1);
			ImageIcon itemImageIcon = item.getImage();
			
			int x = i1 % NUM_ITEMS_WIDTH;
			int y = i1 / NUM_ITEMS_WIDTH;
			
			g.drawImage(itemImageIcon.getImage(), x * itemSize, y * itemSize, itemSize, itemSize, null);
		}
		
		//Draw the active weapon
		Item activeWeapon = player.getActiveWeapon();
		ImageIcon activeWeaponImage = activeWeapon.getImage();
		g.drawImage(activeWeaponImage.getImage(), 0, NUM_ITEMS_HEIGHT * itemSize, itemSize, itemSize, null);
		
		//Draw the active armor
		Item activeArmor = player.getActiveArmor();
		ImageIcon activeArmorImage = activeArmor.getImage();
		g.drawImage(activeArmorImage.getImage(), itemSize, NUM_ITEMS_HEIGHT * itemSize, itemSize, itemSize, null);
		
		//Draw the player's health
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 22));
		String weightStr = "Weight:" + player.getInventoryWeight() + "/" + player.getMaxInventoryWeight();
		g.drawString(weightStr, itemSize * 2 + 10, NUM_ITEMS_HEIGHT * itemSize + itemSize / 2 + Window.TEXT_OFFSET);
		
		//TODO Add XP, and show the player's level and current xp below their health
	}
}
