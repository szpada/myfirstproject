package com.edu4java.android.killthemall;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

<<<<<<< HEAD
enum bonusType{mana_potion, repair};

=======
/**
 * @author Maciej
 * bonusy wyswietlajace sie na ekranie - np. extra mana, extra cosie
 */
>>>>>>> b730aecdded5ec7acce9124bf7b699eaac8447e2
public class TempSprite {
       private int x;
       private int y;
       private int width;
       private int height;
       private boolean animated;
       private Bitmap bmp;
       private int life;
       private int currentLife;
       private List<TempSprite> temps;
       private int BMP_ROWS;
       private int BMP_COLUMNS;
       private int currentFrame = 0;
       private GameView gameView;
 
       public TempSprite(List<TempSprite> temps, GameView gameView, int x,int y, bonusType bt) {
    	   this.gameView = gameView;
		   this.temps = temps;
		   this.x = x;
		   this.y = y;
		   switch(bt){
		   case mana_potion:
			   this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.manapotion);
			   this.life = 30;
			   this.currentLife = 30;
			   this.BMP_ROWS = 3;
			   this.BMP_COLUMNS = 3;
			   break; 
		   case repair:
			   this.life = 30;
			   this.currentLife = 30;
			   this.BMP_ROWS = 3;
			   this.BMP_COLUMNS = 3;
			   break;
		   }
		   this.width = bmp.getWidth() / BMP_COLUMNS;
           this.height = bmp.getHeight() / BMP_ROWS;
       }
 
       public void onDraw(Canvas canvas) {
             update();
             int srcX = (currentFrame % BMP_COLUMNS) * width;
             int row = this.life / this.currentLife;
             int srcY = row * height;
             Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
             Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
             canvas.drawBitmap(bmp, src, dst, null);
             currentFrame++;
       }

       private void update() {
    	   int size = (BMP_COLUMNS * BMP_ROWS)-1;
    	   if(currentFrame >= size){
    		   currentFrame = 0;
    	   }
           if (--life < 1) {
        	   temps.remove(this);
           }
       }
       
       public boolean collision(int x, int y){
   		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
   		if(dst.contains(x, y)){
   			return true;
   		}
   		return false;
   	}
}