package com.edu4java.android.killthemall;

public class Unit {
	private int x;
	private int y;
	private enemyType enemy;
	
	public Unit(enemyType enemy, int x, int y){
		this.x = x;
		this.y = y;
		this.enemy = enemy;
	}
}
