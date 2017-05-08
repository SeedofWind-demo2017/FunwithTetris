package tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import tetris.ShapeModel.Piece;

/**
 * TetrisAdpater extends KeyAdapter,I chose to use keyadapter instead of keyListner since i only want to implement keyPressed 
It has 
  * a board instance to access information about the game board.
This class is a keyAdapter(Convinence class for keyListner). KeyBinding is probablly a better but here keyAdapter serves fine. More detail about each key setting in the file
 * @author SeedZ
 *
 */
public class TetrisAdapter extends KeyAdapter {
	public BoardViewController board;

	public TetrisAdapter(BoardViewController myboard) {
		this.board = myboard;
	}

	/**
	 * Override the keyPressed method
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		if (!board.isStarted || board.currentpiece.getShape() == Piece.NoShape) {
			return;
		}

		int keycode = e.getKeyCode();

		if (keycode == 'p' || keycode == 'P') {
			board.pause();
			return;
		}

		if (board.isPaused)
			return;

		switch (keycode) {

		/*
		 * Basic left and right
		 */
		case 'A':
			board.tryMove(board.currentpiece, board.currentx - 1, board.currenty);
			break;
		case 'D':
			board.tryMove(board.currentpiece, board.currentx + 1, board.currenty);
			break;
		case 'a':
			board.tryMove(board.currentpiece, board.currentx - 1, board.currenty);
			break;
		case 'd':
			board.tryMove(board.currentpiece, board.currentx + 1, board.currenty);
			break;

		/**
		 * When press s/S, we call nextIteration to achive the accelerate of the moving down
		 */
		case 'S':
			board.nextIteration();
			break;
		case 's':
			board.nextIteration();
			break;
		
        /*
         * Do the rotation trick
         * By
         * 1. ask the shapeModel to rotate
         * 2. then tryMove the new coords of the rotated piece 
         */
		case 'w':
			board.tryMove(board.currentpiece.rotate(), board.currentx, board.currenty);
			break;

		case 'W':
			board.tryMove(board.currentpiece.rotate(), board.currentx, board.currenty);
			break;
		/*
		 * Press space the show you are good at this game
		 */
		case KeyEvent.VK_SPACE:

			if (!board.isNewGame) {
				board.IamSoGoodAtThisGame();
				break;
			} else {
				break;
			}
		/*
		 * Enter to start or restart
		 */
		case KeyEvent.VK_ENTER:
			if (board.isNewGame && !board.morethanoneGame) {
				board.isNewGame = false;
				board.timer.start();
			} else if (board.isNewGame && board.morethanoneGame) {

				board.isNewGame = false;
				board.timer.start();
				board.tetirs.updateStatus(board.nextpiece, 0, "Game Over");
				board.tetirs.repaint();

			} else {
				return;
			}

		}

	}
}