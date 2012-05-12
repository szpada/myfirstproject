package com.gra.rozgrywka;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
	
	public ChapterView(Context context,double w_factor, double h_factor) {
		super(context);
		/*
		 * odkomentowac jak wartosci beda poprawnie przesylane
		 */
//        this.h_factor = (float)h_factor;
// 	   	this.w_factor = (float)w_factor;
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
	}
	public void createLevelChains(){
		/*
		 * tworzenie przyciskow
		 */
		ChapterSwitcher left = new ChapterSwitcher(this,100,10,-1);	//przycisk zmiany rozdzialu (kierunek lewo)
		ChapterSwitcher right = new ChapterSwitcher(this,320,10,1);	//przycisk zmiany rozdzialu (kierunek prawo)
		/*
		 * tworzenie leveli
		 */
			/*
			 * rozdzial TUTORIAL
			 */																			//ID,child ID,parent IDs
			LevelChain Ltutorial1 = new LevelChain(this,240,140,true,true,landscape.tutorial,	0,	 1,	 -1);
			LevelChain Ltutorial2 = new LevelChain(this,240,280,false,true,landscape.tutorial,	1,	 2,	  0);
			LevelChain Ltutorial3 = new LevelChain(this,240,420,false,false,landscape.tutorial,	2,	-1,	  1);
			/*
			 * rozdzial NUMER I
			 */
			LevelChain LI1 = new LevelChain(this,100,140,true,true,landscape.village,0,1,-1);
			LevelChain LI2 = new LevelChain(this,100,280,false,true,landscape.village,1,2,0);
			LevelChain LI3 = new LevelChain(this,100,420,false,false,landscape.village,2,6,1);
			
			LevelChain LI4 = new LevelChain(this,320,140,true,true,landscape.village,3,4,-1);
			LevelChain LI5 = new LevelChain(this,320,280,false,true,landscape.village,4,5,3);
			LevelChain LI6 = new LevelChain(this,320,420,false,false,landscape.village,5,6,4);
			
			LevelChain LI7 = new LevelChain(this,240,560,false,false,landscape.village,6,-1,5,2);
		/*
		 * tworzenie rozdzialow
		 */
		LevelHolder tutorial = new LevelHolder(Ltutorial1,Ltutorial2,Ltutorial3);
		LevelHolder village = new LevelHolder(LI1,LI2,LI3,LI4,LI5,LI6,LI6, LI7);
		/*
		 * stworzenie listy rozdzialow
		 */
		this.chapters.add(tutorial);
		this.chapters.add(village);
		/*
		 * stworzenie listy switcherow
		 */
		this.switchers.add(left);
		this.switchers.add(right);
		/*
		 * stworzenie listy leveli
		 */
		this.levels = this.chapters.get(this.currentChapter).getLevels();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int coolDown = 300;
		float x = event.getX();
		float y = event.getY();
			/*
		 * odkomentowac jak juz beda przekazywane wlasciwe wartosci w konstruktorze
		 */
		//x = x / this.w_factor;
		//y = y / this.h_factor;
		if(System.currentTimeMillis() - lastClick > coolDown) {
			lastClick = System.currentTimeMillis();
			for(int i = 0; i < this.switchers.size(); i++){
				if(this.switchers.get(i).checkCollision((int)x, (int)y)){
					this.currentChapter += this.switchers.get(i).getDirection();
					if(this.currentChapter < 0){
						this.currentChapter = this.chapters.size()-1;
					}
					else if(this.currentChapter > this.chapters.size()-1){
						this.currentChapter = 0;
					}
					setCurrentChapter(this.currentChapter);
				}
			}
			for(int i = 0; i < this.levels.size(); i++){
				if(this.levels.get(i).checkCollision((int)x, (int)y)){
					if(this.levels.get(i).isActive()){
						this.levels.get(i).setComplited(true);
						unlockLevels(i);
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
		/*
		 * metoda odblokowujaca levele - zaczyna od levelu przekzanego jako argument (jego ID)
		 *  i sprawdza czy odblokowuje kolejny level.
		 */
		int ID = 0;
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
					ID = levels.get(i).getId(); 
					break;
				}
			}
		}
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
			/*
			 * przejscie do dziecka levelu o podanym ID w celu sprawdzenia czy moze zostac odblokowany
			 */
			int child_ID = -1;
			for(int i = 0; i<levels.size(); i++){
				/*
				 * sprawdz dla ktorego levelu ID jest rowne ID dziecka naszego levelu
				 */
				if(i != ID && levels.get(i).getId() == levels.get(ID).getChild()){
					child_ID = levels.get(i).getId();
					break;
				}
			}
			/*
			 * jesli znaleziono dziecko
			 */
			if(child_ID != -1){
				for(int i=0; i < levels.get(child_ID).getParents().length; i++){
					Log.d("chapter","sprawdzam rodzicow, rodzic nr : " + i);
					/*
					 * sprawdz wszystkie poza naszym ID, jesli ktorys rodzic nie jest ukonczony wyjdz z funkcji
					 */
					if(levels.get(child_ID).getParents()[i] != ID && !levels.get(levels.get(child_ID).getParents()[i]).isComplited()){
						Log.d("chapter","rodzic nr : " + i + " nie zostal ukonczony");
						return false;
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
}
