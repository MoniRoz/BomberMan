package com.rozwadowska.bomberman.gui;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.rozwadowska.bomberman.game.Game;

/**
 * Klasa odpowiedzialana za odtwarzanie muzyki podczas gry
 * @author Monika
 *
 */
public class Music extends Thread {
	private Thread t;
	boolean running;

	public void run() {
		while (running) {
			try {
				playSound();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
			running = true;
		}
	}
	
	/**
	 * Metoda wywolywana by przerwac granie muzyki
	 */
	public void terminate() {
		running = false;
	}
	
	/**
	 * Metoda odtwarzajaca muzyke
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream AIS = AudioSystem.getAudioInputStream(Game.class.getResource("/music/TheFatRatCut.wav"));
		AudioFormat format = AIS.getFormat();
		SourceDataLine playbackLine = AudioSystem.getSourceDataLine(format);
		playbackLine.open(format);
		playbackLine.start();
		int bytesRead = 0;
		byte[] buffer = new byte[123000];
		while (bytesRead != -1 && running) {
			bytesRead = AIS.read(buffer, 0, buffer.length);
			if (bytesRead >= 0)
				playbackLine.write(buffer, 0, bytesRead);
		}
		playbackLine.drain();
		playbackLine.close();

	}
}
