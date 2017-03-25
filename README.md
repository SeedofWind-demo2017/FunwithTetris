# Tetris Game
    @Seed Zeng
    Basic Tetris game  where you will be able to have fun with tetris
    functionality done:
    1.  Descending 
    2.  Rotation
    3.  Speed up 
    4.  Drop down
    5.  Scoring
    
 --------------------------------
## Implementation
  
Use Java Swing to achive the animation
 * Jframe for baisc layout
 * JPanel + keyadpater for GUI
 * Swing Timer + action listner to progress the animation
 * MVC model as basic design concept. 
 
	*Notice that mvc for the board is not completely seperated out due to the limitied time for developement* 

===================  

## List of classes/Interfaces

Below is a list of classes. 
For each class, a breif descritpion about the classe/interface and a short discussion the importance of it to the game design will be presented.
        Click on the name of the class to explore code detail and inline comments about individual methods in the class/interface

### [Anim(interface)](./Anim.java)
        Click the name to see comments about individual method

Anim interface extends actionLinstner interface, where requriring the implementation of actionPerformed method.

In addtion, Anim requirs the implementation of drawSqarueMethod, where we draw the minimum logical block (square) on the screen, based on the parameters. 

Anim also requires the implementation of tryMove method that returns a boolean indicates whether the current piece can be progressed to the new location.

In my Design, [BoardViewController](./BoardViewController.java)(discussed Later) will be implementing this interface. It is imporatnt that it extends the actionListener, which allows the BoardViewController listen to the Timer and progress the game (according to the cycle, 0.35s in this design)

-----------------------


### [ShapeModel](./ShapeModel.java)
        Click the name to see comments about individual method

ShapeModel is the class that specifies the shape of a piece 
It has 
  * group of final static final constants that represent the coords of a certain shape and other related info(see file for detail)
  *  a enum (class) piece that has
     * element names
     * values that holds an array of enum elements
     * a ReferenceTable to look up the coordinate setting of a certain type
  * a pieceShape tahat tells the shapeType 
  * coords that holds the correct coordinates setting of the corresponding shape
ShapeModel abstracts the shape of each piece by referencing to a enum class and corresponding coord. This abstraction is very important for our game implementation when drawing the square according to the type and rorate the shape. 

Notice that in java, enum is a full class unlike C++, so we have to have an extra values field inside and call ordinal to interact with it correctly 

-------------------

### [ScroePanelViewController](./ScorePanelViewController.java)
        Click the name to see comments about individual method

ScorePanel extends Jpanel and is responsible for displaying the status of the game including
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

---------------------

### [BoardViewController](./BoardViewController.java)
        Click the name to see comments about individual method

BoardViewController extends Jpanel holds all the logic regarding the game itself. 
It has 
  * Group of Static final constants that descrbing the board layout and piece color, and the timer delay (see detail in the file)
  * a (swing)*timer* instance that we use to time our game, actionPerformed will be invoked every 300ms
  * boolean variables
    * isNewGame determines whether it is a new game
    * currentPieceDone determines whether the current piece finished falling (reach bottom, hit another piece)
    * isStarted determines whether the game has been started 
    * isPaused determines whether the game has been paused
    * morethanoneGame determines whether the game is played more than one time (to display different welcome msg)

  * LinesAchived keeps track of how many lines have been successfully removed (score = 10*this number)
  * currentx/y gives us the location of the  current falling piece 
  * nextpiece gives the shape of the next piece falling
  * currenpiece gives the shape of the current piece falling
  * board is a 2d array that keeps track of the shape of each logical square 
  * Tetris is our GameController where we used to communicate with scorePanel

We devide the board to logical squares, we have Board_height*Board_width number of squares. Each Tile is made up with four suqares. *WE MOVE BY SQUARE, NOT PIEXEL*

Essentially, each iteration of animation, we will draw all these squares according to our board model (paint method)

BoardViewController implements Anim, which extends the actionListener interface. BoardViewcontroller will listen to the timer, every clock cycle, actionPerforemd of this classwill be invoked 

        
        if (currentPieceDone) {
			currentPieceDone = false;
			Spawn();
		} else {
			nextIteration();
		}
	
        
If the falling of currentpiece is done we go to nextIteraton, if not we spawn a new piece. This is how the game will progress without the key interruption. 
BoardViewController extends Jpanel. we implement addKeyListener class to listen to keyInput. KeyInput is handled by TetrisApdater(discuss later).

I chose Adapter over listener since i only want to inplement keyPressed.

We update the status when necssary by invoking the update method of TetrisController.

------------


### [TetrisAdapter](./TetrisAdapter.java)
        Click the name to see comments about individual method


TetrisAdpater extends KeyAdapter,I chose to use keyadapter instead of keyListner since i only want to implement keyPressed 
It has 
  * a board instance to access information about the game board.
This class is a keyAdapter(Convinence class for keyListner). KeyBinding is probablly a better but here keyAdapter serves fine. More detail about each key setting in the file

---------------------

### [TetrisController](./TetrisController.java)
        Click the name to see comments about individual method

 
This is our game controller and where the main method lies. Here we setup the Jframe and add two configured panels. Ideally, it should control the progress of the game. However, since the model and view is not seperated out from the controller, it only serves as a driver in my Design
It has 
  *  board instance (jpanel)
  *  scorePanel instance(jpanel)
 
We set board at center and scorepanel at west. The update method is utilzied by board to update the status to scorePanel. Scorepanel should never talk to the board.

We fire up the TetrisController in the main method and setRelative to null since it's the only frame and then make it visiable


