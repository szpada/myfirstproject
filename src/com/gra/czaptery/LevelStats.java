package com.gra.czaptery;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

	/**
	 * @author Szpada
	 * 
	 * klasa przedstawiajaca statystyke danego levelu - TYLKO GWIAZDKI.
	 *  Przy nowym levelu bedzie wyswietlal puste dane
	 */

public class LevelStats {
		private ChapterView view;
		
		private int x;
		private int y;
		
		private boolean active;		//zmienna okreslajaca czy poziom jest aktywny
		private boolean complited; 	//zmienna okreslajaca czy poziom zostal ukonczony
		private landscape land; 	//zmienna decydujaca o bitmapie levelu
		/*
		 * ikona leveli
		 */
		private int width;
		private int height;
		private Bitmap bmp;
		/*
		 * panel statystyk levelu
		 */
		private Bitmap stats;
		private int stats_width;
		private int stats_height;
		/*
		 * gwiazdki zdobyte w levelu
		 */
		private Bitmap stars_bmp;
		private int stars_width;
		private int stars_height;
		
		private int stars;			//ile gwiazdek dostalismy za ukonczenie tego levelu
		
		public LevelStats(ChapterView view,int x, int y,boolean complited, boolean active, landscape land){
			this.view = view;
			this.x = x;
			this.y = y;
			this.land = land;
			this.complited = complited;
			this.active = active;
			this.stats = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.chapterinfo);
			this.stats_width = stats.getWidth();
			this.stats_height = stats.getHeight();
			this.stars_bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.star);
			this.stars_width = stars_bmp.getWidth();
			this.stars_height = stars_bmp.getHeight();
			if(active){
				switch(land){
				case tutorial:
					this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.leveltutorial);
					break;
				case village:
					this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.levelvillage);
					break;
				case locked:
					this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.levellocked);
					break;
				}
			}
			else{
				this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.levellocked);
			}
			this.width = this.bmp.getWidth();
			this.height = this.bmp.getHeight();
		}
		public void onDraw(Canvas canvas){
			int srcX = 0;
			int srcY = 0;
			int iconX = 4;
			int iconY = 30;
			/*
			 * wyswietlenie ikony levelu
			 */
			Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
			Rect dst = new Rect(this.x + iconX, this.y + iconY, this.x + iconX + this.width, this.y + iconY + this.height);
			canvas.drawBitmap(this.bmp, src, dst, null);
			/*
			 * wyswietlenie calego panelu statystyk levelu
			 */
			src = new Rect(srcX, srcY, srcX + this.stats_width, srcY + this.stats_height);
			dst = new Rect(this.x, this.y, this.x + this.stats_width, this.y + this.stats_height);
			canvas.drawBitmap(this.stats, src, dst, null);
			iconX = 98;
			iconY = 30;
			for(int i = 0; i < this.stars; i++){
				src = new Rect(srcX, srcY, srcX + this.stars_width, srcY + this.stars_height);
				dst = new Rect(this.x + iconX + i * this.stars_width, this.y + iconY, this.x + iconX + this.stars_width + i * this.stars_width, this.y + iconY + this.stars_height);
				canvas.drawBitmap(this.stars_bmp, src, dst, null);
				iconX +=2;
			}
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
			if(state){
				this.active = true;
			}
		}
		public boolean isActive(){
			return this.active;
		}
		public void setActive(boolean state){
			this.active = state;
			if(state){
				switch(this.land){
				case tutorial:
					this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.leveltutorial);
					break;
				case village:
					this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.levelvillage);
					break;
				case locked:
					this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.levellocked);
					break;
				}
			}
		}
		public void setStars(int stars){
			this.stars = stars;
		}
		public int getStars(){
			return this.stars;
		}
		public void setLand(landscape land){
			this.land = land;
		}
}

