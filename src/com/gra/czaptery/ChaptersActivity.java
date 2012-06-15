package com.gra.czaptery;


import com.gra.rozgrywka.Player;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


/**
 * @author Maciej
 * obsluguje drzewko rozdzialow
 * ma przekazanego Player'a - nie uzywa sava bo samo w sobie nie wprowadza zmian
 * wewnatrz ChapterView pozwala na uruchomienie planszy
 */
public class ChaptersActivity extends Activity {
//    private int lastlevelstars = -1;
    private int base[][] = {
    		{1,0,-1,-1,-1},	//ELEKTRYCZNE
    		{0,-1,-1,-1,-2},	//OGNIEN
    		{0,-1,-2,-2,-2},	//WODA
    		{-2,-2,-2,-2,-2},	//FIZYCZNE
    		{-2,-2,-2,-2,-2}
    };

    private Player gplayer;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        
        
        
		if(extras !=null) {
			gplayer = (Player) extras.get("PLAYER"); //player przekazany z menu albo z planszy
			Log.d("ChaptersActivity","player zaladowany");
//			if (extras.containsKey("STARS")) { // act deprecated
//				lastlevelstars  = (int) extras.getInt("STARS");
//			}
		
		}
		else {
			gplayer = new Player("pies",0,0,base,500,500,2,100,100, 0, 0); //pierwsze uruchomienie
		}
			
		
			
		
		DisplayMetrics displaymetrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); 
        int height = displaymetrics.heightPixels; 
        int width = displaymetrics.widthPixels;
        double h_factor = height/800.0;
        double w_factor = width/480.0;
		
        setContentView(new ChapterView(this, w_factor, h_factor, gplayer));
    }
    
    protected void onStop() 
    {
        super.onStop();
        Log.d("ChaptersActivity", "MYonStop is called");
        finish();
    }
    
    
    
    
}