import java.util.Random;

public class Tetris2048{ 
	public static void main(String[] args) throws CloneNotSupportedException {
		CanvasDrawer canvas = new CanvasDrawer();
		canvas.rowSpace = 750;
		canvas.frameOffset =5;
		canvas.rightPanelWidth = 120;
		canvas.numberOfColumns = 8;
		canvas.numberOfRows=12;
		canvas.nextSquareLength = canvas.rightPanelWidth/10;
		canvas.nextSquareOffset = canvas.rightPanelWidth/10;
		canvas.nextSquareGap = 1;
		canvas.ratioOfNextHeight =.87;
		canvas.setUpCanvas();
		StdDraw.setCanvasSize(canvas.calculatedWidth,canvas.calculatedHeigth);	//canvas size set
		StdDraw.setXscale(0, canvas.calculatedWidth);							//canvas x scale set
		StdDraw.setYscale(canvas.calculatedHeigth, 0);							//canvas y scale set
		StdDraw.enableDoubleBuffering();										//enables double buffering for drawing in wanted situations
		
		
		final Tetrimino[] types= {						//Tetrimino class initialization for every type
		         new Tetrimino(Tetrimino.ShapeI),
		         new Tetrimino(Tetrimino.ShapeJ),
		         new Tetrimino(Tetrimino.ShapeL),
		         new Tetrimino(Tetrimino.ShapeO),
		         new Tetrimino(Tetrimino.ShapeS),
		         new Tetrimino(Tetrimino.ShapeT),
		         new Tetrimino(Tetrimino.ShapeZ),};
		
		
		//INITIALIZING FIRST Tetrimino
		Random r = new Random();							//creating random class object 
		boolean createANewTetromino = false;				//control for creating a new Tetrimino	
		boolean check = false;
		int contMerge = 0;
		int randomTetrimino = r.nextInt(7);					//getting random index number
		int nextTetrimino = r.nextInt(7);
		
		//First t
		Tetrimino t = types[randomTetrimino];				//getting Tetrimino with random index number
		int n = t.shape.length;								//Tetrimino's shape length
		t.currentPos = new int[n][n][2];					//initializing Tetrimino's currentPos array
		t.currentValues = new int[n][n];					//initializing Tetrimino's currentValues array
		t.init(canvas);										//putting first Tetrimino to the table
		t.settle(canvas);
		
		//Second t
		Tetrimino t1 = types[nextTetrimino];				//getting Tetrimino with random index number
		int n1 = t1.shape.length;							//Tetrimino's shape length
		t1.currentPos = new int[n1][n1][2];					//initializing Tetrimino's currentPos array
		t1.currentValues = new int[n1][n1];					//initializing Tetrimino's currentValues array
		t1.init(canvas);									//putting first Tetrimino to the table
		canvas.drawCanvas(t1);								//draws canvas
		
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
				t = (Tetrimino) t1.clone();
				
				nextTetrimino = r.nextInt(7);					//getting random index number
				t1 = types[nextTetrimino];						//getting Tetrimino with random index number
				int n2 = t1.shape.length;						//new Tetrimino's shape length
				t1.currentPos = new int[n2][n2][2];				//initializing Tetrimino's currentPos array
				t1.currentValues = new int[n2][n2];				//initializing Tetrimino's currentValues array
				t1.init(canvas);
				
				//putting first Tetrimino to the table.
				//game will be equal false 
				//if it is not possible to initialize new Tetrimino
				game = t.settle(canvas);
				
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
                    success = t.goLeft(canvas);	//returns true if the move is available

                //MOVE RIGHT
                else if (ch == 'd') 										//moves the tetromino right by one
                	success = t.goRight(canvas);	//returns true if the move is available
               
                //ROTATE 90 DEGREE
                else if (ch == 's')											//rotates the tetromino by 90 degree
                	success = t.rotate(canvas);	//returns true if the move is available
                	
                //ROTATE 270 DEGREE
                else if (ch == 'w') { 										//rotates the tetromino by 270 degree
                	success = t.rotate(canvas);	
                	success = t.rotate(canvas);	
                	success = t.rotate(canvas);	//returns true if the move is available
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
				success = t.goDown(canvas);	//returns true if the move is available	
				if(!success && contMerge == 0){
					check = true;
					if(check) {
						//NEW Tetrimino CONTROL
						createANewTetromino = success;		 				// not creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore						
						t.canMerge(canvas);
						contMerge++;
					}
				}else if(!success && contMerge == 1) {
					check = true;
					if(check) {
						//NEW Tetrimino CONTROL
						createANewTetromino = success;		 				// not creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore						
						t.contMerge(canvas);
						contMerge--;
					}
				}
				else {
					//NEW Tetrimino CONTROL
					createANewTetromino = !success;		 				//creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore	
				}
			}
				
			//NEW Tetrimino CONTROL
			createANewTetromino = !success;		 				//creating a new tetromino on the game grid if the current Tetrimino cannot go down anymore	
			
			
			//REDRAWING CANVAS
			canvas.updateCanvas(t1); //redraws the updated table
			StdDraw.pause(timerValue);														//pauses after every move in the table
			
			//TETRIS CONTROL
			boolean[] isTetris = isThereTetris(canvas);	//tetris control for every row in the table
			tetris = tetris(canvas, isTetris);//erases the row and returns true if there is a tetris(full horizontal row in the table)				
			if(tetris) {							//if there was a tetris
				canvas.updateCanvas(t1);	//redraws the updated table
				StdDraw.pause(timerValue);					//pauses after erasing the tetris
				timerValue -= 10;							//game will be more challanging every time a tetris erased											
				createANewTetromino = true;					//new Tetrimino will be placed in next loop
			}
		}
		
