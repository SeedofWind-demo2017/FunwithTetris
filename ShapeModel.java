package lab10;

import java.util.Random;
import java.lang.Math;


/**
 * ShapeModel is the class that specifies the shape of a piece 
It has 
  * group of final static final constants that represent the coords of a certain shape and other related info(see file for detail)
  *  a enum (class) piece that has
     - element names
     - values that holds an array of enum elements
     - a ReferenceTable to look up the coordinate setting of a certain type
  * a pieceShape tahat tells the shapeType 
  * coords that holds the correct coordinates setting of the corresponding shape
ShapeModel abstracts the shape of each piece by referencing to a enum class and corresponding coord. This abstraction is very important for our game implementation when drawing the square according to the type and rorate the shape. 

Notice that in java, enum is a full class unlike C++, so we have to have an extra values field inside and call ordinal to interact with it correctly Ï
 * @author SeedZ
 *
 */
public class ShapeModel {
	/*
	 * Coordinate of different types
	 */
	private final static int[][] null_coords = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
	private final static int[][] lineshape_coords = { { 0, 2 }, { 0, 1 }, { 0, 0 }, { 0, -1 } };
	private final static int[][] tshape_coords = { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 0, 1 } };
	private final static int[][] squareshape_coords = { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
	private final static int[][] lshape_coords = { { 0, 0 }, { 0, -1 }, { -1, -1 }, { 0, 1 } };
	/**
	 * How many shapes available
	 */
	private final static int NUMOFSHAPES = 5;
	/*
	 * How many tiles make up a shape
	 */
	private final static int NUMOF_TILES_EACH_SHAPE = 4;
	/*
	 * 2D coord 
	 */
	private final static int COORD_SIZE = 2;

	/*
	 * a enum (class) piece that has element names values that holds an array of
	 * enum elements a ReferenceTable to look up the coordinate setting of a
	 * certain type
	 */
	enum Piece {
		NoShape, LineShape, TShape, SquareShape, LShape;
		public final static Piece values[] = values();
		private final static int[][][] REFERENCETABLE = new int[][][] { null_coords, lineshape_coords, tshape_coords,
				squareshape_coords, lshape_coords

		};

	};
	
	public Piece pieceShape;
	private int coords[][];

	
	public ShapeModel() {

		this.coords = new int[NUMOF_TILES_EACH_SHAPE][COORD_SIZE];
		this.setShape(Piece.NoShape);

	}
/**
 * 
 * @param shape -- desired shape
 * set the shape of this piece to desired shape
 * and update the coords it holds correctly via looking up to the reference table
 */
	public void setShape(Piece shape) {

		for (int i = 0; i < NUMOF_TILES_EACH_SHAPE; i++) {
			for (int j = 0; j < COORD_SIZE; ++j) {
				// System.out.println("haha" + shape.ordinal());
				// get ordinal for the reference table row
				coords[i][j] = Piece.REFERENCETABLE[shape.ordinal()][i][j];

			}
		}
		pieceShape = shape;

	}

	/**
	 * 
	 * @param index  will iterate from 0 to 3 since there are four tiles for each piece
	 * @param x the new value of x
	 * 	 
	 * set the x coordinate for each tile of certain piece 
	 */
	private void setx(int index, int x) {
		coords[index][0] = x;
	}

	/**
	 * 
	 * @param index  will iterate from 0 to 3 since there are four tiles for each piece
	 * @param y the new value of y coordinate for each tile
	 * 	 
	 * set the y coordinate for each tile of certain piece 
	 */
	private void sety(int index, int y) {
		coords[index][1] = y;
	}

	/**
	 * 
	 * @param inndex will iterate from 0 to 3 since there are four tiles for each piece
	 * @return the x coordinate for each tile of certain piece 
	 */
	public int getx(int index) {
		return coords[index][0];
	}

	/**
	 * 
	 * @param index will iterate from 0 to 3 since there are four tiles for each piece
	 * @return the y coordinate for each tile of certain piece 
	 */
	public int gety(int index) {
		return coords[index][1];
	}

	/**
	 * 
	 * @return the shape of the current piece 
	 */
	public Piece getShape() {
		return pieceShape;
	}

	/**
	 * Set current piece to a randomShape
	 */
	public void setRandomShape() {
		Random random = new Random();

		setShape(Piece.values[random.nextInt(NUMOFSHAPES - 1) + 1]);
	}

	/**
	 * 
	 * @return the lowest point of the shape, used when spanning the piece 
	 */
	public int nexty() {
		int m = coords[0][1];
		for (int i = 0; i < NUMOF_TILES_EACH_SHAPE; i++) {
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	/**
	 * Rotate method square does not need to be rotated
	 * 
	 * @return
	 */
	public ShapeModel rotate() {
		if (pieceShape == Piece.SquareShape)
			return this;

		ShapeModel result = new ShapeModel();
		result.pieceShape = pieceShape;

		for (int i = 0; i < NUMOF_TILES_EACH_SHAPE; ++i) {
			result.setx(i, gety(i));
			result.sety(i, -getx(i));
		}
		return result;
	}

}