package com.gra.rozgrywka;

import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


/**
 * @author Szpada
 * 
 * Boss bossow - jednostka posiadajaca inna mechanike ruchu - zamiast schodzic tylko w dol w kierunku olimpu
 * chodzi na boki oraz teleportuje sie. Glwna jego bronia bedzie atakowanie z dystansu oraz 
 * wskrzeszanie jednostek. Dodatkowo moze sobie odnawiac zycie.
 */

public class BossSprite extends EnemySprite{
	private boolean teleportStart = false;	//true - animacja od lewej do prawej, false odwrotnie (znikanie i pojawianie sie przy teleporcie)
	private boolean teleported = true;	//zmienna oznaczajaca ze boss jest teleportownay (znajduje sie poza ekranem)
	private boolean moveReady = false;
	private int timer = 0;				//zmienna potrzebna do okreslenia czestotliwosci atakow - jesli timer >= 100 - atak speed to atakujesz
	private int move_timer = 5;
	
	private int summons_per_attack = 2;
	private int shots_per_attack  = 4;
	private int heals_per_attack = 5;
	
	public BossSprite(List<EnemySprite> enemies, GameView gameView,
			enemyType tp, int x, int y, List<EnemyAttack> ea) {
		super(enemies, gameView, tp, 240, 300, ea);
		super.setCurrentFrame(super.getFrames() -1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onDraw(Canvas canvas) {
		if(super.getSt() == state.die){
    		int slowFrame = super.getCurrentFrame() / 10;
    		Log.d("BOSS", "DIE frame :" + slowFrame);
			int srcX = super.getWidth() * slowFrame;
			int srcY = super.getSt().ordinal() * super.getHeight();
			Rect src = new Rect(srcX, srcY, srcX + super.getWidth(), srcY + super.getHeight());
			Rect dst = new Rect(super.getX(), super.getY(), super.getX() + super.getWidth(), super.getY() + super.getHeight());
			canvas.drawBitmap(super.getBmp(), src, dst, null);
			if(slowFrame >= super.getFrames()){
				super.getEnemies().remove(this);
			}
			super.setCurrentFrame(super.getCurrentFrame() + 1);
    	}
    	else{
	    	Paint paint = new Paint();
			update();
			int srcX = super.getWidth() * super.getCurrentFrame();
			int srcY = super.getSt().ordinal() * super.getHeight();
			Rect src = new Rect(srcX, srcY, srcX + super.getWidth(), srcY + super.getHeight());
			Rect dst = new Rect(super.getX(), super.getY(), super.getX() + super.getWidth(), super.getY() + super.getHeight());
			canvas.drawBitmap(super.getBmp(), src, dst, null);
			
			/*
			 * wersja ze zwerzajacym sie paskiem zycia do srodka
			 */
			if(super.getLife() > super.getMaxLife()){
				paint.setColor(Color.WHITE);
				canvas.drawRect(super.getX(),super.getY() - 10,super.getX() + super.getWidth(), super.getY() - 5, paint);
				//canvas.drawRect(super.getX() + super.getWidth()/2 - (int)((double)super.getLife()/(double)super.getMaxLife() * (double)super.getWidth()), super.getY() - 10, super.getX() + super.getWidth()/2 + (int)((double)super.getLife()/(double)super.getMaxLife() * (double)super.getWidth())/2, super.getY()- 5, paint);
			}
			if(super.getMaxLife() > super.getLife() && super.getLife() >= 0){
				/*
				 * Pasek zycia
				 */
				if((double)super.getLife()/(double)super.getMaxLife() > 0.66 ){
					paint.setColor(Color.GREEN);
				}
				else if((double)super.getLife()/(double)super.getMaxLife() > 0.33 ){
					paint.setColor(Color.YELLOW);
				}
				else{
					paint.setColor(Color.RED);
				}
				canvas.drawRect(super.getX() + super.getWidth()/2 - (int)((double)super.getLife()/(double)super.getMaxLife() * (double)super.getWidth())/2, super.getY() - 10, super.getX() + super.getWidth()/2 + (int)((double)super.getLife()/(double)super.getMaxLife() * (double)super.getWidth())/2, super.getY()- 5, paint);
			}
    	}
//		if(super.getSt() != state.fight){
//			if(this.teleportStart){
//				Log.d("BOSS", "Teleport start klatka :" + super.getCurrentFrame());
//			}
//			else{
//				Log.d("BOSS", "Teleport stop klatka :" + super.getCurrentFrame());
//			}
//		}
	}
	
	@Override
	public void update(){
		if(super.getSt() != state.die){
			if(this.teleportStart && super.getSt() == state.walk){
				if(super.getCurrentFrame() >= super.getFrames()-1){
					//super.setY(-200);
					this.teleported = true;
					this.teleportStart = false;
					super.setCurrentFrame(super.getFrames()-1);
				}
				else{
					super.setCurrentFrame(super.getCurrentFrame() + 1);
				}
			}
			else if(!this.teleportStart && super.getSt() == state.walk){
				if(super.getCurrentFrame() <= 0){
					this.teleported = false;
					this.moveReady = true;
				}
				else{
					super.setCurrentFrame(super.getCurrentFrame() - 1);
				}
			}
			else{
				super.setCurrentFrame(super.getCurrentFrame() + 1);
			}
		}
		else{
			super.setCurrentFrame(super.getCurrentFrame() + 1);
		}
		
		
	    if(super.getLife() < 1){
		    this.setSt(state.die);
		    if(!super.isRecentStateChange()){
		    	super.setCurrentFrame(0);
		    	this.teleportStart = true;
			    super.setRecentStateChange(true);
		   }
	    }
	    if(this.teleportStart){
	    	if(super.getCurrentFrame() > super.getFrames()-1){
		    	//if(super.isRecentStateChange() && super.getSt() != state.die ){
	    		if(super.getSt() != state.die){
		    		super.setRecentStateChange(false);
		    		super.setCurrentFrame(0);
		    	}
		    }
	    }
	    else{
	    	if(super.getCurrentFrame() < 0){
		    	//if(super.isRecentStateChange() && super.getSt() != state.die ){
	    		if(super.getSt() != state.die){
		    		super.setRecentStateChange(false);
		    		super.setCurrentFrame(super.getFrames()-1);
		    	}
		    }
	    }
	    this.timer++;
	    if(this.move_timer < 0 && this.timer >= 100 - super.getAttackSpeed()){
	    	this.moveReady = true;
	    	this.timer = 0;
	    }
	    else{
	    	this.move_timer--;
	    	if(move_timer < 0){
	    		super.setSt(state.walk);
	    		super.setCurrentFrame(0);
	    	}
	    }
	    if(moveReady){
	    	this.moveReady = false;
	    	pickAction(super.getLife(), super.getMaxLife());
	    }
	    if(teleported){
	    	if(super.getCurrentFrame() == super.getFrames()-1){
		    	Random rnd = new Random();
		    	super.setX(rnd.nextInt(224) + 128);
		    	super.setY(rnd.nextInt(400));
		    	this.teleported = false;
		    	this.teleportStart = false;
	    	}
	    }
	    if(super.getSt() == state.shoot && super.getCurrentFrame() == super.getFrames() -1){
	    	Random rnd = new Random();
	    	super.getAttacks().add(new EnemyAttack(super.getAttacks(),
	    							super.getGameView(),
	    							super.getX() + super.getWidth()/2,
	    							super.getY() + super.getHeight()/2,
	    							rnd.nextInt(400) + 40,
	    							700,
	    							4,
	    							enemyAttackType.catapult_stone));
	    }
	    if(super.getSt() == state.summon && super.getCurrentFrame() == super.getFrames() -1){
	    	Random rnd = new Random();
	    	super.getEnemies().add(new EnemySprite(super.getEnemies(),
	    							super.getGameView(),
	    							enemyType.fire_imp,
	    							rnd.nextInt(400) + 40,
	    							super.getY() + super.getHeight(),
	    							super.getAttacks()));
	    }
	    if(super.getSt() == state.fight && super.getCurrentFrame() == super.getFrames() -1){
	    	Random rnd = new Random();
	    	super.setLife(super.getLife() + rnd.nextInt(25) + 10);
	    }
	}
	
	
	public void pickAction(int life, int maxLife){
		Random rnd = new Random();
		int action;
		if((double)life / (double)maxLife >= 0.66){
			action = rnd.nextInt(5);
		}
		else if((double)life / (double)maxLife > 0.33){
			action = rnd.nextInt(9);
		}
		else{
			action = rnd.nextInt(13);
		}
		/*
		 * test teleportu
		 */
		switch(action){
			/*	opcja dla :
			 * 0 - 100% life
			 */
			case 0:
				teleport();
				break;
			case 1:
			case 2:
				summon();
				break;
			case 3:
			case 4:
				shoot();
				break;
			/*	dodatkowe opcje dla (bardziej agreywny - wiecej strzelania i summonowania)
			 * 0 - 66% life
			 */
			case 5:
			case 6:
				shoot();
				break;
			case 7:
			case 8:
				summon();
				break;
			/*	dodatkowe opcje dla	(bardziej defensywny, wiecej teleportow i heala)
			 * 0 - 33% life
			 */
			case 9:
			case 10:
				heal();
				break;
			case 11:
			case 12:
				teleport();
				break;
		}
		
	}
	public void teleport(){
		this.move_timer = (super.getFrames() -1) * 2;
		super.setSt(state.walk);//walk = teleport
		super.setCurrentFrame(0);
		this.teleportStart = true;
	}
	public void summon(){
		this.move_timer = super.getFrames() * this.summons_per_attack;
		super.setSt(state.summon);
		super.setCurrentFrame(0);
		this.teleportStart = true;
	}
	public void heal(){
		this.move_timer = super.getFrames() * this.heals_per_attack;
		super.setSt(state.fight);//fight = heal
		super.setCurrentFrame(0);
		this.teleportStart = true;
	}
	public void shoot(){
		this.move_timer = super.getFrames() * this.shots_per_attack;
		super.setSt(state.shoot);
		super.setCurrentFrame(0);
		this.teleportStart = true;
	}
//	@Override
//	public int getX() {
//		// TODO Auto-generated method stub
//		return super.getX();
//	}
//
//	@Override
//	public int getY() {
//		// TODO Auto-generated method stub
//		return super.getY();
//	}
//
//	@Override
//	public int getMaxSpeed() {
//		// TODO Auto-generated method stub
//		return super.getMaxSpeed();
//	}
//
//	@Override
//	public void setMaxSpeed(int maxSpeed) {
//		// TODO Auto-generated method stub
//		super.setMaxSpeed(maxSpeed);
//	}
//
//	@Override
//	public int getAttackSpeed() {
//		// TODO Auto-generated method stub
//		return super.getAttackSpeed();
//	}
//
//	@Override
//	public void setAttackSpeed(int attackSpeed) {
//		// TODO Auto-generated method stub
//		super.setAttackSpeed(attackSpeed);
//	}
//
//	@Override
//	public int getDmg() {
//		// TODO Auto-generated method stub
//		return super.getDmg();
//	}
//
//	@Override
//	public void setDmg(int dmg) {
//		// TODO Auto-generated method stub
//		super.setDmg(dmg);
//	}
//
//	@Override
//	public int getRange() {
//		// TODO Auto-generated method stub
//		return super.getRange();
//	}
//
//	@Override
//	public void setRange(int range) {
//		// TODO Auto-generated method stub
//		super.setRange(range);
//	}
//
//	@Override
//	public state getSt() {
//		// TODO Auto-generated method stub
//		return super.getSt();
//	}
//
//	@Override
//	public void setSt(state st) {
//		// TODO Auto-generated method stub
//		super.setSt(st);
//	}
//
//	@Override
//	public void attackedWithDmg(int dmg, int power) {
//		// TODO Auto-generated method stub
//		super.attackedWithDmg(dmg, power);
//	}
//
//	@Override
//	public int getLife() {
//		// TODO Auto-generated method stub
//		return super.getLife();
//	}
//
//	@Override
//	public int getWidth() {
//		// TODO Auto-generated method stub
//		return super.getWidth();
//	}
//
//	@Override
//	public int getHeight() {
//		// TODO Auto-generated method stub
//		return super.getHeight();
//	}
//
//	@Override
//	public Rect getRect() {
//		// TODO Auto-generated method stub
//		return super.getRect();
//	}
//
//	@Override
//	public boolean getDmgReady() {
//		// TODO Auto-generated method stub
//		return super.getDmgReady();
//	}
//
//	@Override
//	public void setSlowTime(int slowedTimes_100) {
//		// TODO Auto-generated method stub
//		super.setSlowTime(slowedTimes_100);
//	}
//
//	@Override
//	public warriorType getWarriorType() {
//		// TODO Auto-generated method stub
//		return super.getWarriorType();
//	}
//
//	@Override
//	public void setY(int y) {
//		// TODO Auto-generated method stub
//		super.setY(y);
//	}
//
//	@Override
//	public void setX(int x) {
//		// TODO Auto-generated method stub
//		super.setX(x);
//	}
//
//	@Override
//	public double getDistance(EnemySprite es) {
//		// TODO Auto-generated method stub
//		return super.getDistance(es);
//	}
//
//	@Override
//	public int getAmmoSpeed() {
//		// TODO Auto-generated method stub
//		return super.getAmmoSpeed();
//	}
//
//	@Override
//	public enemyAttackType getAmmoType() {
//		// TODO Auto-generated method stub
//		return super.getAmmoType();
//	}
//
//	@Override
//	public void dmgReady(boolean state) {
//		// TODO Auto-generated method stub
//		super.dmgReady(state);
//	}
//
//	@Override
//	public Unit getThisAsUnit() {
//		// TODO Auto-generated method stub
//		return super.getThisAsUnit();
//	}
//
//	@Override
//	public void setAll(int x, int y, int life, int slow_times, state st,
//			int range, int currentFrame) {
//		// TODO Auto-generated method stub
//		super.setAll(x, y, life, slow_times, st, range, currentFrame);
//	}
}
