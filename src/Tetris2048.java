import java.util.Random;
/**
 * MEF University - 2020 Spring Faculty of Engineering Computer Engineering
 * Programming Studio - COMP 204 Instructor: Muhittin G�kmen
 * 
 * Project 2 Tetris 2048 Game
 * 
 * @author Can Bi 041701001
 * @author Nadide Beyza Dokur 041801134
 * @since 17.03.2020
 */
public class Tetris2048 {
	public static void main(String[] args) throws CloneNotSupportedException {
		final Tetrimino[] types = { 											// Tetrimino class initialization for every type
				new Tetrimino(Tetrimino.ShapeI), new Tetrimino(Tetrimino.ShapeJ), new Tetrimino(Tetrimino.ShapeL),
				new Tetrimino(Tetrimino.ShapeO), new Tetrimino(Tetrimino.ShapeS), new Tetrimino(Tetrimino.ShapeT),
				new Tetrimino(Tetrimino.ShapeZ), };
		
		CanvasDrawer canvas = new CanvasDrawer();								// canvas object
		//INITIAL CANVAS VALUES
		canvas.rowSpace = 750;
		canvas.frameOffset = 5;
		canvas.rightPanelWidth = 120;
		canvas.numberOfColumns = 10;
		canvas.numberOfRows = 12;
		canvas.nextSquareLength = canvas.rightPanelWidth / 10;
		canvas.nextSquareOffset = canvas.rightPanelWidth / 10;
		canvas.nextSquareGap = 1;
		canvas.ratioOfNextHeight = .87;
		canvas.setUpCanvas();													// calculating canvas attributes
		
		//CANVAS DRAWING
		StdDraw.setCanvasSize(canvas.calculatedWidth, canvas.calculatedHeigth); 		// canvas size set
		StdDraw.setXscale(0, canvas.calculatedWidth); 									// canvas x scale set
		StdDraw.setYscale(canvas.calculatedHeigth, 0); 									// canvas y scale set
		StdDraw.enableDoubleBuffering(); 												// enables double buffering for drawing in wanted situations
		
		int tetrisScore = 0;
		boolean game = true;
		StdAudio.loop("tetris.wav");										// tetris music
		// MAIN LOOP
		while (true) {
			Random r = new Random(); 										// creating random class object
			boolean createANewTetromino = false; 							// control for creating a new Tetrimino
			int contMerge = 0; 												// control for merging operations' order
			int randomTetrimino = r.nextInt(7); 							// getting random index number
			int nextTetrimino = r.nextInt(7);								// getting random index number
			
			// GAME SET
			Tetrimino.totalScore = 0;
			tetrisScore = 0;
			canvas.gameNotStarted = true;
			
			// FIRST TETRIMINO
			Tetrimino t = types[randomTetrimino]; 							// getting Tetrimino with random index number
			int n = t.shape.length; 										// Tetrimino's shape length
			t.currentPos = new int[n][n][2]; 								// initializing Tetriminos currentPos array
			t.currentValues = new int[n][n]; 								// initializing Tetriminos currentValues array
			t.init(canvas); 												// initializing Tetriminos positions
			t.settle(canvas);												// putting first Tetrimino to the table

			// NEXT TETRIMINO
			Tetrimino t1 = types[nextTetrimino]; 							// getting Tetrimino with random index number
			int n1 = t1.shape.length; 										// Tetrimino's shape length
			t1.currentPos = new int[n1][n1][2]; 							// initializing Tetriminos currentPos array
			t1.currentValues = new int[n1][n1]; 							// initializing Tetriminos currentValues array
			t1.init(canvas); 												// putting first Tetrimino to the table
			
			// MENU SCREEN
			Boolean[] menuReturns = canvas.mainMenu();
			boolean menu = menuReturns[0];
			boolean tetrisClear = menuReturns[1];
			
			game = true; 													// game over is equal false in the beginning
			int timerValue = 600; 											// game latency value
			boolean falling = false; 										// fast dropping is equal false in the beginning
			boolean pause = false;											// pause control is equal false in the beginning
			
			// GAME LOOP
			while (game && !menu) {
				boolean tetris = false; 									// tetris(horizontal row erasing) is equal false in every step
				
				// PAUSE
				if (pause) {
					if (StdDraw.hasNextKeyTyped()) {
						char ch = StdDraw.nextKeyTyped();
						if (ch != 'p' || ch != 'P')
							pause = false;
					}
					continue;
				}
				
				// CREATING NEW TETRIMINO
				if (createANewTetromino) {
					t = (Tetrimino) t1.clone();								// next tetrimino is copied to current tetrimino

					nextTetrimino = r.nextInt(7); 							// getting random index number
					t1 = types[nextTetrimino]; 								// getting Tetrimino with random index number
					int n2 = t1.shape.length; 								// new Tetrimino's shape length
					t1.currentPos = new int[n2][n2][2]; 					// initializing Tetrimino's currentPos array
					t1.currentValues = new int[n2][n2]; 					// initializing Tetrimino's currentValues array
					t1.init(canvas);										// initializing Tetriminos positions

					// putting first Tetrimino to the table.
					// game will be equal false
					// if it is not possible to initialize new Tetrimino
					game = t.settle(canvas);

					// FAST DROPPING CONTROL
					if (falling) 											// controlling fast dropping
						timerValue = timerValue * 3; 						// resetting fast dropping speed
					falling = false; 										// falling is equal false again
				}

				// GAME OVER CONTROL
				if (!game) {
					int number = r.nextInt(2);
					canvas.endGameAnimation(number);
					break; 													// game will be over when the new Tetrimino wasn't initialized.
				}
				
				// KEYBOARD INTERACTIONs
				boolean success = false; 									// success control of the move
				if (StdDraw.hasNextKeyTyped()) { 							// keyboard interaction control
					char ch = StdDraw.nextKeyTyped(); 						// getting character which is typed by the user

					// MOVE LEFT
					if (ch == 'a' || ch == 'A') 							// moves the tetromino left by one
						success = t.goLeft(canvas); 						// returns true if the move is available

					// MOVE RIGHT
					else if (ch == 'd' || ch == 'D') 						// moves the tetromino right by one
						success = t.goRight(canvas); 						// returns true if the move is available

					// ROTATE 90 DEGREE
					else if (ch == 's' || ch =='S') 						// rotates the tetromino by 90 degree
						success = t.rotate(canvas); 						// returns true if the move is available

					// ROTATE 270 DEGREE
					else if (ch == 'w' || ch == 'W') { 						// rotates the tetromino by 270 degree
						success = t.rotate(canvas);
						success = t.rotate(canvas);
						success = t.rotate(canvas); 						// returns true if the move is available
					}

					// FAST DROP
					else if (ch == 'x' || ch == 'X' && !falling) { 			// fast drops the tetromino by 3 times faster
						if (!falling) 										// controlling fast dropping
							timerValue /= 3; 								// assigning fast dropping speed
						falling = true; 									// then falling is active
					}
					
					// PAUSE
					else if (ch == 'p' || ch == 'P') 						// rotate the active tetromino by 90 degree
						pause = true;

				}

				// MOVE DOWN
				if (!success) { 											// moves the tetromino down by one if hasn't done any successful move
					success = t.goDown(canvas, t1); 						// returns true if the move is available
					if (!success && contMerge == 0) { 						// if the move is not avaliable
						// NEW Tetrimino CONTROL
						createANewTetromino = success; 						// not creating a new tetromino on the game grid if the  current Tetrimino cannot go down anymore
						t.canMerge(canvas, t1);
						contMerge++;
					} else if (!success && contMerge == 1) {
						// NEW Tetrimino CONTROL
						createANewTetromino = success; 						// not creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore
						t.contMerge(canvas, t1);
						canvas.nextTetriminoDrawing(t1);
						canvas.scoreDrawing(t);
						contMerge--;
					} else {
						// NEW Tetrimino CONTROL
						createANewTetromino = !success; 					// creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore
					}
				}

				// NEW Tetrimino CONTROL
				createANewTetromino = !success; 							// creating a new tetromino on the game grid if the current Tetrimino
																			// cannot go down anymore

				// REDRAWING CANVAS
				canvas.updateCanvas();
				canvas.nextTetriminoDrawing(t1);
				canvas.scoreDrawing(t);
				StdDraw.show();
				StdDraw.pause(timerValue); 									// pauses after every move in the table

				// TETRIS CONTROL
				if (tetrisClear) {
					boolean[] isTetris = isThereTetris(canvas); 					// tetris control for every row in the table
					tetris = tetris(canvas, isTetris, contMerge, tetrisScore);		// erases the row and returns true if there is a
																					// tetris(full horizontal row in the table)
					if (tetris) { 													// if there was a tetris
						// REDRAWING CANVAS
						canvas.updateCanvas(); 										// redraws the updated table
						canvas.nextTetriminoDrawing(t1);
						canvas.scoreDrawing(t);
						StdDraw.show();
						StdDraw.pause(timerValue);
						timerValue -= 10; 											// game will be more challanging every time a tetris erased
						createANewTetromino = true; 								// new Tetrimino will be placed in next loop
					}
				}
			}
		}
	}

