/**
 * 
 */
package com.gra.drzewko;

import android.app.Activity;
import android.os.Bundle;
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
//    int base[][] = {
//    		{1,1,1,1,1},	//ELEKTRYCZNE
//    		{1,1,1,1,0},	//OGNIEN
//    		{1,1,0,0,0},	//WODA
//    		{0,0,0,0,0},	//FIZYCZNE
//    		{0,0,0,0,0}		//SMIERC
//    };
   Log.d("TreeAct","ustawiam view...");
    setContentView(new TreeView(this, 100, 100));
    Log.d("TreeAct","view ustawiony");
    
	}
    
	
	protected void onStop() 
    {
        super.onStop();
        Log.d("TreeAct", "MYonStop is called");
        finish();
    }
	
	
}
