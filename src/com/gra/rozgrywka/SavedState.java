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
//	private List<EnemySprite> enemies = new ArrayList<EnemySprite>();
//    private List<AttackSprite> attack = new ArrayList<AttackSprite>();
//    private List<TempSprite> temps = new ArrayList<TempSprite>();
//    private List<EnemyAttack> enemyAttacks = new ArrayList<EnemyAttack>();
//    private List<Wave> waves = new ArrayList<Wave>();
    private Level level;
    private Player player;
    
    public SavedState() {
    	
    }


//	public List<EnemySprite> getEnemies() {
//		return enemies;
//	}
//
//
//	public void setEnemies(List<EnemySprite> enemies) {
//		this.enemies = enemies;
//	}
//
//
//	public List<AttackSprite> getAttack() {
//		return attack;
//	}
//
//
//	public void setAttack(List<AttackSprite> attack) {
//		this.attack = attack;
//	}
//
//
//	public List<TempSprite> getTemps() {
//		return temps;
//	}
//
//
//	public void setTemps(List<TempSprite> temps) {
//		this.temps = temps;
//	}
//
//
//	public List<EnemyAttack> getEnemyAttacks() {
//		return enemyAttacks;
//	}
//
//
//	public void setEnemyAttacks(List<EnemyAttack> enemyAttacks) {
//		this.enemyAttacks = enemyAttacks;
//	}


//	public List<Wave> getWaves() {
//		return waves;
//	}
//
//
//	public void setWaves(List<Wave> waves) {
//		this.waves = waves;
//	}


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

    
}
