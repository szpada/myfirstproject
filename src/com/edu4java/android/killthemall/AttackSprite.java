package com.edu4java.android.killthemall;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.Random;

enum element{explosion,constant,crazy,multi_explosion,shot};

/**
 * @author Maciej
 * animacje atakow - ich pozycja, stale parametry i chwilowe wartosci
 */

public class AttackSprite {
	
	private static int shock_mana = 10;
	private static int multi_shock_mana = 10;
	private static int charge_defence_mana = 20;
	private static int lightning_mana = 20;
	private static int thunder_mana = 20;
	private static int fireball_mana = 1;
	private static int firewall_mana = 2;
	private static int fireball_shot_mana = 1;
	private static int meteor_mana;
	private static int empty1_mana = 20;
	private static int waterSplash_mana = 20;
	private static int tornado_mana = 20;
	private static int water_level_mana = 20;
	private static int flood_mana = 20;
	private static int intarow_mana = 20;
	private static int spear_mana = 20;
	private static int shield_mana = 20;
	private static int trap_mana = 20;
	private static int poison_mana = 20;
	private static int consumption_mana = 20; 
	private static int corpse_explosion_mana = 20; 
	private static int pit_mana = 20;
	private static int death_touch_mana = 20;
	
	private GameView gameView;
	private Bitmap bmp;
	private attackType attp;
	private Rect rec; //dla zwyklych prostokatnych
	private int range; //dla wybuchow itd (wymagajacaych kola)
	
	private List<AttackSprite> attack;
	
	private int x_distance;	// <-
	private float degree;//dla atakow typu shot
	private int speed;		// <-
	
