package com.rozwadowska.bomberman.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.rozwadowska.bomberman.main.ResourceLoader;

/**
 * Klasa tworzaca obiekty typu MenuPanel. Posiada tlo, rozmiar oraz przyciski.
 * Glowny panel startowy gry oraz panel po zakończeniu gry.
 * 
 * @author Monika
 *
 */
@SuppressWarnings("serial")
public class MenuPanel extends ImagePanel {

	private static ImageIcon startNewGameDown;
	private static ImageIcon startNewGameUp;
	private static ImageIcon exitDown;
	private static ImageIcon exitUp;

	/**
	 * Kontruktor klasy MenuPanel. Ustawia odpowienie tlo oraz rozmiar panelu.
	 * Tworzone sa rowniez oiekty typu ImageIcon na przyciski oraz same
	 * przuyciski. Po wcisnieciu przycisku startButton zostanie wywolana nowa
	 * gra w ogownym oknie gry, zas wcisniecie przycisku exitButton sprawi, ze
	 * zamkniemy okno gry.
	 * 
	 * @param image
	 *            zadane tlo panelu
	 * @param width
	 *            zadana dlugosc panelu
	 * @param height
	 *            zadana wysokosc panelu
	 */
	public MenuPanel(BufferedImage image, int width, int height) {

		super(image, width, height);

		startNewGameDown = ResourceLoader.getImageIcon("bombermenu/new_game.png");
		startNewGameUp = ResourceLoader.getImageIcon("bombermenu/new_game_sel.png");
		exitDown = ResourceLoader.getImageIcon("bombermenu/exit.png");
		exitUp = ResourceLoader.getImageIcon("bombermenu/exit_sel.png");

		JButton startButton = makeMenuButton(startNewGameDown, startNewGameUp);
		JButton exitButton = makeMenuButton(exitDown, exitUp);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// padding
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0;
		c.gridy = 0;
		add(startButton, c);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameWindow.startNewGame();
			}
		});

		c.gridy = 1;
		add(exitButton, c);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Klasa pomocnicza uzywana do tworzenia obiektow typu JButton z dwoma
	 * oniektami typu ImageIcon.
	 * 
	 * @param down
	 *            obiekt typu ImageIcon, jaki przycisk ma okreslony jako obrazek
	 *            startowy.
	 * @param up
	 *            obiekt typu ImageIcon, jaki przycisk ma kiedy zostaje
	 *            najechany myszka lub przy wcisnieciu.
	 * @return nowy obiekt typu JButton o zadanych parametrach.
	 */
	private static JButton makeMenuButton(ImageIcon down, ImageIcon up) {
		JButton button = new JButton(down);
		button.setContentAreaFilled(false);
		button.setBorder(null);
		button.setRolloverIcon(up);
		button.setPressedIcon(up);
		return button;
	}
}
