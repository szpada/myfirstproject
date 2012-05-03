package com.edu4java.android.killthemall;

import java.util.List;
import java.util.Random;
//import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

enum size{small, medium, large, enormous};
enum state{walk,fight,die};
enum enemyType{knight,dragon, knight_general, balista, catapult};
enum warriorType{melee, summoner, range, general};

/**
 * @author Maciej
 * wrogi stworek ://
 * przechowuje jego stale parametry i chwilowe wartosci
 * kontrola animacji
 */
public class EnemySprite { 
       // direction = 0 up, 1 left, 2 down, 3 right,
       // animation = 3 back, 1 left, 0 front, 2 right
	
	private GameView gameView;
    private Bitmap bmp;
    private size sz;
    private state st;
    private warriorType wt;
    private List<EnemySprite> enemies;
    private List<EnemyAttack> attacks;
    
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    //private static int baseSize = 32; 
    private static int olympY = 600;
    private int frames;
    
    private enemyType summonType;//jednostka jaka summoner moze przywolac
    private enemyAttackType ammoType; //pociski jednostek typu range
    private int ammoSpeed; //predkosc pocisku wroga
    
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
    private int maxLife; //do paska zycia
    private int range;
    private int slowTimes = 0;
    
    private long timer = 0; //dla slow downa itd
    private boolean slowed = false; //do wysweitlania komunikatow o spowolnieniu
    private boolean recentStateChange = false;
    private boolean dmgReady = false;
    
    
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
    
    /*
     * 100 - Atak speed -> czestosc ataku
     */
    
