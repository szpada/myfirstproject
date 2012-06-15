package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

enum formation{C,O,X,trianlge,square,line,double_line,triple_line};

public class Wave implements Serializable{
	private List<Unit> units = new ArrayList<Unit>();
	private formation form;
	
	public Wave(formation form,enemyType enemy, enemyType...enemies){
		int x = 10;
		int y = -64;
		int counter = 1;
		for (enemyType enm : enemies) {
			counter++;
		}
		switch(form){
		case C:
			break;
		case line:
			int space = 460/(counter+1);
			x += space;
			units.add(new Unit(enemy,x,y));
			for (enemyType enm : enemies) {
				x += space;
				units.add(new Unit(enm,x,y));
			}
			break;
		case double_line:
			space = 400/(counter/2+1);
			//x += space;
			units.add(new Unit(enemy,x,y));
			for (int i = 0; i < enemies.length; i++){//(enemyType enm : enemies) {
				x += space;
				if(i < counter/2 + 1){
					units.add(new Unit(enemy,x,y));
					if(i >= counter/2){
						x = 10;
					}
				}
				else{
					units.add(new Unit(enemy,x,y - 64));
				}
			}
			break;
		case triple_line:
			space = 460/(counter+1);
			x += space;
			units.add(new Unit(enemy,x,y));
			for (enemyType enm : enemies) {
				x += space;
				units.add(new Unit(enm,x,y));
			}
			break;
		case O:
			break;
		case square:
			break;
		case X:
			break;
		}
	}
	public List<Unit> getUnits(){
		return this.units;
	}
}
