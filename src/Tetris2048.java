import java.awt.Font;
import java.util.Random;

public class Tetris2048{ 
	public static void main(String[] args) {
		int numberOfColumns = 10;
		int numberOfRows = 20;
		
		//calculation of right panel
		int rowSpace = 750;
		int squareLength = (int) Math.round((rowSpace*15)/((double)((31*numberOfRows)-1)));
		int gapOfSquares = Math.round(squareLength/15);
		int calculatedRowSpace = 2*squareLength*numberOfRows + (numberOfRows-1)*gapOfSquares;
		int calculatedColumnSpace = 2*squareLength*numberOfColumns + (numberOfColumns-1)*gapOfSquares;
		
		int rightPanelWidth = 120;
		int frameOffset = 5;
		int calculatedWidth = 3*frameOffset + rightPanelWidth + calculatedColumnSpace; 
		int calculatedHeigth = 2*frameOffset + calculatedRowSpace;
		
		
		int[][] currentTable = new int[numberOfColumns][numberOfRows]; 			//for storing whether there is a Tetrimino in a coordinate.
		int[][] currentTableValues = new int[numberOfColumns][numberOfRows];	//for storing value of coordinates
		final Tetrimino[] types= {						//Tetrimino class initialization for every type
	         new Tetrimino(Tetrimino.ShapeI),
	         new Tetrimino(Tetrimino.ShapeJ),
	         new Tetrimino(Tetrimino.ShapeL),
	         new Tetrimino(Tetrimino.ShapeO),
	         new Tetrimino(Tetrimino.ShapeS),
	         new Tetrimino(Tetrimino.ShapeT),
	         new Tetrimino(Tetrimino.ShapeZ),};
		
		
		//DRAWING CANVAS
		int[] canvasAttributes = {calculatedWidth,calculatedHeigth,squareLength,
				gapOfSquares,calculatedColumnSpace,calculatedRowSpace,frameOffset,rightPanelWidth};	//stores canvas's width and height		
		int[][][] squaresCoords = new int[numberOfColumns][numberOfRows][2];		//for storing center coordinates of every squares
		StdDraw.setCanvasSize(calculatedWidth,calculatedHeigth);				//canvas size set
		StdDraw.setXscale(0, calculatedWidth);						//canvas x scale set
		StdDraw.setYscale(calculatedHeigth, 0);						//canvas y scale set
		StdDraw.enableDoubleBuffering();					//enables double buffering for drawing in wanted situations
		
		
		//INITIALIZING FIRST Tetrimino
		Random r = new Random();							//creating random class object 
		boolean createANewTetromino = false;				//control for creating a new Tetrimino	
		boolean check = false;
		int randomTetrimino = r.nextInt(7);					//getting random index number
		int nextTetrimino = r.nextInt(7);
		Tetrimino t = types[randomTetrimino];				//getting Tetrimino with random index number
		int n = t.shape.length;								//Tetrimino's shape length
		t.currentPos = new int[n][n][2];					//initializing Tetrimino's currentPos array
		t.currentValues = new int[n][n];					//initializing Tetrimino's currentValues array
		t.init(currentTable,currentTableValues);			//putting first Tetrimino to the table
		drawCanvas(squaresCoords,canvasAttributes,nextTetrimino,types);			//draws canvas
		
		//StdAudio.loop("tetris.wav");
		//GAME LOOP	
		boolean game = true;								//game over is equal false in the beginning
		int timerValue = 500;								//game latency value
		boolean falling = false;							//fast dropping is equal false in the beginning
		boolean pause = false;
		while(game) {										//game loop
			boolean tetris = false;							//tetris(horizontal row erasing) is equal false in every step
			//CREATING NEW Tetrimino
			if(createANewTetromino) {								
				t = types[nextTetrimino];						//getting Tetrimino with random index number
				nextTetrimino = r.nextInt(7);					//getting random index number
				int n1 = t.shape.length;						//new Tetrimino's shape length
				t.currentPos = new int[n1][n1][2];				//initializing Tetrimino's currentPos array
				t.currentValues = new int[n1][n1];				//initializing Tetrimino's currentValues array
				
				//putting first Tetrimino to the table.
				//game will be equal false 
				//if it is not possible to initialize new Tetrimino
				game = t.init(currentTable,currentTableValues);	
				
				//FAST DROPPING CONTROL
				if(falling)										//controlling fast dropping
					timerValue = timerValue *3 ;				//resetting fast dropping speed
				falling = false;								//falling is equal false again
			}
			
			//GAME OVER CONTROL
			if(!game)				//game over control. 
				break;				//game will be over when the new Tetrimino wasn't initialized.
			
			//KEYBOARD INTERACTION 
			boolean success = false;										//success control of the move
			if (StdDraw.hasNextKeyTyped()) { 								//keyboard interaction control
                char ch = StdDraw.nextKeyTyped();  							//getting character which is typed by the user       
                
                //MOVE LEFT
                if (ch == 'a') 												//moves the tetromino left by one
                    success = t.goLeft(currentTable,currentTableValues);	//returns true if the move is available

                //MOVE RIGHT
                else if (ch == 'd') 										//moves the tetromino right by one
                	success = t.goRight(currentTable,currentTableValues);	//returns true if the move is available
               
                //ROTATE 90 DEGREE
                else if (ch == 's')											//rotates the tetromino by 90 degree
                	success = t.rotate(currentTable,currentTableValues);	//returns true if the move is available
                	
                //ROTATE 270 DEGREE
                else if (ch == 'w') { 										//rotates the tetromino by 270 degree
                	success = t.rotate(currentTable,currentTableValues);	
                	success = t.rotate(currentTable,currentTableValues);	
                	success = t.rotate(currentTable,currentTableValues);	//returns true if the move is available
                }
                
                //FAST DROP
                else if (ch == 'x' && !falling) { 				//fast drops the tetromino by 3 times faster
                	if(!falling)								//controlling fast dropping								
                		timerValue /=3;							//assigning fast dropping speed
                	falling = true;								//then falling is active
                }
                
                else if (ch == 'p') // rotate the active tetromino by 90 degree
                    pause = true;
                
			}
			
			//PAUSE
    		if (pause) {
				if (StdDraw.hasNextKeyTyped()) {
					char ch = StdDraw.nextKeyTyped(); 
					if(ch != 'p')
						pause = false;
				}
				continue;
			}
			
			//MOVE DOWN
			if (!success) {												//moves the tetromino down by one if hasn't done any successful move 
				success = t.goDown(currentTable,currentTableValues);	//returns true if the move is available	
				if(!success){
					check = true;
					if(check) {
						//NEW Tetrimino CONTROL
						createANewTetromino = success;		 				// not creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore						
						t.canMerge(currentTableValues, currentTableValues);
						
					}
				}else {
					//NEW Tetrimino CONTROL
					createANewTetromino = !success;		 				//creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore	
				}
			}
				
			//NEW Tetrimino CONTROL
			createANewTetromino = !success;		 				//creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore	
			
			
			//REDRAWING CANVAS
			updateCanvas(squaresCoords, currentTable,currentTableValues, canvasAttributes,nextTetrimino,types); //redraws the updated table
			StdDraw.pause(timerValue);														//pauses after every move in the table
			
			//TETRIS CONTROL
			boolean[] isTetris = isThereTetris(currentTable,numberOfRows);	//tetris control for every row in the table
			tetris = tetris(currentTable, isTetris);//erases the row and returns true if there is a tetris(full horizontal row in the table)				
			if(tetris) {							//if there was a tetris
				updateCanvas(squaresCoords, currentTable,currentTableValues, canvasAttributes,nextTetrimino,types);	//redraws the updated table
				StdDraw.pause(timerValue);					//pauses after erasing the tetris
				timerValue -= 10;							//game will be more challanging every time a tetris erased											
				createANewTetromino = true;					//new Tetrimino will be placed in next loop
			}
		}
		
		//THE GAME WAS OVER
		System.out.println("THE END");
	}	
	

