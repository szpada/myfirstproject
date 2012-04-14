package com.edu4java.android.killthemall;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

/**
 * @author Maciej
 * tylko na uruchomienie
 */

public class Main extends Activity {
    /** Called when the activity is first created. */
	private SharedPreferences mPrefs;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mPrefs = this.getPreferences(MODE_PRIVATE); 

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(new MenuView(this));
        setContentView(new GameView(this));
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
        super.onPause();

//        SharedPreferences.Editor ed = mPrefs.edit();
//        ed.putInt("view_mode", mCurViewMode);
//        ed.commit();
    }
    
}

