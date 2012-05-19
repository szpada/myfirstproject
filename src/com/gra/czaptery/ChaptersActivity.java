package com.gra.czaptery;


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
//        int base[][] = {
//        		{1,1,1,1,1},	//ELEKTRYCZNE
//        		{1,1,1,1,0},	//OGNIEN
//        		{1,1,0,0,0},	//WODA
//        		{0,0,0,0,0},	//FIZYCZNE
//        		{0,0,0,0,0}		//SMIERC
//        };
//        Player player = new Player("pies", 1, 1, base, 200, 200, 2, 500, 500, 0 , 0);
        //setContentView(R.layout.options);      
        //setContentView(new TreeView(this,100,100));
        //setContentView(new ChapterView(this,100,100));
        setContentView(new ChapterView(this, 100, 100));
    }
    
    protected void onStop() 
    {
        super.onStop();
        Log.d("ChaptersActivity", "MYonStop is called");
        finish();
    }
    
}