package com.rozwadowska.bomberman.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.rozwadowska.bomberman.game.objects.Dynamite;
import com.rozwadowska.bomberman.game.objects.GameObject;
import com.rozwadowska.bomberman.game.objects.Player;
import com.rozwadowska.bomberman.gui.ImagePanel;
import com.rozwadowska.bomberman.main.Main;
import com.rozwadowska.bomberman.main.ResourceLoader;

/**
 * Klasa GameRenderer jest to klasa odpowiedzialna za renderowanie grafiki.
 * 
 * @author Monika
 *
 */
public class GameRenderer {
	/**
	 * FIELD_SIZE - wielkosc generowanych pol
	 */
	public static final int FIELD_SIZE = 64;

	private GameData gameData;
	private ImagePanel viewPanel;
	private BufferedImage[][][] playerImage;
	private BufferedImage image;
	private BufferedImage textures;
	private BufferedImage background;
	private BufferedImage explosionImage[];

	public GameRenderer(GameData gameData, ImagePanel viewPanel) {

		this.gameData = gameData;
		this.viewPanel = viewPanel;
		background = ResourceLoader.getImage("textures/background.png");
		textures = ResourceLoader.getImage("textures/sprites.png");
		playerImage = new BufferedImage[2][4][4];
		explosionImage = new BufferedImage[7];
		createPlayersImage();
		createExplosionImage();
	}
	
