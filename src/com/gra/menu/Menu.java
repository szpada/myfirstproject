package com.gra.menu;

import com.gra.R;
import com.gra.rozgrywka.GameActivity;
import com.gra.rozgrywka.SaveService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends Activity {
	
	private SaveService saver;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        
        
        saver = new SaveService(Menu.this);
       
        
        Button ResumeGameButton = (Button)findViewById(R.id.ResumeGame);
        
        ResumeGameButton.setOnClickListener(new OnClickListener() {
        
        	
        	//@Override
			public void onClick(View v) {
        		Intent ResumeGameIntent = new Intent(Menu.this,GameActivity.class);
        		ResumeGameIntent.putExtra("RESUMING", true);
        		startActivity(ResumeGameIntent);
        	}
        });
        
        Button StartGameButton = (Button)findViewById(R.id.StartGame);
        StartGameButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent StartGameIntent = new Intent(Menu.this,GameActivity.class);
        		StartGameIntent.putExtra("RESUMING", false);
        		startActivity(StartGameIntent);
        	}
        });
        
        Button OptionsButton = (Button)findViewById(R.id.Options);
        OptionsButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent OptionsIntent = new Intent(Menu.this,Options.class);
        		startActivity(OptionsIntent);
        	}
        });
        
//        Button CreditsButton = (Button)findViewById(R.id.Credits);
//        CreditsButton.setOnClickListener(new OnClickListener() {
//        	
//        	@Override
//			public void onClick(View v) {
//        		Intent CreditsIntent= new Intent(Menu.this,Credits.class);
//        		startActivity(CreditsIntent);
//        	}
//        });
    }
}