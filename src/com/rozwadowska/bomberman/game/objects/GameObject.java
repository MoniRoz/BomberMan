package com.rozwadowska.bomberman.game.objects;

import com.rozwadowska.bomberman.game.GameData;

/**
 * Klasa odnoszaca sie do obiektow renderowanych na plasznszy.
 * 
 * @author Monika
 *
 */
public class GameObject implements ObjectsInterface {
	/**
	 * public static final int WALL_TYPE = 0 - obiekt, ktory jest sciana public
	 * static final int BOX_TYPE =1 - obiekt, ktory jest skrzynia public static
	 * final int DYNAMITE_TYPE = 2 - obiekt, kory jest dynamitem public static
	 * final int DYNAMITE_POWERUP = 3 - obiekt, odpowiadajacy za dodanie
	 * dynamitow dla gracza public static final int STRENGHT_POWERUP = 4 -
	 * obiekt, odpowiadajacy za wzmocnienie sily wybuchu public static final int
	 * SPEED_POWERUP = 5 - obiekt, odpowiadajacy za zwiekszenie szybkosci gracza
	 */
	public static final int WALL_TYPE = 0;
	public static final int BOX_TYPE = 1;
	public static final int DYNAMITE_TYPE = 2;
	public static final int DYNAMITE_POWERUP = 3;
	public static final int STRENGHT_POWERUP = 4;
	public static final int SPEED_POWERUP = 5;

	private int type;

	private boolean destructible;
	private boolean solid;

	/**
	 * Utoworzenie nowego obiektu o zadanym typie
	 * 
	 * @param type
	 *            typ nowotworzonego obiektus
	 */
	public GameObject(int type) {
		this.type = type;
		switch (type) {
		case WALL_TYPE:
			destructible = false;
			solid = true;
			break;
		case BOX_TYPE:
			destructible = true;
			solid = true;
			break;
		case DYNAMITE_TYPE:
		case DYNAMITE_POWERUP:
		case STRENGHT_POWERUP:
		case SPEED_POWERUP:
			destructible = false;
			solid = false;
			break;
		}
	}

	/**
	 * Metoda okreslajaca czy obiekt jest zniszczalny
	 * 
	 * @return zniszczalnosc obiektu (false - jezeli nie jest zniszczalny, true
	 *         - jeżeli jest zniszczalny)
	 */
	public boolean isDestructible() {
		return destructible;
	}

	/**
	 * Metoda okreslajaca czy obiekt jest staly
	 * 
	 * @return stalosc obiektu (false - jeżeli obiekt nie jest staly, true -
	 *         jeżeli obiekt jest staly)
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Metoda sprawdzajaca jaki typ ma dany objekt na planszy
	 * 
	 * @return typ obiektu
	 */
	public int getType() {
		return type;
	}


	/**
	 * Metoda do sprawdzania czy obiekt wybucha
	 * 
	 * @return zwraca true gdy byla eksplozja, false w przeciwnym przypadku
	 */
	public boolean isExploding() {
		return false;
	}

	/**
	 * Metoda wywolujaca update obiektow w tablicy od czasu
	 * 
	 * @param delta
	 *            podawany czas update'u
	 * @param gameData
	 *            obiekt GamaData, w ktorym znajduje sie tablica
	 */
	public void update(float delta, GameData gameData) {
	}
}
