package com.gra.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.gra.R;

public class MainMenu extends Activity {
	
//	private SaveService saver;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.mainmenu);
        
        
//        saver = new SaveService(MainMenu.this);
       
        
        Button PlayGameButton = (Button)findViewById(R.id.Play);
        
        PlayGameButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent PlayGameIntent = new Intent(MainMenu.this,GameMenu.class);
        		
        		startActivity(PlayGameIntent);
        	}
        });
        
        
        
        Button OptionsButton = (Button)findViewById(R.id.Options);
        OptionsButton.setOnClickListener(new OnClickListener() {
        	
        	//@Override
			public void onClick(View v) {
        		Intent OptionsIntent = new Intent(MainMenu.this,Options.class);
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