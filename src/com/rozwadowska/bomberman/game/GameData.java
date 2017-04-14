package com.rozwadowska.bomberman.game;

import java.util.ArrayList;
import java.util.Random;

import com.rozwadowska.bomberman.game.objects.GameObject;
import com.rozwadowska.bomberman.game.objects.Player;

/**
 * Klasa przechowujaca wszelkie dane gry.
 * 
 * @author Monika
 *
 */
public class GameData {
	
	/**
	 * BOARD_WIDTH - dlugosc planszy, w polach
	 * BOARD_HEIGHT - szerokosc planszy, w polach
	 */
	public static final int BOARD_WIDTH = 15;
	public static final int BOARD_HEIGHT = 13;

	public ArrayList<Player> player;

	public GameObject[][] board;
	public int[][] explosionMap;

	public GameData() {
		player = new ArrayList<Player>();

		explosionMap = new int[BOARD_WIDTH][BOARD_HEIGHT];
		createBoard();
		createPlayer();
	}
	
	/**
	 * Metoda tworzaca graczy na planszy
	 */
	private void createPlayer() {
		player.add(new Player(1, 1, 0));
		player.add(new Player(BOARD_WIDTH - 2, BOARD_HEIGHT - 2, 1));
	}
	
	/**
	 * Metoda tworzaca objety na planszy
	 */
	private void createBoard() {
		board = new GameObject[BOARD_WIDTH][BOARD_HEIGHT];
		createWalls();
		createBoxes();
	}
	
	/**
	 * Metoda tworzaca sciany na planszy
	 */
	private void createWalls() {
		
		/**
		 * Tworzenie gornych scian 
		 */
		for (int i = 0; i < BOARD_WIDTH; i++) {
			board[i][0] = new GameObject(GameObject.WALL_TYPE);
			board[i][BOARD_HEIGHT - 1] = new GameObject(GameObject.WALL_TYPE);
		}
		
		/**
		 * Tworzenie bocznych scian
		 */
		for (int j = 1; j < BOARD_HEIGHT - 1; j++) {
			board[0][j] = new GameObject(GameObject.WALL_TYPE);
			board[BOARD_WIDTH - 1][j] = new GameObject(GameObject.WALL_TYPE);
		}
		
		/**
		 * Tworzenie scian w srodku
		 */
		for (int i = 2; i < BOARD_WIDTH - 2; i += 2) {
			for (int j = 2; j < BOARD_HEIGHT - 2; j += 2) {
				board[i][j] = new GameObject(GameObject.WALL_TYPE);
			}
		}
	}
	
	/**
	 * Metoda tworzaca skrzynie na planszy
	 */
	private void createBoxes() {
		Random random = new Random();
		
		/**
		 * Tworzenie skrzyn na srodku planszy
		 */
		for (int i = 2; i < board.length - 2; i++) {
			for (int j = 2; j < board[i].length - 2; j++) {
				if (board[i][j] == null && random.nextDouble() < 0.9)
					board[i][j] = new GameObject(GameObject.BOX_TYPE);
			}
		}

		/**
		 * Tworzenie dolnego i gornego paska skrzyn tak by zostawic miejsce dla gracza
		 */
		for (int i = 4; i < board.length - 4; i++) {
			if (random.nextDouble() < 0.9)
				board[i][1] = new GameObject(GameObject.BOX_TYPE);
			if (random.nextDouble() < 0.9)
				board[i][board[0].length - 2] = new GameObject(GameObject.BOX_TYPE);
		}
		
		/**
		 * Tworzenie lewego i prawego paska skrzyn tak by zostawic miejsce dla gracza
		 */
		for (int j = 4; j < board[0].length - 4; j++) {
			if (random.nextDouble() < 0.9)
				board[1][j] = new GameObject(GameObject.BOX_TYPE);
			if (random.nextDouble() < 0.9)
				board[board.length - 2][j] = new GameObject(GameObject.BOX_TYPE);
		}
	}	
}