    public EnemySprite(List<EnemySprite> enemies,GameView gameView, enemyType tp, int x, int y, List<EnemyAttack> ea){
    	this.enemies = enemies;
    	this.attacks = ea;
        this.gameView = gameView;
        this.x = x; 
        this.y = y;
        this.st = state.walk; 
        switch(tp){
    	case dragon:
    		this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.psismok);
			this.sz = size.small;
			this.width = 96;
  		   	this.height = 96;
	        this.attackSpeed = 20;
	        this.maxSpeed = 2;
	        this.speed = 2;
	        this.dmg = 10;
	        this.life = 500;
	        this.maxLife = 500;
	        this.range = 40;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 4;
	        this.wt = warriorType.melee;
			break;
	    case knight:
	    	this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.bad1);
			this.sz = size.small;
			this.width = 32;
  		   	this.height = 32;
	        this.attackSpeed = 30;
	        this.maxSpeed = 3;
	        this.speed = 3;
	        this.dmg = 10;
	        this.life = 100;
	        this.maxLife = 100;
	        this.range = 4;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 3;
	        this.wt = warriorType.melee;
			break;
	    case knight_general:
	    	Random rnd = new Random();
	    	this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.bad2);
			this.sz = size.small;
			this.width = 32;
		   	this.height = 32;
	        this.attackSpeed = 15;
	        this.maxSpeed = 2;
	        this.speed = 2;
	        this.dmg = 0;
	        this.life = 100;
	        this.maxLife = 100;
	        this.range = rnd.nextInt(100) + 600;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 3;
	        this.wt = warriorType.general;
	        this.summonType = enemyType.knight;
			break;
	    case balista:
	    	this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.catapult);
			this.sz = size.small;
			this.width = 64;
		   	this.height = 64;
	        this.attackSpeed = 15;
	        this.maxSpeed = 3;
	        this.speed = 3;
	        this.dmg = 0;
	        this.life = 500;
	        this.maxLife = 500;
	        this.range = 300;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 4;
	        this.wt = warriorType.range;
	        this.ammoType = enemyAttackType.spear;
	        this.ammoSpeed = 2;
			break;
	    case catapult:
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.catapult);
			this.sz = size.small;
			this.width = 64;
		   	this.height = 64;
	        this.attackSpeed = 20;
	        this.maxSpeed = 3;
	        this.speed = 3;
	        this.dmg = 0;
	        this.life = 500;
	        this.maxLife = 500;
	        this.range = 500;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 4;
	        this.wt = warriorType.range;
	        this.ammoType = enemyAttackType.catapult_stone;
	        this.ammoSpeed = 5;
			break;
		}
    }
    
    private void update() {
    	if(System.currentTimeMillis() - this.timer < this.slowTimes){
//    		if(System.currentTimeMillis() - this.timer < this.slowTimes/4){
//    			//this.slowed = false;
//    		}
    		this.speed = 1;
    	}
    	else{
    		this.slowed = false;
    		this.speed = maxSpeed;
    	}
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
	    }
	    if(this.y + this.height + range >= olympY){
	    	this.st = state.fight;
	    	if(this.life < 1){
	    		this.setSt(state.die);
	    		if(!this.recentStateChange){
	    			this.currentFrame = 0;
	    			this.recentStateChange = true;
	    		}
	    	}
	    	if(!this.recentStateChange){
	    		int real_attack_speed = this.attackSpeed * this.speed/2;
	    		if(real_attack_speed >= 100){
	    			real_attack_speed = 99;
	    		}
	    		if(this.attackIncrement < 100 - real_attack_speed){
	    			this.attackIncrement++;
	    			this.currentFrame = 0;
	    			this.dmgReady = false;
	    		}
	    		else{
	    			this.attackIncrement = 0;
	    			this.recentStateChange = true;
	    			if(this.currentFrame >= (this.frames-1)/2){
	    				/*
	    				 * ataki w zaleznosci od typu wojownika
	    				 */
	    				Log.d("enemy", " jestem w switchu ");
	    				switch(this.wt){
	    				case melee:
	    					this.dmgReady = true;
	    					break;
	    				case general:
	    					Random rand = new Random();
	    					this.enemies.add(new EnemySprite(this.enemies, this.gameView,this.summonType,rand.nextInt(400) + 40,0,this.attacks));
	    					this.enemies.add(new EnemySprite(this.enemies, this.gameView,this.summonType,rand.nextInt(400) + 40,0,this.attacks));
	    					this.enemies.add(new EnemySprite(this.enemies, this.gameView,this.summonType,rand.nextInt(400) + 40,0,this.attacks));
	    					this.enemies.add(new EnemySprite(this.enemies, this.gameView,this.summonType,rand.nextInt(400) + 40,0,this.attacks));
	    					this.st = state.walk;
	    					if(this.range <= 100){
	    						this.range = 100;
	    					}
	    					else{
	    						this.range = 3 * this.range / 4;
	    					}
	    					break;
	    				case range:
	    					//this.dmgReady = true;
	    					Log.d("range", "- " + this.dmgReady);
	    					Random rnd = new Random();
	    					/*
	    					 * CZEMU TA KURWA NIE DZIALA?
	    					 */
//	    					this.attacks.add(new EnemyAttack(this.attacks, this.gameView, 340, 0, 140, 700, 1, enemyAttackType.spear));
	    					this.attacks.add(new EnemyAttack(this.attacks, this.gameView, this.x + this.width/2, this.y + this.height/2, rnd.nextInt(400) + 40, 600, 1 , enemyAttackType.spear));
	    					//this.attacks.add(new EnemyAttack(this.attacks, this.gameView, this.x, this.y, rnd.nextInt(400) + 40, 600, 1 , this.ammoType));
	    					break;
	    				}
	    				
	    			}
	    		}
	    	}
	    }
	    if(this.st == state.walk){
	    	this.y += this.speed;
	    }
    }
    
    public void onDraw(Canvas canvas) {
    	if(this.st == state.die){
    		int slowFrame = this.currentFrame / 10;
    		//update();
			int srcX = this.width * slowFrame;
			int srcY = this.st.ordinal() * this.height;
			Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
			Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
			canvas.drawBitmap(this.bmp, src, dst, null);
			if(slowFrame >= this.frames){
				enemies.remove(this);
			}
    	}
    	else{
	    	Paint paint = new Paint();
			update();
			int srcX = this.width * this.currentFrame;
			int srcY = this.st.ordinal() * this.height;
			Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
			Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
			canvas.drawBitmap(this.bmp, src, dst, null);
			/*
			 * Pasek zycia
			 */
			if((double)this.life/(double)this.maxLife > 0.66 ){
				paint.setColor(Color.GREEN);
			}
			else if((double)this.life/(double)this.maxLife > 0.33 ){
				paint.setColor(Color.YELLOW);
			}
			else{
				paint.setColor(Color.RED);
			}
			/*
			 * wersja ze zwerzajacym sie paskiem zycia do srodka
			 */
			canvas.drawRect(this.x + this.width/2 - (int)((double)this.life/(double)this.maxLife * (double)this.width)/2, this.y - 10, this.x + this.width/2 + (int)((double)this.life/(double)this.maxLife * (double)this.width)/2, this.y - 5, paint);
			/*
			 * wersja z paskiem znikajacym w lewo (standardowy)
			 */
			//canvas.drawRect(this.x, this.x + (int)((double)this.life/(double)this.maxLife * (double)this.width), this.y - 5, paint);
			/*
			 * wyswietlnia komunikatu o spowolnieniu przerobic to pozniej na 
			 * bitmape rozplywajaca sie w powietrzu (animacja od srodka postaci az do paska zycia
			 */
			if(this.slowed){
				paint.setColor(Color.BLUE);
				canvas.drawText("Slowed", this.x + this.width/3, this.y - this.height/2, paint);
			}
    	}
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
	public void setSlowTime(int slowedTimes_100){
		this.slowed = true;
		this.timer = System.currentTimeMillis();
		this.slowTimes = slowedTimes_100 * 100;
	}
	public warriorType getWarriorType(){
		return this.wt;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setX(int x){
		this.x = x;
	}
	public double getDistance(EnemySprite es){
		return Math.pow(Math.pow((this.x - es.getX()),2) + Math.pow((this.y - es.getY()),2),0.5);
	}
	public int getAmmoSpeed(){
		return this.ammoSpeed;
	}
	public enemyAttackType getAmmoType(){
		return this.ammoType;
	}
	public void dmgReady(boolean state){
		this.dmgReady = state;
	}
}