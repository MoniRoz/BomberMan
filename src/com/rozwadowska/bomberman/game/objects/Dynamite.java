package com.rozwadowska.bomberman.game.objects;

import java.util.Random;

import com.rozwadowska.bomberman.game.GameData;

/**
 * Klasa Dynamit dotyczy dynamitow tworzonych na planszy
 * 
 * @author Monika
 *
 */
public class Dynamite extends GameObject {
	
	/**
	 * FUSE_TIME - czas po ktorym wybucha dynamit
	 * EXPLOSION_TIME - czas trwania eksplozji
	 */
	private static final float FUSE_TIME = 3;
	private static final float EXPLOSION_TIME = 1;
	
	/**
	 * x - pozycja x dynamitu
	 * y - pozycja y dynamitu
	 */
	private int x;
	private int y;
	
	
	private float fuseTime;
	private float explosionTime;
	
	/**
	 * explosionArea - tablica wybuchow dynamitu
	 * exploding - sprawdzenie czy dynamit wybucha
	 */
	private boolean[][] explosionArea;
	private boolean exploding;
	
	/**
	 * power - moc wybuchu dynamitu
	 * owner - wlasciciel danego synamitu
	 */
	private int power;
	private Player owner;
	
	/**
	 * Konstruktor klasy Dynamite ustalajacy odpowiednie paramtery klasy. 
	 * 
	 * @param owner wlasciciel danego dynamitu
	 * @param x polożenie x na planszy 
	 * @param y polożenie y na planszy
	 */
	public Dynamite(Player owner, int x, int y) {
		super(GameObject.DYNAMITE_TYPE);
		this.owner = owner;

		this.x = x;
		this.y = y;

		fuseTime = FUSE_TIME;
		explosionTime = EXPLOSION_TIME;

		explosionArea = new boolean[GameData.BOARD_WIDTH][GameData.BOARD_HEIGHT];
		exploding = false;

		power = owner.getDynamitePower();
	}
	
	/**
	 * Metoda update wywolywana od czasu dla obiektu klasy Dynamite
	 */
	@Override
	public void update(float delta, GameData gameData) {
		if (!exploding) {
			fuseTime -= delta;
			if (fuseTime <= 0) {
				explosionTime -= fuseTime;
				exploding = true;
				explode(gameData);
			}
		} else {
			explosionTime -= delta;
			if (explosionTime <= 0) {
				updateOnDeath(gameData);
				stopExploding(gameData);
			}
		}
	}
	
	/**
	 * Metoda okreslajaca zachowanie dynamitu podczas ekspolozji. 
	 * Na poczatku okresla, że epicentrum wybuchu należy do tablicy eksplozji,
	 * pozniej okresla, że obiekt eksploduje, oraz zwieksza pole na tablicy ekspolzji.
	 * Natepnie wywoluje szukanie eksplozji we wszystkich kierunkach od mocy dynamitu.
	 * 
	 * @param gameData objekt klasy GameData potrzebny do probrania tablicy gry
	 * oraz eksplozji
	 */
	private void explode(GameData gameData) {
		explosionArea[x][y] = true;
		gameData.explosionMap[x][y]++;
		evaluateExplosion(x + 1, y, 1, 0, power, gameData);
		evaluateExplosion(x - 1, y, -1, 0, power, gameData);
		evaluateExplosion(x, y + 1, 0, 1, power, gameData);
		evaluateExplosion(x, y - 1, 0, -1, power, gameData);
	}

	/**
	 * Metoda okreslajaca zachowanie eksplozji w razie jej zakończenia oraz w razie
	 * napotkania na swojej drodze skrzyni, ktor jest zniszczalna.
	 * 
	 * @param gameData
	 */
	private void stopExploding(GameData gameData) {
		for (int i = 0; i < explosionArea.length; i++) {
			for (int j = 0; j < explosionArea[i].length; j++) {
				if (explosionArea[i][j]) {
					gameData.explosionMap[i][j]--;
					if (gameData.board[i][j] != null && gameData.board[i][j].isDestructible()) {
						gameData.board[i][j] = null;
						gameData.board[i][j] = makeNewObject(gameData);
					}
				}
			}
		}
	}

	/**
	 * Metoda wyszukujaca możliwe eksplozje we zadanym kierunku idac od epicentrum eksplozji.
	 * 
	 * @param x polożenie x epicentrum eksplozji
	 * @param y polożenie y epicentrum eksplozji
	 * @param dirx kierunek po osi X w jakim należy wszyszukiwac możliwych eksplozji
	 * @param diry kierunek po osi Y w jakim należy wszyszukiwac możliwych eksplozji
	 * @param steps ilosc krokow do wykonania, poczatkowo to moc eksplozji
	 * @param gameData
	 */
	private void evaluateExplosion(int x, int y, int dirx, int diry, int steps, GameData gameData) {
		while (steps > 0) {
			if (gameData.board[x][y] != null && gameData.board[x][y].isDestructible()) {
				gameData.explosionMap[x][y]++;
				explosionArea[x][y] = true;
				break;
			} else if (gameData.board[x][y] != null && !gameData.board[x][y].isDestructible()
					&& gameData.board[x][y].isSolid()) {
				break;
			} else {
				gameData.explosionMap[x][y]++;
				explosionArea[x][y] = true;
				x += dirx;
				y += diry;
				steps--;
			}
		}
	}
	
	/**
	 * Metoda tworzaca nowe objekty typy GameObject na zadanym miejscu na plaszy.
	 * Metoda wykorzystywana do tworzenia powerUpow po zniszczeniu skrzyni.
	 * 
	 * @param gameData
	 * @return nowy objekty typu GameObject
	 */
	private GameObject makeNewObject(GameData gameData) {
		Random random = new Random();
		if (random.nextDouble() < 0.13)
			return new GameObject(STRENGHT_POWERUP);
		else if (random.nextDouble() < 0.18)
			return new GameObject(SPEED_POWERUP);
		else if (random.nextDouble() < 0.22)
			return new GameObject(DYNAMITE_POWERUP);
		return null;
	}
	
	/**
	 * Metoda wywolywana wcelu zniszczenia dynamitu, ustawienia zmiennej
	 * explode klasy GameObject na flase oraz zwiekszeniu liczby dynamitow dla gracza, ktorego dynamit
	 * wybuchl.
	 * 
	 * @param gameData
	 */
	public void updateOnDeath(GameData gameData) {
		gameData.board[x][y] = null;
		owner.acquireDynamite();
		
	}
	

	/**
	 * Metoda do sprawdzania czy obiekt wybucha
	 * 
	 * @return zwraca true gdy byla eksplozja, false w przeciwnym przypadku
	 */
	@Override
	public boolean isExploding() {
		return exploding;
	}
}
