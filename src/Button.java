import java.awt.Font;
/**
 * MEF University - 2020 Spring Faculty of Engineering Computer Engineering
 * Programming Studio - COMP 204 Instructor: Muhittin G�kmen
 * 
 * Project 2 Tetris 2048 Game
 * 
 * Button class for clickable buttons
 * 
 * @author Can Bi 041701001
 * @author Nadide Beyza Dokur 041801134
 * @since 17.03.2020
 */
public class Button {
	double button1RatioX;
	double button1RatioY;
	double button1CenterX;
	double button1CenterY;
	double button1Xmin;
	double button1Xmax;
	double button1Ymin;
	double button1Ymax;
	double button11RatioX; 
	double button11CenterX;
	double button11CenterY;
	double button11Xmin;
	double button11Xmax;
	double button11Ymin;
	double button11Ymax;
	double button12RatioX;
	double button12CenterX;
	double button12CenterY;
	double button12Xmin;
	double button12Xmax;
	double button12Ymin;
	double button12Ymax;
	double button13RatioX; 
	double button13CenterX;
	double button13CenterY;
	double button13Xmin;
	double button13Xmax;
	double button13Ymin;
	double button13Ymax;
	double button1iOffset;
	double button1iDistance;
	double button1iCenterX;
	public Button() {
		super();
	}
	
	/**
	 * Draws 4 part button
	 * 
	 * @param b number of gap between indexes
	 * @param startingB starting index position
	 * @param currentValue current value
	 * @param button1RatioX x coordinate ratio for canvas width
	 * @param button1RatioY y coordinate ratio for canvas height
	 * @param button11RatioX x coordinate ratio for canvas width
	 * @param button12RatioX x coordinate ratio for canvas width	
	 * @param button13RatioX x coordinate ratio for canvas width
	 * @param canvas CanvasDrawer Object
	 */
	public void drawButton(double b,int startingB,int currentValue,double button1RatioX,double button1RatioY,double button11RatioX,double button12RatioX,double button13RatioX,CanvasDrawer canvas) {
		
		// MAIN BOX
		this.button1CenterX = canvas.calculatedColumnSpace*button1RatioX;
		this.button1CenterY = canvas.calculatedRowSpace*button1RatioY;
		double button1Width = canvas.calculatedColumnSpace/12;
		double button1Height = canvas.calculatedRowSpace/40;
		this.button1Xmin = button1CenterX-button1Width;
		this.button1Xmax = button1CenterX+button1Width;
		this.button1Ymin = button1CenterY-button1Height;
		this.button1Ymax = button1CenterY+button1Height;
		
		// INDICATOR STICK
		this.button1iOffset = 5;
		this.button1iDistance = (2*button1Width-2*button1iOffset)/b;
		this.button1iCenterX = button1Xmin+ startingB*button1iDistance + button1iOffset;
		
		// LEFT BUTTON
		this.button11CenterX = canvas.calculatedColumnSpace*button11RatioX;
		this.button11CenterY = canvas.calculatedRowSpace*button1RatioY;
		double button11Width = canvas.calculatedRowSpace/40;
		double button11Height = button11Width;
		this.button11Xmin = button11CenterX-button11Width;
		this.button11Xmax = button11CenterX+button11Width;
		this.button11Ymin = button11CenterY-button11Height;
		this.button11Ymax = button11CenterY+button11Height;
		
		// RIGHT BUTTON
		this.button12CenterX = canvas.calculatedColumnSpace*button12RatioX;
		this.button12CenterY = canvas.calculatedRowSpace*button1RatioY;
		double button12Width = canvas.calculatedRowSpace/40;
		double button12Height = button12Width;
		this.button12Xmin = button12CenterX-button12Width;
		this.button12Xmax = button12CenterX+button12Width;
		this.button12Ymin = button12CenterY-button12Height;
		this.button12Ymax = button12CenterY+button12Height;
		
		// RESULT BOX
		this.button13CenterX = canvas.calculatedColumnSpace*button13RatioX;
		this.button13CenterY = canvas.calculatedRowSpace*button1RatioY;
		double button13Width = canvas.calculatedRowSpace/25;
		double button13Height = canvas.calculatedRowSpace/40;
		this.button13Xmin = button13CenterX-button13Width;
		this.button13Xmax = button13CenterX+button13Width;
		this.button13Ymin = button13CenterY-button13Height;
		this.button13Ymax = button13CenterY+button13Height;
		
		//Button 1
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button1CenterX, button1CenterY ,button1Width ,button1Height);
		StdDraw.setPenColor(0,0,0);
		StdDraw.filledRectangle(button1iCenterX, button1CenterY, button1iOffset, button1Height);
		
