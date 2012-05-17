package com.gra.achievementy;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author Szpada
 * 
 * klasa przedstawiajaca dany achievement - jego opis, ikonke i bonusy jakie daje
 */

enum acvType{blood_bath, perfectionist, explorer, yeah_am_good};

public class AchievementInfo {
	private AchievementView view;
	
	private int x;
	private int y;
	
	private boolean complited; 	//zmienna okreslajaca czy poziom zostal ukonczony
	/*
	 * ikona achievementu
	 */
	private int width;
	private int height;
	private Bitmap bmp;
	/*
	 * panel achievementu
	 */
	private Bitmap panel;
	private int panel_width;
	private int panel_height;
	/*
	 * info na temat achievementu
	 */
	private Bitmap bonus;
	private int bonus_width;
	private int bonus_height;
	
	private String text = "---"; //informacje na temat achievementu
	private Paint paint;
	
	private acvType type;
	
	/*
	 * bonusy jakie daja achievementy
	 */
	private int	lifeBonus = 0;
	private int manaBonus = 0;
	private int	manaReplenishmentBonus = 0;
	private int upgPointsBonus = 0;
	private boolean lifeReplenishmentBonus = false;
	
	public AchievementInfo(AchievementView view,int x, int y,boolean complited, acvType type){
		this.view = view;
		this.x = x;
		this.y = y;
		this.complited = complited;
		this.panel = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.acvpanel);
		this.panel_width = panel.getWidth();
		this.panel_height = panel.getHeight();
		this.type = type;
		if(complited){
			switch(type){
			case blood_bath:
				this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.bloodbath);
				this.bonus = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.bad1);
				this.upgPointsBonus = 1;
				this.lifeBonus = 50;
				break;
			case perfectionist:
				this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.levelvillage);
				this.bonus = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.bad1);
				/*
				 * nowy level (jeszcze nie wiem jak to zrobic - potem wymysle)
				 */
				break;
			case explorer:
				this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.levellocked);
				this.bonus = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.bad1);
				this.manaBonus = 100;
				this.lifeBonus = 50;
				this.lifeReplenishmentBonus = true;
				break;
			case yeah_am_good:
				this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.yeah);
				this.bonus = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.bad1);
				this.upgPointsBonus = 1;
			}
		}
		else{
			this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.acvlocked);
			this.bonus = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.acvlocked);
		}
		this.width = this.bmp.getWidth();
		this.height = this.bmp.getHeight();
		this.bonus_width = bonus.getWidth();
		this.bonus_height = bonus.getHeight();
		this.paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(20.0f);
	}
	
	public void onDraw(Canvas canvas){
		int srcX = 0;
		int srcY = 0;
		int iconX = 8;
		int iconY = 8;
		/*
		 * wyswietlenie calego panelu achievementu
		 */
		Rect src = new Rect(srcX, srcY, srcX + this.panel_width, srcY + this.panel_height);
		Rect dst = new Rect(this.x, this.y, this.x + this.panel_width, this.y + this.panel_height);
		canvas.drawBitmap(this.panel, src, dst, null);
		/*
		 * wyswietlenie ikony achievementu
		 */
		src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		dst = new Rect(this.x + iconX, this.y + iconY, this.x + iconX + this.width, this.y + iconY + this.height);
		canvas.drawBitmap(this.bmp, src, dst, null);
		
		iconX = 376;
		src = new Rect(srcX, srcY, srcX + this.bonus_width, srcY + this.bonus_height);
		dst = new Rect(this.x + iconX, this.y + iconY, this.x + iconX + this.bonus_width, this.y + iconY + this.bonus_height);
		canvas.drawBitmap(this.bonus, src, dst, null);
		
		iconX = 120;
		iconY = 60;
		canvas.drawText(this.text, this.x + iconX, this.y + iconY, this.paint);
	}
	
	public boolean checkCollision(int x, int y){
		Rect rec = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		if(rec.contains(x, y)){
			return true;
		}
		return false;
	}
	
	public boolean isComplited(){
		return this.complited;
	}
	
	public void setComplited(boolean state){
		this.complited = state;
	}
	
	public void setText(String text){
		this.text = text;
	}
	public acvType getAcvType(){
		return this.type;
	}
	public int getLifeBonus(){
		return this.lifeBonus;
	}
	public int getManaBonus(){
		return this.manaBonus;
	}
	public boolean getLifeReplenishmentBonus(){
		return this.lifeReplenishmentBonus;
	}
	public int getManaReplenishmentBonus(){
		return this.manaReplenishmentBonus;
	}
	public int getUpgPointsBonus(){
		return this.upgPointsBonus;
	}
}
