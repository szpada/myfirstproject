package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Level implements Serializable{
	public enum difficulty{tutorial1, tutorial2, tutorial3, 
		c1l1,c1l2,c1l3,c1l4,c1l5,c1l6,c1l7};
	private List<Wave> waves = new ArrayList<Wave>();
	
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
			waves.add(new Wave(enemyType.knight));
		case tutorial2:
			// FALA #1
			waves.add(new Wave(enemyType.knight,enemyType.knight));
			// FALA #2
			waves.add(new Wave(enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight));
			break;
		case tutorial3:
			// FALA #1
			waves.add(new Wave(enemyType.knight,enemyType.catapult,enemyType.knight));
			// FALA #2
			waves.add(new Wave(enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight));
			// FALA #3
			waves.add(new Wave(enemyType.knight,enemyType.knight,enemyType.catapult,enemyType.knight,
					enemyType.knight));
			break;
									/*
									 *  CHAPTER 1
									 */
		case c1l1:
			// FALA #1
			waves.add(new Wave(enemyType.catapult));
			break;
		case c1l2:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult));
			break;
		case c1l3:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult));
			break;
		case c1l4:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult));
			break;
		case c1l5:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult,
					enemyType.catapult));
			break;
		case c1l6:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult,
					enemyType.catapult,enemyType.catapult));
			break;
		case c1l7:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult,
					enemyType.catapult,enemyType.catapult,enemyType.catapult));
			break;
		}
	}
	public List<Wave> getWave(){
		return this.waves;
	}
	public int waveSize(){
		return waves.size();
	}
}
