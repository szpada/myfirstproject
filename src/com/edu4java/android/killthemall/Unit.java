package com.edu4java.android.killthemall;

public class Unit {
	private int x;
	private int y;
	private enemyType enemy;
	
	public Unit(enemyType enemy, int x, int y){
		this.enemy = enemy;
		this.x = x;
		this.y = y;
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
	
}
