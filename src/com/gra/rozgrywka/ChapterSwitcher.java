package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class ChapterSwitcher {
	private int x;
	private int y;
	private int width;
	private int height;
	private int direction; //-1 lewo  1 prawo
	
	private Bitmap bmp;
	
	private ChapterView view;
	
	public ChapterSwitcher(ChapterView view, int x, int y, int direction){
		this.view = view;
		this.x = x;
		this.y = y;
		this.direction = direction;
		if(direction < 0){
			this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.chapterdirectionleft);
		}
		else if(direction > 0){
			this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.chapterdirectionright);
		}
		this.width = this.bmp.getWidth();
		this.height = this.bmp.getHeight();
	}
	public void onDraw(Canvas canvas){
		int srcX = 0;
		int srcY = 0;
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		canvas.drawBitmap(this.bmp, src, dst, null);
	}
	public boolean checkCollision(int x, int y){
		Rect rec = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		if(rec.contains(x, y)){
			return true;
		}
		return false;
	}
	public int getDirection(){
		return this.direction;
	}
}
