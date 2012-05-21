package com.gra.menu;

import com.gra.R;
import com.gra.achievementy.AchievementsActivity;
import com.gra.czaptery.ChaptersActivity;
import com.gra.drzewko.TreeActivity;
import com.gra.rozgrywka.GameActivity;
import com.gra.zapisy.SaveService;
import com.gra.zapisy.SavedState;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameMenu extends Activity {
	
	private SaveService saver;
	private SavedState ss = null;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.gamemenu);
        
        
        saver = new SaveService(GameMenu.this);
        ss = saver.readLastState();
        
        
        Button ResumeGameButton = (Button)findViewById(R.id.ResumeGame);
        
        ResumeGameButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent ResumeGameIntent = new Intent(GameMenu.this,GameActivity.class);
        		ResumeGameIntent.putExtra("RESUMING", true);
        		startActivity(ResumeGameIntent);
        	}
        });
        
        
        
        
//        Button StartGameButton = (Button)findViewById(R.id.StartGame);
//        StartGameButton.setOnClickListener(new OnClickListener() {
//        	
//        	//@Override
//			public void onClick(View v) {
//        		Intent StartGameIntent = new Intent(GameMenu.this,GameActivity.class);
//        		StartGameIntent.putExtra("RESUMING", false);
//        		startActivity(StartGameIntent);
//        	}
//        });
        
        Button ChaptersButton = (Button)findViewById(R.id.StartGame);
        ChaptersButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent ChaptersIntent = new Intent(GameMenu.this,ChaptersActivity.class);
        		if (ss!=null) {
        			ChaptersIntent.putExtra("PLAYER", ss.getPlayer());
        		}
        		startActivity(ChaptersIntent);
        	}
        });
        
        Button TreeButton = (Button)findViewById(R.id.Tree);
        TreeButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent TreeIntent = new Intent(GameMenu.this, TreeActivity.class);
        		
        		startActivity(TreeIntent);
        	}
        });
        
        Button AchievementsButton = (Button)findViewById(R.id.Achievements);
        AchievementsButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent AchievementsIntent = new Intent(GameMenu.this,AchievementsActivity.class);
        		startActivity(AchievementsIntent);
        	}
        });
        

    }
}