	/**
	 * Metoda tworzaca obrazki dla graczy.
	 */
	private void createPlayersImage() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 3; j++) {
				playerImage[0][i][j] = textures.getSubimage(32 * i, 54 + 64 * j, 32, 38);
				playerImage[1][i][j] = textures.getSubimage(32 * i, 250 + 64 * j, 32, 38);
			}
		playerImage[0][3][3] = textures.getSubimage(0, 0, 32, 32);
		playerImage[1][3][3] = textures.getSubimage(0, 0, 32, 32);
	}
	
	/**
	 * Metoda tworzaca obrazki dla ekspolzji.
	 */
	private void createExplosionImage() {
		explosionImage[0] = textures.getSubimage(224, 0, 32, 32);
		explosionImage[1] = textures.getSubimage(288, 0, 32, 32);
		explosionImage[2] = Translate(2, textures.getSubimage(288, 0, 32, 32));
		explosionImage[3] = Translate(3, textures.getSubimage(288, 0, 32, 32));
		explosionImage[4] = Translate(4, textures.getSubimage(288, 0, 32, 32));
		explosionImage[5] = textures.getSubimage(256, 0, 32, 32);
		explosionImage[6] = Translate(6, textures.getSubimage(256, 0, 32, 32));
	}
	
	/**
	 * Metoda wywołujaca wszytskie rysujace funkcjie w klasie.
	 */
	public void render() {
		image = new BufferedImage(Main.WIDTH + 250, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		drawBoard(g);
		drawExplosions(g);
		drawPlayer(g);
		g.dispose();
		viewPanel.setImage(image);
	}
	
	/**
	 * Metoda rysujaca obiekty na planszy poza graczem
	 * 
	 * @param g
	 */
	private void drawBoard(Graphics g) {
		GameObject[][] board = gameData.board;
		g.drawImage(background, 0, 0, board.length * FIELD_SIZE, board[0].length * FIELD_SIZE, null);

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				GameObject go = board[i][j];

				if (go == null)
					continue;

				switch (go.getType()) {
				case GameObject.WALL_TYPE:
					drawWall(g, i, j);
					break;
				case GameObject.BOX_TYPE:
					drawBox(g, i, j);
					break;
				case GameObject.DYNAMITE_TYPE:
					drawDynamite(g, i, j);
					break;
				case GameObject.DYNAMITE_POWERUP:
				case GameObject.STRENGHT_POWERUP:
				case GameObject.SPEED_POWERUP:
					drawPowerUp(g, i, j);
					break;
				}
			}
		}
	}
	
	/**
	 * Metoda rysujaca sciane od zadanej pozycji.
	 * 
	 * @param g
	 * @param x pozycja x sciany
	 * @param y pozycja y sciany
	 */
	private void drawWall(Graphics g, int x, int y) {
		g.drawImage(textures.getSubimage(32, 0, 32, 32), x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE, null);

	}
	
	/**
	 * Metoda rysujaca skrzynie na planszy w zaleznosci od pozycji.
	 * 
	 * @param g
	 * @param x pozycja x skrzyni
	 * @param y pozycja y skrzyni
	 */
	private void drawBox(Graphics g, int x, int y) {
		g.drawImage(textures.getSubimage(64, 0, 32, 32), x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE, null);

	}
	
	/**
	 * Metoda rysujaca dynamity na planszy w zaleznosci od pozycji.
	 * 
	 * @param g
	 * @param x pozycja x dynamitu
	 * @param y pozycja y dynamitu
	 */
	private void drawDynamite(Graphics g, int x, int y) {
		g.drawImage(textures.getSubimage(96, 0, 32, 32), x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE, null);

	}

	/**
	 * Metoda rysujaca powerUp'y na planszy w zaleznosci od pozycji.
	 * 
	 * @param g
	 * @param x pozycja x powerUp'u
	 * @param y pozycja y powerUp'u
	 */
	private void drawPowerUp(Graphics g, int x, int y) {
		if (gameData.board[x][y].getType() == GameObject.DYNAMITE_POWERUP)
			g.drawImage(textures.getSubimage(128, 0, 32, 32), x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE,
					null);
		else if (gameData.board[x][y].getType() == GameObject.STRENGHT_POWERUP)
			g.drawImage(textures.getSubimage(160, 0, 32, 32), x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE,
					null);
		else
			g.drawImage(textures.getSubimage(192, 0, 32, 32), x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE,
					null);

	}

	/**
	 * Metoda rysujaca eksplozje w zależnosc od okreslonego przypadku.
	 * 
	 * @param g
	 */
	private void drawExplosions(Graphics g) {
		int[][] explosionMap = gameData.explosionMap;
		for (int i = 0; i < explosionMap.length; i++) {
			for (int j = 0; j < explosionMap[i].length; j++) {
				if (explosionMap[i][j] > 0) {
					switch (whichExpolosion(i, j)) {
					case 0:
						g.drawImage(explosionImage[0], i * FIELD_SIZE, j * FIELD_SIZE, FIELD_SIZE,
								FIELD_SIZE, null);
						break;
					case 1:
						g.drawImage(explosionImage[1], i * FIELD_SIZE, j * FIELD_SIZE, FIELD_SIZE,
								FIELD_SIZE, null);
						break;
					case 2:
						g.drawImage(explosionImage[2], i * FIELD_SIZE, j * FIELD_SIZE,
								FIELD_SIZE, FIELD_SIZE, null);
						break;
					case 3:
						g.drawImage(explosionImage[3], i * FIELD_SIZE, j * FIELD_SIZE,
								FIELD_SIZE, FIELD_SIZE, null);
						break;
					case 4:
						g.drawImage(explosionImage[4] , i * FIELD_SIZE, j * FIELD_SIZE,
								FIELD_SIZE, FIELD_SIZE, null);
						break;
					case 5:
						g.drawImage(explosionImage[5], i * FIELD_SIZE, j * FIELD_SIZE, FIELD_SIZE,
								FIELD_SIZE, null);
						break;
					case 6:
						g.drawImage(explosionImage[6], i * FIELD_SIZE, j * FIELD_SIZE,
								FIELD_SIZE, FIELD_SIZE, null);
						break;
					default:
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Metoda rysujaca graczy na planszy.
	 * 
	 * @param g
	 */
	private void drawPlayer(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(Main.WIDTH + 1, 0, 250, Main.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.BOLD, 40));
		g.drawString("PLAYER 0", Main.WIDTH + 1, 40);
		g.drawString("PLAYER 1", Main.WIDTH + 1, Main.HEIGHT / 2);
		for (Player player : gameData.player) {
			g.drawImage(playerImage[player.getPlayerNumber()][player.getPlayerDir()][player.getPlayerMove()],
					(int) (player.getX() * FIELD_SIZE), (int) (player.getY() * FIELD_SIZE), FIELD_SIZE, FIELD_SIZE,
					null);
			if (player.getPlayerNumber() == 0) {
				g.drawString("Lives: " + player.getPlayerLives(), Main.WIDTH + 1, 80);
			} else {
				g.drawString("Lives: " + player.getPlayerLives(), Main.WIDTH + 1, Main.HEIGHT / 2 + 40);
			}
		}
	}

	/**
	 * Metoda obrotu obrazkow w zaleznosci od przypadku.
	 * 
	 * @param i przypadek
	 * @param img obrazek do obrocenia
	 * @return nowy obrocony obrazek
	 */
	private BufferedImage Translate(int i, BufferedImage img) {
		AffineTransform transform = new AffineTransform();
		AffineTransformOp op;
		switch (i) {
		case 2:
			transform.rotate(Math.PI, img.getWidth() / 2, img.getHeight() / 2);
			op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			img = op.filter(img, null);
			break;
		case 3:
			transform.rotate(3 * Math.PI / 2, img.getWidth() / 2, img.getHeight() / 2);
			op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			img = op.filter(img, null);
			break;
		case 4:
			transform.rotate(Math.PI / 2, img.getWidth() / 2, img.getHeight() / 2);
			op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			img = op.filter(img, null);
			break;

		case 6:
			transform.rotate(Math.PI / 2, img.getWidth() / 2, img.getHeight() / 2);
			op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			img = op.filter(img, null);
			break;

		}
		return img;
	}
	
	/**
	 * Metoda do okreslania typu eksplozji w zaleznosci od pozycji na planszy.
	 * 
	 * @param x pozycja x ekspozji
	 * @param y pozycja y ekspolzji
	 * @return typ eklspozji
	 */
	private int whichExpolosion(int x, int y) {
		int[][] explosionMap = gameData.explosionMap;
		
		/**
		 * epicentrum wybuchu
		 */
		if (gameData.board[x][y] != null && gameData.board[x][y].getType() == GameObject.DYNAMITE_TYPE)
		{
			gameData.board[x][y] = (Dynamite)gameData.board[x][y];
			if(gameData.board[x][y].isExploding())
					return 0;
		}
	
		/**
		 *  po prawej
		 */
		if (explosionMap[x + 1][y] == 0 && explosionMap[x][y - 1] == 0 && explosionMap[x][y + 1] == 0
				&& explosionMap[x - 1][y] > 0)
			return 1;
		
		/**
		 *  po lewej
		 */
		if (explosionMap[x + 1][y] > 0 && explosionMap[x][y - 1] == 0 && explosionMap[x][y + 1] == 0
				&& explosionMap[x - 1][y] == 0)
			return 2;
		
		/**
		 *  gora
		 */
		if (explosionMap[x + 1][y] == 0 && explosionMap[x][y - 1] == 0 && explosionMap[x][y + 1] > 0
				&& explosionMap[x - 1][y] == 0)
			return 3;
		
		/**
		 *  dol
		 */
		if (explosionMap[x + 1][y] == 0 && explosionMap[x][y - 1] > 0 && explosionMap[x][y + 1] == 0
				&& explosionMap[x - 1][y] == 0)
			return 4;

		/**
		 *  srodek osX
		 */
		if (explosionMap[x + 1][y] > 0 && explosionMap[x - 1][y] > 0)
			return 5;
		
		/**
		 *  srodek osY
		 */
		if (explosionMap[x][y + 1] > 0 && explosionMap[x][y - 1] > 0)
			return 6;

		return -1;
	}

}
