package com.gra.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.gra.R;
import com.gra.achievementy.AchievementsActivity;
import com.gra.czaptery.ChaptersActivity;
import com.gra.drzewko.TreeActivity;
import com.gra.rozgrywka.GameActivity;
import com.gra.zapisy.SaveService;
import com.gra.zapisy.SavedState;

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
        
        // byc moze bedzie mozna wyrzucic bo chyba tak czy inaczej zawsze jest wykonywane onresume (w ktorym jest juz readlaststate)
//        saver = new SaveService(GameMenu.this);
//        ss = saver.readLastState();
//        Log.d("GameMenu","on create wczytuje sava");
//     
        
        Button ResumeGameButton = (Button)findViewById(R.id.ResumeGame);
        
        ResumeGameButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent ResumeGameIntent = new Intent(GameMenu.this,GameActivity.class);
        		ResumeGameIntent.putExtra("RESUMING", true);
        		startActivity(ResumeGameIntent);
        	}
        });
        
              
        Button ChaptersButton = (Button)findViewById(R.id.StartGame);
        ChaptersButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent ChaptersIntent = new Intent(GameMenu.this,ChaptersActivity.class);
        		if (ss!=null) { // ss to obiekt z pliku sava - warunek zajdzie zawsze procz pierwszego uruchomienia
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
        		//nic nie jest przekazywane do drzewka bo i tak drzewko ma odczyt/zapis
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


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		saver = new SaveService(GameMenu.this);
        ss = saver.readLastState();
        Log.d("GameMenu","on resume wczytuje sava");
     
		
		
	}

    


}

