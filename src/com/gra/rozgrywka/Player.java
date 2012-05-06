package com.gra.rozgrywka;

import android.util.Log;

enum attackType{shock, multi_shock, charge_defence, storm, thunder,
				fireball,fireball_shot, firewall, meteor, empty1,
				waterSplash, tornado, water_shield, flood, empty2,
				arow, spear, shield, trap, empty3,
				poison, consumption, corpse_explosion, pit, death_touch};
 
enum otherAttacks{chargeShieldAttack, thunder_shot};
				
/**
 * @author Maciej
 * akcje gracza, ataki, zmiany bogow, itd
 */
public class Player { 
	private String name;
	private int points;
	private int upgradePoints;
	private int currentGod = 0;
	private int currentAttack = 0;
	private int currentMana;
	private int maxMana;
	private int manaSpeed;
	private int godsAttacks[][];
	private int olympLife;
	private int olympMaxLife;
	private int luck = 1;
	
	public Player(String name, int points, int upgradePoints, int godsAttacks[][], int maxMana, int currentMana, int manaSpeed, int maxLife, int currentLife){ 
			//int zeus[],int hephaestus[],int poseidon[],int ares[],int hades[]){
		this.name = name;
		this.points = points;
		this.upgradePoints = upgradePoints;
		this.godsAttacks = godsAttacks;
		this.currentMana = currentMana;
		this.maxMana = maxMana;
		this.manaSpeed = manaSpeed;
		this.olympLife = currentLife;
		this.olympMaxLife = maxLife;
	}
	
	/*
	 * funkcja wybierajaca nastepny atak (w danym bogu)
	 */
	public void nextAttack(){
		this.currentAttack++;
		while(true){
			if(currentAttack < 5){
				if(godsAttacks[currentGod][currentAttack] <= 0){
					this.currentAttack++;
				}
				else {
					break;
				}
			}
			else{
				this.currentAttack = 0;
			}
		}
	}
	/*
	 * funkcja wybiera nastepnego boga
	 */
	public void nextGod(){
		this.currentGod++;
		this.currentAttack = 0;
		while(true){
			if(currentGod < 5){
				if(godsAttacks[currentGod][0] <= 0){
					currentGod++;
				}
				else {
					break;
				}
			}
			else{
				currentGod = 0;
			}
		}
	}
	public int getCurrentGod(){
		return this.currentGod;
	}
	public int getCurrentAttack(){
		return this.currentAttack;
	}
	public int getAttackLevel(){
		return this.godsAttacks[this.currentGod][this.currentAttack];
	}
	public attackType getAttackType(){
		int at = this.currentGod * 5 + this.currentAttack;
		for(attackType at1 : attackType.values()){
			if(at1.ordinal() == at){
				return at1;
			}
		}
		return null;
	}
	public void addMana(){
		if(this.currentMana + this.manaSpeed < this.maxMana){
			this.currentMana += this.manaSpeed;
		}
		else{
			this.currentMana = this.maxMana;
		}
	}
	public boolean manaForAttack(int mana){
		if(this.currentMana - mana < 0){
			return false;
		}
		else{
			this.currentMana += -mana;
			return true;
		}
	}
	public int getCurrentMana(){
		return this.currentMana;
	}
	public int getMana(){
		return this.maxMana;
	}
	public int getOlympLife(){
		return this.olympLife;
	}
	public void dmgToOlymp(int dmg){
		this.olympLife -= dmg;
	}
	public void addMana(int amount){
		this.currentMana += amount;
		if(this.currentMana > this.maxMana){
			this.currentMana = this.maxMana;
		}
	}
	public void addLife(int amount){
		this.olympLife += amount;
		if(this.olympLife > this.olympMaxLife){
			this.olympLife = this.olympMaxLife;
		}
	}
	public int getLuck(){
		return this.luck;
	}
}
