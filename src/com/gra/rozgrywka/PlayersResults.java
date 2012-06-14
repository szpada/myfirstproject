package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.HashMap;

public class PlayersResults implements Serializable{
	
	HashMap<Integer, Boolean> resultsActive; //= new HashMap<Integer, Boolean>();

	HashMap<Integer, Boolean> resultsComplited; //= new HashMap<Integer, Boolean>();

	HashMap<Integer, Integer> resultsStars; //= new HashMap<Integer, Integer>();
	
	public PlayersResults() { //init - domyslne wartosci
		
		resultsActive = new HashMap<Integer, Boolean>();
		resultsComplited = new HashMap<Integer, Boolean>();
		resultsStars = new HashMap<Integer, Integer>();
		setEverything(0,0,true,false,0);
		setEverything(1,0,true,false,0);
		setEverything(1,3,true,false,0);
		setEverything(2,0,true,false,0);
		setEverything(2,1,true,false,0);
		setEverything(2,2,true,false,0);
		setEverything(2,3,true,false,0);
	
	}
	
	
	public boolean getActive(int chapter, int id) {
		return resultsActive.containsKey(chapter*100+id) && (Boolean) resultsActive.get(chapter*100+id);
	}
	
	public boolean getComplited(int chapter, int id) {
		return resultsComplited.containsKey(chapter*100+id) && (Boolean) resultsComplited.get(chapter*100+id);
	}
	
	public int getStars(int chapter, int id) {
		if (resultsStars.containsKey(chapter*100+id)) { 
			return (Integer) resultsStars.get(chapter*100+id);
		}
		else {
			return 0;
		}
	}
	
	
	public void setEverything(int chapter, int id, boolean act, boolean comp, int stars) {
		setActive(chapter, id, act);
		setComplited(chapter, id, comp);
		setStars(chapter, id, stars);
	}
	
	
	private void setActive(int chapter, int id, boolean act) {
		resultsActive.put(100*chapter+id, act);
	}
	
	private void setComplited(int chapter, int id, boolean comp) {
		resultsComplited.put(100*chapter+id, comp);
	}
	
	private void setStars(int chapter, int id, int stars) {
		resultsStars.put(100*chapter+id, stars);
		if (!(resultsStars.containsKey(100*chapter+id) && resultsStars.get(100*chapter+id) > stars)) {
			resultsStars.put(100*chapter+id, stars);
		}
	}
}
