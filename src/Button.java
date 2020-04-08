import java.awt.Font;

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
	
	public void drawButton(double b,int startingB,int number,double button1RatioX,double button1RatioY,double button11RatioX,double button12RatioX,double button13RatioX,CanvasDrawer canvas) {
		
		//çubuðun bulunduðu kutu
		this.button1CenterX = canvas.calculatedColumnSpace*button1RatioX;
		this.button1CenterY = canvas.calculatedRowSpace*button1RatioY;
		double button1Width = canvas.calculatedColumnSpace/12;
		double button1Height = canvas.calculatedRowSpace/40;
		this.button1Xmin = button1CenterX-button1Width;
		this.button1Xmax = button1CenterX+button1Width;
		this.button1Ymin = button1CenterY-button1Height;
		this.button1Ymax = button1CenterY+button1Height;
		
		//gösterge çubuðu
		this.button1iOffset = 5;
		this.button1iDistance = (2*button1Width-2*button1iOffset)/b;
		this.button1iCenterX = button1Xmin+ startingB*button1iDistance + button1iOffset;
		
		//sol buton
		this.button11CenterX = canvas.calculatedColumnSpace*button11RatioX;
		this.button11CenterY = canvas.calculatedRowSpace*button1RatioY;
		double button11Width = canvas.calculatedRowSpace/40;
		double button11Height = button11Width;
		this.button11Xmin = button11CenterX-button11Width;
		this.button11Xmax = button11CenterX+button11Width;
		this.button11Ymin = button11CenterY-button11Height;
		this.button11Ymax = button11CenterY+button11Height;
		
		//sað buton
		this.button12CenterX = canvas.calculatedColumnSpace*button12RatioX;
		this.button12CenterY = canvas.calculatedRowSpace*button1RatioY;
		double button12Width = canvas.calculatedRowSpace/40;
		double button12Height = button12Width;
		this.button12Xmin = button12CenterX-button12Width;
		this.button12Xmax = button12CenterX+button12Width;
		this.button12Ymin = button12CenterY-button12Height;
		this.button12Ymax = button12CenterY+button12Height;
		
		//sayý kutusu
		this.button13CenterX = canvas.calculatedColumnSpace*button13RatioX;
		this.button13CenterY = canvas.calculatedRowSpace*button1RatioY;
		double button13Width = canvas.calculatedRowSpace/34;
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
		StdDraw.text(button13CenterX, button13CenterY+3, Integer.toString(number));
		
		//StdDraw.show();
	}
	
	public boolean  updateButton(Boolean direction) {
		Boolean buttonMove = true;
		//Redrawing Button 1
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button1CenterX, button1CenterY , (button1Xmax-button1Xmin)/2.0 , (button1Ymax-button1Ymin)/2.0);
		StdDraw.setPenColor(100,100,100);
		
		if(direction) {
			button1iCenterX = button1iCenterX - button1iDistance;
			buttonMove = true;
			if(button1iCenterX < button1Xmin+ button1iOffset) {
				button1iCenterX = button1iCenterX + button1iDistance;
				buttonMove = false;
			}
		}
		else {
			button1iCenterX = button1iCenterX + button1iDistance;
			buttonMove = true;
			if(button1iCenterX > button1Xmax - button1iOffset) {
				button1iCenterX = button1iCenterX - button1iDistance;
				buttonMove = false;
			}
		}
		
		StdDraw.filledRectangle(button1iCenterX, button1CenterY, button1iOffset, (button1Ymax-button1Ymin)/2.0);
		StdDraw.show(200);	
		return buttonMove;
	}
	
	public void showResult(int number) {
		StdDraw.setPenColor(255,255,255);
		StdDraw.filledRectangle(button13CenterX, button13CenterY , (button13Xmax-button13Xmin)/2.0 ,(button13Ymax-button13Ymin)/2.0);
		StdDraw.setPenColor(0,0,0);
		StdDraw.rectangle(button13CenterX, button13CenterY ,(button13Xmax-button13Xmin)/2.0 ,(button13Ymax-button13Ymin)/2.0);
		StdDraw.setFont(new Font("calibri", Font.BOLD, 30));
		StdDraw.text(button13CenterX, button13CenterY+3, Integer.toString(number));
	}
}
