package com.rozwadowska.bomberman.main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Klasa do tworzenia obiektow typu BufferdImage oraz ImageIcon
 * 
 * @author Monika
 *
 */
public class ResourceLoader {

	/**
	 * Metoda do tworzenia obiektu typu BufferdImage
	 * 
	 * @param path sciezka do pliku
	 * @return nowa obiekt typu BufferedImage
	 */
	public static BufferedImage getImage(String path) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			return ImageIO.read(classLoader.getResourceAsStream(path));
		} catch (Exception e) {
			e.printStackTrace();
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		}
	}
	
	/**
	 * Metoda do tworzenia obiektow typu ImageIcon
	 * 
	 * @param path sciezka do pliku
	 * @return nowa obiekt typu ImageIcon
	 */
	public static ImageIcon getImageIcon(String path) {
		return new ImageIcon(getImage(path));
	}

}
