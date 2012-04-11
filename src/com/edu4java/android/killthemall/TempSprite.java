package com.edu4java.android.killthemall;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author Maciej
 * bonusy wyswietlajace sie na ekranie - np. extra mana, extra cosie
 */
public class TempSprite {
       private float x;
       private float y;
       private int width;
       private int height;
       private boolean animated;
       private Bitmap bmp;
       private int life = 15;
       private List<TempSprite> temps;
       private static final int BMP_ROWS = 4;
       private static final int BMP_COLUMNS = 4;
       private int currentFrame = 0;
 
       public TempSprite(List<TempSprite> temps, GameView gameView, float x,float y, Bitmap bmp, boolean animate) {
    	   this.bmp = bmp;
		   this.temps = temps;
           this.animated = animate;
       
    	   if(animate){
    		   this.x = x - (bmp.getWidth()/BMP_COLUMNS)/2;
    		   this.y = y - (bmp.getHeight()/BMP_ROWS)/2;
    		   this.width = bmp.getWidth() / BMP_COLUMNS;
               this.height = bmp.getHeight() / BMP_ROWS;
    	   }
    	   else{
    		   this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0),gameView.getWidth() - bmp.getWidth());
    		   this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0),gameView.getHeight() - bmp.getHeight()); 
    	   }
       }
 
       public void onDraw(Canvas canvas) {
             update();
             int srcX = (currentFrame % BMP_COLUMNS) * width;
             int row = currentFrame/ BMP_ROWS;
             int srcY = row * height;
             currentFrame++;
             Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
             Rect dst = new Rect(Math.round(x), Math.round(y), Math.round(x) + width, Math.round(y) + height);
             if(animated){
            	 canvas.drawBitmap(bmp, src, dst, null);
             }
             else{
            	 canvas.drawBitmap(bmp, x, y, null);
             }
       }

       private void update() {
    	   if (this.animated){
    		   int size = (BMP_COLUMNS * BMP_ROWS)-1;
    		   if(currentFrame >= size){
    			   currentFrame = 0;
    		   }
    	   }
           if (--life < 1) {
        	   temps.remove(this);
           }
       }
}