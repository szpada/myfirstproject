package com.edu4java.android.killthemall;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*										TRZEBA W CHUJ ZROBIC !!!
 * 			________________________________________________________________________________
 * 			|ZAMIENIC WSZYSTKO Z PIKSELI (NA SZTYWNO) NA REPREZENTACJE KONKRETNYCH EKRANOW	|
 * 			|																				|
 * 			|																				|
 * 			|_______________________________________________________________________________|
 * 
 * 									OPTYMALIZACJA (tylko jesli bedzie cielo)
 * 			________________________________________________________________________________
 *			|DODAC 3 LISTY DLA ENEMYSPRITE (gora-lewo / gora-prawo / dol-lewo / dol-prawo) 	|
 *			|I SPRAWDZAC TYLKO TE LISTY W KTORYCH ZNAJDUJE SIE ATAK							|
 * 			|																				|
 * 			|																				|
 * 			|_______________________________________________________________________________|
 */



/**
 * @author Maciej
 * glowny widok - plansza gry i wszystko co sie na niej dzieje
 */
public class GameView extends SurfaceView {
	private float w_factor;
	private float h_factor;
    private GameLoopThread gameLoopThread;
    private List<EnemySprite> enemies = new ArrayList<EnemySprite>();
    private List<AttackSprite> attack = new ArrayList<AttackSprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private long lastClick;
    private long manaTime = 0;
    private Switcher switchGod;
	private Switcher switchAttack;
    private Sprite panel;
    private Sprite background;
   //private Sprite olymp;
   private int lastGod;
   private int lastAttack;
   private int base[][] = {
		   {1,1,0,0,0},	//ELEKTRYCZNE
		   {3,1,1,1,0},	//OGNIEN
		   {3,0,0,0,0},	//WODA
		   {0,0,0,0,0},	//FIZYCZNE
		   {0,0,0,0,0}	//SMIERC
   };
   
   private String TAG = "GameView";
   
   private Player player = new Player("pies",0,0,base,200,100,2);
   
