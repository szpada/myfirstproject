/**
 * 
 */
package com.gra.rozgrywka;

import java.util.ArrayList;
import java.util.List;

import com.gra.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	private List<Sprite> attacks = new ArrayList<Sprite>();
	private float h_factor;
	private float w_factor;
	private long lastClick;
	private int points = 10;
	private int currentAttack = 0;
	private int currentGod = 0;
	private int maxUpgrade = 3;
							/*
							*	0 - zeus
							*	1 - hefajstos
							*	2 - posejdon
							*/
	private TreeLoopThread treeLoopThread;
	private Sprite upgrade;
	private Sprite backGround;
	private Sprite currentTree;
	private InfoSprite info;
	private Sprite zeus;
	private Sprite hephaestus;
	private Sprite poseidon;
	/*
	 * player - potrzebny do testowania drzewa rozwoju
	 * pozniej bedzie przekazywany jako argument w konstruktorze
	 */
	private int base[][] = {
			/*
			 * 0 - 3 - poziom ataku (0 oznacza ze mozna wsadzic punkt w dany atak
			 * -1 	 - nie mozna wsadzic punktu w ten atak p�ki ataki nizszego poziomu nie zostan� wybrane
			 * -2	 - atak nie istnieje
			 */
    		{0,0,0,0,0},	//ELEKTRYCZNE
    		{0,-1,0,-1,-2},	//OGNIEN
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
		/*
		 * przycisk upgrade
		 */
		this.upgrade = new Sprite(this,400,660,0);
		/*
		 * tlo (drzewo konkretnego ataku)
		 */
		this.backGround = new Sprite(this,0, 0, backgroundType.background);
		/*
		 * drzewo rozwoju
		 */
		this.currentTree = new Sprite(this,8, 100, backgroundType.tree);
		/*
		 * informacje o wybranym ataku
		 */
		this.info = new InfoSprite(this,0,650,0,0);
		/*
		 * przyciski sluzace do wyboru drzewa
		 */
		this.zeus = new Sprite(this,320,20,backgroundType.zeus);
		this.poseidon = new Sprite(this,320,147,backgroundType.poseidon);
		this.hephaestus = new Sprite(this,320,270,backgroundType.hephaestus);
		/*
		 * ataki z poszczegolnych drzew
		 */
			/*
			 * zeus
			 */
		Sprite shock = 				new Sprite(this,70,600,0,0,1,1,1);
		Sprite multi_shock = 		new Sprite(this,70,480,1,0,1,2,2);
		Sprite charge_defense = 	new Sprite(this,20,320,2,0,4,1,1);
		Sprite storm  = 			new Sprite(this,130,320,3,0,8,3,3);
		Sprite thunder  = 			new Sprite(this,70,100,4,0,6,8,5);
			/*
			 * hefajstos
			 */
		Sprite fireball = 			new Sprite(this,70,100,0,1,3,3,3);
		Sprite fireball_shot = 		new Sprite(this,70,250,1,1,4,4,4);
		Sprite firewall = 			new Sprite(this,70,100,2,1,3,3,3);
		Sprite meteor =				new Sprite(this,70,100,3,1,3,3,3);
			/*
			 * posejdon
			 */
		Sprite tornado = 			new Sprite(this,70,100,0,2,3,3,3);
		Sprite waterSplash = 		new Sprite(this,70,300,1,2,3,3,3);
		
		shock.collisionFriendly(true);
		multi_shock.collisionFriendly(true);
		charge_defense.collisionFriendly(true);
		storm.collisionFriendly(true);
		thunder.collisionFriendly(true);
		
		info.setCurrentGod(shock.getGodNumber());
		info.setCurrentAttack(shock.getAttackNumber());
		info.setDmg(shock.getDmg());
		info.setManaCost(shock.getManaCost());
		info.setRange(shock.getRange());
		/*
		 * dodane atakow do listy
		 */
		attacks.add(shock);
		attacks.add(multi_shock);
		attacks.add(charge_defense);
		attacks.add(storm);
		attacks.add(thunder);
		attacks.add(fireball);
		attacks.add(fireball_shot);
		attacks.add(firewall);
		attacks.add(meteor);
		attacks.add(tornado);
		attacks.add(waterSplash);
		
		this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
		this.currentTree.setCurrentGod(0);
		info.setCurrentAttack(this.currentAttack);
		info.setCurrentGod(this.currentGod);
	}
	public void onDraw(Canvas canvas){
		this.zeus.onDraw(canvas);
		this.poseidon.onDraw(canvas);
		this.hephaestus.onDraw(canvas);
		this.backGround.onDraw(canvas);
		this.currentTree.onDraw(canvas);
		for(int i = 0; i < attacks.size(); i++){
			if(attacks.get(i).getGodNumber() == this.currentGod){
				attacks.get(i).onDraw(canvas);
			}
		}
		this.info.onDraw(canvas);
		this.upgrade.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		canvas.drawText("Points : " + this.points, 10, 10, paint);
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
     		  this.currentTree.setCurrentGod(0);
     		  this.currentGod = 0;
     		 for(int i = 0; i <= attacks.size()-1; i++){
     			 if(attacks.get(i).getGodNumber() != this.currentGod){
     				 attacks.get(i).collisionFriendly(false);
     			 }
     			 else{
     				attacks.get(i).collisionFriendly(true);
     			 }
     		 }
     	   }
     	   else if(hephaestus.checkCollision((int)x, (int)y)){
     		  this.currentTree.setCurrentGod(1);
     		  this.currentGod = 1;
     		 for(int i = 0; i <= attacks.size()-1; i++){
     			if(attacks.get(i).getGodNumber() != this.currentGod){
    				 attacks.get(i).collisionFriendly(false);
    			 }
    			 else{
    				attacks.get(i).collisionFriendly(true);
    			 }
     		 }
     	   }
     	   else if(poseidon.checkCollision((int)x, (int)y)){
     		  this.currentTree.setCurrentGod(2);
     		  this.currentGod = 2;
     		 for(int i = 0; i <= attacks.size()-1; i++){
     			if(attacks.get(i).getGodNumber() != this.currentGod){
    				 attacks.get(i).collisionFriendly(false);
    			 }
    			 else{
    				attacks.get(i).collisionFriendly(true);
    			 }
     		 }
    	   }
     	  for(int i = 0; i <= attacks.size()-1; i++){
  			if(attacks.get(i).checkCollision((int)x, (int)y)){
  				this.currentAttack = attacks.get(i).getAttackNumber();
				this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
  				info.setCurrentGod(attacks.get(i).getGodNumber());
  				info.setCurrentAttack(attacks.get(i).getAttackNumber());
  				info.setDmg(attacks.get(i).getDmg());
  				info.setManaCost(attacks.get(i).getManaCost());
  				info.setRange(attacks.get(i).getRange());
  			}
     	  }
     	  if(this.upgrade.checkCollision((int)x,(int)y)){
     		  if(!performUpgrade(this.currentGod,this.currentAttack)){
     			  //zaswiec punktami
     		  }
     	  }
		}
		return true;
	}
	public boolean performUpgrade(int god, int attack){
		if(this.points <= 0){
			return false;
		}
		else{
			/*
			 * zaleznosci w drzewach
			 */
			switch(god){
			case 0:
				/*
				 * zeus				0
				 * 					1
				 * 				2		3
				 * 					4
				 */
				if(attack == 0){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						this.points--;
						this.base[god][attack]++;
						this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						if(this.base[god][1] == -1){
							this.base[god][1] = 0;
						}
					}
				}
				if(attack == 1){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][0] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][2] == -1){
								this.base[god][2] = 0;
							}
							if(this.base[god][3] == -1){
								this.base[god][3] = 0;
							}
						}
					}
				}
				if(attack == 2){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][1] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][3] > 0){
								this.base[god][4] = 0;
							}
						}
					}
				}
				if(attack == 3){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][1] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][2] > 0){
								this.base[god][4] = 0;
							}
						}
					}
				}
				if(attack == 4){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][2] > 0 && this.base[god][3] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						}
					}
				}
				if(upgrade.getUpgrade() >= this.maxUpgrade){
					this.upgrade.setUpgrade(4);
				}
				Log.d("treeView ", "attack : "+ currentAttack + "level : " + this.base[god][attack]);
				break;
			case 1:
				/*
				 * hefajstos	0
				 * 			1		2
				 * 				3
				 */
				if(attack == 0){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						this.points--;
						this.base[god][attack]++;
						this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						if(this.base[god][1] == -1){
							this.base[god][1] = 0;
						}
						if(this.base[god][2] == -1){
							this.base[god][2] = 0;
						}
					}
				}
				if(attack == 1){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][0] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][2] > 0){
								this.base[god][3] = 0;
							}
						}
					}
				}
				if(attack == 2){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][0] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][1] > 0){
								this.base[god][3] = 0;
							}
						}
					}
				}
				if(attack == 3){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][1] > 0  && this.base[god][2] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][2] > 0){
								this.base[god][4] = 0;
							}
						}
					}
				}
				break;
			case 2:
				/*
				 * posejdon		0
				 * 				1
				 */
				if(attack == 0){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						this.points--;
						this.base[god][attack]++;
						this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						if(this.base[god][1] == -1){
							this.base[god][1] = 0;
						}
					}
				}
				if(attack == 1){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][0] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						}
					}
				}
				break;
			}
			return true;
		}
	}
}
