package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.gra.R;

enum element{explosion,constant,crazy,shot,shield,whip,powerGainer, angle_whip, storm};

/**
 * @author Maciej
 * animacje atakow - ich pozycja, stale parametry i chwilowe wartosci
 */

public class AttackSprite implements Serializable {
	
	private static int Thunder_position_X = 240;	//zmienne potrzebne dla thunder ataku
	private static int Thunder_position_Y = 580;
	private int realX;
	private int realY;
	
	private int unit_under; //zmienna do storma (czy ktos jest pod nim)
	private int dir; //zmienna przechowujaca kierunek
	/*
	 * 0	- gora
	 * 1	- dol
	 * 2	- lewo
	 * 3	- prawo
	 * 
	 * 4 =<	- poprzedni
	 */
	
	private static int shock_mana = 5;
	private static int multi_shock_mana = 30;
	private static int charge_defence_mana = 20;
	private static int storm_mana = 20;
	private static int thunder_mana = 10;
	private static int fireball_mana = 5;
	private static int firewall_mana = 10;
	private static int fireball_shot_mana = 20;
	private static int meteor_mana = 60;
	private static int empty1_mana = 20;
	private static int waterSplash_mana = 20;
	private static int tornado_mana = 20;
	private static int water_level_mana = 20;
	private static int water_shield = 20;
	private static int arow_mana = 20;
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
	
	private double absorbRate; //potrzebne do shielda
	private int maxLife; //potrzbene do shielda
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
	private int power; //do ataku typu atak gainer
	private boolean staticPosition; //czy ma latac po ekranie jak pojebane czy nie
	private boolean exploding; //ataki ktore dzialaja tylko podczas nacisniecia
								//false oznacza ze do konca istnienia sprite-a bedzie on zadawal obrazenia
	
