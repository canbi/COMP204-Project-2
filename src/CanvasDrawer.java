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
	boolean gameNotStarted = true;
	boolean clearRows = true;

	public static final byte[][] tetris = { // Tetris Shape
			{ 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1,
					0, 1, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
					0, 1, 0, 1, 0, 1, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1,
					1, 1, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0,
					0, 1, 0, 1, 0, 1, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0,
					0, 1, 0, 1, 1, 1, 0, 0 } };

	public static final byte[][] canAndNadide = {
			{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0,
					1, 1, 0, 0, 0 },
			{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0,
					1, 0, 1, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0,
					1, 0, 1, 0, 0 },
			{ 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0,
					1, 0, 1, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0,
					1, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1,
					1, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
					0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
					1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
					0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1,
					1, 1, 0, 0, 0 } };

	public static final byte[][] option = { // Tetris Shape
			{ 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0 }, };

	public CanvasDrawer() {
		super();
	}

	@SuppressWarnings("deprecation")
	public Boolean[] mainMenu() {
		Boolean[] returns = { false, this.clearRows };

		this.setUpCanvas();
		StdDraw.setCanvasSize(calculatedWidth, calculatedHeigth); // canvas size set
		StdDraw.setXscale(0, calculatedWidth); // canvas x scale set
		StdDraw.setYscale(calculatedHeigth, 0); // canvas y scale set
		drawCanvas(); // draws canvas

		if (!this.gameNotStarted)
			return returns;
		double backgroundCenterX = (this.calculatedColumnSpace / 2.0) + this.frameOffset;
		double backgroundCenterY = (this.calculatedRowSpace / 2.0) + this.frameOffset;

		StdDraw.setPenColor(192, 179, 165); // med brown color
		StdDraw.filledRectangle(backgroundCenterX, backgroundCenterY, (this.calculatedColumnSpace / 2.0),
				(this.calculatedRowSpace / 2.0));

		// PLAY BUTTON
		double button1RatioX = 0.50;
		double button1RatioY = 0.60;
		double button1Width = this.calculatedColumnSpace / 12 + 2 * numberOfRows;
		double button1Height = this.calculatedRowSpace / 30;
		Button playButton = new Button();
		playButton.singleButton(button1RatioX, button1RatioY, button1Width, button1Height, this);
		playButton.showSingleButton("PLAY", 255, 255, 255);

		// OPTIONS BUTTON
		double button2RatioX = 0.50;
		double button2RatioY = 0.70;
		double button2Width = this.calculatedColumnSpace / 8 + 2 * numberOfRows;
		Button optionsButton = new Button();
		optionsButton.singleButton(button2RatioX, button2RatioY, button2Width, button1Height, this);
		optionsButton.showSingleButton("OPTIONS", 255, 255, 255);

		// RANDOM COLOR
		Random r = new Random();
		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);

		double tetrisSqrLen = (calculatedColumnSpace) / (2 * (tetris[0].length));
		double tetrisCNSqrLen = (calculatedColumnSpace) / (3 * (canAndNadide[0].length));
		int tetrisGap = (int) Math.round(tetrisSqrLen / 15);
		int tetrisCNGap = (int) Math.round(tetrisCNSqrLen / 15);

		while (gameNotStarted) {
			red = r.nextInt(256);
			green = r.nextInt(256);
			blue = r.nextInt(256);

			// TETRIS 2048
			for (int i = 0; i < tetris[0].length; i++) {
				for (int j = 0; j < tetris.length; j++) {
					if (tetris[j][i] == 1) {
						double xCoord = ((tetrisSqrLen * 2 + tetrisGap) * i) + tetrisGap + calculatedRowSpace * 0.005
								+ calculatedColumnSpace * 0.01; // +70*calculatedColumnSpace/100;
						double yCoord = ((tetrisSqrLen * 2 + tetrisGap) * j) + tetrisGap + calculatedColumnSpace * 0.01
								+ calculatedRowSpace * 0.1;// +12*calculatedRowSpace/100;
						StdDraw.setPenColor(red, green, blue);
						StdDraw.filledSquare(xCoord, yCoord, tetrisSqrLen);
						// DRAWING SQUARE FRAME
						StdDraw.setPenColor(132, 122, 113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord, yCoord, tetrisSqrLen + tetrisGap / 2);
					}
				}
			}

			// BY CAN AND NADIDE
			for (int i = 0; i < canAndNadide[0].length; i++) {
				for (int j = 0; j < canAndNadide.length; j++) {
					if (canAndNadide[j][i] == 1) {
						double xCoord = ((tetrisCNSqrLen * 2 + tetrisCNGap) * i) + tetrisCNGap
								+ calculatedRowSpace * 0.1 + calculatedColumnSpace * 0.2; // +70*calculatedColumnSpace/100;
						double yCoord = ((tetrisCNSqrLen * 2 + tetrisCNGap) * j) + tetrisCNGap
								+ calculatedColumnSpace * 0.12 + calculatedRowSpace * 0.17;// +12*calculatedRowSpace/100;
						StdDraw.setPenColor(255 - red, 255 - green, 255 - blue);
						StdDraw.filledSquare(xCoord, yCoord, tetrisCNSqrLen);
						// DRAWING SQUARE FRAME
						StdDraw.setPenColor(132, 122, 113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord, yCoord, tetrisCNSqrLen + tetrisCNGap / 2);
					}
				}
			}

			// RANDOM COLOR PLAY BUTTON
			playButton.showSingleButton("PLAY", red, green, blue);
			StdDraw.show(50);

			// MOUSE PRESSED
			if (StdDraw.isMousePressed()) {
				double xCoord = StdDraw.mouseX();
				double yCoord = StdDraw.mouseY();

				// PLAY BUTTON
				if (xCoord < playButton.button1Xmax && xCoord > playButton.button1Xmin
						&& yCoord < playButton.button1Ymax && yCoord > playButton.button1Ymin) {
					this.gameNotStarted = false;
					StdDraw.pause(100);
				}

				// OPTIONS BUTTON
				else if (xCoord < optionsButton.button1Xmax && xCoord > optionsButton.button1Xmin
						&& yCoord < optionsButton.button1Ymax && yCoord > optionsButton.button1Ymin) {
					boolean optionsMenu = true;
					while (optionsMenu) {
						optionsMenu = this.optionsMenu();
					}
					this.mainMenu();
				}
			}
		}
		Boolean[] returns2 = { false, this.clearRows };
		return returns2;
	}

	@SuppressWarnings("deprecation")
	public boolean optionsMenu() {

		// BACKGROUND
		double backgroundCenterX = (this.calculatedColumnSpace / 2.0) + this.frameOffset;
		double backgroundCenterY = (this.calculatedRowSpace / 2.0) + this.frameOffset;

		StdDraw.setPenColor(192, 179, 165); // med brown color
		StdDraw.filledRectangle(backgroundCenterX, backgroundCenterY, (this.calculatedColumnSpace / 2.0),
				(this.calculatedRowSpace / 2.0));

		int currentPosColumn = -1;
		if (numberOfColumns == 8)
			currentPosColumn = 0;
		if (numberOfColumns == 9)
			currentPosColumn = 1;
		if (numberOfColumns == 10)
			currentPosColumn = 2;
		if (numberOfColumns == 11)
			currentPosColumn = 3;
		if (numberOfColumns == 12)
			currentPosColumn = 4;

		int currentPosRow = -1;
		if (numberOfRows == 8)
			currentPosRow = 0;
		if (numberOfRows == 9)
			currentPosRow = 1;
		if (numberOfRows == 10)
			currentPosRow = 2;
		if (numberOfRows == 11)
			currentPosRow = 3;
		if (numberOfRows == 12)
			currentPosRow = 4;
		if (numberOfRows == 13)
			currentPosRow = 5;
		if (numberOfRows == 14)
			currentPosRow = 6;
		if (numberOfRows == 15)
			currentPosRow = 7;
		if (numberOfRows == 16)
			currentPosRow = 8;
		if (numberOfRows == 17)
			currentPosRow = 9;
		if (numberOfRows == 18)
			currentPosRow = 10;
		if (numberOfRows == 19)
			currentPosRow = 11;
		if (numberOfRows == 20)
			currentPosRow = 12;

		int currentPosRightPL = -1;
		if (rightPanelWidth == 100)
			currentPosRightPL = 0;
		if (rightPanelWidth == 110)
			currentPosRightPL = 1;
		if (rightPanelWidth == 120)
			currentPosRightPL = 2;
		if (rightPanelWidth == 130)
			currentPosRightPL = 3;
		if (rightPanelWidth == 140)
			currentPosRightPL = 4;
		if (rightPanelWidth == 150)
			currentPosRightPL = 5;
		if (rightPanelWidth == 160)
			currentPosRightPL = 6;

		int currentPosLeftPL = -1;
		if (rowSpace == 660)
			currentPosLeftPL = 0;
		if (rowSpace == 690)
			currentPosLeftPL = 1;
		if (rowSpace == 720)
			currentPosLeftPL = 2;
		if (rowSpace == 750)
			currentPosLeftPL = 3;
		if (rowSpace == 780)
			currentPosLeftPL = 4;
		if (rowSpace == 810)
			currentPosLeftPL = 5;
		if (rowSpace == 840)
			currentPosLeftPL = 6;
		if (rowSpace == 870)
			currentPosLeftPL = 7;
		if (rowSpace == 900)
			currentPosLeftPL = 8;

		// BUTTON SETS
		// attributes
		double leftButtonXRatio = 0.10;
		double theBoxXRatio = 0.23;
		double rightButtonXRatio = 0.36;
		double resultBoxXRatio = 0.48;
		double firstSetYRatio = 0.40;
		double secondSetYRatio = 0.50;
		double thirdSetYRatio = 0.60;
		double fourthSetYRatio = 0.70;

		// Number of Columns Button Set
		StdDraw.setFont(new Font("calibri", Font.BOLD, 20));
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.textLeft(this.calculatedColumnSpace * 0.08, this.calculatedRowSpace * 0.36, "Number of Columns");
		Button columnsButton = new Button();
		columnsButton.drawButton(4.0, currentPosColumn, this.numberOfColumns, theBoxXRatio, firstSetYRatio,
				leftButtonXRatio, rightButtonXRatio, resultBoxXRatio, this);

		// Number of Rows Button Set
		StdDraw.setFont(new Font("calibri", Font.BOLD, 20));
		StdDraw.textLeft(this.calculatedColumnSpace * 0.08, this.calculatedRowSpace * 0.46, "Number of Rows");
		Button rowsButton = new Button();
		rowsButton.drawButton(12.0, currentPosRow, this.numberOfRows, theBoxXRatio, secondSetYRatio, leftButtonXRatio,
				rightButtonXRatio, resultBoxXRatio, this);

		// Right Panel Width Button Set
		StdDraw.setFont(new Font("calibri", Font.BOLD, 20));
		StdDraw.textLeft(this.calculatedColumnSpace * 0.08, this.calculatedRowSpace * 0.56, "Right Panel Width");
		Button rightPanelButton = new Button();
		rightPanelButton.drawButton(6.0, currentPosRightPL, this.rightPanelWidth, theBoxXRatio, thirdSetYRatio,
				leftButtonXRatio, rightButtonXRatio, resultBoxXRatio, this);

		// Window Height Button Set
		StdDraw.setFont(new Font("calibri", Font.BOLD, 20));
		StdDraw.textLeft(this.calculatedColumnSpace * 0.08, this.calculatedRowSpace * 0.66, "Window Height");
		Button leftPanelButton = new Button();
		leftPanelButton.drawButton(8.0, currentPosLeftPL, this.rowSpace, theBoxXRatio, fourthSetYRatio,
				leftButtonXRatio, rightButtonXRatio, resultBoxXRatio, this);

		// BACK TO MAIN MENU BUTTON
		double button3RatioX = 0.50;
		double button3RatioY = 0.80;
		double button3Width = this.calculatedColumnSpace / 4 + 3.5 * this.numberOfRows;
		double button3Height = this.calculatedRowSpace / 30;
		Button mainMenu = new Button();
		mainMenu.singleButton(button3RatioX, button3RatioY, button3Width, button3Height, this);
		mainMenu.showSingleButton("BACK TO MAIN MENU", 255, 255, 255);

		// TETRIS ROW CLEAN BUTTON
		double button2RatioX = 0.80;
		double button2RatioY = 0.40;
		double button2Width = this.calculatedColumnSpace / 8 + 2 * this.numberOfRows;
		double button2Height = this.calculatedRowSpace / 40;
		Button tetrisButton = new Button();
		tetrisButton.singleButton(button2RatioX, button2RatioY, button2Width, button2Height, this);
		if (clearRows)
			tetrisButton.showSingleButton("CLEAR ROW", 50, 205, 50);
		else
			tetrisButton.showSingleButton("CLEAR ROW", 220, 20, 60);

		// OPTIONS PICTURE
		double tetrisSqrLen = (calculatedColumnSpace) / (2 * (option[0].length));
		int tetrisGap = (int) Math.round(tetrisSqrLen / 15);

		// RANDOM COLOR
		Random r = new Random();
		int red = r.nextInt(256);
		int green = r.nextInt(256);
		int blue = r.nextInt(256);

		// BOOLEAN CONTROLS
		boolean options = true;
		boolean button1left = false;
		boolean button1right = false;
		boolean button2left = false;
		boolean button2right = false;
		boolean button3left = false;
		boolean button3right = false;
		boolean button4left = false;
		boolean button4right = false;
		while (options) {

			// OPTIONS
			for (int i = 0; i < option[0].length; i++) {
				for (int j = 0; j < option.length; j++) {
					if (option[j][i] == 1) {
						double xCoord = ((tetrisSqrLen * 2 + tetrisGap) * i) + tetrisGap + calculatedRowSpace * 0.005
								+ calculatedColumnSpace * 0.01; // +70*calculatedColumnSpace/100;
						double yCoord = ((tetrisSqrLen * 2 + tetrisGap) * j) + tetrisGap + calculatedColumnSpace * 0.01
								+ calculatedRowSpace * 0.1;// +12*calculatedRowSpace/100;
						StdDraw.setPenColor(red, green, blue);
						StdDraw.filledSquare(xCoord, yCoord, tetrisSqrLen);
						// DRAWING SQUARE FRAME
						StdDraw.setPenColor(132, 122, 113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord, yCoord, tetrisSqrLen + tetrisGap / 2);
					}
				}
			}

			// MOUSE PRESSED
			if (StdDraw.isMousePressed()) {
				double xCoord = StdDraw.mouseX();
				double yCoord = StdDraw.mouseY();
				red = r.nextInt(256);
				green = r.nextInt(256);
				blue = r.nextInt(256);

				// BACK TO MAIN MENU button
				if (xCoord < mainMenu.button1Xmax && xCoord > mainMenu.button1Xmin && yCoord < mainMenu.button1Ymax
						&& yCoord > mainMenu.button1Ymin) {
					options = false;
					StdDraw.show(100);
				}

				if (xCoord < tetrisButton.button1Xmax && xCoord > tetrisButton.button1Xmin
						&& yCoord < tetrisButton.button1Ymax && yCoord > tetrisButton.button1Ymin) {
					if (clearRows) {
						clearRows = false;
						tetrisButton.showSingleButton("CLEAR ROW", 220, 20, 60);
					} else {
						clearRows = true;
						tetrisButton.showSingleButton("CLEAR ROW", 50, 205, 50);
					}
					StdDraw.show();
					StdDraw.pause(100);
				}

				// --------------------------------------------------------------------------------------------------
				// Button1 left button
				if (xCoord < columnsButton.button11Xmax && xCoord > columnsButton.button11Xmin
						&& yCoord < columnsButton.button11Ymax && yCoord > columnsButton.button11Ymin)
					button1left = columnsButton.updateButton(true);

				// Button1 right button
				if (xCoord < columnsButton.button12Xmax && xCoord > columnsButton.button12Xmin
						&& yCoord < columnsButton.button12Ymax && yCoord > columnsButton.button12Ymin)
					button1right = columnsButton.updateButton(false);

				if (button1left) {
					button1left = false;
					this.numberOfColumns--;
					columnsButton.showResult(this.numberOfColumns);
				}

				if (button1right) {
					button1right = false;
					this.numberOfColumns++;
					columnsButton.showResult(this.numberOfColumns);
				}
				// ---------------------------------------------------------------------------------------------------

				// Button2 left button
				if (xCoord < rowsButton.button11Xmax && xCoord > rowsButton.button11Xmin
						&& yCoord < rowsButton.button11Ymax && yCoord > rowsButton.button11Ymin)
					button2left = rowsButton.updateButton(true);

				// Button2 right button
				if (xCoord < rowsButton.button12Xmax && xCoord > rowsButton.button12Xmin
						&& yCoord < rowsButton.button12Ymax && yCoord > rowsButton.button12Ymin)
					button2right = rowsButton.updateButton(false);

				if (button2left) {
					button2left = false;
					this.numberOfRows--;
					rowsButton.showResult(this.numberOfRows);
				}

				if (button2right) {
					button2right = false;
					this.numberOfRows++;
					rowsButton.showResult(this.numberOfRows);
				}
				// ---------------------------------------------------------------------------------------------------
				// Button3 left button
				if (xCoord < rightPanelButton.button11Xmax && xCoord > rightPanelButton.button11Xmin
						&& yCoord < rightPanelButton.button11Ymax && yCoord > rightPanelButton.button11Ymin)
					button3left = rightPanelButton.updateButton(true);

				// Button3 right button
				if (xCoord < rightPanelButton.button12Xmax && xCoord > rightPanelButton.button12Xmin
						&& yCoord < rightPanelButton.button12Ymax && yCoord > rightPanelButton.button12Ymin)
					button3right = rightPanelButton.updateButton(false);

				if (button3left) {
					button3left = false;
					this.rightPanelWidth = this.rightPanelWidth - 10;
					rightPanelButton.showResult(this.rightPanelWidth);
				}

				if (button3right) {
					button3right = false;
					this.rightPanelWidth = this.rightPanelWidth + 10;
					rightPanelButton.showResult(this.rightPanelWidth);
				}
				// ---------------------------------------------------------------------------------------------------
				// Button4 left button
				if (xCoord < leftPanelButton.button11Xmax && xCoord > leftPanelButton.button11Xmin
						&& yCoord < leftPanelButton.button11Ymax && yCoord > leftPanelButton.button11Ymin)
					button4left = leftPanelButton.updateButton(true);

				// Button4 right button
				if (xCoord < leftPanelButton.button12Xmax && xCoord > leftPanelButton.button12Xmin
						&& yCoord < leftPanelButton.button12Ymax && yCoord > leftPanelButton.button12Ymin)
					button4right = leftPanelButton.updateButton(false);

				if (button4left) {
					button4left = false;
					this.rowSpace = this.rowSpace - 30;
					leftPanelButton.showResult(this.rowSpace);
				}

				if (button4right) {
					button4right = false;
					this.rowSpace = this.rowSpace + 30;
					leftPanelButton.showResult(this.rowSpace);
				}
				// ---------------------------------------------------------------------------------------------------
				StdDraw.show();
			}
			StdDraw.show();
		}
		return false;
	}

	public void setUpCanvas() {
		this.currentTable = new int[this.numberOfColumns][this.numberOfRows]; // for storing whether there is a
																				// Tetrimino in a coordinate.
		this.currentTableValues = new int[this.numberOfColumns][this.numberOfRows]; // for storing value of coordinates
		this.squaresCoords = new int[this.numberOfColumns][this.numberOfRows][2]; // for storing center coordinates of
																					// every squares
		this.squareLength = (int) Math.round((this.rowSpace * 15) / ((double) ((31 * this.numberOfRows) - 1)));
		this.gapOfSquares = Math.round(this.squareLength / 15);
		this.calculatedRowSpace = 2 * this.squareLength * this.numberOfRows
				+ (this.numberOfRows - 1) * this.gapOfSquares;
		this.calculatedColumnSpace = 2 * this.squareLength * this.numberOfColumns
				+ (this.numberOfColumns - 1) * this.gapOfSquares;
		this.calculatedWidth = 3 * this.frameOffset + this.rightPanelWidth + this.calculatedColumnSpace;
		this.calculatedHeigth = 2 * this.frameOffset + this.calculatedRowSpace;
	}

	/**
	 * Redraws the canvas
	 * 
	 * @param squaresCoords     stores center coordinates of every squares
	 * @param currentTable      stores tables last status in types of 1 or 0.
	 * @param currentTableValue tables last status of value in types of 2048 game
	 *                          values.
	 * @param canvasAttributes  canvas width and height values
	 */
	public void updateCanvas() {
		// DRAWING CANVAS
		drawCanvas();

		// DRAWING UPDATED TABLE
		for (int i = 0; i < this.squaresCoords.length; i++) {
			for (int j = 0; j < this.squaresCoords[i].length; j++) {
				int xCoord = this.squaresCoords[i][j][0]; // getting center x coordinates of every squares
				int yCoord = this.squaresCoords[i][j][1]; // getting center y coordinates of every squares

				if (this.currentTable[i][j] == 0) { // if there is no Tetrimino in current table
					StdDraw.setPenColor(206, 195, 181);
					StdDraw.filledSquare(xCoord, yCoord, this.squareLength);

				} else { // if there is a Tetrimino in current table
					if (this.currentTableValues[i][j] == 2)
						StdDraw.setPenColor(238, 229, 219); // if the current square has a value of 2
					else if (this.currentTableValues[i][j] == 4)
						StdDraw.setPenColor(235, 224, 204); // if the current square has a value of 4
					else if (this.currentTableValues[i][j] == 8)
						StdDraw.setPenColor(228, 173, 126); // if the current square has a value of 8
					else if (this.currentTableValues[i][j] == 16)
						StdDraw.setPenColor(234, 152, 112); // if the current square has a value of 16
					else if (this.currentTableValues[i][j] == 32)
						StdDraw.setPenColor(231, 130, 103); // if the current square has a value of 32
					else if (this.currentTableValues[i][j] == 64)
						StdDraw.setPenColor(230, 103, 72); // if the current square has a value of 64
					else if (this.currentTableValues[i][j] == 128)
						StdDraw.setPenColor(233, 206, 127); // if the current square has a value of 128
					else if (this.currentTableValues[i][j] == 256)
						StdDraw.setPenColor(233, 204, 115); // if the current square has a value of 256
					else if (this.currentTableValues[i][j] == 512)
						StdDraw.setPenColor(228, 193, 101); // if the current square has a value of 512
					else if (this.currentTableValues[i][j] == 1024)
						StdDraw.setPenColor(216, 181, 65); // if the current square has a value of 1024
					else if (this.currentTableValues[i][j] == 2048)
						StdDraw.setPenColor(232, 194, 79); // if the current square has a value of 2048

					// DRAWING SQUARE
					StdDraw.filledSquare(xCoord, yCoord, this.squareLength);

					// DRAWING SQUARE VALUE
					StdDraw.setPenColor(0, 0, 0);
					StdDraw.setFont(new Font("calibri", Font.BOLD, this.squareLength));
					StdDraw.text(xCoord, yCoord, Integer.toString(this.currentTableValues[i][j]));

					// DRAWING SQUARE FRAME
					StdDraw.setPenColor(132, 122, 113);
					StdDraw.setPenRadius(0.004);
					StdDraw.square(xCoord, yCoord, this.squareLength + this.gapOfSquares / 2);
				}
			}
		}
		StdDraw.show(); // draws the changes to the screen
	}

	/**
	 * Draws canvas
	 * 
	 * @param squaresCoords    center coordinates of every squares
	 * @param canvasAttributes canvas width and height values
	 */
	public void drawCanvas() {
		// BACKGROUND
		StdDraw.setPenColor(132, 122, 113);
		StdDraw.filledRectangle(this.calculatedWidth / 2, this.calculatedHeigth / 2, this.calculatedWidth / 2,
				this.calculatedHeigth / 2);

		// LEFT AND RIGHT BACKGROUND
		StdDraw.setPenColor(192, 179, 165); // med brown color
		StdDraw.filledRectangle((this.calculatedColumnSpace / 2.0) + this.frameOffset,
				(this.calculatedRowSpace / 2.0) + this.frameOffset, (this.calculatedColumnSpace / 2.0),
				(this.calculatedRowSpace / 2.0));
		StdDraw.setPenColor(167, 160, 151);
		StdDraw.filledRectangle(this.calculatedWidth - this.frameOffset - (this.rightPanelWidth / 2.0),
				(this.calculatedRowSpace / 2) + this.frameOffset, this.rightPanelWidth / 2.0,
				(this.calculatedRowSpace / 2));

		// SQUARES
		for (int i = 0; i < this.numberOfColumns; i++) {
			for (int j = 0; j < this.numberOfRows; j++) {
				int xCoord = ((2 * this.squareLength + this.gapOfSquares) * i) + this.squareLength + this.frameOffset;
				int yCoord = ((2 * this.squareLength + this.gapOfSquares) * j) + this.squareLength + this.frameOffset;
				this.squaresCoords[i][j][0] = xCoord;
				this.squaresCoords[i][j][1] = yCoord;
				StdDraw.setPenColor(206, 195, 181);
				StdDraw.filledSquare(xCoord, yCoord, this.squareLength);
			}
		}

		StdDraw.show(); // draws to the screen
	}

	public void nextTetriminoDrawing(Tetrimino t) {

		StdDraw.setPenColor(255, 255, 255);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.text(((rightPanelWidth / 2.0) + 2 * frameOffset + calculatedColumnSpace),
				(frameOffset + 0.84 * calculatedRowSpace), "NEXT");

		if (t.shape.length == 3) {
			for (int i = 0; i < t.shape.length; i++) {
				for (int j = 0; j < t.shape.length; j++) {
					if (t.shape[i][j] == 1) {
						double xCoord = ((2 * this.nextSquareLength + this.nextSquareGap) * j) + 2 * this.frameOffset
								+ this.calculatedColumnSpace + 2 * this.nextSquareOffset + this.nextSquareLength;
						double yCoord = ((2 * this.nextSquareLength + this.nextSquareGap) * i) + this.frameOffset
								+ this.ratioOfNextHeight * this.calculatedRowSpace;

						if (t.currentValues[i][j] == 2)
							StdDraw.setPenColor(238, 229, 219); // if the current square has a value of 2
						else if (t.currentValues[i][j] == 4)
							StdDraw.setPenColor(235, 224, 204); // if the current square has a value of 4
						StdDraw.filledSquare(xCoord, yCoord, this.nextSquareLength);

						// DRAWING SQUARE VALUE
						StdDraw.setPenColor(0, 0, 0);
						StdDraw.setFont(new Font("calibri", Font.BOLD, this.nextSquareLength));
						StdDraw.text(xCoord, yCoord, Integer.toString(t.currentValues[i][j]));

						// DRAWING SQUARE FRAME
						StdDraw.setPenColor(132, 122, 113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord, yCoord, this.nextSquareLength + this.nextSquareGap / 2);
					}
				}
			}
		} else {
			for (int i = 0; i < t.shape.length; i++) {
				for (int j = 0; j < t.shape.length; j++) {
					if (t.shape[i][j] == 1) {
						double xCoord = ((2 * this.nextSquareLength + this.nextSquareGap) * j) + 2 * this.frameOffset
								+ this.calculatedColumnSpace + this.nextSquareOffset + this.nextSquareLength;
						double yCoord = ((2 * this.nextSquareLength + this.nextSquareGap) * i) + this.frameOffset
								+ this.ratioOfNextHeight * this.calculatedRowSpace;

						if (t.currentValues[i][j] == 2)
							StdDraw.setPenColor(238, 229, 219); // if the current square has a value of 2
						else if (t.currentValues[i][j] == 4)
							StdDraw.setPenColor(235, 224, 204); // if the current square has a value of 4
						StdDraw.filledSquare(xCoord, yCoord, this.nextSquareLength);

						// DRAWING SQUARE VALUE
						StdDraw.setPenColor(0, 0, 0);
						StdDraw.setFont(new Font("calibri", Font.BOLD, this.nextSquareLength));
						StdDraw.text(xCoord, yCoord, Integer.toString(t.currentValues[i][j]));

						// DRAWING SQUARE FRAME
						StdDraw.setPenColor(132, 122, 113);
						StdDraw.setPenRadius(0.0025);
						StdDraw.square(xCoord, yCoord, this.nextSquareLength + this.nextSquareGap / 2);
					}
				}
			}
		}

		StdDraw.show(); // draws to the screen

	}

	public void endGameAnimation(int number) {
		if (number == 0) {
			Random r = new Random();
			for (int i = 0; i < this.currentTable[0].length; i++) {
				for (int j = 0; j < this.currentTable.length; j++) {
					int red = r.nextInt(256);
					int green = r.nextInt(256);
					int blue = r.nextInt(256);
					int xCoord = this.squaresCoords[j][i][0]; // getting center x coordinates of every squares
					int yCoord = this.squaresCoords[j][i][1]; // getting center y coordinates of every squares
					StdDraw.setPenColor(red, green, blue);
					StdDraw.filledSquare(xCoord, yCoord, this.squareLength);
					StdDraw.setPenColor(132, 122, 113);
					StdDraw.setPenRadius(0.004);
					StdDraw.square(xCoord, yCoord, this.squareLength + this.gapOfSquares / 2);
					StdDraw.show();
					StdDraw.pause(10);
				}
			}
		} else if (number == 1) {
			int count = 0;
			for (int i = 0; i < this.currentTable[0].length; i++) {
				for (int j = 0; j < this.currentTable.length; j++) {
					int xCoord = this.squaresCoords[j][i][0]; // getting center x coordinates of every squares
					int yCoord = this.squaresCoords[j][i][1]; // getting center y coordinates of every squares
					StdDraw.setPenColor(255 - count, 255 - count, 255 - count);
					StdDraw.filledSquare(xCoord, yCoord, this.squareLength);
					StdDraw.setPenColor(132, 122, 113);
					StdDraw.setPenRadius(0.004);
					StdDraw.square(xCoord, yCoord, this.squareLength + this.gapOfSquares / 2);
					StdDraw.show();
					StdDraw.pause(10);

					if (count < 255)
						count = count + 3;
				}
			}
		}

	}
	
	//CALCULATION OF GAME SCORE
	
	int totalScore = 0; //contains total score of game
	// int tetrisScore = 0;

	public void calculateScore(Tetrimino t) {
		StdDraw.setPenColor(255, 255, 255);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.text(((rightPanelWidth / 2.0) + 2 * frameOffset + calculatedColumnSpace),
				(frameOffset + 0.10 * calculatedRowSpace), "SCORE");

		int numberOfColumns = currentTableValues.length;
		int numberOfRows = currentTableValues[0].length;

		// int counter = 0;
		// int score = 0;

		for (int i = 0; i < numberOfColumns; i++) {
			for (int j = numberOfRows - 1; j > 0; j--) {
				if (currentTable[i][j] == 1 && currentTable[i][j - 1] == -80) { //check the tetrimino pieces has just finished first part of merging operations 
					if (currentTableValues[i][j] == currentTableValues[i][j - 1] * 2) { //check the tetrimino pieces has just finished first part of merging operations 
						int mergeScore = currentTableValues[i][j]; //mergeScore contains merged tetrimino pieces' values
						totalScore = totalScore + mergeScore; // add mergeScore value to totalScore
						// System.out.println("mergescore:" + mergeScore);
						// System.out.println("totalscore:" + totalScore);
						StdDraw.text(((rightPanelWidth / 2.0) + 2 * frameOffset + calculatedColumnSpace),
								(frameOffset + 0.15 * calculatedRowSpace), Integer.toString(totalScore)); //update new score
						StdDraw.show(); //show new score
					}
				}
			}
			StdDraw.text(((rightPanelWidth / 2.0) + 2 * frameOffset + calculatedColumnSpace),
					(frameOffset + 0.15 * calculatedRowSpace), Integer.toString(totalScore)); //update new score
			StdDraw.show();  //show new score

		}
		/*
		 * for(int h = numberOfRows-1; h < numberOfRows; h++) { if(currentTable[i][h] ==
		 * 1) { counter++; score = currentTableValues[i][h]; tetrisScore = tetrisScore +
		 * score; if(counter == numberOfColumns) { counter = 0; totalScore = totalScore
		 * + tetrisScore; System.out.println("tetrisscore:" + tetrisScore);
		 * System.out.println("totalscore:" + totalScore);
		 * StdDraw.text(((rightPanelWidth/2.0)+2*frameOffset+calculatedColumnSpace),
		 * (frameOffset+0.15*calculatedRowSpace), Integer.toString(totalScore));
		 * StdDraw.show(); tetrisScore = 0; } } }
		 */
	}
}
