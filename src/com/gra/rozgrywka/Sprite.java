package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Rect;

//enum backgroundType{empty, background, zeus, hephaestus, poseidon,attack, upgrade,tree};

public class Sprite {
       // direction = 0 up, 1 left, 2 down, 3 right,
       // animation = 3 back, 1 left, 0 front, 2 right
	//private TreeView treeView;
	private GameView gameView;
    private Bitmap bmp;
    private int x;
    private int y;
    private int width;
    private int height;
    private int life;
    private int currentLife;
    private int currentframe = 0;
    private boolean animated;
    //private boolean enabled = true;
    private String function;
    private backgroundType bt = backgroundType.empty;
    private int frames;
    private int basicY; //zmienna dla ambrozji zeby mozna bylo w samym gameView ustalkic wysokosc

    public Sprite(GameView gameView,int x, int y, Bitmap bmp, String function,int MaxLife) {
    	this.gameView = gameView;
		this.x = x;
		this.y = y;
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.currentframe = 0;
		if(function.equalsIgnoreCase("olymp")){
			this.frames = 1;
			this.function = "olymp";
			this.animated = true;
			this.life = MaxLife;
			this.currentLife = MaxLife;
			this.width = bmp.getWidth()/this.frames;
		}
		if(function.equalsIgnoreCase("ambrosia")){
			this.basicY = this.y;
			this.frames = 4;
			this.function = "ambrosia";
			this.animated = true;
			this.life = MaxLife;
			this.currentLife = MaxLife;
			this.width = bmp.getWidth()/this.frames;
		}
	    else{
	    	this.animated = false;
	    }
	}
    public void onDraw(Canvas canvas) {
    	if(this.animated){
    		update();
    	}
    	int srcX = 0;
    	int srcY = 0;
		if(this.animated){
			srcX = currentframe * this.width;
		}
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		canvas.drawBitmap(this.bmp, src, dst, null);
		if(this.animated){
			this.currentframe++;
		}
	}
    
    private void update(){
    	if(this.currentframe < 0){
			this.currentframe = 0;
		}
		else if(this.currentframe > frames-1){
			this.currentframe = 0;
		}
    	if(this.function.equalsIgnoreCase("ambrosia")){
			this.y = this.basicY + 120 + (-110 * (this.currentLife)/this.life);
		}
    }
    
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	public void updateStats(int life){
		this.currentLife = life;
	}
}