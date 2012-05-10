package com.gra.rozgrywka;

import java.util.ArrayList;
import com.gra.R;
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
    private List<EnemyAttack> enemyAttacks = new ArrayList<EnemyAttack>();
    private List<Wave> waves = new ArrayList<Wave>();
    private Level level;
    private int current_wave = 0;
    
    private Switcher switchGod;
	private Switcher switchAttack;
    private Sprite panel;
    private Sprite background;
    private Sprite ambrosia;

    private int lastGod;
    private int lastAttack;
    private int base[][] = {
    		{1,1,1,1,1},	//ELEKTRYCZNE
    		{1,1,1,1,0},	//OGNIEN
    		{1,1,0,0,0},	//WODA
    		{0,0,0,0,0},	//FIZYCZNE
    		{0,0,0,0,0}		//SMIERC
    };
   
   private String TAG = "GameView";
   
   private Player player = new Player("pies",0,0,base,1000,1000,2,100,100, 0, 0);
   
   public GameView(Context context, double w_factor, double h_factor, Level level) {
         super(context);
         this.h_factor = (float)h_factor;
 	   	 this.w_factor = (float)w_factor;
 	   	 this.level = level;
 	   	 
 	   	 this.waves = this.level.getWave();
 	   	  	   	
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
                       
                       try {
						gameLoopThread.start();
					} catch (IllegalThreadStateException e) {
						Log.d("GameView", "bylo thread exception");
						e.printStackTrace();
						throw e;
					}
                       
                }
                //@Override
                public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
                	
                }
         }); 
       }
   		
   
       


	private void createSprites() {
    	   this.
    	   /*
    	    * Tworzenie wszystkich bitmap i wrogów
    	    */
//    	   enemies.add(createEnemy(enemyType.knight_general,80,10));
//    	   enemies.add(createEnemy(enemyType.balista,10,10));
//    	   enemies.add(createEnemy(enemyType.catapult,200,10));
//    	   enemies.add(createEnemy(enemyType.knight,10,10));
//    	   enemies.add(createEnemy(enemyType.knight,240,10));  	   
//           enemies.add(createEnemy(enemyType.dragon,240,10));
//    	   enemies.add(createEnemy(enemyType.fire_titan,240,10));
//           temps.add(createTemp(240,400,bonusType.mana_potion));
           
           nextWave(1);	//dodaj pierwsza fale;
           
           switchGod = new Switcher(this.player,this,true,16,624);
           switchAttack = new Switcher(this.player,this,false,333,624);
           Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.panel3);
           panel = new Sprite(this,-1,600,bmp,"panel",0);
           bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
           background = new Sprite(this,-1,-1,bmp,"background",0);
           bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ambrosia3);
           ambrosia = new Sprite(this,178,625,bmp,"ambrosia",player.getMana());
           
