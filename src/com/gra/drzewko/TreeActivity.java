/**
 * 
 */
package com.gra.drzewko;

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
	
	
	public void onCreate(Bundle savedInstanceState) {
		Log.d("TreeAct","Act rozpoczete...");
		   
	requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);

   Log.d("TreeAct","ustawiam view...");
   
   DisplayMetrics displaymetrics = new DisplayMetrics(); 
   getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); 
   int height = displaymetrics.heightPixels; 
   int width = displaymetrics.widthPixels;
   double h_factor = height/800.0;
   double w_factor = width/480.0;
   
   
    setContentView(new TreeView(this, w_factor, h_factor));
    Log.d("TreeAct","view ustawiony");
    
	}
    
	
	protected void onStop() 
    {
        super.onStop();
        Log.d("TreeAct", "MYonStop is called");
        finish();
    }
	
	
}
