package com.edu4java.android.killthemall;

import java.util.List;
//import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

enum size{small, medium, large};
enum state{walk,fight,die};
enum enemyType{knight,dragon};

public class EnemySprite {
       // direction = 0 up, 1 left, 2 down, 3 right,
       // animation = 3 back, 1 left, 0 front, 2 right
	
	private GameView gameView;
    private Bitmap bmp;
    private size sz;
    private state st;
    private List<EnemySprite> enemies;
    
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static int baseSize = 32; 
    private static int olympY = 600;
    private int frames = 3;
    
    private int maxSpeed;
    private int speed;
    private int attackSpeed;
    private int attackIncrement = 0;
    private int x = 0;
    private int y = 0;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int dmg;
    private int life;
    private int range;
    private boolean recentStateChange = false;
    private boolean dmgReady = false;
    
    //private resistance light_res;
    //private resistance fire_res;
    //private resistance water_res;
    //private resistance physical_res;
    //private resistance death_res;
    
    private boolean immute[] = new boolean[5];
    private boolean absorbs[] = new boolean[5];
    private int res[] = new int[5];
    /*		KOLEJNOSC :
     *	0) pioruny
     *	1) ogien
     *  2) woda
     *  3) fizyczny
     *  4) smierc
     */
    
    
    public EnemySprite(List<EnemySprite> enemies,GameView gameView, enemyType tp, Bitmap bmp, int x, int y){
    	this.enemies = enemies;
        this.gameView = gameView;
        this.bmp = bmp;
        this.x = x; 
        this.y = y;
        this.st = state.walk; 
        switch(tp){
    	case dragon:
    		this.sz = size.large; 
            this.attackSpeed = 10;
            this.maxSpeed = 4;
            this.speed = 4;
            this.dmg = 10;
            this.life = 1000;
            this.range = 5;
            for(int i=0; i<5; i++){
            	this.res[i] = 0;
            	this.immute[i] = false;
            	this.absorbs[i] = false;
            }
    		break;
	    case knight:
			this.sz = size.small; 
	        this.attackSpeed = 5;
	        this.maxSpeed = 2;
	        this.speed = 2;
	        this.dmg = 2;
	        this.life = 100;
	        this.range = 1;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
			break;
		}
        switch(this.sz){
     	   case small:
     		   this.width = baseSize;
     		   this.height = baseSize;
     		   break;
     	   case medium:
     		   this.width = baseSize * 2;
     		   this.height = baseSize * 2;
     		   break;
     	   case large:
     		   this.width = baseSize * 3;
     		   this.height = baseSize * 3;
     		   break;
        }
    }
    
    private void update() {
    	   if(this.life < 1){
    		   this.setSt(state.die);
    		   if(!this.recentStateChange){
    			   this.currentFrame = 0;
    			   this.recentStateChange = true;
    		   }
    	   }
    	   if(this.currentFrame > this.frames-1){
    		   if(this.recentStateChange && this.st == state.fight){
    			   this.recentStateChange = false;
    		   }
    		   this.currentFrame = 0;
    		   if(this.life < 1){
    			   enemies.remove(this);
    		   }
    	   }
    	   if(this.y + range >= olympY){
    		   this.st = state.fight;
    		   if(this.life < 1){
        		   this.setSt(state.die);
        		   if(!this.recentStateChange){
        			   this.currentFrame = 0;
        			   this.recentStateChange = true;
        		   }
        	   }
    		   if(!this.recentStateChange){
				   if(this.attackIncrement < 20 - this.attackSpeed){
					   this.attackIncrement++;
					   this.currentFrame = 0;
					   this.dmgReady = false;
				   }
				   else{
					   this.attackIncrement = 0;
					   this.recentStateChange = true;
					   if(this.currentFrame == this.frames/2){
						   this.dmgReady = true;
					   }
				   }
    		   }
		   }
    	   if(this.st == state.walk){
    		   this.y += this.speed;
    	   }
    }

    public void onDraw(Canvas canvas) {
    	Paint paint = new Paint();
		update();
		int srcX = this.width * this.currentFrame;
		int srcY = this.st.ordinal() * this.height;
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		canvas.drawBitmap(this.bmp, src, dst, null);
		canvas.drawText(Integer.toString(this.life), this.x + this.width/2, this.y, paint);
		this.currentFrame++;
    } 
    
    public int getX(){
    	return x;
    }
    
    public int getY(){
    	return y;
    }
    
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public state getSt() {
		return st;
	}

	public void setSt(state st) {
		this.st = st;
	}
	public void attackedWithDmg(int dmg, int power){
		if(this.absorbs[power]){
			if(dmg > 0){
				this.life += dmg;
			}
		}
		else if(this.immute[power]){
			//wysweitl ze nie zadalo dmg
		}
		else {
			if(this.res[power] < dmg){
				this.life += this.res[power] - dmg;
			}
		}
	}
	public int getLife(){
		return this.life;
	}
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	public Rect getRect(){
		Rect rect = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		return rect;
	}
	public boolean getDmgReady(){
		return this.dmgReady;
	}
}