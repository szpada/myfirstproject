package com.gra.czaptery;


import com.gra.rozgrywka.Player;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class ChaptersActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        
        Player gplayer = null;
		if(extras !=null) {
			gplayer = (Player) extras.get("PLAYER");
		}
        setContentView(new ChapterView(this, 100, 100, gplayer));
    }
    
    protected void onStop() 
    {
        super.onStop();
        Log.d("ChaptersActivity", "MYonStop is called");
        finish();
    }
    
    
    
    
}