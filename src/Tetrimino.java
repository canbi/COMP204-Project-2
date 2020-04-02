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
public class Tetrimino {
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
    
    public int[][][] nextLocRotate(int[][] currentTable, int[][] currentTableValues){
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
    
    public boolean rotate(int[][] currentTable, int[][] currentTableValues) {
    	int n = this.shape.length;
    	boolean canRotate = false;
    	
    	//erasing old location tetrimino from currentTable
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(this.currentPos[i][j][0] != -10) {
					int xCoord = this.currentPos[i][j][0];
					int yCoord = this.currentPos[i][j][1];
					currentTable[xCoord][yCoord] = 0;
					currentTableValues[xCoord][yCoord] = -1;
				}
			}
    	}		
		
    	int[][][] nextPos = this.nextLocRotate(currentTable,currentTableValues);
    	int[][] nextPosValue = this.nextLocValueRotate();
    	canRotate = canMove(nextPos, currentTable,currentTableValues);
    	
    	if(canRotate) {
    		
    		//writing tetrimino new location to currentTable
        	for (int i = n-1; i >= 0; i--) {
    			for (int j = n-1; j >=0; j--) {
    				if(nextPos[i][j][0] != -10) {
    					currentTable[nextPos[i][j][0]][nextPos[i][j][1]] = 1;
    					this.currentPos[i][j][0] = nextPos[i][j][0];
    					this.currentPos[i][j][1] = nextPos[i][j][1];
    					int value = nextPosValue[i][j];
    					this.currentValues[i][j] = value;
    					currentTableValues[nextPos[i][j][0]][nextPos[i][j][1]] = value;
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
    					currentTable[this.currentPos[i][j][0]][this.currentPos[i][j][1]] = 1;
    					int value = this.currentValues[i][j];
    					currentTableValues[this.currentPos[i][j][0]][this.currentPos[i][j][1]] = value;
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
    public boolean goLeft(int[][] currentTable, int[][] currentTableValues) {
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
					currentTable[xCoord][yCoord] = 0;				//erases the old position in the currentTable array
					currentTableValues[xCoord][yCoord] = -1;		//assings -1 in currentTableValues array
				}
				else {												//if the Tetrimino has no a piece on that coordinates of shape
					nextPos[i][j][0] = -10;							//assings -10 to that coordinate in nextPos array
					nextPos[i][j][1] = -10;							//assings -10 to that coordinate in nextPos array
				}
			}
		}
    	
    	//NEXT POSITION CONTROL
    	canGoLeft = canMove(nextPos, currentTable,currentTableValues);		//returns true if the next position is available
    	
    	if(canGoLeft) {														//if the next position is available. Updates by using nextPos array
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					this.currentPos[i][j][0] = xCoord - 1;				//updates x coordinate
    					currentTable[xCoord-1][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					currentTableValues[xCoord-1][yCoord] = value;		//assigns the value to currentTableValues array
    					
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
    					currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					currentTableValues[xCoord][yCoord] = value;			//assigns the value to currentTableValues array
    					
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
    public boolean goRight(int[][] currentTable, int[][] currentTableValues) {
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
					currentTable[xCoord][yCoord] = 0;				//erases the old position in the currentTable array
					currentTableValues[xCoord][yCoord] = -1;		//assings -1 in currentTableValues array
				}
				else {												//if the Tetrimino has no a piece on that coordinates of shape
					nextPos[i][j][0] = -10;							//assings -10 to that coordinate in nextPos array
					nextPos[i][j][1] = -10;							//assings -10 to that coordinate in nextPos array
				}
			}
		}
    	
    	//NEXT POSITION CONTROL
    	canGoRight = canMove(nextPos, currentTable,currentTableValues);		//returns true if the next position is available
    	
    	if(canGoRight) {													//if the next position is available. Updates by using nextPos array
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					this.currentPos[i][j][0] = xCoord + 1;				//updates x coordinate
    					currentTable[xCoord+1][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					currentTableValues[xCoord+1][yCoord] = value;		//assigns the value to currentTableValues array
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
    					currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					currentTableValues[xCoord][yCoord] = value;			//assigns the value to currentTableValues array
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
    public boolean goDown(int[][] currentTable, int[][] currentTableValues) {
    	int n = this.shape.length;				//length of the Tetrimino shape
    	int numberOfColumns = currentTable.length;
    	int numberOfRows = currentTable[0].length;
    	boolean canGoDown = false;				//initially assings false
    	boolean mergeControl = true;
    //	boolean moreControl = false;
    	int[][][] nextPos = new int[n][n][2];	//for storing next position of the given Tetrimino
    	
    	//FINDING NEXT POSITION AND ERASING OLD POSITION
    	for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(this.currentPos[i][j][0] != -10) {				//if the Tetrimino has a piece on that coordinates of shape
					int xCoord = this.currentPos[i][j][0];			//gets current x coordinate
					int yCoord = this.currentPos[i][j][1];			//gets current y coordinate
					nextPos[i][j][0] = xCoord;						//assigns it to nextPos array
					nextPos[i][j][1] = yCoord+1;					//updates y coordinate and assigns it to nextPos array
					currentTable[xCoord][yCoord] = 0;				//erases the old position in the currentTable array
				//	currentTableValues[xCoord][yCoord] = -1;		//assings -1 in currentTableValues array
				}
				else {												//if the Tetrimino has no a piece on that coordinates of shape
					nextPos[i][j][0] = -10;							//assings -10 to that coordinate in nextPos array
					nextPos[i][j][1] = -10;							//assings -10 to that coordinate in nextPos array
				}
			}
		}
    	
    	//NEXT POSITION CONTROL
    	canGoDown = canMove(nextPos, currentTable,currentTableValues);		//returns true if the next position is available
    	
    	if(canGoDown) {														//if the next position is available. Updates by using nextPos array
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					this.currentPos[i][j][1] = yCoord +1;				//updates y coordinate
    					currentTable[xCoord][yCoord+1] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					currentTableValues[xCoord][yCoord+1] = value;		//assigns the value to currentTableValues array
    				}
    			}
    		}
    	}
			else {
        		for (int i = 0; i < n; i++) {
        			for (int j = 0; j < n; j++) {
        				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
        					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
        					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
        					currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
        					int value = this.currentValues[i][j];				//gets value of that coordinate
        					currentTableValues[xCoord][yCoord] = value;			//assigns the value to currentTableValues array
        					System.out.println("amazing");
        				}
        			}	
        		}
        		while(mergeControl) 
        			mergeControl = canMerge(currentTableValues, currentTableValues);
        		
        		       		
        		return false;													//returns false because next position was not available
    		} 
	return true;																//returns true because next position was available
}
    
    	
    	
    	
    	
    /*	else {																//if the next position is not available. Uses old position
    		mergeControl = canMerge(currentTableValues, currentTableValues);
    		if(mergeControl) {
        		for (int i = 0; i < n; i++) {
        			for (int j = 0; j < n; j++) {
        				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
        					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
        					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
        					currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
        					int value = this.currentValues[i][j];				//gets value of that coordinate
        				  	if(yCoord < numberOfRows-1) {
        				  		if(currentTableValues[xCoord][yCoord] == currentTableValues[xCoord][yCoord+1]) {
        				  			currentTableValues[xCoord][yCoord+1] = value + value;			//assigns the value to currentTableValues array       					
        				  			currentTable[xCoord][yCoord] = 0;
        				  	comment*		moreControl = canGoMore(currentTable, currentTableValues);
        				  				if(moreControl) {
        				  					System.out.println("yes more");
        				  			    	for (int x = 0; x < b; x++) {
        				  						for (int y = b1-1; y > 0; y--) {
        				  							int carrier = currentTableValues[x][y-1];
        				  							currentTableValues[x][y] = carrier;
        				  							currentTable[x][y-1] = 0;																       				  							
        				  						}  	
        				  			    	}
        				  				}else
        				  					System.out.println("no more"); *comment
        				  		}
        				  	}
        				}
        			}	
        		}
        		return false;													//returns false because next position was not available
    		} */
     		
    		/*	else {
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.currentPos[i][j][0] != -10) {					//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];				//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];				//gets current y coordinate
    					currentTable[xCoord][yCoord] = 1;					//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];				//gets value of that coordinate
    					currentTableValues[xCoord][yCoord] = value;			//assigns the value to currentTableValues array
    					System.out.println("amazing");
    				}
    			}	
    		}
    		return false;													//returns false because next position was not available
		} */
  
    
    /**
     * 
     * @param currentTable
     * @param currentTableValues
     * @return
     */
    public boolean init(int[][] currentTable, int[][] currentTableValues) {
    	Random r = new Random();				//creating random class object
    	int[] valueArr = {2, 2, 2, 4};			//initializing values
    	int n = this.shape.length;				//length of the Tetrimino shape
    	boolean canInit = true;					//initially assings false
    	int numberOfColumns = currentTable.length;
    	
    	
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
    	
    	//INITIAL POSITION CONTROL
    	canInit = canMove(this.currentPos, currentTable,currentTableValues);	//returns true if the initial position is available
    	
    	if(canInit) {															//if the initial position is available.
    		for (int i = 0; i < n; i++) {
    			for (int j = 0; j < n; j++) {
    				if(this.shape[i][j] == 1) {									//if the Tetrimino has a piece on that coordinates of shape
    					int xCoord = this.currentPos[i][j][0];					//gets current x coordinate
    					int yCoord = this.currentPos[i][j][1];					//gets current y coordinate
    					currentTable[xCoord][yCoord] = 1;						//assigns 1 that coordinate to currentTable array
    					int value = this.currentValues[i][j];					//gets value of that coordinate				
    					currentTableValues[xCoord][yCoord] = value;				//assigns the value to currentTableValues array
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
    public boolean canMove(int[][][] nextPos, int[][] currentTable, int[][] currentTableValues) {
    	int n = this.shape.length;															//length of the Tetrimino shape
    	int numberOfColumns = currentTable.length;
    	int numberOfRows = currentTable[0].length;
    	
    	
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
								
					if(currentTable[xCoord][yCoord] == 1) {									//if there is a Tetrimino piece in wanted next position
						return false;													//returns false					
					}
													
				}
			}
		}
    	return true;																		//returns true if the next position is available
    }
    
    public boolean canMerge(int[][] currentTable, int[][] currentTableValues) {   	
    	int numberOfColumns = currentTableValues.length;
    	int numberOfRows = currentTableValues[0].length;
    	boolean checkMerge = false;
    	
    	for (int i = 0; i < numberOfColumns; i++) {
			for (int j = numberOfRows-1; j > 0; j--) {
				if(currentTable[i][j] == 1 && currentTable[i][j-1] == 1) {
					System.out.println("excellent");
					if(currentTableValues[i][j] == currentTableValues[i][j-1]) {
						currentTableValues[i][j] +=currentTableValues[i][j];
						currentTableValues[i][j-1] = -30;
						currentTable[i][j-1] = 0;
						this.shape[i][j-1] = -10;
						for (int j2 = j-1; j2 > 0; j2--) {
							currentTable[i][j2-1] = currentTable[i][j2];
							currentTableValues[i][j2-1] = currentTableValues[i][j2];
						}
						currentTable[i][0] = 0;
						currentTableValues[i][0] = -50;
						System.out.println("hello2");
						checkMerge = true;
					}												
				}
			}  	
    	}
    	System.out.println("hello");
    	return checkMerge;
    }
  
    /*
    public boolean canGoMore(int[][] currentTable, int[][] currentTableValues) {
    	int b = currentTable.length;
    	int b1 = currentTable[0].length;
    	
    	for (int i = 0; i < b; i++) {
			for (int j = b1-1; j > 0; j--) {
				if(currentTable[i][j] == 0 && currentTable[i][j-1] == 1) {
					return true;																
				}
			}  	
    	}
    	return false;
    } */
    
    
}


