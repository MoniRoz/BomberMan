package com.rozwadowska.bomberman.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Klasa tworzaca obiekty typu ImagePanel. Obiekt ten bedzie glownym panelem
 * gry. Posiada tlo oraz odpowiedni rozmiar.
 * 
 * @author Monika
 *
 */

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private BufferedImage image;

	/**
	 * Kontruktor klasy ImagePanel. Ustawia odpowienie tlo oraz rozmiar panelu.
	 * 
	 * @param image
	 *            zadane tlo panelu
	 * @param width
	 *            zadana dlugosc panelu
	 * @param height
	 *            zadana wysokosc panelu
	 */
	public ImagePanel(BufferedImage image, int width, int height) {

		this.image = image;
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
	}

	/**
	 * Metoda rysujaca tlo panelu
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);

	}

	/**
	 * Metoda wywolywana do ponownego ustawienia tla dla panelu. Wywolywana przy
	 * renderowaniu gry w celu ukazania na ekranie zmian na ekranie.
	 * 
	 * @param image
	 *            Obiekt typu BufferedImage jak nalezy podmienic dla panelu
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
		// update
		repaint();
	}
}
