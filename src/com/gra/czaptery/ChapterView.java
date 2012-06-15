package com.gra.czaptery;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gra.rozgrywka.GameActivity;
import com.gra.rozgrywka.Level;
import com.gra.rozgrywka.PlayersResults;
import com.gra.rozgrywka.Level.difficulty;
import com.gra.rozgrywka.Player;

	/**
	 * @author Szpada
	 * 
	 * Rozdzial - tutaj znajduja sie levele (a wlsciwie lancuchy leveli), w ktore mozemy zagrac
	 */

public class ChapterView extends SurfaceView{
	
	private long lastClick; // zmienna potrzebna do wyliczenia czasu miedzy kliknieciami
	
	private ChapterLoopThread chapterLoopThread; // watek dla tego widoku
	
	private List<ChapterSwitcher> switchers = new ArrayList<ChapterSwitcher>(); //lista przyciskow do zmiany rozdzialu
	private List<LevelHolder> chapters = new ArrayList<LevelHolder>(); //lista rozdzialow
	private List<LevelChain> levels = new ArrayList<LevelChain>(); // lista leveli
	
	private Rect background = new Rect(0,0,480,800); //tlo (wywalic gdy bedzie zrobiona porzadna grafika!)
	private Paint paint = new Paint();				 //tez wywalic
	
	private int currentChapter = 0;	//zmienna przechowujaca numer rozdzialu w ktorym obecnie sie znajdujemy
	private LevelStats stats;
	
	private PlayersResults results;
	
	private Player player;	//player decycuje o tym ktore levele sa dostepne do przejscia
	private Level level; 	//level wybrany przez gracza
	
	private float h_factor;
	private float w_factor;
	private int coolDown = 200;
	
