package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameFinished {
	
	private int x;
	private int y;
	
	private GameView view;
	
	private Bitmap bmp;
	private int width;
	private int height;
	
	private Bitmap star_bmp;
	private int star_width;
	private int star_height;
	
	private Bitmap label;
	private int label_width;
	private int label_height;
	
	private boolean completed; //PATRZ DOBRZE NAPISALEM! :/
	
	private int stars;
	
	public GameFinished(GameView view, boolean completed, int x, int y,int stars){
		this.view = view;
		this.x = x;
		this.y = y;
		this.stars = stars;
		if(completed){
			this.label = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.won);
			this.label_width = this.label.getWidth();
			this.label_height = this.label.getHeight();
		}
		else{
			this.label = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.owned);
			this.label_width = this.label.getWidth();
			this.label_height = this.label.getHeight();
		}
		this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.gamefinished);
		this.width = this.bmp.getWidth();
		this.height = this.bmp.getHeight();
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
		starY = 300;
		src = new Rect(srcX, srcY, srcX + this.label_width, srcY + this.label_height);
		dst = new Rect(this.x + starX, this.y + starY, this.x + starX + this.label_width, this.y + starY + this.label_height);
		canvas.drawBitmap(this.label, src, dst, null);
	}
}
