package com.jordanturley;

import java.io.IOException;

import com.jordanturley.graphics.Window;

public class Runner {
	public static void main(String[] args) {
		try {
			Window window = new Window();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