//           enemyAttacks.add(new EnemyAttack(enemyAttacks, this, 140, 0, 140, 700, 5, enemyAttackType.spear));
//           enemyAttacks.add(new EnemyAttack(enemyAttacks, this, 340, 0, 140, 700, 1, enemyAttackType.catapult_stone));
       }
       private EnemySprite createEnemy(enemyType e, int x, int y){
    	   return new EnemySprite(enemies,this,e,x,y, enemyAttacks);
       }
       private AttackSprite createAttack(attackType at, int lvl,int x, int y){
    	   return new AttackSprite(attack,this, at, lvl,x,y);
       }
       public TempSprite createTemp(int x,int y, bonusType bt){
    	   return new TempSprite(temps,this,x,y,bt);
       }
       @Override
       protected void onDraw(Canvas canvas) {
    	   nextWave(this.enemies.size());	//nowa fala dodawana kiedy cala poprzednia jest martwa
    	   
    	   boolean shield_on = false; //zmienna potrzebna do dzielenia dmg miedzy olimpem a shieldem
    	   int attack_number = 0;
    	   for (int j = attack.size() - 1; j >= 0; j--) {
    		   if(attack.get(j).getAttackType() == attackType.charge_defence){
    			   shield_on = true;
    			   attack_number = j;
    			   break;
    		   }
		   }
    	   /*
    	    * wyswietlanie ca³ej grafiki i update many
    	    */
    	   canvas.scale(this.w_factor, this.h_factor);
    	   background.onDraw(canvas);
    	   /*
    	    * zadawanie dmg olimpowi przez wrogow atakujacych wrecz
    	    */
           for (int i = enemies.size() - 1; i >= 0; i--) {
        	   if(enemies.get(i).getWarriorType() == warriorType.melee && enemies.get(i).getDmgReady()){
        		   /*
        		    * jesli mamy shielda to dzielimy dmg na shield i olimp
        		    */
        		   if(shield_on){
        			   shieldOlympDmg(i,attack_number);
        		   }
        		   else{
	        		   player.dmgToOlymp(enemies.get(i).getDmg());
        		   }
//        		   enemies.get(i).dmgReady(false);
        	   }
        	   enemies.get(i).onDraw(canvas);
           }
           /*
    	    * zadawanie dmg olimpowi przez ataki wrogow (dystansowe)
    	    */
    	   for (int i = enemyAttacks.size() - 1; i >= 0; i--) {
        	   if(enemyAttacks.get(i).getY() >= panel.getY() && enemyAttacks.get(i).getAttackState() != attackState.die){
        		   /*
        		    * jesli mamy shielda to dzielimy dmg na shield i olimp
        		    */
        		   if(shield_on){
        			   shieldOlympDmg_shot(enemyAttacks.get(i).getDmg(),attack_number);
        		   }
        		   else{
	        		   player.dmgToOlymp(enemyAttacks.get(i).getDmg());
        		   }
        		   enemyAttacks.get(i).setState(attackState.die);
        	   }
        	   enemyAttacks.get(i).onDraw(canvas);
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
	        			   if(attack.size() > 0 && attack.get(attack.size()-1).getAttackType() == attackType.thunder && player.getAttackType() == attackType.thunder && System.currentTimeMillis() - lastClick < attack.get(attack.size()-1).getCoolDown() * 4){
	        				   if(attack.get(attack.size()-1).getPower() < 50){
	        					   if(player.manaForAttack(1)){
	        						   attack.get(attack.size()-1).setPower(attack.get(attack.size()-1).getPower() + 1);
	        					   }
	        				   }
	        			   }
	        			   else{
		        			   AttackSprite temp = createAttack(player.getAttackType(),player.getAttackLevel(),Math.round(x), Math.round(y));
		        			   if(player.manaForAttack(temp.getManaCost())){
		        				   attack.add(temp);
			        			   this.lastAttack = player.getCurrentAttack();
			        			   this.lastGod = player.getCurrentGod();
			        			   for(int i = enemyAttacks.size()-1; i >= 0; i--){
			        				   if((attack.get(attack.size()-1).getExploding())){
			        					   int power = attack.get(attack.size()-1).checkCollision(enemyAttacks.get(i).getRect());
										   if(power > 0){
											   enemyAttacks.get(i).attackedWithDmg(power);
										   }
			        				   }
			        			   }
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
           }
           return true;     
       }
       /*
        * zadawanie dmg przez wszystkie ataki znajdujace sie w danej chwili na mapie
        */
       
       /*
        * kolizje z atakami wrogow
        */
       public void executeDamage(){
    	   if((enemyAttacks.size()>0) && (attack.size()>0)){
    		   for(int i = enemyAttacks.size()-1; i >= 0; i--){
    			   if((attack.size()-1 >= 0)){
    				   for(int j = attack.size()-1; j >= 0; j--){
    					   if(!(attack.get(j).getExploding())){
    						   int power = 0;
    						   if(!(enemyAttacks.get(i).getAttackState() == attackState.die)){
	    						   power = attack.get(j).checkCollision(enemyAttacks.get(i).getRect()); 
    						   }
							   if(power > 0){
								   enemyAttacks.get(i).attackedWithDmg(power);//(power + attack.get(j).getDmg(), player.getCurrentGod());
							   }
    					   }
    				   }
    			   }
    		   }
    	   }
    	   /*
    	    * kolizje z wrogami
    	    */
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
								   //enemies.get(i).setSlowTime(attack.get(attack.size()-1).getSlow());
								   enemies.get(i).setSlowTime(attack.get(j).getSlow());
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
       public void shieldOlympDmg_shot(int dmg, int a_number){
    	   attack.get(a_number).dmgToShield((int)(dmg * attack.get(a_number).getAbsorbRate()));
		   player.dmgToOlymp((int)(dmg * (1 - attack.get(a_number).getAbsorbRate())));
       }
       public void nextWave(int size){
    	   if(size == 0 && this.current_wave < this.level.waveSize()-1){
    		   Log.d("waves", "enemy size = " + size);
    		   Log.d("waves", "wave  size = " + this.level.waveSize());
    		   List<Unit> units = new ArrayList<Unit>();
    		   if(this.waves.size() > 0){
    			   units = waves.get(this.current_wave).getUnits();
    			   Log.d("waves", "unitsy zaladowane");
    			   for(int j = units.size()-1; j >= 0; j--){
    				   Log.d("waves", "petla for przejazd nr : " + j);
    				   enemies.add(createEnemy(units.get(j).getEnemyType(),units.get(j).getX(),units.get(j).getY()));
    			   }
    			   this.current_wave++;
    			   Log.d("waves", "wyszedlem z fora");
    		   }
    	   }
       }

	public List<EnemySprite> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<EnemySprite> enemies) {
		this.enemies = enemies;
	}

	public List<AttackSprite> getAttack() {
		return attack;
	}

	public void setAttack(List<AttackSprite> attack) {
		this.attack = attack;
	}

	public List<TempSprite> getTemps() {
		return temps;
	}

	public void setTemps(List<TempSprite> temps) {
		this.temps = temps;
	}

	public List<EnemyAttack> getEnemyAttacks() {
		return enemyAttacks;
	}

	public void setEnemyAttacks(List<EnemyAttack> enemyAttacks) {
		this.enemyAttacks = enemyAttacks;
	}

	public List<Wave> getWaves() {
		return waves;
	}

	public void setWaves(List<Wave> waves) {
		this.waves = waves;
	}

	public Switcher getSwitchGod() {
		return switchGod;
	}

	public void setSwitchGod(Switcher switchGod) {
		this.switchGod = switchGod;
	}

	public Switcher getSwitchAttack() {
		return switchAttack;
	}

	public void setSwitchAttack(Switcher switchAttack) {
		this.switchAttack = switchAttack;
	}

	public Sprite getAmbrosia() {
		return ambrosia;
	}

	public void setAmbrosia(Sprite ambrosia) {
		this.ambrosia = ambrosia;
	}

	public int getLastGod() {
		return lastGod;
	}

	public void setLastGod(int lastGod) {
		this.lastGod = lastGod;
	}

	public int getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(int lastAttack) {
		this.lastAttack = lastAttack;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public GameLoopThread getGameLoopThread() {
		return gameLoopThread;
	}

	public void setGameLoopThread(GameLoopThread gameLoopThread) {
		this.gameLoopThread = gameLoopThread;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getCurrent_wave() {
		return current_wave;
	}

	public void setCurrent_wave(int current_wave) {
		this.current_wave = current_wave;
	}
	/*
	 * metoda zwracajaca liste wrogow w postaci serializable (lista unitow)
	 */
	public List<Unit> getSerializableEnemies(){
		List<Unit> serializableEnemies = new ArrayList<Unit>();
		for(int i = enemies.size()-1; i>=0; i--){
			Unit sEnemy = enemies.get(i).getThisAsUnit();
			serializableEnemies.add(sEnemy);
		}
		return serializableEnemies;
	}
	/*
	 * metoda zwracajaca liste atakow w postaci serializable (lista unitow)
	 */
	public List<Unit> getSerializableAttacks(){
		List<Unit> serializableAttacks = new ArrayList<Unit>();
		for(int i = attack.size()-1; i>=0; i--){
			Unit sAttack = attack.get(i).getThisAsUnit();
			serializableAttacks.add(sAttack);
		}
		return serializableAttacks;
	}
	/*
	 * metoda zwracajaca liste tempow w postaci serializable (lista unitow)
	 */
	public List<Unit> getSerializableTemps(){
		List<Unit> serializableTemps = new ArrayList<Unit>();
		for(int i = temps.size()-1; i>=0; i--){
			Unit sTemp = temps.get(i).getThisAsUnit();
			serializableTemps.add(sTemp);
		}
		return serializableTemps;
	}
	/*
	 * metoda zwracajaca liste atakow wrogow w postaci serializable (lista unitow)
	 */
	public List<Unit> getSerializableEnemyAttacks(){
		List<Unit> serializableEnemyAttacks = new ArrayList<Unit>();
		for(int i = enemyAttacks.size()-1; i>=0; i--){
			Unit sEnemyAttack = enemyAttacks.get(i).getThisAsUnit();
			serializableEnemyAttacks.add(sEnemyAttack);
		}
		return serializableEnemyAttacks;
	}
	/*
	 * metoda wypelniajaca liste wrogow na podstawie zserializowanej(?) listy
	 */
	public void setSerializableEnemies(List<Unit> sEnemies){
		for(int i= 0; i <=sEnemies.size()-1; i++){
			/*
			 * setter
			 */
			
			EnemySprite e = createEnemy(sEnemies.get(i).getEnemyType(),sEnemies.get(i).getX(),sEnemies.get(i).getY());
			e.setAll(sEnemies.get(i).getX(),
									sEnemies.get(i).getY(), 
									sEnemies.get(i).getLife(),
									sEnemies.get(i).getSlowedTimes(), 
									sEnemies.get(i).getState(), 
									sEnemies.get(i).getRange(), 
									sEnemies.get(i).getCurrentFrame());
									/*
									 * wlasnie skumalem ze kilka gowien z seterow
									 * sie powtarza ale juz chuj na ryj z tym :D
									 */
			enemies.add(e);
		}
		
	}
	/*
	 * metoda wypelniajaca liste atakow na podstawie zserializowanej(?) listy
	 */
	public void setSerializableAttacks(List<Unit> sAttacks){
		for(int i= 0; i <=sAttacks.size()-1; i++){
			/*
			 * setter
			 */
			AttackSprite a = createAttack(sAttacks.get(i).getAttackType(),sAttacks.get(i).getLevel(),sAttacks.get(i).getX(),sAttacks.get(i).getY());
			a.SetAll(sAttacks.get(i).getX(),
						sAttacks.get(i).getY(),
						sAttacks.get(i).getLife(),
						sAttacks.get(i).getDegree(),
						sAttacks.get(i).getRange(),
						sAttacks.get(i).getCurrentFrame(), 
						sAttacks.get(i).getRect(), 
						sAttacks.get(i).getXdistance());
			attack.add(a);
			
		}
		
	}
	/*
	 * metoda wypelniajaca liste atakow na podstawie zserializowanej(?) listy
	 */
	public void setSerializableTemps(List<Unit> sTemps){
		for(int i= 0; i <=sTemps.size()-1; i++){
			/*
			 * setter
			 */
			TempSprite t = createTemp(sTemps.get(i).getX(),sTemps.get(i).getY(),sTemps.get(i).getBonusType());
			t.setAll(sTemps.get(i).getX(), 
								sTemps.get(i).getY(), 
								sTemps.get(i).getLife(), 
								sTemps.get(i).getAmount(), 
								sTemps.get(i).getCurrentFrame());
			temps.add(t);
		}
		
	}
	/*
	 * metoda wypelniajaca liste atakow wroga na podstawie zserializowanej(?) listy
	 */
	public void setSerializableEnemyAttacks(List<Unit> sEnemyAttacks){
		for(int i= 0; i <=sEnemyAttacks.size()-1; i++){
			/*
			 * setter
			 */
			EnemyAttack ea = new EnemyAttack(enemyAttacks,this,
					sEnemyAttacks.get(i).getX(),
					sEnemyAttacks.get(i).getY(),
					sEnemyAttacks.get(i).getXdestination(),
					sEnemyAttacks.get(i).getYdestination(),
					sEnemyAttacks.get(i).getSpeed(),
					sEnemyAttacks.get(i).getEnemyAttackType()
					);
			ea.setAll(	sEnemyAttacks.get(i).getLife(),
										sEnemyAttacks.get(i).getAttackState(),
										sEnemyAttacks.get(i).getCurrentFrame(),
										sEnemyAttacks.get(i).getDegree());
			enemyAttacks.add(ea);
		}
		
	}
}