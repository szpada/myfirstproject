package com.edu4java.android.killthemall;

import java.util.ArrayList;
import java.util.List;

public class Level {
	private List<Wave> waves = new ArrayList<Wave>();
	
	public Level(difficulty dif){
		switch(dif){
		/*
		 * fale sa dodawane w zaleznosci od wybranego poziomu trudnosci
		 */
		case tutorial:
			// FALA #1
			waves.add(new Wave(enemyType.knight,enemyType.knight));
			// FALA #2
			waves.add(new Wave(enemyType.knight,enemyType.knight,enemyType.knight,enemyType.knight));
			// FALA #3
			waves.add(new Wave(enemyType.knight,enemyType.knight,enemyType.catapult,enemyType.knight,
					enemyType.knight));
			// FALA #4
			waves.add(new Wave(enemyType.catapult,enemyType.knight,enemyType.knight,enemyType.knight_general,
					enemyType.knight,enemyType.knight,enemyType.knight,enemyType.balista));
			// FALA #5
			waves.add(new Wave(enemyType.knight,enemyType.knight_general,enemyType.dragon,enemyType.knight_general,enemyType.knight));
			// FALA #6
			waves.add(new Wave(enemyType.fire_imp,enemyType.fire_imp,enemyType.fire_imp,enemyType.fire_titan,enemyType.fire_imp,
					enemyType.fire_imp,enemyType.fire_imp));
			break;
		case basic:
			// FALA #1
			waves.add(new Wave(enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult,
					enemyType.catapult,enemyType.catapult,enemyType.catapult,enemyType.catapult));
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
