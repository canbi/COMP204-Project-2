import java.awt.Font;
import java.util.Random;

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
	
	
	public boolean[] mainMenu(){
		//BACKGROUND
		//StdDraw.setPenColor(0,0,0);
		//StdDraw.filledRectangle(this.calculatedWidth, this.calculatedHeigth, this.calculatedWidth, this.calculatedHeigth);
		
		double backgroundCenterX = (this.calculatedColumnSpace/2.0)+this.frameOffset;
		double backgroundCenterY = (this.calculatedRowSpace/2.0)+this.frameOffset;
		
		StdDraw.setPenColor(192,179,165); //med brown color
		StdDraw.filledRectangle(backgroundCenterX,backgroundCenterY , (this.calculatedColumnSpace/2.0), (this.calculatedRowSpace/2.0));
		
		double button1RatioX = 0.50;
		double button1RatioY = 0.50;
		double button1CenterX = this.calculatedColumnSpace*button1RatioX;
		double button1CenterY = this.calculatedRowSpace*button1RatioY;
		double button1Width = this.calculatedColumnSpace/8;
		double button1Height = this.calculatedRowSpace/30;
		double button1Xmin = button1CenterX-button1Width;
		double button1Xmax = button1CenterX+button1Width;
		double button1Ymin = button1CenterY-button1Height;
		double button1Ymax = button1CenterY+button1Height;
		
		
		double button2RatioX = 0.50;
		double button2RatioY = 0.60;
		double button2CenterX = this.calculatedColumnSpace*button2RatioX;
		double button2CenterY = this.calculatedRowSpace*button2RatioY;
		double button2Width = this.calculatedColumnSpace/6;
		double button2Height = this.calculatedRowSpace/30;
		double button2Xmin = button2CenterX-button2Width;
		double button2Xmax = button2CenterX+button2Width;
		double button2Ymin = button2CenterY-button2Height;
		double button2Ymax = button2CenterY+button2Height;
		
		
		//Button 1
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button1CenterX, button1CenterY ,button1Width ,button1Height);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.setPenColor(0,0,0);
		StdDraw.text(button1CenterX, button1CenterY+1, "PLAY");
		
		//Button 2
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button2CenterX, button2CenterY ,button2Width ,button2Height);
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.text(button2CenterX, button2CenterY+1, "MUSIC ON");
		StdDraw.show();
		
		boolean gameNotStart = true;
		boolean music = true;
		
		StdAudio.play("tetris.wav");
		while(gameNotStart) {
			Random r = new Random();
			int red = r.nextInt(256);
			int green = r.nextInt(256);
			int blue = r.nextInt(256);
			StdDraw.setPenColor(255,255,255);
			StdDraw.filledRectangle(button1CenterX, button1CenterY ,button1Width ,button1Height);
			StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
			StdDraw.setPenColor(red,green, blue);
			StdDraw.text(button1CenterX, button1CenterY+1, "PLAY");
			StdDraw.show(50);
			
			
			if (StdDraw.isMousePressed()) { 								
	            double xCoord = StdDraw.mouseX();
	            double yCoord = StdDraw.mouseY();
	            
	            if(xCoord< button1Xmax && xCoord > button1Xmin && yCoord<button1Ymax && yCoord > button1Ymin) {
	            	gameNotStart = false;
	            	StdDraw.pause(100);
	            }
	            
	            if(xCoord< button2Xmax && xCoord > button2Xmin && yCoord<button2Ymax && yCoord > button2Ymin) {
	            	StdDraw.setPenColor(255,255,255);
	        		StdDraw.filledRectangle(button2CenterX, button2CenterY ,button2Width ,button2Height);
	            	
	        		if(music) {
	            		StdDraw.setPenColor(StdDraw.BOOK_RED);
	            		StdDraw.text(button2CenterX, button2CenterY+1, "MUSIC OFF");
	            		StdAudio.close();
	            		music = false;
	            	}
	            	else {
	            		StdDraw.setPenColor(StdDraw.GREEN);
	            		StdDraw.text(button2CenterX, button2CenterY+1, "MUSIC ON");
	            		StdAudio.play("tetris.wav");
	            		music = true;
	            	}
	            	StdDraw.show();
	            	StdDraw.pause(100);
	            	
	            }
	            
			}
		}
		boolean[] a = {false,music};
		return a;
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
