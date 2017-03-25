package lab10;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;


import javax.swing.JPanel;
import javax.swing.Timer;
//Run on event thread

import lab10.ShapeModel.Piece;


/**
 * BoardViewController extends Jpanel holds all the logic regarding the game itself. 
It has 
  * Group of Static final constants that descrbing the board layout and piece color, and the timer delay (see detail in the file)
  * a (swing)*timer* instance that we use to time our game, actionPerformed will be invoked every 300ms
  * boolean variables
    * isNewGame determines whether it is a new game
    * currentPieceDone determines whether the current piece finished falling (reach bottom, hit another piece)
    * isStarted determines whether the game has been started 
    * isPaused determines whether the game has been paused
    * morethanoneGame determines whether the game is played more than one time (to display different welcome msg)
    * SpawnCreated is used to tell whether we have several lines to be removed to stop us from spawning new pieces twice
  * lineAchived keeps track of how many lines have been successfully removed (score = 10*this number)
  * currentx/y gives us the location of the  current falling piece 
  * nextpiece gives the shape of the next piece falling
  * currenpiece gives the shape of the current piece falling
  * board is a 2d array that keeps track of the shape of each logical square 

We devide the board to logical squares, we have Board_height*Board_width number of squares. Each Tile is made up with four suqares. *WE MOVE BY SQUARE, NOT PIEXEL*

Essentially, each iteration of animation, we will draw all these squares according to our board model.

BoardViewController implements Anim, which extends the actionListener interface. BoardViewcontroller will listen to the timer, every clock cycle, actionPerforemd of this classwill be invoked 

        
        if (currentPieceDone) {
			currentPieceDone = false;
			Spawn();
		} else {
			nextIteration();
		}
	
        
If the falling of currentpiece is done we go to nextIteraton, if not we spawn a new piece. This is how the game will progress without the key interruption. 
BoardViewController extends Jpanel. we implement addKeyListener class to listen to keyInput. KeyInput is handled by TetrisApdater(discuss later).

I chose Adapter over listener since i only want to implement keyPressed.
 * 
 * @author SeedZ
 *
 */
public class BoardViewController extends JPanel implements Anim {

	private static final long serialVersionUID = 1L;
	/*
	 * Board Width and Height
	 */
	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_HEIGHT = 22;

	/*
	 * Center of the Board
	 */
	private static final int CENTER_X = 120;
	
	/*
	 * Tile Width and Height for each individual tile (piece)
	 */
	private static final int TILE_WIDTH = 25;
	private static final int TILE_HEIGHT = 21;
	/*
	 * Where each peice start falling
	 */
	private static final int BOARDTOP = 6;
	
	/*
	 * Dimension constants for this panel
	 */
	public static final int PANEL_WIDTH = 250;
	public static final int PANEL_HEIGHT = 490;
	
	/*
	 * Time Delay for the timer
	 */
	public static final int FREQUENCY = 300;
	/*
	 * How many square to represent one tile
	 */
	public static final int TILE_MADE_SIZE = 4;
	
	/*
	 * coordinates for showing the enter/notification msg  on the game board
	 */
	public static final int ENTER_WINDOW_LOCATION = 300;
	public static final int NOTIFICATION_WINDOW_LOCATION = 150;

	/**
	 * 5 colors for 5 type of pieces (no shape as the first one )
	 */
	public static final Color col1 = new Color(0, 0, 0);
	public static final Color col2 = new Color(204, 102, 102);
	public static final Color col3 = new Color(102, 204, 102);
	public static final Color col4 = new Color(102, 102, 204);
	public static final Color col5 = new Color(204, 204, 102);


	
	
	
	/*
	 * a (swing)timer instance that we use to time our game, 
	 * actionPerformed will be invoked every 300ms according to our setting
	 */
	public Timer timer;
	/*
	 * determines whether it is a new game
	 */
	public boolean isNewGame = true;
	/*
	 * determines whether the current piece finished falling (reach bottom, hit another piece)
	 */
	public boolean currentPieceDone = false;
	/*
	 * isStarted determines whether the game has been started
	 */
	public boolean isStarted = false;
	/*
	 * determines whether the game has been paused
	 */
	public boolean isPaused = false;
	
	/*
	 * determines whether the game is played more than one time (to display different welcome msg)
	 */
	public boolean morethanoneGame = false;
	/*
	 * is used to tell whether we have several lines to be removed to stop us from spawning new pieces twice
	 */
	public boolean SpawnCreated = false;
	
	/*
	 *  keeps track of how many lines have been successfully removed (score = 10*this number)
	 */
	public int lineAchived = 0;
	/**
	 * location of current falling piece
	 */
	public int currentx = 0;
	public int currenty = 0;

