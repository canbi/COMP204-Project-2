import java.awt.Font;

public class CanvasDrawer {
	
	int numberOfColumns;
	int numberOfRows;
	int rowSpace;
	int squareLength;
	int gapOfSquares;
	int calculatedRowSpace;
	int calculatedColumnSpace;
	int rightPanelWidth;
	int frameOffset;
	int calculatedWidth; 
	int calculatedHeigth;
	int nextSquareLength;
	double nextSquareOffset;
	int nextSquareGap;
	double ratioOfNextHeight;
	int[][] currentTable;
	int[][] currentTableValues;
	int[][][] squaresCoords;
	
	
	public CanvasDrawer() {
		super();
	}
	
	public void setUpCanvas() {
		this.currentTable = new int[this.numberOfColumns][this.numberOfRows]; 			//for storing whether there is a Tetrimino in a coordinate.
		this.currentTableValues = new int[this.numberOfColumns][this.numberOfRows];		//for storing value of coordinates
		this.squaresCoords = new int[this.numberOfColumns][this.numberOfRows][2];		//for storing center coordinates of every squares
		this.squareLength = (int) Math.round((this.rowSpace*15)/((double)((31*this.numberOfRows)-1)));
		this.gapOfSquares = Math.round(this.squareLength/15);
		this.calculatedRowSpace = 2*this.squareLength*this.numberOfRows + (this.numberOfRows-1)*this.gapOfSquares;
		this.calculatedColumnSpace = 2*this.squareLength*this.numberOfColumns + (this.numberOfColumns-1)*this.gapOfSquares;
		this.calculatedWidth = 3*this.frameOffset + this.rightPanelWidth + this.calculatedColumnSpace; 
		this.calculatedHeigth = 2*this.frameOffset + this.calculatedRowSpace;
	}
	
	/**
	 * Redraws the canvas
	 * 
	 * @param squaresCoords stores center coordinates of every squares
	 * @param currentTable stores tables last status in types of 1 or 0. 
	 * @param currentTableValue tables last status of value in types of 2048 game values.
	 * @param canvasAttributes canvas width and height values
	 */
	public void updateCanvas(Tetrimino t) {
		//DRAWING CANVAS
		drawCanvas(t);
		
		//DRAWING UPDATED TABLE
		for (int i = 0; i < this.squaresCoords.length; i++) {
			for (int j = 0; j < this.squaresCoords[i].length; j++) {
				int xCoord = this.squaresCoords[i][j][0];								//getting center x coordinates of every squares
				int yCoord = this.squaresCoords[i][j][1];								//getting center y coordinates of every squares
				
				if(this.currentTable[i][j] == 0) {										//if there is no Tetrimino in current table
					StdDraw.setPenColor(206,195,181);
					StdDraw.filledSquare(xCoord, yCoord, this.squareLength);
				}
				else {																			//if there is a Tetrimino in current table
					if(this.currentTableValues[i][j] == 2) StdDraw.setPenColor(238, 229, 219);		//if the current square has a value of 2
					else if(this.currentTableValues[i][j] == 4) StdDraw.setPenColor(235, 224, 204);	//if the current square has a value of 4
					else if(this.currentTableValues[i][j] == 8)StdDraw.setPenColor(228, 173, 126);	//if the current square has a value of 8
					else if(this.currentTableValues[i][j] == 16)StdDraw.setPenColor(234, 152, 112);	//if the current square has a value of 16
					else if(this.currentTableValues[i][j] == 32)StdDraw.setPenColor(231, 130, 103);	//if the current square has a value of 32
					else if(this.currentTableValues[i][j] == 64)StdDraw.setPenColor(230, 103, 72);	//if the current square has a value of 64
					else if(this.currentTableValues[i][j] == 128)StdDraw.setPenColor(233, 206, 127);	//if the current square has a value of 128
					else if(this.currentTableValues[i][j] == 256)StdDraw.setPenColor(233, 204, 115);	//if the current square has a value of 256
					else if(this.currentTableValues[i][j] == 512)StdDraw.setPenColor(228, 193, 101);	//if the current square has a value of 512
					else if(this.currentTableValues[i][j] == 1024)StdDraw.setPenColor(216, 181, 65);	//if the current square has a value of 1024
					else if(this.currentTableValues[i][j] == 2048)StdDraw.setPenColor(232, 194, 79);	//if the current square has a value of 2048
					
					//DRAWING SQUARE
					StdDraw.filledSquare(xCoord, yCoord, this.squareLength);
					
					//DRAWING SQUARE VALUE
					StdDraw.setPenColor(0,0,0);
					StdDraw.setFont(new Font("calibri", Font.BOLD, this.squareLength));
					StdDraw.text(xCoord, yCoord, Integer.toString(this.currentTableValues[i][j]));
					
					//DRAWING SQUARE FRAME
					StdDraw.setPenColor(132,122,113);
					StdDraw.setPenRadius(0.004);
					StdDraw.square(xCoord,yCoord, this.squareLength+this.gapOfSquares/2);
				}
			}
		}
		StdDraw.show(); //draws the changes to the screen
	}
	
