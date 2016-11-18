package com.jordanturley.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jordanturley.game.Game;
import com.jordanturley.game.Player;
import com.jordanturley.item.Item;
import com.jordanturley.item.Weapon;
import com.jordanturley.monster.Monster;
import com.jordanturley.room.Room;

/**
 * The <code>Window</code> class is the main Window graphics class. This class extends from JFrame,
 * and kind of brings everything in the program together.
 * 
 * received help from kate young. she caught my capitalization error!!!! :D 
 * 
 * @author Jordan Turley
 */
public class Window extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	public static final Dimension SIZE = new Dimension(600, 800);
	public static final Dimension MAP_DIALOG_SIZE = new Dimension(500, 400);
	public static final int SOUTH_PANEL_HEIGHT = 250;
	
	/**
	 * A small text offset used for drawing the player's direction and health in MoveButtonsPainting, and
	 * the player's inventory weight in InventoryPainting
	 */
	public static final int TEXT_OFFSET = 10;
	
	private Game game;
	
	private JPanel mainPanel;
	private JPanel moveButtonsPanel;
	private JDialog mapDialog;
	private MapPainting mapPainting;
	private MoveButtonsPainting moveButtonsPainting;
	private InventoryPainting inventoryPainting;
	private FirstPersonPainting firstPersonPainting;
	
	private Thread monsterThread;

	public Window() throws IOException {
		super("Dungeon Adventure");
		
		setSize(SIZE);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game = new Game("maze.txt");
		
		monsterThread = new Thread(new MonsterThreadRunnable());
		monsterThread.start();
		
		setupMainPanel();
		setupMapDialog();
		
		setVisible(true);
	}
	
	private void setupMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setFocusable(true);
		mainPanel.addKeyListener(this);
		
		JPanel firstPersonView = new JPanel();
		firstPersonView.setLayout(new BorderLayout());
		firstPersonPainting = new FirstPersonPainting();
		firstPersonPainting.addMouseListener(new FirstPersonPaintingMouseListener());
		setFirstPersonViewDir();
		setFirstPersonViewRoom();
		firstPersonView.add(firstPersonPainting, BorderLayout.CENTER);
		mainPanel.add(firstPersonView, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(0, SOUTH_PANEL_HEIGHT));
		southPanel.setLayout(new BorderLayout());
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		
		JPanel mapButtonPanel = new JPanel();
		mapButtonPanel.setPreferredSize(new Dimension(175, 0));
		mapButtonPanel.setLayout(new BorderLayout());
		
		JButton mapButton = new JButton("Map");
		
		////////////////////////////////////////////////////////////////////////
		mapButton.setFocusable(false); //Best line of code in the entire program
		////////////////////////////////////////////////////////////////////////
		
		mapButtonPanel.add(mapButton, BorderLayout.CENTER);
		
		mapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mapDialog.setVisible(!mapDialog.isVisible());
			}
		});
		
		southPanel.add(mapButtonPanel, BorderLayout.EAST);
		
		moveButtonsPanel = new JPanel();
		moveButtonsPanel.setPreferredSize(new Dimension(175, 0));
		moveButtonsPanel.setLayout(new BorderLayout());
		moveButtonsPainting = new MoveButtonsPainting(game.getPlayer());
		moveButtonsPanel.add(moveButtonsPainting, BorderLayout.CENTER);
		moveButtonsPanel.addMouseListener(new MoveButtonsMouseAdapter());
		southPanel.add(moveButtonsPanel, BorderLayout.WEST);
		
		JPanel inventoryPanel = new JPanel();
		inventoryPanel.setLayout(new BorderLayout());
		inventoryPainting = new InventoryPainting(game.getPlayer());
		inventoryPainting.addMouseListener(new InventoryPaintingMouseListener());
		inventoryPanel.add(inventoryPainting, BorderLayout.CENTER);
		southPanel.add(inventoryPanel, BorderLayout.CENTER);
		
		setContentPane(mainPanel);
	}
	
	private void setupMapDialog() {
		mapDialog = new JDialog(this, "Map");
		mapDialog.setSize(MAP_DIALOG_SIZE);
		mapDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		//mapDialog.setResizable(false);
		mapDialog.addKeyListener(this);
		
		JPanel mapPanel = new JPanel();
		mapPanel.setLayout(new BorderLayout());
		mapPainting = new MapPainting(game.getRooms(), game.getPlayer());
		mapPanel.add(mapPainting, BorderLayout.CENTER);
		
		mapDialog.setContentPane(mapPanel);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			moveForward();
		} else if (code == KeyEvent.VK_S) {
			moveBack();
		} else if (code == KeyEvent.VK_A) {
			moveLeft();
		} else if (code == KeyEvent.VK_D) {
			moveRight();
		} else if (code == KeyEvent.VK_Q) {
			turnLeft();
		} else if (code == KeyEvent.VK_E) {
			turnRight();
		}
		
		if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
	
	/**
	 * Turns the player to the left. Called when 'Q' is pressed on the keyboard or the 'turn left' button
	 * is clicked in the GUI
	 */
	private void turnLeft() {
		game.getPlayer().turnLeft();
		mapPainting.repaint();
		setFirstPersonViewDir();
		moveButtonsPainting.repaint();
	}
	
	/**
	 * Turns the player to the right. Called when 'E' is pressed on the keyboard or the 'turn right' button
	 * is clicked in the GUI
	 */
	private void turnRight() {
		game.getPlayer().turnRight();
		mapPainting.repaint();
		setFirstPersonViewDir();
		moveButtonsPainting.repaint();
	}
	
	/**
	 * Moves the player forward. Called when 'W' is pressed on the keyboard or the 'up' button is clicked
	 * in the GUI
	 */
	private void moveForward() {
		game.movePlayerForward();
		mapPainting.repaint();
		setFirstPersonViewRoom();
	}
	
	/**
	 * Moves the player backward. Called when 'S' is pressed on the keyboard or the 'down' button is clicked
	 * in the GUI
	 */
	private void moveBack() {
		game.movePlayerBack();
		mapPainting.repaint();
		setFirstPersonViewRoom();
	}
	
	/**
	 * Moves the player to the left. Called when 'S' is pressed on the keyboard or the 'left' button is clicked
	 * in the GUI
	 */
	private void moveLeft() {
		game.movePlayerLeft();
		mapPainting.repaint();
		setFirstPersonViewRoom();
	}
	
	/**
	 * Moves the player to the right. Called when 'D' is pressed on the keyboard or the 'right' button is clicked
	 * in the GUI
	 */
	private void moveRight() {
		game.movePlayerRight();
		mapPainting.repaint();
		setFirstPersonViewRoom();
	}
	
	/**
	 * Sets the player's direction on the first person painting object, and repaints. Called
	 * when the player turns left or right
	 */
	private void setFirstPersonViewDir() {
		int dir = game.getPlayer().getDirection();
		firstPersonPainting.setDirectionLooking(dir);
		firstPersonPainting.repaint();
		
	}
	
	/**
	 * Sets the current room the player is in on the first person painting object. Called when
	 * the player moves from one room to another
	 */
	private void setFirstPersonViewRoom() {
		Room curRoom = game.getCurRoom();
		firstPersonPainting.setCurRoom(curRoom);
		firstPersonPainting.repaint();
	}
	
	/**
	 * Repaints the first person view, inventory, and map, when an item is picked up or dropped
	 */
	private void repaintForItems() {
		firstPersonPainting.repaint();
		inventoryPainting.repaint();
		mapPainting.repaint();
		moveButtonsPainting.repaint();
	}
	
	/**
	 * Checks when one of the move buttons is pressed
	 */
	private class MoveButtonsMouseAdapter extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int buttonWidth = moveButtonsPanel.getWidth() / 3;
			int buttonHeight = moveButtonsPanel.getHeight() / 4;
			int x = e.getX();
			int y = e.getY();
			
			if (x > 0 && x <= buttonWidth && y > 0 && y <= buttonHeight) {
				turnLeft();
			} else if (x > buttonWidth && x <= buttonWidth * 2 && y > 0 && y <= buttonHeight) {
				moveForward();
			} else if (x > buttonWidth * 2 && x <= buttonWidth * 3 && y > 0 && y <= buttonHeight) {
				turnRight();
			} else if (x > 0 && x <= buttonWidth && y > buttonHeight && y <= buttonHeight * 2) {
				moveLeft();
			} else if (x > buttonWidth * 2 && x <= buttonWidth * 3 && y > buttonHeight && y <= buttonHeight * 2) {
				moveRight();
			} else if (x > buttonWidth && x <= buttonWidth * 2 && y > buttonHeight * 2 && y <= buttonHeight * 3) {
				moveBack();
			}
		}
	}
	
	/**
	 * Checks when the first person view is clicked on, for attacking monsters or picking up items
	 */
	private class FirstPersonPaintingMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			int panelWidth = firstPersonPainting.getWidth() / 3;
			int paintingHeight = firstPersonPainting.getHeight();
			
			int itemImgSize = panelWidth / 3;
			
			//TODO move a lot of this to the Game class. The window class should be mainly for graphics.
			//TODO Just check here if the click is in the right place, then send it to the Game class to handle it
			
			if (x > panelWidth && x < panelWidth * 2) {
				if (y > paintingHeight - FirstPersonPainting.ITEM_IMAGE_BOTTOM_MARGIN - itemImgSize && y < paintingHeight - FirstPersonPainting.ITEM_IMAGE_BOTTOM_MARGIN) {
					x -= panelWidth;
					int idx = x / itemImgSize;
					Room room = game.getCurRoom();
					if (room.getNumItems() >= idx + 1) {
						Item item = room.getItem(idx);
						Player player = game.getPlayer();
						if (player.canCarry(item)) {
							player.addItem(item);
							room.removeItem(idx);
							repaintForItems();
						} else {
							System.out.println("Can't carry this item! Too heavy!");
							System.out.println(player + ", " + item.getWeight());
						}
					}
				}
			}
			
			if (x > panelWidth + (panelWidth / 2 - FirstPersonPainting.MONSTER_IMAGE_WIDTH / 2) &&
				x < panelWidth + (panelWidth / 2 + FirstPersonPainting.MONSTER_IMAGE_WIDTH / 2)) {
				
				if (y > paintingHeight / 2 - FirstPersonPainting.MONSTER_IMAGE_HEIGHT / 2 - FirstPersonPainting.ITEM_IMAGE_BOTTOM_MARGIN &&
					y < paintingHeight / 2 + FirstPersonPainting.MONSTER_IMAGE_HEIGHT / 2 - FirstPersonPainting.ITEM_IMAGE_BOTTOM_MARGIN) {
					if (game.getCurRoom().hasMonster()) {
						Monster monster = game.getCurRoom().getMonster();
						if (monster.isAlive()) {
							Weapon curWeapon = game.getPlayer().getActiveWeapon();
							
							monster.doDamage(curWeapon.getDamage());
							firstPersonPainting.repaint();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Checks when the inventory painting is clicked on, for using or dropping an item
	 */
	private class InventoryPaintingMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			//Calculate the size of the images in the painting
			int imgSize = inventoryPainting.getWidth() / InventoryPainting.NUM_ITEMS_WIDTH;
			
			//Calculate the height of the actual part that shows the items
			int yItemsHeight = imgSize * InventoryPainting.NUM_ITEMS_HEIGHT;
			
			//TODO move a lot of this to the Game class. The window class should be mainly for graphics.
			//TODO Just check here if the click is in the right place, then send it to the Game class to handle it
			
			if (y < yItemsHeight) {
				int xIdx = x / imgSize;
				int yIdx = y / imgSize;
				int idx = xIdx + yIdx * InventoryPainting.NUM_ITEMS_WIDTH;
				
				Player player = game.getPlayer();
				if (player.getNumItems() >= idx + 1) { //Make sure it is a valid index
					Item item = player.getItem(idx);
					
					if (SwingUtilities.isLeftMouseButton(e)) { //Use the item
						player.useItem(item);
						repaintForItems();
					} else if (SwingUtilities.isRightMouseButton(e) && item.canDrop()) { //Drop the item
						Room room = game.getCurRoom();
						if (room.canHoldAnotherItem()) {
							player.removeItem(idx);
							room.addItem(item);
							repaintForItems();
						} else {
							System.out.println("A room can hold a max of " + Room.MAX_ITEMS + " items");
						}
					} else {
						//Middle mouse button, print name of item and it's info?
					}
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	/**
	 * This class is used by the monsterThread object, for controlling all of the monsters in the game.
	 * It is started as soon as the game is started, and if the room has a monster, it animates it
	 * and attacks the player.
	 */
	private class MonsterThreadRunnable implements Runnable {
		@Override
		public void run() {
			while (true) {
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
}
