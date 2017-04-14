package com.rozwadowska.bomberman.game.objects;

import com.rozwadowska.bomberman.game.GameData;

/**
 * Interface uzywany do objektow na plaszny.
 * 
 * @author Monika
 *
 */
public interface ObjectsInterface {
	
	public void update(float delta, GameData gameData);
	public boolean isExploding();

}
