package com.edu4java.android.killthemall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

/*			
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
	private long lastClick;
    private long manaTime = 0;
    
    private GameLoopThread gameLoopThread;
    
    private List<EnemySprite> enemies = new ArrayList<EnemySprite>();
    private List<AttackSprite> attack = new ArrayList<AttackSprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    
    private Switcher switchGod;
	private Switcher switchAttack;
    private Sprite panel;
    private Sprite background;
    private Sprite ambrosia;

    private int lastGod;
    private int lastAttack;
    private int base[][] = {
    		{1,1,1,0,0},	//ELEKTRYCZNE
    		{3,1,1,1,0},	//OGNIEN
    		{3,0,0,0,0},	//WODA
    		{0,0,0,0,0},	//FIZYCZNE
    		{0,0,0,0,0}		//SMIERC
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
    	   /*
    	    * Tworzenie wszystkich bitmap i wrog�w
    	    */
//    	   enemies.add(createEnemy(enemyType.knight_summoner,10,10));
//           enemies.add(createEnemy(enemyType.knight_summoner,240,10));
//         enemies.add(createEnemy(enemyType.knight_summoner,80,10));
           enemies.add(createEnemy(enemyType.dragon,240,10));
           temps.add(createTemp(240,400,bonusType.mana_potion));
           switchGod = new Switcher(this.player,this,true,16,624);
           switchAttack = new Switcher(this.player,this,false,333,624);
           Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.panel3);
           panel = new Sprite(this,-1,600,bmp,"panel",0);
           bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
           background = new Sprite(this,-1,-1,bmp,"background",0);
           bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ambrosia3);
           ambrosia = new Sprite(this,178,625,bmp,"ambrosia",player.getMana());
       }
       private EnemySprite createEnemy(enemyType e, int x, int y){
    	   return new EnemySprite(enemies,this, e,x,y);
       }
       private AttackSprite createAttack(attackType at, int lvl,int x, int y){
    	   return new AttackSprite(attack,this, at, lvl,x,y);
       }
       public TempSprite createTemp(int x,int y, bonusType bt){
    	   return new TempSprite(temps,this,x,y,bt);
       }
       @Override
       protected void onDraw(Canvas canvas) {
    	   boolean shield_on = false; //zmienna potrzebna do dzielenia dmg miedzy olimpem a shieldem
    	   int attack_number = 0;
    	   /*
    	    * wyswietlanie ca�ej grafiki i update many
    	    */
    	   //canvas.save();
    	   canvas.scale(this.w_factor, this.h_factor);
    	   background.onDraw(canvas);
           for (int i = enemies.size() - 1; i >= 0; i--) {
        	   if(enemies.get(i).getWarriorType() == warriorType.melee && enemies.get(i).getDmgReady()){
        		   /*
        		    * jesli mamy shielda to dzielimy dmg na shield i olimp
        		    */
        		   for (int j = attack.size() - 1; j >= 0; j--) {
	        		   if(attack.get(j).getAttackType() == attackType.charge_defence){
	        			   shield_on = true;
	        			   attack_number = j;
	        			   break;
	        		   }
        		   }
        		   if(shield_on){
        			   shieldOlympDmg(i,attack_number);
        		   }
        		   else{
	        		   player.dmgToOlymp(enemies.get(i).getDmg());
        		   }
        	   }
        	   enemies.get(i).onDraw(canvas);
           }
           for (int i = attack.size() - 1; i >= 0; i--) {
        	   attack.get(i).onDraw(canvas);
           }
           for(int i = temps.size() - 1; i >= 0; i--){
        	   temps.get(i).onDraw(canvas);
           }
           ambrosia.onDraw(canvas);
           switchGod.onDraw(canvas);
           switchAttack.onDraw(canvas);
           panel.onDraw(canvas);
           executeDamage();
           updateMana();
           /*
            * Obecne zycie i mana
            * (do usuniecia gdy nie gra bedzie gotowa)
            */
           Paint paint = new Paint();
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
        	   if(!(switchGod.collision((int)(x),(int)(y))) && !(switchAttack.collision((int)(x),(int)(y)))){
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
										   enemies.get(i).setSlowTime(attack.get(attack.size()-1).getSlow());
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
       /*
        * zadawanie dmg przez wszystkie ataki znajdujace sie w danej chwili na mapie
        */
       public void executeDamage(){
    	   if((enemies.size()>0) && (attack.size()>0)){
    		   for(int i = enemies.size()-1; i >= 0; i--){
    			   if((attack.size()-1 >= 0)){
    				   for(int j = attack.size()-1; j >= 0; j--){
    					   if(!(attack.get(j).getExploding())){
    						   int power = 0;
    						   if(!(enemies.get(i).getSt() == state.die)){
	    						   power = attack.get(j).checkCollision(enemies.get(i).getRect());
    						   }
							   if(power > 0){
								   enemies.get(i).attackedWithDmg(power,player.getCurrentGod());//(power + attack.get(j).getDmg(), player.getCurrentGod());
								   enemies.get(i).setSlowTime(attack.get(attack.size()-1).getSlow());
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
       /*
        * zwiekszanie czasowe many(ambrozji) oraz aktualizacja jej poziomu
        */
       public void updateMana(){
    	   ambrosia.updateStats(player.getCurrentMana());
    	   if(System.currentTimeMillis() - this.manaTime > 1000){
        	   player.addMana();
        	   manaTime = System.currentTimeMillis();
           }
       }
       public void shieldOlympDmg(int enemy, int a_number){
    	   /*
		    * atakujemy wroga nowym atakiem
		    */
    	   Random rnd = new Random();
    	   if(attack.get(a_number).checkCollision(enemies.get(enemy).getRect()) == 0){
	    	   if(rnd.nextInt(player.getLuck() + 1) > 0){
	    		   attack.add(new AttackSprite(attack,this,otherAttacks.chargeShieldAttack,1,
	    				   enemies.get(enemy).getX() + enemies.get(enemy).getWidth()/2,
	    				   enemies.get(enemy).getY() + enemies.get(enemy).getHeight()/2,true));
	    	   }
    	   }
		   attack.get(a_number).dmgToShield((int)(enemies.get(enemy).getDmg() * attack.get(a_number).getAbsorbRate()));
		   player.dmgToOlymp((int)(enemies.get(enemy).getDmg() * (1 - attack.get(a_number).getAbsorbRate())));
       }
}