package lab10;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import lab10.ShapeModel.Piece;

/**
 * Anim interface extends actionLinstner interface, where requriring the implementation of actionPerformed method.

In addtion, Anim requirs the implementation of drawSqarueMethod, where we draw the minimum logical block (square) on the screen,
based on the parameters. 
Anim also requires the implementation of tryMove method that returns a boolean indicates whether the current piece 
can be progressed to the new location.
 * @author SeedZ
 *
 */
public interface Anim extends ActionListener {
	/**
	 * Draw the minimum logical block (square) on the screen, based on 
	 */
	public void drawSquare(Graphics g, int x, int y, Piece shape);
	
	/**
	 * Tell me whether the piece can be moved to the new location
	 * @return whether this  piece can be moved the new location
	 */
	public boolean tryMove(ShapeModel tryPiece, int tryX, int tryY);
}