	/*
	 * konstruktor atakow niestandardowych
	 */
	public AttackSprite(List<AttackSprite> attack,GameView gameView, otherAttacks at, int lvl, int x, int y,boolean manaFree){
		Random rand = new Random();
		this.gameView = gameView;
		this.attack = attack;
		this.range = 0;
		this.rec = null;
		this.lvl = lvl;
		this.attp = attackType.empty1;
		this.x = x;
		this.y = y;
		switch(at){
		case chargeShieldAttack:
			if(manaFree){
				this.manaCost = 0;
			}
			else{
				this.manaCost = shock_mana;
			}
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.shieldchargeattack);
			this.columns = 4;
			this.rows = 1;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.range = 16;
			this.dmg = 1;
			this.slow = 0;
			this.life = 15;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = true;
			this.element = element.whip;
			break;
		case thunder_shot:
			this.x = 240;
			this.y = 620;
			this.realX = x;
			this.realY = y;
			Random rnd = new Random();
			if(manaFree){
				this.manaCost = 0;
			}
			else{
				this.manaCost = thunder_mana;
			}
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.thundershot);
			this.columns = 4;
			this.rows = 1;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.range = 16;
			this.dmg = rnd.nextInt(this.lvl) + 20;
			this.slow = 0;
			this.life = 15;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = true;
			this.element = element.angle_whip;
			if(this.x >= x){
				this.degree = - (float)Math.toDegrees(Math.atan((float)(this.x - x)/((float)(this.y - y))));
			}
			else{
				this.degree = (float)Math.toDegrees(Math.atan((float)(x - this.x)/((float)(this.y - y))));
			}
			//this.rec = new Rect(240 - power, -100, 240 + power, 600);
			this.rec = new Rect(208, -100, 272, 600);
			Log.d("thunder shot", "kat = " + this.degree);
			break;
		}
	}
	/*
	 * konstruktor dodatkowy dla atakow wywolywanych przez inne ataki (manafree)case shock:
	 */
	public AttackSprite(List<AttackSprite> attack,GameView gameView, attackType at, int lvl, int x, int y,boolean manaFree){
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
		case fireball:
			if(manaFree){
				this.manaCost = 0;
			}
			else{
				this.manaCost = firewall_mana;
			}
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.explosion);
			this.columns = 4;
			this.rows = 4;
			this.width = bmp.getWidth()/this.rows;
			this.height = bmp.getHeight()/this.columns;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.lvl * 2 + 3*this.width/4;
			this.dmg = 80;
			this.slow = 0;
			this.life = 15;
			this.cooldown = 900 - lvl * 100;
			this.exploding = true;
			this.staticPosition = true;
			this.element = element.explosion;
			break;
		case shock:
			if(manaFree){
				this.manaCost = 0;
			}
			else{
				this.manaCost = shock_mana;
			}
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.shock);
			this.columns = 4;
			this.rows = 4;
			this.width = bmp.getWidth()/this.rows;
			this.height = bmp.getHeight()/this.columns;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.lvl * 4 + 3*this.width/4;
			this.dmg = rand.nextInt(lvl + 2) + 8;
			this.slow = 0;
			this.life = 15;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = false;
			this.element = element.explosion;
			break;
		case storm:
			if(manaFree){
				this.manaCost = 0;
			}
			else{
				this.manaCost = storm_mana;
			}
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.shock);
			this.columns = 4;
			this.rows = 4;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.lvl * 4 + 3*this.width/4;
			this.dmg = rand.nextInt(lvl + 2) + 8;
			this.slow = 0;
			this.life = 15;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = true;
			this.element = element.explosion;
			break;
		}
	}
	/*
	 * konstruktor w�asciwy - normlanych atakow
	 */
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
			this.dmg = 16 + rand.nextInt(lvl*4);
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
			this.dmg = 60 + rand.nextInt(lvl * 5);
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
			this.slow = 0;
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
			this.dmg = 80 + rand.nextInt(2 * lvl * lvl);
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
			this.dmg = rand.nextInt(lvl*2) + 2;
			this.slow = 0;
			this.life = (this.lvl+1) * 5;
			this.cooldown = 500 + lvl * 100;
			this.staticPosition = true;
			this.exploding = false;
			this.element = element.constant;
			break;
		case tornado:
			this.manaCost = tornado_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.tornado);
			this.columns = 4;
			this.rows = 1;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.lvl * 4 + 3*this.width/4;
			this.dmg = rand.nextInt(lvl) + 2;
			this.slow = 30;
			this.life = 45;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = false;
			this.element = element.crazy;
			this.rec = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);
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
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.lvl * 4 + 3*this.width/4;
			this.dmg = rand.nextInt(lvl + 2) + 8;
			this.slow = 0;
			this.life = 15;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = false;
			this.element = element.explosion;
			break;
		case multi_shock:
			this.manaCost = multi_shock_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.multishock);
			this.columns = 3;
			this.rows = 3;
			this.width = bmp.getWidth()/this.rows;
			this.height = bmp.getHeight()/this.columns;
			this.frames = (this.rows * this.columns) - 1;
			this.range = 32;//this.lvl * 4 + 3*this.width/4;
			this.dmg = 1;
			this.slow = 0;
			this.life = 10;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = false;
			this.element = element.explosion;
			break;
		case charge_defence:
			this.x = 0;
			this.y = 590;
			this.manaCost = charge_defence_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.shield);
			this.columns = 2;
			this.rows = 3;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.range = 100;//this.lvl * 4 + 3*this.width/4;
			this.dmg = 0;
			this.slow = 0;
			this.maxLife = 480;
			this.life = this.maxLife - 1;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = true;
			this.element = element.shield;
			this.absorbRate = 0.2 * this.lvl;
			break;
		case storm:
			this.manaCost = storm_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.storm);
			this.columns = 4;
			this.rows = 2;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = this.columns - 1;
			this.range = 64;
			this.dmg = rand.nextInt(lvl + 2) + 8;
			this.slow = 0;
			this.life = 360;
			this.cooldown = 900 - lvl * 100;
			this.exploding = false;
			this.staticPosition = false;
			this.element = element.storm;
			this.unit_under = 0;
			this.dir = rand.nextInt(4);
			//this.rec = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);
			this.rec = new Rect(this.x + this.width/4, this.y, this.x + this.width, this.y + this.height);
			break;
		case thunder:
			this.manaCost = thunder_mana;
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.electriccircle);
			this.columns = 4;
			this.rows = 1;
			this.width = bmp.getWidth()/this.columns;
			this.height = bmp.getHeight()/this.rows;
			this.frames = (this.rows * this.columns) - 1;
			this.range = this.lvl * 4 + 3*this.width/4;
			this.dmg = rand.nextInt(lvl + 2) + 8;
			this.slow = 0;
			this.life = 10;
			this.cooldown = 50;
			this.exploding = false;
			this.staticPosition = true;
			this.element = element.powerGainer;
			this.power = 1;
			this.rec = new Rect(240 - this.width/5 - power,580 - this.height/5 - 2 * power,240 + this.width/5 + power , 580 + this.height/5);
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
        	srcX = (currentFrame % this.columns) * this.width;
            srcY = 0;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);//(this.x,this.y,this.x + this.width, this.y + this.height);
            canvas.save();
            canvas.rotate(this.degree, this.x, this.y);
            canvas.drawBitmap(this.bmp, src, dst, null);
            canvas.restore();
        	break;
        case shield:
        	srcX = (currentFrame % this.columns) * this.width;
        	srcY = (2 - ((int)((double)((double)this.life/(double)this.maxLife) * 3))) * this.height;
            //srcY =  this.height;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            dst = new Rect(0, this.y, this.width, this.y + this.height);//(this.x,this.y,this.x + this.width, this.y + this.height);
            //canvas.save();
            //canvas.rotate(this.degree, this.x, this.y);
            canvas.drawBitmap(this.bmp, src, dst, null);
            //canvas.restore();
        	break;
        case whip:
        	srcX = currentFrame * this.width;//(currentFrame % this.columns) * this.width;
            //row = currentFrame / this.rows;
            srcY = 0;//(600 - this.y);//row * this.height;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            dst = new Rect(this.x-this.width/2, this.y, this.x + this.width/2, 600);
            canvas.drawBitmap(this.bmp, src, dst, null);
        	break;
        case powerGainer:
        	srcX = currentFrame * this.width;//(currentFrame % this.columns) * this.width;
            //row = currentFrame / this.rows;
            srcY = 0;//(600 - this.y);//row * this.height;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            float scaler = this.power/5;
            //dst = new Rect((int)(this.x - ((this.width/2) * scaler)), (int)(this.y- ((this.height/2)*scaler)), (int)(this.x + ((this.width/2)*scaler)), (int)(this.y + ((this.height/2)*scaler)));
            //dst = new Rect((int)(this.x-this.width/2 * 10/(float)power), (int)((this.y * 10/(float)power)) , (int)((this.x + this.width/2) * 10/(float)power), (int)(this.y * 10/(float)power + this.height/2 * 10/(float)power));
            //dst = new Rect((this.x-this.width/2)* 10/power, ((this.y - this.height/2)* 10/power , this.x + this.width/2, this.y+ this.height/2);
            dst = new Rect(this.Thunder_position_X - this.width/5 - power,this.Thunder_position_Y - this.height/5 - 2 * power,this.Thunder_position_X + this.width/5 + power , this.Thunder_position_Y + this.height/5);
            this.rec = dst;
            canvas.drawBitmap(this.bmp, src, dst, null);
        	break;
        case angle_whip:
        	srcX = currentFrame * this.width;//(currentFrame % this.columns) * this.width;
            //row = currentFrame / this.rows;
            srcY = 0;//(600 - this.y);//row * this.height;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            //dst = new Rect(this.x-this.width/2, this.y, this.x + this.width/2, this.y + this.height/2);
            dst = new Rect(208, -100, 272, 600);
            //dst = new Rect(240 - power, -100, 240 + power, 600);
            canvas.save();
            canvas.rotate(this.degree, this.x, this.y);
            canvas.drawBitmap(this.bmp, src, dst, null);
            canvas.restore();
        	break;
        case storm:
        	srcX = (currentFrame % this.columns) * this.width;
            //row = currentFrame / this.rows;
            srcY = this.unit_under * this.height;//row * this.height;
            src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
            dst = new Rect(this.x-this.width/2, this.y-this.height/2, this.x + this.width/2, this.y + this.height/2);
            canvas.drawBitmap(this.bmp, src, dst, null);
