/**
 * 
 */
package com.gra.rozgrywka;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.gra.R;
import com.gra.rozgrywka.Level.difficulty;
import com.gra.zapisy.SaveService;
import com.gra.zapisy.SavedState;

/**
 * @author Maciej
 *
 */
public class GameActivity extends Activity {
	//private SharedPreferences mPrefs;
	private String TAG = "GameActivity";
	private GameView gview;
	//private GameLoopThread gthread;
	private Player gplayer;
	private Level glevel;
	private SaveService saver;
	
	private int base[][] = {
    		{1,0,0,0,0},	//ELEKTRYCZNE
    		{0,0,0,0,0},	//OGNIEN
    		{0,0,0,0,0},	//WODA
    		{0,0,0,0,0},	//FIZYCZNE
    		{0,0,0,0,0}		//SMIERC
    };
	
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
		glevel = new Level(difficulty.tutorial3);
		if(extras !=null) {
			Log.d("GameActivity","byly extrasy");
			resuming = extras.getBoolean("RESUMING");
			if (!resuming) {
				Log.d("GameActivity","nie bylo resuma, wczytuje level i player przekazany z chaptera");
				glevel = (Level) extras.get("LEVEL");
				gplayer = (Player) extras.get("PLAYER");
				
			}
			else {
				gplayer = new Player("pies",0,0,base,500,500,2,100,100, 0, 0);
			}
			
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
        Log.d("GameActivity", "!jestem w GameActivity.onCreate");
        
        saver = new SaveService(GameActivity.this);

        //musze zresetowac zycie i mane playera zeby zawsze na starcie byly pelne
        gplayer.setCurrentMana(500);
        gplayer.setOlympLife(100);
        
        gview = new GameView(this, w_factor, h_factor, glevel, gplayer);

        Log.d("GameActivity", "new gameview");

        
        setContentView(gview);
        
    }
    
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//    	  // Save UI state changes to the savedInstanceState.
//    	  // This bundle will be passed to onCreate if the process is
//    	  // killed and restarted.
//    	super.onSaveInstanceState(savedInstanceState);
//
//    	}
//    
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//    	  super.onRestoreInstanceState(savedInstanceState);
//    	  Log.d("GameActivity", "jestem w GameActivity.onRestoreIS");
//    	  // Restore UI state from the savedInstanceState.
//    	  // This bundle has also been passed to onCreate.
//    	  
//    	  
//    	}
    
    protected void onResume() {
        super.onResume();
        Log.d("GameActivity", "!jestem w GameActivity.onResume()");
        
        if (resuming) { //only if the game is being resumed.
        	readLastSavedState();
        	Log.d("GameActivity", "read last saved state");
            
        }
		
		
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
		Log.d("GameAct", "state zapisany");
	}

}