	public ChapterView(Context context,double w_factor, double h_factor, Player player) {
		super(context);
		
        this.h_factor = (float)h_factor;
 	   	this.w_factor = (float)w_factor;
 	   	
		this.player = player;
		
		try{
			results = player.getPresults();
		}catch (NullPointerException e){ //zajdzie jezeli przekazany player nie ma jeszcze zadnych resultsow
			Log.d("Chapter", "zlapalem nullpointera");
			results = new PlayersResults(); //opcjonalnie tworzy nowe resultsy
			
		}
        chapterLoopThread = new ChapterLoopThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {
               //@Override
               public void surfaceDestroyed(SurfaceHolder holder) {
                      boolean retry = true;
                      chapterLoopThread.setRunning(false);
                      while (retry) {
                             try {
                                   chapterLoopThread.join();
                                   retry = false;
                             } catch (InterruptedException e) {}
                      }
               }
               //@Override
               public void surfaceCreated(SurfaceHolder holder) {
            	   createLevelChains();
            	   chapterLoopThread.setRunning(true);
                   chapterLoopThread.start();
	            }
	            //@Override
	            public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
	            	
	            }
        }); 
	}
	public void onDraw(Canvas canvas){
		canvas.scale(this.w_factor, this.h_factor);
		/*
		 * wywalic jak bedzie gotowe tlo
		 */
		paint.setColor(Color.WHITE);
		canvas.drawRect(background,paint);
		
		/*
		 * przyciski do rozdzialow
		 */
		for(int i = 0; i< this.switchers.size(); i++){
			switchers.get(i).onDraw(canvas);
		}
		
		/*
		 * wyswietlenie listy leveli
		 */
		for(int i = 0; i<this.levels.size(); i++){
			this.levels.get(i).onDraw(canvas);
		}
		/*
		 * wyswietlenie statystyk levelu
		 */
		this.stats.onDraw(canvas);
	}
	public void createLevelChains(){
		/*
		 * tworzenie statystyk levelu
		 */
		this.stats = new LevelStats(this,0,655,true,true,landscape.tutorial);
		/*
		 * tworzenie przyciskow
		 */
		ChapterSwitcher left = new ChapterSwitcher(this,100,10,-1);	//przycisk zmiany rozdzialu (kierunek lewo)
		ChapterSwitcher right = new ChapterSwitcher(this,320,10,1);	//przycisk zmiany rozdzialu (kierunek prawo)
		ChapterSwitcher play = new ChapterSwitcher(this, 380, 690, 0);
		/*
		 * tworzenie leveli
		 */
			/*
			 * rozdzial TUTORIAL
			 */																				//ID,child_ID,parent IDs
			LevelChain Ltutorial1 = new LevelChain(this,0,240,140,false,true,landscape.tutorial,	0,	 1,	 -1);
			LevelChain Ltutorial2 = new LevelChain(this,0,240,280,false,false,landscape.tutorial,	1,	 2,	  0);
			LevelChain Ltutorial3 = new LevelChain(this,0,240,420,false,false,landscape.tutorial,	2,	-1,	  1);
			
			Ltutorial1.setLevel(new Level(difficulty.tutorial1));
			Ltutorial2.setLevel(new Level(difficulty.tutorial2));
			Ltutorial3.setLevel(new Level(difficulty.tutorial3));
			/*
			 * rozdzial NUMER I
			 */
			LevelChain LI1 = new LevelChain(this,1,100,140,false,true,landscape.village,0,1,-1);
			LevelChain LI2 = new LevelChain(this,1,100,280,false,false,landscape.village,1,2,0);
			LevelChain LI3 = new LevelChain(this,1,100,420,false,false,landscape.village,2,6,1);
			
			LevelChain LI4 = new LevelChain(this,1,320,140,false,true,landscape.village,3,4,-1);
			LevelChain LI5 = new LevelChain(this,1,320,280,false,false,landscape.village,4,5,3);
			LevelChain LI6 = new LevelChain(this,1,320,420,false,false,landscape.village,5,6,4);
			
			LevelChain LI7 = new LevelChain(this,1,240,560,false,false,landscape.village,6,-1,5,2);
			
			LI1.setLevel(new Level(difficulty.c1l1));	//LI1.setStars(3);
			LI2.setLevel(new Level(difficulty.c1l2));
			LI3.setLevel(new Level(difficulty.c1l6));
			
			LI4.setLevel(new Level(difficulty.c1l3));
			LI5.setLevel(new Level(difficulty.c1l4));
			LI6.setLevel(new Level(difficulty.c1l5));
			
			LI7.setLevel(new Level(difficulty.c1l7));
			
			/*
			 * rozdzial NUMER 2
			 */																		//ID,child_ID,parent IDs
			LevelChain LII1 = new LevelChain(this,2,20,140,false,false,landscape.tutorial,0,4,-1);
			LevelChain LII2 = new LevelChain(this,2,140,140,false,false,landscape.village,1,4,-1);
			LevelChain LII3 = new LevelChain(this,2,260,140,false,false,landscape.tutorial,2,5,-1);
			LevelChain LII4 = new LevelChain(this,2,380,140,false,false,landscape.village,3,5,-1);
			
			LevelChain LII5 = new LevelChain(this,2,80,280,false,false,landscape.tutorial,4,6,0,1);
			LevelChain LII6 = new LevelChain(this,2,320,280,false,false,landscape.village,5,6,2,3);
			
			LevelChain LII7 = new LevelChain(this,2,240,420,false,false,landscape.village,6,-1,4,5);
			
			LII1.setLevel(new Level(difficulty.c2l1));
			LII2.setLevel(new Level(difficulty.c2l2));
			LII3.setLevel(new Level(difficulty.c2l6));
			
			LII4.setLevel(new Level(difficulty.c2l3));
			LII5.setLevel(new Level(difficulty.c2l4));
			LII6.setLevel(new Level(difficulty.c2l5));
			
			LII7.setLevel(new Level(difficulty.c2l7));
			
			
		/*
		 * tworzenie rozdzialow
		 */
		LevelHolder tutorial = new LevelHolder(Ltutorial1,Ltutorial2,Ltutorial3);
		LevelHolder chapter1 = new LevelHolder(LI1,LI2,LI3,LI4,LI5,LI6,LI7);
		LevelHolder chapter2 = new LevelHolder(LII1,LII2,LII3,LII4,LII5,LII6,LII7);
		/*
		 * stworzenie listy rozdzialow
		 */
		this.chapters.add(tutorial);
		this.chapters.add(chapter1);
		if(LI7.isComplited()){
			this.chapters.add(chapter2);
		}

		/*
		 * wypelnij levele gwaizdkami i active/completed
		 */
		if(results != null){
			for (LevelHolder lh : chapters) {
				lh.updateLevels(results);
				for (LevelChain llc: lh.getLevels()) {
					Log.d("ChapterView",
							Integer.toString(llc.getChapter())+" "+
							Integer.toString(llc.getId())+" "+
							Boolean.toString(llc.getActive())+" "+
							Boolean.toString(llc.getComplited())+" "+
							Integer.toString(llc.getStars())
							);
				}
			}
		/*
		 * odblokuj levele
		 */
			Log.d("Chapter", "odblokowuje levele");
			for(int i = 0; i < chapters.size(); i++){
				for(int j = 0; j < chapters.get(i).getLevels().size(); j++){
					Log.d("Chapter" , "chapter : " + i + "level : " + j);
					if(chapters.get(i).getLevels().get(j).isComplited()){
						this.currentChapter = i;
						levels = chapters.get(i).getLevels();
						unlockLevels(chapters.get(i).getLevels().get(j).getId());
					}
				}
			}
		}
		/*
		 * stworzenie listy switcherow
		 */
		this.switchers.add(left);
		this.switchers.add(right);
		this.switchers.add(play);
		/*
		 * stworzenie listy leveli
		 */
		this.levels = this.chapters.get(this.currentChapter).getLevels();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_UP && System.currentTimeMillis() - lastClick > coolDown) {
			
			float x = event.getX();
			float y = event.getY();
//			Log.d("ChapterView", "byl klik: " + Float.toString(x) + "  "
//					+ Float.toString(y));
			
			x = x / this.w_factor;
			y = y / this.h_factor;
			
				lastClick = System.currentTimeMillis();
				for (int i = 0; i < this.switchers.size(); i++) {
					if (this.switchers.get(i).checkCollision((int) x, (int) y)) {
						if(this.switchers.get(i).getDirection() ==0){
							play(this.level);
						}
						else{
							this.currentChapter += this.switchers.get(i).getDirection();
							if (this.currentChapter < 0) {
								this.currentChapter = this.chapters.size() - 1;
							} else if (this.currentChapter > this.chapters.size() - 1) {
								this.currentChapter = 0;
							}
							setCurrentChapter(this.currentChapter);
						}
					}
				}
				for (int i = 0; i < this.levels.size(); i++) {
					if (this.levels.get(i).checkCollision((int) x, (int) y)) {
						showStats(this.levels.get(i));
						if (this.levels.get(i).isActive()) {
							Log.d("ChapterView",
									"iteracja: " + Integer.toString(i) + "level" + this.levels.get(i));
							this.level = this.levels.get(i).getLevel(); //wybrany przez nas level, dostep przez getLevel()
							this.level.setChapter(currentChapter);
							this.level.setId(this.levels.get(i).getId());
						}
					}
				}
			
		}
		return true;
	}
	
	
	
	public void setCurrentChapter(int chapter){
		levels = this.chapters.get(chapter).getLevels();
	}
	public boolean unlockLevels(int complitedLevelId){
		Log.d("ChapterView", "ODBLOKOWUJE");
		/*
		 * metoda odblokowujaca levele - zaczyna od levelu przekzanego jako argument (jego ID)
		 *  i sprawdza czy odblokowuje kolejny level.
		 */
		int ID = -1;
		/*
		 * sprawdzamy czi ID to nie jest przypadkiem numer na liscie (skraca to czas dzialania)
		 */
		if(levels.get(complitedLevelId).getId() == complitedLevelId){
			ID = complitedLevelId;
		}
		/*
		 * jesli to nie ten numer to przeszukujemy cala liste w poszukiwaniu tego ID
		 */
		else{
			for(int i = 0; i<levels.size(); i++){
				if(levels.get(i).getId() == complitedLevelId){
					ID = i; 
					break;
				}
			}
		}
		Log.d("chapter", "ID : " + ID);
		/*
		 * sprawdzamy czy wybrany level ma dziecko
		 */
		if(levels.get(ID).getChild() == -1){
			/*
			 * jesli nie wyjdz z funkcji
			 */
			return false;
		}
		/*
		 * jesli ma zrob co masz do roboty <3
		 */
		else{
			int child_ID = -1;
			/*
			 * przejscie do dziecka levelu o podanym ID w celu sprawdzenia czy moze zostac odblokowany
			 */
			for(int i = 0; i < levels.size(); i++){
				if(levels.get(i).getId() == levels.get(ID).getChild()){
					child_ID = i;
				}
			}
			/*
			 * jesli znaleziono dziecko
			 */
			if(child_ID != -1){
				int[]parents = new int[levels.get(child_ID).getParents().length];
				for(int i:parents){
					parents[i] = levels.get(child_ID).getParents()[i];
					Log.d("chapter", "parents_ID : " + parents[i]);
				}
				//Log.d("chapter", "parents_ID : " + levels.get(child_ID).getParents());
				for(int i=0; i < levels.get(child_ID).getParents().length; i++){
					Log.d("chapter","sprawdzam rodzicow, rodzic nr : " + (i+1));
					/*
					 * sprawdz wszystkie poza naszym ID, jesli ktorys rodzic nie jest ukonczony wyjdz z funkcji
					 */
					int parent_id = -1;
					int parent_number = -1;
					for(int j = 0; j < levels.size(); j++){
						if(levels.get(j).getId() == levels.get(child_ID).getParents()[i]){
							parent_id = levels.get(j).getId();
							parent_number = j;
							Log.d("chapter", "parent id " + parent_id);
							Log.d("chapter", "parent number " + parent_number);
							if(parent_id != ID && !levels.get(parent_number).isComplited()){
								Log.d("chapter", "rodzic nie ukonczony");
								return false;
							}
						}
					}
				}
				/*
				 * mozna odblokowac dziecko
				 */
				Log.d("chapter","odblokowuje dziecko o numerze : " + levels.get(child_ID).getId());
				levels.get(child_ID).setActive(true);
				return true;
			}
			/*
			 * nie znaleziono dziecka - wyjdz
			 */
			else {
				return false;
			}
		}
	}
	public void showStats(LevelChain lvl){
		/*
		 * metoda wysweitlajaca statystyki wybranego levelu
		 */
		this.stats.setActive(lvl.isActive());
		this.stats.setComplited(lvl.isComplited());
		this.stats.setLand(lvl.getLand());
		this.stats.setStars(lvl.getStars());
	}
	public Level getLevel(){
		return this.level;
	}
	public void play(Level level){
		Context context = getContext();
		Intent GameIntent = new Intent(context,
				GameActivity.class);
		Log.d("ChapterView", "stworzony gameact intent");
		
		GameIntent.putExtra("LEVEL", level);
		Log.d("ChapterView", "wlozony lewel");
		GameIntent.putExtra("PLAYER", player);
		Log.d("ChapterView", "wlozony player");
		
		Intent ChaptersIntent = new Intent(context,
				ChaptersActivity.class);

		context.startActivity(GameIntent);
		Log.d("ChapterView", "wystartowal gameact");

		context.stopService(ChaptersIntent);
		Log.d("ChapterView", "zabity chaptersact");
	}
}
