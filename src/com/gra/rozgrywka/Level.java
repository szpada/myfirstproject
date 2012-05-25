package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Level implements Serializable{
	public enum difficulty{tutorial1, tutorial2, tutorial3, 
		c1l1,c1l2,c1l3,c1l4,c1l5,c1l6,c1l7};
	private List<Wave> waves = new ArrayList<Wave>();
	
	private long time_goal;	//szacowany czas rozegrania levelu
	private int upgrade_point = 1;
	private boolean completed;
	public Level(difficulty dif){
		switch(dif){
		/*
		 * fale sa dodawane w zaleznosci od wybranego poziomu trudnosci
		 */
									/*
									 * 	TUTORIAL
									 */
		case tutorial1:
			// FALA #1
			waves.add(new Wave(enemyType.knight,enemyType.fire_boss));
			this.time_goal = 10000;
			break;
		case tutorial2:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.balista));
			// FALA #2
			waves.add(new Wave(enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight));
			this.time_goal = 20000;
			break;
		case tutorial3:
			// FALA #1
			waves.add(new Wave(enemyType.knight,enemyType.catapult,enemyType.knight));
			// FALA #2
			waves.add(new Wave(enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight));
			// FALA #3
			waves.add(new Wave(enemyType.knight,enemyType.knight,enemyType.catapult,enemyType.knight,
					enemyType.knight));
			this.time_goal = 50000;
			this.upgrade_point = 1;
			break;
									/*
									 *  CHAPTER 1
									 */
		case c1l1:
			// FALA #1
			waves.add(new Wave(enemyType.catapult));
			this.time_goal = 20000;
			break;
		case c1l2:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult));
			this.time_goal = 30000;
			break;
		case c1l3:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult));
			this.time_goal = 40000;
			break;
		case c1l4:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult));
			this.time_goal = 50000;
			break;
		case c1l5:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult,
					enemyType.catapult));
			this.time_goal = 60000;
			break;
		case c1l6:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult,
					enemyType.catapult,enemyType.catapult));
			this.time_goal = 70000;
			break;
		case c1l7:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult,
					enemyType.catapult,enemyType.catapult,enemyType.catapult));
			this.time_goal = 80000;
			this.upgrade_point = 1;
			break;
		}
	}
	public List<Wave> getWave(){
		return this.waves;
	}
	public int waveSize(){
		return waves.size();
	}
	public long getTimeGoal(){
		return this.time_goal;
	}
	public int getUpgradePoints(){
		return this.upgrade_point;
	}
	public boolean getCompleted(){
		return this.completed;
	}
	public void setCompleted(boolean state){
		this.completed = state;
	}
}
