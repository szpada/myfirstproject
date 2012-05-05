package com.edu4java.android.killthemall;

import java.util.ArrayList;
import java.util.List;

enum level{tutorial, basic};
enum army{knight_squad};

public class Wave {
	private List<Unit> units = new ArrayList<Unit>();
	
	public Wave(level lvl, army army_squad){
		switch(lvl){
		case tutorial:
			for(int i = 1; i < 6; i++){
				units.add(new Unit(enemyType.knight,i*80,0));
			}
			break;
		case basic:
				units.add(new Unit(enemyType.dragon,50,0));
			break;
		}
	}
}
