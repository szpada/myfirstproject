package com.gra.czaptery;

public class LevelUnit {
	private int chapter;
	private int id;
	private int stars;
	private boolean active;
	
	public LevelUnit(int chapter, int id, int stars, boolean active){
		this.chapter = chapter;
		this.id = id;
		this.stars = stars;
		this.active = active;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public void setChapter(int chapter){
		this.chapter = chapter;
	}
	public int getChapter() {
		return chapter;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
