/**
 * 
 */
package com.gra.rozgrywka;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Maciej
 *
 */
public class GameActivity extends Activity {
	private SharedPreferences mPrefs;
	private String TAG = "GameActive";
	private GameView gview;
	private GameLoopThread gthread;
	private Player gplayer;
	private SaveService saver;
	
	private boolean resuming = false;
	
	/**
	 * 
	 */
	public void onCreate(Bundle savedInstanceState) {
    	/*
    	 * Gowno potrzebne do skalowania wszystkiego
    	 */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
//		boolean resuming = false;
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
			Log.d("GameActivity","byly extrasy");
			resuming = extras.getBoolean("RESUMING");
			Log.d("GameActivity",Boolean.toString(resuming));
		}
		
    	DisplayMetrics displaymetrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); 
        int height = displaymetrics.heightPixels; 
        int width = displaymetrics.widthPixels;
        double h_factor = height/800.0;
        double w_factor = width/480.0;
        
        super.onCreate(savedInstanceState);
        
        //mPrefs = this.getPreferences(MODE_PRIVATE); 
        Log.d("GameActivity", "jestem w GameActivity.onCreate");
        
        saver = new SaveService(GameActivity.this);
        
        boolean x = saver.exists();

        
        Level lvl = new Level(difficulty.tutorial);
        
        
        gview = new GameView(this, w_factor, h_factor, lvl);
        
        Log.d("GameActivity", "new gameview");
        gthread = gview.getGameLoopThread();
        Log.d("GameActivity", "get thread");
        
//        tymczasowo to usuwamy zeby unitsy sie nie ladowaly podwojnie
//        if (resuming) { //only if the game is beeing resumed.
//        	readLastSavedState();
//        }
        Log.d("GameActivity", "read last saved state");
        
//        if (savedInstanceState != null) {
//        	Log.d("GameActivity", "jestem w GameActivity.niepustySIS");
//        	gplayer = gview.getPlayer();
//        	int god = savedInstanceState.getInt("GOD");
//        	int attack = savedInstanceState.getInt("ATTACK");
//	    	gplayer.setCurrentAttack(attack);
//	    	gplayer.setCurrentGod(god);
//        	gview.setPlayer(gplayer);
//        	
//        }
        
        setContentView(gview);
        
    }
    
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	  // Save UI state changes to the savedInstanceState.
    	  // This bundle will be passed to onCreate if the process is
    	  // killed and restarted.
    	super.onSaveInstanceState(savedInstanceState);
//    	  if (savedInstanceState != null) {
//	    	  savedInstanceState.putInt("GOD", gview.getPlayer().getCurrentGod());
//	    	  savedInstanceState.putInt("ATTACK", gview.getPlayer().getCurrentAttack());
//	    	  Log.d("GameActivity", "jestem w GameActivity.onSaveIS");
//	    	  
//    	  }
	    	  
    	  
    	}
    
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	  super.onRestoreInstanceState(savedInstanceState);
    	  Log.d("GameActivity", "jestem w GameActivity.onRestoreIS");
    	  // Restore UI state from the savedInstanceState.
    	  // This bundle has also been passed to onCreate.
    	  
    	  
    	}
    
    protected void onResume() {
        super.onResume();
        Log.d("GameActivity", "jestem w GameActivity.onResume()");
        
        if (resuming) { //only if the game is beeing resumed.
        	readLastSavedState();
        }
		
		Log.d("GameActivity", "read last saved state");
        
    }
    
    private void readLastSavedState() {
    	SavedState p = saver.readLastState();
        
        if (p != null) {
        	Log.d("GameActivity", "laduje playera z pamieci");
        	gview.setSerializableAttacks(p.getAttack());
        	gview.setSerializableEnemies(p.getEnemies());
        	gview.setSerializableEnemyAttacks(p.getEnemyAttacks());
        	gview.setLevel(p.getLevel());
        	gview.setPlayer(p.getPlayer());
        	gview.setSerializableTemps(p.getTemps());
        	gview.setWaves(p.getWaves());
        	gview.setCurrent_wave(p.getCurrent_wave());
        	
        	
        	
        }
		
	}

	protected void onPause() {
    	super.onPause();
    	Log.d("GameActivity", "jestem w GameActivity.onPause()");
//    	gview.getGameLoopThread().
    	saveCurrentState();
    	
        
        	
    }
	
	protected void onStop() 
    {
        super.onStop();
        Log.d("GameActivity", "MYonStop is called");
        finish();
    }

	private void saveCurrentState() {
		SavedState p = new SavedState();
		p.setAttack(gview.getSerializableAttacks());
		p.setEnemies(gview.getSerializableEnemies());
		p.setEnemyAttacks(gview.getSerializableEnemyAttacks());
		p.setLevel(gview.getLevel());
		p.setPlayer(gview.getPlayer());
		p.setTemps(gview.getSerializableTemps());
		p.setWaves(gview.getWaves());
		p.setCurrent_wave(gview.getCurrent_wave());
		saver.save(p);
		
	}
	

	
}

