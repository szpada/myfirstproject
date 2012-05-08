package com.gra.rozgrywka;

import java.io.Serializable;

import android.graphics.Rect;

public class Unit implements Serializable {
	private int x;
	private int y;
	private int lvl;
	private int life;
	private int slowed_times;
	private int range;
	private int currentFrame;
	private int slow_times;
	private int x_distance;
	private int dmg;
	private int amount;
	private int x_destination;
	private int y_destination;
	private int speed;
	private float degree;
	
	private Rect rect;
	private enemyType enemy;
	private attackType attack;
	private bonusType bonus;
	private enemyAttackType enemyAttack;
	private state st;
	private attackState attackstate;
	
	
	
	public Unit(enemyType enemy, int x, int y){
		this.enemy = enemy;
		this.x = x;
		this.y = y;
	}
	/*
	 * konstruktor wroga (do odtwarzania jednostek na mapie);
	 */
	public Unit(enemyType enemy, int x, int y, int life, int slow_times, state st, int range, int currentFrame){
		this.enemy = enemy;
		this.x = x;
		this.y = y;
		this.life = life;
		this.slowed_times = slow_times;
		this.st = st;
		this.range = range;
		this.currentFrame = currentFrame;
	}
	/*
	 * konstruktor dla ataku gracza
	 */
	public Unit(attackType attack,int level,int x, int y, int life, int currentFrame, float degree,int dmg,int range,Rect rect,int x_distance){
		this.attack = attack;
		this.lvl = level;
		this.x = x;
		this.y = y;
		this.life = life;
		this.degree = degree;
		this.range = range;
		this.currentFrame = currentFrame;
		this.rect = rect;
		this.x_distance = x_distance;
	}
	/*
	 * konstruktor dla tempow
	 */
	public Unit(bonusType bonus,int x, int y, int life, int amount, int currentFrame){
		this.bonus = bonus;
		this.x = x;
		this.y = y;
		this.life = life;
		this.amount = amount;
		this.currentFrame = currentFrame;
	}
	/*
	 * konstruktor dla atakow wroga
	 */
	public Unit(enemyAttackType enemyAttack,int x, int y, int life, attackState state, int currentFrame, float degree, int x_destination, int y_destination, int speed){
		this.enemyAttack = enemyAttack;
		this.x = x;
		this.y = y;
		this.life = life;
		this.attackstate = state;
		this.currentFrame = currentFrame;
		this.degree = degree;
		this.x_destination = x_destination;
		this.y_destination = y_destination;
		this.speed = speed;
	}
	public enemyType getEnemyType(){
		return this.enemy;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getLevel(){
		return this.lvl;
	}
	public Unit getUnit(){
		return this;
	}
	public int getLife(){
		return this.life;
	}
	public int getSlowedTimes(){
		return this.slow_times;
	}
	public int getRange(){
		return this.range;
	}
	public int getCurrentFrame(){
		return this.currentFrame;
	}
	public int getXdistance(){
		return this.x_distance;
	}
	public int getDmg(){
		return this.dmg;
	}
	public int getAmount(){
		return this.amount;
	}
	public int getXdestination(){
		return this.x_destination;
	}
	public int getYdestination(){
		return this.y_destination;
	}
	public int getSpeed(){
		return this.speed;
	}
	public float getDegree(){
		return this.degree;
	}
	public Rect getRect(){
		return this.rect;
	}
	public attackType getAttackType(){
		return this.attack;
	}
	public bonusType getBonusType(){
		return this.bonus;
	}
	public enemyAttackType getEnemyAttackType(){
		return this.enemyAttack;
	}
	public state getState(){
		return this.st;
	}
	public attackState getAttackState(){
		return this.attackstate;
	}
}