//            this.rec = new Rect(this.x - this.width/4, this.y-this.height/4, this.x + this.width/4, this.y + this.height/4);
            this.rec = new Rect(this.x + this.width/4, this.y, this.x + this.width, this.y + this.height);
            this.unit_under = 0;
        	break;
        }
        currentFrame++;
   }
   private void update() {
	   if(!this.staticPosition){
		   Random rnd = new Random();
		   if(this.element == element.storm){
			   int new_direction = rnd.nextInt(16);
			   if(new_direction < 4){
				   this.dir = new_direction;
			   }
			   switch(this.dir){
			   case 0:
				   this.x += -1;
				   this.y += -2;
				   break;
			   case 1:
				   this.x += 1;
				   this.y += 2;
				   break;
			   case 2:
				   this.x += -1;
				   this.y +=  2;
				   break;
			   case 3:
				   this.x +=  1;
				   this.y += -2;
				   break;
			   }
			   if(this.rec.centerX() + this.width/2 >= gameView.getWidth()){
				   this.dir = 2;
			   }
			   if(this.rec.centerX() + this.width/2 <= 0){
				   this.dir = 3;
			   }
			   if(this.rec.centerY() + this.height/2 >= 600){
				   this.dir = 0;
			   }
			   if(this.rec.centerY() + this.height/2 <= 0){
				   this.dir = 1;
			   }
		   }
		   else{
			   this.x = this.x + rnd.nextInt(11) - 5;
			   this.y = this.y + rnd.nextInt(11) - 5;
		   }
	   }
	   if(this.currentFrame > this.frames){
		   currentFrame = 0;
	   }
	   if(this.life < 1) {
		   if(this.attp == attackType.multi_shock){
			   int distance = 50;
			   Random rand = new Random();
			   attack.add(new AttackSprite(attack,gameView,attackType.shock,1,x + rand.nextInt(distance), y + rand.nextInt(distance),true));
			   attack.add(new AttackSprite(attack,gameView,attackType.shock,1,x - rand.nextInt(distance), y - rand.nextInt(distance),true));
			   attack.add(new AttackSprite(attack,gameView,attackType.shock,1,x - rand.nextInt(distance), y + rand.nextInt(distance),true));
			   attack.add(new AttackSprite(attack,gameView,attackType.shock,1,x + rand.nextInt(distance), y - rand.nextInt(distance),true));
		   }
		   if(this.attp == attackType.thunder){
			   attack.add(new AttackSprite(attack,this.gameView,otherAttacks.thunder_shot,this.lvl * this.power,this.x, this.y,false));
		   }
		   attack.remove(this);
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
		   case whip:
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
		   case powerGainer:
		   //case angle_whip:
			   Rect temp = new Rect(this.rec);
			   if(temp.intersect(rect)){
				   return this.dmg;
			   }
			   return 0;
		   case shot:
			   distance = Math.pow(Math.pow((this.rec.centerX() - rect.centerX()),2) + Math.pow((this.rec.centerY() - rect.centerY()),2),0.5);
			   if(distance < this.range){
				   this.attack.add(new AttackSprite(this.attack,this.gameView,attackType.fireball,1,this.x,this.y,true));
				   if(distance/this.range < 0.5){
					   return this.dmg;
				   }
				   else{
					   return 2*this.dmg/3;
				   }
			   }
			   return 0;
		   case shield:
			   if(this.y - rect.centerY() < this.range){
				   return 0;
			   }
			   else {
				   return -1;
			   }
		   case storm:
			   distance = Math.pow(Math.pow((this.x - rect.centerX()),2) + Math.pow((this.y - rect.centerY()),2),0.5);
			   if(distance < this.range){
				   this.unit_under = 1;
				   Random rnd = new Random();
				   return rnd.nextInt(this.dmg);
			   }
			   return -1;
		   case angle_whip:
			   /*sprawdzamy czy wrog znajduje sie w polu razenia ataku
			    * 
			    *	Wz�r na prost� przechodz�ca przez dwa punkty P1(x1,y1) P2(x2,y2) :
			    *			(x2 - x1)(Y - y1) = (y2 - y1)(X - x1)
			    *
			    *	Do tego dorzucam sprawdzenie czy punkt nalezy do tej prostej +- range
			    */
//			   Log.d("thunder", "(y2 - y1)(X - x1) = " + (this.Thunder_position_Y - this.realY) * ((rect.centerX())));
//			   Log.d("thunder", "(x2 - x1)(Y - y1) = " + ((this.Thunder_position_X - this.realX) * (rect.centerY() - this.realY)));
//			   Log.d("thunder", "(y2 - y1)(X - x1) = " + (this.Thunder_position_Y - this.realY) * ((rect.centerX())));
//			   
//			   if(((this.Thunder_position_Y - this.realY) * ((rect.centerX() - this.realX) - this.range)) <= ((this.Thunder_position_X - this.realX) * (rect.centerY() - this.realY))
//					   && 
//				   ((this.Thunder_position_X - this.realX) * (rect.centerY() - this.realY))	<= ((this.Thunder_position_Y - this.realY) * (rect.centerX() - this.realX)) + this.range){
//				   return this.dmg;
//			   }
//			   else {
//				   return 0;
//			   }
			   /*
			    * NOWA METODA KURWA! ZAJEBISTA! this.rec ataku pozosatwiamy na miejscu a obrtacamy o kat (minus kat) srodki ciezkosci bitmap
			    * wrogow!
			    */
			   
			   /*
			    * TYLKO KURWA NIE DZIALA T_T
			    */
			   double newX = rect.centerX()*Math.cos(-this.degree) + rect.centerY()*Math.sin(-this.degree);
			   double newY = rect.centerX()*Math.sin(-this.degree) - rect.centerY()*Math.cos(-this.degree);
			   
			   int z_x = (int) (( rect.centerX()*Math.cos(-this.degree) - rect.centerY()* Math.sin(-this.degree))  );// + this.Thunder_position_X);
		       int z_y = (int) (( rect.centerX()*Math.sin(-this.degree) + rect.centerY()* Math.cos(-this.degree))  );//+ this.Thunder_position_Y);  
		        
			   double x1 = (rect.centerX()-this.Thunder_position_X)*Math.cos(this.degree) - (rect.centerY()-this.Thunder_position_Y)*Math.sin(this.degree)+this.Thunder_position_X;
			   double y1 = (rect.centerX()-this.Thunder_position_X)*Math.sin(this.degree) + (rect.centerY()-this.Thunder_position_Y)*Math.cos(this.degree)+this.Thunder_position_Y;
			   
			   Log.d("thunder", "degree = " + this.degree);
			   Log.d("thunder", "x = " + rect.centerX());
			   Log.d("thunder", "y = " + rect.centerY());
			   Log.d("thunder", "x1 = " + z_x);
			   Log.d("thunder", "y1 = " + z_y);
			   
			   if(this.rec.contains((int)z_x, (int)z_y)){
				   return this.dmg;
			   }
			   else{
				   return 0;
			   }
	   }
	   return -1;
	   
   }
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
	   return this.manaCost;
   }
   public int getSlow(){
	   return this.slow;
   }
   public element getElement(){
	   return this.element;
   }
   public double getAbsorbRate(){
	   return this.absorbRate;
   }
   public void dmgToShield(int dmg){
	   if(dmg > 0){
		   this.life += -dmg;
	   }
   }
   public attackType getAttackType(){
	   return this.attp;
   }
   public int getRange(){
	   return this.range;
   }
   public int getLvl(){
	   return this.lvl;
   }
   public int getPower(){
	   return this.power;
   }
   public void setPower(int power){
	   this.power = power;
	   this.life += 1;
   }
   public void attackReady(){
	   
   }
}