		//THE GAME WAS OVER
		System.out.println("THE END");
	
	}
	
	/**
	 * Control every row in the current table
	 * Determining whether is it full horizontal row or not
	 * Returns boolean array which contains every row's status of being tetris or not
	 * 
	 * @param currentTable stores tables last status in types of 1 or 0. 
	 * @return returns boolean array for determining whether is it full horizontal row or not in the current table
	 */
	public static boolean[] isThereTetris(CanvasDrawer canvas) {
		boolean[] isTetris = new boolean[canvas.numberOfRows];				//creating boolean array in size of number of rows in the table
		
		for (int i = 0; i < canvas.currentTable[0].length; i++) {
			isTetris[i] = true;												//initially assigning true every index of the boolean array
		}
		for (int j = 0; j < canvas.currentTable[0].length; j++) { 			//iterates in number of rows in the table
			for (int i = 0; i < canvas.currentTable.length; i++) { 			//iterates in number of columns in the table
				if(canvas.currentTable[i][j] == 0) {						//if there is no Tetrimino block in that coordinate
					isTetris[j] = false;									//assigns false for that row in the table
				}
			}
		}
		return isTetris;													//returns boolean array
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
	public static boolean tetris(CanvasDrawer canvas,boolean[] isTetris) {
		boolean tetris = false;				//initially assumes there is no tetris in the current table
		
		for (int i = 0; i < isTetris.length; i++) {
			if(isTetris[i] == false)													//if the current row is not a tetris
				continue;																//passes the current row
			
			for (int j = i; j > 0 ; j--) {												//iterates in number of rows in the table
				for (int j2 = 0; j2 < canvas.currentTable.length; j2++) { 				//iterates in number of columns in the table
					canvas.currentTable[j2][j] = canvas.currentTable[j2][j-1];			//moves every row above the tetris down one line.
					canvas.currentTableValues[j2][j] = canvas.currentTableValues[j2][j-1];
				}
			}
			tetris = true;																//assigns true
		}
		
		for (int i = 0; i < canvas.currentTable.length; i++)							//iterates in number of columns in the table
			canvas.currentTable[i][0] = 0;												//adds new row in the current table
		return tetris;															//returns true if there is a tetris, false when there is no tetris.
	}
}
