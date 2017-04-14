package com.rozwadowska.bomberman.input;

/**
 * Klasa UserInput odpowiedzialna za "tlumaczenie" wcisniec przyciskow przez
 * graczy na ich faktyczne intencje.
 * 
 * @author Monika
 *
 */
public class UserInput {
	/**
	 * private static boolean[] up - tablica okreslajaca za stan klawisza poruszania sie gracza do gory
	 * private static boolean[] down - tablica okreslajaca za stan klawisza poruszania sie gracza do dolu
	 * private static boolean[] left - tablica okreslajaca za stan klawisza poruszania sie gracza w lewo
	 * private static boolean[] right - tablica okreslajaca za stan klawisza poruszania sie gracza w prawo
	 * private static boolean[] action - tablica okreslajaca za stan klawisza stawiania dynamitu przez gracza
	 */
	// inicjalizacja zmiennej na new boolean : {false,false}
	private static boolean[] up = new boolean[2];
	private static boolean[] down = new boolean[2];
	private static boolean[] left = new boolean[2];
	private static boolean[] right = new boolean[2];
	private static boolean[] action = new boolean[2];

	/**
	 * Metoda okreslajaca zachownie gracza wzgledem osi X
	 * 
	 * @param playerNumber
	 *            numer gracza, ktory wcisna przycisk
	 * @return: 
	 * -1 jezeli zostal wcisniety przycisk do poruszania sie w lewo
	 *  1 jezeli zostal wcisniety przycisk do poruszania sie w prawo 
	 *  0 w przeciwnym przypadku
	 */
	public static int getXAxis(int playerNumber) {
		if (!right[playerNumber] && left[playerNumber])
			return -1;
		if (right[playerNumber] && !left[playerNumber])
			return 1;
		else
			return 0;
	}

	/**
	 * Metoda okreslajaca zachownie gracza wzgledem osi Y
	 * 
	 * @param playerNumber
	 *            numer gracza, ktory wcisna przycisk
	 * @return: 
	 * -1 jezeli zostal wcisniety przycisk do poruszania sie w gore 
	 *  1 jezeli zostal wcisniety przycisk do poruszania sie w dol 
	 *  0 w przeciwnym przypadku
	 */
	public static int getYAxis(int playerNumber) {
		if (up[playerNumber] && !down[playerNumber])
			return -1;
		if (!up[playerNumber] && down[playerNumber])
			return 1;
		else
			return 0;
	}

	/**
	 * Metoda okreslajaca czy dany gracz wcisnal przyciski do stawiania
	 * dynamitow
	 * 
	 * @param playerNumber
	 *            numer gracza, ktory wcisnal przycisk
	 * @return wartosc tablicy action[playerNumber]
	 */
	public static boolean isActionPressed(int playerNumber) {
		return action[playerNumber];
	}
	
	/**
	 * Metoda ustawiajaca wartosc w tablicy up[playerNumber]
	 * 
	 * @param value boolowska wartosc  wpisywana do tablicy up[playerNumber]
	 * @param playerNumber numer gracza, ktorego dotyczy ustawianie wartosci tablicy up[]
	 */
	public static void setUp(boolean value, int playerNumber) {
		up[playerNumber] = value;
	}
	
	/**
	 * Metoda ustawiajaca wartosc w tablicy down[playerNumber]
	 * 
	 * @param value boolowska wartosc  wpisywana do tablicy down[playerNumber]
	 * @param playerNumber numer gracza, ktorego dotyczy ustawianie wartosci tablicy down[]
	 */
	public static void setDown(boolean value, int playerNumber) {
		down[playerNumber] = value;
	}
	
	/**
	 * Metoda ustawiajaca wartosc w tablicy left[playerNumber]
	 * 
	 * @param value boolowska wartosc  wpisywana do tablicy left[playerNumber]
	 * @param playerNumber numer gracza, ktorego dotyczy ustawianie wartosci tablicy left[]
	 */
	public static void setLeft(boolean value, int playerNumber) {
		left[playerNumber] = value;
	}
	
	/**
	 * Metoda ustawiajaca wartosc w tablicy right[playerNumber]
	 * 
	 * @param value boolowska wartosc  wpisywana do tablicy right[playerNumber]
	 * @param playerNumber numer gracza, ktorego dotyczy ustawianie wartosci tablicy right[]
	 */
	public static void setRight(boolean value, int playerNumber) {
		right[playerNumber] = value;
	}

	/**
	 * Metoda ustawiajaca wartosc w tablicy action[playerNumber]
	 * 
	 * @param value boolowska wartosc  wpisywana do tablicy action[playerNumber]
	 * @param playerNumber numer gracza, ktorego dotyczy ustawianie wartosci tablicy action[]
	 */
	public static void setAction(boolean value, int playerNumber) {
		action[playerNumber] = value;
	}
}
