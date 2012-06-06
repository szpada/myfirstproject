package com.gra.czaptery;

import java.io.Serializable;
import java.util.HashMap;

public class PlayersResults implements Serializable{
	
	HashMap resultsActive = new HashMap();

	HashMap resultsComplited = new HashMap();

	HashMap resultsStars = new HashMap();
	
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
	
	
	public void setActive(int chapter, int id, boolean act) {
		resultsActive.put(100*chapter+id, act);
		
		
	}
	
	public void setComplited(int chapter, int id, boolean comp) {
		resultsComplited.put(100*chapter+id, comp);
		
		
	}
	
	public void setStars(int chapter, int id, int stars) {
		resultsStars.put(100*chapter+id, stars);
	
	
	}

}
