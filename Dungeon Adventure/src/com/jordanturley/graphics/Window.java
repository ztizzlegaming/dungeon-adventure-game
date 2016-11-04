package com.jordanturley.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jordanturley.game.Game;
import com.jordanturley.room.Room;

/**
 * received help from kate young. she caught my capitalization error!!!! :D 
 * 
 * @author Jordan Turley
 */
public class Window extends JFrame implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	public static final Dimension SIZE = new Dimension(600, 800);
	public static final Dimension MAP_DIALOG_SIZE = new Dimension(500, 400);
	public static final int SOUTH_PANEL_HEIGHT = 200;
	
	private Game game;
	
	private JPanel mainPanel;
	private JPanel moveButtonsPanel;
	private JDialog mapDialog;
	private MapPainting mapPainting;
	private FirstPersonPainting firstPersonPainting;

	public Window() throws IOException {
		super("Dungeon Adventure");
		setSize(SIZE);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game = new Game("maze.txt");
		
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
		
		//(flex)
		mapButton.setFocusable(false); //Best line of code in the entire program
		//(dream) (selfie)
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
		moveButtonsPanel.add(new MoveButtonsPainting(), BorderLayout.CENTER);
		moveButtonsPanel.addMouseListener(this);
		southPanel.add(moveButtonsPanel, BorderLayout.WEST);
		
		JPanel inventoryPanel = new JPanel();
		inventoryPanel.setLayout(new BorderLayout());
		inventoryPanel.add(new InventoryPainting(game.getPlayer()), BorderLayout.CENTER);
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
	
	@Override
	public void mousePressed(MouseEvent e) {
		int buttonWidth = moveButtonsPanel.getWidth() / 3;
		int buttonHeight = moveButtonsPanel.getHeight() / 3;
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
	
	/**
	 * Turns the player to the left. Called when 'Q' is pressed on the keyboard or the 'turn left' button
	 * is clicked in the GUI
	 */
	private void turnLeft() {
		game.getPlayer().turnLeft();
		mapPainting.repaint();
		setFirstPersonViewDir();
	}
	
	/**
	 * Turns the player to the right. Called when 'E' is pressed on the keyboard or the 'turn right' button
	 * is clicked in the GUI
	 */
	private void turnRight() {
		game.getPlayer().turnRight();
		mapPainting.repaint();
		setFirstPersonViewDir();
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
	
	private void setFirstPersonViewDir() {
		int dir = game.getPlayer().getDirection();
		firstPersonPainting.setDirectionLooking(dir);
		firstPersonPainting.repaint();
	}
	
	private void setFirstPersonViewRoom() {
		Room curRoom = game.getCurRoom();
		firstPersonPainting.setCurRoom(curRoom);
		firstPersonPainting.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	private class FirstPersonPaintingMouseListener implements MouseListener {
		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
}
