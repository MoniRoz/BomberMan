package com.rozwadowska.bomberman.gui;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.rozwadowska.bomberman.game.Game;
import com.rozwadowska.bomberman.input.GameKeyListener;
import com.rozwadowska.bomberman.main.Main;
import com.rozwadowska.bomberman.main.ResourceLoader;

/**
 * Klasa odpowiedzialna za rysowanie glownego okna gry wraz z odpowienimi
 * panelami
 * 
 * @author Monika
 *
 */
public class GameWindow {

	private static JFrame frame;
	private static BufferedImage backgroundMenuImage;
	private static BufferedImage gameBoard;
	private static MenuPanel menuPanel;
	private static ImagePanel gamePanel;
	private static MenuPanel winner;
	private static Game game;

	/**
	 * Metoda do utworzenia nowej gry. Jeżeli gra odbywa sie poraz pierwszy
	 * zostanie usuniety menuPanel jezeli nie to winnerPanel a w oknie zostanie
	 * wyswietlony gamePanel na ktorym bedzie odbywac sie gra.
	 */
	public static void startNewGame() {
		if (frame == null)
			return;
		if (winner == null)
			frame.remove(menuPanel);
		else
			frame.remove(winner);
		frame.add(gamePanel);
		game = new Game(gamePanel);
		game.start();
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Metoda wywolywana po wygranej ktoregos z graczy. Wstawia odpowiedni panel
	 * typu WinnerPanel do okna.
	 */
	public static void update(Integer looser) {
		if (frame == null)
			return;
		frame.remove(gamePanel);
		switch (looser) {
		case 0:
			winner = new MenuPanel(ResourceLoader.getImage("bombermenu/WinnerPanel2.png"), Main.WIDTH + 250,
					Main.HEIGHT);
			break;
		case 1:
			winner = new MenuPanel(ResourceLoader.getImage("bombermenu/WinnerPanel.png"), Main.WIDTH + 250,
					Main.HEIGHT);
			break;
		default:
			winner = new MenuPanel(ResourceLoader.getImage("bombermenu/DrawPanel.png"), Main.WIDTH + 250, Main.HEIGHT);
			break;
		}
		frame.add(winner);
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Metoda rysujaca glowne okno gry. Ustawia odpowiednio okno oraz wstawia
	 * nowy panel typu MenuPanel.
	 */
	public static void createAndShowGUI() {

		backgroundMenuImage = ResourceLoader.getImage("bombermenu/Menu.png");
		gameBoard = ResourceLoader.getImage("textures/background.png");

		frame = new JFrame();
		frame.setTitle("BOMBERMAN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuPanel = new MenuPanel(backgroundMenuImage, Main.WIDTH + 250, Main.HEIGHT);
		gamePanel = new ImagePanel(gameBoard, Main.WIDTH + 250, Main.HEIGHT);
		winner = null;

		frame.setFocusable(true);
		frame.addKeyListener(new GameKeyListener());

		frame.add(menuPanel);

		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