	/*
	 * Shape of nextpiece
	 */
	public ShapeModel nextpiece;
	/*
	 * Shape of current piece
	 */
	public ShapeModel currentpiece;
	
	/*
	 * board is a 2d array that keeps track of the shape of each logical square 
	 */
	public Piece[][] board;

	/*
	 * Tetris is our GameController where we used to communicate with scorePanel
	 */
	public TetrisController tetirs;

	@Override
	/**
	 * overrided actionperformed to drive the game 
	 * If the falling of currentpiece is done we go to nextIteraton, if not we spawn a new piece. This is how the game will progress without the key interruption. 
	 * BoardViewController extends Jpanel. we implement addKeyListener class to listen to keyInput. KeyInput is handled by TetrisApdater(discuss later).
	 */
	public void actionPerformed(ActionEvent e) {
		if (currentPieceDone) {
			currentPieceDone = false;
			Spawn();
		} else {
			nextIteration();
		}
	}

	/**
	 * Constructor.
	 * Initialize all necessary variables and start the time
	 * @param tetrisgame
	 */
	public BoardViewController(TetrisController tetrisgame) {

		this.tetirs = tetrisgame;

		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		//Since everything happen in the event thread, we do this to set the focus 
		//to the board to be able to listen to the key input
		setFocusable(true);
		currentpiece = new ShapeModel();
		nextpiece = new ShapeModel();
		board = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
		
		
		timer = new Timer(FREQUENCY, this);
		//We add the event handler, keylistner in this case to our panel
		addKeyListener(new TetrisAdapter(this));
		
		
		clearBoard();
		timer.start();

	}



	/**
	 * Start The game by 
	 * 1. initializing all the instance variables
	 * 2. Spawn a new piece and notify the score panel
	 * 3. notice we actually stop the timer here to wait for the "ENTER" from the keyboard
	 */
	public void start() {
		if (isPaused)
			return;

		isStarted = true;
		currentPieceDone = false;
		lineAchived = 0;
		clearBoard();

		nextpiece.setRandomShape();
		this.tetirs.updateStatus(nextpiece, lineAchived * 10, "PRESS ENTER");
		Spawn();
		timer.stop();
	}

	/**
	 * Pause the game
	 * and if called again, continue the game
	 */
	public void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {
			timer.stop();
			this.tetirs.updateStatus(nextpiece, lineAchived * 10, "paused");
			this.tetirs.scorepanel.repaint();
		} else {
			timer.start();
			this.tetirs.updateStatus(nextpiece, lineAchived * 10, "Play");
			this.tetirs.repaint();
		}
		repaint();
	}
	
	
	
	

	/**
	 * try Move the passed in shape to passed in location (current piece, new desired location due to
	 * either rotation or one line down)
	 * If we can do the move, repatin and update the currentx, currenty with the newx and newy
	 * Return a boolean indicates can it be moved to the new location or not
	 * The reason why we need to pass in the x,y and shape is because of how i implmented the roataion
	 */
	public boolean tryMove(ShapeModel Spawn, int tryx, int tryy) {
		
		for (int i = 0; i < TILE_MADE_SIZE; ++i) {
			int x = tryx + Spawn.getx(i);
			int y = tryy - Spawn.gety(i);
			//Reach Boundary
			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT)
				return false;
			//On top of another piece
			if (board[x][y] != Piece.NoShape)
				return false;
		}

		currentpiece = Spawn;
		currentx = tryx;
		currenty = tryy;
		repaint();
		return true;
	}
	
	/**
	 * Check how many lines we can get to score and eliminate by iterating through the board. 
	 * update the socrePanel accordingly
	 * If we achieved more than one line, we repaint and set CurrentPieceDone to true
	 */

	public void checkandremoveLineAchived() {
		int success = 0;

		for (int i = BOARD_HEIGHT - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < BOARD_WIDTH; ++j) {
				if(board[j][i] == Piece.NoShape) {
					lineIsFull = false;
					break;
				}
			}
			//update success
			//Also, remove the full line and move the whole thing down by one line.
			if (lineIsFull) {
				++success;
				for (int k = i; k < BOARD_HEIGHT - 1; ++k) {
					for (int j = 0; j < BOARD_WIDTH; ++j)
						board[j][k] = board[j][k + 1];
				}
			}
		}

		if (success > 0) {
			lineAchived += success;
			this.tetirs.updateStatus(nextpiece, lineAchived * 10, " ");
			this.tetirs.repaint();
			currentPieceDone = true;
			currentpiece.setShape(Piece.NoShape);
			repaint();
		}
	}	
	
	
