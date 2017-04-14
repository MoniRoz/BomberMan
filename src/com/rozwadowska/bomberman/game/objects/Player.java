package com.rozwadowska.bomberman.game.objects;

import com.rozwadowska.bomberman.game.GameData;
import com.rozwadowska.bomberman.input.UserInput;

/**
 * Klasa Player dotyczy playerow renderowanych na planszy.
 * 
 * @author Monika
 *
 */
public class Player {
	/**
	 * x- pozycja x na plnszy
	 * y - pozycja y na planszy
	 */
	private float x;
	private float y;

	/**
	 * destX - kierunek playera po osi X 
	 * destY - kierunek playera po osi Y
	 */
	private int destX;
	private int destY;

	/**
	 * speed - predkosc gracza vx - predkosc poruszania sie gracza po osi X 
	 * vy - predkosc poruszania sie gracza po osi Y
	 */
	private float speed;

	private float vx;
	private float vy;

	/**
	 * playerNumber - numer playera 
	 * lives - liczba zyc playera 
	 * invulnerableTime - czas przez ktory gracz jest niesmiertelny 
	 * dynamites - liczba dynamitow gracza 
	 * dynamitePower - sila wybuchu dynamitu gracza
	 */
	private int playerNumber;

	private int lives;
	private float invulnerableTime;
	private int dynamites;
	private int dynamitePower;

	/**
	 * potrzebne do renderowania: 
	 * dir - kierunek poruszania sie gracza 
	 * move - zmienna okreslajaca rodzaj ruchu dla gracza 
	 * deltaToMove - delta czasu do zmieniania ruchu 
	 * deltaInvulnerable - delta czasu do niesmiertelnosci
	 */
	private int dir;
	private int move;
	private float deltaToMove;
	private float deltaInvulnerable;

	/**
	 * Konstruktor klasy Player innicjujacy wszystkie zmienny klasy Player.
	 * 
	 * @param x
	 *            pozycja x gracza
	 * @param y
	 *            pozycja y gracza
	 * @param playerNumber
	 *            numer gracza
	 */
	public Player(int x, int y, int playerNumber) {
		this.x = x;
		this.y = y;
		destX = x;
		destY = y;
		speed = 3f;
		vx = 0;
		vy = 0;
		this.playerNumber = playerNumber;
		lives = 3;
		invulnerableTime = 0f;
		dynamites = 1;
		dynamitePower = 2;
		dir = 0;
		move = 0;
		deltaToMove = 0;
		deltaInvulnerable = 0;
	}

	/**
	 * Metoda wywolujaca update playerow w tablicy od czasu
	 * 
	 * @param delta
	 *            podawany czas update'u
	 * @param gameData
	 *            obiekt GamaData, w ktorym znajduje sie tablica
	 */
	public void update(float delta, GameData gameData) {
		move(delta, gameData);
		reactToPowerUps(gameData.board);
		updateDynamites(gameData);
		updateOnExpolsionHit(delta, gameData);
	}

	/**
	 * Metoda odpowiedzialna za zachowanie gracza w przypadku napotkania przez
	 * gracza ekspolozji.
	 * 
	 * @param delta czas z jakim wykonuje sie update
	 * @param gameData obiekt klasy GameData
	 */
	private void updateOnExpolsionHit(float delta, GameData gameData) {
		int m = move;
		invulnerableTime -= delta;
		if (gameData.explosionMap[Math.round(x)][Math.round(y)] > 0) {
			deltaInvulnerable += delta;
			if (deltaInvulnerable > 0.1f) {
				if (move != 3)
					move = 3;
				else
					move = m;
				deltaInvulnerable -= 0.1f;
			}
		}
		if (invulnerableTime <= 0)
			invulnerableTime = 0;
		if (invulnerableTime == 0 && gameData.explosionMap[Math.round(x)][Math.round(y)] > 0) {
			invulnerableTime = 2f;
			lives--;

		}

	}
	
	/**
	 * Metoda odpowiadajaca za zachowanie w przypadku gdy gracz postawil dynamit na plaszny
	 * 
	 * @param gameData Objekt klasy GameData, gdzie jest tablica objektow
	 */
	private void updateDynamites(GameData gameData) {

		if (UserInput.isActionPressed(playerNumber) && gameData.board[Math.round(x)][Math.round(y)] == null
				&& dynamites > 0) {
			dynamites--;
			UserInput.setAction(false, playerNumber);
			gameData.board[Math.round(x)][Math.round(y)] = new Dynamite(this, Math.round(x), Math.round(y));
		}
	}
	