	private int dmg;
	private int slow;
	private int columns;
	private int rows;
	private int width;
	private int height;
	private int currentFrame = 0;
	private int frames;
	private int x;
	private int y;
	private int life;
	private int cooldown;
	private int lvl;
	private int manaCost;
	private element element;
	private boolean staticPosition; //czy ma latac po ekranie jak pojebane czy nie
	private boolean exploding; //ataki ktore dzialaja tylko podczas nacisniecia
								//false oznacza ze do konca istnienia sprite-a bedzie on zadawal obrazenia
	public AttackSprite(List<AttackSprite> attack,GameView gameView, attackType at, int lvl, int x, int y){
		Random rand = new Random();
		this.gameView = gameView;
		this.attack = attack;
		this.range = 0;
		this.rec = null;
		this.lvl = lvl;
		this.attp = at;
		this.x = x;
		this.y = y;
		switch(at){
		/*
		 * =======================================================
		 * 							OGIEN
		 *=======================================================
		 */	 
		case fireball:
			this.manaCost = firewall_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.explosion);
			this.columns = 4;
			this.rows = 4;
			this.width = bmp.getWidth()/this.rows;
			this.height = bmp.getHeight()/this.columns;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.lvl * 2 + 3*this.width/4;
			this.dmg = 8 + rand.nextInt(lvl);
			this.slow = 0;
			this.life = 15;
			this.cooldown = 900 - lvl * 100;
			this.exploding = true;
			this.staticPosition = true;
			this.element = element.explosion;
			break;
		case fireball_shot:
			
			//this.y_Destination = y;
			this.x = 240;
			this.y = 620;
			this.speed = 10;
			if(this.x >= x){
				this.x_distance = (this.x - x)/((this.y - y)/this.speed);
				this.degree = - (float)Math.toDegrees(Math.atan((float)(this.x - x)/((float)(this.y - y))));
			}
			else{
				this.x_distance = -(x - this.x)/((this.y - y)/this.speed);
				this.degree = (float)Math.toDegrees(Math.atan((float)(x - this.x)/((float)(this.y - y))));
			}
			this.manaCost = fireball_shot_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.fireballshot);
			this.columns = 1;
			this.rows = 4;
			this.width = bmp.getWidth()/this.rows;
			this.height = bmp.getHeight()/this.columns;
			this.rec = new Rect(this.x,this.y,this.x + this.width/2,this.y + this.height/2);//(this.x - this.width/4,this.y - this.height/4, this.x + this.width/4, this.y + this.height/4);
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.width;//this.lvl * 4 + 3*this.width/4;
			this.dmg = rand.nextInt(lvl * 5) + 20;
			this.slow = 0;
			this.life = 80;
			this.cooldown = 900 - lvl * 50;
			this.exploding = false;
			this.staticPosition = true;
			this.element = element.shot;
			break;
		case firewall:
			this.manaCost = firewall_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.firewall);
			this.columns = 4;
			this.rows = 1;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.rec = new Rect(this.x - this.width/4,this.y - this.height/4, this.x + this.width/4, this.y + this.height/4);
			this.range = 32;
			this.frames = (this.rows * this.columns) - 1;
			this.dmg = 1;
			this.slow = 3;
			this.life = 20 + this.lvl * 10;
			this.cooldown = 100;
			this.staticPosition = true;
			this.exploding = false;
			this.element = element.crazy;
			break;
		case meteor:
			this.manaCost = meteor_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.meteor);
			this.columns = 4;
			this.rows = 4;
			this.width = bmp.getWidth()/this.rows;
			this.height = bmp.getHeight()/this.columns;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.width/2;
			this.dmg = 50 + rand.nextInt(2 * lvl * lvl);
			this.slow = 0;
			this.life = 15;
			this.cooldown = 1500;
			this.exploding = false;
			this.staticPosition = true;
			this.element = element.explosion;
			break;
		/*
		 * =======================================================
		 * 						WODA
		 *=======================================================
		 */	 
		case waterSplash:
			this.manaCost = waterSplash_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.water_splash2);
			this.columns = 1;
			this.rows = 4;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.rec = new Rect(0,(this.y - this.height/4)+10-lvl, 480, (this.y + this.height/4)-10+lvl);
			this.frames = (this.rows * this.columns) - 1;
			this.dmg = rand.nextInt(lvl) + 1;
			this.slow = 3;
			this.life = this.lvl * 5;
			this.cooldown = 500 + lvl * 100;
			this.staticPosition = true;
			this.exploding = false;
			this.element = element.constant;
			break;
		/*
		 * =======================================================
		 * 						ELEKTRYCZNE
		 *=======================================================
		 */	 
		case shock:
			this.manaCost = shock_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.shock);
			this.columns = 4;
			this.rows = 4;
			this.width = bmp.getWidth()/this.rows;
			this.height = bmp.getHeight()/this.columns;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.lvl * 4 + 3*this.width/4;
			this.dmg = rand.nextInt(lvl + 2) + 1;
			this.slow = 0;
			this.life = 15;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = false;
			this.element = element.explosion;
			break;
		case multi_shock:
			this.manaCost = multi_shock_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.shock);
			this.columns = 4;
			this.rows = 4;
			this.width = bmp.getWidth()/this.rows;
			this.height = bmp.getHeight()/this.columns;
			this.frames = (this.rows * this.columns) - 1;
			this.range = 0;//this.lvl * 4 + 3*this.width/4;
			this.dmg = 0;
			this.slow = 0;
			this.life = 1;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = false;
			this.element = element.explosion;
			break;
		}
	}
	
	public void onDraw(Canvas canvas) {
        update();
        int srcX;
        int row;
        int srcY;
        Rect src;
        Rect dst;
        switch(this.element){
        case constant:
        	srcX = (currentFrame % this.columns) * this.width;
            srcY = currentFrame * this.height;
            src = new Rect(0, srcY, this.width, srcY + this.height);
            canvas.drawBitmap(this.bmp, src, this.rec, null);
        	break;
        case explosion:
        	srcX = (currentFrame % this.columns) * this.width;
            row = currentFrame / this.rows;
            srcY = row * this.height;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);
            canvas.drawBitmap(this.bmp, src, dst, null);
        	break;
        case crazy:
        	srcX = (currentFrame % this.columns) * this.width;
            //row = currentFrame / this.rows;
            srcY = 0;//row * this.height;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);
            canvas.drawBitmap(this.bmp, src, this.rec, null);
            break;
        case shot:
        	srcX = (currentFrame % this.rows) * this.width;
            srcY = 0;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);//(this.x,this.y,this.x + this.width, this.y + this.height);
            canvas.save();
            canvas.rotate(this.degree, this.x, this.y);
            canvas.drawBitmap(this.bmp, src, dst, null);
            canvas.restore();
            /*
             * 
             * SPRAWDZIC CZEMU TA KURWA SIE JEBIE
             * 
             * 		if(this.x < 0 || this.x > this.gameView.getWidth() || this.y < 0 || this.y > this.gameView.getHeight()){
 			   			attack.remove(this); 
            		}
            */
        	break;
        }
        currentFrame++;
   }
   private void update() {
	   if(!this.staticPosition){
		   Random rnd = new Random();
		   this.x = this.x + rnd.nextInt(11) - 5;
		   this.y = this.y + rnd.nextInt(11) - 5;
	   }
	   if(this.currentFrame > this.frames){
		   currentFrame = 0;
	   }
	   if(this.life < 1) {
		   attack.remove(this);
	   }
	   if(this.attp == attackType.multi_shock){
		   //POPRACOWAC NAD OPTYMALIZACJA (TNIE)
		   int distance = 50;
		   Random rand = new Random();
		   attack.add(new AttackSprite(attack,gameView,attackType.shock,1,x + rand.nextInt(distance), y + rand.nextInt(distance)));
		   //attack.add(new AttackSprite(attack,gameView,attackType.shock,1,x - rand.nextInt(distance), y - rand.nextInt(distance)));
		   //attack.add(new AttackSprite(attack,gameView,attackType.shock,1,x - rand.nextInt(distance), y + rand.nextInt(distance)));
		   //attack.add(new AttackSprite(attack,gameView,attackType.shock,1,x + rand.nextInt(distance), y - rand.nextInt(distance)));
	   }
	   if(this.attp == attackType.fireball_shot){
		   this.y += -this.speed;
		   this.x += -this.x_distance;
		   this.rec = new Rect(this.x-this.width/2,this.y - this.height/2,this.x + this.width/2,this.y + this.height/2);
	   }
	   life--;
   }
   public int checkCollision(Rect rect){
	   switch(this.element){
	   case explosion:
		   double distance = Math.pow(Math.pow((this.x - rect.centerX()),2) + Math.pow((this.y - rect.centerY()),2),0.5);
		   if(distance < this.range){
			   if(this.attp==attackType.meteor){
				   if(this.currentFrame < 8){
					   return 0;
				   }
				   else{
					   int damage = this.dmg / (this.currentFrame-7);
					   return damage;
				   }
			   }
			   if(distance/this.range < 0.2){
				   return this.dmg;
			   }
			   else if(distance/this.range < 0.5){
				   return this.dmg/2;
			   }
			   else{
				   return this.dmg/4;
			   }
		   }
		   return 0;
	   case constant:
	   case crazy:
		   Rect temp = new Rect(this.rec);
		   if(temp.intersect(rect)){
			   return 1;
		   }
		   return -1;
	   case shot:
		   distance = Math.pow(Math.pow((this.rec.centerX() - rect.centerX()),2) + Math.pow((this.rec.centerY() - rect.centerY()),2),0.5);
		   if(distance < this.range){
			   if(distance/this.range < 0.2){
				   return this.dmg;
			   }
			   else if(distance/this.range < 0.5){
				   return this.dmg/2;
			   }
			   else{
				   return this.dmg/4;
			   }
		   }
		   return 0;
	   }
	   return -1;
	   
   }
   /*
   public int checkCollision(int x, int y){
	   switch(this.element){
	   case explosion:
	   case multi_explosion:
		   double distance = Math.pow(Math.pow((this.x - x),2) + Math.pow((this.y - y),2),0.5);
		   if(distance < this.range){
			   if(distance/this.range < 0.2){
				   return this.dmg;
			   }
			   else if(distance/this.range < 0.5){
				   return this.dmg/2;
			   }
			   else{
				   return this.dmg/4;
			   }
		
		   }
		   return 0;
	   case constant:
		   if(this.rec.contains(x, y)){
			   return 1;
		   }
		   return -1;
	   }
	   return -1;
   }
   */
   public int getCoolDown(){
		return this.cooldown;
   }
   public void setPosition(int x, int y){
	   this.x = x;
	   this.y = y;
   }
   public int getDmg(){
	   return this.dmg;
   }
   public boolean getExploding(){
	   return this.exploding;
   }
   public int getManaCost(){
	   if(this.attp == attackType.multi_shock){
		   int mana = multi_shock_mana + 4 * shock_mana;
		   return mana;
	   }
	   else{
		   return this.manaCost;
	   }
   }
   public int getSlow(){
	   return this.slow;
   }
   public element getElement(){
	   return this.element;
   }
}
