package com.gra.rozgrywka;

import java.util.ArrayList;
import java.util.List;

enum difficulty{tutorial, basic};
enum army{knight_squad};

public class Wave {
	private List<Unit> units = new ArrayList<Unit>();
	
	public Wave(enemyType enemy, enemyType...enemies){
		int x = 0;
		int y = -64;
		int counter = 1;
		for (enemyType enm : enemies) {
			counter++;
		}
		int space = 460/(counter+1);
		x += space;
		units.add(new Unit(enemy,x,y));
		for (enemyType enm : enemies) {
			x += space;
			units.add(new Unit(enm,x,y));
		}
	}
	public List<Unit> getUnits(){
		return this.units;
	}
}
