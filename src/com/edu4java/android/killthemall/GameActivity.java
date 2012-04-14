/**
 * 
 */
package com.edu4java.android.killthemall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Maciej
 *
 */
public class GameActivity extends Activity {
	private SharedPreferences mPrefs;
	private String TAG = "GameActive";
	
	/**
	 * 
	 */
	public void onCreate(Bundle savedInstanceState) {
    	/*
    	 * Gowno potrzebne do skalowania wszystkiego
    	 */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	DisplayMetrics displaymetrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); 
        int height = displaymetrics.heightPixels; 
        int width = displaymetrics.widthPixels;
        double h_factor = height/800.0;
        double w_factor = width/480.0;
        
        super.onCreate(savedInstanceState);
        
        //mPrefs = this.getPreferences(MODE_PRIVATE); 

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this, w_factor, h_factor));
    }
    
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	  // Save UI state changes to the savedInstanceState.
    	  // This bundle will be passed to onCreate if the process is
    	  // killed and restarted.
//	    	  savedInstanceState.putBoolean("MyBoolean", true);
//	    	  savedInstanceState.putDouble("myDouble", 1.9);
//	    	  savedInstanceState.putInt("MyInt", 1);
//	    	  savedInstanceState.putString("MyString", "Welcome back to Android");
    	  // etc.
    	  super.onSaveInstanceState(savedInstanceState);
    	}
    
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	  super.onRestoreInstanceState(savedInstanceState);
    	  // Restore UI state from the savedInstanceState.
    	  // This bundle has also been passed to onCreate.
//	    	  boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
//	    	  double myDouble = savedInstanceState.getDouble("myDouble");
//	    	  int myInt = savedInstanceState.getInt("MyInt");
//	    	  String myString = savedInstanceState.getString("MyString");
    	}
    
    protected void onResume() {
        super.onResume();


    }
    
    protected void onPause() {
    	Log.d("GameActivity", "jestem w GameActivity.onPause()");
    	Intent intnt = new Intent(this , Main.class);
		startActivity(intnt);
        super.onPause();

//	        SharedPreferences.Editor ed = mPrefs.edit();
//	        ed.putInt("view_mode", mCurViewMode);
//	        ed.commit();
    }
}