		//Button 11
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button11CenterX, button11CenterY ,button11Width ,button11Height);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.setPenColor(0,0,0);
		StdDraw.text(button11CenterX, button11CenterY+3, "<");
		
		//Button 12
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button12CenterX, button12CenterY ,button12Width ,button12Height);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.setPenColor(0,0,0);
		StdDraw.text(button12CenterX, button12CenterY+3, ">");
		
		//Button 13
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button13CenterX, button13CenterY ,button13Width ,button13Height);
		StdDraw.setPenColor(0,0,0);
		StdDraw.rectangle(button13CenterX, button13CenterY ,button13Width ,button13Height);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.text(button13CenterX, button13CenterY+2, Integer.toString(currentValue));
	}
	
	@SuppressWarnings("deprecation")
	/**
	 * Controls the action.
	 * It applies if the action is possible.
	 * 
	 * @param direction direction. If it is true, left. If it is false, right.
	 * @return returns true if it is possible
	 */
	public boolean  updateButton(Boolean direction) {
		Boolean buttonMove = true;
		//Redrawing Button 1
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button1CenterX, button1CenterY , (button1Xmax-button1Xmin)/2.0 , (button1Ymax-button1Ymin)/2.0);
		StdDraw.setPenColor(0,0,0);
		if(direction) {
			button1iCenterX = button1iCenterX - button1iDistance;
			buttonMove = true;
			if(button1iCenterX < button1Xmin+ 1) {
				button1iCenterX = button1iCenterX + button1iDistance;
				buttonMove = false;
			}
		}
		else {
			button1iCenterX = button1iCenterX + button1iDistance;
			buttonMove = true;
			if(button1iCenterX > button1Xmax - 1) {
				button1iCenterX = button1iCenterX - button1iDistance;
				buttonMove = false;
			}
		}
		StdDraw.filledRectangle(button1iCenterX, button1CenterY, button1iOffset, (button1Ymax-button1Ymin)/2.0);
		StdDraw.show(200);	
		return buttonMove;
	}
	
	/**
	 * Updates the result box
	 * 
	 * @param currentValue current value
	 */
	public void showResult(int currentValue) {
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button13CenterX, button13CenterY , (button13Xmax-button13Xmin)/2.0 ,(button13Ymax-button13Ymin)/2.0);
		StdDraw.setPenColor(0,0,0);
		StdDraw.rectangle(button13CenterX, button13CenterY ,(button13Xmax-button13Xmin)/2.0 ,(button13Ymax-button13Ymin)/2.0);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.text(button13CenterX, button13CenterY+2, Integer.toString(currentValue));
	}

	/**
	 * Draws single button
	 * 
	 * @param buttonRatioX x coordinate ratio for canvas width
	 * @param buttonRatioY y coordinate ratio for canvas height
	 * @param buttonWidth button width
	 * @param buttonHeight button height
	 * @param canvas CanvasDrawer Object
	 */
	public void singleButton(double buttonRatioX,double buttonRatioY, double buttonWidth, double buttonHeight, CanvasDrawer canvas) {
		
		button1CenterX = canvas.calculatedColumnSpace*buttonRatioX+canvas.frameOffset;
		button1CenterY = canvas.calculatedRowSpace*buttonRatioY;
		button1Xmin = button1CenterX-buttonWidth;
		button1Xmax = button1CenterX+buttonWidth;
		button1Ymin = button1CenterY-buttonHeight;
		button1Ymax = button1CenterY+buttonHeight;
		
	}
	
	/**
	 * Updates the button
	 * 
	 * @param buttonName button name
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 */
	public void showSingleButton(String buttonName, int r, int g, int b) {
		StdDraw.setPenColor(132,122,113);
		StdDraw.filledRectangle(button1CenterX, button1CenterY , (button1Xmax-button1Xmin)/2.0 , (button1Ymax-button1Ymin)/2.0);
		StdDraw.setPenColor(r,g, b);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.text(button1CenterX, button1CenterY+3, buttonName);
		
	}
	
}
