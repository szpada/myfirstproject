package com.gra.rozgrywka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maciej
 * Contains a complete state of the game that can be retrieved at further time
 */
public class SavedState 
	implements Serializable
	{
	private List<Unit> enemies = new ArrayList<Unit>();
	private List<Unit> attack = new ArrayList<Unit>();
	private List<Unit> temps = new ArrayList<Unit>();
	private List<Unit> enemyAttacks = new ArrayList<Unit>();
	
    private List<Wave> waves = new ArrayList<Wave>();
    private Level level;
    private Player player;
    private int current_wave;
    
    public SavedState() {
    	
    }

/**
 * @author Szpada
 * 
 * Gettery i settery do list wrogów, atakow i tempow (na podstawie ktorych potem stworzymy jednostki na planszy)
 */
	public List<Unit> getEnemies() {
		return enemies;
	}
	
	public void setEnemies(List<Unit> enemies) {
		this.enemies = enemies;
	}
	
	public List<Unit> getAttack() {
		return attack;
	}
	
	public void setAttack(List<Unit> attack) {
		this.attack = attack;
	}
	
	public List<Unit> getTemps() {
		return temps;
	}
	
	public void setTemps(List<Unit> temps) {
		this.temps = temps;
	}
	
	public List<Unit> getEnemyAttacks() {
		return enemyAttacks;
	}
	
	public void setEnemyAttacks(List<Unit> enemyAttacks) {
		this.enemyAttacks = enemyAttacks;
	}

	public List<Wave> getWaves() {
		return waves;
	}


	public void setWaves(List<Wave> waves) {
		this.waves = waves;
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public int getCurrent_wave() {
		return current_wave;
	}


	public void setCurrent_wave(int current_wave) {
		this.current_wave = current_wave;
	}

    
}
