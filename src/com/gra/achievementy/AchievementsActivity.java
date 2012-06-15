package com.gra.achievementy;


import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.gra.rozgrywka.Player;

public class AchievementsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        int base[][] = {
        		{1,0,-1,-1,-1},	//ELEKTRYCZNE
        		{0,-1,-1,-1,-2},	//OGNIEN
        		{0,-1,-2,-2,-2},	//WODA
        		{-2,-2,-2,-2,-2},	//FIZYCZNE
        		{-2,-2,-2,-2,-2}	//SMIERC
        };
        Player player = new Player("piesek", 1, 1, base, 200, 200, 2, 500, 500, 0 , 0);
        
        DisplayMetrics displaymetrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); 
        int height = displaymetrics.heightPixels; 
        int width = displaymetrics.widthPixels;
        double h_factor = height/800.0;
        double w_factor = width/480.0;
        
        
        
        setContentView(new AchievementView(this, w_factor, h_factor, player));
    }
    
    protected void onStop() 
    {
        super.onStop();
        Log.d("AchievementsActivity", "MYonStop is called");
        finish();
    }
    
}