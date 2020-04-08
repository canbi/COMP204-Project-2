import java.util.Random;
/**
 * 			MEF University - 2020 Spring
 * 			   Faculty of Engineering
 * 			    Computer Engineering
 * 		    Programming Studio - COMP 204
 * 			 Instructor: Muhittin G�kmen
 * 
 * 					Project 2
 * 				Tetris 2048 Game
 * 
 * 
 * Tetrimino class for tetris objects
 * 
 * @author Can Bi 				041701001
 * @author Nadide Beyza Dokur   041801134
 * @since 17.03.2020
 */
public class Tetrimino implements Cloneable{
	byte[][] shape;								//Tetrimino shape
	int[][][] currentPos;						//Tetrimino current position for every index of its shape
	int[][] currentValues;						//Tetrimino current values for every position of its shape
	
	public static final byte[][] ShapeI = {		//Tetrimino shape for I
	    {0, 0, 0, 0},
	    {0, 0, 0, 0},
	    {1, 1, 1, 1},
	    {0, 0, 0, 0},};
	
	public static final byte[][] ShapeJ = {		//Tetrimino shape for J
        {0, 0, 0},
        {1, 0, 0},
        {1, 1, 1},};
	
	public static final byte[][] ShapeL = {		//Tetrimino shape for L
        {0, 0, 0},
        {0, 0, 1},
        {1, 1, 1},};
	
    public static final byte[][] ShapeO = {		//Tetrimino shape for 0
	    {0, 0, 0, 0},
	    {0, 1, 1, 0},
	    {0, 1, 1, 0},
	    {0, 0, 0, 0},};
    
    public static final byte[][] ShapeS = {		//Tetrimino shape for S
        {0, 0, 0},
        {0, 1, 1},
        {1, 1, 0},};
    
    public static final byte[][] ShapeT = {		//Tetrimino shape for T
        {0, 0, 0},
        {0, 1, 0},
        {1, 1, 1},};
    
    public static final byte[][] ShapeZ = {		//Tetrimino shape for Z
        {0, 0, 0},
        {1, 1, 0},
        {0, 1, 1},};
    
    public Tetrimino(byte[][] shape) {			//constructor for Tetrimino class
        this.shape = shape;						//assigns Tetrimino type
    }
    
    @Override
   	protected Object clone() throws CloneNotSupportedException {
       	try {
   			return super.clone();
   		} catch (CloneNotSupportedException e) {
   			System.out.println("Cloning not allowed.");
   			return this;
   		}
   	}
    public int[][][] nextLocRotate(){
    	int n = this.shape.length;
    	int[][][] nextPos = new int[n][n][2];
    	
    	//find maxx and maxy
		int maxx = 0;
		int maxxCoord = -1;
		int maxy= 0;
		int maxyCoord=-1;
		for (int j = 0; j < n; j++) {
			for (int j2 = 0; j2 < n; j2++) {
				nextPos[j][j2][0] = this.currentPos[j][j2][0];
				nextPos[j][j2][1] = this.currentPos[j][j2][1];
				if(this.currentPos[j][j2][0] != -10) {
					if(this.currentPos[j][j2][1] > maxy) {
						maxy = this.currentPos[j][j2][1];
						maxyCoord = j;
					}
					if(this.currentPos[j][j2][0] > maxx) {
						maxx = this.currentPos[j][j2][0];
						maxxCoord = j2;
					}
				}
			}
		}
    	
    	//Rotation all indexes
        for (int x = 0; x < n / 2; x++){ 
            for (int y = x; y < n-x-1; y++){ 
                int[] temp = nextPos[x][y];  // store current cell in temp variable 
                nextPos[x][y] = nextPos[y][n-1-x]; // move values from right to top 
                nextPos[y][n-1-x] = nextPos[n-1-x][n-1-y]; // move values from bottom to right 
                nextPos[n-1-x][n-1-y] = nextPos[n-1-y][x];  // move values from left to bottom 
                nextPos[n-1-y][x] = temp;  // assign temp to left 
            } 
        } 
        
    	//Correcting coords
		for (int k = 0; k < n; k++) {
			for (int k2 = 0; k2 < n; k2++) {
				if(nextPos[k][k2][0] != -10) {
					nextPos[k][k2][0] = maxx-(maxxCoord-k2);
					nextPos[k][k2][1] = maxy-(maxyCoord-k); 
				}
			}
		}
		
		return nextPos;
    }
    
