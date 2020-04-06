
public class deneme {

	public static void main(String[] args) {
		
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
		
		
		canvas.mainMenu();
		
	}

}
