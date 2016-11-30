package com.jordanturley.thread;

import com.jordanturley.game.Game;
import com.jordanturley.graphics.FirstPersonPainting;
import com.jordanturley.graphics.MoveButtonsPainting;
import com.jordanturley.room.Room;

/**
 * This class is used by the monsterThread object, for controlling all of the monsters in the game.
 * It is started as soon as the game is started, and if the room has a monster, it animates it
 * and attacks the player.
 * 
 * @author Jordan Turley
 */
public class MonsterCombatRunnable implements Runnable {
	private Game game;
	private FirstPersonPainting firstPersonPainting;
	private MoveButtonsPainting moveButtonsPainting;
	
	public MonsterCombatRunnable(Game game, FirstPersonPainting fpp, MoveButtonsPainting mbp) {
		this.game = game;
		firstPersonPainting = fpp;
		moveButtonsPainting = mbp;
	}
	
	@Override
	public void run() {
		while (game.getPlayer().isAlive()) {
			try {
				Thread.sleep(750);

				Room curRoom = game.getCurRoom();
				if (curRoom.hasMonster() && curRoom.getMonster().isAlive()) {
					curRoom.getMonster().nextImage();
					game.getPlayer().doDamage(curRoom.getMonster().getDamage());
					firstPersonPainting.repaint();
					moveButtonsPainting.repaint();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
