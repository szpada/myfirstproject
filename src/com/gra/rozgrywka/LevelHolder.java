package com.gra.rozgrywka;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Szpada
 *
 *klasa przechowujaca liste leveli w danym rozdziale - przekazywana jest do ChapterView
 * jako aktualna lista leveli (optymalizacja).
 */
public class LevelHolder {
	private List<LevelChain> levels = new ArrayList<LevelChain>();
	
	public LevelHolder(LevelChain...level){
		for(LevelChain lvl:level){
			levels.add(lvl);
		}
	}
	public List<LevelChain> getLevels(){
		return this.levels;
	}
}