    public int[][] nextLocValueRotate(){
    	int n = this.shape.length;
    	int[][] nextPosValue = new int[n][n];
    	
    	
    	
    	for (int j = 0; j < n; j++) {
			for (int j2 = 0; j2 < n; j2++) {
				nextPosValue[j][j2] = this.currentValues[j][j2];
				nextPosValue[j][j2] = this.currentValues[j][j2];
			}
    	}
        
    	//Rotation all indexes
        for (int x = 0; x < n / 2; x++){ 
            for (int y = x; y < n-x-1; y++){ 
                int temp = nextPosValue[x][y];  // store current cell in temp variable 
                nextPosValue[x][y] = nextPosValue[y][n-1-x]; // move values from right to top 
                nextPosValue[y][n-1-x] = nextPosValue[n-1-x][n-1-y]; // move values from bottom to right 
                nextPosValue[n-1-x][n-1-y] = nextPosValue[n-1-y][x];  // move values from left to bottom 
                nextPosValue[n-1-y][x] = temp;  // assign temp to left 
            } 
        } 
        
		return nextPosValue;
    }
    
    public boolean rotate(CanvasDrawer canvas) {
    	int n = this.shape.length;
    	boolean canRotate = false;
    	
    	//erasing old location tetrimino from currentTable
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(this.currentPos[i][j][0] != -10) {
					int xCoord = this.currentPos[i][j][0];
					int yCoord = this.currentPos[i][j][1];
					canvas.currentTable[xCoord][yCoord] = 0;
					canvas.currentTableValues[xCoord][yCoord] = -1;
				}
			}
    	}		
		
    	int[][][] nextPos = this.nextLocRotate();
    	int[][] nextPosValue = this.nextLocValueRotate();
    	canRotate = canMove(nextPos, canvas);
    	
    	if(canRotate) {
    		
    		//writing tetrimino new location to currentTable
        	for (int i = n-1; i >= 0; i--) {
    			for (int j = n-1; j >=0; j--) {
    				if(nextPos[i][j][0] != -10) {
    					canvas.currentTable[nextPos[i][j][0]][nextPos[i][j][1]] = 1;
    					this.currentPos[i][j][0] = nextPos[i][j][0];
    					this.currentPos[i][j][1] = nextPos[i][j][1];
    					int value = nextPosValue[i][j];
    					this.currentValues[i][j] = value;
    					canvas.currentTableValues[nextPos[i][j][0]][nextPos[i][j][1]] = value;
    				}
    				else {
    					this.currentPos[i][j][0] = -10;
    					this.currentPos[i][j][1] = -10;
    				}
    			}		
        	}
        	return true;
    	}
    	else {
    		//writing tetrimino old location to currentTable
        	for (int i = n-1; i >= 0; i--) {
    			for (int j = n-1; j >=0; j--) {
    				if(this.currentPos[i][j][0] != -10) {
    					canvas.currentTable[this.currentPos[i][j][0]][this.currentPos[i][j][1]] = 1;
    					int value = this.currentValues[i][j];
    					canvas.currentTableValues[this.currentPos[i][j][0]][this.currentPos[i][j][1]] = value;
    				}
    			}
        	}
        	return false;
    	}
    }
    
    /**
     * 
     * @param currentTable
     * @param currentTableValues
     * @return
     */
    public boolean goLeft(CanvasDrawer canvas) {
    	int n = this.shape.length;				//length of the Tetrimino shape
    	boolean canGoLeft = false;				//initially assings false
    	int[][][] nextPos = new int[n][n][2];	//for storing next position of the given Tetrimino
    	
    	//FINDING NEXT POSITION AND ERASING OLD POSITION
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(this.currentPos[i][j][0] != -10) {				//if the Tetrimino has a piece on that coordinates of shape
					int xCoord = this.currentPos[i][j][0];			//gets current x coordinate
					int yCoord = this.currentPos[i][j][1];			//gets current y coordinate
					nextPos[i][j][0] = xCoord-1;					//updates x coordinate and assigns it to nextPos array
					nextPos[i][j][1] = yCoord;						//assigns it to nextPos array
					canvas.currentTable[xCoord][yCoord] = 0;				//erases the old position in the currentTable array
					canvas.currentTableValues[xCoord][yCoord] = -1;		//assings -1 in currentTableValues array
				}
				else {												//if the Tetrimino has no a piece on that coordinates of shape
					nextPos[i][j][0] = -10;							//assings -10 to that coordinate in nextPos array
					nextPos[i][j][1] = -10;							//assings -10 to that coordinate in nextPos array
				}
			}
		}
    	
    	//NEXT POSITION CONTROL
    	canGoLeft = canMove(nextPos, canvas);		//returns true if the next position is available
    	
    	if(canGoLeft) {														//if the next position is available. Updates by using nextPos array
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					this.currentPos[i][j][0] = xCoord - 1;				//updates x coordinate
    					canvas.currentTable[xCoord-1][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					canvas.currentTableValues[xCoord-1][yCoord] = value;		//assigns the value to currentTableValues array
    					
    				}
    			}
    		}
    	}
    	
    	else {																//if the next position is not available. Uses old position
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					canvas.currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					canvas.currentTableValues[xCoord][yCoord] = value;			//assigns the value to currentTableValues array
    					
    				}
    			}	
    		}
    		return false;			//returns false because next position was not available
    	}
    	return true;				//returns true because next position was available
    }
    
    /**
     * 
     * @param currentTable
     * @param currentTableValues
     * @return
     */
    public boolean goRight(CanvasDrawer canvas) {
    	int n = this.shape.length;				//length of the Tetrimino shape
    	boolean canGoRight = false;				//initially assings false
    	int[][][] nextPos = new int[n][n][2];	//for storing next position of the given Tetrimino
    	
    	//FINDING NEXT POSITION AND ERASING OLD POSITION
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(this.currentPos[i][j][0] != -10) {				//if the Tetrimino has a piece on that coordinates of shape
					int xCoord = this.currentPos[i][j][0];			//gets current x coordinate
					int yCoord = this.currentPos[i][j][1];			//gets current y coordinate
					nextPos[i][j][0] = xCoord+1;					//updates x coordinate and assigns it to nextPos array
					nextPos[i][j][1] = yCoord;						//assigns it to nextPos array
					canvas.currentTable[xCoord][yCoord] = 0;				//erases the old position in the currentTable array
					canvas.currentTableValues[xCoord][yCoord] = -1;		//assings -1 in currentTableValues array
				}
				else {												//if the Tetrimino has no a piece on that coordinates of shape
					nextPos[i][j][0] = -10;							//assings -10 to that coordinate in nextPos array
					nextPos[i][j][1] = -10;							//assings -10 to that coordinate in nextPos array
				}
			}
		}
    	
    	//NEXT POSITION CONTROL
    	canGoRight = canMove(nextPos, canvas);		//returns true if the next position is available
    	
    	if(canGoRight) {													//if the next position is available. Updates by using nextPos array
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					this.currentPos[i][j][0] = xCoord + 1;				//updates x coordinate
    					canvas.currentTable[xCoord+1][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					canvas.currentTableValues[xCoord+1][yCoord] = value;		//assigns the value to currentTableValues array
    				}
    			}
    		}
    	}
    	else {																//if the next position is not available. Uses old position 
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					canvas.currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					canvas.currentTableValues[xCoord][yCoord] = value;			//assigns the value to currentTableValues array
    				}
    			}	
    		}
    		return false;			//returns false because next position was not available
    	}
    	return true;				//returns true because next position was available
    }
    
    /**
     * 
     * @param currentTable
     * @param currentTableValues
     * @return
     */
    public boolean goDown(CanvasDrawer canvas,Tetrimino t) {
    	int n = this.shape.length;				//length of the Tetrimino shape
    	boolean canGoDown = false;				//initially assings false
    	boolean mergeControl = true;
    	boolean contControl = true;
    	boolean moreControl = true;
    	int[][][] nextPos = new int[n][n][2];	//for storing next position of the given Tetrimino
    	
    	//FINDING NEXT POSITION AND ERASING OLD POSITION
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(this.currentPos[i][j][0] != -10) {				//if the Tetrimino has a piece on that coordinates of shape
					int xCoord = this.currentPos[i][j][0];			//gets current x coordinate
					int yCoord = this.currentPos[i][j][1];			//gets current y coordinate
					nextPos[i][j][0] = xCoord;						//assigns it to nextPos array
					nextPos[i][j][1] = yCoord+1;					//updates y coordinate and assigns it to nextPos array
					canvas.currentTable[xCoord][yCoord] = 0;				//erases the old position in the currentTable array
				//	currentTableValues[xCoord][yCoord] = -1;		//assings -1 in currentTableValues array
				}
				else {												//if the Tetrimino has no a piece on that coordinates of shape
					nextPos[i][j][0] = -10;							//assings -10 to that coordinate in nextPos array
					nextPos[i][j][1] = -10;							//assings -10 to that coordinate in nextPos array
				}
			}
		}
    	
    	//NEXT POSITION CONTROL
    	canGoDown = canMove(nextPos, canvas);		//returns true if the next position is available
    	mergeControl = canMerge(canvas,t);
    	
    	if(canGoDown) {														//if the next position is available. Updates by using nextPos array
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					this.currentPos[i][j][1] = yCoord +1;				//updates y coordinate
    					canvas.currentTable[xCoord][yCoord+1] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					canvas.currentTableValues[xCoord][yCoord+1] = value;		//assigns the value to currentTableValues array
    				}
    			}
    		}
    	}
    	
		else if(!canGoDown && mergeControl){
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					canvas.currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					canvas.currentTableValues[xCoord][yCoord] = value;			//assigns the value to currentTableValues array
    				}
    			}	
    		}
        	while(mergeControl) {
        		mergeControl = canMerge(canvas,t);        		
        	}  		       		
        	return false;													//returns false because next position was not available
    		 
		}else {
        		for (int i = 0; i < n; i++) {
        			for (int j = 0; j < n; j++) {
        				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
        					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
        					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
        					canvas.currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
        					int value = this.currentValues[i][j];				//gets value of that coordinate
        					canvas.currentTableValues[xCoord][yCoord] = value;			//assigns the value to currentTableValues array
        				}
        			}	
        		}
	        	while(contControl) {
	        		contControl = contMerge(canvas,t);
	        	}  		       		
	        	return false;													//returns false because next position was not available
	    		 
			}
    	return true;															//returns true because next position was available
}    
    /**
     * 
     * @param currentTable
     * @param currentTableValues
     * @return
     */
    public void init(CanvasDrawer canvas) {
    	Random r = new Random();				//creating random class object
    	int[] valueArr = {2, 2, 2, 4};			//initializing values
    	int n = this.shape.length;				//length of the Tetrimino shape
    	int numberOfColumns = canvas.currentTable.length;
    	
    	
    	//CREATING INITIAL POSITION
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(this.shape[i][j] == 1) {					//if the Tetrimino has a piece on that coordinates of shape
					int randomValue = r.nextInt(4);			//gets random index number
					int value = valueArr[randomValue];		//gets tetrimino with random index number
					this.currentPos[i][j][0] = j+(numberOfColumns/2)-1;			//assings x coordinate
					this.currentPos[i][j][1] = i;			//assigns y coordinate
					this.currentValues[i][j] = value; 		//assigns value
				}
				else {										//if the Tetrimino has no piece on that coordinates of shape
					this.currentPos[i][j][0] = -10;			//assigns -10 to x coordinate
					this.currentPos[i][j][1] = -10;			//assigns -10 to y coordinate
				}
			}
		}
    	
    	
    }

    public boolean settle(CanvasDrawer canvas) {
    	boolean canInit = true;					//initially assings false
    	int n = this.shape.length;				//length of the Tetrimino shape
    	//INITIAL POSITION CONTROL
    	canInit = canMove(this.currentPos, canvas);	//returns true if the initial position is available
    	
    	if(canInit) {															//if the initial position is available.
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.shape[i][j] == 1) {									//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];					//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];					//gets current y coordinate
    					canvas.currentTable[xCoord][yCoord] = 1;						//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];					//gets value of that coordinate				
    					canvas.currentTableValues[xCoord][yCoord] = value;				//assigns the value to currentTableValues array
    				}
    			}
    		}
    		return true;		//returns true because initial position is available
    	}
    	return false;			//returns false because initial position was not available
    }
    
    /**
     * 
     * @param nextPos
     * @param currentTable
     * @param currentTableValues
     * @return
     */
    public boolean canMove(int[][][] nextPos, CanvasDrawer canvas) {
    	int n = this.shape.length;															//length of the Tetrimino shape
    	int numberOfColumns = canvas.currentTable.length;
    	int numberOfRows = canvas.currentTable[0].length;
    	
    	
    	//NEXT POSITION CONTROL
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(nextPos[i][j][0] != -10) {												//if the Tetrimino has a piece on that coordinates of shape	
					int xCoord = nextPos[i][j][0];											//gets current x coordinate
					int yCoord = nextPos[i][j][1];											//gets current y coordinate
					
					if(xCoord < 0 || xCoord >=numberOfColumns)								//if column size is not available
						return false;														//returns false
						
					if(yCoord >= numberOfRows)												//if row size is not available
						return false;														//returns false
								
					if(canvas.currentTable[xCoord][yCoord] == 1) {									//if there is a Tetrimino piece in wanted next position
						return false;													//returns false					
					}
													
				}
			}
		}
    	return true;																		//returns true if the next position is available
    }
    
    public boolean canMerge(CanvasDrawer canvas,Tetrimino t) {   	
    	int numberOfColumns = canvas.currentTableValues.length;
    	int numberOfRows = canvas.currentTableValues[0].length;
    	boolean abc = true;
    	boolean hasMerged = false;
    	for (int i = 0; i < numberOfColumns; i++) {
    		abc = true;
			for (int j = numberOfRows-1; j > 0 && abc; j--) {
				if(canvas.currentTable[i][j] == 1 && canvas.currentTable[i][j-1] == 1) {
					if(canvas.currentTableValues[i][j] == canvas.currentTableValues[i][j-1]) {
						canvas.currentTableValues[i][j] += canvas.currentTableValues[i][j-1];
						canvas.currentTable[i][j-1] = -80;
						contMerge(canvas,t);
						abc = false;
						hasMerged = true;
					}												
				}
			}  	
    	}
    	return hasMerged;
    }
    
    public boolean contMerge(CanvasDrawer canvas,Tetrimino t) {   	
    	int numberOfColumns = canvas.currentTableValues.length;
    	int numberOfRows = canvas.currentTableValues[0].length;
    	
    	for (int i = 0; i < numberOfColumns; i++) {
			for (int j = numberOfRows-1; j > 0; j--) {
				if(canvas.currentTable[i][j] == 1 && canvas.currentTable[i][j-1] == -80) {
					if(canvas.currentTableValues[i][j] == canvas.currentTableValues[i][j-1]*2) {
						canvas.currentTableValues[i][j-1] = -30;
						canvas.currentTable[i][j-1] = 0;
						for (int j2 = j-1; j2 > 0; j2--) {
							canvas.currentTable[i][j2] = canvas.currentTable[i][j2-1];
							canvas.currentTableValues[i][j2] = canvas.currentTableValues[i][j2-1];
						}
						canvas.currentTable[i][0] = 0;
						canvas.currentTableValues[i][0] = -50;
						return true;
					}												
				}				
				if(canvas.currentTable[i][j] == 0 && canvas.currentTable[i][j-1] == 1) {
					canvas.currentTable[i][j] = 1;
					canvas.currentTable[i][j-1] = 0;
					canvas.currentTableValues[i][j] = canvas.currentTableValues[i][j-1];
					canvas.updateCanvas();
					canvas.nextTetriminoDrawing(t);
					StdDraw.show();
					StdDraw.pause(100);
					return true;																
				}
			}  	
    	}
    	return false;
    }    
}


