package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

enum backgroundType{empty, background, zeus, hephaestus, poseidon,attack, upgrade,tree};

public class TreeButtons {
	private TreeView treeView;
    private Bitmap bmp;
    private int x;
    private int y;
    private int width;
    private int height;
    private int currentframe = 0;
    private boolean animated;
    private boolean enabled = true;
    private backgroundType bt = backgroundType.empty;
    private int frames;
    private Rect rec;
    private int godNumber; //numer aktualneg oboga - zmienna potrzebna do drzewka rozwoju
    private int gods = 3; //ile jest bogow - drzewek rozwoju narysowanych na bitmapie
    private int attacks = 5;
    private int attackNumber;
    private int sourceX;
    private int sourceY;
    private int mana_cost;
    private int range;
    private int dmg;
    private int upgrade;

    public TreeButtons(TreeView treeView,int x, int y, backgroundType bt) {
    	this.treeView = treeView;
		this.x = x;
		this.y = y;
		this.currentframe = 0;
		this.bt = bt;
		switch(bt){
		case tree:
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
			this.sourceX = this.width * 2;
			this.sourceY = 0;
			break;
		case hephaestus:
			this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.onlythreegods1);
			this.width = bmp.getWidth()/this.gods;
			this.height = bmp.getHeight();
			this.frames = 1;
			this.animated = false;
			this.sourceX = this.width;
			this.sourceY = 0;
			break;
		case background:
			this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.treebackground);
			this.width = bmp.getWidth();
			this.height = bmp.getHeight();
			this.animated = false;
			this.sourceX = 0;
			this.sourceY = 0;
		}
    }
	public TreeButtons(TreeView treeView,int x, int y, int attack_number, int god_number, int mana_cost, int dmg, int range) {
	    	this.treeView = treeView;
			this.x = x;
			this.y = y;
			this.currentframe = 0;
			this.attackNumber = attack_number;
			this.godNumber = god_number;
			this.bt = backgroundType.attack;
			this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.attackmini);
			this.width = bmp.getWidth()/this.attacks;
			this.height = bmp.getHeight()/this.gods;
			this.frames = 1;
			this.animated = false;
			this.rec = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
			this.mana_cost = mana_cost;
			this.dmg = dmg;
			this.range = range;
    }
	public TreeButtons(TreeView treeView,int x, int y, int upgrade) {
		this.treeView = treeView;
		this.x = x;
		this.y = y;
		this.bt = backgroundType.upgrade;
		this.bmp = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.treebuttons);
		this.width = bmp.getWidth()/7;
		this.height = bmp.getHeight();
		this.animated = false;
		this.rec = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		this.upgrade = upgrade;
	}
    public void onDraw(Canvas canvas) {
//    	if(this.animated){
//    		update();
//    	}
    	int srcX = this.sourceX;
    	int srcY = this.sourceY;
		if(this.animated){
			srcX = currentframe * this.width;
		}
		if(this.bt == backgroundType.tree){
			srcX = this.godNumber * this.width;
		}
		else if(this.bt == backgroundType.attack){
			srcX = this.width * this.attackNumber;
			srcY = this.height * this.godNumber;
		}
		else if(this.bt == backgroundType.upgrade){
			srcX = this.width * (this.upgrade+2);
			srcY = 0;
		}		
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		this.rec = dst;
		canvas.drawBitmap(this.bmp, src, dst, null);
//		if(this.animated){
//			this.currentframe++;
//		}
		if(this.bt == backgroundType.upgrade){
			if(this.upgrade + 2 >= 6){
				this.upgrade = 3;
			}
		}
	}
    
    private void update(){
    	if(this.currentframe < 0){
			this.currentframe = 0;
		}
		else if(this.currentframe > frames-1){
			this.currentframe = 0;
		}
    }
    
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	public boolean checkCollision(int x, int y){
		if(this.rec.contains(x,y) && this.enabled){
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
	public int getManaCost(){
		return this.mana_cost;
	}
	public int getRange(){
		return this.range;
	}
	public int getDmg(){
		return this.dmg;
	}
	public void collisionFriendly(boolean state){
		this.enabled = state;
	}
	public void setUpgrade(int upgrade){
		this.upgrade = upgrade;
	}
	public int getUpgrade(){
		return this.upgrade;
	}
}