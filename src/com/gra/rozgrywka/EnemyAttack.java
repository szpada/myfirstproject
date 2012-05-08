package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

enum enemyAttackType{spear,catapult_stone};

enum attackState{fly,die};

public class EnemyAttack implements Serializable {
	private int x;
	private int y;
	private int currentX;
	private int currentY;
	private int x_destination;
	private int y_destination;
	private int speed;//predkosc to 100 - to co podajemy w konstruktorze
	private int columns;
	private int rows;
	private int width;
	private int height;
	private int frames;
	private int dmg;
	private int life;
	private int currentFrame = 0;
	private float degree;
	
	private attackState as;
	private Bitmap bmp;
	private GameView gameView;
	private List<EnemyAttack> enemyAttacks;
	private enemyAttackType ea;
	
	public EnemyAttack(List<EnemyAttack> enemyAttacks, GameView gameView, int x, int y, int x_destination, int y_destination, int speed, enemyAttackType enemyType){
		this.gameView = gameView;
		this.enemyAttacks = enemyAttacks;
		this.x = x;
		this.y = y;
		this.currentX = x;
		this.currentY = y;
		this.x_destination = x_destination;
		this.y_destination = y_destination;
		this.speed = 100 - speed;
		this.ea = enemyType;
		switch(ea){
		case spear:
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), com.gra.R.drawable.catapultammo);
			this.columns = 3;
			this.rows = 2;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.dmg = 20;
			this.life = 10;
			this.as = attackState.fly;
			if(this.x >= this.x_destination){
				this.degree = -(float)Math.toDegrees(Math.atan((float)(this.x_destination - this.x)/((float)(this.y_destination - this.y))));
			}
			else{
				this.degree = (float)Math.toDegrees(Math.atan((float)(this.x - this.x_destination)/((float)(this.y_destination - this.y))));
			}
			break;
		case catapult_stone:
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), com.gra.R.drawable.catapultammo);
			this.columns = 3;
			this.rows = 2;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.dmg = 40;
			this.life = 20;
			this.dmg = 1;
			this.life = 1;
			this.as = attackState.fly;
			if(this.x >= this.x_destination){
				this.degree = -(float)Math.toDegrees(Math.atan((float)(this.x_destination - this.x)/((float)(this.y_destination - this.y))));
			}
			else{
				this.degree = (float)Math.toDegrees(Math.atan((float)(this.x - this.x_destination)/((float)(this.y_destination - this.y))));
			}
			break;
		}
	}
	public void onDraw(Canvas canvas){
		int srcX;
        int srcY;
        Rect src;
        Rect dst;
        if(this.as == attackState.die){
        	int slowedFrame = currentFrame/5;
        	switch(this.ea){
            case spear:
            	srcX = (slowedFrame % this.columns) * this.width;
                srcY = this.as.ordinal() * this.height;
                src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
                dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);//(this.x,this.y,this.x + this.width, this.y + this.height);
                canvas.save();
                canvas.rotate(this.degree, this.x, this.y);
                canvas.drawBitmap(this.bmp, src, dst, null);
                canvas.restore();
            	break;
	        case catapult_stone:
	        	srcX = (slowedFrame % this.columns) * this.width;
	            srcY = this.as.ordinal() * this.height;
	            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
	            dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);//(this.x,this.y,this.x + this.width, this.y + this.height);
	            canvas.save();
	            canvas.rotate(this.degree, this.x, this.y);
	            canvas.drawBitmap(this.bmp, src, dst, null);
	            canvas.restore();
	        	break;
	        }
        }
        else{
        	update();
	        switch(this.ea){
	        case spear:
	        	srcX = (currentFrame % this.columns) * this.width;
	            srcY = this.as.ordinal() * this.height;
	            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
	            dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);//(this.x,this.y,this.x + this.width, this.y + this.height);
	            canvas.save();
	            canvas.rotate(this.degree, this.x, this.y);
	            canvas.drawBitmap(this.bmp, src, dst, null);
	            canvas.restore();
	        	break;
	        case catapult_stone:
	        	srcX = (currentFrame % this.columns) * this.width;
	            srcY = this.as.ordinal() * this.height;
	            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
	            dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);//(this.x,this.y,this.x + this.width, this.y + this.height);
	            canvas.save();
	            canvas.rotate(this.degree, this.x, this.y);
	            canvas.drawBitmap(this.bmp, src, dst, null);
	            canvas.restore();
	        	break;
	        }
        }
        if(this.as == attackState.die && this.currentFrame >= this.frames){
        	enemyAttacks.remove(this);
        }
        this.currentFrame++;
	}
    public void update(){	
    	if(this.currentFrame > this.frames){
 		   currentFrame = 0;
 	   	}
 	   	if(this.life < 1) {
 		   enemyAttacks.remove(this);
 	   	}
// 	   	if(this.ea == enemyAttackType.spear){
// 	   		this.y += (this.y_destination - this.currentY)/this.speed;
// 	   		this.x += (this.x_destination - this.currentX)/this.speed;
// 	   		//this.rec = new Rect(this.x-this.width/2,this.y - this.height/2,this.x + this.width/2,this.y + this.height/2);
// 	   	}
// 	    if(this.ea == enemyAttackType.catapult_stone){
//	   		this.y += (this.y_destination - this.currentY)/this.speed;
//	   		this.x += (this.x_destination - this.currentX)/this.speed;
	   		//this.rec = new Rect(this.x-this.width/2,this.y - this.height/2,this.x + this.width/2,this.y + this.height/2);
//	   	}
 	   	this.y += (this.y_destination - this.currentY)/this.speed;
   		this.x += (this.x_destination - this.currentX)/this.speed;
 	    if(this.life < 1){
		    this.as = attackState.die;
			this.currentFrame = 0;		
	    }
    }
    public void attackedWithDmg(int dmg){
		this.life -= dmg;
	}
    public Rect getRect(){
		Rect rect = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		return rect;
	}
    public attackState getAttackState(){
    	return this.as;
    }
    public int getDmg(){
    	return this.dmg;
    }
    public int getX(){
    	return this.x;
    }
    public int getY(){
    	return this.y;
    }
    public void setState(attackState ats){
    	this.as = ats;
    }
    public Unit getThisAsUnit(){
 	   return new Unit(this.ea,this.x,this.y,this.life,this.as,this.currentFrame,this.degree,this.x_destination, this.y_destination, this.speed);
    }
    public void setAll(int life, attackState state, int currentFrame, float degree){
		this.life = life;
		this.as= state;
		this.currentFrame = currentFrame;
		this.degree = degree;
    }
}