	/**
	 * Draws canvas
	 * 
	 * @param squaresCoords	center coordinates of every squares
	 * @param canvasAttributes canvas width and height values
	 */
	public static void drawCanvas(int[][][] squaresCoords, int[] canvasAttributes,int nextTetrimino,Tetrimino[] types) {
		int width = canvasAttributes[0];						//getting canvas' width
		int height = canvasAttributes[1];						//getting canvas' height
		
		int squareLength = canvasAttributes[2];
		int gapOfSquares = canvasAttributes[3];
		int calculatedColumnSpace = canvasAttributes[4];
		int calculatedRowSpace = canvasAttributes[5];
		int frameOffset = canvasAttributes[6];
		int rightPanelWidth = canvasAttributes[7];
		int numberOfColumns = squaresCoords.length;
		int numberOfRows = squaresCoords[0].length;
		
		Tetrimino t = types[nextTetrimino];
		
		//BACKGROUND
		StdDraw.setPenColor(132,122,113);
		StdDraw.filledRectangle(width/2, height/2, width/2, height/2);
		
		//LEFT AND RIGHT BACKGROUND
		StdDraw.setPenColor(192,179,165); //med brown color
		StdDraw.filledRectangle((calculatedColumnSpace/2.0)+frameOffset, (calculatedRowSpace/2.0)+frameOffset, (calculatedColumnSpace/2.0), (calculatedRowSpace/2.0));
		StdDraw.setPenColor(167,160,151);
		StdDraw.filledRectangle(width-frameOffset-(rightPanelWidth/2.0), (calculatedRowSpace/2)+frameOffset,rightPanelWidth/2.0,(calculatedRowSpace/2));
		
		//SQUARES
		for (int i = 0; i < numberOfColumns; i++) {
			for (int j = 0; j < numberOfRows; j++) {
				int xCoord = ((2*squareLength+gapOfSquares)*i)  + squareLength + frameOffset;
				int yCoord = ((2*squareLength+gapOfSquares)*j)  + squareLength + frameOffset;
				squaresCoords[i][j][0] = xCoord;
				squaresCoords[i][j][1] = yCoord;
				StdDraw.setPenColor(206,195,181);
				StdDraw.filledSquare(xCoord, yCoord, squareLength);
			}
		}
		
		int nextSquareLength = 12;
		double nextSquareOffset = 12;
		int nextSquareGap = 1;
		double ratioOfNextHeight =.87;
		
		if(t.shape.length == 3) {
			for (int i = 0; i < t.shape.length; i++) {
				for (int j = 0; j < t.shape.length; j++) {
					if(t.shape[i][j] == 1) {
						double xCoord = ((2*nextSquareLength+nextSquareGap)*j) + 2*frameOffset+calculatedColumnSpace+2*nextSquareOffset+nextSquareLength;
						double yCoord = ((2*nextSquareLength+nextSquareGap)*i) + frameOffset + ratioOfNextHeight*calculatedRowSpace;
						StdDraw.setPenColor(206,195,181);
						StdDraw.filledSquare(xCoord, yCoord, nextSquareLength);
						StdDraw.setPenColor(132,122,113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord,yCoord, nextSquareLength+nextSquareGap/2.0 );
					}
				}
			}
		}
		else {
			for (int i = 0; i < t.shape.length; i++) {
				for (int j = 0; j < t.shape.length; j++) {
					if(t.shape[i][j] == 1) {
						double xCoord = ((2*nextSquareLength+nextSquareGap)*j) + 2*frameOffset+calculatedColumnSpace+nextSquareOffset+nextSquareLength;
						double yCoord = ((2*nextSquareLength+nextSquareGap)*i) + frameOffset + ratioOfNextHeight*calculatedRowSpace;
						StdDraw.setPenColor(206,195,181);
						StdDraw.filledSquare(xCoord, yCoord, nextSquareLength);
						StdDraw.setPenColor(132,122,113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord,yCoord, nextSquareLength+nextSquareGap/2.0 );
					}
				}
			}
			
		}
		
		
		
		StdDraw.show();	//draws to the screen
	}
	
	
	/**
	 * Redraws the canvas
	 * 
	 * @param squaresCoords stores center coordinates of every squares
	 * @param currentTable stores tables last status in types of 1 or 0. 
	 * @param currentTableValue tables last status of value in types of 2048 game values.
	 * @param canvasAttributes canvas width and height values
	 */
	public static void updateCanvas(int[][][] squaresCoords, int[][] currentTable,int[][] currentTableValues,int[] canvasAttributes, int nextTetrimino,Tetrimino[] types) {
		int squareLength = canvasAttributes[2];
		int gapOfSquares = canvasAttributes[3];
		
		//DRAWING CANVAS
		drawCanvas(squaresCoords,canvasAttributes,nextTetrimino,types);
		
		//DRAWING UPDATED TABLE
		for (int i = 0; i < squaresCoords.length; i++) {
			for (int j = 0; j < squaresCoords[i].length; j++) {
				int xCoord = squaresCoords[i][j][0];								//getting center x coordinates of every squares
				int yCoord = squaresCoords[i][j][1];								//getting center y coordinates of every squares
				
				if(currentTable[i][j] == 0) {										//if there is no tetrimino in current table
					StdDraw.setPenColor(206,195,181);
					StdDraw.filledSquare(xCoord, yCoord, squareLength);
				}
				else {										//if there is a tetrimino in current table
					if(currentTableValues[i][j] == 2) StdDraw.setPenColor(238, 229, 219);		//if the current square has a value of 2
					else if(currentTableValues[i][j] == 4) StdDraw.setPenColor(235, 224, 204);	//if the current square has a value of 4
					else if(currentTableValues[i][j] == 8)StdDraw.setPenColor(255, 0, 0);
					
					//DRAWING SQUARE
					StdDraw.filledSquare(xCoord, yCoord, squareLength);
					
					//DRAWING SQUARE VALUE
					StdDraw.setPenColor(0,0,0);
					StdDraw.setFont(new Font("calibri", Font.BOLD, squareLength));
					StdDraw.text(xCoord, yCoord, Integer.toString(currentTableValues[i][j]));
					
					//DRAWING SQUARE FRAME
					StdDraw.setPenColor(132,122,113);
					StdDraw.setPenRadius(0.004);
					StdDraw.square(xCoord,yCoord, squareLength+gapOfSquares/2);
				}
			}
		}
		StdDraw.show(); //draws the changes to the screen
	}
	
