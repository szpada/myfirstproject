package com.gra.achievementy;

import java.util.ArrayList;
import java.util.List;

import com.gra.rozgrywka.Player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AchievementView extends SurfaceView{
	private AchievementLoopThread thread;

	private List<AchievementInfo> achievements = new ArrayList<AchievementInfo>();
	
	private Paint paint;
	
	private Player player;
	
	public AchievementView(Context context,double w_factor, double h_factor, Player player) {
		super(context);
		/*
		 * odkomentowac jak wartosci beda poprawnie przesylane
		 */
	//    this.h_factor = (float)h_factor;
	//	   	this.w_factor = (float)w_factor;
		this.player = player;
		thread = new AchievementLoopThread(this);
	    getHolder().addCallback(new SurfaceHolder.Callback() {
	           //@Override
	           public void surfaceDestroyed(SurfaceHolder holder) {
	                  boolean retry = true;
	                  thread.setRunning(false);
	                  while (retry) {
	                         try {
	                        	 thread.join();
	                               retry = false;
	                         } catch (InterruptedException e) {}
	                  }
	           }
	           //@Override
	           public void surfaceCreated(SurfaceHolder holder) {
	        	   createSprites();
	        	   thread.setRunning(true);
	        	   thread.start();
	            }
	            //@Override
	            public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
	            	
	            }
	    }); 
	}
	public void onDraw(Canvas canvas){
		canvas.drawRect(0, 0, 480, 800, this.paint);
		for(int i = 0; i < this.achievements.size(); i++){
			achievements.get(i).onDraw(canvas);
		}
	}
	public void createSprites(){
		paint = new Paint();
		paint.setColor(Color.WHITE);
		AchievementInfo blood_bath = new AchievementInfo(this, 0, 100, true, acvType.blood_bath);
		AchievementInfo yeah = new AchievementInfo(this, 0, 300, true, acvType.yeah_am_good);
		AchievementInfo locked = new AchievementInfo(this, 0, 500, false, acvType.perfectionist);
		
		blood_bath.setText("Kill 100 units");
		yeah.setText("Complite tutorial with max stars");
		
		this.achievements.add(blood_bath);
		this.achievements.add(yeah);
		this.achievements.add(locked);
	}
	public void countAchievements(){
		/*
		 * blood bath
		 */
		if(player.getKills() >= 100){
			/*
			 * znajdz blood batha
			 */
			for(int i = 0; i < this.achievements.size(); i++){
				/*
				 * jesli nie jest jeszcze aktyny
				 */
				if(this.achievements.get(i).getAcvType() == acvType.blood_bath && !this.achievements.get(i).isComplited()){
					/*
					 * aktywuj i przydziel bonusy
					 */
					this.achievements.get(i).setComplited(true);
					addBonus(i);
				}
			}
		}
	}
	public void addBonus(int acvNumber){
		/*
		 * dodawanie (int):
		 * 				zycia
		 * 				many
		 * 				odnawianie zycia	(boolean)
		 * 				odnawianie many
		 * 				upg pointy
		 */
		player.addMaxLife((this.achievements.get(acvNumber).getLifeBonus()));
		player.addMaxMana(this.achievements.get(acvNumber).getManaBonus());
		player.setLifeReplenishment((this.achievements.get(acvNumber).getLifeReplenishmentBonus()));
		player.addManaSpeed((this.achievements.get(acvNumber).getManaReplenishmentBonus()));
		player.addUpgPoints(this.achievements.get(acvNumber).getUpgPointsBonus());
	}
	public Player getPlayer(){
		return this.player;
	}
}