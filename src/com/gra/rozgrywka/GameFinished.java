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
	
	private Bitmap go_tree;
	private int tree_width;
	private int tree_height;
	
	private Bitmap go_chapter;
	private int chapter_width;
	private int chapter_height;
	
	private Bitmap replay;
	private int replay_width;
	private int replay_height;
	
	private int buttonY;	//gdzie maja znajdowac sie guziki
	private int buttonX;
	
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
		this.go_tree = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.gotree);
		this.tree_height = this.go_tree.getHeight();
		this.tree_width = this.go_tree.getWidth();
		
		this.replay = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.replay);
		this.replay_height = this.replay.getHeight();
		this.replay_width = this.replay.getWidth();
		
		this.go_chapter = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.czapter);
		this.chapter_height = this.go_chapter.getHeight();
		this.chapter_width = this.go_chapter.getWidth();
		
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
		
		/*
		 * guzik replay
		 */
		starX = 100;
		starY = 150;
		this.buttonY = starY;
		this.buttonX = starX;
		src = new Rect(srcX, srcY, srcX + this.replay_width, srcY + this.replay_height);
		dst = new Rect(this.x + starX, this.y + starY, this.x + starX + this.replay_width, this.y + starY + this.replay_height);
		canvas.drawBitmap(this.replay, src, dst, null);
		/*
		 * guzik przejscia do drzewa
		 */
		starX = 200;
		src = new Rect(srcX, srcY, srcX + this.tree_width, srcY + this.tree_height);
		dst = new Rect(this.x + starX, this.y + starY, this.x + starX + this.tree_width, this.y + starY + this.tree_height);
		canvas.drawBitmap(this.go_tree, src, dst, null);
		/*
		 * guzik przejscia do rozdzialow
		 */
		starX = 300;
		src = new Rect(srcX, srcY, srcX + this.chapter_width, srcY + this.chapter_height);
		dst = new Rect(this.x + starX, this.y + starY, this.x + starX + this.chapter_width, this.y + starY + this.chapter_height);
		canvas.drawBitmap(this.go_chapter, src, dst, null);
	}
	public int checkCollision(int x, int y){
		int starX = this.buttonX;
		int starY = this.buttonY;
		Rect dst = new Rect(this.x + starX, this.y + starY, this.x + starX + this.chapter_width, this.y + starY + this.chapter_height);
		if(dst.contains(x,y)){
			return 0;
		}
		starX = 200;
		dst = new Rect(this.x + starX, this.y + starY, this.x + starX + this.chapter_width, this.y + starY + this.chapter_height);
		if(dst.contains(x,y)){
			return 1;
		}
		starX = 300;
		dst = new Rect(this.x + starX, this.y + starY, this.x + starX + this.chapter_width, this.y + starY + this.chapter_height);
		if(dst.contains(x,y)){
			return -1;
		}
		return 0;
	}
}
