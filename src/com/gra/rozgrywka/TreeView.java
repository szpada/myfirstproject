/**
 * 
 */
package com.gra.rozgrywka;

import com.gra.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * @author Maciej
 *
 *	I jego gowna
 *
 *
 *
//	 * @param context
//	 * @param attrs
//	 */
//	public TreeView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * @param context
//	 * @param attrs
//	 * @param defStyle
//	 */
//	public TreeView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		// TODO Auto-generated constructor stub
//	}
/**
 *
 *
 *
 *
 *
 */
public class TreeView extends SurfaceView {
	private float h_factor;
	private float w_factor;
	private long lastClick;
	private int currentAttack;
	private int currentGod;/*
							*	0 - zeus
							*	1 - hefajstos
							*	2 - posejdon
							*/
	private TreeLoopThread treeLoopThread;
	private Sprite backGround;
	private InfoSprite info;
	private Sprite zeus;
	private Sprite hephaestus;
	private Sprite poseidon;
	
	/*
	 * ataki zeusa
	 */
	private Sprite shock;
	private Sprite multi_shock;
	/*
	 * ataki hefajstosa
	 */
	private Sprite fireball;
	private Sprite fireball_shot;
	/*
	 * player - potrzebny do testowania drzewa rozwoju
	 * pozniej bedzie przekazywany jako argument w konstruktorze
	 */
	private int base[][] = {
			/*
			 * 0 - 3 - poziom ataku (0 oznacza ze mozna wsadzic punkt w dany atak
			 * -1 	 - nie mozna wsadzic punktu w ten atak póki ataki nizszego poziomu nie zostan¹ wybrane
			 * -2	 - atak nie istnieje
			 */
    		{1,1,1,0,0},	//ELEKTRYCZNE
    		{1,1,1,1,-2},	//OGNIEN
    		{1,1,-2,-2,-2},	//WODA
    		{-2,-2,-2,-2,-2},	//FIZYCZNE
    		{-2,-2,-2,-2,-2}		//SMIERC
    };
	private Player player = new Player("pies",0,0,base,1000,1000,2,100,100, 0, 0);
	/**
	 * @param context
	 */
	public TreeView(Context context,double w_factor, double h_factor) {
		super(context);
        this.h_factor = (float)h_factor;
 	   	this.w_factor = (float)w_factor;
        treeLoopThread = new TreeLoopThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {
               //@Override
               public void surfaceDestroyed(SurfaceHolder holder) {
               Log.d("GameView", "odpalam surfaceDestroyed");
                      boolean retry = true;
                      treeLoopThread.setRunning(false);
                      while (retry) {
                             try {
                                   treeLoopThread.join();
                                   retry = false;
                             } catch (InterruptedException e) {}
                      }
               }
               //@Override
               public void surfaceCreated(SurfaceHolder holder) {
                      createSprites();
                      treeLoopThread.setRunning(true);
                      treeLoopThread.start();
               }
               //@Override
               public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
               	
               }
        }); 
	}
	
	public void createSprites(){
		this.backGround = new Sprite(this,0, 0, backgroundType.background);
		this.info = new InfoSprite(this,100,300,attackType.charge_defence);
		this.zeus = new Sprite(this,300,200,backgroundType.zeus);
		this.poseidon = new Sprite(this,300,400,backgroundType.hephaestus);
		this.hephaestus = new Sprite(this,300,600,backgroundType.poseidon);
		
		this.shock = new Sprite(this,100,500,0,0);
		this.fireball_shot = new Sprite(this,100,400,1,0);
		
		this.fireball = new Sprite(this,100,500,0,1);
		this.fireball_shot = new Sprite(this,100,400,1,1);
	}
	public void onDraw(Canvas canvas){
		
		this.backGround.onDraw(canvas);
		
		//this.info.onDraw(canvas);
		this.zeus.onDraw(canvas);
		this.hephaestus.onDraw(canvas);
		this.poseidon.onDraw(canvas);
		switch(this.currentGod){
		case 0:
			this.shock.onDraw(canvas);
			//this.multi_shock.onDraw(canvas);
			break;
		case 1:
			//this.fireball.onDraw(canvas);
			//this.fireball_shot.onDraw(canvas);
			break;
		case 2:
			break;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int coolDown = 300;
		float x = event.getX();
		float y = event.getY();
		/*
		 * odkomentowac jak juz beda przekazywane wlasciwe wartosci w konstruktorze
		 */
		//x = x / this.w_factor;
		//y = y / this.h_factor;
		if(System.currentTimeMillis() - lastClick > coolDown) {
     	   lastClick = System.currentTimeMillis();
     	   if(zeus.checkCollision((int)x, (int)y)){
     		  this.backGround.setCurrentGod(0);
     		  this.currentGod = 0;
     	   }
     	   else if(hephaestus.checkCollision((int)x, (int)y)){
     		  this.backGround.setCurrentGod(1);
     		  this.currentGod = 1;
     	   }
     	   else if(poseidon.checkCollision((int)x, (int)y)){
     		  this.backGround.setCurrentGod(2);
     		  this.currentGod = 2;
    	   }
		}
		return true;
	}
}
