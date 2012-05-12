package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


public class InfoSprite {
	/**
	 * @author Szpada
	 * 
	 * klasa wyswietlajace statystyki ataku - jego
	 * zasieg, obrazenia i koszt ambrozji.
	 */
	private TreeView treeView;
	private int window_x;
	private int window_y;
	private int animation_x;
	private int animation_y;
	private int cost_x;
	private int cost_y;
	
	private Bitmap windowBitmap;
	private Bitmap animationBitmap;
	private Bitmap costBitmap; 
	
	private int frames;
	private int currentFrame = 0;
	private int columns;
	private int rows;
	
	private int window_height;	
	private int window_width;	
	private int animation_height;
	private int animation_width;
	private int cost_height;
	private int cost_width;
	
	private int gods = 3;
	private int attacks = 5;
	private int god_number;
	private int attack_number;
	private int mana_cost = 0;
	private int dmg = 0;
	private int range = 0;
	
	public InfoSprite(TreeView treeView, int x, int y, int attack_number, int god_number){
		this.treeView = treeView;
		this.window_x = x;
		this.window_y = y;
		this.animation_x = x;
		this.animation_y = y;
		this.cost_x = x + 210;
		this.cost_y = y + 12;
		this.windowBitmap  = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.treeinfo);
		this.window_height = this.windowBitmap.getHeight();
		this.window_width = this.windowBitmap.getWidth();
		this.animationBitmap = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.attacksonlythree2);
		this.animation_height = this.animationBitmap.getHeight()/this.gods;
		this.animation_width = this.animationBitmap.getWidth()/this.attacks;
		this.costBitmap = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.bad1);
		this.cost_height = this.costBitmap.getHeight()/4;
		this.cost_width = this.costBitmap.getHeight()/4;
		this.god_number = god_number;
		this.attack_number = attack_number;
	}
	public void onDraw(Canvas canvas){
		update();
		/*
		 * wyswietlenie -a-n-i-m-o-w-a-n-e-g-o- (<- przekreslenie :/) ataku
		 */
        int srcX = this.attack_number * this.animation_width;
        int srcY = this.god_number * this.animation_height;
        Rect src = new Rect(srcX, srcY, srcX + this.animation_width, srcY + this.animation_height);
        Rect dst = new Rect(this.animation_x, this.animation_y, this.animation_x + this.animation_width, this.animation_y + this.animation_height);
        canvas.drawBitmap(this.animationBitmap, src, dst, null);
        /*
		 * wyswietlenie statycznego okna
		 */
        src = new Rect(0, 0, this.window_width, this.window_height);
        dst = new Rect(this.window_x, this.window_y, this.window_x + this.window_width, this.window_y + this.window_height);
        canvas.drawBitmap(this.windowBitmap, src, dst, null);
        /*
         * wyswietlanie kosztow 
         */
        	/*
        	 * 			DMG
        	 */
	        srcX = 0;
	        srcY = 0;
	        for(int i = 0; i<this.dmg; i++){
	        	src = new Rect(srcX + (i%2) * this.cost_width/2, srcY, srcX + this.cost_width - ((1+i)%2) * this.cost_width/2, srcY + this.cost_height);
	        	dst = new Rect(this.cost_x + i * this.cost_width/2, this.cost_y, this.cost_x + this.cost_width/2 + i * this.cost_width/2, this.cost_y + this.cost_height);
	        	canvas.drawBitmap(this.costBitmap, src, dst, null);
	        }
	        /*
        	 * 			RANGE
        	 */
	        srcY = this.cost_height;
	        for(int i = 0; i<this.range; i++){
	        	src = new Rect(srcX + (i%2) * this.cost_width/2, srcY, srcX + this.cost_width - ((1+i)%2) * this.cost_width/2, srcY + this.cost_height);
	        	dst = new Rect(this.cost_x + i * this.cost_width/2, this.cost_y + this.cost_height, this.cost_x + this.cost_width/2 + i * this.cost_width/2, this.cost_y + this.cost_height*2);
	        	canvas.drawBitmap(this.costBitmap, src, dst, null);
	        }
	        /*
        	 * 			AMBROSIA COST	
        	 */
	        srcY = this.cost_height * 2;
	        for(int i = 0; i<this.mana_cost; i++){
	        	src = new Rect(srcX + (i%2) * this.cost_width/2, srcY, srcX + this.cost_width - ((1+i)%2) * this.cost_width/2, srcY + this.cost_height);
	        	dst = new Rect(this.cost_x + i * this.cost_width/2, this.cost_y + this.cost_height*2, this.cost_x + this.cost_width/2 + i * this.cost_width/2, this.cost_y + this.cost_height*3);
	        	canvas.drawBitmap(this.costBitmap, src, dst, null);
	        }
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
	public void setDmg(int dmg){
		this.dmg = dmg;
	}
	public void setRange(int range){
		this.range = range;
	}
	public void setManaCost(int mana){
		this.mana_cost = mana;
	}
}
