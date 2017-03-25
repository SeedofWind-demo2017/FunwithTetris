package lab10;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import lab10.ShapeModel.Piece;

/**
 * ScorePanel extends Jpanel and is responsible for displaying the status of the game including
  * game status
  * game score
  * control instructions
  * next Piece

It has 
  *  a group of static final constants that specify the fonts and board dimension (see detail in file)
  *  a score int that is the score of the current game
  *  a String status that indicates the status of the game
  *  a previewShape that tells the shape of the next falling piece 

This companent will simply display the msg updated by the boardViewcontroller through the update method. It will also draw the nextPiece which is a smaller version.
 * @author SeedZ
 *
 */
public class ScorePanelViewController extends JPanel {

	
	/*
	 * Dimension constants for the score panel
	 */

	private static final long serialVersionUID = 1L;

	private static final int TILE_SIZE = 12;

	private static final int SQUARE_CENTER_X = 130;

	private static final int SQUARE_CENTER_Y = 65;

	private static final int SQUARE_SIZE = 30;

	private static final int SMALL_INSET = 20;

	private static final int LARGE_INSET = 40;

	private static final int CONTROLS_INSET = 300;

	private static final int TEXT_STRIDE = 25;

	private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);

	private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);

	private static final Color DRAW_COLOR = new Color(128, 192, 128);
	private static final int STATS_INSET = 175;
	
	private static final int TILE_MADE_SIZE =4;

	
	
	/*
	 *  a score int that is the score of the current game
	 */
	public int score = 0;
	/*
	 * String indicates the status of the game
	 */
	public String status = " ";
	/**
	 * Shape of the next Piece
	 */
	public ShapeModel previewShape;

	public ScorePanelViewController(TetrisController game) {
		this.score = 0;
		setPreferredSize(new Dimension(200, BoardViewController.PANEL_HEIGHT));
		//Set background to be black to be cool
		setBackground(Color.BLACK);
	}
/**
 * 
 * @param shape
 * @param score
 * @param status
 */
	public void update(ShapeModel shape, int score, String status) {
		this.score = score;
		this.previewShape = shape;
		if (!status.equals(" "))
			this.status = status;

	}

	@Override
	public void paintComponent(Graphics g) {
		if (this.status.equals("Game Over")) {
			this.status = "PLAY";
			this.score = 0;
		}
		super.paintComponent(g);

		// Set the color for drawing.
		g.setColor(DRAW_COLOR);

	
		int offset;

		/*
		 * Draw the "Status" category.
		 */
		g.setFont(LARGE_FONT);
		g.drawString("Status: " + this.status, SMALL_INSET, offset = STATS_INSET);
		g.setFont(SMALL_FONT);

		g.drawString("Score: " + this.score, LARGE_INSET, offset += TEXT_STRIDE);

		/*
		 * Draw the "Controls" category.
		 */
		g.setFont(LARGE_FONT);
		g.drawString("Controls", SMALL_INSET, offset = CONTROLS_INSET);
		g.setFont(SMALL_FONT);
		g.drawString("A - Move Left", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("D - Move Right", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("W - Rotate", LARGE_INSET, offset += TEXT_STRIDE);

		g.drawString("Space - Drop", LARGE_INSET, offset += TEXT_STRIDE);
		g.drawString("P - Pause Game", LARGE_INSET, offset += TEXT_STRIDE);

		/*
		 * Draw the next piece preview box.
		 */
		g.setFont(LARGE_FONT);
		g.drawString("Next Piece:", SMALL_INSET, 70);
		g.drawRect(SQUARE_CENTER_X - SQUARE_SIZE, SQUARE_CENTER_Y - SQUARE_SIZE, SQUARE_SIZE * 3, SQUARE_SIZE * 3);
		System.out.println(previewShape.getShape());
		if (previewShape.getShape() != ShapeModel.Piece.NoShape) {

			int startX = (SQUARE_CENTER_X);
			int startY = (SQUARE_CENTER_Y);
			for (int i = 0; i < TILE_MADE_SIZE; ++i) {
				int x = startX + previewShape.getx(i) * TILE_SIZE;
				int y = startY + previewShape.gety(i) * TILE_SIZE;
				drawSquare(g, x, y, previewShape.getShape());
			}
		}

	}

	
	/**
	 * Draw logical square
	 * @param g graphcis
	 * @param x x coord
	 * @param y y coord
	 * @param shape shape of the square to determine the color 
	 */

	public void drawSquare(Graphics g, int x, int y, Piece shape) {
		Color colors[] = { 
				BoardViewController.col1,
				BoardViewController.col2,
				BoardViewController.col3,
				BoardViewController.col4,
				BoardViewController.col5,
			
				
		};

		Color color = colors[shape.ordinal()];

		g.setColor(color);
		/*
		 * -1 for the clearer boarder of the tile
		 */
		g.fillRect(x , y , TILE_SIZE-1, TILE_SIZE-1);

	}

}
