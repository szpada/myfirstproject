package com.gra.rozgrywka;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author Szpada
 * 
 * Boss bossow - jednostka posiadajaca inna mechanike ruchu - zamiast schodzic tylko w dol w kierunku olimpu
 * chodzi na boki oraz teleportuje sie. Glwna jego bronia bedzie atakowanie z dystansu oraz 
 * wskrzeszanie jednostek. Dodatkowo moze sobie odnawiac zycie.
 */
public class BossSprite extends EnemySprite{

	public BossSprite(List<EnemySprite> enemies, GameView gameView,
			enemyType tp, int x, int y, List<EnemyAttack> ea) {
		super(enemies, gameView, tp, x, y, ea);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return super.getX();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}

	@Override
	public int getMaxSpeed() {
		// TODO Auto-generated method stub
		return super.getMaxSpeed();
	}

	@Override
	public void setMaxSpeed(int maxSpeed) {
		// TODO Auto-generated method stub
		super.setMaxSpeed(maxSpeed);
	}

	@Override
	public int getAttackSpeed() {
		// TODO Auto-generated method stub
		return super.getAttackSpeed();
	}

	@Override
	public void setAttackSpeed(int attackSpeed) {
		// TODO Auto-generated method stub
		super.setAttackSpeed(attackSpeed);
	}

	@Override
	public int getDmg() {
		// TODO Auto-generated method stub
		return super.getDmg();
	}

	@Override
	public void setDmg(int dmg) {
		// TODO Auto-generated method stub
		super.setDmg(dmg);
	}

	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange();
	}

	@Override
	public void setRange(int range) {
		// TODO Auto-generated method stub
		super.setRange(range);
	}

	@Override
	public state getSt() {
		// TODO Auto-generated method stub
		return super.getSt();
	}

	@Override
	public void setSt(state st) {
		// TODO Auto-generated method stub
		super.setSt(st);
	}

	@Override
	public void attackedWithDmg(int dmg, int power) {
		// TODO Auto-generated method stub
		super.attackedWithDmg(dmg, power);
	}

	@Override
	public int getLife() {
		// TODO Auto-generated method stub
		return super.getLife();
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return super.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return super.getHeight();
	}

	@Override
	public Rect getRect() {
		// TODO Auto-generated method stub
		return super.getRect();
	}

	@Override
	public boolean getDmgReady() {
		// TODO Auto-generated method stub
		return super.getDmgReady();
	}

	@Override
	public void setSlowTime(int slowedTimes_100) {
		// TODO Auto-generated method stub
		super.setSlowTime(slowedTimes_100);
	}

	@Override
	public warriorType getWarriorType() {
		// TODO Auto-generated method stub
		return super.getWarriorType();
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		super.setY(y);
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		super.setX(x);
	}

	@Override
	public double getDistance(EnemySprite es) {
		// TODO Auto-generated method stub
		return super.getDistance(es);
	}

	@Override
	public int getAmmoSpeed() {
		// TODO Auto-generated method stub
		return super.getAmmoSpeed();
	}

	@Override
	public enemyAttackType getAmmoType() {
		// TODO Auto-generated method stub
		return super.getAmmoType();
	}

	@Override
	public void dmgReady(boolean state) {
		// TODO Auto-generated method stub
		super.dmgReady(state);
	}

	@Override
	public Unit getThisAsUnit() {
		// TODO Auto-generated method stub
		return super.getThisAsUnit();
	}

	@Override
	public void setAll(int x, int y, int life, int slow_times, state st,
			int range, int currentFrame) {
		// TODO Auto-generated method stub
		super.setAll(x, y, life, slow_times, st, range, currentFrame);
	}
}
