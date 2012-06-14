package com.gra.czaptery;

import com.gra.R;
import com.gra.rozgrywka.Level;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

	/**
	 * @author Szpada
	 * klasa przedstawiajaca poszczegolne levele i zaleznosci miedzy nimi
	 * (przechowuje swojego rodzica oraz dzieci przez co mozna latwo okreslic
	 * ktory level odblokowuje ktory itd). Oprocz tego przechowuje statystyki
	 * levelu - gwiazdki :C
	 * 
	 *...
	 *
	 *zamiast jebac sie z tym tak jak w treeView gdzie wszystko jest na sztywno T_T
	 */
enum landscape{tutorial, village, locked}	//enum zawierajacy "ikonki" leveli

public class LevelChain {
	private ChapterView view;
	
	private int x;
	private int y;
	
	private boolean active;		//zmienna okreslajaca czy poziom jest aktywny
	private boolean complited; 	//zmienna okreslajaca czy poziom zostal ukonczony
	private landscape land; 	//zmienna decydujaca o bitmapie levelu
	/*
	 * parent ID :
	 * -1 - ojciec globalny (zawsze aktywny)
	 * 0 - x - zwykle ID po kotrych sprawdzamy czy dany jest aktywny
	 */
	private int chapter;		//zmienna przechowujaca rozdzial do ktorgo nalezy dany level
	private int id;
	private int child_id;		//zmienna przechowujaca ID dziecka
	private int parents[];		//tablica przechowujaca ID rodzicow
	
	private int width;
	private int height;
	private Bitmap bmp;
	
	private int stars = 1;		//ile gwiazdek dostalismy za ukonczenie tego levelu
	
	private Level level;		//level ktory reprezentuje dany obiekt
	
	public LevelChain(ChapterView view,int chapter, int x, int y,boolean complited, boolean active, landscape land, int id, int child_id, int...parents_id){
		this.view = view;
		this.x = x;
		this.y = y;
		this.land = land;
		this.id = id;
		this.child_id = child_id;
		this.complited = complited;
		this.active = active;
		this.chapter = chapter;
		parents = new int[parents_id.length];
		for(int i = 0; i < parents_id.length; i++){
			Log.d("LevelChain", "kurwa jebana id :" + parents_id[i]);
			parents[i] = parents_id[i];
		}	
//		for(int i:parents){
//			Log.d("LevelChain", "parent id :" + parents_id[i]);
//			
//		}
		if(active){
			switch(land){
			case tutorial:
				this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.leveltutorial);
				break;
			case village:
				this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.levelvillage);
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
		Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		canvas.drawBitmap(this.bmp, src, dst, null);
		if(this.active && !this.complited){
			Paint paint = new Paint();
			paint.setTextSize(20.0f);
			paint.setColor(Color.GREEN);
			canvas.drawText("NEW", this.x, this.y, paint);
		}
	}
	public int getId(){
		return this.id;
	}
	public int[] getParents(){
		return this.parents;
	}
	public int getChild(){
		return this.child_id;
	}
	public int getChapter(){
		return this.chapter;
	}
	
	public boolean getActive() {
		return this.active;
	}
	
	public boolean getComplited() {
		return this.complited;
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
			}
		}
	}
	public void setStars(int stars){
		this.stars = stars;
	}
	public int getStars(){
		return this.stars;
	}
	public landscape getLand(){
		return this.land;
	}
	public void setLevel(Level level){
		this.level = level;
	}
	public Level getLevel(){
		return this.level;
	}
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
	public void setId(int id) {
		this.id = id;
	}
}
