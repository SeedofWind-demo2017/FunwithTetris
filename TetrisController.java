package tetris;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * This is our game controller and where the main method lies. Here we setup the Jframe and add two configured panels. Ideally, it should control the progress of the game. However, since the model and view is not seperated out from the controller, it only serves as a driver in my Design
It has
  *  board instance (jpanel)
  *  scorePanel instance(jpanel)

We set board at center and scorepanel at west. The update method is utilzied by board to update the status to scorePanel. Scorepanel should never talk to the board.

We fire up the TetrisController in the main method and setRelative to null since it's the only frame and then make it visiable

 * @author SeedZ
 *
 */
public class TetrisController extends JFrame {


	private static final long serialVersionUID = 1L;
	public Timer timer;
	public ScorePanelViewController scorepanel;
	public BoardViewController boardpanel;

	public TetrisController() {
		setLayout(new BorderLayout());
		setResizable(false);
		//Construct two panels
		this.scorepanel = new ScorePanelViewController(this);
		this.boardpanel = new BoardViewController(this);
		//Set fixed size
		setSize(BoardViewController.PANEL_WIDTH + 200, BoardViewController.PANEL_HEIGHT);
		//Add in two panels
		add(boardpanel, BorderLayout.CENTER);
		add(scorepanel, BorderLayout.EAST);
		boardpanel.start();

		setTitle("Fun with Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void updateStatus(ShapeModel shape, int socre, String status) {
		scorepanel.update(shape, socre, status);

	}

	public static void main(String[] args) {
		TetrisController game = new TetrisController();
		game.setLocationRelativeTo(null);
		game.setVisible(true);

	}


}
