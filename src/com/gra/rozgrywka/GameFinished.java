package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameFinished {
	
	private GameView view;
	
	private Bitmap bmp;
	private int width;
	private int height;
	private int x;
	private int y;
	
	private Bitmap star_bmp;
	private int star_width;
	private int star_height;
	
	private boolean completed; //PATRZ DOBRZE NAPISALEM! :/
	
	private int stars;
	
	public GameFinished(GameView view, boolean completed, int x, int y,int stars){
		this.view = view;
		this.x = x;
		this.y = y;
		this.stars = stars;
		if(completed){
			this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.gamefinished);
			this.width = this.bmp.getWidth();
			this.height = this.bmp.getHeight();
		}
		else{
			this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.gamefinished);
			this.width = this.bmp.getWidth();
			this.height = this.bmp.getHeight();
		}
		this.star_bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.star);
		this.star_height = this.star_bmp.getHeight();
		this.star_width = this.star_bmp.getWidth();
	}
	public void onDraw(Canvas canvas){
		int srcX = 0;
    	int srcY = 0;
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		canvas.drawBitmap(this.bmp, src, dst, null);
		
		int starX = 100;
		int starY = 400;
		for(int i = 0; i < this.stars; i++){
			src = new Rect(srcX, srcY, srcX + this.star_width, srcY + this.star_height);
			dst = new Rect(this.x + starX + i*this.star_width, this.y + starY, this.x + starX + this.star_width + i*this.star_width, this.y + starY + this.star_height);
			canvas.drawBitmap(this.star_bmp, src, dst, null);
			starX += 2;
		}
	}
}