	/**
	 * Control every row in the current table
	 * Determining whether is it full horizontal row or not
	 * Returns boolean array which contains every row's status of being tetris or not
	 * 
	 * @param currentTable stores tables last status in types of 1 or 0. 
	 * @return returns boolean array for determining whether is it full horizontal row or not in the current table
	 */
	public static boolean[] isThereTetris(int[][] currentTable, int numberOfRows) {
		boolean[] isTetris = new boolean[numberOfRows];	//creating boolean array in size of number of rows in the table
		
		for (int i = 0; i < currentTable[0].length; i++)  
			isTetris[i] = true;					//initially assigning true every index of the boolean array
		
		for (int j = 0; j < currentTable[0].length; j++) { 			//iterates in number of rows in the table
			for (int i = 0; i < currentTable.length; i++) { 		//iterates in number of columns in the table
				if(currentTable[i][j] == 0) {						//if there is no tetrimino block in that coordinate
					isTetris[j] = false;							//assigns false for that row in the table
				}
			}
		}
		return isTetris;											//returns boolean array
	}

	/**
	 * Erases row if the row is tetris
	 * Moves every row above the tetris down one line.
	 * Returns true if there was a tetris
	 * 
	 * @param currentTable stores tables last status in types of 1 or 0. 
	 * @param isTetris boolean array which contains every row's status of being tetris or not
	 * @return returns true if there is a tetris, false when there is no tetris.
	 */
	public static boolean tetris(int[][] currentTable,boolean[] isTetris) {
		boolean tetris = false;				//initially assumes there is no tetris in the current table
		
		for (int i = 0; i < isTetris.length; i++) {
			if(isTetris[i] == false)										//if the current row is not a tetris
				continue;													//passes the current row
			
			for (int j = i; j >= 1 ; j--) {									//iterates in number of rows in the table
				for (int j2 = 0; j2 < currentTable.length; j2++) { 			//iterates in number of columns in the table
					currentTable[j2][j] = currentTable[j2][j-1];			//moves every row above the tetris down one line.
				}
			}
			tetris = true;													//assigns true
		}
		
		for (int i = 0; i < currentTable.length; i++)						//iterates in number of columns in the table
			currentTable[i][0] = 0;											//adds new row in the current table
		
		return tetris;			//returns true if there is a tetris, false when there is no tetris.
	}
}