	/**
	 * Metoda odpowiedzialna za ruch gracza
	 * 
	 * @param delta czas z jakim przesuwany jest gracz
	 * @param gameData Objekt klasy GameData, gdzie jest tablica objektow
	 */
	private void move(float delta, GameData gameData) {
		x += vx * delta;
		y += vy * delta;
		deltaToMove += delta;
		
		/**
		 * Zmiana obrazkow podczas ruszania
		 */
		if (deltaToMove > 0.5 / speed && (vx != 0 || vy != 0)) {
			if (move == 1)
				move = 2;
			else
				move = 1;
			deltaToMove -= 0.5 / speed;
		}
		
		if (x == destX && y == destY && vx == 0 && vy == 0) {
			reactToUserInput(gameData.board);
		} else if (vy < 0 && y < destY) {
			reactToUserInput(gameData.board);
			if (!(vy < 0)) {
				y = destY;

			}
		} else if (vy > 0 && y > destY) {
			reactToUserInput(gameData.board);
			if (!(vy > 0)) {
				y = destY;
			}
		} else if (vx < 0 && x < destX) {
			reactToUserInput(gameData.board);
			if (!(vx < 0)) {
				x = destX;
			}
		} else if (vx > 0 && x > destX) {
			reactToUserInput(gameData.board);
			if (!(vx > 0)) {
				x = destX;
			}
		}
	}
	
	/**
	 * Metoda odpowiedzialana za reakcje input wprowadzony przez gracza.
	 * 
	 * @param board tablica objektow znajdujacych sie na planszy
	 */
	private void reactToUserInput(GameObject[][] board) {
		int xAxis = UserInput.getXAxis(playerNumber);
		int yAxis = UserInput.getYAxis(playerNumber);

		if (board[destX + xAxis][destY] != null && board[destX + xAxis][destY].isSolid()) {
			xAxis = 0;
		}
		if (board[destX][destY + yAxis] != null && board[destX][destY + yAxis].isSolid()) {
			yAxis = 0;
		}

		if (vx != 0 && yAxis != 0) {
			vx = 0;
			vy = yAxis * speed;
		} else if (vy != 0 && xAxis != 0) {
			vx = xAxis * speed;
			vy = 0;
		} else if (vx == 0 && xAxis != 0) {
			vx = xAxis * speed;
			vy = 0;
		} else if (vy == 0 && yAxis != 0) {
			vx = 0;
			vy = yAxis * speed;
		} else {
			vx = vy = 0;
			move = 0;
			return;
		}
		
		/**
		 * Okreslenie kierunku w ktorym porusza sie grac
		 */
		if (vx > 0) {
			destX++;
			dir = 2;
		} else if (vx < 0) {
			destX--;
			dir = 3;
		}

		if (vy > 0) {
			destY++;
			dir = 0;
		} else if (vy < 0) {
			destY--;
			dir = 1;
		}
	}
	
	/**
	 * Metoda okreslajaca zachowanie gracza kiedy na plaszy napotka na jakis powerUp
	 * 
	 * @param board tablica objektow znajdujacych sie na planszy
	 */
	private void reactToPowerUps(GameObject[][] board) {
		int x = Math.round(this.x);
		int y = Math.round(this.y);

		if (board[x][y] != null && board[x][y].getType() == GameObject.DYNAMITE_POWERUP) {
			board[x][y] = null;
			dynamites++;

		} else if (board[x][y] != null && board[x][y].getType() == GameObject.SPEED_POWERUP) {
			board[x][y] = null;
			speed++;

		} else if (board[x][y] != null && board[x][y].getType() == GameObject.STRENGHT_POWERUP) {
			board[x][y] = null;
			dynamitePower++;
		}

	}
	
	/**
	 * Metoda dodajaca dynamity dla playera
	 */
	public void acquireDynamite() {
		dynamites++;
	}
	
	/**
	 * Metoda pobierajaca pozycke x gracza
	 * 
	 * @return pozycja x gracza
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Metoda pobierajaca pozycje y gracza
	 * 
	 * @return pozycja y gracza
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Metoda pobierajaca sile dynamitu gracza
	 * 
	 * @return sila dynamitu gracza
	 */
	public int getDynamitePower() {
		return dynamitePower;
	}
	
	/**
	 * Metoda pobierajaca liczbe zyc gracza
	 * 
	 * @return liczba zyc gracza
	 */
	public int getPlayerLives() {
		return lives;
	}
	
	/**
	 * Metoda pobierajaca numer gracza
	 * 
	 * @return numer gracza
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	/**
	 * Metoda pobierajaca kierunek gracza,potrzebnyh do renderowania
	 * 
	 * @return kierunek gracza
	 */
	public int getPlayerDir() {
		return dir;
	}
	
	/**
	 *  Metoda pobierajaca ruch gracza,potrzebnyh do renderowania
	 *  
	 * @return ruch gracza
	 */
	public int getPlayerMove() {
		return move;
	}
}
