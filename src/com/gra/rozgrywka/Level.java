package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Level implements Serializable{
	public enum difficulty{tutorial1, tutorial2, tutorial3, 
		c1l1,c1l2,c1l3,c1l4,c1l5,c1l6,c1l7,
		c2l1,c2l2,c2l3,c2l4,c2l5,c2l6,c2l7};
	private List<Wave> waves = new ArrayList<Wave>();
	
	private long time_goal;	//szacowany czas rozegrania levelu
	private int upgrade_point = 1;
	private boolean completed;
	
	private int chapter;
	private int id;
	
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
			waves.add(new Wave(formation.triple_line,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight));
			this.time_goal = 10000;
			this.upgrade_point = 1;
			break;
		case tutorial2:
			// FALA #1
			waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.knight));
			this.time_goal = 20000;
			this.upgrade_point = 1;
			break;
		case tutorial3:
			// FALA #1
			waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.knight,enemyType.knight));
			// FALA #2
			waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight));
			this.time_goal = 50000;
			this.upgrade_point = 1;
			break;
									/*
									 *  CHAPTER 1
									 */
		case c1l1:
			// FALA #1
			waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.balista,enemyType.knight));
			// FALA #2
			waves.add(new Wave(formation.double_line,enemyType.balista,enemyType.knight,enemyType.balista,enemyType.knight, enemyType.balista));
			this.time_goal = 20000;
			break;
		case c1l2:
			// FALA #1
			waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight));
			// FALA #2
			waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.catapult,enemyType.knight,enemyType.knight,enemyType.catapult,enemyType.knight));
			this.time_goal = 30000;
			break;
		case c1l3:
			waves.add(new Wave(formation.double_line,enemyType.fire_imp,enemyType.dragon,enemyType.fire_imp));
			waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.knight,enemyType.dragon,enemyType.knight,enemyType.knight));
			waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.knight,enemyType.dragon,enemyType.dragon,enemyType.knight,enemyType.knight));
			this.time_goal = 40000;
			break;
		case c1l4:
			// FALA #1
			waves.add(new Wave(formation.double_line,enemyType.balista, enemyType.balista, enemyType.balista));
			waves.add(new Wave(formation.double_line,enemyType.balista, enemyType.catapult, enemyType.balista));
			this.time_goal = 50000;
			break;
		case c1l5:
			// FALA #1
			waves.add(new Wave(formation.double_line,enemyType.knight_general,enemyType.balista,enemyType.catapult,enemyType.catapult,enemyType.balista,enemyType.knight_general));
			this.time_goal = 60000;
			break;
		case c1l6:
			// FALA #1
			waves.add(new Wave(formation.double_line,enemyType.knight_general,enemyType.knight,enemyType.knight_general));
			// FALA #2
			waves.add(new Wave(formation.double_line,enemyType.catapult,enemyType.knight_general,enemyType.knight,enemyType.knight_general,enemyType.catapult));
			// FALA #3
			waves.add(new Wave(formation.double_line,enemyType.balista,enemyType.catapult,enemyType.knight_general,enemyType.knight_general,enemyType.knight_general,enemyType.catapult,enemyType.balista));
			this.time_goal = 70000;
			break;
		case c1l7:
			// FALA #1
			waves.add(new Wave(formation.double_line,enemyType.dragon,enemyType.dragon,enemyType.dragon,enemyType.dragon,enemyType.dragon));
			this.time_goal = 80000;
			this.upgrade_point = 1;
			/*
			 *  CHAPTER 1
			 */
		case c2l1:
		// FALA #1
		waves.add(new Wave(formation.double_line,enemyType.fire_titan));
		this.time_goal = 20000;
		break;
		case c2l2:
		// FALA #1
		waves.add(new Wave(formation.double_line,enemyType.fire_imp,enemyType.fire_titan,enemyType.fire_imp));
		this.time_goal = 30000;
		break;
		case c2l3:
		// FALA #1
		waves.add(new Wave(formation.double_line,enemyType.fire_imp,enemyType.fire_imp,enemyType.fire_titan,enemyType.fire_imp,enemyType.fire_imp));
		this.time_goal = 40000;
		break;
		case c2l4:
		// FALA #1
		waves.add(new Wave(formation.double_line,enemyType.fire_imp,enemyType.fire_imp,enemyType.fire_imp,enemyType.fire_titan,enemyType.fire_imp,enemyType.fire_imp,enemyType.fire_imp));
		this.time_goal = 50000;
		break;
		case c2l5:
		// FALA #1
		waves.add(new Wave(formation.double_line,enemyType.knight_general,enemyType.fire_titan,enemyType.knight_general));	
		waves.add(new Wave(formation.double_line,enemyType.fire_titan,enemyType.fire_titan));
		this.time_goal = 60000;
		break;
		case c2l6:
		// FALA #1
		waves.add(new Wave(formation.double_line,enemyType.knight,enemyType.knight,enemyType.knight,enemyType.fire_titan,enemyType.knight,enemyType.knight,enemyType.knight));
		waves.add(new Wave(formation.double_line,enemyType.fire_titan,enemyType.fire_titan,enemyType.fire_titan));
		this.time_goal = 70000;
		break;
		case c2l7:
		// FALA #1
		waves.add(new Wave(formation.double_line,enemyType.knight_general,enemyType.knight_general,enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.knight_general,enemyType.knight_general));
		waves.add(new Wave(formation.double_line,enemyType.catapult,enemyType.fire_titan,enemyType.catapult));
		waves.add(new Wave(formation.double_line,enemyType.fire_titan,enemyType.fire_titan,enemyType.fire_titan));
		waves.add(new Wave(formation.double_line,enemyType.fire_boss));
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
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
	public int getChapter() {
		return chapter;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
