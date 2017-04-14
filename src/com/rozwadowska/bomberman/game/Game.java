package com.rozwadowska.bomberman.game;

import com.rozwadowska.bomberman.game.objects.Player;
import com.rozwadowska.bomberman.gui.GameWindow;
import com.rozwadowska.bomberman.gui.ImagePanel;
import com.rozwadowska.bomberman.gui.Music;

/**
 * Glowna klasa gry, jest odpowiedzialan za zarzadzanie danymi,logika oraz
 * grafika gry.
 * 
 * @author Monika
 *
 */
public class Game implements Runnable {

	/**
	 * FPS - ilosc klatek na sekunde SECOND - sekunda w miliskeundach
	 * (milisekundy - by uzyskac wieksza dokladnosc) NS_PER_FRAME - czas co jaki
	 * ma byc odswiezany obraz w milisekundach
	 */
	private static final int FPS = 60;
	private static final int SECOND = 1000000000;
	private static final int NS_PER_FRAME = SECOND / FPS;

	private GameData gameData;
	private GameLogic gameLogic;
	private GameRenderer gameRenderer;
	private boolean running;

	/**
	 * looser - przegrany gracz
	 */
	private Integer looser;

	public Game(ImagePanel viewPanel) {
		gameData = new GameData();
		gameLogic = new GameLogic(gameData);
		gameRenderer = new GameRenderer(gameData, viewPanel);
		running = false;
		looser = null;
	}

	/**
	 * Metoda odpowiadajaca za utworzenie nowej gry, jezeli gra nie jest
	 * uruchomiona
	 */
	public void start() {
		if (!running) {
			running = true;
			new Thread(this).start();
		}
	}

	/**
	 * Metoda wywolywana w trakcie dzialania gry. Co okreslony okres czasu
	 * wymusza update'y gry oraz generowanie obrazu.
	 * 
	 * W przypadku wykrycia zakończenia gry wysyla informacje do klasy
	 * GameWindow w celu poinformowania jaki panel nalezy teraz wyswietlic.
	 */
	@Override
	public void run() {
		Music T1 = new Music();
	    T1.start();
		long lastTime = System.nanoTime();
		long now;
		long delta = 0;
		while (running) {
			now = System.nanoTime();
			delta += now - lastTime;
			lastTime = now;
			if (delta >= NS_PER_FRAME) {
				gameLogic.update((float) NS_PER_FRAME / SECOND);
				gameRenderer.render();
				delta -= NS_PER_FRAME;
			}
			for (Player player : gameData.player) {
				if (player.getPlayerLives() <= 0) {
					if (looser == null)
						looser = player.getPlayerNumber();
					else
						looser = -1;
				}
			}
			if (looser != null)
				running = false;
		}
		T1.terminate();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GameWindow.update(looser);
	}
}
