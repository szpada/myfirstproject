/**
 * 
 */
package com.gra.drzewko;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gra.rozgrywka.Player;

public class TreeView extends SurfaceView {											//pogladowe wartosci atakow
																					//mana cost, dmg, range
	private TreeButtons shock;// = 				new TreeButtons(this,115,525,0,0,	2,1,1);
	private TreeButtons multi_shock;// = 			new TreeButtons(this,115,400,1,0,	4,3,3);
	private TreeButtons charge_defense;// = 		new TreeButtons(this,45,270,2,0,	5,1,1);
	private TreeButtons storm;//  = 				new TreeButtons(this,185,270,3,0,	8,4,5);
	private TreeButtons thunder;//  = 				new TreeButtons(this,115,145,4,0,	6,8,6);
	
	private TreeButtons fireball;// = 				new TreeButtons(this,115,525,0,1,	2,1,1);
	private TreeButtons fireball_shot;// = 		new TreeButtons(this,45,400,1,1,	2,3,1);
	private TreeButtons firewall;// = 				new TreeButtons(this,185,400,2,1,	1,1,1);
	private TreeButtons meteor;// =				new TreeButtons(this,115,270,3,1,	6,8,4);
	
	private TreeButtons tornado;// = 				new TreeButtons(this,115,530,0,2,	2,2,2);
	private TreeButtons waterSplash;// = 			new TreeButtons(this,115,395,1,2,	4,3,4);
	
	private List<TreeButtons> attacks = new ArrayList<TreeButtons>();
	
	private boolean zeus_first_time = true;
	private boolean hephaestus_first_time = true;
	private boolean poseidon_first_time = true;
	/*
	 * odkomentowac gdy beda poprawne
	 */
	private float h_factor;
	private float w_factor;
	private long lastClick;
	private int coolDown = 200;
	private int points;
	private int currentAttack = 0;
	private int currentGod = 0;
	private int maxUpgrade = 3;
							/*
							*	0 - zeus
							*	1 - hefajstos
							*	2 - posejdon
							*/
	private TreeLoopThread treeLoopThread;
	
