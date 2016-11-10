package com.jordanturley;

import java.io.IOException;
import com.jordanturley.graphics.Window;

/**
 * Runs the Dungeon game
 * 
 * @author Jordan Turley
 */
public class Runner {
	public static void main(String[] args) {
		try {
			new Window();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
