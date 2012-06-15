package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.gra.R;

enum size{small, medium, large, enormous};
enum state{walk,fight,die,summon,shoot};	//summon dla bossa i jednostek ktore sa "tworzone" przez inne. shoot tylko dla bosa
enum enemyType{knight,dragon, knight_general, balista, catapult, fire_imp, fire_titan, fire_boss};
enum warriorType{melee, summoner, range, general, boss, chapter_boss};

/**
 * @author Maciej
 * wrogi stworek ://
 * przechowuje jego stale parametry i chwilowe wartosci
 * kontrola animacji
 */
public class EnemySprite implements Serializable { 
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
    
    private enemyType thisType;
    private enemyType summonType;//jednostka jaka summoner moze przywolac
    private enemyAttackType ammoType; //pociski jednostek typu range
    private int ammoSpeed; //predkosc pocisku wroga
    private state next_state; //nastepny atak bossa
    private int melee_range; //zasieg wrecz dla bossow
    
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
    
    private int repair_drop = 0;	//0 - 50  % na wypadniecie naprawy
    private int mana_drop = 0;		//0 - 50  % na wypadniecie ambrosji
    //NIE DAWAC WIECEJ NIZ 50% - bo do losowania dodawany jest luck gracza
    //(no chyba ze chcesz zeby zawsze dropowal wtedy daj 100 i styka)
    
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
        this.thisType = tp;
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
	        this.life = 250;
	        this.maxLife = 250;
	        this.range = 20;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 4;
	        this.wt = warriorType.melee;
	        this.summonType = enemyType.fire_imp;
	        this.repair_drop = 10;
	        this.mana_drop = 15;
			break;
	    case knight:
	    	this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.bad1);
			this.sz = size.small;
			this.width = 32;
  		   	this.height = 32;
	        this.attackSpeed = 30;
	        this.maxSpeed = 4;
	        this.speed = 4;
	        this.dmg = 1;
	        this.life = 10;
	        this.maxLife = 10;
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
	        this.maxSpeed = 3;
	        this.speed = 3;
	        this.dmg = 0;
	        this.life = 20;
	        this.maxLife = 20;
	        this.range = rnd.nextInt(100) + 300;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 3;
	        this.wt = warriorType.general;
	        this.summonType = enemyType.knight;
	        this.mana_drop = 10;
			break;
	    case balista:
	    	this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.balista);
			this.sz = size.small;
			this.width = 64;
		   	this.height = 64;
	        this.attackSpeed = 40;
	        this.maxSpeed = 2;
	        this.speed = 2;
	        this.dmg = 0;
	        this.life = 100;
	        this.maxLife = 100;
	        this.range = 450;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 4;
	        this.wt = warriorType.range;
	        this.ammoType = enemyAttackType.spear;
	        this.ammoSpeed = 6;
	        this.repair_drop = 15;
			break;
	    case catapult:
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.catapult);
			this.sz = size.small;
			this.width = 64;
		   	this.height = 64;
	        this.attackSpeed = 20;
	        this.maxSpeed = 1;
	        this.speed = 1;
	        this.dmg = 0;
	        this.life = 200;
	        this.maxLife = 200;
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
	        this.repair_drop = 25;
			break;
	    case fire_imp:
	    	this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.bad2);
			this.sz = size.small;
			this.width = 32;
  		   	this.height = 32;
	        this.attackSpeed = 30;
	        this.maxSpeed = 6;
	        this.speed = 6;
	        this.dmg = 10;
	        this.life = 10;
	        this.maxLife = 10;
	        this.range = 4;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.absorbs[1] = true;
	        this.frames = 3;
	        this.wt = warriorType.melee;
	        this.st = state.summon;
			break;
	    case fire_titan:
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.firetitan);
			this.sz = size.small;
			this.width = 128;
  		   	this.height = 128;
	        this.attackSpeed = 20;
	        this.maxSpeed = 2;
	        this.speed = 2;
	        this.dmg = 10;
	        this.life = 500;
	        this.maxLife = 500;
	        this.range = 400;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 0;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.frames = 4;
	        this.wt = warriorType.boss;
	        this.summonType = enemyType.fire_imp;
	        this.next_state = state.shoot;
	        this.melee_range = 20;
	        this.mana_drop = 40;
	        this.repair_drop = 10;
	        break;
	    case fire_boss:
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.chapterbossfire);
			this.sz = size.small;
			this.width = 128;
  		   	this.height = 128;
	        this.attackSpeed = 50;
	        this.maxSpeed = 2;
	        this.speed = 2;
	        this.dmg = 10;
	        this.life = 2000;
	        this.maxLife = 2000;
	        this.range = 20;
	        for(int i=0; i<5; i++){
	        	this.res[i] = 5;
	        	this.immute[i] = false;
	        	this.absorbs[i] = false;
	        }
	        this.absorbs[1] = true;
	        this.frames = 4;
	        this.wt = warriorType.chapter_boss;
	        this.summonType = enemyType.fire_imp;
	        this.next_state = state.shoot;
	        this.melee_range = 20;
	        break;
		}
    }
    
    public GameView getGameView() {
		return gameView;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}

	public size getSz() {
		return sz;
	}

	public void setSz(size sz) {
		this.sz = sz;
	}

	public warriorType getWt() {
		return wt;
	}

	public void setWt(warriorType wt) {
		this.wt = wt;
	}

	public List<EnemySprite> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<EnemySprite> enemies) {
		this.enemies = enemies;
	}

	public List<EnemyAttack> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<EnemyAttack> attacks) {
		this.attacks = attacks;
	}

	public static int getOlympY() {
		return olympY;
	}

	public static void setOlympY(int olympY) {
		EnemySprite.olympY = olympY;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public enemyType getThisType() {
		return thisType;
	}

	public void setThisType(enemyType thisType) {
		this.thisType = thisType;
	}

	public enemyType getSummonType() {
		return summonType;
	}

	public void setSummonType(enemyType summonType) {
		this.summonType = summonType;
	}

	public state getNext_state() {
		return next_state;
	}

	public void setNext_state(state next_state) {
		this.next_state = next_state;
	}

	public int getMelee_range() {
		return melee_range;
	}

	public void setMelee_range(int melee_range) {
		this.melee_range = melee_range;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAttackIncrement() {
		return attackIncrement;
	}

	public void setAttackIncrement(int attackIncrement) {
		this.attackIncrement = attackIncrement;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public int getSlowTimes() {
		return slowTimes;
	}

	public void setSlowTimes(int slowTimes) {
		this.slowTimes = slowTimes;
	}

	public long getTimer() {
		return timer;
	}

	public void setTimer(long timer) {
		this.timer = timer;
	}

	public boolean isSlowed() {
		return slowed;
	}

	public void setSlowed(boolean slowed) {
		this.slowed = slowed;
	}

	public boolean isRecentStateChange() {
		return recentStateChange;
	}

	public void setRecentStateChange(boolean recentStateChange) {
		this.recentStateChange = recentStateChange;
	}

	public boolean[] getImmute() {
		return immute;
	}

	public void setImmute(boolean[] immute) {
		this.immute = immute;
	}

	public boolean[] getAbsorbs() {
		return absorbs;
	}

	public void setAbsorbs(boolean[] absorbs) {
		this.absorbs = absorbs;
	}

	public int[] getRes() {
		return res;
	}

	public void setRes(int[] res) {
		this.res = res;
	}

	public void setAmmoType(enemyAttackType ammoType) {
		this.ammoType = ammoType;
	}

	public void setAmmoSpeed(int ammoSpeed) {
		this.ammoSpeed = ammoSpeed;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setDmgReady(boolean dmgReady) {
		this.dmgReady = dmgReady;
	}

	public void update() {
    	if(System.currentTimeMillis() - this.timer < this.slowTimes){
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
	    /*
	     * burdel odpowiadajacy za atak wroga
	     */
	    if(this.currentFrame > this.frames-1){
	    	if(this.recentStateChange && this.st != state.walk && this.st != state.die ){
	    		this.recentStateChange = false;
	    	}
	    	this.currentFrame = 0;
	    }
	    if(this.y + this.height + range >= olympY){
	    	this.st = state.fight;
	    	if(this.wt == warriorType.boss){
	    		this.st = this.next_state;
	    	}
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
	    					Random rnd = new Random();
	    					this.attacks.add(new EnemyAttack(this.attacks, this.gameView, this.x + this.width/2, this.y + this.height/2, rnd.nextInt(400) + 40, 700, 10 , this.ammoType));
	    					break;
	    				case summoner:
	    					rand = new Random();
	    					this.enemies.add(new EnemySprite(this.enemies, this.gameView,this.summonType,rand.nextInt(400) + 40, this.y,this.attacks));
	    					this.st = state.walk;
	    					if(this.range <= 100){
	    						this.range = 100;
	    					}
	    					else{
	    						this.range = 3 * this.range / 4;
	    					}
	    					break;
	    				case boss:
	    					if(this.range <= this.melee_range){
	    						this.range = this.melee_range;
	    						this.st = state.fight;
	    						this.wt = warriorType.melee;
	    					}
	    					else{
	    						this.range = 3 * this.range / 4;
	    					}
	    					rand = new Random();
	    					if(this.st == state.summon){
	    						this.enemies.add(new EnemySprite(this.enemies, this.gameView,this.summonType,rand.nextInt(400) + 40, this.y,this.attacks));
	    					}
	    					else if(this.st == state.shoot){
	    						this.attacks.add(new EnemyAttack(this.attacks, this.gameView, this.x + this.width/2, this.y + this.height/2, rand.nextInt(400) + 40, 700, 10 , enemyAttackType.spear));
	    					}
	    					this.st = state.walk;
	    					//rand = new Random();
	    					if(rand.nextBoolean()){
	    						this.next_state = state.summon;
	    					}
	    					else{
	    						this.next_state = state.shoot;
	    					}
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
    	else if(this.st == state.summon && this.wt != warriorType.boss){
    		int slowFrame = this.currentFrame / 10;
    		//update();
			int srcX = this.width * slowFrame;
			int srcY = this.st.ordinal() * this.height;
			Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
			Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
			canvas.drawBitmap(this.bmp, src, dst, null);
			if(slowFrame >= this.frames-1){
				this.st = state.walk;
			}
			if(this.life < 1){
				this.st = state.die;
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
			
			if(this.life > this.maxLife){
				paint.setColor(Color.WHITE);
				canvas.drawRect(getX(),getY() - 10,getX() + getWidth(), getY() - 5, paint);
				//canvas.drawRect(this.x + this.width/2 - (int)((double)this.life/(double)this.maxLife * (double)this.width)/2, this.y - 10, this.x + this.width/2 + (int)((double)this.life/(double)this.maxLife * (double)this.width)/2, this.y - 5, paint);
			}
			/*
			 * wersja ze zwerzajacym sie paskiem zycia do srodka
			 */
			if(this.maxLife > this.life && this.life >= 0){
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
				canvas.drawRect(this.x + this.width/2 - (int)((double)this.life/(double)this.maxLife * (double)this.width)/2, this.y - 10, this.x + this.width/2 + (int)((double)this.life/(double)this.maxLife * (double)this.width)/2, this.y - 5, paint);
			}
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
	public Unit getThisAsUnit(){
	 	   return new Unit(this.thisType,this.x,this.y,this.life,this.slowTimes,this.st,this.range,this.currentFrame);
    }
	public void setAll(int x,int y,int life, int slow_times,state st,int range, int currentFrame){
		this.x = x;
		this.y = y;
		this.life = life;
		this.slowTimes = slow_times;
		this.st = st;
		this.range = range;
		this.currentFrame = currentFrame;
	}
	public int getSize(){
		return (this.width + this.height)/2;
	}
	public int getManaDrop(){
		return this.mana_drop;
	}
	public int getRepairDrop(){
		return this.repair_drop;
	}
}