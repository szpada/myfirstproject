package com.edu4java.android.killthemall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
       // direction = 0 up, 1 left, 2 down, 3 right,
       // animation = 3 back, 1 left, 0 front, 2 right
	private GameView gameView;
    private Bitmap bmp;
    private int x;
    private int y;
    private int width;
    private int height;
    private int life;
    private int currentLife;
    private int currentframe;
    private boolean animated;

    public Sprite(GameView gameView,int x, int y, Bitmap bmp, String function,int MaxLife) {
    	this.gameView = gameView;
		this.x = x;
		this.y = y;
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.currentframe = 0;
		if(function.equalsIgnoreCase("olymp")){
			this.animated = true;
			this.life = MaxLife;
			this.currentLife = MaxLife;
			this.width = bmp.getWidth()/4;
		}
	    else{
	    	this.animated = false;
	    }
	}
      
    public void onDraw(Canvas canvas) {
    	if(this.animated){
    		//update();
    	}
    	int srcX = currentframe * this.width;
		int srcY = 0;
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		canvas.drawBitmap(this.bmp, src, dst, null);
	}
    
    private void update(){
    	if(this.animated){
    		this.currentframe = (int)-((-4 - ((double)(this.life / this.currentframe) * 4))) - 1;
    		if(this.currentframe < 0){
    			this.currentframe = 0;
    		}
    		else if(this.currentframe > 3){
    			this.currentframe = 3;
    		}
    	}
    }
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	public void updateOlymp(int life){
		this.currentLife = life;
	}
}