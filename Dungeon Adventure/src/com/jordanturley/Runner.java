package com.jordanturley;

import com.jordanturley.graphics.Window;

/**
 * Runs the Dungeon game
 * 
 * @author Jordan Turley
 */
public class Runner {
	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				new Window(args[0], true);
			} else {
				new Window();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
