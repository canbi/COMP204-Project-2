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
	
	
	public static final byte[][] tetris = {		//Tetris Shape
		    {0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0},
		    {0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0},
		    {0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0},
		    {0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0},
		    {0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0}};
	
	public static final byte[][] canAndNadide = {
		    {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0},
		    {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0},
		    {0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0},
		    {0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0},
		    {0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
		    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0}};
	
	
	public static final byte[][] option = {		//Tetris Shape
			{0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0},
			{0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0},
			{0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0},
			{0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0},
			{0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0},};
	
	
	
	public CanvasDrawer() {
		super();
	}
	
	@SuppressWarnings("deprecation")
	public boolean[] mainMenu(){
		//BACKGROUND
		//StdDraw.setPenColor(0,0,0);
		//StdDraw.filledRectangle(this.calculatedWidth, this.calculatedHeigth, this.calculatedWidth, this.calculatedHeigth);
		
		this.setUpCanvas();
		StdDraw.setCanvasSize(calculatedWidth,calculatedHeigth);	//canvas size set
		StdDraw.setXscale(0, calculatedWidth);							//canvas x scale set
		StdDraw.setYscale(calculatedHeigth, 0);							//canvas y scale set
		drawCanvas();								//draws canvas
		
		double backgroundCenterX = (this.calculatedColumnSpace/2.0)+this.frameOffset;
		double backgroundCenterY = (this.calculatedRowSpace/2.0)+this.frameOffset;
		
		StdDraw.setPenColor(192,179,165); //med brown color
		StdDraw.filledRectangle(backgroundCenterX,backgroundCenterY , (this.calculatedColumnSpace/2.0), (this.calculatedRowSpace/2.0));
		
		double button1RatioX = 0.50;
		double button1RatioY = 0.60;
		double button1CenterX = this.calculatedColumnSpace*button1RatioX+this.frameOffset;
		double button1CenterY = this.calculatedRowSpace*button1RatioY;
		double button1Width = this.calculatedColumnSpace/12+2*numberOfRows;
		double button1Height = this.calculatedRowSpace/30;
		double button1Xmin = button1CenterX-button1Width;
		double button1Xmax = button1CenterX+button1Width;
		double button1Ymin = button1CenterY-button1Height;
		double button1Ymax = button1CenterY+button1Height;
		
		
		double button2RatioX = 0.50;
		double button2RatioY = 0.70;
		double button2CenterX = this.calculatedColumnSpace*button2RatioX+this.frameOffset;
		double button2CenterY = this.calculatedRowSpace*button2RatioY;
		double button2Width = this.calculatedColumnSpace/8+2*numberOfRows;
		double button2Height = this.calculatedRowSpace/30;
		double button2Xmin = button2CenterX-button2Width;
		double button2Xmax = button2CenterX+button2Width;
		double button2Ymin = button2CenterY-button2Height;
		double button2Ymax = button2CenterY+button2Height;
		
		//Button 1
		StdDraw.setPenColor(132,122,113);
		StdDraw.filledRectangle(button1CenterX, button1CenterY ,button1Width ,button1Height);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.text(button1CenterX, button1CenterY+3, "PLAY");
		
		//Button 2
		StdDraw.setPenColor(132,122,113);
		StdDraw.filledRectangle(button2CenterX, button2CenterY ,button2Width ,button2Height);
		StdDraw.setPenColor(255,255,255);
		StdDraw.text(button2CenterX, button2CenterY+3, "OPTIONS");
		StdDraw.show();
		
		boolean gameNotStart = true;
		boolean music = true;
		Random r = new Random();
		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);
		
		double tetrisSqrLen = (calculatedColumnSpace)/(2*(tetris[0].length));
		double tetrisCNSqrLen = (calculatedColumnSpace)/(3*(canAndNadide[0].length));
		int tetrisGap = (int) Math.round(tetrisSqrLen/15);
		int tetrisCNGap = (int) Math.round(tetrisCNSqrLen/15);
		while(gameNotStart) {
			red = r.nextInt(256);
			green = r.nextInt(256);
			blue = r.nextInt(256);
			
			for (int i = 0; i < tetris[0].length; i++) {
				for (int j = 0; j < tetris.length; j++) {
					if(tetris[j][i] == 1) {
						double xCoord = ((tetrisSqrLen*2+tetrisGap)*i) + tetrisGap+ calculatedRowSpace*0.005+ calculatedColumnSpace*0.01; //+70*calculatedColumnSpace/100;
						double yCoord = ((tetrisSqrLen*2+tetrisGap)*j) + tetrisGap+ calculatedColumnSpace*0.01+ calculatedRowSpace*0.1;// +12*calculatedRowSpace/100;
						StdDraw.setPenColor(red,green, blue);
						StdDraw.filledSquare(xCoord, yCoord, tetrisSqrLen);
						//DRAWING SQUARE FRAME
						StdDraw.setPenColor(132,122,113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord,yCoord, tetrisSqrLen+tetrisGap/2);
					}
				}
			}
			
			for (int i = 0; i < canAndNadide[0].length; i++) {
				for (int j = 0; j < canAndNadide.length; j++) {
					if(canAndNadide[j][i] == 1) {
						double xCoord = ((tetrisCNSqrLen*2+tetrisCNGap)*i) + tetrisCNGap+ calculatedRowSpace*0.1+ calculatedColumnSpace*0.2; //+70*calculatedColumnSpace/100;
						double yCoord = ((tetrisCNSqrLen*2+tetrisCNGap)*j) + tetrisCNGap+calculatedColumnSpace*0.12+ calculatedRowSpace*0.17;// +12*calculatedRowSpace/100;
						StdDraw.setPenColor(red,green, blue);
						StdDraw.filledSquare(xCoord, yCoord, tetrisCNSqrLen);
						//DRAWING SQUARE FRAME
						StdDraw.setPenColor(132,122,113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord,yCoord, tetrisCNSqrLen+tetrisCNGap/2);
					}
				}
			}
			
			StdDraw.setPenColor(132,122,113);
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
	            	
	            	boolean optionsMenu = true;
	            	while(optionsMenu) {
	            		optionsMenu = this.optionsMenu();
	            	}
	            	this.mainMenu();
	            	/*
	            	StdDraw.setPenColor(255,255,255);
	        		StdDraw.filledRectangle(button2CenterX, button2CenterY ,button2Width ,button2Height);
	            	
	        		
	        		if(music) {
	            		StdDraw.setPenColor(StdDraw.BOOK_RED);
	            		StdDraw.text(button2CenterX, button2CenterY+1, "??? OFF");
	            		StdAudio.close();
	            		music = false;
	            	}
	            	else {
	            		StdDraw.setPenColor(StdDraw.GREEN);
	            		StdDraw.text(button2CenterX, button2CenterY+1, "??? ON");
	            		music = true;
	            	}
	            	StdDraw.show();
	            	StdDraw.pause(100);
	            	*/
	            }
			}
		}
		boolean[] a = {false,music};
		return a;
	}
	
	@SuppressWarnings("deprecation")
	public boolean optionsMenu(){
		
		//BACKGROUND
		double backgroundCenterX = (this.calculatedColumnSpace/2.0)+this.frameOffset;
		double backgroundCenterY = (this.calculatedRowSpace/2.0)+this.frameOffset;
		
		StdDraw.setPenColor(192,179,165); //med brown color
		StdDraw.filledRectangle(backgroundCenterX,backgroundCenterY , (this.calculatedColumnSpace/2.0), (this.calculatedRowSpace/2.0));
		
		int currentPosColumn = -1;
		if(numberOfColumns == 8) currentPosColumn = 0;
		if(numberOfColumns == 9) currentPosColumn = 1;
		if(numberOfColumns == 10) currentPosColumn = 2;
		if(numberOfColumns == 11) currentPosColumn = 3;
		if(numberOfColumns == 12) currentPosColumn = 4;
		
		int currentPosRow = -1;
		if(numberOfRows == 8) currentPosRow = 0;
		if(numberOfRows == 9) currentPosRow = 1;
		if(numberOfRows == 10) currentPosRow = 2;
		if(numberOfRows == 11) currentPosRow = 3;
		if(numberOfRows == 12) currentPosRow = 4;
		if(numberOfRows == 13) currentPosRow = 5;
		if(numberOfRows == 14) currentPosRow = 6;
		if(numberOfRows == 15) currentPosRow = 7;
		if(numberOfRows == 16) currentPosRow = 8;
		if(numberOfRows == 17) currentPosRow = 9;
		if(numberOfRows == 18) currentPosRow = 10;
		if(numberOfRows == 19) currentPosRow = 11;
		if(numberOfRows == 20) currentPosRow = 12;
		
		int currentPosRightPL = -1;
		if(rightPanelWidth == 100) currentPosRightPL =0;
		if(rightPanelWidth == 110) currentPosRightPL =1;
		if(rightPanelWidth == 120) currentPosRightPL =2;
		if(rightPanelWidth == 130) currentPosRightPL =3;
		if(rightPanelWidth == 140) currentPosRightPL =4;
		if(rightPanelWidth == 150) currentPosRightPL =5;
		if(rightPanelWidth == 170) currentPosRightPL =6;
		
		StdDraw.setFont(new Font("calibri", Font.BOLD, 20));
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.textLeft(this.calculatedColumnSpace*0.10, this.calculatedRowSpace*0.46, "Number of Columns");
		Button columnsButton = new Button();
		columnsButton.drawButton(4.0,currentPosColumn,this.numberOfColumns, 0.25, 0.50, 0.12, 0.38, 0.50,this);
		
		StdDraw.setFont(new Font("calibri", Font.BOLD, 20));
		StdDraw.textLeft(this.calculatedColumnSpace*0.10, this.calculatedRowSpace*0.56, "Number of Rows");
		Button rowsButton = new Button();
		rowsButton.drawButton(12.0,currentPosRow,this.numberOfRows, 0.25, 0.60, 0.12, 0.38, 0.50,this);
		
		StdDraw.setFont(new Font("calibri", Font.BOLD, 20));
		StdDraw.textLeft(this.calculatedColumnSpace*0.10, this.calculatedRowSpace*0.66, "Right Panel Width");
		Button rightPanelButton = new Button();
		rightPanelButton.drawButton(6.0, currentPosRightPL, this.rightPanelWidth,  0.25, 0.70, 0.12, 0.38, 0.50,this);
		
		
		//BUTTON3
		double button3RatioX = 0.50;
		double button3RatioY = 0.80;
		double button3CenterX = this.calculatedColumnSpace*button3RatioX+this.frameOffset;
		double button3CenterY = this.calculatedRowSpace*button3RatioY;
		double button3Width = this.calculatedColumnSpace/4+3.5*this.numberOfRows;
		double button3Height = this.calculatedRowSpace/30;
		double button3Xmin = button3CenterX-button3Width;
		double button3Xmax = button3CenterX+button3Width;
		double button3Ymin = button3CenterY-button3Height;
		double button3Ymax = button3CenterY+button3Height;
		
		
		//Button 3
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button3CenterX, button3CenterY ,button3Width ,button3Height);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(button3CenterX, button3CenterY+3, "BACK TO MAIN MENU");
		StdDraw.show();
		
		
		double tetrisSqrLen = (calculatedColumnSpace)/(2*(option[0].length));
		int tetrisGap = (int) Math.round(tetrisSqrLen/15);
		
		boolean options = true;
		Random r = new Random();
		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);
		boolean button1left = false;
		boolean button1right = false;
		boolean button2left = false;
		boolean button2right = false;
		boolean button3left = false;
		boolean button3right = false;
		while(options) {
			for (int i = 0; i < option[0].length; i++) {
				for (int j = 0; j < option.length; j++) {
					if(option[j][i] == 1) {
						double xCoord = ((tetrisSqrLen*2+tetrisGap)*i) + tetrisGap+ calculatedRowSpace*0.005+ calculatedColumnSpace*0.01; //+70*calculatedColumnSpace/100;
						double yCoord = ((tetrisSqrLen*2+tetrisGap)*j) + tetrisGap+ calculatedColumnSpace*0.01+ calculatedRowSpace*0.1;// +12*calculatedRowSpace/100;
						StdDraw.setPenColor(red,green, blue);
						StdDraw.filledSquare(xCoord, yCoord, tetrisSqrLen);
						//DRAWING SQUARE FRAME
						StdDraw.setPenColor(132,122,113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord,yCoord, tetrisSqrLen+tetrisGap/2);
					}
				}
			}
			
			if (StdDraw.isMousePressed()) { 								
	            double xCoord = StdDraw.mouseX();
	            double yCoord = StdDraw.mouseY();
				red = r.nextInt(256);
				green = r.nextInt(256);
				blue = r.nextInt(256);

	            //BACK TO MAIN MENU button
	            if(xCoord< button3Xmax && xCoord > button3Xmin && yCoord<button3Ymax && yCoord > button3Ymin) {
	            	options = false;
	            	StdDraw.show(100);
	            }
	            
	            //--------------------------------------------------------------------------------------------------
	            //Button1 left button
	            if(xCoord< columnsButton.button11Xmax && xCoord > columnsButton.button11Xmin && yCoord<columnsButton.button11Ymax && yCoord > columnsButton.button11Ymin)
	            	button1left = columnsButton.updateButton(true);  
	            
	            
	            //Button1 right button
	            if(xCoord< columnsButton.button12Xmax && xCoord > columnsButton.button12Xmin && yCoord<columnsButton.button12Ymax && yCoord > columnsButton.button12Ymin)
	            	button1right = columnsButton.updateButton(false);  
	            
	            if(button1left) {
	            	button1left = false;
	            	this.numberOfColumns--;
	            	columnsButton.showResult(this.numberOfColumns);
	            }
	            
	            if(button1right) {
	            	button1right = false;
	            	this.numberOfColumns++;
	            	columnsButton.showResult(this.numberOfColumns);
	            }
	            //---------------------------------------------------------------------------------------------------
	            
	            //Button2 left button
	            if(xCoord< rowsButton.button11Xmax && xCoord > rowsButton.button11Xmin && yCoord<rowsButton.button11Ymax && yCoord > rowsButton.button11Ymin)
	            	button2left = rowsButton.updateButton(true);  
	            
	            
	            //Button2 right button
	            if(xCoord< rowsButton.button12Xmax && xCoord > rowsButton.button12Xmin && yCoord<rowsButton.button12Ymax && yCoord > rowsButton.button12Ymin)
	            	button2right = rowsButton.updateButton(false);  
	            
	            if(button2left) {
	            	button2left = false;
	            	this.numberOfRows--;
	            	rowsButton.showResult(this.numberOfRows);
	            }
	            
	            if(button2right) {
	            	button2right = false;
	            	this.numberOfRows++;
	            	rowsButton.showResult(this.numberOfRows);
	            }
	            //---------------------------------------------------------------------------------------------------
	            //Button3 left button
	            if(xCoord< rightPanelButton.button11Xmax && xCoord > rightPanelButton.button11Xmin && yCoord<rightPanelButton.button11Ymax && yCoord > rightPanelButton.button11Ymin)
	            	button3left = rightPanelButton.updateButton(true);  
	            
	            
	            //Button3 right button
	            if(xCoord< rightPanelButton.button12Xmax && xCoord > rightPanelButton.button12Xmin && yCoord<rightPanelButton.button12Ymax && yCoord > rightPanelButton.button12Ymin)
	            	button3right = rightPanelButton.updateButton(false);  
	            
	            if(button3left) {
	            	button3left = false;
	            	this.rightPanelWidth = this.rightPanelWidth -10;
	            	rightPanelButton.showResult(this.rightPanelWidth);
	            }
	            
	            if(button3right) {
	            	button3right = false;
	            	this.rightPanelWidth = this.rightPanelWidth +10;
	            	rightPanelButton.showResult(this.rightPanelWidth);
	            }
	            //---------------------------------------------------------------------------------------------------
	            
	            /*
	            if(xCoord< button2Xmax && xCoord > button2Xmin && yCoord<button2Ymax && yCoord > button2Ymin) {
	            	StdDraw.setPenColor(255,255,255);
	        		StdDraw.filledRectangle(button2CenterX, button2CenterY ,button2Width ,button2Height);
	            	
	        		if(music) {
	            		StdDraw.setPenColor(StdDraw.BOOK_RED);
	            		StdDraw.text(button2CenterX, button2CenterY+1, "??? OFF");
	            		StdAudio.close();
	            		music = false;
	            	}
	            	else {
	            		StdDraw.setPenColor(StdDraw.GREEN);
	            		StdDraw.text(button2CenterX, button2CenterY+1, "??? ON");
	            		music = true;
	            	}
	            	StdDraw.show();
	            	StdDraw.pause(100);
	            	
	            }*/
	            
	            
	            StdDraw.show();
			}
			StdDraw.show();
		}
		//boolean[] a = {false,music};
		return false;
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
	public void updateCanvas() {
		//DRAWING CANVAS
		drawCanvas();
		
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
	public void drawCanvas() {
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
		
		StdDraw.show();	//draws to the screen
	}
	
	public void nextTetriminoDrawing(Tetrimino t) {
		
		StdDraw.setPenColor(255,255,255);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.text(((rightPanelWidth/2.0)+2*frameOffset+calculatedColumnSpace), (frameOffset+0.84*calculatedRowSpace), "NEXT");
		
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
	
	public void endGameAnimation(int number) {
		if(number == 0) {
			Random r = new Random();
			for (int i = 0; i < this.currentTable[0].length; i++) {
				for (int j = 0; j < this.currentTable.length; j++) {
					int red = r.nextInt(256);
					int green = r.nextInt(256);
					int blue = r.nextInt(256);
					int xCoord = this.squaresCoords[j][i][0];								//getting center x coordinates of every squares
					int yCoord = this.squaresCoords[j][i][1];								//getting center y coordinates of every squares
					StdDraw.setPenColor(red, green, blue);
					StdDraw.filledSquare(xCoord, yCoord, this.squareLength);
					StdDraw.setPenColor(132,122,113);
					StdDraw.setPenRadius(0.004);
					StdDraw.square(xCoord,yCoord, this.squareLength+this.gapOfSquares/2);
					StdDraw.show();
					StdDraw.pause(10);
				}
			}
		}
		else if(number == 1) {
			int count=0;
			for (int i = 0; i < this.currentTable[0].length; i++) {
				for (int j = 0; j < this.currentTable.length; j++) {
					int xCoord = this.squaresCoords[j][i][0];								//getting center x coordinates of every squares
					int yCoord = this.squaresCoords[j][i][1];								//getting center y coordinates of every squares
					StdDraw.setPenColor(255-count, 255-count, 255-count);
					StdDraw.filledSquare(xCoord, yCoord, this.squareLength);
					StdDraw.setPenColor(132,122,113);
					StdDraw.setPenRadius(0.004);
					StdDraw.square(xCoord,yCoord, this.squareLength+this.gapOfSquares/2);
					StdDraw.show();
					StdDraw.pause(10);
					
					if(count <255)
						count = count + 3;
				}
			}
		}
		
	}
}
