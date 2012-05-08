package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class InfoSprite {
	private TreeView treeView;
	private int window_x;
	private int window_y;
	private int animation_x;
	private int animation_y;
	private Bitmap windowBitmap;
	private Bitmap animationBitmap;
	private attackType attack;
	private int frames;
	private int currentFrame = 0;
	private int columns;
	private int rows;
	private int window_height;	
	private int window_width;	
	private int animation_height;
	private int animation_width;
	
	public InfoSprite(TreeView treeView, int x, int y, attackType attack){
		this.treeView = treeView;
		this.window_x = x;
		this.window_y = y;
		this.animation_x = x + 16;
		this.animation_y = y + 16;
		this.attack = attack;
		this.windowBitmap  = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.guzikdown);
		this.window_height = this.windowBitmap.getHeight();
		this.window_width = this.windowBitmap.getWidth();
		
		switch(attack){
		case charge_defence:
			this.animationBitmap = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.fireballshot);
			this.columns = 4;
			this.rows = 1;
			this.frames = columns*rows -1;
			this.animation_height = this.animationBitmap.getHeight()/this.rows;
			this.animation_width = this.animationBitmap.getWidth()/this.columns;
			break;
		}
		
	}
	public void onDraw(Canvas canvas){
		update();
		/*
		 * wyswietlenie statycznego okna
		 */
        Rect src = new Rect(0, 0, this.window_width, this.window_height);
        Rect dst = new Rect(this.window_x-this.window_width/2, this.window_y-this.window_height/2, this.window_x + this.window_width/2, this.window_y + this.window_height/2);
        canvas.drawBitmap(this.windowBitmap, src, dst, null);
		/*
		 * wyswietlenie animowanego ataku
		 */
		int srcX = (currentFrame % this.columns) * this.animation_width;
        int row = currentFrame / this.rows;
        int srcY = row * this.animation_height;
        src = new Rect(srcX, srcY, srcX + this.animation_width, srcY + this.animation_height);
        dst = new Rect(this.animation_x-this.animation_width/2, this.animation_y-this.animation_height/2, this.animation_x + this.animation_width/2, this.animation_y + this.animation_height/2);
        canvas.drawBitmap(this.animationBitmap, src, dst, null);
	}
	public void update(){
		this.currentFrame++;
		if(this.currentFrame > this.frames){
			this.currentFrame = 0;
		}
	}
}