   public GameView(Context context, double w_factor, double h_factor) {
         super(context);
         this.h_factor = (float)h_factor;
 	   	 this.w_factor = (float)w_factor;
         gameLoopThread = new GameLoopThread(this);
         getHolder().addCallback(new SurfaceHolder.Callback() {
                //@Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("GameView", "odpalam surfaceDestroyed");
                       boolean retry = true;
                       gameLoopThread.setRunning(false);
                       while (retry) {
                              try {
                                    gameLoopThread.join();
                                    retry = false;
                              } catch (InterruptedException e) {}
                       }
                }
                //@Override
                public void surfaceCreated(SurfaceHolder holder) {
                       createSprites();
                       gameLoopThread.setRunning(true);
                       gameLoopThread.start();
                }
                //@Override
                public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
                	
                }
         }); 
       }

       private void createSprites() {
    	   enemies.add(createEnemy(enemyType.knight,R.drawable.good1,10,10));
           enemies.add(createEnemy(enemyType.knight,R.drawable.good3,240,10));
           enemies.add(createEnemy(enemyType.knight,R.drawable.bad1,80,10));
           enemies.add(createEnemy(enemyType.dragon,R.drawable.psismok,240,10));
           temps.add(createTemp(240,400,bonusType.mana_potion));
           switchGod = new Switcher(this.player,this,true,20,10);
           switchAttack = new Switcher(this.player,this,false,80,10);
           //switchGod = new Switcher(this.player,this,true,(int)(140 * this.w_factor), (int)(600 * this.h_factor));
           //switchAttack = new Switcher(this.player,this,false,(int)(240 * this.w_factor), (int)(600 * this.h_factor));
           Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.panel);
           panel = new Sprite(this,0,600,bmp,"panel",0);
           bmp = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
           background = new Sprite(this,0,0,bmp,"background",0);
       }
       private EnemySprite createEnemy(enemyType e, int resouce, int x, int y){
    	   Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
    	   return new EnemySprite(enemies,this, e,bmp,x,y);
       }
       private AttackSprite createAttack(attackType at, int lvl,int x, int y){
    	   return new AttackSprite(attack,this, at, lvl,x,y);
       }
       public TempSprite createTemp(int x,int y, bonusType bt){
    	   return new TempSprite(temps,this,x,y,bt);
       }
       @Override
       protected void onDraw(Canvas canvas) {
    	   canvas.scale(this.w_factor, this.h_factor);
    	   Paint paint = new Paint();
    	   background.onDraw(canvas);
           for (int i = enemies.size() - 1; i >= 0; i--) {
        	   if(enemies.get(i).getDmgReady()){
        		   player.dmgToOlymp(enemies.get(i).getDmg());
        	   }
        	   enemies.get(i).onDraw(canvas);
           }
           for (int i = attack.size() - 1; i >= 0; i--) {
        	   attack.get(i).onDraw(canvas);
           }
           for(int i = temps.size() - 1; i >= 0; i--){
        	   temps.get(i).onDraw(canvas);
           }
           switchGod.onDraw(canvas);
           switchAttack.onDraw(canvas);
           panel.onDraw(canvas);
           if(System.currentTimeMillis() - manaTime > 1000){
        	   player.addMana();
        	   manaTime = System.currentTimeMillis();
           }
           executeDamage();
           canvas.drawText(Integer.toString(player.getCurrentMana()), 240, 400, paint);	//mana gracza
           canvas.drawText(Integer.toString(player.getOlympLife()), 240, 450, paint);	//zycie olimpu
       }
       @Override
       public boolean onTouchEvent(MotionEvent event) {
    	   boolean noBonus = true;//zmienna potrzebna do blokowania atakow podczas klikania w bonusy
    	   int coolDown = 300;
    	   if(attack.size()>0){
    		   coolDown = attack.get(attack.size()-1).getCoolDown();
    	   }
    	   float x = event.getX();
           float y = event.getY();
           x = x / this.w_factor;
           y = y / this.h_factor;
           Log.d("GameView", "kliknalem w : [x] = " + Float.toString(x) + "[y] = " + Float.toString(y));
    	   if((player.getCurrentGod() != this.lastGod) || player.getCurrentAttack() != this.lastAttack){
    		   coolDown = 300;
           }
           if(System.currentTimeMillis() - lastClick > coolDown) {
        	   lastClick = System.currentTimeMillis();
        	   if(!(switchGod.collision(Math.round(x),Math.round(y))) && !(switchAttack.collision(Math.round(x),Math.round(y)))){
        		   for(int i = temps.size() - 1; i >= 0; i--){
        			   if(temps.get(i).collision(Math.round(x), Math.round(y))){
        				   noBonus = false;
        				   switch(temps.get(i).getBonusType()){
        				   case mana_potion:
        					   player.addMana(temps.get(i).getAmount());
        					   break;
        				   case repair:
        					   player.addLife(temps.get(i).getAmount());
        					   break;
        				   }
        			   }
        		   }
        		   if(noBonus){
	        		   synchronized (getHolder()) {
	        			   AttackSprite temp = createAttack(player.getAttackType(),player.getAttackLevel(),Math.round(x), Math.round(y));
	        			   if(player.manaForAttack(temp.getManaCost())){
	        				   attack.add(temp);
		        			   this.lastAttack = player.getCurrentAttack();
		        			   this.lastGod = player.getCurrentGod();
		        			   for(int i = enemies.size()-1; i >= 0; i--){
		        				   if((attack.get(attack.size()-1).getExploding())){
		        					   int power = attack.get(attack.size()-1).checkCollision(enemies.get(i).getRect());
									   if(power > 0){
										   enemies.get(i).attackedWithDmg(power,player.getCurrentGod());
										   temps.add(createTemp(240,400,bonusType.mana_potion));
										   temps.add(createTemp(140,200,bonusType.repair));
									   }
		        				   }
		        			   }
	        			   }
	        		   }
        		   }
        	   }
           }
           return true;     
       }

       public void executeDamage(){
    	   if((enemies.size()>0) && (attack.size()>0)){
    		   for(int i = enemies.size()-1; i >= 0; i--){
    			   if((attack.size()-1 >= 0)){
    				   for(int j = attack.size()-1; j >= 0; j--){
    					   if(!(attack.get(j).getExploding())){
    						   int power = attack.get(j).checkCollision(enemies.get(i).getRect());
							   if(power > 0){
								   enemies.get(i).attackedWithDmg(power,player.getCurrentGod());//(power + attack.get(j).getDmg(), player.getCurrentGod());
								   if(attack.get(j).getElement() == element.shot ){ 
									   if(enemies.get(i).getLife() > 0){
										   attack.remove(attack.get(j));
									   }
								   }
								   if(enemies.get(i).getLife() < 1){
									   temps.add(createTemp(enemies.get(i).getLife() * -5,400,bonusType.mana_potion));
									   //enemies.add(createEnemy(enemyType.knight,R.drawable.bad3,enemies.get(i).getLife() * -5,10));
								   }
							   }
    					   }
    				   }
    			   }
    		   }
    	   }
       }
}