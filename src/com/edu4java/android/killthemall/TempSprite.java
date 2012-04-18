package com.edu4java.android.killthemall;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


enum bonusType{mana_potion, repair, exp};


/**
 * @author Maciej
 * bonusy wyswietlajace sie na ekranie - np. extra mana, extra cosie
 */

public class TempSprite {
       private int x;
       private int y;
       private int width;
       private int height;
       private boolean animated;
       private Bitmap bmp;
       private int life;	//zmienna przechowujaca zycie podzielone przez liczbe wierszy (potrzebne do animacji)
       private int currentLife;
       private List<TempSprite> temps;
       private int BMP_ROWS;
       private int BMP_COLUMNS;
       private int currentFrame = 0;
       private int amount;	//ilosc zwracanej : many, zycia, upg pointow i innych bonusowych rzeczy
       private GameView gameView;
       private bonusType bt;
       private boolean clicked = false; //zmienna do kolizji (obiekt musi zniuknac gdy juz go nacisniemy
 
       public TempSprite(List<TempSprite> temps, GameView gameView, int x,int y, bonusType bt) {
    	   this.gameView = gameView;
		   this.temps = temps;
		   this.x = x;
		   this.y = y;
		   this.bt = bt;
		   switch(bt){
		   case mana_potion:
			   this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.manapotion);
			   this.BMP_ROWS = 2;
			   this.BMP_COLUMNS = 3;
			   this.currentLife = 120;
			   this.life = this.currentLife / this.BMP_ROWS;
			   this.amount = 50;
			   break; 
		   case repair:
			   this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.repair);
			   this.BMP_ROWS = 2;
			   this.BMP_COLUMNS = 3;
			   this.currentLife = 120;
			   this.life = this.currentLife / this.BMP_ROWS;
			   this.amount = 20;
			   break;
	       case exp:
			   this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.repair);
			   this.BMP_ROWS = 2;
			   this.BMP_COLUMNS = 3;
			   this.currentLife = 60;
			   this.life = this.currentLife / this.BMP_ROWS;
			   this.amount = 1;
			   break;
		   }
		   this.width = bmp.getWidth() / BMP_COLUMNS;
           this.height = bmp.getHeight() / BMP_ROWS;
       }
 
       public void onDraw(Canvas canvas) {
             update();
             int srcX = (currentFrame % BMP_COLUMNS) * width;
             int row = (this.BMP_ROWS-1) - (this.currentLife /this.life);
             int srcY = row * height;
             Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
             Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
             canvas.drawBitmap(bmp, src, dst, null);
             currentFrame++;
       }

       private void update() {
    	   if(clicked){
    		   temps.remove(this);
    	   }
    	   int size = (BMP_COLUMNS * BMP_ROWS)-1;
    	   if(currentFrame >= size){
    		   currentFrame = 0;
    	   }
           if (--currentLife < 1) {
        	   temps.remove(this);
           }
       }
       
       public boolean collision(int x, int y){
    	   Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
   			if(dst.contains(x, y)){
   				this.clicked = true;
   				return true;
   			}
   			return false;
       }
       public bonusType getBonusType(){
    	   return this.bt;
       }
       public int getAmount(){
    	   return this.amount;
       }

}