	private TreeButtons upgrade;
	private TreeButtons backGround;
	private TreeButtons currentTree;
	private InfoSprite info;
	private TreeButtons zeus;
	private TreeButtons hephaestus;
	private TreeButtons poseidon;
	/*
	 * player - potrzebny do testowania drzewa rozwoju
	 * pozniej bedzie przekazywany jako argument w konstruktorze
	 */
	private int base[][] = {
			/*
			 * 0 - 3 - poziom ataku (0 oznacza ze mozna wsadzic punkt w dany atak
			 * -1 	 - nie mozna wsadzic punktu w ten atak póki ataki nizszego poziomu nie zostan¹ wybrane
			 * -2	 - atak nie istnieje
			 */
			{1,0,-1,-1,-1},	//ELEKTRYCZNE
    		{0,-1,-1,-1,-2},	//OGNIEN
    		{0,-1,-2,-2,-2},	//WODA
    		{-2,-2,-2,-2,-2},	//FIZYCZNE
    		{-2,-2,-2,-2,-2}
    };
	private Player player;// = new Player("pies",0,0,base,1000,1000,2,100,100, 0, 0);
	/**
	 * @param context
	 */
	
	
	/*
	 * odkomentowac jak bedzie wszystko dzialac - przekazywac tablice atakow a nie calego playera!
	 */
	public TreeView(Context context,double w_factor, double h_factor, Player player) {
//	public TreeView(Context context,double w_factor, double h_factor) {
		super(context);
		/*
		 * odkomentowac jak wartosci beda poprawnie przesylane
		 */
		this.player = player;
		Log.d("TreeView", "po przekazaniu " +Integer.toString(this.player.getUpgPoints()));
		this.base = player.getArray();
		this.points = player.getUpgPoints();
		this.h_factor = (float)h_factor;
 	   	this.w_factor = (float)w_factor;
        treeLoopThread = new TreeLoopThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {
               //@Override
               public void surfaceDestroyed(SurfaceHolder holder) {
               Log.d("GameView", "odpalam surfaceDestroyed");
                      boolean retry = true;
                      treeLoopThread.setRunning(false);
                      while (retry) {
                             try {
                                   treeLoopThread.join();
                                   retry = false;
                             } catch (InterruptedException e) {}
                      }
               }
               //@Override
               public void surfaceCreated(SurfaceHolder holder) {
                      createSprites();
                      treeLoopThread.setRunning(true);
                      treeLoopThread.start();
               }
               //@Override
               public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
               	
               }
        }); 
	}
	public void createAttackFirstTime(int god){
		switch(god){
		case 0:														//mana dmg range
			shock = 				new TreeButtons(this,115,525,0,0,	2,1,1);
								//dmg range mana
			shock.setUpgradeFactors(1, 0, 0);
			
			multi_shock = 			new TreeButtons(this,115,400,1,0,	4,3,3);
			multi_shock.setUpgradeFactors(1, 0, 0);
			
			charge_defense = 		new TreeButtons(this,45,270,2,0,	5,1,1);
			charge_defense.setUpgradeFactors(2, 0, 0);
			
			storm  = 				new TreeButtons(this,185,270,3,0,	8,4,5);
			storm.setUpgradeFactors(3, 0, 0);
			
			thunder  = 				new TreeButtons(this,115,145,4,0,	6,8,6);
			thunder.setUpgradeFactors(3, 2, 0);
			this.zeus_first_time = false;
			break;
		case 1:
			fireball = 				new TreeButtons(this,115,525,0,1,	2,1,1);
			fireball.setUpgradeFactors(1, 2, 0);
			
			fireball_shot = 		new TreeButtons(this,45,400,1,1,	2,3,1);
			fireball_shot.setUpgradeFactors(2, 0, 0);
			
			firewall = 				new TreeButtons(this,185,400,2,1,	1,1,1);
			firewall.setUpgradeFactors(1, 0, 0);
			
			meteor =				new TreeButtons(this,115,270,3,1,	1,2,4);
			meteor.setUpgradeFactors(4, 3, 0);
			this.hephaestus_first_time = false;
			break;
		case 2:
			tornado = 				new TreeButtons(this,115,530,0,2,	2,2,2);
			tornado.setUpgradeFactors(1, 0, 0);
			
			waterSplash = 			new TreeButtons(this,115,395,1,2,	4,3,4);
			waterSplash.setUpgradeFactors(1, 0, 0);
			this.poseidon_first_time = false;
			break;
		}
	}
	
	public void createSprites(){
		/*
		 * wywolanie funkcji tworzacej ataki dla peirwszego drzewa
		 */
		createAttackFirstTime(this.currentGod);
		/*
		 * odkomentowac jak beda poprawne wartosci
		 */
		//canvas.scale(this.w_factor, this.h_factor);
		/*
		 * przycisk upgrade
		 */
		this.upgrade = new TreeButtons(this,400,660,0);
		/*
		 * tlo (drzewo konkretnego ataku)
		 */
		this.backGround = new TreeButtons(this,0, 0, backgroundType.background);
		/*
		 * drzewo rozwoju
		 */
		this.currentTree = new TreeButtons(this,8, 100, backgroundType.tree);
		/*
		 * informacje o wybranym ataku
		 */
		this.info = new InfoSprite(this,0,650,0,0);
		/*
		 * przyciski sluzace do wyboru drzewa
		 */
		this.zeus = new TreeButtons(this,320,20,backgroundType.zeus);
		this.poseidon = new TreeButtons(this,320,147,backgroundType.poseidon);
		this.hephaestus = new TreeButtons(this,320,270,backgroundType.hephaestus);
		
		info.setCurrentGod(shock.getGodNumber());
		info.setCurrentAttack(shock.getAttackNumber());
		info.setDmg(shock.getDmg());
		info.setManaCost(shock.getManaCost());
		info.setRange(shock.getRange());
		/*
		 * dodane atakow do listy
		 */
		attacks.add(shock);
		attacks.add(multi_shock);
		attacks.add(charge_defense);
		attacks.add(storm);
		attacks.add(thunder);
		
		this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
		this.currentTree.setCurrentGod(0);
		info.setCurrentAttack(this.currentAttack);
		info.setCurrentGod(this.currentGod);
	}
	public void createAttacks(int god){
		this.attacks.removeAll(attacks);
		switch(god){
		case 0:
			/*
			 * zeus
			 */
		attacks.add(shock);
		attacks.add(multi_shock);
		attacks.add(charge_defense);
		attacks.add(storm);
		attacks.add(thunder);			
			break;
		case 1:
			/*
			 * hefajstos
			 */
		attacks.add(fireball);
		attacks.add(fireball_shot);
		attacks.add(firewall);
		attacks.add(meteor);
			break;
		case 2:
			/*
			 * posejdon
			 */
		attacks.add(tornado);
		attacks.add(waterSplash);
			break;
		}
	}
	public void onDraw(Canvas canvas){
		canvas.scale(this.w_factor, this.h_factor);
		this.zeus.onDraw(canvas);
		this.poseidon.onDraw(canvas);
		this.hephaestus.onDraw(canvas);
		this.backGround.onDraw(canvas);
		for(int i = 0; i < attacks.size(); i++){
			attacks.get(i).onDraw(canvas);
		}
		this.currentTree.onDraw(canvas);
		this.info.onDraw(canvas);
		this.upgrade.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		canvas.drawText("Points : " + this.points, 10, 10, paint);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		
		
		if (event.getAction() == MotionEvent.ACTION_UP && System.currentTimeMillis() - lastClick > coolDown) {
			float x = event.getX();
			float y = event.getY();
				/*
			 * odkomentowac jak juz beda przekazywane wlasciwe wartosci w konstruktorze
			 */
			x = x / this.w_factor;
			y = y / this.h_factor;
			
			
				lastClick = System.currentTimeMillis();
				if (zeus.checkCollision((int) x, (int) y)) {
					if (this.zeus_first_time) {
						createAttackFirstTime(0);
					}
					if (this.currentGod != 0) {
						createAttacks(0);
					}
					this.currentTree.setCurrentGod(0);
					this.currentGod = 0;
				} else if (hephaestus.checkCollision((int) x, (int) y)) {
					if (this.hephaestus_first_time) {
						createAttackFirstTime(1);
					}
					if (this.currentGod != 1) {
						createAttacks(1);
					}
					this.currentTree.setCurrentGod(2);
					this.currentGod = 1;
				} else if (poseidon.checkCollision((int) x, (int) y)) {
					if (this.poseidon_first_time) {
						createAttackFirstTime(2);
					}
					if (this.currentGod != 2) {
						createAttacks(2);
					}
					this.currentTree.setCurrentGod(1);
					this.currentGod = 2;
				}
				for (int i = 0; i <= attacks.size() - 1; i++) {
					if (attacks.get(i).checkCollision((int) x, (int) y)) {
						this.currentAttack = attacks.get(i).getAttackNumber();
						this.upgrade
								.setUpgrade(base[this.currentGod][this.currentAttack]);
						info.setCurrentGod(attacks.get(i).getGodNumber());
						info.setCurrentAttack(attacks.get(i).getAttackNumber());
						info.setDmg(attacks.get(i).getDmg());
						info.setManaCost(attacks.get(i).getManaCost());
						info.setRange(attacks.get(i).getRange());
					}
				}
				if (this.upgrade.checkCollision((int) x, (int) y)) {
					if (!performUpgrade(this.currentGod, this.currentAttack)) {
						//zaswiec punktami
					} else {
						this.player.addUpgPoints(-1);//odejmij chujowi punkty
						for (int i = 0; i <= attacks.size() - 1; i++) {
							if (attacks.get(i).getGodNumber() == this.currentGod
									&& attacks.get(i).getAttackNumber() == this.currentAttack) {
								attacks.get(i).setUpgrade(upgrade.getUpgrade());
								info.setDmg(attacks.get(i).getDmg());
								info.setManaCost(attacks.get(i).getManaCost());
								info.setRange(attacks.get(i).getRange());
							}
						}
					}
				}
			
		}
		return true;
	}
	public boolean performUpgrade(int god, int attack){
		if(this.points <= 0){
			return false;
		}
		else{
			/*
			 * zaleznosci w drzewach
			 */
			switch(god){
			case 0:
				/*
				 * zeus				0
				 * 					1
				 * 				2		3
				 * 					4
				 */
				if(attack == 0){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						this.points--;
						this.base[god][attack]++;
						this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						if(this.base[god][1] == -1){
							this.base[god][1] = 0;
						}
					}
				}
				if(attack == 1){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][0] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][2] == -1){
								this.base[god][2] = 0;
							}
							if(this.base[god][3] == -1){
								this.base[god][3] = 0;
							}
						}
					}
				}
				if(attack == 2){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][1] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][3] > 0){
								this.base[god][4] = 0;
							}
						}
					}
				}
				if(attack == 3){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][1] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][2] > 0){
								this.base[god][4] = 0;
							}
						}
					}
				}
				if(attack == 4){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][2] > 0 && this.base[god][3] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						}
					}
				}
				if(upgrade.getUpgrade() >= this.maxUpgrade){
					this.upgrade.setUpgrade(4);
				}
				break;
			case 1:
				/*
				 * hefajstos	0
				 * 			1		2
				 * 				3
				 */
				if(attack == 0){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						this.points--;
						this.base[god][attack]++;
						this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						if(this.base[god][1] == -1){
							this.base[god][1] = 0;
						}
						if(this.base[god][2] == -1){
							this.base[god][2] = 0;
						}
					}
				}
				if(attack == 1){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][0] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][2] > 0){
								this.base[god][3] = 0;
							}
						}
					}
				}
				if(attack == 2){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][0] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][1] > 0){
								this.base[god][3] = 0;
							}
						}
					}
				}
				if(attack == 3){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][1] > 0  && this.base[god][2] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
							if(this.base[god][2] > 0){
								this.base[god][4] = 0;
							}
						}
					}
				}
				if(upgrade.getUpgrade() >= this.maxUpgrade){
					this.upgrade.setUpgrade(4);
				}
				break;
			case 2:
				/*
				 * posejdon		0
				 * 				1
				 */
				if(attack == 0){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						this.points--;
						this.base[god][attack]++;
						this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						if(this.base[god][1] == -1){
							this.base[god][1] = 0;
						}
					}
				}
				if(attack == 1){
					if(upgrade.getUpgrade() < this.maxUpgrade && this.base[god][attack] != -2){
						if(this.base[god][0] > 0){
							this.points--;
							this.base[god][attack]++;
							this.upgrade.setUpgrade(base[this.currentGod][this.currentAttack]);
						}
					}
				}
				if(upgrade.getUpgrade() >= this.maxUpgrade){
					this.upgrade.setUpgrade(4);
				}
				break;
			}
			return true;
		}
	}
	public int[][] getArray(){
		return this.base;
	}
	public void setArray(int[][] base){
		this.base = base;
	}
}