/**
 * this method will try move the currentPiece one line down until tryMove returns a false 
 * then we update the board according to the newest currentx and currenty
 */
	public void nextIteration() {
		if (!tryMove(currentpiece, currentx, currenty - 1))
			updateBoard();
	}

	/**
	 * 
	//Update the board model according to the current location of the falling piece
	//We only invoke this method when the falling is finished, we are putting the piece into the model (already prestned to user)
	*/
	public void updateBoard() {
		for (int i = 0; i < TILE_MADE_SIZE; ++i) {
			int x = currentx + currentpiece.getx(i);
			int y = currenty - currentpiece.gety(i);
			board[x][y] = currentpiece.getShape();
		}

		checkandremoveLineAchived();

		currentPieceDone = true;
	}

	/**
	 * Clear the board
	 */
	public void clearBoard() {
		for (int i = 0; i < BOARD_WIDTH; ++i)
			for (int j = 0; j < BOARD_HEIGHT; ++j) {
				board[i][j] = Piece.NoShape;
			}

	}

	/**
	 * Restart the game
	 */
	public void restart() {
		currentpiece.setShape(Piece.NoShape);
		isStarted = false;
		isNewGame = true;
		morethanoneGame = true;
		this.tetirs.updateStatus(nextpiece, lineAchived * 10, "GAME OVER");
		this.tetirs.repaint();

		nextpiece.setRandomShape();
		this.tetirs.updateStatus(nextpiece, lineAchived * 10, "GAME OVER");

		clearBoard();

		isStarted = true;
		currentPieceDone = false;
		lineAchived = 0;

		timer.stop();
		Spawn();
	}
	
	/**
	 * WE TRY spawn a new piece,
	 * If cannot spawn a new piece. Game over	
	 */
	public void Spawn() {
		currentpiece.setShape(nextpiece.getShape());
		nextpiece.setRandomShape();
		System.out.println("from board current piece" + currentpiece.getShape());
		System.out.println("from board next piece" + nextpiece.getShape());
		if (!isNewGame) {
			this.tetirs.updateStatus(nextpiece, lineAchived * 10, "PLAY");
			this.tetirs.scorepanel.repaint();
		}

		currentx = BOARD_WIDTH / 2 + 1;
		//get the initial target moving location
		currenty = BOARD_HEIGHT - 1 + currentpiece.nexty();
		// If cannot spawn Good game well-played
		if (!tryMove(currentpiece, currentx, currenty)) {
			restart();
		}
	}

	/**
	 *  In this paint method, we paint each logical square according to the info in the "board"
	 *  If the game has not started(or game over), we display welcome message accordingly  
	 */
		@Override

		public void paint(Graphics g) {
			super.paint(g);
			if (isNewGame) {
				String msg = (morethanoneGame) ? "YOU LOOSE!" : "TETRIS";
				g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, NOTIFICATION_WINDOW_LOCATION);
				g.setFont(new Font("Tahoma", Font.BOLD, 12));
				msg = (morethanoneGame) ? "Press Enter to try again" : "Press Enter to Play";
				g.drawString(msg, CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, ENTER_WINDOW_LOCATION);
			} else {

				for (int i = 0; i < BOARD_HEIGHT; ++i) {
					for (int j = 0; j < BOARD_WIDTH; ++j) {
						Piece shape = board[j][BOARD_HEIGHT - i - 1];
						if (shape != Piece.NoShape)
							drawSquare(g, 0 + j * TILE_WIDTH, BOARDTOP + i * TILE_HEIGHT, shape);
					}
				}

				if (currentpiece.getShape() != Piece.NoShape) {
					for (int i = 0; i < TILE_MADE_SIZE; ++i) {
						int x = currentx + currentpiece.getx(i);
						int y = currenty - currentpiece.gety(i);
						drawSquare(g, 0 + x * TILE_WIDTH, BOARDTOP + (BOARD_HEIGHT - y - 1) * TILE_HEIGHT,
								currentpiece.getShape());
					}
				}
			}
		}
	/**
	 * This method will invoke the nextIteration method till the current piece hits another piece/reach the end
	 * It will make the current piece "drop"(invoked by press space key), do this if you think you are so good at this game
	 */
		public void IamSoGoodAtThisGame() {
			int tryy = currenty;
			while (tryy-- > 0) {
				
				nextIteration();
				
			}
			
		}
		

	@Override
	/**
	 * Draw one square according to the location passed in and according to the shape 
	 * set the color
	 * @param g- graphics
	 * @x- x coord
	 * @y- y coord
	 * @shape shape of the target square to determine the color
	 */
	public void drawSquare(Graphics g, int x, int y, Piece shape) {
		//Set the color according to the type
		Color colors[] = { col1, col2, col3, col4, col5 };

		Color color = colors[shape.ordinal()];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, TILE_WIDTH - 2, TILE_HEIGHT - 2);

	}

}