/**
 * 
 */
package com.gra.drzewko;

import com.gra.menu.GameMenu;
import com.gra.rozgrywka.Player;
import com.gra.zapisy.SaveService;
import com.gra.zapisy.SavedState;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Maciej
 * activity obslugujace drzewko, wczytuje dane o graczu i uruchamia wju
 */
public class TreeActivity extends Activity {

	/**
	 * 
	 */
	private SaveService saver;
	private SavedState ss = null;
	private Player tplayer;
	
	public void onCreate(Bundle savedInstanceState) {
		Log.d("TreeAct","Act rozpoczete...");
		   
	requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    
    saver = new SaveService(TreeActivity.this);
    ss = saver.readLastState();
   
   DisplayMetrics displaymetrics = new DisplayMetrics(); 
   getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); 
   int height = displaymetrics.heightPixels; 
   int width = displaymetrics.widthPixels;
   double h_factor = height/800.0;
   double w_factor = width/480.0;
   
   if (ss!=null) {
	   tplayer = ss.getPlayer();
	   Log.d("TreeAct","player odczytany");
   }
   else {
	   int[][] base = {
			   {1,0,-1,-1,-1}, //ELEKTRYCZNE
			    {0,-1,-1,-1,-2}, //OGNIEN
			    {0,-1,-2,-2,-2}, //WODA
			    {-2,-2,-2,-2,-2}, //FIZYCZNE
			    {-2,-2,-2,-2,-2} //SMIERC 
	   };
	   tplayer = new Player("pies",0,0,base,500,500,2,100,100, 0, 0);
   }
   	
   
    setContentView(new TreeView(this, w_factor, h_factor, tplayer));
    Log.d("TreeAct","view ustawiony");
    
	}
    
	
	protected void onStop() 
    {
        super.onStop();
        Log.d("TreeAct", "My onStop is called");
        savePlayer();
        finish();
    }


	private void savePlayer() {
		if (ss!=null) {
		ss.setPlayer(tplayer);
		}
		else {
			ss = new SavedState();
			ss.setPlayer(tplayer);
		}
		saver.save(ss);
	}
	
	
}
