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
	private int frames;
	private int currentFrame = 0;
	private int columns;
	private int rows;
	private int window_height;	
	private int window_width;	
	private int animation_height;
	private int animation_width;
	private int gods = 3;
	private int attacks = 5;
	private int god_number;
	private int attack_number;
	
	public InfoSprite(TreeView treeView, int x, int y, int attack_number, int god_number){
		this.treeView = treeView;
		this.window_x = x;
		this.window_y = y;
		this.animation_x = x + 16;
		this.animation_y = y + 16;
		this.windowBitmap  = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.treeinfo);
		this.window_height = this.windowBitmap.getHeight();
		this.window_width = this.windowBitmap.getWidth();
		this.animationBitmap = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.attacksonlythree2);
//		this.columns = 1;
//		this.rows = 1;
//		this.frames = columns*rows - 1;
//		this.animation_height = this.animationBitmap.getHeight()/this.rows;
//		this.animation_width = this.animationBitmap.getWidth()/this.columns;
		this.animation_height = this.animationBitmap.getHeight()/this.gods;
		this.animation_width = this.animationBitmap.getWidth()/this.attacks;
		this.god_number = god_number;
		this.attack_number = attack_number;
	}
	public void onDraw(Canvas canvas){
		update();
		/*
		 * wyswietlenie statycznego okna
		 */
        Rect src = new Rect(0, 0, this.window_width, this.window_height);
        Rect dst = new Rect(this.window_x, this.window_y, this.window_x + this.window_width, this.window_y + this.window_height);
        canvas.drawBitmap(this.windowBitmap, src, dst, null);
		/*
		 * wyswietlenie animowanego ataku
		 */
//		int srcX = (currentFrame % this.columns) * this.animation_width;
//        int row = currentFrame / this.rows;
//        int srcY = row * this.animation_height;
        int srcX = this.attack_number * this.animation_width;
        int srcY = this.god_number * this.animation_height;
        src = new Rect(srcX, srcY, srcX + this.animation_width, srcY + this.animation_height);
        dst = new Rect(this.animation_x, this.animation_y, this.animation_x + this.animation_width, this.animation_y + this.animation_height);
        canvas.drawBitmap(this.animationBitmap, src, dst, null);
	}
	public void update(){
		this.currentFrame++;
		if(this.currentFrame >= this.frames){
			this.currentFrame = 0;
		}
	}
	public void setCurrentGod(int god){
		this.god_number = god;
	}
	public void setCurrentAttack(int attack){
		this.attack_number = attack;
	}
}
