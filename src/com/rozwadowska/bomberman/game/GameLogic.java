package com.rozwadowska.bomberman.game;

import com.rozwadowska.bomberman.game.objects.GameObject;
import com.rozwadowska.bomberman.game.objects.Player;

/**
 * Klasa odpowaidajaca za logike gry.
 * 
 * @author Monika
 *
 */
public class GameLogic {
	private GameData gameData;

	public GameLogic(GameData gameData) {
		this.gameData = gameData;
	}
	
	/**
	 * Metoda wywolująca update obietow klasy Player oraz GameObject
	 * 
	 * @param delta czas updae'owania
	 */
	public void update(float delta) {
		for (Player player : gameData.player) {
			player.update(delta, gameData);
		}
		for (GameObject[] objects : gameData.board) {
			for (GameObject o : objects)
				if (o != null)
					o.update(delta, gameData);
		}
	}

}
