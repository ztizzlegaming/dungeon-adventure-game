package com.jordanturley.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.jordanturley.game.Player;
import com.jordanturley.item.Item;

public class InventoryPainting extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private static final int NUM_ITEMS_WIDTH = 5;
	private static final int NUM_ITEMS_HEIGHT = 3;
	
	private Player player;

	public InventoryPainting(Player player) {
		super();
		
		this.player = player;
	}
	
	@Override
	public void paint(Graphics g) {
		int itemSize = getWidth() / NUM_ITEMS_WIDTH;
		
		Color[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.GRAY, Color.BLACK};
		Color randColor = colors[(int) (Math.random() * colors.length)];
		
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
	}
}
