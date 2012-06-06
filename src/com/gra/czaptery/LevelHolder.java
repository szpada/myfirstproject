package com.gra.czaptery;

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
	
	public void updateLevels(PlayersResults res) {
		for (LevelChain lc : levels) {

			lc.setActive(res.getActive(lc.getChapter(), lc.getId()));
			lc.setComplited(res.getComplited(lc.getChapter(), lc.getId()));
			lc.setStars(res.getStars(lc.getChapter(), lc.getId()));
		}
	}
}