	/**
	 * Draws canvas
	 * 
	 * @param squaresCoords	center coordinates of every squares
	 * @param canvasAttributes canvas width and height values
	 */
	public void drawCanvas(Tetrimino t) {
		//BACKGROUND
		StdDraw.setPenColor(132,122,113);
		StdDraw.filledRectangle(this.calculatedWidth/2, this.calculatedHeigth/2, this.calculatedWidth/2, this.calculatedHeigth/2);
		
		//LEFT AND RIGHT BACKGROUND
		StdDraw.setPenColor(192,179,165); //med brown color
		StdDraw.filledRectangle((this.calculatedColumnSpace/2.0)+this.frameOffset, (this.calculatedRowSpace/2.0)+this.frameOffset, (this.calculatedColumnSpace/2.0), (this.calculatedRowSpace/2.0));
		StdDraw.setPenColor(167,160,151);
		StdDraw.filledRectangle(this.calculatedWidth-this.frameOffset-(this.rightPanelWidth/2.0), (this.calculatedRowSpace/2)+this.frameOffset,this.rightPanelWidth/2.0,(this.calculatedRowSpace/2));
		
		//SQUARES
		for (int i = 0; i < this.numberOfColumns; i++) {
			for (int j = 0; j < this.numberOfRows; j++) {
				int xCoord = ((2*this.squareLength+this.gapOfSquares)*i)  + this.squareLength + this.frameOffset;
				int yCoord = ((2*this.squareLength+this.gapOfSquares)*j)  + this.squareLength + this.frameOffset;
				this.squaresCoords[i][j][0] = xCoord;
				this.squaresCoords[i][j][1] = yCoord;
				StdDraw.setPenColor(206,195,181);
				StdDraw.filledSquare(xCoord, yCoord, this.squareLength);
			}
		}
		
		
		if(t.shape.length == 3) {
			for (int i = 0; i < t.shape.length; i++) {
				for (int j = 0; j < t.shape.length; j++) {
					if(t.shape[i][j] == 1) {
						double xCoord = ((2*this.nextSquareLength+this.nextSquareGap)*j) + 2*this.frameOffset+this.calculatedColumnSpace+2*this.nextSquareOffset+this.nextSquareLength;
						double yCoord = ((2*this.nextSquareLength+this.nextSquareGap)*i) + this.frameOffset + this.ratioOfNextHeight*this.calculatedRowSpace;
						
						if(t.currentValues[i][j] == 2) StdDraw.setPenColor(238, 229, 219);		//if the current square has a value of 2
						else if(t.currentValues[i][j] == 4) StdDraw.setPenColor(235, 224, 204);	//if the current square has a value of 4
						StdDraw.filledSquare(xCoord, yCoord, this.nextSquareLength);
						
						//DRAWING SQUARE VALUE
						StdDraw.setPenColor(0,0,0);
						StdDraw.setFont(new Font("calibri", Font.BOLD, this.nextSquareLength));
						StdDraw.text(xCoord, yCoord, Integer.toString(t.currentValues[i][j]));
						
						//DRAWING SQUARE FRAME
						StdDraw.setPenColor(132,122,113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord,yCoord, this.nextSquareLength+this.nextSquareGap/2);
					}
				}
			}
		}
		else {
			for (int i = 0; i < t.shape.length; i++) {
				for (int j = 0; j < t.shape.length; j++) {
					if(t.shape[i][j] == 1) {
						double xCoord = ((2*this.nextSquareLength+this.nextSquareGap)*j) + 2*this.frameOffset+this.calculatedColumnSpace+this.nextSquareOffset+this.nextSquareLength;
						double yCoord = ((2*this.nextSquareLength+this.nextSquareGap)*i) + this.frameOffset + this.ratioOfNextHeight*this.calculatedRowSpace;
						
						if(t.currentValues[i][j] == 2) StdDraw.setPenColor(238, 229, 219);		//if the current square has a value of 2
						else if(t.currentValues[i][j] == 4) StdDraw.setPenColor(235, 224, 204);	//if the current square has a value of 4
						StdDraw.filledSquare(xCoord, yCoord, this.nextSquareLength);
						
						//DRAWING SQUARE VALUE
						StdDraw.setPenColor(0,0,0);
						StdDraw.setFont(new Font("calibri", Font.BOLD, this.nextSquareLength));
						StdDraw.text(xCoord, yCoord, Integer.toString(t.currentValues[i][j]));
						
						//DRAWING SQUARE FRAME
						StdDraw.setPenColor(132,122,113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord,yCoord, this.nextSquareLength+this.nextSquareGap/2);
					}
				}
			}
			
		}
		
		
		
		StdDraw.show();	//draws to the screen
	}
	
}