	/**
	 * 
	 * Control every row in the current table Determining whether is it full
	 * horizontal row or not Returns boolean array which contains every row's status
	 * of being tetris or not
	 * 
	 * @param canvas CanvasDrawer Object
	 * @return returns boolean array for determining whether is it full horizontal row or not in the current table
	 */
	public static boolean[] isThereTetris(CanvasDrawer canvas) {
		boolean[] isTetris = new boolean[canvas.numberOfRows]; 					// creating boolean array in size of number of rows in																											// the table
		
		for (int i = 0; i < canvas.currentTable[0].length; i++) {
			isTetris[i] = true; 												// initially assigning true every index of the boolean array
		}
		
		for (int j = 0; j < canvas.currentTable[0].length; j++) { 				// iterates in number of rows in the table
			for (int i = 0; i < canvas.currentTable.length; i++) { 				// iterates in number of columns in the table
				if (canvas.currentTable[i][j] == 0) { 							// if there is no Tetrimino block in that coordinate
					isTetris[j] = false; 										// assigns false for that row in the table
				}
			}
		}
		return isTetris;														// returns boolean array
	}

	/**
	 * 
	 * Erases row if the row is tetris Moves every row above the tetris down one line. Returns true if there was a tetris
	 * 
	 * @param canvas CanvasDrawer Object
	 * @param isTetris boolean array which contains every row's status of being tetris or not
	 * @param contMerge
	 * @param tetrisScore tetris score
	 * @return returns true if there is a tetris, false when there is no tetris.
	 */
	public static boolean tetris(CanvasDrawer canvas, boolean[] isTetris, int contMerge, int tetrisScore) {
		boolean tetris = false; 						// initially assumes there is no tetris in the current table

		for (int i = 0; i < isTetris.length; i++) {
			if (isTetris[i] == false)  																				// if the current row is not a tetris
				continue; 																							// passes the current row
			if (contMerge == 1) {
				for (int j = i; j > 0; j--) { 																		// iterates in number of rows in the table
					for (int j2 = 0; j2 < canvas.currentTable.length; j2++) { 										// iterates in number of columns in the table  
						if(canvas.currentTable[j2][j] == 1) {
							if(canvas.currentTable[j2][j] == canvas.currentTable[canvas.currentTable.length-1][j])
							tetrisScore += canvas.currentTableValues[j2][j];
						}
						canvas.currentTable[j2][j] = canvas.currentTable[j2][j - 1]; 								// moves every row above the tetris down one line.
						canvas.currentTableValues[j2][j] = canvas.currentTableValues[j2][j - 1];
					}
				}
				tetris = true; 												// assigns true
			}
			Tetrimino.totalScore += tetrisScore;
		}
		for (int i = 0; i < canvas.currentTable.length; i++) 				// iterates in number of columns in the table
			canvas.currentTable[i][0] = 0; 									// adds new row in the current table
		return tetris; 														// returns true if there is a tetris, false when there is no tetris.
	}
}
