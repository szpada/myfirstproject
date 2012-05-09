package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

enum backgroundType{empty, background, zeus, hephaestus, poseidon,attack};

public class Sprite {
       // direction = 0 up, 1 left, 2 down, 3 right,
       // animation = 3 back, 1 left, 0 front, 2 right
	private TreeView treeView;
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
    private String function;
    private backgroundType bt = backgroundType.empty;
    private int frames;
    private int basicY; //zmienna dla ambrozji zeby mozna bylo w samym gameView ustalkic wysokosc
    private Rect rec;
    private int godNumber; //numer aktualneg oboga - zmienna potrzebna do drzewka rozwoju
    private int gods = 3; //ile jest bogow - drzewek rozwoju narysowanych na bitmapie
    private int attacks = 5;
    private int attackNumber;
    private int sourceX;
    private int sourceY;

    public Sprite(GameView gameView,int x, int y, Bitmap bmp, String function,int MaxLife) {
    	this.gameView = gameView;
		this.x = x;
		this.y = y;
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.currentframe = 0;
		this.sourceX = 0;
		this.sourceY = 0;
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
    public Sprite(TreeView treeView,int x, int y, backgroundType bt) {
    	this.treeView = treeView;
		this.x = x;
		this.y = y;
		this.currentframe = 0;
		this.bt = bt;
		switch(bt){
		case background:
			this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.treemenu);
			this.width = bmp.getWidth()/this.gods;
			this.height = bmp.getHeight();
			this.frames = 1;
			this.animated = false;
			this.sourceX = 0;
			this.sourceY = 0;
			break;
		case zeus:
			this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.onlythreegods1);
			this.width = bmp.getWidth()/this.gods;
			this.height = bmp.getHeight();
			this.frames = 1;
			this.animated = false;
			this.sourceX = 0;
			this.sourceY = 0;
			break;
		case poseidon:
			this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.onlythreegods1);
			this.width = bmp.getWidth()/this.gods;
			this.height = bmp.getHeight();
			this.frames = 1;
			this.animated = false;
			this.sourceX = this.width;
			this.sourceY = 0;
			break;
		case hephaestus:
			this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.onlythreegods1);
			this.width = bmp.getWidth()/this.gods;
			this.height = bmp.getHeight();
			this.frames = 1;
			this.animated = false;
			this.sourceX = this.width * 2;
			this.sourceY = 0;
			break;
		}
    }
	public Sprite(TreeView treeView,int x, int y, int attack_number, int god_number) {
	    	this.treeView = treeView;
			this.x = x;
			this.y = y;
			this.currentframe = 0;
			this.attackNumber = attack_number;
			this.godNumber = god_number;
			this.bt = backgroundType.attack;
			this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.attacksonlythree2);
			this.width = bmp.getWidth()/this.attacks;
			this.height = bmp.getHeight()/this.gods;
			this.frames = 1;
			this.animated = false;
			this.rec = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
			
    }
      
    public void onDraw(Canvas canvas) {
    	if(this.animated){
    		update();
    	}
    	int srcX = this.sourceX;
    	int srcY = this.sourceY;
		if(this.animated){
			srcX = currentframe * this.width;
		}
		if(this.bt == backgroundType.background){
			srcX = this.godNumber * this.width;
		}
		else if(this.bt == backgroundType.attack){
			srcX = this.width * this.attackNumber;
			srcY = this.height * this.godNumber;
		}
		
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		this.rec = dst;
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
	public boolean checkCollision(int x, int y){
		if(this.rec.contains(x,y)){
			return true;
		}
		else{
			return false;
		}
	}
	public void setCurrentGod(int godNumber){
		this.godNumber = godNumber;
	}
	public int getGodNumber(){
		return this.godNumber;
	}
	public int getAttackNumber(){
		return this.attackNumber;
	}		
}