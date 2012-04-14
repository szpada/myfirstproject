package com.edu4java.android.killthemall;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Maciej
 * tylko na uruchomienie
 */

public class Main extends Activity {
    /** Called when the activity is first created. */
	private SharedPreferences mPrefs;
	private String TAG = "Main";
	
    public void onCreate(Bundle savedInstanceState) {
    	/*
    	 * Gowno potrzebne do skalowania wszystkiego
    	 */
    	DisplayMetrics displaymetrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); 
        
//        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//        
//        int width = display.getWidth();
//        int height = display.getHeight();
//        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        
        int height = displaymetrics.heightPixels; 
        int width = displaymetrics.widthPixels;
        
        double hfactor = height/800.0;
        double wfactor = width/480.0;
        Log.d("MAIN", "height factor = " + Double.toString(hfactor) + ", width factor = " + Double.toString(wfactor));
        
        
        
        double kfactor = hfactor/wfactor;
        Log.d("MAIN", "difference factor = " + Double.toString(kfactor));
        
        super.onCreate(savedInstanceState);
        
        mPrefs = this.getPreferences(MODE_PRIVATE); 

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new MenuView(this));//,hfactor,wfactor,kfactor));
        
    }
    
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	  // Save UI state changes to the savedInstanceState.
    	  // This bundle will be passed to onCreate if the process is
    	  // killed and restarted.
//    	  savedInstanceState.putBoolean("MyBoolean", true);
//    	  savedInstanceState.putDouble("myDouble", 1.9);
//    	  savedInstanceState.putInt("MyInt", 1);
//    	  savedInstanceState.putString("MyString", "Welcome back to Android");
    	  // etc.
    	  super.onSaveInstanceState(savedInstanceState);
    	}
    
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	  super.onRestoreInstanceState(savedInstanceState);
    	  // Restore UI state from the savedInstanceState.
    	  // This bundle has also been passed to onCreate.
//    	  boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
//    	  double myDouble = savedInstanceState.getDouble("myDouble");
//    	  int myInt = savedInstanceState.getInt("MyInt");
//    	  String myString = savedInstanceState.getString("MyString");
    	}
    
    protected void onResume() {
        super.onResume();


    }
    
    protected void onPause() {
    	MenuThread.interrupted();
        super.onPause();

//        SharedPreferences.Editor ed = mPrefs.edit();
//        ed.putInt("view_mode", mCurViewMode);
//        ed.commit();
    }
    
}

